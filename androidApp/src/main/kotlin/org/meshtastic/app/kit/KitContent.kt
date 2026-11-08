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

/** One risk sheet (schede 01-10 of the Quaderno): what to do before, during and after. */
data class KitRisk(
    val id: String,
    val number: String,
    val title: String,
    val category: String,
    val summary: String,
    val about: List<String>,
    val contacts: List<String>,
    val before: List<String>,
    val during: List<String>,
    val after: List<String>,
    val source: String,
)

data class KitSection(val heading: String, val lines: List<String>)

/** Plain reading page made of titled sections (scheda 00). */
data class KitIntro(
    val id: String,
    val number: String,
    val title: String,
    val summary: String,
    val sections: List<KitSection>,
)

/** A checklist with tick boxes, kept on the phone (schede 12 and 14). */
data class KitChecklist(
    val id: String,
    val number: String,
    val title: String,
    val intro: String,
    val groups: List<Pair<String, List<String>>>,
    val note: String,
    val source: String,
)

enum class KitKeyboard { Text, Phone, Number }

data class KitField(
    val key: String,
    val label: String,
    val keyboard: KitKeyboard = KitKeyboard.Text,
    val multiline: Boolean = false,
)

data class KitFormGroup(val title: String, val hint: String? = null, val fields: List<KitField>)

/** A form to fill in on the phone (schede 11 and 13). The data never leaves the device unless shared by the user. */
data class KitForm(
    val id: String,
    val number: String,
    val title: String,
    val intro: String,
    val groups: List<KitFormGroup>,
    val source: String,
)

const val KIT_SOURCES =
    "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024), " +
        "Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile; " +
        "modelli della Croce Rossa Italiana. Indirizzi e indicazioni sono indicativi: in caso di evento reale " +
        "seguite sempre le indicazioni ufficiali del Comune e dei soccorritori."

val KitIntro00 =
    KitIntro(
        id = "s00",
        number = "00",
        title = "Siena: cosa sapere prima",
        summary = "Come funziona il sistema di allerta e chi fa cosa, in sintesi, prima di aprire le schede per singolo rischio.",
        sections =
        listOf(
            KitSection(
                "I codici colore dell’allerta",
                listOf(
                    "La Regione Toscana comunica il livello di rischio previsto con quattro codici colore, a cui " +
                        "corrisponde una fase operativa del Comune:",
                    "VERDE — criticità assente — Normalità",
                    "GIALLO — criticità ordinaria — Vigilanza",
                    "ARANCIONE — criticità moderata — Attenzione, eventuale pre-allarme",
                    "ROSSO — criticità elevata — Pre-allarme, allarme, C.O.C. attivo",
                ),
            ),
            KitSection(
                "Come arrivano gli avvisi",
                listOf(
                    "Sito e App del Centro Funzionale Regionale (CFR) Toscana.",
                    "Sito internet del Comune di Siena, App comunale e Alert System.",
                    "In evento in atto: sirene, avvisi acustici, altoparlanti e, se serve, informazione porta a porta.",
                ),
            ),
            KitSection(
                "Chi fa cosa",
                listOf(
                    "Il Sindaco attiva il Centro Operativo Comunale (C.O.C.) e, nei casi più gravi, l’Unità di Crisi.",
                    "Sede C.O.C. principale in Comune; sede secondaria, se la prima è inagibile: scuola " +
                        "“P.A. Mattioli”, via D. di Boninsegna 76.",
                    "Il presidio territoriale per il rischio idraulico è al ponte sul Fiume Arbia, Taverne d’Arbia.",
                ),
            ),
            KitSection(
                "Punti più esposti sul territorio",
                listOf(
                    "Taverne d’Arbia — rischio idraulico classe 2, lungo via S. Isidoro e l’inizio di via Principale.",
                    "Isola d’Arbia — rischio idraulico classe 2 e 3 (la zona più esposta): residenze in via della " +
                        "Biccherna e via Cassia Sud; in caso di piena vengono interdette Strada dei Balzoni, le " +
                        "strade sterrate verso il torrente Arbia, via di Salteano/via Cassia Sud oltre il ponte e via " +
                        "della Biccherna.",
                    "A Isola d’Arbia sorge anche l’impianto di depurazione comunale (gestito da Acquedotto del " +
                        "Fiora): infrastruttura strategica da monitorare in caso di piena.",
                    "Per entrambe le zone, l’Area di Ricovero coperta di riferimento è la palestra della scuola " +
                        "“P.A. Mattioli”, via Duccio di Boninsegna — la stessa sede secondaria del C.O.C.",
                    "Rischio idrogeologico (frane): diffuso su tutto il territorio collinare e argilloso (crete " +
                        "senesi), senza punti puntuali codificati nel Piano.",
                    "Zona sismica: 3 su tutto il Comune (scuotimenti attesi modesti).",
                    "Incendi boschivi: aree di interfaccia, dove case e giardini confinano con bosco o campagna " +
                        "incolta.",
                ),
            ),
            KitSection(
                "Aree di emergenza: dove andare",
                listOf(
                    "Area di attesa (AA) — primo punto di raccolta a piedi, vicino a casa: 35 in tutto il Comune.",
                    "Area di ricovero (AR) — dove si viene eventualmente accolti, anche per la notte: 12 in tutto " +
                        "il Comune.",
                    "Area di ammassamento soccorritori (AS) — riservata ai mezzi di soccorso, non è un punto di " +
                        "raccolta per i cittadini: 6 in tutto il Comune.",
                    "Individuate l’area di attesa più vicina a casa vostra e scrivetela nel Piano famiglia.",
                ),
            ),
        ),
    )

