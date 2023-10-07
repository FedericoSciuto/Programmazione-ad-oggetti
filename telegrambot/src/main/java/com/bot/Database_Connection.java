package com.bot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database_Connection {
    
    private static Connection connection;                                               // Dichiarazione della variabile "connection" di tipo "Connection", usata per la memorizzazione della connessione
    
    private static Config config = new Config("src/main/java/com/bot/config.xml");
    
    public static Connection getConnection() {
        
        if (connection == null) {
            String host = config.get("host");
            String port = config.get("port");
            String dbName = config.get("name");
            String username = config.get("username");
            String password = config.get("password");
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");                    // Carica il driver JDBC di MySQL
                String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
                connection = DriverManager.getConnection(url, username, password);      // Effettua la connessione
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
