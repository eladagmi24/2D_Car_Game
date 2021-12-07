package com.example.morgan;

import java.util.ArrayList;

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
}
