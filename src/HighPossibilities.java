import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class HighPossibilities extends JFrame {
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
    private JLabel lblTeamFirstName;
    private JLabel lblTeamSecondName;
    private JLabel lblTeamFirstPercent;
    private JLabel lblTeamSecondPercent;
    private JLabel lblResult;
    private JLabel lblEquality;
    private JLabel lblCmbTeamFirst;

    public HighPossibilities() throws Exception {
        initialize();
        try {
            Helper.fillCombobox(cmbTeamFirst);
            Helper.fillCombobox(cmbTeamSecond);
            cmbTeamSecond.setSelectedIndex(1);
            validatePossibilities();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnResult.addActionListener(e -> {
            try {
                validatePossibilities();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        HighPossibilities.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) throws Exception {
        HighPossibilities dialog = new HighPossibilities();
        dialog.setVisible(b);
    }

    private void validatePossibilities() throws Exception {

        if (cmbTeamFirst.getSelectedIndex() == cmbTeamSecond.getSelectedIndex()) {
            Helper.showDialog("Select different team");
            lblCmbTeamSecond.setForeground(Color.red);
            lblCmbTeamFirst.setForeground(Color.red);
            cmbTeamSecond.requestFocus();
        } else {
            this.getPossibilities();
        }

    }

    private void getPossibilities() throws Exception {
        String teamFirst = Objects.requireNonNull(cmbTeamFirst.getSelectedItem()).toString(),
                teamSecond = Objects.requireNonNull(cmbTeamSecond.getSelectedItem()).toString();
        PreparedStatement state = Database.matchResults(teamFirst, teamSecond);
        try (ResultSet rs = state.executeQuery()) {
            int totalRows = 0, teamFirstTotalGoals = 0, teamSecondTotalGoals = 0, teamFirstTotalWins = 0, teamSecondTotalWins = 0, totalReciprocal = 0, totalGoals = 0;
            while (rs.next()) {
                if (rs.getString("team1_name").equals(teamFirst)) {
                    teamFirstTotalGoals += rs.getInt("ms_team1");
                    teamSecondTotalGoals += rs.getInt("ms_team2");
                    if (rs.getInt("ms_team1") > rs.getInt("ms_team2")) {
                        teamFirstTotalWins++;
                    } else if (rs.getInt("ms_team1") < rs.getInt("ms_team2")) {
                        teamSecondTotalWins++;
                    } else {
                        totalReciprocal++;
                    }
                } else {
                    teamFirstTotalGoals += rs.getInt("ms_team2");
                    teamSecondTotalGoals += rs.getInt("ms_team1");
                    if (rs.getInt("ms_team2") > rs.getInt("ms_team1")) {
                        teamFirstTotalWins++;
                    } else if (rs.getInt("ms_team2") < rs.getInt("ms_team1")) {
                        teamSecondTotalWins++;
                    } else {
                        totalReciprocal++;
                    }
                }

            }

            totalRows = teamFirstTotalWins + teamSecondTotalWins + totalReciprocal;
            totalGoals = teamFirstTotalGoals + teamSecondTotalGoals;
            if ((totalGoals / totalRows) < 2) {
                lbl15UpperDown.setText("Down");
            } else {
                lbl15UpperDown.setText("Up");
            }

            if ((totalGoals / totalRows) < 4) {
                lbl35UpperDown.setText("Down");
            } else {
                lbl35UpperDown.setText("Up");
            }

            if (totalGoals % 2 == 0) {
                lblOddOrEven.setText("Even");
            } else {
                lblOddOrEven.setText("Odd");
            }

            String calculatedTotalGoals = String.valueOf(totalGoals / totalRows);

            int reciprocalPercent = (100 / totalRows) * totalReciprocal,
                    teamFirstWinPercent = (100 / totalRows) * teamFirstTotalWins,
                    teamSecondWinPercent = (100 / totalRows) * teamSecondTotalWins;

            lblReciprocal.setText((teamFirstTotalGoals * teamSecondTotalGoals == 0) ? "Yes" : "No");
            lblTeamFirstName.setText(teamFirst);
            lblTeamSecondName.setText(teamSecond);
            lblTeamFirstPercent.setText(teamFirstWinPercent + "%");
            lblTeamSecondPercent.setText(teamSecondWinPercent + "%");
            lblTotalGoals.setText(calculatedTotalGoals);
            lblEquality.setText(reciprocalPercent + "%");

        } catch (Exception e) {
            state.close();
            clearResults();
            System.out.println(e.getMessage());
        }

        Database.fillHistoryTable(teamFirst, teamSecond, tblHistory);
    }

    private void initialize() {
        setContentPane(pnlPossibilities);
        setTitle("HighPossibilities");
        setBounds(100, 100, 530, 350);
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

    private void clearResults() {
        String teamFirst = Objects.requireNonNull(cmbTeamFirst.getSelectedItem()).toString(),
                teamSecond = Objects.requireNonNull(cmbTeamSecond.getSelectedItem()).toString();
        lblReciprocal.setText("No");
        lblTeamFirstName.setText(teamFirst);
        lblTeamSecondName.setText(teamSecond);
        lblTeamFirstPercent.setText("0%");
        lblTeamSecondPercent.setText("0%");
        lblTotalGoals.setText("0");
        lblEquality.setText("0%");
        lbl15UpperDown.setText("Down");
        lbl35UpperDown.setText("Down");
        lblOddOrEven.setText("Even");
    }
}
