package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to get Contact information from the Database.
 */
public abstract class ContactQuery {

    /**
     * This function allows you to add a Contact.
     * @param contactID
     * @param contactName
     * @param contactEmail
     * @return
     * @throws SQLException
     */
    public static int addContact(int contactID, String contactName, String contactEmail) throws SQLException {
        String sql = "INSERT INTO contacts (Contact_ID, Contact_Name, Email) VALUES (?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);               //Must NOT match another ID
        ps.setString(2, contactName);
        ps.setString(3, contactEmail);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This function allows you to update a Contact.
     * @param contactID
     * @param contactName
     * @param contactEmail
     * @return
     * @throws SQLException
     */
    public static int updateContactByID(int contactID, String contactName, String contactEmail) throws SQLException {
        String sql = "UPDATE contacts Contact_Name = ?, Email = ? WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ps.setString(2, contactEmail);
        ps.setInt(3, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This function allows you to delete a Contact.
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static int deleteContactByID(int contactID) throws SQLException {
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This function allows you to get a List of contacts.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Contact> getContactList() throws SQLException {
        ObservableList<Contact> ContactList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            ContactList.add(contact);
        }
        return ContactList;
    }

    /**
     * This function allows you to get a List of contact name by ID.
     * @param contID
     * @return
     * @throws SQLException
     */
    public static String getContactNameByID(int contID) throws SQLException {
        Contact selectedContact = new Contact();
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            selectedContact = contact;
        }
        String output = selectedContact.getContactName();
        return output;
    }

    /**
     * This function allows you to get a List of contacts by ID.
     * @param contID
     * @return
     * @throws SQLException
     */
    public static Contact getObjectByID(int contID) throws SQLException {
        Contact selectedContact = new Contact();
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            selectedContact = contact;
        }
        return selectedContact;
    }

    /**
     * This function allows you to get a contact ID by name.
     * @param contName
     * @return
     * @throws SQLException
     */
    public static int getIdByName(String contName) throws SQLException {
        Contact selectedContact = new Contact();
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contName);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            selectedContact = contact;
        }
        int output = selectedContact.getContactID();
        return output;
    }

}
