package com.ahhasc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static final String SessionScrt = "KbUusS35rVMTa7NQWsNWPPXVkP8Gbccl";
    public static final String PasswordScrt = "r7AdA3qhV0LKJO25TB79ZdolzgSiWbFQ";
    public static final String Appname = "PixaService";

    public static final boolean IsDebug = true;
    public static final ObservableList<String> RoleList = FXCollections.observableList(new ArrayList<String>(List.of("Lighting technician","Lineworker","Mechanic","Electronic")));
    public static final ObservableList<Integer> HourChoices =  FXCollections.observableList(new ArrayList<Integer>(List.of(1,2,3,4,5,6,7,8,9,10,11,12)));
    public static final ObservableList<String> MinuteChoices =  FXCollections.observableList(new ArrayList<String>(List.of("00","15","30","45")));
    public static final ObservableList<String> TimeTypeChoices =  FXCollections.observableList(new ArrayList<String>(List.of("AM","PM")));
    public static final ObservableList<String> SearchAppointmentTypeChoices = FXCollections.observableList(new ArrayList<String>(List.of("Customer","Is Before", "Is After")));
}
