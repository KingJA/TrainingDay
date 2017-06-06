package com.kingja.trainingday.util;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.UUID;

/**
 * Description：TODO
 * Create Time：2016/8/22 14:33
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class StringUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getPercent(int upNumber, int downNumber) {
        return Math.round(upNumber * 100f / downNumber) + "%";
    }
}
