package com.example.morgan.DataBase;

import com.example.morgan.Activities.Record;

import java.util.ArrayList;
import java.util.Collections;

public class MyDB {
    private ArrayList<Record> records;

    public MyDB() {
    records = new ArrayList<>();
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public MyDB setRecords(ArrayList<Record> records){
        this.records = records;
        return this;
    }
    public void sortRecords(){
        Collections.sort(records);
    }
}
