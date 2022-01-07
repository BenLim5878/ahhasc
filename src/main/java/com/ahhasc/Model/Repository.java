package com.ahhasc.Model;

import com.ahhasc.Config;
import com.ahhasc.ResourceLoader;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Repository {
    private String Filepath;
    private String Filename;
    private ArrayList<String> Records = new ArrayList<String>();

    public Repository(String filepath, String filename){
        this.Filename = filename;
        this.Filepath =  String.format("/%1$s/%2$s",filepath,this.Filename);
    }

    public Repository(String filename){
        this.Filename = filename;
        this.Filepath = String.format("/store/%1$s",this.Filename);
    }

    public void Load(){
        try{
            File dataFile;
            if (Config.IsDebug){
                dataFile = new File(ResourceLoader.LoadURL(this.Filepath).getPath());
            } else {
                dataFile = new File(this.Filename);
            }
            this.CheckFileExist(dataFile);
            Scanner dataReader = new Scanner(dataFile);
            while(dataReader.hasNextLine()){
                this.Records.add(dataReader.nextLine());
            }
            dataReader.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public ArrayList<String> GetRecords(){
        return this.Records;
    }

    public void AddNewRecord(String record){
        this.Records.add(record);
        UpdateContent(this.Records);
    }

    public void UpdateRecord(int index, String record){
        try{
            this.Records.set(index,record);
            UpdateContent(this.Records);
        } catch(IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public void DeleteRecord(int index){
        try{
            this.Records.remove(index);
            UpdateContent(this.Records);
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    private static void CheckFileExist(File targetFile){
        if (targetFile.exists()){
        } else {
            try{
                targetFile.createNewFile();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    private void UpdateContent(ArrayList<String> content){
        if (Config.IsDebug){
            CheckFileExist(new File(ResourceLoader.LoadURL(this.Filepath).getPath()));
        } else {
            CheckFileExist(new File(this.Filename));
        }
        this.ClearContent();
        int i = 0;
        try {
            FileWriter targetFile;
            if (Config.IsDebug){
                targetFile = new FileWriter(ResourceLoader.LoadURL(this.Filepath).getPath(),true);
            } else {
                targetFile = new FileWriter(this.Filename,true);
            }
            for (String record:content){
                targetFile.append(record);
                if (i < (content.size()-1)){
                    targetFile.append("\n");
                }
                i++;
            }
            targetFile.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void ClearContent(){
        if (Config.IsDebug){
            CheckFileExist(new File(ResourceLoader.LoadURL(this.Filepath).getPath()));
        } else {
            CheckFileExist(new File(this.Filename));
        }
        try {
            FileWriter targetFile;
            if (Config.IsDebug){
                targetFile = new FileWriter(ResourceLoader.LoadURL(this.Filepath).getPath(),false);
            } else {
                targetFile = new FileWriter(this.Filename);
            }
            targetFile.write("");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
