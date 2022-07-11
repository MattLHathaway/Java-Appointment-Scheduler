package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionsQuery {

    //Division_ID, Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID
    //int divisionID, String division, String divisionCreateDate, String divisionCreatedBy, String divisionLastUpdate, String divisionLastUpdatedBy, int countryID


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
}