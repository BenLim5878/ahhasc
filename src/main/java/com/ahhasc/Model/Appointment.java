package com.ahhasc.Model;

import java.beans.FeatureDescriptor;
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
}
