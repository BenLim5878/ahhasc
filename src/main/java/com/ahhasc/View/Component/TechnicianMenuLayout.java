package com.ahhasc.View.Component;

import com.ahhasc.View.Abstract.IMenu;
import com.ahhasc.View.TechnicianAppointmentListPage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ahhasc.WindowApp.SetScene;

public class TechnicianMenuLayout implements Initializable, IMenu {

    @FXML
    private Button appointmentButton, paymentButton, feedbackButton;
    @FXML
    private AnchorPane layout;
    @FXML
    private MainLayout mainLayoutController;

    public static final String APPOINTMENT = "Appointment";
    public static final String PAYMENT = "Payment";
    public static final String FEEDBACK = "Feedback";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layout.setPickOnBounds(false);
    }

    @Override
    public void SetTab(String tabName) {
        switch (tabName){
            case APPOINTMENT:
                StyleTab(appointmentButton, true);
                StyleTab(paymentButton, false);
                StyleTab(feedbackButton, false);
                break;
            case PAYMENT:
                StyleTab(appointmentButton, false);
                StyleTab(paymentButton, true);
                StyleTab(feedbackButton, false);
                break;
            case FEEDBACK:
                StyleTab(appointmentButton, false);
                StyleTab(paymentButton, false);
                StyleTab(feedbackButton, true);
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
    private void appointmentClicked() throws IOException {
        SetScene("TechnicianAppointmentOverviewPage.fxml");
    }

    @FXML
    private void paymentClicked() throws IOException {
        SetScene("TechnicianAppointmentListPage.fxml");
    }

    @FXML
    private void feedbackClicked() throws IOException {
        TechnicianAppointmentListPage controller = (TechnicianAppointmentListPage) SetScene("TechnicianAppointmentListPage.fxml");
        controller.SetInterface(TechnicianAppointmentListPage.FEEDBACK);
    }




}
