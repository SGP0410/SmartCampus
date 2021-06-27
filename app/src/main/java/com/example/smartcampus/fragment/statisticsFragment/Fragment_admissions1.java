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
            txtStudent.setText("国内招生");
        });
        btnForeign.setOnClickListener(v -> {
            imgForeign.setImageResource(R.mipmap.xs_2_lan);
            txtForeign.setTextColor(Color.parseColor("#386FE2"));
            imgDomestic.setImageResource(R.mipmap.xs_1_hui);
            txtDomestic.setTextColor(Color.parseColor("#414141"));
            getData("GH");
            setMap("GH");
            txtStudent.setText("留学生招生");
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
                case "延边朝鲜族自治州":
                    s = "延边朝鲜";
                    break;
                case "齐齐哈尔市":
                    s = "齐齐哈尔";
                    break;
                case "大兴安岭地区":
                    s = "大兴安岭";
                    break;
                case "恩施土家族苗族自治州":
                    s = "恩施土家";
                    break;
                case "神农架林区":
                    s = "神农架林";
                    break;
                case "湘西土家族苗族自治州":
                    s = "湘西土家";
                    break;
                case "白沙黎族自治区":
                    s = "白沙黎族";
                    break;
                case "昌江黎族自治区":
                    s = "昌江黎族";
                    break;
                case "乐东黎族自治县":
                    s = "乐东黎族";
                    break;
                case "保亭黎族苗族自治县":
                    s = "保亭黎族";
                    break;
                case "琼中黎族苗族自治县":
                    s = "琼中黎族";
                    break;
                case "阿坝藏族羌族自治州":
                    s = "阿坝藏族";
                    break;
                case "甘孜藏族自治州":
                    s = "甘孜藏族";
                    break;
                case "凉山彝族自治州":
                    s = "凉山彝族";
                    break;
                case "黔西南布依族苗族自治州":
                    s = "黔西南布依";
                    break;
                case "黔东南苗族侗族自治州":
                    s = "黔东南苗族";
                    break;
                case "黔南布依族苗族自治州":
                    s = "黔南布依族";
                    break;
                case "楚雄彝族自治州":
                    s = "楚雄彝族";
                    break;
                case "红河哈尼族彝族自治州":
                    s = "红河哈尼族";
                    break;
                case "文山壮族苗族自治州":
                    s = "文山壮族";
                    break;
                case "大理白族自治州":
                    s = "大理白族";
                    break;
                case "西双版纳傣族自治州":
                    s = "西双版纳傣";
                    break;
                case "德宏傣族景颇族自治州":
                    s = "德宏傣族";
                    break;
                case "怒江傈僳族自治州":
                    s = "怒江傈僳族";
                    break;
                case "迪庆藏族自治州":
                    s = "迪庆藏族";
                    break;
                case "临夏回族自治州":
                    s = "临夏回族";
                    break;
                case "甘南藏族自治州":
                    s = "甘南藏族";
                    break;
                case "海西蒙古族藏族自治州":
                    s = "海西蒙古";
                    break;
                case "玉树藏族自治州":
                    s = "玉树藏族";
                    break;
                case "果洛藏族自治州":
                    s = "果洛藏族";
                    break;
                case "海南藏族自治州":
                    s = "海南藏族";
                    break;
                case "黄南藏族自治州":
                    s = "黄南藏族";
                    break;
                case "海北藏族自治州":
                    s = "海北藏族";
                    break;
                case "呼和浩特市":
                    s = "呼和浩特";
                    break;
                case "鄂尔多斯市":
                    s = "鄂尔多斯";
                    break;
                case "呼伦贝尔市":
                    s = "呼伦贝尔";
                    break;
                case "巴彦淖尔市":
                    s = "巴彦淖尔";
                    break;
                case "乌兰察布市":
                    s = "乌兰察布";
                    break;
                case "锡林郭勒盟":
                    s = "锡林郭勒";
                    break;
                case "乌鲁木齐市":
                    s = "乌鲁木齐";
                    break;
                case "克拉玛依市":
                    s = "克拉玛依";
                    break;
                case "昌吉回族自治州":
                    s = "昌吉回族";
                    break;
                case "博尔塔拉蒙古自治州":
                    s = "博尔塔拉蒙古";
                    break;
                case "巴音郭楞蒙古自治州":
                    s = "巴音郭楞蒙古";
                    break;
                case "阿克苏地区":
                    s = "阿克苏";
                    break;
                case "克孜勒苏柯尔克孜自治州":
                    s = "克孜勒苏柯尔";
                    break;
                case "阿勒泰地区":
                    s = "阿勒泰";
                    break;
                case "伊犁哈萨克自治州":
                    s = "伊犁哈萨克";
                    break;
                case "图木舒克市":
                    s = "图木舒克";
                    break;
                case "可克达拉市":
                    s = "可克达拉";
                    break;
                case "石柱土家族自治区":
                    s = "石柱土家族";
                    break;
                case "彭水苗族土家族自治县":
                    s = "彭水苗族土家族";
                    break;
                case "秀山土家族苗族自治县":
                    s = "秀山土家族苗族";
                    break;
                case "酉阳土家族苗族自治县":
                    s = "酉阳土家族苗族";
                    break;
            }
            
            strings.add(s);
            yValues.add(new BarEntry(i + 1, new float[]{val1, val2}));
            
        }
    
        
        
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
            //设置图例的颜色和名称
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.parseColor("#E6746A"));
            colors.add(Color.parseColor("#FFB81C"));
            
            set1.setColors(colors);
            
            set1.setStackLabels(new String[]{"国内学生", "留学生 "});
            
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
            txtPlan.setText(plan + "人");
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
            txtPlan.setText(plan + "人");
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
            case "河北省":
                mapView.setMapResId(R.raw.hebeisheng);
                break;
            case "山西省":
                mapView.setMapResId(R.raw.shanxisheng);
                break;
            case "辽宁省":
                mapView.setMapResId(R.raw.liaoningsheng);
                break;
            case "吉林省":
                mapView.setMapResId(R.raw.jilinsheng);
                break;
            case "黑龙江省":
                mapView.setMapResId(R.raw.heilongjiangsheng);
                break;
            case "江苏省":
                mapView.setMapResId(R.raw.jiangsusheng);
                break;
            case "浙江省":
                mapView.setMapResId(R.raw.zhejiangsheng);
                break;
            case "安徽省":
                mapView.setMapResId(R.raw.anhui);
                break;
            case "福建省":
                mapView.setMapResId(R.raw.fujian);
                break;
            case "江西省":
                mapView.setMapResId(R.raw.jiangxisheng);
                break;
            case "山东省":
                mapView.setMapResId(R.raw.shandongsheng);
                break;
            case "河南省":
                mapView.setMapResId(R.raw.henansheng);
                break;
            case "湖北省":
