package Servis;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;

import static Servis.DatabaseConnection.getConnection;

public class XMLToMySQL {


    public static void KreirajTablicuIzXML(String filePath, String table_name) throws ParserConfigurationException {
        // Putanja do XML datoteke
//        String filePath = "xml/IvanObrazac.xml";
        try {
            // Stvaranje objekta DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parsiranje XML datoteke
            Document document = builder.parse(filePath);

            // Dobivanje korijenskog elementa
            Element root = document.getDocumentElement();

            // Dobivanje svih djece korijenskog elementa
            NodeList nodeList = root.getChildNodes();

            // Uspostavljanje veze s MySQL bazom podataka
            Connection connection = getConnection();

            // Kreiranje SQL upita za stvaranje tablice
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + table_name + " (";
            Element firstElement = (Element) nodeList.item(0);
            NodeList columnList = firstElement.getChildNodes();
            for (int i = 0; i < columnList.getLength(); i++) {
                Node columnNode = columnList.item(i);
                if (columnNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element columnElement = (Element) columnNode;
                    String columnName = columnElement.getNodeName();
                    createTableQuery += columnName + " VARCHAR(255), ";
                }
            }
            createTableQuery = createTableQuery.substring(0, createTableQuery.length() - 2); // Uklanjanje zadnje zareze i razmaka
            createTableQuery += ")";

            // Izvršavanje SQL upita za stvaranje tablice
            PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
            createTableStatement.executeUpdate();

            // Kreiranje SQL upita za umetanje podataka
            String insertQuery = "INSERT INTO " + table_name + " VALUES (";
            for (int i = 0; i < columnList.getLength(); i++) {
                insertQuery += "?, ";
            }
            insertQuery = insertQuery.substring(0, insertQuery.length() - 2); // Uklanjanje zadnje zareze i razmaka
            insertQuery += ")";

            // Popunjavanje tablice podacima iz XML-a
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Izvršavanje SQL upita za umetanje podataka
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    for (int j = 0; j < columnList.getLength(); j++) {
                        String columnName = columnList.item(j).getNodeName();
                        String columnValue = element.getElementsByTagName(columnName).item(0).getTextContent();
                        insertStatement.setString(j + 1, columnValue);
                    }
                    insertStatement.executeUpdate();
                }
            }

            // Zatvaranje veze s bazom podataka
            connection.close();
            System.out.println("Podaci su uspješno uneseni u MySQL tablicu.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ParserConfigurationException {
        KreirajTablicuIzXML("xml/IvanObrazac.xml","joppd_primatelji");
    }
}
