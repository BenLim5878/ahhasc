package com.ahhasc.Model;

import java.util.ArrayList;

public class Technician extends User {
    private int TechnicianID;
    public ArrayList<Appointment> ActiveDuty = new ArrayList<Appointment>();
    public ArrayList<Appointment> AddedDuty = new ArrayList<Appointment>();
    public String Specialization;

    public Technician(int technicianID){
        super();
        this.TechnicianID = technicianID;
    }

    public int getTechnicianID(){
        return this.TechnicianID;
    }

    public void setTechnicianID(int technicianID){
        this.TechnicianID = technicianID;
    }
}
