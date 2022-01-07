package com.ahhasc.View;

import com.ahhasc.Config;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Technician;
import com.ahhasc.View.Abstract.AbstractTechnicianRegistrationPage;
import com.ahhasc.View.Component.ModalControl;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TechnicianRegistrationPage2 extends AbstractTechnicianRegistrationPage implements Initializable{

    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private TextArea descriptionTextArea;

    public TechnicianRegistrationPage2(){
        super();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.technicianDescriptor = new Technician();
        modalControlController.changeTheme(ModalControl.Light);
        roleComboBox.setItems(Config.RoleList);
        validateInput();
    }

    @Override
    public void LoadTechnicianDetails(){
        if (this.technicianDescriptor.Specialization != null && this.technicianDescriptor.Description != null){
            roleComboBox.getSelectionModel().select(DataAccess.Capitalize(this.technicianDescriptor.Specialization.trim()));
            descriptionTextArea.setText(this.technicianDescriptor.Description);
        }
        validateInput();
    }

    @FXML
    private void validateInput(){
        String specialization = roleComboBox.getSelectionModel().getSelectedItem();
        String description = descriptionTextArea.getText();

        if (specialization != null && description.trim().length() > 0){
            nextBtn.setDisable(false);
        } else {
            nextBtn.setDisable(true);
        }
    }

    @FXML
    private void onNextButtonClicked() throws IOException {
        String specialization = roleComboBox.getSelectionModel().getSelectedItem();
        String description = descriptionTextArea.getText();

        this.technicianDescriptor.Specialization = DataAccess.Capitalize(specialization).trim();
        this.technicianDescriptor.Description = description.trim();

        TechnicianRegistrationPage3 controller = (TechnicianRegistrationPage3) WindowApp.SetScene("TechnicianRegistrationPage3.fxml");
        controller.InjectTechnicianDetails(this.technicianDescriptor);
        controller.LoadTechnicianDetails();
    }

    @FXML
    private void onBackButtonClicked() throws IOException {
        String specialization = roleComboBox.getSelectionModel().getSelectedItem();
        String description = descriptionTextArea.getText();

        this.technicianDescriptor.Specialization = specialization;
        this.technicianDescriptor.Description = description;

       TechnicianRegistrationPage1 controller = (TechnicianRegistrationPage1) WindowApp.SetScene("TechnicianRegistrationPage1.fxml");
       controller.InjectTechnicianDetails(this.technicianDescriptor);
       controller.LoadTechnicianDetails();
    }
}
