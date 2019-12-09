import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class League extends JFrame {
    private JPanel pnlLeague;
    private JTable tblLeague;

    public League() {
        initialize();
        try {
            fillLeagueTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        League.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        League dialog = new League();
        dialog.setVisible(b);
    }

    private void fillLeagueTable() throws SQLException {
        Database db = new Database();
        String query = "SELECT t.team_name AS Team, " +
                "    IFNULL(Sum(W) , 0) AS W, " +
                "    IFNULL(Sum(G) , 0) AS G, " +
                "    IFNULL(Sum(V) , 0) AS V, " +
                "    IFNULL(SUM(DV), 0) as DV, " +
                "    IFNULL(SUM(DT), 0) AS DT, " +
                "    IFNULL(SUM(S) , 0) AS S, " +
                "    IFNULL(SUM(P) , 0) AS P " +
                "FROM teams t " +
                "  LEFT JOIN ( " +
                "    SELECT " +
                "      team1_id AS id, " +
                "      team2_id AS id2, " +
                "      CASE WHEN(ms_team1 > ms_team2) THEN 1 ELSE 0 END W, " +
                "      CASE WHEN(ms_team1 = ms_team2) THEN 1 ELSE 0 END G, " +
                "      CASE WHEN(ms_team1 < ms_team2) THEN 1 ELSE 0 END V, " +
                "      ms_team1 DV, " +
                "      ms_team2 DT, " +
                "      ms_team1 - ms_team2 S, " +
                "      CASE WHEN ms_team1 > ms_team2 THEN 3 WHEN ms_team1 = ms_team2 THEN 1 ELSE 0 END P " +
                "    FROM match_additions " +
                "    UNION ALL " +
                "    SELECT " +
                "      team2_id AS id, " +
                "      1, " +
                "      CASE WHEN(ms_team1 < ms_team2) THEN 1 ELSE 0 END W, " +
                "      CASE WHEN(ms_team1 = ms_team2) THEN 1 ELSE 0 END G, " +
                "      CASE WHEN(ms_team1 > ms_team2) THEN 1 ELSE 0 END V, " +
                "      ms_team2, " +
                "      ms_team1, " +
                "      ms_team2 - ms_team1 S, " +
                "      CASE WHEN ms_team1 < ms_team2 THEN 3 WHEN ms_team1 = ms_team2 THEN 1 ELSE 0 END P " +
                "    FROM match_additions " +
                "  ) AS tot ON tot.id = t.id " +
                "GROUP BY Team " +
                "ORDER BY SUM(P) DESC, S DESC";
        Statement state = db.connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        try (ResultSet result = state.executeQuery(query)) {
            result.last();
            String[] columns = new String[]{"Team", "W", "G", "V", "DV", "DT", "S", "P"};
            String[][] tableContents = new String[result.getRow()][8];
            int i = 0;
            result.beforeFirst();
            while (result.next()) {
                tableContents[i][0] = result.getString(1);
                tableContents[i][1] = result.getString(2);
                tableContents[i][2] = result.getString(3);
                tableContents[i][3] = result.getString(4);
                tableContents[i][4] = result.getString(5);
                tableContents[i][5] = result.getString(6);
                tableContents[i][6] = result.getString(7);
                tableContents[i][7] = result.getString(8);
                i++;
            }
            DefaultTableModel tableModel = new DefaultTableModel(tableContents, columns);
            tblLeague.setModel(tableModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //tblLeague.setModel(DbUtils.resultSetToTableModel(state.executeQuery()));
    }

    private void initialize() {
        setContentPane(pnlLeague);
        setTitle("League");
        setBounds(100, 100, 750, 400);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        pnlLeague.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        Helper.centreWindow(this);

    }

    private void onCancel() {
        setVisible(false);
    }
}
