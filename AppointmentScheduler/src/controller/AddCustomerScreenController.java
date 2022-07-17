package controller;


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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customer;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This is the controller for the AddCustomerScreen.fxml.
 */
public class AddCustomerScreenController implements Initializable {
    public TextField CustNameField;
    public TextField CustPhoneField;
    public TextField custAddressField;
    public TextField custPostalCodeField;
    public ChoiceBox custStateChoicebox;
    public ChoiceBox custCountryChoicebox;
    public Button saveButton;
    public Button cancelButton;
    public Button applyCountryButton;

    /**
     * Upon initializing, it fills choicebox options.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("This is the Add Customer Page");

        try {
            fillChoiceBoxOptions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * When called, this function fills the choicebox list with the three available countries.
     * @throws SQLException
     */
    public void fillChoiceBoxOptions() throws SQLException {
        //ADDING countries to list
        ObservableList<Countries> countriesList = CountriesQuery.getCountriesList();
        ObservableList<String> allCountriesNames = FXCollections.observableArrayList();
        countriesList.forEach(Countries -> allCountriesNames.add(Countries.getCountry()));
        custCountryChoicebox.setItems(allCountriesNames);

    }

    /**
     * This function takes all user-entered data in the fields and choiceboxes and formats them, then places them in the
     * database as a new Customer.  Once done it returns you to the Customer Table Page.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onSaveButtonPressed(ActionEvent actionEvent) throws IOException, SQLException {
        //Get Field Values
        String currentState = custStateChoicebox.getValue().toString();

        int custID = createUniqueAppointmentID();
        String name = CustNameField.getText();
        String phone = CustPhoneField.getText();
        String address = custAddressField.getText();
        String postalCode = custPostalCodeField.getText();
        String country = custCountryChoicebox.getValue().toString();
        String state = custStateChoicebox.getValue().toString();
        String createdDate = String.valueOf(LocalDate.now());
        String createdBy = "script";
        String lastUpdate = createdDate;
        String lastUpdatedBy = "script";
        int divisionID = getDivisionID(currentState);

        Customer newCustomer = new Customer(custID, name, address, postalCode, phone, createdDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);

        //Adding Object to DB
        CustomerQuery.addCustomer(newCustomer.getCustomerID(),
                newCustomer.getCustomerName(),
                newCustomer.getAddress(),
                newCustomer.getPostalCode(),
                newCustomer.getPhoneNumber(),
                newCustomer.getCreatedDate(),
                newCustomer.getCreatedBy(),
                newCustomer.getLastUpdate(),
                newCustomer.getLastUpdatedBy(),
                newCustomer.getDivisionID());

        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This function switches screens.
     * @param actionEvent
     * @throws IOException
     */
    public void onCancelButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This checks which country was chosen from the dropdown and allows the State/Province drop down to be properly populated.
     * @param actionEvent
     * @throws SQLException
     */
    public void onCountryChosen(javafx.event.ActionEvent actionEvent) throws SQLException {
        //CHECKING WHICH COUNTRY WAS CHOSEN
        if (Objects.equals(custCountryChoicebox.getValue().toString(), "U.S")) {
            //Adding US States by Country ID
            ObservableList<FirstLevelDivisions> statesList = FirstLevelDivisionsQuery.getAllDivisionsByCountryID(1);
            ObservableList<String> usStateNames = FXCollections.observableArrayList();
            statesList.forEach(FirstLevelDivisions -> usStateNames.add(FirstLevelDivisions.getDivision()));
            custStateChoicebox.setItems(usStateNames);
        } else if (Objects.equals(custCountryChoicebox.getValue().toString(), "UK")) {
            //Adding UK States by Country ID
            ObservableList<FirstLevelDivisions> statesList = FirstLevelDivisionsQuery.getAllDivisionsByCountryID(2);
            ObservableList<String> ukStateNames = FXCollections.observableArrayList();
            statesList.forEach(FirstLevelDivisions -> ukStateNames.add(FirstLevelDivisions.getDivision()));
            custStateChoicebox.setItems(ukStateNames);
        } else {
            //Adding Canadian States by Country ID
            ObservableList<FirstLevelDivisions> statesList = FirstLevelDivisionsQuery.getAllDivisionsByCountryID(3);
            ObservableList<String> canadaStateNames = FXCollections.observableArrayList();
            statesList.forEach(FirstLevelDivisions -> canadaStateNames.add(FirstLevelDivisions.getDivision()));
            custStateChoicebox.setItems(canadaStateNames);
        }
    }

    /**
     * This function takes in an input state as a String.  It then returns the correct Division ID as an Integer.
     * @param inputState
     * @return
     * @throws SQLException
     */
    public int getDivisionID(String inputState) throws SQLException {
        int inputStateID = FirstLevelDivisionsQuery.getDivisionIDbyName(inputState);
        return inputStateID;
    }

    /**
     * This function is designed to create a unique ID for the customer.  It accomplishes by setting a new ID to the sum
     * of all other customer IDs.
     * @return
     * @throws SQLException
     */
    public int createUniqueAppointmentID() throws SQLException {
        ObservableList<Customer> custList = CustomerQuery.getCustomerList();
        ObservableList<String> allCustomerIDs = FXCollections.observableArrayList();
        custList.forEach(Customer -> allCustomerIDs.add(Integer.toString(Customer.getCustomerID())));
        int sumVal = 0;
        for(int i = 0; i < allCustomerIDs.size(); i++) {
            sumVal = Integer.parseInt(String.valueOf(allCustomerIDs.get(i))) + sumVal;
        }
        return sumVal;
    }
}
