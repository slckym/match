import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LowPossibilities extends JFrame {
    private JPanel pnlPossibilities;
    private JComboBox cmbTeamFirst;
    private JComboBox cmbTeamSecond;
    private JButton btnResult;
    private JLabel lblTeamSecond;
    private JLabel lbl25UpperDown;
    private JLabel lblHandicap;
    private JTabbedPane tabResults;
    private JTable tblHistory;
    private JLabel lblFirstPeriod;
    private JLabel lblMoreGoal;
    private JLabel lblSecondPeriod;
    private JLabel lblTeamFirst;

    public LowPossibilities() throws Exception {
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
        LowPossibilities.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) throws Exception {
        LowPossibilities dialog = new LowPossibilities();
        dialog.setVisible(b);
    }

    private void validatePossibilities() throws Exception {

        if (cmbTeamFirst.getSelectedIndex() == cmbTeamSecond.getSelectedIndex()) {
            Helper.showDialog("Select different team");
            lblTeamSecond.setForeground(Color.red);
            lblTeamFirst.setForeground(Color.red);
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
            int totalFPRows = 0,
                totalSPRows = 0,
                teamFPFirstTotalGoals = 0,
                teamFPSecondTotalGoals = 0,
                teamFPFirstTotalWins = 0,
                teamFPSecondTotalWins = 0,
                totalFPReciprocal = 0,
                totalFPGoals = 0,
                teamSPFirstTotalGoals = 0,
                teamSPSecondTotalGoals = 0,
                teamSPFirstTotalWins = 0,
                teamSPSecondTotalWins = 0,
                totalSPReciprocal = 0,
                totalSPGoals = 0;
            while (rs.next()) {
                if (rs.getString("team1_name").equals(teamFirst)) {
                    teamFPFirstTotalGoals += rs.getInt("fp_team1_score");
                    teamFPSecondTotalGoals += rs.getInt("fp_team2_score");

                    if (rs.getInt("fp_team1_score") > rs.getInt("fp_team2_score")) {
                        teamFPFirstTotalWins++;
                    } else if (rs.getInt("fp_team1_score") < rs.getInt("fp_team2_score")) {
                        teamFPSecondTotalWins++;
                    } else {
                        totalFPReciprocal++;
                    }
                } else {
                    teamFPFirstTotalGoals += rs.getInt("fp_team2_score");
                    teamFPSecondTotalGoals += rs.getInt("fp_team1_score");
                    if (rs.getInt("fp_team2_score") > rs.getInt("fp_team1_score")) {
                        teamFPFirstTotalWins++;
                    } else if (rs.getInt("fp_team2_score") < rs.getInt("fp_team1_score")) {
                        teamFPSecondTotalWins++;
                    } else {
                        totalFPReciprocal++;
                    }
                }

                if (rs.getString("team1_name").equals(teamFirst)) {
                    teamSPFirstTotalGoals += rs.getInt("sp_team1_score");
                    teamSPSecondTotalGoals += rs.getInt("sp_team2_score");

                    if (rs.getInt("sp_team1_score") > rs.getInt("sp_team2_score")) {
                        teamSPFirstTotalWins++;
                    } else if (rs.getInt("sp_team1_score") < rs.getInt("sp_team2_score")) {
                        teamSPSecondTotalWins++;
                    } else {
                        totalSPReciprocal++;
                    }
                } else {
                    teamSPFirstTotalGoals += rs.getInt("sp_team2_score");
                    teamSPSecondTotalGoals += rs.getInt("sp_team1_score");
                    if (rs.getInt("sp_team2_score") > rs.getInt("sp_team1_score")) {
                        teamSPFirstTotalWins++;
                    } else if (rs.getInt("sp_team2_score") < rs.getInt("sp_team1_score")) {
                        teamSPSecondTotalWins++;
                    } else {
                        totalSPReciprocal++;
                    }
                }
            }

            totalFPRows = teamFPFirstTotalWins + teamFPSecondTotalWins + totalFPReciprocal;
            totalFPGoals = teamFPFirstTotalGoals + teamFPSecondTotalGoals;

            totalSPRows = teamSPFirstTotalWins + teamSPSecondTotalWins + totalSPReciprocal;
            totalSPGoals = teamSPFirstTotalGoals + teamSPSecondTotalGoals;



            if (((totalFPGoals + totalSPGoals) / (totalFPRows + totalSPRows)) < 2) {
                lbl25UpperDown.setText("Down");
            } else {
                lbl25UpperDown.setText("Up");
            }

            if (teamFPFirstTotalWins > teamFPSecondTotalWins) {
                lblFirstPeriod.setText(cmbTeamFirst.getSelectedItem().toString());
            } else {
                lblFirstPeriod.setText(cmbTeamSecond.getSelectedItem().toString());
            }

            if (teamSPFirstTotalWins > teamSPSecondTotalWins) {
                lblSecondPeriod.setText(cmbTeamFirst.getSelectedItem().toString());
            } else {
                lblSecondPeriod.setText(cmbTeamSecond.getSelectedItem().toString());
            }

            if (totalFPGoals - totalSPGoals >= 3) {
                lblHandicap.setText("Yes");
            } else {
                lblHandicap.setText("No");
            }

            if (totalFPGoals < totalSPGoals) {
                lblMoreGoal.setText("Second Period");
            } else {
                lblMoreGoal.setText("First Period");
            }

        } catch (Exception e) {
            state.close();
            clearResults();
            System.out.println(e.getMessage());
        }

        Database.fillHistoryTable(teamFirst, teamSecond, tblHistory);
    }

    private void initialize() {
        setContentPane(pnlPossibilities);
        setTitle("Low Possibilities");
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
        lblSecondPeriod.setText("-");
        lblMoreGoal.setText("-");
        lbl25UpperDown.setText("-");
        lblHandicap.setText("-");
        lblFirstPeriod.setText("-");
    }

}
