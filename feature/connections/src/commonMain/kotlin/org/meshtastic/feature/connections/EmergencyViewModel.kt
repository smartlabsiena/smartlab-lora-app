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
package org.meshtastic.feature.connections

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.KoinViewModel
import org.meshtastic.core.model.NodeAddress
import org.meshtastic.core.model.QuickChatAction
import org.meshtastic.core.repository.QuickChatActionRepository
import org.meshtastic.core.repository.usecase.SendMessageUseCase
import org.meshtastic.core.ui.viewmodel.safeLaunch
import org.meshtastic.core.ui.viewmodel.stateInWhileSubscribed

/**
 * Smartlab LoRa Emergency home: the quick messages the user has set up (the same list as the Meshtastic quick chat
 * editor) and a one-tap manual send. Nothing is ever sent automatically.
 */
@KoinViewModel
class EmergencyViewModel(
    quickChatActionRepository: QuickChatActionRepository,
    private val sendMessageUseCase: SendMessageUseCase,
) : ViewModel() {

    val quickMessages: StateFlow<List<QuickChatAction>> =
        quickChatActionRepository.getAllActions().stateInWhileSubscribed(initialValue = emptyList())

    /** Sends [text] as a broadcast on the primary channel (channel 0), like a normal message. */
    fun send(text: String) {
        safeLaunch(tag = "emergencyQuickMessage") {
            sendMessageUseCase.invoke(text, "0${NodeAddress.ID_BROADCAST}", null)
        }
    }
}
