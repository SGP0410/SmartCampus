package com.example.smartcampus.dialog.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
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
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChartViewDialog extends DialogFragment {

    @BindView(R.id.layout1)
    LinearLayout layout1;
    private List<Entry> entryList;
    private List<String> nameList;
    private int[] colors;
    private int position;
    private View view;
    private LineChart lineChart;
    private BarChart barChart;
    private PieChart pieChart;
    private RadarChart radarChart;
    private String lable;
    private int wh;

    public ChartViewDialog(List<Entry> entryList, List<String> nameList, int[] colors, int position, String lable) {
        this.entryList = entryList;
        this.nameList = nameList;
        this.colors = colors;
        this.position = position;
        this.lable = lable;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_view_dialog, container , true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        wh = layout1.getWidth();
        initData();
    }

    private void initData() {
        switch (position){
            case 0:
                lineChart = new LineChart(getContext());
                Log.i("aaaaaaa" , "-----------------"+wh);
                lineChart.setLayoutParams(new LayoutParams(wh , wh));
                view = lineChart;
                setLineChart1();
                break;
            case 1:
                barChart = new BarChart(getContext());
                barChart.setLayoutParams(new LayoutParams(wh , wh));
                view = barChart;
                setBarChart1();
                break;
            case 2:
                pieChart = new PieChart(getContext());
                pieChart.setLayoutParams(new LayoutParams(wh , wh));
                view = pieChart;
                setPieChart1();
                break;
            case 3:
                radarChart = new RadarChart(getContext());
                radarChart.setLayoutParams(new LayoutParams(wh , wh));
                view = radarChart;
                setRadarChart1();
                break;
            default:
        }
//        layout1.addView(view);
    }


    private void setRadarChart1() {
        radarChart.setDescription(null);
        radarChart.setTouchEnabled(false);

        List<RadarEntry> radarEntryList = new ArrayList<>();
        for (Entry entry : entryList) {
            radarEntryList.add(new RadarEntry(entry.getY(), entry.getData()));
        }

        RadarDataSet set = new RadarDataSet(radarEntryList, lable);
        set.setColors(colors);

        //禁用标签
        set.setDrawValues(false);
        //设置填充颜色
        set.setFillColor(Color.BLUE);
        //设置填充透明度
        set.setFillAlpha(40);
        //设置启用填充
        set.setDrawFilled(true);
        //设置点击之后标签是否显示圆形外围
        set.setDrawHighlightCircleEnabled(true);
        //设置点击之后标签圆形外围的颜色
        set.setHighlightCircleFillColor(Color.RED);
        //设置点击之后标签圆形外围的透明度
        set.setHighlightCircleStrokeAlpha(40);
        //设置点击之后标签圆形外围的半径
        set.setHighlightCircleInnerRadius(20f);
        //设置点击之后标签圆形外围内圆的半径
        set.setHighlightCircleOuterRadius(10f);

        RadarData radarData = new RadarData(set);


        radarChart.setData(radarData);
        radarChart.animateXY(0, 2000);
        radarChart.invalidate();

    }

    private void setPieChart1() {
        pieChart.setDescription(null);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setDrawEntryLabels(true);

        List<PieEntry> pieEntryList = new ArrayList<>();
        for (Entry entry : entryList) {
            pieEntryList.add(new PieEntry(entry.getY(), entry.getData()));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntryList, lable);
        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.parseColor("#333333"));
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("00.0%");
                return format.format(value) + "(" + entry.getData() + ")";
            }
        });


        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.animateXY(0, 2000);
        pieChart.invalidate();
    }

    private void setBarChart1() {
        barChart.setDescription(null);
        barChart.setTouchEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);

        List<BarEntry> barEntryList = new ArrayList<>();
        for (Entry entry : entryList) {
            barEntryList.add(new BarEntry(entry.getX(), entry.getY(), entry.getData()));
        }

        BarDataSet barDataSet = new BarDataSet(barEntryList, lable);
        barDataSet.setColors(colors);

        BarData barData = new BarData(barDataSet);
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("00.0%");
                return format.format(value);
            }
        });
        barData.setValueTextColor(Color.parseColor("#333333"));
        barData.setValueTextSize(12f);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0.5f);
        xAxis.setAxisMaximum(nameList.size() - 0.5f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(nameList));
        xAxis.setGranularity(0.5f);
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(12f);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(5);
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat format = new DecimalFormat("00%");
                return format.format(value);
            }
        });
        yAxis.setTextColor(Color.parseColor("#333333"));
        yAxis.setTextSize(12f);

        YAxis yAxis1 = barChart.getAxisRight();
        yAxis1.setEnabled(false);
        yAxis1.setAxisMinimum(0f);
        yAxis1.setLabelCount(5);


        barChart.setData(barData);
        barChart.animateXY(0, 2000);
        barChart.invalidate();
    }

    private void setLineChart1() {
        lineChart.setDescription(null);
        lineChart.setTouchEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        LineDataSet lineDataSet = new LineDataSet(entryList, lable);
        lineDataSet.setColors(colors);
        lineDataSet.setCircleColors(colors);
        lineDataSet.setDrawCircleHole(false);


        LineData lineData = new LineData(lineDataSet);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("00.0%");
                return format.format(value);
            }
        });
        lineData.setValueTextColor(Color.parseColor("#333333"));
        lineData.setValueTextSize(12f);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0.5f);
        xAxis.setAxisMaximum(nameList.size() - 0.5f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(nameList));
        xAxis.setGranularity(0.5f);
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(12f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(5);
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat format = new DecimalFormat("00%");
                return format.format(value);
            }
        });
        yAxis.setTextColor(Color.parseColor("#333333"));
        yAxis.setTextSize(12f);

        YAxis yAxis1 = lineChart.getAxisRight();
        yAxis1.setEnabled(false);
        yAxis1.setAxisMinimum(0f);
        yAxis1.setLabelCount(5);


        lineChart.setData(lineData);
        lineChart.animateXY(0, 2000);
        lineChart.invalidate();
    }

}
