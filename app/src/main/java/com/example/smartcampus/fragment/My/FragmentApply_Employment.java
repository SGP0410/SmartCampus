package com.example.smartcampus.fragment.My;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.wodeeAdapter.Apply_Employment_adapter;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.WorkNature;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/7 9:09 星期六
 */
public class FragmentApply_Employment extends BaseFragment {

    private ImageView back;
    private TextView title;
    private Spinner spinner;
    private Button btnSave;
    private List<WorkNature> list = new ArrayList<>();
    private String workname;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_employment;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        spinner = view.findViewById(R.id.spinner);
        btnSave = view.findViewById(R.id.btn_save);
    }

    @Override
    protected void initData() {
        title.setText("就业单位认证");
        getOKHttp();
        btnSpinner();
        btnSave.setOnClickListener(view -> {
            setOkHttp();
        });
    }

    private void setOkHttp() {
        User user = Application.getUser();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String str = simpleDateFormat.format(date);

        new OkHttpTo()
                .setUrl("StudentSetWordNatureId")
                .setRequestType("post")
                .setJSONObject("schoolCard",user.getSchoolCard())
                .setJSONObject("wordNatureId",workname)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, "onResponse: "+jsonObject.toString());
                        Toast.makeText(getContext(),"操作成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();

//        new OkHttpTo()
//                .setUrl("GetApproveSetEmployment")
//                .setRequestType("post")
//                .setJSONObject("schoolCard",user.getSchoolCard())
//                .setJSONObject("title","就业认证")
//                .setJSONObject("grade",user.getName())
//                .setJSONObject("time",str)
//                .setJSONObject("clas",user.getClassid())
//                .setJSONObject("msg",workname)
//                .setJSONObject("state","审核中")
//                .setJSONObject("majorId",user.getMajorId())
//                .setJSONObject("clas",user.getClas())
//                .setOkHttpLo(new OkHttpLo() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        String alarm = jsonObject.optString("alarm");
//                        if ("修改成功".equals(alarm)){
//                            Toast.makeText(getContext(),"修改成功",Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(getContext(),"提交成功",Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(IOException e) {
//
//                    }
//                }).start();

    }

    private static final String TAG = "FragmentApply_Employmen";

    private void btnSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                workname = list.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void getOKHttp() {
        new OkHttpTo()
                .setUrl("getWorkNatureName_query_all")
                .setRequestType("GET")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        list.addAll(new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<WorkNature>>(){}.getType()));
                        workname = list.get(0).getWorkNatureName();
                        getSpinner();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();

    }

    private void getSpinner() {
        Apply_Employment_adapter adapter = new Apply_Employment_adapter(getContext(),list);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }
}
