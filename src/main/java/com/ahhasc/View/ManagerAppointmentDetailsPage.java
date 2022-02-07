package com.ahhasc.View;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.User;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Component.AppointmentDetails;
import com.ahhasc.View.Component.ManagerMenuLayout;
import com.ahhasc.View.Component.TechnicianMenuLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerAppointmentDetailsPage implements Initializable{
    @FXML
    private ManagerMenuLayout layoutController;
    @FXML
    private AppointmentDetails appointmentDetailsController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layoutController.SetTab(ManagerMenuLayout.APPOINTMENT);
        appointmentDetailsController.SetAccessType(User.MANAGER);
    }

    public void SetPreviousScene(IDynamicContent previousSceneController){
        appointmentDetailsController.SetPreviousScene(previousSceneController);
    }

    public void SetAppointment(Appointment appointment){
        appointmentDetailsController.SetAppointment(appointment);
    }
}
