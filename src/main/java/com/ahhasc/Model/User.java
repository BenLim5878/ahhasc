package com.ahhasc.Model;

public class User {
    private int ID;
    public String FullName;
    public String TelNumber;
    public String EmailAddress;
    private boolean IsManager;

    public User(int id){
        this.ID = id;
    }

    public User(String fullName, String telNumber, String emailAddress, boolean isManager ){
        this.FullName = fullName ;
        this.TelNumber = telNumber;
        this.EmailAddress = emailAddress;
        this.IsManager = isManager;
    }

    public boolean getIsManager(){
        return this.IsManager;
    }

    public void setIsManager(boolean isManager){
        this.IsManager = isManager;
    }
}
