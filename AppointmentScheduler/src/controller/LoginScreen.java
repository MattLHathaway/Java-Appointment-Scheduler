package controller;

import helper.AppointmentQuery;
import helper.UsersQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.TimeUtility;
import model.Appointment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static helper.AppointmentQuery.*;

/**
 * The LoginScreen is used to allow or deny a user entry to the rest of the program
 */
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


    }

    /**
     * Get the default timezone of the Local User and set as text on the login GUI
     */
    public void applyTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        TimeZoneText.setText(tz.getID());
    }

    /**
     * This function triggers on initialization and tells our application which language to use, French or English
     */
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

    /**
     * This is applied to allow users to change the text of the login form buttons between English and French
     */
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

    /**
     * Called after the language is set, this function applies the newly selected language to all forms/buttons on the Login GUI
     * @param rb
     */
    public void applyLanguageValues(ResourceBundle rb) {
        LoginLabel.setText(rb.getString("LoginTitle"));
        UsernameLabel.setText(rb.getString("UsernameLabel"));
        LanguageLabel.setText(rb.getString("LanguageLabel"));
        PasswordLabel.setText(rb.getString("PasswordLabel"));
        TimeZoneLabel.setText(rb.getString("TimeZoneLabel"));
        loginButton.setText(rb.getString("LoginButton"));
        resetButton.setText(rb.getString("ResetButton"));
    }

    /**
     * Triggered when pressing the Reset button, this is used to empty the Username and Password text fields
     * @param event
     * @throws IOException
     */
    public void resetButtonPressed(ActionEvent event) throws IOException {
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * This event triggers when a user clicks the login button.  It checks the username and password and gives an error
     * if it is incorrect.  It also logs successful and unsuccessful login attempts in the login_activity.txt file.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void loginPressed(ActionEvent event) throws IOException, SQLException {
        boolean correctCredentials = false;
        Locale currentLocale = Locale.getDefault();
        String localeLanguage = currentLocale.getDisplayLanguage();
        File loginRecord = new File("login_activity.txt");
        FileWriter fr = new FileWriter(loginRecord, true);
        BufferedWriter br = new BufferedWriter(fr);
        String userName = usernameField.getText();
        String currentDateTime = String.valueOf(LocalDateTime.now());
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
            //Write Successful Login to login_activity.txt
            br.write("Successful Login by " + userName + " at " + currentDateTime + "\n");
            br.close();
            fr.close();

            //Check for Appointments within 15 minutes
            ObservableList<Appointment> allAppointments = getAppointmentList();
            LocalDateTime currentTimeMinus15Min = LocalDateTime.now().minusMinutes(15);
            LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
            LocalDateTime startTime = null;
            int getAppointmentID = 0;
            LocalDateTime displayTime = null;
            boolean appointmentWithin15Min = false;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


            for (Appointment appointment: allAppointments) {

                startTime = LocalDateTime.parse(appointment.getStartTime(), formatter);
                if ((startTime.isAfter(currentTimeMinus15Min) || startTime.isEqual(currentTimeMinus15Min)) && (startTime.isBefore(currentTimePlus15Min) || (startTime.isEqual(currentTimePlus15Min)))) {
                    getAppointmentID = appointment.getApptID();
                    displayTime = startTime;
                    appointmentWithin15Min = true;
                }
            }
            if (appointmentWithin15Min !=false) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment Within 15 Minutes: Appointment ID: " + getAppointmentID + " and Appointment Start Time Of: " + displayTime);
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("There is an appointment within 15 minutes");
            }

            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("no upcoming appointments");

                //Setting custom message on the MainMenu Controller and Switching Screens
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"));
                root = loader.load();

                MainMenu mainMenuController = loader.getController();
                mainMenuController.customMessageLabel.setText("There are currently no appointments within 15 minutes!");

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

            //Switching Screens
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            //Write Unsuccessful Login to login_activity.txt
            br.write("Unsuccessful Login Attempt at " + currentDateTime + "\n");
            br.close();
            fr.close();
            //Show Alert if UN/PW was incorrect
            if (Objects.equals(localeLanguage, "English")) {
                AlertMessageController.partError(1, null);
            } else {
                AlertMessageController.partError(2, null);
            }
            return;
        };
    }
}