package com.example.smartcampus.fragment.applyFragment;

import android.view.View;
import android.webkit.WebView;

import com.example.smartcampus.R;
import com.example.smartcampuslibrary.fragment.BaseFragment;

public class XiaoYuanKaWebFragment extends BaseFragment {
    private String content;
    private WebView web;

    public XiaoYuanKaWebFragment(String content) {
        this.content = content;
    }

    @Override
    protected int layoutResId() {
        return R.layout.web_layout;
    }

    @Override
    protected void initView(View view) {

        web = view.findViewById(R.id.web);
    }

    @Override
    protected void initData() {
        web.loadUrl("http://www.baidu.com");
    }

    @Override
    public void onClick(View v) {

    }
}
