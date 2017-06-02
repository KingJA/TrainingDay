package com.kingja.trainingday.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.trainingday.R;
import com.kingja.trainingday.activity.AddPlanActivity;
import com.kingja.trainingday.base.BaseFragment;
import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.util.GoUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2017/6/2 10:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlanFragment extends BaseFragment {
    @BindView(R.id.aiv_add_plan)
    AppCompatImageView aivAddPlan;

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewAndListener() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_plane;
    }

    @OnClick({R.id.aiv_add_plan})
    public void addPlan() {
        GoUtil.goActivity(getActivity(), AddPlanActivity.class);
    }
}
