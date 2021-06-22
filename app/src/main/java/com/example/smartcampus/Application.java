package com.example.smartcampus;

import com.example.smartcampuslibrary.ZhcsConfig;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();   //http://192.168.155.202:8080/Smart_campus/
        ZhcsConfig.setUrl("http://192.168.155.208:8080/Smart_campus_war/");
        ZhcsConfig.setContext(getApplicationContext());
    }
}
