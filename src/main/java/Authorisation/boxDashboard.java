package Authorisation;

import Podaci.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class boxDashboard extends JDialog {
    private JPanel dashboardPanel;
    private JButton btnIsplate;
    private JButton btnCancel;
    private JButton btnVrsteIsplata;
    private JButton btnOsobe;
    private JLabel lblUserName;
    private JButton podaciOVlasnikuButton;
    private static String glUserName;

    public boxDashboard(JFrame parent, String userName) {
        super(parent);
        glUserName = userName;
        setTitle("Neoporezive isplate");
        setContentPane(dashboardPanel);
        setMinimumSize(new Dimension(300, 450));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        lblUserName.setText(glUserName);

        btnOsobe.addActionListener(e -> {
            dispose();
            formOsobe osobeForm = new formOsobe(parent);
            boxDashboard myForm = new boxDashboard(parent, glUserName);
        });
        btnVrsteIsplata.addActionListener(e -> {
            dispose();
            formVrstaIsplate vrsteIsplataForm = new formVrstaIsplate(parent);
            boxDashboard myForm = new boxDashboard(parent, glUserName);
        });
        btnIsplate.addActionListener(e -> {
            dispose();
            formIsplate isplateForm = new formIsplate(parent);
            boxDashboard myForm = new boxDashboard(parent, glUserName);
        });
        btnCancel.addActionListener(e -> System.exit(0));
        podaciOVlasnikuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dispose();
                    boxVlasnik vlasnikForm = new boxVlasnik(parent);
                    boxDashboard myForm = new boxDashboard(parent, glUserName);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        setVisible(true);
    }
    public static void main(String[] args) {
        boxDashboard myForm = new boxDashboard(null, glUserName);
    }
}
