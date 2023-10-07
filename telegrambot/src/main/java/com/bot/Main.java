package com.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    
    public static void main(String[] args) {

        try {
            Home_ButlerBot bot = new Home_ButlerBot();
            bot.startSmokeDetector();                                                   // Fa partire il metodo per triggerare il "sensore di fumo" così da inviare la notifica
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Home_ButlerBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
