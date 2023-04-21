package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.lang.Thread;

/* compiled from: DynamicExceptionHandler */
public class aj implements Thread.UncaughtExceptionHandler {
    private static aj a;
    private Thread.UncaughtExceptionHandler b = Thread.getDefaultUncaughtExceptionHandler();
    private Context c;
    private v d;

    private aj(Context context, v vVar) {
        this.c = context.getApplicationContext();
        this.d = vVar;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    static synchronized aj a(Context context, v vVar) {
        aj ajVar;
        synchronized (aj.class) {
            if (a == null) {
                a = new aj(context, vVar);
            }
            ajVar = a;
        }
        return ajVar;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String a2 = w.a(th);
        try {
            if (!TextUtils.isEmpty(a2) && a2.contains("amapdynamic") && a2.contains("com.amap.api")) {
                ah.a(new aa(this.c, ak.c()), this.c, this.d);
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        if (this.b != null) {
            this.b.uncaughtException(thread, th);
        }
    }
}
