package com.example.smartcampus.fragment.homeScreenFragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.homeScreenAdapter.FunctionRecyclerViewAdapter;
import com.example.smartcampus.adapter.homeScreenAdapter.HomeThemeRecyclerViewAdapter;
import com.example.smartcampus.bean.home.HomeFunction;
import com.example.smartcampus.bean.home.HomeTheme;
import com.example.smartcampus.bean.home.Image;
import com.example.smartcampuslibrary.adapter.BaseViewPagerAdapter;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.example.smartcampuslibrary.utils.Utils;
import com.example.smartcampuslibrary.utils.myView.BaseImageView;
import com.example.smartcampuslibrary.utils.myView.BaseRecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.lun_bo_view_pager)
    ViewPager lunBoViewPager;
    @BindView(R.id.function_recycler_view)
    BaseRecyclerView functionRecyclerView;
    @BindView(R.id.theme_recycler_view)
    BaseRecyclerView themeRecyclerView;
    @BindView(R.id.news_recycler_view)
    BaseRecyclerView newsRecyclerView;

    private List<HomeFunction> functionList;
    private List<HomeTheme> themeList;
    private List<Image> imageList;
    private boolean isLunBo;
    private MyThreat myThreat;

    @Override
    protected int layoutResId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {
        showFunction();
        showTheme();
        getImage();
    }

    private void getImage() {
        new OkHttpTo().setUrl("GetImages")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i("aaaaaa" , "=======================1111");
                        imageList = new Gson().fromJson(jsonObject.optJSONArray("rows").toString() ,
                                new TypeToken<List<Image>>(){}.getType());
                        if (imageList != null){
                            showLunBoViewPager();
                            Log.i("aaaaaa" , "=======================222222");
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void showLunBoViewPager() {
        List<View> views = new ArrayList<>();

        for (Image image :
                imageList) {
            BaseImageView imageView = new BaseImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageUrl(image.getName());
            views.add(imageView);
        }

        lunBoViewPager.setAdapter(new BaseViewPagerAdapter(views) {
            @Override
            protected int getItemCount() {
                return Integer.MAX_VALUE;
            }
        });
        startLunBo();
    }

    private void startLunBo(){
        if (myThreat == null){
            isLunBo = true;
            myThreat = new MyThreat();
            myThreat.start();
        }
    }

    private void stopLunBo(){
        if (myThreat != null){
            isLunBo = false;
            myThreat.interrupt();
            myThreat = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        startLunBo();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLunBo();
    }

    private class MyThreat extends Thread{
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(3000);
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lunBoViewPager.setCurrentItem(lunBoViewPager.getCurrentItem() + 1);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (isLunBo);
        }
    }

    private void showTheme() {
        setTheme();
        themeRecyclerView.setLayoutManager(new GridLayoutManager(getContext() , 3 ,
            GridLayoutManager.VERTICAL , false));
        HomeThemeRecyclerViewAdapter adapter = new HomeThemeRecyclerViewAdapter(
            themeList);
        themeRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            for (int i = 0; i < themeList.size(); i++) {
                themeList.get(i).setClick(i == position);
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void setTheme() {
        themeList = new ArrayList<>();
        themeList.add(new HomeTheme("学习之星" , true));
        themeList.add(new HomeTheme("就业标兵" , false));
        themeList.add(new HomeTheme("学校简介" , false));
    }

    private void showFunction() {
        setFunctionList();
        functionRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
            GridLayoutManager.VERTICAL, false));
        FunctionRecyclerViewAdapter adapter = new FunctionRecyclerViewAdapter(
            functionList);
        functionRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            if (Application.getUser() == null){
                Utils.showToast("请先登录！");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("name" , functionList.get(position).getName());
            toClass(getContext() , FragmentActivity.class , bundle);
        });
    }

    private void setFunctionList() {
        functionList = new ArrayList<>();
        functionList.add(new HomeFunction("今日课堂", Color.parseColor("#FBBA01"), R.mipmap.jrkt));
        functionList.add(new HomeFunction("信息统计", Color.parseColor("#65EBD9"), R.mipmap.xxtj));
    }

    @Override
    public void onClick(View v) {

    }
}
