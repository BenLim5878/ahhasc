package com.ahhasc;
import com.ahhasc.Model.*;

public class Main {
    public static void main(String[] args) {
        Repository repository = new Repository("User.txt");
        repository.Load();
    }
}