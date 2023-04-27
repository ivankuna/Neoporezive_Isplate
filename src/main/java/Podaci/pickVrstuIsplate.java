package Podaci;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static Servis.DatabaseUtils.connectToDatabase;

public class pickVrstuIsplate extends JDialog{
    private JPanel pickVrstuIsplatePanel;
    private JButton btnOK;
    private JButton btnCancel;
    private JTable pickVrstuIsplateTable;
    private Integer idVrsteIsplate;
    public pickVrstuIsplate (JFrame parent) {
        super(parent);
        setTitle("Odabir vrste isplate");
        setContentPane(pickVrstuIsplatePanel);
        setMinimumSize(new Dimension(800, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        connectToDatabase("vrsta_isplate", pickVrstuIsplateTable);
        pickVrstuIsplateTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = pickVrstuIsplateTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Ovdje možete dohvatiti ID selektiranog retka
                        Object idObject = pickVrstuIsplateTable.getValueAt(selectedRow, 0);
                        idVrsteIsplate = Integer.parseInt(idObject.toString());
                    }
                }
            }
        });
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(idVrsteIsplate, null)) {
                    JOptionPane.showMessageDialog(parent,  "Morate selektirati redak u tablici","Greška", JOptionPane.ERROR_MESSAGE);
                } else {
                    dispose();
                }
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
    public Integer getIdVrsteIsplate() {
        return idVrsteIsplate;
    }
    public static void main(String[] args) {
        pickVrstuIsplate myForm = new pickVrstuIsplate(null);
    }
}
