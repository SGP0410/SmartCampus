package com.example.smartcampus.fragment.My;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.wodeeAdapter.WoDeviewCertificationAdapter;
import com.example.smartcampuslibrary.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/11 8:29 星期三
 */
public class FragmentWoDeviewCertification extends BaseFragment {

    private ImageView back;
    private TextView title;
    private RecyclerView recyclerView;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_wode_certification;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    protected void initData() {
        title.setText("查看认证");
        List<String> strings = new ArrayList<>();
        strings.add("贫困生认证");
        strings.add("就业认证");
        strings.add("证书认证");

        WoDeviewCertificationAdapter adapter = new WoDeviewCertificationAdapter(strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            String s = strings.get(position);
            if ("贫困生认证".equals(s)) {
                ((FragmentActivity) getActivity()).setFragment(new FragmentWoDeviewCertificationPoor());
            } else if ("就业认证".equals(s)) {
                ((FragmentActivity) getActivity()).setFragment(new FragmentWoDeviewCertificationgetAJob());
            } else if ("证书认证".equals(s)) {
                ((FragmentActivity) getActivity()).setFragment(new FragmentWoDeCertificateRZ());
            } else {
                return;
            }

        });

    }

    @Override
    public void onClick(View v) {

    }
}
