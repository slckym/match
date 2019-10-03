import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class Calender extends JFrame {
    private JPanel pnlCalender;
    private JComboBox cmbTeamFirst;
    private JComboBox cmbTeamSecond;
    private JTable tblResults;
    private JButton btnFilter;
    private JLabel lblTeamFirst;
    private JLabel lblTeamSecond;
    private JScrollPane scrollResultTable;

    private Calender() throws Exception {
        fillMatchTableRecords(null, null);
        initialize();
        try {
            Helper.fillCombobox(cmbTeamFirst);
            Helper.fillCombobox(cmbTeamSecond);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        btnFilter.addActionListener(e -> {
            String teamFirst = Objects.requireNonNull(cmbTeamFirst.getSelectedItem()).toString();
            String teamSecond = Objects.requireNonNull(cmbTeamSecond.getSelectedItem()).toString();
            fillMatchTableRecords(teamFirst, teamSecond);
        });
    }

    private void fillMatchTableRecords(String teamFirst, String teamSecond) {

        Database db = new Database();
        try {
            String query = "SELECT ma.id AS 'ID'," +
                    "team1.team_name AS 'Team First', team2.team_name AS 'Team Second', " +
                    "ma.fp_team1_score AS 'FP Team First Score', ma.fp_team2_score AS 'FP Team Second Score', " +
                    "ma.sp_team1_score AS 'SP Team First Score', ma.sp_team2_score AS 'SP Team Second Score', " +
                    "ma.ms_team1 AS 'MS Team First Score', ma.ms_team2 AS 'MS Team Second Score', " +
                    "ma.match_date as 'Match Date'" +
                    " FROM match_additions AS ma" +
                    " LEFT JOIN teams AS team1 ON team1.id = ma.team1_id" +
                    " LEFT JOIN teams AS team2 ON team2.id = ma.team2_id";
            if (teamFirst != null && teamSecond != null) {
                int teamFirstID = Database.getTeamId(teamFirst);
                int teamSecondID = Database.getTeamId(teamSecond);

                query += String.format(" WHERE ma.team1_id = '%s' AND ma.team2_id = '%s' ", teamFirstID, teamSecondID);
            }
            query += " ORDER BY match_date DESC";

            PreparedStatement state = db.connection.prepareStatement(query);
            tblResults.setModel(DbUtils.resultSetToTableModel(state.executeQuery()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Calender.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) throws Exception {
        Calender calender = new Calender();
        calender.setVisible(b);
    }

    private void initialize() {
        setContentPane(pnlCalender);
        setSize(960, 700);
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
