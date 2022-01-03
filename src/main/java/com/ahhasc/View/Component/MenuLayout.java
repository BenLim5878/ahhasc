package com.ahhasc.View.Component;

import com.ahhasc.View.Abstract.IMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuLayout implements Initializable, IMenu {
    @FXML
    private Button menuButton, appointmentButton, customerButton, administrationButton;
    @FXML
    private AnchorPane layout;
    @FXML
    public MainLayout mainLayoutController;

    public static final String MENU = "Menu";
    public static final String APPOINTMENT = "Appointment";
    public static final String CUSTOMER = "Customer";
    public static final String ADMINISTRATION = "Administration";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layout.setPickOnBounds(false);
    }

    public void SetTab(String tabName){
        switch (tabName){
            case MENU:
                StyleTab(menuButton, true);
                StyleTab(appointmentButton, false);
                StyleTab(customerButton, false);
                StyleTab(administrationButton, false);
                break;
            case APPOINTMENT:
                StyleTab(menuButton, false);
                StyleTab(appointmentButton, true);
                StyleTab(customerButton, false);
                StyleTab(administrationButton, false);
                break;
            case CUSTOMER:
                StyleTab(menuButton, false);
                StyleTab(appointmentButton, false);
                StyleTab(customerButton, true);
                StyleTab(administrationButton, false);
                break;
            case ADMINISTRATION:
                StyleTab(menuButton, false);
                StyleTab(appointmentButton, false);
                StyleTab(customerButton, false);
                StyleTab(administrationButton, true);
                break;
        }
    }

    private void StyleTab(Button tabButton, Boolean isSelected){
        if (isSelected){
            tabButton.setStyle("-fx-background-color: #80B386;-fx-text-fill: #FAFAFA;-fx-background-radius: 23");
        } else{
            tabButton.setStyle("-fx-background-color: transparent;-fx-text-fill: #5E5E5E;-fx-background-radius: 23");
        }
    }
}
