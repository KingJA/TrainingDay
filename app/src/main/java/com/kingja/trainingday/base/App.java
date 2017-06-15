package com.kingja.trainingday.base;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import com.kingja.trainingday.dao.DBManager;
import com.kingja.trainingday.greendaobean.Plan;
import com.kingja.trainingday.greendaobean.PlanDay;
import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.inject.commonent.DaggerAppComponent;
import com.kingja.trainingday.inject.module.AppModule;
import com.kingja.trainingday.service.InitializeService;
import com.kingja.trainingday.util.AssetsUtil;
import com.kingja.trainingday.util.StringUtil;
import com.kingja.trainingday.util.TestDataProvider;
import com.kingja.trainingday.util.TimeUtil;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;


/**
 * Description：App
 * Create Time：2016/10/14:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 * <p>
 * 1.创建全局AppComponent
 * 2.对外提供方法获取AppComponent
 */
public class App extends Application {
    private static final String TAG = "App";
    private static App sInstance;
    private AppComponent appComponent;
    private static SharedPreferences mSharedPreferences;
    private AppModule appModule;

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, InitializeService.class));
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        sInstance = this;
        setupComponent();
//        initTestData();
        AssetsUtil.getInstance(this).copyAssetsToSD("rings", "rings");
    }

    private void initTestData() {
        TestDataProvider.setNowPlanDay();
        TestDataProvider.setFinishedPlanDay(1, 10);
        TestDataProvider.setFinishedPlanDay(0, 20);
        TestDataProvider.setFinishedPlanDay(-1, 30);
    }

    /**
     * 全局注射器,把全局经常用的实例全引用，然后再给各个Activity或者Fragment引用
     */
    private void setupComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).build();
        appModule = new AppModule(this);
    }

    public static App getContext() {
        return sInstance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public AppModule getAppModule() {
        return appModule;
    }

}
