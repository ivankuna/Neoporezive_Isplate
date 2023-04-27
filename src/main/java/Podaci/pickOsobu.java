package Podaci;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static Servis.DatabaseUtils.connectToDatabase;

public class pickOsobu extends JDialog {
    private JPanel pickOsobuPanel;
    private JButton btnOK;
    private JButton btnCancel;
    private JTable pickOsobuTable;
    private Integer idOsobe;
    public pickOsobu (JFrame parent) {
        super(parent);
        setTitle("Odabir osobe");
        setContentPane(pickOsobuPanel);
        setMinimumSize(new Dimension(800, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        connectToDatabase("osobe", pickOsobuTable);
        pickOsobuTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = pickOsobuTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Ovdje možete dohvatiti ID selektiranog retka
                        Object idObject = pickOsobuTable.getValueAt(selectedRow, 0);
                        idOsobe = Integer.parseInt(idObject.toString());
                    }
                }
            }
        });
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(idOsobe, null)) {
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
    public Integer getIdOsobe() {
        return idOsobe;
    }
    public static void main(String[] args) {
        pickOsobu myform = new pickOsobu(null);
    }
}
