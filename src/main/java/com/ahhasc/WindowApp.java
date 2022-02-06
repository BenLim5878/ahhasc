package com.ahhasc;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.View.ManagerAppointmentViewAllPage;
import com.ahhasc.View.TechnicianAppointmentDetailsPage;
import com.ahhasc.View.TechnicianPaymentReviewPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class WindowApp {

    private static Stage _mainStage = null;

    public static void init(Stage stage) throws IOException {
        _mainStage =stage;
        _mainStage.getIcons().add(new Image(ResourceLoader.LoadResourceAsStream("/material/app_icon.png")));
        _mainStage.setResizable(false);
        _mainStage.setTitle(Config.Appname);
        _mainStage.initStyle(StageStyle.TRANSPARENT);
        _mainStage.centerOnScreen();
        SetScene("TechnicianPaymentReviewPage.fxml");
        _mainStage.show();
    }

    public static Object SetScene(String fxmlFileName) throws IOException {
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
