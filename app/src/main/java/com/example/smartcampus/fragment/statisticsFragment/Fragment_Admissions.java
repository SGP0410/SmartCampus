package com.example.smartcampus.fragment.statisticsFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.bean.statistics.GetMunicipal_query_all;
import com.example.smartcampus.bean.statistics.GetProvinceMenAndWomenNumberAll;
import com.example.smartcampus.bean.statistics.GetProvinceRecruitStudentNumber;
import com.example.smartcampus.utils.MapView;
import com.example.smartcampus.utils.destureviewbinder.GestureViewBinder;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.github.mikephil.charting.charts.BarChart;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class Fragment_Admissions extends BaseFragment {
    
    private TextView title;
    private MapView mapView;
    private LinearLayout groupView;
    private ProgressDialog progressDialog;
    private ImageView back;
    private LinearLayout btnDomestic;
    private ImageView imgDomestic;
    private TextView txtDomestic;
    private LinearLayout btnForeign;
    private ImageView imgForeign;
    private TextView txtForeign;
    private ViewFlipper viewFlipper;
    private TextView txtPlan;
    private TextView txtStudent;
    private BarChart barChartPileUp;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_admissions;
    }
    
    @Override
    protected void initView(View view) {
        ImageView back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        mapView = view.findViewById(R.id.map_view);
        groupView = view.findViewById(R.id.group_view);
        btnDomestic = view.findViewById(R.id.btn_domestic);
        imgDomestic = view.findViewById(R.id.img_domestic);
        txtDomestic = view.findViewById(R.id.txt_domestic);
        btnForeign = view.findViewById(R.id.btn_foreign);
        imgForeign = view.findViewById(R.id.img_foreign);
        txtForeign = view.findViewById(R.id.txt_foreign);
        imgForeign.setImageResource(R.mipmap.xs_2_hui);
        txtForeign.setTextColor(Color.parseColor("#414141"));
        imgDomestic.setImageResource(R.mipmap.xs_1_lan);
        txtDomestic.setTextColor(Color.parseColor("#386FE2"));
        viewFlipper = view.findViewById(R.id.view_flipper);
        txtStudent = view.findViewById(R.id.txt_student);
        txtPlan = view.findViewById(R.id.txt_plan);
        
        btnDomestic.setOnClickListener(v -> {
            imgForeign.setImageResource(R.mipmap.xs_2_hui);
            txtForeign.setTextColor(Color.parseColor("#414141"));
            imgDomestic.setImageResource(R.mipmap.xs_1_lan);
            txtDomestic.setTextColor(Color.parseColor("#386FE2"));
            showData("CH");
            barChart1.animateXY(0, 2000);
            viewFlipper.setDisplayedChild(0);
            txtStudent.setText("国内招生");
        });
        btnForeign.setOnClickListener(v -> {
            imgForeign.setImageResource(R.mipmap.xs_2_lan);
            txtForeign.setTextColor(Color.parseColor("#386FE2"));
            imgDomestic.setImageResource(R.mipmap.xs_1_hui);
            txtDomestic.setTextColor(Color.parseColor("#414141"));
            showData("GH");
            viewFlipper.setDisplayedChild(1);
            barChart2.animateXY(0, 2000);
            txtStudent.setText("留学生招生");
        });
        
        barChartPileUp = view.findViewById(R.id.barChart_pileUp);
    }
    
    @Override
    protected void initData() {
        barChart1 = new BarChart(getContext());
        barChart2 = new BarChart(getContext());
        
        title.setText("招生信息");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("提示");
        progressDialog.setMessage("网络请求中···");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        
        GestureViewBinder binder = GestureViewBinder.bind(getContext(), groupView, mapView);
        binder.setFullGroup(true);
        mapView.setOnMapViewClickListener(name -> {
            Log.i("aaaaa", "--------" + name);
            
            ((FragmentActivity) getActivity()).setFragment(new Fragment_admissions1(name, getMunicipal_query_alls,
                getProvinceRecruitStudentNumbers));
            
            RectF rectF = mapView.getRectF(name);
            initPopWindow((int) rectF.centerX(), (int) rectF.bottom);
        });
        
        getDate();
        
    }
    
    private List<String> strings = new ArrayList<>();
    
    private void getBarUp() {
        
        Collections.sort(getProvinceRecruitStudentNumbers, new Comparator<GetProvinceRecruitStudentNumber>() {
            @Override
            public int compare(GetProvinceRecruitStudentNumber o1, GetProvinceRecruitStudentNumber o2) {
                return o2.getEnrollStudentNum()-o1.getEnrollStudentNum();
            }
        });
        
        strings.clear();
        strings.add("");
        
        barChartPileUp.getDescription().setEnabled(false);
        //所有值均绘制在其条形顶部上方
        barChartPileUp.setDrawValueAboveBar(false);
        //纵坐标不显示网格线
        barChartPileUp.getXAxis().setDrawGridLines(false);
        //不支持图表缩放
        barChartPileUp.setScaleEnabled(false);
 
        //右y轴不显示
        barChartPileUp.getAxisRight().setEnabled(false);
    
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
    
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < getProvinceRecruitStudentNumbers.size(); i++) {
            GetProvinceRecruitStudentNumber getProvinceRecruitStudentNumber = getProvinceRecruitStudentNumbers.get(i);
            float val1 = (float) getProvinceRecruitStudentNumber.getEnrollStudentNum();
            float val2 = (float) getProvinceRecruitStudentNumber.getOverseasStudentNum();
            strings.add(getProvinceRecruitStudentNumber.getProvinceName().substring(0,3));
            yValues.add(new BarEntry(i+1, new float[]{val1, val2}));
        }
        
        xLabels.setValueFormatter(new IndexAxisValueFormatter(strings));
        xLabels.setLabelCount(strings.size());
        xLabels.setLabelRotationAngle(-90);
        xLabels.setTextSize(18);
    
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
                return format.format(value);
            });
            //设置图例的颜色和名称
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#E6746A"));
            colors.add(Color.parseColor("#FFB81C"));
        
            set1.setColors(colors);
        
            set1.setStackLabels(new String[]{"国内学生","留学生 "});
        
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            set1.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> "");
            dataSets.add(set1);
            
            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);
            barChartPileUp.setData(data);
        }
    
        YAxis yAxis = barChartPileUp.getAxisLeft();
    
        yAxis.setAxisMinimum(0);
        yAxis.setDrawAxisLine(false);                    //关闭Y轴网格线
    
        YAxis yAxis1 = barChartPileUp.getAxisRight();
        yAxis1.setAxisMinimum(0);
        
        Legend legend = barChartPileUp.getLegend();
        legend.setHorizontalAlignment(LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(LegendOrientation.HORIZONTAL);
        legend.setTextSize(14);
        legend.setFormSize(15);
        
        Matrix m = new Matrix();
        m.postScale(2.478f, 2f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
        barChartPileUp.getViewPortHandler().refresh(m, barChartPileUp, false);//将图表动画显示之前进行缩放
    
        barChartPileUp.animateXY(0, 2000);
        barChartPileUp.setFitBars(true);
        
        barChartPileUp.invalidate();
        
    }
    
    private List<GetProvinceRecruitStudentNumber> getProvinceRecruitStudentNumbers;
    private int plan;
    
    private void getDate() {
        if (getProvinceRecruitStudentNumbers == null) {
            getProvinceRecruitStudentNumbers = new ArrayList<>();
        } else {
            getProvinceRecruitStudentNumbers.clear();
        }
        new OkHttpTo()
            .setUrl("GetProvinceRecruitStudentNumber")
            .setRequestType("GET")
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    getProvinceRecruitStudentNumbers
                        .addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                            new TypeToken<List<GetProvinceRecruitStudentNumber>>() {
                            }.getType()));
                    
                    if (getProvinceRecruitStudentNumbers != null) {
                        getCity();
                    }
                }
                
                @Override
                public void onFailure(IOException e) {
                
                }
            }).start();
        
        
    }
    
    private List<GetMunicipal_query_all> getMunicipal_query_alls;
    
    private void getCity() {
        if (getMunicipal_query_alls == null) {
            getMunicipal_query_alls = new ArrayList<>();
        } else {
            getMunicipal_query_alls.clear();
        }
        new OkHttpTo()
            .setUrl("getMunicipal_query_all")
            .setRequestType("GET")
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    getMunicipal_query_alls.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                        new TypeToken<List<GetMunicipal_query_all>>() {
                        }.getType()));
                    
                    viewFlipper.addView(barChart1);
                    viewFlipper.addView(barChart2);
    
                    getBarUp();
                    
                    showData("CH");
                }
                
                @Override
                public void onFailure(IOException e) {
                
                }
            }).start();
        
        
    }
    
    private int count = 0;
    private static final String TAG = "Fragment_Admissions";
    
    private List<Integer> colorList1 = new ArrayList<>();
    
    
    private void showData(String ch) {
        
        if (ch.equals("CH")) {
            
            plan = 0;
            for (GetProvinceRecruitStudentNumber getProvinceRecruitStudentNumber : getProvinceRecruitStudentNumbers) {
                plan = plan + getProvinceRecruitStudentNumber.getEnrollStudentNum();
            }
            
            txtPlan.setText(plan + "人");
            
            colorList1.clear();
            Collections.sort(getProvinceRecruitStudentNumbers,
                (o1, o2) -> o2.getEnrollStudentNum() - o1.getEnrollStudentNum());
            
            setBarChart(ch);
            
            for (int i = 0; i < getProvinceRecruitStudentNumbers.size(); i++) {
                if (i == 0) {
                    colorList1.add(Color.parseColor("#A61F25"));
                } else if (i <= 10) {
                    colorList1.add(Color.parseColor("#D8232A"));
                } else if (i <= 20) {
                    colorList1.add(Color.parseColor("#DE4A4A"));
                } else if (i <= 30) {
                    colorList1.add(Color.parseColor("#E6746A"));
                } else {
                    colorList1.add(Color.parseColor("#ED9A92"));
                }
            }
        } else {
            plan = 0;
            for (GetProvinceRecruitStudentNumber getProvinceRecruitStudentNumber : getProvinceRecruitStudentNumbers) {
                plan = plan + getProvinceRecruitStudentNumber.getOverseasStudentNum();
            }
            txtPlan.setText(plan + "人");
            
            colorList1.clear();
            Collections.sort(getProvinceRecruitStudentNumbers,
                (o1, o2) -> o2.getOverseasStudentNum() - o1.getOverseasStudentNum());
            
            setBarChart(ch);
            
            for (int i = 0; i < getProvinceRecruitStudentNumbers.size(); i++) {
                if (i == 0) {
                    colorList1.add(Color.parseColor("#EC9F2D"));
                } else if (i <= 10) {
                    colorList1.add(Color.parseColor("#F4B452"));
                } else if (i <= 20) {
                    colorList1.add(Color.parseColor("#F5C178"));
                } else if (i <= 30) {
                    colorList1.add(Color.parseColor("#FAD79F"));
                } else {
                    colorList1.add(Color.parseColor("#FCE3C4"));
                }
            }
        }
        
        Map<String, Integer> colorMap = new HashMap<>();
        
        for (int i = 0; i < colorList1.size(); i++) {
            colorMap.put(getProvinceRecruitStudentNumbers.get(i).getProvinceName(), colorList1.get(i));
        }
        
        mapView.setColors(colorMap);
        mapView.setMapResId(R.raw.china);
        progressDialog.dismiss();
    }
    
    List<BarEntry> barEntryList = new ArrayList<>();
    BarDataSet barDataSet = null;
    BarData barData = null;
    List<String> names = new ArrayList<>();
    
    private BarChart barChart1;
    private BarChart barChart2;
    
    private void setBarChart(String ch) {
        
        barEntryList.clear();
        names.clear();
        if (ch.equals("CH")) {
            
            barChart1.getLegend().setEnabled(false);
            barChart1.setDescription(null);
            
            for (int i1 = 0; i1 < getProvinceRecruitStudentNumbers.size(); i1++) {
                barEntryList.add(new BarEntry(i1,
                    getProvinceRecruitStudentNumbers.get(i1).getEnrollStudentNum(),
                    getProvinceRecruitStudentNumbers.get(i1).getProvinceName()));
                names.add(getProvinceRecruitStudentNumbers.get(i1).getProvinceName());
            }
            barDataSet = new BarDataSet(barEntryList, "国内学生");
            barDataSet.setColors(colorList1);
            
            XAxis xAxis = barChart1.getXAxis();
            xAxis.setPosition(XAxisPosition.BOTTOM);
            xAxis.setLabelCount(getProvinceRecruitStudentNumbers.size());
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
            xAxis.setLabelCount(names.size());
            xAxis.setLabelRotationAngle(90);
            
            YAxis yAxis = barChart1.getAxisLeft();
            yAxis.setAxisMinimum(0f);
            yAxis.setLabelCount(10);
            
            YAxis yAxis1 = barChart1.getAxisRight();
            yAxis1.setEnabled(false);
            yAxis1.setAxisMinimum(0f);
            yAxis1.setLabelCount(10);
            
            barData = new BarData(barDataSet);
            barData.setBarWidth(0.5f);
            barData.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                    ViewPortHandler viewPortHandler) {
                    DecimalFormat format = new DecimalFormat("0");
                    return format.format(value);
                }
            });
            barChart1.animateXY(0, 2000);
            Matrix m = new Matrix();
            m.postScale(4f, 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
            barChart1.getViewPortHandler().refresh(m, barChart1, false);//将图表动画显示之前进行缩放
            barChart1.setData(barData);
            barChart1.invalidate();
            
        } else {
            
            Log.i(TAG, "setBarChart: HH");
            
            barChart2.getLegend().setEnabled(false);
            barChart2.setDescription(null);
            
            for (int i1 = 0; i1 < getProvinceRecruitStudentNumbers.size(); i1++) {
                barEntryList.add(new BarEntry(i1,
                    getProvinceRecruitStudentNumbers.get(i1).getOverseasStudentNum(),
                    getProvinceRecruitStudentNumbers.get(i1).getProvinceName()));
                names.add(getProvinceRecruitStudentNumbers.get(i1).getProvinceName());
            }
            barDataSet = new BarDataSet(barEntryList, "留学生");
            barDataSet.setColors(colorList1);
            
            XAxis xAxis = barChart2.getXAxis();
            xAxis.setPosition(XAxisPosition.BOTTOM);
            xAxis.setLabelCount(getProvinceRecruitStudentNumbers.size());
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
            xAxis.setLabelCount(names.size());
            xAxis.setLabelRotationAngle(90);
            
            YAxis yAxis = barChart2.getAxisLeft();
            yAxis.setAxisMinimum(0f);
            yAxis.setLabelCount(10);
            
            YAxis yAxis1 = barChart2.getAxisRight();
            yAxis1.setEnabled(false);
            yAxis1.setAxisMinimum(0f);
            yAxis1.setLabelCount(10);
            
            barData = new BarData(barDataSet);
            barData.setBarWidth(0.5f);
            barData.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                    ViewPortHandler viewPortHandler) {
                    DecimalFormat format = new DecimalFormat("0");
                    return format.format(value);
                }
            });
            barChart2.animateXY(0, 2000);
            
            Matrix m = new Matrix();
            m.postScale(4f, 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
            barChart2.getViewPortHandler().refresh(m, barChart2, false);//将图表动画显示之前进行缩放
            
            barChart2.setData(barData);
            barChart2.invalidate();
        }
        
    }
    
    @SuppressLint("NewApi")
    private void initPopWindow(int x, int y) {
        PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(500);
        popupWindow.setHeight(200);
        //设置布局
        popupWindow.showAsDropDown(groupView,
            (int) (x * mapView.getMScale()) - (popupWindow.getWidth() / 2),
            (int) (y * mapView.getMScale()) - groupView.getHeight(),
            Gravity.CENTER);
    }
    
    
    @Override
    public void onClick(View v) {
    
    }
    
}
