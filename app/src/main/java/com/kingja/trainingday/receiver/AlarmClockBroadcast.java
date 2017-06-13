package com.kingja.trainingday.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.greendaobean.PlanClock;
import com.kingja.trainingday.service.AlarmService;
import com.kingja.trainingday.util.IntentUtil;

/**
 * Description:唤醒广播，保证能顺利执行闹钟行为。
 * Create Time:2017/6/13 11:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AlarmClockBroadcast extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PlanClock planClock = IntentUtil.getData(intent, PlanClock.class);
        //开启唤起服务
        Intent alarmServiceIntent = new Intent(context, AlarmService.class);
        alarmServiceIntent.putExtra(PlanClock.class.getSimpleName(), planClock);
        startWakefulService(context, alarmServiceIntent);
        //删除闹钟
        DBManager.getInstance().removePlanClock(planClock.getClockId());
    }
}
