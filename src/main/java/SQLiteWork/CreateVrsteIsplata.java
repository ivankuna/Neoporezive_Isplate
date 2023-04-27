package SQLiteWork;

import java.sql.*;
import static Servis.DatabaseConnection.getConnection;

public class CreateVrsteIsplata {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            System.out.println("Baza podataka uspješno otvorena.");

            statement = connection.createStatement();
            String sql = "CREATE TABLE vrsta_isplate " +
                    "(id_vrsta_isplate INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " naziv_vrste_isplate TEXT, " +
                    " sifra_mjesta_rada TEXT, " +
                    " oznaka_osiguranika TEXT, " +
                    " oznaka_primitka TEXT, " +
                    " oznaka_neoporezivog_primitka TEXT)";
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
