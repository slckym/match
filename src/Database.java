import java.sql.*;

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
}
