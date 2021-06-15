package com.example.smartcampuslibrary.utils.myView;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class BaseRecyclerView extends RecyclerView {
    public BaseRecyclerView(@NonNull Context context) {
        super(context);
    }

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2 , MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
