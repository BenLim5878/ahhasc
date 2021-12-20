package com.ahhasc.Model;

import java.util.ArrayList;

public class Manager extends User {

    private int ManagerID;
    public ArrayList<Appointment> ActiveAppointment = new ArrayList<Appointment>();
    public ArrayList<Appointment> AddedAppointment = new ArrayList<Appointment>();


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
}
