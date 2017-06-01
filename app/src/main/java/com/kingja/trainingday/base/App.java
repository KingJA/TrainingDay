package com.kingja.trainingday.base;

import android.app.Application;
import android.content.SharedPreferences;

import com.kingja.trainingday.inject.commonent.AppComponent;
import com.kingja.trainingday.inject.commonent.DaggerAppComponent;
import com.kingja.trainingday.inject.module.AppModule;
import com.squareup.leakcanary.LeakCanary;



/**
 * Description：App
 * Create Time：2016/10/14:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 *
 * 1.创建全局AppComponent
 * 2.对外提供方法获取AppComponent
 */
public class App extends Application {
    private static App sInstance;
    private AppComponent appComponent;
    private static SharedPreferences mSharedPreferences;
    private AppModule appModule;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        this.sInstance = this;
        setupComponent();
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
