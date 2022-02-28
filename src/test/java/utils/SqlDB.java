package utils;

import java.sql.*;

public class SqlDB {

    // Establish connection
    public static Connection getSqlConnection(String dbDriver, String URL, String userName, String Password) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(URL, userName, Password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Return Result set
    public static ResultSet executeQuery(Connection conn, String query) throws SQLException {

        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static int deleteRows(Connection conn, String query) throws SQLException {

        int dbcount = 0;
        try {
            Statement stmt = conn.createStatement();
            dbcount = stmt.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbcount;
    }

    public static int insertRows(Connection conn, String query) throws SQLException {
        int insertRecord = 0;
        try {
            Statement stmt = conn.createStatement();
            insertRecord = stmt.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return insertRecord;
    }
}
