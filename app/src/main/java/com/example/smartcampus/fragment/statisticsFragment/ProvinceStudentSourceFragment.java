package com.example.smartcampus.fragment.statisticsFragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.bean.statistics.Province;
import com.example.smartcampus.bean.statistics.ProvinceStudentSource;
import com.example.smartcampus.utils.MapView;
import com.example.smartcampus.utils.destureviewbinder.GestureViewBinder;
import com.example.smartcampuslibrary.adapter.BaseViewPagerAdapter;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class ProvinceStudentSourceFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.group_view)
    LinearLayout groupView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layout1)
    LinearLayout layout1;


    private List<Province> provinceList;
    private List<ProvinceStudentSource> provinceStudentSourceList1;
    private List<ProvinceStudentSource> provinceStudentSourceList2;
    private List<String> names;
    private BarChart barChart1;
    private BarChart barChart2;
    private List<Integer> colorList1;
    private List<Integer> colorList2;


    @Override
    protected int layoutResId() {
        return R.layout.student_source_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        title.setText("省生源");
    }

    @Override
    protected void initData() {
        GestureViewBinder binder = GestureViewBinder.bind(getContext(), groupView, mapView);
        binder.setFullGroup(true);
        mapView.setOnMapViewClickListener(name -> {
            Log.i("aaaaa" , "--------"+name);
//            ((FragmentActivity) getActivity())
//                .setFragment(new MunicipalStudentSourceFragment(name));
        });

        province_query_all();

    }

    private void province_query_all() {
        new OkHttpTo().setUrl("province_query_all")
            .setRequestType("get")
            .setDialog(getContext())
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    provinceList = new Gson().fromJson(jsonObject.optJSONArray(
                        "data").toString(), new TypeToken<List<Province>>() {
                    }.getType());
                    if (provinceList != null) {
                        getProvinceStudentSource();
                    }
                }

                @Override
                public void onFailure(IOException e) {

                }
            }).start();
    }

    private void getProvinceStudentSource() {
        provinceStudentSourceList1 = new ArrayList<>();
        for (Province province : provinceList) {
            new OkHttpTo()
                .setUrl("GetProvinceStudentSource?provinceName=" + province.getProvinceName())
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        provinceStudentSourceList1.add(new Gson().fromJson(jsonObject.toString(),
                            ProvinceStudentSource.class));

                        if (provinceStudentSourceList1.size() == provinceList.size()) {
                            showData();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
        }
    }

    private void showData() {
        provinceStudentSourceList2 = new ArrayList<>();
        provinceStudentSourceList2.addAll(provinceStudentSourceList1);

        Collections.sort(provinceStudentSourceList1, ((o1, o2) -> {
            return o2.getEliteStudent() - o1.getEliteStudent();
        }));

        Collections.sort(provinceStudentSourceList2, ((o1, o2) -> {
            return o2.getPoorStudent() - o1.getPoorStudent();
        }));

        setView();
    }

    private void setView() {
        List<View> viewList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            BarChart barChart = new BarChart(getContext());
            setBarChart(barChart, i);
            viewList.add(barChart);
        }

        viewPager.setAdapter(new BaseViewPagerAdapter(viewList) {
            @Override
            protected int getItemCount() {
                return viewList.size();
            }
        });

        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showLayout1(position);
                if (position == 0) {
                    barChart1.animateXY(0, 2000);
                    showMap(colorList1 , provinceStudentSourceList1);
                } else if (position == 1) {
                    barChart2.animateXY(0, 2000);
                    showMap(colorList2 , provinceStudentSourceList2);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setLayout1();

        showMap(colorList1 , provinceStudentSourceList1);

    }

    private void showMap(List<Integer> colorList,
        List<ProvinceStudentSource> provinceStudentSourceList) {

        Map<String , Integer> colorMap = new HashMap<>();
        for (int i = 0; i < colorList.size(); i++) {
            colorMap.put(provinceStudentSourceList.get(i).getProvinceName() , colorList.get(i));
        }
        mapView.setColors(colorMap);
        mapView.setMapResId(R.raw.china);
    }


    private void setBarChart(BarChart barChart, int i) {
        barChart.getLegend().setEnabled(false);
        barChart.setDescription(null);
        setBarChartData(barChart, i);
    }

    private void setBarChartData(BarChart barChart, int i) {
        List<BarEntry> barEntryList = new ArrayList<>();
        BarDataSet barDataSet = null;
        BarData barData = null;

        names = new ArrayList<>();
        names.add("");

        String[] colors1 = {"#f4a235", "#f8b751", "#ebba46", "#efc800", "#f1cc3e",
            "#f5e84c", "#f5e84c"};
        String[] colors2 = {"#e21c13", "#a71e32", "#46b797", "#63be9d", "#a0d4bd",
            "#bee0d0", "#bee0d0"};
        //优秀
        if (i == 0) {
            barChart1 = barChart;
            for (int i1 = 0; i1 < provinceStudentSourceList1.size(); i1++) {
                barEntryList
                    .add(new BarEntry(i1 + 1,
                        provinceStudentSourceList1.get(i1).getEliteStudent()));
                names.add(provinceStudentSourceList1.get(i1).getProvinceName());
            }
            barDataSet = new BarDataSet(barEntryList, "优秀学子");

            colorList1 = new ArrayList<>();
            for (int j = 0; j < provinceStudentSourceList1.size(); j++) {
                colorList1.add(Color.parseColor(colors1[j / colors1.length]));
            }

            barDataSet.setColors(colorList1);
        }
        //贫困
        if (i == 1) {
            barChart2 = barChart;
            for (int i1 = 0; i1 < provinceStudentSourceList2.size(); i1++) {
                barEntryList
                    .add(new BarEntry(i1 + 1,
                        provinceStudentSourceList2.get(i1).getPoorStudent()));
                names.add(provinceStudentSourceList2.get(i1).getProvinceName());
            }
            barDataSet = new BarDataSet(barEntryList, "贫困学子");

            colorList2 = new ArrayList<>();
            for (int j = 0; j < provinceStudentSourceList2.size(); j++) {
                colorList2.add(Color.parseColor(colors2[j / colors2.length]));
            }

            barDataSet.setColors(colorList2);
        }

        setAxis(barChart);

        barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);

        barChart.setData(barData);
        barChart.animateXY(0, 2000);
    }

    private void setAxis(BarChart barChart) {

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setLabelCount(provinceStudentSourceList1.size());
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
        xAxis.setLabelRotationAngle(90);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(10);

        YAxis yAxis1 = barChart.getAxisRight();
        yAxis1.setEnabled(false);
        yAxis1.setAxisMinimum(0f);
        yAxis1.setLabelCount(10);

    }

    private void showLayout1(int position) {
        for (int i = 0; i < layout1.getChildCount(); i++) {
            if (i == position) {
                layout1.getChildAt(i).setLayoutParams(new LayoutParams(110, 30));
            } else {
                layout1.getChildAt(i).setLayoutParams(new LayoutParams(100, 20));
            }
        }
    }

    private void setLayout1() {
        for (int i = 0; i < 2; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.green_dian);
            imageView.setPadding(40, 0, 40, 0);
            if (i == 0) {
                imageView.setLayoutParams(new LayoutParams(110, 30));
            } else {
                imageView.setLayoutParams(new LayoutParams(100, 20));
            }
            layout1.addView(imageView);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
