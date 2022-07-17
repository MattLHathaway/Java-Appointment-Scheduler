package model;

/**
 * The purpose of this class is to model FirstLevelDivision data.
 */
public class FirstLevelDivisions {
    private int divisionID;
    private String division;
    private String divisionCreateDate;
    private String divisionCreatedBy;
    private String divisionLastUpdate;
    private String divisionLastUpdatedBy;
    private int countryID;

    /**
     * This is our primary constructor.
     * @param divisionID
     * @param division
     * @param divisionCreateDate
     * @param divisionCreatedBy
     * @param divisionLastUpdate
     * @param divisionLastUpdatedBy
     * @param countryID
     */
    public FirstLevelDivisions(int divisionID, String division, String divisionCreateDate, String divisionCreatedBy, String divisionLastUpdate, String divisionLastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.divisionCreateDate = divisionCreateDate;
        this.divisionCreatedBy = divisionCreatedBy;
        this.divisionLastUpdate = divisionLastUpdate;
        this.divisionLastUpdatedBy = divisionLastUpdatedBy;
        this.countryID = countryID;
    }

    //GETTERS & SETTERS

    /**
     * get DivisionID
     * @return
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * set DivisionID
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * get Division
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**
     * set Division
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * get DivisionCreateDate
     * @return
     */
    public String getDivisionCreateDate() {
        return divisionCreateDate;
    }

    /**
     * set DivisionCreateDate
     * @param divisionCreateDate
     */
    public void setDivisionCreateDate(String divisionCreateDate) {
        this.divisionCreateDate = divisionCreateDate;
    }

    /**
     * get DivisionCreatedBy
     * @return
     */
    public String getDivisionCreatedBy() {
        return divisionCreatedBy;
    }

    /**
     * set DivisionCreatedBy
     * @param divisionCreatedBy
     */
    public void setDivisionCreatedBy(String divisionCreatedBy) {
        this.divisionCreatedBy = divisionCreatedBy;
    }

    /**
     * get DivisionLastUpdate
     * @return
     */
    public String getDivisionLastUpdate() {
        return divisionLastUpdate;
    }

    /**
     * set DivisionLastUpdate
     * @param divisionLastUpdate
     */
    public void setDivisionLastUpdate(String divisionLastUpdate) {
        this.divisionLastUpdate = divisionLastUpdate;
    }

    /**
     * get DivisionLastUpdatedBy
     * @return
     */
    public String getDivisionLastUpdatedBy() {
        return divisionLastUpdatedBy;
    }

    /**
     * set DivisionLastUpdatedBy
     * @param divisionLastUpdatedBy
     */
    public void setDivisionLastUpdatedBy(String divisionLastUpdatedBy) {
        this.divisionLastUpdatedBy = divisionLastUpdatedBy;
    }

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
}
