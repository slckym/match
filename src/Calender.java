import javax.swing.*;

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
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        Helper.centreWindow(this);
    }

    public static void main(String[] args) {
        Calender dialog = new Calender();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
