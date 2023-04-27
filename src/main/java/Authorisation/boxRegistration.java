package Authorisation;
import Servis.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import static Servis.DatabaseConnection.getConnection;

public class boxRegistration extends JDialog {
    private JTextField tfName;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JTextField tfAddress;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel registerPanel;

    public boxRegistration(JFrame parent) {
        super(parent);
        setTitle("Izrada korisničkog računa");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(470, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    public User user;
    private void registerUser() {
        String name = tfName.getText();
        String email = tfEmail.getText();
        String phone = tfPhone.getText();
        String address = tfAddress.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Popunite sva polja!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Potvrda lozinke je netočna!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean exists = emailExists(email);
        if (exists) {
            JOptionPane.showMessageDialog(this,
                    "Email adresa je već iskorištena!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
//        Slanje autentikacijskog koda:
        int kontrola = new RandomNumberGenerator().generateRandomNumber();

        SendMail.sendMail(email, kontrola);

        String kontrolaTemp = Integer.toString(kontrola);

        String odgovor = JOptionPane.showInputDialog(this, "Upišite kod");
        if (!odgovor.equals(kontrolaTemp)) {
            JOptionPane.showMessageDialog(this,
                    "Potvrda autentikacijskog broja je netočna!",
                    "Greška404",
                    JOptionPane.ERROR_MESSAGE);
            dispose();
        } else {
            user = addUserToDatabase(name, email, phone, address, password);
        }
        if (user != null) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Neuspjela registracija!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public static boolean emailExists(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exists = false;

        try {
            connection = getConnection();

            String sql = "SELECT email FROM users WHERE email = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exists = true;
            }
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return exists;
    }
    private User addUserToDatabase(String name, String email, String phone, String address, String password) {
        User user = null;
        user = new User();
        user.name = name;
        user.email = email;
        user.phone = phone;
        user.address = address;
        user.password = password;

        DatabaseUtils.insertIntoTable( "users", user);
        return user;
    }
    public static void main(String[] args) {
        boxRegistration myForm = new boxRegistration(null);
    }
}
