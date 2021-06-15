package com.example.smartcampus.adapter.yinYongAdapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.yinYongAdapter.YinYong_adapter.ViewHolder;
import com.example.smartcampus.bean.yinYong.ApplyType;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import com.example.smartcampuslibrary.utils.myView.ImageViewOval;
import java.util.List;

public class YinYong_adapter extends BaseRecyclerViewAdapter<ViewHolder, ApplyType> {
    
    private List<ApplyType> applyTypes;

    
    public YinYong_adapter(List<ApplyType> applyTypes) {
        super(applyTypes, R.layout.apply_type);
    }
    
    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }
    
    @Override
    protected void setValues(ViewHolder holder, ApplyType bean) {
        holder.itemImageview.setBackgroundResource(bean.getColor());
        holder.itemImageview.setImageResource(bean.getImage());
        holder.itemTitle.setText(bean.getName());
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageViewOval itemImageview;
        private TextView itemTitle;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageview = itemView.findViewById(R.id.item_imageview);
            itemTitle = itemView.findViewById(R.id.item_title);
        }
    }
}
