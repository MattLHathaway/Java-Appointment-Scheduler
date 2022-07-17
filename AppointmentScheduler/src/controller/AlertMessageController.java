package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * This class is used to display custom alert messages.
 */
public class AlertMessageController {
    public static void partError(int code, TextField field) {
        fieldError(field);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setHeaderText("Error");

        switch (code) {
            case 1: {
                alert.setContentText("Username and/or Password are incorrect");
                break;
            }
            case 2: {
                alert.setContentText("Le nom d'utilisateur et/ou le mot de passe sont incorrects");
                break;
            }
            case 3: {
                alert.setContentText("Customer has open Appointments!  These appointments must be deleted before you can delete the Customer.");
                break;
            }
            case 4: {
                alert.setContentText("Name is invalid!");
                break;
            }
            case 5: {
                alert.setContentText("Value cannot be negative!");
                break;
            }

            default: {
                alert.setContentText("Unknown error!");
                break;
            }
        }
        alert.showAndWait();
    }
    private static void fieldError(TextField field) {
        if (field != null) {
            field.setStyle("-fx-border-color: blue");
        }
    }
}
