package com.ahhasc.Model;

public class Room {

    private int RoomID;
    public int Unit;
    public int Floor;
    public Character Block;


    public Room(int ID){

    }

    public Room(){}

    public int getRoomID(){
        return this.RoomID;
    }

    public void setRoomID(int roomID){
        this.RoomID = roomID;
    }
}
