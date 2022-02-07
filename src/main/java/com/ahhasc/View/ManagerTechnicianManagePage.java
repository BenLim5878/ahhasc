package com.ahhasc.View;

import com.ahhasc.Model.*;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Component.ManagerAppointmentSideMenu;
import com.ahhasc.View.Component.ManagerMenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ManagerTechnicianManagePage implements Initializable {

    @FXML
    private ScrollPane technicianPaneContainer;
    @FXML
    private VBox assignedTechnicianContainer;
    @FXML
    private Text assignTechnicianAlert;
    @FXML
    private TextField searchField;
    @FXML
    private ManagerAppointmentSideMenu sideMenuController;
    @FXML
    private ManagerMenuLayout menuLayoutController;
    @FXML
    private Button completeButton, cancelButton;

    private ArrayList<Technician> technicians = new ArrayList<>();
    private ArrayList<Technician> selectedTechnician = new ArrayList<>();
    private Appointment _appointment;
    private boolean IsUpdateMode = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        technicians = DataAccess.GetInstance().UserController.GetTechnicians();
        menuLayoutController.SetTab(ManagerMenuLayout.APPOINTMENT);
        loadTechnicansDetailsPane();
    }

    public void LoadAppointment(Appointment appointment, boolean isUpdate) {
        _appointment = appointment;
        IsUpdateMode = isUpdate;

        selectedTechnician = appointment.ActiveTechnicians;
        loadComponent();
        checkCompleted();
    }

    public void loadTechnicansDetailsPane(){
        VBox root = new VBox();
        root.setPrefWidth(425);
        root.setStyle("-fx-background-color: transparent;");
        root.setSpacing(19.94);
        if (technicians.size() > 0){
            for (Technician technician: technicians){
                if (!selectedTechnician.contains(technician)){
                    VBox node = createTechnicianDetailsPane(technician);
                    root.getChildren().add(node);
                }
            }
        }
        technicianPaneContainer.setContent(root);
    }

    public void loadComponent(){
        loadTechnicansDetailsPane();
        loadAddedTechnicianContent();
    }

    public void loadAddedTechnicianContent(){
        assignedTechnicianContainer.getChildren().clear();
        if (selectedTechnician.size() > 0){
            for (Technician selectedTechnician: selectedTechnician){
                HBox node = buildAddedTechnicianButton(selectedTechnician);
                assignedTechnicianContainer.getChildren().add(node);
            }
        } else {
            assignedTechnicianContainer.getChildren().add(assignTechnicianAlert);
        }
    }

    private VBox createTechnicianDetailsPane(Technician technicianDetails){
        // VBox
        VBox technicianPane = new VBox();
        NodeHelper.SetDropShadow(technicianPane, 0 ,2);
        technicianPane.setPadding(new Insets(14,10,19,9));
        technicianPane.setStyle("-fx-background-color: #E0E7E0;-fx-background-radius: 11.5");
        technicianPane.setCursor(Cursor.HAND);

        Label nameText = buildText(technicianDetails.FullName, true);
        Label emailText = buildText(technicianDetails.EmailAddress, false);
        Label telNoText = buildText(technicianDetails.TelNumber, false);
        Label specializationText = buildText(technicianDetails.Specialization, false);

        technicianPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addTechnician(technicianDetails);
                loadComponent();
                checkCompleted();
            }
        });
        technicianPane.getChildren().addAll(nameText, emailText, telNoText, specializationText);
        return technicianPane;
    }

    private void addTechnician(Technician technicianDetails){
        selectedTechnician.add(technicianDetails);
    }

    private void removeTechnician(Technician technician){
        selectedTechnician.remove(technician);
    }

    private HBox buildAddedTechnicianButton(Technician technician){
        HBox container = new HBox();
        container.setStyle("-fx-background-color: #A9CA91;-fx-font-family: Nunito;-fx-text-fill: white;-fx-font-weight: bold;-fx-font-size: 13.8;-fx-background-radius: 11.5;-fx-alignment: center");
        container.setPadding(new Insets(6.9,12.27,6.9,12.27));

        Label text = new Label(technician.FullName);
        text.setStyle("-fx-text-fill: white;-fx-padding: 0 0 0 0");
        text.setPrefWidth(180);

        ImageView imageView = new ImageView(new Image(ResourceLoader.LoadResourceAsStream("/material/deleteIcon.png")));
        imageView.setFitWidth(17.5);
        imageView.setFitHeight(17.5);

        container.getChildren().add(text);
        container.getChildren().add(imageView);
        container.setCursor(Cursor.HAND);

        container.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeTechnician(technician);
                loadComponent();
                checkCompleted();
            }
        });

        return container;
    }

    @FXML
    private void onSearchFieldType(){
        String value = searchField.getText();
        if (value.length() == 0){
            technicians = DataAccess.GetInstance().UserController.GetTechnicians();
            loadComponent();
            return;
        }
        for (char c : value.toCharArray()){
            if (!Character.isDigit(c)){
                checkCompleted();
                return;
            }
        }
        Technician technician= DataAccess.GetInstance().UserController.GetTechnician(Integer.parseInt(value));
        if (technician != null){
            technicians = new ArrayList<>(Arrays.asList(technician));
        } else {
            technicians = new ArrayList<>();
        }
        loadComponent();
    }

    private void checkCompleted(){
        if (selectedTechnician.size() > 0){
            completeButton.setDisable(false);
        } else {
            completeButton.setDisable(true);
        }
    }

    private Label buildText(String text, boolean isName){
        Label newText = new Label(text);

        if (isName){
            newText.setStyle("-fx-text-fill: #4C4C4C;-fx-font-family: Nunito;-fx-font-weight: bold;-fx-font-size: 15.34");
        } else {
            newText.setStyle("-fx-text-fill: #4C4C4C;-fx-font-family: Nunito;-fx-font-weight: normal;-fx-font-size: 13.8");
        }
        return newText;
    }

    @FXML
    private void onCompleteButtonClick(){
        _appointment.ActiveTechnicians = selectedTechnician;
        if (IsUpdateMode){
            DataAccess.GetInstance().AppointmentController.UpdateAppointment(_appointment);
        } else {
            DataAccess.GetInstance().AppointmentController.AddAppointment(_appointment);
        }
    }

    @FXML
    private void onCancelButtonClick(){

    }

}
