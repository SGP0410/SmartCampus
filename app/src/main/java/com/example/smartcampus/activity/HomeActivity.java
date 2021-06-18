package com.example.smartcampus.activity;

import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.smartcampus.R;
import com.example.smartcampus.fragment.homeScreenFragment.HomeFragment;
import com.example.smartcampus.fragment.homeScreenFragment.WoDeFragment;
import com.example.smartcampus.fragment.homeScreenFragment.YinYongFragment;
import com.example.smartcampuslibrary.activity.BaseHomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.Objects;

/**
 * 主页可调用setFragment()方法显示fragment
 */
public class HomeActivity extends BaseHomeActivity {
    
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.nav)
    BottomNavigationView nav;
    private Map<String, Fragment> fragmentMap;

    @Override
    protected int frameLayoutID() {
        return R.id.frame_layout;
    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        initFragmentMap();
        initNav();
        showFragment(1);
    }

    private void showFragment(int index){
        nav.postDelayed(() -> {
            nav.setSelectedItemId(nav.getMenu().getItem(index).getItemId());
        } , 100);
    }

    /**
     * 初始化首页fragment
     */
    private void initFragmentMap() {
        fragmentMap = new HashMap<>();
        //用于存放主页fragment界面
        fragmentMap.put("首页" , new HomeFragment());
        fragmentMap.put("应用" , new YinYongFragment());
        fragmentMap.put("我的" , new WoDeFragment());
    }

    private void initNav() {
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //切换fragment
                switch (item.getItemId()){
                    case R.id.yin_yong:
                        setFragment(Objects.requireNonNull(fragmentMap.get("应用")));
                        break;
                    case R.id.sou_ye:
                        setFragment(Objects.requireNonNull(fragmentMap.get("首页")));
                        break;
                    case R.id.wo_de:
                        setFragment(Objects.requireNonNull(fragmentMap.get("我的")));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}