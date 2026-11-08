<p align="center">
  <img src=".github/smartlab_logo.png" alt="Smartlab LoRa" width="200"/>
</p>
<h1 align="center">Smartlab LoRa — app Android</h1>

App per comunicare e restare orientati **anche senza internet**, pensata per le emergenze e per una rete di quartiere
fatta di radio LoRa, Wi-Fi e Bluetooth. Un progetto di **Smart Lab Siena**.

È un fork di [Meshtastic-Android](https://github.com/meshtastic/Meshtastic-Android) e resta **compatibile al 100% con il
protocollo Meshtastic**: dialoga con i nodi Meshtastic e con il nostro firmware
([smartlab-lora-firmware](https://github.com/smartlabsiena/smartlab-lora-firmware)). Il protocollo non è modificato.

## Cosa aggiunge rispetto a Meshtastic

- **Home di emergenza** al posto della schermata di connessione, con grafica e icone Smartlab.
- **Kit emergenza offline**, ricavato dal *Quaderno delle emergenze* del Comune di Siena: schede per ogni rischio
  (terremoto, alluvione, incendio, blackout…), numeri utili con chiamata diretta, liste da spuntare (kit famiglia, kit
  neonato) e moduli da compilare (piano famiglia, carta d'identità del bambino). I dati compilati restano sul telefono.
- **Aree di emergenza del Comune di Siena** (attesa, ricovero, ammassamento) con le coordinate ufficiali del Piano di
  Protezione Civile, disegnate sulla mappa e utilizzabili senza rete.
- **Mappe e rilievi offline**: scarica la tua zona prima di averne bisogno.
- Testi in italiano, sfondo mimetico, schermata di avvio e icone nuove.

Lo stato del lavoro è in sviluppo: alcune parti (invio S.O.S., schermate squadra, modalità sentinella) sono ancora da fare.

## Compilare

Servono JDK 21 e l'Android SDK. Dalla cartella del progetto:

```bash
./gradlew :androidApp:assembleFdroidDebug
```

L'APK si trova in `androidApp/build/outputs/apk/fdroid/debug/`. Il nome del pacchetto è `it.smartlab.lora`
(`it.smartlab.lora.fdroid.debug` per la build di debug).

## Licenza e attribuzioni

Software libero sotto licenza **GPL-3.0-or-later** (vedi `LICENSE`). Il codice originale è di Meshtastic LLC e dei suoi
collaboratori: le intestazioni di copyright sono mantenute. Le nostre modifiche sono elencate in `SMARTLAB_NOTICE.md`.

Meshtastic® è un marchio registrato di Meshtastic LLC. Questo progetto **non è affiliato né approvato** dal progetto
Meshtastic. Il loro README originale è conservato in `README.meshtastic.md`.

Le indicazioni del Kit sono a scopo informativo: in caso di evento reale seguire sempre le disposizioni ufficiali del
Comune e dei soccorritori.
