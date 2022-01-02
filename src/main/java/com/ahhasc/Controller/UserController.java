package com.ahhasc.Controller;

import com.ahhasc.Model.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class UserController implements  IController{

    private Repository _repository;
    private ArrayList<User> _data = new ArrayList<User>();

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

    public AuthenticatedResult Authenticate(String email, String password, boolean isUserSaved){
        AuthenticatedResult authResult = new AuthenticatedResult();
        for (User user : _data){
            if (user.EmailAddress.trim().equalsIgnoreCase(email.toLowerCase().trim()) && user.Password.equals(User.EncryptPassword(password))){
                authResult.AuthenticatedUser = user;
                authResult.IsSuccessful = true;
                authResult.TimeAuthenticated = LocalDateTime.now();
                authResult.ErrorMessage = "none";
                Session.GetInstance().CreateSession(authResult,isUserSaved);
                break;
            } else {
                authResult.AuthenticatedUser = null;
                authResult.IsSuccessful = false;
                authResult.TimeAuthenticated = LocalDateTime.now();
                authResult.ErrorMessage = String.format("Invalid Credentials (Trying to access: %1$s)",email);
                Session.GetInstance().DeleteSession();
            }
        }
        DataAccess.GetInstance().AuthLogger.AddRecord(authResult);
        return authResult;
    }

    public void Logout(){
        Session.GetInstance().DeleteSession();
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

    public ArrayList<Manager> GetManagers(){
        ArrayList<Manager> out = new ArrayList<Manager>();
        for (User user: this._data){
            if (user.Role.equals(User.MANAGER)){
                out.add((Manager)user);
            }
        }
        return out;
    }

    public Technician GetTechnician(int technicianID) {
        for (Technician technician:this.GetTechnicians()){
            if (technician.getTechnicianID() == technicianID){
                return technician;
            }
        }
        return null;
    }

    public Manager GetManager(int managerID){
        for (Manager manager: this.GetManagers()){
            if (manager.getManagerID() == managerID){
                return manager;
            }
        }
        return null;
    }

    public RegistrationResult RegisterTechnician(Technician technicianDescriptor){
        RegistrationResult out = new RegistrationResult();
        // Check if the account exists
        for (Technician technician : this.GetTechnicians()){
            if (technician.EmailAddress.equalsIgnoreCase(technicianDescriptor.EmailAddress)){
                out.IsSuccessful = false;
                out.Message = "Account already exist";
                return out;
            }
        }
        int userID = GetNewUserID();
        int technicianID = GetNewTechnicianID();
        String password = technicianDescriptor.Password;
        // Check if the password meet minimum requirement
        if (password.length() < 8){
            out.IsSuccessful = false;
            out.Message = "Password must be more than 8 characters";
            return out;
        }
        technicianDescriptor.setID(userID);
        technicianDescriptor.setTechnicianID(technicianID);
        technicianDescriptor.Role = User.TECHNICIAN;
        technicianDescriptor.Password = User.EncryptPassword(password);
        technicianDescriptor.EmailAddress = technicianDescriptor.EmailAddress.toLowerCase();

        _data.add(technicianDescriptor);
        _repository.AddNewRecord(technicianDescriptor.toString());

        out.Message = "Account registered successfully";
        out.IsSuccessful = true;
        return out;
    }

    public void UpdateTechnician(Technician technicianDescriptor){
        int dataIndex = this.GetTechnicianIndex(technicianDescriptor.getTechnicianID());
        _repository.UpdateRecord(dataIndex,technicianDescriptor.toString());
        _data.set(dataIndex,technicianDescriptor);
    }

    public void DeleteTechnician(int technicianID){
        for (User user: _data){
            if (user.Role.equals(User.TECHNICIAN)){
                Technician technician = (Technician) user;
                if (technician.getTechnicianID() == technicianID){
                    _repository.DeleteRecord(_data.indexOf(user));
                    _data.remove(user);
                    break;
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
        technician.Description = record[8];
        return technician;
    }

    private int GetNewUserID(){
        if (_data.size() == 0){
            return 0;
        }
        ArrayList<Integer> ids = new ArrayList<Integer>();
        _data.forEach(user -> ids.add(user.getID()));
        return (Collections.max(ids) + 1);
    }

    private int GetNewTechnicianID(){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        if (this.GetTechnicians().size() == 0){
            return 0;
        }
        this.GetTechnicians().forEach(technician -> ids.add(technician.getTechnicianID()));
        return (Collections.max(ids) + 1);
    }

    private int GetTechnicianIndex(int technicianID){
        for (User user: _data){
            if (user.Role.equals(User.TECHNICIAN)){
                Technician technician = (Technician) user;
                if (technician.getTechnicianID() == technicianID){
                    return _data.indexOf(user);
                }
            }
        }
        return -1;
    }
}
