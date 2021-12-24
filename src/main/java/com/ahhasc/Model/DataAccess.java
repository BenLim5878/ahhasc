package com.ahhasc.Model;

import com.ahhasc.Controller.*;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataAccess {
    private static DataAccess Instance = null;
    public static DateTimeFormatter DefaultDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter DefaultDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    // Controllers
    public AuthLogger AuthLogger;
    public UserController UserController;
    public CustomerController CustomerController;
    public RoomController RoomController;
    public PaymentController PaymentController;
    public FeedbackController FeedbackController;
    public AppointmentController AppointmentController;

    private DataAccess(){
        AuthLogger = new AuthLogger("Auth.txt");
        UserController= new UserController("User.txt");
        CustomerController = new CustomerController("Customer.txt");
        RoomController = new RoomController("Room.txt");
        PaymentController = new PaymentController("Payment.txt");
        FeedbackController = new FeedbackController("Feedback.txt");
        AppointmentController = new AppointmentController("Appointment.txt");
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
