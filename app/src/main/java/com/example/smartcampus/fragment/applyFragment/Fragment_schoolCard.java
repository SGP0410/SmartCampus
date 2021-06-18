package com.example.smartcampus.fragment.applyFragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampuslibrary.fragment.BaseFragment;

public class Fragment_schoolCard extends BaseFragment {
    
    private TextView title;
    private ImageView imgExpensesRecord;
    private TextView txtExpensesRecord;
    private ImageView imgRechargeRecord;
    private TextView txtRechargeRecord;
    private RecyclerView recyclerView;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_schoolcarid;
    }
    
    @Override
    protected void initView(View view) {
        ImageView back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        imgExpensesRecord = view.findViewById(R.id.img_expensesRecord);
        txtExpensesRecord = view.findViewById(R.id.txt_expensesRecord);
        imgRechargeRecord = view.findViewById(R.id.img_rechargeRecord);
        txtRechargeRecord = view.findViewById(R.id.txt_rechargeRecord);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayout btnRechargeRecord = view.findViewById(R.id.btn_recharge_record);
        LinearLayout btnExpensesRecord = view.findViewById(R.id.btn_expenses_record);
        btnRechargeRecord.setOnClickListener(this);
        btnExpensesRecord.setOnClickListener(this);
    }
    
    @Override
    protected void initData() {
        title.setText("校园卡");
//        ((FragmentActivity)getActivity()).setFragment();
    
    
    }
    
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_expenses_record:
                txtExpensesRecord.setTextColor(txtExpensesRecord.getResources().getColor(R.color.blue_title));
                imgExpensesRecord.setImageResource(R.mipmap.expenses_record);
                txtRechargeRecord.setTextColor(txtRechargeRecord.getResources().getColor(R.color.color_title_2));
                imgRechargeRecord.setImageResource(R.mipmap.recharge_record_hui);
                break;
            case R.id.btn_recharge_record:
                txtRechargeRecord.setTextColor(txtRechargeRecord.getResources().getColor(R.color.blue_title));
                imgRechargeRecord.setImageResource(R.mipmap.recharge_record);
                txtExpensesRecord.setTextColor(txtExpensesRecord.getResources().getColor(R.color.color_title_2));
                imgExpensesRecord.setImageResource(R.mipmap.expenses_record_hui);
                break;
        }
    }
}
