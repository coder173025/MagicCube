package com.mc.library;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by dinghui on 2016/10/27.
 */
public class Library {
    public static Application sInstance;

    public static void initialize(Context context) {
        if (sInstance == null) {
            if (context != null && context instanceof Application) {
                sInstance = (Application) context;
                Stetho.initializeWithDefaults(context);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addNetworkInterceptor(new StethoInterceptor())
                        .build();
            } else {
                throw new IllegalArgumentException("context is null or doesn't Application Instance!");
            }
        } else {
            Log.e("Library: initialize", "sInstance != null ==> 重复初始化！");
        }
    }
}
