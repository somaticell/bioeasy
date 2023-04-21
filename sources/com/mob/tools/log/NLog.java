package com.mob.tools.log;

import android.content.Context;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.HashMap;

public abstract class NLog {
    private static boolean disable;
    private static HashMap<String, NLog> loggers = new HashMap<>();
    private static LogPrinter printer = new LogPrinter();

    /* access modifiers changed from: protected */
    public abstract String getSDKTag();

    public static void disable() {
        disable = true;
    }

    static {
        MobUncaughtExceptionHandler.register();
    }

    public NLog() {
        loggers.put(getSDKTag(), this);
        if (loggers.size() == 1) {
            loggers.put("__FIRST__", this);
        }
    }

    public static void setContext(Context context) {
        if (context != null) {
            printer.setContext(context);
            NativeErrorHandler.prepare(context);
        }
    }

    public static void setCollector(String sdkTag, LogCollector collector) {
        printer.setCollector(sdkTag, collector);
    }

    protected static final NLog getInstanceForSDK(final String sdkTag, boolean createNew) {
        NLog instance = loggers.get(sdkTag);
        if (instance == null) {
            instance = loggers.get("__FIRST__");
        }
        if (instance != null || !createNew) {
            return instance;
        }
        return new NLog() {
            /* access modifiers changed from: protected */
            public String getSDKTag() {
                return sdkTag;
            }
        };
    }

    public final int v(Throwable t) {
        if (disable) {
            return 0;
        }
        return printer.println(getSDKTag(), 2, 0, getStackTraceString(t));
    }

    public final int v(Object format, Object... args) {
        String message;
        if (disable) {
            return 0;
        }
        String s = format.toString();
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return printer.println(getSDKTag(), 2, 0, message);
    }

    public final int v(Throwable throwable, Object format, Object... args) {
        if (disable) {
            return 0;
        }
        String s = format.toString();
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return printer.println(getSDKTag(), 2, 0, sb.append(s).append(10).append(getStackTraceString(throwable)).toString());
    }

    public final int d(Throwable t) {
        if (disable) {
            return 0;
        }
        return printer.println(getSDKTag(), 3, 0, getStackTraceString(t));
    }

    public final int d(Object format, Object... args) {
        String message;
        if (disable) {
            return 0;
        }
        String s = format.toString();
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return printer.println(getSDKTag(), 3, 0, message);
    }

    public final int d(Throwable throwable, Object format, Object... args) {
        if (disable) {
            return 0;
        }
        String s = format.toString();
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return printer.println(getSDKTag(), 3, 0, sb.append(s).append(10).append(getStackTraceString(throwable)).toString());
    }

    public final int i(Throwable t) {
        if (disable) {
            return 0;
        }
        return printer.println(getSDKTag(), 4, 0, getStackTraceString(t));
    }

    public final int i(Object format, Object... args) {
        String message;
        if (disable) {
            return 0;
        }
        String s = format.toString();
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return printer.println(getSDKTag(), 4, 0, message);
    }

    public final int i(Throwable throwable, Object format, Object... args) {
        if (disable) {
            return 0;
        }
        String s = format.toString();
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return printer.println(getSDKTag(), 4, 0, sb.append(s).append(10).append(getStackTraceString(throwable)).toString());
    }

    public final int w(Throwable t) {
        if (disable) {
            return 0;
        }
        return printer.println(getSDKTag(), 5, 0, getStackTraceString(t));
    }

    public final int w(Object format, Object... args) {
        String message;
        if (disable) {
            return 0;
        }
        String s = format.toString();
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return printer.println(getSDKTag(), 5, 0, message);
    }

    public final int w(Throwable throwable, Object format, Object... args) {
        if (disable) {
            return 0;
        }
        String s = format.toString();
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return printer.println(getSDKTag(), 5, 0, sb.append(s).append(10).append(getStackTraceString(throwable)).toString());
    }

    public final int e(Throwable t) {
        if (disable) {
            return 0;
        }
        return printer.println(getSDKTag(), 6, 0, getStackTraceString(t));
    }

    public final int e(Object format, Object... args) {
        String message;
        if (disable) {
            return 0;
        }
        String s = format.toString();
        if (args.length > 0) {
            message = String.format(s, args);
        } else {
            message = s;
        }
        return printer.println(getSDKTag(), 6, 0, message);
    }

    public final int e(Throwable throwable, Object format, Object... args) {
        if (disable) {
            return 0;
        }
        String s = format.toString();
        StringBuilder sb = new StringBuilder();
        if (args.length > 0) {
            s = String.format(s, args);
        }
        return printer.println(getSDKTag(), 6, 0, sb.append(s).append(10).append(getStackTraceString(throwable)).toString());
    }

    public final int crash(Throwable t) {
        if (disable) {
            return 0;
        }
        return printer.println(getSDKTag(), 6, 1, getStackTraceString(t));
    }

    public final void nativeCrashLog(String log) {
        if (!disable) {
            printer.nativeCrashLog(getSDKTag(), log);
        }
    }

    private final String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        for (Throwable t = tr; t != null; t = t.getCause()) {
            if (t instanceof UnknownHostException) {
                return "";
            }
        }
        StringWriter sw = new StringWriter();
        tr.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
