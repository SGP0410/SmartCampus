package com.example.smartcampus.fragment.My;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.User;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 关鑫
 * @date 2021/8/7 9:07 星期六
 */
public class FragmentApplyPorr extends BaseFragment {

    private ImageView back;
    private TextView title;
    private EditText edSearch;
    private Button btnSave;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_apply_porr;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        edSearch = view.findViewById(R.id.ed_search);
        btnSave = view.findViewById(R.id.btn_save);
    }

    @Override
    protected void initData() {
        title.setText("贫困生申请");
        btnSave.setOnClickListener(view -> {
            String string = edSearch.getText().toString();
            if ("".equals(string)){
                Toast.makeText(getContext(),"申请内容不能为空",Toast.LENGTH_SHORT).show();
            }else {
                getOkHttp(string);
            }
        });

    }

    private void getOkHttp(String msg) {
        User user = Application.getUser();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String str = simpleDateFormat.format(date);

        Log.d(TAG, "getOkHttp:user "+user.getMajorId());
        new OkHttpTo()
                .setUrl("setApprove")
                .setRequestType("post")
                .setJSONObject("schoolCard",user.getSchoolCard())
                .setJSONObject("title","贫困生申请")
                .setJSONObject("grade",user.getName())
                .setJSONObject("time",str)
                .setJSONObject("clas",user.getClassid())
                .setJSONObject("msg",msg)
                .setJSONObject("state","审核中")
                .setJSONObject("majorId",user.getMajorId())
                .setJSONObject("clas",user.getClas())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String alarm = jsonObject.optString("alarm");
                        if ("重复提交".equals(alarm)){
                            Toast.makeText(getContext(),"您已提交申请，请勿重复提交",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(),"提交成功",Toast.LENGTH_SHORT).show();
                        }
                        edSearch.setText("");
                        Log.d(TAG, "onResponse: "+jsonObject.toString());
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();


    }

    private static final String TAG = "FragmentApply_Porr";

    @Override
    public void onClick(View view) {

    }
}
