package com.example.smartcampus.adapter.homeScreenAdapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.bean.home.HomeTheme;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import java.util.List;

public class HomeThemeRecyclerViewAdapter extends
    BaseRecyclerViewAdapter<HomeThemeRecyclerViewAdapter.ViewHolder, HomeTheme> {

    public HomeThemeRecyclerViewAdapter(List<HomeTheme> themeList) {
        super(themeList , android.R.layout.simple_spinner_item);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, HomeTheme bean) {
        if (bean.isClick()){
            holder.textView.setBackgroundResource(R.drawable.heixiahuaxian);
        }else {
            holder.textView.setBackgroundColor(Color.parseColor("#FDFDFE"));
        }

        holder.textView.setText(bean.getTheme());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
        }
    }
}
