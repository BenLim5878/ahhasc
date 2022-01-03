package com.ahhasc.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Customer{
    private Integer CustomerID;
    public Room Room;
    public String FullName;
    public String EmailAddress;
    public String TelNumber;

    public Customer(int customerID){
        super();
        this.CustomerID= customerID;
    }

    public Customer(){
        super();
    }

    public int getCustomerID(){
        return this.CustomerID;
    }

    public void setCustomerID(int customerID){
        this.CustomerID = customerID;
    }


    public boolean Contains(Object object) {
        ArrayList<Field> fieldToMatch = new ArrayList<Field>();
        boolean out = false;
        for (Field customerField: this.getClass().getDeclaredFields()){
            try {
                if (customerField.get(object) != null){
                    fieldToMatch.add(customerField);
                }
            } catch(IllegalAccessException ex){
                ex.printStackTrace();
            }
        }
        for (Field field: fieldToMatch){
            try {
                var target = field.get(object);
                var value = field.get(this);
                if (field.getName() == "Room"){
                  Room targetRoom = (Room) target;
                  Room valueRoom = (Room) value;
                    if (valueRoom != null){
                        if (valueRoom.Contains(targetRoom)){
                            out = true;
                            continue;
                        }
                        out = false;
                    }
                } else {
                    if (value != null){
                        if (value.toString().equalsIgnoreCase(target.toString())){
                            out = true;
                            continue;
                        }
                        out = false;
                    }
                }
                out = false;
            } catch(IllegalAccessException ex){
                ex.printStackTrace();
            }
        }
        return out;
    }

    @Override
    public String toString(){
        return String.format(
                "%1$s;%2$s;%3$s;%4$s;%5$s",
                Integer.toString(this.getCustomerID()),
                this.EmailAddress,
                this.FullName,
                this.TelNumber,
                Integer.toString(this.Room.getRoomID())
        );
    }
}
