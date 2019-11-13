import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class Addition extends JFrame {
    private JPanel pnlAddition;
    private JComboBox cmbTeamFirst;
    private JComboBox cmbTeamSecond;
    private JButton btnSave;
    private JTextField txtFPTeamFirst;
    private JTextField txtFPTeamSecond;
    private JTextField txtSPTeamFirst;
    private JTextField txtSPTeamSecond;
    private JLabel lblCmbTeamFirst;
    private JLabel lblCmbTeamSecond;
    private JLabel lblFPMS;
    private JLabel lblSPMS;
    private JLabel lblMatchDate;
    private DateTimePicker dtpMatchDate;

    public Addition() {
        initialize();
        try {
            Helper.fillCombobox(cmbTeamFirst);
            Helper.fillCombobox(cmbTeamSecond);
            cmbTeamSecond.setSelectedIndex(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnSave.addActionListener(e -> {
            try {
                validateMatchAddition();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        Addition.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Addition dialog = new Addition();
        dialog.setVisible(b);
    }

    private void initialize() {
        setContentPane(pnlAddition);
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
    }

    private void validateMatchAddition() {

        if (cmbTeamFirst.getSelectedIndex() == cmbTeamSecond.getSelectedIndex()) {
            Helper.showDialog("Select different team");
            lblCmbTeamSecond.setForeground(Color.red);
            lblCmbTeamFirst.setForeground(Color.red);
            cmbTeamSecond.requestFocus();
        } else if (txtFPTeamFirst.getText().isEmpty() || txtFPTeamSecond.getText().isEmpty()) {
            Helper.showDialog("First period score is required");
            lblFPMS.setForeground(Color.red);
            txtFPTeamFirst.requestFocus();
        } else if (txtSPTeamFirst.getText().isEmpty() || txtSPTeamSecond.getText().isEmpty()) {
            Helper.showDialog("Second period score is required");
            lblSPMS.setForeground(Color.red);
            txtSPTeamFirst.requestFocus();
        } else if (dtpMatchDate.getDatePicker().getText().isEmpty()) {
            Helper.showDialog("Match date is required");
            lblMatchDate.setForeground(Color.red);
            dtpMatchDate.getDatePicker().requestFocus();
        } else if (dtpMatchDate.getTimePicker().getText().isEmpty()) {
            Helper.showDialog("Match time is required");
            lblMatchDate.setForeground(Color.red);
            dtpMatchDate.getTimePicker().requestFocus();

        } else {
            insertMatchAddition();
        }
    }

    private void insertMatchAddition() {
        PreparedStatement state = null;
        int rs = 0;
        try {
            int teamFirstID = Database.getTeamId(Objects.requireNonNull(cmbTeamFirst.getSelectedItem()).toString());
            int teamSecondID = Database.getTeamId(Objects.requireNonNull(cmbTeamSecond.getSelectedItem()).toString());

            int fpTeamFirst = Integer.parseInt(txtFPTeamFirst.getText());
            int fpTeamSecond = Integer.parseInt(txtFPTeamSecond.getText());

            int spTeamFirst = Integer.parseInt(txtSPTeamFirst.getText());
            int spTeamSecond = Integer.parseInt(txtSPTeamSecond.getText());

            String date = String.format("%s %s", dtpMatchDate.getDatePicker().toString(), dtpMatchDate.getTimePicker().getText());

            String query = String.format(
                    "INSERT INTO match_additions (team1_id, team2_id, fp_team1_score, fp_team2_score, sp_team1_score, sp_team2_score, ms_team1, ms_team2, match_date)"
                            + " VALUES( '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                    teamFirstID, teamSecondID, fpTeamFirst, fpTeamSecond, spTeamFirst, spTeamSecond,
                    (fpTeamFirst + spTeamFirst), (fpTeamSecond + spTeamSecond), date);

            Database db = new Database();
            state = db.connection.prepareStatement(query);
            rs = state.executeUpdate();

            if (rs > 0) {
                Helper.showDialog("Match added!");
                clearInput();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (state != null) {
                try {
                    state.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearInput() {
        cmbTeamFirst.setSelectedIndex(0);
        cmbTeamSecond.setSelectedIndex(1);
        txtFPTeamFirst.setText(null);
        txtFPTeamSecond.setText(null);
        txtSPTeamFirst.setText(null);
        txtSPTeamSecond.setText(null);
        dtpMatchDate.getDatePicker().setText(null);
        dtpMatchDate.getTimePicker().setText(null);
        lblCmbTeamFirst.setForeground(Color.black);
        lblCmbTeamSecond.setForeground(Color.black);
        lblFPMS.setForeground(Color.black);
        lblSPMS.setForeground(Color.black);
        lblMatchDate.setForeground(Color.black);
    }
}
