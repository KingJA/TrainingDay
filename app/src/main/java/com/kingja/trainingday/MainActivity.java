package com.kingja.trainingday;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kingja.trainingday.base.BaseActivity;
import com.kingja.trainingday.fragment.PersonalFragment;
import com.kingja.trainingday.fragment.PlanFragment;
import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.ui.RippleLinearLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.aiv_plane)
    AppCompatImageView aivPlane;
    @BindView(R.id.tv_plane)
    TextView tvPlane;
    @BindView(R.id.rll_plane)
    RippleLinearLayout rllPlane;
    @BindView(R.id.aiv_personal)
    AppCompatImageView aivPersonal;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.rll_personal)
    RippleLinearLayout rllPersonal;
    private PlanFragment planFragment;
    private PersonalFragment personalFragment;

    @Override
    public void initVariable() {

    }

    @Override
    public int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewAndListener() {
        setTab(0);
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rll_plane, R.id.rll_personal})
    public void onSwitch(View view) {
        switch (view.getId()) {
            case R.id.rll_plane:
                setTab(0);
                break;
            case R.id.rll_personal:
                setTab(1);
                break;

        }
    }

    private void setTab(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        resetStatus(transaction);
        switch (index) {
            case 0:
                if (planFragment == null) {
                    planFragment = new PlanFragment();
                    transaction.add(R.id.fl_main, planFragment);
                } else {
                    transaction.show(planFragment);
                }
                tvPlane.setTextColor(getResources().getColor(R.color.k_black));
                aivPlane.setColorFilter(getResources().getColor(R.color.k_black));
                break;
            case 1:
                if (personalFragment == null) {
                    personalFragment = new PersonalFragment();
                    transaction.add(R.id.fl_main, personalFragment);
                } else {
                    transaction.show(personalFragment);
                }
                tvPersonal.setTextColor(getResources().getColor(R.color.k_black));
                aivPersonal.setColorFilter(getResources().getColor(R.color.k_black));
                break;
        }
        transaction.commit();
    }

    private void resetStatus(FragmentTransaction transaction) {
        tvPersonal.setTextColor(getResources().getColor(R.color.k_font_9));
        tvPlane.setTextColor(getResources().getColor(R.color.k_font_9));

        aivPlane.setColorFilter(getResources().getColor(R.color.k_font_9));
        aivPersonal.setColorFilter(getResources().getColor(R.color.k_font_9));
        if (planFragment != null) {
            transaction.hide(planFragment);
        }
        if (personalFragment != null) {
            transaction.hide(personalFragment);
        }
    }
}
