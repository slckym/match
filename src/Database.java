import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class Database {

    Connection connection = null;

    Database() {

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:Match.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    static boolean login(String username, String password) throws Exception {
        Database db = new Database();
        String sql = String.format("SELECT * FROM users WHERE username = '%s' AND password = '%s' ", username, password);
        var statement = db.connection.prepareStatement(sql);

        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            statement.close();
            db.connection.close();
        }

        return false;
    }

    static int getTeamId(String teamName) throws Exception {
        Database db = new Database();
        String query = String.format("SELECT * FROM teams WHERE team_name = '%s'", teamName);
        var statement = db.connection.prepareStatement(query);

        try (ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                return Integer.parseInt(result.getString("id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            statement.close();
        }

        return 0;
    }

    static ResultSet getAllTeams() {
        try {
            Database db = new Database();
            String query = "SELECT * FROM teams";

            PreparedStatement state = db.connection.prepareStatement(query);
            return state.executeQuery();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static void fillHistoryTable(String teamFirst, String teamSecond, JTable table) {
        try {
            Database db = new Database();
            String query = "SELECT ma.id AS 'ID'," +
                    "team1.team_name AS 'Team First'," +
                    "team2.team_name AS 'Team Second'," +
                    "ma.fp_team1_score || '-' || ma.fp_team2_score AS 'FP Score'," +
                    "ma.sp_team1_score || '-' || ma.sp_team2_score AS 'SP Score'," +
                    "ma.ms_team1 || '-' || ma.ms_team2 AS 'MS Score'," +
                    "ma.match_date AS 'Match Date'" +
                    " FROM match_additions AS ma" +
                    " LEFT JOIN teams AS team1 ON team1.id = ma.team1_id" +
                    " LEFT JOIN teams AS team2 ON team2.id = ma.team2_id";
            if (teamFirst != null && teamSecond != null) {
                int teamFirstID = Database.getTeamId(teamFirst);
                int teamSecondID = Database.getTeamId(teamSecond);

                query += String.format(" WHERE ma.team1_id = '%s' AND ma.team2_id = '%s' ", teamFirstID, teamSecondID);
                query += String.format(" OR ma.team1_id = '%s' AND ma.team2_id = '%s' ", teamSecondID, teamFirstID);
            }
            query += " ORDER BY match_date DESC";

            PreparedStatement state = db.connection.prepareStatement(query);
            ;
            table.setModel(DbUtils.resultSetToTableModel(state.executeQuery()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static PreparedStatement matchResults(String teamFirst, String teamSecond) throws Exception {
        Database db = new Database();
        String query = "SELECT ma.id, ma.*, team1.team_name AS team1_name, team2.team_name AS team2_name " +
                "FROM match_additions AS ma " +
                "LEFT JOIN teams AS team1 ON team1.id = ma.team1_id " +
                "LEFT JOIN teams AS team2 ON team2.id = ma.team2_id ";
        if (teamFirst != null && teamSecond != null) {
            int teamFirstID = Database.getTeamId(teamFirst);
            int teamSecondID = Database.getTeamId(teamSecond);

            query += String.format("WHERE ma.team1_id = '%s' AND ma.team2_id = '%s' ", teamFirstID, teamSecondID);
            query += String.format("OR ma.team1_id = '%s' AND ma.team2_id = '%s' ", teamSecondID, teamFirstID);
        }

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        query += String.format("AND match_date < '%s' ORDER BY match_date DESC", date);

        return db.connection.prepareStatement(query);
    }

    static int forgetPassword(String username, String answer1, String answer2) throws SQLException {
        Database db = new Database();
        String query = String.format("SELECT * FROM users WHERE username = '%s' AND answer1 = '%s' AND answer2 = '%s'", username, answer1, answer2);
        var statement = db.connection.prepareStatement(query);

        try (ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                return Integer.parseInt(result.getString("id"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            statement.close();
        }

        return 0;
    }

    public static boolean updatePassword(String username, String password) throws SQLException {
        Database db = new Database();
        String query = String.format("UPDATE users SET password = '%s' WHERE username = '%s'", password, username);
        var statement = db.connection.prepareStatement(query);
        try{
            int resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            statement.close();
            db.connection.close();
        }

        return false;
    }
}
