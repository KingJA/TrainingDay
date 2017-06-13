package com.kingja.trainingday.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.rxbus2.RxBus;
import com.kingja.trainingday.R;
import com.kingja.trainingday.base.BaseTitleActivity;
import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.event.RefreshEvent;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanClock;
import com.kingja.trainingday.greendaobean.PlanDay;
import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.service.AlarmService;
import com.kingja.trainingday.util.AlarmUtil;
import com.kingja.trainingday.util.CheckUtil;
import com.kingja.trainingday.util.StringUtil;
import com.kingja.trainingday.util.TimeUtil;
import com.kingja.trainingday.util.ToastUtil;

import java.util.Calendar;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/6/2 13:34
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddPlanActivity extends BaseTitleActivity implements View.OnClickListener, BaseTitleActivity
        .OnRightClickListener {
    private EditText mEtPlanContent;
    private TextView mTvStartDate;
    private TextView mTvPlanDays;
    private EditText mEtGift;

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";


    private int revealX;
    private int revealY;
    private LinearLayout mLlRemindTime;
    private LinearLayout mLlRemindType;
    private int mRemindTypeHeight;
    private ViewGroup.LayoutParams layoutParams;
    private TimePickerDialog mTimePickerDialog;
    private TextView mTvRemindTime;
    private TextView mTvRemindType;
    private String mRemindTime;
    private LinearLayout mLlStartDate;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private LinearLayout mLlPlanDays;

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
        Calendar mCalendar = Calendar.getInstance();
        selectedYear = mCalendar.get(Calendar.YEAR);
        selectedMonth = mCalendar.get(Calendar.MONTH);
        selectedDay = mCalendar.get(Calendar.DAY_OF_MONTH);
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
        mTvRemindTime = (TextView) findViewById(R.id.tv_remindTime);
        mTvRemindType = (TextView) findViewById(R.id.tv_remindType);
        mTvStartDate = (TextView) findViewById(R.id.tv_startDate);
        mTvPlanDays = (TextView) findViewById(R.id.tv_planDays);
        mEtGift = (EditText) findViewById(R.id.et_gift);
        mLlRemindTime = (LinearLayout) findViewById(R.id.ll_remindTime);
        mLlRemindType = (LinearLayout) findViewById(R.id.ll_remindType);
        mLlStartDate = (LinearLayout) findViewById(R.id.ll_startDate);
        mLlPlanDays = (LinearLayout) findViewById(R.id.ll_planDays);
    }

    private void showTimePickerDialog() {
        mTimePickerDialog = new TimePickerDialog(this,
                (TimePickerDialog.OnTimeSetListener) (view, hourOfDay, minute) -> {
                    String mSelectedHour = String.format("%02d", hourOfDay);
                    String mSelectMinute = String.format("%02d", minute);
                    currentHour = hourOfDay;
                    currentMinute = minute;
                    mRemindTime = mSelectedHour + ":" + mSelectMinute;
                    mTvRemindTime.setText(mRemindTime);
                }, currentHour, currentMinute, true);
        mTimePickerDialog.show();
    }

    private void showDatePickerDialog() {
        new DatePickerDialog(this,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        selectedYear = year;
                        selectedMonth = monthOfYear + 1;
                        selectedDay = dayOfMonth;
                        mTvStartDate.setText(year + "/" + String.format("%02d", monthOfYear + 1) + "/" + String
                                .format("%02d", dayOfMonth));
                    }
                }
                , selectedYear, selectedMonth, selectedDay).show();
    }


    private void showRemindTypeDialog() {
        android.support.v7.app.AlertDialog.Builder b = new android.support.v7.app.AlertDialog.Builder(this);
        b.setTitle("提醒方式");
        b.setSingleChoiceItems(remindTypes, currentRemindType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentRemindType = which;
                mTvRemindType.setText(remindTypes[which]);
                dialog.dismiss();
            }
        });
        b.show();
    }

    private void showPlanDaysDialog() {
        android.support.v7.app.AlertDialog.Builder b = new android.support.v7.app.AlertDialog.Builder(this);
        b.setTitle("计划天数");
        b.setSingleChoiceItems(planDays, currentPlanDays, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentPlanDays = which;
                mTvPlanDays.setText(planDays[which]);
                dialog.dismiss();
            }
        });
        b.show();
    }

    private String[] remindTypes = {"铃声", "振动", "铃声+振动"};
    private String[] planDays = {"7", "15", "30"};

    private int currentHour = 20;
    private int currentMinute = 0;
    private int currentRemindType = 0;
    private int currentPlanDays = 0;

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        setOnRightClick("发布", this);
        mLlStartDate.setOnClickListener(this);
        mLlRemindTime.setOnClickListener(this);
        mLlRemindType.setOnClickListener(this);
        mLlPlanDays.setOnClickListener(this);
    }


    @Override
    public void onRightClick() {
        String planContent = mEtPlanContent.getText().toString().trim();
        String startDate = mTvStartDate.getText().toString().trim();
        int days = Integer.valueOf(mTvPlanDays.getText().toString().trim());
        String gift = mEtGift.getText().toString().trim();
        if (CheckUtil.checkEmpty(planContent, "请输入计划事项") &&
                CheckUtil.checkEmpty(startDate, "请选择开始时间")) {
            publishPlan(planContent, startDate, days, gift);
        }

    }

    private void publishPlan(String planContent, String startDate, int days, String gift) {
        Log.e(TAG, "publishPlan: ");
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
            String remindTime = date + " " + mRemindTime + ":00";
            long milliseconds = TimeUtil.getMilliseconds(remindTime);
            PlanDay planDay = new PlanDay();
            planDay.setDate(date);
            planDay.setPlanId(planId);
            planDay.setDayId(milliseconds);
            DBManager.getInstance().addPlanDay(planDay);

            PlanClock planClock = new PlanClock();
            planClock.setClockId(milliseconds);
            planClock.setRemindType(currentRemindType);
            planClock.setRemindTime(remindTime);

            AlarmUtil.setAlarm(planClock);
            DBManager.getInstance().addPlanClock(planClock);
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
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_startDate:
                showDatePickerDialog();
                break;
            case R.id.ll_remindTime:
                showTimePickerDialog();
                break;
            case R.id.ll_remindType:
                showRemindTypeDialog();
                break;
            case R.id.ll_planDays:
                showPlanDaysDialog();
                break;

        }
    }
}
