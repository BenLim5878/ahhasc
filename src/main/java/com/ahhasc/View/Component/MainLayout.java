package com.ahhasc.View.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    }

    @FXML
    private void onProfileButtonClick(){
        if (topMenu.isVisible()){
            topMenu.setVisible(false);
        } else {
            topMenu.setVisible(true);
        }
    }
}
