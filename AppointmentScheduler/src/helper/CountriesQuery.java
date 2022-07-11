package helper;

import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountriesQuery {

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
            String countryCreatedBy = rs.getString("Create_By");
            String countryLastUpdate = rs.getString("Last_Update");
            String countryLastUpdatedBy = rs.getString("Last_Updated_By");
            Countries countries = new Countries(countryID, countryName, countryCreateDate, countryCreatedBy, countryLastUpdate, countryLastUpdatedBy);
            selectedCountry = countries;
        }
        return selectedCountry;
    }
}