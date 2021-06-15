package com.example.smartcampuslibrary.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;
import com.example.smartcampuslibrary.ZhcsConfig;
import java.util.Random;
import org.json.JSONObject;

public class Utils {
    private static Toast toast = Toast.makeText(ZhcsConfig.getContext() , "" , Toast.LENGTH_SHORT);
    private static Random random;

    @SuppressLint("ShowToast")
    public static void showToast(String text) {
        toast.setText(text);
        toast.show();
    }

    public static void isCode(JSONObject jsonObject, String text) {
        if (jsonObject.optString("code").equals("200"))
            showToast(text + "成功");
        else
            showToast(text + "失败");
    }

    public static Random getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }
}
