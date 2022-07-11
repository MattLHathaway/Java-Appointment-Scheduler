package model;

public class FirstLevelDivisions {
    private int divisionID;
    private String division;
    private String divisionCreateDate;
    private String divisionCreatedBy;
    private String divisionLastUpdate;
    private String divisionLastUpdatedBy;
    private int countryID;

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

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivisionCreateDate() {
        return divisionCreateDate;
    }

    public void setDivisionCreateDate(String divisionCreateDate) {
        this.divisionCreateDate = divisionCreateDate;
    }

    public String getDivisionCreatedBy() {
        return divisionCreatedBy;
    }

    public void setDivisionCreatedBy(String divisionCreatedBy) {
        this.divisionCreatedBy = divisionCreatedBy;
    }

    public String getDivisionLastUpdate() {
        return divisionLastUpdate;
    }

    public void setDivisionLastUpdate(String divisionLastUpdate) {
        this.divisionLastUpdate = divisionLastUpdate;
    }

    public String getDivisionLastUpdatedBy() {
        return divisionLastUpdatedBy;
    }

    public void setDivisionLastUpdatedBy(String divisionLastUpdatedBy) {
        this.divisionLastUpdatedBy = divisionLastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
