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
@file:Suppress("MagicNumber", "LongMethod", "TooManyFunctions")

package org.meshtastic.feature.connections.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.meshtastic.core.model.ConnectionState
import org.meshtastic.core.model.DeviceType
import org.meshtastic.core.resources.Res
import org.meshtastic.core.resources.emergency_battery_powered
import org.meshtastic.core.resources.emergency_cancel
import org.meshtastic.core.resources.emergency_disclaimer
import org.meshtastic.core.resources.emergency_link_bluetooth
import org.meshtastic.core.resources.emergency_link_lora
import org.meshtastic.core.resources.emergency_link_wifi
import org.meshtastic.core.resources.emergency_manage_links
import org.meshtastic.core.resources.emergency_quick_edit
import org.meshtastic.core.resources.emergency_quick_empty
import org.meshtastic.core.resources.emergency_quick_send
import org.meshtastic.core.resources.emergency_quick_send_text
import org.meshtastic.core.resources.emergency_quick_send_title
import org.meshtastic.core.resources.emergency_quick_title
import org.meshtastic.core.resources.emergency_radio_battery
import org.meshtastic.core.resources.emergency_radio_board
import org.meshtastic.core.resources.emergency_radio_preset
import org.meshtastic.core.resources.emergency_radio_region
import org.meshtastic.core.resources.emergency_radio_title
import org.meshtastic.core.resources.emergency_region_unset
import org.meshtastic.core.resources.emergency_settings
import org.meshtastic.core.resources.emergency_sos_active
import org.meshtastic.core.resources.emergency_sos_cancel
import org.meshtastic.core.resources.emergency_sos_countdown_text
import org.meshtastic.core.resources.emergency_sos_countdown_title
import org.meshtastic.core.resources.emergency_sos_failed
import org.meshtastic.core.resources.emergency_sos_heard
import org.meshtastic.core.resources.emergency_sos_need_board
import org.meshtastic.core.resources.emergency_sos_no_channel
import org.meshtastic.core.resources.emergency_sos_no_confirm
import org.meshtastic.core.resources.emergency_sos_no_position
import org.meshtastic.core.resources.emergency_sos_phone_position
import org.meshtastic.core.resources.emergency_sos_sent_count
import org.meshtastic.core.resources.emergency_sos_stop
import org.meshtastic.core.resources.emergency_sos_hold
import org.meshtastic.core.resources.emergency_sos_label
import org.meshtastic.core.resources.emergency_state_connected
import org.meshtastic.core.resources.emergency_state_connecting
import org.meshtastic.core.resources.emergency_state_disconnected
import org.meshtastic.core.resources.emergency_state_sleeping
import org.meshtastic.core.resources.emergency_title
import org.meshtastic.core.resources.emergency_unknown
import org.meshtastic.core.resources.ic_settings
import org.meshtastic.core.model.QuickChatAction
import org.meshtastic.core.ui.viewmodel.ConnectionsViewModel
import org.meshtastic.feature.connections.EmergencyViewModel
import org.meshtastic.feature.connections.ScannerViewModel
import org.meshtastic.feature.connections.SosPosition
import org.meshtastic.feature.connections.SosState
import org.meshtastic.feature.connections.SosViewModel
import org.meshtastic.proto.Config

/** Strong red for the SOS button. Fixed (not themed) so the button reads the same in light and dark. */
private val SosRed = Color(0xFFD9372D)
private val SosOnRed = Color.White
private const val SOS_HOLD_MILLIS = 3000

