package MySQLWork;

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
            String sql = "CREATE TABLE `isplate` (\n" +
                    "  `id_isplate` int NOT NULL AUTO_INCREMENT,\n" +
                    "  `id_osobe` int DEFAULT NULL,\n" +
                    "  `id_vrsta_isplate` int DEFAULT NULL,\n" +
                    "  `datum_isplate` date DEFAULT NULL,\n" +
                    "  `razdoblje_od` date DEFAULT NULL,\n" +
                    "  `razdoblje_do` date DEFAULT NULL,\n" +
                    "  `iznos_isplate` decimal(13,2) DEFAULT NULL,\n" +
                    "  `ime` varchar(255) DEFAULT NULL,\n" +
                    "  `prezime` varchar(255) DEFAULT NULL,\n" +
                    "  `oib` varchar(11) DEFAULT NULL,\n" +
                    "  `iban` varchar(21) DEFAULT NULL,\n" +
                    "  `sifra_prebivalista` varchar(5) DEFAULT NULL,\n" +
                    "  `oznaka_isplate` varchar(1) DEFAULT NULL,\n" +
                    "  `naziv_vrste_isplate` varchar(255) DEFAULT NULL,\n" +
                    "  `sifra_mjesta_rada` varchar(5) DEFAULT NULL,\n" +
                    "  `oznaka_osiguranika` varchar(5) DEFAULT NULL,\n" +
                    "  `oznaka_primitka` varchar(5) DEFAULT NULL,\n" +
                    "  `oznaka_neoporezivog_primitka` varchar(2) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id_isplate`)\n" +
                    ")";
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
