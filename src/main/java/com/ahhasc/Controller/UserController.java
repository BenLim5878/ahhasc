package com.ahhasc.Controller;

import com.ahhasc.Model.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserController implements  IController{

    private Repository _repository;
    private ArrayList<User> _data = new ArrayList<User>();
    private Technician technicianDescriptor;

    public UserController(String storeFileName){
        this._repository = new Repository(storeFileName);
        this._repository.Load();
        this.Serialize();
    }

    @Override
    public void Serialize() {
        for (String userRecord : _repository.GetRecords()){
            String[] record =  userRecord.split(";");
            switch (record[1]){
                case User.MANAGER:
                    Manager manager = ToManager(record);
                    _data.add(manager);
                    break;
                case User.TECHNICIAN:
                    Technician technician = ToTechnician(record);
                    _data.add(technician);
                    break;
            }
        }
    }

    public AuthenticatedResult Authenticate(String email, String password){
        AuthenticatedResult authResult = new AuthenticatedResult();
        for (User user : _data){
            if (user.EmailAddress.toLowerCase().trim().equals(email.toLowerCase().trim()) && user.Password.equals(password)){
                authResult.AuthenticatedUser = user;
                authResult.IsSuccessful = true;
                authResult.TimeAuthenticated = LocalDateTime.now();
                authResult.ErrorMessage = "none";
                break;
            } else {
                authResult.AuthenticatedUser = null;
                authResult.IsSuccessful = false;
                authResult.TimeAuthenticated = LocalDateTime.now();
                authResult.ErrorMessage = "Invalid Credentials";
            }
        }
        DataAccess.GetInstance().AuthLogger.AddRecord(authResult);
        return authResult;
    }

    public User GetUserByID(int userID){
        for (User user: _data){
            if (user.getID() == userID){
                return user;
            }
        }
        return null;
    }

    public ArrayList<Technician> GetTechnicians(){
        ArrayList<Technician> out = new ArrayList<Technician>();
        for (User user:_data){
            if (user.Role.equals(User.TECHNICIAN)){
                out.add((Technician) user);
            }
        }
        return out;
    }

    public ArrayList<Technician> GetTechnician(Technician technicianDescriptor) throws IllegalAccessException {
        ArrayList<Technician> out = new ArrayList<Technician>();
        ArrayList<Technician> technicians = GetTechnicians();
        Field fieldList[] = Technician.class.getDeclaredFields();
        for (Field field:fieldList){
            Object targetVal = field.get(technicianDescriptor);
            if (targetVal != null){
                for (Technician technician: technicians){
                    Object val = field.get(technician);
                    if (val.equals(targetVal)){
                        if (!out.contains(technician)){
                            out.add(technician);
                        }
                    }
                }
            }
        }
        return out;
    }

    public void RegisterTechnician(Technician technicianDescriptor){
        int userID = _data.size();
        int technicianID = GetTechnicians().size();

        technicianDescriptor.setID(userID);
        technicianDescriptor.setTechnicianID(technicianID);
        technicianDescriptor.Role = User.TECHNICIAN;

        _data.add(technicianDescriptor);
        _repository.AddNewRecord(technicianDescriptor.toString());
    }

    public void UpdateTechnician(Technician technicianDescriptor){
        _data.set(technicianDescriptor.getID(),technicianDescriptor);
        _repository.UpdateRecord(technicianDescriptor.getID(),technicianDescriptor.toString());
    }

    public void DeleteTechnician(int technicianID){
        for (User user: _data){
            if (user.Role == User.TECHNICIAN){
                Technician technician = (Technician) user;
                if (technician.getTechnicianID() == technicianID){
                    _data.remove(user);
                    _repository.DeleteRecord(_data.indexOf(user));
                }
            }
        }
    }


    private Manager ToManager(String[] record){
        Manager manager = new Manager(Integer.parseInt(record[2]));
        manager.setID(Integer.parseInt(record[0]));
        manager.Role = record[1];
        manager.EmailAddress = record[3];
        manager.Password = record[4];
        manager.FullName = record[5];
        manager.TelNumber = record[6];
        return manager;
    }

    private Technician ToTechnician(String[] record){
        Technician technician = new Technician(Integer.parseInt(record[2]));
        technician.setID(Integer.parseInt(record[0]));
        technician.Role = record[1];
        technician.EmailAddress = record[3];
        technician.Password = record[4];
        technician.FullName = record[5];
        technician.TelNumber = record[6];
        technician.Specialization = record[7];
        return technician;
    }

}
