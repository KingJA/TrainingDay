package com.kingja.trainingday.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kingja.trainingday.service.InitializeService;
import com.kingja.trainingday.util.AlarmPlayer;
import com.kingja.trainingday.util.AlarmUtil;

/**
 * Description:TODO
 * Create Time:2017/6/13 17:13
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmPlayer.getInstance(context).vibrate();
        context.startService(new Intent(context, InitializeService.class));
    }
}