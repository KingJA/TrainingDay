package com.kingja.trainingday.fragment;

import android.widget.TextView;

import com.kingja.trainingday.R;
import com.kingja.trainingday.base.BaseFragment;
import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanDay;
import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.util.CheckUtil;
import com.kingja.trainingday.util.StringUtil;

import java.util.List;

import butterknife.BindView;

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
    @BindView(R.id.tv_sequenceFinishedPlanRate)
    TextView tvSequenceFinishedPlanRate;
    @BindView(R.id.tv_planFinishedRate)
    TextView tvPlanFinishedRate;
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
        tvTotlePlanCount.setText("总计划数：" + plans.size());
        tvActionPlanCount.setText("进行中计划数：" + getActionPlanCount(plans));
        tvSequenceFinishedPlanCount.setText("连击完成计划数：" + getSequenceFinishedPlanCount(plans));
        tvFinishedDaysCount.setText("完成天数：" + getFinishedCount(planDays));
        tvSequenceFinishedPlanRate.setText("连击完成率：" + StringUtil.getPercent(getSequenceFinishedPlanCount(plans),
                plans.size()));
        tvPlanFinishedRate.setText("计划完成率：" + StringUtil.getPercent(getFinishedCount(planDays), planDays.size()));

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_personal;
    }

}
