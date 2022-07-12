package controller;

import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class MainMenu implements Initializable {

    public Button logoutButton;
    public Label AppointmentScheduleLabel;
    public RadioButton viewByWeekRadio;
    public RadioButton viewByMonthRadio;
    public RadioButton viewAllRadio;
    public TableView<Appointment> table;
    public TableColumn apptIDCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn customerIDCol;
    public TableColumn userIDCol;
    public Label timeZoneLabel;
    public Button reportsButton;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public Label timeZoneText;
    public Button customersButton;
    public TextField apptIDField;
    public TextField apptTitleField;
    public TextField apptDescriptionField;
    public TextField apptLocationField;
    public TextField apptTypeField;
    public TextField apptCustomerIDField;
    public DatePicker apptStartDatePicker;
    public DatePicker apptEndDatePicker;
    public ChoiceBox apptContactChoicebox;
    public TextField apptUserIDField;
    public TableColumn startDateTimeCol;
    public TableColumn endDateTimeCol;
    public TextField apptStartTimeField;
    public TextField apptEndTimeField;
    public ChoiceBox apptUserIDChoicebox;
    public ChoiceBox apptCustomerIDChoicebox;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("We are at the Main Menu!");
        setTimeZoneText();
        try {
            populateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onDeleteButtonPressed(ActionEvent event) throws Exception {
        //Check for selected appointment from appt table
        if (table.getSelectionModel() != null) {
            //Create "ARE YOU SURE?" Alert Box
            ButtonType delete = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Are you sure you want to Delete the Appointment?",
                    delete,
                    cancel);
            alert.setTitle("Delete Warning");
            Optional<ButtonType> result = alert.showAndWait();
            //Deletes Appt from DB if delete button is clicked
            if (result.orElse(cancel) == delete) {
                int deleteApptID = table.getSelectionModel().getSelectedItem().getApptID();
                AppointmentQuery.deleteByID(deleteApptID);
            }
        }
        //Refresh the Table
        populateTable();

        //Clear the modification fields
        apptIDField.setText("");
        apptTitleField.setText("");
        apptDescriptionField.setText("");
        apptLocationField.setText("");
        apptTypeField.setText("");
        apptCustomerIDChoicebox.setValue(null);
        apptStartDatePicker.setValue(null);
        apptStartTimeField.setText("");
        apptContactChoicebox.setValue(null);
        apptEndDatePicker.setValue(null);
        apptEndTimeField.setText("");
        apptUserIDChoicebox.setValue(null);
    }

    public void onModifyAppointmentPressed(ActionEvent event) throws IOException {
        //Store selected appt as object
        Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();

    }

    public void populateTable() throws SQLException {
        ObservableList<Appointment> appointmentsList = AppointmentQuery.getAppointmentList();

        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        table.setItems(appointmentsList);

        //Set Table to be Clickable
        table.setOnMouseClicked(event ->{
            if (table.getSelectionModel() != null) {

                //Grabbing the selected Appointment as an Object
                Appointment selectedAppt = new Appointment();
                selectedAppt = table.getSelectionModel().getSelectedItem();

                //Variables for formatting
                String startRaw = selectedAppt.getStartTime();
                String startTimeFormatted = startRaw.substring(startRaw.length()-8);
                String startDateFormatted = startRaw.substring(0, 10);
                String endRaw = selectedAppt.getEndTime();
                String endTimeFormatted = endRaw.substring(endRaw.length()-8);
                String endDateFormatted = endRaw.substring(0, 10);

                //Filling Choicebox Options
                    //PULLING Customer IDs of ALL customers
                ObservableList<Customer> custList = null;
                try {
                    custList = CustomerQuery.getCustomerList();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ObservableList<String> allCustomerIDs = FXCollections.observableArrayList();
                custList.forEach(Customer -> allCustomerIDs.add(Integer.toString(Customer.getCustomerID())));
                apptCustomerIDChoicebox.setItems(allCustomerIDs);
                    //Pulling User IDs of ALL users
                ObservableList<Users> userList = null;
                try {
                    userList = UsersQuery.getAllUsers();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ObservableList<String> allUserIDs = FXCollections.observableArrayList();
                userList.forEach(Users -> allUserIDs.add(Integer.toString(Users.getUserID())));
                apptUserIDChoicebox.setItems(allUserIDs);
                    //Pulling Contact IDs of ALL contacts
                ObservableList<Contact> contactList = null;
                try {
                    contactList = ContactQuery.getContactList();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ObservableList<String> allContactIDs = FXCollections.observableArrayList();
                contactList.forEach(Contact -> allContactIDs.add(Integer.toString(Contact.getContactID())));
                apptContactChoicebox.setItems(allContactIDs);

                //Filling the Modification Fields with Selected Appointments' Data
                apptIDField.setText(String.valueOf(selectedAppt.getApptID()));
                apptTitleField.setText(selectedAppt.getTitle());
                apptDescriptionField.setText(selectedAppt.getDescription());
                apptLocationField.setText(selectedAppt.getLocation());
                apptTypeField.setText(selectedAppt.getType());
                apptCustomerIDChoicebox.setValue(String.valueOf(selectedAppt.getCustomerID()));
                apptStartDatePicker.setValue(LocalDate.parse(startDateFormatted));
                apptStartTimeField.setText(startTimeFormatted);
                apptContactChoicebox.setValue(String.valueOf(selectedAppt.getContactID()));
                apptEndDatePicker.setValue(LocalDate.parse(endDateFormatted));
                apptEndTimeField.setText(endTimeFormatted);
                apptUserIDChoicebox.setValue(String.valueOf(selectedAppt.getUserID()));

            }
        });
    }

    public void setTimeZoneText() {
        TimeZone tz = TimeZone.getDefault();
        timeZoneText.setText(tz.getID());
    }

    public void CustomerNavButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void addAppointmentButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void logoutPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
