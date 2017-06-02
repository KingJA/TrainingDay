package com.kingja.trainingday.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.kingja.trainingday.R;
import com.kingja.trainingday.base.BaseTitleActivity;
import com.kingja.trainingday.inject.commonent.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:TODO
 * Create Time:2017/6/2 13:34
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddPlanActivity extends BaseTitleActivity implements BaseTitleActivity.OnRightClickListener {
    @BindView(R.id.tv_startDate)
    TextView tvStartDate;
    @BindView(R.id.tv_days)
    TextView tvDays;

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected String getContentTitle() {
        return "新计划";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_plan;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        setOnRightClick("发布", this);
    }

    @Override
    public void onRightClick() {
        String startDate = tvStartDate.getText().toString().trim();

    }

}
