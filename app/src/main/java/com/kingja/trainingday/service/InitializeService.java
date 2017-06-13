package com.kingja.trainingday.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.greendaobean.PlanClock;
import com.kingja.trainingday.util.AlarmUtil;
import com.kingja.trainingday.util.TimeUtil;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/6/13 15:30
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class InitializeService extends IntentService {
    public InitializeService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        setClocks();
    }

    private void setClocks() {
        List<PlanClock> planClocks = DBManager.getInstance().getPlanClocks();
        /*删除过期闹钟，生成未过期闹钟*/
        for (PlanClock planClock : planClocks) {
            if (TimeUtil.availableAlarm(planClock.getRemindTime())) {
                AlarmUtil.setAlarm(planClock);
            } else {
                DBManager.getInstance().removePlanClock(planClock.getClockId());
            }

        }
    }
}
