package com.ahhasc.View.Component;

import com.ahhasc.Model.Technician;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TechnicianCard {
    @FXML
    private Label technicianCountText,technicianSpecializationText, technicianContactText,technicianNameText;

    public void LoadData(int count,Technician technicianData){
        this.technicianCountText.setText(String.format("Technician %s",count));
        this.technicianSpecializationText.setText(technicianData.Specialization);
        this.technicianContactText.setText(technicianData.TelNumber);
        this.technicianNameText.setText(technicianData.FullName);
    }

}
