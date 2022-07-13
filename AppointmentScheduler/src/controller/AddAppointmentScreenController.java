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
import java.util.ResourceBundle;
import java.util.UUID;

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
    public TextField addApptStartTimeField;
    public TextField addApptEndTimeField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillChoiceBoxOptions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

//        this.apptID = apptID;
//        this.title = title;
//        this.description = description;
//        this.location = location;
//        this.type = type;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.createDate = createDate;
//        this.createdBy = createdBy;
//        this.lastUpdate = lastUpdate;
//        this.lastUpdatedBy = lastUpdatedBy;
//        this.customerID = customerID;
//        this.userID = userID;
//        this.contactID = contactID;
    public void saveButtonPressed(ActionEvent event) throws IOException, SQLException {
        //Variables and Time String Formatting
        int uniqueID = createUniqueAppointmentID();
        String formattedStartTime = addApptStartDatePicker.getValue() + " " + addApptStartTimeField.getText();
        String formattedEndTime = addApptEndDatePicker.getValue() + " " + addApptEndTimeField.getText();
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

        //PULLING Customer IDs of ALL customers
//        ObservableList<Customer> custList = CustomerQuery.getCustomerList();
//        ObservableList<String> allCustomerIDs = FXCollections.observableArrayList();
//        custList.forEach(Customer -> allCustomerIDs.add(Integer.toString(Customer.getCustomerID())));
//        addApptCustomerIDChoicebox.setItems(allCustomerIDs);

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
