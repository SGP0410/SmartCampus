package com.example.smartcampus.dialog.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;

public class ChartViewDialog extends DialogFragment {

    @BindView(R.id.layout1)
    LinearLayout layout1;
    private View view;
    private int width;

    public ChartViewDialog(View view , int width) {
        this.width = width;
        this.view = view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_view_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this , view);
        BarChart barChart = new BarChart(getContext());
        barChart.setLayoutParams(new LayoutParams(width , width));
        layout1.addView(barChart);
    }

}
