package main;

import helper.JDBC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        //write your code here
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}

//--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics <--- Paste to VM options in Edit Configuration