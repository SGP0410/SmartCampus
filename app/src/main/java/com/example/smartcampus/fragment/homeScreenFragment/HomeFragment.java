package com.example.smartcampus.fragment.homeScreenFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.homeScreenAdapter.FunctionRecyclerViewAdapter;
import com.example.smartcampus.adapter.homeScreenAdapter.HomeThemeRecyclerViewAdapter;
import com.example.smartcampus.bean.home.HomeFunction;
import com.example.smartcampus.bean.home.HomeTheme;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.utils.myView.BaseRecyclerView;
import java.util.ArrayList;
import java.util.List;

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
        themeList.add(new HomeTheme("学校新闻" , true));
        themeList.add(new HomeTheme("院系新闻" , false));
        themeList.add(new HomeTheme("学术动态" , false));
    }

    private void showFunction() {
        setFunctionList();
        functionRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,
            GridLayoutManager.VERTICAL, false));
        FunctionRecyclerViewAdapter adapter = new FunctionRecyclerViewAdapter(
            functionList);
        functionRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
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
