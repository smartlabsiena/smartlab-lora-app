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

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.meshtastic.feature.map.areas.EmergencyArea
import org.meshtastic.feature.map.areas.EmergencyAreaFocus
import org.meshtastic.feature.map.areas.EmergencyAreaGroups

private val PagePadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)

/** Kit home: the list of every sheet, list and form. */
@Composable
fun KitHomeScreen(onOpen: (String) -> Unit) {
    val context = LocalContext.current
    val store = remember { KitStore.get(context) }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(contentPadding = PagePadding, verticalArrangement = Arrangement.spacedBy(10.dp)) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Kit", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text(
                        "Quaderno delle emergenze · Comune di Siena. Si legge anche senza rete.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            item { SectionTitle("Da leggere per prima") }
            item { KitCard("Scheda ${KitIntro00.number}", KitIntro00.title, KitIntro00.summary) { onOpen(KitIntro00.id) } }
            item { KitCard("Scheda 00-B", "Aree di emergenza", "Aree di attesa, ricovero e ammassamento del Comune, con indicazioni sulla mappa") { onOpen(AREE_ID) } }
            item { SectionTitle("Cosa fare") }
            items(KitRisks) { risk ->
                KitCard("Scheda ${risk.number}", risk.title, risk.category) { onOpen(risk.id) }
            }
            item { SectionTitle("Da compilare") }
            items(KitForms) { form ->
                val filled = form.groups.sumOf { g -> g.fields.count { store.field(form.id, it.key).isNotBlank() } }
                val label = if (filled == 0) "Da compilare" else "Compilato in parte"
                KitCard("Scheda ${form.number}", form.title, label) { onOpen(form.id) }
            }
            item { SectionTitle("Liste da spuntare") }
            items(KitChecklists) { list ->
                val total = list.groups.sumOf { it.second.size }
                val done = store.checked(list.id).size
                KitCard("Scheda ${list.number}", list.title, "$done di $total pronti") { onOpen(list.id) }
            }
            item {
                Text(
                    KIT_SOURCES,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }
    }
}

/** One page of the Kit, chosen by [id]. */
@Composable
fun KitPageScreen(id: String, onBack: () -> Unit, onShowOnMap: () -> Unit = {}) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        val risk = KitRisks.firstOrNull { it.id == id }
        val list = KitChecklists.firstOrNull { it.id == id }
        val form = KitForms.firstOrNull { it.id == id }
        when {
            id == KitIntro00.id -> IntroPage(KitIntro00, onBack)
            id == AREE_ID -> AreePage(onBack, onShowOnMap)
            risk != null -> RiskPage(risk, onBack)
            list != null -> ChecklistPage(list, onBack)
            form != null -> FormPage(form, onBack)
            else -> Column(Modifier.padding(PagePadding)) {
                BackButton(onBack)
                Text("Pagina non trovata.")
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 10.dp),
    )
}

