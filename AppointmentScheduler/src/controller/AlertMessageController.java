package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

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
            case 6: {
                alert.setContentText("Product cost cannot be lower than it's parts!");
                break;
            }
            case 7: {
                alert.setContentText("Products must have at least one part");
                break;
            }
            case 8: {
                alert.setContentText("Inventory must be lower than Max and greater than Min");
                break;
            }
            case 9: {
                alert.setContentText("Inventory, Price, Minimum, Maximum, and Machine ID fields must be NUMERIC");
                break;
            }
            case 10: {
                alert.setContentText("Min is greater than max");
                break;
            }
            case 11: {
                alert.setContentText("You must click on a part to modify!");
                break;
            }
            case 12: {
                alert.setContentText("This Product has associated parts!");
                break;
            }
            case 13: {
                alert.setContentText("You must click a part to add or remove!");
                break;
            }
            case 14: {
                alert.setContentText("This is already an associated part!");
                break;
            }
            case 15: {
                alert.setContentText("Search found nothing!  Try again and be case sensitive!");
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
