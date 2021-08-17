package com.example.smartcampus.adapter.homeAdapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.GetClassSchedule;
import com.example.smartcampus.bean.statistics.Major;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ScheduleRecyclerViewAdapter extends BaseRecyclerViewAdapter<ScheduleRecyclerViewAdapter.ViewHolder, GetClassSchedule> {
    private Calendar calendar;
    private List<Major> majorList;

    public ScheduleRecyclerViewAdapter(List<GetClassSchedule> schedules, Calendar calendar) {
        super(schedules, R.layout.schedule_item);
        this.calendar = calendar;
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setValues(ViewHolder holder, GetClassSchedule bean) {
        holder.tvDate1.setText(calendar.getMonth() + "-" + calendar.getDay()+"\n\n"+getWeek(calendar.getWeek()));
        holder.tvDate2.setText(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay() + getWeek(calendar.getWeek()) + "的课堂教学");
        holder.number.setText("第" + (getPosition() + 1) + "节    "+bean.getName()+"    "+bean.getCourse());
        User user = Application.getUser();
        if (Application.getMajorList() == null) {
            new OkHttpTo().setUrl("getMajor_query_all")
                    .setRequestType("get")
                    .setOkHttpLo(new OkHttpLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            List<Major> majorList = new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                    new TypeToken<List<Major>>() {
                                    }.getType());
                            Application.setMajorList(majorList);
                            holder.banJi.setText(user.getGrade() + getMajorName(user.getMajorId()) + user.getClas());
                        }

                        @Override
                        public void onFailure(IOException e) {

                        }
                    }).start();
        }else {
            holder.banJi.setText(user.getGrade() + getMajorName(user.getMajorId()) + user.getClas());
        }
    }

    private String getMajorName(String MajorId) {
        majorList = Application.getMajorList();
        for (Major m :
                majorList) {
            if (m.getId().equals(MajorId)) {
                return m.getMajorName();
            }
        }
        return "";
    }

    private String getWeek(int week) {
        switch (week) {
            case 0:
                return "周日";
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            default:
                return "";
        }
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate1;
        private TextView tvDate2;
        private TextView number;
        private TextView banJi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate1 = itemView.findViewById(R.id.tv_date1);
            tvDate2 = itemView.findViewById(R.id.tv_date2);
            number = itemView.findViewById(R.id.number);
            banJi = itemView.findViewById(R.id.ban_ji);
        }
    }
}
