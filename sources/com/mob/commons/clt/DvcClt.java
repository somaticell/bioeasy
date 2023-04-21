package com.mob.commons.clt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Parcelable;
import com.mob.commons.LockAction;
import com.mob.commons.a;
import com.mob.commons.b;
import com.mob.commons.d;
import com.mob.commons.e;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.MobLog;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.FileLocker;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class DvcClt implements Handler.Callback {
    private static DvcClt a;
    /* access modifiers changed from: private */
    public Context b;
    /* access modifiers changed from: private */
    public Hashon c = new Hashon();
    private Handler d;
    private Random e = new Random();
    private BroadcastReceiver f;

    public static synchronized void startCollector(Context context) {
        synchronized (DvcClt.class) {
            if (a == null) {
                a = new DvcClt(context);
                a.a();
            }
        }
    }

    private DvcClt(Context context) {
        this.b = context.getApplicationContext();
    }

    private void a() {
        AnonymousClass1 r0 = new MobHandlerThread() {
            public void run() {
                d.a(new File(ResHelper.getCacheRoot(DvcClt.this.b), "comm/locks/.dic_lock"), new LockAction() {
                    public boolean run(FileLocker fileLocker) {
                        AnonymousClass1.this.a();
                        return false;
                    }
                });
            }

            /* access modifiers changed from: private */
            public void a() {
                super.run();
            }
        };
        r0.start();
        this.d = new Handler(r0.getLooper(), this);
        this.d.sendEmptyMessage(1);
        this.d.sendEmptyMessage(2);
        this.d.sendEmptyMessage(3);
        this.d.sendEmptyMessage(5);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0084, code lost:
        if (h() != false) goto L_0x0086;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handleMessage(android.os.Message r9) {
        /*
            r8 = this;
            r7 = 4
            r1 = 2
            r6 = 0
            int r0 = r9.what
            switch(r0) {
                case 1: goto L_0x0009;
                case 2: goto L_0x0015;
                case 3: goto L_0x0036;
                case 4: goto L_0x005d;
                case 5: goto L_0x00a6;
                default: goto L_0x0008;
            }
        L_0x0008:
            return r6
        L_0x0009:
            android.content.Context r0 = r8.b
            boolean r0 = com.mob.commons.a.i(r0)
            if (r0 == 0) goto L_0x0008
            r8.b()
            goto L_0x0008
        L_0x0015:
            android.content.Context r0 = r8.b
            boolean r0 = com.mob.commons.a.n(r0)
            if (r0 == 0) goto L_0x0032
            boolean r0 = r8.c()
            if (r0 == 0) goto L_0x0026
            r8.d()
        L_0x0026:
            r8.e()
        L_0x0029:
            android.os.Handler r0 = r8.d
            r2 = 3600000(0x36ee80, double:1.7786363E-317)
            r0.sendEmptyMessageDelayed(r1, r2)
            goto L_0x0008
        L_0x0032:
            r8.f()
            goto L_0x0029
        L_0x0036:
            android.content.Context r0 = r8.b
            boolean r0 = com.mob.commons.a.j(r0)
            if (r0 == 0) goto L_0x0008
            r8.g()     // Catch:{ Throwable -> 0x0054 }
        L_0x0041:
            java.util.Random r0 = r8.e
            r1 = 120(0x78, float:1.68E-43)
            int r0 = r0.nextInt(r1)
            int r0 = r0 + 180
            android.os.Handler r1 = r8.d
            int r0 = r0 * 1000
            long r2 = (long) r0
            r1.sendEmptyMessageDelayed(r7, r2)
            goto L_0x0008
        L_0x0054:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            goto L_0x0041
        L_0x005d:
            android.content.Context r0 = r8.b
            boolean r0 = com.mob.commons.a.j(r0)
            if (r0 == 0) goto L_0x0008
            android.content.Context r0 = r8.b
            long r0 = com.mob.commons.a.a((android.content.Context) r0)
            android.content.Context r2 = r8.b
            int r2 = com.mob.commons.a.k(r2)
            long r2 = (long) r2
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r4
            long r0 = r0 + r2
            android.content.Context r2 = r8.b
            long r2 = com.mob.commons.a.a((android.content.Context) r2)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x0086
            boolean r0 = r8.h()     // Catch:{ Throwable -> 0x009d }
            if (r0 == 0) goto L_0x0089
        L_0x0086:
            r8.g()     // Catch:{ Throwable -> 0x009d }
        L_0x0089:
            java.util.Random r0 = r8.e
            r1 = 120(0x78, float:1.68E-43)
            int r0 = r0.nextInt(r1)
            int r0 = r0 + 180
            android.os.Handler r1 = r8.d
            int r0 = r0 * 1000
            long r2 = (long) r0
            r1.sendEmptyMessageDelayed(r7, r2)
            goto L_0x0008
        L_0x009d:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            goto L_0x0089
        L_0x00a6:
            android.content.Context r0 = r8.b
            boolean r0 = com.mob.commons.a.l(r0)
            if (r0 == 0) goto L_0x010e
            java.lang.String r0 = "DeviceHelper"
            java.lang.String r1 = "getInstance"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x011f }
            r3 = 0
            android.content.Context r4 = r8.b     // Catch:{ Throwable -> 0x011f }
            r2[r3] = r4     // Catch:{ Throwable -> 0x011f }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeStaticMethod(r0, r1, r2)     // Catch:{ Throwable -> 0x011f }
            java.lang.String r0 = "getLocation"
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x011f }
            r3 = 0
            r4 = 30
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x011f }
            r2[r3] = r4     // Catch:{ Throwable -> 0x011f }
            r3 = 1
            r4 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x011f }
            r2[r3] = r4     // Catch:{ Throwable -> 0x011f }
            r3 = 2
            r4 = 1
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x011f }
            r2[r3] = r4     // Catch:{ Throwable -> 0x011f }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r2)     // Catch:{ Throwable -> 0x011f }
            android.location.Location r0 = (android.location.Location) r0     // Catch:{ Throwable -> 0x011f }
            r2 = 1
            r8.a(r0, r2)     // Catch:{ Throwable -> 0x011f }
            java.lang.String r0 = "getLocation"
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x011f }
            r3 = 0
            r4 = 15
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x011f }
            r2[r3] = r4     // Catch:{ Throwable -> 0x011f }
            r3 = 1
            r4 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Throwable -> 0x011f }
            r2[r3] = r4     // Catch:{ Throwable -> 0x011f }
            r3 = 2
            r4 = 1
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x011f }
            r2[r3] = r4     // Catch:{ Throwable -> 0x011f }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r2)     // Catch:{ Throwable -> 0x011f }
            android.location.Location r0 = (android.location.Location) r0     // Catch:{ Throwable -> 0x011f }
            r1 = 2
            r8.a(r0, r1)     // Catch:{ Throwable -> 0x011f }
        L_0x010e:
            android.os.Handler r0 = r8.d
            r1 = 5
            android.content.Context r2 = r8.b
            int r2 = com.mob.commons.a.m(r2)
            int r2 = r2 * 1000
            long r2 = (long) r2
            r0.sendEmptyMessageDelayed(r1, r2)
            goto L_0x0008
        L_0x011f:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            goto L_0x010e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.clt.DvcClt.handleMessage(android.os.Message):boolean");
    }

    private void b() {
        try {
            HashMap hashMap = new HashMap();
            Object invokeStaticMethod = ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", this.b);
            hashMap.put("phonename", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getBluetoothName", new Object[0]));
            hashMap.put("signmd5", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getSignMD5", new Object[0]));
            String MD5 = Data.MD5(this.c.fromHashMap(hashMap));
            String a2 = e.a(this.b);
            if (a2 == null || !a2.equals(MD5)) {
                HashMap hashMap2 = new HashMap();
                hashMap2.put("type", "DEVEXT");
                hashMap2.put("data", hashMap);
                hashMap2.put("datetime", Long.valueOf(a.a(this.b)));
                b.a(this.b).a(a.a(this.b), (HashMap<String, Object>) hashMap2);
                e.a(this.b, MD5);
            }
        } catch (Throwable th) {
            MobLog.getInstance().w(th);
        }
    }

    private boolean c() {
        long b2 = e.b(this.b);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(b2);
        int i = instance.get(1);
        int i2 = instance.get(2);
        int i3 = instance.get(5);
        long a2 = a.a(this.b);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(a2);
        int i4 = instance2.get(1);
        int i5 = instance2.get(2);
        int i6 = instance2.get(5);
        if (i == i4 && i2 == i5 && i3 == i6) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void d() {
        synchronized (a) {
            try {
                HashMap hashMap = new HashMap();
                Object invokeStaticMethod = ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", this.b);
                hashMap.put("ssid", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getSSID", new Object[0]));
                hashMap.put("bssid", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getBssid", new Object[0]));
                HashMap hashMap2 = new HashMap();
                hashMap2.put("type", "WIFI_INFO");
                hashMap2.put("data", hashMap);
                long a2 = a.a(this.b);
                hashMap2.put("datetime", Long.valueOf(a2));
                b.a(this.b).a(a.a(this.b), (HashMap<String, Object>) hashMap2);
                e.a(this.b, a2);
                e.b(this.b, Data.MD5(this.c.fromHashMap(hashMap)));
            } catch (Throwable th) {
                MobLog.getInstance().w(th);
            }
        }
    }

    private void e() {
        if (this.f == null) {
            this.f = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    Parcelable parcelableExtra;
                    try {
                        if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction()) && (parcelableExtra = intent.getParcelableExtra("networkInfo")) != null && ((NetworkInfo) parcelableExtra).isAvailable()) {
                            HashMap hashMap = new HashMap();
                            Object invokeStaticMethod = ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", context);
                            hashMap.put("ssid", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getSSID", new Object[0]));
                            hashMap.put("bssid", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getBssid", new Object[0]));
                            String MD5 = Data.MD5(DvcClt.this.c.fromHashMap(hashMap));
                            String c = e.c(context);
                            if ((c == null || !c.equals(MD5)) && a.n(context)) {
                                DvcClt.this.d();
                            }
                        }
                    } catch (Throwable th) {
                        MobLog.getInstance().w(th);
                    }
                }
            };
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        try {
            this.b.registerReceiver(this.f, intentFilter);
        } catch (Throwable th) {
        }
    }

    private void f() {
        if (this.f != null) {
            try {
                this.b.unregisterReceiver(this.f);
            } catch (Throwable th) {
            }
            this.f = null;
        }
    }

    private void g() throws Throwable {
        int i;
        HashMap hashMap = new HashMap();
        Object invokeStaticMethod = ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", this.b);
        try {
            i = Integer.parseInt((String) ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getCarrier", new Object[0]));
        } catch (Throwable th) {
            i = -1;
        }
        hashMap.put("carrier", Integer.valueOf(i));
        hashMap.put("simopname", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getCarrierName", new Object[0]));
        hashMap.put("lac", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getCellLac", new Object[0]));
        hashMap.put("cell", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getCellId", new Object[0]));
        HashMap hashMap2 = new HashMap();
        hashMap2.put("type", "BSINFO");
        hashMap2.put("data", hashMap);
        hashMap2.put("datetime", Long.valueOf(a.a(this.b)));
        b.a(this.b).a(a.a(this.b), (HashMap<String, Object>) hashMap2);
        e.c(this.b, Data.MD5(this.c.fromHashMap(hashMap)));
        e.b(this.b, a.a(this.b) + (((long) a.k(this.b)) * 1000));
    }

    private boolean h() throws Throwable {
        int i;
        HashMap hashMap = new HashMap();
        Object invokeStaticMethod = ReflectHelper.invokeStaticMethod("DeviceHelper", "getInstance", this.b);
        try {
            i = Integer.parseInt((String) ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getCarrier", new Object[0]));
        } catch (Throwable th) {
            i = -1;
        }
        hashMap.put("carrier", Integer.valueOf(i));
        hashMap.put("simopname", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getCarrierName", new Object[0]));
        hashMap.put("lac", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getCellLac", new Object[0]));
        hashMap.put("cell", ReflectHelper.invokeInstanceMethod(invokeStaticMethod, "getCellId", new Object[0]));
        String MD5 = Data.MD5(this.c.fromHashMap(hashMap));
        String d2 = e.d(this.b);
        return d2 == null || !d2.equals(MD5);
    }

    private void a(Location location, int i) {
        if (location != null) {
            HashMap hashMap = new HashMap();
            hashMap.put("accuracy", Float.valueOf(location.getAccuracy()));
            hashMap.put("latitude", Double.valueOf(location.getLatitude()));
            hashMap.put("longitude", Double.valueOf(location.getLongitude()));
            hashMap.put("location_type", Integer.valueOf(i));
            HashMap hashMap2 = new HashMap();
            hashMap2.put("type", "LOCATION");
            hashMap2.put("data", hashMap);
            hashMap2.put("datetime", Long.valueOf(a.a(this.b)));
            b.a(this.b).a(a.a(this.b), (HashMap<String, Object>) hashMap2);
        }
    }
}
