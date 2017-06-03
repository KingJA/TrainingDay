package com.kingja.trainingday.base;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingja.trainingday.R;
import com.kingja.trainingday.inject.commonent.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：TODO
 * Create Time：2017/3/20 14:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BaseTitleActivity extends BaseActivity {
    @BindView(R.id.ll_title_back)
    LinearLayout llTitleBack;
    @BindView(R.id.tv_title_title)
    TextView tvTitleTitle;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.tv_right)
    TextView tvRight;


    @Override
    public int getContentId() {
        return R.layout.activity_back;
    }

    protected abstract void initComponent(AppComponent appComponent);

    @Override
    protected void initViewAndListener() {
        tvTitleTitle.setText(getContentTitle() == null ? "" : getContentTitle());
        llTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View content = View.inflate(this, getContentView(), null);
        if (content != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            flContent.addView(content, params);
        }
        initView();
    }

    public void setOnRightClick(String rightText, final OnRightClickListener onClickListener) {
        tvRight.setText(rightText);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onRightClick();
                }
            }
        });
    }

    public interface OnRightClickListener {
        void onRightClick();
    }

    protected abstract void initVariable();

    protected abstract String getContentTitle();

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initNet();

    protected abstract void initData();

}
