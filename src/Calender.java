import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Calender extends JDialog {
    private JPanel contentPane;
    private JComboBox cmbTeamFirst;
    private JComboBox cmbTeamSecond;
    private JTable tblResults;
    private JButton btnFilter;
    private JLabel lblTeamFirst;
    private JLabel lblTeamSecond;
    private JButton buttonOK;

    private Calender() {
        initialize();
        try {
            Helper.fillCombobox(cmbTeamFirst);
            Helper.fillCombobox(cmbTeamSecond);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(100, 100, 620, 500);
        setTitle("Calender");
        Helper.centreWindow(this);
    }

    public static void main(String[] args) {
        Calender dialog = new Calender();
        dialog.setVisible(true);
        System.exit(0);
    }
}
