package com.example.smartcampus.fragment.My;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.adapter.wodeeAdapter.CustomerFeedbackAdapter;
import com.example.smartcampus.bean.statistics.Feedback;
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
 * @date 2021/8/17 8:58 星期二
 */
public class FragmentWoDeCustomerFeedback extends BaseFragment {

    private ImageView back;
    private TextView title;
    private RecyclerView recyclerView;
    private List<Feedback> feedbackList;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_customer;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        title.setText("用户反馈");
        getOkHttp();

    }

    private static final String TAG = "FragmentWoDeCustomerFee";

    private void getOkHttp() {
        if (feedbackList == null){
            feedbackList = new ArrayList<>();
        }else {
            feedbackList.clear();
        }
        new OkHttpTo()
                .setUrl("GetFeedback")
                .setRequestType("GET")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, "onResponse: "+jsonObject.toString());
                        feedbackList.addAll(new Gson().fromJson(jsonObject.optString("data").toString(),
                                new TypeToken<List<Feedback>>(){}.getType()));
                        show();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void show() {
        CustomerFeedbackAdapter adapter = new CustomerFeedbackAdapter(feedbackList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
