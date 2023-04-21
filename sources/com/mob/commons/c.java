package com.mob.commons;

import android.content.Context;
import com.mob.tools.MobLog;
import com.mob.tools.utils.ResHelper;
import java.io.File;

/* compiled from: DeviceLevelTags */
public class c {
    public static synchronized boolean a(Context context, String str) {
        boolean z;
        synchronized (c.class) {
            try {
                z = new File(ResHelper.getCacheRoot(context), str).exists();
            } catch (Throwable th) {
                MobLog.getInstance().w(th);
                z = true;
            }
        }
        return z;
    }

    public static synchronized void b(Context context, String str) {
        synchronized (c.class) {
            try {
                File file = new File(ResHelper.getCacheRoot(context), str);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (Throwable th) {
                MobLog.getInstance().w(th);
            }
        }
        return;
    }

    public static synchronized void c(Context context, String str) {
        synchronized (c.class) {
            try {
                File file = new File(ResHelper.getCacheRoot(context), str);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Throwable th) {
                MobLog.getInstance().w(th);
            }
        }
        return;
    }
}
