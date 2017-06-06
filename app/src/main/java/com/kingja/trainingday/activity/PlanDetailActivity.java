package com.kingja.trainingday.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
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
import com.kingja.trainingday.util.CheckUtil;
import com.kingja.trainingday.util.TimeUtil;
import com.kingja.trainingday.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
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
    private AppCompatImageView aiv_sun;
    private Plan plan;
    private int[] fromLocation;
    private PlanDayAdapter planDayAdapter;
    private TextView mTvSequenceDays;
    private TextView mTvIsFinishedToday;


    @Override
    public void initVariable() {
        plan = (Plan) getIntent().getSerializableExtra("plan");
        planDays = DBManager.getInstance().getPlanDays(plan.getPlanId());
    }


    private int getSequenceDays(List<PlanDay> planDays) {
        List<Integer> maxSequenceDay = new ArrayList<>();
        int sequenceDay = 0;
        for (int i = 0; i < planDays.size(); i++) {
            if (planDays.get(i).getStatus() == 1) {
                sequenceDay++;
                if (i == planDays.size() - 1) {
                    maxSequenceDay.add(sequenceDay);
                }
            } else {
                maxSequenceDay.add(sequenceDay);
                sequenceDay = 0;
            }
        }
        return Collections.max(maxSequenceDay);
    }

    private boolean isTodayFinished(List<PlanDay> planDays) {
        for (PlanDay planDay : planDays) {
            if (planDay.getDate().equals(TimeUtil.getNowDate()) && planDay.getStatus() == 1) {
                return true;
            }
        }
        return false;
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
        mTvSequenceDays = (TextView) findViewById(R.id.tv_sequenceDays);
        mTvIsFinishedToday = (TextView) findViewById(R.id.tv_isFinishedToday);
        mTvPlanMonth = (TextView) findViewById(R.id.tv_planMonth);
        mTvPlanContent = (TextView) findViewById(R.id.tv_planContent);
        mTvPlanGift = (TextView) findViewById(R.id.tv_planGift);
        mRvPlanDetail = (RecyclerView) findViewById(R.id.rv_planDetail);
        aiv_sun = (AppCompatImageView) findViewById(R.id.aiv_sun);

        planDayAdapter = new PlanDayAdapter(this, planDays);
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
        if (!CheckUtil.hasFinished(plan.getEndDate()) && !isTodayFinished(planDays)) {
            aiv_sun.setVisibility(View.VISIBLE);
        }
        if (!CheckUtil.hasFinished(plan.getEndDate())) {
            mTvIsFinishedToday.setVisibility(View.VISIBLE);
        }
        mTvSequenceDays.setText("连续完成" + getSequenceDays(planDays) + "天");
        mTvIsFinishedToday.setText(isTodayFinished(planDays) ? "今日已完成" : "今日未完成");
        mTvPlanMonth.setText(plan.getStartDate() + "-" + plan.getEndDate());
        mTvPlanContent.setText(plan.getPlanContent());
        mTvPlanGift.setText(plan.getGift());
        aiv_sun.setOnClickListener(v -> {
            startStarAnimation();
        });
    }

    public void startStarAnimation() {
        int[] toLocation = planDayAdapter.getLocation();
        if (toLocation == null) {
            ToastUtil.showToast("已过期");
            return;
        }
        aiv_sun.animate().translationX(toLocation[0] - fromLocation[0]).translationY(toLocation[1] - fromLocation[1])
                .rotation(360).setInterpolator(new OvershootInterpolator()).setDuration(1500).setListener(new AnimatorListenerAdapter() {


            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                aiv_sun.setColorFilter(getResources().getColor(R.color.k_yellow));
                aiv_sun.setEnabled(false);
                mTvIsFinishedToday.setText("今日已完成" );
            }
        }).start();

    }

    public static void goActivity(Context context, Plan plan, int position) {
        Intent intent = new Intent(context, PlanDetailActivity.class);
        intent.putExtra("plan", plan);
        intent.putExtra("position", position);
        context.startActivity(intent);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fromLocation = new int[2];
        aiv_sun.getLocationOnScreen(fromLocation);
        Log.e(TAG, "fromLocationX: " + fromLocation[0]);
        Log.e(TAG, "fromLocationY: " + fromLocation[1]);
        getStateHegiht();
    }

    public int getStateHegiht() {
        Rect rectangle = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }
}
