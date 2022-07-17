package model;

/**
 * The purpose of this class is to model Contact data.
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * This is our primary constructor.
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * This is our secondary constructor.
     */
    public Contact() {

    }

    //GETTERS & SETTERS

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

    /**
     * get ContactName
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * set ContactName
     * @param contactName
     */

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * get ContactEmail
     * @return
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * set ContactEmail
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
