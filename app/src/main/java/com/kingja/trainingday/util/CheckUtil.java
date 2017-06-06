package com.kingja.trainingday.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class CheckUtil {
    public static boolean checkEmpty(String s, String tip) {
        if (TextUtils.isEmpty(s)) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    public static boolean checkEquals(String s1, String s2, String tip) {
        if (!s1.equals(s2)) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    public static boolean checkZero(double d, String tip) {
        if (d == -1) {
            ToastUtil.showToast(tip);
            return false;
        }
        return true;
    }

    public static boolean isBigger(String startDate, String endDate) {
        if (startDate.compareTo(endDate) > 0) {
            return true;
        }
        return false;
    }

    public static boolean hasFinished(String endDate) {
        if (TimeUtil.getNowDate().compareTo(endDate) > 0) {
            return true;
        }
        return false;
    }
}
