package com.example.smartcampus.fragment.statisticsFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.core.graphics.drawable.DrawableCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.bean.statistics.Municipal;
import com.example.smartcampus.bean.statistics.Province;
import com.example.smartcampus.bean.statistics.ProvinceStudentSource;
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

public class ProvinceStudentSourceFragment extends BaseFragment {

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
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.you_xiu)
    LinearLayout youXiu;
    @BindView(R.id.pin_kun)
    LinearLayout pinKun;
    @BindView(R.id.you_xiu_image)
    ImageView youXiuImage;
    @BindView(R.id.you_xiu_text)
    TextView youXiuText;
    @BindView(R.id.pin_kun_image)
    ImageView pinKunImage;
    @BindView(R.id.pin_kun_text)
    TextView pinKunText;
    
    private List<Province> provinceList;
    private List<ProvinceStudentSource> provinceStudentSourceList1;
    private List<ProvinceStudentSource> provinceStudentSourceList2;
    private List<String> names;
    private BarChart barChart1;
    private BarChart barChart2;
    private List<Integer> colorList1;
    private List<Integer> colorList2;
    private PopupWindow popupWindow;
    private String markerName;
    private Map<String, List<Municipal>> municipalMap;
    private ProgressDialog progressDialog;

    @Override
    protected int layoutResId() {
        return R.layout.student_source_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        title.setText("?????????");
        youXiu.setOnClickListener(this::onClick);
        pinKun.setOnClickListener(this::onClick);
        setColor(youXiuImage, youXiuText, Color.parseColor("#4B5CC5"));
        setColor(pinKunImage, pinKunText, Color.parseColor("#333333"));
    }

    @Override
    protected void initData() {
//        popupWindow = new PopupWindow(getContext());
        GestureViewBinder binder = GestureViewBinder.bind(getContext(), groupView, mapView);
        binder.setFullGroup(true);
        mapView.setOnMapViewClickListener(name -> {
            Log.i("aaaaa", "--------" + name);
//            ((FragmentActivity) getActivity())
//                .setFragment(new MunicipalStudentSourceFragment(name , municipalMap.get(name)));

            RectF rectF = mapView.getRectF(name);
            initPopWindow((int) rectF.centerX(), (int) rectF.bottom, mapView.getColor(name), name);
        });

        province_query_all();
        showDialog();
    }

    private void showDialog(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("????????????????????????");
        progressDialog.setTitle("??????");
        progressDialog.show();
    }
    
    private static final String TAG = "ProvinceStudentSourceFr";

    @SuppressLint("SetTextI18n")
    private void initPopWindow(int x, int y, int color, String name) {
    
        Log.i(TAG, "initPopWindow: "+color);
        popupWindow = new PopupWindow(500,400);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setPadding(15, 15, 15, 15);
        linearLayout.setBackgroundColor(color);
        linearLayout.setLayoutParams(
            new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(getContext());
        title.setLayoutParams(
            new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        title.setGravity(Gravity.CENTER);
        title.setTextSize(16);
        title.setBackgroundColor(Color.parseColor("#FDFDFE"));
        title.setTextColor(Color.parseColor("#333333"));
        title.setText(name);

        linearLayout.addView(title);

        List<View> viewList = new ArrayList<>();
        List<Municipal> municipalList = municipalMap.get(name);
        if (municipalList == null){
            return;
        }
        for (Municipal municipal : municipalList) {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            textView.setPadding(15, 5, 15, 5);
            textView.setTextSize(14);
            textView.setBackgroundColor(Color.parseColor("#FDFDFE"));
            textView.setTextColor(Color.parseColor("#666666"));
            if (viewFlipper.getDisplayedChild() == 0) {
                textView.setText(
                    municipal.getMunicipalName() + "????????????" +
                        municipal.getEliteStudent() + "???"
                );
            } else {
                textView.setText(
                    municipal.getMunicipalName() + "????????????" +
                        municipal.getPoorStudent() + "???"
                );
            }
            viewList.add(textView);
            textView.setOnClickListener(v -> {
                popupWindow.dismiss();
                ((FragmentActivity) getActivity())
                    .setFragment(new MunicipalStudentSourceFragment(name, municipalMap.get(name)));
            });
        }

        ListView listView = new ListView(getContext());
        linearLayout.addView(listView);
        listView.setAdapter(new BaseAdapter() {
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

        popupWindow.setContentView(linearLayout);
        popupWindow.showAsDropDown(groupView,
            (int) (x * mapView.getMScale()) - (popupWindow.getWidth() / 2),
            (int) (y * mapView.getMScale()) - groupView.getHeight(),
            Gravity.CENTER);
    }

    private void province_query_all() {
        new OkHttpTo().setUrl("province_query_all")
            .setRequestType("get")
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    provinceList = new Gson().fromJson(jsonObject.optJSONArray(
                        "data").toString(), new TypeToken<List<Province>>() {
                    }.getType());
                    if (provinceList != null) {
                        getProvinceStudentSource();
                        setMunicipalMap();
                    }
                }

                @Override
                public void onFailure(IOException e) {

                }
            }).start();
    }

    private void getProvinceStudentSource() {
        provinceStudentSourceList1 = new ArrayList<>();
        OkHttpTo okHttpTo;
        for (Province province : provinceList) {
            okHttpTo = new OkHttpTo();
            okHttpTo
                .setUrl("GetProvinceStudentSource?provinceName=" + province.getProvinceName())
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        provinceStudentSourceList1.add(new Gson().fromJson(jsonObject.toString(),
                            ProvinceStudentSource.class));

                        if (provinceStudentSourceList1.size() == provinceList.size()) {
                            showData();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
        }
    }

    private void showData() {
        provinceStudentSourceList2 = new ArrayList<>();
        provinceStudentSourceList2.addAll(provinceStudentSourceList1);

        Collections.sort(provinceStudentSourceList1, ((o1, o2) -> {
            return o2.getEliteStudent() - o1.getEliteStudent();
        }));

        Collections.sort(provinceStudentSourceList2, ((o1, o2) -> {
            return o2.getPoorStudent() - o1.getPoorStudent();
        }));

        setView();
    }

    private void setView() {

        for (int i = 0; i < 2; i++) {
            BarChart barChart = new BarChart(getContext());
            barChart.setLayoutParams(
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            setBarChart(barChart, i);
            viewFlipper.addView(barChart);
        }

        showMap(colorList1, provinceStudentSourceList1);
    }

    private void showMap(List<Integer> colorList,
        List<ProvinceStudentSource> provinceStudentSourceList) {

        Map<String, Integer> colorMap = new HashMap<>();
        for (int i = 0; i < colorList.size(); i++) {
            colorMap.put(provinceStudentSourceList.get(i).getProvinceName(), colorList.get(i));
        }
        mapView.setColors(colorMap);
        mapView.setMapResId(R.raw.china);

        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }

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
        //??????
        if (i == 0) {
            barChart1 = barChart;
            for (int i1 = 0; i1 < provinceStudentSourceList1.size(); i1++) {
                barEntryList.add(new BarEntry(i1 + 1,
                        provinceStudentSourceList1.get(i1).getEliteStudent(),
                        provinceStudentSourceList1.get(i1).getProvinceName()));
                names.add(provinceStudentSourceList1.get(i1).getProvinceName());
            }
            barDataSet = new BarDataSet(barEntryList, "????????????");
            colorList1 = new ArrayList<>();
            for (int j = 0; j < provinceStudentSourceList1.size(); j++) {
                colorList1.add(Color.parseColor(colors1[j / colors1.length]));
            }
            barDataSet.setColors(colorList1);
        }
        //??????
        if (i == 1) {
            barChart2 = barChart;
            for (int i1 = 0; i1 < provinceStudentSourceList2.size(); i1++) {
                barEntryList
                    .add(new BarEntry(i1 + 1,
                        provinceStudentSourceList2.get(i1).getPoorStudent(),
                        provinceStudentSourceList2.get(i1).getProvinceName()));
                names.add(provinceStudentSourceList2.get(i1).getProvinceName());
            }
            barDataSet = new BarDataSet(barEntryList, "????????????");

            colorList2 = new ArrayList<>();
            for (int j = 0; j < provinceStudentSourceList2.size(); j++) {
                colorList2.add(Color.parseColor(colors2[j / colors2.length]));
            }
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

        StudentSourceMarker marker = new StudentSourceMarker(getContext(),
            barDataSet.getColors());
        barChart.setMarker(marker);
        marker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(String name, int color) {
                if (markerName == null || !markerName.equals(name)) {
                    RectF rectF = mapView.getRectF(name);
                    initPopWindow((int) rectF.centerX(), (int) rectF.bottom, color, name);
                    if (!name.equals("?????????????????????")) {
                        if (mapView.setHandleTouch((int) rectF.centerX(), (int) rectF.centerY())) {
                            markerName = name;
                        }
                    } else {
                        if (mapView.setHandleTouch(0, 0)) {
                            markerName = name;
                        }
                    }
                }
            }
        });

        Matrix m = new Matrix();
        m.postScale(4f, 1f);//?????????????????????x,y?????????????????????????????????x??????????????????????????????1.5???
        barChart.getViewPortHandler().refresh(m, barChart, false);//???????????????????????????????????????

        barChart.setData(barData);
        barChart.animateXY(0, 2000);

    }

    private void setAxis(BarChart barChart) {

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(names.size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
        xAxis.setLabelRotationAngle(90);
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(12f);

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(10);

        YAxis yAxis1 = barChart.getAxisRight();
        yAxis1.setEnabled(false);
        yAxis1.setAxisMinimum(0f);
        yAxis1.setLabelCount(10);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.you_xiu:
                viewFlipper.setDisplayedChild(0);
                barChart1.animateXY(0, 2000);
                showMap(colorList1, provinceStudentSourceList1);
                setColor(youXiuImage, youXiuText, Color.parseColor("#4B5CC5"));
                setColor(pinKunImage, pinKunText, Color.parseColor("#333333"));
                break;
            case R.id.pin_kun:
                viewFlipper.setDisplayedChild(1);
                barChart2.animateXY(0, 2000);
                showMap(colorList2, provinceStudentSourceList2);
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

    //?????????????????????????????????
    private void setMunicipalMap() {
        municipalMap = new HashMap<>();

        for (Province province : provinceList) {
            Log.i(TAG, "setMunicipalMap: "+province.getProvinceName());
        }

        for (Province province : provinceList) {
            new OkHttpTo().setUrl("GetMunicipalStudentSourceByProvinceName?provinceName=" + province
                .getProvinceName())
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        List<Municipal> municipalList =
                            new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<Municipal>>() {
                                }.getType());
                        if (municipalList != null) {
                            municipalMap.put(province.getProvinceName(), municipalList);
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
        }
    }
}
