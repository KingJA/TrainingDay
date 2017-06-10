package com.kingja.trainingday.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";


    private int revealX;
    private int revealY;
    private LinearLayout mLlRemindTime;
    private LinearLayout mLlRemindType;
    private int mRemindTypeHeight;
    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            llRootView.setVisibility(View.INVISIBLE);
            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = llRootView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        llRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }

        } else {
            llRootView.setVisibility(View.VISIBLE);
        }
    }

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
        mLlRemindTime = (LinearLayout) findViewById(R.id.ll_remindTime);
        mLlRemindType = (LinearLayout) findViewById(R.id.ll_remindType);
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        setOnRightClick("发布", this);
        mLlRemindTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemindType();
            }
        });
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

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(llRootView.getWidth(), llRootView.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(llRootView, x, y, 0, finalRadius);
            circularReveal.setDuration(800);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            llRootView.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(llRootView.getWidth(), llRootView.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    llRootView, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    llRootView.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unRevealActivity();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e(TAG, "onWindowFocusChanged getHeight: " + mLlRemindTime.getHeight());
        mRemindTypeHeight = mLlRemindType.getHeight();
        layoutParams = mLlRemindType.getLayoutParams();
        layoutParams.height=0;
        mLlRemindType.setLayoutParams(layoutParams);
        mLlRemindType.setVisibility(View.GONE);
    }

    private void showRemindType() {
        mLlRemindType.setVisibility(View.VISIBLE);
        mLlRemindType.setPivotX(0);
        mLlRemindType.setPivotY(0);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mLlRemindType, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(mLlRemindType, View.SCALE_X, 0.0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(mLlRemindType, View.SCALE_Y, 0.0f, 1f);
        ValueAnimator heightAnimator = ValueAnimator.ofInt(mRemindTypeHeight);
        heightAnimator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            Log.e(TAG, "currentValue: "+currentValue );
            layoutParams = mLlRemindType.getLayoutParams();
            layoutParams.height=currentValue;
            mLlRemindType.setLayoutParams(layoutParams);

        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator,heightAnimator);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    private void showDialog() {
    }
}
