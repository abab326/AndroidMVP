package com.liushuxue.corelibrary.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.liushuxue.corelibrary.helper.ActivityManagerHelper;
import com.liushuxue.corelibrary.impl.ActivityLifecycleCallbacksImpl;

public class BaseApplication extends Application {
    private static BaseApplication baseApplication;
    private ActivityManagerHelper activityManagerHelper;
    private ActivityLifecycleCallbacksImpl callbacks;

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        activityManagerHelper = new ActivityManagerHelper();
        callbacks = new ActivityLifecycleCallbacksImpl(activityManagerHelper);
        registerActivityLifecycleCallbacks(callbacks);

    }

}
