package com.ahhasc.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AuthenticatedResult {
    public LocalDateTime TimeAuthenticated;
    public boolean IsSuccessful;
    public User AuthenticatedUser;
    public String ErrorMessage = "none";

    public static final int TIMEAUTHENTICATED = 0;
    public static final int USERID = 1;
    public static final int ISSUCCESSFUL = 2;
    public static final int ERRORMESSAGE = 3;

    @Override
    public String toString(){
        return String.format("%1$s;%2$s;%3$s;%4$s",this.TimeAuthenticated.format(DataAccess.DefaultDateTimeFormat).toString(), Integer.toString(this.AuthenticatedUser.getID()),Boolean.toString(this.IsSuccessful),this.ErrorMessage);
    }
}
