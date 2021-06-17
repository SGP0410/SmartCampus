package com.example.smartcampus.fragment.statisticsFragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Province;
import com.example.smartcampus.utils.MapView;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jarvislau.destureviewbinder.GestureViewBinder;
import java.io.IOException;
import java.util.List;
import org.json.JSONObject;

public class StudentSourceFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.group_view)
    LinearLayout groupView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layout1)
    LinearLayout layout1;

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

        mapView.setMapResId(R.raw.anhui);


        GestureViewBinder binder = GestureViewBinder.bind(getContext(), groupView, mapView);
        binder.setFullGroup(true);
        mapView.setOnMapViewClickListener(name -> {

        });


        province_query_all();

    }

    private void province_query_all() {
        new OkHttpTo().setUrl("province_query_all")
            .setRequestType("get")
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    List<Province> provinceList = new Gson().fromJson(jsonObject.optJSONArray(
                        "data").toString() , new TypeToken<List<Province>>(){}.getType());

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
