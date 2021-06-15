package com.example.smartcampuslibrary.utils;

import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class MyFragmentManage {

    private List<Fragment> fragmentList;

    public MyFragmentManage() {
        fragmentList = new ArrayList<>();
    }

    public void add(Fragment fragment){
        fragmentList.add(fragment);
    }

    public Fragment get(){
        if (fragmentList.size() == 0){
            return null;
        }else {
            int index = fragmentList.size() - 1;
            Fragment fragment = fragmentList.get(index);
            fragmentList.remove(index);
            return fragment;
        }
    }
}
