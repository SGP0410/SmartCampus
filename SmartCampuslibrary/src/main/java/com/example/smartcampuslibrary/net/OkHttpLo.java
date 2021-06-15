package com.example.smartcampuslibrary.net;

import java.io.IOException;
import org.json.JSONObject;

public interface OkHttpLo {
    void onResponse(JSONObject jsonObject);
    void onFailure(IOException e);
}
