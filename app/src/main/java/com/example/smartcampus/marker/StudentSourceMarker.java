package com.example.smartcampus.marker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import java.util.List;

@SuppressLint("ViewConstructor")
public class StudentSourceMarker extends MarkerView {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.layout1)
    LinearLayout layout1;

    private List<Integer> colors;


    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param colors
     */
    public StudentSourceMarker(Context context, List<Integer> colors) {
        super(context, R.layout.student_source_marker);
        ButterKnife.bind(getRootView());
        this.colors = colors;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        name.setText(e.getData().toString());
        content.setText("人数：" + (int) e.getY() + "人");
        layout1.setBackgroundColor(colors.get((int) e.getX() - 1));
        if (onClickListener != null) {
            onClickListener.onClick(e.getData().toString() , colors.get((int) e.getX() - 1));
        }
        super.refreshContent(e, highlight);
    }

    private OnClickListener onClickListener;

    public interface OnClickListener {

        void onClick(String name , int color);
    }

    public void setOnClickListener(
        OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -(getHeight() + (layout1.getHeight() / 2)));
    }
}

