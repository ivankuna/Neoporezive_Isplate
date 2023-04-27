package SQLiteWork;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static Servis.DatabaseConnection.getConnection;
public class CreateUsers {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            System.out.println("Baza podataka uspješno otvorena.");

            statement = connection.createStatement();
            String sql = "CREATE TABLE users " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT, " +
                    " email TEXT, " +
                    " password TEXT, " +
                    " phone TEXT, " +
                    " address TEXT)";
            statement.executeUpdate(sql);
            System.out.println("Tablica users uspješno kreirana u bazi podataka.");

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
