package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class' purpose is to move Countries data to and from the database.
 */
public abstract class CountriesQuery {

    /**
     * This function is used to get all Countries by ID #.
     * @param selectedCountryID
     * @return
     * @throws SQLException
     */
    //int countryID, String country, String countryCreateDate, String countryCreatedBy, String countryLastUpdate, String countryLastUpdatedBy
    public static Countries getByID(int selectedCountryID) throws SQLException {
        Countries selectedCountry = new Countries();
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, selectedCountryID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            String countryCreateDate = rs.getString("Create_Date");
            String countryCreatedBy = rs.getString("Created_By");
            String countryLastUpdate = rs.getString("Last_Update");
            String countryLastUpdatedBy = rs.getString("Last_Updated_By");
            Countries countries = new Countries(countryID, countryName, countryCreateDate, countryCreatedBy, countryLastUpdate, countryLastUpdatedBy);
            selectedCountry = countries;
        }
        return selectedCountry;
    }

    /**
     * This function is used to get all Countries.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Countries> getCountriesList() throws SQLException {
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            String countryCreateDate = rs.getString("Create_Date");
            String countryCreatedBy = rs.getString("Created_By");
            String countryLastUpdate = rs.getString("Last_Update");
            String countryLastUpdatedBy = rs.getString("Last_Updated_By");
            Countries countries = new Countries(countryID, countryName, countryCreateDate, countryCreatedBy, countryLastUpdate, countryLastUpdatedBy);
            countriesList.add(countries);
        }
        return countriesList;
    }
}
