package com.example.smartcampus.fragment.My;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.wodeeAdapter.StringSpinnerAdapter;
import com.example.smartcampus.bean.User;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/12 16:14 星期四
 */
public class FragmentCertificate extends BaseFragment {

    private ImageView back;
    private TextView title;
    private EditText edPersonnel;
    private RadioButton radGuojia;
    private RadioButton radSheng;
    private RadioButton radXiao;
    private Spinner spinner;
    private EditText edTeacher;
    private EditText edTime;
    private Button btnSave;
    private RadioGroup radioGroup;
    private String level;
    private String member = "特等奖";
    private EditText edTitle;
    private static final String TAG = "FragmentCertificate";

    @Override
    protected int layoutResId() {
        return R.layout.fragment_certificate;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        edPersonnel = view.findViewById(R.id.ed_personnel);
        radGuojia = view.findViewById(R.id.rad_guojia);
        radSheng = view.findViewById(R.id.rad_sheng);
        radXiao = view.findViewById(R.id.rad_xiao);
        spinner = view.findViewById(R.id.spinner);
        edTeacher = view.findViewById(R.id.ed_teacher);
        edTime = view.findViewById(R.id.ed_time);
        btnSave = view.findViewById(R.id.btn_save);
        radioGroup = view.findViewById(R.id.radioGroup);
        edTitle = view.findViewById(R.id.ed_title);
    }

    @Override
    protected void initData() {
        title.setText("证书申请");
        level = "国家级";
        SpinnerData();
        radGuojia.setChecked(true);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rad_guojia) {
                level = "国家级";
            } else if (checkedId == R.id.rad_sheng) {
                level = "省级";
            } else {
                level = "校级";
            }
        });

        btnSave.setOnClickListener(v -> {
            String title = edTitle.getText().toString();
            String personnel = edPersonnel.getText().toString();
            String teacher = edTeacher.getText().toString();
            String time = edTime.getText().toString();
            if ("".equals(title)){
                Toast.makeText(getContext(),"请输入活动名称",Toast.LENGTH_SHORT).show();
            }else if ("".equals(personnel)){
                Toast.makeText(getContext(),"请输入参赛成员",Toast.LENGTH_SHORT).show();
            }else if ("".equals(teacher)){
                Toast.makeText(getContext(),"请输入指导老师",Toast.LENGTH_SHORT).show();
            }else if ("".equals(time)){
                Toast.makeText(getContext(),"请输入获奖时间",Toast.LENGTH_SHORT).show();
            }else {
                User user = Application.getUser();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(System.currentTimeMillis());
                String str = format.format(date);
                Log.d(TAG, "initData: "+str);
                new OkHttpTo()
                        .setUrl("SetCertificateAdd")
                        .setRequestType("post")
                        .setJSONObject("title",title)
                        .setJSONObject("level",level)
                        .setJSONObject("time",str)
                        .setJSONObject("member",member)
                        .setJSONObject("grade",user.getGrade())
                        .setJSONObject("teacher",teacher)
                        .setJSONObject("years",time)
                        .setJSONObject("name",user.getName())
                        .setJSONObject("schoolcard",user.getSchoolCard())
                        .setJSONObject("state","审核中")
                        .setJSONObject("classid",user.getClassid())
                        .setOkHttpLo(new OkHttpLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.d(TAG, "onResponse: "+jsonObject.toString());
                                Toast.makeText(getContext(),"提交成功",Toast.LENGTH_SHORT).show();
                                edPersonnel.setText("");
                                edTeacher.setText("");
                                edTime.setText("");
                                edTitle.setText("");

                            }

                            @Override
                            public void onFailure(IOException e) {

                            }
                        }).start();
            }
        });

    }



    private void SpinnerData() {
        List<String> strings = new ArrayList<>();
        strings.add("特等奖");
        strings.add("一等奖");
        strings.add("二等奖");
        strings.add("三等奖");

        StringSpinnerAdapter adapter = new StringSpinnerAdapter(getContext(), strings);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                member = strings.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
