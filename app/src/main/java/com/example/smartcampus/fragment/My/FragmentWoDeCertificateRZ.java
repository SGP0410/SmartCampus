package com.example.smartcampus.fragment.My;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.wodeeAdapter.WoDeCertificateRZAdapter;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.Certificate;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/16 10:36 星期一
 */
public class FragmentWoDeCertificateRZ extends BaseFragment {

    private ImageView back;
    private TextView title;
    private RecyclerView recyclerView;
    private List<Certificate> certificates;
    private TextView txtNo;
    private Certificate certificate;


    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_certificaterz;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
        txtNo = view.findViewById(R.id.txt_no);
    }

    @Override
    protected void initData() {
        title.setText("证书认证");
        getOkHttp();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getOkHttp();
    }

    private void getOkHttp() {
        User user = Application.getUser();
        if (certificates == null) {
            certificates = new ArrayList<>();
        } else {
            certificates.clear();
        }
        new OkHttpTo()
                .setUrl("GetCertificateClassid")
                .setRequestType("post")
                .setJSONObject("classid", user.getCourse())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        certificates.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<Certificate>>() {
                                }.getType()));
                        if (certificates.size() == 0){

                        }else {
                            txtNo.setVisibility(View.GONE);
                            show();
                        }

                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void show() {
        WoDeCertificateRZAdapter adapter = new WoDeCertificateRZAdapter(certificates);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            ((FragmentActivity) getActivity()).setFragment(new FragmentWoDeCertificateRZMS(certificates.get(position)));
        });
    }

    @Override
    public void onClick(View v) {

    }
}
