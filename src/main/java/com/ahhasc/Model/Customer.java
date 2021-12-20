package com.ahhasc.Model;

import java.util.ArrayList;

public class Customer extends User{
    private int CustomerID;
    public Room Room;

    public Customer(int customerID){
        super();
        this.CustomerID= customerID;
    }

    public int getCustomerID(){
        return this.CustomerID;
    }

    public void setCustomerID(int customerID){
        this.CustomerID = customerID;
    }
}
