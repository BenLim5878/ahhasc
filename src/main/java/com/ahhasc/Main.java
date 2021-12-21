package com.ahhasc;
import com.ahhasc.Controller.*;
import com.ahhasc.Model.*;


public class Main {
    public static void main(String[] args) {
        DataAccess.GetInstance().UserController.Authenticate("limzhengwei1002@gmail.com","iamgf");
    }
}