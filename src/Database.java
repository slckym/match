import java.sql.*;

class Database {

    private Connection connection;

    private Database() {

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:Match.db");
            System.out.println("Opened database successfully");

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    static boolean login(String username, String password) throws Exception {
        Database db = new Database();
        String sql = String.format("SELECT * FROM users WHERE username = '%s' AND password = '%s' ", username, password);
        var stmt = db.connection.prepareStatement(sql);

        try (ResultSet resultSet = stmt.executeQuery()) {
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            stmt.close();
            db.connection.close();
        }

        return false;
    }
}
