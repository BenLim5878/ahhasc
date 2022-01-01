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

    @Override
    public String toString(){
        String technicians = "";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        this.ActiveTechnicians.forEach(technician -> ids.add(technician.getTechnicianID()));
        if (ids.size() > 0){
            for (int id: ids){
                if (ids.indexOf(id) == (ids.size() -1)){
                    technicians += id;
                    continue;
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
                Integer.toString(this.Payment.getID()),
                Boolean.toString(this.IsCompleted),
                Integer.toString(this.Feedback.getID()),
                this.Description
        );
    }
}
