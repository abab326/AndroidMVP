package com.liushuxue.corelibrary.util;

import android.widget.Toast;

import com.liushuxue.corelibrary.base.BaseApplication;

public class ToastUtils {
    private static Toast mToast;

    public static void show(String message) {
        show(message, Toast.LENGTH_SHORT);
    }

    public static void show(String message, int duration) {
        if (null == mToast) {
            mToast = Toast.makeText(BaseApplication.getInstance().getApplicationContext(), message, duration);
        } else {
            mToast.cancel();
            mToast.setDuration(duration);
            mToast.setText(message);
        }
        mToast.show();
    }
}