//                mapView.setMapResId(R.raw.);
                break;
            case "湖南省":
                mapView.setMapResId(R.raw.hunansheng);
                break;
            case "广东省":
                mapView.setMapResId(R.raw.guangdong);
                break;
            case "海南省":
                mapView.setMapResId(R.raw.hainansheng);
                break;
            case "四川省":
                mapView.setMapResId(R.raw.sichuansheng);
                break;
            case "贵州省":
                mapView.setMapResId(R.raw.guizhousheng);
                break;
            case "云南省":
//                mapView.setMapResId(R.raw.gu);
                break;
            case "陕西省":
                mapView.setMapResId(R.raw.shanxisheng1);
                break;
            case "甘肃省":
                mapView.setMapResId(R.raw.gansu);
                break;
            case "青海省":
                mapView.setMapResId(R.raw.qinghaisheng);
                break;
            case "台湾省":
                mapView.setMapResId(R.raw.taiwansheng);
                break;
            case "北京市":
                mapView.setMapResId(R.raw.beijing);
                break;
            case "天津市":
                mapView.setMapResId(R.raw.tianjinshi);
                break;
            case "上海市":
                mapView.setMapResId(R.raw.shanghaishi);
                break;
            case "重庆市":
                mapView.setMapResId(R.raw.chongqingshi);
                break;
            case "广西壮族自治区":
                mapView.setMapResId(R.raw.guangxi);
                break;
            case "内蒙古自治区":
                mapView.setMapResId(R.raw.neimengguzizhiqu);
                break;
            case "西藏自治区":
                mapView.setMapResId(R.raw.xizangzizhiqu);
                break;
            case "宁夏回族自治区":
                mapView.setMapResId(R.raw.ningxiahuizuzizhiqu);
                break;
            case "新疆维吾尔自治区":
                mapView.setMapResId(R.raw.xinjiangweiwuerzuzizhiqu);
                break;
            case "香港特别行政区":
                mapView.setMapResId(R.raw.xianggangtebiexingzhengqu);
                break;
            case "澳门特别行政区":
                mapView.setMapResId(R.raw.aomen);
                break;
        }
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
