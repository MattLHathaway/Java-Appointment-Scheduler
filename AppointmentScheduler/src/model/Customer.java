package model;

/**
 * The purpose of this class is to model Customer data.
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String createdDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;

    /**
     * This is our primary constructor.
     * @param customerID
     * @param customerName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param createdDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param divisionID
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phoneNumber, String createdDate, String createdBy, String lastUpdate, String lastUpdatedBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    /**
     * This is our secondary constructor.
     */
    public Customer() {

    }
    //GETTERS & SETTERS

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
     * get CustomerName
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * set CustomerName
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * get Address
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * set Address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get PostalCode
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * set PostalCode
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * get PhoneNumber
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * set PhoneNumber
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * get CreatedDate
     * @return
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * set CreatedDate
     * @param createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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
}
