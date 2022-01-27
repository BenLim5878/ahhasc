package com.ahhasc.View;

import com.ahhasc.Config;
import com.ahhasc.Model.*;
import com.ahhasc.View.Component.ManagerAppointmentSideMenu;
import com.ahhasc.View.Component.ManagerMenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerAppointmentManagePage implements Initializable {

    @FXML
    private ManagerMenuLayout menuLayoutController;
    @FXML
    private ManagerAppointmentSideMenu sideMenuController;
    @FXML
    private TextField searchField, nameField, emailAddressField, roomUnitField, floorField, blockField,paymentField;
    @FXML
    private DatePicker dueDateField, startDateField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private HBox container;
    @FXML
    private Button searchTypeButton, nextButton, saveButton;
    @FXML
    private Text notFoundMessage,dueDateErrorMessage,startDateErrorMessage;
    @FXML
    private ComboBox<Integer> hourComboBox;
    @FXML
    private ComboBox<String> minuteComboBox, timeTypeComboBox;

    private String searchCondition = ID;

    private static final String ID = "Customer ID";
    private static final String EMAILADDRESS = "Email";
    private static final String NAME = "Name";

    private Appointment _appointment;
    private boolean IsUpdateMode = false;
    private Customer _selectedCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuLayoutController.SetTab(ManagerMenuLayout.APPOINTMENT);
        sideMenuController.SetTab(ManagerAppointmentSideMenu.ADDNEWAPPOINTMENT);
        NodeHelper.SetTextfieldDigitOnly(paymentField);
        DateTimeFormatter formatter = DataAccess.DefaultTimeFormat;
        hourComboBox.setItems(Config.HourChoices);
        minuteComboBox.setItems(Config.MinuteChoices);
        timeTypeComboBox.setItems(Config.TimeTypeChoices);
        loadDefaultTime();
    }

    private void loadDefaultTime(){
        hourComboBox.valueProperty().set(12);
        minuteComboBox.valueProperty().set("00");
        timeTypeComboBox.valueProperty().set("PM");
    }

    public void LoadAppointment(Appointment appointment){
        _appointment = appointment;
        IsUpdateMode = true;

        sideMenuController.SetTab(ManagerAppointmentSideMenu.UPDATEEXISTINGAPPOINTMENT);
        _selectedCustomer = appointment.BookingCustomer;

        Integer amount = Math.round(appointment.Payment.Amount);
        nameField.setText(appointment.BookingCustomer.FullName);
        emailAddressField.setText(appointment.BookingCustomer.EmailAddress);
        roomUnitField.setText(appointment.BookingCustomer.Room.Unit.toString());
        floorField.setText(appointment.BookingCustomer.Room.Floor.toString());
        blockField.setText(appointment.BookingCustomer.Room.Block);
        paymentField.setText(amount.toString());
        dueDateField.setValue(appointment.Payment.DueDate);
        startDateField.setValue(appointment.StartTime.toLocalDate());
        hourComboBox.setValue(Integer.parseInt(NodeHelper.ProcessTime(appointment.StartTime)[0]));
        minuteComboBox.setValue(NodeHelper.ProcessTime(appointment.StartTime)[1]);
        timeTypeComboBox.setValue(NodeHelper.ProcessTime(appointment.StartTime)[2]);
        descriptionField.setText(appointment.Description);

        nextButton.setDisable(false);
        saveButton.setDisable(true);
    }

    @FXML
    private void onSearchTypeButtonClick(){
        switch (searchTypeButton.getText()){
            case ID -> {
                searchCondition = NAME;
                searchTypeButton.setText(NAME);
            }
            case NAME -> {
                searchCondition = EMAILADDRESS;
                searchTypeButton.setText(EMAILADDRESS);
            }
            case EMAILADDRESS ->{
                searchCondition = ID;
                searchTypeButton.setText(ID);
            }
        }
        searchField.clear();
        clearFields();
        checkCompleted();
        notFoundMessage.setVisible(false);
    }

    @FXML
    private void onSearchFieldType(){
        Customer customerDescriptor = new Customer();
        String value = searchField.getText();
        if (value.length() == 0){
            notFoundMessage.setVisible(false);
            clearFields();
            checkCompleted();
            return;
        }
        switch (searchCondition){
            case ID -> {
                if (!DataAccess.IsDigit(value)){
                    notFoundMessage.setVisible(true);
                    clearFields();
                    checkCompleted();
                    return;
                }
                customerDescriptor.setCustomerID(Integer.parseInt(value));
            }
            case NAME -> {
                customerDescriptor.FullName = value;
            }
            case EMAILADDRESS ->{
                customerDescriptor.EmailAddress = value;
            }
        }
        ArrayList<Customer> results = DataAccess.GetInstance().CustomerController.GetCustomer(customerDescriptor);
        if (results.size() > 0 ) {
            Customer target  = results.get(0);
            _selectedCustomer = target;
            nameField.setText(target.FullName);
            emailAddressField.setText(target.EmailAddress);
            roomUnitField.setText(target.Room.Unit.toString());
            floorField.setText(target.Room.Floor.toString());
            blockField.setText(target.Room.Block);
            notFoundMessage.setVisible(false);

        } else {
            clearFields();
            notFoundMessage.setVisible(true);
        }
        checkCompleted();
    }

    @FXML
    private void onPaymentKeyType(){
        if (paymentField.getText().equals("0")){
            paymentField.setText("");
        }
        checkCompleted();
    }

    @FXML
    private void onDateAction(Event e){
        DatePicker src = (DatePicker) e.getSource();
        LocalDate triggerDate = src.getValue();
        switch (src.getId()) {
            case "dueDateField":
                if (triggerDate.isBefore(LocalDate.now())) {
                    dueDateErrorMessage.setVisible(true);
                } else {
                    dueDateErrorMessage.setVisible(false);
                }
                break;
            case "startDateField":
                if (triggerDate.isBefore(LocalDate.now())) {
                    startDateErrorMessage.setVisible(true);
                } else {
                    startDateErrorMessage.setVisible(false);
                }
        }
        checkCompleted();
    }

    @FXML
    private void onDescriptionType(){
        checkCompleted();
    }

    @FXML
    private void comboBoxSelect(){
        checkCompleted();
    }

    private void checkCompleted(){
        if (nameField.getText().trim().length() > 0 && paymentField.getText().trim().length() > 0 && !dueDateErrorMessage.isVisible() && !startDateErrorMessage.isVisible() &&dueDateField.getValue() != null && startDateField.getValue() != null && descriptionField.getText().trim().length() > 0){
            nextButton.setDisable(false);
            saveButton.setDisable(false);
        } else {
            nextButton.setDisable(true);
            saveButton.setDisable(true);
        }
    }

    private void clearFields(){
        nameField.clear();
        emailAddressField.clear();
        roomUnitField.clear();
        floorField.clear();
        blockField.clear();
    }

    @FXML
    private void onNextButtonClick() throws IOException {
        setContent();
        ManagerTechnicianManagePage controller = (ManagerTechnicianManagePage) WindowApp.SetScene("ManagerTechniciansManagePage.fxml");
        controller.LoadAppointment(_appointment, IsUpdateMode);
    }

    private void setContent(){
        if (!IsUpdateMode){
            _appointment = new Appointment();
        }
        _appointment.BookingCustomer = _selectedCustomer;
        _appointment.StartTime = LocalDateTime.of(startDateField.getValue(),DataAccess.ParseTime(hourComboBox.getValue(),Integer.parseInt(minuteComboBox.getValue()),timeTypeComboBox.getValue()));
        _appointment.Description = descriptionField.getText().trim();

        if (IsUpdateMode){
            _appointment.Payment.Amount = Float.parseFloat(paymentField.getText());
            _appointment.Payment.DueDate = dueDateField.getValue();
        } else {
            Payment payment = new Payment(false, dueDateField.getValue(),Float.parseFloat(paymentField.getText()));
            _appointment.ActiveManager = (Manager) Session.GetInstance().LoggedUser;
            _appointment.Payment = payment;
            _appointment.setIsCompleted(false);
        }
    }

    @FXML
    private void onSaveButtonClick(){
        setContent();
        if (IsUpdateMode){
            DataAccess.GetInstance().AppointmentController.UpdateAppointment(_appointment);
        } else {
            DataAccess.GetInstance().AppointmentController.AddAppointment(_appointment);
        }
    }
}
