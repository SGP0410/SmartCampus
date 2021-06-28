package com.example.smartcampus.fragment.statisticsFragment;

import static com.example.smartcampus.R.id.spinner2;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.GradeSexExpenseSum;
import com.example.smartcampus.bean.statistics.ProvinceMunicipalExpenseSum;
import com.example.smartcampus.dialog.statistics.ChartViewDialog;
import com.example.smartcampus.utils.Utils;
import com.example.smartcampus.dialog.statistics.ChartViewDialog;
import com.example.smartcampus.utils.Utils;
import com.example.smartcampuslibrary.adapter.BaseSpinnerAdapter;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;

public class ConsumptionTrendFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.grid_view)
    GridView gridView;
    private List<GradeSexExpenseSum> gradeSexExpenseSumList;
    private List<ProvinceMunicipalExpenseSum> provinceMunicipalExpenseSumList;
    private ProgressDialog progressDialog;
    private LineChart lineChart;
    private BarChart barChart;
    private PieChart pieChart;
    private RadarChart radarChart;
    private List<Entry> entryList;
    private List<String> nameList;
    private String lable;
    private int[] colors;

    @Override
    protected int layoutResId() {
        return R.layout.consumption_trend_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        title.setText("消费趋势");
        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        progressDialog = Utils.showDialog(getContext());
        getGradeSexExpenseSumAll();
        getProvinceMunicipalExpenseSumAll();
    }

    private void dismissDialog() {
        if (provinceMunicipalExpenseSumList != null && gradeSexExpenseSumList != null) {
            progressDialog.dismiss();
            showSpinner();
            setView();
            setViewData(spinner1.getSelectedItem().toString());
        }
    }

    private void setViewData(String name) {
        lable = name;
        switch (name) {
            case "各年级消费趋势":
                setConsumptionTrendsOfAllGradesData();
                break;
            case "年级消费总计":
                setTotalGradeConsumptionData();
                break;
            case "性别消费总计":
                setTotalGenderConsumptionData();
                break;
            case "省份消费总计":
                setTotalProvinceConsumptionData();
                break;
            default:
                setTotalConsumptionInEachProvinceData();
        }
    }

    //各省消费趋势
    private void setTotalConsumptionInEachProvinceData() {
        entryList = new ArrayList<>();
        nameList = new ArrayList<>();
        nameList.add("");

        for (ProvinceMunicipalExpenseSum provinceMunicipalExpenseSum : provinceMunicipalExpenseSumList) {
            if (provinceMunicipalExpenseSum.getProvinceName().equals(lable)) {
                List<ProvinceMunicipalExpenseSum.MunicipalExpenditureBean>
                        municipalExpenditure = provinceMunicipalExpenseSum.getMunicipalExpenditure();
                for (int i = 0; i < municipalExpenditure.size(); i++) {
                    entryList.add(new Entry(i + 1, municipalExpenditure.get(i).getExpenditure(),
                            municipalExpenditure.get(i).getMunicipalName()));
                    nameList.add(municipalExpenditure.get(i).getMunicipalName());
                }
                break;
            }
        }

        colors = new int[]{
                Color.parseColor("#ddcd21"),
                Color.parseColor("#009956"),
                Color.parseColor("#00a09b")
        };

        setLineChart3();
        setBarChart3();
        setPieChart3();
        setRadarChart3();

    }

    //省份消费总计
    private void setTotalProvinceConsumptionData() {
        entryList = new ArrayList<>();
        nameList = new ArrayList<>();
        nameList.add("");

        for (int i = 0; i < provinceMunicipalExpenseSumList.size(); i++) {
            int sum = 0;
            for (ProvinceMunicipalExpenseSum.MunicipalExpenditureBean municipalExpenditureBean :
                    provinceMunicipalExpenseSumList.get(i).getMunicipalExpenditure()) {
                sum += municipalExpenditureBean.getExpenditure();
            }
            entryList.add(new Entry(i + 1, sum, provinceMunicipalExpenseSumList.get(i).getProvinceName()));
            nameList.add(provinceMunicipalExpenseSumList.get(i).getProvinceName());
        }

        colors = new int[]{
                Color.parseColor("#ddcd21"),
                Color.parseColor("#009956"),
                Color.parseColor("#00a09b")
        };

        setLineChart3();
        setBarChart3();
        setPieChart3();
        setRadarChart3();

    }

    private void setRadarChart3() {
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

    private void setPieChart3() {
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
                DecimalFormat format = new DecimalFormat("00");
                return format.format(value) + "(" + entry.getData() + ")";
            }
        });


        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.animateXY(0, 2000);
        pieChart.invalidate();
    }

    private void setBarChart3() {
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
                DecimalFormat format = new DecimalFormat("00");
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
                DecimalFormat format = new DecimalFormat("0");
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

    private void setLineChart3() {
        lineChart.setDescription(null);
        lineChart.setTouchEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);

        LineDataSet lineDataSet = new LineDataSet(entryList, lable);
        lineDataSet.setColors(colors);
        lineDataSet.setCircleColors(colors);
        lineDataSet.setDrawCircleHole(false);


        LineData lineData = new LineData(lineDataSet);
        lineData.setDrawValues(false);
//        lineData.setValueFormatter(new IValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//                DecimalFormat format = new DecimalFormat("0");
//                return format.format(value);
//            }
//        });
        lineData.setValueTextColor(Color.parseColor("#333333"));
        lineData.setValueTextSize(12f);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0.5f);
        xAxis.setAxisMaximum(nameList.size() - 0.5f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(nameList));
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return value+"";
//            }
//        });
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(5f);
//        xAxis.setLabelRotationAngle(90);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(5);
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat format = new DecimalFormat("0");
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


    //性别消费总计
    private void setTotalGenderConsumptionData() {
        entryList = new ArrayList<>();
        nameList = new ArrayList<>();
        nameList.add("");

        int man = 0;
        int woman = 0;

        for (GradeSexExpenseSum gradeSexExpenseSum : gradeSexExpenseSumList) {
            man += gradeSexExpenseSum.getManExpenditure();
            woman += gradeSexExpenseSum.getWomanExpenditure();
        }

        entryList.add(new Entry(1, man, "男"));
        entryList.add(new Entry(2, woman, "女"));
        nameList.add("男");
        nameList.add("女");

        colors = new int[]{
                Color.parseColor("#ddcd21"),
                Color.parseColor("#009956")
        };

        setLineChart2();
        setBarChart2();
        setPieChart2();
        setRadarChart2();

    }

    //年级消费总计
    private void setTotalGradeConsumptionData() {
        entryList = new ArrayList<>();
        nameList = new ArrayList<>();
        nameList.add("");

        for (int i = 0; i < gradeSexExpenseSumList.size(); i++) {
            entryList.add(new Entry(i + 1, gradeSexExpenseSumList.get(i).getManExpenditure() +
                    gradeSexExpenseSumList.get(i).getWomanExpenditure(), gradeSexExpenseSumList.get(i).getGrade()));
            nameList.add(gradeSexExpenseSumList.get(i).getGrade());
        }

        colors = new int[]{
                Color.parseColor("#ddcd21"),
                Color.parseColor("#009956"),
                Color.parseColor("#00a09b")
        };

        setLineChart2();
        setBarChart2();
        setPieChart2();
        setRadarChart2();

    }

    private void setRadarChart2() {
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

    private void setPieChart2() {
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
                DecimalFormat format = new DecimalFormat("00");
                return format.format(value) + "(" + entry.getData() + ")";
            }
        });


        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.animateXY(0, 2000);
        pieChart.invalidate();
    }

    private void setBarChart2() {
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
                DecimalFormat format = new DecimalFormat("00");
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
                DecimalFormat format = new DecimalFormat("0");
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

    private void setLineChart2() {
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
                DecimalFormat format = new DecimalFormat("0");
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
                DecimalFormat format = new DecimalFormat("0");
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


    //各年级消费趋势
    private void setConsumptionTrendsOfAllGradesData() {
        entryList = new ArrayList<>();
        nameList = new ArrayList<>();
        nameList.add("");
        int he = 0;
        for (GradeSexExpenseSum gradeSexExpenseSum : gradeSexExpenseSumList) {
            he += gradeSexExpenseSum.getManExpenditure();
            he += gradeSexExpenseSum.getWomanExpenditure();
        }

        for (int i = 0; i < gradeSexExpenseSumList.size(); i++) {
            entryList.add(new Entry(i + 1,
                    (gradeSexExpenseSumList.get(i).getManExpenditure() + gradeSexExpenseSumList.get(i)
                            .getWomanExpenditure()) / (float) he,
                    gradeSexExpenseSumList.get(i).getGrade()
            ));
            nameList.add(gradeSexExpenseSumList.get(i).getGrade());
        }

        colors = new int[]{
                Color.parseColor("#ddcd21"),
                Color.parseColor("#009956"),
                Color.parseColor("#00a09b")
        };

        setLineChart1();
        setBarChart1();
        setPieChart1();
        setRadarChart1();
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

    private void showSpinner() {
        String[] strings1 = {"各年级消费趋势", "年级消费总计", "性别消费总计", "省份消费总计"};
        spinner1.setVisibility(View.VISIBLE);
        spinner1.setAdapter(new BaseSpinnerAdapter(getContext(), Arrays.asList(strings1)) {
            @Override
            protected int setBackground() {
                return 0;
            }
        });
        List<String> strings2 = new ArrayList<>();
        strings2.add("-----各省份-----");
        for (ProvinceMunicipalExpenseSum provinceMunicipalExpenseSum : provinceMunicipalExpenseSumList) {
            strings2.add(provinceMunicipalExpenseSum.getProvinceName());
        }
        spinner2.setAdapter(new BaseSpinnerAdapter(getContext(), strings2) {
            @Override
            protected int setBackground() {
                return 0;
            }
        });
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner1.getSelectedItem().toString().equals("省份消费总计")) {
                    spinner2.setVisibility(View.VISIBLE);
                } else {
                    spinner2.setVisibility(View.GONE);
                }
                setViewData(spinner1.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinner2.getSelectedItem().toString().equals("-----各省份-----")) {
                    setViewData(spinner2.getSelectedItem().toString());
                }else {
                    if (!lable.equals("省份消费总计")){
                        setViewData("省份消费总计");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setView() {
        int height = (gridView.getHeight() - 120) / 2;
        int width = (gridView.getWidth() - 100) / 2;
        List<View> viewList = new ArrayList<>();
        lineChart = new LineChart(getContext());
        lineChart.setLayoutParams(new LayoutParams(width, height));
        viewList.add(lineChart);
        barChart = new BarChart(getContext());
        barChart.setLayoutParams(new LayoutParams(width, height));
        viewList.add(barChart);
        pieChart = new PieChart(getContext());
        pieChart.setLayoutParams(new LayoutParams(width, height));
        viewList.add(pieChart);
        radarChart = new RadarChart(getContext());
        radarChart.setLayoutParams(new LayoutParams(width, height));
        viewList.add(radarChart);

        for (View view : viewList) {
            view.setBackgroundColor(Color.parseColor("#FDFDFE"));
            view.setEnabled(false);
        }

        gridView.setNumColumns(2);
        gridView.setVerticalSpacing(30);
        gridView.setHorizontalSpacing(30);
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return viewList.get(position);
            }
        });
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("aaaa", "---------------" + position);
                assert getFragmentManager() != null;
                new ChartViewDialog(entryList , nameList , colors , position , lable).show(getFragmentManager(),
                        ChartViewDialog.class.getName());
            }
        });
    }

    private void getProvinceMunicipalExpenseSumAll() {
        new OkHttpTo().setUrl("GetProvinceMunicipalExpenseSumAll")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        provinceMunicipalExpenseSumList = new Gson()
                                .fromJson(jsonObject.optJSONArray("rows").toString(),
                                        new TypeToken<List<ProvinceMunicipalExpenseSum>>() {
                                        }.getType());
                        if (provinceMunicipalExpenseSumList != null) {
                            dismissDialog();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }


    private void getGradeSexExpenseSumAll() {
        new OkHttpTo().setUrl("GetGradeSexExpenseSumAll")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        gradeSexExpenseSumList = new Gson()
                                .fromJson(jsonObject.optJSONArray("rows").toString(),
                                        new TypeToken<List<GradeSexExpenseSum>>() {
                                        }.getType());
                        if (gradeSexExpenseSumList != null) {
                            dismissDialog();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    @Override
    public void onClick(View v) {

    }
}
