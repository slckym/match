import javax.swing.*;
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
        initialize();
        fillResultTable();
        try {
            Helper.fillCombobox(cmbTeamFirst);
            Helper.fillCombobox(cmbTeamSecond);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("BoundFieldAssignment")
    private void fillResultTable() {
        String[][] data = {
                {"Kundan Kumar Jha", "4031", "CSE"},
                {"Anand Jha", "6014", "IT"}
        };
        String[] columns = {"Name", "Roll Number", "Department"};

        tblResults = new JTable(data, columns);
        tblResults.setFillsViewportHeight(true);
    }

    private void initialize() {
        setContentPane(pnlCalender);
        setBounds(100, 100, 620, 500);
        setTitle("Match Calender");
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

    public static void main(String[] args) {
        Calender.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Calender calender = new Calender();
        calender.setVisible(b);
    }

}
