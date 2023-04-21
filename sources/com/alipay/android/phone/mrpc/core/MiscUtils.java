package com.alipay.android.phone.mrpc.core;

import android.content.Context;

public final class MiscUtils {
    private static Boolean DEBUG = null;
    public static final String RC_PACKAGE_NAME = "com.eg.android.AlipayGphoneRC";

    public static final boolean isDebugger(Context context) {
        if (DEBUG != null) {
            return DEBUG.booleanValue();
        }
        try {
            Boolean valueOf = Boolean.valueOf((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) != 0);
            DEBUG = valueOf;
            return valueOf.booleanValue();
        } catch (Exception e) {
            return false;
        }
    }
}
