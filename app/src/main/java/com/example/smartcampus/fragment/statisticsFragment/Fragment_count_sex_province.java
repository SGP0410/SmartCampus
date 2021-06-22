package com.example.smartcampus.fragment.statisticsFragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.bean.statistics.GetProvinceMenAndWomenNumberAll;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment;
import com.github.mikephil.charting.components.Legend.LegendOrientation;
import com.github.mikephil.charting.components.Legend.LegendVerticalAlignment;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Fragment_count_sex_province extends BaseFragment {
    
    private final GetProvinceMenAndWomenNumberAll getProvinceMenAndWomenNumberAll;
    private TextView title;
    private TextView txtProvince;
    private TextView itemMan;
    private TextView itemWoman;
    private BarChart barchart;
    private int woman1;
    private int man1;
    private LineChart lineChart;
    private BarChart barChartPileUp;
    private PieChart pieChart;
    private LinearLayout btnNext;
    private PieChart chart;
    
    public Fragment_count_sex_province(GetProvinceMenAndWomenNumberAll getProvinceMenAndWomenNumberAll) {
        this.getProvinceMenAndWomenNumberAll = getProvinceMenAndWomenNumberAll;
    }
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_count_sex_province;
    }
    
    @Override
    protected void initView(View view) {
        ImageView back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        txtProvince = view.findViewById(R.id.txt_province);
        itemMan = view.findViewById(R.id.item_man);
        itemWoman = view.findViewById(R.id.item_woman);
        barchart = view.findViewById(R.id.barChart);
        lineChart = view.findViewById(R.id.lineChart);
        barChartPileUp = view.findViewById(R.id.barChart_pileUp);
        pieChart = view.findViewById(R.id.pieChart);
        btnNext = view.findViewById(R.id.btn_next);
        chart = view.findViewById(R.id.chart1);
        btnNext.setOnClickListener(v -> {
            ((FragmentActivity) getActivity())
                .setFragment(new Fragment_count_sex_city(getProvinceMenAndWomenNumberAll));
        });
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        title.setText("性别统计");
        txtProvince.setText(getProvinceMenAndWomenNumberAll.getProvinceName());
        double sum = getProvinceMenAndWomenNumberAll.getMan() + getProvinceMenAndWomenNumberAll.getWoman();
        double nv = getProvinceMenAndWomenNumberAll.getWoman();
        double woman = nv / sum;
        woman1 = (int) (woman * 100);
        man1 = 100 - woman1;
        itemWoman.setText(woman1 + "%");
        itemMan.setText(man1 + "%");
        
        getBarChart();  //柱状图
        
        getLineChart();
        
        getBarChart_PileUp();
        
        getPieChart1();//平面图
        
        getPieChart();
        
    }
    
    private void getPieChart1() {
        
        ArrayList<PieEntry> entries = new ArrayList<>();
    
        entries.add(new PieEntry((float) (man1), "男"));
        entries.add(new PieEntry((float) (woman1), "女"));
    
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#22D5CE"));
        colors.add(Color.parseColor("#F39444"));
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
    
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(13);
        chart.setData(data);
    
        // undo all highlights
        chart.highlightValues(null);
    
        chart.invalidate();
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
    
        chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
    
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.parseColor("#EEEEEE"));
        chart.setCenterTextSize(20);
    
        chart.setTransparentCircleColor(Color.parseColor("#EEEEEE"));
        chart.setTransparentCircleAlpha(110);
    
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
    
        chart.setDrawCenterText(true);
    
        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
    
        Legend  l = chart.getLegend();
        l.setVerticalAlignment(LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
        chart.animateXY(0, 2000);
        chart.invalidate();
    }
    
    
    private void getPieChart() {
    
    }
    
    private void getBarChart_PileUp() {
        
        barChartPileUp.getDescription().setEnabled(false);        //不展示图表描述信息
        //所有值均绘制在其条形顶部上方
        barChartPileUp.setDrawValueAboveBar(false);
        //纵坐标不显示网格线
        barChartPileUp.getXAxis().setDrawGridLines(false);
        //不支持图表缩放
        barChartPileUp.setScaleEnabled(false);
        //设置点击图表时的标记，可以自定义布局
//        barChartPileUp.setMarker(new MyMarkView(context);
        
        // 改变y标签的位置
        YAxis leftAxis = barChartPileUp.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        //右y轴不显示
        barChartPileUp.getAxisRight().setEnabled(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setMaxWidth(100);
        //获取x坐标轴
        XAxis xLabels = barChartPileUp.getXAxis();
        //设置x坐标轴显示位置在下方
        xLabels.setPosition(XAxisPosition.BOTTOM);
        //转换x坐标轴显示内容
        xLabels.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });
        
        //获取图表的图例
        Legend l = barChartPileUp.getLegend();
        //图例设置在下方
        l.setVerticalAlignment(LegendVerticalAlignment.BOTTOM);
        //图例设置在中间
        l.setHorizontalAlignment(LegendHorizontalAlignment.CENTER);
        l.setOrientation(LegendOrientation.HORIZONTAL);
        l.setTextSize(18);
        l.setFormSize(18);
        l.setDrawInside(false);
        
        ArrayList<BarEntry> yValues = new ArrayList<>();
        
        yValues.add(new BarEntry(0, new float[]{(float) (woman1), (float) (man1)}));
        
        BarDataSet set1;
        
        if (barChartPileUp.getData() != null && barChartPileUp.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChartPileUp.getData().getDataSetByIndex(0);
            set1.setValues(yValues);
            barChartPileUp.getData().notifyDataChanged();
            barChartPileUp.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yValues, "");
            set1.setValueTextSize(20);
            set1.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
                DecimalFormat format = new DecimalFormat("0");
                return format.format(value) + "%";
            });
            //设置图例的颜色和名称
            set1.setColors(new int[]{R.color.drawing_nv, R.color.drawing_man}, getContext());
            set1.setStackLabels(new String[]{"女", "男"});
            
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            
            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);
            
            barChartPileUp.setData(data);
        }
        barChartPileUp.animateXY(0, 2000);
        barChartPileUp.setFitBars(true);
        barChartPileUp.setTouchEnabled(false);                   //禁用触摸
        barChartPileUp.invalidate();
        
        
    }
    
    private List<Entry> entries;
    private List<String> stringLine;
    
    private void getLineChart() {
        if (entries == null) {
            entries = new ArrayList<>();
            stringLine = new ArrayList<>();
        } else {
            entries.clear();
            stringLine.clear();
        }
        
        entries.add(new Entry(0, man1));
        entries.add(new Entry(1, woman1));
        stringLine.add("男");
        stringLine.add("女");
        
        LineDataSet dataSet = new LineDataSet(entries, "");  //单数据存入
        
        dataSet.setCircleColor(Color.parseColor("#F39445"));                 //设置外圈颜色
        dataSet.setCircleHoleRadius(3f);                    //内圈半径
        dataSet.setColor(Color.parseColor("#20D5CE"));                       //绘制线条颜色
        dataSet.setValueTextSize(20);                       //显示数字大小
        dataSet.setLineWidth(4f);                           //线条宽度
        dataSet.setDrawValues(false);                       //关闭图中显示数字
        dataSet.setDrawCircleHole(false);                   //关闭标识线，垂直显示的
        dataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
            DecimalFormat format = new DecimalFormat("0");
            return format.format(value) + "%";
        });
        LineData lineData = new LineData(dataSet);
        
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);                    //关闭Y轴网格线
        yAxis.setAxisMinimum(0);                        //最小值
        yAxis.setAxisMaximum(100);                        //最大值
        
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);    //X轴在底部显示
        xAxis.setValueFormatter(new IndexAxisValueFormatter(stringSex));    //将每秒显示的数据放到X轴
        xAxis.setLabelCount(stringSex.size());            //标签个数
        xAxis.setGranularity(1f);
        xAxis.setTextSize(20);
        //两个数之间的间距
        xAxis.setDrawGridLines(false);                    //关闭网格线
        
        lineChart.setData(lineData);
        lineChart.getLegend().setEnabled(false);
        lineChart.animateXY(1500, 1000);                    //动画效果
        lineChart.getDescription().setEnabled(false);       //说明关闭
        lineChart.getDescription().setText("(S)");          //右下角说明打开
        lineChart.setScaleEnabled(false);                   //禁用缩放比例
        lineChart.setTouchEnabled(false);                   //禁用触摸
        lineChart.invalidate();                             //刷新
        
        
    }
    
    private List<BarEntry> barEntries;
    private List<String> stringSex;
    
    private void getBarChart() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            stringSex = new ArrayList<>();
        } else {
            barEntries.clear();
            stringSex.clear();
        }
        
        barEntries.add(new BarEntry(0, man1));
        barEntries.add(new BarEntry(1, woman1));
        
        stringSex.add("男");
        stringSex.add("女");
        
        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        
        barDataSet.setColor(Color.parseColor("#20D5CE"));
        BarData data = new BarData(barDataSet);
        barDataSet.setValueTextSize(18);            //设置大小
        barDataSet.setValueTextColor(Color.parseColor("#404040"));
        barDataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
            DecimalFormat format = new DecimalFormat("0");
            return format.format(value) + "%";
        });
        data.setBarWidth(0.5f);
        
        barchart.getLegend().setEnabled(false);
        
        YAxis yAxis = barchart.getAxisLeft();
        yAxis.setDrawAxisLine(false);                    //关闭Y轴网格线
        yAxis.setAxisMinimum(0);                        //最小值
        yAxis.setAxisMaximum(100);                        //最大值
        
        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);    //X轴在底部显示
        xAxis.setValueFormatter(new IndexAxisValueFormatter(stringSex));    //将每秒显示的数据放到X轴
        xAxis.setLabelCount(stringSex.size());            //标签个数
        xAxis.setGranularity(1f);
        xAxis.setTextSize(20);
        //两个数之间的间距
        xAxis.setDrawGridLines(false);                    //关闭网格线
        
        barDataSet.setColors(Color.parseColor("#22D5CE"), Color.parseColor("#F39444"));
        
        barchart.animateXY(0, 2000);
        barchart.setFitBars(true);
        barchart.getAxisRight().setEnabled(false);         //关闭右边线和数据
        barchart.setData(data);                            //数据集合
        barchart.setTouchEnabled(false);                   //禁用触摸
        barchart.getDescription().setEnabled(false);       //关闭右下角
        barchart.invalidate();
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
