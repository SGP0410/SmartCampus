package com.example.smartcampus.fragment.statisticsFragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.GetMunicipal_query_all;
import com.example.smartcampus.bean.statistics.GetProvinceRecruitStudentNumber;
import com.example.smartcampus.utils.MapView;
import com.example.smartcampus.utils.destureviewbinder.GestureViewBinder;
import com.example.smartcampuslibrary.fragment.BaseFragment;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_admissions1 extends BaseFragment {
    
    private ImageView back;
    private TextView title;
    private LinearLayout groupView;
    private MapView mapView;
    private BarChart barChartPileUp;
    private TextView txtStudent;
    private TextView txtPlan;
    private LinearLayout btnDomestic;
    private ImageView imgDomestic;
    private TextView txtDomestic;
    private LinearLayout btnForeign;
    private ImageView imgForeign;
    private TextView txtForeign;
    private List<GetMunicipal_query_all> getMunicipal_query_alls, list;
    private String name;
    private List<GetProvinceRecruitStudentNumber> getProvinceRecruitStudentNumbers;
    private String nameId;
    private Map<String, Integer> colorMap;
    
    public Fragment_admissions1(String name, List<GetMunicipal_query_all> getMunicipal_query_alls,
        List<GetProvinceRecruitStudentNumber> getProvinceRecruitStudentNumbers) {
        this.name = name;
        this.getMunicipal_query_alls = getMunicipal_query_alls;
        this.getProvinceRecruitStudentNumbers = getProvinceRecruitStudentNumbers;
    }
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_admissions1;
    }
    
    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        groupView = view.findViewById(R.id.group_view);
        mapView = view.findViewById(R.id.map_view);
        barChartPileUp = view.findViewById(R.id.barChart_pileUp);
        txtStudent = view.findViewById(R.id.txt_student);
        txtPlan = view.findViewById(R.id.txt_plan);
        btnDomestic = view.findViewById(R.id.btn_domestic);
        imgDomestic = view.findViewById(R.id.img_domestic);
        txtDomestic = view.findViewById(R.id.txt_domestic);
        btnForeign = view.findViewById(R.id.btn_foreign);
        imgForeign = view.findViewById(R.id.img_foreign);
        txtForeign = view.findViewById(R.id.txt_foreign);
    
    
        btnDomestic.setOnClickListener(v -> {
            imgForeign.setImageResource(R.mipmap.xs_2_hui);
            txtForeign.setTextColor(Color.parseColor("#414141"));
            imgDomestic.setImageResource(R.mipmap.xs_1_lan);
            txtDomestic.setTextColor(Color.parseColor("#386FE2"));
            getData("CH");
            setMap("CH");
            txtStudent.setText("????????????");
        });
        btnForeign.setOnClickListener(v -> {
            imgForeign.setImageResource(R.mipmap.xs_2_lan);
            txtForeign.setTextColor(Color.parseColor("#386FE2"));
            imgDomestic.setImageResource(R.mipmap.xs_1_hui);
            txtDomestic.setTextColor(Color.parseColor("#414141"));
            getData("GH");
            setMap("GH");
            txtStudent.setText("???????????????");
        });
        
        
        
    }
    
    @Override
    protected void initData() {
        GestureViewBinder binder = GestureViewBinder.bind(getContext(), groupView, mapView);
        binder.setFullGroup(true);
        getData("CH");
        setMap("CH");
        title.setText(name);
    }
    
    private List<Integer> colorList1 = new ArrayList<>();
    private List<Integer> colorList2 = new ArrayList<>();
    
    
    private static final String TAG = "Fragment_admissions1";
    
    private void getData(String ch) {
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        
        nameId = "";
        for (GetProvinceRecruitStudentNumber getProvinceRecruitStudentNumber : getProvinceRecruitStudentNumbers) {
            if (getProvinceRecruitStudentNumber.getProvinceName().equals(name)) {
                nameId = getProvinceRecruitStudentNumber.getProvinceId();
            }
        }
        
        for (GetMunicipal_query_all getMunicipal_query_all : getMunicipal_query_alls) {
            if (getMunicipal_query_all.getProvinceId().equals(nameId)) {
                list.add(getMunicipal_query_all);
            }
        }
        
        if (ch.equals("CH")){
            Collections.sort(list, new Comparator<GetMunicipal_query_all>() {
                @Override
                public int compare(GetMunicipal_query_all o1, GetMunicipal_query_all o2) {
                    return o2.getEnrollStudentNum() - o1.getEnrollStudentNum();
                }
            });
        }else {
            Collections.sort(list, new Comparator<GetMunicipal_query_all>() {
                @Override
                public int compare(GetMunicipal_query_all o1, GetMunicipal_query_all o2) {
                    return o2.getOverseasStudentNum() - o1.getOverseasStudentNum();
                }
            });
        }
        
        
        List<String> strings = new ArrayList<>();
        ArrayList<BarEntry> yValues = new ArrayList<>();
        strings.add("");
        for (int i = 0; i < list.size(); i++) {
            GetMunicipal_query_all getProvinceRecruitStudentNumber = list.get(i);
            float val1 = (float) getProvinceRecruitStudentNumber.getEnrollStudentNum();
            float val2 = (float) getProvinceRecruitStudentNumber.getOverseasStudentNum();
            String s = getProvinceRecruitStudentNumber.getMunicipalName();
            
            switch (s){
                case "????????????????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "??????????????????":
                    s = "????????????";
                    break;
                case "??????????????????????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "??????????????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "???????????????????????????":
                    s = "????????????";
                    break;
                case "???????????????????????????":
                    s = "????????????";
                    break;
                case "???????????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????????????????":
                    s = "???????????????";
                    break;
                case "??????????????????????????????":
                    s = "???????????????";
                    break;
                case "??????????????????????????????":
                    s = "???????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "??????????????????????????????":
                    s = "???????????????";
                    break;
                case "???????????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "???????????????????????????":
                    s = "???????????????";
                    break;
                case "??????????????????????????????":
                    s = "????????????";
                    break;
                case "????????????????????????":
                    s = "???????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "??????????????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "?????????????????????":
                    s = "????????????";
                    break;
                case "???????????????????????????":
                    s = "??????????????????";
                    break;
                case "???????????????????????????":
                    s = "??????????????????";
                    break;
                case "???????????????":
                    s = "?????????";
                    break;
                case "?????????????????????????????????":
                    s = "??????????????????";
                    break;
                case "???????????????":
                    s = "?????????";
                    break;
                case "????????????????????????":
                    s = "???????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "???????????????":
                    s = "????????????";
                    break;
                case "????????????????????????":
                    s = "???????????????";
                    break;
                case "??????????????????????????????":
                    s = "?????????????????????";
                    break;
                case "??????????????????????????????":
                    s = "?????????????????????";
                    break;
                case "??????????????????????????????":
                    s = "?????????????????????";
                    break;
            }
            
            strings.add(s);
            yValues.add(new BarEntry(i + 1, new float[]{val1, val2}));
            
        }
    
        
        
        barChartPileUp.getDescription().setEnabled(false);
        //??????????????????????????????????????????
        barChartPileUp.setDrawValueAboveBar(false);
        //???????????????????????????
        barChartPileUp.getXAxis().setDrawGridLines(false);
        //?????????????????????
        barChartPileUp.setScaleEnabled(false);
        
        //???y????????????
        barChartPileUp.getAxisRight().setEnabled(false);
        
        //??????x?????????
        XAxis xLabels = barChartPileUp.getXAxis();
        //??????x??????????????????????????????
        xLabels.setPosition(XAxisPosition.BOTTOM);
        xLabels.setValueFormatter(new IndexAxisValueFormatter(strings));
        xLabels.setLabelCount(strings.size());
        xLabels.setLabelRotationAngle(-90);
        xLabels.setTextSize(16);
        
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
            //??????????????????????????????
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#E6746A"));
            colors.add(Color.parseColor("#FFB81C"));
            
            set1.setColors(colors);
            
            set1.setStackLabels(new String[]{"????????????", "????????? "});
            
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            set1.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> "");
            dataSets.add(set1);
            
            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);
            barChartPileUp.setData(data);
        }
        
        YAxis yAxis = barChartPileUp.getAxisLeft();
        
        yAxis.setAxisMinimum(0);
        yAxis.setDrawAxisLine(false);                    //??????Y????????????
        
        YAxis yAxis1 = barChartPileUp.getAxisRight();
        yAxis1.setAxisMinimum(0);
        
        Legend legend = barChartPileUp.getLegend();
        legend.setHorizontalAlignment(LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(LegendVerticalAlignment.TOP);
        legend.setOrientation(LegendOrientation.VERTICAL);
        legend.setTextSize(14);
        legend.setFormSize(15);
        
        barChartPileUp.animateXY(0, 2000);
        barChartPileUp.setFitBars(true);
        
        barChartPileUp.invalidate();
        
        
    }
    
    private int plan = 0;
    
    private void setMap(String ch) {
        if (ch.equals("CH")) {
            plan = 0;
            for (GetMunicipal_query_all getProvinceRecruitStudentNumber : list) {
                plan = plan + getProvinceRecruitStudentNumber.getEnrollStudentNum();
            }
            txtPlan.setText(plan + "???");
            colorList1.clear();
            Collections.sort(list,
                (o1, o2) -> o2.getEnrollStudentNum() - o1.getEnrollStudentNum());
            
            for (int i = 0; i < list.size(); i++) {
                
                GetMunicipal_query_all all = list.get(i);
                
                if (list.get(i).getEnrollStudentNum() == 0) {
                    colorList1.add(Color.parseColor("#DDDDDD"));
                }else {
                    if (i <= 3) {
                        colorList1.add(Color.parseColor("#A61F25"));
                    } else if (i <= 5) {
                        colorList1.add(Color.parseColor("#D8232A"));
                    } else if (i <= 10) {
                        colorList1.add(Color.parseColor("#DE4A4A"));
                    } else if (i <= 15) {
                        colorList1.add(Color.parseColor("#E6746A"));
                    } else {
                        colorList1.add(Color.parseColor("#ED9A92"));
                    }
                }
     
            }
        } else {
            plan = 0;
            for (GetMunicipal_query_all getProvinceRecruitStudentNumber : list) {
                plan = plan + getProvinceRecruitStudentNumber.getOverseasStudentNum();
            }
            txtPlan.setText(plan + "???");
            colorList1.clear();
            Collections.sort(list,
                (o1, o2) -> o2.getOverseasStudentNum() - o1.getOverseasStudentNum());
            
            for (int i = 0; i < list.size(); i++) {
                
                if (list.get(i).getOverseasStudentNum() == 0) {
                    colorList1.add(Color.parseColor("#DDDDDD"));
                    continue;
                }
                
                if (i == 0) {
                    colorList1.add(Color.parseColor("#EC9F2D"));
                } else if (i <= 5) {
                    colorList1.add(Color.parseColor("#F4B452"));
                } else if (i <= 10) {
                    colorList1.add(Color.parseColor("#F5C178"));
                } else if (i <= 15) {
                    colorList1.add(Color.parseColor("#FAD79F"));
                } else {
                    colorList1.add(Color.parseColor("#FCE3C4"));
                }
            }
        }
        
        colorMap = new HashMap<>();
        colorMap.clear();
        
        Log.i(TAG, "setMap: " + colorList1.size());
        Log.i(TAG, "setMap: " + list.size());
        
        for (int i = 0; i < colorList1.size(); i++) {
            colorMap.put(list.get(i).getMunicipalName(), colorList1.get(i));
        }
        
        mapView.setColors(colorMap);
        
        switch (name) {
            case "?????????":
                mapView.setMapResId(R.raw.hebeisheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.shanxisheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.liaoningsheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.jilinsheng);
                break;
            case "????????????":
                mapView.setMapResId(R.raw.heilongjiangsheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.jiangsusheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.zhejiangsheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.anhui);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.fujian);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.jiangxisheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.shandongsheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.henansheng);
                break;
            case "?????????":
//                mapView.setMapResId(R.raw.);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.hunansheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.guangdong);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.hainansheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.sichuansheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.guizhousheng);
                break;
            case "?????????":
//                mapView.setMapResId(R.raw.gu);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.shanxisheng1);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.gansu);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.qinghaisheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.taiwansheng);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.beijing);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.tianjinshi);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.shanghaishi);
                break;
            case "?????????":
                mapView.setMapResId(R.raw.chongqingshi);
                break;
            case "?????????????????????":
                mapView.setMapResId(R.raw.guangxi);
                break;
            case "??????????????????":
                mapView.setMapResId(R.raw.neimengguzizhiqu);
                break;
            case "???????????????":
                mapView.setMapResId(R.raw.xizangzizhiqu);
                break;
            case "?????????????????????":
                mapView.setMapResId(R.raw.ningxiahuizuzizhiqu);
                break;
            case "????????????????????????":
                mapView.setMapResId(R.raw.xinjiangweiwuerzuzizhiqu);
                break;
            case "?????????????????????":
                mapView.setMapResId(R.raw.xianggangtebiexingzhengqu);
                break;
            case "?????????????????????":
                mapView.setMapResId(R.raw.aomen);
                break;
        }
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
