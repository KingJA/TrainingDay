package com.kingja.trainingday.util;

import android.app.Service;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import java.io.IOException;

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
        mMediaPlayer.setLooping(false);
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

    public void playAssets(String fileName) {
        stop();
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        AssetManager assetManager = mContext.getAssets();
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(fileName);
            mMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playUri(Uri uri) {
        stop();
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(mContext, uri);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.start();
    }

    public void playPath(String path) {
        stop();
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.start();
    }

}
