package SQLiteWork;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static Servis.DatabaseConnection.getConnection;
public class CreateIsplate {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            System.out.println("Baza podataka uspješno otvorena.");

            statement = connection.createStatement();
            String sql = "CREATE TABLE isplate " +
                    "(id_isplate INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " id_osobe INT, " +
                    " id_vrsta_isplate INT, " +
                    " datum_isplate TEXT, " +
                    " razdoblje_od TEXT, " +
                    " razdoblje_do TEXT, " +
                    " iznos_isplate DECIMAL(13, 2), " +
                    " ime TEXT, " +
                    " prezime TEXT, " +
                    " oib TEXT(11), " +
                    " iban TEXT(21), " +
                    " sifra_prebivalista TEXT(5), " +
                    " oznaka_isplate TEXT(1), " +
                    " naziv_vrste_isplate TEXT, " +
                    " sifra_mjesta_rada TEXT(5), " +
                    " oznaka_osiguranika TEXT(5), " +
                    " oznaka_primitka TEXT(5), " +
                    " oznaka_neoporezivog_primitka TEXT(2))";
            statement.executeUpdate(sql);
            System.out.println("Tablica isplate uspješno kreirana u bazi podataka.");

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


