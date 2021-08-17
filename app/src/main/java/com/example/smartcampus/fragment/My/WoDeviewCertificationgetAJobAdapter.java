package com.example.smartcampus.fragment.My;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.bean.statistics.Student;
import com.example.smartcampus.bean.statistics.WorkNature;
import com.example.smartcampuslibrary.adapter.BaseRecyclerViewAdapter;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * @author 关鑫
 * @date 2021/8/11 15:55 星期三
 */
public class WoDeviewCertificationgetAJobAdapter extends BaseRecyclerViewAdapter<WoDeviewCertificationgetAJobAdapter.ViewHolder, Student> {

    private List<WorkNature> workNatureList;
    private Context context;


    public WoDeviewCertificationgetAJobAdapter(List<Student> students, List<WorkNature> workNatures, Context context) {
        super(students, R.layout.certificationajobadapter);
        this.workNatureList = workNatures;
        this.context = context;
    }

    @Override
    protected ViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    protected void setValues(ViewHolder holder, Student bean) {
        AJobAdapterSpinner adapter = new AJobAdapterSpinner(context,workNatureList);
        holder.itemSpinner.setAdapter(adapter);
        holder.itemSpinner.setSelection(Integer.parseInt(bean.getWordNatureId())-1);
        holder.itemName.setText("姓名："+bean.getName());
        holder.itemTime.setText("地址："+bean.getAddress());

        holder.itemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getOkHttp(position,bean.getSchoolCard());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getOkHttp(int position, String schoolCard) {
        new OkHttpTo()
                .setUrl("StudentSetWordNatureId")
                .setRequestType("post")
                .setJSONObject("wordNatureId",workNatureList.get(position).getId())
                .setJSONObject("schoolCard",schoolCard)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName;
        private Spinner itemSpinner;
        private TextView itemTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemSpinner = itemView.findViewById(R.id.item_spinner);
            itemTime = itemView.findViewById(R.id.item_time);
        }
    }
}
