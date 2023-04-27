package SQLiteWork;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static Servis.DatabaseConnection.getConnection;
public class CreateDatabase {
    public static void createSQLiteDatabase() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            System.out.println("Database created successfully");
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        createSQLiteDatabase();
    }
}
