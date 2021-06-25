package com.example.smartcampus.fragment.statisticsFragment;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.GradeSexExpenseSum;
import com.example.smartcampus.bean.statistics.ProvinceMunicipalExpenseSum;
import com.example.smartcampus.utils.Utils;
import com.example.smartcampuslibrary.adapter.BaseSpinnerAdapter;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
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
        if (provinceMunicipalExpenseSumList != null && gradeSexExpenseSumList != null){
            progressDialog.dismiss();
            showSpinner();
            showView();
        }
    }

    private void showSpinner() {
        String[] strings1 = {"各年级消费趋势" , "年级消费总计" , "性别消费总计" , "省份消费总计"};
        spinner1.setVisibility(View.VISIBLE);
        spinner1.setAdapter(new BaseSpinnerAdapter(getContext() , Arrays.asList(strings1)) {
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
        spinner2.setAdapter(new BaseSpinnerAdapter(getContext() , strings2) {
            @Override
            protected int setBackground() {
                return 0;
            }
        });
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner1.getSelectedItem().toString().equals("省份消费总计")){
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

    private void showView() {

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
                    if (gradeSexExpenseSumList != null){
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
