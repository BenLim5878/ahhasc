package com.ahhasc.View.Component;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.View.Abstract.AbstractAppointmentRow;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AppointmentRowPayment extends AbstractAppointmentRow {

    @FXML
    private Label contactNumberLabel, customerNameLabel, paymentLabel, timeStartLabel;
    @FXML
    private Text contactNumberText, customerText, paymentText, timeText;
    @FXML
    private Button paidButton;
    @FXML
    private HBox rowContainer;

    @Override
    protected void DisplayAppointment(){
        customerNameLabel.setText(_appointment.BookingCustomer.FullName);
        paymentLabel.setText(String.format("%s$",_appointment.Payment.Amount));
        timeStartLabel.setText(_appointment.Payment.DueDate.format(DataAccess.DefaultDateFormat));
        contactNumberLabel.setText(_appointment.BookingCustomer.TelNumber);
    }

    @FXML
    private void paymentCompleted(){
        _appointment.Payment.IsResolved = true;
        DataAccess.GetInstance().AppointmentController.UpdateAppointment(_appointment);
        VBox appointmentList = (VBox) rowContainer.getParent();
        appointmentList.getChildren().remove(rowContainer);
    }

}
