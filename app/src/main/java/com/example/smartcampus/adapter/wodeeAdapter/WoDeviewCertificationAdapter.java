package com.example.smartcampus.adapter.wodeeAdapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/11 8:34 星期三
 */
public class WoDeviewCertificationAdapter extends BaseRecyclerViewAdapter<WoDeviewCertificationAdapter.ViewHolder, String> {

    public WoDeviewCertificationAdapter(List<String> strings) {
        super(strings, R.layout.wode_certification_adapter);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, String bean) {
        holder.itemName.setText(bean);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemBtn;
        private TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBtn = itemView.findViewById(R.id.item_btn);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }
}
