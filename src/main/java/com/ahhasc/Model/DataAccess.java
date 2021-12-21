package com.ahhasc.Model;

import com.ahhasc.Controller.*;
import java.time.format.DateTimeFormatter;

public class DataAccess {
    private static DataAccess Instance = null;
    public static DateTimeFormatter DefaultDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public AuthLogger AuthLogger = new AuthLogger("Auth.txt");
    public UserController UserController = new UserController("User.txt");



    private DataAccess(){
    }

    public static DataAccess GetInstance(){
        if (Instance == null){
            Instance = new DataAccess();
        }
        return Instance;
    }



}
