package com.ahhasc.Controller;

import com.ahhasc.Model.*;

import java.util.ArrayList;
import java.util.Collections;

public class RoomController implements IController {

    private Repository _repository;
    private ArrayList<Room> _data = new ArrayList<Room>();

    public RoomController(String storeFileName){
        _repository = new Repository(storeFileName);
        _repository.Load();
        this.Serialize();
    }

    @Override
    public void Serialize() {
        for (String roomRecord : _repository.GetRecords()){
            String[] record =  roomRecord.split(";");
            Room room = new Room(Integer.parseInt(record[0]));
            room.Block =  record[1];
            room.Floor = Integer.parseInt(record[2]);
            room.Unit = Integer.parseInt(record[3]);
            _data.add(room);
        }
    }

    public ArrayList<Room> GetRooms(){
        return _data;
    }

    public ArrayList<Room> GetRoom(Room roomDescriptor){
        ArrayList<Room> out = new ArrayList<Room>();
        for (Room room: this.GetRooms()){
            if (room.Contains(roomDescriptor)){
                out.add(room);
            }
        }
        return out;
    }

    public void AddRoom(Room roomDescriptor){
        int roomID = GetNewRoomID();

        roomDescriptor.setRoomID(roomID);

        _data.add(roomDescriptor);
        _repository.AddNewRecord(roomDescriptor.toString());
    }

    public void UpdateRoom(Room roomDescriptor){
        int dataIndex = this.GetRoomIndex(roomDescriptor.getRoomID());
        _repository.UpdateRecord(dataIndex,roomDescriptor.toString());
        _data.set(dataIndex,roomDescriptor);
    }

    public void DeleteRoom(int roomID){
        int dataIndex = this.GetRoomIndex(roomID);
        _repository.DeleteRecord(dataIndex);
        _data.remove(dataIndex);
    }

    private int GetRoomIndex(int roomID){
        for (Room room: _data){
            if (room.getRoomID() == roomID){
                return _data.indexOf(room);
            }
        }
        return -1;
    }

    private int GetNewRoomID(){
        if (_data.size() == 0){
            return 0;
        }
        ArrayList<Integer> ids = new ArrayList<Integer>();
        _data.forEach(room -> ids.add(room.getRoomID()));
        return (Collections.max(ids) + 1);
    }
}
