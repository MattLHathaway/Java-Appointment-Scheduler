package controller;

import helper.AppointmentQuery;
import helper.ContactQuery;
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
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The purpose of this menu is to show Appointments for each Contact.
 */
public class ReportsMenuController implements Initializable {
    public TableView reportsTable;
    public ChoiceBox reportsChoicebox;
    public RadioButton apptByCustomerRadio;
    public ToggleGroup ReportsRadioGroup;
    public RadioButton customersByTypeRadio;
    public RadioButton customersByMonthRadio;
    public RadioButton customersByCountryRadio;
    public Button customerButton;
    public Button AppointmentsButton;
    public Button logoutButton;
    public TableColumn apptIDCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartDateTimeCol;
    public TableColumn apptEndDateTimeCol;
    public TableColumn apptCustomerIDCol;
    public Button searchButton;

    /**
     * Upon initialization, the choicebox is filled and table refreshed.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillChoiceBoxOptions();
            populateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This function fills the choicebox with contact IDs.
     * @throws SQLException
     */
    public void fillChoiceBoxOptions() throws SQLException {
        //Fill Choicebox with Contact IDs
        ObservableList<Contact> contList = null;
        try {
            contList = ContactQuery.getContactList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> allContactIDs = FXCollections.observableArrayList();
        contList.forEach(Contact -> allContactIDs.add(String.valueOf(Contact.getContactID())));
        reportsChoicebox.setItems(allContactIDs);
    }

    /**
     * This refreshes the table.
     * @throws SQLException
     */
    public void populateTable() throws SQLException {
        //Check if a Contact ID has been chosen and applies that to table
        if (reportsChoicebox.getValue() == null) {
            ObservableList<Appointment> appointmentList = AppointmentQuery.getAppointmentList();

            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            apptEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            reportsTable.setItems(appointmentList);
        } else {
            String contactID = reportsChoicebox.getValue().toString();
            ObservableList<Appointment> appointmentList = AppointmentQuery.getAppointmentListByContactID(Integer.parseInt(contactID));

            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            apptEndDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

            reportsTable.setItems(appointmentList);
        }

    }

    /**
     * This is for switching pages.
     * @param actionEvent
     * @throws IOException
     */
    public void radioCheck(javafx.event.ActionEvent actionEvent) throws IOException {
        RadioButton reportsMenu = (RadioButton) apptByCustomerRadio;
        RadioButton customersByType = (RadioButton) customersByTypeRadio;
        RadioButton customersByMonth = (RadioButton) customersByMonthRadio;
        RadioButton customersByCountry = (RadioButton) customersByCountryRadio;

        if (customersByType.isSelected()) {
            //Switch Screen Logic
            Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsCustomerByType.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Appointments by Type");
        } else if (reportsMenu.isSelected()) {
            //Switch Screen Logic
            Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Appointments by Contact");
        } else if (customersByMonth.isSelected()) {
            //Switch Screen Logic
            Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsCustomerByMonth.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Appointments by Month");
        } else if (customersByCountry.isSelected()) {
            //Switch Screen Logic
            Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsCustomerByCountry.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("Customers by Type");
        }
    }

    /**
     * This is for switching pages.
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
     * This is for switching pages.
     * @param event
     * @throws IOException
     */
    public void AppointmentButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is for switching pages.
     * @param event
     * @throws IOException
     */
    public void CustomerButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}