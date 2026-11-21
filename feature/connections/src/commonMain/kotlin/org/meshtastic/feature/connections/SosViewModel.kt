/*
 * Copyright (c) 2026 Smart Lab Siena
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
@file:Suppress("MagicNumber")

package org.meshtastic.feature.connections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import org.koin.core.annotation.KoinViewModel
import org.meshtastic.core.common.util.nowMillis
import org.meshtastic.core.model.MessageStatus
import org.meshtastic.core.model.NodeAddress
import org.meshtastic.core.repository.NodeRepository
import org.meshtastic.core.repository.PacketRepository
import org.meshtastic.core.repository.PhoneLocationProvider
import org.meshtastic.core.repository.RadioConfigRepository
import org.meshtastic.core.repository.usecase.SendMessageOutcome
import org.meshtastic.core.repository.usecase.SendMessageUseCase
import kotlin.math.roundToLong

/** Where the coordinates in the SOS text come from. */
enum class SosPosition {
    /** The board's own position (GPS or fixed). */
    BOARD,

    /** The phone's position, used when the board has none (for example a board without GPS). */
    PHONE,

    /** No coordinates available: the SOS goes out without them. */
    NONE,
}

/** What the SOS button is doing, as shown to the user. */
sealed interface SosState {
    /** Nothing is happening. */
    data object Idle : SosState

    /** The button was held: [secondsLeft] seconds to cancel before anything is sent. */
    data class Countdown(val secondsLeft: Int) : SosState

    /**
     * The alarm is on and repeating. [sent] messages were handed to the radio, the last at [lastSentMillis];
     * [heardByOthers] is true when the radio reported that another node repeated one of them.
     */
    data class Active(
        val sent: Int,
        val lastSentMillis: Long,
        val heardByOthers: Boolean,
        val position: SosPosition,
        val failed: Boolean,
    ) : SosState

    /** The radio has no channel named "S.O.S.", so there is nowhere to send the alarm. */
    data object NoChannel : SosState
}

/**
 * Smartlab LoRa SOS: a plain text message on the public S.O.S. channel (found by name), repeated until cancelled. It
 * uses nothing beyond what Meshtastic already carries, so any Meshtastic app shows it as a normal text message.
 *
 * Text: `SOS <short name> <lat>,<lon> bat <n>%`, with the position of the board when it has a valid one, else the
 * phone's position (a board without GPS is a normal B.O.B.), else `posizione n.d.`.
 */
