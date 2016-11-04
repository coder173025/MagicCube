package com.mc.library.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.mc.library.Library;

/**
 * Created by dinghui on 2016/10/27.
 * SharedPreferences工具类
 */
public class PreferencesUtil {
    private static final SharedPreferences PREFERENCES = PreferenceManager
            .getDefaultSharedPreferences(Library.sInstance);

    private PreferencesUtil() {
        /* Cannot be instantiated! */
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    public static <T> void putPreferences(String key, T value) {
        if (TextUtils.isEmpty(key) || value == null) {
            return;
        }
        SharedPreferences.Editor editor = PREFERENCES.edit();
        if (value instanceof String) {
            editor.putString(key, value.toString());
        } else if (value instanceof Integer) {
            editor.putInt(key, ((Integer) value).intValue());
        } else if (value instanceof Long) {
            editor.putLong(key, ((Long) value).longValue());
        } else if (value instanceof Float) {
            editor.putFloat(key, ((Float) value).floatValue());
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) value).booleanValue());
        }
        editor.commit();
        if (!TextUtils.isEmpty(value.toString())) {
            LogUtil.d("putPreferences: " + key + " = " + value);
        }
    }

    public static <T> T getPreferences(String key, T defValue) {
        if (TextUtils.isEmpty(key) || defValue == null) {
            return null;
        }
        Object object = null;
        if (defValue instanceof String) {
            object = PREFERENCES.getString(key, String.valueOf(defValue));
        } else if (defValue instanceof Integer) {
            object = PREFERENCES.getInt(key, ((Integer) defValue).intValue());
        } else if (defValue instanceof Long) {
            object = PREFERENCES.getLong(key, ((Long) defValue).longValue());
        } else if (defValue instanceof Float) {
            object = PREFERENCES.getFloat(key, ((Float) defValue).floatValue());
        } else if (defValue instanceof Boolean) {
            object = PREFERENCES.getBoolean(key, ((Boolean) defValue).booleanValue());
        }
        T t = (T) object;
        if (!TextUtils.isEmpty(t.toString())) {
            LogUtil.d("getPreferences: " + key + " = " + t);
        }
        return t;
    }

    public static void clearPreferences() {
        SharedPreferences.Editor editor = PREFERENCES.edit();
        editor.clear();
        editor.commit();
        LogUtil.d("clearPreferences(): All clear!!!");
    }
}