package com.example.smartcampus.fragment.My;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.wodeeAdapter.WoDeScoreBoardAdapter;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.Student;
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
 * @date 2021/8/10 10:23 星期二
 */
public class FragmentWoDeScoreBoard extends BaseFragment {

    private ImageView back;
    private TextView title;
    private RecyclerView recyclerView;
    private List<Student> studentList;
    private static final String TAG = "FragmentWoDeScoreBoard";

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_score_board;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        title.setText("成绩管理");
        getOkHttp();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onContextItemSelected: ");
        return super.onContextItemSelected(item);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        getOkHttp();
    }

    private void getOkHttp() {
        User user = Application.getUser();
        if (studentList == null) {
            studentList = new ArrayList<>();
        }else {
            studentList.clear();
        }
        new OkHttpTo()
                .setUrl("GetStudentClassId")
                .setRequestType("post")
                .setJSONObject("classid",user.getCourse())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        studentList.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<Student>>(){}.getType()));
                        getRecyclerView();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void getRecyclerView() {
        WoDeScoreBoardAdapter adapter = new WoDeScoreBoardAdapter(studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            ((FragmentActivity)getActivity()).setFragment(new Fragment_setScoreBoard(studentList.get(position)));
        });

    }

    @Override
    public void onClick(View v) {

    }
}
