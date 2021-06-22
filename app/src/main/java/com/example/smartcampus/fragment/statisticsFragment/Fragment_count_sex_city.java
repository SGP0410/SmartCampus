package com.example.smartcampus.fragment.statisticsFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.statisticsAdapter.Count_sex_city_adapter;
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

public class Fragment_count_sex_city extends BaseFragment {
    
    private GetProvinceMenAndWomenNumberAll getProvinceMenAndWomenNumberAll;
    private ImageView back;
    private RecyclerView recyclerView;
    private EditText edSearch;
    private TextView cancel;
    private Count_sex_city_adapter adapter;
    
    public Fragment_count_sex_city(GetProvinceMenAndWomenNumberAll getProvinceMenAndWomenNumberAll) {
        this.getProvinceMenAndWomenNumberAll = getProvinceMenAndWomenNumberAll;
    }
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_count_sex_city;
    }
    
    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        recyclerView = view.findViewById(R.id.recyclerView);
        edSearch = view.findViewById(R.id.ed_search);
        cancel = view.findViewById(R.id.cancel);
    }
    
    private static final String TAG = "Fragment_count_sex_city";
    
    
    @Override
    protected void initData() {
        
        getOkHttp();
        
        cancel.setOnClickListener(v -> edSearch.setText(""));
        
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String msg = edSearch.getText().toString();
                if (msg.equals("")) {
                    seeklist.clear();
                    for (GetMunicipalMenAndWomenNumberAll getProvinceMenAndWomenNumberAll : getMunicipalMenAndWomenNumberAlls) {
                        seeklist.add(getProvinceMenAndWomenNumberAll);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    for (GetMunicipalMenAndWomenNumberAll getProvinceMenAndWomenNumberAll : getMunicipalMenAndWomenNumberAlls) {
                        if (getProvinceMenAndWomenNumberAll.getMunicipalName().contains(msg)) {
                            seeklist.clear();
                            for (GetMunicipalMenAndWomenNumberAll provinceMenAndWomenNumberAll : getMunicipalMenAndWomenNumberAlls) {
                                if (provinceMenAndWomenNumberAll.getMunicipalName().contains(msg)) {
                                    seeklist.add(provinceMenAndWomenNumberAll);
                                    Log.i(TAG, "onTextChanged: " + seeklist.size());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            break;
                        }
                    }
                }
                
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            
            }
        });
        
    }
    
    private List<GetMunicipalMenAndWomenNumberAll> getMunicipalMenAndWomenNumberAlls, seeklist;
    
    private void getOkHttp() {
        if (getMunicipalMenAndWomenNumberAlls == null) {
            getMunicipalMenAndWomenNumberAlls = new ArrayList<>();
            seeklist = new ArrayList<>();
        } else {
            seeklist.clear();
            getMunicipalMenAndWomenNumberAlls.clear();
        }
        new OkHttpTo()
            .setUrl("GetMunicipalMenAndWomenNumberAll?provinceName=" + getProvinceMenAndWomenNumberAll
                .getProvinceName())
            .setRequestType("GET")
            .setDialog(getContext())
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    getMunicipalMenAndWomenNumberAlls.addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                            new TypeToken<List<GetMunicipalMenAndWomenNumberAll>>() {
                            }.getType()));
                    seeklist.addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                        new TypeToken<List<GetMunicipalMenAndWomenNumberAll>>() {
                        }.getType()));
                    show();
                }
                
                @Override
                public void onFailure(IOException e) {
                
                }
            }).start();
        
        
    }
    
    private void show() {
        adapter = new Count_sex_city_adapter(seeklist);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            ((FragmentActivity) getActivity())
                .setFragment(new Fragment_count_sex_city_count(seeklist.get(position)));
        });
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
