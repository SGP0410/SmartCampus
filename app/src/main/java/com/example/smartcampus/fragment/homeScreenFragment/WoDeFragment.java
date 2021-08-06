package com.example.smartcampus.fragment.homeScreenFragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.activity.LoginActivity;
import com.example.smartcampus.adapter.OnClickListener;
import com.example.smartcampus.adapter.wodeeAdapter.WodeAdapter;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_admissions1;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.utils.myView.ImageViewOval;

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
    }


    @Override
    protected void initData() {
        title.setText("我的");
        login();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

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
            } else if ("老师".equals(user.getStatus())) {
                imgUser.setImageResource(R.mipmap.user6);
                list.clear();
                list.add("我的成绩");
                list.add("个人资料");
                list.add("学生成绩");
                list.add("问题反馈");
            } else {
                imgUser.setImageResource(R.mipmap.user5);
                list.clear();
                list.add("个人资料");
                list.add("学生成绩");
                list.add("查看认证");
                list.add("问题反馈");
            }
            name.setText(user.getName());
            studentId.setText("学号：" + user.getSchoolCard());
            department.setText("所在系：" + user.getCollegName());
            if (adapter == null){
                adapter = new WodeAdapter(list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
            adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    String s = list.get(position);
                    switch (s){
                        case "我的成绩":
                            getFragment(new FragmentWoDeScore());
                            break;
                        case "个人资料":
                            getFragment(new FragmentWoDeGerenziliao());
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    private void getFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment).commit();
    }

    private static final String TAG = "WoDeFragment";

    private void login() {
        name.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
    }

    @Override
    public void onClick(View v) {

    }
}
