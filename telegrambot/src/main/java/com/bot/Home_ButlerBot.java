package com.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Home_ButlerBot extends TelegramLongPollingBot {
    
    @Override
    public String getBotUsername() {
        return "@Home_ButlerBot";
    }

    @Override
    public String getBotToken() {
        return "6067257365:AAGhgJc6Oth6-bbGFKGQxXYnTyuwJt-34wk";
    }

    public void startSmokeDetector() {
        SmokeDetector smokeDetector = new SmokeDetector(this, "2137236837");
        smokeDetector.trigger();
    }

    public String pulsante = null;                                                      // Dichiarazione ed inizializzazione della variabile "pulsante" utilizzata per la gestione dei messaggi inviati dall'utente (per controllare se si tratta di un comando o un inserimento)

    @Override
    public void onUpdateReceived(Update update) {                                       // Metodo che viene richiamato ogni volta che il bot riceve un nuovo Update da Telegram (un messaggio o un callBack)

        String chatId = null;
        SendMessage sendMessage = new SendMessage();

        if (update.hasMessage()) {

            String msg = update.getMessage().getText();
            chatId = update.getMessage().getChatId().toString();

            if (pulsante != null)                                                       // Controlla se c'è un'operazione in corso (aggiunta, rimozione, ...)
            {
                Call chiama = new Call();
                pulsante = chiama.risposta(update, msg, sendMessage, chatId, pulsante);
            } else {
                Commands comandi = new Commands();
                pulsante = comandi.botCommands(msg, sendMessage, chatId);
            }

        } else if (update.hasCallbackQuery()) {

            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            chatId = callbackQuery.getMessage().getChatId().toString();

            Call chiama = new Call();
            pulsante = chiama.chiamata(update, data, sendMessage, chatId, pulsante);
        }

        if (chatId != null) {
            sendMessage.setChatId(chatId);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
