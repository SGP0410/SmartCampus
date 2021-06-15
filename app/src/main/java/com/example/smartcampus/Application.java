package com.example.smartcampus;

import com.example.smartcampuslibrary.ZhcsConfig;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZhcsConfig.setUrl("");
        ZhcsConfig.setContext(getApplicationContext());
    }
}
