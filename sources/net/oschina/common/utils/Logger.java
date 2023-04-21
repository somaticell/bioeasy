package net.oschina.common.utils;

import android.util.Log;

public final class Logger {
    public static boolean DEBUG = false;

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }
}
