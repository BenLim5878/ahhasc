package com.ahhasc.View.Component;

import com.ahhasc.Model.Customer;
import com.ahhasc.View.Abstract.IMenu;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ManagerCustomerSideMenu implements IMenu {

    @FXML
    private Button viewAllButton, addCustomerButton, manageRoomButton, updateCustomerButton;

    public static final String VIEWALL = "viewall";
    public static final String ADDCUSTOMER = "addcustomer";
    public static final String MANAGEROOM = "manageroom";
    public static final String UPDATECUSTOMER = "updatecustomer";

    private Customer _customer;

    public void SetCustomer(Customer customer){
        _customer = customer;
        updateCustomerButton.setDisable(false);
    }

    public void ClearCustomer(){
        _customer = null;
        updateCustomerButton.setDisable(true);
    }

    @Override
    public void SetTab(String tabName) {
        switch (tabName) {
            case VIEWALL -> {
                StyleTab(viewAllButton, true);
                StyleTab(addCustomerButton, false);
                StyleTab(manageRoomButton, false);
                StyleTab(updateCustomerButton, false);
            }
            case ADDCUSTOMER -> {
                StyleTab(viewAllButton, false);
                StyleTab(addCustomerButton, true);
                StyleTab(manageRoomButton, false);
                StyleTab(updateCustomerButton, false);
            }
            case MANAGEROOM -> {
                StyleTab(viewAllButton, false);
                StyleTab(addCustomerButton, false);
                StyleTab(manageRoomButton, true);
                StyleTab(updateCustomerButton, false);
            }
            case UPDATECUSTOMER -> {
                StyleTab(viewAllButton, false);
                StyleTab(addCustomerButton, false);
                StyleTab(manageRoomButton, false);
                StyleTab(updateCustomerButton, true);
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
    private void viewAllClicked(){

    }

    @FXML
    private void addCustomerClicked() throws IOException {
    }

    @FXML
    private void manageRoomClicked() throws IOException {
        WindowApp.SetScene("ManagerRoomManagePage.fxml");
    }

    @FXML
    private void updateCustomerClicked() throws IOException {

    }
}
