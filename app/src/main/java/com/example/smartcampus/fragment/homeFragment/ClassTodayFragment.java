package com.example.smartcampus.fragment.homeFragment;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.homeAdapter.ScheduleRecyclerViewAdapter;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.GetClassSchedule;
import com.example.smartcampus.bean.statistics.Major;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassTodayFragment extends BaseFragment implements CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener {

    private ImageView back;
    private TextView title;
    private CalendarView calendarView;
    private RelativeLayout rlTool;
    private TextView tvMonthDay;
    private TextView tvYear;
    private TextView tvLunar;
    private FrameLayout flCurrent;
    private ImageView ibCalendar;
    private TextView tvCurrentDay;
    private int mYear;
    private CalendarLayout calendarLayout;
    private List<GetClassSchedule> schedules;
    private RecyclerView recyclerView;
    private ScheduleRecyclerViewAdapter adapter;
    private List<GetClassSchedule> getClassScheduleList = new ArrayList<>();

    @Override
    protected int layoutResId() {
        return R.layout.class_today_frament;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        title.setText("今日课堂");
        calendarView = view.findViewById(R.id.calendar_view);
        calendarView.setOnCalendarSelectListener(this);
        calendarView.setOnYearChangeListener(this);
        rlTool = view.findViewById(R.id.rl_tool);
        tvMonthDay = view.findViewById(R.id.tv_month_day);
        tvYear = view.findViewById(R.id.tv_year);
        tvLunar = view.findViewById(R.id.tv_lunar);
        flCurrent = view.findViewById(R.id.fl_current);
        ibCalendar = view.findViewById(R.id.ib_calendar);
        tvCurrentDay = view.findViewById(R.id.tv_current_day);
        view.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.scrollToCurrent();
            }
        });
        tvMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!calendarLayout.isExpand()) {
                    calendarLayout.expand();
                    return;
                }
                calendarView.showYearSelectLayout(mYear);
                tvLunar.setVisibility(View.GONE);
                tvYear.setVisibility(View.GONE);
                tvMonthDay.setText(String.valueOf(mYear));
            }
        });
        calendarLayout = view.findViewById(R.id.calendarLayout);
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        tvYear.setText(calendarView.getCurYear() + "");
        tvMonthDay.setText(calendarView.getCurMonth() + "月" + calendarView.getCurDay() + "日");
        tvLunar.setText("今日");
        tvCurrentDay.setText(String.valueOf(calendarView.getCurDay()));
        mYear = calendarView.getCurYear();
        getOkHttp();


//        int year = calendarView.getCurYear();
//        int month = calendarView.getCurMonth();
//        Map<String, Calendar> map = new HashMap<>();
//        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
//                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
//        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
//                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//        //此方法在巨大的数据量上不影响遍历性能，推荐使用
//        calendarView.setSchemeDate(map);
    }

//    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
//        Calendar calendar = new Calendar();
//        calendar.setYear(year);
//        calendar.setMonth(month);
//        calendar.setDay(day);
//        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
//        calendar.setScheme(text);
//        calendar.addScheme(new Calendar.Scheme());
//        calendar.addScheme(0xFF008800, "假");
//        calendar.addScheme(0xFF008800, "节");
//        return calendar;
//    }

    private void getOkHttp() {
        User user = Application.getUser();
        String classid = "";

        if ("学生".equals(user.getStatus())) {
            classid = user.getClassid();
        } else if ("辅导员".equals(user.getStatus())) {
            classid = user.getCourse();
        } else {
            return;
        }

        if (schedules == null) {
            schedules = new ArrayList<>();
        } else {
            schedules.clear();
        }
        new OkHttpTo()
                .setUrl("GetClassSchedule")
                .setRequestType("post")
                .setJSONObject("classid", classid)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        schedules.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<GetClassSchedule>>() {
                                }.getType()));
                        showRecyclerView(calendarView.getSelectedCalendar());
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();



    }

    @SuppressLint("NotifyDataSetChanged")
    private void showRecyclerView(Calendar calendar) {
        getClassScheduleList.clear();
        int x = calendar.getWeek() * 3;
        if (x > 1 && x < 16) {
            getClassScheduleList.add(schedules.get(x - 3));
            getClassScheduleList.add(schedules.get(x - 2));
            getClassScheduleList.add(schedules.get(x - 1));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ScheduleRecyclerViewAdapter(getClassScheduleList, calendarView.getSelectedCalendar());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        tvLunar.setVisibility(View.VISIBLE);
        tvYear.setVisibility(View.VISIBLE);
        tvMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        tvYear.setText(String.valueOf(calendar.getYear()));
        tvLunar.setText(calendar.getLunar() + "");
        mYear = calendar.getYear();
        showRecyclerView(calendar);
    }

    @Override
    public void onYearChange(int year) {
        tvMonthDay.setText(String.valueOf(year));
    }
}
