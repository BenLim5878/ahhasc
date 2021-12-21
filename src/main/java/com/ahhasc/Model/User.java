package com.ahhasc.Model;

public class User {
    private int ID;
    public String FullName;
    public String Role;
    public String TelNumber;
    public String EmailAddress;
    public String Password;

    public static final String MANAGER = "Manager";
    public static final String TECHNICIAN = "Technician";

    public User(){

    }

    public User(int id){
        this.ID = id;
    }

    public User(String fullName, String role,String telNumber, String emailAddress ){
        this.FullName = fullName ;
        this.Role = role;
        this.TelNumber = telNumber;
        this.EmailAddress = emailAddress;
    }

    public int getID(){
        return this.ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }
}
