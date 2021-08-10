package com.example.smartcampus.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.User;
import com.example.smartcampus.bean.statistics.College;
import com.example.smartcampus.bean.statistics.Major;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private RadioGroup radioGroup;
    private RadioButton radStudent;
    private RadioButton radTeacher;
    private Button btnLogin;
    private String identity = "学生";
    private List<Major> majors;     //专业
    private List<College> colleges; //系

    private static final String TAG = "LoginActivity";
    private String line = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getOkHttp();
        radio();
        login();

    }

    private void getOkHttp() {
        new OkHttpTo()
                .setUrl("getMajor_query_all")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(TAG, "onResponse: "+jsonObject.toString());
                        majors = new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<Major>>(){}.getType());
                        Application.setMajors(majors);
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();


        new OkHttpTo()
                .setUrl("GetCollegeAll")
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        colleges = new Gson().fromJson(jsonObject.optJSONArray("data").toString(),
                                new TypeToken<List<College>>(){}.getType());
                        Application.setColleges(colleges);
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();

    }

    private void login() {
        btnLogin.setOnClickListener(view -> {
            String name = username.getText().toString();
            String pass = password.getText().toString();
            if ("".equals(name) || "".equals(pass)) {
                Toast.makeText(LoginActivity.this,"学号或密码不能为空",Toast.LENGTH_SHORT).show();
            }else {
                if ("学生".equals(identity)){
                    getStudentID(name,pass);
                }else {
                    getTeacherID(name,pass);
                }
            }

        });
    }

    private void getTeacherID(String name, String pass) {
        new OkHttpTo()
                .setUrl("GetTeacherQuerySchoolCard?card="+name)
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String msg = jsonObject.optString("msg");
                        if ("操作成功".equals(msg)){
                            if (pass.equals(jsonObject.optString("password"))){
                                User user = new Gson().fromJson(jsonObject.toString(),User.class);
                                String collegeId = jsonObject.optString("collegeId");
                                for (College college : colleges) {
                                    if (collegeId.equals(college.getCollegeId())){
                                        line = college.getCollegeName();
                                        Log.d(TAG, "onResponse: "+line);
                                    }
                                }
                                user.setStatus("老师");
                                user.setCollegName(line);
                                Application.setUser(user);
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this,"密码输入错误",Toast.LENGTH_SHORT).show();
                                password.setText("");
                            }
                        }else {
                            Toast.makeText(LoginActivity.this,"没有查到此学号",Toast.LENGTH_SHORT).show();
                            username.setText("");
                            password.setText("");
                        }

                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void getStudentID(String name, String pass) {
        new OkHttpTo()
                .setUrl("GetStudentSchoolCard?card="+name)
                .setRequestType("get")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String msg = jsonObject.optString("msg");
                        if ("操作成功".equals(msg)){
                            if (pass.equals(jsonObject.optString("password"))){
                                String majorId = jsonObject.optString("majorId");
                                for (Major major : majors) {
                                    if (majorId.equals(major.getId())){
                                        for (College college : colleges) {
                                            if (major.getCollegeId().equals(college.getCollegeId())){
                                                line = college.getCollegeName();
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                User user = new Gson().fromJson(jsonObject.toString(),User.class);
                                user.setStatus("学生");
                                user.setCollegName(line);
                                Application.setUser(user);
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this,"密码输入错误",Toast.LENGTH_SHORT).show();
                                password.setText("");
                            }
                        }else {
                            Toast.makeText(LoginActivity.this,"没有查到此学号",Toast.LENGTH_SHORT).show();
                            username.setText("");
                            password.setText("");
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();



    }

    private void radio() {
        radStudent.setChecked(true);
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rad_student) {
                identity = "学生";
            } else {
                identity = "老师";
            }
        });
    }

    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        radioGroup = findViewById(R.id.radioGroup);
        radStudent = findViewById(R.id.rad_student);
        radTeacher = findViewById(R.id.rad_teacher);
        btnLogin = findViewById(R.id.btn_login);
    }
}