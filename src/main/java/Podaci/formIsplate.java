package Podaci;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import static Servis.DatabaseUtils.connectToDatabase;

public class formIsplate extends JDialog {
    private JPanel panel1;
    private JButton dodajButton;
    private JButton promijeniButton;
    private JButton brišiButton;
    private JButton btnCancel;
    private JTable tblIsplate;
    private Integer id;

    public formIsplate (JFrame parent) {
        super(parent);
        setTitle("Isplate");
        setContentPane(panel1);
        setMinimumSize(new Dimension(1200, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        connectToDatabase("isplate", tblIsplate);
        tblIsplate.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = tblIsplate.getSelectedRow();
                    if (selectedRow != -1) {
                        // Ovdje možete dohvatiti ID selektiranog retka
                        Object idObject = tblIsplate.getValueAt(selectedRow, 0);
                        id = Integer.parseInt(idObject.toString());
                    }
                }
            }
        });
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boxIsplate myForm = new boxIsplate(parent, "unos", 0);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                connectToDatabase("isplate", tblIsplate);
                setVisible(true);
                id = null;
            }
        });
        promijeniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boxIsplate myForm = new boxIsplate(parent, "izmjena", id);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                connectToDatabase("isplate", tblIsplate);
                setVisible(true);
                id = null;
            }
        });
        brišiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boxIsplate myForm = new boxIsplate(parent, "briši", id);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                connectToDatabase("isplate", tblIsplate);
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
        tblIsplate.getTableHeader().setReorderingAllowed(false);
        tblIsplate.getTableHeader().setResizingAllowed(true);
        tblIsplate.setAutoCreateColumnsFromModel(false);
        tblIsplate.setShowGrid(true);
        setVisible(true);
    }
    public static void main(String[] args) {
        formIsplate myFrom = new formIsplate(null);
    }
}
