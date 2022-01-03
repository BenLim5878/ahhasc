package com.ahhasc;

import com.ahhasc.Model.*;
import com.ahhasc.View.ManagerAppointmentManagePage;
import com.ahhasc.View.ManagerTechnicianManagePage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application{

    private static Stage _mainStage;

    public static void main(String[] args) {
        Session.GetInstance().LoggedUser = DataAccess.GetInstance().UserController.GetUserByID(1);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        _mainStage = stage;
        System.setProperty("prism.lcdtext", "false");
        ManagerAppointmentManagePage controller = (ManagerAppointmentManagePage) SwitchScene("ManagerAppointmentManagerPage.fxml");
        controller.LoadAppointment(DataAccess.GetInstance().AppointmentController.GetAppointments().get(2));
        stage.setResizable(false);
        stage.setTitle(Config.Appname);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        _mainStage = stage;
    }

    public static Object SwitchScene(String fxmlFileName) throws IOException {
        if (_mainStage == null){
            return null;
        }
        FXMLLoader loader = new FXMLLoader(ResourceLoader.LoadURL(String.format("/fxml/%1$s",fxmlFileName)));
        Scene newScene = new Scene(loader.load());
        newScene.setFill(Color.TRANSPARENT);
        _mainStage.setScene(newScene);
        newScene.getWindow().centerOnScreen();
        return loader.getController();
    }

    public static Stage GetStage(){
        return _mainStage;
    }

}