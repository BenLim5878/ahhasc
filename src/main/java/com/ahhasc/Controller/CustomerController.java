package com.ahhasc.Controller;

import com.ahhasc.Model.*;
import java.util.ArrayList;
import java.util.Collections;

public class CustomerController  implements IController{

    private Repository _repository;
    private ArrayList<Customer> _data = new ArrayList<Customer>();

    public CustomerController(String storeFileName){
        _repository = new Repository(storeFileName);
        _repository.Load();
        this.Serialize();
    }

    @Override
    public void Serialize() {
        for (String customerRecord : _repository.GetRecords()){
            String[] record =  customerRecord.split(";");
            Customer customer = new Customer(Integer.parseInt(record[0]));
            customer.EmailAddress = record[1];
            customer.FullName = record[2];
            customer.TelNumber = record[3];
            customer.Room =  new Room(Integer.parseInt(record[4]));
            _data.add(customer);
        }
    }

    public ArrayList<Customer> GetCustomers(){
        _data.forEach(customer -> customer.Room = DataAccess.GetInstance().RoomController.GetRoom(new Room(customer.Room.getRoomID())).get(0));
        return this._data;
    }

    public ArrayList<Customer> GetCustomer(Customer customerDescriptor){
        ArrayList<Customer> out = new ArrayList<Customer>();
        for (Customer customer: this.GetCustomers()){
            if (customer.Contains(customerDescriptor)){
                customer.Room = DataAccess.GetInstance().RoomController.GetRoom(new Room(customer.Room.getRoomID())).get(0);
                out.add(customer);
            }
        }
        return out;
    }

    public void RegisterCustomer(Customer customerDescriptor){
        int userID = GetNewCustomerID();

        customerDescriptor.setCustomerID(userID);

        _data.add(customerDescriptor);
        _repository.AddNewRecord(customerDescriptor.toString());
    }

    public void UpdateCustomer(Customer customerDescriptor){
        int dataIndex = this.GetCustomerIndex(customerDescriptor.getCustomerID());
        _repository.UpdateRecord(dataIndex,customerDescriptor.toString());
        _data.set(dataIndex,customerDescriptor);
    }

    public void DeleteCustomer(int customerID){
        int dataIndex = this.GetCustomerIndex(customerID);
        _repository.DeleteRecord(dataIndex);
        _data.remove(dataIndex);
    }

    public boolean ValidateCustomer(int customerID, Room roomDescriptor){
        ArrayList<Customer> customers = this.GetCustomer(new Customer(customerID));
        Customer customer = customers.get(0);

        if (customer.Room.Contains(roomDescriptor)){
            return true;
        }
        return false;
    }

    private int GetCustomerIndex(int customerID){
        for (Customer customer: _data){
            if (customer.getCustomerID() == customerID){
                return _data.indexOf(customer);
            }
        }
        return -1;
    }

    private int GetNewCustomerID(){
        if (_data.size() == 0){
            return 0;
        }
        ArrayList<Integer> ids = new ArrayList<Integer>();
        _data.forEach(customer -> ids.add(customer.getCustomerID()));
        return (Collections.max(ids) + 1);
    }
}
