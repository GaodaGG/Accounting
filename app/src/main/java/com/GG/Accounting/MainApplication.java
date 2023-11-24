package com.GG.Accounting;

import android.app.Application;

import com.GG.Accounting.Activity.CrashActivity;

import cn.qqtheme.framework.AppContext;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.initialize(this, CrashActivity.class);
    }

}
