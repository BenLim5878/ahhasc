package com.ahhasc.View;

import com.ahhasc.Model.Customer;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Room;
import com.ahhasc.View.Component.ManagerCustomerSideMenu;
import com.ahhasc.View.Component.MenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerCustomerManagePage implements Initializable {

    @FXML
    private TextField nameField, telephoneNumberField, emailAddressField, roomUnitField, floorField, blockField;
    @FXML
    private Text roomNotFoundText, invalidEmailAddressText,invalidTelephoneNumberText;
    @FXML
    private Button completeButton, cancelButton;
    @FXML
    private MenuLayout menuLayoutController;
    @FXML
    private ManagerCustomerSideMenu sideMenuController;

    private Customer _customer;
    private boolean IsUpdateMode =false ;
    private Room _selectedRoom;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuLayoutController.SetTab(MenuLayout.CUSTOMER);
        sideMenuController.SetTab(ManagerCustomerSideMenu.ADDCUSTOMER);
        NodeHelper.setTextfieldDigitOnly(telephoneNumberField);
        NodeHelper.setTextfieldDigitOnly(roomUnitField);
    }

    public void LoadCustomer(Customer customer){
        sideMenuController.SetTab(ManagerCustomerSideMenu.UPDATECUSTOMER);
        _customer = customer;
        _selectedRoom = customer.Room;
        IsUpdateMode = true;

        nameField.setText(customer.FullName);
        emailAddressField.setText(customer.EmailAddress);
        telephoneNumberField.setText(customer.TelNumber);
        roomUnitField.setText(customer.Room.Unit.toString());
        floorField.setText(customer.Room.Floor.toString());
        blockField.setText(customer.Room.Block);
    }

    @FXML
    private void onCompleteButtonClick(){
        if (!IsUpdateMode){
            _customer = new Customer();
        }

        String name = DataAccess.Capitalize(nameField.getText().trim());
        String email = emailAddressField.getText().trim();
        String telephoneNumber = telephoneNumberField.getText().trim();

        _customer.FullName = name;
        _customer.EmailAddress = email;
        _customer.TelNumber = telephoneNumber;
        _customer.Room = _selectedRoom;

        if (IsUpdateMode && _customer.getCustomerID() != null){
            DataAccess.GetInstance().CustomerController.UpdateCustomer(_customer);
        } else {
            DataAccess.GetInstance().CustomerController.RegisterCustomer(_customer);
        }

    }

    @FXML
    private void onCancelButtonClick(){

    }

    @FXML
    private void onRoomUnitType(){
        String roomUnit = roomUnitField.getText().trim();
        if (roomUnit.length() == 0){
            roomNotFoundText.setVisible(false);
            clearFields();
        }else {
            Room out = new Room();
            if (DataAccess.IsDigit(roomUnit)){
                Room roomDescriptor = new Room();
                roomDescriptor.Unit = Integer.parseInt(roomUnit);
                ArrayList<Room> data = DataAccess.GetInstance().RoomController.GetRoom(roomDescriptor);
                if (data.size() > 0){
                    out = data.get(0);
                }
            }
            if (out.getRoomID() != null){
                _selectedRoom = out;
                floorField.setText(out.Floor.toString());
                blockField.setText(out.Block);
            } else {
                roomNotFoundText.setVisible(true);
                clearFields();
            }
        }
        checkCompleted();
    }

    @FXML
    private void onTelephoneNumberType(){
        String telNo = telephoneNumberField.getText().trim();
        if (!DataAccess.IsValidTelephoneNumber(telNo)){
            invalidTelephoneNumberText.setVisible(true);
        } else {
            invalidTelephoneNumberText.setVisible(false);
        }
        checkCompleted();
    }

    @FXML
    private void onEmailAddressType(){
        String emailAddress = emailAddressField.getText().trim();
        if (!DataAccess.IsValidEmailAddress(emailAddress)){
            invalidEmailAddressText.setVisible(true);
        } else {
            invalidEmailAddressText.setVisible(false);
        }
        checkCompleted();
    }

    @FXML
    private void onNameType(){
        checkCompleted();
    }

    private void clearFields(){
        floorField.clear();
        blockField.clear();
    }

    private void checkCompleted(){
        String name = nameField.getText().trim();
        String email = emailAddressField.getText().trim();
        String telephoneNumber = telephoneNumberField.getText().trim();
        String roomUnit = roomUnitField.getText().trim();
        String floor = floorField.getText().trim();

        if (name.length() > 0 && email.length() >0 && telephoneNumber.length() > 0 && roomUnit.length() > 0 && floor.length() > 0 && !invalidTelephoneNumberText.isVisible() && !invalidEmailAddressText.isVisible() && !roomNotFoundText.isVisible()){
            completeButton.setDisable(false);
        } else {
            completeButton.setDisable(true);
        }
    }
}
