package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class MainMenu implements Initializable {

    public Button logoutButton;
    public Label AppointmentScheduleLabel;
    public RadioButton ViewCustomerRadio;
    public RadioButton viewByWeekRadio;
    public RadioButton viewByMonthRadio;
    public RadioButton viewAllRadio;
    public TableView table;
    public TableColumn apptIDCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startTimeCol;
    public TableColumn endTimeCol;
    public TableColumn startDateCol;
    public TableColumn endDateCol;
    public TableColumn customerIDCol;
    public TableColumn userIDCol;
    public Label timeZoneLabel;
    public Button reportsButton;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public Label timeZoneText;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("We are at the Main Menu!");
        setTimeZoneText();

    }

    public void setTimeZoneText() {
        TimeZone tz = TimeZone.getDefault();
        timeZoneText.setText(tz.getID());
    }

    public void logoutPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
