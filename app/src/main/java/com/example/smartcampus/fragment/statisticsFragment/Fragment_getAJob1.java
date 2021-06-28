package com.example.smartcampus.fragment.statisticsFragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcampus.R;
import com.example.smartcampus.adapter.ProvinceAdapter;
import com.example.smartcampus.bean.statistics.GetMunicipalqueryall;
import com.example.smartcampus.bean.statistics.GetStudentqueryall;
import com.example.smartcampus.bean.statistics.GetWorkNatureNamequeryall;
import com.example.smartcampus.bean.statistics.Provincequeryall;
import com.example.smartcampus.listener.AppBarLayoutStateChangeListener;
import com.example.smartcampus.model.MycolorArea;
import com.example.smartcampus.util.ColorChangeUtil;
import com.example.smartcampus.view.ColorView;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxy.chinamapview.model.ChinaMapModel;
import com.wxy.chinamapview.model.ProvinceModel;
import com.wxy.chinamapview.view.ChinaMapView;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment_getAJob1 extends BaseFragment implements OnChartValueSelectedListener {
    private ImageView back;
    private TextView title;
    private PieChart chart1;
    private List<GetWorkNatureNamequeryall> getWorkNatureNamequeryalls;
    private List<GetStudentqueryall> getStudentqueryalls;
    private Map<String, Integer> student1;


    private ChinaMapModel chinaMapModel;
    private HashMap<String, List<MycolorArea>> colorView_hashmap;
    private int currentColor = 0;
    private List<String> list;
    private ProvinceAdapter adapter;

    private Typeface tf;
    private ChinaMapView chinamapView;
    private ColorView colorView;
    private RecyclerView recycle;
    private Button btnChange;

    private List<Provincequeryall> provincequeryalls;//获取省
    private List<GetMunicipalqueryall> getMunicipalqueryalls;//获取市
    private Map<String, Float> studentadress;
    private LineChart chart2;
    private BarChart chart3;
    private BarChart chart4;
    private LinearLayout swipe;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_getajob;
    }


    @Override
    protected void initView(View view) {

        back = (ImageView) view.findViewById(R.id.back);
        title = (TextView) view.findViewById(R.id.title);
        chart1 = (PieChart) view.findViewById(R.id.chart1);
        chinamapView = (ChinaMapView) view.findViewById(R.id.chinamap_view);
        colorView = (ColorView) view.findViewById(R.id.color_view);
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        /*swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);*/

        btnChange = (Button) view.findViewById(R.id.btn_change);

        chart2 = (LineChart) view.findViewById(R.id.chart2);
        chart3 = (BarChart) view.findViewById(R.id.chart3);
        chart4 = (BarChart) view.findViewById(R.id.chart4);

        swipe = (LinearLayout) view.findViewById(R.id.swipe);
    }

    @Override
    protected void initData() {
        title.setText("学业信息");
        provincequeryalls = new ArrayList<>();
        getWorkNatureName_query_all();
        //初始化map
        initMap();
        btnChange.setText("就业地市显示");


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnChange.getText().equals("就业地市显示")) {
                    btnChange.setText("岗位性质统计");
                    swipe.setVisibility(View.GONE);
                } else {
                    btnChange.setText("就业地市显示");
                    swipe.setVisibility(View.VISIBLE);
                }
              /*  String namestring = ColorChangeUtil.nameStrings[++currentColor % ColorChangeUtil.nameStrings.length];
                btnChange.setText(namestring);
                colorView.setList(colorView_hashmap.get(namestring));
                //重置map各省份颜色
                ColorChangeUtil.changeMapColors(chinaMapModel, namestring);
                chinamapView.notifyDataChanged();*/
            }
        });
    }

    /*private void initSwipRefresh() {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chinamapView.setEnableTouch(false);
                //模拟耗时
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     *//*   String nameString = ColorChangeUtil.nameStrings[++currentColor % ColorChangeUtil.nameStrings.length];
                        btnChange.setText(nameString);
                        colorView.setList(colorView_hashmap.get(nameString));
                        //重置map各省份颜色
                        ColorChangeUtil.changeMapColors(chinaMapModel, nameString);*//*
                        chinamapView.notifyDataChanged();
                        swipe.setRefreshing(false);
                        if (appbarState == EXPANDED) {
                            swipe.setEnabled(true);
                            chinamapView.setEnableTouch(true);

                        } else {
                            swipe.setEnabled(false);
                            chinamapView.setEnableTouch(false);

                        }
                    }
                }, 1000);
            }
        });
    }*/

   /* private void initAppbarListener() {
        appbarLayout.addOnOffsetChangedListener(new AppBarLayoutStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                appbarState = state;
                switch (state) {
                    case EXPANDED:
                        *//*  swipe.setEnabled(true);*//*
                        chinamapView.setEnableTouch(true);
                        break;
                    case COLLAPSED:
                    case INTERMEDIATE:
                        chinamapView.setEnableTouch(false);
                      *//*  if (!swipe.isRefreshing()) {
                            swipe.setEnabled(false);
                        }*//*
                        break;
                }
            }
        });
    }*/

    private void intMapColor() {

        String colorStrings[] = {"#D50D0D,#DC5754,#E98683,#F8CECF,#D3DFD5,#8DB093,#5E9361,#1C6620"};
        String textStrings[] = {"0~0.5,0.5~1,1~1.5,1.5~2,2~2.5,2.5~3,3~3.5,3.5~"};
        float a = 0;
        for (ProvinceModel p : chinaMapModel.getProvincesList()) {
            for (Provincequeryall p1 : provincequeryalls) {
                if (p1.getProvinceName().contains(p.getName())) {
                    if (studentadress.get(p1.getProvinceName()) == null) {
                    } else {
                        a = studentadress.get(p1.getProvinceName()) * 100;
                    }

                    int b = (int) ((a - 0.5) / 0.5 + 1);
                    Log.d("aaaaaaaa", "intMapColor: " + a + "/" + b);
                    if (b <= 0) {
                        b = 0;
                    } else if (b >= textStrings[0].split(",").length - 1) {
                        b = textStrings[0].split(",").length - 1;
                    }
                    p.setColor(Color.parseColor(colorStrings[0].split(",")[b]));
                    chinamapView.notifyDataChanged();
                }


            }

        }

    }
     /*
        ColorChangeUtil.changeMapColors(chinaMapModel, ColorChangeUtil.nameStrings[currentColor]);
        chinamapView.notifyDataChanged();*/

    private void initRecycleView() {
      /*  list = new ArrayList<>();
        for (int i = 0; i < ColorChangeUtil.province_datas.length; i++) {
            list.add(ColorChangeUtil.province_datas[i]);
        }*/

        adapter = new ProvinceAdapter(studentadress, provincequeryalls);
        recycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycle.setAdapter(adapter);
    }

    /**
     * 设置颜色渐变条
     */
    private void setColorView() {
        colorView_hashmap = new HashMap<>();
        for (int i = 0; i < ColorChangeUtil.nameStrings.length; i++) {
            String colors[] = ColorChangeUtil.colorStrings[i].split(",");
            String texts[] = ColorChangeUtil.textStrings[i].split(",");
            List<MycolorArea> list = new ArrayList<>();
            for (int j = 0; j < colors.length; j++) {
                MycolorArea c = new MycolorArea();
                c.setColor(Color.parseColor(colors[j]));
                c.setText(texts[j]);
                list.add(c);
            }
            colorView_hashmap.put(ColorChangeUtil.nameStrings[i], list);
        }
        colorView.setList(colorView_hashmap.get(ColorChangeUtil.nameStrings[0]));
    }

    private void initMap() {
        chinaMapModel = chinamapView.getChinaMapModel();
        //传数据
        chinamapView.setScaleMax(3);
        chinamapView.setScaleMin(1);
        chinamapView.setOnProvinceClickLisener(new ChinaMapView.onProvinceClickLisener() {
            @Override
            public void onSelectProvince(String provinceName) {

                for (int i = 0; i < provincequeryalls.size(); i++) {

                    if (provincequeryalls.get(i).getProvinceName().contains(provinceName)) {
                        Log.d("aaaaaaaax", "onSelectProvince: " + provinceName + "/" + provincequeryalls.get(i).getProvinceName());
                        Provincequeryall p = provincequeryalls.get(i);
                        provincequeryalls.remove(i);
                        provincequeryalls.add(0, p);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
//        chinamapView.setOnPromiseParentTouchListener(new ChinaMapView.onPromiseParentTouchListener() {
//            @Override
//            public void onPromiseTouch(boolean promise) {
//                /* swipe.setEnabled(promise);*/
//                banAppBarScroll(promise);
//                Log.v("xixi=", promise + "");
//            }
//        });
    }

//    private void banAppBarScroll(boolean isScroll) {
//        for (int i = 0; i < appbarLayout.getChildCount(); i++) {
//            View mAppBarChildAt = appbarLayout.getChildAt(i);
//            AppBarLayout.LayoutParams mAppBarParams = (AppBarLayout.LayoutParams) mAppBarChildAt.getLayoutParams();
//            if (isScroll) {
//                mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
//                mAppBarChildAt.setLayoutParams(mAppBarParams);
//            } else {
//                mAppBarParams.setScrollFlags(0);
//            }
//        }
//    }

    private void getStudentqueryalls() {
        new OkHttpTo().setUrl("getStudent_query_all")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        getStudentqueryalls = new Gson().fromJson(jsonObject.optJSONArray("data").toString()
                                , new TypeToken<List<GetStudentqueryall>>() {
                                }.getType());
                        if (getStudentqueryalls.size() != 0) {
                            huoqu();
                            huoqusheng();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }

                }).start();
    }

    private void huoqusheng() {
        new OkHttpTo().setUrl("province_query_all")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        provincequeryalls.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString()
                                , new TypeToken<List<Provincequeryall>>() {
                                }.getType()));
                        if (provincequeryalls.size() != 0) {
                            huoqushi();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void huoqushi() {
        studentadress = new HashMap<>();
        new OkHttpTo().setUrl("getMunicipal_query_all")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        getMunicipalqueryalls = new Gson().fromJson(jsonObject.optJSONArray("data").toString()
                                , new TypeToken<List<GetMunicipalqueryall>>() {
                                }.getType());
                        for (GetStudentqueryall s : getStudentqueryalls) {
                            for (GetMunicipalqueryall m : getMunicipalqueryalls) {
                                if (s.getMunicipalId().equals(m.getId())) {
                                    for (Provincequeryall p : provincequeryalls) {
                                        if (m.getProvinceId().equals(p.getProvinceId())) {
                                            String sheng = p.getProvinceName();
                                            Float count = studentadress.get(sheng);
                                            studentadress.put(sheng, (count == null) ? 1 : count + 1);
                                        }
                                    }
                                }
                            }
                        }
                        for (Provincequeryall p : provincequeryalls) {
                            if (studentadress.get(p.getProvinceName()) == null) {

                            } else {
                                float zongshu = studentadress.get(p.getProvinceName()) / getStudentqueryalls.size();
                                studentadress.put(p.getProvinceName(), zongshu);
                            }

                        }
                        //设置颜色渐变条
                        setColorView();
                        initRecycleView();
                        //初始化地图颜色
                        intMapColor();
                       /* initAppbarListener();*/
                        /* initSwipRefresh();*/
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void huoqu() {
        student1 = new HashMap<>();
        int x = 0;
        int q = 0;
        int s1 = 0;
        int s2 = 0;
        int w = 0;
        for (GetStudentqueryall s : getStudentqueryalls) {
            switch (s.getWordNatureId()) {
                case "1":
                    x++;
                    break;
                case "2":
                    q++;
                    break;
                case "3":
                    s1++;
                    break;
                case "4":
                    s2++;
                    break;
                case "5":
                    w++;
                    break;
            }
        }
        student1.put("行政", x);
        student1.put("企业", q);
        student1.put("事业", s1);
        student1.put("升学", s2);
        student1.put("未就业", w);
        setView();//饼图
        setView2();//折线图
        setView3();//柱状图
        setView4();
    }

    private void setView4() {
        chart4.getDescription().setEnabled(false);        //不展示图表描述信息
        //所有值均绘制在其条形顶部上方
        chart4.setDrawValueAboveBar(false);
        //纵坐标不显示网格线
        chart4.getXAxis().setDrawGridLines(false);
        //不支持图表缩放
        chart4.setScaleEnabled(false);
        //设置点击图表时的标记，可以自定义布局
//        barChartPileUp.setMarker(new MyMarkView(context);

        // 改变y标签的位置
        YAxis leftAxis = chart4.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        //右y轴不显示
        chart4.getAxisRight().setEnabled(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setMaxWidth(100);
        //获取x坐标轴
        XAxis xLabels = chart4.getXAxis();
        //设置x坐标轴显示位置在下方
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        //转换x坐标轴显示内容
        xLabels.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });

        //获取图表的图例
        Legend l = chart4.getLegend();
        //图例设置在下方
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        //图例设置在中间
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTextSize(18);
        l.setFormSize(18);
        l.setDrawInside(false);

        ArrayList<BarEntry> yValues = new ArrayList<>();

        yValues.add(new BarEntry(0, new float[]{(float) (student1.get(getWorkNatureNamequeryalls.get(0).getWorkNatureName())), (float) (student1.get(getWorkNatureNamequeryalls.get(1).getWorkNatureName()))
                , (float) (student1.get(getWorkNatureNamequeryalls.get(2).getWorkNatureName())), (float) (student1.get(getWorkNatureNamequeryalls.get(3).getWorkNatureName()))
                , (float) (student1.get(getWorkNatureNamequeryalls.get(4).getWorkNatureName()))}));

        BarDataSet set1;

        if (chart4.getData() != null && chart4.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart4.getData().getDataSetByIndex(0);
            set1.setValues(yValues);
            chart4.getData().notifyDataChanged();
            chart4.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yValues, "");
            set1.setValueTextSize(20);
            set1.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
                DecimalFormat format = new DecimalFormat("0");
                return format.format(value);
            });
            //设置图例的颜色和名称
            set1.setColors(new int[]{R.color.design_default_color_secondary, R.color.drawing_nv, R.color.blue_title, R.color.design_default_color_secondary_variant, R.color.design_default_color_error}, getContext());
            set1.setStackLabels(new String[]{getWorkNatureNamequeryalls.get(0).getWorkNatureName(),
                    getWorkNatureNamequeryalls.get(1).getWorkNatureName(),
                    getWorkNatureNamequeryalls.get(2).getWorkNatureName(),
                    getWorkNatureNamequeryalls.get(3).getWorkNatureName(),
                    getWorkNatureNamequeryalls.get(4).getWorkNatureName()});

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextColor(Color.WHITE);

            chart4.setData(data);
        }
        chart4.animateXY(0, 2000);
        chart4.setFitBars(true);
        chart4.setTouchEnabled(false);                   //禁用触摸
        chart4.invalidate();

    }

    private List<BarEntry> barEntries;
    private List<String> stringSex;

    private void setView3() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            stringSex = new ArrayList<>();
        } else {
            barEntries.clear();
            stringSex.clear();
        }


        for (int i = 0; i < getWorkNatureNamequeryalls.size(); i++) {
            GetWorkNatureNamequeryall w = getWorkNatureNamequeryalls.get(i);
            barEntries.add(new BarEntry(i, student1.get(w.getWorkNatureName())));
            stringSex.add(w.getWorkNatureName());
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "");

        barDataSet.setColor(Color.parseColor("#20D5CE"));
        BarData data = new BarData(barDataSet);
        barDataSet.setValueTextSize(18);            //设置大小
        barDataSet.setValueTextColor(Color.parseColor("#404040"));
        barDataSet.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> {
            DecimalFormat format = new DecimalFormat("0");
            return format.format(value);
        });
        data.setBarWidth(0.5f);

        chart3.getLegend().setEnabled(false);

        YAxis yAxis = chart3.getAxisLeft();
        yAxis.setDrawAxisLine(false);                    //关闭Y轴网格线
        yAxis.setAxisMinimum(0);                        //最小值
        yAxis.setAxisMaximum(500);                        //最大值

        XAxis xAxis = chart3.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);    //X轴在底部显示
        xAxis.setValueFormatter(new IndexAxisValueFormatter(stringSex));    //将每秒显示的数据放到X轴
        xAxis.setLabelCount(stringSex.size());            //标签个数
        xAxis.setGranularity(1f);
        xAxis.setTextSize(20);
        //两个数之间的间距
        xAxis.setDrawGridLines(false);                    //关闭网格线

        barDataSet.setColors(Color.parseColor("#22D5CE"), Color.parseColor("#F39444"));

        chart3.animateXY(0, 2000);
        chart3.setFitBars(true);
        chart3.getAxisRight().setEnabled(false);         //关闭右边线和数据
        chart3.setData(data);                            //数据集合
        chart3.setTouchEnabled(false);                   //禁用触摸
        chart3.getDescription().setEnabled(false);       //关闭右下角
        chart3.invalidate();
    }

    private List<Entry> entries;
    private List<String> stringLine;

    private void setView2() {
        if (entries == null) {
            entries = new ArrayList<>();
            stringLine = new ArrayList<>();
        } else {
            entries.clear();
            stringLine.clear();
        }


        for (int i = 0; i < getWorkNatureNamequeryalls.size(); i++) {
            GetWorkNatureNamequeryall w = getWorkNatureNamequeryalls.get(i);
            entries.add(new Entry(i, student1.get(w.getWorkNatureName())));
            Log.d("aaaaaaaaaax", "setView2: " + student1.get(w.getWorkNatureName()));
            stringLine.add(w.getWorkNatureName());
        }


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

        YAxis yAxis = chart2.getAxisLeft();
        yAxis.setDrawAxisLine(false);                    //关闭Y轴网格线
        yAxis.setAxisMinimum(0);                        //最小值
        yAxis.setAxisMaximum(500);                        //最大值

        XAxis xAxis = chart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);    //X轴在底部显示
        xAxis.setValueFormatter(new IndexAxisValueFormatter(stringLine));    //将每秒显示的数据放到X轴
        xAxis.setLabelCount(stringLine.size());            //标签个数
        xAxis.setGranularity(1f);
        xAxis.setTextSize(20);
        //两个数之间的间距
        xAxis.setDrawGridLines(false);                    //关闭网格线

        chart2.setData(lineData);
        chart2.getLegend().setEnabled(false);
        chart2.animateXY(1500, 1000);                    //动画效果
        chart2.getDescription().setEnabled(false);       //说明关闭
        chart2.getDescription().setText("(S)");          //右下角说明打开
        chart2.setScaleEnabled(false);                   //禁用缩放比例
        chart2.setTouchEnabled(false);                   //禁用触摸
        chart2.invalidate();                             //刷新


    }


    private final int[] colors = new int[]{
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    private void getWorkNatureName_query_all() {
        new OkHttpTo().setUrl("getWorkNatureName_query_all")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        getWorkNatureNamequeryalls = new Gson().fromJson(jsonObject.optJSONArray(
                                "data").toString(), new TypeToken<List<GetWorkNatureNamequeryall>>() {
                        }.getType());
                        if (getWorkNatureNamequeryalls != null) {
                            getStudentqueryalls();

                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void setView() {
        //以百分比绘制
        chart1.setUsePercentValues(true);
        chart1.getDescription().setEnabled(false);
        //偏移量
        chart1.setExtraOffsets(5, 10, 5, 5);

        chart1.setDragDecelerationFrictionCoef(0.95f);
        //字体设置
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        //设置显示在Piechart中心的文本字符串
        chart1.setCenterText(generateCenerSpannableText());

        chart1.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        //饼图中心是否为空
        chart1.setDrawHoleEnabled(true);
        //设置透明圆应有的颜色
        chart1.setTransparentCircleColor(Color.WHITE);
        //设置透明度
        chart1.setTransparentCircleAlpha(110);
        //设置中心圆的半径，以整个图的百分比来设置，默认50%
        chart1.setHoleRadius(58f);
        //设置中心透明圆的半径
        chart1.setTransparentCircleRadius(61f);
        //是否绘制显示在中间的文本
        chart1.setDrawCenterText(true);
        //设置旋转的偏移度
        chart1.setRotationAngle(0);
        //enable rotation of the chart by touch
        //通过触摸启动图标旋转
        chart1.setRotationEnabled(true);
        chart1.setHighlightPerTapEnabled(true);

        //chart.setUnit("€")
        //chart.setDrawUnitsInChart(true);

        //add a selection listener
        //设置图表监听
        chart1.setOnChartValueSelectedListener(this);

//        chart1.animateY(1400, Easing.EaseInOutQuad);

        Legend legend = chart1.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        //设置在图例的表内还是表外
        legend.setDrawInside(false);
        legend.setEnabled(false);
        setData();

    }


    private void setData() {

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (GetWorkNatureNamequeryall w : getWorkNatureNamequeryalls) {
            entries.add(new PieEntry(student1.get(w.getWorkNatureName()), w.getWorkNatureName()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        //为该数据对象的所有数据集设置文本大小的（dp）包含
        data.setValueTextSize(11f);
        //为该对象所有数据集设置颜色
        data.setValueTextColor(Color.BLACK);
        //标签字体
        data.setValueTypeface(tf);
        chart1.setData(data);

        chart1.animateXY(1500, 1000);
        //undo all highlights
        //撤销所有高亮
        chart1.highlightValues(null);
        chart1.invalidate();

    }

    private CharSequence generateCenerSpannableText() {
        SpannableString s = new SpannableString("岗位性质统计");
        s.setSpan(new RelativeSizeSpan(1.5f), 0, 6, 0);/*
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);*/
        return s;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null) {
            return;
        }
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());

    }


    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}
