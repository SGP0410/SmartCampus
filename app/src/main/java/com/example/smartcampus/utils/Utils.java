package com.example.smartcampus.utils;

import static com.example.smartcampus.Application.getContext;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {

    public static ProgressDialog showDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("网络请求中。。。");
        progressDialog.setTitle("提示");
        progressDialog.show();
        return progressDialog;
    }

}
