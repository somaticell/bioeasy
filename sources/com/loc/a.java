package com.loc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Messenger;
import com.amap.api.fence.Fence;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.APSService;
import com.amap.api.location.LocationManagerBase;
import com.baidu.mapapi.UIMsg;
import java.util.ArrayList;

/* compiled from: AMapLocationManager */
public class a implements LocationManagerBase {
    AMapLocationClientOption a;
    b b;
    g c = null;
    ArrayList<AMapLocationListener> d = new ArrayList<>();
    f e;
    boolean f = false;
    i g;
    Messenger h = null;
    Messenger i = null;
    C0022a j;
    Intent k = null;
    int l = 0;
    long m = 0;
    long n = 0;
    int o = 0;
    boolean p = false;
    private final int q = 10000;
    private final int r = UIMsg.m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    private Context s;
    /* access modifiers changed from: private */
    public boolean t = false;
    /* access modifiers changed from: private */
    public boolean u = true;
    private long v = 0;
    /* access modifiers changed from: private */
    public boolean w = true;
    private boolean x = false;
    private ServiceConnection y = new b(this);

    public a(Context context, Intent intent) {
        this.s = context;
        this.k = intent;
        b();
    }

    private void b() {
        a(this.k);
        this.g = i.a(this.s);
        if (Looper.myLooper() == null) {
            this.b = new b(this, this.s.getMainLooper());
        } else {
            this.b = new b(this);
        }
        this.i = new Messenger(this.b);
        this.c = new g(this.s, this.b);
        try {
            this.e = new f(this.s);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void a(Intent intent) {
        if (intent == null) {
            try {
                intent = new Intent(this.s, APSService.class);
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
        intent.putExtra("apiKey", e.a);
        this.s.bindService(intent, this.y, 1);
    }

    public void setLocationOption(AMapLocationClientOption aMapLocationClientOption) {
        this.a = aMapLocationClientOption;
    }

    public void setLocationListener(AMapLocationListener aMapLocationListener) {
        if (aMapLocationListener == null) {
            throw new IllegalArgumentException("listener参数不能为null");
        }
        if (this.d == null) {
            this.d = new ArrayList<>();
        }
        if (!this.d.contains(aMapLocationListener)) {
            this.d.add(aMapLocationListener);
        }
    }

    private void c() {
        if (this.j == null) {
            this.j = new C0022a("locationThread");
            this.j.start();
        }
    }

    public void startLocation() {
        if (this.a == null) {
            this.a = new AMapLocationClientOption();
        }
        this.u = false;
        c();
        switch (c.a[this.a.getLocationMode().ordinal()]) {
            case 1:
                this.c.a();
                this.x = false;
                return;
            case 2:
            case 3:
                if (!this.x) {
                    this.c.a(this.a);
                    this.x = true;
                    return;
                }
                return;
            default:
                return;
        }
    }

    public AMapLocation getLastKnownLocation() {
        try {
            return this.g.a();
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public void stopLocation() {
        a();
        this.c.a();
        this.x = false;
        this.p = false;
        this.t = false;
        this.u = true;
        this.n = 0;
        this.m = 0;
        this.o = 0;
        this.l = 0;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (this.j != null) {
            this.j.a = false;
            this.j.interrupt();
        }
        this.j = null;
    }

    public void onDestroy() {
        this.w = true;
        stopLocation();
        if (this.e != null) {
            this.e.a();
        }
        if (this.y != null) {
            this.s.unbindService(this.y);
        }
        if (this.d != null) {
            this.d.clear();
            this.d = null;
        }
        this.y = null;
        if (this.b != null) {
            this.b.removeCallbacksAndMessages((Object) null);
        }
    }

    public void addGeoFenceAlert(String str, double d2, double d3, float f2, long j2, PendingIntent pendingIntent) {
        Fence fence = new Fence();
        fence.b = str;
        fence.d = d2;
        fence.c = d3;
        fence.e = f2;
        fence.a = pendingIntent;
        fence.a(j2);
        if (this.e != null) {
            this.e.a(fence, fence.a);
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent, String str) {
        if (this.e != null) {
            this.e.a(pendingIntent, str);
        }
    }

    public void removeGeoFenceAlert(PendingIntent pendingIntent) {
        if (this.e != null) {
            this.e.a(pendingIntent);
        }
    }

    /* renamed from: com.loc.a$a  reason: collision with other inner class name */
    /* compiled from: AMapLocationManager */
    class C0022a extends Thread {
        boolean a = false;

        public C0022a(String str) {
            super(str);
        }

        /* JADX WARNING: CFG modification limit reached, blocks count: 183 */
        /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
            r10.b.n = 0;
            com.loc.a.a(r10.b, true);
            r0 = android.os.Message.obtain();
            r0.what = 1;
            r1 = new android.os.Bundle();
            r1.putBoolean("isfirst", com.loc.a.c(r10.b));
            r1.putBoolean("wifiactivescan", r10.b.a.isWifiActiveScan());
            r1.putBoolean("isNeedAddress", r10.b.a.isNeedAddress());
            r1.putBoolean("isKillProcess", r10.b.a.isKillProcess());
            r1.putBoolean("isOffset", r10.b.a.isOffset());
            r1.putLong("httptimeout", r10.b.a.getHttpTimeOut());
            r0.setData(r1);
            r0.replyTo = r10.b.i;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x0170, code lost:
            if (r10.b.h == null) goto L_0x0179;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x0172, code lost:
            r10.b.h.send(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x0196, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:0x0197, code lost:
            r0.printStackTrace();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r10 = this;
                r8 = 0
                r6 = 0
                r0 = 1
                r10.a = r0
            L_0x0006:
                boolean r0 = r10.a     // Catch:{ Throwable -> 0x0043 }
                if (r0 == 0) goto L_0x0047
                boolean r0 = java.lang.Thread.interrupted()     // Catch:{ Throwable -> 0x0043 }
                if (r0 != 0) goto L_0x0047
                android.os.Message r0 = android.os.Message.obtain()     // Catch:{ Throwable -> 0x019e }
                r1 = 6
                r0.what = r1     // Catch:{ Throwable -> 0x019e }
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x019e }
                android.os.Messenger r1 = r1.h     // Catch:{ Throwable -> 0x019e }
                if (r1 == 0) goto L_0x0024
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x019e }
                android.os.Messenger r1 = r1.h     // Catch:{ Throwable -> 0x019e }
                r1.send(r0)     // Catch:{ Throwable -> 0x019e }
            L_0x0024:
                com.amap.api.location.AMapLocationClientOption$AMapLocationMode r0 = com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Device_Sensors     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption r1 = r1.a     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption$AMapLocationMode r1 = r1.getLocationMode()     // Catch:{ Throwable -> 0x0043 }
                boolean r0 = r0.equals(r1)     // Catch:{ Throwable -> 0x0043 }
                if (r0 == 0) goto L_0x004d
                r0 = 2000(0x7d0, double:9.88E-321)
                java.lang.Thread.sleep(r0)     // Catch:{ InterruptedException -> 0x003a }
                goto L_0x0006
            L_0x003a:
                r0 = move-exception
                java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x0043 }
                r0.interrupt()     // Catch:{ Throwable -> 0x0043 }
                goto L_0x0006
            L_0x0043:
                r0 = move-exception
                r0.printStackTrace()
            L_0x0047:
                com.loc.a r0 = com.loc.a.this
                boolean unused = r0.t = r6
                return
            L_0x004d:
                com.amap.api.location.AMapLocationClientOption$AMapLocationMode r0 = com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption r1 = r1.a     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption$AMapLocationMode r1 = r1.getLocationMode()     // Catch:{ Throwable -> 0x0043 }
                boolean r0 = r0.equals(r1)     // Catch:{ Throwable -> 0x0043 }
                if (r0 == 0) goto L_0x0065
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                boolean r0 = r0.d()     // Catch:{ Throwable -> 0x0043 }
                if (r0 == 0) goto L_0x0087
            L_0x0065:
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption r0 = r0.a     // Catch:{ Throwable -> 0x0043 }
                boolean r0 = r0.isGpsFirst()     // Catch:{ Throwable -> 0x0043 }
                if (r0 == 0) goto L_0x00b7
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption r0 = r0.a     // Catch:{ Throwable -> 0x0043 }
                boolean r0 = r0.isOnceLocation()     // Catch:{ Throwable -> 0x0043 }
                if (r0 == 0) goto L_0x00b7
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                boolean r0 = r0.e()     // Catch:{ Throwable -> 0x0043 }
                if (r0 != 0) goto L_0x00b7
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                boolean r0 = r0.p     // Catch:{ Throwable -> 0x0043 }
                if (r0 != 0) goto L_0x00b7
            L_0x0087:
                com.loc.a r0 = com.loc.a.this     // Catch:{ InterruptedException -> 0x00a8 }
                com.amap.api.location.AMapLocationClientOption r0 = r0.a     // Catch:{ InterruptedException -> 0x00a8 }
                boolean r0 = r0.isOnceLocation()     // Catch:{ InterruptedException -> 0x00a8 }
                if (r0 == 0) goto L_0x00a1
                com.loc.a r0 = com.loc.a.this     // Catch:{ InterruptedException -> 0x00a8 }
                long r0 = r0.n     // Catch:{ InterruptedException -> 0x00a8 }
                int r0 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
                if (r0 != 0) goto L_0x00a1
                com.loc.a r0 = com.loc.a.this     // Catch:{ InterruptedException -> 0x00a8 }
                long r2 = com.loc.bw.b()     // Catch:{ InterruptedException -> 0x00a8 }
                r0.n = r2     // Catch:{ InterruptedException -> 0x00a8 }
            L_0x00a1:
                r0 = 2000(0x7d0, double:9.88E-321)
                java.lang.Thread.sleep(r0)     // Catch:{ InterruptedException -> 0x00a8 }
                goto L_0x0006
            L_0x00a8:
                r0 = move-exception
                java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x0043 }
                r0.interrupt()     // Catch:{ Throwable -> 0x0043 }
                goto L_0x0006
            L_0x00b2:
                r0 = 50
                java.lang.Thread.sleep(r0)     // Catch:{ InterruptedException -> 0x019b }
            L_0x00b7:
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                android.os.Messenger r0 = r0.h     // Catch:{ Throwable -> 0x0043 }
                if (r0 != 0) goto L_0x00fe
                com.loc.a r0 = com.loc.a.this     // Catch:{ InterruptedException -> 0x019b }
                int r1 = r0.l     // Catch:{ InterruptedException -> 0x019b }
                int r1 = r1 + 1
                r0.l = r1     // Catch:{ InterruptedException -> 0x019b }
                com.loc.a r0 = com.loc.a.this     // Catch:{ InterruptedException -> 0x019b }
                int r0 = r0.l     // Catch:{ InterruptedException -> 0x019b }
                r1 = 40
                if (r0 <= r1) goto L_0x00b2
                android.os.Message r0 = android.os.Message.obtain()     // Catch:{ InterruptedException -> 0x019b }
                android.os.Bundle r1 = new android.os.Bundle     // Catch:{ InterruptedException -> 0x019b }
                r1.<init>()     // Catch:{ InterruptedException -> 0x019b }
                com.autonavi.aps.amapapi.model.AmapLoc r2 = new com.autonavi.aps.amapapi.model.AmapLoc     // Catch:{ InterruptedException -> 0x019b }
                r2.<init>()     // Catch:{ InterruptedException -> 0x019b }
                r3 = 10
                r2.b((int) r3)     // Catch:{ InterruptedException -> 0x019b }
                java.lang.String r3 = "请检查配置文件是否配置服务"
                r2.b((java.lang.String) r3)     // Catch:{ InterruptedException -> 0x019b }
                java.lang.String r3 = "location"
                r1.putParcelable(r3, r2)     // Catch:{ InterruptedException -> 0x019b }
                r0.setData(r1)     // Catch:{ InterruptedException -> 0x019b }
                r1 = 1
                r0.what = r1     // Catch:{ InterruptedException -> 0x019b }
                com.loc.a r1 = com.loc.a.this     // Catch:{ InterruptedException -> 0x019b }
                com.loc.a$b r1 = r1.b     // Catch:{ InterruptedException -> 0x019b }
                if (r1 == 0) goto L_0x00fe
                com.loc.a r1 = com.loc.a.this     // Catch:{ InterruptedException -> 0x019b }
                com.loc.a$b r1 = r1.b     // Catch:{ InterruptedException -> 0x019b }
                r1.sendMessage(r0)     // Catch:{ InterruptedException -> 0x019b }
            L_0x00fe:
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r2 = 0
                r0.n = r2     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r1 = 1
                boolean unused = r0.t = r1     // Catch:{ Throwable -> 0x0043 }
                android.os.Message r0 = android.os.Message.obtain()     // Catch:{ Throwable -> 0x0043 }
                r1 = 1
                r0.what = r1     // Catch:{ Throwable -> 0x0043 }
                android.os.Bundle r1 = new android.os.Bundle     // Catch:{ Throwable -> 0x0043 }
                r1.<init>()     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r2 = "isfirst"
                com.loc.a r3 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                boolean r3 = r3.w     // Catch:{ Throwable -> 0x0043 }
                r1.putBoolean(r2, r3)     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r2 = "wifiactivescan"
                com.loc.a r3 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption r3 = r3.a     // Catch:{ Throwable -> 0x0043 }
                boolean r3 = r3.isWifiActiveScan()     // Catch:{ Throwable -> 0x0043 }
                r1.putBoolean(r2, r3)     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r2 = "isNeedAddress"
                com.loc.a r3 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption r3 = r3.a     // Catch:{ Throwable -> 0x0043 }
                boolean r3 = r3.isNeedAddress()     // Catch:{ Throwable -> 0x0043 }
                r1.putBoolean(r2, r3)     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r2 = "isKillProcess"
                com.loc.a r3 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption r3 = r3.a     // Catch:{ Throwable -> 0x0043 }
                boolean r3 = r3.isKillProcess()     // Catch:{ Throwable -> 0x0043 }
                r1.putBoolean(r2, r3)     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r2 = "isOffset"
                com.loc.a r3 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption r3 = r3.a     // Catch:{ Throwable -> 0x0043 }
                boolean r3 = r3.isOffset()     // Catch:{ Throwable -> 0x0043 }
                r1.putBoolean(r2, r3)     // Catch:{ Throwable -> 0x0043 }
                java.lang.String r2 = "httptimeout"
                com.loc.a r3 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                com.amap.api.location.AMapLocationClientOption r3 = r3.a     // Catch:{ Throwable -> 0x0043 }
                long r4 = r3.getHttpTimeOut()     // Catch:{ Throwable -> 0x0043 }
                r1.putLong(r2, r4)     // Catch:{ Throwable -> 0x0043 }
                r0.setData(r1)     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                android.os.Messenger r1 = r1.i     // Catch:{ Throwable -> 0x0043 }
                r0.replyTo = r1     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x0196 }
                android.os.Messenger r1 = r1.h     // Catch:{ Throwable -> 0x0196 }
                if (r1 == 0) goto L_0x0179
                com.loc.a r1 = com.loc.a.this     // Catch:{ Throwable -> 0x0196 }
                android.os.Messenger r1 = r1.h     // Catch:{ Throwable -> 0x0196 }
                r1.send(r0)     // Catch:{ Throwable -> 0x0196 }
            L_0x0179:
                com.loc.a r0 = com.loc.a.this     // Catch:{ Throwable -> 0x0043 }
                r1 = 0
                boolean unused = r0.w = r1     // Catch:{ Throwable -> 0x0043 }
                com.loc.a r0 = com.loc.a.this     // Catch:{ InterruptedException -> 0x018c }
                com.amap.api.location.AMapLocationClientOption r0 = r0.a     // Catch:{ InterruptedException -> 0x018c }
                long r0 = r0.getInterval()     // Catch:{ InterruptedException -> 0x018c }
                java.lang.Thread.sleep(r0)     // Catch:{ InterruptedException -> 0x018c }
                goto L_0x0006
            L_0x018c:
                r0 = move-exception
                java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ Throwable -> 0x0043 }
                r0.interrupt()     // Catch:{ Throwable -> 0x0043 }
                goto L_0x0006
            L_0x0196:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Throwable -> 0x0043 }
                goto L_0x0179
            L_0x019b:
                r0 = move-exception
                goto L_0x00b7
            L_0x019e:
                r0 = move-exception
                goto L_0x0024
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.a.C0022a.run():void");
        }
    }

