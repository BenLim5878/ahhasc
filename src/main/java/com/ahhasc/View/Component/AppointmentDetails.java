package com.ahhasc.View.Component;

import com.ahhasc.Model.*;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.View.ManagerAppointmentManagePage;
import com.ahhasc.View.TechnicianAppointmentDetailsPage;
import com.ahhasc.View.TechnicianAppointmentFeedbackPage;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentDetails implements IDynamicContent {
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
    private VBox modifierButtonContainer,appointmentDetailsModifierContainer;
    @FXML
    private Button acceptContractButton, cancelContractButton, updateContractButton, feedbackButton, paymentReceivedButton,completedButton;
    @FXML
    private VBox noTechnicianContainer;

    private Appointment _appointment;
    private boolean _isUpdateMode = false;
    private IDynamicContent _previousSceneController = null;
    private String _accessType = "Technician";

    public void SetAccessType(String accessType){
        _accessType = accessType;
    }

    public void SetPreviousScene(IDynamicContent previousSceneController){
        _previousSceneController = previousSceneController;
    }

    public void SetAppointment(Appointment appointment){
        this._appointment = appointment;
        LoadAppointmentData();
        LoadCustomerData();
        LoadRoomData();
        LoadPaymentData();
        LoadTechnicianData();
        switch (_accessType){
            case User.TECHNICIAN -> {
                // Check if appointment is still available
                if (_appointment.ActiveTechnicians.size() == 0 && !_appointment.IsExpired()){
                    modifierButtonContainer.getChildren().clear();
                    modifierButtonContainer.getChildren().add(acceptContractButton);
                    modifierButtonContainer.setVisible(true);
                }

                // Check if appointment is owned by logged technician
                if (Session.GetInstance().LoggedUser.Role.equals(User.TECHNICIAN)){
                    Technician technician = (Technician) Session.GetInstance().LoggedUser;
                    if (_appointment.ActiveTechnicians.contains(technician)){
                        modifierButtonContainer.getChildren().clear();
                        modifierButtonContainer.setVisible(true);
                        if (!_appointment.IsExpired() && !_appointment.getIsCompleted() && !_appointment.Payment.IsResolved && _appointment.getFeedback() == null){
                            modifierButtonContainer.getChildren().add(cancelContractButton);
                        }
                        modifierButtonContainer.getChildren().add(updateContractButton);
                    } else {
                        modifierButtonContainer.getChildren().clear();
                        modifierButtonContainer.setVisible(true);
                        modifierButtonContainer.getChildren().add(acceptContractButton);
                    }
                }

                // Update appointments params
                String baseStyling = "-fx-border-color: transparent;-fx-background-radius: 5;";
                String completedStyling = String.format("%s -fx-background-color: #5FD78F;",baseStyling);
                String incompleteStyling = String.format("%s -fx-background-color: #C1C1C1;",baseStyling);
                appointmentDetailsModifierContainer.getChildren().remove(feedbackButton);
                if (appointment.Payment.IsResolved){
                    paymentReceivedButton.setText("Not Paid");
                    paymentReceivedButton.setStyle(completedStyling);
                } else {
                    paymentReceivedButton.setText("Paid");
                    paymentReceivedButton.setStyle(incompleteStyling);
                }
                if (appointment.getFeedback() != null){
                    feedbackButton.setText("Update Feedback");
                    feedbackButton.setStyle(completedStyling);
                    appointmentDetailsModifierContainer.getChildren().remove(paymentReceivedButton);
                    appointmentDetailsModifierContainer.getChildren().remove(completedButton);
                } else {
                    feedbackButton.setText("Add Feedback");
                    feedbackButton.setStyle(incompleteStyling);
                }
                if (appointment.getIsCompleted()){
                    completedButton.setText("Not Completed");
                    completedButton.setStyle(completedStyling);
                    if (appointment.Payment.IsResolved){
                        appointmentDetailsModifierContainer.getChildren().add(0,feedbackButton);
                    }
                } else {
                    completedButton.setText("Completed");
                    completedButton.setStyle(incompleteStyling);
                }

                // Update mode
                if (_isUpdateMode){
                    modifierButtonContainer.getChildren().add(appointmentDetailsModifierContainer);
                    updateContractButton.setText("Close");
                } else {
                    modifierButtonContainer.getChildren().remove(appointmentDetailsModifierContainer);
                    updateContractButton.setText("Update Contract");
                }
            }
            case User.MANAGER -> {
                Manager loggedManager = (Manager) Session.GetInstance().LoggedUser;
                modifierButtonContainer.getChildren().clear();
                if (_appointment.ActiveManager.getManagerID() == loggedManager.getManagerID()){
                    if (_appointment.ActiveTechnicians.size() == 0){
                        modifierButtonContainer.setSpacing(10);
                        modifierButtonContainer.getChildren().add(updateContractButton);
                        modifierButtonContainer.getChildren().add(cancelContractButton);
                    }
                }
            }
        }

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
            if (_appointment.IsExpired()){
                appointmentStatusText.setText("Expired");
            }
        }

        // Appointment Start Time
        LocalDateTime appointmentTimeStart = _appointment.StartTime;
        if (_appointment.IsExpired()){
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
            technicianList.getChildren().clear();
            if (!technicianList.getChildren().contains(noTechnicianContainer)){
                technicianList.getChildren().add(noTechnicianContainer);
            }
            return;
        }
        technicianList.getChildren().clear();
        try{
            for (Technician technician: _appointment.ActiveTechnicians){
                technicianCount += 1;
                FXMLLoader technicianCardLoader = NodeHelper.LoadFXMLLoader(ResourceLoader.LoadURL("/fxml/Component/TechnicianCard.fxml"));
                technicianList.getChildren().add(technicianCardLoader.load());
                TechnicianCard technicianCardController = (TechnicianCard)  technicianCardLoader.getController();
                technicianCardController.LoadData(technicianCount,technician,_appointment.getFeedback());
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void backToOverview() throws IOException {
         _previousSceneController.showContent();
    }

    @FXML
    private void contractAccepted(){
        _appointment.ActiveTechnicians.add((Technician) Session.GetInstance().LoggedUser);
        DataAccess.GetInstance().AppointmentController.UpdateAppointment(_appointment);
        acceptContractButton.setVisible(false);
        cancelContractButton.setVisible(true);
        updateContractButton.setVisible(true);
        SetAppointment(_appointment);
    }

    @FXML
    private void contractCancelled() throws IOException {
        switch (_accessType){
            case User.TECHNICIAN -> {
                _appointment.ActiveTechnicians.remove((Technician) Session.GetInstance().LoggedUser);
                DataAccess.GetInstance().AppointmentController.UpdateAppointment(_appointment);
                SetAppointment(_appointment);
                appointmentDetailsModifierContainer.setVisible(false);
                updateContractButton.setText("Update Contract");
            }
            case User.MANAGER -> {
                DataAccess.GetInstance().AppointmentController.DeleteAppointment(_appointment.getAppointmentID());
                _previousSceneController.showContent();
            }
        }

    }

    @FXML
    private void changeUpdateMode() throws IOException {
        switch (_accessType){
            case User.TECHNICIAN -> {
                if (!modifierButtonContainer.getChildren().contains(appointmentDetailsModifierContainer)){
                    _isUpdateMode = true;
                    updateContractButton.setText("Close");
                    modifierButtonContainer.getChildren().add(appointmentDetailsModifierContainer);
                } else {
                    _isUpdateMode = false;
                    updateContractButton.setText("Update Contract");
                    modifierButtonContainer.getChildren().remove(appointmentDetailsModifierContainer);
                }
            }
            case User.MANAGER -> {
                ManagerAppointmentManagePage controller = (ManagerAppointmentManagePage) WindowApp.SetScene("ManagerAppointmentManagePage.fxml");
                controller.LoadAppointment(_appointment);
                controller.SetPreviousScene(_previousSceneController);
            }
        }

    }

    @FXML
    private void feedbackClicked() throws IOException {
        TechnicianAppointmentFeedbackPage controller = (TechnicianAppointmentFeedbackPage) WindowApp.SetScene("TechnicianAppointmentFeedbackPage.fxml");
        controller.SetAppointment(_appointment);
        controller.SetPreviousScene(this::showContent);
    }

    @FXML
    private void paymentClicked(){
        if (_appointment.Payment.IsResolved){
            _appointment.Payment.IsResolved = false;
        } else {
            _appointment.Payment.IsResolved = true;
        }
        DataAccess.GetInstance().AppointmentController.UpdateAppointment(_appointment);
        SetAppointment(_appointment);
    }

    @FXML
    private void completedClicked(){
        if (_appointment.getIsCompleted()){
            _appointment.setIsCompleted(false);
        } else {
            _appointment.setIsCompleted(true);
        }
        DataAccess.GetInstance().AppointmentController.UpdateAppointment(_appointment);
        SetAppointment(_appointment);
    }

    @Override
    public void showContent() throws IOException {
        TechnicianAppointmentDetailsPage controller = (TechnicianAppointmentDetailsPage) WindowApp.SetScene("TechnicianAppointmentDetailsPage.fxml");
        controller.SetPreviousScene(_previousSceneController);
        controller.SetAppointment(_appointment);
    }
}
