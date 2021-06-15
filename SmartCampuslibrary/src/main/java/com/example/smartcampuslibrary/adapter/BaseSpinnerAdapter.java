package com.example.smartcampuslibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public abstract class BaseSpinnerAdapter extends ArrayAdapter<String> {

    public BaseSpinnerAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_item , parent , false);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setTextSize(20);
        textView.setTextColor(Color.parseColor("#333333"));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(setBackground());
        textView.setText(getItem(position));
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_item , parent , false);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setTextSize(20);
        textView.setTextColor(Color.parseColor("#333333"));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(setBackground());
        textView.setText(getItem(position));
        return convertView;
    }

    protected abstract int setBackground();
}
