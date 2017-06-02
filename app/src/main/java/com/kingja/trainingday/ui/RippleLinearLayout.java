package com.kingja.trainingday.ui;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.kingja.trainingday.R;

/**
 * Description:TODO
 * Create Time:2017/6/1 12:57
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RippleLinearLayout extends LinearLayout {
    private String TAG = getClass().getSimpleName();
    private int pressedColor = getResources().getColor(R.color.k_half_grey);
    private int normalColor = getResources().getColor(R.color.k_transparent);
    private int alpha = 90;
    private boolean running;
    private float radius = 100;
    private float x;
    private float y;
    private Paint mRipplePaint;
    private float currentValue;

    public RippleLinearLayout(Context context) {
        this(context, null);
    }

    public RippleLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setDrawingCacheEnabled(true);
        this.setClickable(true);
        initRippleView();
    }

    private void animateRipple(MotionEvent event) {
        if (running) {
            return;
        }
        x = event.getX();
        y = event.getY();
        startRippleAnimator();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressedTransfer(normalColor, pressedColor);
                break;
            case MotionEvent.ACTION_UP:
                pressedTransfer(pressedColor, normalColor);
                animateRipple(event);
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }


    private void pressedTransfer(int colorFrom, int colorTo) {
        ValueAnimator pressedAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        pressedAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        pressedAnimation.setDuration(500);
        pressedAnimation.start();
    }


    public void startRippleAnimator() {
        ValueAnimator rippleAnimation = ValueAnimator.ofFloat(0f, radius);
        rippleAnimation.setDuration(500);
        rippleAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        rippleAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                running = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                running = false;
                mRipplePaint.setAlpha(alpha);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        rippleAnimation.start();
    }


    private void initRippleView() {
        mRipplePaint = new Paint();
        mRipplePaint.setAntiAlias(true);
        mRipplePaint.setStyle(Paint.Style.FILL);
        mRipplePaint.setColor(0xffff0000);
        mRipplePaint.setAlpha(alpha);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRipplePaint.setAlpha((int) (alpha - alpha * (currentValue / radius)));
        canvas.drawCircle(x, y, currentValue, mRipplePaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.max(getMeasuredWidth(), getMeasuredHeight());
    }
}
