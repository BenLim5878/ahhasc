package com.ahhasc.View;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Technician;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Component.TechnicianCard;
import com.ahhasc.View.Component.TechnicianMenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class TechnicianAppointmentDetailsPage implements Initializable {
    @FXML
    private Label appointmentIDText, appointmentStartTimeText, appointmentStatusText;
    @FXML
    private TextArea appointmentDescriptionText;
    @FXML
    private Label customerNameText, customerContactText,customerEmailText;
    @FXML
    private Label roomIDText, floorNumberText, blockText;
    @FXML
    private Label paymentAmountText, paymentDueText, paymentStatusText;
    @FXML
    private HBox technicianList;
    @FXML
    private TechnicianMenuLayout layoutController;

    private Appointment _appointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layoutController.SetTab(TechnicianMenuLayout.APPOINTMENT);
    }

    public void SetAppointment(Appointment appointment){
        this._appointment = appointment;
        LoadAppointmentData();
        LoadCustomerData();
        LoadRoomData();
        LoadPaymentData();
        LoadTechnicianData();
    }

    private void LoadAppointmentData(){
        // Appointment ID
        appointmentIDText.setText(String.format("Appointment %s", _appointment.getAppointmentID()));

        // Appointment Description
        appointmentDescriptionText.setText(_appointment.Description);

        // Appointment Status
        if (_appointment.getIsCompleted()){
            appointmentStatusText.setText("Completed");
            appointmentStatusText.setTextFill(Paint.valueOf("#59a553"));
        } else {
            appointmentStatusText.setText("Not Completed");
            appointmentStatusText.setTextFill(Paint.valueOf("#a4a4a4"));
        }

        // Appointment Start Time
        LocalDateTime appointmentTimeStart = _appointment.StartTime;
        if (appointmentTimeStart.isBefore(LocalDateTime.now())){
            appointmentStartTimeText.setTextFill(Paint.valueOf("#E06666"));
        } else {
            appointmentStartTimeText.setTextFill(Paint.valueOf("#6CA579"));
            if (appointmentTimeStart.minusHours(1).isBefore(LocalDateTime.now())){
                appointmentStartTimeText.setTextFill(Paint.valueOf("#C6B03F"));
            }
        }
        appointmentStartTimeText.setText(_appointment.StartTime.format(DataAccess.DefaultDateTimeFormat));
    }

    private void LoadCustomerData(){
        // Customer Name
        customerNameText.setText(_appointment.BookingCustomer.FullName);
        // Customer Contact Number
        customerContactText.setText(_appointment.BookingCustomer.TelNumber);
        // Customer Email Address
        customerEmailText.setText(_appointment.BookingCustomer.EmailAddress);
    }

    private void LoadRoomData(){
        // Room ID
        roomIDText.setText(String.format("Room %s", _appointment.BookingCustomer.Room.getRoomID()));
        // Floor
        floorNumberText.setText(String.format("Floor %s",_appointment.BookingCustomer.Room.Floor));
        // Block
        blockText.setText(String.format("Block %s",_appointment.BookingCustomer.Room.Block));
    }

    private void LoadPaymentData(){
        // Payment Amount
        paymentAmountText.setText(String.format("%.2f$",_appointment.Payment.Amount));
        // Payment Due Date
        paymentDueText.setText(String.format("Due on %s",_appointment.Payment.DueDate.format(DataAccess.DefaultDateFormat)));
        // Payment Status
        if (_appointment.Payment.IsResolved){
            paymentStatusText.setTextFill(Paint.valueOf("#59a553"));
            paymentStatusText.setText("Resolved");
        } else {
            paymentStatusText.setTextFill(Paint.valueOf("#d0786e"));
            paymentStatusText.setText("Not Resolved");
        }
    }

    private void LoadTechnicianData(){
        int technicianCount = 0;
        if (_appointment.ActiveTechnicians.size() == 0){
            return;
        }
        technicianList.getChildren().clear();
        try{
            for (Technician technician: _appointment.ActiveTechnicians){
                technicianCount += 1;
                FXMLLoader technicianCardLoader = NodeHelper.LoadFXMLLoader(ResourceLoader.LoadURL("/fxml/Component/TechnicianCard.fxml"));
                technicianList.getChildren().add(technicianCardLoader.load());
                TechnicianCard technicianCardController = (TechnicianCard)  technicianCardLoader.getController();
                technicianCardController.LoadData(technicianCount,technician);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void backToOverview() throws IOException {
        WindowApp.SetScene("TechnicianAppointmentOverviewPage.fxml");
    }
}
