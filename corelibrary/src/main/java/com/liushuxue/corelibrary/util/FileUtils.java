package com.liushuxue.corelibrary.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    /**
     * 判断外部存储是否可用
     *
     * @return
     */
    public static boolean sdCardStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return Environment.isExternalStorageLegacy();
        } else {
            return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        }
    }

}
