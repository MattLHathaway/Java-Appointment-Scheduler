package controller;

import helper.AppointmentQuery;
import helper.ContactQuery;
import helper.CustomerQuery;
import helper.UsersQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.TimeUtility;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import static helper.AppointmentQuery.doesAppointmentOverlap;

/**
 * This class is the controller for the AddAppointmentScreen.fxml.  It's main purpose is to allow the user to add new
 * Appointments and customize the data that comprises said new Appointment.
 */
public class AddAppointmentScreenController implements Initializable {
    public TextField addApptIDField;
    public DatePicker addApptStartDatePicker;
    public Button addApptSaveButton;
    public TextField addApptTitleField;
    public TextField addApptDescriptionField;
    public TextField addApptLocationField;
    public TextField addApptTypeField;
    public DatePicker addApptEndDatePicker;
    public ChoiceBox addApptContactChoicebox;
    public Button addApptCancelButton;
    public ChoiceBox addApptCustomerIDChoicebox;
    public ChoiceBox addApptUserIDCheckbox;
    public ChoiceBox addApptEndTimeChoicebox;
    public ChoiceBox addApptStartTimeChoicebox;

    /**
     * Upon initialization, the choicebox options are set so that the User may correctly choose between available times.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillChoiceBoxOptions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        return !start1.after(end2) && !start2.after(end1);
    }

    /**
     * Triggered once the save button is clicked, this function saves all user input data as a new Appointment.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void saveButtonPressed(ActionEvent event) throws IOException, SQLException, ParseException {
        //Variables and Time String Formatting
        int uniqueID = createUniqueAppointmentID();
        String formattedStartTime = addApptStartDatePicker.getValue() + " " + addApptStartTimeChoicebox.getValue() + ":00";
        String formattedEndTime = addApptEndDatePicker.getValue() + " " + addApptEndTimeChoicebox.getValue() + ":00";
        String formattedCreateTime = java.time.LocalDate.now() + " " + java.time.LocalTime.now();

        String formattedStartTimeToUTC = TimeUtility.convertToUTCfromEST(formattedStartTime);
        String formattedEndTimeToUTC = TimeUtility.convertToUTCfromEST(formattedEndTime);
        String formattedCreateTimeUTC = TimeUtility.convertToUTCfromEST(formattedCreateTime);

        //Turning Names into IDs for the Table
        String customerID = (String) addApptCustomerIDChoicebox.getValue();
        int customerIdByName = CustomerQuery.getIDbyName(String.valueOf(customerID));

        String usersID = (String) addApptUserIDCheckbox.getValue();
        int usersIdByName = UsersQuery.getIdByName(String.valueOf(usersID));

        String contID = (String) addApptContactChoicebox.getValue();
        int contactIdByName = ContactQuery.getIdByName(String.valueOf(contID));

        //Store Input into Appointment Object
        Appointment newAppointment = new Appointment(uniqueID,
                addApptTitleField.getText(),
                addApptDescriptionField.getText(),
                addApptLocationField.getText(),
                addApptTypeField.getText(),
                formattedStartTimeToUTC,
                formattedEndTimeToUTC,
                formattedCreateTimeUTC,
                "script",
                formattedCreateTime,
                "script",
                customerIdByName,
                usersIdByName,
                contactIdByName
        );

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean overlaps = false;

        String newAppointmentStartTime = newAppointment.getStartTime();
        String newAppointmentEndTime = newAppointment.getEndTime();
        int newAppointmentCustomerID = newAppointment.getCustomerID();

        System.out.println("About to make Call");
        overlaps = doesAppointmentOverlap(newAppointmentStartTime, newAppointmentEndTime, newAppointmentCustomerID);
        System.out.println("Made the call");

        if (!overlaps) {
            //Add Object to DB
            AppointmentQuery.insert(newAppointment.getApptID(),
                    newAppointment.getTitle(),
                    newAppointment.getDescription(),
                    newAppointment.getLocation(),
                    newAppointment.getType(),
                    newAppointment.getStartTime(),
                    newAppointment.getEndTime(),
                    newAppointment.getCreateDate(),
                    newAppointment.getCreatedBy(),
                    newAppointment.getLastUpdate(),
                    newAppointment.getLastUpdatedBy(),
                    newAppointment.getCustomerID(),
                    newAppointment.getUserID(),
                    newAppointment.getContactID());

            //Switch Screen Logic
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            AlertMessageController.partError(5, null);
        }
    }

    /**
     * This function creates a unique ID using the sum of all other current appointment IDs.
     * @return
     * @throws SQLException
     */
    public int createUniqueAppointmentID() throws SQLException {
        ObservableList<Appointment> apptList = AppointmentQuery.getAppointmentList();
        ObservableList<String> allAppointmentIDs = FXCollections.observableArrayList();
        apptList.forEach(Appointment -> allAppointmentIDs.add(Integer.toString(Appointment.getApptID())));
        int sumVal = 0;
        for(int i = 0; i < allAppointmentIDs.size(); i++) {
            sumVal = Integer.parseInt(String.valueOf(allAppointmentIDs.get(i))) + sumVal;
        }
        return sumVal;
    }

    /**
     * This fills the choiceboxes with appropriate options only, preventing the user from entering incorrect information.
     * @throws SQLException
     */
    public void fillChoiceBoxOptions() throws SQLException {

        //PULLING Customer Names
        ObservableList<Customer> custList = CustomerQuery.getCustomerList();
        ObservableList<String> allCustomerNames = FXCollections.observableArrayList();
        custList.forEach(Customer -> allCustomerNames.add(Customer.getCustomerName()));
        addApptCustomerIDChoicebox.setItems(allCustomerNames);

        //Pulling Usernames of ALL users
        ObservableList<Users> userList = UsersQuery.getAllUsers();
        ObservableList<String> allUserNames = FXCollections.observableArrayList();
        userList.forEach(Users -> allUserNames.add(Users.getUserName()));
        addApptUserIDCheckbox.setItems(allUserNames);

        //Pulling Contact Names of ALL contacts
        ObservableList<Contact> contactList = ContactQuery.getContactList();
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        contactList.forEach(Contact -> allContactNames.add(Contact.getContactName()));
        addApptContactChoicebox.setItems(allContactNames);

        //Adding All existing Appointment Times
        Double offsetToUTC = TimeUtility.getTimeOffset();
        int offsetToEST = offsetToUTC.intValue();
        offsetToEST = offsetToEST - 4;

        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        LocalTime firstAppointment = LocalTime.MIN.plusHours(8); //8am EST
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45); //10pm EST

        if (!firstAppointment.equals(0) || !lastAppointment.equals(0)) {
            while (firstAppointment.isBefore(lastAppointment)) {
                appointmentTimes.add(String.valueOf(firstAppointment));
                firstAppointment = firstAppointment.plusMinutes(15);
            }
        }
        //Filtering out unavailable appointments !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        ObservableList<String> availableAppointments = FXCollections.observableArrayList();

        addApptStartTimeChoicebox.setItems(appointmentTimes);
        addApptEndTimeChoicebox.setItems(appointmentTimes);

    }

    /**
     * This switches screens.
     * @param event
     * @throws IOException
     */
    public void cancelButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
