package com.kingja.trainingday.util;

import android.app.Service;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

/**
 * Description:
 * 支持：1.单例，2.播放，暂停，停止，销毁
 * Create Time:2017/6/13 9:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AlarmPlayer {
    private volatile static AlarmPlayer sAlarmPlayer;
    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private Vibrator mVibrator;

    private AlarmPlayer(Context context) {
        mContext = context.getApplicationContext();
    }

    public static AlarmPlayer getInstance(Context context) {
        if (sAlarmPlayer == null) {
            synchronized (AlarmPlayer.class) {
                if (sAlarmPlayer == null) {
                    sAlarmPlayer = new AlarmPlayer(context);
                }
            }
        }
        return sAlarmPlayer;
    }


    public void playRaw(int resId, boolean vibrate) {
        stop();
        if (vibrate) {
            vibrate();
        }
        mMediaPlayer = MediaPlayer.create(mContext, resId);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    public void vibrate() {
        mVibrator = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
        mVibrator.vibrate(new long[]{1000, 1000}, 0);
    }


    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        if (mVibrator != null) {
            mVibrator.cancel();
        }

    }

}
