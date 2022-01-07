package com.ahhasc.View.Component;

import com.ahhasc.ResourceLoader;
import com.ahhasc.WindowApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class ModalControl implements Initializable {
    @FXML
    private Button closeButton, minimizeButton;
    @FXML
    private ImageView closeButtonIcon, minimizeButtonIcon;
    @FXML
    private HBox modalControls;

    private double xOffset;
    private double yOffset;

    public static final String Light = "Light";
    public static final String Dark = "Dark";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modalControls.setOnMousePressed(event -> {
            xOffset = WindowApp.GetStage().getX() - event.getScreenX();
            yOffset = WindowApp.GetStage().getY() - event.getScreenY();
        });
        modalControls.setOnMouseDragged(event -> {
            WindowApp.GetStage().setX(event.getScreenX() + xOffset);
            WindowApp.GetStage().setY(event.getScreenY() + yOffset);
        });
    }

    @FXML
    private void closeMouseEnter(){
        closeButton.setStyle("-fx-background-color: rgba(227, 227, 227, 0.49)");

    }

    public void changeTheme (String theme){
        switch (theme){
            case Light:
                closeButtonIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/windowcloselight.png")));
                minimizeButtonIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/windowminimizelight.png")));
                break;
            case Dark:
                closeButtonIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/windowclosedark.png")));
                minimizeButtonIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/windowminimizedark.png")));
                break;
        }
    }

    @FXML
    private void closeMouseExit(){
        closeButton.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void minimizeMouseEnter(){
        minimizeButton.setStyle("-fx-background-color: rgba(227, 227, 227, 0.49)");

    }

    @FXML
    private void minimizeMouseExit(){
        minimizeButton.setStyle("-fx-background-color: transparent");
    }

    @FXML
    private void closeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void minimizeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.setIconified(true);
    }
}
