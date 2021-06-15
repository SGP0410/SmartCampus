package com.example.smartcampuslibrary.utils.myView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.smartcampuslibrary.net.OkHttpImageLo;
import com.example.smartcampuslibrary.net.OkHttpImageTo;
import java.io.IOException;

public class BaseImageView extends androidx.appcompat.widget.AppCompatImageView {
    public BaseImageView(@NonNull Context context) {
        super(context);
    }

    public BaseImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageUrl(String url){
        new OkHttpImageTo().setUrl(url)
                .setOkHttpImageLo(new OkHttpImageLo() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }
}
