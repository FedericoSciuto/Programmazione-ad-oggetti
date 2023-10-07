package com.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class Commands {
    
    public String botCommands(String msg, SendMessage sendMessage, String chatId) {

        String pulsante = null;

        String q = "SELECT Id FROM chat WHERE Id = " + chatId;                          // Controlla che il chatId sia già presente nel database
        String colonna = "Id";
        Query query = new Query();
        String user = query.getResult(q, colonna);

        if (user.isEmpty()) {                                                           // Se non dovesse essere nel database, lo inserisce senza associare ad esso una casa
            q = "INSERT INTO chat (Id, Id_Casa) VALUES (" + chatId + ", NULL)";
            query.insertUpdateRemove(q);
        }

        q = "SELECT Id_Casa FROM chat WHERE Id = " + chatId;                            // Prende il valore dell'Id della casa associato al chatId e lo memorizza nella variabile "casa"
        colonna = "Id_Casa";
        String casa = query.getResult(q, colonna);

        String pulsante1;
        String pulsante2;

        switch (msg) {
            case "/start":

                if (casa == null || casa == "") {                                       // Se non è associata nessuna casa al chatId si fa scegliere all'utente se vuole gestire una casa già presente nel database, o crearne una nuova
                    sendMessage.setText("Benvenuto, possiede una casa già esistente o desidera crearne una nuova?");
                    pulsante1 = "Casa esistente";
                    pulsante2 = "Nuova casa";

                    Buttons button = new Buttons();
                    button.twoButtons(sendMessage, pulsante1, pulsante1, pulsante2, pulsante2);
                } else {                                                                // Se la casa è associata si visualizzano i tipi dei dispositivi presenti nella casa (Elettrodomestici, Finestre, Luci, Riscaldamenti)
                    q = "SELECT dispositivi.Tipo FROM dispositivi JOIN stanze ON dispositivi.Id_Stanza = stanze.Id JOIN home ON stanze.Id_Casa = home.Id WHERE home.Id = " + casa + " GROUP BY Tipo";
                    colonna = "Tipo";
                    InlineKeyboardMarkup keyboard = query.getPulsanti(q, colonna);

                    if (keyboard.getKeyboard().isEmpty()) {                             // Controlla se nella casa è presente qualche dispositivo
                        sendMessage.setText("Non ha nessun dispositivo collegato a questa casa, se vuole aggiungerli digiti /edit_device");
                    } else {
                        sendMessage.setText("Benvenuto, di cosa desidera che mi occupi?");
                        sendMessage.setReplyMarkup(keyboard);
                        pulsante = "Stanza";
                    }
                }
                
                break;

            case "/help":
                sendMessage.setText("I comandi validi sono:\n/start per iniziare a gestire la sua dimora,\n/help per conoscere ulteriori comandi,\n/stop per uscire,\n/id per ottenere l'Id della casa che sta gestendo,\n/edit_home per rinominare o eliminare la casa che sta gestendo,\n/edit_room per aggiungere o rimuovere le stanze,\n/edit_device per aggiungere o rimuovere i dispositivi");
                break;

            case "/stop":
                
                if (casa == null) {
                    sendMessage.setText("Non sta gestendo nessuna dimora, se vuole iniziare a gestirne una digiti /start");
                } else {                                                                // Se la casa è associata la toglie
                    q = "UPDATE chat SET Id_Casa = NULL WHERE Id = " + chatId;
                    boolean i = query.insertUpdateRemove(q);

                    if (i) {
                        sendMessage.setText("Arrivederla, le auguro una buona giornata");
                    } else {
                        sendMessage.setText("Errore riscontrato durante l'uscita");
                    }
                    break;
                }

            case "/id":

                if (casa == null) {
                    sendMessage.setText("Non sta gestendo nessuna dimora, se vuole iniziare a gestirne una digiti /start");
                } else {                                                                // Restituisce l'Id della casa associata al chatId
                    q = "SELECT Id_Casa FROM chat WHERE Id = " + chatId;
                    colonna = "Id_Casa";
                    String res = query.getResult(q, colonna);
                    sendMessage.setText("L'Id della sua casa è: " + res);
                }
                break;

            case "/edit_home":

                if (casa == null) {
                    sendMessage.setText("Non sta gestendo nessuna dimora, se vuole iniziare a gestirne una digiti /start");
                } else {
                    q = "SELECT home.Nome FROM home JOIN chat ON home.Id = chat.Id_Casa WHERE chat.Id = " + chatId;
                    colonna = "Nome";
                    String res = query.getResult(q, colonna);
                    sendMessage.setText("Cosa vuole fare con la dimora " + res + "? (La rimozione della casa comporterà l'eliminazione di tutte le stanze e i dispositivi ad essa associati)");
                    pulsante1 = "Rinomina casa";
                    pulsante2 = "Rimuovi casa";
                    
                    Buttons button = new Buttons();
                    button.twoButtons(sendMessage, pulsante1, pulsante1, pulsante2, pulsante2);
                }
                break;

            case "/edit_room":

                if (casa == null) {
                    sendMessage.setText("Non sta gestendo nessuna dimora, se vuole iniziare a gestirne una digiti /start");
                } else {
                    sendMessage.setText("Cosa desidera fare con le stanze di casa sua? (La rimozione della stanza comporterà l'eliminazione di tutti i dispositivi associati ad essa)");
                    pulsante1 = "Aggiungi stanza";
                    pulsante2 = "Rimuovi stanza";

                    Buttons button = new Buttons();
                    button.twoButtons(sendMessage, pulsante1, pulsante1, pulsante2, pulsante2);
                }
                break;

            case "/edit_device":

                if (casa == null) {
                    sendMessage.setText("Non sta gestendo nessuna dimora, se vuole iniziare a gestirne una digiti /start");
                } else {
                    sendMessage.setText("Cosa desidera fare con i dispositivi?");
                    pulsante1 = "Aggiungi dispositivo";
                    pulsante2 = "Rimuovi dispositivo";

                    Buttons button = new Buttons();
                    button.twoButtons(sendMessage, pulsante1, pulsante1, pulsante2, pulsante2);
                }
                break;

            default:
                sendMessage.setText("Il comando inserito non è valido, per conoscere i comandi è pregato di digitare /help");
        }
        return pulsante;
    }
}
