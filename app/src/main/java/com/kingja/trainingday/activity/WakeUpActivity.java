package com.kingja.trainingday.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.kingja.trainingday.R;

/**
 * Description:TODO
 * Create Time:2017/6/12 16:46
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class WakeUpActivity extends Activity {

    private PowerManager.WakeLock mWakelock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("WakeUpActivity", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("WakeUpActivity", "isLocked: "+isLocked() );
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakelock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,
                "SimpleTimer");
        mWakelock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakelock.release();
    }

    private boolean isLocked() {
        KeyguardManager km =
                (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }
}
