package com.example.smartcampus.adapter.wodeeAdapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Certificate;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/16 11:18 星期一
 */
public class WoDeCertificateRZAdapter extends BaseRecyclerViewAdapter<WoDeCertificateRZAdapter.ViewHolder, Certificate> {

    public WoDeCertificateRZAdapter(List<Certificate> certificates) {
        super(certificates, R.layout.adapter_certificaterz);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setValues(ViewHolder holder, Certificate bean) {
        holder.itemName.setText("姓名："+bean.getName());
        holder.itemLevel.setText("申请等级："+bean.getLevel());
        holder.itemTitle.setText(bean.getTitle());
        holder.itemType.setText("状态："+bean.getState());
        holder.itemTime.setText("获奖时间："+bean.getYears());
        switch (bean.getState()){
            case "已通过":
                holder.itemType.setBackgroundResource(R.drawable.green_r10);
                break;
            case "审核中":
                holder.itemType.setBackgroundResource(R.drawable.blue_r10);
                break;
            default:
                holder.itemType.setBackgroundResource(R.drawable.red_r10);
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemLin;
        private TextView itemName;
        private TextView itemLevel;
        private TextView itemTitle;
        private TextView itemType;
        private TextView itemTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLin = itemView.findViewById(R.id.item_lin);
            itemName = itemView.findViewById(R.id.item_name);
            itemLevel = itemView.findViewById(R.id.item_level);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemType = itemView.findViewById(R.id.item_type);
            itemTime = itemView.findViewById(R.id.item_time);
        }
    }
}
