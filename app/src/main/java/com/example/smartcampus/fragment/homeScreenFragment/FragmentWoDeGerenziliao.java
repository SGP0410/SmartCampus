package com.example.smartcampus.fragment.homeScreenFragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.Major;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.example.smartcampuslibrary.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class FragmentWoDeGerenziliao extends BaseFragment {

    private TextView title;
    private TextView txtSchoolcard;
    private TextView txtName;
    private TextView txtSex;
    private TextView txtAge;
    private TextView txtCollege;
    private TextView major;
    private TextView data;
    private TextView txtGrade;
    private TextView txtNation;
    private TextView txtFace;
    private EditText address;
    private ImageView back;
    private TextView txtCarid;
    private LinearLayout linZhuanye;
    private LinearLayout linGrade;
    private LinearLayout linClass;
    private TextView txtClass;
    private Button btnSave;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_geren;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        txtSchoolcard = view.findViewById(R.id.txt_schoolcard);
        txtName = view.findViewById(R.id.txt_name);
        txtSex = view.findViewById(R.id.txt_sex);
        txtAge = view.findViewById(R.id.txt_age);
        txtCollege = view.findViewById(R.id.txt_college);
        major = view.findViewById(R.id.major);
        data = view.findViewById(R.id.data);
        txtGrade = view.findViewById(R.id.txt_grade);
        txtNation = view.findViewById(R.id.txt_nation);
        txtFace = view.findViewById(R.id.txt_face);
        address = view.findViewById(R.id.address);
        txtCarid = view.findViewById(R.id.txt_carid);
        linZhuanye = view.findViewById(R.id.lin_zhuanye);
        linGrade = view.findViewById(R.id.lin_grade);
        linClass = view.findViewById(R.id.lin_class);
        txtClass = view.findViewById(R.id.txt_class);
        btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
    }

    private static final String TAG = "FragmentWoDeGerenziliao";

    @Override
    protected void initData() {
        title.setText("????????????");
        User user = Application.getUser();
        txtName.setText(user.getName());
        txtSchoolcard.setText(user.getSchoolCard());
        txtSex.setText(user.getSex());
        txtAge.setText(user.getAge());
        txtCollege.setText(user.getCollegName());
        data.setText(user.getDateOfBirth());
        txtNation.setText(user.getNationality());
        txtFace.setText(user.getFace());
        address.setText(user.getAddress());
        txtCarid.setText(user.getIdCard());


        if ("??????".equals(user.getStatus())) {
            List<Major> majors = Application.getMajors();
            String grade = "";
            for (Major major1 : majors) {
                if (user.getMajorId().equals(major1.getId())) {
                    grade = major1.getMajorName();
                    break;
                }
            }
            txtGrade.setText(user.getGrade());
            major.setText(grade);
            txtClass.setText(grade + user.getClas());
        } else {
            linClass.setVisibility(View.GONE);
            linGrade.setVisibility(View.GONE);
            linZhuanye.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View view) {
        new OkHttpTo().setUrl("UpdateStudentAddress")
                .setRequestType("post")
                .setJSONObject("address" , address.getText().toString())
                .setJSONObject("id" , Application.getUser().getId())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (jsonObject.optString("code").equals("200")){
                            Utils.showToast("????????????");
                            Application.getUser().setAddress(address.getText().toString());
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }
}
