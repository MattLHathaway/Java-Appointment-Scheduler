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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.TimeUtility;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginScreen implements Initializable {
    public ChoiceBox languageDropDown;
    public TextField passwordField;
    public TextField usernameField;
    public Button resetButton;
    public Button loginButton;
    public Label UsernameLabel;
    public Label PasswordLabel;
    public Label LanguageLabel;
    public Label TimeZoneLabel;
    public Label TimeZoneText;
    public Label LoginLabel;
    public Button languageChangeButton;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<String> allPW;
    private ObservableList<String> allUN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized Woooo!");
        languageDropDown.getItems().add("English");
        languageDropDown.getItems().add("French");
        setAppLanguage();
        applyTimeZone();
        ResourceBundle rb = ResourceBundle.getBundle("en_lang");

        System.out.println(TimeUtility.convertToUTC("2022-07-15 15:00:00"));

    }

    public void applyTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        TimeZoneText.setText(tz.getID());
    }

    public void applyLanguageButtonPressed() {
        ResourceBundle rb = ResourceBundle.getBundle("en_lang");

        if(languageDropDown.getValue() == "English") {
            applyLanguageValues(rb);
            Locale.setDefault(new Locale("en"));
        } else {
            rb = ResourceBundle.getBundle("fr_lang");
            applyLanguageValues(rb);
            Locale.setDefault(new Locale("fr"));
        }
    }

    public void setAppLanguage() {
        //Checking for users Language
        boolean isEnglish = false;
        ResourceBundle rb = ResourceBundle.getBundle("en_lang");
        Locale currentLocale = Locale.getDefault();
        String localeLanguage = currentLocale.getDisplayLanguage();
        //Setting the Dropdown and Resource Bundle
        if (Objects.equals(localeLanguage, "English")) {
            isEnglish = true;
            languageDropDown.setValue("English");
        }
        if(!isEnglish) {
            languageDropDown.setValue("French");
            rb = ResourceBundle.getBundle("fr_lang");
        }
        //Changing the Text Values of Labels, Buttons, and Error Messages
        applyLanguageValues(rb);
    }

    public void applyLanguageValues(ResourceBundle rb) {
        LoginLabel.setText(rb.getString("LoginTitle"));
        UsernameLabel.setText(rb.getString("UsernameLabel"));
        LanguageLabel.setText(rb.getString("LanguageLabel"));
        PasswordLabel.setText(rb.getString("PasswordLabel"));
        TimeZoneLabel.setText(rb.getString("TimeZoneLabel"));
        loginButton.setText(rb.getString("LoginButton"));
        resetButton.setText(rb.getString("ResetButton"));
    }

    public void resetButtonPressed(ActionEvent event) throws IOException {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void loginPressed(ActionEvent event) throws IOException, SQLException {
        boolean correctCredentials = false;
        Locale currentLocale = Locale.getDefault();
        String localeLanguage = currentLocale.getDisplayLanguage();
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
            if (Objects.equals(localeLanguage, "English")) {
                AlertMessageController.partError(1, null);
            } else {
                AlertMessageController.partError(2, null);
            }
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