package com.example.smartcampus.adapter.wodeeAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smartcampus.bean.statistics.WorkNature;

import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/7 15:48 星期六
 */
public class Apply_Employment_adapter extends ArrayAdapter<WorkNature> {

    public Apply_Employment_adapter(@NonNull Context context, List<WorkNature> resource) {
        super(context, 0,resource);					 						//传递上下文和数组，数组的话前面写0
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = View.inflate(getContext(),android.R.layout.simple_list_item_1,null);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getWorkNatureName());
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.LEFT|Gravity.CENTER);
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = View.inflate(getContext(),android.R.layout.simple_list_item_1,null);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position).getWorkNatureName());
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.LEFT|Gravity.CENTER);
        return view;
    }

}
