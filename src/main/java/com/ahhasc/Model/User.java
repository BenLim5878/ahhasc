package com.ahhasc.Model;

public class User {
    private int ID;
    public String FullName;
    public String Role;
    public String TelNumber;
    public String EmailAddress;
    private boolean IsManager;

    public static final String MANAGER = "Manager";
    public static final String TECHNICIAN = "Technician";
    public static final String CUSTOMER = "Customer";

    public User(){

    }

    public User(int id){
        this.ID = id;
    }

    public User(String fullName, String role,String telNumber, String emailAddress, boolean isManager ){
        this.FullName = fullName ;
        this.Role = role;
        this.TelNumber = telNumber;
        this.EmailAddress = emailAddress;
        this.IsManager = isManager;
    }

    public int getID(){
        return this.ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }

    public boolean getIsManager(){
        return this.IsManager;
    }

    public void setIsManager(boolean isManager){
        this.IsManager = isManager;
    }
}
