package com.example.smartcampus.adapter.wodeeAdapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.GetClassSchedule;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/10 9:05 星期二
 */
public class WoDeCourseAdapter extends BaseRecyclerViewAdapter<WoDeCourseAdapter.ViewHolder, GetClassSchedule> {

    public WoDeCourseAdapter(List<GetClassSchedule> getClassSchedules) {
        super(getClassSchedules, R.layout.adapter_wode_course);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, GetClassSchedule bean) {
        int position = getPosition();
        if (position % 2 == 0) {
            holder.lin.setBackgroundResource(R.drawable.bai_r3);
        }else {
            holder.lin.setBackgroundResource(R.drawable.hui_r3);
        }
        holder.itemClass.setText(bean.getCourse());
        if (bean.getName() == null){

        }else {
            holder.itemName.setText(bean.getName()+"");
        }

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout lin;
        private TextView itemClass;
        private TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lin = itemView.findViewById(R.id.lin);
            itemClass = itemView.findViewById(R.id.item_class);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }

}
