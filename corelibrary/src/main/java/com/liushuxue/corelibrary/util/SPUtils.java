package com.liushuxue.corelibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * @auther liushuxue
 * @describe
 * @createTime 2021/5/23
 */
public class SPUtils {
    public static final String FILE_NAME = "shard_name";

    public static SharedPreferences getInstance(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = getInstance(context);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        editor.apply();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context       上下文
     * @param key           key值
     * @param defaultObject 默认值
     * @return 返回结果
     */
    public static <T> T get(Context context, String key, T defaultObject) {
        try {
            SharedPreferences sp = getInstance(context);
            return  (T) sp.getAll().get(key);
        } catch (NullPointerException e) {
            return defaultObject;
        }

    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        getInstance(context).edit().remove(key).apply();
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {

        getInstance(context).edit().clear().apply();

    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
}
