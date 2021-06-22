package com.example.smartcampus.fragment.statisticsFragment;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.statisticsAdapter.StraightAStudent_adapter;
import com.example.smartcampus.bean.statistics.GetStraightAStudent;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public class Fragment_straightAStudent extends BaseFragment {
    
    private TextView title;
    private RecyclerView recyclerView;
    private LinearLayout btnLearningSituation;
    private BarChart barChart;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_straightastudent;
    }
    
    @Override
    protected void initView(View view) {
        ImageView back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnLearningSituation = view.findViewById(R.id.btn_learning_situation);
        barChart = view.findViewById(R.id.barChart);
    }
    
    @Override
    protected void initData() {
        title.setText("学霸指数");
        
        getOkHttp();
        
        btnLearningSituation.setOnClickListener(v -> {
            ((FragmentActivity) getActivity())
                .setFragment(new Fragment_straightAStudent_msg(getStraightAStudents));
        });
    }
    
    private List<BarEntry> barEntries;
    private List<String> strings;
    private double node250 = 0;
    private double node200 = 0;
    private double node150 = 0;
    private double node100 = 0;
    private double node0 = 0;
    
    private static final String TAG = "Fragment_straightAStude";
    
    private void getPieChart() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            strings = new ArrayList<>();
        } else {
            barEntries.clear();
            strings.clear();
        }
        
        for (GetStraightAStudent getStraightAStudent : getStraightAStudents) {
            if (getStraightAStudent.getSum() >= 250) {
                node250++;
            } else if (getStraightAStudent.getSum() >= 200) {
                node200++;
            } else if (getStraightAStudent.getSum() >= 150) {
                node150++;
            } else if (getStraightAStudent.getSum() >= 100) {
                node100++;
            } else {
                node0++;
            }
        }
        
        int sun = getStraightAStudents.size();
        
        int fin250 = (int) Math.ceil(node250 / sun * 100);
        int fin200 = (int) Math.ceil(node200 / sun * 100);
        int fin100 = (int) Math.ceil(node100 / sun * 100);
        int fin0 = (int) Math.ceil(node0 / sun * 100);
        int fin150 = 100 - fin250 - fin200 - fin100 - fin0;
        
        barEntries.add(new BarEntry(0, fin250));
        barEntries.add(new BarEntry(1, fin200));
        barEntries.add(new BarEntry(2, fin150));
        barEntries.add(new BarEntry(3, fin100));
        barEntries.add(new BarEntry(4, fin0));
        
        strings.add("学霸");
        strings.add("200分以上");
        strings.add("150分以上");
        strings.add("100分以上");
        strings.add("100分以下");
        
        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        
        barDataSet.setColors(
            Color.parseColor("#722ED1"),
            Color.parseColor("#F5212D"),
            Color.parseColor("#FA531C"),
            Color.parseColor("#1890FF"),
            Color.parseColor("#53C41A")
        );
    
    
        barDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("0");
                return format.format(value)+"%";
            }
        });
        BarData data = new BarData(barDataSet);
        data.setValueTextSize(20);
        barChart.getLegend().setEnabled(false);
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);					//关闭Y轴网格线
        yAxis.setAxisMinimum(0);						//最小值
        yAxis.setTextSize(20);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);	//X轴在底部显示
        xAxis.setValueFormatter(new IndexAxisValueFormatter(strings));	//将每秒显示的数据放到X轴
        xAxis.setLabelCount(strings.size());			//标签个数
        xAxis.setGranularity(1f);						//两个数之间的间距
        xAxis.setTextSize(15);
        xAxis.setDrawGridLines(false);					//关闭网格线
        xAxis.setLabelRotationAngle(-35);
        barChart.getAxisRight().setEnabled(false);		//关闭右边线和数据
        barChart.animateXY(0, 2000);
        barChart.setData(data);							//数据集合
        barChart.setTouchEnabled(false);				//禁用触摸
        barChart.getDescription().setEnabled(false);        //不展示图表描述信息
        barChart.invalidate();
        
    }
    
    private List<GetStraightAStudent> getStraightAStudents, getStraightAStudentList;
    
    private void getOkHttp() {
        if (getStraightAStudents == null) {
            getStraightAStudents = new ArrayList<>();
            getStraightAStudentList = new ArrayList<>();
        } else {
            getStraightAStudents.clear();
            getStraightAStudentList.clear();
        }
        new OkHttpTo()
            .setUrl("getStraightAStudent")
            .setRequestType("GET")
            .setDialog(getContext())
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    getStraightAStudents.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                        new TypeToken<List<GetStraightAStudent>>() {
                        }.getType()));
                    getPieChart();
                    show();
                }
                
                @Override
                public void onFailure(IOException e) {
                
                }
            }).start();
        
    }
    
    private void show() {
        
        Collections.sort(getStraightAStudents, (o1, o2) -> o2.getSum() - o1.getSum());
        for (int i = 0; i < 10; i++) {
            GetStraightAStudent student = getStraightAStudents.get(i);
            getStraightAStudentList.add(student);
        }
    
        for (int i = 0; i < getStraightAStudents.size(); i++) {
            GetStraightAStudent student = getStraightAStudents.get(i);
            student.setNumber(i+1+"");
        }

        
        
        
        StraightAStudent_adapter adapter = new StraightAStudent_adapter(getStraightAStudentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
