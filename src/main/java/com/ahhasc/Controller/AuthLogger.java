package com.ahhasc.Controller;

import com.ahhasc.Model.AuthenticatedResult;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.Repository;
import com.ahhasc.Model.User;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AuthLogger implements IController{

    private Repository _repository;
    private ArrayList<AuthenticatedResult> _data = new ArrayList<AuthenticatedResult>();

    public AuthLogger(String storeFileName){
        _repository = new Repository(storeFileName);
        _repository.Load();
        this.Serialize();
    }

    @Override
    public void Serialize() {
        for (String authRecord : _repository.GetRecords()){
            String[] record =  authRecord.split(";");
            AuthenticatedResult authDesc = new AuthenticatedResult();
            authDesc.TimeAuthenticated = LocalDateTime.parse(record[AuthenticatedResult.TIMEAUTHENTICATED], DataAccess.DefaultDateTimeFormat);
            authDesc.AuthenticatedUser = new User(Integer.parseInt(record[AuthenticatedResult.USERID]));
            authDesc.IsSuccessful = Boolean.parseBoolean(record[AuthenticatedResult.ISSUCCESSFUL]);
            authDesc.ErrorMessage = record[AuthenticatedResult.ERRORMESSAGE];
            _data.add(authDesc);
        }
    }

    public ArrayList<AuthenticatedResult> GetAllRecord(){
        return this._data;
    }

    public void AddRecord(AuthenticatedResult authenticatedResult){
        this._data.add(authenticatedResult);
        this._repository.AddNewRecord(authenticatedResult.toString());
    }
}
