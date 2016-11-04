package com.mc.library.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dinghui on 2016/10/27.
 */
public class Result<T> {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String msg;
    @SerializedName("result")
    public T data;

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
