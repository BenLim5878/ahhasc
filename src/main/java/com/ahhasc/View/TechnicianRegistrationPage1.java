package com.ahhasc.View;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Technician;
import com.ahhasc.View.Abstract.AbstractTechnicianRegistrationPage;
import com.ahhasc.View.Component.ModalControl;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TechnicianRegistrationPage1 extends AbstractTechnicianRegistrationPage implements Initializable{

    @FXML
    private TextField nameField, emailAddressField, telephoneNumberField;
    @FXML
    private Text emailAddressErrorMsg, telephoneNumberErrorMsg;
    @FXML
    private VBox mainContent;

    public TechnicianRegistrationPage1(){
        super();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.technicianDescriptor = new Technician();
        NodeHelper.RemoveTextFieldFocus(nameField,mainContent);
        modalControlController.changeTheme(ModalControl.Light);
        NodeHelper.SetTextfieldDigitOnly(telephoneNumberField);
    }

    @Override
    public void LoadTechnicianDetails(){
        if (this.technicianDescriptor.FullName.trim().length() > 0 && this.technicianDescriptor.EmailAddress.trim().length() > 0 && this.technicianDescriptor.TelNumber.trim().length() > 0){
            nameField.setText(this.technicianDescriptor.FullName);
            emailAddressField.setText(this.technicianDescriptor.EmailAddress.trim().toLowerCase());
            telephoneNumberField.setText(this.technicianDescriptor.TelNumber.trim());
        }
        validateInput();
    }

    @FXML
    private void validateInput(){
        String name = nameField.getText();
        String emailAddress = emailAddressField.getText();
        String telephoneNumber = telephoneNumberField.getText();

        // Validate Email Address
        if (!DataAccess.IsValidEmailAddress(emailAddress) && emailAddress.trim().length() > 0){
            emailAddressErrorMsg.setVisible(true);
        }else {
            emailAddressErrorMsg.setVisible(false);
        }

        // Validate Telephone Number
        if (!DataAccess.IsValidTelephoneNumber(telephoneNumber) && telephoneNumber.length() > 0){
            telephoneNumberErrorMsg.setVisible(true);
        } else {
            telephoneNumberErrorMsg.setVisible(false);
        }

        if (name.trim().length() > 0 && emailAddress.trim().length() > 0 && telephoneNumber.trim().length() > 0 && !emailAddressErrorMsg.isVisible()&& !telephoneNumberErrorMsg.isVisible()){
            nextBtn.setDisable(false);
        } else {
            nextBtn.setDisable(true);
        }
    }

    @FXML
    private void onNextButtonClicked() throws IOException {
        String name = DataAccess.Capitalize(nameField.getText().trim());
        String emailAddress = emailAddressField.getText();
        String telephoneNumber = telephoneNumberField.getText();

        this.technicianDescriptor.FullName = name;
        this.technicianDescriptor.EmailAddress = emailAddress;
        this.technicianDescriptor.TelNumber = telephoneNumber;

        TechnicianRegistrationPage2 controller = (TechnicianRegistrationPage2) WindowApp.SetScene("TechnicianRegistrationPage2.fxml");
        controller.InjectTechnicianDetails(this.technicianDescriptor);
        controller.LoadTechnicianDetails();
    }

    @FXML
    private void onBackButtonClicked() throws IOException {
        WindowApp.SetScene("LoginPage.fxml");
    }
}
