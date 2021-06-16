package com.example.smartcampus.adapter.homeAdapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.homeAdapter.StatisticsRecyclerViewAdapter.ViewHolder;
import com.example.smartcampus.bean.home.HomeFunction;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import com.example.smartcampuslibrary.utils.Utils;
import com.example.smartcampuslibrary.utils.myView.ImageViewOval;
import java.util.List;
import java.util.Random;

public class StatisticsRecyclerViewAdapter extends
    BaseRecyclerViewAdapter<ViewHolder, HomeFunction> {

    public StatisticsRecyclerViewAdapter(List<HomeFunction> functionList) {
        super(functionList, R.layout.statistics_item);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, HomeFunction bean) {
        GradientDrawable myGrad = (GradientDrawable)holder.itemView.getBackground();
        myGrad.setColor(Color.parseColor("#FDFDFE"));

        holder.imageView1.setBackgroundColor(bean.getColor());

        ViewGroup.LayoutParams layoutParams = holder.imageView1.getLayoutParams();
        layoutParams.width = Utils.getRandom().nextInt(70)+70;
        holder.imageView1.setLayoutParams(layoutParams);

        holder.imageView.setImageResource(bean.getImage());

        holder.imageView.setImageDrawable(tintDrawable(holder.imageView.getDrawable() ,
            ColorStateList.valueOf(bean.getColor())));

        String[] names = bean.getName().split(",");
        holder.text1.setText(names[1]);
        holder.text2.setText(names[0]);
    }



    private Drawable tintDrawable(Drawable drawable , ColorStateList colorStateList){
        Drawable wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrap , colorStateList);
        return drawable;
    }

    static
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view1)
        ImageView imageView1;
        @BindView(R.id.text1)
        TextView text1;
        @BindView(R.id.text2)
        TextView text2;
        @BindView(R.id.image_view)
        ImageView imageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
