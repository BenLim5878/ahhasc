package com.ahhasc.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Room {

    private Integer RoomID;
    public Integer Unit;
    public Integer Floor;
    public String Block;


    public Room(int ID){
        this.RoomID = ID;
    }

    public Room(){}

    public int getRoomID(){
        return this.RoomID;
    }

    public void setRoomID(int roomID){
        this.RoomID = roomID;
    }

    public boolean Contains(Object object){
        ArrayList<Field> fieldToMatch = new ArrayList<Field>();
        boolean out = false;
        for (Field roomField: this.getClass().getDeclaredFields()){
            try {
                if (roomField.get(object) != null){
                    fieldToMatch.add(roomField);
                }
            } catch(IllegalAccessException ex){
                ex.printStackTrace();
            }
        }
        for (Field field: fieldToMatch){
            try {
                var target = field.get(object);
                var value = field.get(this);
                if (value != null){
                    if (value.equals(target)){
                        out = true;
                        continue;
                    }
                    out = false;
                }
                out = false;
            } catch(IllegalAccessException ex){
                ex.printStackTrace();
            }
        }
        return out;
    }

    @Override
    public String toString(){
        return String.format(
                "%1$s;%2$s;%3$s;%4$s",
                Integer.toString(this.getRoomID()),
                this.Block,
                Integer.toString(this.Floor),
                Integer.toString(this.Unit)
        );
    }
}
