package com.example.smartcampuslibrary;

import android.content.Context;

public class ZhcsConfig {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ZhcsConfig.context = context;
    }

    private static String url;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        ZhcsConfig.url = url;
    }

    private static String toKen = "";

    public static String getToKen() {
        return toKen;
    }

    public static void setToKen(String toKen) {
        ZhcsConfig.toKen = toKen;
    }

}
