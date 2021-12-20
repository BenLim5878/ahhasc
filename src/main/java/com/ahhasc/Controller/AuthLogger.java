package com.ahhasc.Controller;

import com.ahhasc.Model.AuthenticatedResult;
import com.ahhasc.Model.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AuthLogger implements IController{

    private Repository _repository;
    private ArrayList<AuthenticatedResult> _data;

    public AuthLogger(String storeFilePath){

    }

    @Override
    public void Serialize() {

    }

    @Override
    public Repository GetRepository() {
        return this._repository;
    }

    @Override
    public ArrayList<AuthenticatedResult> GetData() {
        return this._data;
    }
}
