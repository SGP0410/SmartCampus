package com.example.smartcampus.adapter.homeScreenAdapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.example.smartcampus.R;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import java.util.List;

public class FunctionRecyclerViewAdapter extends
    BaseRecyclerViewAdapter<FunctionRecyclerViewAdapter.ViewHolder , String> {

    public FunctionRecyclerViewAdapter(List<String> strings) {
        super(strings , R.layout.function_item);
    }

    @Override
    protected ViewHolder getViewHolder(View view) {
        return null;
    }

    @Override
    protected void setValues(ViewHolder viewHolder, String s) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
