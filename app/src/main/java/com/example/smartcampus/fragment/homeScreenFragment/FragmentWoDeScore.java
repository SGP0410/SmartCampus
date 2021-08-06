package com.example.smartcampus.fragment.homeScreenFragment;

import android.view.View;
import android.widget.TextView;

import com.example.smartcampus.Application;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.User;
import com.example.smartcampuslibrary.fragment.BaseFragment;

public class FragmentWoDeScore extends BaseFragment {
    private TextView title;
    private TextView tvYuwen;
    private TextView tvShuxue;
    private TextView tvYingyu;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_score;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        tvYuwen = view.findViewById(R.id.tv_yuwen);
        tvShuxue = view.findViewById(R.id.tv_shuxue);
        tvYingyu = view.findViewById(R.id.tv_yingyu);
    }

    @Override
    protected void initData() {
        title.setText("我的成绩");
        User user = Application.getUser();
        tvYuwen.setText(user.getYu());
        tvShuxue.setText(user.getShu());
        tvYingyu.setText(user.getWai());
    }

    @Override
    public void onClick(View view) {

    }
}
