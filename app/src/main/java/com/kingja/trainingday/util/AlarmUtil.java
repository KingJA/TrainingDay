package com.kingja.trainingday.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kingja.trainingday.base.App;
import com.kingja.trainingday.greendaobean.PlanDay;

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
    public static void setAlarm(PlanDay planDay, Class service) {
        if (!TimeUtil.availableAlarm(planDay.getRemindTime())) {
            return;
        }
        Intent intent = new Intent(App.getContext(), service);
        intent.putExtra("PLAN_DAY", planDay);
        PendingIntent sender = PendingIntent.getService(App.getContext(), Long.valueOf(planDay.getDayId()).intValue()
                , intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date = format.parse(planDay.getRemindTime());
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AlarmManager am = (AlarmManager) App.getContext().getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        Log.e("AlarmUtil", "设定闹钟时间: "+planDay.getRemindTime() );
    }
}
