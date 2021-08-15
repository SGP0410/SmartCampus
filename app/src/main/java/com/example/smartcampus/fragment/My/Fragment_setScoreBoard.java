package com.example.smartcampus.fragment.My;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Student;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;

import org.json.JSONObject;

import java.io.IOException;

/**
 * @author 关鑫
 * @date 2021/8/10 15:38 星期二
 */
public class Fragment_setScoreBoard extends BaseFragment {

    private Student student;
    private ImageView back;
    private TextView title;
    private EditText edYu;
    private EditText edShu;
    private EditText edWai;
    private Button btnSave;

    public Fragment_setScoreBoard(Student student) {
        super();
        this.student = student;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_setscore_board;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        edYu = view.findViewById(R.id.ed_yu);
        edShu = view.findViewById(R.id.ed_shu);
        edWai = view.findViewById(R.id.ed_wai);
        btnSave = view.findViewById(R.id.btn_save);
    }

    @Override
    protected void initData() {
        title.setText("成绩修改");
        edShu.setText(student.getShu());
        edWai.setText(student.getWai());
        edYu.setText(student.getYu());

        btnSave.setOnClickListener(v -> {
            String shu = edShu.getText().toString();
            String yu = edYu.getText().toString();
            String wai = edWai.getText().toString();
            if ("".equals(yu)){
                Toast.makeText(getContext(),"语文成绩不能为空",Toast.LENGTH_SHORT).show();
            }else if ("".equals(shu)){
                Toast.makeText(getContext(),"数学成绩不能为空",Toast.LENGTH_SHORT).show();
            }else if ("".equals(wai)){
                Toast.makeText(getContext(),"英语成绩不能为空",Toast.LENGTH_SHORT).show();
            }else {
                setOkHttp(yu,shu,wai);
            }
        });

    }

    private static final String TAG = "Fragment_setScoreBoard";

    private void setOkHttp(String yu, String shu, String wai) {
        new OkHttpTo()
                .setUrl("SetStudentUpdateId")
                .setRequestType("post")
                .setJSONObject("yu",yu)
                .setJSONObject("shu",shu)
                .setJSONObject("wai",wai)
                .setJSONObject("id",student.getId())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        String msg = jsonObject.optString("msg");
                        if ("操作成功".equals(msg)){
                            Toast.makeText(getContext(),"修改成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(),"修改失败",Toast.LENGTH_SHORT).show();
                        }
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