@KoinViewModel
class SosViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val radioConfigRepository: RadioConfigRepository,
    private val nodeRepository: NodeRepository,
    private val packetRepository: PacketRepository,
    private val phoneLocationProvider: PhoneLocationProvider,
) : ViewModel() {

    private val _state = MutableStateFlow<SosState>(SosState.Idle)
    val state: StateFlow<SosState> = _state.asStateFlow()

    private var job: Job? = null
    private var contactKey: String? = null

    /** Starts the countdown; when it reaches zero the alarm begins. No effect if one is already running. */
    fun start() {
        if (job?.isActive == true) return
        job =
            viewModelScope.launch {
                val key = findSosContactKey()
                if (key == null) {
                    _state.value = SosState.NoChannel
                    return@launch
                }
                contactKey = key
                for (left in COUNTDOWN_SECONDS downTo 1) {
                    _state.value = SosState.Countdown(left)
                    delay(1_000)
                }
                runAlarm(key)
            }
    }

    /**
     * Cancels the countdown (nothing is sent) or, if the alarm is on, sends the "cancelled" message once and stops
     * repeating.
     */
    fun cancel() {
        val wasActive = _state.value is SosState.Active
        job?.cancel()
        job = null
        _state.value = SosState.Idle
        val key = contactKey
        if (wasActive && key != null) {
            viewModelScope.launch {
                val short = shortName()
                runCatching { sendMessageUseCase.invoke("SOS ANNULLATO $short", key, null) }
            }
        }
    }

    /** Clears the "no S.O.S. channel" notice. */
    fun dismissNotice() {
        if (_state.value is SosState.NoChannel) _state.value = SosState.Idle
    }

    private suspend fun runAlarm(key: String) {
        val startedAt = nowMillis
        var sent = 0
        var lastPacketId: Int? = null
        while (true) {
            val (text, position) = buildText()
            val outcome =
                try {
                    sendMessageUseCase.invoke(text, key, null)
                } catch (e: CancellationException) {
                    throw e
                } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                    null
                }
            if (outcome is SendMessageOutcome.Queued) {
                sent++
                lastPacketId = outcome.packetId
            }
            val failed = outcome !is SendMessageOutcome.Queued
            _state.value =
                SosState.Active(
                    sent = sent,
                    lastSentMillis = nowMillis,
                    heardByOthers = _state.value.let { it is SosState.Active && it.heardByOthers },
                    position = position,
                    failed = failed,
                )
            // Wait for the next send, checking now and then whether another node repeated our message.
            val waitMillis = if (nowMillis - startedAt < FAST_PHASE_MILLIS) FAST_INTERVAL_MILLIS else SLOW_INTERVAL_MILLIS
            var waited = 0L
            while (waited < waitMillis) {
                delay(CHECK_MILLIS)
                waited += CHECK_MILLIS
                if (lastPacketId != null && wasHeard(key, lastPacketId)) {
                    _state.update { if (it is SosState.Active) it.copy(heardByOthers = true) else it }
                }
            }
        }
    }

    private suspend fun wasHeard(key: String, packetId: Int): Boolean = runCatching {
        packetRepository
            .getMessagesFrom(key, limit = 10) { id -> nodeRepository.getNode(id ?: NodeAddress.ID_BROADCAST) }
            .first()
            .any { it.packetId == packetId && it.status == MessageStatus.DELIVERED }
    }
        .getOrDefault(false)

    private suspend fun findSosContactKey(): String? {
        val index = radioConfigRepository.channelSetFlow.first().settings.indexOfFirst { it.name == SOS_CHANNEL_NAME }
        return if (index < 0) null else "$index${NodeAddress.ID_BROADCAST}"
    }

    private fun shortName(): String = nodeRepository.ourNodeInfo.value?.user?.short_name?.takeIf { it.isNotBlank() } ?: "?"

    private suspend fun buildText(): Pair<String, SosPosition> {
        val node = nodeRepository.ourNodeInfo.value
        val short = shortName()
        val battery = node?.batteryLevel?.takeIf { it in 1..100 }?.let { " bat $it%" } ?: ""
        val boardPos = node?.validPosition
        if (boardPos != null) {
            return "SOS $short ${coord(node.latitude)},${coord(node.longitude)}$battery" to SosPosition.BOARD
        }
        // The board has no position (no GPS, or no fix yet): ask the phone, but never hold the alarm back for long.
        val phone =
            runCatching {
                withTimeoutOrNull(PHONE_FIX_WAIT_MILLIS) {
                    phoneLocationProvider.locationUpdates().first { it.hasFix || !it.permissionGranted || !it.providerEnabled }
                }
            }
                .getOrNull()
                ?.location
        return if (phone != null) {
            "SOS $short ${coord(phone.latitude)},${coord(phone.longitude)}$battery" to SosPosition.PHONE
        } else {
            "SOS $short posizione n.d.$battery" to SosPosition.NONE
        }
    }

    private fun coord(value: Double): Double = (value * 100_000).roundToLong() / 100_000.0

    private companion object {
        const val SOS_CHANNEL_NAME = "S.O.S."
        const val COUNTDOWN_SECONDS = 5
        const val FAST_PHASE_MILLIS = 10 * 60_000L
        const val FAST_INTERVAL_MILLIS = 2 * 60_000L
        const val SLOW_INTERVAL_MILLIS = 10 * 60_000L
        const val CHECK_MILLIS = 5_000L
        const val PHONE_FIX_WAIT_MILLIS = 8_000L
    }
}
