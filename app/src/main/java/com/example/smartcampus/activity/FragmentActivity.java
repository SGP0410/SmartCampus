package com.example.smartcampus.activity;

import android.os.Bundle;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.fragment.My.FragmentApplyForCertification;
import com.example.smartcampus.fragment.My.FragmentFeedback;
import com.example.smartcampus.fragment.My.FragmentWoDeCourse;
import com.example.smartcampus.fragment.My.FragmentWoDeCustomerFeedback;
import com.example.smartcampus.fragment.My.FragmentWoDeScoreBoard;
import com.example.smartcampus.fragment.My.FragmentWoDeTongbao;
import com.example.smartcampus.fragment.My.FragmentWoDecertificate;
import com.example.smartcampus.fragment.My.FragmentWoDeviewCertification;
import com.example.smartcampus.fragment.applyFragment.Fragment_schoolCard;
import com.example.smartcampus.fragment.homeFragment.ClassTodayFragment;
import com.example.smartcampus.fragment.homeFragment.StatisticsFragment;
import com.example.smartcampus.fragment.homeScreenFragment.FragmentWoDeGerenziliao;
import com.example.smartcampus.fragment.homeScreenFragment.FragmentWoDeScore;
import com.example.smartcampuslibrary.activity.BaseFragmentActivity;
import com.example.smartcampuslibrary.utils.Utils;


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
        User user = Application.getUser();
        String status = user.getStatus();
        switch (name){
            case "今日课堂":
                setFragment(new ClassTodayFragment());
                break;
            case "信息统计":
                setFragment(new StatisticsFragment());
                break;
            case "校园卡":
                if (Application.getUser() == null){
                    Utils.showToast("请先登录");
                }else {
                    setFragment(new Fragment_schoolCard());
                }
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
            case "成绩管理":
                setFragment(new FragmentWoDeScoreBoard());
                break;
            case "查看认证":
                setFragment(new FragmentWoDeviewCertification());
                break;
            case "证书":
                setFragment(new FragmentWoDecertificate(status));
                break;
            case "用户反馈":
                setFragment(new FragmentWoDeCustomerFeedback());
                break;
            case "通报":
                setFragment(new FragmentWoDeTongbao());
                break;
            default:
                break;
        }
    }
}