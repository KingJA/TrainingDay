package com.kingja.trainingday.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
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
import com.kingja.trainingday.util.ToastUtil;

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
                .rotation(360).setDuration(2000).setListener(new AnimatorListenerAdapter() {


            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                aiv_sun.setColorFilter(getResources().getColor(R.color.k_yellow));
                aiv_sun.setEnabled(false);
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
