package model;

/**
 * The purpose of this class is to model Appointment data.
 */
public class Appointment {
    private int apptID;
    private String title;
    private String description;
    private String location;
    private String type;
    private String startTime;
    private String endTime;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * This is our primary constructor.
     * @param apptID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startTime
     * @param endTime
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     */
    public Appointment(int apptID, String title, String description, String location, String type, String startTime, String endTime, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.apptID = apptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * This is our secondary constructor.
     */
    public Appointment() {

    }


    //GETTERS & SETTERS

    /**
     * get ApptID
     * @return
     */
    public int getApptID() {
        return apptID;
    }

    /**
     * Set ApptID
     * @param apptID
     */
    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    /**
     * get Title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * set Title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * get Description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * setDescription
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get Location
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * set Location
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get Type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Set Type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * get StartTime
     * @return
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Set StartTime
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * get EndTime
     * @return
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Set EndTime
     * @param endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Get CreateDate
     * @return
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * set CreateDate
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * get CreatedBy
     * @return
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * set CreatedBy
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * get LastUpdate
     * @return
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * set LastUpdate
     * @param lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * get LastUpdatedBy
     * @return
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * set LastUpdatedBy
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * get CustomerID
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * set CustomerID
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Get UserID
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * set UserID
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * get ContactID
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * set ContactID
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
