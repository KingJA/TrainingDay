package com.kingja.trainingday.util;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Description:TODO
 * Create Time:2017/6/12 13:39
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VibratorUtil {
    private static final long[] DEFAULT_PATTERN = {2000, 1000, 2000, 1000};

    public static Vibrator vibrateRepeat(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(DEFAULT_PATTERN, 0);
        return vibrator;
    }
}
