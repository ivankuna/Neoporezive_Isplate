import Authorisation.boxDashboard;
import Authorisation.boxLogin;
import Authorisation.boxRegistration;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static Servis.DatabaseConnection.getConnection;

public class Glavna {
    private static boolean mozeDalje = false;
    private static String userName;
    public static void main(String[] args) {
        boolean hasRegistereddUsers = connectToDatabase();
        JFrame parent = new JFrame();

        while (true) {
            if (hasRegistereddUsers) {
                boxLogin loginForm = new boxLogin(parent);
                mozeDalje = loginForm.isConfirm();
                try {
                    userName = loginForm.user.getName();
                } catch (Exception e) {
                    System.exit(0);
                }
            } else {
                boxRegistration registrationForm = new boxRegistration(parent);
                hasRegistereddUsers = connectToDatabase();
                if (!hasRegistereddUsers) {
                    System.exit(0);
                }
            }
            if (mozeDalje) {
                boxDashboard myForm = new boxDashboard(parent, userName);
                break;
            }
        }
    }
    private static boolean connectToDatabase() {
        boolean hasRegistredUsers = false;

        try {
            //check if we have users in the table users
            Connection conn = getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM users");

            if (resultSet.next()) {
                int numUsers = resultSet.getInt(1);
                if (numUsers > 0) {
                    hasRegistredUsers = true;
                }
            }
            statement.close();
            conn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return hasRegistredUsers;
    }
}
