package com.ahhasc.View;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.Customer;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Room;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Component.*;
import com.ahhasc.View.Helper.NodeHelper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ManagerCustomerViewAllPage implements Initializable {
    @FXML
    private TextField searchCustomerTextField;
    @FXML
    private ViewTableView tableViewController;
    @FXML
    private ManagerCustomerSideMenu sideMenuController;
    @FXML
    private ManagerMenuLayout topMenuController;
    @FXML
    private Button searchCustomerTypeButton;
    @FXML
    private HBox content;
    @FXML
    private VBox contentList;

    private final static String CUSTOMERID = "Customer ID";
    private final static String EMAILADDRESS = "Email Address";
    private final static String FULLNAME = "Name";
    private final static String ROOMNO = "Room Unit";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewController.LoadConfig(TableType.ManagerCustomer);
        topMenuController.SetTab(ManagerMenuLayout.CUSTOMER);
        sideMenuController.SetTab(ManagerCustomerSideMenu.VIEWALL);
    }

    @FXML
    private void searchCustomer(Event e){
        TextField textField = (TextField) e.getSource();
        String text = textField.getText().trim();
        if (text.length() == 0){
            tableViewController.loadDefaultData();
            return;
        }
        Customer customerDescriptor = new Customer();
        switch (searchCustomerTypeButton.getText()){
            case CUSTOMERID -> {
                if (DataAccess.IsDigit(text)){
                    int customerID = Integer.parseInt(text);
                    customerDescriptor.setCustomerID(customerID);
                    tableViewController.searchData(customerDescriptor);
                } else {
                    tableViewController.showEmptyData();
                }
            } case EMAILADDRESS -> {
                customerDescriptor.EmailAddress = text;
                tableViewController.searchData(customerDescriptor);
            } case FULLNAME -> {
                customerDescriptor.FullName = text;
                tableViewController.searchData(customerDescriptor);
            } case ROOMNO -> {
                if (DataAccess.IsDigit(text)){
                    int roomUnit = Integer.parseInt(text);
                    Room customerRoom = new Room();
                    customerRoom.Unit = roomUnit;
                    customerDescriptor.Room = customerRoom;
                    tableViewController.searchData(customerDescriptor);
                } else {
                    tableViewController.showEmptyData();
                }
            }
        }
    }

    @FXML
    private void changeCustomerType(){
        searchCustomerTextField.clear();
        tableViewController.loadDefaultData();
        String customerType = searchCustomerTypeButton.getText();
        switch (customerType){
            case CUSTOMERID -> {
                searchCustomerTypeButton.setText(EMAILADDRESS);
                searchCustomerTextField.setPromptText(EMAILADDRESS);
            } case EMAILADDRESS -> {
                searchCustomerTypeButton.setText(FULLNAME);
                searchCustomerTextField.setPromptText(FULLNAME);
            } case FULLNAME -> {
                searchCustomerTypeButton.setText(ROOMNO);
                searchCustomerTextField.setPromptText(ROOMNO);
            } case ROOMNO -> {
                searchCustomerTypeButton.setText(CUSTOMERID);
                searchCustomerTextField.setPromptText(CUSTOMERID);
            }
        }
    }

}
