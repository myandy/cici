package com.myth.cici;

import android.app.Application;

import com.myth.cici.db.DBManager;

public class MyApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
    }

}
