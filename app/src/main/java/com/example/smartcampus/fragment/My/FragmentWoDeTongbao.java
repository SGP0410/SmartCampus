package com.example.smartcampus.fragment.My;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.R;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 关鑫
 * @date 2021/8/21 8:27 星期六
 */
public class FragmentWoDeTongbao extends BaseFragment {

    private ImageView back;
    private TextView title;
    private EditText edTitle;
    private EditText edSuozaixi;
    private Button btnSave;
    private EditText edMsg;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_tongbao;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        edTitle = view.findViewById(R.id.ed_title);
        edSuozaixi = view.findViewById(R.id.ed_suozaixi);
        btnSave = view.findViewById(R.id.btn_save);
        edMsg = view.findViewById(R.id.ed_msg);
    }

    @Override
    protected void initData() {
        title.setText("通报");
        btnSave.setOnClickListener(v -> {
            String edtitle = edTitle.getText().toString();
            String suozaixi = edSuozaixi.getText().toString();
            String msg = edMsg.getText().toString();
            if ("".equals(edtitle)) {
                Toast.makeText(getContext(), "标题不能为空", Toast.LENGTH_SHORT).show();
            } else if ("".equals(msg)) {
                Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
            } else if ("".equals(suozaixi)) {
                Toast.makeText(getContext(), "请输入所在系", Toast.LENGTH_SHORT).show();
            } else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());;
                String time = format.format(date);
                new OkHttpTo()
                        .setUrl("SetBulletin")
                        .setRequestType("post")
                        .setJSONObject("title",edtitle)
                        .setJSONObject("msg",msg)
                        .setJSONObject("faculty",suozaixi)
                        .setJSONObject("time",time)
                        .setOkHttpLo(new OkHttpLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                String msg1 = jsonObject.optString("msg");
                                if ("操作成功".equals(msg1)){
                                    Toast.makeText(getContext(),"提交成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getContext(),"提交失败",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(IOException e) {

                            }
                        }).start();
            }

        });


    }

    @Override
    public void onClick(View v) {

    }
}
