package com.example.smartcampus.fragment.My;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.activity.LoginActivity;
import com.example.smartcampus.adapter.wodeeAdapter.WodeAdapter;
import com.example.smartcampus.bean.User;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.example.smartcampuslibrary.utils.myView.ImageViewOval;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WoDeFragment extends BaseFragment {

    private TextView title;
    private TextView name;
    private TextView studentId;
    private TextView department;
    private ImageViewOval imgUser;
    private List<String> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private WodeAdapter adapter;
    private LinearLayout btnCourse;
    private LinearLayout btnProof;
    private TextView txtProof;

    @Override
    protected int layoutResId() {
        return R.layout.wo_de_fragment;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        name = view.findViewById(R.id.name);
        studentId = view.findViewById(R.id.student_id);
        department = view.findViewById(R.id.department);
        imgUser = view.findViewById(R.id.img_user);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnCourse = view.findViewById(R.id.btn_course);
        btnProof = view.findViewById(R.id.btn_proof);
        txtProof = view.findViewById(R.id.txt_proof);
    }

    @Override
    protected void initData() {
        title.setText("我的");
        login();
        btn();
    }


    private void btn() {
        btnCourse.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", "课程表");
            toClass(getContext(), FragmentActivity.class, bundle);
        });
        btnProof.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", "证书");
            toClass(getContext(), FragmentActivity.class, bundle);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        getCertificate();
    }

    private void getCertificate() {
        User user = Application.getUser();
        if (user == null) {
            return;
        } else {
            if ("学生".equals(user.getStatus())) {
                new OkHttpTo()
                        .setUrl("GetCertificateSchoolCard")
                        .setRequestType("post")
                        .setJSONObject("schoolcard", user.getSchoolCard())
                        .setOkHttpLo(new OkHttpLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                String string = jsonObject.optString("total");
                                txtProof.setText(string);
                            }

                            @Override
                            public void onFailure(IOException e) {

                            }
                        }).start();
            }
        }


    }

    private static final String TAG = "WoDeFragment";

    @SuppressLint("SetTextI18n")
    private void getData() {
        User user = Application.getUser();
        if (user != null) {
            if ("学生".equals(user.getStatus())) {
                imgUser.setImageResource(R.mipmap.user1);
                list.clear();
                list.add("个人资料");
                list.add("我的成绩");
                list.add("申请认证");
                list.add("问题反馈");
                studentId.setText("学号：" + user.getSchoolCard());
                department.setText("所在系：" + user.getCollegName());
            } else if ("老师".equals(user.getStatus())) {
                imgUser.setImageResource(R.mipmap.user6);
                list.clear();
                list.add("个人资料");
                list.add("成绩管理");
                list.add("问题反馈");
                studentId.setText("学号：" + user.getSchoolCard());
                department.setText("所在系：" + user.getCollegName());
            } else if ("辅导员".equals(user.getStatus())) {
                imgUser.setImageResource(R.mipmap.user5);
                list.clear();
                list.add("个人资料");
                list.add("成绩管理");
                list.add("查看认证");
                list.add("问题反馈");
                studentId.setText("学号：" + user.getSchoolCard());
                department.setText("所在系：" + user.getCollegName());
            } else {
                imgUser.setImageResource(R.mipmap.user3);
                user.setName("管理员");
                list.clear();
                list.add("用户反馈");
            }
            name.setText(user.getName());

            if (adapter == null) {
                adapter = new WodeAdapter(list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            adapter.setOnItemClickListener(position -> {
                String s = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("name", s);
                toClass(getContext(), FragmentActivity.class, bundle);
            });
        }
    }

    private void login() {
        name.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
    }

    @Override
    public void onClick(View v) {

    }
}
