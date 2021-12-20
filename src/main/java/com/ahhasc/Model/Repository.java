package com.ahhasc.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Repository {
    private String Filepath;
    private String Filename;
    private ArrayList<String> Records = new ArrayList<String>();

    public Repository(String filepath, String filename){
        this.Filename = filename;
        this.Filepath = getClass().getResource(String.format("/%1$s/%2$s",this.Filepath,this.Filename)).getPath();
    }

    public Repository(String filename){
        this.Filename = filename;
        this.Filepath = getClass().getResource(String.format("/store/%1$s",this.Filename)).getPath();
    }

    public void Load(){
        try{
            File dataFile = new File(this.Filepath);
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
        CheckFileExist(new File(this.Filepath));
        this.ClearContent();
        int i = 0;
        try {
            FileWriter targetFile = new FileWriter(this.Filepath,true);
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
        try {
            FileWriter targetFile = new FileWriter(this.Filepath,false);
            targetFile.write("");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
