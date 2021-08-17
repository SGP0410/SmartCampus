package com.example.smartcampus.fragment.My;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.wodeeAdapter.WoDeviewCertificationPoorAdapter;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.Approve;
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
 * @date 2021/8/11 8:41 星期三
 */
public class FragmentWoDeviewCertificationPoor extends BaseFragment {

    private ImageView back;
    private TextView title;
    private List<Approve> approves,approveList;
    private TextView txtZanwu;
    private RecyclerView recyclerView;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_certification_poor;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        txtZanwu = view.findViewById(R.id.txt_zanwu);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        title.setText("贫困生认证");
        getokhttp();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        getokhttp();
    }

    private static final String TAG = "FragmentWoDeviewCertifi";

    private void getokhttp() {
        if (approves == null) {
            approves = new ArrayList<>();
            approveList = new ArrayList<>();
        } else {
            approves.clear();
            approveList.clear();
        }
        User user = Application.getUser();
        new OkHttpTo()
                .setUrl("GetApproveMajorId")
                .setRequestType("post")
                .setJSONObject("majorId", user.getCourse())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        approves.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<Approve>>() {
                                }.getType()));
                        for (Approve approve : approves) {
                            if ("贫困生申请".equals(approve.getTitle())){
                                approveList.add(approve);
                            }
                        }
                        if (approveList.size() == 0) {
                            txtZanwu.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }else {
                            txtZanwu.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            show();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void show() {
        WoDeviewCertificationPoorAdapter adapter = new WoDeviewCertificationPoorAdapter(approveList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            Approve approve = approveList.get(position);
            ((FragmentActivity)getActivity()).setFragment(new FragmentCertificationPoorMsg(approve));
        });
    }

    @Override
    public void onClick(View v) {

    }
}
