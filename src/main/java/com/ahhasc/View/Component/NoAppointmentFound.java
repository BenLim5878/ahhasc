package com.ahhasc.View.Component;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class NoAppointmentFound {

    @FXML
    private Text messageTitle, messageDescription;

    public void SetMessageTitle(String title){
        messageTitle.setText(title);
    }

    public void SetMessageDescription(String description){
        messageDescription.setText(description);
    }
}
