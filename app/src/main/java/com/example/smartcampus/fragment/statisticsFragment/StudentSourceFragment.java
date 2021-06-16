package com.example.smartcampus.fragment.statisticsFragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.utils.MapView;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.jarvislau.destureviewbinder.GestureViewBinder;

public class StudentSourceFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.group_view)
    LinearLayout groupView;

    @Override
    protected int layoutResId() {
        return R.layout.student_source_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        title.setText("省生源");
    }

    @Override
    protected void initData() {
        GestureViewBinder binder = GestureViewBinder.bind(getContext(), groupView, mapView);
        binder.setFullGroup(true);
        mapView.setOnMapViewClickListener(name -> {

        });
    }

    @Override
    public void onClick(View v) {

    }
}
