package Podaci;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Servis.DatabaseUtils.connectToDatabase;

public class formVrstaIsplate extends JDialog {
    private JPanel prikazVrstaIsplatePanel;
    private JButton btnInsert;
    private JButton btnUpdate;
    private JTable tblVrsteIsplata;
    private JButton btnDrop;
    private JButton btnCancel;
    private Integer id;

    public formVrstaIsplate(JFrame parent) {
        super(parent);
        setTitle("Vrsta Isplate");
        setContentPane(prikazVrstaIsplatePanel);
        setMinimumSize(new Dimension(1200, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        connectToDatabase("vrsta_isplate", tblVrsteIsplata);
        tblVrsteIsplata.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = tblVrsteIsplata.getSelectedRow();
                    if (selectedRow != -1) {
                        // Ovdje možete dohvatiti ID selektiranog retka
                        Object idObject = tblVrsteIsplata.getValueAt(selectedRow, 0);
                        id = Integer.parseInt(idObject.toString());
                    }
                }
            }
        });
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxVrstaIsplate myForm = new boxVrstaIsplate(parent, "unos", 0);
                connectToDatabase("vrsta_isplate", tblVrsteIsplata);
                setVisible(true);
                id = null;
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxVrstaIsplate myForm = new boxVrstaIsplate(parent, "izmjena", id);
                connectToDatabase("vrsta_isplate", tblVrsteIsplata);
                setVisible(true);
                id = null;
            }
        });
        btnDrop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxVrstaIsplate myForm = new boxVrstaIsplate(parent, "briši", id);
                connectToDatabase("vrsta_isplate", tblVrsteIsplata);
                setVisible(true);
                id = null;
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        tblVrsteIsplata.getTableHeader().setReorderingAllowed(false);
        tblVrsteIsplata.getTableHeader().setResizingAllowed(true);
        tblVrsteIsplata.setAutoCreateColumnsFromModel(false);
        tblVrsteIsplata.setShowGrid(true);

        setVisible(true);
    }
    public static void main(String[] args) {
        formVrstaIsplate myFrom = new formVrstaIsplate(null);
    }
}
