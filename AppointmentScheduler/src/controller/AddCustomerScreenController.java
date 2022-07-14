package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerScreenController implements Initializable {
    public TextField CustNameField;
    public TextField CustPhoneField;
    public TextField custAddressField;
    public TextField custPostalCodeField;
    public ChoiceBox custStateChoicebox;
    public ChoiceBox custCountryChoicebox;
    public Button saveButton;
    public Button cancelButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("This is the Add Customer Page");
    }



    public void onCancelButtonPressed(javafx.event.ActionEvent actionEvent) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomersMenu.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
