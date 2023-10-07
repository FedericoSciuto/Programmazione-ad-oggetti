package com.bot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class Query {

    public String getResult(String query, String colonna) {

        String risultato = "";

        try {
            Connection connection = Database_Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);                  // Preparazione dell'istruzione SQL contenuta nella variabile "query" per essere eseguita sul database
            ResultSet rs = ps.executeQuery();                                           // Esecuzione della query preparata in precedenza

            if (rs.next()) {                                                            // Controlla se il ResultSet contiene almeno una riga di dati
                risultato = rs.getString(colonna);                                      // Memorizzazione del dato ottenuto nella variabile "risultato"
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return risultato;
    }

    public boolean insertUpdateRemove(String query) {

        try {
            Connection connection = Database_Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            int rowsAffected = ps.executeUpdate();                                      // Esecuzione dell'operazione e memorizza nella variabile "rowsAffected" il numero di righe interessate dalla modifica
            return (rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public InlineKeyboardMarkup getPulsanti(String query, String colonna) {

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        int c = 0;

        try {
            Connection connection = Database_Connection.getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {                                                         // Finchè ci sono righe ottenute dalla query crea un pulsante
                String risultato = rs.getString(colonna);

                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(risultato);
                button.setCallbackData(risultato);

                if (c % 2 == 0) {                                                       // Ogni 2 pulsanti crea una nuova riga (per avere 2 pulsanti su ogni riga)
                    List<InlineKeyboardButton> row = new ArrayList<>();
                    row.add(button);
                    buttons.add(row);
                } else {
                    List<InlineKeyboardButton> lastRow = buttons.get(buttons.size() - 1);
                    lastRow.add(button);
                }
                c++;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        keyboard.setKeyboard(buttons);

        return keyboard;
    }
}
