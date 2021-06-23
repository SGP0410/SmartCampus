package com.example.smartcampus.fragment.statisticsFragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.bean.statistics.GetMunicipal_query_all;
import com.example.smartcampus.bean.statistics.GetProvinceRecruitStudentNumber;
import com.example.smartcampus.utils.MapView;
import com.example.smartcampus.utils.destureviewbinder.GestureViewBinder;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_admissions1 extends BaseFragment {
    
    private String name;
    private ImageView back;
    private TextView title;
    private LinearLayout groupView;
    private MapView mapView;
    private ViewFlipper viewFlipper;
    private LinearLayout btnDomestic;
    private ImageView imgDomestic;
    private TextView txtDomestic;
    private LinearLayout btnForeign;
    private ImageView imgForeign;
    private TextView txtForeign;
    private List<GetMunicipal_query_all> getMunicipal_query_alls;
    private List<GetProvinceRecruitStudentNumber> getProvinceRecruitStudentNumbers;
    private String name1;
    
    public Fragment_admissions1(String name, List<GetMunicipal_query_all> getMunicipal_query_alls,
        List<GetProvinceRecruitStudentNumber> getProvinceRecruitStudentNumbers) {
        this.name = name;
        this.getMunicipal_query_alls = getMunicipal_query_alls;
        this.getProvinceRecruitStudentNumbers = getProvinceRecruitStudentNumbers;
    }
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_admissions1;
    }
    
    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        groupView = view.findViewById(R.id.group_view);
        mapView = view.findViewById(R.id.map_view);
        viewFlipper = view.findViewById(R.id.view_flipper);
        btnDomestic = view.findViewById(R.id.btn_domestic);
        imgDomestic = view.findViewById(R.id.img_domestic);
        txtDomestic = view.findViewById(R.id.txt_domestic);
        btnForeign = view.findViewById(R.id.btn_foreign);
        imgForeign = view.findViewById(R.id.img_foreign);
        txtForeign = view.findViewById(R.id.txt_foreign);
    }
    
    @Override
    protected void initData() {
        GestureViewBinder binder = GestureViewBinder.bind(getContext(), groupView, mapView);
        binder.setFullGroup(true);
        mapView.setOnMapViewClickListener(name -> {
            Log.i("aaaaa", "--------" + name);
            RectF rectF = mapView.getRectF(name);
            initPopWindow((int) rectF.centerX(), (int) rectF.bottom);
        });
        
        getData();
        
    }
    
    private List<GetMunicipal_query_all> getMunicipal_query = new ArrayList<>();
    
    private void getData() {
        getMunicipal_query.clear();
        for (GetProvinceRecruitStudentNumber getProvinceRecruitStudentNumber : getProvinceRecruitStudentNumbers) {
            if (getProvinceRecruitStudentNumber.getProvinceName().equals(name)) {
                name1 = getProvinceRecruitStudentNumber.getProvinceId();
            }
        }
        
        for (GetMunicipal_query_all getMunicipal_query_all : getMunicipal_query_alls) {
            if (getMunicipal_query_all.getProvinceId().equals(name1)) {
                getMunicipal_query.add(getMunicipal_query_all);
            }
        }
        
        
    }
    
    @SuppressLint("NewApi")
    private void initPopWindow(int x, int y) {
        PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(500);
        popupWindow.setHeight(200);
        //设置布局
        popupWindow.showAsDropDown(groupView,
            (int) (x * mapView.getMScale()) - (popupWindow.getWidth() / 2),
            (int) (y * mapView.getMScale()) - groupView.getHeight(),
            Gravity.CENTER);
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
