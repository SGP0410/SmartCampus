package com.example.smartcampus.fragment.homeFragment;

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
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.homeAdapter.StatisticsRecyclerViewAdapter;
import com.example.smartcampus.bean.home.HomeFunction;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_count;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_count_student;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_getAJob;
import com.example.smartcampus.fragment.statisticsFragment.Fragment_straightAStudent;
import com.example.smartcampus.fragment.statisticsFragment.ProvinceStudentSourceFragment;
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
        statisticsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),
            getSpanCount(200),
            GridLayoutManager.VERTICAL, false));
        StatisticsRecyclerViewAdapter adapter = new StatisticsRecyclerViewAdapter(
            functionList);
        statisticsRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            switch (functionList.get(position).getName().split(",")[0]) {
                case "学生生源":
                    ((FragmentActivity) Objects.requireNonNull(getActivity()))
                        .setFragment(new ProvinceStudentSourceFragment());
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
                    ((FragmentActivity)getActivity()).setFragment(new Fragment_straightAStudent());
                    break;
                case "招生信息":
                    break;
                case "学生就业":
                    ((FragmentActivity) getActivity()).setFragment(new Fragment_getAJob());
                    break;
            }
        });
    }

    /**
     * 获取一行最多显示多少item
     *
     * @param itemWidth item的宽度
     * @return
     */
    private int getSpanCount(int itemWidth) {
        DisplayMetrics dm = new DisplayMetrics();
        Objects.requireNonNull(Objects.requireNonNull(getContext()).getDisplay()).getMetrics(dm);
        //获取屏幕宽度
        int widthPixels = dm.widthPixels;
        //获取设备像素密度
        float density = dm.densityDpi;
        //1dp*像素密度(dpi)/160(dpi) = 实际像素数(px)
        int dp = (int) ((widthPixels * 160) / density);

        if (itemWidth == 0) {
            return 0;
        }
        //计算出一行显示多少item

        double spanCount = ((double)dp) / ((double)itemWidth);

        return (int) Math.round(spanCount);
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
