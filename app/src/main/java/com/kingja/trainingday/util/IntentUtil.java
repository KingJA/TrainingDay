package com.kingja.trainingday.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2017/6/13 16:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class IntentUtil {

    public static void goActivityInService(Context context, Class<?> cls, Serializable data) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(data.getClass().getSimpleName(), data);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static <T> T getData(Intent intent, Class<T> cls) {
        T t = (T) intent.getSerializableExtra(cls.getSimpleName());
        return t;
    }
}
