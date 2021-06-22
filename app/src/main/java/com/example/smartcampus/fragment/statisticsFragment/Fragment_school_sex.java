package com.example.smartcampus.fragment.statisticsFragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.example.smartcampus.R;
import com.example.smartcampus.activity.FragmentActivity;
import com.example.smartcampus.adapter.statisticsAdapter.School_sex_adapter;
import com.example.smartcampus.bean.statistics.GetCollegeMenAndWomenNumberAll;
import com.example.smartcampus.bean.statistics.GetMunicipalMenAndWomenNumberAll;
import com.example.smartcampuslibrary.fragment.BaseFragment;
import com.example.smartcampuslibrary.net.OkHttpLo;
import com.example.smartcampuslibrary.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.json.JSONObject;

public class Fragment_school_sex extends BaseFragment {
    
    private RecyclerView recyclerView;
    private List<GetCollegeMenAndWomenNumberAll> getCollegeMenAndWomenNumberAlls, seeklist;
    private EditText edSearch;
    private TextView cancel;
    private InputMethodManager imm;
    
    @Override
    protected int layoutResId() {
        return R.layout.fragment_school_sex;
    }
    
    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        edSearch = view.findViewById(R.id.ed_search);
        cancel = view.findViewById(R.id.cancel);
    }
    
    @Override
    protected void initData() {
        
        getOkHttp();
    
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    
        cancel.setOnClickListener(v -> edSearch.setText(""));
    
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
            }
        
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String msg = edSearch.getText().toString();
                if (msg.equals("")) {
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                    }
                    seeklist.clear();
                    seeklist.addAll(getCollegeMenAndWomenNumberAlls);
                    myAdapter.notifyDataSetChanged();
                } else {
                    for (GetCollegeMenAndWomenNumberAll getProvinceMenAndWomenNumberAll : getCollegeMenAndWomenNumberAlls) {
                        if (getProvinceMenAndWomenNumberAll.getCollegeName().contains(msg)) {
                            seeklist.clear();
                            for (GetCollegeMenAndWomenNumberAll provinceMenAndWomenNumberAll : getCollegeMenAndWomenNumberAlls) {
                                if (provinceMenAndWomenNumberAll.getCollegeName().contains(msg)) {
                                    seeklist.add(provinceMenAndWomenNumberAll);
                                    myAdapter.notifyDataSetChanged();
                                }
                            }
                            break;
                        }
                    }
                }
            }
        
            @Override
            public void afterTextChanged(Editable s) {
            
            }
        });
        
    }
    
    private void getOkHttp() {
        if (getCollegeMenAndWomenNumberAlls == null) {
            getCollegeMenAndWomenNumberAlls = new ArrayList<>();
            seeklist = new ArrayList<>();
        } else {
            seeklist.clear();
            getCollegeMenAndWomenNumberAlls.clear();
        }
        new OkHttpTo()
            .setUrl("GetCollegeMenAndWomenNumberAll")
            .setRequestType("GET")
            .setDialog(getContext())
            .setOkHttpLo(new OkHttpLo() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    getCollegeMenAndWomenNumberAlls
                        .addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                            new TypeToken<List<GetCollegeMenAndWomenNumberAll>>() {
                            }.getType()));
                    
                    seeklist.addAll(new Gson().fromJson(jsonObject.optJSONArray("rows").toString(),
                        new TypeToken<List<GetCollegeMenAndWomenNumberAll>>() {
                        }.getType()));
                    
                    Show();
                }
                
                @Override
                public void onFailure(IOException e) {
                
                }
            }).start();
        
    }
    
    private int getSpanCount(int itemWidth) {
        DisplayMetrics dm = new DisplayMetrics();
        Objects.requireNonNull(Objects.requireNonNull(getContext()).getDisplay()).getMetrics(dm);
        //获取屏幕宽度
        int widthPixels = dm.widthPixels;
        //获取设备像素密度
        float density = dm.densityDpi;
        //1dp*像素密度(dpi)/160(dpi) = 实际像素数(px)
        int dp = (int) ((widthPixels * 160) / density);
        
        if (itemWidth == 0) {
            return 0;
        }
        //计算出一行显示多少item
        
        double spanCount = ((double) dp) / ((double) itemWidth);
        
        return (int) Math.round(spanCount);
        
    }
    
    private School_sex_adapter myAdapter;
    private ItemTouchHelper mItemTouchHelper;
    
    public class MyItemTouchHelper extends Callback {
        
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView,
            @NonNull ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                final int dragFlags =
                    ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }
        
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder,
            @NonNull ViewHolder target) {
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(getCollegeMenAndWomenNumberAlls, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(getCollegeMenAndWomenNumberAlls, i, i - 1);
                }
            }
            myAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }
        
        @Override
        public void onSwiped(@NonNull ViewHolder viewHolder, int direction) {
        
        }
        
        /**
         * 长按选中Item的时候开始调用
         *
         * @param viewHolder
         * @param actionState
         */
        @Override
        public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);   拖拽过程中的颜色
            }
            super.onSelectedChanged(viewHolder, actionState);
        }
        
        /**
         * 手指松开的时候还原
         *
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
//            viewHolder.itemView.setBackgroundColor(Color.YELLOW);     拖拽完成后的颜色
        }
        
        /**
         * 重写拖拽不可用
         *
         * @return
         */
        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
        
    }
    
    private void Show() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount(200)));
        recyclerView.setAdapter(myAdapter = new School_sex_adapter(seeklist));
        mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelper());
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        myAdapter.setOnItemClickListener(position -> {
            if (imm != null) {
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
            }
            ((FragmentActivity) getActivity()).setFragment(new Fragment_count_school(seeklist.get(position)));
        });
        
    }
    
    @Override
    public void onClick(View v) {
    
    }
}
