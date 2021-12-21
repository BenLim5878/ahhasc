package com.ahhasc.Model;

import java.util.ArrayList;
import java.util.Locale;

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

    @Override
    public String toString(){
        return String.format(
                "%1$s;%2$s;%3$s;%4$s;%5$s;%6$s;%7$s;%8$s",
                Integer.toString(this.getID()),
                this.Role.substring(0,1).toUpperCase()+this.Role.substring(1).toLowerCase(),
                Integer.toString(this.getTechnicianID()),
                this.EmailAddress.toLowerCase(),
                this.Password,
                this.FullName,
                this.TelNumber,
                this.Specialization
                );
    }
}
