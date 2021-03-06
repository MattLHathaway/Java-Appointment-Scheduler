package controller;

import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.TimeUtility;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * The MainMenu is for displaying Appointments.  This is where the Login form takes you.  The purpose is to display
 * all appointments in local timezones and allow the user to navigate through the rest of the program as needed.
 */
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
    public DatePicker apptStartDatePicker;
    public DatePicker apptEndDatePicker;
    public ChoiceBox apptContactChoicebox;
    public TableColumn startDateTimeCol;
    public TableColumn endDateTimeCol;
    public ChoiceBox apptUserIDChoicebox;
    public ChoiceBox apptCustomerIDChoicebox;
    public ChoiceBox startTimeChoiceBox;
    public ChoiceBox endTimeChoicebox;
    public Label customMessageLabel;

    /**
     * On initialization we ensure that the table is populated and no custom messages are currently displaying.
     * @param url
     * @param resourceBundle
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("We are at the Main Menu!");
        customMessageLabel.setText("");
        setTimeZoneText();
        try {
            populateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This triggers when the user clicks on the Modify Button.  Any changes the user made to the modifiable appointment
     * text fields is applied to the appointments in the database.  It does ask if you are sure before allowing the
     * permanent changes to be applied.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void onModifyButtonPressed(ActionEvent event) throws IOException, SQLException {
        //Check for selected appointment from appt table
        if (table.getSelectionModel() != null) {
            //Create "ARE YOU SURE?" Alert Box
            ButtonType modify = new ButtonType("Modify", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Are you sure you want to Modify the Appointment?",
                    modify,
                    cancel);
            alert.setTitle("Update Warning");
            Optional<ButtonType> result = alert.showAndWait();
            //Modifies Appt in DB if modify button is clicked
            if (result.orElse(cancel) == modify) {
                //Grab Original Info to be Modified
                Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();

                //Variables for formatting
                String startFormatted = apptStartDatePicker.getValue() + " " + startTimeChoiceBox.getValue() + ":00";
                String endFormatted = apptEndDatePicker.getValue() + " " + endTimeChoicebox.getValue() + ":00";

                String startFormattedUTC = TimeUtility.convertToUTC(startFormatted);
                String endFormattedUTC = TimeUtility.convertToUTC(endFormatted);

                //Translating the Usernames into IDs for DB storage
                String customerID = (String) apptCustomerIDChoicebox.getValue();
                int customerIdByName = CustomerQuery.getIDbyName(String.valueOf(customerID));

                String usersID = (String) apptUserIDChoicebox.getValue();
                int usersIdByName = UsersQuery.getIdByName(String.valueOf(usersID));

                String contID = (String) apptContactChoicebox.getValue();
                int contactIdByName = ContactQuery.getIdByName(String.valueOf(contID));

                //Grab Info to be Updated and add it to temporary Appointment object
                selectedAppointment.setTitle(apptTitleField.getText());
                selectedAppointment.setDescription(apptDescriptionField.getText());
                selectedAppointment.setLocation(apptLocationField.getText());
                selectedAppointment.setType(apptTypeField.getText());
                selectedAppointment.setCustomerID(customerIdByName);
                selectedAppointment.setStartTime(startFormattedUTC);
                selectedAppointment.setEndTime(endFormattedUTC);
                selectedAppointment.setUserID(contactIdByName);
                selectedAppointment.setContactID(usersIdByName);

                System.out.println(selectedAppointment.getStartTime());
                //Check for overlap
                boolean overlaps = false;
                String modifiedAppointmentStartTime = selectedAppointment.getStartTime();
                String modifiedAppointmentEndTime = selectedAppointment.getEndTime();
                int modifiedAppointmentCustomerID = selectedAppointment.getCustomerID();
                int modifiedAppointmentApptID = selectedAppointment.getApptID();

                overlaps = AppointmentQuery.doesAppointmentOverlapForModification(modifiedAppointmentStartTime, modifiedAppointmentEndTime, modifiedAppointmentCustomerID, modifiedAppointmentApptID);

                if (!overlaps) {
                    //Push updated Info to DB
                    AppointmentQuery.update(selectedAppointment.getApptID(),
                            selectedAppointment.getTitle(),
                            selectedAppointment.getDescription(),
                            selectedAppointment.getLocation(),
                            selectedAppointment.getType(),
                            selectedAppointment.getStartTime(),
                            selectedAppointment.getEndTime(),
                            selectedAppointment.getCreateDate(),
                            selectedAppointment.getCreatedBy(),
                            selectedAppointment.getLastUpdate(),
                            selectedAppointment.getLastUpdatedBy(),
                            selectedAppointment.getCustomerID(),
                            selectedAppointment.getUserID(),
                            selectedAppointment.getContactID());

                    System.out.println("Appointment Modified!");

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
        startTimeChoiceBox.setValue(null);
        apptContactChoicebox.setValue(null);
        apptEndDatePicker.setValue(null);
        endTimeChoicebox.setValue(null);
        apptUserIDChoicebox.setValue(null);
    }

    /**
     * This allows the user to delete an appointment.  The program displays a custom message on the user interface when
     * you do.
     * @param event
     * @throws Exception
     */
    public void onDeleteButtonPressed(ActionEvent event) throws Exception {
        //Check for selected appointment from appt table
        String appointmentID = String.valueOf(table.getSelectionModel().getSelectedItem().getApptID());
        String appointmentType = table.getSelectionModel().getSelectedItem().getType();
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
                customMessageLabel.setText("Deleted Appointment: ID: " + appointmentID + " Type: " + appointmentType);
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
        startTimeChoiceBox.setValue(null);
        apptContactChoicebox.setValue(null);
        apptEndDatePicker.setValue(null);
        endTimeChoicebox.setValue(null);
        apptUserIDChoicebox.setValue(null);
    }

    /**
     * This is a recognizable name function that just triggers the populateTable function.
     * @param event
     * @throws SQLException
     */
    public void radioCheck(ActionEvent event) throws SQLException {
        populateTable();
    }

    /**
     * This function triggers any time changes are made to the table and on initialization of the page.  The goal is to
     * ensure correct data in the table at all times and allow for filtering/modification of Appointment Data.  This
     * function also the ability to set the table to be clickable and thereby filling the modification fields with the
     * data that was clicked.
     * @throws SQLException
     */
    public void populateTable() throws SQLException {

        //Define Filter Radio/ChoiceBoxes
        RadioButton AllRB = (RadioButton) viewAllRadio;
        RadioButton weekRB = (RadioButton) viewByWeekRadio;
        RadioButton monthRB = (RadioButton) viewByMonthRadio;

        //CHECK FILTER
        if (AllRB.isSelected()) {
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
        } else if (weekRB.isSelected()) {
            //getAppointmentListByWeek
            ObservableList<Appointment> appointmentsList = AppointmentQuery.getAppointmentListByWeek();

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
        } else if (monthRB.isSelected()) {
            ObservableList<Appointment> appointmentsList = AppointmentQuery.getAppointmentListByMonth();

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
        }


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
                    //PULLING Customer Names of ALL customers
                ObservableList<Customer> custList = null;
                try {
                    custList = CustomerQuery.getCustomerList();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ObservableList<String> allCustomerNames = FXCollections.observableArrayList();
                custList.forEach(Customer -> allCustomerNames.add(Customer.getCustomerName()));
                apptCustomerIDChoicebox.setItems(allCustomerNames);
                    //Pulling User Names of ALL users
                ObservableList<Users> userList = null;
                try {
                    userList = UsersQuery.getAllUsers();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ObservableList<String> allUserNames = FXCollections.observableArrayList();
                userList.forEach(Users -> allUserNames.add(Users.getUserName()));
                apptUserIDChoicebox.setItems(allUserNames);
                    //Pulling Contact Names of ALL contacts
                ObservableList<Contact> contactList = null;
                try {
                    contactList = ContactQuery.getContactList();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ObservableList<String> allContactNames = FXCollections.observableArrayList();
                contactList.forEach(Contact -> allContactNames.add(Contact.getContactName()));
                apptContactChoicebox.setItems(allContactNames);


                //Filling the Modification Fields with Selected Appointments' Data
                apptIDField.setText(String.valueOf(selectedAppt.getApptID()));
                apptTitleField.setText(selectedAppt.getTitle());
                apptDescriptionField.setText(selectedAppt.getDescription());
                apptLocationField.setText(selectedAppt.getLocation());
                apptTypeField.setText(selectedAppt.getType());
                try {
                    apptCustomerIDChoicebox.setValue(CustomerQuery.getNameByID(selectedAppt.getCustomerID()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                apptStartDatePicker.setValue(LocalDate.parse(startDateFormatted));
                try {
                    apptContactChoicebox.setValue(ContactQuery.getContactNameByID(selectedAppt.getContactID()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                apptEndDatePicker.setValue(LocalDate.parse(endDateFormatted));
                try {
                    apptUserIDChoicebox.setValue(UsersQuery.getNameByID(selectedAppt.getUserID()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                //Filling available appointment times
                ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

                LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
                LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

                //if statement to prevent loops
                if (!firstAppointment.equals(0) || !lastAppointment.equals(0)) {
                    while (firstAppointment.isBefore(lastAppointment)) {
                        appointmentTimes.add(String.valueOf(firstAppointment));
                        firstAppointment = firstAppointment.plusMinutes(15);
                    }
                }
                startTimeChoiceBox.setItems(appointmentTimes);
                endTimeChoicebox.setItems(appointmentTimes);
                startTimeChoiceBox.setValue(startTimeFormatted.substring(0,5));
                endTimeChoicebox.setValue(endTimeFormatted.substring(0,5));

                customMessageLabel.setText("");
            }
        });
    }

    /**
     * This allows us to set the time zone visibly on the screen.
     */
    public void setTimeZoneText() {
        TimeZone tz = TimeZone.getDefault();
        timeZoneText.setText(tz.getID());
    }

    /**
     * This function allows the user to switch pages.
     * @param event
     * @throws IOException
     */
    public void CustomerNavButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This function allows the user to switch pages.
     * @param event
     * @throws IOException
     */
    public void addAppointmentButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * This function allows the user to switch pages.
     * @param event
     * @throws IOException
     */
    public void logoutPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This function allows the user to switch pages.
     * @param actionEvent
     * @throws IOException
     */
    public void reportButtonPressed(ActionEvent actionEvent) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
