package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

/**
 * This class' purpose is to move Users data to and from the Database.
 */
public abstract class UsersQuery {

    /**
     * This functions purpose is to add users to the db.
     * @param usersID
     * @param usersName
     * @param usersPassword
     * @return
     * @throws SQLException
     */
    public static int insert(int usersID, String usersName, String usersPassword) throws SQLException {
        String sql = "INSERT INTO users (User_ID, User_Name, Password) VALUES (?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, usersID);
        ps.setString(2, usersName);
        ps.setString(3, usersPassword);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This functions purpose is to modify users in the db.
     * @param usersID
     * @param usersName
     * @param usersPassword
     * @return
     * @throws SQLException
     */
    public static int update(int usersID, String usersName, String usersPassword) throws SQLException {
        String sql = "UPDATE users SET User_Name = ?, Password = ? WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, usersName);
        ps.setString(2, usersPassword);
        ps.setInt(3, usersID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This functions purpose is to delete users to the db.
     * @param usersID
     * @return
     * @throws SQLException
     */
    public static int delete(int usersID) throws SQLException {
        String sql = "DELETE FROM users WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, usersID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This functions purpose is to get all Users from DB.
     * @throws SQLException
     */
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

    /**
     * This functions purpose is to get all Users from DB.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Users> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        ObservableList<Users> userList = FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int usersID = rs.getInt("User_ID");
            String usersName = rs.getString("User_Name");
            String password = rs.getString("Password");
            String userCreateDate = rs.getString("Create_Date");
            String userCreatedBy = rs.getString("Created_By");
            String userLastUpdate = rs.getString("Last_Update");
            String userLastUpdatedBy = rs.getString("Last_Updated_By");
            Users user = new Users(usersID, usersName, password, userCreateDate, userCreatedBy, userLastUpdate, userLastUpdatedBy);
            userList.add(user);
        }
        return userList;
    }

    /**
     * This functions purpose is to get all Users by ID.
     * @param inputUserID
     * @throws SQLException
     */
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

    /**
     * This functions purpose is to get all User Names. This is used for Login verification.
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> returnUserNames() throws SQLException {
        ObservableList<String> allUserNames = FXCollections.observableArrayList();
        String sql = "SELECT User_Name FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String usersName = rs.getString("User_Name");
            allUserNames.add(usersName);
        }
        return allUserNames;
    }

    /**
     * This functions purpose is to return all User Passwords.  This is used for Login verification.
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> returnUserPasswords() throws SQLException {
        ObservableList<String> allUserPasswords = FXCollections.observableArrayList();
        String sql = "SELECT Password FROM users ORDER BY Password ASC";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String usersPassword = rs.getString("Password");
            allUserPasswords.add(usersPassword);
        }
        return allUserPasswords;
    }

    /**
     * This is used to get all User names by ID.
     * @param inputUserID
     * @return
     * @throws SQLException
     */
    public static String getNameByID(int inputUserID) throws SQLException  {
        String sql = "SELECT * FROM users WHERE User_ID = ?";
        Users selectedUser = new Users();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, inputUserID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            String userCreateDate = rs.getString("Create_Date");
            String userCreatedBy = rs.getString("Created_By");
            String userLastUpdate = rs.getString("Last_Update");
            String userLastUpdatedBy = rs.getString("Last_Updated_By");
            Users user = new Users(userID, userName, userPassword, userCreateDate, userCreatedBy, userLastUpdate, userLastUpdatedBy);
            selectedUser = user;
        }
        return selectedUser.getUserName();
    }

    /**
     * This is used to get all User IDs by Name.
     * @param inputUserName
     * @return
     * @throws SQLException
     */
    public static int getIdByName(String inputUserName) throws SQLException  {
        String sql = "SELECT * FROM users WHERE User_Name = ?";
        Users selectedUser = new Users();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, inputUserName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            String userCreateDate = rs.getString("Create_Date");
            String userCreatedBy = rs.getString("Created_By");
            String userLastUpdate = rs.getString("Last_Update");
            String userLastUpdatedBy = rs.getString("Last_Updated_By");
            Users user = new Users(userID, userName, userPassword, userCreateDate, userCreatedBy, userLastUpdate, userLastUpdatedBy);
            selectedUser = user;
        }
        return selectedUser.getUserID();
    }
}