val KitChecklists: List<KitChecklist> =
    listOf(
        KitChecklist(
            id = "c12",
            number = "12",
            title = "Kit di emergenza della famiglia",
            intro =
            "Zaino da tenere pronto vicino alla porta. Il kit serve ad affrontare le prime 72 ore, il tempo " +
                "tipico perché i soccorsi organizzino un’assistenza strutturata. Uno zaino a testa, o uno per " +
                "famiglia se lo spazio è poco: l’importante è sapere dov’è e controllarlo due volte l’anno.",
            groups =
            listOf(
                "Documenti (in busta impermeabile)" to
                    listOf(
                        "Carte d’identità, tessere sanitarie, codici fiscali (originali o copie)",
                        "Piano di emergenza familiare compilato (Scheda 11)",
                        "Copie di contratti, polizze assicurative, atti di proprietà",
                        "Contanti in tagli piccoli e medi",
                        "Copia delle chiavi di casa e dell’auto",
                    ),
                "Acqua e cibo" to
                    listOf(
                        "Acqua: almeno 1,5 litri a persona al giorno, per 3 giorni",
                        "Cibo non deperibile e pronto al consumo (barrette, scatolame, biscotti)",
                        "Apriscatole manuale e posate monouso",
                        "Cibo per eventuali animali domestici",
                    ),
                "Salute e pronto soccorso" to
                    listOf(
                        "Farmaci abituali per ogni componente della famiglia, con posologia scritta",
                        "Kit di primo soccorso: garze, cerotti, disinfettante, guanti",
                        "Termometro",
                        "Mascherine e gel igienizzante",
                        "Occhiali di scorta, se necessari",
                    ),
                "Strumenti e illuminazione" to
                    listOf(
                        "Torcia elettrica e batterie di scorta (o torcia a dinamo)",
                        "Radio a batterie o dinamo, per restare informati anche senza rete",
                        "Power bank carico e cavi di ricarica",
                        "Fischietto per segnalare la propria posizione",
                        "Coltellino multiuso e nastro adesivo resistente",
                    ),
                "Abbigliamento e riparo" to
                    listOf(
                        "Cambio d’abiti e scarpe robuste per ogni persona",
                        "Coperta termica o sacco a pelo leggero",
                        "Guanti da lavoro e mascherine antipolvere",
                        "Impermeabile o poncho",
                    ),
                "Da aggiungere all’ultimo momento" to
                    listOf(
                        "Cellulari carichi e caricabatterie",
                        "Chiavi di casa e dell’auto",
                        "Occhiali, protesi, ausili medici in uso",
                        "Kit del neonato, se presente in famiglia (Schede 13–14)",
                    ),
            ),
            note =
            "Promemoria: controllate scadenze di farmaci e cibo, batterie e documenti ogni 6 mesi, insieme alla " +
                "revisione del Piano famiglia (Scheda 11).",
            source =
            "Checklist ispirata alle indicazioni nazionali di Protezione Civile (iononrischio.it) e Croce Rossa " +
                "Italiana.",
        ),
        KitChecklist(
            id = "c14",
            number = "14",
            title = "Kit di evacuazione del neonato (0–3 anni)",
            intro =
            "Borsa da controllare periodicamente. Le quantità vanno adattate a età, alimentazione, stagione e " +
                "indicazioni del pediatra. Prima regola: in caso di febbre, difficoltà respiratoria, sonnolenza " +
                "insolita, disidratazione, trauma o dubbio sanitario, contattate il pediatra o il 112.",
            groups =
            listOf(
                "Alimentazione" to
                    listOf(
                        "Latte artificiale, solo se usato abitualmente",
                        "Biberon puliti o monouso",
                        "Acqua idonea alla preparazione",
                        "Alimenti adeguati all’età, se già svezzato",
                        "Cucchiaino, bavaglini, tazza o borraccia",
                    ),
                "Igiene" to
                    listOf(
                        "Pannolini in quantità adeguata",
                        "Salviette o materiale per il cambio",
                        "Crema barriera, se usata abitualmente",
                        "Detergente delicato e gel igienizzante",
                        "Asciugamani o teli puliti",
                    ),
                "Vestiario" to
                    listOf(
                        "Body e cambi completi della taglia attuale",
                        "Tutine o vestiti comodi, calzini",
                        "Cappellino (sole o freddo, secondo stagione)",
                        "Scarpe, se il bambino cammina",
                    ),
                "Salute" to
                    listOf(
                        "Termometro digitale",
                        "Farmaci pediatrici solo se indicati dal pediatra",
                        "Soluzione fisiologica e aspiratore nasale, se usati",
                        "Cerotti e garze sterili",
                        "Libretto pediatrico o vaccinale in copia",
                        "Tessera sanitaria e documenti disponibili",
                    ),
                "Sonno e comfort" to
                    listOf(
                        "Copertina o sacco nanna adatto alla stagione",
                        "Peluche, copertina o oggetto familiare",
                        "Ciuccio di ricambio, se usato",
                        "Tappetino o telo per il cambio",
                        "Marsupio, fascia o passeggino richiudibile",
                        "Piccolo gioco sicuro e lavabile",
                    ),
                "Documenti e contatti" to
                    listOf(
                        "Carta d’identità di emergenza compilata (Scheda 13)",
                        "Tessera sanitaria e libretto pediatrico",
                        "Foto recente del bambino",
                        "Contatti di pediatra e familiari",
                    ),
            ),
            note =
            "Promemoria: non preparate latte artificiale in anticipo se non indicato dal personale sanitario · " +
                "continuate l’allattamento al seno quando possibile · proteggete il bambino da freddo, caldo e " +
                "sbalzi di temperatura · aggiornate periodicamente taglie, farmaci e contatti.",
            source =
            "Checklist ispirata a buone pratiche di Protezione Civile su infanzia in emergenza. Non sostituisce " +
                "indicazioni sanitarie.",
        ),
    )

