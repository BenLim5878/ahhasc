package com.ahhasc;

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
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("prism.lcdtext", "false");
        FXMLLoader login = new FXMLLoader(ResourceLoader.LoadURL("/fxml/LoginPage.fxml"));
        Scene scene = new Scene(login.load(), 1088, 681);
        stage.setScene(scene);
        scene.getWindow().centerOnScreen();
        stage.setResizable(false);
        stage.setTitle(Config.Appname);
        scene.setFill(Color.TRANSPARENT);
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
        return loader.getController();
    }

    public static Stage GetStage(){
        return _mainStage;
    }

}