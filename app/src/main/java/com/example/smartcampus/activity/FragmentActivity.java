package com.example.smartcampus.activity;

import android.os.Bundle;

import com.example.smartcampus.R;
import com.example.smartcampus.fragment.My.FragmentApplyForCertification;
import com.example.smartcampus.fragment.My.FragmentFeedback;
import com.example.smartcampus.fragment.My.FragmentWoDeCourse;
import com.example.smartcampus.fragment.applyFragment.Fragment_schoolCard;
import com.example.smartcampus.fragment.homeFragment.ClassTodayFragment;
import com.example.smartcampus.fragment.homeFragment.StatisticsFragment;
import com.example.smartcampus.fragment.homeScreenFragment.FragmentWoDeGerenziliao;
import com.example.smartcampus.fragment.homeScreenFragment.FragmentWoDeScore;
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
            case "我的成绩":
                setFragment(new FragmentWoDeScore());
                break;
            case "个人资料":
                setFragment(new FragmentWoDeGerenziliao());
                break;
            case "申请认证":
                setFragment(new FragmentApplyForCertification());
                break;
            case "问题反馈":
                setFragment(new FragmentFeedback());
                break;
            case "课程表":
                setFragment(new FragmentWoDeCourse());
                break;
            default:
                break;
        }
    }
}