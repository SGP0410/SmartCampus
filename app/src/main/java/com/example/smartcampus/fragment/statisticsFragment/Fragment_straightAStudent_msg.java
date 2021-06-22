package com.example.smartcampus.fragment.statisticsFragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.statisticsAdapter.StraightAStudent_adapter;
import com.example.smartcampus.bean.statistics.GetStraightAStudent;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import java.util.ArrayList;
import java.util.List;

public class Fragment_straightAStudent_msg extends BaseFragment {
    
    private final List<GetStraightAStudent> getStraightAStudentList;
    private List<GetStraightAStudent> seeklist = new ArrayList<>();
    private ImageView back;
    private TextView title;
    private RecyclerView recyclerView;
    private EditText edSearch;
    private TextView cancel;
    private InputMethodManager imm;
    
    public Fragment_straightAStudent_msg(List<GetStraightAStudent> getStraightAStudents) {
        this.getStraightAStudentList = getStraightAStudents;
    }
    
    @Override
    protected int layoutResId() {
        return R.layout.adapter_straightastdent_msg;
    }
    
    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
        edSearch = view.findViewById(R.id.ed_search);
        cancel = view.findViewById(R.id.cancel);
    }
    
    @Override
    protected void initData() {
        title.setText("学习情况");
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        seeklist.addAll(getStraightAStudentList);
        
        StraightAStudent_adapter adapter = new StraightAStudent_adapter(seeklist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        
        cancel.setOnClickListener(v -> {
            if (imm != null) {
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
            }
            edSearch.setText("");
        });
        
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String msg = edSearch.getText().toString();
                if (msg.equals("")) {
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                    }
                    seeklist.clear();
                    for (GetStraightAStudent getProvinceMenAndWomenNumberAll : getStraightAStudentList) {
                        seeklist.add(getProvinceMenAndWomenNumberAll);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    for (GetStraightAStudent getProvinceMenAndWomenNumberAll : getStraightAStudentList) {
                        if (getProvinceMenAndWomenNumberAll.getName().contains(msg)) {
                            seeklist.clear();
                            for (GetStraightAStudent provinceMenAndWomenNumberAll : getStraightAStudentList) {
                                if (provinceMenAndWomenNumberAll.getName().contains(msg)) {
                                    seeklist.add(provinceMenAndWomenNumberAll);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            break;
                        }
                    }
                }
                
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            
            }
        });
        
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