/**
 * Smartlab LoRa Emergency home: link status, SOS button, check-in and radio summary, laid out like the approved
 * mockup. The SOS button needs a board connected: held for 3 s it starts a 5 s countdown (cancellable), then sends a
 * text message on the S.O.S. channel and repeats it until cancelled (see [SosViewModel]).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EmergencyScreen(
    onOpenDevices: () -> Unit,
    onOpenSettings: () -> Unit,
    onEditQuickMessages: () -> Unit,
    modifier: Modifier = Modifier,
    connectionsViewModel: ConnectionsViewModel = koinViewModel(),
    scanModel: ScannerViewModel = koinViewModel(),
    emergencyViewModel: EmergencyViewModel = koinViewModel(),
    sosViewModel: SosViewModel = koinViewModel(),
) {
    val connectionState by connectionsViewModel.connectionState.collectAsStateWithLifecycle()
    val ourNode by connectionsViewModel.ourNodeForDisplay.collectAsStateWithLifecycle()
    val localConfig by connectionsViewModel.localConfig.collectAsStateWithLifecycle()
    val transport by scanModel.activeTransport.collectAsStateWithLifecycle()
    val quickMessages by emergencyViewModel.quickMessages.collectAsStateWithLifecycle()

    val sosState by sosViewModel.state.collectAsStateWithLifecycle()
    val haptic = LocalHapticFeedback.current
    var pendingQuick by remember { mutableStateOf<QuickChatAction?>(null) }

    val connected = connectionState is ConnectionState.Connected
    val statusText =
        when {
            connected -> stringResource(Res.string.emergency_state_connected)
            connectionState == ConnectionState.Connecting -> stringResource(Res.string.emergency_state_connecting)
            connectionState == ConnectionState.DeviceSleep -> stringResource(Res.string.emergency_state_sleeping)
            else -> stringResource(Res.string.emergency_state_disconnected)
        }
    val boardName = ourNode?.user?.long_name?.takeIf { it.isNotBlank() }
    val region = localConfig.lora?.region
    val preset = localConfig.lora?.modem_preset
    val regionUnset = connected && region == Config.LoRaConfig.RegionCode.UNSET
    val unknown = stringResource(Res.string.emergency_unknown)
    val poweredText = stringResource(Res.string.emergency_battery_powered)
    val batteryText =
        ourNode?.batteryLevel?.let { level ->
            when {
                level in 1..100 -> "$level %"
                level > 100 -> poweredText
                else -> null
            }
        } ?: unknown

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        // Header: title, status chip, settings
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(Res.string.emergency_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = onOpenSettings) {
                Icon(
                    painter = painterResource(Res.drawable.ic_settings),
                    contentDescription = stringResource(Res.string.emergency_settings),
                )
            }
        }
        StatusChip(
            text = if (connected && boardName != null) "$boardName · $statusText" else statusText,
            active = connected,
            onClick = onOpenDevices,
        )

        // Links
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            StatusChip(text = stringResource(Res.string.emergency_link_lora), active = connected)
            StatusChip(
                text = stringResource(Res.string.emergency_link_bluetooth),
                active = connected && transport == DeviceType.BLE,
            )
            StatusChip(
                text = stringResource(Res.string.emergency_link_wifi),
                active = connected && transport == DeviceType.TCP,
            )
        }

        if (regionUnset) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
            ) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(stringResource(Res.string.emergency_region_unset), style = MaterialTheme.typography.bodyMedium)
                    OutlinedButton(onClick = onOpenSettings) { Text(stringResource(Res.string.emergency_settings)) }
                }
            }
        }

        // SOS
        Box(Modifier.fillMaxWidth().padding(vertical = 6.dp), contentAlignment = Alignment.Center) {
            SosButton(
                onTriggered = {
                    if (connected && sosState is SosState.Idle) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        sosViewModel.start()
                    }
                },
            )
        }
        if (!connected && sosState is SosState.Idle) {
            Text(
                text = stringResource(Res.string.emergency_sos_need_board),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        (sosState as? SosState.Active)?.let { active -> SosActiveCard(active, onStop = { sosViewModel.cancel() }) }

        // Quick messages: manual, one tap plus a confirmation. Set up from the app (same list as the quick chat editor).
        InfoCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(Res.string.emergency_quick_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                )
                TextButton(onClick = onEditQuickMessages) { Text(stringResource(Res.string.emergency_quick_edit)) }
            }
            if (quickMessages.isEmpty()) {
                Text(
                    text = stringResource(Res.string.emergency_quick_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    quickMessages.forEach { action ->
                        OutlinedButton(onClick = { pendingQuick = action }, enabled = connected) {
                            Text(action.name.ifBlank { action.message })
                        }
                    }
                }
            }
        }

        // Radio summary (real data)
        InfoCard {
            Text(stringResource(Res.string.emergency_radio_title), style = MaterialTheme.typography.titleLarge)
            ValueRow(stringResource(Res.string.emergency_radio_board), boardName ?: unknown)
            ValueRow(
                stringResource(Res.string.emergency_radio_region),
                region?.takeIf { it != Config.LoRaConfig.RegionCode.UNSET }?.name ?: unknown,
            )
            ValueRow(stringResource(Res.string.emergency_radio_preset), preset?.name?.prettyEnum() ?: unknown)
            ValueRow(stringResource(Res.string.emergency_radio_battery), batteryText)
            OutlinedButton(onClick = onOpenDevices, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(Res.string.emergency_manage_links))
            }
        }

        Text(
            text = stringResource(Res.string.emergency_disclaimer),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 8.dp),
        )
    }

    pendingQuick?.let { action ->
        AlertDialog(
            onDismissRequest = { pendingQuick = null },
            title = { Text(stringResource(Res.string.emergency_quick_send_title)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = action.message, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = stringResource(Res.string.emergency_quick_send_text),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        emergencyViewModel.send(action.message)
                        pendingQuick = null
                    },
                ) {
                    Text(stringResource(Res.string.emergency_quick_send))
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingQuick = null }) { Text(stringResource(Res.string.emergency_cancel)) }
            },
        )
    }

    (sosState as? SosState.Countdown)?.let { countdown ->
        LaunchedEffect(countdown.secondsLeft) { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }
        AlertDialog(
            onDismissRequest = {},
            title = { Text(stringResource(Res.string.emergency_sos_countdown_title, countdown.secondsLeft)) },
            text = { Text(stringResource(Res.string.emergency_sos_countdown_text)) },
            confirmButton = {
                Button(onClick = { sosViewModel.cancel() }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(Res.string.emergency_sos_cancel), fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            },
        )
    }

    if (sosState is SosState.NoChannel) {
        AlertDialog(
            onDismissRequest = { sosViewModel.dismissNotice() },
            text = { Text(stringResource(Res.string.emergency_sos_no_channel)) },
            confirmButton = { TextButton(onClick = { sosViewModel.dismissNotice() }) { Text("OK") } },
        )
    }
}

/** Card shown while the SOS is on: what has been done and what is known, with the big stop button. */
@Composable
private fun SosActiveCard(active: SosState.Active, onStop: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = SosRed.copy(alpha = 0.18f),
        border = BorderStroke(2.dp, SosRed),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(Res.string.emergency_sos_active),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = SosRed,
            )
            Text(
                text = stringResource(Res.string.emergency_sos_sent_count, active.sent),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text =
                if (active.heardByOthers) {
                    stringResource(Res.string.emergency_sos_heard)
                } else {
                    stringResource(Res.string.emergency_sos_no_confirm)
                },
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
            )
            when (active.position) {
                SosPosition.NONE ->
                    Text(stringResource(Res.string.emergency_sos_no_position), style = MaterialTheme.typography.bodySmall)
                SosPosition.PHONE ->
                    Text(stringResource(Res.string.emergency_sos_phone_position), style = MaterialTheme.typography.bodySmall)
                SosPosition.BOARD -> Unit
            }
            if (active.failed) {
                Text(stringResource(Res.string.emergency_sos_failed), style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = onStop, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(Res.string.emergency_sos_stop))
            }
        }
    }
}

