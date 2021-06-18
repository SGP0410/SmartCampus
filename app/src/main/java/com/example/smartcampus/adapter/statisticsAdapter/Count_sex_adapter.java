package com.example.smartcampus.adapter.statisticsAdapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smartcampus.R;
import com.example.smartcampus.adapter.statisticsAdapter.Count_sex_adapter.ViewHolder;
import com.example.smartcampus.bean.statistics.GetProvinceMenAndWomenNumberAll;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import java.util.List;

public class Count_sex_adapter extends BaseRecyclerViewAdapter<ViewHolder, GetProvinceMenAndWomenNumberAll> {

    public Count_sex_adapter(List<GetProvinceMenAndWomenNumberAll> getProvinceMenAndWomenNumberAlls) {
        super(getProvinceMenAndWomenNumberAlls, R.layout.count_sex_adapter);
    }
    
    
    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    protected void setValues(ViewHolder holder, GetProvinceMenAndWomenNumberAll bean) {
        holder.itemTitle.setText(bean.getProvinceName());
        double sum = bean.getMan() + bean.getWoman();
        double nan = bean.getMan();
        double nv = bean.getWoman();
        double woman = nv/sum;
        
        int woman1 = (int) (woman*100);
        int man1 = 100 - woman1;
        
        holder.itemWoman.setText(woman1+"%");
        holder.itemMan.setText(man1+"%");
        
        switch (bean.getProvinceName()){
            case "河北省":
                holder.itemEnglish.setText("HeBei");
                break;
            case "山西省":
                holder.itemEnglish.setText("ShanXi");
                break;
            case "辽宁省":
                holder.itemEnglish.setText("LiaoNing");
                break;
            case "吉林省":
                holder.itemEnglish.setText("QingHai");
                break;
            case "黑龙江省":
                holder.itemEnglish.setText("HeiLongJiang");
                break;
            case "江苏省":
                holder.itemEnglish.setText("JiangSu");
                break;
            case "浙江省":
                holder.itemEnglish.setText("ZheJiang");
                break;
            case "安徽省":
                holder.itemEnglish.setText("AnHui");
                break;
            case "福建省":
                holder.itemEnglish.setText("FuJian");
                break;
            case "江西省":
                holder.itemEnglish.setText("JiangXi");
                break;
            case "河南省":
                holder.itemEnglish.setText("HeNan");
                break;
            case "山东省":
                holder.itemEnglish.setText("ShanDong");
                break;
            case "湖北省":
                holder.itemEnglish.setText("HuBei");
                break;
            case "湖南省":
                holder.itemEnglish.setText("HuNan");
                break;
            case "广东省":
                holder.itemEnglish.setText("GuangDong");
                break;
            case "海南省":
                holder.itemEnglish.setText("HaiNan");
                break;
            case "四川省":
                holder.itemEnglish.setText("SiChuan");
                break;
            case "贵州省":
                holder.itemEnglish.setText("GuiZhou");
                break;
            case "云南省":
                holder.itemEnglish.setText("YunNan");
                break;
            case "陕西省":
                holder.itemEnglish.setText("ShanXi");
                break;
            case "甘肃省":
                holder.itemEnglish.setText("GanSu");
                break;
            case "青海省":
                holder.itemEnglish.setText("QingHai");
                break;
            case "台湾省":
                holder.itemEnglish.setText("TaiWan");
                break;
            case "北京市":
                holder.itemEnglish.setText("BeiJing");
                break;
            case "天津市":
                holder.itemEnglish.setText("TianJin");
                break;
            case "上海市":
                holder.itemEnglish.setText("ShangHai");
                break;
            case "重庆市":
                holder.itemEnglish.setText("ChongQing");
                break;
            case "广西壮族自治区":
                holder.itemEnglish.setText("GuangXi");
                break;
            case "内蒙古自治区":
                holder.itemEnglish.setText("NeiMengGu");
                break;
            case "西藏自治区":
                holder.itemEnglish.setText("XiZang");
                break;
            case "宁夏回族自治区":
                holder.itemEnglish.setText("NingXia");
                break;
            case "新疆维吾尔自治区":
                holder.itemEnglish.setText("XinJiang");
                break;
            case "香港特别行政区":
                holder.itemEnglish.setText("XiangGang");
                break;
            case "澳门特别行政区":
                holder.itemEnglish.setText("AoMen");
                break;
            default:
                break;
        }
        
        
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemBtn;
        private TextView itemEnglish;
        private TextView itemTitle;
        private TextView itemMan;
        private TextView itemWoman;
    
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBtn = itemView.findViewById(R.id.item_btn);
            itemEnglish = itemView.findViewById(R.id.item_english);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemMan = itemView.findViewById(R.id.item_man);
            itemWoman = itemView.findViewById(R.id.item_woman);
        }
    }
    
}
