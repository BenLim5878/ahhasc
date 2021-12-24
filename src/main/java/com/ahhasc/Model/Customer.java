package com.ahhasc.Model;

import com.ahhasc.Controller.IController;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Customer{
    private int CustomerID;
    public Room Room;
    public String FullName;
    public String EmailAddress;
    public String TelNumber;

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

//    @Override
//    public boolean equals(Object object) {
//        ArrayList<Field> fieldToMatch = new ArrayList<Field>();
//        boolean out = false;
//        for (Field customerField: this.getClass().getDeclaredFields()){
//            try {
//                if (customerField.get(object) != null){
//                    fieldToMatch.add(customerField);
//                }
//            } catch(IllegalAccessException ex){
//                ex.printStackTrace();
//            }
//        }
//        for (Field field: fieldToMatch){
//            try {
//                var target = field.get(object);
//                var value = field.get(this);
//                if (value.equals(target)){
//                    out = true;
//                    continue;
//                }
//                out = false;
//            } catch(IllegalAccessException ex){
//                ex.printStackTrace();
//            }
//        }
//        return out;
//    }

    @Override
    public String toString(){
        return String.format(
                "%1$s;%2$s;%3$s;%4$s;%5$s",
                Integer.toString(this.getCustomerID()),
                this.EmailAddress,
                this.FullName,
                this.TelNumber,
                Integer.toString(this.Room.GetRoomID())
        );
    }
}
