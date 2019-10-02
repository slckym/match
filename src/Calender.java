import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class Calender extends JFrame {
    private JPanel pnlCalender;
    private JComboBox cmbTeamFirst;
    private JComboBox cmbTeamSecond;
    private JTable tblResults;
    private JButton btnFilter;
    private JLabel lblTeamFirst;
    private JLabel lblTeamSecond;
    private JScrollPane scrollResultTable;

    private Calender() {
        fillResultTable();
        initialize();
        try {
            Helper.fillCombobox(cmbTeamFirst);
            Helper.fillCombobox(cmbTeamSecond);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Calender.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Calender calender = new Calender();
        calender.setVisible(b);
    }

    private void fillResultTable() {


    }

    private void initialize() {
        setContentPane(pnlCalender);
        setSize(620, 500);
        setTitle("Match Calender");
        setResizable(false);
        Helper.centreWindow(this);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        pnlCalender.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onCancel() {
        setVisible(false);
    }

}
