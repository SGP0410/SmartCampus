package com.example.smartcampus.fragment.applyFragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampuslibrary.fragment.BaseFragment;

public class Fragment_schoolCard extends BaseFragment {
    
    private ImageView back;
    private TextView title;
    private LinearLayout btnExpensesRecord;
    private ImageView imgExpensesRecord;
    private TextView txtExpensesRecord;
    private LinearLayout btnRechargeRecord;
    private ImageView imgRechargeRecord;
    private TextView txtRechargeRecord;
    private RecyclerView recyclerView;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_schoolcarid;
    }
    
    @Override
    protected void initView(View view) {
        
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        btnExpensesRecord = view.findViewById(R.id.btn_expenses_record);
        btnExpensesRecord.setOnClickListener(this::onClick);
        imgExpensesRecord = view.findViewById(R.id.img_expensesRecord);
        txtExpensesRecord = view.findViewById(R.id.txt_expensesRecord);
        btnRechargeRecord = view.findViewById(R.id.btn_recharge_record);
        imgRechargeRecord = view.findViewById(R.id.img_rechargeRecord);
        txtRechargeRecord = view.findViewById(R.id.txt_rechargeRecord);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnRechargeRecord.setOnClickListener(this::onClick);
    
    }
    
    @Override
    protected void initData() {
//        ((FragmentActivity)getActivity()).setFragment();
    
    }
    
    private static final String TAG = "Fragment_schoolCard";
    
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_expenses_record:
                txtExpensesRecord.setTextColor(txtExpensesRecord.getResources().getColor(R.color.blue_title));
                imgExpensesRecord.setImageResource(R.mipmap.expenses_record_hui);
                break;
            case R.id.btn_recharge_record:
                txtRechargeRecord.setTextColor(txtRechargeRecord.getResources().getColor(R.color.blue_title));
                imgRechargeRecord.setImageResource(R.mipmap.expenses_record_hui);
                break;
        }
    }
}
