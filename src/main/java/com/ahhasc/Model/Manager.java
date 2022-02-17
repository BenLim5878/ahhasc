package com.ahhasc.Model;

public class Manager extends User {

    private int ManagerID;

    public Manager(int managerID){
        super();
        this.ManagerID = managerID;
    }

    public int getManagerID(){
        return this.ManagerID;
    }

    public void setManagerID(int managerID){
        this.ManagerID = managerID;
    }

    @Override
    public String toString(){
        return String.format(
                "%1$s;%2$s;%3$s;%4$s;%5$s;%6$s;%7$s",
                Integer.toString(this.getID()),
                this.Role.substring(0,1).toUpperCase()+this.Role.substring(1).toLowerCase(),
                Integer.toString(this.getManagerID()),
                this.EmailAddress.toLowerCase(),
                this.Password,
                this.FullName,
                this.TelNumber
        );
    }
}
