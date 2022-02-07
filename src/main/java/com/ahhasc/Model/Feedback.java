package com.ahhasc.Model;

import java.time.LocalDateTime;

public class Feedback {
    private Integer ID;
    private Integer Rating;
    private String Description;
    public LocalDateTime CreatedAt;

    public Feedback(int ID){
        this.ID = ID;
    }
    public Feedback(){}

    public Integer getID(){
        return this.ID;
    }

    public void setID(Integer ID){
        this.ID = ID;
    }

    public Integer getRating(){
        return this.Rating;
    }

    public void setRating(Integer rating){
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
                this.getRating(),
                this.getDescription(),
                this.CreatedAt.format(DataAccess.DefaultDateTimeFormat).toString()
        );
    }
}
