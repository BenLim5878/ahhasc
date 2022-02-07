package com.ahhasc.View.Component;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.Customer;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.ResourceLoader;
import com.ahhasc.View.Abstract.IDynamicContent;
import com.ahhasc.View.Helper.NodeHelper;
import com.ahhasc.View.ManagerAppointmentDetailsPage;
import com.ahhasc.View.ManagerCustomerManagePage;
import com.ahhasc.View.ViewModel.ViewAppointment;
import com.ahhasc.View.ViewModel.ViewCustomer;
import com.ahhasc.WindowApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewTableView implements Initializable,IDynamicContent {
    @FXML
    private TableView dataTable;

    private TableType _tableType;
    private ArrayList<TableColumn> _columns = new ArrayList<>();
    private ObservableList _tableData = FXCollections.observableList(new ArrayList());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            VBox node = (VBox) NodeHelper.LoadNode(ResourceLoader.LoadURL("/fxml/Component/NoResultFound.fxml"));
            dataTable.setPlaceholder(node);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void LoadConfig(TableType tableType){
        _tableType = tableType;
        load();
    }

    private void load(){
        switch (_tableType){
            case ManagerAppointment -> {
                _columns.add(new TableColumn<ViewAppointment, Integer>("Appointment ID"));
                _columns.add(new TableColumn<ViewAppointment, LocalDateTime>("Start Time"));
                _columns.add(new TableColumn<ViewAppointment, String>("Customer"));
                _columns.add(new TableColumn<ViewAppointment, Boolean>("Completed"));
                _columns.add(new TableColumn<ViewAppointment, Boolean>("Paid"));
            } case ManagerCustomer -> {
                _columns.add(new TableColumn<ViewCustomer, Integer>("Customer ID"));
                _columns.add(new TableColumn<ViewCustomer, String>("Full Name"));
                _columns.add(new TableColumn<ViewCustomer, String>("Email Address"));
                _columns.add(new TableColumn<ViewCustomer, String>("Telephone Number"));
                _columns.add(new TableColumn<ViewCustomer, Integer>("Room Unit"));
            }
        }
        configRows();
        configCells();
        for (TableColumn column : _columns){
            column.setReorderable(false);
            dataTable.getColumns().add(column);
        }
        loadDefaultData();
    }

    private void configRows(){
        dataTable.setRowFactory( tv -> {
            TableRow row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                switch (_tableType){
                    case ManagerAppointment ->{
                        ViewAppointment rowData = (ViewAppointment)row.getItem();
                        try{
                            ManagerAppointmentDetailsPage controller = (ManagerAppointmentDetailsPage) WindowApp.SetScene("ManagerAppointmentDetailsPage.fxml");
                            controller.SetAppointment(DataAccess.GetInstance().AppointmentController.GetAppointmentByID(rowData.getAppointmentID()));
                            controller.SetPreviousScene(this::showContent);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    } case ManagerCustomer -> {
                        ViewCustomer rowData = (ViewCustomer)row.getItem();
                        try{
                            ManagerCustomerManagePage controller = (ManagerCustomerManagePage) WindowApp.SetScene("ManagerCustomerManagePage.fxml");
                            controller.LoadCustomer(DataAccess.GetInstance().CustomerController.GetCustomer(new Customer(rowData.getCustomerID())).get(0));
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
            row.setOnMouseEntered(event->{
                if (row.getItem() != null){
                    row.setCursor(Cursor.HAND);
                    row.setStyle("-fx-background-color: #f5f8f5");
                    NodeHelper.SetDropShadow(row,0,2);
                }
            });
            row.setOnMouseExited(event->{
                row.setCursor(Cursor.DEFAULT);
                row.setStyle("-fx-background-color: transparent");
            });
            return row ;
        });
    }

    private void configCells(){
        switch (_tableType){
            case ManagerAppointment -> {
                _columns.get(0).setCellValueFactory(new PropertyValueFactory<ViewAppointment,Integer>("AppointmentID"));
                _columns.get(1).setCellValueFactory(new PropertyValueFactory<ViewAppointment,LocalDateTime>("StartTime"));
                _columns.get(2).setCellValueFactory(new PropertyValueFactory<ViewAppointment,String>("Customer"));
                _columns.get(3).setCellValueFactory(new PropertyValueFactory<ViewAppointment,Boolean>("IsCompleted"));
                _columns.get(4).setCellValueFactory(new PropertyValueFactory<ViewAppointment,Boolean>("IsPaid"));
            }
            case ManagerCustomer -> {
                _columns.get(0).setCellValueFactory(new PropertyValueFactory<ViewAppointment,Integer>("CustomerID"));
                _columns.get(1).setCellValueFactory(new PropertyValueFactory<ViewAppointment,String>("FullName"));
                _columns.get(2).setCellValueFactory(new PropertyValueFactory<ViewAppointment,String>("EmailAddress"));
                _columns.get(3).setCellValueFactory(new PropertyValueFactory<ViewAppointment,String>("TelNumber"));
                _columns.get(4).setCellValueFactory(new PropertyValueFactory<ViewAppointment,Integer>("RoomNo"));
            }
        }
    }

    public void searchData(Object searchObjectDescriptor){
        _tableData.clear();
        switch (_tableType){
            case ManagerAppointment -> {
                Appointment appointment = DataAccess.GetInstance().AppointmentController.GetAppointmentByID(((Appointment)searchObjectDescriptor).getAppointmentID());
                if (appointment != null){
                    _tableData.add(new ViewAppointment(appointment));
                }
            } case ManagerCustomer -> {
                Customer target = (Customer) searchObjectDescriptor;
                ArrayList<Customer> customers = DataAccess.GetInstance().CustomerController.GetCustomer(target);
                if (customers.size() > 0){
                    customers.forEach(customer -> {_tableData.add(new ViewCustomer(customer));});
                }
            }
        }
    }

    public void loadDefaultData(){
        _tableData.clear();
        switch (_tableType) {
            case ManagerAppointment -> {
                ArrayList<Appointment> appointments = DataAccess.GetInstance().AppointmentController.GetAppointments();
                appointments.forEach(appointment -> _tableData.add(new ViewAppointment(appointment)));
            } case ManagerCustomer -> {
                ArrayList<Customer> customers = DataAccess.GetInstance().CustomerController.GetCustomers();
                customers.forEach(customer->_tableData.add(new ViewCustomer(customer)));
            }
        }
        dataTable.setItems(_tableData);
    }

    public void showEmptyData(){
        _tableData.clear();
    }

    @Override
    public void showContent() throws IOException {
        switch (_tableType){
            case ManagerAppointment -> {
                WindowApp.SetScene("ManagerAppointmentViewAllPage.fxml");
            }
            case ManagerCustomer -> {
                WindowApp.SetScene("ManagerCustomerViewAllPage.fxml");
            }
        }
    }
}
