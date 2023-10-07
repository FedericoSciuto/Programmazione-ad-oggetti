package com.bot;

import java.util.Timer;
import java.util.TimerTask;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SmokeDetector {

    private final TelegramLongPollingBot bot;                                           // Dichiarazione delle variabili bot e chatId in modo che siano disponibili all'interno della classe (private) e che il loro valore non cambi dopo l'inizializzazione (final)
    private final String chatId;

    public SmokeDetector(TelegramLongPollingBot bot, String chatId) {
        this.bot = bot;                                                                 // Assegnazione dei valori alle variabili di istanza della classe "SmokeDetector" (this)
        this.chatId = chatId;
    }

    public void trigger() {
        Timer timer = new Timer();                                                      // Creazione di un nuovo oggetto Timer
        timer.schedule(new TimerTask() {                                                // Pianifica l'esecuzione dell'attività

            @Override
            public void run() {
                
                if (isSmokeDetected()) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("ALLARME!!! Fumo rilevato!");
                    try {
                        bot.execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 5000);                                                       // Lasso di tempo nel quale il "rilevatore" verifica la presenza di fumo (in questo caso ogni 5 secondi)
    }

    private boolean isSmokeDetected() {
        return Math.random() < 0.4;                                                     // Probabilità con la quale si triggererà il "rilevatore" (0.4 indica il 40% di probabilità)
    }
}
