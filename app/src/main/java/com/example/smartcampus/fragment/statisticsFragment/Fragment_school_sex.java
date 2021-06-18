package com.example.smartcampus.fragment.statisticsFragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.statisticsAdapter.School_sex_adapter;
import com.example.smartcampus.bean.statistics.GetCollegeMenAndWomenNumberAll;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class Fragment_school_sex extends BaseFragment {
    
    private ImageView back;
    private TextView title;
    private RecyclerView recyclerView;
    private List<GetCollegeMenAndWomenNumberAll> getCollegeMenAndWomenNumberAlls;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_school_sex;
    }
    
    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
    }
    
    @Override
    protected void initData() {
        title.setText("性别统计");
        getOkHttp();
        
    }
    
    private void getOkHttp() {
        if (getCollegeMenAndWomenNumberAlls == null){
            getCollegeMenAndWomenNumberAlls = new ArrayList<>();
        }else {
            getCollegeMenAndWomenNumberAlls.clear();
        }
        new OkHttpTo()
            .setUrl("GetCollegeMenAndWomenNumberAll")
            .setRequestType("GET")
            .setDialog(getContext())
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    getCollegeMenAndWomenNumberAlls.addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                        new TypeToken<List<GetCollegeMenAndWomenNumberAll>>(){}.getType()));
                    Show();
                }
    
                @Override
                public void onFailure(IOException e) {
        
                }
            }).start();
    
    
    }
    
    private void Show() {
        School_sex_adapter adapter = new School_sex_adapter(getCollegeMenAndWomenNumberAlls);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            ((FragmentActivity)getActivity()).setFragment(new Fragment_count_school(getCollegeMenAndWomenNumberAlls.get(position)));
        });
    
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
