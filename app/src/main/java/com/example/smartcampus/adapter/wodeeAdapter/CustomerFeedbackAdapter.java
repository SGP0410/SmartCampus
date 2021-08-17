package com.example.smartcampus.adapter.wodeeAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Feedback;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/17 9:14 星期二
 */
public class CustomerFeedbackAdapter extends BaseRecyclerViewAdapter<CustomerFeedbackAdapter.ViewHolder, Feedback> {

    public CustomerFeedbackAdapter(List<Feedback> feedbacks) {
        super(feedbacks, R.layout.customer_feedback);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, Feedback bean) {
        holder.itemTitle.setText(bean.getTitle());
        holder.itemMsg.setText(bean.getMsg());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView itemTitle;
        private TextView itemMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemMsg = itemView.findViewById(R.id.item_msg);
        }
    }
}
