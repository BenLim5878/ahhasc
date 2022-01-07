package com.ahhasc.View;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.RegistrationResult;
import com.ahhasc.Model.Technician;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Abstract.AbstractTechnicianRegistrationPage;
import com.ahhasc.View.Component.ModalControl;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TechnicianRegistrationPage3 extends AbstractTechnicianRegistrationPage implements Initializable{

    @FXML
    private PasswordField passwordField, comfirmPasswordField;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private ImageView passwordModifierIcon;

    public TechnicianRegistrationPage3(){
        super();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.technicianDescriptor = new Technician();
        NodeHelper.RemoveTextFieldFocus(passwordField, passwordModifierIcon);
        modalControlController.changeTheme(ModalControl.Light);
        validateInput();
    }

    @Override
    public void LoadTechnicianDetails(){
        if (this.technicianDescriptor.Password != null){
            passwordField.setText(this.technicianDescriptor.Password);
        }
        validateInput();
    }

    @FXML
    private void validateInput(){
        String password = passwordField.getText();
        String comfirmPassword = comfirmPasswordField.getText();

        if (comfirmPassword.length() > 7 && password.equals(comfirmPassword)){
            nextBtn.setDisable(false);
        } else {
            nextBtn.setDisable(true);
        }

        if (password.length() < 8 && password.length() > 0){
            errorMessageLabel.setVisible(true);
            errorMessageLabel.setText("Password must be more than 8 characters");
            comfirmPasswordField.clear();
            comfirmPasswordField.setDisable(true);
            return;
        } else {
            errorMessageLabel.setVisible(false);
            comfirmPasswordField.setDisable(false);
        }

        if (!password.equals(comfirmPassword) && comfirmPassword.length() > 0 && !comfirmPasswordField.isDisable()){
            errorMessageLabel.setVisible(true);
            errorMessageLabel.setText("Password does not match");
            return;
        } else {
            errorMessageLabel.setVisible(false);
        }

    }

    @FXML
    private void onNextButtonClicked() throws IOException {
        String password = passwordField.getText();
        this.technicianDescriptor.Password = password;

        RegistrationResult res = DataAccess.GetInstance().UserController.RegisterTechnician(this.technicianDescriptor);
        LoginPage controller =  (LoginPage) WindowApp.SetScene("LoginPage.fxml");
        controller.processRegistrationResult(res);
    }

    @FXML
    private void onBackButtonClicked() throws IOException {
        String password = passwordField.getText();

        this.technicianDescriptor.Password = password;

       TechnicianRegistrationPage2 controller = (TechnicianRegistrationPage2) WindowApp.SetScene("TechnicianRegistrationPage2.fxml");
       controller.InjectTechnicianDetails(this.technicianDescriptor);
       controller.LoadTechnicianDetails();
    }

    private String _password;

    @FXML
    private void onPasswordModifierClicked(){
        _password =  passwordField.getText();
        passwordField.clear();
        passwordField.setPromptText(_password);
        passwordModifierIcon.setImage(new Image(ResourceLoader.LoadResource("/material/showpasswordicon.png")));
    }

    @FXML
    private void onPasswordModifierReleased(){
        passwordField.setText(_password);
        passwordField.setPromptText("");
        passwordModifierIcon.setImage(new Image(ResourceLoader.LoadResource("/material/hidepasswordicon.png")));
    }
}
