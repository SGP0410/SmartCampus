package com.example.smartcampus;

import android.content.Context;

import com.example.smartcampus.bean.User;
import com.example.smartcampuslibrary.ZhcsConfig;

public class Application extends android.app.Application {

    private static Context context;
    public static String name;
    public static String card;
    public static String type;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Application.user = user;
    }

    public static User user;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ZhcsConfig.setUrl("http://10.0.0.5:8080/Smart_campus/");
        ZhcsConfig.setContext(getApplicationContext());
    }

    public static Context getContext() {
        return context;
    }
}
