package com.example.smartcampus.fragment.My;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.Certificate;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/12 15:10 星期四
 */
public class FragmentWoDecertificate extends BaseFragment {

    private String status;
    private TextView title;
    private RecyclerView recyclerView;
    private ImageView back;
    private TextView txtNO;
    private List<Certificate> certificateList;

    public FragmentWoDecertificate(String status) {
        this.status = status;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_certificate;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
        txtNO = view.findViewById(R.id.txt_NO);
    }

    @Override
    protected void initData() {
        if ("学生".equals(status)) {
            User user = Application.getUser();
            if (certificateList==null){
                certificateList = new ArrayList<>();
            }else {
                certificateList.clear();
            }
             new OkHttpTo()
                     .setUrl("GetCertificateSchoolCard")
                     .setRequestType("post")
                     .setJSONObject("schoolcard",user.getSchoolCard())
                     .setOkHttpLo(new OkHttpLo() {
                         @Override
                         public void onResponse(JSONObject jsonObject) {

                         }

                         @Override
                         public void onFailure(IOException e) {

                         }
                     }).start();
        }

    }

    @Override
    public void onClick(View v) {

    }
}
