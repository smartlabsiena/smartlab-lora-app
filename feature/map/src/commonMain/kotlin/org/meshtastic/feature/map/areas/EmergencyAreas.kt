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

/**
 * An emergency area of the Comune di Siena (scheda 00-B of the Quaderno). [lat] and [lon] are the GPS coordinates
 * printed in Allegato 03 "Aree di emergenza" of the Piano di Protezione Civile del Comune di Siena.
 */
data class EmergencyArea(val name: String, val address: String, val lat: Double? = null, val lon: Double? = null)

data class EmergencyAreaGroup(val code: String, val title: String, val note: String, val areas: List<EmergencyArea>)

val EmergencyAreaGroups =
    listOf(
        EmergencyAreaGroup(
            "AA",
            "Aree di attesa",
            "Primo punto di raccolta, a piedi, vicino a casa: 35 in tutto il Comune. Scegliete la più vicina e scrivetela nella Scheda 11.",
            listOf(
                EmergencyArea("Taverne", "Strada Provinciale 438", 43.293486, 11.395828),
                EmergencyArea("Achille Grandi", "via Achille Grandi 6", 43.348089, 11.325894),
                EmergencyArea("Aldo Moro", "via Aldo Moro", 43.344394, 11.321217),
                EmergencyArea("Bernardo Tolomei", "via B. Tolomei 3", 43.337014, 11.301719),
                EmergencyArea("Costalpino", "via Dario Neri, ingresso", 43.2909833, 11.3007416),
                EmergencyArea("Federigo Tozzi", "strada Petriccio e Belriguardo", 43.3313944, 11.3005111),
                EmergencyArea("Fontebecci", "via Napoli", 43.34185, 11.3051388),
                EmergencyArea("Gino Fruschelli", "viale Gino Fruschelli", 43.3200222, 11.3231444),
                EmergencyArea("Bruno Bonci", "via Bruno Bonci", 43.31975, 11.3238277),
                EmergencyArea("Mandorlo", "strada del Mandorlo", 43.3084278, 11.3320277777),
                EmergencyArea("Parco della Rimembranza", "viale Vittorio Veneto", 43.3210799, 11.3225698),
                EmergencyArea("Parco Ochino", "via Luciano Banchi", 43.3392944, 11.2993166),
                EmergencyArea("Pispini", "via dei Pispini", 43.3176, 11.3426861),
                EmergencyArea("Unità d’Italia", "strada di Pescaia", 43.32375, 11.317275),
                EmergencyArea("Sant’Andrea", "strada Grossetana, incrocio Montecchio–Ginestreto", 43.2829361, 11.30379444),
                EmergencyArea("Vico Alto", "presso Asilo l’Arcobaleno, via Sicilia", 43.3449528, 11.3109722),
                EmergencyArea("Porta all’Ovile", "zona Porta Ovile", 43.324361, 11.3351055),
                EmergencyArea("Bruna Talluri", "viale Nazario Sauro, incrocio viale A. Diaz", 43.3253194, 11.3257333),
                EmergencyArea("Orto dei Pecci", "via Porta Giustizia", 43.3142056, 11.3356944),
                EmergencyArea("Laterino", "via Laterino", 43.3142944, 11.3237527),
                EmergencyArea("Chiocciola", "via del Nuovo Asilo", 43.3134306, 11.3263722),
                EmergencyArea("Liguria", "via Liguria", 43.3432417, 11.3115722),
                EmergencyArea("Mario Bracci", "viale Mario Bracci", 43.3403278, 11.3246833),
                EmergencyArea("Malizia", "strada di Malizia", 43.3350694, 11.3294444),
                EmergencyArea("Riccardo Lombardi", "via Riccardo Lombardi", 43.3318972, 11.3250138),
                EmergencyArea("Fonti di Pescaia", "via Martiri Caserma La Marmora", 43.3253528, 11.3219583),
                EmergencyArea("Dante", "via Dante", 43.3324, 11.2978833),
                EmergencyArea("Massetana", "strada Massetana Romana", 43.3032583, 11.3234777),
                EmergencyArea("Isola", "via della Mercanzia – via della Gabella", 43.2567528, 11.3803361),
                EmergencyArea("Taddeo di Bartolo", "via Taddeo di Bartolo", 43.3224111, 11.3433833),
                EmergencyArea("Ruffolo", "strada del Ruffolo", 43.3023583, 11.379738),
                EmergencyArea("Luigi Sprugnoli", "via Luigi Sprugnoli", 43.3203, 11.35037777),
                EmergencyArea("Sardegna", "viale Sardegna", 43.3278361, 11.33261111),
                EmergencyArea("Parco K2", "via della Fornace Vecchia", 43.2967694, 11.3438694),
                EmergencyArea("“La Racchetta”", "via Antonio Vivaldi", 43.3182083, 11.345175),
            ),
        ),
        EmergencyAreaGroup(
            "AR",
            "Aree di ricovero",
            "Dove si viene eventualmente accolti, anche per la notte: 12 in tutto il Comune.",
            listOf(
                EmergencyArea("Narciso Mengozzi", "via Narciso Mengozzi", 43.2640222, 11.37424444),
                EmergencyArea("Achille Sclavo", "via Achille Sclavo", 43.3346944, 11.3128888),
                EmergencyArea("Impianti sportivi S. Miniato", "via Veterani dello Sport – via Pertini", 43.3464139, 11.3297055),
                EmergencyArea("Enzo Corsi", "viale Avignone – via Pisacane", 43.327875, 11.31709166),
                EmergencyArea("Cerchiaia", "strada di Cerchiaia", 43.293525, 11.339258333),
                EmergencyArea("“Fagiolone”", "strada di Pescaia", 43.3151278, 11.31678611),
                EmergencyArea("Fausto Coppi", "via Fausto Coppi, Acqua Calda", 43.3353556, 11.295413),
                EmergencyArea("La Lizza", "viale Rinaldo Franci – viale Cesare Maccari", 43.3234556, 11.32558888),
                EmergencyArea("La Marmora", "viale Amendola", 43.3269139, 11.3219416),
                EmergencyArea("San Prospero", "viale Vittorio Veneto", 43.3226694, 11.3221416666),
                EmergencyArea("Rugby", "via Fausto Coppi", 43.3362194, 11.29779722),
                EmergencyArea("Conte d’Arras", "via Conte d’Arras", 43.2950722, 11.39373055),
            ),
        ),
        EmergencyAreaGroup(
            "AS",
            "Aree di ammassamento soccorritori",
            "Riservate ai mezzi di soccorso: non sono punti di raccolta per i cittadini. 6 in tutto il Comune.",
            listOf(
                EmergencyArea("Acquacalda", "via Fausto Coppi", 43.3360306, 11.29638611),
                EmergencyArea("Coroncina", "strada Regionale n. 2", 43.290913, 11.3436944),
                EmergencyArea("Costalpino", "strada provinciale n. 73 bis", 43.2921528, 11.303052),
                EmergencyArea("“Mens Sana”", "strada del Tiro a Segno", 43.3337472, 11.3159),
                EmergencyArea("Petriccio", "strada del Petriccio e Belriguardo", 43.3320278, 11.3003611),
                EmergencyArea("San Miniato", "via Sandro Pertini", 43.3470167, 11.32805277),
            ),
        ),
        EmergencyAreaGroup(
            "ALTRE",
            "Altre aree e strutture coperte",
            "Aree per giornalisti e turisti e strutture coperte censite per l’accoglienza.",
            listOf(
                EmergencyArea("Area giornalisti (AG)", "viale dello Stadio, parcheggio presso lo stadio comunale", 43.3219083, 11.32444166),
                EmergencyArea("Area turisti e non residenti (TUR)", "giardini pubblici de La Lizza", 43.3235167, 11.32679722),
                EmergencyArea("Palestra scuola “P.A. Mattioli”", "via Duccio di Boninsegna (anche sede secondaria del C.O.C.)", 43.3219111, 11.3439333),
                EmergencyArea("Asilo Nido “L’Arcobaleno”", "via Sicilia 27", 43.345344, 11.31061944),
                EmergencyArea("Scuola dell’Infanzia “Bandini”", "via San Marco", 43.3121694, 11.3254138),
            ),
        ),
    )
