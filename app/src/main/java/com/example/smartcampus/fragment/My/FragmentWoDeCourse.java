package com.example.smartcampus.fragment.My;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.wodeeAdapter.WoDeCourseAdapter;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.GetClassSchedule;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/10 8:22 星期二
 */
public class FragmentWoDeCourse extends BaseFragment {

    private ImageView back;
    private TextView title;
    private List<GetClassSchedule> schedules = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wodecourse;
    }

    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        title.setText("课程表");
        getOkHttp();
    }

    private static final String TAG = "FragmentWoDeCourse";

    private void getOkHttp() {
        User user = Application.getUser();
        String classid = "";
        if ("学生".equals(user.getStatus())) {
            classid = user.getClassid();
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
                        getRecycler();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();

    }

    private void getRecycler() {
        WoDeCourseAdapter adapter = new WoDeCourseAdapter(schedules);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),5));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),RecyclerView.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
