package com.example.smartcampuslibrary.utils.myView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ImageViewOval extends BaseImageView{
    public ImageViewOval(@NonNull Context context) {
        super(context);
    }

    public ImageViewOval(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewOval(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        @SuppressLint("DrawAllocation") Path path = new Path();
        path.addOval(0 , 0 , getWidth() , getHeight() , Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
