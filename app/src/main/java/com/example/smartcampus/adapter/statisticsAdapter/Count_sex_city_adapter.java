package com.example.smartcampus.adapter.statisticsAdapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.statisticsAdapter.Count_sex_city_adapter.ViewHolder;
import com.example.smartcampus.bean.statistics.GetMunicipalMenAndWomenNumberAll;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import java.util.List;

public class Count_sex_city_adapter extends BaseRecyclerViewAdapter<ViewHolder, GetMunicipalMenAndWomenNumberAll> {
    
    public Count_sex_city_adapter(List<GetMunicipalMenAndWomenNumberAll> getMunicipalMenAndWomenNumberAlls) {
        super(getMunicipalMenAndWomenNumberAlls, R.layout.adapter_count_sex_city);
    }
    
    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    protected void setValues(ViewHolder holder, GetMunicipalMenAndWomenNumberAll bean) {
        holder.itemTitle.setText(bean.getMunicipalName());
        double sum = bean.getMan() + bean.getWoman();
        double nan = bean.getMan();
        double nv = bean.getWoman();
        double woman = nv/sum;
    
        int woman1 = (int) (woman*100);
        int man1 = 100 - woman1;
    
        holder.itemWoman.setText(woman1+"%");
        holder.itemMan.setText(man1+"%");
        
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemBtn;
        private TextView itemTitle;
        private TextView itemMan;
        private TextView itemWoman;
    
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBtn = itemView.findViewById(R.id.item_btn);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemMan = itemView.findViewById(R.id.item_man);
            itemWoman = itemView.findViewById(R.id.item_woman);
        }
    }
    
}
