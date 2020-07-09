package banking;

import java.sql.*;

public class SqlLiteDB {
    private final String URL;

    SqlLiteDB(String URL){
        this.URL = URL;
    }

    public void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS card ("
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "	number TEXT,"
                + "	pin TEXT,"
                + " balance INTEGER DEFAULT 0"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void removeAccount(String card){
        String sql = "DELETE FROM card WHERE number = '" + card + "';";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addMoney(String card, int amount) {
        // SQL statement for creating a new table
        String sql = "UPDATE card SET balance = balance + " + amount + " WHERE number = '" + card + "'";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int getBalance(String card){
        String sql = "SELECT balance FROM card WHERE number = '" + card + "';";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                return rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
    public void insertCard(String number, String pin){
        String sql = "INSERT INTO card(number,pin) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.setString(2, pin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean isCardExists(String card){
        String sql = "SELECT id FROM card WHERE number = '" + card + "';";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean isCardExists(String card, String PIN){
        String sql = "SELECT id FROM card WHERE number = '" + card + "' AND pin = '" + PIN + "';";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
