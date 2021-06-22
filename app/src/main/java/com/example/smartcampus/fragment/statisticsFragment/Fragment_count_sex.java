package com.example.smartcampus.fragment.statisticsFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.statisticsAdapter.Count_sex_adapter;
import com.example.smartcampus.bean.statistics.GetProvinceMenAndWomenNumberAll;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

public class Fragment_count_sex extends BaseFragment {
    
    private RecyclerView recyclerView;
    private EditText edSearch;
    private TextView cancel;
    private Count_sex_adapter adapter;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_count_sex;
    }
    
    @Override
    protected void initView(View view) {
        ImageView back = view.findViewById(R.id.back);
        recyclerView = view.findViewById(R.id.recyclerView);
        edSearch = view.findViewById(R.id.ed_search);
        cancel = view.findViewById(R.id.cancel);
    }
    
    @Override
    protected void initData() {
        
        getSeek();
        
        getOkHttp();
        
        cancel.setOnClickListener(v -> edSearch.setText(""));
        
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        
            }
    
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String msg = edSearch.getText().toString();
                if (msg.equals("")){
                    seeklist.clear();
                    for (GetProvinceMenAndWomenNumberAll getProvinceMenAndWomenNumberAll : getProvinceMenAndWomenNumberAlls) {
                        seeklist.add(getProvinceMenAndWomenNumberAll);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    for (GetProvinceMenAndWomenNumberAll getProvinceMenAndWomenNumberAll : getProvinceMenAndWomenNumberAlls) {
                        if (getProvinceMenAndWomenNumberAll.getProvinceName().contains(msg)){
                            seeklist.clear();
                            for (GetProvinceMenAndWomenNumberAll provinceMenAndWomenNumberAll : getProvinceMenAndWomenNumberAlls) {
                                if (provinceMenAndWomenNumberAll.getProvinceName().contains(msg)){
                                    seeklist.add(provinceMenAndWomenNumberAll);
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
    
    private void getSeek() {
        edSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            }
            return true;
        });
        
    }
    
    private int getSpanCount(int itemWidth) {
        DisplayMetrics dm = new DisplayMetrics();
        Objects.requireNonNull(Objects.requireNonNull(getContext()).getDisplay()).getMetrics(dm);
        //获取屏幕宽度
        int widthPixels = dm.widthPixels;
        //获取设备像素密度
        float density = dm.densityDpi;
        //1dp*像素密度(dpi)/160(dpi) = 实际像素数(px)
        int dp = (int) ((widthPixels * 160) / density);
        
        if (itemWidth == 0) {
            return 0;
        }
        //计算出一行显示多少item
        
        double spanCount = ((double) dp) / ((double) itemWidth);
        
        return (int) Math.round(spanCount);
    }
    
    private List<GetProvinceMenAndWomenNumberAll> getProvinceMenAndWomenNumberAlls, seeklist;
    
    private void getOkHttp() {
        if (getProvinceMenAndWomenNumberAlls == null) {
            getProvinceMenAndWomenNumberAlls = new ArrayList<>();
            seeklist = new ArrayList<>();
        } else {
            seeklist.clear();
            getProvinceMenAndWomenNumberAlls.clear();
        }
        new OkHttpTo()
            .setUrl("GetProvinceMenAndWomenNumberAll")
            .setRequestType("GET")
            .setDialog(getContext())
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    getProvinceMenAndWomenNumberAlls
                        .addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                            new TypeToken<List<GetProvinceMenAndWomenNumberAll>>() {
                            }.getType()));
                    
                    seeklist.addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
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
        adapter = new Count_sex_adapter(seeklist);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount(200)));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            ((FragmentActivity) getActivity())
                .setFragment(new Fragment_count_sex_province(seeklist.get(position)));
        });
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
