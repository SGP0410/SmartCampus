package com.example.smartcampus.fragment.statisticsFragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcampus.R;
import com.example.smartcampus.adapter.ProvinceAdapter;
import com.example.smartcampus.bean.statistics.GetMunicipalqueryall;
import com.example.smartcampus.bean.statistics.GetStudentqueryall;
import com.example.smartcampus.bean.statistics.GetWorkNatureNamequeryall;
import com.example.smartcampus.bean.statistics.Province;
import com.example.smartcampus.bean.statistics.Provincequeryall;
import com.example.smartcampus.listener.AppBarLayoutStateChangeListener;
import com.example.smartcampus.model.MycolorArea;
import com.example.smartcampus.util.ColorChangeUtil;
import com.example.smartcampus.view.ColorView;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.example.smartcampus.listener.AppBarLayoutStateChangeListener.State.EXPANDED;

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
    private AppBarLayoutStateChangeListener.State appbarState;

    private Typeface tf;
    private ChinaMapView chinamapView;
    private ColorView colorView;
    private RecyclerView recycle;
    private SwipeRefreshLayout swipe;
    private AppBarLayout appbarLayout;
    private Button btnChange;

    private List<Provincequeryall> provincequeryalls;//获取省
    private List<GetMunicipalqueryall> getMunicipalqueryalls;//获取市
    private Map<String,Float> studentadress;

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
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        appbarLayout = (AppBarLayout) view.findViewById(R.id.appbar_layout);
        btnChange = (Button) view.findViewById(R.id.btn_change);
    }

    @Override
    protected void initData() {
        title.setText("学业信息");
        provincequeryalls=new ArrayList<>();
        getWorkNatureName_query_all();

        //初始化map
        initMap();

        //初始化地图颜色
        intMapColor();
        initAppbarListener();
        initSwipRefresh();
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  String namestring = ColorChangeUtil.nameStrings[++currentColor % ColorChangeUtil.nameStrings.length];
                btnChange.setText(namestring);
                colorView.setList(colorView_hashmap.get(namestring));
                //重置map各省份颜色
                ColorChangeUtil.changeMapColors(chinaMapModel, namestring);
                chinamapView.notifyDataChanged();*/
            }
        });
    }
    private void initSwipRefresh() {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chinamapView.setEnableTouch(false);
                //模拟耗时
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String nameString = ColorChangeUtil.nameStrings[++currentColor % ColorChangeUtil.nameStrings.length];
                        btnChange.setText(nameString);
                        colorView.setList(colorView_hashmap.get(nameString));
                        //重置map各省份颜色
                        ColorChangeUtil.changeMapColors(chinaMapModel, nameString);
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
                }, 2000);
            }
        });
    }

    private void initAppbarListener() {
        appbarLayout.addOnOffsetChangedListener(new AppBarLayoutStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                appbarState = state;
                switch (state) {
                    case EXPANDED:
                        swipe.setEnabled(true);
                        chinamapView.setEnableTouch(true);
                        break;
                    case COLLAPSED:
                    case INTERMEDIATE:
                        chinamapView.setEnableTouch(false);
                        if (!swipe.isRefreshing()) {
                            swipe.setEnabled(false);
                        }
                        break;
                }
            }
        });
    }

    private void intMapColor() {

      /*  String colorStrings[] = {"#D50D0D,#DC5754,#E98683,#F8CECF,#D3DFD5,#8DB093,#5E9361,#1C6620"};
        String textStrings[] = {"0~0.5,0.5~1,1~1.5,1.5~2,2~2.5,2.5~3,3~3.5,3.5~"};
        for (ProvinceModel p:chinaMapModel.getProvincesList()){
            for (Provincequeryall p1:provincequeryalls){
             float  a= studentadress.get(p1.getProvinceName())*100;
             int b=(int)((a-0)/2+1);
                if (b<=0){
                    b=0;
                }else if (b>=textStrings[0].split(",").length-1){
                    b=textStrings[0].split(",").length-1;
                }

            }

        }

        }*/
        btnChange.setText("就业地市显示");
        ColorChangeUtil.changeMapColors(chinaMapModel, ColorChangeUtil.nameStrings[currentColor]);
        chinamapView.notifyDataChanged();
    }
    private void initRecycleView() {
      /*  list = new ArrayList<>();
        for (int i = 0; i < ColorChangeUtil.province_datas.length; i++) {
            list.add(ColorChangeUtil.province_datas[i]);
        }*/
        adapter = new ProvinceAdapter(studentadress,provincequeryalls);
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
        chinamapView.setOnProvinceClickLisener(new com.wxy.chinamapview.view.ChinaMapView.onProvinceClickLisener() {
            @Override
            public void onSelectProvince(String provinceName) {

                for (int i = 0; i < provincequeryalls.size(); i++) {

                    if (provincequeryalls.get(i).getProvinceName().contains(provinceName)) {
                        Log.d("aaaaaaaax", "onSelectProvince: "+provinceName+"/"+provincequeryalls.get(i).getProvinceName());
                        Provincequeryall p = provincequeryalls.get(i);
                        provincequeryalls.remove(i);
                        provincequeryalls.add(0,p);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
        chinamapView.setOnPromiseParentTouchListener(new com.wxy.chinamapview.view.ChinaMapView.onPromiseParentTouchListener() {
            @Override
            public void onPromiseTouch(boolean promise) {
                swipe.setEnabled(promise);
                banAppBarScroll(promise);
                Log.v("xixi=", promise + "");
            }
        });
    }

    private void banAppBarScroll(boolean isScroll) {
        for (int i = 0; i < appbarLayout.getChildCount(); i++) {
            View mAppBarChildAt = appbarLayout.getChildAt(i);
            AppBarLayout.LayoutParams mAppBarParams = (AppBarLayout.LayoutParams) mAppBarChildAt.getLayoutParams();
            if (isScroll) {
                mAppBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                mAppBarChildAt.setLayoutParams(mAppBarParams);
            } else {
                mAppBarParams.setScrollFlags(0);
            }
        }
    }

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
                                ,new TypeToken<List<Provincequeryall>>(){}.getType()));
                        if (provincequeryalls.size()!=0){
                            huoqushi();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void huoqushi() {
        studentadress=new HashMap<>();
        new OkHttpTo().setUrl("getMunicipal_query_all")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        getMunicipalqueryalls=new Gson().fromJson(jsonObject.optJSONArray("data").toString()
                        ,new TypeToken<List<GetMunicipalqueryall>>(){}.getType());
                        for (GetStudentqueryall s:getStudentqueryalls){
                            for (GetMunicipalqueryall m:getMunicipalqueryalls){
                                if (s.getMunicipalId().equals(m.getId())){
                                    for (Provincequeryall p:provincequeryalls){
                                       if (m.getProvinceId().equals(p.getProvinceId())){
                                           String sheng=p.getProvinceName();
                                           Float count=studentadress.get(sheng);
                                           studentadress.put(sheng,(count==null)?1:count+1);
                                       }
                                    }
                                }
                            }
                        }
                        for (Provincequeryall p:provincequeryalls){
                          if (studentadress.get(p.getProvinceName())==null){

                          }else {
                              float zongshu=studentadress.get(p.getProvinceName())/getStudentqueryalls.size();
                              studentadress.put(p.getProvinceName(),zongshu);
                          }

                        }
                        //设置颜色渐变条
                        setColorView();
                        initRecycleView();

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
        setView();
    }

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
