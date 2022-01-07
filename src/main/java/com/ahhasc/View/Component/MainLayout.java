package com.ahhasc.View.Component;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Session;
import com.ahhasc.WindowApp;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayout implements Initializable {

    @FXML
    private Button profileButton, userProfileButton, signOutButton, exitButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Text roleText;
    @FXML
    private VBox topMenu;
    @FXML
    private AnchorPane layout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topMenu.setVisible(false);
        layout.setPickOnBounds(false);
        updateUserInformation();
    }

    private void setCloseTopMenuHandler(){
        Scene scene = topMenu.getScene();
        scene.setOnMouseClicked(null);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (topMenu.isVisible()){
                    topMenu.setVisible(false);
                }
            }
        });
    }

    public void updateUserInformation(){
        usernameLabel.setText(Session.GetInstance().LoggedUser.FullName);
        roleText.setText(Session.GetInstance().LoggedUser.Role);
    }

    @FXML
    private void onProfileButtonClick(){
        setCloseTopMenuHandler();
        if (topMenu.isVisible()){
            topMenu.setVisible(false);
        } else {
            topMenu.setVisible(true);
        }
    }

    @FXML
    private void toAccountUpdatePage() throws IOException {
        WindowApp.SetScene("AccountProfileUpdatePage.fxml");
    }

    @FXML
    private void signOut() throws IOException {
        DataAccess.GetInstance().UserController.Logout();
        WindowApp.SetScene("LoginPage.fxml");
    }

    @FXML
    private void exit(){
        WindowApp.GetStage().close();
        Platform.exit();
        System.exit(0);
    }
}
