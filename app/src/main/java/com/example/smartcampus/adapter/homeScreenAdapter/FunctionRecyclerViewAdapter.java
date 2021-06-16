package com.example.smartcampus.adapter.homeScreenAdapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.homeScreenAdapter.FunctionRecyclerViewAdapter.ViewHolder;
import com.example.smartcampus.bean.home.HomeFunction;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import java.util.List;

public class FunctionRecyclerViewAdapter extends
    BaseRecyclerViewAdapter<ViewHolder, HomeFunction> {

    public FunctionRecyclerViewAdapter(List<HomeFunction> functionList) {
        super(functionList, R.layout.function_item);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, HomeFunction bean) {
        holder.imageView.setImageResource(bean.getImage());
        holder.text1.setText(bean.getName());
        GradientDrawable myGrad = (GradientDrawable)holder.itemView.getBackground();
        myGrad.setColor(bean.getColor());
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view)
        ImageView imageView;
        @BindView(R.id.text1)
        TextView text1;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
