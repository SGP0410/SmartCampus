package com.example.smartcampus.fragment.homeFragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smartcampus.R;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.HashMap;
import java.util.Map;

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
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        tvYear.setText(calendarView.getCurYear() + "");
        tvMonthDay.setText(calendarView.getCurMonth() + "月" + calendarView.getCurDay() + "日");
        tvLunar.setText("今日");
        tvCurrentDay.setText(String.valueOf(calendarView.getCurDay()));
        mYear = calendarView.getCurYear();



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
    }

    @Override
    public void onYearChange(int year) {
        tvMonthDay.setText(String.valueOf(year));
    }
}
