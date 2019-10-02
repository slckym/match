import javafx.scene.control.DatePicker;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Addition extends JDialog {
    private JPanel pnlAddition;
    private JComboBox cmbTeamFirst;
    private JComboBox cmbTeamSecond;
    private JButton btnSave;
    private JTextField txtFPTeamFirst;
    private JTextField txtFPTeamSecond;
    private JTextField txtSPTeamFirst;
    private JTextField txtSPTeamSecond;
    private JTextField txtMatchDate;

    public Addition() {
        initialize();
        try {
            Helper.fillCombobox(cmbTeamFirst);
            Helper.fillCombobox(cmbTeamSecond);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        setContentPane(pnlAddition);
        setModal(true);
        setTitle("Match Addition");
        setBounds(100, 100, 500, 300);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        pnlAddition.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        Helper.centreWindow(this);
    }

    private void onCancel() {
        setVisible(false);
        Main.visible(true);
    }

    public static void main(String[] args) {
        Addition.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Addition dialog = new Addition();
        dialog.setVisible(b);
        System.exit(0);
    }
}
