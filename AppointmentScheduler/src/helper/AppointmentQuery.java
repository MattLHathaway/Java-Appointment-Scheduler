package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.TimeUtility;
import model.Appointment;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class allows us to pull Appointment Information from our database.
 */
public abstract class AppointmentQuery {

    /**
     * This inserts an Appointment into the DB.
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static int insert(int appointmentID, String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);      //Must NOT match another ID
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, type);
        ps.setString(6, start);           //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(7, end);             //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(8, createDate);      //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(9, createdBy);
        ps.setString(10, lastUpdate);     //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(11, lastUpdatedBy);
        ps.setInt(12, customerID);        //Must match a valid customer ID
        ps.setInt(13, userID);            //Must match a valid user ID
        ps.setInt(14, contactID);         //Must match a valid contact ID
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This function updates a current Appointment using its ID.
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerID
     * @param userID
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static int update(int appointmentID, String title, String description, String location, String type, String start, String end, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);           //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(6, end);             //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(7, createDate);      //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(8, createdBy);
        ps.setString(9, lastUpdate);      //Date Format must be like "2022-06-28 13:00:00"
        ps.setString(10, lastUpdatedBy);
        ps.setInt(11, customerID);        //Must match a valid customer ID
        ps.setInt(12, userID);            //Must match a valid user ID
        ps.setInt(13, contactID);         //Must match a valid contact ID
        ps.setInt(14, appointmentID);     //Must NOT match another ID
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This function deletes an Appointment by its ID.
     * @param appointmentID
     * @return
     * @throws SQLException
     */
    public static int deleteByID(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This function gets an ObservableList of All Appointments.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentList() throws SQLException {
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String end = rs.getString("End");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, title, description, location, type, TimeUtility.convertUTCtoLocal(start), TimeUtility.convertUTCtoLocal(end), TimeUtility.convertUTCtoLocal(createDate), createdBy, TimeUtility.convertUTCtoLocal(lastUpdate), lastUpdatedBy, customerID, userID, contactID);
            AppointmentList.add(appointment);
        }
        return AppointmentList;
    }
    //2022-07-20 14:10:00
    //2022-07-20 15:20:00

    public static boolean doesAppointmentOverlap(String possibleAppointmentStartDateTime, String possibleAppointmentEndDateTime, int inputApptID) throws SQLException {
        System.out.println("Inside doesAppointmentOverlap");
        System.out.println(possibleAppointmentStartDateTime);
        System.out.println(possibleAppointmentEndDateTime);
        boolean overlaps = false;
        String sql = "select count(*) as cnt from (\n" +
                "select Appointment_ID FROM appointments\n" +
                "WHERE '" + possibleAppointmentStartDateTime + "' < end\n" +
                "  AND '" + possibleAppointmentEndDateTime + "' > start\n" +
                "  AND Customer_ID = " + inputApptID + " \n" +
                ") t";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        System.out.println(inputApptID);
        System.out.println(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int cnt = rs.getInt("cnt");
            if (cnt >= 1) {
                overlaps = true;
            }
        }
        System.out.println(overlaps);
        return overlaps;
    }

    public static boolean doesAppointmentOverlapForModification(String possibleAppointmentStartDateTime, String possibleAppointmentEndDateTime, int inputCustID, int inputApptID) throws SQLException {
        System.out.println("Inside doesAppointmentOverlap");
        System.out.println(possibleAppointmentStartDateTime);
        System.out.println(possibleAppointmentEndDateTime);
        boolean overlaps = false;
        String sql = "select count(*) as cnt from (\n" +
                "select Appointment_ID FROM appointments\n" +
                "WHERE '" + possibleAppointmentStartDateTime + "' < end\n" +
                "  AND '" + possibleAppointmentEndDateTime + "' > start\n" +
                "  AND Customer_ID = " + inputCustID + " \n" +
                "  AND Appointment_ID != " + inputApptID + " \n" +
                ") t";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        System.out.println(inputCustID);
        System.out.println(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int cnt = rs.getInt("cnt");
            if (cnt >= 1) {
                overlaps = true;
            }
        }
        System.out.println(overlaps);
        return overlaps;
    }


    public static boolean checkAppointmentsWithinFifteenMinutes() throws SQLException {

        boolean withinFifteenMinutes = false;
        String sql = "select count(*) as num_recs from (\n" +
                "select appointment_id\n" +
                "from appointments \n" +
                "where start between sysdate() and (sysdate() + INTERVAL 15 MINUTE) group by Appointment_ID\n" +
                ") t";
        System.out.println(sql);
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int num_recs = rs.getInt("num_recs");
            System.out.println(num_recs);
            if (num_recs >= 1) {
                System.out.println("num_recs >= 1");
                withinFifteenMinutes = true;
            }
        }
        System.out.println(withinFifteenMinutes);
        return withinFifteenMinutes;
    }

    /**
     * This function gives a list of all currently taken Appointment Start times by Date.
     * @param inputDate
     * @return
     * @throws SQLException
     */
    public static ObservableList<String> getTakenStartTimeListByDate(String inputDate) throws SQLException {
        ObservableList<String> AppointmentList = FXCollections.observableArrayList();
        String sql = "SELECT time(Start) AS start_date FROM appointments WHERE DATE(Start) BETWEEN '?' AND '? 23:59:59';";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, inputDate);
        ps.setString(2, inputDate);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String end = rs.getString("End");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            AppointmentList.add((appointment.getStartTime().substring(appointment.getStartTime().length()-8)).substring(0,5)); //Formatting to just 12:00
        }
        return AppointmentList;
    }

    /**
     * This function gets an ObservableList of Appointments by Week and is used to filter the MainMenu file in the controller folder.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentListByWeek() throws SQLException {
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE YEARWEEK(Start) = YEARWEEK(NOW());";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String end = rs.getString("End");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            AppointmentList.add(appointment);
        }
        return AppointmentList;
    }

    /**
     * This function returns an ObservableList of allAppointments by month and is used to filter the MainMenu page in the controller folder.
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentListByMonth() throws SQLException {
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE MONTH(Start)=MONTH(now()) AND YEAR(Start)=YEAR(now());";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String end = rs.getString("End");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            AppointmentList.add(appointment);
        }
        return AppointmentList;
    }

    /**
     * This function gets an ObservableList of Appointments by a specific month.  This is used for the Reports page.
     * @param inputMonth
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentListBySpecifiedMonth(int inputMonth) throws SQLException {
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE MONTH(Start)=? AND YEAR(Start)=YEAR(now());";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, inputMonth);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String end = rs.getString("End");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            AppointmentList.add(appointment);
        }
        return AppointmentList;
    }

    /**
     * This function returns an ObservableList of Appointments by CustomerID.
     * @param inputID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentListByCustomerID(int inputID) throws SQLException {
        ObservableList<Appointment> appointmentListByID = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, inputID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String end = rs.getString("End");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            appointmentListByID.add(appointment);
        }
        return appointmentListByID;
    }

    /**
     * This function returns an ObservableList of all Appointments by Contact ID.
     * @param inputID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentListByContactID(int inputID) throws SQLException {
        ObservableList<Appointment> appointmentListByID = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, inputID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String end = rs.getString("End");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            appointmentListByID.add(appointment);
        }
        return appointmentListByID;
    }

    /**
     * This function returns an ObservableList of all Appointments by Type.
     * @param inputType
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getByType(String inputType) throws SQLException {
        ObservableList<Appointment> appointmentListByID = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, inputType);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String end = rs.getString("End");
            String createDate = rs.getString("Create_Date");
            String createdBy = rs.getString("Created_By");
            String lastUpdate = rs.getString("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            Appointment appointment = new Appointment(appointmentID, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
            appointmentListByID.add(appointment);
        }
        return appointmentListByID;
    }
}
