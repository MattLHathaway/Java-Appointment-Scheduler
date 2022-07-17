package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class' purpose is to move FirstLevelDivision data to and from the database.
 */
public class FirstLevelDivisionsQuery {

    /**
     * This function gets all Divisions by Country ID.
     * @param fldID
     * @return
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisionsByCountryID(int fldID) throws SQLException {
        ObservableList<FirstLevelDivisions> selectedFirstLevelDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, fldID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            String divisionCreateDate = rs.getString("Create_Date");
            String divisionCreatedBy = rs.getString("Created_By");
            String divisionLastUpdate = rs.getString("Last_Update");
            String divisionLastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, divisionCreateDate, divisionCreatedBy, divisionLastUpdate, divisionLastUpdatedBy, countryID);
            selectedFirstLevelDivisions.add(firstLevelDivisions);
        }
        return selectedFirstLevelDivisions;
    }

    /**
     * This function gets all Divisions.
     * @return
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() throws SQLException {
        ObservableList<FirstLevelDivisions> selectedFirstLevelDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            String divisionCreateDate = rs.getString("Create_Date");
            String divisionCreatedBy = rs.getString("Created_By");
            String divisionLastUpdate = rs.getString("Last_Update");
            String divisionLastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, divisionCreateDate, divisionCreatedBy, divisionLastUpdate, divisionLastUpdatedBy, countryID);
            selectedFirstLevelDivisions.add(firstLevelDivisions);
        }
        return selectedFirstLevelDivisions;
    }

    /**
     * This function gets all Divisions by Country Name.
     * @param inputState
     * @return
     * @throws SQLException
     */
    public static int getDivisionIDbyName(String inputState) throws SQLException {
        int inputDivisionID = 1;
        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, inputState);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            String divisionCreateDate = rs.getString("Create_Date");
            String divisionCreatedBy = rs.getString("Created_By");
            String divisionLastUpdate = rs.getString("Last_Update");
            String divisionLastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, divisionCreateDate, divisionCreatedBy, divisionLastUpdate, divisionLastUpdatedBy, countryID);
            inputDivisionID = firstLevelDivisions.getDivisionID();
        }
        return inputDivisionID;
    }


    /**
     * This function gets all Division Names by ID.
     * @param inputID
     * @return
     * @throws SQLException
     */
    public static String getDivisionNameByID(int inputID) throws SQLException {
        String inputDivisionName = "ERROR";
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, inputID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            String divisionCreateDate = rs.getString("Create_Date");
            String divisionCreatedBy = rs.getString("Created_By");
            String divisionLastUpdate = rs.getString("Last_Update");
            String divisionLastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, divisionCreateDate, divisionCreatedBy, divisionLastUpdate, divisionLastUpdatedBy, countryID);
            inputDivisionName = firstLevelDivisions.getDivision();
        }
        return inputDivisionName;
    }

    /**
     * This function gets Country ID by Division ID.
     * @param inputDivisionID
     * @return
     * @throws SQLException
     */
    public static int getCountryIDbyDivisionID(int inputDivisionID) throws SQLException {
        int outputCountryID = 0;
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, inputDivisionID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            String divisionCreateDate = rs.getString("Create_Date");
            String divisionCreatedBy = rs.getString("Created_By");
            String divisionLastUpdate = rs.getString("Last_Update");
            String divisionLastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("Country_ID");
            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, division, divisionCreateDate, divisionCreatedBy, divisionLastUpdate, divisionLastUpdatedBy, countryID);
            outputCountryID = firstLevelDivisions.getCountryID();
        }
        return outputCountryID;
    }
}