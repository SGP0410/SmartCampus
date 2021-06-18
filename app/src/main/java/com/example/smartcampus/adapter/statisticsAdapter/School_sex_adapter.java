package com.example.smartcampus.adapter.statisticsAdapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.statisticsAdapter.School_sex_adapter.ViewHolder;
import com.example.smartcampus.bean.statistics.GetCollegeMenAndWomenNumberAll;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import java.util.List;

public class School_sex_adapter extends BaseRecyclerViewAdapter<ViewHolder, GetCollegeMenAndWomenNumberAll> {
    
    public School_sex_adapter(List<GetCollegeMenAndWomenNumberAll> getCollegeMenAndWomenNumberAlls) {
        super(getCollegeMenAndWomenNumberAlls, R.layout.adapter_school_sex);
    }
    
    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    protected void setValues(ViewHolder holder, GetCollegeMenAndWomenNumberAll bean) {
        holder.itemMan.setText(bean.getMan()+"");
        holder.itemWoman.setText(bean.getWoman()+"");
        holder.itemTitle.setText(bean.getCollegeName());
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemBtn;
        private final TextView itemTitle;
        private final TextView itemMan;
        private final TextView itemWoman;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBtn = itemView.findViewById(R.id.item_btn);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemMan = itemView.findViewById(R.id.item_man);
            itemWoman = itemView.findViewById(R.id.item_woman);
        }
    }
}
