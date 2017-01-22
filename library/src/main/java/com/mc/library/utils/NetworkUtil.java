package com.mc.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

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
    public static final int NETWORK_TYPE_MOBILE_2G = 2; // 2G
    public static final int NETWORK_TYPE_MOBILE_3G = 3; // 3G
    public static final int NETWORK_TYPE_MOBILE_4G = 4; // 4G
    public static final int NETWORK_TYPE_MOBILE_CMNET = 5;
    public static final int NETWORK_TYPE_MOBILE_CMWAP = 6;
    public static final int NETWORK_TYPE_WIFI = 7;
    public static final int NETWORK_TYPE_LINE = 8;

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
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    int type = activeNetworkInfo.getType();
                    if (type == ConnectivityManager.TYPE_MOBILE) {
                        networkType = NETWORK_TYPE_MOBILE;
                        String extraInfo = activeNetworkInfo.getExtraInfo();
                        if ("cmnet".equalsIgnoreCase(extraInfo)) {
                            networkType = NETWORK_TYPE_MOBILE_CMNET;
                        } else if ("cmwap".equalsIgnoreCase(extraInfo)) {
                            networkType = NETWORK_TYPE_MOBILE_CMWAP;
                        }
                    } else if (type == ConnectivityManager.TYPE_WIFI) {
                        networkType = NETWORK_TYPE_WIFI;
                    }
                }
            }
        }
        return networkType;
    }

    /**
     * 获取网络详细的类型
     */
    public static int getNetworkDetailedType(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    String typeName = activeNetworkInfo.getTypeName();
                    if ("WIFI".equalsIgnoreCase(typeName)) {
                        return NETWORK_TYPE_WIFI;
                    } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                        NetworkInfo mobileNetworkInfo = connectivityManager
                                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                        if (mobileNetworkInfo != null) {
                            switch (mobileNetworkInfo.getType()) {
                                case ConnectivityManager.TYPE_MOBILE:
                                    switch (mobileNetworkInfo.getSubtype()) {
                                        case TelephonyManager.NETWORK_TYPE_LTE:
                                            return NETWORK_TYPE_MOBILE_4G;
                                        case TelephonyManager.NETWORK_TYPE_UMTS:
                                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                                        case TelephonyManager.NETWORK_TYPE_HSPA:
                                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                                            return NETWORK_TYPE_MOBILE_3G;
                                        case TelephonyManager.NETWORK_TYPE_CDMA:
                                        case TelephonyManager.NETWORK_TYPE_GPRS:
                                        case TelephonyManager.NETWORK_TYPE_EDGE:
                                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                                        case TelephonyManager.NETWORK_TYPE_IDEN:
                                            return NETWORK_TYPE_MOBILE_2G;
                                        default:
                                            return NETWORK_TYPE_NONE;
                                    }
                            }
                        }
                    }
                }
            }
        }
        return NETWORK_TYPE_NONE;
    }

    /**
     * 当前网络是否已连接并可用
     *
     * @return true 已连接并可用；false 未连接或不可用
     */
    public static boolean isAvailable(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    // 当前网络是连接的
                    if (activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        // 当前所连接的网络可用
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
