package com.ahhasc.View.Component;

import com.ahhasc.View.Abstract.IMenu;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMenuLayout implements Initializable, IMenu {
    @FXML
    private Button menuButton, appointmentButton, customerButton;
    @FXML
    private AnchorPane layout;
    @FXML
    public MainLayout mainLayoutController;

    public static final String MENU = "Menu";
    public static final String APPOINTMENT = "Appointment";
    public static final String CUSTOMER = "Customer";

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
                break;
            case APPOINTMENT:
                StyleTab(menuButton, false);
                StyleTab(appointmentButton, true);
                StyleTab(customerButton, false);
                break;
            case CUSTOMER:
                StyleTab(menuButton, false);
                StyleTab(appointmentButton, false);
                StyleTab(customerButton, true);
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

    @FXML
    private void menuClicked() throws IOException {
        WindowApp.SetScene("ManagerMenuPage.fxml");
    }

    @FXML
    private void appointmentClicked() throws IOException {
        WindowApp.SetScene("ManagerAppointmentViewAllPage.fxml");
    }

    @FXML
    private void customerClicked() throws IOException {
        WindowApp.SetScene("ManagerCustomerViewAllPage.fxml");
    }
}
