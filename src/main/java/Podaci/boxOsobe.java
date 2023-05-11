package Podaci;

import Servis.DatabaseUtils;
import Servis.MaxLenght;


import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.sql.Connection;

import static Servis.DatabaseConnection.getConnection;

public class boxOsobe extends JDialog {
    private JTextField tfIme;
    private JTextField tfPrezime;
    private JTextField tfOIB;
    private JTextField tfIBAN;
    private JTextField tfSifraPrebivalista;
    private JTextField tfOznakaIsplate;
    private JButton btnInsert;
    private JButton btnCancel;
    private JPanel insertOsobePanel;
    private String glUnos_izmjena;
    private Integer glID;

    public boxOsobe(JFrame parent, String unos_izmjena, Integer id) {
        super(parent);
        glUnos_izmjena = unos_izmjena;
        glID = id;
        setTitle("Osobe");
        setContentPane(insertOsobePanel);
        setMinimumSize(new Dimension(470, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnInsert.addActionListener(e -> constructOsoba());
        btnCancel.addActionListener(e -> dispose());

        if (Objects.equals(glUnos_izmjena, "unos")) {
            postaviOgranicenje();
            setVisible(true);
        }
        else if (Objects.equals(glUnos_izmjena, "izmjena")) {
            postaviOgranicenje();
            boolean isTrue = fillFieldsWithUserData();
            if (isTrue) {
                setVisible(true);
            } else {
                dispose();
            }
        } else if (Objects.equals(glUnos_izmjena, "briši")) {
            if (glID != null) {
                int choice = JOptionPane.showConfirmDialog(parent, "Jeste sigurni?", "Brisanje Osoba", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    boolean tempSuccess = deleteOsoba();
                    if (tempSuccess) {
                        dispose();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,  "Morate selektirati redak u tablici","Greška", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        }
    }
    public Osobe osoba;
    private void constructOsoba() {
        String ime = tfIme.getText();
        String prezime = tfPrezime.getText();
        String oib = tfOIB.getText();
        String iban = tfIBAN.getText();
        String sifra_prebivalista = tfSifraPrebivalista.getText();
        String oznaka_isplate = tfOznakaIsplate.getText();

        if (ime.isEmpty() || prezime.isEmpty() || oib.isEmpty() || iban.isEmpty() || sifra_prebivalista.isEmpty() || oznaka_isplate.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Popunite sva polja!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Objects.equals(glUnos_izmjena, "unos")) {
            osoba = insertUpdateOsoba(ime, prezime, oib, iban, sifra_prebivalista, oznaka_isplate, true);
        } else if (Objects.equals(glUnos_izmjena, "izmjena")) {
            osoba = insertUpdateOsoba(ime, prezime, oib, iban, sifra_prebivalista, oznaka_isplate, false);
        } else if (Objects.equals(glUnos_izmjena, "briši")) {
            deleteOsoba();
        }
        if (osoba != null) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Neuspjeli unos osoba u bazu podataka!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private Osobe insertUpdateOsoba(String ime, String prezime, String oib, String iban, String sifra_prebivalista, String oznaka_isplate, boolean isInsert) {
        Osobe osoba;
        osoba = new Osobe();
        osoba.ime = ime;
        osoba.prezime = prezime;
        osoba.oib = oib;
        osoba.iban = iban;
        osoba.sifra_prebivalista = sifra_prebivalista;
        osoba.oznaka_isplate = oznaka_isplate;

        if (isInsert) {
            DatabaseUtils.insertIntoTable( "osobe", osoba);
        } else {
            DatabaseUtils.updateTable( "osobe", osoba,"id_osobe", glID);
        }
        return osoba;
    }
    private boolean deleteOsoba() {
        return DatabaseUtils.deleteRow("osobe", "id_osobe", glID);
    }
    private boolean fillFieldsWithUserData() {
        boolean moze_dalje = true;
        if (glID != null) {
            try {
                ResultSet rs = DatabaseUtils.selectFromTable("osobe", "id_osobe", glID);
                if (rs.next()) {
                    tfIme.setText(rs.getString("ime"));
                    tfPrezime.setText(rs.getString("prezime"));
                    tfOIB.setText(rs.getString("oib"));
                    tfIBAN.setText(rs.getString("iban"));
                    tfSifraPrebivalista.setText(rs.getString("sifra_prebivalista"));
                    tfOznakaIsplate.setText(rs.getString("oznaka_isplate"));
                }
            } catch(SQLException ex){
                JOptionPane.showMessageDialog(this,
                        "Greška prilikom čitanja podataka iz baze podataka!",
                        "Pokušajte ponovno!",
                        JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this,  "Morate selektirati redak u tablici","Greška", JOptionPane.ERROR_MESSAGE);
            dispose();
            moze_dalje = false;
        }
        return moze_dalje;
    }
    private void postaviOgranicenje() {
        MaxLenght maxLengthService = new MaxLenght();
        PlainDocument doc1 = maxLengthService.maxLenght(11);
        tfOIB.setDocument(doc1);
        PlainDocument doc2 = maxLengthService.maxLenght(21);
        tfIBAN.setDocument(doc2);
        PlainDocument doc3 = maxLengthService.maxLenght(5);
        tfSifraPrebivalista.setDocument(doc3);
        PlainDocument doc4 = maxLengthService.maxLenght(1);
        tfOznakaIsplate.setDocument(doc4);
    }
    public static void main(String[] args) {
        boxOsobe myform = new boxOsobe(null, "unos", 0);
    }
}
