package com.example.smartcampus.fragment.statisticsFragment;

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
import com.example.smartcampuslibrary.adapter.BaseSpinnerAdapter;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    }

    //省份消费总计
    private void setTotalProvinceConsumptionData() {

    }

    //性别消费总计
    private void setTotalGenderConsumptionData() {

    }

    //年级消费总计
    private void setTotalGradeConsumptionData() {

    }

    //各年级消费趋势
    private void setConsumptionTrendsOfAllGradesData() {
        entryList = new ArrayList<>();
        int he = 0;
        for (GradeSexExpenseSum gradeSexExpenseSum : gradeSexExpenseSumList) {
            he += gradeSexExpenseSum.getManExpenditure();
            he += gradeSexExpenseSum.getWomanExpenditure();
        }

        for (int i = 0; i < gradeSexExpenseSumList.size(); i++) {
            entryList.add(new Entry(i + 1,
                (gradeSexExpenseSumList.get(i).getManExpenditure() + gradeSexExpenseSumList.get(i)
                    .getWomanExpenditure()) / (float)he,
                gradeSexExpenseSumList.get(i).getGrade()
            ));
        }

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
                Log.d("aaaa" , "---------------"+position);
                assert getFragmentManager() != null;
                new ChartViewDialog(viewList.get(position) , gridView.getWidth()).show(getFragmentManager() ,
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
