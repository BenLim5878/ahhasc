package com.ahhasc.View.Abstract;

import com.ahhasc.Model.AuthenticatedResult;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Technician;
import com.ahhasc.ResourceLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AbstractTechnicianRegistrationPage extends AbstractModalWindow {

    @FXML
    protected Button nextBtn;
    @FXML
    protected ImageView nextIcon;

    public Technician technicianDescriptor;

    public void InjectTechnicianDetails(Technician technicianDescriptor){
        this.technicianDescriptor = technicianDescriptor;
    }

    public void LoadTechnicianDetails(){

    }

    @FXML
    private void onNextBtnHovered(){
        nextIcon.setImage(new Image(ResourceLoader.LoadResource("/material/nextbtnfilledimg.png")));
    }

    @FXML
    private void onNextBtnExit(){
        nextIcon.setImage(new Image(ResourceLoader.LoadResource("/material/nextbtnimg.png")));
    }

}
