package com.example.smartcampus.fragment.statisticsFragment;

import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.smartcampus.R;
import com.example.smartcampuslibrary.fragment.BaseFragment;

public class MunicipalStudentSourceFragment extends BaseFragment {

    private String name;
    public MunicipalStudentSourceFragment(String name) {
        this.name = name;
    }

    @Override
    protected int layoutResId() {
        return R.layout.student_source_fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
