package com.mob;

import android.content.Context;
import android.os.Bundle;
import com.mob.commons.clt.DvcClt;
import com.mob.commons.clt.PkgClt;
import com.mob.commons.clt.RtClt;
import com.mob.commons.iosbridge.UDPServer;

public class MobSDK {
    /* access modifiers changed from: private */
    public static Context a;
    private static String b;
    private static String c;

    public static synchronized void init(Context context) {
        synchronized (MobSDK.class) {
            init(context, (String) null, (String) null);
        }
    }

    public static synchronized void init(Context context, String str) {
        synchronized (MobSDK.class) {
            init(context, str, (String) null);
        }
    }

    public static synchronized void init(Context context, String str, String str2) {
        synchronized (MobSDK.class) {
            if (a == null) {
                a = context.getApplicationContext();
                a(str, str2);
                a.a(a, b, c);
                b();
            }
        }
    }

    private static void a(String str, String str2) {
        if (str == null || str2 == null) {
            Bundle bundle = null;
            try {
                bundle = a.getPackageManager().getPackageInfo(a.getPackageName(), 128).applicationInfo.metaData;
            } catch (Throwable th) {
            }
            if (str == null && bundle != null) {
                str = bundle.getString("Mob-AppKey");
            }
            if (str2 == null && bundle != null) {
                str2 = bundle.getString("Mob-AppSeret");
            }
        }
        b = str;
        c = str2;
    }

    private static void b() {
        new Thread() {
            public void run() {
                DvcClt.startCollector(MobSDK.a);
                PkgClt.startCollector(MobSDK.a);
                RtClt.startCollector(MobSDK.a);
                UDPServer.start(MobSDK.a);
            }
        }.start();
    }

    public static Context getContext() {
        return a;
    }

    public static String getAppkey() {
        return b;
    }

    public static String getAppSecret() {
        return c;
    }
}
