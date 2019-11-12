import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class Possibilities extends JFrame {
    private JPanel pnlPossibilities;
    private JComboBox cmbTeamFirst;
    private JComboBox cmbTeamSecond;
    private JButton btnResult;
    private JLabel lblCmbTeamSecond;
    private JLabel lblTeamFirst;
    private JLabel lbl15UpperDown;
    private JLabel lbl35UpperDown;
    private JTabbedPane tabResults;
    private JTable tblHistory;
    private JLabel lblOddOrEven;
    private JLabel lblTotalGoals;
    private JLabel lblReciprocal;
    private JLabel lblCmbTeamFirst;

    public Possibilities() {
        initialize();
        try {
            Helper.fillCombobox(cmbTeamFirst);
            Helper.fillCombobox(cmbTeamSecond);
            cmbTeamSecond.setSelectedIndex(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnResult.addActionListener(e -> {
            try {
                validateMatchAddition();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        Possibilities.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Possibilities dialog = new Possibilities();
        dialog.setVisible(b);
    }

    private void validateMatchAddition() {

        if (cmbTeamFirst.getSelectedIndex() == cmbTeamSecond.getSelectedIndex()) {
            Helper.showDialog("Select different team");
            lblCmbTeamSecond.setForeground(Color.red);
            lblCmbTeamFirst.setForeground(Color.red);
            cmbTeamSecond.requestFocus();
        }

    }

    private void initialize() {
        setContentPane(pnlPossibilities);
        setTitle("Possibilities");
        setBounds(100, 100, 500, 320);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        pnlPossibilities.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        Helper.centreWindow(this);
    }

    private void onCancel() {
        setVisible(false);
    }

}
