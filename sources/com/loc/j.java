package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.amap.api.location.DPoint;
import java.io.File;
import java.math.BigDecimal;

/* compiled from: OffsetUtil */
public class j {
    static double a = 3.141592653589793d;
    private static boolean b = false;

    public static DPoint a(Context context, DPoint dPoint) {
        if (context == null) {
            return null;
        }
        String a2 = u.a(context, "libwgs2gcj.so");
        if (!TextUtils.isEmpty(a2) && new File(a2).exists() && !b) {
            try {
                System.load(a2);
                b = true;
            } catch (Throwable th) {
            }
        }
        return a(dPoint, b);
    }

    public static DPoint a(Context context, double d, double d2) {
        if (context == null) {
            return null;
        }
        return a(context, new DPoint(d2, d));
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.amap.api.location.DPoint a(com.amap.api.location.DPoint r6, boolean r7) {
        /*
            r0 = 2
            double[] r0 = new double[r0]     // Catch:{ Throwable -> 0x0052 }
            if (r7 == 0) goto L_0x0044
            r1 = 2
            double[] r1 = new double[r1]     // Catch:{ Throwable -> 0x0052 }
            r2 = 0
            double r4 = r6.getLongitude()     // Catch:{ Throwable -> 0x0052 }
            r1[r2] = r4     // Catch:{ Throwable -> 0x0052 }
            r2 = 1
            double r4 = r6.getLatitude()     // Catch:{ Throwable -> 0x0052 }
            r1[r2] = r4     // Catch:{ Throwable -> 0x0052 }
            int r1 = com.amap.api.location.CoordUtil.convertToGcj(r1, r0)     // Catch:{ Throwable -> 0x0036 }
            if (r1 == 0) goto L_0x0028
            double r0 = r6.getLongitude()     // Catch:{ Throwable -> 0x0036 }
            double r2 = r6.getLatitude()     // Catch:{ Throwable -> 0x0036 }
            double[] r0 = com.loc.bx.a(r0, r2)     // Catch:{ Throwable -> 0x0036 }
        L_0x0028:
            r1 = r0
        L_0x0029:
            com.amap.api.location.DPoint r0 = new com.amap.api.location.DPoint     // Catch:{ Throwable -> 0x0052 }
            r2 = 1
            r2 = r1[r2]     // Catch:{ Throwable -> 0x0052 }
            r4 = 0
            r4 = r1[r4]     // Catch:{ Throwable -> 0x0052 }
            r0.<init>(r2, r4)     // Catch:{ Throwable -> 0x0052 }
            r6 = r0
        L_0x0035:
            return r6
        L_0x0036:
            r0 = move-exception
            double r0 = r6.getLongitude()     // Catch:{ Throwable -> 0x0052 }
            double r2 = r6.getLatitude()     // Catch:{ Throwable -> 0x0052 }
            double[] r0 = com.loc.bx.a(r0, r2)     // Catch:{ Throwable -> 0x0052 }
            goto L_0x0028
        L_0x0044:
            double r0 = r6.getLongitude()     // Catch:{ Throwable -> 0x0052 }
            double r2 = r6.getLatitude()     // Catch:{ Throwable -> 0x0052 }
            double[] r0 = com.loc.bx.a(r0, r2)     // Catch:{ Throwable -> 0x0052 }
            r1 = r0
            goto L_0x0029
        L_0x0052:
            r0 = move-exception
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.j.a(com.amap.api.location.DPoint, boolean):com.amap.api.location.DPoint");
    }

    public static DPoint b(Context context, DPoint dPoint) {
        try {
            return a(context, c(dPoint.getLongitude(), dPoint.getLatitude()));
        } catch (Throwable th) {
            th.printStackTrace();
            return dPoint;
        }
    }

    public static double a(double d, double d2) {
        return (Math.cos(d2 / 100000.0d) * (d / 18000.0d)) + (Math.sin(d / 100000.0d) * (d2 / 9000.0d));
    }

    public static double b(double d, double d2) {
        return (Math.sin(d2 / 100000.0d) * (d / 18000.0d)) + (Math.cos(d / 100000.0d) * (d2 / 9000.0d));
    }

    private static DPoint c(double d, double d2) {
        double d3 = (double) (((long) (100000.0d * d)) % 36000000);
        double d4 = (double) (((long) (100000.0d * d2)) % 36000000);
        int i = (int) ((-b(d3, d4)) + d4);
        int i2 = (int) (((double) (d3 > 0.0d ? 1 : -1)) + (-a((double) ((int) ((-a(d3, d4)) + d3)), (double) i)) + d3);
        return new DPoint(((double) ((int) (((double) (d4 > 0.0d ? 1 : -1)) + ((-b((double) i2, (double) i)) + d4)))) / 100000.0d, ((double) i2) / 100000.0d);
    }

    public static DPoint a(DPoint dPoint) {
        if (dPoint == null) {
            return dPoint;
        }
        try {
            return a(dPoint, 2);
        } catch (Throwable th) {
            th.printStackTrace();
            return dPoint;
        }
    }

    private static double a(double d) {
        return Math.sin(3000.0d * d * (a / 180.0d)) * 2.0E-5d;
    }

    private static double b(double d) {
        return Math.cos(3000.0d * d * (a / 180.0d)) * 3.0E-6d;
    }

    private static DPoint d(double d, double d2) {
        DPoint dPoint = new DPoint();
        dPoint.setLongitude(a((Math.cos(b(d) + Math.atan2(d2, d)) * (a(d2) + Math.sqrt((d * d) + (d2 * d2)))) + 0.0065d, 8));
        dPoint.setLatitude(a((Math.sin(b(d) + Math.atan2(d2, d)) * (a(d2) + Math.sqrt((d * d) + (d2 * d2)))) + 0.006d, 8));
        return dPoint;
    }

    private static double a(double d, int i) {
        return new BigDecimal(d).setScale(i, 4).doubleValue();
    }

    private static DPoint a(DPoint dPoint, int i) {
        double d = 0.006401062d;
        double d2 = 0.0060424805d;
        int i2 = 0;
        DPoint dPoint2 = null;
        while (i2 < i) {
            DPoint a2 = a(dPoint.getLongitude(), dPoint.getLatitude(), d, d2);
            d = dPoint.getLongitude() - a2.getLongitude();
            d2 = dPoint.getLatitude() - a2.getLatitude();
            i2++;
            dPoint2 = a2;
        }
        return dPoint2;
    }

    private static DPoint a(double d, double d2, double d3, double d4) {
        DPoint dPoint = new DPoint();
        double d5 = d - d3;
        double d6 = d2 - d4;
        DPoint d7 = d(d5, d6);
        dPoint.setLongitude(a((d5 + d) - d7.getLongitude(), 8));
        dPoint.setLatitude(a((d2 + d6) - d7.getLatitude(), 8));
        return dPoint;
    }
}
