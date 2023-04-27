package Podaci;

import Servis.DatabaseUtils;
import Servis.MaxLenght;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static Servis.DateUtils.*;

public class boxIsplate extends JDialog {
    private JPanel isplatePanel;
    private JTextField tfDatumIsplate;
    private JTextField tfIznosIsplate;
    private JTextField tfRazdobljeOd;
    private JTextField tfRazdobljeDo;
    private JTextField tfNazivVrsteIsplate;
    private JTextField tfSifraMjestaRada;
    private JTextField tfOznakaOsiguranika;
    private JTextField tfOznakaPrimitka;
    private JTextField tfOznakaNeoporezivogPrimitka;
    private JButton btnOdabirVrsteIsplate;
    private JTextField tfPrezime;
    private JTextField tfIme;
    private JTextField tfOIB;
    private JTextField tfIBAN;
    private JTextField tfSifraPrebivalista;
    private JTextField tfOznakaIsplate;
    private JButton btnOdabirOsobe;
    private JButton btnSpremi;
    private JButton btnOdustani;
    private Integer glIDosobe;
    private Integer glIDvrstaIsplate;
    private String glUnos_izmjena;
    private Integer glID;

    public boxIsplate(JFrame parent, String unos_izmjena, Integer id) throws ParseException {
        super(parent);
        glUnos_izmjena = unos_izmjena;
        glID = id;
        setTitle("Isplate");
        setContentPane(isplatePanel);
        setMinimumSize(new Dimension(490, 570));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date currentDate = new Date();
        String currentDateString = sdf.format(currentDate);
        if (Objects.equals(glUnos_izmjena, "unos")) {
            tfDatumIsplate.setText(currentDateString);
            tfRazdobljeOd.setText(getFirstDayOfMonth(currentDateString));
            tfRazdobljeDo.setText(getLastDayOfMonth(currentDateString));
        }

        tfDatumIsplate.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDateFields();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateDateFields();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateDateFields();
            }
            private void updateDateFields() {
                // Get the date string from tfDatumIsplate
                String dateString = tfDatumIsplate.getText();

                if (dateString.isEmpty()) {
                    // If the date string is empty, clear the other text fields
                    tfRazdobljeOd.setText("");
                    tfRazdobljeDo.setText("");
                } else {
                    try {
                        // Set the text fields with the first and last days of the month
                        tfRazdobljeOd.setText(getFirstDayOfMonth(dateString));
                        tfRazdobljeDo.setText(getLastDayOfMonth(dateString));
                    } catch (ParseException ex) {
                        // If the entered date is not in the correct format, do nothing
                    }
                }
            }
        });
        btnOdabirVrsteIsplate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pickVrstuIsplate myForm = new pickVrstuIsplate(parent);
                glIDvrstaIsplate = myForm.getIdVrsteIsplate();
                fillVrstaIsplateFieldsWithUserData();
            }
        });
        btnOdabirOsobe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pickOsobu myForm = new pickOsobu(parent);
                glIDosobe = myForm.getIdOsobe();
                fillOsobeFieldsWithUserData();
            }
        });
        btnSpremi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    constructIsplata();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnOdustani.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
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
                int choice = JOptionPane.showConfirmDialog(parent, "Jeste sigurni?", "Brisanje Isplata", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    boolean tempSuccess = deleteIsplata();
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
    public Isplate isplata;
    private void constructIsplata() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String datumIsplateTemp = tfDatumIsplate.getText();
        Date datum_isplate = dateFormat.parse(datumIsplateTemp);
        String razdobljeOdTemp = tfRazdobljeOd.getText();
        Date razdoblje_od = dateFormat.parse(razdobljeOdTemp);
        String razdobljeDoTemp = tfRazdobljeDo.getText();
        Date razdoblje_do = dateFormat.parse(razdobljeDoTemp);
        String iznosIsplateTemp = tfIznosIsplate.getText();
        BigDecimal iznos_isplate = new BigDecimal(iznosIsplateTemp);
        String ime = tfIme.getText();
        String prezime = tfPrezime.getText();
        String oib = tfOIB.getText();
        String iban = tfIBAN.getText();
        String sifra_prebivalista = tfSifraPrebivalista.getText();
        String oznaka_isplate = tfOznakaIsplate.getText();
        String naziv_vrste_isplate = tfNazivVrsteIsplate.getText();
        String sifra_mjesta_rada = tfSifraMjestaRada.getText();
        String oznaka_osiguranika = tfOznakaOsiguranika.getText();
        String oznaka_primitka = tfOznakaPrimitka.getText();
        String oznaka_neoporezivog_primitka = tfOznakaNeoporezivogPrimitka.getText();

        if (datumIsplateTemp.isEmpty() || razdobljeOdTemp.isEmpty() || razdobljeDoTemp.isEmpty() || iznosIsplateTemp.isEmpty() || ime.isEmpty() ||
                prezime.isEmpty() || oib.isEmpty() || iban.isEmpty() || sifra_prebivalista.isEmpty() || oznaka_isplate.isEmpty() || naziv_vrste_isplate.isEmpty() ||
                sifra_mjesta_rada.isEmpty() || oznaka_osiguranika.isEmpty() || oznaka_primitka.isEmpty() || oznaka_neoporezivog_primitka.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Popunite sva polja!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (Objects.equals(glUnos_izmjena, "unos")) {
            isplata = insertUpdateIsplata(glIDosobe, glIDvrstaIsplate, datum_isplate, razdoblje_od, razdoblje_do, iznos_isplate,
                    ime, prezime, oib, iban, sifra_prebivalista, oznaka_isplate, naziv_vrste_isplate,
                    sifra_mjesta_rada, oznaka_osiguranika, oznaka_primitka, oznaka_neoporezivog_primitka, true);
        } else if (Objects.equals(glUnos_izmjena, "izmjena")) {
            isplata = insertUpdateIsplata(glIDosobe, glIDvrstaIsplate, datum_isplate, razdoblje_od, razdoblje_do, iznos_isplate, ime, prezime, oib, iban, sifra_prebivalista,
                    oznaka_isplate, naziv_vrste_isplate, sifra_mjesta_rada, oznaka_osiguranika, oznaka_primitka, oznaka_neoporezivog_primitka, false);
        } else if (Objects.equals(glUnos_izmjena, "briši")) {
            deleteIsplata();
        }
        if (isplata != null) {
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Neuspjeli unos isplate u bazu podataka!",
                    "Pokušajte ponovno!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private Isplate insertUpdateIsplata(Integer glIDosobe, Integer glIDvrstaIsplate, Date datum_isplate, Date razdoblje_od, Date razdoblje_do, BigDecimal iznos_isplate,
                                  String ime, String prezime, String oib, String iban, String sifra_prebivalista, String oznaka_isplate, String naziv_vrste_isplate,
                                  String sifra_mjesta_rada, String oznaka_osiguranika, String oznaka_primitka, String oznaka_neoporezivog_primitka, boolean isInsert) {
        Isplate isplata;
        isplata = new Isplate();
        isplata.id_osobe = glIDosobe;
        isplata.id_vrsta_isplate = glIDvrstaIsplate;
        isplata.datum_isplate = datum_isplate;
        isplata.razdoblje_od = razdoblje_od;
        isplata.razdoblje_do = razdoblje_do;
        isplata.iznos_isplate = iznos_isplate;
        isplata.ime = ime;
        isplata.prezime = prezime;
        isplata.oib = oib;
        isplata.iban = iban;
        isplata.sifra_prebivalista = sifra_prebivalista;
        isplata.oznaka_isplate = oznaka_isplate;
        isplata.naziv_vrste_isplate = naziv_vrste_isplate;
        isplata.sifra_mjesta_rada = sifra_mjesta_rada;
        isplata.oznaka_osiguranika = oznaka_osiguranika;
        isplata.oznaka_primitka = oznaka_primitka;
        isplata.oznaka_neoporezivog_primitka = oznaka_neoporezivog_primitka;

        if (isInsert) {
            DatabaseUtils.insertIntoTable( "isplate", isplata);
        } else {
            DatabaseUtils.updateTable( "isplate", isplata,"id_isplate", glID);
        }
        return isplata;
    }
    private boolean deleteIsplata() {
        return DatabaseUtils.deleteRow("isplate", "id_isplate", glID);
    }
    private void fillOsobeFieldsWithUserData() {
        if (glIDosobe != null) {
            try {
                ResultSet rs = DatabaseUtils.selectFromTable("osobe", "id_osobe", glIDosobe);
                if (rs.next()) {
                    glIDosobe = rs.getInt("id_osobe");
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
            dispose();
        }
    }
    private void fillVrstaIsplateFieldsWithUserData() {
        if (glIDvrstaIsplate != null) {
            try {
                ResultSet rs = DatabaseUtils.selectFromTable("vrsta_isplate", "id_vrsta_isplate", glIDvrstaIsplate);
                if (rs.next()) {
                    glIDvrstaIsplate = rs.getInt("id_vrsta_isplate");
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
            dispose();
        }
    }
    private boolean fillFieldsWithUserData() {
        boolean moze_dalje = true;
        if (glID != null) {
            try {
                ResultSet rs = DatabaseUtils.selectFromTable("isplate", "id_isplate", glID);
                if (rs.next()) {
                    glIDosobe = rs.getInt("id_osobe");
                    glIDvrstaIsplate = rs.getInt("id_vrsta_isplate");
                    tfDatumIsplate.setText(dateCroFormatting(rs.getString("datum_isplate")));
                    tfRazdobljeOd.setText(dateCroFormatting(rs.getString("razdoblje_od")));
                    tfRazdobljeDo.setText(dateCroFormatting(rs.getString("razdoblje_do")));
                    tfIznosIsplate.setText(rs.getString("iznos_isplate"));
                    tfIme.setText(rs.getString("ime"));
                    tfPrezime.setText(rs.getString("prezime"));
                    tfOIB.setText(rs.getString("oib"));
                    tfIBAN.setText(rs.getString("iban"));
                    tfSifraPrebivalista.setText(rs.getString("sifra_prebivalista"));
                    tfOznakaIsplate.setText(rs.getString("oznaka_isplate"));
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
        PlainDocument doc1 = maxLengthService.maxLenght(11);
        tfOIB.setDocument(doc1);
        PlainDocument doc2 = maxLengthService.maxLenght(21);
        tfIBAN.setDocument(doc2);
        PlainDocument doc3 = maxLengthService.maxLenght(5);
        tfSifraPrebivalista.setDocument(doc3);
        PlainDocument doc4 = maxLengthService.maxLenght(1);
        tfOznakaIsplate.setDocument(doc4);
    }
    public static void main(String[] args) throws ParseException {
        boxIsplate myForm = new boxIsplate(null, "", 0);
    }
}
