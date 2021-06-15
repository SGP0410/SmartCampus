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

public class ImageViewRoundRect extends BaseImageView {

    private int rx = 20;
    private int ry = 20;

    public ImageViewRoundRect(@NonNull Context context) {
        super(context);
    }

    public ImageViewRoundRect(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewRoundRect(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        @SuppressLint("DrawAllocation") Path path = new Path();
        path.addRoundRect(0 , 0 , getWidth() , getHeight() , rx , ry , Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    public void setRx(int rx) {
        this.rx = rx;
    }

    public void setRy(int ry) {
        this.ry = ry;
    }
}
