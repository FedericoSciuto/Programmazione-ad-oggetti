# 🏠 HomeButler: Smart Home Telegram Bot

[![Java](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/Database-MySQL-blue.svg)](https://www.mysql.com/)

**HomeButler** è un bot Telegram sviluppato in Java che simula la gestione di una smart home. Il progetto esplora l'integrazione tra API di messaggistica, logica di controllo asincrona e persistenza dei dati.

## 🚀 Funzionalità
- **Controllo Dispositivi:** Gestione stato (ON/OFF) di elettrodomestici e luci tramite comandi chat.
- **Logica di Stato:** Il sistema verifica lo stato attuale sul DB prima di agire (es. evita di accendere un forno già attivo).
- **Safety Simulation:** Thread in background che simula un sensore fumo con invio di alert critici casuali.
- **Persistenza:** Ogni cambiamento di stato è sincronizzato in tempo reale su un database MySQL.

## 🛠 Tech Stack
- **Backend:** Java (JDK 11+) con Maven per la gestione dipendenze.
- **Bot API:** Telegram Bots Library.
- **Database:** MySQL per la memorizzazione dello stato dei dispositivi.
- **Data Format:** XML per la configurazione dei parametri.

## 📦 Installazione e Configurazione
1. **Database:** Esegui lo script `telegrambot.sql`.
2. **Bot Token:** Inserisci il tuo API Token ottenuto da @BotFather nel file `Home_ButlerBot.java`.
3. **Build:** Esegui `mvn clean install`.
4. **Run:** Avvia la classe `Main.java`.

---
*Nota: Questo progetto è stato realizzato a scopo didattico per l'esame di Programmazione ad oggetti. Per i dettagli teorici e i diagrammi UML, consulta la [Relazione PDF](./Relazione.pdf).*
