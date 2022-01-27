package com.ahhasc.View.ViewModel;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.Customer;

import java.time.LocalDateTime;

public class ViewCustomer {

    private Integer CustomerID;
    private String FullName ;
    private String EmailAddress ;
    private String TelNumber;
    private Integer RoomNo ;

    public ViewCustomer(Customer customer) {
        this.CustomerID = customer.getCustomerID();
        this.FullName = customer.FullName;
        this.EmailAddress = customer.EmailAddress;
        this.TelNumber = customer.TelNumber;
        this.RoomNo = customer.Room.Unit;
    }

    public Integer getCustomerID() {
        return this.CustomerID;
    }

    public String getFullName() {
        return this.FullName;
    }

    public String getEmailAddress() {
        return this.EmailAddress;
    }

    public String getTelNumber() {
        return this.TelNumber;
    }

    public Integer getRoomNo() {
        return this.RoomNo;
    }

}
