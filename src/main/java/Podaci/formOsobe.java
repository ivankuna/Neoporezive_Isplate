package Podaci;
import Authorisation.boxDashboard;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Servis.DatabaseUtils.connectToDatabase;

public class formOsobe extends JDialog{
    private JPanel prikazOsobaPanel;
    private JButton dodajButton;
    private JButton brišiButton;
    private JButton promijeniButton;
    private JTable tblOsobe;
    private JButton btnCancel;
    private Integer id;

    public formOsobe(JFrame parent) {
        super(parent);
        setTitle("Osobe");
        setContentPane(prikazOsobaPanel);
        setMinimumSize(new Dimension(800, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        connectToDatabase("osobe", tblOsobe);
        tblOsobe.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = tblOsobe.getSelectedRow();
                    if (selectedRow != -1) {
                        // Ovdje možete dohvatiti ID selektiranog retka
                        Object idObject = tblOsobe.getValueAt(selectedRow, 0);
                        id = Integer.parseInt(idObject.toString());
                    }
                }
            }
        });
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxOsobe myForm = new boxOsobe(parent, "unos", 0);
                connectToDatabase("osobe", tblOsobe);
                setVisible(true);
                id = null;
            }
        });
        promijeniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxOsobe myForm = new boxOsobe(parent, "izmjena", id);
                connectToDatabase("osobe", tblOsobe);
                setVisible(true);
                id = null;
            }
        });
        brišiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boxOsobe myForm = new boxOsobe(parent, "briši", id);
                connectToDatabase("osobe", tblOsobe);
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
        tblOsobe.getTableHeader().setReorderingAllowed(false);
        tblOsobe.getTableHeader().setResizingAllowed(true);
        tblOsobe.setAutoCreateColumnsFromModel(false);
        tblOsobe.setShowGrid(true);
        setVisible(true);
    }
    public static void main(String[] args) {
        formOsobe myFrom = new formOsobe(null);
    }
}