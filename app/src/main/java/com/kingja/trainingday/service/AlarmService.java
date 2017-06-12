package com.kingja.trainingday.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kingja.trainingday.R;
import com.kingja.trainingday.activity.WakeUpActivity;
import com.kingja.trainingday.greendaobean.PlanDay;
import com.kingja.trainingday.util.VibratorUtil;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2017/6/9 14:54
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AlarmService extends Service {

    private SoundPool soundPool;
    private Vibrator vibrator;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PlanDay planDay = (PlanDay) intent.getSerializableExtra("PLAN_DAY");
        Log.e("AlarmService", "planDay: " + planDay.getRemindTime());
        vibrator = VibratorUtil.vibrateRepeat(getApplicationContext());
        mMediaPlayer = MediaPlayer.create(this, R.raw.classics);
        mMediaPlayer.start();

        Intent dialogIntent = new Intent(getBaseContext(), WakeUpActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(dialogIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    private Binder mBinder = new Binder();

    class MusicBinder extends Binder {

    }
}
