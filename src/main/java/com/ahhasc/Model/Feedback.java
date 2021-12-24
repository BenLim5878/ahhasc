package com.ahhasc.Model;

import java.time.LocalDateTime;

public class Feedback {
    private int ID;
    private float Rating;
    private String Description;
    public LocalDateTime CreatedAt;

    public Feedback(int ID){
        this.ID = ID;
    }

    public int getID(){
        return this.ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public float getRating(){
        return this.Rating;
    }

    public void setRating(float rating){
        this.Rating = rating;
    }

    public String getDescription(){
        return this.Description;
    }

    public void setDescription(String Description){
        this.Description = Description;
    }

    @Override
    public String toString(){
        return String.format(
                "%1$s;%2$s;%3$s;%4$s",
                Integer.toString(this.getID()),
                Float.toString(this.getRating()),
                this.getDescription(),
                this.CreatedAt.format(DataAccess.DefaultDateTimeFormat).toString()
        );
    }
}
