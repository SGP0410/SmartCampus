package com.example.smartcampus.fragment.My;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.R;
import com.example.smartcampus.adapter.wodeeAdapter.StringSpinnerAdapter;
import com.example.smartcampus.bean.statistics.Approve;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/11 10:51 星期三
 */
public class FragmentCertificationPoorMsg extends BaseFragment {

    private Approve approve;
    private TextView title;
    private TextView itemName;
    private TextView itemMsg;
    private Spinner spinner;
    private TextView itemTime;
    private Button btnSave;
    private List<String> strings = new ArrayList<>();
    private String status = "审核中";

    public FragmentCertificationPoorMsg(Approve approve) {
        this.approve = approve;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragmentcertificationpoormsg;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        itemName = view.findViewById(R.id.item_name);
        itemMsg = view.findViewById(R.id.item_msg);
        spinner = view.findViewById(R.id.spinner);
        itemTime = view.findViewById(R.id.item_time);
        btnSave = view.findViewById(R.id.btn_save);
    }

    private static final String TAG = "FragmentCertificationPo";

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        title.setText("贫困生认证");
        strings.add("审核中");
        strings.add("已通过");
        strings.add("未通过");
        itemMsg.setText(approve.getMsg());
        itemName.setText("姓名：" + approve.getGrade());
        itemTime.setText("申请时间：" + approve.getTime());

        StringSpinnerAdapter adapter = new StringSpinnerAdapter(getContext(), strings);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = strings.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSave.setOnClickListener(v -> {
            new OkHttpTo()
                    .setUrl("SetApproveSetState")
                    .setRequestType("post")
                    .setJSONObject("state", status)
                    .setJSONObject("id", approve.getId())
                    .setOkHttpLo(new OkHttpLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d(TAG, "onResponse: "+jsonObject.toString());
                            if ("已通过".equals(status)){
                                setOkhttp("1");
                            }else {
                                setOkhttp("0");
                            }
                        }

                        @Override
                        public void onFailure(IOException e) {

                        }
                    }).start();
        });


    }

    private void setOkhttp(String s) {
        new OkHttpTo()
                .setUrl("SetStudentPoverty")
                .setRequestType("post")
                .setJSONObject("poverty",s)
                .setJSONObject("schoolCard",approve.getSchoolCard())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String string = jsonObject.optString("msg");
                        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    @Override
    public void onClick(View v) {

    }
}
