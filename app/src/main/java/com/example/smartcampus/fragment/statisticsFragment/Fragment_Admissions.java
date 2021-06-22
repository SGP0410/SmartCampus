package com.example.smartcampus.fragment.statisticsFragment;

import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.GetMunicipalMenAndWomenNumberAll;
import com.example.smartcampus.bean.statistics.Province;
import com.example.smartcampus.bean.statistics.Provinces;
import com.example.smartcampus.utils.MapView;
import com.example.smartcampus.utils.destureviewbinder.GestureViewBinder;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class Fragment_Admissions extends BaseFragment {
    
    private ImageView back;
    private TextView title;
    private MapView mapView;
    private LinearLayout groupView;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_admissions;
    }
    
    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        mapView = view.findViewById(R.id.map_view);
        groupView = view.findViewById(R.id.group_view);
    }
    
    @Override
    protected void initData() {
        title.setText("招生信息");
        
        GestureViewBinder binder = GestureViewBinder.bind(getContext(), groupView, mapView);
        binder.setFullGroup(true);
        mapView.setOnMapViewClickListener(name -> {
            Log.i("aaaaa", "--------" + name);
            RectF rectF = mapView.getRectF(name);
            initPopWindow((int) rectF.centerX(), (int) rectF.bottom);
        });
        
        getData();
        
    }
    
    private List<Provinces> provinceList;
    
    private void getData() {
        if (provinceList == null) {
            provinceList = new ArrayList<>();
        } else {
            provinceList.clear();
        }
        new OkHttpTo()
            .setUrl("province_query_all")
            .setRequestType("GET")
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    provinceList.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                        new TypeToken<List<Provinces>>() {
                        }.getType()));
                    if (provinceList != null) {
                        Province_GetMunicipalMenAndWomenNumberAll();
                    }
                }
                
                @Override
                public void onFailure(IOException e) {
                
                }
            }).start();
        
        
    }
    
    private int count = 0;
    private static final String TAG = "Fragment_Admissions";
    
    private void Province_GetMunicipalMenAndWomenNumberAll() {
        count = 0;
        for (Provinces provinces : provinceList) {
            new OkHttpTo()
                .setUrl("GetMunicipalMenAndWomenNumberAll?provinceName=" + provinces.getProvinceName())
                .setRequestType("GET")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        
                        List<GetMunicipalMenAndWomenNumberAll> getMunicipalMenAndWomenNumberAlls =
                            new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                                new TypeToken<List<GetMunicipalMenAndWomenNumberAll>>(){}.getType());
                        provinces.setGetMunicipalMenAndWomenNumberAll(getMunicipalMenAndWomenNumberAlls);
                        
                        count++;
                        Log.i(TAG, "onResponse: " + count);
                        if (count == provinceList.size()) {
                            showData();
                        }
                    }
                    
                    @Override
                    public void onFailure(IOException e) {
                    
                    }
                }).start();
        }
        
    }
    
    private List<Integer> colorList1 = new ArrayList<>();
    private String[] colors1 = {"#f4a235", "#f8b751", "#ebba46", "#efc800", "#f1cc3e", "#f5e84c", "#f5e84c"};
    
    
    private void showData() {
        for (Provinces provinces : provinceList) {
            colorList1.add(Color.parseColor("#DDDDDD"));
        }
        
        Map<String, Integer> colorMap = new HashMap<>();
        for (int i = 0; i < colorList1.size(); i++) {
            colorMap.put(provinceList.get(i).getProvinceName(), colorList1.get(i));
        }
        mapView.setColors(colorMap);
        mapView.setMapResId(R.raw.china);
        
        
    }
    
    private void initPopWindow(int x, int y) {
        PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(500);
        popupWindow.setHeight(200);
        //设置布局
//        popupWindow.setContentView(textView);
//        setTouchListener(popupWindow);
        
        popupWindow.showAsDropDown(groupView,
            (int) (x * mapView.getMScale()) - (popupWindow.getWidth() / 2),
            (int) (y * mapView.getMScale()) - groupView.getHeight(),
            Gravity.CENTER);
    }
    
    
    @Override
    public void onClick(View v) {
    
    }
    
}
