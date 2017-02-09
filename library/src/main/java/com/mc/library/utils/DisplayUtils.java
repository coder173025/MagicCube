package com.mc.library.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.mc.library.Library;

/**
 * Created by Qiu on 2017/2/6.
 * 显示工具类
 */
public class DisplayUtils {
    private DisplayUtils() {
        /* Cannot be instantiated! */
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    public static int px2dp(float pxValue) {
        final float density = Library.sInstance.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);    // 四舍五入
    }

    public static int dp2px(float dpValue) {
        final float density = Library.sInstance.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);    // 四舍五入
    }

    public static int px2sp(float pxValue) {
        final float scaledDensity = Library.sInstance.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);  // 四舍五入
    }

    public static int sp2px(float spValue) {
        final float scaledDensity = Library.sInstance.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);  // 四舍五入
    }

    public static int getScreenWidthPixels(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int getScreenHeightPixels(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }
}