package com.ahhasc.View.Abstract;

import com.ahhasc.Model.Appointment;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public abstract class AbstractAppointmentRow {

    @FXML
    private HBox appointmentRow;

    protected Appointment _appointment;

    public void SetAppointment(Appointment appointment){
        this._appointment = appointment;
        DisplayAppointment();
    }

    protected void DisplayAppointment(){

    }

    public void SetRowSpacing(double spacing){
        appointmentRow.setSpacing(spacing);
    }
}
