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
        title.setText("????????????");
        txtProvince.setText(getProvinceMenAndWomenNumberAll.getProvinceName());
        double sum = getProvinceMenAndWomenNumberAll.getMan() + getProvinceMenAndWomenNumberAll.getWoman();
        double nv = getProvinceMenAndWomenNumberAll.getWoman();
        double woman = nv / sum;
        woman1 = (int) (woman * 100);
        man1 = 100 - woman1;
        itemWoman.setText(woman1 + "%");
        itemMan.setText(man1 + "%");
        
        getBarChart();  //?????????
        
        getLineChart();
        
        getBarChart_PileUp();
        
        getPieChart1();//?????????
        
        getPieChart();
        
    }
    
    private void getPieChart1() {
        
        ArrayList<PieEntry> entries = new ArrayList<>();
    
        entries.add(new PieEntry((float) (man1), "???"));
        entries.add(new PieEntry((float) (woman1), "???"));
    
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
        
        barChartPileUp.getDescription().setEnabled(false);        //???????????????????????????
        //??????????????????????????????????????????
        barChartPileUp.setDrawValueAboveBar(false);
        //???????????????????????????
        barChartPileUp.getXAxis().setDrawGridLines(false);
        //?????????????????????
        barChartPileUp.setScaleEnabled(false);
        //??????????????????????????????????????????????????????
//        barChartPileUp.setMarker(new MyMarkView(context);
        
        // ??????y???????????????
        YAxis leftAxis = barChartPileUp.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        //???y????????????
        barChartPileUp.getAxisRight().setEnabled(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setMaxWidth(100);
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
        
        //?????????????????????
        Legend l = barChartPileUp.getLegend();
        //?????????????????????
        l.setVerticalAlignment(LegendVerticalAlignment.BOTTOM);
        //?????????????????????
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
            //??????????????????????????????
            set1.setColors(new int[]{R.color.drawing_nv, R.color.drawing_man}, getContext());
            set1.setStackLabels(new String[]{"???", "???"});
            
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            
            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);
            
            barChartPileUp.setData(data);
        }
        barChartPileUp.animateXY(0, 2000);
        barChartPileUp.setFitBars(true);
        barChartPileUp.setTouchEnabled(false);                   //????????????
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
        stringLine.add("???");
        stringLine.add("???");
        
        LineDataSet dataSet = new LineDataSet(entries, "");  //???????????????
        
        dataSet.setCircleColor(Color.parseColor("#F39445"));                 //??????????????????
        dataSet.setCircleHoleRadius(3f);                    //????????????
        dataSet.setColor(Color.parseColor("#20D5CE"));                       //??????????????????
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
        yAxis.setAxisMinimum(0);                        //?????????
        yAxis.setAxisMaximum(100);                        //?????????
        
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);    //X??????????????????
        xAxis.setValueFormatter(new IndexAxisValueFormatter(stringSex));    //??????????????????????????????X???
        xAxis.setLabelCount(stringSex.size());            //????????????
        xAxis.setGranularity(1f);
        xAxis.setTextSize(20);
        //????????????????????????
        xAxis.setDrawGridLines(false);                    //???????????????
        
        lineChart.setData(lineData);
        lineChart.getLegend().setEnabled(false);
        lineChart.animateXY(1500, 1000);                    //????????????
        lineChart.getDescription().setEnabled(false);       //????????????
        lineChart.getDescription().setText("(S)");          //?????????????????????
        lineChart.setScaleEnabled(false);                   //??????????????????
        lineChart.setTouchEnabled(false);                   //????????????
        lineChart.invalidate();                             //??????
        
        
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
        
        stringSex.add("???");
        stringSex.add("???");
        
        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        
        barDataSet.setColor(Color.parseColor("#20D5CE"));
        BarData data = new BarData(barDataSet);
        barDataSet.setValueTextSize(18);            //????????????
        barDataSet.setValueTextColor(Color.parseColor("#404040"));
        barDataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
            DecimalFormat format = new DecimalFormat("0");
            return format.format(value) + "%";
        });
        data.setBarWidth(0.5f);
        
        barchart.getLegend().setEnabled(false);
        
        YAxis yAxis = barchart.getAxisLeft();
        yAxis.setDrawAxisLine(false);                    //??????Y????????????
        yAxis.setAxisMinimum(0);                        //?????????
        yAxis.setAxisMaximum(100);                        //?????????
        
        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);    //X??????????????????
        xAxis.setValueFormatter(new IndexAxisValueFormatter(stringSex));    //??????????????????????????????X???
        xAxis.setLabelCount(stringSex.size());            //????????????
        xAxis.setGranularity(1f);
        xAxis.setTextSize(20);
        //????????????????????????
        xAxis.setDrawGridLines(false);                    //???????????????
        
        barDataSet.setColors(Color.parseColor("#22D5CE"), Color.parseColor("#F39444"));
        
        barchart.animateXY(0, 2000);
        barchart.setFitBars(true);
        barchart.getAxisRight().setEnabled(false);         //????????????????????????
        barchart.setData(data);                            //????????????
        barchart.setTouchEnabled(false);                   //????????????
        barchart.getDescription().setEnabled(false);       //???????????????
        barchart.invalidate();
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
