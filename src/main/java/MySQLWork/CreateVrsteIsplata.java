package MySQLWork;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static Servis.DatabaseConnection.getConnection;

public class CreateVrsteIsplata {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            System.out.println("Baza podataka uspješno otvorena.");

            statement = connection.createStatement();
            String sql = "CREATE TABLE `vrsta_isplate` (\n" +
                    "  `id_vrsta_isplate` int NOT NULL AUTO_INCREMENT,\n" +
                    "  `naziv_vrste_isplate` varchar(255) DEFAULT NULL,\n" +
                    "  `sifra_mjesta_rada` varchar(5) DEFAULT NULL,\n" +
                    "  `oznaka_osiguranika` varchar(5) DEFAULT NULL,\n" +
                    "  `oznaka_primitka` varchar(5) DEFAULT NULL,\n" +
                    "  `oznaka_neoporezivog_primitka` varchar(2) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id_vrsta_isplate`)\n" +
                    ")";
            statement.executeUpdate(sql);
            System.out.println("Tablica vrste_isplata uspješno kreirana u bazi podataka.");

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
