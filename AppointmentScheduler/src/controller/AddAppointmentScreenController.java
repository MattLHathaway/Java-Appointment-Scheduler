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
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillChoiceBoxOptions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void saveButtonPressed(ActionEvent event) throws IOException, SQLException {
        //Variables and Time String Formatting
        int uniqueID = createUniqueAppointmentID();
        String formattedStartTime = addApptStartDatePicker.getValue() + " " + addApptStartTimeChoicebox.getValue() + ":00";
        String formattedEndTime = addApptEndDatePicker.getValue() + " " + addApptEndTimeChoicebox.getValue() + ":00";
        String formattedCreateTime = java.time.LocalDate.now() + " " + java.time.LocalTime.now();

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
                formattedStartTime,
                formattedEndTime,
                formattedCreateTime,
                "script",
                formattedCreateTime,
                "script",
                customerIdByName,
                usersIdByName,
                contactIdByName
                );
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
        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        LocalTime firstAppointment = LocalTime.MIN.plusHours(8); //8am
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45); //10pm

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


    public void onStartDateChosen(ActionEvent event) throws SQLException {
        //Creating List of unavailable times (takenAppointments)
        String chosenDate = String.valueOf(addApptStartDatePicker.getValue());
        ObservableList<String> takenAppointments = AppointmentQuery.getTakenStartTimeListByDate(chosenDate);

        //Creating List of ALL times (appointmentTimes)
        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        LocalTime firstAppointment = LocalTime.MIN.plusHours(8); //8am
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45); //10pm
        if (!firstAppointment.equals(0) || !lastAppointment.equals(0)) {
            while (firstAppointment.isBefore(lastAppointment)) {
                appointmentTimes.add(String.valueOf(firstAppointment));
                firstAppointment = firstAppointment.plusMinutes(15);
            }
        }

        //Creating List of available times (availableAppointments)
        ObservableList<String> availableAppointments = FXCollections.observableArrayList();

        AtomicInteger i = new AtomicInteger();
        appointmentTimes.forEach(String -> {
            if(!Objects.equals(String, takenAppointments.get(i.get()))) {
                availableAppointments.add(String);
                i.getAndIncrement();
            }
        });

        //Assign Available Appointments to the choicebox
        addApptStartTimeChoicebox.setItems(availableAppointments);
        System.out.println("It worked?");
    }

    public void cancelButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
