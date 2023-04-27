package Authorisation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import static Servis.DatabaseConnection.getConnection;

public class boxLogin extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;
    private JButton btnRegister;
    public boolean isConfirm() {
        return confirm;
    }
    private boolean confirm = false;

    public boxLogin(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                user = getAuthenticatedUser(email, password);

                if (user != null) {
                    confirm = true;
                    dispose();
                } else {
                    confirm = false;
                    JOptionPane.showMessageDialog(boxLogin.this,
                            "Email ili lozinka su netočni!",
                            "Pokušajte ponovno!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxRegistration registrationForm = new boxRegistration(parent);
                User user = registrationForm.user;

                if (user != null) {
                    JOptionPane.showMessageDialog(parent,
                            "Novi korisnik: " + user.name,
                            "Uspješna registracija!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        setVisible(true);
    }
    public User user;
    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        try{
            Connection connection = getConnection();

            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }
            stmt.close();
            connection.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public static void main(String[] args) {
        boxLogin myForm = new boxLogin(null);
    }
}




