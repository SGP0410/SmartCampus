package com.example.smartcampus.adapter.statisticsAdapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.statisticsAdapter.StraightAStudent_adapter.ViewHolder;
import com.example.smartcampus.bean.statistics.GetStraightAStudent;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import java.util.List;

public class StraightAStudent_adapter extends BaseRecyclerViewAdapter<ViewHolder, GetStraightAStudent> {
    
    public StraightAStudent_adapter(List<GetStraightAStudent> getStraightAStudents) {
        super(getStraightAStudents, R.layout.adapter_straightastdent);
    }
    
    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    protected void setValues(ViewHolder holder, GetStraightAStudent bean) {
        int position = getPosition();
        if (position % 2 == 0) {
            holder.itemLine.setBackgroundResource(R.drawable.lin_lan_r20);
        } else {
            holder.itemLine.setBackgroundResource(R.drawable.lin_bai_r20);
        }
        holder.itemNo.setText(bean.getNumber());
        holder.itemName.setText(bean.getName());
        holder.itemYuwen.setText(bean.getYu());
        holder.itemShuxue.setText(bean.getShu());
        holder.itemYingyu.setText(bean.getWai());
        holder.itemSum.setText(bean.getSum() + "");
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        
        private LinearLayout itemLine;
        private TextView itemNo;
        private TextView itemName;
        private TextView itemYuwen;
        private TextView itemShuxue;
        private TextView itemYingyu;
        private TextView itemSum;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLine = itemView.findViewById(R.id.item_line);
            itemNo = itemView.findViewById(R.id.item_No);
            itemName = itemView.findViewById(R.id.item_name);
            itemYuwen = itemView.findViewById(R.id.item_yuwen);
            itemShuxue = itemView.findViewById(R.id.item_shuxue);
            itemYingyu = itemView.findViewById(R.id.item_yingyu);
            itemSum = itemView.findViewById(R.id.item_sum);
        }
    }
}
