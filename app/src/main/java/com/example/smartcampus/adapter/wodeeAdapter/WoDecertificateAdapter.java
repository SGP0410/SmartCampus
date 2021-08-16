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
 * @date 2021/8/16 9:31 星期一
 */
public class WoDecertificateAdapter extends BaseRecyclerViewAdapter<WoDecertificateAdapter.ViewHolder, Certificate> {


    public WoDecertificateAdapter(List<Certificate> certificates) {
        super(certificates, R.layout.wode_certificate_adapter);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setValues(ViewHolder holder, Certificate bean) {
        holder.itemTitle.setText(bean.getTitle());
        holder.itemTime.setText("获证时间：" + bean.getTime());
        holder.itemTeacher.setText(bean.getTeacher());
        holder.itemGrade.setText(bean.getGrade());
        holder.itemMember.setText(bean.getMember());
        switch (bean.getLevel()) {
            case "国家级":
                if ("已通过".equals(bean.getState())){
                    holder.itemLin.setBackgroundResource(R.mipmap.guo_1);
                }else {
                    holder.itemLin.setBackgroundResource(R.mipmap.guo_2);
                }
                break;
            case "省级":
                if ("已通过".equals(bean.getState())){
                    holder.itemLin.setBackgroundResource(R.mipmap.sheng_1);
                }else {
                    holder.itemLin.setBackgroundResource(R.mipmap.sheng_2);
                }
                break;
            case "校级":
                if ("已通过".equals(bean.getState())){
                    holder.itemLin.setBackgroundResource(R.mipmap.xiao_1);
                }else {
                    holder.itemLin.setBackgroundResource(R.mipmap.xiao_2);
                }
                break;
            default:
                break;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemLin;
        private TextView itemTitle;
        private TextView itemMember;
        private TextView itemGrade;
        private TextView itemTeacher;
        private TextView itemTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLin = itemView.findViewById(R.id.item_lin);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemMember = itemView.findViewById(R.id.item_member);
            itemGrade = itemView.findViewById(R.id.item_grade);
            itemTeacher = itemView.findViewById(R.id.item_teacher);
            itemTime = itemView.findViewById(R.id.item_time);
        }
    }
}
