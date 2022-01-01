package com.ahhasc;
import com.ahhasc.Controller.*;
import com.ahhasc.Model.*;

import com.ahhasc.View.LoginPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import io.github.palexdev.materialfx.controls.*;
import javafx.stage.StageStyle;


public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader login = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
        Scene scene = new Scene(login.load(), 1088, 681);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.show();
        
    }

}