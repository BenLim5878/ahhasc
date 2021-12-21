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

    public static String ByteToHex(byte[] bytes){
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }



}
