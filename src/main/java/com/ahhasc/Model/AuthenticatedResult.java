package com.ahhasc.Model;

import java.time.LocalDate;

public class AuthenticatedResult {
    public LocalDate TimeAuthenticated;
    public boolean IsSuccessful;
    public User AuthenticatedUser;
    public String ErrorMessage;
}
