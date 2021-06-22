package com.example.smartcampus.fragment.statisticsFragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Municipal;
import com.example.smartcampus.utils.MapView;
import com.example.smartcampus.utils.destureviewbinder.GestureViewBinder;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import org.json.JSONObject;

public class MunicipalStudentSourceFragment extends BaseFragment {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.group_view)
    LinearLayout groupView;
    @BindView(R.id.view_flipper)
    ViewFlipper viewFlipper;
    @BindView(R.id.you_xiu_image)
    ImageView youXiuImage;
    @BindView(R.id.you_xiu_text)
    TextView youXiuText;
    @BindView(R.id.you_xiu)
    LinearLayout youXiu;
    @BindView(R.id.pin_kun_image)
    ImageView pinKunImage;
    @BindView(R.id.pin_kun_text)
    TextView pinKunText;
    @BindView(R.id.pin_kun)
    LinearLayout pinKun;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    private String name;
    private List<Municipal> municipalList;

    public MunicipalStudentSourceFragment(String name) {
        this.name = name;
    }

    @Override
    protected int layoutResId() {
        return R.layout.student_source_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        title.setText(name);
    }

    @Override
    protected void initData() {
        getMunicipalStudentSourceByProvinceName();

        GestureViewBinder binder = GestureViewBinder.bind(getContext() , groupView , mapView);
        binder.setFullGroup(true);

    }

    private void getMunicipalStudentSourceByProvinceName() {
        new OkHttpTo().setUrl("GetMunicipalStudentSourceByProvinceName?provinceName=" + name)
            .setRequestType("get")
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    municipalList = new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                        new TypeToken<List<Municipal>>() {
                        }.getType());

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
