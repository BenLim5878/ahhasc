package com.ahhasc.View;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Session;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Component.AppointmentRowPayment;
import com.ahhasc.View.Helper.NodeHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TechnicianPaymentReviewPage implements Initializable {

    @FXML
    private VBox appointmentList;

    private ArrayList<Appointment> _unpaidAppointment =  new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load Unpaid Appointment Row
        ArrayList<Appointment> allAppointments = DataAccess.GetInstance().AppointmentController.GetAppointmentByTechnicianID(Session.GetInstance().LoggedUser.getID(),false);
        for (Appointment appointment:allAppointments){
            if (!appointment.Payment.IsResolved){
                _unpaidAppointment.add(appointment);
            }
        }
        LoadAppointmentList();
    }

    private void LoadAppointmentList(){
        try{
            for (Appointment appointment: _unpaidAppointment){
                FXMLLoader appointmentRowLoader = NodeHelper.LoadFXMLLoader(ResourceLoader.LoadURL("/fxml/Component/AppointmentRowPayment.fxml"));
                appointmentList.getChildren().add(appointmentRowLoader.load());
                AppointmentRowPayment appointmentRowController = (AppointmentRowPayment) appointmentRowLoader.getController();
                appointmentRowController.SetAppointment(appointment);
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

}
