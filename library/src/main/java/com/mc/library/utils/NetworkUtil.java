package com.mc.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dinghui on 2016/10/27.
 * Network工具类
 */
public class NetworkUtil {
    /**
     * 网络类型
     */
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_MOBILE = 1;    // 非CMNET、CMWAP
    public static final int NETWORK_TYPE_MOBILE_CMNET = 2;
    public static final int NETWORK_TYPE_MOBILE_CMWAP = 3;
    public static final int NETWORK_TYPE_WIFI = 4;

    private NetworkUtil() {
        /* Cannot be instantiated! */
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    /**
     * 网络是否已连接
     *
     * @return true 已连接；false 未连接
     */
    public static boolean isConnected(Context context) {
        return isWiFiConnected(context) || isMobileConnected(context);
    }

    /**
     * WiFi是否已连接
     *
     * @return true 已连接；false 未连接
     */
    public static boolean isWiFiConnected(Context context) {
        return getNetworkType(context) == NETWORK_TYPE_WIFI;
    }

    /**
     * 移动数据网络是否已连接
     *
     * @return true 已连接；false 未连接
     */
    public static boolean isMobileConnected(Context context) {
        return getNetworkType(context) == NETWORK_TYPE_MOBILE_CMNET
                || getNetworkType(context) == NETWORK_TYPE_MOBILE_CMWAP
                || getNetworkType(context) == NETWORK_TYPE_MOBILE;
    }

    /**
     * 获取网络类型
     */
    public static int getNetworkType(Context context) {
        int networkType = NETWORK_TYPE_NONE;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_MOBILE) {
                networkType = NETWORK_TYPE_MOBILE;
                String extraInfo = networkInfo.getExtraInfo();
                if ("cmnet".equalsIgnoreCase(extraInfo)) {
                    networkType = NETWORK_TYPE_MOBILE_CMNET;
                } else if ("cmwap".equalsIgnoreCase(extraInfo)) {
                    networkType = NETWORK_TYPE_MOBILE_CMWAP;
                }
            } else if (type == ConnectivityManager.TYPE_WIFI) {
                networkType = NETWORK_TYPE_WIFI;
            }
        }
        return networkType;
    }
}
