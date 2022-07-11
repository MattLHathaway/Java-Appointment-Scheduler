package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomersMenuController {
    public TableView customerTable;
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
    public Button saveCustomerButton;
    public Button appointmentsNavButton;
    public Button reportsNavButton;

    public void AppointmentNavButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
