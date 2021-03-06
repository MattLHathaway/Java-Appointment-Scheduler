package controller;

import helper.AppointmentQuery;
import helper.CountriesQuery;
import helper.CustomerQuery;
import helper.FirstLevelDivisionsQuery;
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
import model.Countries;
import model.Customer;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller for the CustomersMenu.fxml.
 */
public class CustomersMenuController implements Initializable {
    public TableView<Customer> customerTable;
    public TableColumn customerIDCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn customerPostalCodeCol;
    public TableColumn customerPhoneCol;
    public TableColumn customerFirstLevelDataCol;
    public TextField customerIDField;
    public Button editCustomerButton;
    public ChoiceBox customerCountryChoicebox;
    public TextField customerPhoneField;
    public TextField customerPostalCodeField;
    public TextField customerNameField;
    public ChoiceBox customerStateProvincePicker;
    public TextField customerAddressField;
    public Button deleteCustomerButton;
    public Button addCustomerButton;
    public Button logoutButton;
    public Button appointmentsNavButton;
    public Button reportsNavButton;
    public Button applyCountryButton;
    public Label customMessageLabel;

    /**
     * Upon initialization of this page, we fill the customersTable with all current Customers.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("We are at the Customers Menu!");

        try {
            populateCustomersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        customMessageLabel.setText("");
    }

    /**
     * We use this function to populate the customersTable.  It is used to refresh the table any time table data is changed,
     * deleted, or added.  It is also used to set the table to be clickable, allowing the modification fields to populate
     * with customer information.
     * @throws SQLException
     */
    public void populateCustomersTable() throws SQLException {
        ObservableList<Customer> customersList = CustomerQuery.getCustomerList();

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerFirstLevelDataCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        customerTable.setItems(customersList);

        //Set Table to be Clickable
        customerTable.setOnMouseClicked(event -> {

            //Grabbing the selected Customer as an Object
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

            //Variables for formatting (fldName & countryName)
            int countryID = 0;
            String countryName = "9";
            String divisionName = "9";
            try {
                divisionName = String.valueOf(FirstLevelDivisionsQuery.getDivisionNameByID(selectedCustomer.getDivisionID()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (FirstLevelDivisionsQuery.getCountryIDbyDivisionID(selectedCustomer.getDivisionID()) == 1) {
                   countryName = "U.S";
                   countryID = 1;
                } else if (FirstLevelDivisionsQuery.getCountryIDbyDivisionID(selectedCustomer.getDivisionID()) == 2) {
                    countryName = "UK";
                    countryID = 2;
                } else {
                    countryName = "Canada";
                    countryID = 3;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //Filling Modify Fields
            customerIDField.setText(String.valueOf(selectedCustomer.getCustomerID()));
            customerNameField.setText(String.valueOf(selectedCustomer.getCustomerName()));
            customerAddressField.setText(String.valueOf(selectedCustomer.getAddress()));
            customerPostalCodeField.setText(String.valueOf(selectedCustomer.getPostalCode()));
            customerPhoneField.setText(String.valueOf(selectedCustomer.getPhoneNumber()));
            //Setting Choicebox Options, then Choicebox Values
            try {
                ObservableList<Countries> allCountriesObjects = CountriesQuery.getCountriesList();
                ObservableList<String> allCountries = FXCollections.observableArrayList();
                allCountriesObjects.forEach(Countries -> allCountries.add(Countries.getCountry()));
                customerCountryChoicebox.setItems(allCountries);
                customerCountryChoicebox.setValue(countryName);
                ObservableList<FirstLevelDivisions> allDivisionsObjects = FirstLevelDivisionsQuery.getAllDivisionsByCountryID(countryID);
                ObservableList<String> allDivisions = FXCollections.observableArrayList();
                allDivisionsObjects.forEach(FirstLevelDivisions -> allDivisions.add(FirstLevelDivisions.getDivision()));
                customerStateProvincePicker.setItems(allDivisions);
                customerStateProvincePicker.setValue(divisionName);

                customMessageLabel.setText("");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    /**
     * This function is used to change the choicebox options between state/province options for the US, UK, or Canada.
     * @param actionEvent
     * @throws SQLException
     */
    public void onCountryChosen(javafx.event.ActionEvent actionEvent) throws SQLException {
        //CHECKING WHICH COUNTRY WAS CHOSEN
        if (Objects.equals(customerCountryChoicebox.getValue().toString(), "U.S")) {
            //Adding US States by Country ID
            ObservableList<FirstLevelDivisions> statesList = FirstLevelDivisionsQuery.getAllDivisionsByCountryID(1);
            ObservableList<String> usStateNames = FXCollections.observableArrayList();
            statesList.forEach(FirstLevelDivisions -> usStateNames.add(FirstLevelDivisions.getDivision()));
            customerStateProvincePicker.setItems(usStateNames);
        } else if (Objects.equals(customerCountryChoicebox.getValue().toString(), "UK")) {
            //Adding UK States by Country ID
            ObservableList<FirstLevelDivisions> statesList = FirstLevelDivisionsQuery.getAllDivisionsByCountryID(2);
            ObservableList<String> ukStateNames = FXCollections.observableArrayList();
            statesList.forEach(FirstLevelDivisions -> ukStateNames.add(FirstLevelDivisions.getDivision()));
            customerStateProvincePicker.setItems(ukStateNames);
        } else {
            //Adding Canadian States by Country ID
            ObservableList<FirstLevelDivisions> statesList = FirstLevelDivisionsQuery.getAllDivisionsByCountryID(3);
            ObservableList<String> canadaStateNames = FXCollections.observableArrayList();
            statesList.forEach(FirstLevelDivisions -> canadaStateNames.add(FirstLevelDivisions.getDivision()));
            customerStateProvincePicker.setItems(canadaStateNames);
        }
    }

    /**
     * This function switches screens.
     * @param event
     * @throws IOException
     */
    public void AppointmentNavButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This function switches screens.
     * @param event
     * @throws IOException
     */
    public void addCustomerButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This function switches screens.
     * @param event
     * @throws IOException
     */
    public void reportButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This buttons takes information from the customer modification fields and applies them to the selected customer
     * from the customerTable.  It clears the modification fields and refreshes the table once done.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void onModifyButtonPressed(ActionEvent event) throws IOException, SQLException {
        //Check for selected Customer from cust table
        if (customerTable.getSelectionModel() != null) {
            //Create "ARE YOU SURE" Alert Box
            ButtonType modify = new ButtonType("Modify", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Are you sure you want to Modify the Appointment?",
                    modify,
                    cancel);
            alert.setTitle("Update Warning");
            Optional<ButtonType> result = alert.showAndWait();
            //Modifies Customer in DB if modify button is clicked
            if (result.orElse(cancel) == modify) {
                //Grab Original Info to be modified !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

                selectedCustomer.setCustomerID(Integer.parseInt(customerIDField.getText()));
                selectedCustomer.setCustomerName(customerNameField.getText());
                selectedCustomer.setAddress(customerAddressField.getText());
                selectedCustomer.setPostalCode(customerPostalCodeField.getText());
                selectedCustomer.setPhoneNumber(customerPhoneField.getText());
                selectedCustomer.setDivisionID(FirstLevelDivisionsQuery.getDivisionIDbyName(String.valueOf(customerStateProvincePicker.getValue())));
                selectedCustomer.setLastUpdate(String.valueOf(LocalDateTime.now()));
                selectedCustomer.setLastUpdatedBy("script");
                selectedCustomer.setCreatedBy("script");
                selectedCustomer.setCreatedDate(customerTable.getSelectionModel().getSelectedItem().getCreatedDate());

                //Push to DB
                CustomerQuery.updateCustomer(selectedCustomer.getCustomerID(),
                        selectedCustomer.getCustomerName(),
                        selectedCustomer.getAddress(),
                        selectedCustomer.getPostalCode(),
                        selectedCustomer.getPhoneNumber(),
                        selectedCustomer.getCreatedDate(),
                        selectedCustomer.getCreatedBy(),
                        selectedCustomer.getLastUpdate(),
                        selectedCustomer.getLastUpdatedBy(),
                        selectedCustomer.getDivisionID());
            }

        }
        //Clear the modification fields
        customerIDField.setText("");
        customerNameField.setText("");
        customerAddressField.setText("");
        customerPostalCodeField.setText("");
        customerPhoneField.setText("");
        customerStateProvincePicker.setValue(null);
        customerCountryChoicebox.setValue(null);

        populateCustomersTable();
    }

    /**
     * This deletes a selected customer.  It warns the user first, and will only allow the customer to be deleted if no
     * appointments exist in their name.
     * @param event
     * @throws Exception
     */
    public void onDeleteButtonPressed(ActionEvent event) throws Exception {
        //Check for selected customer from table
        if (customerTable.getSelectionModel() != null) {
            //Make sure the Customer didn't have any Appointments
            String name = String.valueOf(customerTable.getSelectionModel().getSelectedItem().getCustomerName());
            int selectedCustomerID = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
            ObservableList<Appointment> apptList = AppointmentQuery.getAppointmentListByCustomerID(selectedCustomerID);
            if (apptList.size() == 0) {
                //Create "ARE YOU SURE?" Alert Box
                ButtonType delete = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Are you sure you want to Delete the Customer?",
                        delete,
                        cancel);
                alert.setTitle("Delete Warning");
                Optional<ButtonType> result = alert.showAndWait();
                //Deletes Customer from DB if delete button is clicked
                if (result.orElse(cancel) == delete) {
                    int customerFromTable = customerTable.getSelectionModel().getSelectedItem().getCustomerID();
                    CustomerQuery.deleteCustomerByID(customerFromTable);
                    customMessageLabel.setText(name + " was deleted from the database!");
                }
            } else {
                AlertMessageController.partError(3, null);
            }
        }
        //Refresh the Table
        populateCustomersTable();

        //Clear the modification fields
        customerIDField.setText("");
        customerNameField.setText("");
        customerAddressField.setText("");
        customerPostalCodeField.setText("");
        customerPhoneField.setText("");
        customerStateProvincePicker.setValue(null);
        customerCountryChoicebox.setValue(null);
    }

    /**
     * This function switches screens.
     * @param event
     * @throws IOException
     */
    public void logoutButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
