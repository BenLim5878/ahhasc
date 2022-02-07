package com.ahhasc.View;

import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Session;
import com.ahhasc.Model.User;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Component.MainLayout;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountProfileUpdatePage implements Initializable {

    @FXML
    private Button cancelButton, savePasswordButton, saveUserInfoButton;
    @FXML
    private TextField confirmPasswordField,currentPasswordField,emailAddressField,nameField,newPasswordField,telephoneNumberField;
    @FXML
    private Text invalidTelephoneNumberText;
    @FXML
    private MainLayout mainLayoutController;
    @FXML
    private ImageView currentPasswordModifierIcon, newPasswordModifierIcon;
    @FXML
    private Pane editablePasswordContent;
    @FXML
    private Label savePasswordMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPersonalDetail();
        NodeHelper.SetTextfieldDigitOnly(telephoneNumberField);
    }

    private void loadPersonalDetail(){
        cancelButton.setVisible(false);
        saveUserInfoButton.setDisable(true);
        invalidTelephoneNumberText.setVisible(false);

        User currentUser = Session.GetInstance().LoggedUser;
        emailAddressField.setText(currentUser.EmailAddress);
        nameField.setText(currentUser.FullName);
        telephoneNumberField.setText(currentUser.TelNumber);
    }

    @FXML
    private void checkTelephoneNumber(){
        String telNo = telephoneNumberField.getText().trim();
        if (DataAccess.IsValidTelephoneNumber(telNo)){
            invalidTelephoneNumberText.setVisible(false);
        } else {
            invalidTelephoneNumberText.setVisible(true);
        }
        checkPersonalSectionComplete();
    }

    private void checkPersonalSectionComplete(){
        String name = nameField.getText().trim();
        String telNo = telephoneNumberField.getText().trim();
        if (name.length() > 0 && telNo.length() > 0 && !invalidTelephoneNumberText.isVisible()){
            saveUserInfoButton.setDisable(false);
        } else {
            saveUserInfoButton.setDisable(true);
        }
        cancelButton.setVisible(true);
    }

    @FXML
    private void nameTyped(){
        checkPersonalSectionComplete();
    }

    private String _currentPassword;
    private String _newPassword;

    @FXML
    private void onCurrentPasswordModifierClicked() {
        _currentPassword =  currentPasswordField.getText();
        currentPasswordField.clear();
        currentPasswordField.setPromptText(_currentPassword);
        currentPasswordModifierIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/showpasswordicon.png")));
    }

    @FXML
    private void onCurrentPasswordModifierReleased() {
        currentPasswordField.setText(_currentPassword);
        currentPasswordField.setPromptText("");
        currentPasswordModifierIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/hidepasswordicon.png")));
    }

    @FXML
    private void onNewCurrentPasswordModifierReleased() {
        newPasswordField.setText(_newPassword);
        newPasswordField.setPromptText("");
        newPasswordModifierIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/hidepasswordicon.png")));
    }

    @FXML
    private void onNewPasswordModifierClicked() {
        _newPassword =  newPasswordField.getText();
        newPasswordField.clear();
        newPasswordField.setPromptText(_newPassword);
        newPasswordModifierIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/showpasswordicon.png")));
    }

    @FXML
    private void resetPersonalFields(){
        loadPersonalDetail();
    }

    @FXML
    private void savePersonalDetails(){
        String name = DataAccess.Capitalize(nameField.getText().trim());
        String telNo = telephoneNumberField.getText().trim();

        Session.GetInstance().LoggedUser.FullName = name;
        Session.GetInstance().LoggedUser.TelNumber = telNo;

        DataAccess.GetInstance().UserController.UpdateUser(Session.GetInstance().LoggedUser);
        loadPersonalDetail();
        mainLayoutController.updateUserInformation();
    }

    @FXML
    private void currentPasswordTyped(){
        String typedPassword = currentPasswordField.getText();
        if (Session.GetInstance().LoggedUser.Password.equals(User.EncryptPassword(typedPassword))){
            editablePasswordContent.setVisible(true);
            savePasswordMessage.setVisible(false);
        } else {
            editablePasswordContent.setVisible(false);
            savePasswordMessage.setText("Current password is not valid");
            savePasswordMessage.setVisible(true);
            savePasswordMessage.setStyle("-fx-text-fill: #bf3636");
        }
        newPasswordReset();
    }

    private void newPasswordReset(){
        savePasswordButton.setDisable(true);
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    @FXML
    private void newPasswordTyped(){
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();


        if (newPassword.length() < 8){
            savePasswordMessage.setText("Password must more than 8 characters");
            savePasswordMessage.setVisible(true);
            savePasswordMessage.setStyle("-fx-text-fill: #bf3636");
            return;
        } else {
            savePasswordMessage.setVisible(false);
        }

        if (confirmPassword.length()== 0){
            return;
        }

        if (!newPassword.equals(confirmPassword)){
            savePasswordMessage.setText("Password does not match");
            savePasswordMessage.setVisible(true);
            savePasswordMessage.setStyle("-fx-text-fill: #bf3636");
            return;
        } else {
            savePasswordMessage.setVisible(false);
        }

        savePasswordButton.setDisable(false);
        savePasswordMessage.setVisible(false);
    }

    @FXML
    private void updatePassword(){
        String password = newPasswordField.getText();
        Session.GetInstance().LoggedUser.Password = User.EncryptPassword(password);
        DataAccess.GetInstance().UserController.UpdateUser(Session.GetInstance().LoggedUser);
        savePasswordMessage.setVisible(true);
        savePasswordMessage.setText("Password updated successfully");
        savePasswordMessage.setStyle("-fx-text-fill: #41a24d");
        newPasswordReset();
        editablePasswordContent.setVisible(false);
        currentPasswordField.clear();
    }

    @FXML
    private void toMenu() throws IOException {
        if (Session.GetInstance().LoggedUser.Role.equals(User.MANAGER)){
            WindowApp.SetScene("ManagerMenuPage.fxml");
        } else {
            WindowApp.SetScene("TechnicianAppointmentOverviewPage.fxml");
        }
    }
}
