package com.example.smartcampus.adapter.apply;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.apply.TradingRecord;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;

import java.util.List;

public class TradingRecordRecyclerViewAdapter extends BaseRecyclerViewAdapter<TradingRecordRecyclerViewAdapter.ViewHolder, TradingRecord> {

    public TradingRecordRecyclerViewAdapter(List<TradingRecord> tradingRecords) {
        super(tradingRecords, R.layout.trading_record_item);
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, TradingRecord bean) {
        holder.tradingType.setText(bean.getTradingType());
        if (bean.getTradingType().contains("转入") || bean.getTradingType().contains("充值")){
            holder.tradingAmount.setText("+"+bean.getTradingAmount());
        }else {
            holder.tradingAmount.setText("-"+bean.getTradingAmount());
        }
        holder.tradingDate.setText(bean.getTradingDate());
        holder.tradingSite.setText(bean.getTradingSite());
    }


    static public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tradingSite;
        private TextView tradingType;
        private TextView tradingDate;
        private TextView tradingAmount;

        public ViewHolder(@NonNull View view) {
            super(view);
            tradingSite = view.findViewById(R.id.tradingSite);
            tradingType = view.findViewById(R.id.tradingType);
            tradingDate = view.findViewById(R.id.tradingDate);
            tradingAmount = view.findViewById(R.id.tradingAmount);
        }

    }


}
