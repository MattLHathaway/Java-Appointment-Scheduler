package helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public abstract class UsersQuery {

    public static int insert(int usersID, String usersName, String usersPassword) throws SQLException {
        String sql = "INSERT INTO users (User_ID, User_Name, Password) VALUES (?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, usersID);
        ps.setString(2, usersName);
        ps.setString(3, usersPassword);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(int usersID, String usersName, String usersPassword) throws SQLException {
        String sql = "UPDATE users SET User_Name = ?, Password = ? WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, usersName);
        ps.setString(2, usersPassword);
        ps.setInt(3, usersID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int usersID) throws SQLException {
        String sql = "DELETE FROM users WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, usersID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static void select() throws SQLException {
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int usersID = rs.getInt("User_ID");
            String usersName = rs.getString("User_Name");
            String password = rs.getString("Password");
            System.out.println(usersID + " | " + usersName + " | " + password + "\n");
        }
    }

    public static void select(int inputUserID) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, inputUserID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int usersID = rs.getInt("User_ID");
            String usersName = rs.getString("User_Name");
            String password = rs.getString("Password");
            int userIdFK = rs.getInt("User_ID");
            System.out.println(usersID + " | " + usersName + " | " + password + " | " + userIdFK + "\n");
            System.out.println(userIdFK);
        }
    }
}
