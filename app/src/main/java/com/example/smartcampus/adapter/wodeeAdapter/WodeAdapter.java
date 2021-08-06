package com.example.smartcampus.adapter.wodeeAdapter;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.adapter.OnClickListener;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;

import java.util.List;

public class WodeAdapter extends BaseRecyclerViewAdapter<WodeAdapter.ViewHolder, String> {

    public WodeAdapter(List<String> strings) {
        super(strings, R.layout.wode_adapter);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    private static final String TAG = "WodeAdapter";

    @Override
    protected void setValues(ViewHolder holder, String bean) {
        holder.itemName.setText(bean);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemBtn;
        private TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBtn = itemView.findViewById(R.id.item_btn);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }

}
