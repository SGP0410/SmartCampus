package com.example.smartcampuslibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID());
        initView();
        initData();
    }

    protected abstract int layoutResID();
    protected abstract void initView();
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
