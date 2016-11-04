package com.mc.library.utils;

import java.util.List;

public class ArrayUtil {
    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static <T> boolean isEmpty(T[] arr) {
        if (arr == null || arr.length == 0) {
            return true;
        }
        return false;
    }
}
