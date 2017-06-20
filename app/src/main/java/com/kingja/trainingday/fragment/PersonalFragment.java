package com.kingja.trainingday.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingja.trainingday.R;
import com.kingja.trainingday.base.BaseFragment;
import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanDay;
import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.ui.CircleProgress;
import com.kingja.trainingday.util.CheckUtil;
import com.kingja.trainingday.util.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2017/6/2 10:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PersonalFragment extends BaseFragment {
    @BindView(R.id.tv_totlePlanCount)
    TextView tvTotlePlanCount;
    @BindView(R.id.tv_actionPlanCount)
    TextView tvActionPlanCount;
    @BindView(R.id.tv_sequenceFinishedPlanCount)
    TextView tvSequenceFinishedPlanCount;
    @BindView(R.id.tv_finishedDaysCount)
    TextView tvFinishedDaysCount;
    @BindView(R.id.cp_sequenceFinishedPlanRate)
    CircleProgress cpSequenceFinishedPlanRate;
    @BindView(R.id.cp_planFinishedRate)
    CircleProgress cpPlanFinishedRate;

    private List<Plan> plans;
    private List<PlanDay> planDays;

    @Override
    protected void initVariable() {
        plans = DBManager.getInstance().getPlans();
        planDays = DBManager.getInstance().getPlanDays();
    }

    private int getActionPlanCount(List<Plan> plans) {
        int result = 0;
        for (Plan plan : plans) {
            if (!CheckUtil.hasFinished(plan.getEndDate())) {
                result++;
            }
        }
        return result;
    }

    private int getSequenceFinishedPlanCount(List<Plan> plans) {
        int result = 0;
        for (Plan plan : plans) {
            List<PlanDay> planDays = DBManager.getInstance().getPlanDays(plan.getPlanId());
            int finishedCount = 0;
            for (PlanDay planDay : planDays) {
                if (planDay.getStatus() == 1) {
                    finishedCount++;
                }
            }
            if (finishedCount == planDays.size()) {
                result++;
            }
        }
        return result;
    }

    private int getFinishedCount(List<PlanDay> planDays) {
        int result = 0;
        for (PlanDay planDay : planDays) {
            if (planDay.getStatus() == 1) {
                result++;
            }
        }
        return result;
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewAndListener() {
        tvTotlePlanCount.setText(plans.size() + "");
        tvActionPlanCount.setText(getActionPlanCount(plans) + "");
        tvSequenceFinishedPlanCount.setText(getSequenceFinishedPlanCount(plans) + "");
        tvFinishedDaysCount.setText(getFinishedCount(planDays) + "");
        cpSequenceFinishedPlanRate.setProgress(StringUtil.getPercent(getSequenceFinishedPlanCount(plans),
                plans.size()));
        cpPlanFinishedRate.setProgress(StringUtil.getPercent(getFinishedCount(planDays), planDays.size()));

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_personal;
    }


}
