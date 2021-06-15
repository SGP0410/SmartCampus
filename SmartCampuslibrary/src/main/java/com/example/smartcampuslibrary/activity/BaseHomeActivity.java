package com.example.smartcampuslibrary.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseHomeActivity extends BaseActivity {
    private Fragment countFragment;

    public void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()){
            transaction.hide(countFragment).show(fragment);
        }else {
            if (countFragment != null){
                transaction.hide(countFragment);
            }
            transaction.add(frameLayoutID() , fragment , fragment.getTag());
        }
        countFragment = fragment;
        transaction.commit();
    }

    protected abstract int frameLayoutID();

}
