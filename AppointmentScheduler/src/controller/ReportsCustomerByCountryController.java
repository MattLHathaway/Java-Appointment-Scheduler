package controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * The purpose of this Report page is to display Customers by Appointment Type
 */
public class ReportsCustomerByCountryController implements Initializable {
    public TableView reportsTable;
    public ChoiceBox reportsChoicebox;
    public RadioButton apptByCustomerRadio;
    public RadioButton customersByTypeRadio;
    public RadioButton customersByMonthRadio;
    public RadioButton customersByCountryRadio;
    public Button customerButton;
    public Button AppointmentsButton;
    public Button logoutButton;
    public TableColumn customerIDCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerPostalCodeCol;
    public TableColumn customerPhoneNumberCol;
    public TableColumn customerCreatedDateCol;
    public TableColumn customerCreatedByCol;
    public TableColumn customerLastUpdateCol;
    public TableColumn customerLastUpdatedByCol;
    public TableColumn customerStateProvinceCol;
    public Button searchButton;
    public ToggleGroup group4;

    /**
     * On initialization it fills the table and sets Choicebox with Types.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillChoiceBoxOptions();
            populateCustomersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This function fills the choicebox with appointment types.
     * @throws SQLException
     */
    public void fillChoiceBoxOptions() throws SQLException {
        //Fill Choicebox with Appointment Types
        ObservableList<Appointment> apptTypeList = AppointmentQuery.getAppointmentList();
        ObservableList<String> allApptTypes = FXCollections.observableArrayList();
        apptTypeList.forEach(Appointment -> allApptTypes.add(String.valueOf(Appointment.getType())));
        reportsChoicebox.setItems(allApptTypes);
    }

    /**
     * This function allows the table to be refreshed with current data.
     * @throws SQLException
     */
    public void populateCustomersTable() throws SQLException {
        //Check for Choicebox Selection
        if (reportsChoicebox.getValue() == null) {
            ObservableList<Customer> customersList = CustomerQuery.getCustomerList();

            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            customerCreatedDateCol.setCellValueFactory(new PropertyValueFactory("createdDate"));
            customerCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            customerLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            customerLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            customerStateProvinceCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

            reportsTable.setItems(customersList);
        } else {
            //Creating a list of Customer IDs that have Appointments of the Chosen Type (stringListOfCustomerIDs)
            String appointmentType = reportsChoicebox.getValue().toString();
            ObservableList<Appointment> appointmentListByType = AppointmentQuery.getByType(appointmentType);
            ObservableList<String> stringListOfCustomerIDs = FXCollections.observableArrayList();
            appointmentListByType.forEach(Appointment -> stringListOfCustomerIDs.add(String.valueOf(Appointment.getCustomerID())));

            //Pull all appointments that correlate with the String list of IDs
            ObservableList<Customer> allCustomers = CustomerQuery.getCustomerList();

            //Checks both ObservableLists and puts the ones that match into a new list named "myFilteredList"
            List<Customer> myFilteredList = allCustomers.stream()
                    .filter(cust -> appointmentListByType.stream()
                            .anyMatch(appt ->
                                    cust.getCustomerID() ==
                                            appt.getCustomerID()))
                    .collect(Collectors.toList());

            //converting filteredList into ObservableList
            ObservableList<Customer> finalList = FXCollections.observableList(myFilteredList);

            //Assigning filtered list to the table
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            customerCreatedDateCol.setCellValueFactory(new PropertyValueFactory("createdDate"));
            customerCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
            customerLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            customerLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
            customerStateProvinceCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

            reportsTable.setItems(finalList);

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
