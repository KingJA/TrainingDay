package com.kingja.trainingday.activity;

import android.util.Log;

import com.kingja.trainingday.R;
import com.kingja.trainingday.base.BaseTitleActivity;
import com.kingja.trainingday.inject.commonent.AppComponent;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Description:TODO
 * Create Time:2017/6/12 14:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class RingActivity extends BaseTitleActivity {
    @Override
    protected void initComponent(AppComponent appComponent) {

    }

    @Override
    protected void initVariable() {

        ArrayList<Integer> list = new ArrayList<Integer>();
        Field[] fields = R.raw.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                list.add(fields[i].getInt(R.raw.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.e("raw文件目录", list.size()+"");

    }

    @Override
    protected String getContentTitle() {
        return "选择铃声";
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_rv;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {

    }
}
