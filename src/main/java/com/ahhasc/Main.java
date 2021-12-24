package com.ahhasc;
import com.ahhasc.Controller.*;
import com.ahhasc.Model.*;
import javafx.scene.control.CustomMenuItem;

import javax.xml.crypto.Data;


public class Main {
    public static void main(String[] args) {
        Customer value = new Customer(3);
        Customer target = new Customer(3);
        System.out.println(DataAccess.CompareObject(target,value));
    }
}