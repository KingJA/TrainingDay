package com.kingja.trainingday.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kingja.trainingday.R;
import com.kingja.trainingday.activity.WakeUpActivity;
import com.kingja.trainingday.greendaobean.PlanClock;
import com.kingja.trainingday.receiver.AlarmClockBroadcast;
import com.kingja.trainingday.util.AlarmPlayer;
import com.kingja.trainingday.util.IntentUtil;

/**
 * Description:TODO
 * Create Time:2017/6/9 14:54
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AlarmService extends IntentService {

    public AlarmService() {
        super("AlarmService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        PlanClock planClock = IntentUtil.getData(intent, PlanClock.class);
        //播放闹钟
        AlarmPlayer.getInstance(getBaseContext()).playRaw(R.raw.classics, true);
        //打开提示Activity
        IntentUtil.goActivityInService(getBaseContext(), WakeUpActivity.class, planClock);
        //释放唤起锁
        AlarmClockBroadcast.completeWakefulIntent(intent);
    }

}