@Composable
private fun StatusChip(text: String, active: Boolean, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    val dot = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val shape = RoundedCornerShape(percent = 50)
    val content: @Composable () -> Unit = {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(Modifier.size(8.dp).background(dot, CircleShape))
            Text(text = text, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
        }
    }
    if (onClick != null) {
        Surface(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            color = MaterialTheme.colorScheme.surfaceContainer,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            content = content,
        )
    } else {
        Surface(
            modifier = modifier,
            shape = shape,
            color = MaterialTheme.colorScheme.surfaceContainer,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            content = content,
        )
    }
}

@Composable
private fun InfoCard(content: @Composable () -> Unit) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) { content() }
    }
}

@Composable
private fun ValueRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f),
        )
        Spacer(Modifier.width(12.dp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}

/** Round SOS button: it must be held for [SOS_HOLD_MILLIS] ms, with a ring that fills while held. */
@Composable
private fun SosButton(onTriggered: () -> Unit) {
    val progress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val holdLabel = stringResource(Res.string.emergency_sos_hold)
    Box(
        modifier =
        Modifier.size(184.dp)
            .semantics(mergeDescendants = true) {
                role = Role.Button
                onLongClick(label = holdLabel) {
                    onTriggered()
                    true
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        val job =
                            scope.launch {
                                progress.animateTo(1f, tween(SOS_HOLD_MILLIS, easing = LinearEasing))
                                onTriggered()
                                progress.snapTo(0f)
                            }
                        tryAwaitRelease()
                        job.cancel()
                        scope.launch { progress.snapTo(0f) }
                    },
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2f
            drawCircle(SosRed.copy(alpha = 0.10f), radius = radius)
            drawCircle(SosRed.copy(alpha = 0.22f), radius = radius - 9.dp.toPx())
            drawCircle(SosRed, radius = radius - 18.dp.toPx())
            if (progress.value > 0f) {
                val stroke = 6.dp.toPx()
                val inset = 10.dp.toPx() + stroke / 2f
                drawArc(
                    color = SosOnRed,
                    startAngle = -90f,
                    sweepAngle = 360f * progress.value,
                    useCenter = false,
                    topLeft = Offset(inset, inset),
                    size = Size(size.width - inset * 2f, size.height - inset * 2f),
                    style = Stroke(width = stroke, cap = StrokeCap.Round),
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(Res.string.emergency_sos_label),
                color = SosOnRed,
                fontSize = 46.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(text = holdLabel, color = SosOnRed, style = MaterialTheme.typography.labelMedium)
        }
    }
}

/** "LONG_FAST" -> "Long Fast". */
private fun String.prettyEnum(): String =
    lowercase().split('_').joinToString(" ") { part -> part.replaceFirstChar { it.uppercase() } }
