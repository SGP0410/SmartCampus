package com.example.smartcampus.fragment.statisticsFragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampuslibrary.fragment.BaseFragment;

public class Fragment_count extends BaseFragment {
    
    private ImageView back;
    private TextView title;
    private LinearLayout btnArea;
    private LinearLayout btnCollege;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_count;
    }
    
    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        btnArea = view.findViewById(R.id.btn_area);
        btnCollege = view.findViewById(R.id.btn_college);
    }
    
    @Override
    protected void initData() {
        title.setText("性别统计");
        btnArea.setOnClickListener(v -> {
            ((FragmentActivity) getActivity()).setFragment(new Fragment_count_sex());
        });
        
        btnCollege.setOnClickListener(v -> {
            ((FragmentActivity) getActivity()).setFragment(new Fragment_school_sex());
        });
        
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
