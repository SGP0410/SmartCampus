package com.example.smartcampus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.smartcampus.R;
import com.example.smartcampus.fragment.homeFragment.ClassTodayFragment;
import com.example.smartcampus.fragment.homeFragment.StatisticsFragment;
import com.example.smartcampus.fragment.applyFragment.Fragment_schoolCard;
import com.example.smartcampuslibrary.activity.BaseFragmentActivity;


/**
 * 子fragment显示页面
 */
public class FragmentActivity extends BaseFragmentActivity {

    private Bundle bundle;
    private String name;

    @Override
    protected int frameLayoutID() {
        return R.id.frame_layout;
    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void initView() {
        bundle = getIntent().getExtras();
        assert bundle != null;
        name = bundle.getString("name");
    }

    @Override
    protected void initData() {
        switch (name){
            case "今日课堂":
                setFragment(new ClassTodayFragment());
                break;
            case "信息统计":
                setFragment(new StatisticsFragment());
                break;
            case "校园卡":
                setFragment(new Fragment_schoolCard());
                break;
        }
    }
}