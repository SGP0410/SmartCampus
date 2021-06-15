package com.example.smartcampuslibrary.activity;


import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.smartcampuslibrary.utils.MyFragmentManage;


public abstract class BaseFragmentActivity extends BaseActivity {
    private MyFragmentManage myFragmentManage = new MyFragmentManage();
    private Fragment countFragment;

    public void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()){//返回上一界面时执行，将当前界面清除，显示上一界面
            transaction.hide(countFragment).show(fragment);
            transaction.remove(countFragment);
        }else {//添加新的界面时执行
            if (countFragment != null && countFragment.isAdded()) {
                transaction.hide(countFragment);//隐藏上一界面
                myFragmentManage.add(countFragment);//将上一界面实例对象保存
            }
            transaction.add(frameLayoutID() , fragment , fragment.getTag());//添加新界面
        }
        countFragment = fragment;
        transaction.commit();
    }
    @Override
    public void onBackPressed() {//返回时调用
        Fragment fragment = myFragmentManage.get();
        if (fragment == null){
            finish();
        }else {
            setFragment(fragment);
        }
    }

    protected abstract int frameLayoutID();
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
