package model;

/**
 * The purpose of this class is to model Countries data.
 */
public class Countries {
    private int countryID;
    private String country;
    private String countryCreateDate;
    private String countryCreatedBy;
    private String countryLastUpdate;
    private String countryLastUpdatedBy;

    /**
     * This is our primary constructor.
     * @param countryID
     * @param country
     * @param countryCreateDate
     * @param countryCreatedBy
     * @param countryLastUpdate
     * @param countryLastUpdatedBy
     */
    public Countries(int countryID, String country, String countryCreateDate, String countryCreatedBy, String countryLastUpdate, String countryLastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.countryCreateDate = countryCreateDate;
        this.countryCreatedBy = countryCreatedBy;
        this.countryLastUpdate = countryLastUpdate;
        this.countryLastUpdatedBy = countryLastUpdatedBy;
    }

    /**
     * This is our secondary constructor.
     */
    public Countries() {

    }

    //GETTERS & SETTERS

    /**
     * get CountryID
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * set CountryID
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * get Country
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * set Country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * get CountryCreateDate
     * @return
     */
    public String getCountryCreateDate() {
        return countryCreateDate;
    }

    /**
     * set CountryCreateDate
     * @param countryCreateDate
     */
    public void setCountryCreateDate(String countryCreateDate) {
        this.countryCreateDate = countryCreateDate;
    }

    /**
     * get CountryCreatedBy
     * @return
     */
    public String getCountryCreatedBy() {
        return countryCreatedBy;
    }

    /**
     * set CountryCreatedBy
     * @param countryCreatedBy
     */
    public void setCountryCreatedBy(String countryCreatedBy) {
        this.countryCreatedBy = countryCreatedBy;
    }

    /**
     * get CountryLastUpdate
     * @return
     */
    public String getCountryLastUpdate() {
        return countryLastUpdate;
    }

    /**
     * set CountryLastUpdate
     * @param countryLastUpdate
     */
    public void setCountryLastUpdate(String countryLastUpdate) {
        this.countryLastUpdate = countryLastUpdate;
    }

    /**
     * get CountryLastUpdatedBy
     * @return
     */
    public String getCountryLastUpdatedBy() {
        return countryLastUpdatedBy;
    }

    /**
     * set CountryLastUpdatedBy
     * @param countryLastUpdatedBy
     */
    public void setCountryLastUpdatedBy(String countryLastUpdatedBy) {
        this.countryLastUpdatedBy = countryLastUpdatedBy;
    }
}
