# HomeButler - Java Telegram Bot 🤖🏠

Progetto sviluppato per l'esame di **Programmazione ad Oggetti**. Si tratta di un sistema di simulazione Smart Home che integra un bot Telegram con un database relazionale per la gestione degli stati dei dispositivi.

## 🛠️ Tecnologie e Pattern
* **Linguaggio:** Java 11+
* **Build Tool:** Maven (gestione dipendenze e build)
* **Database:** MySQL (MariaDB)
* **Librerie:** Telegram Bots API, JDBC
* **Pattern:** Utilizzo di classi DAO per l'accesso ai dati e programmazione guidata dagli eventi per la gestione dei messaggi.

## 🌟 Funzionalità Principali
* **Controllo Remoto:** Accensione/spegnimento simulato di dispositivi tramite comandi Telegram.
* **Monitoraggio Stato:** Il bot interroga il database per evitare azioni ridondanti (es. "Il forno è già acceso").
* **Sistema di Alert:** Simulazione di un sensore fumo con invio di messaggi d'allerta basato su probabilità casuale (Thread dedicato).
* **Configurazione Dinamica:** Organizzazione gerarchica Casa -> Stanza -> Dispositivo tramite database.

## 🚀 Come avviare il progetto
1.  **Database:** Importa il file `telegrambot.sql` presente nella root del progetto sul tuo server MySQL locale.
2.  **Configurazione:** Assicurati di configurare le credenziali di accesso al DB nella classe `Connessione.java`.
3.  **Bot Token:** Sostituisci il token segreto nel codice di `Home_ButlerBot.java` con quello ottenuto da `@BotFather`.
4.  **Esecuzione:** Esegui il comando `mvn clean compile exec:java`.
