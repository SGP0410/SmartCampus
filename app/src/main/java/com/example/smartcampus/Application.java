package com.example.smartcampus;

import android.content.Context;
import com.example.smartcampuslibrary.ZhcsConfig;

public class Application extends android.app.Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ZhcsConfig.setUrl("http://192.168.155.202:8080/Smart_campus/");
        ZhcsConfig.setContext(getApplicationContext());
    }

    public static Context getContext() {
        return context;
    }
}
