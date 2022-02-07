package com.ahhasc.View.Component;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.View.Abstract.AbstractAppointmentRow;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.TechnicianAppointmentDetailsPage;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.time.LocalDateTime;

public class AppointmentRow extends AbstractAppointmentRow {
    @FXML
    private Label customerNameLabel, paymentLabel, roomLabel, timeStartLabel;

    private IDynamicContent _previousSceneController = null;

    public void SetPreviousScene(IDynamicContent previousSceneController){
        _previousSceneController = previousSceneController;
    }

    @Override
    protected void DisplayAppointment(){
        // Customer Name
        customerNameLabel.setText(this._appointment.BookingCustomer.FullName);

        // Payment
        paymentLabel.setText(String.format("%s$",this._appointment.Payment.Amount.toString()));

        // Room
        roomLabel.setText(this._appointment.BookingCustomer.Room.Unit.toString());

        // Time Start
        LocalDateTime appointmentTimeStart = this._appointment.StartTime;
        if (appointmentTimeStart.isBefore(LocalDateTime.now())){
            timeStartLabel.setTextFill(Paint.valueOf("#E06666"));
        } else {
            timeStartLabel.setTextFill(Paint.valueOf("#6CA579"));
            if (appointmentTimeStart.minusHours(1).isBefore(LocalDateTime.now())){
                timeStartLabel.setTextFill(Paint.valueOf("#C6B03F"));
            }
        }
        timeStartLabel.setText(this._appointment.StartTime.format(DataAccess.DefaultDateTimeFormat));
    }

    @FXML
    private void showAppointmentDetail() throws IOException {
        TechnicianAppointmentDetailsPage controller = (TechnicianAppointmentDetailsPage) WindowApp.SetScene("TechnicianAppointmentDetailsPage.fxml");
        controller.SetAppointment(this._appointment);
        controller.SetPreviousScene(this._previousSceneController);
    }

}
