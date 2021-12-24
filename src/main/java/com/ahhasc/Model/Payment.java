package com.ahhasc.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Payment {
    private int ID;
    public boolean IsResolved = false;
    public LocalDate DueDate;
    public Float Amount;

    public Payment(int ID){
        this.ID = ID;
    }

    public Payment(boolean isResolved, LocalDate dueDate, float amount){
        this.IsResolved = isResolved;
        this.DueDate = dueDate;
        this.Amount = amount;
    }

    public int getID(){return this.ID;}
    public void setID(int ID){
        this.ID = ID;
    }

    @Override
    public String toString(){
        return String.format(
                "%1$s;%2$s;%3$s;%4$s",
                Integer.toString(this.getID()),
                Float.toString(this.Amount),
                this.DueDate.format(DataAccess.DefaultDateTimeFormat).toString(),
                Boolean.toString(this.IsResolved)
        );
    }
}
