package com.example.smartcampus.fragment.My;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.R;
import com.example.smartcampus.adapter.wodeeAdapter.StringSpinnerAdapter;
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
 * @date 2021/8/16 14:41 星期一
 */
public class FragmentWoDeCertificateRZMS extends BaseFragment {

    private Certificate certificate;
    private ImageView back;
    private TextView title;
    private TextView titles;
    private TextView name;
    private TextView member;
    private TextView level;
    private TextView grade;
    private TextView teacher;
    private TextView type;
    private Spinner spinner;
    private TextView time;
    private Button btnSave;
    private List<String> strings;
    private String S_type = "已通过";

    public FragmentWoDeCertificateRZMS(Certificate certificate) {
        this.certificate = certificate;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_certificaterzmsg;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        titles = view.findViewById(R.id.titles);
        name = view.findViewById(R.id.name);
        member = view.findViewById(R.id.member);
        level = view.findViewById(R.id.level);
        grade = view.findViewById(R.id.grade);
        teacher = view.findViewById(R.id.teacher);
        type = view.findViewById(R.id.type);
        spinner = view.findViewById(R.id.spinner);
        time = view.findViewById(R.id.time);
        btnSave = view.findViewById(R.id.btn_save);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        title.setText("证书认证");
        name.setText("姓名："+certificate.getName());
        titles.setText(certificate.getTitle());
        member.setText("获奖等级："+certificate.getMember());
        level.setText("颁奖单位："+certificate.getLevel());
        grade.setText("成员："+certificate.getGrade());
        teacher.setText("指导老师："+certificate.getTeacher());
        time.setText("申请时间："+certificate.getTime());
        strings = new ArrayList<>();
        strings.add("已通过");
        strings.add("未通过");
        StringSpinnerAdapter adapter = new StringSpinnerAdapter(getContext(),strings);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                S_type = strings.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(v -> {
            new OkHttpTo()
                    .setUrl("SetCertificateUpdate")
                    .setRequestType("post")
                    .setJSONObject("state",S_type)
                    .setJSONObject("id",certificate.getId())
                    .setOkHttpLo(new OkHttpLo() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            String string = jsonObject.optString("msg");
                            if ("操作成功".equals(string)){
                                Toast.makeText(getContext(),"操作成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(),"操作失败",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(IOException e) {

                        }
                    }).start();
        });

    }

    private static final String TAG = "FragmentWoDeCertificate";

    @Override
    public void onClick(View v) {

    }
}
