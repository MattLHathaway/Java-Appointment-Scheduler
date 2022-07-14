package controller;

import helper.AppointmentQuery;
import helper.CustomerQuery;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

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


    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("We are at the Customers Menu!");

        try {
            populateCustomersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void populateCustomersTable() throws SQLException {
        ObservableList<Customer> customersList = CustomerQuery.getCustomerList();

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerFirstLevelDataCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        customerTable.setItems(customersList);
    }
    public void AppointmentNavButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addCustomerButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteButtonPressed(ActionEvent event) throws Exception {
        //Check for selected customer from table
        if (customerTable.getSelectionModel() != null) {
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

    public void logoutButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
