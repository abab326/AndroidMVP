package com.liushuxue.corelibrary.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.RecoverableSecurityException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.liushuxue.corelibrary.base.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;

import butterknife.internal.Utils;

/**
 * 图片处理类
 */
public class ImageUtils {

    /**
     * 保存图片到公共图片目录
     *
     * @param context     上下文
     * @param inputStream 输入流
     * @param imagePath   二目录
     * @param imageName   文件名称
     * @return true 保存成功
     */
    public static Boolean saveInputStreamAsFile(Context context, InputStream inputStream, String imagePath, String imageName) {
        try {
            OutputStream outputStream = createImageOutputStream(context, imagePath, imageName);
            if (outputStream != null) {
                byte[] fileReader = new byte[4096];
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();
                return true;
            }
            return false;
        } catch (FileNotFoundException e) {

            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 保存图片到公共目录
     *
     * @param context   上下文
     * @param bitmap    图片源
     * @param imagePath 图片路径
     * @param imageName 图片名称
     * @return true 保存成功
     */
    public static boolean saveFile(Context context, Bitmap bitmap, String imagePath, String imageName) {

        try {
            OutputStream outputStream = createImageOutputStream(context, imagePath, imageName);

            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                outputStream.flush();
                outputStream.close();
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 创建插入图片到公共目录
     *
     * @param context   上下文
     * @param imagePath 图片路径
     * @param imageName 图片名称
     * @return 创建成功后的接收流
     */
    private static OutputStream createImageOutputStream(Context context, String imagePath, String imageName) {
        ContentResolver contentResolver = context.getContentResolver();

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.TITLE, imageName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + imagePath);
        } else {
            values.put(MediaStore.Images.Media.DATA, Environment.getExternalStorageDirectory().getAbsolutePath() + Environment.DIRECTORY_PICTURES + imagePath);
        }
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri insertUri = contentResolver.insert(external, values);
        OutputStream outputStream = null;
        if (insertUri != null) {
            try {
                outputStream = contentResolver.openOutputStream(insertUri);
                return outputStream;
            } catch (FileNotFoundException e) {
                return null;
            }
        }
        return null;

    }

    /**
     * 获取指定名称的图片 uri
     *
     * @param context
     * @param imageName
     * @return
     */
    public static Uri queryImage(Context context, String imageName) {

        ContentResolver contentResolver = context.getContentResolver();
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String selection = "${MediaStore.Images.Media.DISPLAY_NAME}=?";
        String[] args = new String[]{imageName};
        String[] projection = new String[]{MediaStore.Images.Media._ID};
        Cursor cursor = contentResolver.query(external, projection, selection, args, null);
        Uri imageUri = null;
        if (cursor != null && cursor.moveToFirst()) {
            imageUri = ContentUris.withAppendedId(external, cursor.getLong(0));
            cursor.close();
        }
        return imageUri;
    }

    /**
     * 通过uri获取bitmap
     *
     * @param context 上下文
     * @param uri     图片uri
     * @return
     */
    public static Bitmap getImageByUri(Context context, Uri uri) {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            ParcelFileDescriptor fileDescriptor = contentResolver.openFileDescriptor(uri, "r");
            if (fileDescriptor != null) {
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor());
                fileDescriptor.close();
                return bitmap;
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    public static void modifyImage(Activity activity, String imageName, int sendRequestCode) {
        Uri queryUri = queryImage(activity, imageName);
        //首先判断是否有READ_EXTERNAL_STORAGE权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            OutputStream outputStream;
            try {
                outputStream = activity.getContentResolver().openOutputStream(queryUri);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (@SuppressLint({"NewApi", "LocalSuppress"}) RecoverableSecurityException recoverableSecurityException) {

                try {
                    activity.startIntentSenderForResult(
                            recoverableSecurityException.getUserAction().getActionIntent().getIntentSender(),
                            sendRequestCode,
                            null,
                            0,
                            0,
                            0
                    );
                } catch (IntentSender.SendIntentException sendIntentException) {

                }
            }
        }
    }

    /**
     * 保存图片到系统相册 适配 10 以上版本
     *
     * @param mContext 上下文
     * @param file     图片文件
     * @return true 保存成功
     */
    public static boolean scanFile(Context mContext, File file) {
        String mimeType = getMimeType(file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String fileName = file.getName();
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
            ContentResolver contentResolver = mContext.getContentResolver();
            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri == null) {
                return false;
            }
            try {
                OutputStream out = contentResolver.openOutputStream(uri);
                FileInputStream fis = new FileInputStream(file);
                FileUtils.copy(fis, out);
                fis.close();
                out.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            MediaScannerConnection.scanFile(mContext.getApplicationContext(), new String[]{file.getAbsolutePath()}, new String[]{"image/jpeg"}, (path, uri) -> {

            });
            return true;
        }
    }

    /**
     * 获取文件类型
     *
     * @param file
     * @return
     */
    public static String getMimeType(File file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(file.getName());
    }

    /**
     * 获取文件 uri 路径
     *
     * @param context 上下文
     * @param filePath 文件路径
     * @return 文件 uri
     */
    public static Uri toUri(Context context, String filePath) {
        return toUri(context, new File(filePath));
    }

    /**
     * 获取文件 uri 路径
     *
     * @param context 上下文
     * @param file 文件
     * @return 文件 uri
     */
    public static Uri toUri(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getApplicationInfo().packageName + ".fileProvider", file);
        }
        return Uri.fromFile(file);
    }

}
