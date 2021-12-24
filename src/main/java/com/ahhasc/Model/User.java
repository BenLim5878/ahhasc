package com.ahhasc.Model;

import com.ahhasc.Config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private Integer ID;
    public String FullName;
    public String Role;
    public String TelNumber;
    public String EmailAddress;
    public String Password;

    public static final String MANAGER = "Manager";
    public static final String TECHNICIAN = "Technician";

    public User(){

    }

    public User(int id){
        this.ID = id;
    }

    public User(String fullName, String role,String telNumber, String emailAddress ){
        this.FullName = fullName ;
        this.Role = role;
        this.TelNumber = telNumber;
        this.EmailAddress = emailAddress;
    }

    public int getID(){
        return this.ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }

    public static String EncryptPassword(String password){
        String input = String.format("%1$s;%2$s",password, Config.PasswordScrt);
        try{
            MessageDigest hashFunction = MessageDigest.getInstance("SHA-256");
            hashFunction.update(input.getBytes());
            return DataAccess.ByteToHex(hashFunction.digest());
        } catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
