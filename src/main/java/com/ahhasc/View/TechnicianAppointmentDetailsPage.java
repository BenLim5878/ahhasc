package com.ahhasc.View;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Session;
import com.ahhasc.Model.Technician;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Component.AppointmentDetails;
import com.ahhasc.View.Component.TechnicianCard;
import com.ahhasc.View.Component.TechnicianMenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class  TechnicianAppointmentDetailsPage implements Initializable{
    @FXML
    private TechnicianMenuLayout layoutController;
    @FXML
    private AppointmentDetails appointmentDetailsController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layoutController.SetTab(TechnicianMenuLayout.APPOINTMENT);
    }

    public void SetPreviousScene(IDynamicContent previousSceneController){
        appointmentDetailsController.SetPreviousScene(previousSceneController);
    }

    public void SetAppointment(Appointment appointment){
        appointmentDetailsController.SetAppointment(appointment);
    }
}
