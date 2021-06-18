package com.example.smartcampus.fragment.statisticsFragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.statisticsAdapter.Count_sex_adapter;
import com.example.smartcampus.bean.statistics.GetMunicipalMenAndWomenNumberAll;
import com.example.smartcampus.bean.statistics.GetProvinceMenAndWomenNumberAll;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class Fragment_count_sex extends BaseFragment {
    
    private TextView title;
    private RecyclerView recyclerView;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_count_sex;
    }
    
    @Override
    protected void initView(View view) {
        ImageView back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
    }
    
    @Override
    protected void initData() {
        title.setText("性别统计");
        
        getOkHttp();
        
    }
    
    private List<GetProvinceMenAndWomenNumberAll> getProvinceMenAndWomenNumberAlls;
    
    private void getOkHttp() {
        if (getProvinceMenAndWomenNumberAlls == null) {
            getProvinceMenAndWomenNumberAlls = new ArrayList<>();
        } else {
            getProvinceMenAndWomenNumberAlls.clear();
        }
        new OkHttpTo()
            .setUrl("GetProvinceMenAndWomenNumberAll")
            .setRequestType("GET")
            .setDialog(getContext())
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    getProvinceMenAndWomenNumberAlls.addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                            new TypeToken<List<GetProvinceMenAndWomenNumberAll>>() {
                            }.getType()));
                    show();
                }
                
                @Override
                public void onFailure(IOException e) {
                
                }
            }).start();
        
    }
    
    private void show() {
        Count_sex_adapter adapter = new Count_sex_adapter(getProvinceMenAndWomenNumberAlls);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            ((FragmentActivity)getActivity()).setFragment(new Fragment_count_sex_province(getProvinceMenAndWomenNumberAlls.get(position)));
        });
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
