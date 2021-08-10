package com.example.smartcampus;

import android.content.Context;

import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.College;
import com.example.smartcampus.bean.statistics.Major;
import com.example.smartcampuslibrary.ZhcsConfig;

import java.util.List;

public class Application extends android.app.Application {

    private static Context context;
    public static String name;
    public static String card;
    public static String type;
    public static List<Major> majors;
    public static List<College> colleges;

    public static List<College> getColleges() {
        return colleges;
    }

    public static void setColleges(List<College> colleges) {
        Application.colleges = colleges;
    }

    public static List<Major> getMajors() {
        return majors;
    }

    public static void setMajors(List<Major> majors) {
        Application.majors = majors;
    }


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
        ZhcsConfig.setUrl("http://10.0.0.8:8080/Smart_campus_war/");
        ZhcsConfig.setContext(getApplicationContext());
    }

    public static Context getContext() {
        return context;
    }
}
