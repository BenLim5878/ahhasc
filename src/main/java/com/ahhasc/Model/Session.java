package com.ahhasc.Model;

import com.ahhasc.Config;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class Session {

    private Repository _repository;
    private static Session Instance = null;
    public boolean IsLoggedIn;
    public User LoggedUser;
    private LocalDateTime TimeCreated;
    private LocalDateTime ExpiryTime;
    private String HashValue;

    private Session(){
        _repository = new Repository("Session.txt");
        _repository.Load();
        if (_repository.GetRecords().size() == 0){
            this.DeleteSession();
        } else {
            String[] sessionDetails = _repository.GetRecords().get(0).split(";");
            this.ValidateSession(sessionDetails);
        }
    }

    public static Session GetInstance(){
        if (Instance == null){
            Instance = new Session();
        }
        return Instance;
    }

    public void DeleteSession(){
        this.IsLoggedIn = false;
        this.LoggedUser = null;
        this.HashValue = "";
        this.TimeCreated = null;
        this.ExpiryTime = null;

        if (_repository.GetRecords().size() > 0){
            _repository.DeleteRecord(0);
        }
    }

    private void ValidateSession(String[] sessionDescriptor){
        this.TimeCreated = LocalDateTime.parse(sessionDescriptor[0],DataAccess.DefaultDateTimeFormat);
        this.ExpiryTime = LocalDateTime.parse(sessionDescriptor[1],DataAccess.DefaultDateTimeFormat);
        this.LoggedUser= new User(Integer.parseInt(sessionDescriptor[2]));
        this.HashValue = sessionDescriptor[3];

        String hashOutput = this.Hash(this.ToHashInput());

        if (this.HashValue.equals(hashOutput) && this.ExpiryTime.isAfter(LocalDateTime.now())){
            this.IsLoggedIn = true;
            this.LoggedUser = DataAccess.GetInstance().UserController.GetUserByID(this.LoggedUser.getID());
        } else {
            this.DeleteSession();
        }
    }

    public void CreateSession(AuthenticatedResult authResult){
        this.DeleteSession();

        this.TimeCreated = LocalDateTime.now();
        this.ExpiryTime = this.TimeCreated.plusHours(4);
        this.LoggedUser = authResult.AuthenticatedUser;
        this.HashValue = this.Hash(this.ToHashInput());
        this.IsLoggedIn = true;

        this._repository.AddNewRecord(this.toString());
    }

    private String Hash(String hashInput){
        String hashOutput = "";
        try{
            MessageDigest hashFunction = MessageDigest.getInstance("SHA-256");
            hashFunction.update(hashInput.getBytes());
            hashOutput = DataAccess.ByteToHex(hashFunction.digest());
        } catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return hashOutput;
    }

    @Override
    public String toString() {
        return String.format("%1$s;%2$s;%3$s;%4$s",this.TimeCreated.format(DataAccess.DefaultDateTimeFormat),this.ExpiryTime.format(DataAccess.DefaultDateTimeFormat),this.LoggedUser.getID(),this.HashValue);
    }

    private String ToHashInput(){
        return String.format("%1$s;%2$s;%3$s;%4$s",this.TimeCreated.format(DataAccess.DefaultDateTimeFormat),this.ExpiryTime.format(DataAccess.DefaultDateTimeFormat),Integer.toString(this.LoggedUser.getID()), Config.SessionScrt);
    }
}
