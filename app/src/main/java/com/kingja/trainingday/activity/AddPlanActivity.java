package com.kingja.trainingday.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kingja.rxbus2.RxBus;
import com.kingja.trainingday.R;
import com.kingja.trainingday.base.BaseTitleActivity;
import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.event.RefreshEvent;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanDay;
import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.util.CheckUtil;
import com.kingja.trainingday.util.StringUtil;
import com.kingja.trainingday.util.TimeUtil;
import com.kingja.trainingday.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:TODO
 * Create Time:2017/6/2 13:34
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddPlanActivity extends BaseTitleActivity implements BaseTitleActivity.OnRightClickListener {
    private EditText mEtPlanContent;
    private TextView mTvStartDate;
    private TextView mTvDays;
    private EditText mEtGift;


    @Override
    public void initVariable() {

    }

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
        mEtPlanContent = (EditText) findViewById(R.id.et_planContent);
        mTvStartDate = (TextView) findViewById(R.id.tv_startDate);
        mTvDays = (TextView) findViewById(R.id.tv_days);
        mEtGift = (EditText) findViewById(R.id.et_gift);
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
        String planContent = mEtPlanContent.getText().toString().trim();
        String startDate = mTvStartDate.getText().toString().trim();
        int days = Integer.valueOf(mTvDays.getText().toString().trim());
        String gift = mEtGift.getText().toString().trim();
        if (CheckUtil.checkEmpty(planContent, "请输入计划事项") &&
                CheckUtil.checkEmpty(startDate, "请选择开始时间")) {
            publishPlan(planContent, startDate, days, gift);
        }

    }

    private void publishPlan(String planContent, String startDate, int days, String gift) {
        Plan plan = new Plan();
        String planId = StringUtil.getUUID();
        plan.setPlanId(planId);
        plan.setCreateTime(TimeUtil.getNowTime());
        plan.setGift(gift);
        plan.setPlanDays(days);
        plan.setPlanContent(planContent);
        plan.setStartDate(startDate);
        plan.setEndDate(TimeUtil.getEndDate(startDate, days));
        DBManager.getInstance().addPlan(plan);

        List<String> dates = TimeUtil.getDates(startDate, days);
        for (String date : dates) {
            PlanDay planDay = new PlanDay();
            planDay.setDate(date);
            planDay.setDayId(StringUtil.getUUID());
            planDay.setPlanId(planId);
            DBManager.getInstance().addPlanDay(planDay);
        }

        RxBus.getDefault().post(new RefreshEvent());
        finish();
        ToastUtil.showToast("发布成功");
    }
}
