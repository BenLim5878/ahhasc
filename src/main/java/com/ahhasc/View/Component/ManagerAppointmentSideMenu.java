package com.ahhasc.View.Component;

import com.ahhasc.Main;
import com.ahhasc.Model.Appointment;
import com.ahhasc.View.Abstract.IMenu;
import com.ahhasc.View.ManagerAppointmentManagePage;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ManagerAppointmentSideMenu implements IMenu {

    public static final String VIEWALL = "viewall";
    public static final String ADDNEWAPPOINTMENT = "addnewappointment";
    public static final String ASSIGNTECHNICIANS = "assigntechnicians";
    public static final String UPDATEEXISTINGAPPOINTMENT = "updateexistingappointment";

    private Appointment _appointment;

    public void SetAppointment(Appointment appointment){
        _appointment = appointment;
        assignTechniciansButton.setDisable(false);
        updateExistingAppointmentButton.setDisable(false);
    }

    public void ClearAppointment(){
        _appointment = null;
        assignTechniciansButton.setDisable(true);
        updateExistingAppointmentButton.setDisable(true);
    }

    @FXML
    private Button viewAllButton, addNewAppointmentButton, assignTechniciansButton, updateExistingAppointmentButton;

    @Override
    public void SetTab(String tabName) {
        switch (tabName) {
            case VIEWALL -> {
                StyleTab(viewAllButton, true);
                StyleTab(addNewAppointmentButton, false);
                StyleTab(assignTechniciansButton, false);
                StyleTab(updateExistingAppointmentButton, false);
            }
            case ADDNEWAPPOINTMENT -> {
                StyleTab(viewAllButton, false);
                StyleTab(addNewAppointmentButton, true);
                StyleTab(assignTechniciansButton, false);
                StyleTab(updateExistingAppointmentButton, false);
            }
            case ASSIGNTECHNICIANS -> {
                StyleTab(viewAllButton, false);
                StyleTab(addNewAppointmentButton, false);
                StyleTab(assignTechniciansButton, true);
                StyleTab(updateExistingAppointmentButton, false);
            }
            case UPDATEEXISTINGAPPOINTMENT -> {
                StyleTab(viewAllButton, false);
                StyleTab(addNewAppointmentButton, false);
                StyleTab(assignTechniciansButton, false);
                StyleTab(updateExistingAppointmentButton, true);
            }
        }
    }

    private void StyleTab(Button tabButton, boolean isSelected){
        if (isSelected){
            tabButton.setStyle("-fx-text-fill: #40C650;-fx-background-color: transparent");
        } else{
            tabButton.setStyle("-fx-text-fill: #5D5D5D;-fx-background-color: transparent");
        }
    }

    @FXML
    private void viewAllClicked() throws IOException {
        WindowApp.SetScene("ManagerAppointmentViewAllPage.fxml");
    }

    @FXML
    private void addNewAppointmentClicked() throws IOException {
        WindowApp.SetScene("ManagerAppointmentManagePage.fxml");
    }

    @FXML
    private void assignTechnicianClicked(){

    }

    @FXML
    private void updateExistingAppointmentClicked() throws IOException {
        ManagerAppointmentManagePage controller = (ManagerAppointmentManagePage) WindowApp.SetScene("ManagerAppointmentManagePage.fxml");
        controller.LoadAppointment(_appointment);
    }
}
