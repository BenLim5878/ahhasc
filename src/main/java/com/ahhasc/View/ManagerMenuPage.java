package com.ahhasc.View;

import com.ahhasc.Model.AuthenticatedResult;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.View.Component.ManagerMenuLayout;
import com.ahhasc.View.ViewModel.ViewAuthenticateResult;
import com.ahhasc.WindowApp;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerMenuPage implements Initializable {

    @FXML
    private TableView<ViewAuthenticateResult> authLoggerTable;

    @FXML
    private TableColumn timeAuthenticatedColumn, userIDColumn, successfulColumn, errorMessageColumn;

    @FXML
    private ManagerMenuLayout menuLayoutController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeAuthenticatedColumn.setCellValueFactory(new PropertyValueFactory<>("TimeAuthenticated"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("AuthenticatedUser"));
        successfulColumn.setCellValueFactory(new PropertyValueFactory<>("IsSuccessful"));
        errorMessageColumn.setCellValueFactory(new PropertyValueFactory<>("ErrorMessage"));
        ArrayList<AuthenticatedResult> logs = DataAccess.GetInstance().AuthLogger.GetAllRecord();
        ArrayList<ViewAuthenticateResult> logsView = new ArrayList<ViewAuthenticateResult>();
        logs.forEach(log -> logsView.add(new ViewAuthenticateResult(log)));
        authLoggerTable.setItems(FXCollections.observableList(FXCollections.observableList(logsView)));
        menuLayoutController.SetTab(ManagerMenuLayout.MENU);
    }

    @FXML
    private void onScheduleAppointmentButtonClicked(){
        System.out.println("haha");
    }

    @FXML
    private void toAddAppointment() throws IOException {
        WindowApp.SetScene("ManagerAppointmentManagePage.fxml");
    }

    @FXML
    private void toRegisterCustomer() throws IOException {
        WindowApp.SetScene("ManagerCustomerManagePage.fxml");
    }
}
