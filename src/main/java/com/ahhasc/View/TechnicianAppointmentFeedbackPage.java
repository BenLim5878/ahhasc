package com.ahhasc.View;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Feedback;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Component.TechnicianMenuLayout;
import com.ahhasc.WindowApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TechnicianAppointmentFeedbackPage implements Initializable {
    @FXML
    private TechnicianMenuLayout layoutController;
    @FXML
    private Label appointmentIDLabel, appointmentStartTimeLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Button rating1Button, rating2Button, rating3Button, rating4Button, rating5Button;
    @FXML
    private TextArea workloadDescriptionTextArea;

    private Appointment _appointment;
    private Integer _selectedRating = null;
    private String _workloadDescription = "";
    private ArrayList<Button> ratingButtons = new ArrayList<>();
    private boolean isUpdateMode =false;
    private IDynamicContent _previousPageAppointmentDetails = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ratingButtons.add(rating1Button);
        ratingButtons.add(rating2Button);
        ratingButtons.add(rating3Button);
        ratingButtons.add(rating4Button);
        ratingButtons.add(rating5Button);
        layoutController.SetTab(TechnicianMenuLayout.FEEDBACK);
    }

    public void SetPreviousScene(IDynamicContent previousPageAppointmentDetails){
        _previousPageAppointmentDetails = previousPageAppointmentDetails;
    }

    public void SetAppointment(Appointment appointment){
        _appointment = appointment;
        appointmentIDLabel.setText(String.format("Appointment %s", _appointment.getAppointmentID()));
        appointmentStartTimeLabel.setText(String.format("Scheduled on %s",_appointment.StartTime.format(DataAccess.DefaultDateTimeFormat)));
        if (_appointment.getFeedback() != null){
            isUpdateMode = true;
            submitButton.setText("Update");
            _selectedRating = _appointment.getFeedback().getRating();
            _workloadDescription = _appointment.getFeedback().getDescription();
            ratingButtons.forEach(button -> {
                if (button.getText().equals(_selectedRating.toString())){
                    button.setStyle("-fx-background-color:  #33CCCC;-fx-text-fill: white;-fx-background-radius: 6;-fx-border-radius: 6");
                }
            });
            workloadDescriptionTextArea.setText(_workloadDescription);
        }
    }

    @FXML
    private void ratingButtonClicked(Event e){
        Button sourceButton = (Button) e.getSource();
        _selectedRating = Integer.parseInt(sourceButton.getText());
        for (Button ratingButton: ratingButtons){
            if (ratingButton.getText().equals(sourceButton.getText())){
                ratingButton.setStyle("-fx-background-color:  #33CCCC;-fx-text-fill: white;-fx-background-radius: 6;-fx-border-radius: 6");
            } else {
                ratingButton.setStyle("-fx-background-color:  white;-fx-text-fill: black;-fx-border-color: #33CCCC;-fx-background-radius: 6;-fx-border-radius: 6");
            }
        }
        checkCompletedForm();
    }

    @FXML
    private void descriptionChanged(Event e){
        TextArea sourceTextArea = (TextArea) e.getSource();
        _workloadDescription = sourceTextArea.getText();
        checkCompletedForm();
    }

    private void checkCompletedForm(){
        if (_selectedRating != null && _workloadDescription.trim().length() > 0){
            submitButton.setDisable(false);
        } else {
            submitButton.setDisable(true);
        }
    }

    @FXML
    private void toAppointmentList() throws IOException {
        _previousPageAppointmentDetails.showContent();
    }

    @FXML
    private void submitFeedback() throws IOException {
        if (isUpdateMode){
            _appointment.getFeedback().setRating(_selectedRating);
            _appointment.getFeedback().setDescription(_workloadDescription);
            DataAccess.GetInstance().FeedbackController.UpdateFeedback(_appointment.getFeedback());
            toAppointmentList();
            return;
        }
        Feedback newFeedback = new Feedback();
        newFeedback.CreatedAt = LocalDateTime.now();
        newFeedback.setDescription(_workloadDescription);
        newFeedback.setRating(_selectedRating);
        // Register new feedback in the system
        Feedback submittedFeedback = DataAccess.GetInstance().FeedbackController.AddFeedback(newFeedback);
        // Update the appointment with feedback ID attached
        _appointment.setFeedback(submittedFeedback);
        DataAccess.GetInstance().AppointmentController.UpdateAppointment(_appointment);
        toAppointmentList();
    }
}
