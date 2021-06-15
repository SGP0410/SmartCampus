package com.example.smartcampuslibrary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder , D> extends RecyclerView.Adapter<T> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private List<D> dList;
    private int layoutResId;
    private int position;

    public BaseRecyclerViewAdapter(List<D> dList, int layoutResId) {
        this.dList = dList;
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutResId , parent , false));
    }

    protected abstract T getViewHolder(View itemView);

    @Override
    public void onBindViewHolder(@NonNull T holder, final int position) {
        this.position = position;
        D d = dList.get(position);
        setValues(holder , d);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onClick(position);
            }
        });
    }

    public int getPosition() {
        return position;
    }

    protected abstract void setValues(T holder , D bean);

    @Override
    public int getItemCount() {
        return dList.size();
    }
}
