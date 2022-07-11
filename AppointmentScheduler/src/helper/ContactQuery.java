package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactQuery {

    public static int addContact(int contactID, String contactName, String contactEmail) throws SQLException {
        String sql = "INSERT INTO contacts (Contact_ID, Contact_Name, Email) VALUES (?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);               //Must NOT match another ID
        ps.setString(2, contactName);
        ps.setString(3, contactEmail);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int updateContactByID(int contactID, String contactName, String contactEmail) throws SQLException {
        String sql = "UPDATE contacts Contact_Name = ?, Email = ? WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, contactName);
        ps.setString(2, contactEmail);
        ps.setInt(3, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int deleteContactByID(int contactID) throws SQLException {
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

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

    public static Contact getByID(int contID) throws SQLException {
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

}
