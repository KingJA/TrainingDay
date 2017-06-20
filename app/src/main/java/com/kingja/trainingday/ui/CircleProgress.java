package com.kingja.trainingday.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.kingja.trainingday.R;

/**
 * Description:TODO
 * Create Time:2017/6/20 14:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class CircleProgress extends View {
    private float DEFAULT_UNREACHED_WIDTH = 6;
    private float DEFAULT_REACHED_WIDTH = 10;
    private int DEFAULT_UNREACHED_COLOR = 0xffbebebe;
    private int DEFAULT_REACHED_COLOR = 0xff00ffff;
    private float mUnreachedWidth;
    private float mReachedWidth;
    private int mUnreachedColor;
    private int mReachedColor;
    private int mWidth;
    private int mHeight;
    private Paint mUnreachedPaint;
    private int mDiameter;
    private Paint mTextPaint;
    private Paint mProgressPaint;
    private Paint mReachedPaint;
    private float mOffset;
    private int mProgress;
    private String mTipText = "";


    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCircleProgress(attrs);
    }

    private void initCircleProgress(AttributeSet attrs) {
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        mProgress = typedArray.getInteger(R.styleable.CircleProgress_cp_progress, 0);
        mTipText = typedArray.getString(R.styleable.CircleProgress_cp_tip);

        mUnreachedWidth = dp2px(typedArray.getDimension(R.styleable.CircleProgress_cp_unreachedWidth,
                DEFAULT_UNREACHED_WIDTH));
        mReachedWidth = dp2px(typedArray.getDimension(R.styleable.CircleProgress_cp_reachedWidth,
                DEFAULT_REACHED_WIDTH));

        mUnreachedColor = typedArray.getColor(R.styleable.CircleProgress_cp_unreachedColor, DEFAULT_UNREACHED_COLOR);
        mReachedColor = typedArray.getColor(R.styleable.CircleProgress_cp_reachedColor, DEFAULT_REACHED_COLOR);
    }

    private void initPaint() {
        mUnreachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedPaint.setStrokeWidth(mUnreachedWidth);
        mUnreachedPaint.setColor(mUnreachedColor);
        mUnreachedPaint.setStyle(Paint.Style.STROKE);
        mUnreachedPaint.setStrokeCap(Paint.Cap.ROUND);

        mReachedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedPaint.setStrokeWidth(mReachedWidth);
        mReachedPaint.setColor(mReachedColor);
        mReachedPaint.setStyle(Paint.Style.STROKE);
        mReachedPaint.setStrokeCap(Paint.Cap.ROUND);


    }

    protected int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mDiameter = Math.min(mWidth, mHeight);
        mOffset = Math.max(mUnreachedWidth, mReachedWidth) * 0.5f;

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setTextSize(mDiameter * 0.2f);
        mProgressPaint.setColor(mReachedColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mDiameter * 0.1f);
        mTextPaint.setColor(0xff999999);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(new RectF(mOffset, mOffset, mDiameter - mOffset, mDiameter - mOffset), -225, 270, false,
                mUnreachedPaint);
        canvas.drawArc(new RectF(mOffset, mOffset, mDiameter - mOffset, mDiameter - mOffset), -225, 270 * mProgress /
                        100f, false,
                mReachedPaint);
        drawProgressText(canvas);
        drawTipText(canvas);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }



    private void drawProgressText(Canvas canvas) {
        float textWidth = mProgressPaint.measureText(mProgress + "%");
        Paint.FontMetrics fontMetrics = mProgressPaint.getFontMetrics();
        float textHeight = -(fontMetrics.ascent + fontMetrics.descent);
        canvas.drawText(mProgress + "%", mDiameter * 0.5f - textWidth * 0.5f, mDiameter * 0.5f + textHeight * 0.5f,
                mProgressPaint);
    }

    private void drawTipText(Canvas canvas) {
        float textWidth = mTextPaint.measureText(mTipText);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float textHeight = -(fontMetrics.ascent + fontMetrics.descent);
        canvas.drawText(mTipText + "", mDiameter * 0.5f - textWidth * 0.5f, mDiameter - textHeight,
                mTextPaint);
    }
}
