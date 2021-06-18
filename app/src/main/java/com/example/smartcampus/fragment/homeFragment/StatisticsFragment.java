package com.example.smartcampus.fragment.homeFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.homeAdapter.StatisticsRecyclerViewAdapter;
import com.example.smartcampus.bean.home.HomeFunction;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_count;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_count_sex;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_count_student;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_getAJob;
import com.example.smartcampus.fragment.statisticsFragment.StudentSourceFragment;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatisticsFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.statistics_recycler_view)
    RecyclerView statisticsRecyclerView;

    private List<HomeFunction> functionList;

    @Override
    protected int layoutResId() {
        return R.layout.statistics_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        title.setText("信息统计");
    }

    @Override
    protected void initData() {
        setHomeFunction();
        statisticsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
            GridLayoutManager.VERTICAL, false));
        StatisticsRecyclerViewAdapter adapter = new StatisticsRecyclerViewAdapter(
            functionList);
        statisticsRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            switch (functionList.get(position).getName().split(",")[0]) {
                case "学生生源":
                    ((FragmentActivity) Objects.requireNonNull(getActivity()))
                        .setFragment(new StudentSourceFragment());
                    break;
                case "性别统计":
                    ((FragmentActivity)getActivity()).setFragment(new Fragment_count());
                    
                    break;
                case "学生统计":
                    ((FragmentActivity)getActivity()).setFragment(new Fragment_count_student());
                    
                    break;
                case "消费趋势":
                    break;
                case "学霸指数":
                    break;
                case "招生信息":
                    break;
                case "学生就业":
                    ((FragmentActivity)getActivity()).setFragment(new Fragment_getAJob());
                    break;
            }
        });
    }
    
    private void setHomeFunction() {
        functionList = new ArrayList<>();
        functionList.add(new HomeFunction("学生生源,StudentSource", Color.parseColor("#2C77C5"),
            R.mipmap.xssy));
        functionList.add(new HomeFunction("性别统计,GenderStatistics", Color.parseColor("#14A88B"),
            R.mipmap.xbtj));
        functionList.add(new HomeFunction("学生统计,StudentStatistics", Color.parseColor("#FF7D01"),
            R.mipmap.xstj));
        functionList.add(new HomeFunction("消费趋势,ConsumptionTrend", Color.parseColor("#798EFE"),
            R.mipmap.xfqs));
        functionList.add(new HomeFunction("学霸指数,schoolDomination", Color.parseColor("#F86851"),
            R.mipmap.xbzs));
        functionList.add(new HomeFunction("招生信息,Admissions", Color.parseColor("#14A88B"), R.mipmap.zsxx));
        functionList.add(new HomeFunction("学生就业,Employment", Color.parseColor("#FF7D01"),
            R.mipmap.xsjy));
    }

    @Override
    public void onClick(View v) {

    }
}
