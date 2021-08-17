package com.example.smartcampus.adapter.wodeeAdapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Student;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/10 11:00 星期二
 */
public class WoDeScoreBoardAdapter extends BaseRecyclerViewAdapter<WoDeScoreBoardAdapter.ViewHolder, Student> {

    public WoDeScoreBoardAdapter(List<Student> students) {
        super(students, R.layout.wode_score_board_adapter);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, Student bean) {
        int position = getPosition();
        if (position % 2 == 0) {
            holder.itemLin.setBackgroundResource(R.drawable.hui1_r5);
        }else {
            holder.itemLin.setBackgroundResource(R.drawable.hui_r5);
        }
        holder.itemName.setText(bean.getName());
        holder.itemYuwen.setText(bean.getYu());
        holder.itemShuxue.setText(bean.getShu());
        holder.itemYingyu.setText(bean.getWai());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemLin;
        private TextView itemName;
        private TextView itemYuwen;
        private TextView itemShuxue;
        private TextView itemYingyu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLin = itemView.findViewById(R.id.item_lin);
            itemName = itemView.findViewById(R.id.item_name);
            itemYuwen = itemView.findViewById(R.id.item_yuwen);
            itemShuxue = itemView.findViewById(R.id.item_shuxue);
            itemYingyu = itemView.findViewById(R.id.item_yingyu);
        }
    }

}
