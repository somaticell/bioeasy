package com.baidu.location.indoor.mapversion;

import android.os.Build;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class a {
    private static Lock a = new ReentrantLock();

    public static void a() {
        try {
            IndoorJni.stopPdr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void a(int i, float[] fArr, long j) {
        synchronized (a.class) {
            a.lock();
            try {
                if (b() && fArr != null && fArr.length >= 3) {
                    IndoorJni.phs(i, fArr[0], fArr[1], fArr[2], j);
                }
                a.unlock();
            } catch (Exception e) {
                e.printStackTrace();
                a.unlock();
            } catch (Throwable th) {
                a.unlock();
                throw th;
            }
        }
        return;
    }

    public static boolean b() {
        if (Build.VERSION.SDK_INT < 19) {
            return false;
        }
        return IndoorJni.a;
    }

    public static float[] c() {
        a.lock();
        float[] fArr = null;
        try {
            fArr = IndoorJni.pgo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.unlock();
        }
        return fArr;
    }
}
