package Authorisation;
import Servis.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

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

        boolean emailValidity = emailInputControl();

        if (emailValidity) {

            boolean exists = emailExists(email);
            if (exists) {
                JOptionPane.showMessageDialog(this,
                        "Email adresa je već iskorištena!",
                        "Pokušajte ponovno!",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
//        Slanje autorizacijskog koda:
            int kontrola = new RandomNumberGenerator().generateRandomNumber();

            SendMail.sendMail(email, kontrola);

            String kontrolaTemp = Integer.toString(kontrola);

            String odgovor = JOptionPane.showInputDialog(this, "Upišite kod");
            if (odgovor == null) {
                System.exit(0);
            } else if (!odgovor.equals(kontrolaTemp)) {
                JOptionPane.showMessageDialog(this,
                        "Potvrda autorizacijskog broja je netočna!",
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
    private boolean emailInputControl() {
        String email = tfEmail.getText();
        StringBuilder sb = new StringBuilder();
        char[] chars = email.toCharArray();
        int validityCounter;
        int atSymbolCounter = 0;
        boolean moze_dalje = true;

        String[] validChars = {
                ".", "-", "_", "@", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };

        for (char ch : chars) {
            if (!Character.isLetter(ch)) {
                validityCounter = 0;
                for (String str : validChars) {
                    if (Objects.equals(Character.toString(ch), str)) {
                        validityCounter += 1;
                    }
                }
                if (Objects.equals(Character.toString(ch), "@")) {
                    atSymbolCounter += 1;
                }
                if (validityCounter > 0) {
                    sb.append(ch);
                }
            } else {
                sb.append(ch);
            }
        }
        if (!Objects.equals(sb.toString(), email) || atSymbolCounter != 1) {
            JOptionPane.showMessageDialog(this,
                    "Neispravan unos Email adrese!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
            moze_dalje = false;
        }
        return moze_dalje;
    }
    public static void main(String[] args) {
        boxRegistration myForm = new boxRegistration(null);
    }
}