private fun memberGroup(n: Int) =
    KitFormGroup(
        title = "Componente $n",
        fields =
        listOf(
            KitField("m${n}_name", "Nome"),
            KitField("m${n}_phone", "Cellulare", KitKeyboard.Phone),
            KitField("m${n}_blood", "Gruppo sanguigno"),
            KitField("m${n}_allergy", "Allergie / farmaci", multiline = true),
        ),
    )

val KitForms: List<KitForm> =
    listOf(
        KitForm(
            id = "f11",
            number = "11",
            title = "Piano di emergenza familiare",
            intro =
            "Da compilare in famiglia. Compilatelo insieme e tenetene una copia nello zaino di emergenza. I dati " +
                "restano solo su questo telefono. Numeri utili: 112 emergenze · 115 Vigili del Fuoco · 118 " +
                "emergenza sanitaria.",
            groups =
            listOf(
                KitFormGroup(
                    "Famiglia",
                    fields =
                    listOf(
                        KitField("family", "Famiglia / indirizzo"),
                        KitField("date", "Data compilazione"),
                    ),
                ),
                memberGroup(1),
                memberGroup(2),
                memberGroup(3),
                memberGroup(4),
                memberGroup(5),
                memberGroup(6),
                KitFormGroup(
                    "Punto di ritrovo vicino a casa",
                    "Se dovete uscire in fretta e non potete restare in casa.",
                    listOf(KitField("meeting", "Punto di ritrovo", multiline = true)),
                ),
                KitFormGroup(
                    "Area di attesa comunale",
                    "La più vicina a casa vostra.",
                    listOf(KitField("area", "Area di attesa", multiline = true)),
                ),
                KitFormGroup(
                    "Referente esterno (fuori città)",
                    "Un parente o amico lontano da chiamare tutti, se separati.",
                    listOf(KitField("ref_name", "Nome"), KitField("ref_phone", "Cellulare", KitKeyboard.Phone)),
                ),
                KitFormGroup(
                    "Medico di famiglia / pediatra",
                    fields =
                    listOf(KitField("doc_name", "Nome"), KitField("doc_phone", "Telefono", KitKeyboard.Phone)),
                ),
                KitFormGroup(
                    "Rubinetti gas / luce / acqua",
                    "Dove si trovano: tutti in famiglia devono saperlo.",
                    listOf(KitField("valves", "Dove si trovano", multiline = true)),
                ),
                KitFormGroup(
                    "Animali domestici",
                    fields = listOf(KitField("pets", "Animali e cosa serve loro", multiline = true)),
                ),
                KitFormGroup(
                    "Chi si occupa di cosa, in emergenza",
                    fields =
                    listOf(
                        KitField("task_kit", "Prendere il kit di emergenza (zaino pronto, Scheda 12)"),
                        KitField("task_info", "Restare informati (radio, sito del Comune, App)"),
                        KitField("task_docs", "Documenti e informazioni mediche della famiglia"),
                        KitField("task_pets", "Animali domestici"),
                        KitField("task_update", "Aggiornare questo piano ogni 6 mesi"),
                    ),
                ),
            ),
            source = "Adattato dal modello “Piano di Emergenza Familiare” della Croce Rossa Italiana.",
        ),
        KitForm(
            id = "f13",
            number = "13",
            title = "Carta d’identità di emergenza del bambino",
            intro =
            "Da tenere nel marsupio o nella borsa fasciatoio. In caso di evacuazione, è la prima cosa da " +
                "consegnare ai soccorritori: puoi condividerla dal telefono. Tienine una copia anche nel " +
                "portafoglio. I dati restano solo su questo telefono.",
            groups =
            listOf(
                KitFormGroup(
                    "Anagrafica",
                    fields =
                    listOf(
                        KitField("c_name", "Nome e cognome"),
                        KitField("c_birth", "Data di nascita"),
                        KitField("c_cf", "Codice fiscale"),
                        KitField("c_weight", "Peso (kg)", KitKeyboard.Number),
                        KitField("c_length", "Lunghezza (cm)", KitKeyboard.Number),
                        KitField("c_blood", "Gruppo sanguigno"),
                    ),
                ),
                KitFormGroup(
                    "Genitori e tutori",
                    fields =
                    listOf(
                        KitField("p_mother", "Madre — nome"),
                        KitField("p_mother_phone", "Madre — cellulare", KitKeyboard.Phone),
                        KitField("p_father", "Padre — nome"),
                        KitField("p_father_phone", "Padre — cellulare", KitKeyboard.Phone),
                        KitField("p_other", "Altro tutore — nome"),
                        KitField("p_other_phone", "Altro tutore — cellulare", KitKeyboard.Phone),
                    ),
                ),
                KitFormGroup(
                    "Alimentazione",
                    fields =
                    listOf(
                        KitField("f_diet", "Come si alimenta", multiline = true),
                        KitField("f_allergy", "Allergie / intolleranze alimentari", multiline = true),
                    ),
                ),
                KitFormGroup(
                    "Salute",
                    fields =
                    listOf(
                        KitField("h_ped", "Pediatra — nome"),
                        KitField("h_ped_phone", "Pediatra — cellulare", KitKeyboard.Phone),
                        KitField("h_conditions", "Patologie note / farmaci in corso", multiline = true),
                        KitField("h_drug_allergy", "Allergie a farmaci", multiline = true),
                        KitField("h_notes", "Note utili ai soccorritori (paure, oggetto consolatorio)", multiline = true),
                    ),
                ),
            ),
            source =
            "Ispirato a modelli diffusi dai gruppi di volontariato di Protezione Civile e dalla Società Italiana " +
                "di Neonatologia (SIN), “Newborn health in humanitarian settings” (WHO).",
        ),
    )
