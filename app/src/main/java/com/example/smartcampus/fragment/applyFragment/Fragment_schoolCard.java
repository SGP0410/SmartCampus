package com.example.smartcampus.fragment.applyFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.apply.TradingRecordRecyclerViewAdapter;
import com.example.smartcampus.bean.apply.TradingRecord;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.example.smartcampuslibrary.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class Fragment_schoolCard extends BaseFragment implements PopupMenu.OnMenuItemClickListener {

    private ImageView back;
    private TextView title;
    private ImageView jiaHao;
    private RecyclerView recyclerView;
    private List<TradingRecord> tradingRecordList;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_schoolcarid;
    }

    @Override
    protected void initView(View view) {
        back = (ImageView) view.findViewById(R.id.back);
        title = (TextView) view.findViewById(R.id.title);
        title.setText("校园卡");
        jiaHao = (ImageView) view.findViewById(R.id.jia_hao);
        jiaHao.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }

    @Override
    protected void initData() {
        getTradingRecordBySchoolCardId();
    }

    private void getTradingRecordBySchoolCardId() {
        new OkHttpTo().setUrl("GetTradingRecordBySchoolCardId?schoolCardId="+ Application.getUser().getSchoolCard())
                .setRequestType("GET")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        tradingRecordList = new Gson().fromJson(jsonObject.optJSONArray("rows").toString() ,
                                new TypeToken<List<TradingRecord>>(){}.getType());
                        if (tradingRecordList != null){
                            getTradingSiteById();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private int y = 0;
    private void getTradingSiteById() {
        for (int i = 0; i < tradingRecordList.size(); i++) {
            final int x = i;
            new OkHttpTo().setUrl("GetTradingSiteById?tradingSiteId="+tradingRecordList.get(i).getTradingSiteId())
                    .setRequestType("get")
                    .setOkHttpLo(new OkHttpLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            tradingRecordList.get(x).setTradingSite(jsonObject.optString("site"));
                            y++;
                            if (y == tradingRecordList.size()){
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
        TradingRecordRecyclerViewAdapter adapter = new TradingRecordRecyclerViewAdapter(tradingRecordList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            ((FragmentActivity)getActivity()).setFragment(new TradingRecordXQ(tradingRecordList.get(position)));
        });
    }

    @SuppressLint({"NonConstantResourceId", "RestrictedApi"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jia_hao:
                PopupMenu popupMenu = new PopupMenu(getContext(), jiaHao);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.xiao_yuan_ka, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(this);
                try {
                    @SuppressLint("DiscouragedPrivateApi") Field field = popupMenu.getClass()
                            .getDeclaredField("mPopup");
                    field.setAccessible(true);
                    try {
                        MenuPopupHelper mHelper = (MenuPopupHelper) field.get(popupMenu);
                        assert mHelper != null;
                        mHelper.setForceShowIcon(true);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                popupMenu.show();
                break;
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sys:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    //Toast.makeText(MainActivity.this,"您申请了动态权限",Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent2,1111);
                }else{
                    //否则去请求相机权限
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},100);
                }
                break;
            case R.id.ewm:
                ((FragmentActivity)getActivity()).setFragment(new XiaoYuanKaEWM());
                break;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1111) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                test_edit_id.setText(content+"");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
