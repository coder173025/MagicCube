package com.mc.library.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.mc.library.Library;

/**
 * Created by dinghui on 2016/10/27.
 * Toast工具类
 */
public class ToastUtil {
    private static Toast sToast;
    private static Toast sLongToast;
    private static Toast sCenterToast;

    private ToastUtil() {
        /* Cannot be instantiated! */
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    public static void showToast(CharSequence text) {
        if (TextUtils.isEmpty(text)) return;
        if (sToast == null) {
            sToast = Toast.makeText(Library.sInstance, text,
                    Toast.LENGTH_SHORT);
        }
        sToast.setText(text);
        sToast.show();
    }

    public static void showLongToast(CharSequence text) {
        if (sLongToast == null) {
            sLongToast = Toast.makeText(Library.sInstance, text,
                    Toast.LENGTH_LONG);
        }
        sLongToast.setText(text);
        sLongToast.show();
    }

    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
        }
    }

    public static void cancelLongToast() {
        if (sLongToast != null) {
            sLongToast.cancel();
        }
    }

    public static void showCenterToast(CharSequence text) {
        if (TextUtils.isEmpty(text)) return;
        if (sCenterToast == null) {
            sCenterToast = Toast.makeText(Library.sInstance, text, Toast.LENGTH_LONG);
            sCenterToast.setGravity(Gravity.CENTER, sCenterToast.getXOffset() / 2, sCenterToast.getYOffset() / 2);
        }
        sCenterToast.show();
    }
}