    public void startAssistantLocation() {
        if (this.b != null) {
            this.b.sendEmptyMessage(101);
        }
    }

    public void stopAssistantLocation() {
        if (this.b != null) {
            this.b.sendEmptyMessage(102);
        }
    }

    /* access modifiers changed from: private */
    public boolean d() {
        if (bw.b() - this.m > 10000) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean e() {
        long b2 = bw.b();
        if (this.n != 0 && b2 - this.n > 30000) {
            return true;
        }
        return false;
    }

    /* compiled from: AMapLocationManager */
    public static class b extends Handler {
        a a = null;

        public b(a aVar, Looper looper) {
            super(looper);
            this.a = aVar;
        }

        public b(a aVar) {
            this.a = aVar;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0047, code lost:
            if ("gps".equals(r1.getProvider()) == false) goto L_0x0049;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x004f, code lost:
            if (com.loc.a.a(r5.a) == false) goto L_0x0082;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0057, code lost:
            if (com.loc.a.e(r5.a) != false) goto L_0x0082;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x005d, code lost:
            if (r5.a.g == null) goto L_0x0066;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x005f, code lost:
            r5.a.g.a(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0066, code lost:
            r2 = r5.a.d.iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0072, code lost:
            if (r2.hasNext() == false) goto L_0x0082;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0074, code lost:
            r2.next().onLocationChanged(r1);
         */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x0034 A[Catch:{ Throwable -> 0x0142 }] */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x003d  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r6) {
            /*
                r5 = this;
                r2 = 10
                r4 = 1
                super.handleMessage(r6)
                r1 = 0
                int r0 = r6.what
                switch(r0) {
                    case 1: goto L_0x000d;
                    case 2: goto L_0x0099;
                    case 5: goto L_0x00b8;
                    case 100: goto L_0x00c6;
                    case 101: goto L_0x00d0;
                    case 102: goto L_0x0109;
                    default: goto L_0x000c;
                }
            L_0x000c:
                return
            L_0x000d:
                android.os.Bundle r0 = r6.getData()     // Catch:{ Throwable -> 0x014b }
                if (r0 == 0) goto L_0x0152
                java.lang.Class<com.autonavi.aps.amapapi.model.AmapLoc> r2 = com.autonavi.aps.amapapi.model.AmapLoc.class
                java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch:{ Throwable -> 0x014b }
                r0.setClassLoader(r2)     // Catch:{ Throwable -> 0x014b }
                java.lang.String r2 = "location"
                android.os.Parcelable r0 = r0.getParcelable(r2)     // Catch:{ Throwable -> 0x014b }
                com.autonavi.aps.amapapi.model.AmapLoc r0 = (com.autonavi.aps.amapapi.model.AmapLoc) r0     // Catch:{ Throwable -> 0x014b }
                com.amap.api.location.AMapLocation r0 = com.loc.e.a((com.autonavi.aps.amapapi.model.AmapLoc) r0)     // Catch:{ Throwable -> 0x014b }
                java.lang.String r1 = "lbs"
                r0.setProvider(r1)     // Catch:{ Throwable -> 0x014e }
            L_0x002d:
                r1 = r0
            L_0x002e:
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x0142 }
                com.loc.f r0 = r0.e     // Catch:{ Throwable -> 0x0142 }
                if (r0 == 0) goto L_0x003b
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x0142 }
                com.loc.f r0 = r0.e     // Catch:{ Throwable -> 0x0142 }
                r0.a((com.amap.api.location.AMapLocation) r1)     // Catch:{ Throwable -> 0x0142 }
            L_0x003b:
                if (r1 == 0) goto L_0x0049
                java.lang.String r0 = "gps"
                java.lang.String r2 = r1.getProvider()     // Catch:{ Throwable -> 0x007e }
                boolean r0 = r0.equals(r2)     // Catch:{ Throwable -> 0x007e }
                if (r0 != 0) goto L_0x0051
            L_0x0049:
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x007e }
                boolean r0 = r0.d()     // Catch:{ Throwable -> 0x007e }
                if (r0 == 0) goto L_0x0082
            L_0x0051:
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x007e }
                boolean r0 = r0.u     // Catch:{ Throwable -> 0x007e }
                if (r0 != 0) goto L_0x0082
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x007e }
                com.loc.i r0 = r0.g     // Catch:{ Throwable -> 0x007e }
                if (r0 == 0) goto L_0x0066
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x007e }
                com.loc.i r0 = r0.g     // Catch:{ Throwable -> 0x007e }
                r0.a((com.amap.api.location.AMapLocation) r1)     // Catch:{ Throwable -> 0x007e }
            L_0x0066:
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x007e }
                java.util.ArrayList<com.amap.api.location.AMapLocationListener> r0 = r0.d     // Catch:{ Throwable -> 0x007e }
                java.util.Iterator r2 = r0.iterator()     // Catch:{ Throwable -> 0x007e }
            L_0x006e:
                boolean r0 = r2.hasNext()     // Catch:{ Throwable -> 0x007e }
                if (r0 == 0) goto L_0x0082
                java.lang.Object r0 = r2.next()     // Catch:{ Throwable -> 0x007e }
                com.amap.api.location.AMapLocationListener r0 = (com.amap.api.location.AMapLocationListener) r0     // Catch:{ Throwable -> 0x007e }
                r0.onLocationChanged(r1)     // Catch:{ Throwable -> 0x007e }
                goto L_0x006e
            L_0x007e:
                r0 = move-exception
                r0.printStackTrace()
            L_0x0082:
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x0093 }
                com.amap.api.location.AMapLocationClientOption r0 = r0.a     // Catch:{ Throwable -> 0x0093 }
                boolean r0 = r0.isOnceLocation()     // Catch:{ Throwable -> 0x0093 }
                if (r0 == 0) goto L_0x000c
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x0093 }
                r0.stopLocation()     // Catch:{ Throwable -> 0x0093 }
                goto L_0x000c
            L_0x0093:
                r0 = move-exception
                r0.printStackTrace()
                goto L_0x000c
            L_0x0099:
                java.lang.Object r0 = r6.obj     // Catch:{ Throwable -> 0x00b3 }
                com.amap.api.location.AMapLocation r0 = (com.amap.api.location.AMapLocation) r0     // Catch:{ Throwable -> 0x00b3 }
                int r1 = r0.getErrorCode()     // Catch:{ Throwable -> 0x0148 }
                if (r1 != 0) goto L_0x00b0
                com.loc.a r1 = r5.a     // Catch:{ Throwable -> 0x0148 }
                long r2 = com.loc.bw.b()     // Catch:{ Throwable -> 0x0148 }
                r1.m = r2     // Catch:{ Throwable -> 0x0148 }
                com.loc.a r1 = r5.a     // Catch:{ Throwable -> 0x0148 }
                r2 = 1
                r1.p = r2     // Catch:{ Throwable -> 0x0148 }
            L_0x00b0:
                r1 = r0
                goto L_0x002e
            L_0x00b3:
                r0 = move-exception
                r0 = r1
            L_0x00b5:
                r1 = r0
                goto L_0x002e
            L_0x00b8:
                com.loc.a r0 = r5.a
                long r2 = com.loc.bw.b()
                r0.m = r2
                com.loc.a r0 = r5.a
                r0.p = r4
                goto L_0x000c
            L_0x00c6:
                com.loc.a r0 = r5.a     // Catch:{ Throwable -> 0x00cd }
                r0.f()     // Catch:{ Throwable -> 0x00cd }
                goto L_0x000c
            L_0x00cd:
                r0 = move-exception
                goto L_0x000c
            L_0x00d0:
                android.os.Message r0 = android.os.Message.obtain()     // Catch:{ RemoteException -> 0x00eb }
                r1 = 2
                r0.what = r1     // Catch:{ RemoteException -> 0x00eb }
                com.loc.a r1 = r5.a     // Catch:{ RemoteException -> 0x00eb }
                android.os.Messenger r1 = r1.h     // Catch:{ RemoteException -> 0x00eb }
                if (r1 == 0) goto L_0x00ee
                com.loc.a r1 = r5.a     // Catch:{ RemoteException -> 0x00eb }
                r2 = 0
                r1.o = r2     // Catch:{ RemoteException -> 0x00eb }
                com.loc.a r1 = r5.a     // Catch:{ RemoteException -> 0x00eb }
                android.os.Messenger r1 = r1.h     // Catch:{ RemoteException -> 0x00eb }
                r1.send(r0)     // Catch:{ RemoteException -> 0x00eb }
                goto L_0x000c
            L_0x00eb:
                r0 = move-exception
                goto L_0x000c
            L_0x00ee:
                com.loc.a r0 = r5.a     // Catch:{ RemoteException -> 0x00eb }
                int r1 = r0.o     // Catch:{ RemoteException -> 0x00eb }
                int r1 = r1 + 1
                r0.o = r1     // Catch:{ RemoteException -> 0x00eb }
                com.loc.a r0 = r5.a     // Catch:{ RemoteException -> 0x00eb }
                int r0 = r0.o     // Catch:{ RemoteException -> 0x00eb }
                if (r0 >= r2) goto L_0x000c
                com.loc.a r0 = r5.a     // Catch:{ RemoteException -> 0x00eb }
                com.loc.a$b r0 = r0.b     // Catch:{ RemoteException -> 0x00eb }
                r1 = 101(0x65, float:1.42E-43)
                r2 = 50
                r0.sendEmptyMessageDelayed(r1, r2)     // Catch:{ RemoteException -> 0x00eb }
                goto L_0x000c
            L_0x0109:
                android.os.Message r0 = android.os.Message.obtain()     // Catch:{ RemoteException -> 0x0124 }
                r1 = 3
                r0.what = r1     // Catch:{ RemoteException -> 0x0124 }
                com.loc.a r1 = r5.a     // Catch:{ RemoteException -> 0x0124 }
                android.os.Messenger r1 = r1.h     // Catch:{ RemoteException -> 0x0124 }
                if (r1 == 0) goto L_0x0127
                com.loc.a r1 = r5.a     // Catch:{ RemoteException -> 0x0124 }
                r2 = 0
                r1.o = r2     // Catch:{ RemoteException -> 0x0124 }
                com.loc.a r1 = r5.a     // Catch:{ RemoteException -> 0x0124 }
                android.os.Messenger r1 = r1.h     // Catch:{ RemoteException -> 0x0124 }
                r1.send(r0)     // Catch:{ RemoteException -> 0x0124 }
                goto L_0x000c
            L_0x0124:
                r0 = move-exception
                goto L_0x000c
            L_0x0127:
                com.loc.a r0 = r5.a     // Catch:{ RemoteException -> 0x0124 }
                int r1 = r0.o     // Catch:{ RemoteException -> 0x0124 }
                int r1 = r1 + 1
                r0.o = r1     // Catch:{ RemoteException -> 0x0124 }
                com.loc.a r0 = r5.a     // Catch:{ RemoteException -> 0x0124 }
                int r0 = r0.o     // Catch:{ RemoteException -> 0x0124 }
                if (r0 >= r2) goto L_0x000c
                com.loc.a r0 = r5.a     // Catch:{ RemoteException -> 0x0124 }
                com.loc.a$b r0 = r0.b     // Catch:{ RemoteException -> 0x0124 }
                r1 = 102(0x66, float:1.43E-43)
                r2 = 50
                r0.sendEmptyMessageDelayed(r1, r2)     // Catch:{ RemoteException -> 0x0124 }
                goto L_0x000c
            L_0x0142:
                r0 = move-exception
                r0.printStackTrace()
                goto L_0x003b
            L_0x0148:
                r1 = move-exception
                goto L_0x00b5
            L_0x014b:
                r0 = move-exception
                goto L_0x002e
            L_0x014e:
                r1 = move-exception
                r1 = r0
                goto L_0x002e
            L_0x0152:
                r0 = r1
                goto L_0x002d
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.a.b.handleMessage(android.os.Message):void");
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        boolean z = true;
        boolean z2 = false;
        try {
            if (this.s.checkCallingOrSelfPermission("android.permission.SYSTEM_ALERT_WINDOW") == 0) {
                z2 = true;
            } else if (this.s instanceof Activity) {
                z2 = true;
                z = false;
            } else {
                z = false;
            }
            if (z2) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.s);
                builder.setMessage(bu.j());
                if (!"".equals(bu.k()) && bu.k() != null) {
                    builder.setPositiveButton(bu.k(), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            a.this.g();
                            dialogInterface.cancel();
                        }
                    });
                }
                builder.setNegativeButton(bu.l(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog create = builder.create();
                if (z) {
                    create.getWindow().setType(2003);
                }
                create.setCanceledOnTouchOutside(false);
                create.show();
                return;
            }
            g();
        } catch (Throwable th) {
            g();
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.autonavi.minimap", bu.o()));
            intent.setFlags(268435456);
            intent.setData(Uri.parse(bu.m()));
            this.s.startActivity(intent);
        } catch (Throwable th) {
        }
    }

    public String getVersion() {
        return "2.3.0";
    }

    public boolean isStarted() {
        return this.t;
    }

    public void unRegisterLocationListener(AMapLocationListener aMapLocationListener) {
        if (!this.d.isEmpty() && this.d.contains(aMapLocationListener)) {
            this.d.remove(aMapLocationListener);
        }
        if (this.d.isEmpty()) {
            stopLocation();
        }
    }
}