@Composable
private fun KitCard(overline: String, title: String, detail: String, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(overline.uppercase(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun BackButton(onBack: () -> Unit) {
    TextButton(onClick = onBack, contentPadding = PaddingValues(0.dp)) { Text("‹ Kit") }
}

@Composable
private fun PageHeader(overline: String, title: String, summary: String?, onBack: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        BackButton(onBack)
        Text(overline.uppercase(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        if (summary != null) {
            Text(summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun Bullets(lines: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        lines.forEach { line ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("•", style = MaterialTheme.typography.bodyLarge)
                Text(line, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
private fun Small(text: String) {
    Text(text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
private fun IntroPage(intro: KitIntro, onBack: () -> Unit) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(PagePadding),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        PageHeader("Scheda ${intro.number}", intro.title, intro.summary, onBack)
        intro.sections.forEach { section ->
            Text(section.heading, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                section.lines.forEach { Text(it, style = MaterialTheme.typography.bodyLarge) }
            }
        }
        Small(KIT_SOURCES)
    }
}

private const val AREE_ID = "s00b"

/** Scheda 00-B: the Comune's emergency areas, searchable, each opening the phone's map app. */
@Composable
private fun AreePage(onBack: () -> Unit, onShowOnMap: () -> Unit) {
    var query by remember { mutableStateOf("") }
    val needle = query.trim().lowercase()
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(PagePadding),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PageHeader(
            "Scheda 00-B",
            "Aree di emergenza",
            "Individuate l’area di attesa più vicina a casa vostra e scrivetela nella Scheda 11 — Piano famiglia.",
            onBack,
        )
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Cerca per nome o via") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        EmergencyAreaGroups.forEach { group ->
            val shown = group.areas.filter { needle.isEmpty() || "${it.name} ${it.address}".lowercase().contains(needle) }
            if (shown.isNotEmpty()) {
                Text(
                    if (group.code == "ALTRE") "${group.title} · ${shown.size}" else "${group.title} (${group.code}) · ${shown.size}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                )
                if (needle.isEmpty()) Small(group.note)
                shown.forEach { AreaRow(it, onShowOnMap) }
            }
        }
        Small("“Mappa” mostra l’area sulla mappa dell’app, anche senza rete se avete scaricato la zona offline; toccate il punto per toglierlo. “Naviga” apre un’altra app di mappe. Coordinate dal Piano di Protezione Civile del Comune di Siena; in caso di evento seguite le indicazioni ufficiali.")
    }
}

@Composable
private fun AreaRow(area: EmergencyArea, onShowOnMap: () -> Unit) {
    val context = LocalContext.current
    Card(
        onClick = {
            // The in-app map works offline: it draws this one point from coordinates embedded in the app.
            EmergencyAreaFocus.show(area)
            onShowOnMap()
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(Modifier.padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(area.name, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(area.address, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            TextButton(onClick = {
                EmergencyAreaFocus.show(area)
                onShowOnMap()
            }) { Text("Mappa") }
            TextButton(onClick = {
                val lat = area.lat
                val lon = area.lon
                val uri =
                    if (lat != null && lon != null) {
                        Uri.parse("geo:$lat,$lon?q=$lat,$lon(${Uri.encode(area.name)})")
                    } else {
                        Uri.parse("geo:0,0?q=${Uri.encode("${area.address}, Siena")}")
                    }
                runCatching { context.startActivity(Intent(Intent.ACTION_VIEW, uri)) }
            }) { Text("Naviga") }
        }
    }
}

@Composable
private fun RiskPage(risk: KitRisk, onBack: () -> Unit) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(PagePadding),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        PageHeader("Scheda ${risk.number} · ${risk.category}", risk.title, risk.summary, onBack)
        Text("A Siena", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Bullets(risk.about)
        Text("Chi contattare", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        risk.contacts.forEach { ContactRow(it) }
        PhaseCard("Prima", risk.before, MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.onPrimaryContainer)
        PhaseCard("Durante", risk.during, MaterialTheme.colorScheme.errorContainer, MaterialTheme.colorScheme.onErrorContainer)
        PhaseCard("Dopo", risk.after, MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onSecondaryContainer)
        Small(risk.source)
    }
}

@Composable
private fun PhaseCard(title: String, lines: List<String>, container: androidx.compose.ui.graphics.Color, content: androidx.compose.ui.graphics.Color) {
    Card(colors = CardDefaults.cardColors(containerColor = container, contentColor = content), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title.uppercase(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Bullets(lines)
        }
    }
}

/** A contact line such as "112 — Numero unico emergenze": a leading number opens the dialer (it does not call). */
@Composable
private fun ContactRow(text: String) {
    val context = LocalContext.current
    val number = Regex("^([0-9 ]+?)\\s+—").find(text)?.groupValues?.get(1)?.replace(" ", "")
    val modifier =
        if (number != null) {
            Modifier.fillMaxWidth().clickable { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))) }
        } else {
            Modifier.fillMaxWidth()
        }
    Card(modifier = modifier) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            if (number != null) {
                Text("Chiama", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun ChecklistPage(list: KitChecklist, onBack: () -> Unit) {
    val context = LocalContext.current
    val store = remember { KitStore.get(context) }
    val checked = remember { mutableStateListOf<String>().apply { addAll(store.checked(list.id)) } }
    var confirmReset by remember { mutableStateOf(false) }
    val total = list.groups.sumOf { it.second.size }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(PagePadding),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PageHeader("Scheda ${list.number}", list.title, list.intro, onBack)
        Text("${checked.size} di $total pronti", style = MaterialTheme.typography.titleSmall)
        LinearProgressIndicator(progress = { if (total == 0) 0f else checked.size.toFloat() / total }, modifier = Modifier.fillMaxWidth())
        list.groups.forEachIndexed { gi, (group, items) ->
            Text(group, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 6.dp))
            items.forEachIndexed { ii, item ->
                val itemId = "${gi}_$ii"
                val isChecked = itemId in checked
                Row(
                    modifier = Modifier.fillMaxWidth().clickable {
                        if (isChecked) checked.remove(itemId) else checked.add(itemId)
                        store.setChecked(list.id, checked.toSet())
                    },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(checked = isChecked, onCheckedChange = null)
                    Text(item, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
        Text(list.note, style = MaterialTheme.typography.bodyMedium)
        OutlinedButton(onClick = { confirmReset = true }) { Text("Azzera le spunte") }
        Small(list.source)
    }
    if (confirmReset) {
        AlertDialog(
            onDismissRequest = { confirmReset = false },
            title = { Text("Azzerare le spunte?") },
            text = { Text("Tutte le voci della lista torneranno da spuntare.") },
            confirmButton = {
                TextButton(onClick = {
                    checked.clear()
                    store.clearChecks(list.id)
                    confirmReset = false
                }) { Text("Azzera") }
            },
            dismissButton = { TextButton(onClick = { confirmReset = false }) { Text("Annulla") } },
        )
    }
}

@Composable
private fun FormPage(form: KitForm, onBack: () -> Unit) {
    val context = LocalContext.current
    val store = remember { KitStore.get(context) }
    val values = remember {
        mutableStateMapOf<String, String>().apply {
            form.groups.forEach { g -> g.fields.forEach { put(it.key, store.field(form.id, it.key)) } }
        }
    }
    var confirmClear by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(PagePadding),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        PageHeader("Scheda ${form.number}", form.title, form.intro, onBack)
        form.groups.forEach { group ->
            Text(group.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp))
            group.hint?.let { Small(it) }
            group.fields.forEach { field ->
                OutlinedTextField(
                    value = values[field.key].orEmpty(),
                    onValueChange = {
                        values[field.key] = it
                        store.setField(form.id, field.key, it)
                    },
                    label = { Text(field.label) },
                    singleLine = !field.multiline,
                    keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                        when (field.keyboard) {
                            KitKeyboard.Phone -> KeyboardType.Phone
                            KitKeyboard.Number -> KeyboardType.Number
                            KitKeyboard.Text -> KeyboardType.Text
                        },
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(top = 8.dp)) {
            Button(onClick = { shareForm(context, form, values) }) { Text("Condividi") }
            OutlinedButton(onClick = { confirmClear = true }) { Text("Cancella i dati") }
        }
        Small("I dati restano solo su questo telefono. “Condividi” li invia solo a chi scegli tu.")
        Small(form.source)
    }
    if (confirmClear) {
        AlertDialog(
            onDismissRequest = { confirmClear = false },
            title = { Text("Cancellare i dati?") },
            text = { Text("Tutti i campi di questa scheda verranno svuotati. Non si può annullare.") },
            confirmButton = {
                TextButton(onClick = {
                    values.keys.toList().forEach { values[it] = "" }
                    store.clearForm(form.id)
                    confirmClear = false
                }) { Text("Cancella") }
            },
            dismissButton = { TextButton(onClick = { confirmClear = false }) { Text("Annulla") } },
        )
    }
}

private fun shareForm(context: android.content.Context, form: KitForm, values: Map<String, String>) {
    val text = buildString {
        appendLine(form.title)
        form.groups.forEach { group ->
            val lines = group.fields.mapNotNull { f -> values[f.key]?.takeIf { it.isNotBlank() }?.let { "${f.label}: $it" } }
            if (lines.isNotEmpty()) {
                appendLine()
                appendLine(group.title)
                lines.forEach { appendLine(it) }
            }
        }
    }
    val intent =
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, form.title)
            putExtra(Intent.EXTRA_TEXT, text)
        }
    context.startActivity(Intent.createChooser(intent, null))
}
