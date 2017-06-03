package com.kingja.trainingday.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kingja.recyclerviewhelper.LayoutHelper;
import com.kingja.recyclerviewhelper.RecyclerViewHelper;
import com.kingja.trainingday.R;
import com.kingja.trainingday.adapter.PlanDayAdapter;
import com.kingja.trainingday.base.BaseTitleActivity;
import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanDay;
import com.kingja.trainingday.inject.commonent.AppComponent;

import java.util.List;


/**
 * Description:TODO
 * Create Time:2017/6/3 11:07
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PlanDetailActivity extends BaseTitleActivity {

    private List<PlanDay> planDays;
    private TextView mTvPlanMonth;
    private TextView mTvPlanContent;
    private TextView mTvPlanGift;
    private RecyclerView mRvPlanDetail;
    private Plan plan;


    @Override
    public void initVariable() {
        plan = (Plan) getIntent().getSerializableExtra("plan");
        planDays = DBManager.getInstance().getPlanDays(plan.getPlanId());
    }

    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected String getContentTitle() {
        return "计划详情";
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_plan_detail;
    }

    @Override
    protected void initView() {
        mTvPlanMonth = (TextView) findViewById(R.id.tv_planMonth);
        mTvPlanContent = (TextView) findViewById(R.id.tv_planContent);
        mTvPlanGift = (TextView) findViewById(R.id.tv_planGift);
        mRvPlanDetail = (RecyclerView) findViewById(R.id.rv_planDetail);

        PlanDayAdapter planDayAdapter = new PlanDayAdapter(this, planDays);
        new RecyclerViewHelper.Builder(this)
                .setAdapter(planDayAdapter)
                .setLayoutStyle(LayoutHelper.LayoutStyle.GRID)
                .setDividerHeight(0)
                .setDividerColor(0xff0000)
                .setColumns(4)
                .build()
                .attachToRecyclerView(mRvPlanDetail);
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mTvPlanMonth.setText(plan.getStartDate());
        mTvPlanContent.setText(plan.getPlanContent());
        mTvPlanGift.setText(plan.getGift());
    }

    public static void goActivity(Context context, Plan plan, int position) {
        Intent intent = new Intent(context, PlanDetailActivity.class);
        intent.putExtra("plan", plan);
        intent.putExtra("position", position);
        context.startActivity(intent);

    }
}
