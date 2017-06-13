package com.kingja.trainingday.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.kingja.trainingday.R;
import com.kingja.trainingday.greendaobean.PlanClock;
import com.kingja.trainingday.util.AlarmPlayer;
import com.kingja.trainingday.util.AlarmUtil;
import com.kingja.trainingday.util.IntentUtil;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2017/6/12 16:46
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class WakeUpActivity extends Activity {

    private PowerManager.WakeLock mWakelock;
    private PlanClock planClock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       planClock = IntentUtil.getData(getIntent(), PlanClock.class);

        Log.e("WakeUpActivity", "planClock2: " + planClock.getRemindTime());
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |               //这个在锁屏状态下
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_wakeup);
    }


    private boolean isLocked() {
        KeyguardManager km =
                (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    public void onCloseClock(View view) {
        AlarmPlayer.getInstance(this).stop();
        finish();
    }
}
