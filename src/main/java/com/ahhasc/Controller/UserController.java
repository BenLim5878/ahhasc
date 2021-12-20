package com.ahhasc.Controller;

import com.ahhasc.Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class UserController implements  IController{

    private Repository _repository;
    private ArrayList<User> _data = new ArrayList<User>();

    public UserController(String storeFileName){
        this._repository = new Repository(storeFileName);
        this._repository.Load();
        this.Serialize();
    }

    @Override
    public void Serialize() {
        for (String userRecord : _repository.GetRecords()){
            String[] record =  userRecord.split(";");
            switch (record[1]){
                case User.MANAGER:
                    Manager manager = new Manager(Integer.parseInt(record[2]));
                    manager.setID(Integer.parseInt(record[0]));
                    manager.Role = record[1];
                    manager.EmailAddress = record[3];
                    manager.FullName = record[5];
                    manager.TelNumber = record[6];
                    manager.setIsManager(Boolean.parseBoolean(record[7]));
                    _data.add(manager);
                case User.TECHNICIAN:
                    Technician technician = new Technician(Integer.parseInt(record[2]));
                    technician.setID(Integer.parseInt(record[0]));
                    technician.Role = record[1];
                    technician.EmailAddress = record[3];
                    technician.FullName = record[5];
                    technician.TelNumber = record[6];
                    technician.setIsManager(Boolean.parseBoolean(record[7]));
                    technician.Specialization = record[8];
                    _data.add(technician);
                case User.CUSTOMER:
                    Customer customer = new Customer(Integer.parseInt(record[2]));
                    customer.setID(Integer.parseInt(record[0]));
                    customer.Role = record[1];
                    customer.EmailAddress = record[3];
                    customer.FullName = record[5];
                    customer.TelNumber = record[6];
                    customer.setIsManager(Boolean.parseBoolean(record[7]));
                    customer.Room = new Room(Integer.parseInt(record[8]));
                    _data.add(customer);

            }
        }
    }
}
