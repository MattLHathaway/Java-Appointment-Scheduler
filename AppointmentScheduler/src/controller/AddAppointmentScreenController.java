package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAppointmentScreenController {
    public TextField addApptIDField;
    public DatePicker addApptStartDatePicker;
    public Button addApptSaveButton;
    public TextField addApptTitleField;
    public TextField addApptDescriptionField;
    public TextField addApptLocationField;
    public TextField addApptTypeField;
    public DatePicker addApptEndDatePicker;
    public ChoiceBox addApptContactChoicebox;
    public Button addApptCancelButton;
    public ChoiceBox addApptCustomerIDChoicebox;
    public ChoiceBox addApptUserIDCheckbox;
    public TextField addApptStartTimeHourField;
    public TextField addApptStartTimeMinuteField;
    public TextField addApptEndTimeHourField;
    public TextField addApptEndTimeMinuteField;

    public void cancelButtonPressed(ActionEvent event) throws IOException {
        //Switch Screen Logic
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
