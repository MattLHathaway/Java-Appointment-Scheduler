package model;

/**
 * The purpose of this class is to model Users data.
 */
public class Users {
    private int userID;
    private String userName;
    private String userPassword;
    private String userCreateDate;
    private String userCreatedBy;
    private String userLastUpdate;
    private String userLastUpdatedBy;

    /**
     * This is our primary constructor.
     * @param userID
     * @param userName
     * @param userPassword
     * @param userCreateDate
     * @param userCreatedBy
     * @param userLastUpdate
     * @param userLastUpdatedBy
     */
    public Users(int userID, String userName, String userPassword, String userCreateDate, String userCreatedBy, String userLastUpdate, String userLastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userCreateDate = userCreateDate;
        this.userCreatedBy = userCreatedBy;
        this.userLastUpdate = userLastUpdate;
        this.userLastUpdatedBy = userLastUpdatedBy;
    }

    /**
     * This is our secondary constructor.
     */
    public Users() {

    }
    //GETTERS & SETTERS

    /**
     * get UserID
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
     * get UserName
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set UserName
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * get UserPassword
     * @return
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * set UserPassword
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * get UserCreateDate
     * @return
     */
    public String getUserCreateDate() {
        return userCreateDate;
    }

    /**
     * set UserCreateDate
     * @param userCreateDate
     */
    public void setUserCreateDate(String userCreateDate) {
        this.userCreateDate = userCreateDate;
    }

    /**
     * get UserCreatedBy
     * @return
     */
    public String getUserCreatedBy() {
        return userCreatedBy;
    }

    /**
     * set UserCreatedBy
     * @param userCreatedBy
     */
    public void setUserCreatedBy(String userCreatedBy) {
        this.userCreatedBy = userCreatedBy;
    }

    /**
     * get UserLastUpdate
     * @return
     */
    public String getUserLastUpdate() {
        return userLastUpdate;
    }

    /**
     * set UserLastUpdate
     * @param userLastUpdate
     */
    public void setUserLastUpdate(String userLastUpdate) {
        this.userLastUpdate = userLastUpdate;
    }

    /**
     * get UserLastUpdatedBy
     * @return
     */
    public String getUserLastUpdatedBy() {
        return userLastUpdatedBy;
    }

    /**
     * set UserLastUpdatedBy
     * @param userLastUpdatedBy
     */
    public void setUserLastUpdatedBy(String userLastUpdatedBy) {
        this.userLastUpdatedBy = userLastUpdatedBy;
    }
}
