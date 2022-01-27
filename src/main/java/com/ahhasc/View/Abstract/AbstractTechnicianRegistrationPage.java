package com.ahhasc.View.Abstract;

import com.ahhasc.Model.Technician;
import com.ahhasc.ResourceLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AbstractTechnicianRegistrationPage extends AbstractModalWindow {

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
        nextIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/nextbtnfilledimg.png")));
    }

    @FXML
    private void onNextBtnExit(){
        nextIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/nextbtnimg.png")));
    }

}
