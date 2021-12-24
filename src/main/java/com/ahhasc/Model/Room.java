package com.ahhasc.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Room {

    private int RoomID;
    public int Unit;
    public int Floor;
    public char Block;


    public Room(int ID){

    }

    public Room(){}

    public int GetRoomID(){
        return this.RoomID;
    }

    public void SetRoomID(int roomID){
        this.RoomID = roomID;
    }

    @Override
    public boolean equals(Object object){
        ArrayList<Field> fieldToMatch = new ArrayList<Field>();
        boolean out = false;
        for (Field customerField: this.getClass().getDeclaredFields()){
            try {
                if (customerField.get(object) != null){
                    fieldToMatch.add(customerField);
                }
            } catch(IllegalAccessException ex){
                ex.printStackTrace();
            }
        }
        for (Field field: fieldToMatch){
            try {
                var target = field.get(object);
                var value = field.get(this);
                if (value.equals(target)){
                    out = true;
                    continue;
                }
                out = false;
            } catch(IllegalAccessException ex){
                ex.printStackTrace();
            }
        }
        return out;
    }
}
