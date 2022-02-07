package com.ahhasc.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Appointment {
    private int ID;
    public LocalDateTime StartTime;
    public Customer BookingCustomer;
    public Manager ActiveManager;
    public ArrayList<Technician> ActiveTechnicians = new ArrayList<Technician>();
    public Payment Payment;
    private boolean IsCompleted;
    private Feedback Feedback;
    public String Description;

    public int getAppointmentID(){
        return this.ID;
    }

    public void setAppointmentID(int ID){
        this.ID = ID;
    }

    public boolean getIsCompleted(){
        return this.IsCompleted;
    }

    public void setIsCompleted(boolean isCompleted){
        this.IsCompleted = isCompleted;
    }

    public Feedback getFeedback(){
        return this.Feedback;
    }

    public void setFeedback(Feedback feedback){
        this.Feedback = feedback;
    }

    public ArrayList<Integer> GetActiveTechnicianIds(){
        ArrayList<Integer> out = new ArrayList<Integer>();
        for (Technician technician: this.ActiveTechnicians){
            out.add(technician.getID());
        }
        return out;
    }

    public boolean IsExpired(){
        if (this.StartTime.isBefore(LocalDateTime.now()) && !this.IsCompleted){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String technicians = "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        this.ActiveTechnicians.forEach(technician -> ids.add(technician.getTechnicianID()));
        if (ids.size() > 0){
            for (int id: ids){
                if (ids.indexOf(id) == (ids.size() -1)){
                    technicians += id;
                    break;
                }
                technicians += (id + ",");
            }
        } else{
            technicians = "none";
        }
        return String.format(
                "%1$s;%2$s;%3$s;%4$s;%5$s;%6$s;%7$s;%8$s;%9$s",
                Integer.toString(this.ID),
                this.StartTime.format(DataAccess.DefaultDateTimeFormat).toString(),
                Integer.toString(this.BookingCustomer.getCustomerID()),
                Integer.toString(this.ActiveManager.getManagerID()),
                technicians,
                this.Payment.getID() != null ? Integer.toString(this.Payment.getID()) : "none"  ,
                Boolean.toString(this.IsCompleted),
                this.getFeedback() != null ? Integer.toString(this.getFeedback().getID()) : "none" ,
                this.Description
        );
    }
}
