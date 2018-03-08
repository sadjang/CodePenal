package com.example.sadjang.codepenal.ReCycler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by sadjang on 09/10/2016.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> listfragments;
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> listfragments) {
        super(fm);
        this.listfragments=listfragments;
    }

    @Override
    public Fragment getItem(int position) {
        return listfragments.get(position);
    }

    @Override
    public int getCount() {
        return listfragments.size();
    }
}
