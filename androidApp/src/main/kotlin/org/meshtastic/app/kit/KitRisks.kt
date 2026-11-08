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

// Generated from "Quaderno delle emergenze - Comune di Siena" (schede 01-10). Text kept as in the Quaderno.

val KitRisks: List<KitRisk> =
    listOf(
        KitRisk(
            id = "r01",
            number = "01",
            title = "Terremoto",
            category = "Rischio geofisico",
            summary = "Il Comune di Siena è in zona sismica 3: scuotimenti possibili, di norma moderati e non prevedibili in anticipo.",
            about = listOf(
            "Zona sismica 3 (delibera regionale 2014): scuotimenti attesi modesti (accelerazione 0,05–0,15 g).",
            "Non essendo prevedibile, non c'è fase di preallerta: si passa direttamente alla gestione dell'emergenza.",
            "Sede del Centro Operativo Comunale (C.O.C.): se danneggiata, sede secondaria alla scuola “P.A. Mattioli”, via D. di Boninsegna 76.",
            "Priorità nel ripristino: strade verso edifici strategici e Aree di Emergenza comunali (Allegato 3 del Piano).",
        ),
            contacts = listOf(
            "112 — Numero unico emergenze",
            "115 — Vigili del Fuoco",
        ),
            before = listOf(
            "Individuate in casa i punti più sicuri: sotto un architrave, vicino a un muro portante, lontano da vetri e mobili alti.",
            "Fissate al muro librerie, pensili e lo scaldabagno.",
            "Sapete tutti dove sono le chiavi di gas, luce e acqua.",
            "Tenete pronto il kit di emergenza e la scheda famiglia con il punto di ritrovo.",
        ),
            during = listOf(
            "In casa: riparatevi sotto un tavolo robusto o vicino a un muro portante, lontano da finestre e mobili che possono cadere. Non usate l'ascensore.",
            "All'aperto: allontanatevi da edifici, alberi e linee elettriche.",
            "In auto: fermatevi lontano da ponti, gallerie ed edifici.",
        ),
            after = listOf(
            "Uscite con prudenza controllando che non ci siano crolli; niente fiamme libere se sospettate una fuga di gas.",
            "Chiudete il gas e raggiungete il punto di ritrovo o l'area di attesa concordata (scheda famiglia).",
            "Ascoltate radio e canali ufficiali del Comune; non rientrate in edifici danneggiati.",
            "Evitate di intasare le linee telefoniche: usate messaggi, non chiamate, per farvi sentire al sicuro.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
        KitRisk(
            id = "r02",
            number = "02",
            title = "Alluvione",
            category = "Rischio idraulico",
            summary = "Piene e allagamenti lungo i corsi d'acqua maggiori: Arbia, Ombrone, Elsa, Merse.",
            about = listOf(
            "Punti più esposti: Isola d'Arbia e Taverne d'Arbia (classi 2–3 di pericolosità idraulica del PGRA).",
            "Presidio territoriale del Comune al ponte sul Fiume Arbia a Taverne d'Arbia in caso di allerta arancione/ rossa.",
            "Sequenza di allerta: codice giallo (vigilanza) → arancione (attenzione) → rosso (pre-allarme / allarme), diffusa dal Centro Funzionale Regionale.",
            "In evento in atto, il Comune informa tramite App e Alert System comunale, sito internet, megafoni e, se necessario, porta a porta.",
        ),
            contacts = listOf(
            "112 — Numero unico emergenze",
            "115 — Vigili del Fuoco",
        ),
            before = listOf(
            "Seguite i bollettini di criticità della Regione Toscana e gli avvisi del Comune di Siena.",
            "Portate ai piani alti beni di valore e apparecchi elettrici dai piani bassi.",
            "Non ostruite tombini e caditoie vicino a casa.",
        ),
            during = listOf(
            "Salite ai piani superiori; evitate scantinati e piani terra. Non usate l'ascensore.",
            "Staccate la corrente se l'acqua sta salendo e chiudete il gas.",
            "Non attraversate mai strade allagate, a piedi o in auto: bastano pochi centimetri d'acqua a trascinarvi.",
            "Non sostate su ponti o argini; tenete a portata di mano documenti, kit di pronto soccorso, torcia e denaro.",
        ),
            after = listOf(
            "Non bevete l'acqua del rubinetto finché non è dichiarata potabile.",
            "Evitate il contatto con l'acqua alluvionata.",
            "Fate verificare l'impianto elettrico e il gas prima di riattivarli.",
            "Seguite le indicazioni diffuse dal Comune per il rientro in sicurezza.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
        KitRisk(
            id = "r03",
            number = "03",
            title = "Frana",
            category = "Rischio idrogeologico",
            summary = "Smottamenti e dissesti del terreno, tipici delle colline argillose senesi (crete, calanchi, balze).",
            about = listOf(
            "Il territorio collinare e argilloso del senese è soggetto a fenomeni franosi, anche a bassa intensità di pioggia o in sua assenza.",
            "Stessa scala di allerta del rischio idraulico (giallo → arancione → rosso), ma i fenomeni sono puntuali e meno prevedibili degli allagamenti.",
            "In caso di criticità, il Comune valuta interdizioni di viabilità e, nei casi più gravi, l'evacuazione verso le Aree di Emergenza comunali.",
        ),
            contacts = listOf(
            "112 — Numero unico emergenze",
            "Comune — Ufficio Protezione Civile — vedi Scheda 00",
        ),
            before = listOf(
            "Osservate crepe nei muri, avvallamenti del terreno, alberi o pali inclinati vicino a casa: segnalateli al Comune.",
            "Evitate di costruire, parcheggiare o sostare sotto pendii instabili.",
            "Conoscete la viabilità alternativa in caso di chiusura della strada principale.",
        ),
            during = listOf(
            "Se percepite il movimento del terreno, allontanatevi subito in direzione trasversale (mai a valle né a monte).",
            "Lasciate l'edificio se notate crepe improvvise, rumori sordi dal terreno o cedimenti.",
            "Raggiungete un'area aperta e stabile, lontana da pendii e corsi d'acqua.",
        ),
            after = listOf(
            "Non rientrate finché l'area non è dichiarata sicura dalle autorità.",
            "Segnalate al Comune ogni nuovo cedimento o crepa.",
            "Seguite le indicazioni della Protezione Civile per la viabilità alternativa.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
        KitRisk(
            id = "r04",
            number = "04",
            title = "Vento forte e temporali",
            category = "Rischio meteo",
            summary = "Raffiche e temporali intensi: effetti attesi da blackout a caduta di rami e alberi.",
            about = listOf(
            "Codice arancione: raffiche probabili 80–100+ km/h in pianura, oltre 100–150 km/h su crinali — possibili blackout diffusi e caduta di alberi.",
            "Codice rosso: raffiche oltre 100 km/ h in pianura — interruzioni diffuse e prolungate di circolazione, elettricità e telefonia.",
            "I temporali forti in Toscana hanno allerta massima arancione: non esiste un codice rosso specifico per temporali isolati.",
        ),
            contacts = listOf(
            "112 — Numero unico emergenze",
            "Comune — per segnalare alberi/rami pericolanti",
        ),
            before = listOf(
            "Mettete in sicurezza oggetti su balconi e terrazzi e strutture leggere in giardino.",
            "Evitate di parcheggiare sotto alberi, impalcature o cartelloni.",
        ),
            during = listOf(
            "Evitate spostamenti non necessari, specie in auto su strade alberate.",
            "Allontanatevi da alberi, impalcature, cartelloni e linee elettriche.",
            "In auto, tenetevi a distanza da mezzi alti o con rimorchio, più esposti al vento laterale.",
        ),
            after = listOf(
            "Segnalate al Comune rami o alberi pericolanti.",
            "Non toccate cavi elettrici caduti a terra.",
            "Fate attenzione a detriti e vetri sulle strade prima di uscire.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
        KitRisk(
            id = "r05",
            number = "05",
            title = "Neve e ghiaccio",
            category = "Rischio meteo",
            summary = "Isolamento e disagi soprattutto nelle zone collinari e montane (Chianti, Amiata, Val d'Orcia).",
            about = listOf(
            "Codice arancione: possibili danneggiamenti, blackout elettrici e telefonici, interruzioni della viabilità.",
            "Codice rosso: problemi diffusi e prolungati a circolazione, viabilità e forniture di acqua, telefono ed elettricità.",
            "Le procedure operative di dettaglio sono nel Piano Neve del Comune di Siena.",
        ),
            contacts = listOf(
            "112 — Numero unico emergenze",
            "Comune — per interruzioni di viabilità o servizi",
        ),
            before = listOf(
            "Se vi spostate verso le zone collinari, dotate l'auto di gomme invernali o catene.",
            "Tenete una scorta di cibo, acqua, farmaci e coperte in caso di isolamento temporaneo.",
            "Conoscete l'ubicazione del quadro elettrico di casa.",
        ),
            during = listOf(
            "Limitate gli spostamenti non necessari.",
            "In auto: velocità ridotta, niente frenate brusche, portate con voi pala e catene.",
            "Non sostate sotto alberi con rami carichi di neve o ghiaccio.",
        ),
            after = listOf(
            "Liberate da neve e ghiaccio gli accessi di casa e i tombini vicini.",
            "Segnalate al Comune interruzioni di viabilità o servizi.",
            "Attenzione al ghiaccio “nero”, poco visibile, su strade e marciapiedi.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
        KitRisk(
            id = "r06",
            number = "06",
            title = "Incendio boschivo",
            category = "Rischio incendio",
            summary = "Incendi di bosco e di interfaccia urbano-rurale, dove case e campagna si toccano.",
            about = listOf(
            "Coordinamento regionale H24 tramite la SOUP (Sala Operativa Unificata Permanente) della Regione Toscana.",
            "Periodo di massima allerta: indicativamente dal 15 giugno al 15 settembre (le date esatte sono fissate ogni anno dalla Regione).",
            "Rischio più alto nelle zone di interfaccia: abitazioni a ridosso di boschi o campagna incolta.",
        ),
            contacts = listOf(
            "115 — Vigili del Fuoco",
            "800 425 425 — Numero verde SOUP Regione Toscana",
        ),
            before = listOf(
            "Mantenete sgombra la vegetazione secca nella fascia intorno a casa.",
            "Non accendete fuochi all'aperto nei periodi di divieto.",
            "Conoscete le vie di fuga verso aree già bruciate o prive di vegetazione.",
        ),
            during = listOf(
            "Se sorpresi all'aperto, allontanatevi in direzione opposta al vento e alla pendenza.",
            "Non ripiegate in auto in mezzo alla vegetazione.",
            "Se l'incendio si avvicina a casa: chiudete porte e finestre, sigillate le fessure con panni bagnati, spegnete il gas.",
            "Segnalate al 115 o al numero verde SOUP.",
        ),
            after = listOf(
            "Non rientrate in aree percorse dal fuoco finché non autorizzati.",
            "Controllate eventuali braci residue prima di allontanarvi.",
            "Segnalate focolai non ancora spenti.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
        KitRisk(
            id = "r07",
            number = "07",
            title = "Incendio domestico",
            category = "Rischio incendio",
            summary = "Il rischio più comune in assoluto: la prevenzione in casa fa la differenza.",
            about = listOf(
            "Non è un rischio territoriale specifico del Piano comunale: le indicazioni qui sono le buone pratiche nazionali di prevenzione incendi.",
            "Verificate periodicamente rilevatori di fumo ed estintore: sono spesso l'unica differenza tra un principio d'incendio e un disastro.",
        ),
            contacts = listOf(
            "115 — Vigili del Fuoco",
            "112 — Numero unico emergenze",
        ),
            before = listOf(
            "Installate rilevatori di fumo e tenete un estintore a norma, sapendo come si usa.",
            "Non sovraccaricate le prese elettriche.",
            "Non lasciate fornelli o candele accesi incustoditi.",
        ),
            during = listOf(
            "Date l'allarme e uscite rapidamente, stando bassi sotto il fumo.",
            "Chiudete le porte dietro di voi per rallentare le fiamme; non usate l'ascensore.",
            "Se i vestiti prendono fuoco: fermatevi, sdraiatevi, rotolatevi.",
            "Se non riuscite a uscire, sigillate la porta con panni e segnalatevi alla finestra.",
        ),
            after = listOf(
            "Chiamate il 115 e non rientrate finché i Vigili del Fuoco non lo autorizzano.",
            "Fate controllare impianto elettrico e del gas prima di riutilizzarli.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
        KitRisk(
            id = "r08",
            number = "08",
            title = "Nucleare e radiologico",
            category = "Rischio tecnologico",
            summary = "Evento raro: il Comune ha un ruolo di supporto e informazione, non di gestione diretta.",
            about = listOf(
            "Scenario “micro locale”: incidente nel trasporto di materiale radiogeno — gestito da Vigili del Fuoco e Prefettura; il Comune supporta con informazione e viabilità.",
            "Scenario “nazionale”: incidente in una centrale entro 200 km dal confine — fasi di preallarme e allarme decise a livello nazionale/ regionale e trasmesse dal Comune.",
            "In entrambi i casi il Comune non decide da solo: diffonde le indicazioni ufficiali ricevute dalla catena nazionale di comando.",
        ),
            contacts = listOf(
            "112 — Numero unico emergenze",
            "Radio / TV — canali ufficiali nazionali e comunali",
        ),
            before = listOf(
            "Nessuna azione preventiva specifica è richiesta alla popolazione.",
            "Sapete che, se necessario, le istruzioni ufficiali arriveranno da radio, TV e canali istituzionali: non da voci informali.",
        ),
            during = listOf(
            "Rifugiatevi il più rapidamente possibile in un edificio; chiudete porte e finestre e spegnete la ventilazione/condizionamento.",
            "Non uscite finché non indicato dalle autorità; sintonizzatevi su radio, TV e canali ufficiali.",
            "Non assumete pastiglie di iodio se non espressamente indicato dalle autorità sanitarie.",
        ),
            after = listOf(
            "Seguite scrupolosamente le indicazioni per un eventuale allontanamento o per il rientro.",
            "Se siete stati all'aperto durante l'evento, lavatevi accuratamente e cambiate abiti.",
            "Non consumate alimenti o acqua non dichiarati sicuri dalle autorità.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
        KitRisk(
            id = "r09",
            number = "09",
            title = "Rischio industriale",
            category = "Rischio tecnologico",
            summary = "Stabilimenti soggetti a normativa “Seveso”: per chi abita nelle vicinanze, in Provincia di Siena.",
            about = listOf(
            "Per gli stabilimenti a rischio di incidente rilevante, la Prefettura predispone un Piano di Emergenza Esterna specifico.",
            "Chi abita vicino a uno di questi stabilimenti riceve dal gestore/ Comune una scheda informativa dedicata: conservatela con questo quaderno.",
        ),
            contacts = listOf(
            "112 — Numero unico emergenze",
            "115 — Vigili del Fuoco",
        ),
            before = listOf(
            "Prendete visione della scheda informativa fornita dal gestore dello stabilimento o dal Comune, se abitate nelle vicinanze.",
            "Individuate un locale chiudibile ermeticamente da usare come rifugio.",
        ),
            during = listOf(
            "Al segnale di allarme (sirena o avviso), rifugiatevi rapidamente in un locale chiuso.",
            "Chiudete porte e finestre, spegnete condizionatori e ventilazione, sigillate le fessure con panni bagnati.",
            "Non usate il telefono se non necessario, per non intasare le linee; ascoltate radio e canali ufficiali.",
        ),
            after = listOf(
            "Non uscite finché non viene comunicato il cessato allarme dalle autorità.",
            "Arieggiate i locali solo dopo l'autorizzazione ufficiale.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
        KitRisk(
            id = "r10",
            number = "10",
            title = "Blackout e interruzione servizi",
            category = "Rischio tecnologico",
            summary = "Interruzione di elettricità, acqua o telefonia: spesso conseguenza di vento, neve o temporali forti.",
            about = listOf(
            "È uno degli effetti più comuni di vento forte, neve e temporali già descritti nelle schede 4 e 5.",
            "Per interruzioni prolungate, segnalate al gestore di rete (elettrico, idrico) e consultate il sito del Comune per gli aggiornamenti.",
        ),
            contacts = listOf(
            "112 — Numero unico emergenze",
            "Gestore di rete — numero verde sulla bolletta",
        ),
            before = listOf(
            "Tenete torce, batterie di scorta e un power bank sempre carico.",
            "Sapete dov'è il quadro elettrico di casa.",
            "Mantenete una scorta minima di acqua e cibo non deperibile.",
        ),
            during = listOf(
            "Scollegate gli apparecchi elettronici sensibili.",
            "Usate il cellulare con parsimonia; aprite frigorifero e freezer il meno possibile.",
            "Non usate fornelli o generatori a combustione in ambienti chiusi: rischio di monossido di carbonio.",
        ),
            after = listOf(
            "Riaccendete gli apparecchi gradualmente.",
            "Controllate gli alimenti deperibili prima di consumarli.",
            "Segnalate l'interruzione al gestore se si prolunga oltre le previsioni.",
        ),
            source = "Fonti: Piano di Protezione Civile del Comune di Siena e della Provincia di Siena (2024) — Allegato 1 “Procedure ad hoc per ogni rischio”; iononrischio.it, campagna nazionale di Protezione Civile.",
        ),
    )
