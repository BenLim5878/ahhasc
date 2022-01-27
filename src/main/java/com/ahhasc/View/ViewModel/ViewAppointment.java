package com.ahhasc.View.ViewModel;

import com.ahhasc.Model.Appointment;
import com.ahhasc.Model.AuthenticatedResult;
import com.ahhasc.Model.DataAccess;

import java.time.LocalDateTime;

public class ViewAppointment {
    private Integer AppointmentID;
    private LocalDateTime StartTime ;
    private String Customer ;
    private Boolean IsCompleted;
    private Boolean IsPaid;

    public ViewAppointment(Appointment appointment) {
        this.AppointmentID = appointment.getAppointmentID();
        this.StartTime = appointment.StartTime;
        this.Customer = appointment.BookingCustomer.FullName;
        this.IsCompleted = appointment.getIsCompleted();
        this.IsPaid = appointment.Payment.IsResolved;
    }

    public Integer getAppointmentID() {
        return this.AppointmentID;
    }

    public LocalDateTime getStartTime(){
        return this.StartTime;
    }

    public String getCustomer(){
        return this.Customer;
    }

    public Boolean getIsCompleted(){
        return this.IsCompleted;
    }

    public Boolean getIsPaid(){
        return this.IsPaid;
    }
}
