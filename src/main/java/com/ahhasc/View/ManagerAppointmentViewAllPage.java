package com.ahhasc.View;

import com.ahhasc.Model.Appointment;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Component.ManagerAppointmentSideMenu;
import com.ahhasc.View.Component.ManagerMenuLayout;
import com.ahhasc.View.Component.TableType;
import com.ahhasc.View.Component.ViewTableView;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ManagerAppointmentViewAllPage implements Initializable {
    @FXML
    private TextField searchAppointmentTextField;
    @FXML
    private ViewTableView tableViewController;
    @FXML
    private ManagerAppointmentSideMenu sideMenuController;
    @FXML
    private ManagerMenuLayout topMenuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NodeHelper.SetTextfieldDigitOnly(searchAppointmentTextField);
        tableViewController.LoadConfig(TableType.ManagerAppointment);
        topMenuController.SetTab(ManagerMenuLayout.APPOINTMENT);
        sideMenuController.SetTab(ManagerAppointmentSideMenu.VIEWALL);
    }

    @FXML
    private void searchAppointment(Event e){
        TextField textField = (TextField) e.getSource();
        String appointmentID = textField.getText();
        if (appointmentID.length() == 0){
            tableViewController.loadDefaultData();
        } else {
            Appointment searchTarget = new Appointment();
            searchTarget.setAppointmentID(Integer.parseInt(appointmentID));
            tableViewController.searchData(searchTarget);
        }
    }
}
