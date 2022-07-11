package model;

public class Countries {
    private int countryID;
    private String country;
    private String countryCreateDate;
    private String countryCreatedBy;
    private String countryLastUpdate;
    private String countryLastUpdatedBy;

    public Countries(int countryID, String country, String countryCreateDate, String countryCreatedBy, String countryLastUpdate, String countryLastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.countryCreateDate = countryCreateDate;
        this.countryCreatedBy = countryCreatedBy;
        this.countryLastUpdate = countryLastUpdate;
        this.countryLastUpdatedBy = countryLastUpdatedBy;
    }

    public Countries() {

    }

    //GETTERS & SETTERS

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCreateDate() {
        return countryCreateDate;
    }

    public void setCountryCreateDate(String countryCreateDate) {
        this.countryCreateDate = countryCreateDate;
    }

    public String getCountryCreatedBy() {
        return countryCreatedBy;
    }

    public void setCountryCreatedBy(String countryCreatedBy) {
        this.countryCreatedBy = countryCreatedBy;
    }

    public String getCountryLastUpdate() {
        return countryLastUpdate;
    }

    public void setCountryLastUpdate(String countryLastUpdate) {
        this.countryLastUpdate = countryLastUpdate;
    }

    public String getCountryLastUpdatedBy() {
        return countryLastUpdatedBy;
    }

    public void setCountryLastUpdatedBy(String countryLastUpdatedBy) {
        this.countryLastUpdatedBy = countryLastUpdatedBy;
    }
}
