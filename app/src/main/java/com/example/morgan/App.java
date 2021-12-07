package com.example.morgan;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MSPv3.getInstance(this);
    }
}
