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
package org.meshtastic.app.kit

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.meshtastic.core.navigation.KitRoute

/** Navigation entries of the Kit tab: the list and one page per sheet, list or form. */
fun EntryProviderScope<NavKey>.kitEntries(backStack: NavBackStack<NavKey>, onShowOnMap: () -> Unit) {
    entry<KitRoute.Kit> { KitHomeScreen(onOpen = { backStack.add(KitRoute.Page(it)) }) }
    entry<KitRoute.Page> { key ->
        KitPageScreen(id = key.id, onBack = { backStack.removeLastOrNull() }, onShowOnMap = onShowOnMap)
    }
}
