package com.kingja.trainingday.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Description：TODO
 * Create Time：2016/10/715:33
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private String[] titles;

    public MainPagerAdapter(FragmentManager fm, Fragment[] fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }



    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
