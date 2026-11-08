<p align="center">
  <img src=".github/smartlab_logo.png" alt="Smartlab LoRa" width="200"/>
</p>
<h1 align="center">Smartlab LoRa — app Android</h1>

App per comunicare e restare orientati **anche senza internet**: pensata per le emergenze e per una rete di quartiere
fatta di radio LoRa, Wi-Fi e Bluetooth. Un progetto di **Smart Lab Siena**.

## A cosa serve

Quando le reti telefoniche e internet non funzionano, le schede radio LoRa continuano a far passare messaggi e posizioni
da un nodo all'altro, anche a chilometri di distanza. L'app è la parte che si tiene in mano: si collega a una scheda e
permette di scrivere, vedere i nodi sulla mappa e consultare le informazioni utili, tutto senza rete.

## Come funziona

- **Collegamento alla scheda**: via Bluetooth, USB o Wi-Fi (porta TCP 4403). L'app parla con la scheda, e la scheda
  con le altre schede via radio; i messaggi viaggiano cifrati sui canali condivisi.
- **Home di emergenza**: stato della scheda e della rete a colpo d'occhio, con i comandi principali a portata di mano.
- **Messaggi e nodi**: chat di canale e dirette, elenco dei nodi vicini con segnale e posizione.
- **Mappa**: nodi e posizioni sulla mappa, con **mappe e rilievi scaricabili** per usarla senza rete. Dal Kit si può
  mostrare sulla mappa un'area di emergenza scelta, ricavata da coordinate salvate nell'app.
- **Kit emergenza offline**: guide per ogni rischio (cosa fare prima, durante e dopo), numeri utili con chiamata
  diretta, liste da spuntare e moduli da compilare, come il piano di famiglia. I dati inseriti restano solo sul
  telefono e si condividono solo se lo decidi tu.
- **Aree di emergenza**: elenco consultabile di aree di attesa, ricovero e ammassamento con indicazione sulla mappa.

### Dati del Kit

I contenuti di esempio sono ricavati dai documenti pubblici di protezione civile del Comune di Siena. Chi vuole usare
l'app nella propria zona può inserire i dati presi dai siti istituzionali del proprio comune: schede e liste stanno
in `androidApp/src/main/kotlin/org/meshtastic/app/kit/` e le aree di emergenza in
`feature/map/src/commonMain/kotlin/org/meshtastic/feature/map/areas/`.

## Compilare

Servono JDK 21 e l'Android SDK. Dalla cartella del progetto:

```bash
./gradlew :androidApp:assembleFdroidDebug
```

L'APK si trova in `androidApp/build/outputs/apk/fdroid/debug/`. Il nome del pacchetto è `it.smartlab.lora`
(`it.smartlab.lora.fdroid.debug` per la build di debug).

## Licenza e attribuzioni

Software libero sotto licenza **GPL-3.0-or-later** (vedi `LICENSE`). Il progetto nasce da
[Meshtastic-Android](https://github.com/meshtastic/Meshtastic-Android) e ne usa il protocollo, quindi dialoga con i nodi
Meshtastic e con il nostro firmware ([smartlab-lora-firmware](https://github.com/smartlabsiena/smartlab-lora-firmware)).
Le intestazioni di copyright originali sono mantenute; le modifiche sono elencate in `SMARTLAB_NOTICE.md` e il README
originale è in `README.meshtastic.md`.

Meshtastic® è un marchio registrato di Meshtastic LLC; questo progetto non è affiliato né approvato da Meshtastic.

Le indicazioni del Kit sono a scopo informativo: in caso di evento reale seguire sempre le disposizioni ufficiali del
Comune e dei soccorritori.
