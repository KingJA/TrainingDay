package com.kingja.trainingday.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kingja.trainingday.R;
import com.kingja.trainingday.adapter.MainPagerAdapter;
import com.kingja.trainingday.base.BaseTitleActivity;
import com.kingja.trainingday.fragment.DefalRingFragment;
import com.kingja.trainingday.fragment.LocalRingFragment;
import com.kingja.trainingday.inject.commonent.AppComponent;

/**
 * Description:TODO
 * Create Time:2017/6/12 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RingActivity extends BaseTitleActivity {
    private TabLayout mTbRing;
    private ViewPager mVpRing;


    private String[] tabItems;

    private Fragment[] mFragments=new Fragment[2];

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initVariable() {
        tabItems = getResources().getStringArray(R.array.rings);

    }

    @Override
    protected String getContentTitle() {
        return "选择铃声";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ring;
    }

    @Override
    protected void initView() {
        mTbRing = (TabLayout) findViewById(R.id.tb_ring);
        mVpRing = (ViewPager) findViewById(R.id.vp_ring);

        mTbRing.setTabMode(TabLayout.MODE_FIXED);
        mTbRing.addTab(mTbRing.newTab().setText(tabItems[0]));
        mTbRing.addTab(mTbRing.newTab().setText(tabItems[1]));
        mFragments[0]=new DefalRingFragment();
        mFragments[1]=new LocalRingFragment();
        mVpRing.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mFragments, tabItems));
        mTbRing.setupWithViewPager(mVpRing);
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {

    }
}
