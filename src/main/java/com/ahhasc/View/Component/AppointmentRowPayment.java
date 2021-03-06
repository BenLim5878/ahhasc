package com.ahhasc.View.Component;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.View.Abstract.AbstractAppointmentRow;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.View.TechnicianAppointmentDetailsPage;
import com.ahhasc.View.TechnicianAppointmentFeedbackPage;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class AppointmentRowPayment extends AbstractAppointmentRow{

    @FXML
    private Label contactNumberLabel, customerNameLabel, paymentLabel, timeStartLabel;
    @FXML
    private Text contactNumberText, customerText, paymentText, timeText;
    @FXML
    private Button paidButton, detailsButton, addFeedbackButton;
    @FXML
    private HBox rowContainer;

    private boolean _isDone = false;
    private String _rowType = PAYMENT;
    private VBox _alertContainer;
    private IDynamicContent _previousSceneController = null;

    public static final String FEEDBACK = "Feedback";
    public static final String PAYMENT = "Payment";

    public void SetPreviousScene(IDynamicContent previousSceneController){
        _previousSceneController = previousSceneController;
    }

    public void setAlertContainer(VBox alertContainer){
        _alertContainer = alertContainer;
    }

    public void setRowType(String rowType){
        _rowType = rowType;
    }

    public void IsDone(boolean isPaid){
        this._isDone = isPaid;
    }

    @Override
    protected void DisplayAppointment(){
        switch (_rowType){
            case PAYMENT ->{
                rowContainer.getChildren().remove(addFeedbackButton);
                if (_isDone){
                    rowContainer.getChildren().remove(paidButton);
                }
                timeStartLabel.setText(_appointment.Payment.DueDate.format(DataAccess.DefaultDateFormat));

            }
            case FEEDBACK -> {
                rowContainer.getChildren().remove(paidButton);
                rowContainer.getChildren().remove(paymentText);
                rowContainer.getChildren().remove(paymentLabel);
                if (_isDone){
                    rowContainer.getChildren().remove(addFeedbackButton);
                }
                timeStartLabel.setText(_appointment.StartTime.format(DataAccess.DefaultDateTimeFormat));
                timeText.setText("Scheduled On");
            }
        }
        if (!_isDone){
            rowContainer.getChildren().remove(detailsButton);
        }
        customerNameLabel.setText(_appointment.BookingCustomer.FullName);
        paymentLabel.setText(String.format("%s$",_appointment.Payment.Amount));
        contactNumberLabel.setText(_appointment.BookingCustomer.TelNumber);
    }

    @FXML
    private void paymentCompleted(){
        _appointment.Payment.IsResolved = true;
        DataAccess.GetInstance().AppointmentController.UpdateAppointment(_appointment);
        VBox appointmentList = (VBox) rowContainer.getParent();
        appointmentList.getChildren().remove(rowContainer);
        NodeHelper.UpdateAppointmentContainer(appointmentList,_alertContainer);
    }

    @FXML
    private void viewAppointmentDetails() throws IOException {
        TechnicianAppointmentDetailsPage controller = (TechnicianAppointmentDetailsPage) WindowApp.SetScene("TechnicianAppointmentDetailsPage.fxml");
        controller.SetAppointment(this._appointment);
        controller.SetPreviousScene(this._previousSceneController);
    }

    @FXML
    private void toAddFeedback() throws IOException {
        TechnicianAppointmentFeedbackPage controller = (TechnicianAppointmentFeedbackPage) WindowApp.SetScene("TechnicianAppointmentFeedbackPage.fxml");
        controller.SetAppointment(_appointment);
        controller.SetPreviousScene(_previousSceneController);
    }

}
