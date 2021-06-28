package com.example.smartcampus.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Provincequeryall;

import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vmmet on 2016/10/10.
 */
public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> {
    private Map<String, Float> studentAdress;
    private List<Provincequeryall> provincequeryalls;

    public ProvinceAdapter(Map<String, Float> studentAdress, List<Provincequeryall> provincequeryalls) {
        this.studentAdress = studentAdress;
        this.provincequeryalls = provincequeryalls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_province_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Provincequeryall provincequeryall=provincequeryalls.get(position);
        if (holder.getAdapterPosition()==0){
            holder.provinceName.setTextColor(Color.RED);
        }else {
            holder.provinceName.setTextColor(Color.BLACK);
        }
        holder.provinceName.setText(provincequeryall.getProvinceName());
        if (studentAdress.get(provincequeryall.getProvinceName())==null){

        }else {
            holder.provinceElecRise.setText((studentAdress.get(provincequeryall.getProvinceName())*100)+"%");
        }

    }

    @Override
    public int getItemCount() {
        return provincequeryalls.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView provinceName;
        private TextView provinceElecRise;

        public ViewHolder(View itemView) {
            super(itemView);
            provinceName = (TextView) itemView.findViewById(R.id.province_name);
            provinceElecRise = (TextView) itemView.findViewById(R.id.province_elec_rise);
        }
    }
}

