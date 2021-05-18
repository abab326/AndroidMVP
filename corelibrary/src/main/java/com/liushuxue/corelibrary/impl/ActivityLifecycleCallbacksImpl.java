package com.liushuxue.corelibrary.impl;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liushuxue.corelibrary.event.ForeOrBackEvent;
import com.liushuxue.corelibrary.helper.ActivityManagerHelper;

import org.greenrobot.eventbus.EventBus;

public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    private final ActivityManagerHelper activityManagerHelper;
    private int foreActivityCount = 0;

    public ActivityLifecycleCallbacksImpl(ActivityManagerHelper activityManagerHelper) {
        this.activityManagerHelper = activityManagerHelper;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        activityManagerHelper.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        foreActivityCount++;
        if (foreActivityCount > 0) {
            EventBus.getDefault().post(new ForeOrBackEvent(true));
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        foreActivityCount--;
        if (foreActivityCount <= 0) {
            EventBus.getDefault().post(new ForeOrBackEvent(false));
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activityManagerHelper.remove(activity);
    }
}
