package com.ahhasc.View.Component;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Session;
import com.ahhasc.WindowApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        injectUser();
    }

    public void injectUser(){
        usernameLabel.setText(Session.GetInstance().LoggedUser.FullName);
        roleText.setText(Session.GetInstance().LoggedUser.Role);
    }

    @FXML
    private void onProfileButtonClick(){
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
