package com.bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class Buttons {

    public void twoButtons(SendMessage sendMessage, String pulsante1a, String pulsante1b, String pulsante2a, String pulsante2b) {       // Metodo per la creazione di due pulsanti

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button1.setText(pulsante1a);
        button2.setText(pulsante2a);
        button1.setCallbackData(pulsante1b);
        button2.setCallbackData(pulsante2b);
        row.add(button1);
        row.add(button2);
        buttons.add(row);
        keyboard.setKeyboard(buttons);
        sendMessage.setReplyMarkup(keyboard);
    }

    public void tipoButtons(SendMessage sendMessage) {                                  // Metodo per la creazione di pulsanti per la scelta del tipo di dispositivo da gestire

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        InlineKeyboardButton button3 = new InlineKeyboardButton();
        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button1.setText("Elettrodomestici");
        button2.setText("Finestre");
        button3.setText("Luci");
        button4.setText("Riscaldamenti");
        button1.setCallbackData("Elettrodomestici");
        button2.setCallbackData("Finestre");
        button3.setCallbackData("Luci");
        button4.setCallbackData("Riscaldamenti");
        row1.add(button1);
        row1.add(button2);
        row2.add(button3);
        row2.add(button4);
        buttons.add(row1);
        buttons.add(row2);
        keyboard.setKeyboard(buttons);
        sendMessage.setReplyMarkup(keyboard);
    }
}
