package controller;

import helper.UsersQuery;
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
import java.sql.SQLException;
import java.util.Objects;
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

    public void loginPressed(ActionEvent event) throws IOException, SQLException {

        if (Objects.equals(usernameField.getText(), userName) && Objects.equals(passwordField.getText(), password)) {
            //Check UN/PW Logic
            UsersQuery.select(1);

            //Switch Screen Logic
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            //Show Alert if UN/PW was incorrect
            AlertMessageController.partError(1, null);
            return;
        };
    }
}

//        int rowsAffected;
//        try {
//            rowsAffected = UsersQuery.delete(3);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        if (rowsAffected > 0) {
//            System.out.println("Insert Successful");
//        }
//        ;