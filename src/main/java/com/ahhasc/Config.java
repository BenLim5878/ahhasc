package com.ahhasc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static final String SessionScrt = "KbUusS35rVMTa7NQWsNWPPXVkP8Gbccl";
    public static final String PasswordScrt = "r7AdA3qhV0LKJO25TB79ZdolzgSiWbFQ";
    public static final String Appname = "Pixaservice";

    public static final ObservableList<String> RoleList = FXCollections.observableList(new ArrayList<String>(List.of("Lighting technician","Lineworker","Mechanic","Electronic")));
}
