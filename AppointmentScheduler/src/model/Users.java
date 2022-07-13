package model;

public class Users {
    private int userID;
    private String userName;
    private String userPassword;
    private String userCreateDate;
    private String userCreatedBy;
    private String userLastUpdate;
    private String userLastUpdatedBy;

    public Users(int userID, String userName, String userPassword, String userCreateDate, String userCreatedBy, String userLastUpdate, String userLastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userCreateDate = userCreateDate;
        this.userCreatedBy = userCreatedBy;
        this.userLastUpdate = userLastUpdate;
        this.userLastUpdatedBy = userLastUpdatedBy;
    }

    public Users() {

    }

    //GETTERS & SETTERS

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCreateDate() {
        return userCreateDate;
    }

    public void setUserCreateDate(String userCreateDate) {
        this.userCreateDate = userCreateDate;
    }

    public String getUserCreatedBy() {
        return userCreatedBy;
    }

    public void setUserCreatedBy(String userCreatedBy) {
        this.userCreatedBy = userCreatedBy;
    }

    public String getUserLastUpdate() {
        return userLastUpdate;
    }

    public void setUserLastUpdate(String userLastUpdate) {
        this.userLastUpdate = userLastUpdate;
    }

    public String getUserLastUpdatedBy() {
        return userLastUpdatedBy;
    }

    public void setUserLastUpdatedBy(String userLastUpdatedBy) {
        this.userLastUpdatedBy = userLastUpdatedBy;
    }
}
