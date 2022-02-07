package com.ahhasc.View.Component;

import com.ahhasc.Model.Feedback;
import com.ahhasc.Model.Technician;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class TechnicianCard implements Initializable {
    @FXML
    private Label technicianCountText,technicianSpecializationText, technicianContactText,technicianNameText;
    @FXML
    private Text noFeedbackText, ratingText;
    @FXML
    private VBox feedbackContainer,feedbackDescriptionContainer;
    @FXML
    private TextArea workloadDescriptionTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        feedbackDescriptionContainer.getChildren().remove(feedbackContainer);
    }

    public void LoadData(int count, Technician technicianData){
        this.technicianCountText.setText(String.format("Technician %s",count));
        this.technicianSpecializationText.setText(technicianData.Specialization);
        this.technicianContactText.setText(technicianData.TelNumber);
        this.technicianNameText.setText(technicianData.FullName);
    }

    public void LoadData(int count, Technician technicianData,Feedback feedback){
        if (feedback != null){
            feedbackDescriptionContainer.getChildren().clear();
            feedbackDescriptionContainer.getChildren().add(feedbackContainer);
            ratingText.setText(feedback.getRating().toString());
            workloadDescriptionTextArea.setText(feedback.getDescription());
        }
        LoadData(count,technicianData);
    }


}
