package com.ahhasc.View;

import com.ahhasc.Config;
import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Session;
import com.ahhasc.Model.Technician;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Component.AppointmentRow;
import com.ahhasc.View.Component.TechnicianMenuLayout;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.WindowApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class TechnicianAppointmentBrowsePage implements Initializable, IDynamicContent {

    @FXML
    private VBox appointmentList;
    @FXML
    private Text currentPageNumberText;
    @FXML
    private Label appointmentListTitleText;
    @FXML
    private Button previousPageButton, nextPageButton, clearSearchButton,appointmentTypeButton;
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
    @FXML
    private ImageView appointmentListIcon;

    // Search Options
    private final String CUSTOMER = "Customer";
    private final String ISBEFORE = "Is Before";
    private final String ISAFTER = "Is After";

    // Page Options
    private static final String NEWAPPOINTMENT = "New Appointment";
    private static final String APPOINTMENTHISTORY = "Browse History...";

    private ArrayList<Appointment> _availableAppointment = new ArrayList();
    private Integer _currentPageNumber = 1;
    private Integer _totalPageNumber = 1;
    private Integer _startIndex = 0;
    private Integer _endIndex = 5;
    private String _searchType = null;
    private String _appointmentType = NEWAPPOINTMENT;

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

    public void SetAppointmentType(String appointmentType){
        _appointmentType = appointmentType;
        if (_appointmentType.equals(APPOINTMENTHISTORY)){
            appointmentListTitleText.setText("Appointment History");
            appointmentListIcon.setImage(new Image(ResourceLoader.LoadResourceAsStream("/material/checklistcompletedicon.png")));
            appointmentTypeButton.setText(NEWAPPOINTMENT);
        } else {
            appointmentListTitleText.setText("New Appointment");
            appointmentTypeButton.setText(APPOINTMENTHISTORY);
        }
        clearSearch();
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
                    appointmentRowController.SetPreviousScene(this::showContent);
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
                switch (_appointmentType){
                    case NEWAPPOINTMENT -> {
                        _availableAppointment = DataAccess.GetInstance().AppointmentController.GetAppointmentByCustomerName((Technician) Session.GetInstance().LoggedUser, targetCustomerName,true);
                    }
                    case APPOINTMENTHISTORY -> {
                        _availableAppointment = DataAccess.GetInstance().AppointmentController.GetAppointmentByCustomerName((Technician) Session.GetInstance().LoggedUser, targetCustomerName,false);
                    }
                }
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
                boolean isNewAppointment = true;
                if (_appointmentType.equals(APPOINTMENTHISTORY)){
                    isNewAppointment = false;
                }
                if (_searchType.equals(ISAFTER)){
                    _availableAppointment = DataAccess.GetInstance().AppointmentController.GetUnassignedAppointmentByChronology((Technician) Session.GetInstance().LoggedUser,targetChronology,true, isNewAppointment );
                } else {
                    _availableAppointment = DataAccess.GetInstance().AppointmentController.GetUnassignedAppointmentByChronology((Technician) Session.GetInstance().LoggedUser,targetChronology,false,isNewAppointment);
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
        switch (_appointmentType){
            case NEWAPPOINTMENT -> {
                _availableAppointment = DataAccess.GetInstance().AppointmentController.GetUnassignedAppointment((Technician) Session.GetInstance().LoggedUser);
            }
            case APPOINTMENTHISTORY -> {
                Technician loggedUser = (Technician) Session.GetInstance().LoggedUser;
                _availableAppointment = DataAccess.GetInstance().AppointmentController.GetAppointmentByTechnicianID(loggedUser.getTechnicianID(), false);
            }
        }
        UpdatePagingDetails();
        LoadAppointments();
        UpdatePagingControls();
    }

    @FXML
    private void changeAppointmentType() throws IOException {
        TechnicianAppointmentBrowsePage controller = (TechnicianAppointmentBrowsePage) WindowApp.SetScene("TechnicianAppointmentBrowsePage.fxml");
        if (_appointmentType.equals(APPOINTMENTHISTORY)){
            controller.SetAppointmentType(NEWAPPOINTMENT);
        } else {
            controller.SetAppointmentType(APPOINTMENTHISTORY);
        }
    }

    @Override
    public void showContent() throws IOException {
         TechnicianAppointmentBrowsePage controller = (TechnicianAppointmentBrowsePage) WindowApp.SetScene("TechnicianAppointmentBrowsePage.fxml");
         controller.SetAppointmentType(_appointmentType);
    }
}
