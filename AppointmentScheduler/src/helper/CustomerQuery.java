package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerQuery {

    public static int addCustomer(int customerID, String customerName, String address, String postalCode, String phoneNumber, String createdDate, String createdBy, String lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);               //Must NOT match another ID
        ps.setString(2, customerName);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phoneNumber);
        ps.setString(6, createdDate);           //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(7, createdBy);             //script
        ps.setString(8, lastUpdate);            //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(9, lastUpdatedBy);         //script
        ps.setInt(10, divisionID);              //only specific IDs allowed matching divisions
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int updateCustomer(int customerID, String customerName, String address, String postalCode, String phoneNumber, String createdDate, String createdBy, String lastUpdate, String lastUpdatedBy, int divisionID) throws SQLException {
        String sql = "UPDATE customers Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setString(5, createdDate);           //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(6, createdBy);             //script
        ps.setString(7, lastUpdate);            //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(8, lastUpdatedBy);         //script
        ps.setInt(9, divisionID);               //only specific IDs allowed matching divisions
        ps.setInt(10, customerID);              //Must NOT match another ID
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int deleteCustomerByID(int customerID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static ObservableList<Customer> getCustomerList() throws SQLException {
        ObservableList<Customer> CustomerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            String createdDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");
            Customer customer = new Customer(customerID, customerName, address, postalCode, phoneNumber, createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
            CustomerList.add(customer);
        }
        return CustomerList;
    }

    public static Customer getByID(int custID) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        Customer selectedCustomer = new Customer();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, custID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            String createdDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionID = rs.getInt("Division_ID");
            Customer customer = new Customer(customerID, customerName, address, postalCode, phoneNumber, createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
            selectedCustomer = customer;
        }
        return selectedCustomer;
    }

}
