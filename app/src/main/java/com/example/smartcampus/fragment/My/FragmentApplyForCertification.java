package com.example.smartcampus.fragment.My;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampuslibrary.fragment.BaseFragment;

public class FragmentApplyForCertification extends BaseFragment {

    private ImageView back;
    private TextView title;
    private LinearLayout btnPoor;
    private LinearLayout btnEmployment;
    private LinearLayout btnCertificate;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_applyforcertifcation;
    }

    @Override
    protected void initView(View view) {
        title = view.findViewById(R.id.title);
        btnPoor = view.findViewById(R.id.btn_poor);
        btnEmployment = view.findViewById(R.id.btn_employment);
        btnCertificate = view.findViewById(R.id.btn_certificate);
    }

    @Override
    protected void initData() {
        title.setText("申请认证");
        btn();

    }

    private void btn() {

        btnPoor.setOnClickListener(view -> {
            ((FragmentActivity) getActivity()).setFragment(new FragmentApplyPorr());
        });

        btnEmployment.setOnClickListener(view -> {
            ((FragmentActivity) getActivity()).setFragment(new FragmentApply_Employment());
        });

        btnCertificate.setOnClickListener(v -> {
            ((FragmentActivity)getActivity()).setFragment(new FragmentCertificate());
        });

    }

    @Override
    public void onClick(View view) {

    }
}
