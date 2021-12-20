package com.ahhasc.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Repository {
    private String Filepath;
    public String Filename;
    private ArrayList<String> Records;

    public Repository(String filepath, String filename){
        this.Filepath = filepath;
        this.Filename = filename;
    }

    public Repository(String filename){
        this.Filepath = Paths.get(System.getProperty("user.dir")).toString();
        this.Filename = filename;
    }

    public void Load(){
        System.out.println(getClass().getClassLoader().getResource("/store/User.txt"));
        try{
            File dataFile = new File(Paths.get(this.Filepath,"store",this.Filename).toString());
            if (dataFile.exists()){
                Scanner dataReader = new Scanner(dataFile);
                while(dataReader.hasNextLine()){
                    Records.add(dataReader.nextLine());
                    System.out.println(dataReader.nextLine());
                }
                dataReader.close();
            } else {
                dataFile.createNewFile();
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
