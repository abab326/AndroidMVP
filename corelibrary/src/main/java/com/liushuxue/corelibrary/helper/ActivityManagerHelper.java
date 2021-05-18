package com.liushuxue.corelibrary.helper;

import android.app.Activity;

import java.util.Stack;

public class ActivityManagerHelper {

    private Stack<Activity> activities = new Stack<>();

    /**
     * 新增
     *
     * @param activity 目标页面
     */
    public void add(Activity activity) {
        if (activities == null)
            activities = new Stack<>();
        activities.push(activity);

    }

    /**
     * 移除
     *
     * @param activity 目标页面
     */
    public void remove(Activity activity) {
        if (!activities.empty() && activities.remove(activity)) {
            activity.finish();
        }
    }

    /**
     * 移除当前页面
     */
    public void removeCurrent() {
        if (!activities.empty()) {
            Activity activity = activities.pop();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 获取当前页面
     *
     * @return null 当页面全
     */
    public Activity current() {
        if (!activities.empty()) {
            return activities.peek();
        }
        return null;
    }

    /**
     * 保留指定类名页央
     */
    public void clearKeepSpecified(Class<? extends Activity> specifiedActivity) {
        Activity targetActivity = null;
        while (!activities.empty()) {
            Activity activity = activities.pop();
            if (activity.getClass().getName().equals(specifiedActivity.getName())) {
                if (targetActivity != null && !targetActivity.isFinishing()) {
                    targetActivity.finish();
                }
                targetActivity = activity;
            }
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }

        if (targetActivity != null) {
            activities.clear();
            activities.add(targetActivity);
        }

    }

    /**
     * 清空所有页面
     */
    public void clear() {

        while (!activities.empty()) {
            Activity activity = activities.pop();
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }


}
