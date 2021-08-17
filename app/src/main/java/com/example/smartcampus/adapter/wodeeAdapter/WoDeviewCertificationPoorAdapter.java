package com.example.smartcampus.adapter.wodeeAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Approve;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/11 9:39 星期三
 */
public class WoDeviewCertificationPoorAdapter extends BaseRecyclerViewAdapter<WoDeviewCertificationPoorAdapter.ViewHolder, Approve> {

    public WoDeviewCertificationPoorAdapter(List<Approve> approves) {
        super(approves, R.layout.certificationpoor_adapter);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, Approve bean) {
        holder.itemName.setText("姓名："+bean.getGrade());
        holder.itemMsg.setText("申请原因："+bean.getMsg());
        if ("审核中".equals(bean.getState())){
            holder.itemType.setText(bean.getState());
            holder.itemType.setBackgroundResource(R.drawable.txt_check);
        }else if ("已通过".equals(bean.getState())){
            holder.itemType.setText(bean.getState());
            holder.itemType.setBackgroundResource(R.drawable.txt_adopt);
        }else {
            holder.itemType.setText(bean.getState());
            holder.itemType.setBackgroundResource(R.drawable.txt_not);
        }
        holder.itemTime.setText("提交时间："+bean.getTime());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private TextView itemType;
        private TextView itemMsg;
        private TextView itemTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemType = itemView.findViewById(R.id.item_type);
            itemMsg = itemView.findViewById(R.id.item_msg);
            itemTime = itemView.findViewById(R.id.item_time);
        }
    }

}
