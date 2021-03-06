package com.ahhasc.View;

import com.ahhasc.Config;
import com.ahhasc.Model.AuthenticatedResult;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.RegistrationResult;
import com.ahhasc.Model.User;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Abstract.AbstractModalWindow;
import com.ahhasc.View.Component.ModalControl;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPage extends AbstractModalWindow implements Initializable {
    @FXML
    private CheckBox staySignInCheckbox;
    @FXML
    private TextField emailAddressField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn, passwordModifier;
    @FXML
    private ImageView loginIcon,passwordModifierIcon;
    @FXML
    private Text appname;
    @FXML
    private Label message;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appname.setText(Config.Appname);
        validateInput();
        modalControlController.changeTheme(ModalControl.Light);
        NodeHelper.RemoveTextFieldFocus(emailAddressField,appname);
    }

    @FXML
    private void validateInput(){
        String email = emailAddressField.getText();
        String password = passwordField.getText();
        message.setVisible(false);
        if (email.trim().length() > 0 && password.trim().length() > 0){
            loginBtn.disableProperty().set(false);
        } else{
            loginBtn.disableProperty().set(true);
        }

    }

    @FXML
    private void onLoginBtnHovered(){
        loginIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/nextbtnfilledimg.png")));
    }

    @FXML
    private void onLoginBtnExit(){
        loginIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/nextbtnimg.png")));
    }

    @FXML
    private void loginBtnClicked() throws IOException {
        String email = emailAddressField.getText();
        String password = passwordField.getText();
        Boolean isUserSaved = staySignInCheckbox.isSelected();
        AuthenticatedResult authResult =  DataAccess.GetInstance().UserController.Authenticate(email.trim().toLowerCase(),password,isUserSaved);
        if (authResult.IsSuccessful){
            loginIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/nextbtnsuccessimg.png")));
            loginBtn.setOnMouseEntered(null);
            loginBtn.setOnMouseExited(null);
            switch (authResult.AuthenticatedUser.Role){
                case User.MANAGER -> {
                    WindowApp.SetScene("ManagerMenuPage.fxml");
                }
                case User.TECHNICIAN -> {
                    WindowApp.SetScene("TechnicianAppointmentOverviewPage.fxml");
                }
            }
        } else {
            message.setTextFill(Color.web("#e14545"));
            message.setText("Invalid Credentials");
            message.setVisible(true);

            passwordField.clear();
        }
    }

    @FXML
    private void onRegisterTechnicianClicked() throws IOException {
        WindowApp.SetScene("TechnicianRegistrationPage1.fxml");
    }

    public void processRegistrationResult(RegistrationResult res){
        message.setVisible(true);
        if (res.IsSuccessful){
            message.setTextFill(Color.web("#7DB752"));
        } else {
            message.setTextFill(Color.web("#e14545"));
        }
        message.setText(res.Message);
    }


    private String _password;

    @FXML
    private void onPasswordModifierClicked(){
        _password =  passwordField.getText();
        passwordField.clear();
        passwordField.setPromptText(_password);
        passwordModifierIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/showpasswordicon.png")));
    }

    @FXML
    private void onPasswordModifierReleased(){
        passwordField.setText(_password);
        passwordField.setPromptText("Password");
        passwordModifierIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/hidepasswordicon.png")));
    }
}
