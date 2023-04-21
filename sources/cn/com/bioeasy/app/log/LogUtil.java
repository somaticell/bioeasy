package cn.com.bioeasy.app.log;

import android.util.Log;

public final class LogUtil {
    private static int logLevel;
    private static boolean logable;

    public enum LogPriority {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
        ASSERT
    }

    public static int v(String tag, String msg) {
        return println(LogPriority.VERBOSE, tag, msg);
    }

    public static int d(String tag, String msg) {
        return println(LogPriority.DEBUG, tag, msg);
    }

    public static int i(String tag, String msg) {
        return println(LogPriority.INFO, tag, msg);
    }

    public static int w(String tag, String msg) {
        return println(LogPriority.WARN, tag, msg);
    }

    public static int e(String tag, String msg) {
        return println(LogPriority.ERROR, tag, msg);
    }

    public static boolean isLogable() {
        return logable;
    }

    public static void setDebugMode(boolean logable2) {
        logable = logable2;
    }

    public static void setLogLevel(LogPriority level) {
        logLevel = level.ordinal();
    }

    private static int println(LogPriority priority, String tag, String msg) {
        int level = priority.ordinal();
        if (!logable || logLevel > level) {
            return -1;
        }
        return Log.println(level + 2, tag, msg);
    }
}
