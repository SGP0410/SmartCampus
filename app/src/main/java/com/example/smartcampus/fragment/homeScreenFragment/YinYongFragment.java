package com.example.smartcampus.fragment.homeScreenFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.yinYongAdapter.YinYong_adapter;
import com.example.smartcampus.bean.yinYong.ApplyType;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import java.util.ArrayList;
import java.util.List;

public class YinYongFragment extends BaseFragment {
    
    private TextView title;
    private RecyclerView recyclerView;
    private List<ApplyType> applyTypes = new ArrayList<>();
    
    @Override
    protected int layoutResId() {
        return R.layout.yin_yong_fragment;
    }
    
    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
    }
    
    @Override
    protected void initData() {
        title.setText("应用");
        getData();
        YinYong_adapter adapter = new YinYong_adapter(applyTypes);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString("name",applyTypes.get(position).getName());
            toClass(getContext(), FragmentActivity.class,bundle);
        });
    }
    
    private void getData() {
        applyTypes.add(new ApplyType("图书馆借阅",R.mipmap.library,R.drawable.apply_green));
        applyTypes.add(new ApplyType("校园卡",R.mipmap.school_card,R.drawable.apply_orange));
        applyTypes.add(new ApplyType("所在系",R.mipmap.department,R.drawable.apply_blue));
        applyTypes.add(new ApplyType("我的课程",R.mipmap.course,R.drawable.apply_red));
        applyTypes.add(new ApplyType("上课请假",R.mipmap.leave,R.drawable.apply_purple));
        applyTypes.add(new ApplyType("校统计",R.mipmap.statistics,R.drawable.apply_green));
        applyTypes.add(new ApplyType("就业信息",R.mipmap.employment,R.drawable.apply_orange));
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
