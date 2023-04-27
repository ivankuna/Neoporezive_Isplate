package Podaci;

import Servis.MaxLenght;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Servis.FileUtils.*;

public class boxVlasnik extends  JDialog {
    private JPanel vlasnikPanel;
    private JTextField tfOIB;
    private JTextField tfNaziv;
    private JTextField tfGrad;
    private JTextField tfUlica;
    private JTextField tfBroj;
    private JTextField tfMail;
    private JTextField tfOznaka;
    private JButton btnOK;
    private JButton btnCancel;
    private JTextField tfIBAN;

    public boxVlasnik(JFrame parent) throws IOException {
        super(parent);
        setTitle("Vlasnik");
        setContentPane(vlasnikPanel);
        setMinimumSize(new Dimension(470, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        postaviOgranicenje();
        fillFieldsWithData();
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTxt("dat/vlasnik.txt");
                storeDataInVlasnikTxt();
                dispose();
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
    private void fillFieldsWithData() throws IOException {
        JTextField[] tfArray = {tfOIB, tfIBAN, tfNaziv, tfGrad, tfUlica, tfBroj, tfMail, tfOznaka};
        String[] podaciVlasnika = readTxt("dat/vlasnik.txt", 8);

        for (int i = 0; i < podaciVlasnika.length; i++) {
            tfArray[i].setText(podaciVlasnika[i]);
        }
    }
    private void storeDataInVlasnikTxt() {
        JTextField[] tfArray = {tfOIB, tfIBAN, tfNaziv, tfGrad, tfUlica, tfBroj, tfMail, tfOznaka};
        String[] podaciVlasnika = new String[8];

        for (int i = 0; i < podaciVlasnika.length; i++) {
            podaciVlasnika[i] = tfArray[i].getText();
        }
        writeToTxt(podaciVlasnika, "dat/vlasnik.txt");
    }
    private void postaviOgranicenje() {
        MaxLenght maxLengthService = new MaxLenght();
        PlainDocument doc1 = maxLengthService.maxLenght(11);
        tfOIB.setDocument(doc1);
        PlainDocument doc2 = maxLengthService.maxLenght(21);
        tfIBAN.setDocument(doc2);
        PlainDocument doc3 = maxLengthService.maxLenght(10);
        tfBroj.setDocument(doc3);
        PlainDocument doc4 = maxLengthService.maxLenght(1);
        tfOznaka.setDocument(doc4);
    }
    public static void main(String[] args) throws IOException {
        boxVlasnik myForm = new boxVlasnik(null);
    }
}
