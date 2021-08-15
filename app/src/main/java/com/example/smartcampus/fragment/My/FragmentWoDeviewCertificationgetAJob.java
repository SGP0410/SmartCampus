package com.example.smartcampus.fragment.My;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.Student;
import com.example.smartcampus.bean.statistics.WorkNature;
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
 * @date 2021/8/11 8:42 星期三
 */
public class FragmentWoDeviewCertificationgetAJob extends BaseFragment {

    private ImageView back;
    private TextView title;
    private List<Student> studentList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_certification_ajob;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        title.setText("就业认证");
        getokHttp();
    }

    private static final String TAG = "FragmentWoDeviewCertifi";

    private void getokHttp() {
        User user = Application.getUser();
        Log.d(TAG, "getokHttp: "+user.getCourse());
        studentList.clear();
        new OkHttpTo()
                .setUrl("GetStudentClassId")
                .setRequestType("post")
                .setJSONObject("classid", user.getCourse())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, "onResponse: "+jsonObject.toString());
                        studentList.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<Student>>() {
                                }.getType()));
                        Log.d(TAG, "onResponse: "+studentList.size());
                        getWork();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private List<WorkNature> workNatureList;

    private void getWork() {
        if (workNatureList == null) {
            workNatureList = new ArrayList<>();
        } else {
            workNatureList.clear();
        }
        new OkHttpTo()
                .setUrl("getWorkNatureName_query_all")
                .setRequestType("post")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        workNatureList.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<WorkNature>>() {}.getType()));
                        show();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void show() {
        WoDeviewCertificationgetAJobAdapter adapter = new WoDeviewCertificationgetAJobAdapter(studentList,workNatureList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
