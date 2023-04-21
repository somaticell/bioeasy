package com.anthony.citypicker.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static Toast mToast;

    public static void showToast(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, 0);
        } else {
            mToast.setText(message);
            mToast.setDuration(0);
        }
        mToast.show();
    }

    public static void showToast(Context context, int messageResId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, messageResId, 0);
        } else {
            mToast.setText(messageResId);
            mToast.setDuration(0);
        }
        mToast.show();
    }
}
