package com.example.smartcampus.fragment.statisticsFragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.GetCollegeMenAndWomenNumberAll;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
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
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class Fragment_count_student extends BaseFragment {
    
    private ImageView back;
    private TextView title;
    private BarChart barChart;
    private LineChart lineChart;
    private BarChart barChartPileUp;
    private PieChart chart;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_count_student;
    }
    
    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        barChart = view.findViewById(R.id.barChart);
        lineChart = view.findViewById(R.id.lineChart);
        barChartPileUp = view.findViewById(R.id.barChart_pileUp);
        chart = view.findViewById(R.id.chart1);
    }
    
    @Override
    protected void initData() {
        title.setText("????????????");
        getOkHttp();
    }
    
    private List<GetCollegeMenAndWomenNumberAll> getCollegeMenAndWomenNumberAlls;
    
    private void getOkHttp() {
        if (getCollegeMenAndWomenNumberAlls == null) {
            getCollegeMenAndWomenNumberAlls = new ArrayList<>();
        } else {
            getCollegeMenAndWomenNumberAlls.clear();
        }
        new OkHttpTo()
            .setUrl("GetCollegeMenAndWomenNumberAll")
            .setRequestType("GET")
            .setDialog(getContext())
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    getCollegeMenAndWomenNumberAlls
                        .addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                            new TypeToken<List<GetCollegeMenAndWomenNumberAll>>() {
                            }.getType()));
                    getShow();
                }
                
                @Override
                public void onFailure(IOException e) {
                
                }
            }).start();
    }
    
    private void getShow() {
        getBarChart();
        getLineChart();
        getBarChart_PileUp();
        getPieChart1();
    }
    
    private void getPieChart1() {
        
        ArrayList<PieEntry> entries = new ArrayList<>();
        
        for (int i = 0; i < getCollegeMenAndWomenNumberAlls.size(); i++) {
            int s = getCollegeMenAndWomenNumberAlls.get(i).getMan() + getCollegeMenAndWomenNumberAlls.get(i)
                .getWoman();
            entries.add(new PieEntry((float) (s), getCollegeMenAndWomenNumberAlls.get(i).getCollegeName()));
        }
        
        
        
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#3CBBFF"));
        colors.add(Color.parseColor("#FFB81C"));
        colors.add(Color.parseColor("#3AEFC3"));
        colors.add(Color.parseColor("#FFA9B3"));
        colors.add(Color.parseColor("#A139FD"));
        colors.add(Color.parseColor("#4DCCED"));
        colors.add(Color.parseColor("#25B576"));
        colors.add(Color.parseColor("#84CBFA"));
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
        
        Legend l = chart.getLegend();
        l.setVerticalAlignment(LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
        chart.animateXY(0, 2000);
        chart.invalidate();
    }
    
    private void getBarChart_PileUp() {
        barChartPileUp.getDescription().setEnabled(false);        //???????????????????????????
        //??????????????????????????????????????????
        barChartPileUp.setDrawValueAboveBar(false);
        //???????????????????????????
        barChartPileUp.getXAxis().setDrawGridLines(false);
        //?????????????????????
        barChartPileUp.setScaleEnabled(false);
        //??????????????????????????????????????????????????????
        
        // ??????y???????????????
        
        YAxis leftAxis = barChartPileUp.getAxisLeft();
        leftAxis.setAxisMinimum(0);
        
        leftAxis.setDrawAxisLine(false);
        //???y????????????
        barChartPileUp.getAxisRight().setEnabled(false);
        
        //??????x?????????
        XAxis xLabels = barChartPileUp.getXAxis();
        //??????x??????????????????????????????
        xLabels.setPosition(XAxisPosition.BOTTOM);
        //??????x?????????????????????
        xLabels.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });
        
        ArrayList<BarEntry> yValues = new ArrayList<>();
        
        for (int i = 0; i < 1; i++) {
            float val1 =
                (float) getCollegeMenAndWomenNumberAlls.get(0).getWoman() + getCollegeMenAndWomenNumberAlls.get(0)
                    .getMan();
            float val2 =
                (float) getCollegeMenAndWomenNumberAlls.get(1).getWoman() + getCollegeMenAndWomenNumberAlls.get(1)
                    .getMan();
            float val3 =
                (float) getCollegeMenAndWomenNumberAlls.get(2).getWoman() + getCollegeMenAndWomenNumberAlls.get(2)
                    .getMan();
            float val4 =
                (float) getCollegeMenAndWomenNumberAlls.get(3).getWoman() + getCollegeMenAndWomenNumberAlls.get(3)
                    .getMan();
            float val5 =
                (float) getCollegeMenAndWomenNumberAlls.get(4).getWoman() + getCollegeMenAndWomenNumberAlls.get(4)
                    .getMan();
            float val6 =
                (float) getCollegeMenAndWomenNumberAlls.get(5).getWoman() + getCollegeMenAndWomenNumberAlls.get(5)
                    .getMan();
            float val7 =
                (float) getCollegeMenAndWomenNumberAlls.get(6).getWoman() + getCollegeMenAndWomenNumberAlls.get(6)
                    .getMan();
            float val8 =
                (float) getCollegeMenAndWomenNumberAlls.get(7).getWoman() + getCollegeMenAndWomenNumberAlls.get(7)
                    .getMan();
            
            yValues.add(new BarEntry(i, new float[]{val1, val2, val3, val4, val5, val6, val7, val8}));
        }
        
        BarDataSet set1;
        
        if (barChartPileUp.getData() != null && barChartPileUp.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChartPileUp.getData().getDataSetByIndex(0);
            set1.setValues(yValues);
            barChartPileUp.getData().notifyDataChanged();
            barChartPileUp.notifyDataSetChanged();
            
            Log.i(TAG, "getBarChart_PileUp: ");
        } else {
            set1 = new BarDataSet(yValues, "");
            set1.setValueTextSize(20);
            set1.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
                DecimalFormat format = new DecimalFormat("0");
                return format.format(value);
            });
            //??????????????????????????????
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#3CBBFF"));
            colors.add(Color.parseColor("#FFB81C"));
            colors.add(Color.parseColor("#3AEFC3"));
            colors.add(Color.parseColor("#FFA9B3"));
            colors.add(Color.parseColor("#A139FD"));
            colors.add(Color.parseColor("#4DCCED"));
            colors.add(Color.parseColor("#25B576"));
            colors.add(Color.parseColor("#84CBFA"));
            
            set1.setColors(colors);
            
            set1.setStackLabels(new String[]{getCollegeMenAndWomenNumberAlls.get(0).getCollegeName().substring(0
                , 3),
                getCollegeMenAndWomenNumberAlls.get(1).getCollegeName().substring(0, 3),
                getCollegeMenAndWomenNumberAlls.get(2).getCollegeName().substring(0, 3),
                getCollegeMenAndWomenNumberAlls.get(3).getCollegeName().substring(0, 3),
                getCollegeMenAndWomenNumberAlls.get(4).getCollegeName().substring(0, 3),
                getCollegeMenAndWomenNumberAlls.get(5).getCollegeName().substring(0, 3),
                getCollegeMenAndWomenNumberAlls.get(6).getCollegeName().substring(0, 3),
                getCollegeMenAndWomenNumberAlls.get(7).getCollegeName().substring(0, 3),
            });
            
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);
            barChartPileUp.setData(data);
        }
        
        YAxis yAxis = barChartPileUp.getAxisLeft();
        
        yAxis.setMinWidth(0);
        yAxis.setDrawAxisLine(false);                    //??????Y????????????
    
        Legend legend = barChartPileUp.getLegend();
        legend.setHorizontalAlignment(LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(LegendOrientation.HORIZONTAL);
   
    
        barChartPileUp.animateXY(0, 2000);
        barChartPileUp.setFitBars(true);
        barChartPileUp.setTouchEnabled(false);                   //????????????
        barChartPileUp.invalidate();
        
        
    }
    
    private static final String TAG = "Fragment_count_student";
    
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
        
        for (int i = 0; i < getCollegeMenAndWomenNumberAlls.size(); i++) {
            int s = getCollegeMenAndWomenNumberAlls.get(i).getMan() + getCollegeMenAndWomenNumberAlls.get(i)
                .getWoman();
            entries.add(new BarEntry(i, s));
            String string = getCollegeMenAndWomenNumberAlls.get(i).getCollegeName().substring(0, 3);
            stringLine.add(string);
        }
        
        LineDataSet dataSet = new LineDataSet(entries, "");  //???????????????
        
        dataSet.setCircleColor(Color.parseColor("#F44848"));                 //??????????????????
        dataSet.setCircleHoleRadius(18f);                    //????????????
        dataSet.setColor(Color.parseColor("#20D5CE"));                       //??????????????????
        //??????????????????
        dataSet.setValueTextSize(20);                       //??????????????????
        dataSet.setLineWidth(4f);                           //????????????
        dataSet.setDrawValues(false);                       //????????????????????????
        dataSet.setDrawCircleHole(false);                   //?????????????????????????????????
        dataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
            DecimalFormat format = new DecimalFormat("0");
            return format.format(value) + "%";
        });
        
        LineData lineData = new LineData(dataSet);
        
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);                    //??????Y????????????
        yAxis.setAxisMinimum(100);                        //?????????
        
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);      //X??????????????????
        xAxis.setValueFormatter(new IndexAxisValueFormatter(stringLine));    //??????????????????????????????X???
        xAxis.setLabelCount(stringLine.size());            //????????????
        xAxis.setGranularity(1f);
        xAxis.setTextSize(15);
        xAxis.setLabelRotationAngle(-35);
        //????????????????????????
        xAxis.setDrawGridLines(false);                    //???????????????
        
        lineChart.setData(lineData);
        lineChart.getLegend().setEnabled(false);
        lineChart.animateXY(1500, 1000);                    //????????????
        lineChart.getDescription().setEnabled(false);       //????????????
        lineChart.setTouchEnabled(false);                   //????????????
        lineChart.invalidate();                             //??????
        
    }
    
    private List<BarEntry> barEntries;
    private List<String> string_Bar;
    
    private void getBarChart() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            string_Bar = new ArrayList<>();
        } else {
            string_Bar.clear();
            barEntries.clear();
        }
        
        for (int i = 0; i < getCollegeMenAndWomenNumberAlls.size(); i++) {
            int s = getCollegeMenAndWomenNumberAlls.get(i).getMan() + getCollegeMenAndWomenNumberAlls.get(i)
                .getWoman();
            barEntries.add(new BarEntry(i, s));
            String string = getCollegeMenAndWomenNumberAlls.get(i).getCollegeName().substring(0, 5);
            string_Bar.add(string);
        }
        
        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColor(Color.parseColor("#20D5CE"));
        BarData data = new BarData(barDataSet);
        barDataSet.setValueTextSize(18);            //????????????
        barDataSet.setValueTextColor(Color.parseColor("#404040"));
        barDataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
            DecimalFormat format = new DecimalFormat("0");
            return format.format(value);
        });
        
        data.setBarWidth(0.5f);
        
        barChart.getLegend().setEnabled(false);
        
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);                    //??????Y????????????
        yAxis.setAxisMinimum(0);                        //?????????
        
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);    //X??????????????????
        xAxis.setValueFormatter(new IndexAxisValueFormatter(string_Bar));    //??????????????????????????????X???
        xAxis.setLabelCount(string_Bar.size());            //????????????
        xAxis.setGranularity(1f);
        xAxis.setTextSize(15);
        //????????????????????????
        xAxis.setDrawGridLines(false);                    //???????????????
        
        barDataSet.setColors(Color.parseColor("#22D5CE"), Color.parseColor("#F39444"));
        
        barChart.animateXY(0, 2000);
        barChart.setFitBars(true);
        barChart.getAxisRight().setEnabled(false);         //????????????????????????
        barChart.setData(data);                            //????????????
        barChart.setTouchEnabled(false);                   //????????????
        barChart.getDescription().setEnabled(false);       //???????????????
        barChart.invalidate();
        
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
