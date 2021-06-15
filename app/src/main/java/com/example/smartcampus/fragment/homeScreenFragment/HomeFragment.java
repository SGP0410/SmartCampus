package com.example.smartcampus.fragment.homeScreenFragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.homeScreenAdapter.FunctionRecyclerViewAdapter;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import java.util.Arrays;
import java.util.Objects;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.function_recycler_view)
    RecyclerView functionRecyclerView;

    private String[] functions = {
        "学生生源",
        "性别统计",
        "学生统计",
        "消费趋势",
        "学霸指数",
        "招生信息",
        "学生就业"
    };

    @Override
    protected int layoutResId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {
        showFunction();
    }

    private void showFunction() {
        DisplayMetrics dm = new DisplayMetrics();

        Objects.requireNonNull(Objects.requireNonNull(getContext()).getDisplay()).getMetrics(dm);

        int widthPixels = dm.widthPixels;//屏幕宽度

        float density = dm.densityDpi;

        int dp = (int) ((widthPixels*160)/density);

        functionRecyclerView.setLayoutManager(new GridLayoutManager(getContext() , dp/150 ,
            GridLayoutManager.VERTICAL , false));

//        functionRecyclerView.setAdapter(new FunctionRecyclerViewAdapter(Arrays.asList(functions)));


    }

    @Override
    public void onClick(View v) {

    }
}
