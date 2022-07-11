package controller;

import helper.AppointmentQuery;
import helper.JDBC;
import helper.UsersQuery;
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
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class MainMenu implements Initializable {

    public Button logoutButton;
    public Label AppointmentScheduleLabel;
    public RadioButton viewByWeekRadio;
    public RadioButton viewByMonthRadio;
    public RadioButton viewAllRadio;
    public TableView table;
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
    public ChoiceBox apptStartTimeChoicebox;
    public TextField apptTitleField;
    public TextField apptDescriptionField;
    public TextField apptLocationField;
    public TextField apptTypeField;
    public TextField apptCustomerIDField;
    public DatePicker apptStartDatePicker;
    public ChoiceBox apptEndTimeChoicebox;
    public DatePicker apptEndDatePicker;
    public ChoiceBox apptContactChoicebox;
    public TextField apptUserIDField;
    public TableColumn startDateTimeCol;
    public TableColumn endDateTimeCol;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("We are at the Main Menu!");
        setTimeZoneText();
        try {
            populateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dummyAppointmentCreator() throws SQLException {
            AppointmentQuery.insert(3, "title", "description", "location", "Planning Session", "2020-05-28 12:00:00", "2020-05-28 13:00:00", "2022-07-06 15:12:56", "script", "2022-07-06 15:12:56", "script", 2, 2, 2);
            System.out.println("Appointment Succesfully Added!");
    }

    public void dummyAppointmentDeletor() throws SQLException {
        AppointmentQuery.deleteByID(3);
        System.out.println("Appointment Succesfully Deleted!");
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
