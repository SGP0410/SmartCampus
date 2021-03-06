package com.example.smartcampus.fragment.homeFragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.homeAdapter.StatisticsRecyclerViewAdapter;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_Admissions;
import com.example.smartcampus.bean.home.HomeFunction;
import com.example.smartcampus.fragment.statisticsFragment.ConsumptionTrendFragment;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_count;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_count_student;

import com.example.smartcampus.fragment.statisticsFragment.Fragment_getAJob1;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_straightAStudent;
import com.example.smartcampus.fragment.statisticsFragment.ProvinceStudentSourceFragment;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;

import org.json.JSONObject;

import java.io.IOException;
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

    private static final String TAG = "StatisticsFragment";

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        title.setText("????????????");
        new OkHttpTo()
                .setUrl("getStraightAStudent")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, "onResponse: "+jsonObject.toString());
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }
    
    @Override
    protected void initData() {
        setHomeFunction();
        statisticsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),
            getSpanCount(200),
            GridLayoutManager.VERTICAL, false));
        StatisticsRecyclerViewAdapter adapter = new StatisticsRecyclerViewAdapter(
            functionList);
        statisticsRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            switch (functionList.get(position).getName().split(",")[0]) {
                case "????????????":
                    ((FragmentActivity) Objects.requireNonNull(getActivity()))
                        .setFragment(new ProvinceStudentSourceFragment());
                    break;
                case "????????????":
                    ((FragmentActivity) getActivity()).setFragment(new Fragment_count());
                    break;
                case "????????????":
                    ((FragmentActivity) getActivity()).setFragment(new Fragment_count_student());
                    break;
                case "????????????":
                    ((FragmentActivity)getActivity()).setFragment(new ConsumptionTrendFragment());
                    break;
                case "????????????":
                    ((FragmentActivity) getActivity()).setFragment(new Fragment_straightAStudent());
                    break;
                case "????????????":
                    ((FragmentActivity)getActivity()).setFragment(new Fragment_Admissions());
                    break;
                case "????????????":
                    ((FragmentActivity) getActivity()).setFragment(new Fragment_getAJob1());
                    break;
            }
        });
    }
    
    /**
     * ??????????????????????????????item
     *
     * @param itemWidth item?????????
     * @return
     */
    @SuppressLint("NewApi")
    private int getSpanCount(int itemWidth) {
        DisplayMetrics dm = new DisplayMetrics();
        Objects.requireNonNull(Objects.requireNonNull(getContext()).getDisplay()).getMetrics(dm);
        //??????????????????
        int widthPixels = dm.widthPixels;
        //????????????????????????
        float density = dm.densityDpi;
        //1dp*????????????(dpi)/160(dpi) = ???????????????(px)
        int dp = (int) ((widthPixels * 160) / density);
        
        if (itemWidth == 0) {
            return 0;
        }
        //???????????????????????????item
        
        double spanCount = ((double) dp) / ((double) itemWidth);
        
        return (int) Math.round(spanCount);
    }
    
    private void setHomeFunction() {
        functionList = new ArrayList<>();
        functionList.add(new HomeFunction("????????????,StudentSource", Color.parseColor("#2C77C5"),
            R.mipmap.xssy));
        functionList.add(new HomeFunction("????????????,GenderStatistics", Color.parseColor("#14A88B"),
            R.mipmap.xbtj));
        functionList.add(new HomeFunction("????????????,StudentStatistics", Color.parseColor("#FF7D01"),
            R.mipmap.xstj));
        functionList.add(new HomeFunction("????????????,ConsumptionTrend", Color.parseColor("#798EFE"),
            R.mipmap.xfqs));
        functionList.add(new HomeFunction("????????????,schoolDomination", Color.parseColor("#F86851"),
            R.mipmap.xbzs));
        functionList.add(new HomeFunction("????????????,Admissions", Color.parseColor("#14A88B"), R.mipmap.zsxx));
        functionList.add(new HomeFunction("????????????,Employment", Color.parseColor("#FF7D01"),
            R.mipmap.xsjy));
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
