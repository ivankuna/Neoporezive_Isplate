package Podaci;

import Servis.DatabaseUtils;
import Servis.MaxLenght;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static Servis.DatabaseConnection.getConnection;

public class boxVrstaIsplate extends JDialog{
    private JTextField tfNazivVrsteIsplate;
    private JTextField tfSifraMjestaRada;
    private JTextField tfOznakaOsiguranika;
    private JTextField tfOznakaPrimitka;
    private JTextField tfOznakaNeoporezivogPrimitka;
    private JButton btnInsert;
    private JButton btnCancel;
    private JPanel insertVrstaIsplatePanel;
    private String glUnos_izmjena;
    private Integer glID;

    public boxVrstaIsplate(JFrame parent, String unos_izmjena, Integer id) {
        super(parent);
        glUnos_izmjena = unos_izmjena;
        glID = id;
        setTitle("Vrste isplata");
        setContentPane(insertVrstaIsplatePanel);
        setMinimumSize(new Dimension(470, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnInsert.addActionListener(e -> constructVrstaIsplate());
        btnCancel.addActionListener(e -> dispose());

        if (Objects.equals(glUnos_izmjena, "unos")) {
            postaviOgranicenje();
            setVisible(true);
        } else if (Objects.equals(glUnos_izmjena, "izmjena")) {
            postaviOgranicenje();
            boolean isTrue = fillFieldsWithUserData();
            if (isTrue) {
                setVisible(true);
            } else {
                dispose();
            }
        } else if (Objects.equals(glUnos_izmjena, "briši")) {
            if (glID != null) {
                int choice = JOptionPane.showConfirmDialog(parent, "Jeste sigurni?", "Brisanje Vrsta Isplate", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    boolean tempSuccess = deleteVrstaIsplate();
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
    public VrstaIsplate vrstaIsplate;
    Connection conn = getConnection();
    private void constructVrstaIsplate() {
        String naziv_vrste_isplate = tfNazivVrsteIsplate.getText();
        String sifra_mjesta_rada = tfSifraMjestaRada.getText();
        String oznaka_osiguranika = tfOznakaOsiguranika.getText();
        String oznaka_primitka = tfOznakaPrimitka.getText();
        String oznaka_neoporezivog_primitka = tfOznakaNeoporezivogPrimitka.getText();

        if (naziv_vrste_isplate.isEmpty() || sifra_mjesta_rada.isEmpty() || oznaka_osiguranika.isEmpty() || oznaka_primitka.isEmpty() || oznaka_neoporezivog_primitka.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Popunite sva polja!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Objects.equals(glUnos_izmjena, "unos")) {
            vrstaIsplate = insertUpdateVrstaIsplate(naziv_vrste_isplate, sifra_mjesta_rada, oznaka_osiguranika, oznaka_primitka, oznaka_neoporezivog_primitka, true);
        } else if (Objects.equals(glUnos_izmjena, "izmjena")) {
            vrstaIsplate = insertUpdateVrstaIsplate(naziv_vrste_isplate, sifra_mjesta_rada, oznaka_osiguranika, oznaka_primitka, oznaka_neoporezivog_primitka, false);
        } else if (Objects.equals(glUnos_izmjena, "briši")) {
            deleteVrstaIsplate();
        }
        if (vrstaIsplate != null) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Neuspjeli unos vrste isplate u bazu podataka!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private VrstaIsplate insertUpdateVrstaIsplate(String naziv_vrste_isplate, String sifra_mjesta_rada, String oznaka_osiguranika, String oznaka_primitka, String oznaka_neoporezivog_primitka, boolean isInsert) {
        VrstaIsplate vrstaIsplate;
        vrstaIsplate = new VrstaIsplate();
        vrstaIsplate.naziv_vrste_isplate = naziv_vrste_isplate;
        vrstaIsplate.sifra_mjesta_rada = sifra_mjesta_rada;
        vrstaIsplate.oznaka_osiguranika = oznaka_osiguranika;
        vrstaIsplate.oznaka_primitka = oznaka_primitka;
        vrstaIsplate.oznaka_neoporezivog_primitka = oznaka_neoporezivog_primitka;
        if (isInsert) {
            DatabaseUtils.insertIntoTable( "vrsta_isplate", vrstaIsplate);
        } else {
            DatabaseUtils.updateTable( "vrsta_isplate", vrstaIsplate,"id_vrsta_isplate", glID);
        }
        return vrstaIsplate;
    }
    private boolean deleteVrstaIsplate() {
        return DatabaseUtils.deleteRow("vrsta_isplate", "id_vrsta_isplate", glID);
    }
    private boolean fillFieldsWithUserData() {
        boolean moze_dalje = true;
        if (glID != null) {
            try {
                ResultSet rs = DatabaseUtils.selectFromTable("vrsta_isplate", "id_vrsta_isplate", glID);
                if (rs.next()) {
                    tfNazivVrsteIsplate.setText(rs.getString("naziv_vrste_isplate"));
                    tfSifraMjestaRada.setText(rs.getString("sifra_mjesta_rada"));
                    tfOznakaOsiguranika.setText(rs.getString("oznaka_osiguranika"));
                    tfOznakaPrimitka.setText(rs.getString("oznaka_primitka"));
                    tfOznakaNeoporezivogPrimitka.setText(rs.getString("oznaka_neoporezivog_primitka"));
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
        PlainDocument doc1 = maxLengthService.maxLenght(5);
        tfSifraMjestaRada.setDocument(doc1);
        PlainDocument doc2 = maxLengthService.maxLenght(5);
        tfOznakaOsiguranika.setDocument(doc2);
        PlainDocument doc3 = maxLengthService.maxLenght(5);
        tfOznakaPrimitka.setDocument(doc3);
        PlainDocument doc4 = maxLengthService.maxLenght(2);
        tfOznakaNeoporezivogPrimitka.setDocument(doc4);
    }
    public static void main(String[] args) {
        boxVrstaIsplate myform = new boxVrstaIsplate(null, "unos", 0);
    }
}
