package com.kingja.trainingday.inject.commonent;


import android.app.Application;


import com.kingja.trainingday.inject.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/13 9:42
 * 修改备注：
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Application getApplication();
}
