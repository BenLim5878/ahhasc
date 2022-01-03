package com.ahhasc.View;

import com.ahhasc.Config;
import com.ahhasc.Main;
import com.ahhasc.Model.AuthenticatedResult;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.RegistrationResult;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Abstract.AbstractModalWindow;
import com.ahhasc.View.Component.ModalControl;
import com.ahhasc.View.Helper.NodeHelper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private Button loginBtn;
    @FXML
    private ImageView loginIcon;
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
        loginIcon.setImage(new Image(ResourceLoader.LoadResource("/material/nextbtnfilledimg.png")));
    }

    @FXML
    private void onLoginBtnExit(){
        loginIcon.setImage(new Image(ResourceLoader.LoadResource("/material/nextbtnimg.png")));
    }

    @FXML
    private void loginBtnClicked(){
        String email = emailAddressField.getText();
        String password = passwordField.getText();
        Boolean isUserSaved = staySignInCheckbox.isSelected();
        AuthenticatedResult authResult =  DataAccess.GetInstance().UserController.Authenticate(email.trim().toLowerCase(),password,isUserSaved);
        if (authResult.IsSuccessful){
            loginIcon.setImage(new Image(ResourceLoader.LoadResource("/material/nextbtnsuccessimg.png")));
            loginBtn.setOnMouseEntered(null);
            loginBtn.setOnMouseExited(null);
        } else {
            message.setTextFill(Color.web("#e14545"));
            message.setText("Invalid Credentials");
            message.setVisible(true);

            passwordField.clear();
        }
    }

    @FXML
    private void onRegisterTechnicianClicked() throws IOException {
        Main.SwitchScene("TechnicianRegistrationPage1.fxml");
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

}
