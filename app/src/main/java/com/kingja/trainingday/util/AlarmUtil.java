package com.kingja.trainingday.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kingja.trainingday.base.App;
import com.kingja.trainingday.greendaobean.PlanClock;
import com.kingja.trainingday.receiver.AlarmClockBroadcast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description:TODO
 * Create Time:2017/6/12 14:55
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AlarmUtil {
    public static void setAlarm(PlanClock planClock) {
        if (!TimeUtil.availableAlarm(planClock.getRemindTime())) {
            return;
        }
        Intent intent = new Intent(App.getContext(), AlarmClockBroadcast.class);
        intent.putExtra(PlanClock.class.getSimpleName(), planClock);
        PendingIntent sender = PendingIntent.getBroadcast(App.getContext(), planClock.getClockId().intValue()
                , intent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = format.parse(planClock.getRemindTime());
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AlarmManager am = (AlarmManager) App.getContext().getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }

    public static void cancleAlarm(Context context, int clockId) {
        Intent intent = new Intent(App.getContext(), AlarmClockBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(App.getContext(), clockId, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Service
                .ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }
}
