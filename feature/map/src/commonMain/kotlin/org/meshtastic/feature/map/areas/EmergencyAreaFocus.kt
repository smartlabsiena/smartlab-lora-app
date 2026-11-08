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
package org.meshtastic.feature.map.areas

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * The emergency area the user asked to see on the map from the Kit, if any. The map draws only this one point, so the
 * emergency areas never mix with the mesh nodes unless the user picks one. Cleared by tapping the point.
 */
object EmergencyAreaFocus {
    private val current = MutableStateFlow<EmergencyArea?>(null)
    val area: StateFlow<EmergencyArea?> = current.asStateFlow()

    fun show(area: EmergencyArea) {
        current.value = area
    }

    fun clear() {
        current.value = null
    }
}
