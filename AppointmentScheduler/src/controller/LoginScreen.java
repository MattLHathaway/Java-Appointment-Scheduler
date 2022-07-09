package controller;

import helper.UsersQuery;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
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

    private ObservableList<String> allPW;
    private ObservableList<String> allUN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized Woooo!");
        setAppLanguage();
    }

    public void setAppLanguage() {
        boolean isEnglish = false;
        Locale currentLocale = Locale.getDefault();
        String localeLanguage = currentLocale.getDisplayLanguage();
        if (Objects.equals(localeLanguage, "English")) {
            isEnglish = true;
        }
        if(!isEnglish) {
            System.out.println("not English");
        }
    }

    public void loginPressed(ActionEvent event) throws IOException, SQLException {
        boolean correctCredentials = false;
        //Store current Usernames & Passwords
        allUN = UsersQuery.returnUserNames();
        allPW = UsersQuery.returnUserPasswords();
        //Checks if Username & Pass combination is correct from DB
        for (int i = 0; i < allUN.toArray().length; i++) {
            if (Objects.equals(usernameField.getText(), allUN.get(i)) && Objects.equals(passwordField.getText(), allPW.get(i))) {
                correctCredentials = true;
            };
        }
        if (correctCredentials) {
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

    public void resetPressed() {

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