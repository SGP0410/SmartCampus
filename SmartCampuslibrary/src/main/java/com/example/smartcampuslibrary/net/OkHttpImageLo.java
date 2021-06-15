package com.example.smartcampuslibrary.net;

import android.graphics.Bitmap;
import java.io.IOException;

public interface OkHttpImageLo {
    void onResponse(Bitmap bitmap);
    void onFailure(IOException e);
}
