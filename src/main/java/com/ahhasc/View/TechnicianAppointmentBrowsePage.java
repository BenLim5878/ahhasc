package com.ahhasc.View;

import com.ahhasc.Config;
import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Session;
import com.ahhasc.Model.Technician;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Component.AppointmentRow;
import com.ahhasc.View.Component.TechnicianMenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TechnicianAppointmentBrowsePage implements Initializable {

    @FXML
    private VBox appointmentList;
    @FXML
    private Text currentPageNumberText;
    @FXML
    private Button previousPageButton, nextPageButton, clearSearchButton;
    @FXML
    private ComboBox<String> searchAppointmentCombobox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField searchField,hourTextfield, minuteTextfield;
    @FXML
    private HBox inputContainer, searchFieldContainer;
    @FXML
    private Button searchButton;
    @FXML
    private TechnicianMenuLayout layoutController;

    // Search Options
    private final String CUSTOMER = "Customer";
    private final String ISBEFORE = "Is Before";
    private final String ISAFTER = "Is After";

    private ArrayList<Appointment> _availableAppointment = new ArrayList();
    private Integer _currentPageNumber = 1;
    private Integer _totalPageNumber = 1;
    private Integer _startIndex = 0;
    private Integer _endIndex = 5;
    private String _searchType = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        layoutController.SetTab(TechnicianMenuLayout.APPOINTMENT);
        clearSearch();
        searchAppointmentCombobox.setItems(Config.SearchAppointmentTypeChoices);
        searchAppointmentCombobox.setValue(CUSTOMER);
        _searchType = CUSTOMER;
        DefaultSearchInput();
        NodeHelper.SetCustomNumericField(hourTextfield,2, 23);
        NodeHelper.SetCustomNumericField(minuteTextfield,2,59);
    }

    private void UpdatePagingDetails(){
        _totalPageNumber = (int) Math.ceil((double)_availableAppointment.size() / 5);
    }

    private void DefaultSearchInput(){
        if (!inputContainer.getChildren().contains(searchFieldContainer)){
            inputContainer.getChildren().add(0, searchFieldContainer);
        }
        inputContainer.getChildren().remove(hourTextfield);
        inputContainer.getChildren().remove(minuteTextfield);
        inputContainer.getChildren().remove(datePicker);
    }

    private void LoadAppointments(){
        appointmentList.getChildren().clear();
        try{
            for (Appointment appointment : _availableAppointment){
                if (_availableAppointment.indexOf(appointment) >= _startIndex && _availableAppointment.indexOf(appointment) < _endIndex){
                    FXMLLoader appointmentRowLoader = NodeHelper.LoadFXMLLoader(ResourceLoader.LoadURL("/fxml/Component/AppointmentRow.fxml"));
                    appointmentList.getChildren().add(appointmentRowLoader.load());
                    AppointmentRow appointmentRowController = (AppointmentRow) appointmentRowLoader.getController();
                    appointmentRowController.SetAppointment(appointment);
                    appointmentRowController.SetRowSpacing(20);
                }
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void UpdatePagingControls(){
        nextPageButton.setDisable(false);
        previousPageButton.setDisable(false);
        // No appointment
        if (_availableAppointment.size() == 0){
            nextPageButton.setDisable(true);
            previousPageButton.setDisable(true);
            currentPageNumberText.setText(_currentPageNumber.toString());
            return;
        }
        // Has appointment
        if (_currentPageNumber == _totalPageNumber){
            nextPageButton.setDisable(true);
        }
        if (_currentPageNumber == 1){
            previousPageButton.setDisable(true);
        }
        currentPageNumberText.setText(_currentPageNumber.toString());
    }

    @FXML
    private void toOverview() throws IOException {
        WindowApp.SetScene("TechnicianAppointmentOverviewPage.fxml");
    }

    @FXML
    private void previousPage(){
        _startIndex -= 5;
        _endIndex -= 5;
        _currentPageNumber -= 1;
        LoadAppointments();
        UpdatePagingControls();
    }

    @FXML
    private void nextPage(){
        _startIndex += 5;
        _endIndex += 5;
        _currentPageNumber += 1;
        LoadAppointments();
        UpdatePagingControls();
    }

    @FXML
    private void changeSearchType(){
        _searchType = searchAppointmentCombobox.getValue();
        if (_searchType.equals(ISBEFORE) || _searchType.equals(ISAFTER)){
            inputContainer.getChildren().remove(searchFieldContainer);
            if (!inputContainer.getChildren().contains(datePicker) && !inputContainer.getChildren().contains(hourTextfield) && !inputContainer.getChildren().contains(minuteTextfield)){
                inputContainer.getChildren().add(0, datePicker);
                inputContainer.getChildren().add(1, hourTextfield);
                inputContainer.getChildren().add(2, minuteTextfield);
            }
        } else {
            DefaultSearchInput();
        }
        resetFields();
    }

    private void resetFields(){
        searchField.clear();
        datePicker.setValue(LocalDate.now());
        hourTextfield.clear();
        minuteTextfield.clear();
        searchButton.setDisable(true);
    }

    @FXML
    private void searchAppointment(){
        switch (_searchType){
            case CUSTOMER -> {
                String targetCustomerName = searchField.getText();
                _availableAppointment = DataAccess.GetInstance().AppointmentController.GetUnassignedAppointmentByCustomerName((Technician) Session.GetInstance().LoggedUser, targetCustomerName );
            }   default  -> {
                // Hour
                Integer targetHour = 0;
                if (hourTextfield.getText().length() > 0){
                    targetHour = Integer.parseInt(hourTextfield.getText());
                }

                // Minute
                Integer targetMinute = 0;
                if (minuteTextfield.getText().length() > 0){
                    targetMinute = Integer.parseInt(minuteTextfield.getText());
                }

                // Time
                LocalTime targetTime = LocalTime.of(targetHour, targetMinute);

                // Chronology
                LocalDateTime targetChronology = LocalDateTime.of(datePicker.getValue(), targetTime);

                if (_searchType.equals(ISAFTER)){
                    _availableAppointment = DataAccess.GetInstance().AppointmentController.GetUnassignedAppointmentByChronology((Technician) Session.GetInstance().LoggedUser,targetChronology,true );
                } else {
                    _availableAppointment = DataAccess.GetInstance().AppointmentController.GetUnassignedAppointmentByChronology((Technician) Session.GetInstance().LoggedUser,targetChronology,false );
                }
            }
        }
        ResetPagingArguments();
        UpdatePagingDetails();
        LoadAppointments();
        UpdatePagingControls();
        clearSearchButton.setVisible(true);
    }

    private void ResetPagingArguments(){
        _currentPageNumber = 1;
        _totalPageNumber = 1;
        _startIndex = 0;
        _endIndex = 5;
    }

    @FXML
    private void searchFieldTyped(){
        String searchText = searchField.getText();
        if (searchText.trim().length() > 0){
            searchButton.setDisable(false);
        } else {
            searchButton.setDisable(true);
        }
    }

    @FXML
    private void chronologyInputChanged(){
        String targetHour = hourTextfield.getText();
        String targetMinute = minuteTextfield.getText();
        if (targetMinute.length() > 0){
            if (targetHour.length() > 0){
                searchButton.setDisable(false);
            }
            else {
                searchButton.setDisable(true);
            }
        } else {
            searchButton.setDisable(false);
        }
    }

    @FXML
    private void clearSearch(){
        resetFields();
        clearSearchButton.setVisible(false);
        _availableAppointment = DataAccess.GetInstance().AppointmentController.GetUnassignedAppointment((Technician) Session.GetInstance().LoggedUser);
        UpdatePagingDetails();
        LoadAppointments();
        UpdatePagingControls();
    }


}
