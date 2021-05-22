package com.liushuxue.corelibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.core.view.ViewCompat;

/**
 * @auther liushuxue
 * @describe
 * @createTime 2021/5/22
 */
public class StatusBarUtils {

    public static void setStatusBarColor(Activity activity, int color, boolean isBlack) {
        // 设置状态栏颜色
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
        //设置文字颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isBlack) {
                window.getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    /**
     * 设置一个状态栏一样高度的View
     *
     * @param activity 目标页面
     * @param color 颜色
     */
    private static void setStatusBarView(Activity activity, int color) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int count = decorView.getChildCount();
        //判断是否已经添加了statusBarView
        if (count > 0 && decorView.getChildAt(count - 1).getTag().equals("statusBarView")) {
            decorView.getChildAt(count - 1).setBackgroundColor(color);
        } else {
            //新建一个和状态栏高宽的view
            View statusBarView = new View(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            statusBarView.setLayoutParams(params);

            statusBarView.setBackgroundColor(color);
            statusBarView.setTag("setFitsSystemWindows");
            decorView.addView(statusBarView);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
