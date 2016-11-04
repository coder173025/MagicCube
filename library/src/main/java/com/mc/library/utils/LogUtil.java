package com.mc.library.utils;

import android.text.TextUtils;
import android.util.Log;

import com.mc.library.BuildConfig;

/**
 * Created by dinghui on 2016/10/27.
 * Log工具类
 */
public class LogUtil {
    private static final String TAG = "MCHRLogUtil: ";
    private static final boolean isDebug = BuildConfig.DEBUG;

    private LogUtil() {
        /* Cannot be instantiated! */
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

    public static void m(String msg) {
        e(methodName(), msg);
    }

    public static void m(int msg) {
        e(methodName(), String.valueOf(msg));
    }

    public static void m() {
        e(TAG, methodName());
    }

    public static String methodName() {
        return new Exception().getStackTrace()[1].getMethodName();
    }
}
