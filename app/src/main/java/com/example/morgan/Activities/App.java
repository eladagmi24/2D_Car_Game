package com.example.morgan.Activities;

import android.app.Application;

import com.example.morgan.DataBase.MSPv3;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MSPv3.getInstance(this);
    }
}
