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

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * What the user ticks and writes in the Kit (checklists and forms), kept in a private file of the app.
 *
 * The file lives in `noBackupFilesDir`: medical and family data must not be copied to a cloud backup by the system.
 * Nothing here is sent anywhere; the user shares a form only on purpose.
 */
class KitStore private constructor(context: Context) {
    private val file = File(context.noBackupFilesDir, "kit.json")
    private var root: JSONObject = runCatching { JSONObject(file.readText()) }.getOrDefault(JSONObject())

    fun checked(listId: String): Set<String> {
        val array = root.optJSONObject("checks")?.optJSONArray(listId) ?: return emptySet()
        return (0 until array.length()).map { array.getString(it) }.toSet()
    }

    fun setChecked(listId: String, ids: Set<String>) {
        val checks = root.optJSONObject("checks") ?: JSONObject().also { root.put("checks", it) }
        checks.put(listId, JSONArray(ids.toList()))
        save()
    }

    fun field(formId: String, key: String): String = root.optJSONObject("forms")?.optJSONObject(formId)?.optString(key, "") ?: ""

    fun setField(formId: String, key: String, value: String) {
        val forms = root.optJSONObject("forms") ?: JSONObject().also { root.put("forms", it) }
        val form = forms.optJSONObject(formId) ?: JSONObject().also { forms.put(formId, it) }
        form.put(key, value)
        save()
    }

    fun clearForm(formId: String) {
        root.optJSONObject("forms")?.remove(formId)
        save()
    }

    fun clearChecks(listId: String) {
        root.optJSONObject("checks")?.remove(listId)
        save()
    }

    private fun save() {
        runCatching { file.writeText(root.toString()) }
    }

    companion object {
        @Volatile private var instance: KitStore? = null

        fun get(context: Context): KitStore = instance ?: synchronized(this) {
            instance ?: KitStore(context.applicationContext).also { instance = it }
        }
    }
}
