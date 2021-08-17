package com.example.smartcampus.fragment.My;

import android.text.Editable;
import android.text.TextWatcher;
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
 * @date 2021/8/9 8:29 星期一
 */
public class FragmentFeedback extends BaseFragment {
    private ImageView back;
    private TextView title;
    private EditText edFeedback;
    private EditText edMsg;
    private Button btnSave;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_feedback;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        edFeedback = view.findViewById(R.id.ed_feedback);
        edMsg = view.findViewById(R.id.ed_msg);
        btnSave = view.findViewById(R.id.btn_save);
    }

    @Override
    protected void initData() {
        title.setText("问题反馈");
        editonclick();
        btnSave.setOnClickListener(v -> {
            String title = edFeedback.getText().toString();
            String msg = edMsg.getText().toString();
            if ("".equals(title)){
                Toast.makeText(getContext(),"请输入问题类型",Toast.LENGTH_SHORT).show();
            }else if ("".equals(msg)){
                Toast.makeText(getContext(),"请输入您的问题描述",Toast.LENGTH_SHORT).show();
            }else {
                setOkHttp(title,msg);
            }
        });

    }

    private void setOkHttp(String title, String msg) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = simpleDateFormat.format(date);
        User user = Application.getUser();
        new OkHttpTo()
                .setUrl("SetFeedback")
                .setRequestType("post")
                .setJSONObject("title",title)
                .setJSONObject("msg",msg)
                .setJSONObject("time",str)
                .setJSONObject("state","已提交")
                .setJSONObject("schoolCard",user.getSchoolCard())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String s = jsonObject.optString("code");
                        if ("200".equals(s)){
                            Toast.makeText(getContext(),"意见提交成功！",Toast.LENGTH_SHORT).show();
                            edFeedback.setText("");
                            edMsg.setText("");
                            btnSave.setBackgroundResource(R.drawable.btn_hui_r20);
                        }else {
                            Toast.makeText(getContext(),"提交失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();

    }

    private static final String TAG = "FragmentFeedback";

    private void editonclick() {
        edMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0){
                    btnSave.setBackgroundResource(R.drawable.btn_hui_r20);
                }else {
                    btnSave.setBackgroundResource(R.drawable.btn_hong_r20);
                }
                if (count==1){
                    btnSave.setBackgroundResource(R.drawable.btn_hong_r20);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
