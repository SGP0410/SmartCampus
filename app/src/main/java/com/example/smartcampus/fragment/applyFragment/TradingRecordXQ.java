package com.example.smartcampus.fragment.applyFragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.apply.TradingRecord;
import com.example.smartcampuslibrary.fragment.BaseFragment;

public class TradingRecordXQ extends BaseFragment {
    private ImageView back;
    private TextView title;
    private TextView tradingSite;
    private TextView tradingType;
    private TextView tradingAmount;
    private TextView content;
    private TradingRecord tradingRecord;
    public TradingRecordXQ(TradingRecord tradingRecord) {
        this.tradingRecord = tradingRecord;
    }

    @Override
    protected int layoutResId() {
        return R.layout.trading_record_xq;
    }

    @Override
    protected void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        title.setText("账单详情");
        tradingSite = view.findViewById(R.id.tradingSite);
        tradingType = view.findViewById(R.id.tradingType);
        tradingAmount = view.findViewById(R.id.tradingAmount);
        content = view.findViewById(R.id.content);
    }

    @Override
    protected void initData() {
        tradingSite.setText(tradingRecord.getTradingSite());
        tradingType.setText(tradingRecord.getTradingType());
        if (tradingRecord.getTradingType().contains("转入") || tradingRecord.getTradingType().contains("充值")){
            tradingAmount.setText("+"+tradingRecord.getTradingAmount());
        }else {
            tradingAmount.setText("-"+tradingRecord.getTradingAmount());
        }

        content.setText("当前状态：交易完成\n交易详情："+tradingRecord.getTradingDescription()+
                "\n交易时间："+tradingRecord.getTradingDate());
    }

    @Override
    public void onClick(View v) {

    }
}
