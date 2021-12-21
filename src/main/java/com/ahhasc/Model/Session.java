package com.ahhasc.Model;

import com.ahhasc.Config;
import com.ahhasc.Controller.IController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Session {

    private Repository _repository;
    private static Session Instance = null;
    public boolean IsLoggedIn;
    private User LoggedUser;
    private LocalDateTime TimeCreated;
    private LocalDateTime ExpiryTime;
    private String HashValue;

    private Session(){
        _repository = new Repository("Session.txt");
        _repository.Load();
        if (_repository.GetRecords().size() == 0){
            this.IsLoggedIn = false;
            this.LoggedUser = null;
        } else {
            String[] sessionRecord = _repository.GetRecords().get(0).split(";");
            this.ValidateSession(sessionRecord);
        }
    }

    public static Session GetInstance(){
        if (Instance == null){
            Instance = new Session();
        }
        return Instance;
    }

    public void SetLoggedUser(User loggedUser){
        this.LoggedUser = loggedUser;
    }

    public User GetLoggedUser(){
        return this.LoggedUser;
    }

    public void Clear(){
        this.IsLoggedIn = false;
        this.LoggedUser = null;
    }

    private void ValidateSession(String[] sessionDescriptor){
        this.TimeCreated = LocalDateTime.parse(sessionDescriptor[0],DataAccess.DefaultDateTimeFormat);
        this.ExpiryTime = LocalDateTime.parse(sessionDescriptor[1],DataAccess.DefaultDateTimeFormat);
        this.LoggedUser= new User(Integer.parseInt(sessionDescriptor[2]));
        this.HashValue = sessionDescriptor[3];

        String hashInput = String.format("%1$s;%2$s;%3$s;%4$s",this.TimeCreated,this.ExpiryTime,this.LoggedUser, Config.SessionScrt);
        String hashOutput = this.Hash(hashInput);

        if (HashValue.trim().equals(hashOutput.trim())){
            this.IsLoggedIn = true;
//            this.LoggedUser = DataAccess.GetInstance();
        } else {
            this.Clear();
        }
    }

    private String Hash(String hashInput){
        String hashOutput = "";
        try{
            MessageDigest hashFunction = MessageDigest.getInstance("SHA-256");
            hashFunction.update(hashInput.getBytes());
            hashOutput = new String(hashFunction.digest());
        } catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return hashOutput;
    }




}
