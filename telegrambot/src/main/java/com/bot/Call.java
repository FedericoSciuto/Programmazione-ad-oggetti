package com.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class Call {

    Query query = new Query();                                                          // Creazione di un nuovo oggetto della classe "Query"

    public static String tipo;                                                          // Dichiarazione di variabili statiche (necessario affinchè mantengano il valore quando si crea un nuovo oggetto della classe Call)
    public static String stato;
    public static String stanza;
    public static String dispositivo;
    
    public String chiamata(Update update, String data, SendMessage sendMessage, String chatId, String pulsante) {           // Metodo usato per il controllo dei callBack

        if (data.equals("Casa esistente")) {
            sendMessage.setText("Digiti l'Id della sua casa");
            pulsante = "Casa esistente";

        } else if (data.equals("Nuova casa")) {
            sendMessage.setText("Inserisca un nome per la sua casa (max 20 caratteri)");
            pulsante = "Nuova casa";

        } else if (data.equals("Rinomina casa")) {
            sendMessage.setText("Inserisca un nuovo nome per la sua casa (max 20 caratteri)");
            pulsante = "Rinomina casa";

        } else if (data.equals("Rimuovi casa")) {
            String q = "SELECT home.Nome FROM home JOIN chat ON home.Id = chat.Id_Casa WHERE chat.Id = " + chatId;
            String colonna = "Nome";
            String res = query.getResult(q, colonna);

            q = "DELETE FROM home WHERE Nome = '" + res + "'";
            boolean i = query.insertUpdateRemove(q);

            if (i) {
                sendMessage.setText(res + " rimossa correttamente");
            } else {
                sendMessage.setText("Errore nella rimozione della casa");
            }

        } else if (data.equals("Aggiungi stanza")) {
            sendMessage.setText("Inserisca il nome che vuole dare alla stanza (max 20 caratteri)");
            pulsante = "Aggiungi stanza";
        
        } else if (data.equals("Rimuovi stanza")) {
            String q = "SELECT stanze.Nome FROM stanze JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE chat.Id = " + chatId;
            String colonna = "Nome";
            InlineKeyboardMarkup keyboard = query.getPulsanti(q, colonna);

            if (keyboard.getKeyboard().isEmpty()) {
                sendMessage.setText("Non c'è nessuna stanza");
            } else {
                sendMessage.setText("Quale stanza vuole rimuovere?");
                sendMessage.setReplyMarkup(keyboard);
                pulsante = "Rimuovi stanza";
            }

        } else if (data.equals("Aggiungi dispositivo")) {
            sendMessage.setText("Che tipo di dispositivo vuole inserire?");
            Buttons button = new Buttons();
            button.tipoButtons(sendMessage);
            pulsante = "Aggiungi dispositivo";

        } else if (data.equals("Rimuovi dispositivo")) {
            String q = "SELECT dispositivi.Tipo FROM dispositivi JOIN stanze ON dispositivi.Id_Stanza = stanze.Id JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE chat.Id = " + chatId + " GROUP BY dispositivi.Tipo";
            String colonna = "Tipo";
            InlineKeyboardMarkup keyboard = query.getPulsanti(q, colonna);

            if (keyboard.getKeyboard().isEmpty()) {
                sendMessage.setText("Non c'è nessun dispositivo");
                pulsante = null;
            } else {
                sendMessage.setText("Selezioni il tipo del dispositivo che desidera rimuovere");
                sendMessage.setReplyMarkup(keyboard);
                pulsante = "Rimuovi dispositivo";
            }

        } else if (pulsante.equals("Stanza")) {
            tipo = data;

            String q = "SELECT stanze.Nome FROM stanze JOIN dispositivi ON stanze.Id = dispositivi.Id_Stanza JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE dispositivi.Tipo = '" + tipo + "' AND chat.Id = " + chatId;
            String colonna = "Nome";
            InlineKeyboardMarkup keyboard = query.getPulsanti(q, colonna);
            sendMessage.setText("Selezioni la stanza del dispositivo che vuole gestire");
            sendMessage.setReplyMarkup(keyboard);
            pulsante = "Dispositivo";

        } else if (pulsante.equals("Dispositivo")) {

            stanza = data;

            String q = "SELECT dispositivi.Nome FROM dispositivi JOIN stanze ON dispositivi.Id_Stanza = stanze.Id JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE dispositivi.Tipo = '" + tipo + "' AND stanze.Nome = '" + stanza + "' AND chat.Id = " + chatId;
            String colonna = "Nome";
            InlineKeyboardMarkup keyboard = query.getPulsanti(q, colonna);
            sendMessage.setText("Quale dispositivo vuole gestire?");
            sendMessage.setReplyMarkup(keyboard);
            pulsante = "Azione";

        } else if (pulsante.equals("Azione")) {
            dispositivo = data;

            sendMessage.setText("Cosa vuole che faccia con: " + dispositivo);

            if (tipo.equals("Finestre")) {
                String pulsante1a = "Apri";
                String pulsante1b = "Aperta";
                String pulsante2a = "Chiudi";
                String pulsante2b = "Chiusa";

                Buttons button = new Buttons();
                button.twoButtons(sendMessage, pulsante1a, pulsante1b, pulsante2a, pulsante2b);
            } else {
                String pulsante1a = "Accendi";
                String pulsante1b = "Acceso";
                String pulsante2a = "Spegni";
                String pulsante2b = "Spento";

                Buttons button = new Buttons();
                button.twoButtons(sendMessage, pulsante1a, pulsante1b, pulsante2a, pulsante2b);
            }
            pulsante = "Termina";

        } else if (pulsante.equals("Termina")) {

            String q = "SELECT dispositivi.Stato FROM dispositivi JOIN stanze ON dispositivi.Id_Stanza = stanze.Id JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE dispositivi.Nome = '" + dispositivo + "' AND stanze.Nome = '" + stanza + "' AND chat.Id = " + chatId;
            String colonna = "Stato";
            String res = query.getResult(q, colonna);

            if (res.equals(data)) {
                sendMessage.setText(dispositivo + " è già " + data);
            } else {
                q = "UPDATE dispositivi JOIN stanze ON dispositivi.Id_Stanza = stanze.Id JOIN chat ON stanze.Id_Casa = chat.Id_Casa SET dispositivi.Stato = '" + data + "' WHERE dispositivi.Nome = '" + dispositivo + "' AND stanze.Nome = '" + stanza + "' AND chat.Id = " + chatId;
                boolean i = query.insertUpdateRemove(q);

                if (i) {
                    sendMessage.setText(dispositivo + " " + data + " correttamente");
                } else {
                    sendMessage.setText("Errore nell'esecuzione dell'operazione");
                }
            }
            pulsante = null;

        } else if (pulsante.equals("Rimuovi stanza")) {
            String q = "DELETE stanze FROM stanze JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE stanze.Nome = '" + data + "' AND chat.Id = " + chatId;
            boolean i = query.insertUpdateRemove(q);
            
            if (i) {
                sendMessage.setText(data + " rimossa correttamente");
            } else {
                sendMessage.setText("Errore nella rimozione della stanza");
            }
            pulsante = null;

        } else if (pulsante.equals("Aggiungi dispositivo")) {
            tipo = data;

            String q = "SELECT stanze.Nome FROM stanze JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE chat.Id = " + chatId;
            String colonna = "Nome";
            InlineKeyboardMarkup keyboard = query.getPulsanti(q, colonna);
            
            if (keyboard.getKeyboard().isEmpty()) {
                sendMessage.setText("Non c'è nessuna stanza in questa casa. Digiti /edit_room per inserirle");
                pulsante = null;
            } else {
                sendMessage.setText("In quale stanza vuole inserire il dispositivo?");
                sendMessage.setReplyMarkup(keyboard);
                pulsante = "Stanza dispositivo";
            }

        } else if (pulsante.equals("Stanza dispositivo")) {
            stanza = data;
            sendMessage.setText("Inserisca il nome che vuole dare al dispositivo");
            pulsante = "Nome dispositivo";

        } else if (pulsante.equals("Rimuovi dispositivo")) {
            tipo = data;
            
            String q = "SELECT stanze.Nome FROM stanze JOIN dispositivi ON stanze.Id = dispositivi.id_Stanza JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE dispositivi.Tipo = '" + tipo + "' AND chat.Id = " + chatId;
            String colonna = "Nome";
            InlineKeyboardMarkup keyboard = query.getPulsanti(q, colonna);
            sendMessage.setText("In quale stanza si trova il dispositivo che vuole rimuovere");
            sendMessage.setReplyMarkup(keyboard);
            pulsante = "Rimuovi dispositivo da stanza";

        } else if (pulsante.equals("Rimuovi dispositivo da stanza")) {
            stanza = data;

            String q = "SELECT dispositivi.Nome FROM dispositivi JOIN stanze ON dispositivi.Id_Stanza = stanze.Id JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE dispositivi.Tipo = '" + tipo + "' AND stanze.Nome = '" + stanza + "' AND chat.Id = " + chatId;
            String colonna = "Nome";
            InlineKeyboardMarkup keyboard = query.getPulsanti(q, colonna);
            sendMessage.setText("Quale dispositivo vuole rimuovere?");
            sendMessage.setReplyMarkup(keyboard);
            pulsante = "Eliminazione dispositivo";

        } else if (pulsante.equals("Eliminazione dispositivo")) {
            String q = "DELETE dispositivi FROM dispositivi JOIN stanze ON dispositivi.Id_Stanza = stanze.Id JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE dispositivi.Nome = '" + data + "' AND stanze.Nome = '" + stanza + "' AND chat.Id = " + chatId;
            boolean i = query.insertUpdateRemove(q);

            if (i) {
                sendMessage.setText(data + " rimosso correttamente");
            } else {
                sendMessage.setText("Errore nella rimozione del dispositivo");
            }
            pulsante = null;
        }
        return pulsante;
    }

    public String risposta(Update update, String msg, SendMessage sendMessage, String chatId, String pulsante) {           // Metodo usato per il controllo dei messaggi inviati dall'utente

        if (pulsante.equals("Casa esistente")) {

            if (msg.matches("[0-9]+")) {                                         // Controlla che il messaggio inviato dall'utente sia interamente formato da numeri
                String q = "SELECT Nome FROM home WHERE Id = " + msg;
                String colonna = "Nome";
                String res = query.getResult(q, colonna);

                if (res == "") {
                    sendMessage.setText("L'Id da lei inserito non corrisponde a nessuna casa esistente");
                } else {
                    q = "UPDATE chat SET Id_Casa = " + msg + " WHERE Id = " + chatId;
                    boolean i = query.insertUpdateRemove(q);

                    if (i) {
                        sendMessage.setText("Benvenuto, sta gestendo " + res);
                    } else {
                        sendMessage.setText("Errore riscontrato");
                    }
                }
            } else {
                sendMessage.setText("Il messaggio da lei inserito comprendeva uno o più caratteri non validi. L'Id è composto solamente da numeri e senza spazi");
            }
            pulsante = null;

        } else if (pulsante.equals("Nuova casa")) {

            if (msg.startsWith("/") || msg.length() > 20) {                     // Controlla che il messaggio inviato dall'utente non inizi con "/" e non contenga più di 20 caratteri
                sendMessage.setText("Il nome da lei inserito contiene caratteri non permessi (inizia con /) oppure supera i 20 caratteri consentiti");
            } else {
                String q = "SELECT Nome FROM home WHERE Nome = '" + msg + "'";
                String colonna = "Nome";
                String res = query.getResult(q, colonna);

                if (res.equals(msg)) {
                    sendMessage.setText("Il nome da lei inserito è già in uso, provi ad inserirne un altro");
                } else {
                    q = "INSERT INTO home (Id, Nome) VALUES (NULL, '" + msg + "')";
                    boolean i = query.insertUpdateRemove(q);

                    if (i) {
                        q = "UPDATE chat SET Id_Casa = (SELECT Id FROM home WHERE Nome = '" + msg + "') WHERE Id = " + chatId;
                        query.insertUpdateRemove(q);
                        sendMessage.setText(msg + " inserita correttamente. Per conoscere l'Id della sua casa digiti /id");
                    } else {
                        sendMessage.setText("Errore nell'inserimento della casa");
                    }
                }
            }
            pulsante = null;
        
        } else if (pulsante.equals("Rinomina casa")) {

            if (msg.startsWith("/") || msg.length() > 20) {
                sendMessage.setText("Il nome da lei inserito contiene caratteri non permessi (inizia con /) oppure supera i 20 caratteri consentiti");
            } else {
                String q = "SELECT Nome FROM home WHERE Nome = '" + msg + "'";
                String colonna = "Nome";
                String res = query.getResult(q, colonna);

                if (res.equals(msg)) {
                    sendMessage.setText("Il nome da lei inserito è già in uso, provi ad inserirne un altro");
                } else {
                    q = "UPDATE home JOIN chat ON home.Id = chat.Id_Casa SET home.Nome = '" + msg + "' WHERE chat.Id = " + chatId;
                    boolean i = query.insertUpdateRemove(q);

                    if (i) {
                        sendMessage.setText("Casa rinominata correttamente in: " + msg);
                    } else {
                        sendMessage.setText("Errore, non è stato possibile rinominare la casa");
                    }
                }
            }
            pulsante = null;

        } else if (pulsante.equals("Aggiungi stanza")) {
            
            if (msg.startsWith("/") || msg.length() > 20) {
                sendMessage.setText("Il nome da lei inserito contiene caratteri non permessi (inizia con /) oppure supera i 20 caratteri consentiti");
            } else {
                String q = "SELECT stanze.Nome FROM stanze JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE stanze.Nome = '" + msg + "' AND chat.Id = " + chatId;
                String colonna = "Nome";
                String res = query.getResult(q, colonna);

                if (res.equals(msg)) {
                    sendMessage.setText("In questa casa c'è già una stanza che si chiama " + res + ", provi ad inserire un altro nome");
                } else {
                    q = "INSERT INTO stanze (Id, Nome, Id_Casa) SELECT NULL, '" + msg + "', chat.Id_Casa FROM chat WHERE chat.Id = " + chatId;
                    boolean i = query.insertUpdateRemove(q);

                    if (i) {
                        sendMessage.setText(msg + " inserita correttamente");
                    } else {
                        sendMessage.setText("Errore nell'inserimento della stanza");
                    }
                }
            }
            pulsante = null;

        } else if (pulsante.equals("Nome dispositivo")) {

            if (msg.startsWith("/") || msg.length() > 20) {
                sendMessage.setText("Il nome da lei inserito contiene caratteri non permessi (inizia con /) oppure supera i 20 caratteri consentiti");
            } else {
                String q = "SELECT dispositivi.Nome FROM dispositivi JOIN stanze ON dispositivi.Id_stanza = stanze.Id JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE dispositivi.Nome = '" + msg + "' AND stanze.Nome = '" + stanza + "' AND chat.Id = " + chatId;
                String colonna = "Nome";
                String res = query.getResult(q, colonna);

                stato = "Spento";

                if (tipo.equals("Finestre")) {
                    stato = "Chiusa";
                }

                if (res.equals(msg)) {
                    sendMessage.setText("In " + stanza + " c'è già un dispositivo che si chiama " + msg + ", provi ad inserire un altro nome");
                } else {
                    q = "SELECT stanze.Id FROM stanze JOIN chat ON stanze.Id_Casa = chat.Id_Casa WHERE stanze.Nome = '" + stanza + "' AND chat.Id = " + chatId;
                    colonna = "Id";
                    res = query.getResult(q, colonna);

                    q = "INSERT INTO dispositivi (Id, Nome, Tipo, Stato, Id_Stanza) VALUES (NULL, '" + msg + "', '" + tipo + "', '" + stato + "', " + res + ")";
                    boolean i = query.insertUpdateRemove(q);

                    if (i) {
                        sendMessage.setText(msg + " inserito correttamente in " + stanza);
                    } else {
                        sendMessage.setText("Errore nell'inserimento del dispositivo");
                    }
                }
            }
            pulsante = null;
        }
        return pulsante;
    }
}
