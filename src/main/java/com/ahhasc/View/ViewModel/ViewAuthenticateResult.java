package com.ahhasc.View.ViewModel;

import com.ahhasc.Model.AuthenticatedResult;
import com.ahhasc.Model.DataAccess;
import com.ahhasc.Model.User;

import java.time.LocalDateTime;

public class ViewAuthenticateResult{

    private LocalDateTime TimeAuthenticated;
    private boolean IsSuccessful;
    private User AuthenticatedUser;
    private String ErrorMessage = "none";

    public ViewAuthenticateResult(AuthenticatedResult authenticatedResult) {
        this.TimeAuthenticated = authenticatedResult.TimeAuthenticated;
        this.AuthenticatedUser = authenticatedResult.AuthenticatedUser;
        this.IsSuccessful = authenticatedResult.IsSuccessful;
        this.ErrorMessage = authenticatedResult.ErrorMessage;
    }

    public String getTimeAuthenticated() {
        return TimeAuthenticated.format(DataAccess.DefaultDateTimeFormat).toString();
    }

    public String getIsSuccessful() {
        return Boolean.toString(IsSuccessful);
    }

    public String getAuthenticatedUser() {
        User user = DataAccess.GetInstance().UserController.GetUserByID(AuthenticatedUser.getID());
        return user.FullName;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }
}
