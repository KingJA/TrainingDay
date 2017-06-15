package com.kingja.trainingday.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Description:TODO
 * Create Time:2017/6/15 15:56
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Sp {
    private static Sp mInstance;
    private final SharedPreferences mSp;

    private Sp(Context context) {
        mSp = context.getApplicationContext().getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, Context
                .MODE_PRIVATE);
    }

    public static Sp getInstance(Context context) {
        if (mInstance == null) {
            synchronized (Sp.class) {
                if (mInstance == null) {
                    mInstance = new Sp(context);
                }
            }
        }
        return mInstance;
    }

    public void putData(String key, Object data) {
        if (data instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) data).apply();
        } else if (data instanceof String) {
            mSp.edit().putString(key, (String) data).apply();
        } else if (data instanceof Integer) {
            mSp.edit().putInt(key, (Integer) data).apply();
        } else if (data instanceof Float) {
            mSp.edit().putFloat(key, (Float) data).apply();
        } else if (data instanceof Long) {
            mSp.edit().putLong(key, (Long) data).apply();
        }
    }

    public Object getData(String key, Object defautData) {
        if (defautData instanceof Boolean) {
            return mSp.getBoolean(key, (Boolean) defautData);
        } else if (defautData instanceof String) {
            return mSp.getString(key, (String) defautData);
        } else if (defautData instanceof Integer) {
            return mSp.getInt(key, (Integer) defautData);
        } else if (defautData instanceof Float) {
            return mSp.getFloat(key, (Float) defautData);
        } else {
            return mSp.getLong(key, (long) defautData);
        }
    }
}
