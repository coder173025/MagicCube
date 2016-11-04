package com.mc.library.network;

/**
 * Created by dinghui on 2016/10/27.
 */
public interface HTTPCallback<T> {
    void onResponse(int code, String message, T result);
}
