package com.baidu.location.indoor.mapversion.a;

import com.alipay.sdk.util.h;
import com.baidu.location.BDLocation;
import com.baidu.location.indoor.mapversion.IndoorJni;
import com.baidu.location.indoor.mapversion.b.a;
import com.baidu.platform.comapi.location.CoordinateType;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class a {
    private static Lock a = new ReentrantLock();

    public static boolean a() {
        return IndoorJni.a;
    }

    public static synchronized boolean a(BDLocation bDLocation) {
        boolean z = false;
        synchronized (a.class) {
            if (a()) {
                a.d b = com.baidu.location.indoor.mapversion.b.a.a().b(bDLocation.getFloor());
                if (b != null) {
                    double a2 = b.a(bDLocation.getLongitude());
                    double b2 = b.b(bDLocation.getLatitude());
                    double[] dArr = {0.0d, 0.0d};
                    a.lock();
                    try {
                        dArr = IndoorJni.setPfWf(a2, b2);
                        a.unlock();
                    } catch (Exception e) {
                        e.printStackTrace();
                        a.unlock();
                    } catch (Throwable th) {
                        a.unlock();
                        throw th;
                    }
                    if (dArr[0] > 0.0d && dArr[1] > 0.0d) {
                        double c = b.c(dArr[0]);
                        double d = b.d(dArr[1]);
                        bDLocation.setLongitude(c);
                        bDLocation.setLatitude(d);
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    /* JADX INFO: finally extract failed */
    public static synchronized boolean a(String str) {
        boolean z = true;
        synchronized (a.class) {
            if (!a()) {
                z = false;
            } else {
                a.d b = com.baidu.location.indoor.mapversion.b.a.a().b(str);
                if (b == null) {
                    z = false;
                } else {
                    b.a(CoordinateType.GCJ02);
                    short[][] sArr = b.g;
                    double d = b.a().a;
                    double d2 = b.a().b;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < sArr.length; i++) {
                        short s = sArr[i][0];
                        int i2 = 1;
                        for (int i3 = 1; i3 < sArr[i].length; i3++) {
                            if (s != sArr[i][i3]) {
                                sb.append(i2).append("*").append(s).append(h.b);
                                s = sArr[i][i3];
                                i2 = 1;
                            } else {
                                i2++;
                            }
                        }
                        sb.append(i2).append("*").append(s).append(h.b);
                        if (i != sArr.length - 1) {
                            sb.append("|");
                        }
                    }
                    a.lock();
                    try {
                        IndoorJni.setRdnt(str, sArr, d, d2, (int) b.f.g, (int) b.f.h);
                        a.unlock();
                    } catch (Exception e) {
                        e.printStackTrace();
                        a.unlock();
                    } catch (Throwable th) {
                        a.unlock();
                        throw th;
                    }
                }
            }
        }
        return z;
    }

    /* JADX INFO: finally extract failed */
    public static synchronized double[] a(String str, double d, double d2) {
        double[] pfFr;
        double[] dArr = null;
        synchronized (a.class) {
            if (a()) {
                a.d b = com.baidu.location.indoor.mapversion.b.a.a().b(str);
                if (b != null) {
                    a.lock();
                    double[] dArr2 = {0.0d, 0.0d};
                    try {
                        pfFr = IndoorJni.getPfFr(d2, d);
                        a.unlock();
                    } catch (Exception e) {
                        e.printStackTrace();
                        a.unlock();
                    } catch (Throwable th) {
                        a.unlock();
                        throw th;
                    }
                    if (pfFr[0] > 0.0d && pfFr[1] > 0.0d) {
                        double c = b.c(pfFr[0]);
                        dArr = new double[]{b.d(pfFr[1]), c};
                    }
                }
            }
        }
        return dArr;
    }

    public static void b() {
        if (a()) {
            try {
                IndoorJni.resetPf();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
