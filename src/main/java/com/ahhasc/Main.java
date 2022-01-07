package com.ahhasc;

import com.ahhasc.Model.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    public static void main(String[] args) {
        Session.GetInstance().LoggedUser = DataAccess.GetInstance().UserController.GetUserByID(1);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("prism.lcdtext", "false");
        WindowApp.init(stage);
    }

}