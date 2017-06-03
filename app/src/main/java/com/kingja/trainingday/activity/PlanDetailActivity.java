package com.kingja.trainingday.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.kingja.recyclerviewhelper.BaseRvAdaper;
import com.kingja.recyclerviewhelper.LayoutHelper;
import com.kingja.recyclerviewhelper.RecyclerViewHelper;
import com.kingja.trainingday.R;
import com.kingja.trainingday.adapter.PlanDayAdapter;
import com.kingja.trainingday.anim.Point;
import com.kingja.trainingday.anim.PointEvaluator;
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
    private AppCompatImageView aiv_sun;
    private Plan plan;
    private int[] fromLocation;
    private PlanDayAdapter planDayAdapter;


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
        aiv_sun = (AppCompatImageView) findViewById(R.id.aiv_sun);

        planDayAdapter = new PlanDayAdapter(this, planDays);
        planDayAdapter.setOnItemClickListener((planDay, position) -> {

            int[] location = planDayAdapter.getLocation(position);
            Log.e(TAG, "x: " + location[0]);
            Log.e(TAG, "y: " + location[1]);
        });
        new RecyclerViewHelper.Builder(this)
                .setAdapter(planDayAdapter)
                .setLayoutStyle(LayoutHelper.LayoutStyle.GRID)
                .setDividerHeight(0)
                .setDividerColor(0xff0000)
                .setColumns(4)
                .build()
                .attachToRecyclerView(mRvPlanDetail);

        ViewTreeObserver vto2 = aiv_sun.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                aiv_sun.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                fromLocation = new int[2];
                aiv_sun.getLocationOnScreen(fromLocation);
                Log.e(TAG, "fromLocationX: " + fromLocation[0]);
                Log.e(TAG, "fromLocationY: " + fromLocation[1]);
            }
        });
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mTvPlanMonth.setText(plan.getStartDate());
        mTvPlanContent.setText(plan.getPlanContent());
        mTvPlanGift.setText(plan.getGift());
        aiv_sun.setOnClickListener(v -> {
            startStarAnimation(1);
        });
    }

    public void startStarAnimation(int position) {
        int[] toLocation = planDayAdapter.getLocation(position);
        Point point1 = new Point(fromLocation[0], fromLocation[1]);
        Point point2 = new Point(toLocation[0], toLocation[1]);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), point1, point2);
        anim.setDuration(5000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point point = (Point) valueAnimator.getAnimatedValue();
                Log.e(TAG, "pointx: "+point.getX() );
                Log.e(TAG, "pointy: "+point.getY() );
                aiv_sun.setX(point.getX());
                aiv_sun.setY(point.getY());
            }
        });
        anim.start();
    }

    public static void goActivity(Context context, Plan plan, int position) {
        Intent intent = new Intent(context, PlanDetailActivity.class);
        intent.putExtra("plan", plan);
        intent.putExtra("position", position);
        context.startActivity(intent);

    }
}
