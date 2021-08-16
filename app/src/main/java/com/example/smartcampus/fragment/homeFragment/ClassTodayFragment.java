package com.example.smartcampus.fragment.homeFragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcampus.R;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.haibin.calendarview.CalendarView;

public class ClassTodayFragment extends BaseFragment {

    private ImageView back;
    private TextView title;
    private CalendarView calendarView;


    @Override
    protected int layoutResId() {
        return R.layout.class_today_frament;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        title.setText("今日课堂");
        calendarView = view.findViewById(R.id.calendar_view);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
