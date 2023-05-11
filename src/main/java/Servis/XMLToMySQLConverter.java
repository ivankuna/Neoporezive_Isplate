package Servis;

import java.io.File;
import java.sql.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import static Servis.DatabaseConnection.getConnection;

public class XMLToMySQLConverter {
    public static void main(String[] args) {
        // XML file path
        String xmlFilePath = "C:\\Users\\betas\\IdeaProjects\\Neoporezive_isplate\\xml\\IvanObrazac.xml";

        try {
            // Create database connection
            Connection connection = getConnection();

            // Create tables
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));
            document.getDocumentElement().normalize();
            Element root = document.getDocumentElement();
            createTables(connection, root);

            // Extract data from XML and insert into database tables
            extractDataAndInsert(connection, root);

            // Close database connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection connection, Element root) throws SQLException {
        Statement statement = connection.createStatement();
        NodeList nodeList = root.getElementsByTagName("P");

        if (nodeList.getLength() > 0) {
            Element sampleElement = (Element) nodeList.item(0);
            NodeList childNodes = sampleElement.getChildNodes();

            StringBuilder createTableQuery = new StringBuilder();
            createTableQuery.append("CREATE TABLE IF NOT EXISTS table_name (");

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String columnName = element.getTagName();
                    String columnDataType = "VARCHAR(255)";

                    // Determine appropriate data type based on the content
                    String columnValue = element.getTextContent();
                    if (columnValue.matches("-?\\d+")) {
                        columnDataType = "INT";
                    } else if (columnValue.matches("-?\\d+(\\.\\d+)?")) {
                        columnDataType = "DECIMAL(10,2)";
                    }

                    createTableQuery.append(columnName).append(" ").append(columnDataType);

                    if (i < childNodes.getLength() - 1) {
                        createTableQuery.append(", ");
                    }
                }
            }

            createTableQuery.append(")");

            statement.execute(createTableQuery.toString());
        }

        statement.close();
    }

    private static void extractDataAndInsert(Connection connection, Element root) throws SQLException {
        Statement statement = connection.createStatement();
        NodeList nodeList = root.getElementsByTagName("P");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                // Extract data from XML nodes
                StringBuilder values = new StringBuilder();
                NodeList childNodes = element.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        String value = childNode.getTextContent();
                        values.append("'").append(value).append("', ");
                    }
                }

                values.setLength(values.length() - 2); // Remove the trailing comma and space

                // Insert data into the table
                String insertQuery = "INSERT INTO table_name VALUES (" + values + ")";
                statement.executeUpdate(insertQuery);
            }
        }

        statement.close();
    }
}
