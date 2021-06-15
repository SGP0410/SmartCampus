package com.example.smartcampus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.smartcampus.R;
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
            case "校园卡":
                setFragment(new Fragment_schoolCard());
                break;
            //用于显示要显示的fragment，调用setFragment()方法返回按钮绑定onClick事件用于返回
        }
    }
}