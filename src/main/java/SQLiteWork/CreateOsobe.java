package SQLiteWork;

import java.sql.*;
import static Servis.DatabaseConnection.getConnection;

public class CreateOsobe {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            System.out.println("Baza podataka uspješno otvorena.");

            statement = connection.createStatement();
            String sql = "CREATE TABLE osobe " +
                    "(id_osobe INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " ime TEXT, " +
                    " prezime TEXT, " +
                    " oib TEXT, " +
                    " iban TEXT, " +
                    " sifra_prebivalista TEXT, " +
                    " oznaka_isplate TEXT)";
            statement.executeUpdate(sql);
            System.out.println("Tablica osobe uspješno kreirana u bazi podataka.");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("Baza podataka uspješno zatvorena.");
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
