package com.ahhasc.Model;

import com.ahhasc.Controller.*;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataAccess {
    private static DataAccess Instance = null;
    public static DateTimeFormatter DefaultDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter DefaultDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter DefaultTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String EmailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
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

    public static String Capitalize(String str) {
        char[] charArray = str.toCharArray();
        boolean foundSpace = true;

        for(int i = 0; i < charArray.length; i++) {
            if(Character.isLetter(charArray[i])) {
                if(foundSpace) {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            }
            else {
                foundSpace = true;
            }
        }
         String out = String.valueOf(charArray);
        return out;
    }

    public static boolean IsValidEmailAddress(String email) {
        Pattern pattern = Pattern.compile(EmailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean IsDigit(String text){
        boolean out = true;
        for (char c : text.toCharArray()){
            if (!Character.isDigit(c)){
                out = false;
            }
        }
        return out;
    }

    public static boolean IsValidTelephoneNumber(String telephoneNumber){
        String target = telephoneNumber.trim();
        if (target.length() >= 8 && target.length() <= 15){
            return true;
        } else {
            return false;
        }
    }

    public static LocalTime ParseTime(int hour, int minute, String timeType){
        if (timeType.equals("AM")){
            if (hour == 12){
                return LocalTime.of(0,minute,0);
            }
            return LocalTime.of(hour, minute,0);
        } else{
            if (hour == 12){
                return LocalTime.of(12,minute,0);
            }
            return LocalTime.of(hour+12,minute,0);
        }
    }
}
