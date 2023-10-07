package com.bot;
import org.w3c.dom.*;
import java.util.*;
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;

public class Config {
    
    private final Map<String, String> Mysql;

    public Config(String path) {
        
        Mysql=new HashMap<>();

        try{
            File parsingFile = new File(path);                                          // Per il parsing del file XML, DOM mette a disposizione una classe DocumentBuilder istanziabile tramite una DocumentBuilderFactory. Il DocumentBuilder effettua il parsing tramite il metodo parse().
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = dbf.newDocumentBuilder();
            Document doc = parser.parse(parsingFile);

            NodeList database = ((Element) doc
                .getElementsByTagName("config").item(0))
                .getElementsByTagName("db");
            Mysql.put("host", ((Element) database.item(0))
                .getElementsByTagName("host").item(0)
                .getTextContent());
            Mysql.put("port", ((Element) database.item(0))
                .getElementsByTagName("port").item(0)
                .getTextContent());
            Mysql.put("name", ((Element) database.item(0))
                .getElementsByTagName("name").item(0)
                .getTextContent());
            Mysql.put("password", ((Element) database.item(0))
                .getElementsByTagName("password").item(0)
                .getTextContent());
            Mysql.put("username", ((Element) database.item(0))
                .getElementsByTagName("username").item(0)
                .getTextContent());

        } catch (ParserConfigurationException | IOException | NullPointerException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) {                                                      // Restituisce il valore della chiave specificata
        return Mysql.get(key);
    }
}
