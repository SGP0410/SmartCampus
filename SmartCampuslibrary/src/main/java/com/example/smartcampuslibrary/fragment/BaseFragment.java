package com.example.smartcampuslibrary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.smartcampuslibrary.activity.BaseActivity;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutResId() , container , false);
    }

    protected abstract int layoutResId();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected abstract void initView(View view);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    protected void toClass(Context context , Class<? extends BaseActivity> clazz , Bundle bundle){
        Intent intent = new Intent(context, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void toClass(Context context , Class<? extends BaseActivity> clazz , Bundle bundle , int requestCode){
        Intent intent = new Intent(context, clazz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent , requestCode);
    }
}
