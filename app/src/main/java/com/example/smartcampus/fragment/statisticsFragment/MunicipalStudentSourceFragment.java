package com.example.smartcampus.fragment.statisticsFragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.core.graphics.drawable.DrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Municipal;
import com.example.smartcampus.marker.StudentSourceMarker;
import com.example.smartcampus.marker.StudentSourceMarker.OnClickListener;
import com.example.smartcampus.utils.MapView;
import com.example.smartcampus.utils.destureviewbinder.GestureViewBinder;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class MunicipalStudentSourceFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.group_view)
    LinearLayout groupView;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.you_xiu_image)
    ImageView youXiuImage;
    @BindView(R.id.you_xiu_text)
    TextView youXiuText;
    @BindView(R.id.you_xiu)
    LinearLayout youXiu;
    @BindView(R.id.pin_kun_image)
    ImageView pinKunImage;
    @BindView(R.id.pin_kun_text)
    TextView pinKunText;
    @BindView(R.id.pin_kun)
    LinearLayout pinKun;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    private String name;
    private List<Municipal> municipalList1;
    private List<Municipal> municipalList2;
    private List<String> names;
    private BarChart barChart1;
    private BarChart barChart2;
    private List<Integer> colorList1;
    private List<Integer> colorList2;
    private String markerName;

    public MunicipalStudentSourceFragment(String name, List<Municipal> municipals) {
        this.name = name;
        municipalList1 = new ArrayList<>();
        municipalList2 = new ArrayList<>();
        municipalList1.addAll(municipals);
        municipalList2.addAll(municipals);
    }

    @Override
    protected int layoutResId() {
        return R.layout.student_source_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        title.setText(name);
        youXiu.setOnClickListener(this::onClick);
        pinKun.setOnClickListener(this::onClick);
    }

    @Override
    protected void initData() {
        GestureViewBinder binder = GestureViewBinder.bind(getContext(), groupView, mapView);
        binder.setFullGroup(true);

        sortMunicipalList();
        setView();
    }

    private void setView() {
        for (int i = 0; i < 2; i++) {
            BarChart barChart = new BarChart(getContext());
            setBarChart(barChart, i);
            viewFlipper.addView(barChart);
        }

        showMap(colorList1, municipalList1);
    }

    private void showMap(List<Integer> colorList, List<Municipal> municipalList) {
        Map<String, Integer> colorMap = new HashMap<>();
        for (int i = 0; i < colorList.size(); i++) {
            colorMap.put(municipalList.get(i).getMunicipalName(), colorList.get(i));
        }
        mapView.setColors(colorMap);
        setMap();
    }


    private void setBarChart(BarChart barChart, int i) {
        barChart.getLegend().setEnabled(false);
        barChart.setDescription(null);
        setBarChartData(barChart, i);
    }

    private void setBarChartData(BarChart barChart, int i) {
        List<BarEntry> barEntryList = new ArrayList<>();
        BarDataSet barDataSet = null;
        BarData barData = null;

        names = new ArrayList<>();
        names.add("");

        String[] colors1 = {"#938c18", "#c2b61e", "#ddcd21", "#fff101", "#fff156",
            "#fff584", "#fff7aa"};
//        String[] colors2 = {"#802425", "#a83032", "#bd3638", "#dc3e3d", "#e36a56",
//            "#e98770", "#efa490"};

        String[] colors2 = {
            "#00a09b",
            "#00adad",
            "#5cbdb9",
            "#87cac3",
            "#bedec9",
            "#bbe0db"};

//        String[] colors2 = {
//            "#291143",
//            "#3d1e5c",
//            "#452468",
//            "#4e2b79",
//            "#694287",
//            "#79629c",
//            "#8a7bac",
//            "#b5a8c6"
//        };


        if (i == 0) {
            barChart1 = barChart;
            colorList1 = new ArrayList<>();
            for (int i1 = 0; i1 < municipalList1.size(); i1++) {
                barEntryList.add(new BarEntry(
                    i1 + 1,
                    municipalList1.get(i1).getEliteStudent(),
                    municipalList1.get(i1).getMunicipalName()
                ));
                names.add(municipalList1.get(i1).getMunicipalName());
                colorList1.add(Color.parseColor(colors1[i1 / colors1.length]));
            }
            barDataSet = new BarDataSet(barEntryList, "????????????");
            barDataSet.setColors(colorList1);
        } else if (i == 1) {
            barChart2 = barChart;
            colorList2 = new ArrayList<>();
            for (int i1 = 0; i1 < municipalList2.size(); i1++) {
                barEntryList.add(new BarEntry(
                    i1 + 1,
                    municipalList2.get(i1).getPoorStudent(),
                    municipalList2.get(i1).getMunicipalName()
                ));
                names.add(municipalList2.get(i1).getMunicipalName());
                colorList2.add(Color.parseColor(colors2[i1 / colors2.length]));
            }
            barDataSet = new BarDataSet(barEntryList, "????????????");
            barDataSet.setColors(colorList2);
        }

        setAxis(barChart);

        assert barDataSet != null;
        barDataSet.setValueTextSize(16f);
        barDataSet.setValueTextColor(Color.parseColor("#333333"));

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

        Matrix m = new Matrix();
        m.postScale((float) names.size() / 8, 1f);//?????????????????????x,y?????????????????????????????????x??????????????????????????????1.5???
        barChart.getViewPortHandler().refresh(m, barChart, false);//???????????????????????????????????????

        barChart.setData(barData);
        barChart.animateXY(0, 2000);

        StudentSourceMarker marker = new StudentSourceMarker(getContext(),
            barDataSet.getColors());
        barChart.setMarker(marker);
        marker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(String name, int color) {
                if (markerName == null || !markerName.equals(name)) {
                    RectF rectF = mapView.getRectF(name);
                    if (mapView.setHandleTouch((int) rectF.centerX(), (int) rectF.centerY())) {
                        markerName = name;
                    }
                }
            }
        });


    }

    private void setAxis(BarChart barChart) {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
//        xAxis.setLabelCount(names.size());
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(names.size());
        Log.d("aaaaaa", "-----------" + names);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(12f);
        xAxis.setLabelRotationAngle(90);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(10);

        YAxis yAxis1 = barChart.getAxisRight();
        yAxis1.setEnabled(false);
        yAxis1.setAxisMinimum(0f);
        yAxis1.setLabelCount(10);
    }

    private void sortMunicipalList() {
        Collections.sort(municipalList1, ((o1, o2) -> {
            return o2.getEliteStudent() - o1.getEliteStudent();
        }));

        Collections.sort(municipalList2, ((o1, o2) -> {
            return o2.getPoorStudent() - o1.getPoorStudent();
        }));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.you_xiu:
                viewFlipper.setDisplayedChild(0);
                barChart1.animateXY(0, 2000);
                showMap(colorList1, municipalList1);
                setColor(youXiuImage, youXiuText, Color.parseColor("#4B5CC5"));
                setColor(pinKunImage, pinKunText, Color.parseColor("#333333"));
                break;
            case R.id.pin_kun:
                viewFlipper.setDisplayedChild(1);
                barChart2.animateXY(0, 2000);
                showMap(colorList2, municipalList2);
                setColor(youXiuImage, youXiuText, Color.parseColor("#333333"));
                setColor(pinKunImage, pinKunText, Color.parseColor("#4B5CC5"));
                break;
        }
    }

    private void setColor(ImageView imageView, TextView textView, int color) {
        Drawable wrap = DrawableCompat.wrap(imageView.getDrawable());
        DrawableCompat.setTintList(wrap, ColorStateList.valueOf(color));
        imageView.setImageDrawable(wrap);
        textView.setTextColor(color);
    }

    /**
     * ????????? ????????? ????????? ????????? ???????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ????????? ?????????
     * ????????? ????????? ????????? ????????? ????????????????????? ?????????????????? ??????????????? ????????????????????? ???????????????????????? ????????????????????? ?????????????????????
     */

    private void setMap() {
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
}
