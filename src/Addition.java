import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        setBounds(100, 100, 530, 300);
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

    private void validateMatchAddition() throws Exception {

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
            int teamFirstID = Database.getTeamId(Objects.requireNonNull(cmbTeamFirst.getSelectedItem()).toString());
            int teamSecondID = Database.getTeamId(Objects.requireNonNull(cmbTeamSecond.getSelectedItem()).toString());

            int[] fpScore = {
                    Integer.parseInt(txtFPTeamFirst.getText()),
                    Integer.parseInt(txtFPTeamSecond.getText())
            };

            int[] spScore = {
                    Integer.parseInt(txtSPTeamFirst.getText()),
                    Integer.parseInt(txtSPTeamSecond.getText())
            };

            String date = String.format("%s %s", dtpMatchDate.getDatePicker().toString(), dtpMatchDate.getTimePicker().getText());
            ModelAddition insertMatch = new ModelAddition(teamFirstID, teamSecondID, fpScore, spScore, date);

            if (insertMatch.save()) {
                Helper.showDialog("Match added!");
                clearInput();
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
