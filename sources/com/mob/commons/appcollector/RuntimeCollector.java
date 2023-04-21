package com.mob.commons.appcollector;

import android.content.Context;
import com.mob.commons.clt.RtClt;

@Deprecated
public class RuntimeCollector {
    public static synchronized void startCollector(Context context, String str) {
        synchronized (RuntimeCollector.class) {
            RtClt.startCollector(context, str);
        }
    }

    public static synchronized void startCollector(Context context) {
        synchronized (RuntimeCollector.class) {
            RtClt.startCollector(context);
        }
    }
}
