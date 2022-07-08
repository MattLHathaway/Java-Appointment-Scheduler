package controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {
    public ChoiceBox languageDropDown;
    public TextField passwordField;
    public TextField usernameField;
    public Button resetButton;
    public Button loginButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String userName = "Test";
    private String password = "1234";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized Woooo!");
    }

    public void loginPressed(ActionEvent event) throws IOException {

        if (usernameField.getText() == userName && passwordField.getText() == password) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {

        };
    }
}
