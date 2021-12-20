package com.ahhasc.Model;

import java.util.Date;

public class Payment {
    private int ID;
    public boolean IsResolved = false;
    public Date DueDate;
    public Float Amount;

    public Payment(int ID){
        this.ID = ID;
    }

    public Payment(boolean isResolved,Date dueDate, float amount){
        this.IsResolved = isResolved;
        this.DueDate = dueDate;
        this.Amount = amount;
    }

    public int getID(){return this.ID;}
    public void setID(int ID){
        this.ID = ID;
    }
}
