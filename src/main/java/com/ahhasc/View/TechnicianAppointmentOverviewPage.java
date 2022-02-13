package com.ahhasc.View;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Session;
import com.ahhasc.Model.Technician;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Component.AppointmentRow;
import com.ahhasc.View.Component.NoAppointmentFound;
import com.ahhasc.View.Component.TechnicianMenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TechnicianAppointmentOverviewPage implements Initializable, IDynamicContent {

    @FXML
    private Label activeAppointmentMessage, completedAppointmentText, feedbackSubmittedText, paymentReceivedText, greetingText, userNameLabel;
    @FXML
    private TechnicianMenuLayout layoutController;
    @FXML
    private VBox appointmentList;

    private Technician _technician;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layoutController.SetTab(TechnicianMenuLayout.APPOINTMENT);
        LocalDateTime timeNow = LocalDateTime.now();
        int hourNow =  timeNow.getHour();
        if (hourNow >= 0 && hourNow <= 11){
            greetingText.setText("Good morning,");
        } else if(hourNow >= 12 && hourNow <= 18){
            greetingText.setText("Good afternoon,");
        } else {
            greetingText.setText("Good evening,");
        }
        this._technician = (Technician) Session.GetInstance().LoggedUser;
        userNameLabel.setText(this._technician.FullName);
        LoadStatData();
        LoadUpcomingAppointments();
    }
    private void LoadStatData(){
        // Active Appointment Amount
        int activeAppointmentAmount =  DataAccess.GetInstance().AppointmentController.GetAppointmentByTechnicianID(this._technician.getTechnicianID(),true).size();
        if (activeAppointmentAmount <= 1){
            activeAppointmentMessage.setText(String.format("You have %s scheduled appointment",activeAppointmentAmount));
        } else {
            activeAppointmentMessage.setText(String.format("You have %s scheduled appointments",activeAppointmentAmount));
        }

        // Completed Appointment Amount
        ArrayList<Appointment> appointments = DataAccess.GetInstance().AppointmentController.GetAppointmentByTechnicianID(this._technician.getTechnicianID(),false);
        int totalCompletedAppointment = 0;
        for (Appointment appointment: appointments){
            if (appointment.getIsCompleted()){
                totalCompletedAppointment += 1;
            }
        }
        completedAppointmentText.setText(Integer.toString(totalCompletedAppointment));

        // Submitted Feedback Amount
        int totalSubmittedFeedback = 0;
        for (Appointment appointment: appointments){
            if (appointment.getFeedback() != null){
                totalSubmittedFeedback += 1;
            }
        }
        feedbackSubmittedText.setText(Integer.toString(totalSubmittedFeedback));

        // Received Payment Amount
        double totalReceivedPayment = 0.0;
        for (Appointment appointment: appointments){
            if (appointment.Payment.IsResolved){
                totalReceivedPayment += appointment.Payment.Amount;
            }
        }
        paymentReceivedText.setText(String.format("%.2f$",totalReceivedPayment));
    }

    private void LoadUpcomingAppointments(){
        ArrayList<Appointment> appointments = DataAccess.GetInstance().AppointmentController.GetAppointmentByTechnicianID(_technician.getTechnicianID(),true);
        try {
            if (appointments.size() == 0){
                FXMLLoader noAppointmentFoundLoader =  NodeHelper.LoadFXMLLoader(ResourceLoader.LoadURL("/fxml/Component/NoAppointmentFound.fxml"));
                appointmentList.getChildren().add(noAppointmentFoundLoader.load());
                return;
            }
            for (Appointment appointment: appointments){
                FXMLLoader appointmentRowLoader = NodeHelper.LoadFXMLLoader(ResourceLoader.LoadURL("/fxml/Component/AppointmentRow.fxml"));
                appointmentList.getChildren().add(appointmentRowLoader.load());
                AppointmentRow appointmentRowController = (AppointmentRow) appointmentRowLoader.getController();
                appointmentRowController.SetAppointment(appointment);
                appointmentRowController.SetPreviousScene(this::showContent);
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void toBrowseAppointment() throws IOException {
        WindowApp.SetScene("TechnicianAppointmentBrowsePage.fxml");
    }

    @Override
    public void showContent() throws IOException {
        WindowApp.SetScene("TechnicianAppointmentOverviewPage.fxml");
    }
}
