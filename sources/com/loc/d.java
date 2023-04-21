package com.loc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import com.alipay.android.phone.mrpc.core.Headers;
import com.amap.api.location.APSServiceBase;
import com.autonavi.aps.amapapi.model.AmapLoc;
import com.loc.v;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: APSServiceCore */
public class d implements APSServiceBase {
    Context a;
    boolean b = false;
    String c = null;
    b d = new b(this);
    a e = null;
    boolean f = false;
    Vector<Messenger> g = new Vector<>();
    volatile boolean h = false;
    boolean i = false;
    Object j = new Object();
    Looper k;
    AmapLoc l;
    long m = bw.b();
    JSONObject n = new JSONObject();
    AmapLoc o;
    ServerSocket p = null;
    boolean q = false;
    Socket r = null;
    boolean s = false;
    c t;
    /* access modifiers changed from: private */
    public volatile boolean u = false;
    private boolean v = false;
    /* access modifiers changed from: private */
    public int w = 0;
    private boolean x = false;
    private boolean y = false;
    /* access modifiers changed from: private */
    public aw z = null;

    static /* synthetic */ int b(d dVar) {
        int i2 = dVar.w;
        dVar.w = i2 + 1;
        return i2;
    }

    public d(Context context) {
        this.a = context.getApplicationContext();
    }

    /* compiled from: APSServiceCore */
    class b extends Handler {
        d a = null;
        private boolean c = true;
        private boolean d = true;

        public b(d dVar) {
            this.a = dVar;
        }

        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 1:
                        try {
                            synchronized (d.this.j) {
                                Bundle data = message.getData();
                                d.this.f = data.getBoolean("isfirst");
                                Messenger messenger = message.replyTo;
                                long b2 = bw.b();
                                boolean z = data.getBoolean("isNeedAddress");
                                boolean z2 = data.getBoolean("isOffset");
                                if (!(z == this.c && z2 == this.d)) {
                                    this.a.m = 0;
                                }
                                this.c = z;
                                this.d = z2;
                                if (d.this.l == null || d.this.l.a() != 0 || b2 - this.a.m >= 1000) {
                                    if (!d.this.g.contains(messenger)) {
                                        d.this.g.add(messenger);
                                    }
                                    d.this.i = true;
                                    this.a.j.notify();
                                } else {
                                    Message obtain = Message.obtain();
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable(Headers.LOCATION, d.this.l);
                                    obtain.setData(bundle);
                                    obtain.what = 1;
                                    messenger.send(obtain);
                                }
                                boolean z3 = data.getBoolean("wifiactivescan");
                                d.this.b = data.getBoolean("isKillProcess");
                                long j = data.getLong("httptimeout");
                                if (d.this.n != null) {
                                    d.this.n.put("reversegeo", z);
                                    d.this.n.put("isOffset", z2);
                                    d.this.n.put("wifiactivescan", z3 ? com.alipay.sdk.cons.a.e : "0");
                                    d.this.n.put("httptimeout", j);
                                }
                                this.a.a(d.this.n);
                            }
                            break;
                        } catch (Throwable th) {
                            break;
                        }
                        break;
                    case 2:
                        d.this.a();
                        break;
                    case 3:
                        d.this.b();
                        break;
                    case 4:
                        synchronized (d.this.j) {
                            if (bu.d() && d.this.w < bu.e()) {
                                d.b(d.this);
                                d.this.i = true;
                                this.a.j.notify();
                                d.this.d.sendEmptyMessageDelayed(4, 2000);
                            }
                        }
                        break;
                    case 5:
                        synchronized (d.this.j) {
                            if (bu.f() && bu.g() > 2) {
                                d.this.i = true;
                                if (bu.h()) {
                                    this.a.j.notify();
                                } else if (!bw.d(d.this.a)) {
                                    this.a.j.notify();
                                }
                                d.this.d.sendEmptyMessageDelayed(5, (long) (bu.g() * 1000));
                            }
                        }
                        break;
                    case 6:
                        synchronized (d.this.j) {
                            d.this.i();
                        }
                        break;
                }
                super.handleMessage(message);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    public Handler getHandler() {
        return this.d;
    }

    public void onCreate() {
        try {
            this.z = new aw();
            this.c = this.a.getApplicationContext().getPackageName();
            this.h = true;
            this.e = new a();
            this.e.setName("serviceThread");
            this.e.start();
        } catch (Throwable th) {
        }
    }

    /* compiled from: APSServiceCore */
    class a extends Thread {
        a() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ab, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
            r7.a.l = com.loc.d.a(r7.a, 9, r0.getMessage());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c5, code lost:
            if (com.loc.d.h(r7.a) == false) goto L_0x00c7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c7, code lost:
            com.loc.d.i(r7.a);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00cd, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
            r7.a.l = com.loc.d.a(r7.a, 8, r0.getMessage());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ee, code lost:
            if (com.loc.d.h(r7.a) == false) goto L_0x00f0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f0, code lost:
            com.loc.d.i(r7.a);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x011a, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x0121, code lost:
            if (com.loc.d.h(r7.a) == false) goto L_0x0123;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x0123, code lost:
            com.loc.d.i(r7.a);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x0128, code lost:
            throw r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:88:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Removed duplicated region for block: B:33:0x00be A[ExcHandler: RemoteException (e android.os.RemoteException), Splitter:B:1:0x0001] */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x00e0 A[ExcHandler: InterruptedException (e java.lang.InterruptedException), Splitter:B:1:0x0001] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r7 = this;
                r6 = 0
                com.loc.d r0 = com.loc.d.this     // Catch:{ Throwable -> 0x00ab, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                r0.f()     // Catch:{ Throwable -> 0x00ab, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
            L_0x0006:
                com.loc.d r0 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                boolean r0 = r0.h     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                if (r0 == 0) goto L_0x0143
                com.loc.d r0 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                boolean r0 = r0.i     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                if (r0 == 0) goto L_0x0129
                com.loc.d r0 = com.loc.d.this     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.loc.d r2 = com.loc.d.this     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                boolean r2 = r2.f     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.autonavi.aps.amapapi.model.AmapLoc r1 = r1.a((boolean) r2)     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                r0.l = r1     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.loc.d r0 = com.loc.d.this     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.loc.aw r0 = r0.z     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                if (r0 == 0) goto L_0x003d
                com.loc.d r0 = com.loc.d.this     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.loc.aw r1 = r1.z     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.loc.d r2 = com.loc.d.this     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.autonavi.aps.amapapi.model.AmapLoc r2 = r2.l     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                r3 = 0
                java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                com.autonavi.aps.amapapi.model.AmapLoc r1 = r1.a((com.autonavi.aps.amapapi.model.AmapLoc) r2, (java.lang.String[]) r3)     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
                r0.l = r1     // Catch:{ Throwable -> 0x00cd, RemoteException -> 0x00be, InterruptedException -> 0x00e0 }
            L_0x003d:
                r0 = 0
                com.loc.d r1 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                java.lang.Object r2 = r1.j     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                monitor-enter(r2)     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                com.autonavi.aps.amapapi.model.AmapLoc r1 = r1.l     // Catch:{ all -> 0x0117 }
                if (r1 == 0) goto L_0x005b
                com.loc.d r1 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                com.autonavi.aps.amapapi.model.AmapLoc r1 = r1.l     // Catch:{ all -> 0x0117 }
                int r1 = r1.a()     // Catch:{ all -> 0x0117 }
                if (r1 != 0) goto L_0x005b
                com.loc.d r1 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                long r4 = com.loc.bw.b()     // Catch:{ all -> 0x0117 }
                r1.m = r4     // Catch:{ all -> 0x0117 }
            L_0x005b:
                com.loc.d r1 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                r3 = 0
                r1.i = r3     // Catch:{ all -> 0x0117 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                java.util.Vector<android.os.Messenger> r1 = r1.g     // Catch:{ all -> 0x0117 }
                if (r1 == 0) goto L_0x00f6
                com.loc.d r1 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                java.util.Vector<android.os.Messenger> r1 = r1.g     // Catch:{ all -> 0x0117 }
                int r1 = r1.size()     // Catch:{ all -> 0x0117 }
                if (r1 <= 0) goto L_0x00f6
                com.loc.d r1 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                java.util.Vector<android.os.Messenger> r1 = r1.g     // Catch:{ all -> 0x0117 }
                int r1 = r1.size()     // Catch:{ all -> 0x0117 }
            L_0x0078:
                if (r6 >= r1) goto L_0x00f6
                android.os.Message r3 = android.os.Message.obtain()     // Catch:{ all -> 0x0117 }
                android.os.Bundle r0 = new android.os.Bundle     // Catch:{ all -> 0x0117 }
                r0.<init>()     // Catch:{ all -> 0x0117 }
                java.lang.String r4 = "location"
                com.loc.d r5 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                com.autonavi.aps.amapapi.model.AmapLoc r5 = r5.l     // Catch:{ all -> 0x0117 }
                r0.putParcelable(r4, r5)     // Catch:{ all -> 0x0117 }
                r3.setData(r0)     // Catch:{ all -> 0x0117 }
                r0 = 1
                r3.what = r0     // Catch:{ all -> 0x0117 }
                com.loc.d r0 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                java.util.Vector<android.os.Messenger> r0 = r0.g     // Catch:{ all -> 0x0117 }
                r4 = 0
                java.lang.Object r0 = r0.get(r4)     // Catch:{ all -> 0x0117 }
                android.os.Messenger r0 = (android.os.Messenger) r0     // Catch:{ all -> 0x0117 }
                r0.send(r3)     // Catch:{ all -> 0x0117 }
                com.loc.d r3 = com.loc.d.this     // Catch:{ all -> 0x0117 }
                java.util.Vector<android.os.Messenger> r3 = r3.g     // Catch:{ all -> 0x0117 }
                r4 = 0
                r3.remove(r4)     // Catch:{ all -> 0x0117 }
                int r1 = r1 + -1
                goto L_0x0078
            L_0x00ab:
                r0 = move-exception
                com.loc.d r1 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                com.loc.d r2 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                r3 = 9
                java.lang.String r0 = r0.getMessage()     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                com.autonavi.aps.amapapi.model.AmapLoc r0 = r2.a((int) r3, (java.lang.String) r0)     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                r1.l = r0     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                goto L_0x0006
            L_0x00be:
                r0 = move-exception
                com.loc.d r0 = com.loc.d.this
                boolean r0 = r0.c()
                if (r0 != 0) goto L_0x00cc
                com.loc.d r0 = com.loc.d.this
                r0.h()
            L_0x00cc:
                return
            L_0x00cd:
                r0 = move-exception
                com.loc.d r1 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                com.loc.d r2 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                r3 = 8
                java.lang.String r0 = r0.getMessage()     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                com.autonavi.aps.amapapi.model.AmapLoc r0 = r2.a((int) r3, (java.lang.String) r0)     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                r1.l = r0     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                goto L_0x003d
            L_0x00e0:
                r0 = move-exception
                java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x011a }
                r0.interrupt()     // Catch:{ all -> 0x011a }
                com.loc.d r0 = com.loc.d.this
                boolean r0 = r0.c()
                if (r0 != 0) goto L_0x00cc
                com.loc.d r0 = com.loc.d.this
                r0.h()
                goto L_0x00cc
            L_0x00f6:
                monitor-exit(r2)     // Catch:{ all -> 0x0117 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                r1.e()     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                r1.d()     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                com.loc.d r1 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                r1.a((android.os.Messenger) r0)     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                goto L_0x0006
            L_0x0108:
                r0 = move-exception
                com.loc.d r0 = com.loc.d.this
                boolean r0 = r0.c()
                if (r0 != 0) goto L_0x00cc
                com.loc.d r0 = com.loc.d.this
                r0.h()
                goto L_0x00cc
            L_0x0117:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0117 }
                throw r0     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
            L_0x011a:
                r0 = move-exception
                com.loc.d r1 = com.loc.d.this
                boolean r1 = r1.c()
                if (r1 != 0) goto L_0x0128
                com.loc.d r1 = com.loc.d.this
                r1.h()
            L_0x0128:
                throw r0
            L_0x0129:
                com.loc.d r0 = com.loc.d.this     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                java.lang.Object r1 = r0.j     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                monitor-enter(r1)     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
                com.loc.d r0 = com.loc.d.this     // Catch:{ all -> 0x0140 }
                boolean r0 = r0.c()     // Catch:{ all -> 0x0140 }
                if (r0 == 0) goto L_0x013d
                com.loc.d r0 = com.loc.d.this     // Catch:{ all -> 0x0140 }
                java.lang.Object r0 = r0.j     // Catch:{ all -> 0x0140 }
                r0.wait()     // Catch:{ all -> 0x0140 }
            L_0x013d:
                monitor-exit(r1)     // Catch:{ all -> 0x0140 }
                goto L_0x0006
            L_0x0140:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0140 }
                throw r0     // Catch:{ RemoteException -> 0x00be, InterruptedException -> 0x00e0, Throwable -> 0x0108 }
            L_0x0143:
                com.loc.d r0 = com.loc.d.this
                boolean r0 = r0.c()
                if (r0 != 0) goto L_0x00cc
                com.loc.d r0 = com.loc.d.this
                r0.h()
                goto L_0x00cc
            */
            throw new UnsupportedOperationException("Method not decompiled: com.loc.d.a.run():void");
        }
    }

    /* access modifiers changed from: private */
    public AmapLoc a(int i2, String str) {
        try {
            AmapLoc amapLoc = new AmapLoc();
            amapLoc.b(i2);
            amapLoc.b(str);
            return amapLoc;
        } catch (Throwable th) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void a(Messenger messenger) {
        try {
            if (bu.q() && messenger != null) {
                bu.a("0");
                Message obtain = Message.obtain();
                obtain.what = 100;
                messenger.send(obtain);
            }
            if (bu.a()) {
                this.z.a();
            }
            if (bu.d() && !this.x) {
                this.x = true;
                this.d.sendEmptyMessage(4);
            }
            if (bu.f() && !this.y) {
                this.y = true;
                this.d.sendEmptyMessage(5);
            }
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: private */
    public boolean c() {
        boolean z2;
        synchronized (this.j) {
            z2 = this.h;
        }
        return z2;
    }

    public int onStartCommand(Intent intent, int i2, int i3) {
        return 0;
    }

    /* access modifiers changed from: private */
    public AmapLoc a(boolean z2) throws Exception {
        return this.z.a(z2);
    }

    /* access modifiers changed from: private */
    public void d() {
        try {
            this.z.g();
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: private */
    public void e() {
        try {
            if (!this.v) {
                this.v = true;
                this.z.b(this.a);
            }
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        try {
            if (!this.u) {
                g();
            }
        } catch (Throwable th) {
            this.u = false;
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        v vVar = null;
        Looper.prepare();
        this.k = Looper.myLooper();
        e.a(this.a);
        this.z.a(this.a);
        this.z.a("api_serverSDK_130905##S128DF1572465B890OE3F7A13167KLEI##" + m.b(this.a) + "##" + m.e(this.a));
        try {
            vVar = new v.a("loc", "2.3.0", "AMAP_Location_SDK_Android 2.3.0").a(e.b()).a();
        } catch (l e2) {
        }
        try {
            String a2 = o.a(this.a, vVar, (Map<String, String>) null, true);
            try {
                this.n.put("key", m.e(this.a));
                this.n.put("X-INFO", a2);
                this.n.put("User-Agent", "AMAP_Location_SDK_Android 2.3.0");
                this.n.put("netloc", "0");
                this.n.put("gpsstatus", "0");
                this.n.put("nbssid", "0");
                if (!this.n.has("reversegeo")) {
                    this.n.put("reversegeo", true);
                }
                if (!this.n.has("isOffset")) {
                    this.n.put("isOffset", true);
                }
                if (!this.n.has("wait1stwifi")) {
                    this.n.put("wait1stwifi", "0");
                }
                this.n.put("autoup", "0");
                this.n.put("upcolmobile", 1);
                this.n.put("enablegetreq", 1);
                this.n.put("wifiactivescan", 1);
            } catch (JSONException e3) {
            }
            this.z.a(this.n);
            this.u = true;
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: private */
    public void a(JSONObject jSONObject) {
        try {
            if (this.z != null) {
                this.z.a(jSONObject);
            }
        } catch (Throwable th) {
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a() {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.s     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            if (r0 != 0) goto L_0x0014
            com.loc.d$c r0 = new com.loc.d$c     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            r0.<init>()     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            r1.t = r0     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            com.loc.d$c r0 = r1.t     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            r0.start()     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
            r0 = 1
            r1.s = r0     // Catch:{ Throwable -> 0x0019, all -> 0x0016 }
        L_0x0014:
            monitor-exit(r1)
            return
        L_0x0016:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        L_0x0019:
            r0 = move-exception
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.d.a():void");
    }

    /* compiled from: APSServiceCore */
    class c extends Thread {
        c() {
        }

        public void run() {
            try {
                if (!d.this.u) {
                    d.this.g();
                }
                if (!d.this.q) {
                    d.this.q = true;
                    d.this.p = new ServerSocket(43689);
                }
                while (d.this.q) {
                    d.this.r = d.this.p.accept();
                    d.this.a(d.this.r);
                }
            } catch (Throwable th) {
            }
            super.run();
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b() {
        /*
            r1 = this;
            monitor-enter(r1)
            java.net.ServerSocket r0 = r1.p     // Catch:{ IOException -> 0x0026 }
            if (r0 == 0) goto L_0x000a
            java.net.ServerSocket r0 = r1.p     // Catch:{ IOException -> 0x0026 }
            r0.close()     // Catch:{ IOException -> 0x0026 }
        L_0x000a:
            java.net.Socket r0 = r1.r     // Catch:{ IOException -> 0x0026 }
            if (r0 == 0) goto L_0x0013
            java.net.Socket r0 = r1.r     // Catch:{ IOException -> 0x0026 }
            r0.close()     // Catch:{ IOException -> 0x0026 }
        L_0x0013:
            r0 = 0
            r1.p = r0     // Catch:{ Throwable -> 0x0024, all -> 0x0021 }
            r0 = 0
            r1.t = r0     // Catch:{ Throwable -> 0x0024, all -> 0x0021 }
            r0 = 0
            r1.s = r0     // Catch:{ Throwable -> 0x0024, all -> 0x0021 }
            r0 = 0
            r1.q = r0     // Catch:{ Throwable -> 0x0024, all -> 0x0021 }
        L_0x001f:
            monitor-exit(r1)
            return
        L_0x0021:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        L_0x0024:
            r0 = move-exception
            goto L_0x001f
        L_0x0026:
            r0 = move-exception
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.d.b():void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x0332, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x022d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x022d A[ExcHandler: all (th java.lang.Throwable), PHI: r3 
      PHI: (r3v9 java.lang.String) = (r3v0 java.lang.String), (r3v0 java.lang.String), (r3v11 java.lang.String), (r3v0 java.lang.String), (r3v0 java.lang.String), (r3v0 java.lang.String), (r3v17 java.lang.String) binds: [B:7:0x001d, B:41:0x0097, B:91:0x0272, B:79:0x022a, B:67:0x01b7, B:68:?, B:52:0x0112] A[DONT_GENERATE, DONT_INLINE], Splitter:B:7:0x001d] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:100:0x02f5=Splitter:B:100:0x02f5, B:109:0x0309=Splitter:B:109:0x0309, B:87:0x026b=Splitter:B:87:0x026b, B:118:0x031d=Splitter:B:118:0x031d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.net.Socket r12) {
        /*
            r11 = this;
            r3 = 0
            r4 = 0
            r9 = 1
            if (r12 != 0) goto L_0x0006
        L_0x0005:
            return
        L_0x0006:
            r1 = 30000(0x7530, float:4.2039E-41)
            java.lang.String r0 = "jsonp1"
            java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x026c }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x032d, all -> 0x0323 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x032d, all -> 0x0323 }
            java.io.InputStream r6 = r12.getInputStream()     // Catch:{ Throwable -> 0x032d, all -> 0x0323 }
            java.lang.String r7 = "UTF-8"
            r5.<init>(r6, r7)     // Catch:{ Throwable -> 0x032d, all -> 0x0323 }
            r2.<init>(r5)     // Catch:{ Throwable -> 0x032d, all -> 0x0323 }
            java.lang.String r5 = r2.readLine()     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r5 == 0) goto L_0x0094
            int r6 = r5.length()     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r6 <= 0) goto L_0x0094
            java.lang.String r6 = " "
            java.lang.String[] r5 = r5.split(r6)     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r5 == 0) goto L_0x0094
            int r6 = r5.length     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r6 <= r9) goto L_0x0094
            r6 = 1
            r5 = r5[r6]     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            java.lang.String r6 = "\\?"
            java.lang.String[] r5 = r5.split(r6)     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r5 == 0) goto L_0x0094
            int r6 = r5.length     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r6 <= r9) goto L_0x0094
            r6 = 1
            r5 = r5[r6]     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            java.lang.String r6 = "&"
            java.lang.String[] r5 = r5.split(r6)     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r5 == 0) goto L_0x0094
            int r6 = r5.length     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r6 <= 0) goto L_0x0094
        L_0x0050:
            int r6 = r5.length     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r4 >= r6) goto L_0x0094
            r6 = r5[r4]     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            java.lang.String r7 = "="
            java.lang.String[] r6 = r6.split(r7)     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r6 == 0) goto L_0x0091
            int r7 = r6.length     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r7 <= r9) goto L_0x0091
            java.lang.String r7 = "to"
            r8 = 0
            r8 = r6[r8]     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            boolean r7 = r7.equals(r8)     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r7 == 0) goto L_0x0072
            r1 = 1
            r1 = r6[r1]     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
        L_0x0072:
            java.lang.String r7 = "callback"
            r8 = 0
            r8 = r6[r8]     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            boolean r7 = r7.equals(r8)     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r7 == 0) goto L_0x0080
            r7 = 1
            r0 = r6[r7]     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
        L_0x0080:
            java.lang.String r7 = "_"
            r8 = 0
            r8 = r6[r8]     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            boolean r7 = r7.equals(r8)     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            if (r7 == 0) goto L_0x0091
            r7 = 1
            r6 = r6[r7]     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
            java.lang.Long.parseLong(r6)     // Catch:{ Throwable -> 0x0331, all -> 0x022d }
        L_0x0091:
            int r4 = r4 + 1
            goto L_0x0050
        L_0x0094:
            r10 = r0
            r0 = r1
            r1 = r10
            int r4 = com.loc.e.j     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            com.loc.e.j = r0     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            com.autonavi.aps.amapapi.model.AmapLoc r0 = r11.o     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            if (r0 == 0) goto L_0x00b0
            com.autonavi.aps.amapapi.model.AmapLoc r0 = r11.o     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            long r8 = r0.k()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            long r6 = r6 - r8
            r8 = 5000(0x1388, double:2.4703E-320)
            int r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r0 <= 0) goto L_0x0114
        L_0x00b0:
            android.content.Context r0 = r11.a     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            boolean r0 = com.loc.bw.d((android.content.Context) r0)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            if (r0 != 0) goto L_0x0114
            com.loc.aw r0 = r11.z     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            r5 = 0
            com.autonavi.aps.amapapi.model.AmapLoc r0 = r0.a((boolean) r5)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            r11.o = r0     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            com.autonavi.aps.amapapi.model.AmapLoc r0 = r11.o     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            int r0 = r0.a()     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            if (r0 == 0) goto L_0x0112
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            r0.<init>()     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.String r5 = "&&"
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.String r5 = "({'package':'"
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.String r5 = r11.c     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.String r5 = "','error_code':"
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            com.autonavi.aps.amapapi.model.AmapLoc r5 = r11.o     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            int r5 = r5.a()     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.String r5 = ",'error':'"
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            com.autonavi.aps.amapapi.model.AmapLoc r5 = r11.o     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.String r5 = r5.c()     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.String r5 = "'})"
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
            java.lang.String r3 = r0.toString()     // Catch:{ Exception -> 0x01b6, all -> 0x0229 }
        L_0x0112:
            com.loc.e.j = r4     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
        L_0x0114:
            if (r3 != 0) goto L_0x0174
            com.autonavi.aps.amapapi.model.AmapLoc r0 = r11.o     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            if (r0 != 0) goto L_0x0272
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            r0.<init>()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "&&"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "({'package':'"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = r11.c     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "','error_code':8,'error':'unknown error'})"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r3 = r0.toString()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
        L_0x0143:
            android.content.Context r0 = r11.a     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            boolean r0 = com.loc.bw.d((android.content.Context) r0)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            if (r0 == 0) goto L_0x0174
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            r0.<init>()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "&&"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "({'package':'"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = r11.c     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "','error_code':36,'error':'app is background'})"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r3 = r0.toString()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
        L_0x0174:
            java.io.PrintStream r0 = new java.io.PrintStream     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            java.io.OutputStream r1 = r12.getOutputStream()     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            r4 = 1
            java.lang.String r5 = "UTF-8"
            r0.<init>(r1, r4, r5)     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            java.lang.String r1 = "HTTP/1.0 200 OK"
            r0.println(r1)     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            r1.<init>()     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            java.lang.String r4 = "Content-Length:"
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            java.lang.String r4 = "UTF-8"
            byte[] r4 = r3.getBytes(r4)     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            int r4 = r4.length     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            r0.println(r1)     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            r0.println()     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            r0.println(r3)     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            r0.close()     // Catch:{ Exception -> 0x02e2, all -> 0x02ee }
            r2.close()     // Catch:{ Exception -> 0x01b3 }
            r12.close()     // Catch:{ Exception -> 0x01b3 }
            goto L_0x0005
        L_0x01b3:
            r0 = move-exception
            goto L_0x0005
        L_0x01b6:
            r0 = move-exception
            com.loc.e.j = r4     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            goto L_0x0114
        L_0x01bb:
            r0 = move-exception
            r0 = r1
            r1 = r2
        L_0x01be:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0327 }
            r2.<init>()     // Catch:{ all -> 0x0327 }
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ all -> 0x0327 }
            java.lang.String r4 = "&&"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x0327 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0327 }
            java.lang.String r2 = "({'package':'"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0327 }
            java.lang.String r2 = r11.c     // Catch:{ all -> 0x0327 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0327 }
            java.lang.String r2 = "','error_code':1,'error':'params error'})"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0327 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0327 }
            java.io.PrintStream r2 = new java.io.PrintStream     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            java.io.OutputStream r3 = r12.getOutputStream()     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            r4 = 1
            java.lang.String r5 = "UTF-8"
            r2.<init>(r3, r4, r5)     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            java.lang.String r3 = "HTTP/1.0 200 OK"
            r2.println(r3)     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            r3.<init>()     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            java.lang.String r4 = "Content-Length:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            java.lang.String r4 = "UTF-8"
            byte[] r4 = r0.getBytes(r4)     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            int r4 = r4.length     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            r2.println(r3)     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            r2.println()     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            r2.println(r0)     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            r2.close()     // Catch:{ Exception -> 0x02f6, all -> 0x0302 }
            r1.close()     // Catch:{ Exception -> 0x0226 }
            r12.close()     // Catch:{ Exception -> 0x0226 }
            goto L_0x0005
        L_0x0226:
            r0 = move-exception
            goto L_0x0005
        L_0x0229:
            r0 = move-exception
            com.loc.e.j = r4     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            throw r0     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
        L_0x022d:
            r0 = move-exception
        L_0x022e:
            java.io.PrintStream r1 = new java.io.PrintStream     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            java.io.OutputStream r4 = r12.getOutputStream()     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            r5 = 1
            java.lang.String r6 = "UTF-8"
            r1.<init>(r4, r5, r6)     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            java.lang.String r4 = "HTTP/1.0 200 OK"
            r1.println(r4)     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            r4.<init>()     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            java.lang.String r5 = "Content-Length:"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            java.lang.String r5 = "UTF-8"
            byte[] r5 = r3.getBytes(r5)     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            int r5 = r5.length     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            r1.println(r4)     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            r1.println()     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            r1.println(r3)     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            r1.close()     // Catch:{ Exception -> 0x030a, all -> 0x0316 }
            r2.close()     // Catch:{ Exception -> 0x0320 }
            r12.close()     // Catch:{ Exception -> 0x0320 }
        L_0x026b:
            throw r0     // Catch:{ Throwable -> 0x026c }
        L_0x026c:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0005
        L_0x0272:
            com.autonavi.aps.amapapi.model.AmapLoc r0 = r11.o     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            r4.<init>()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r5 = "&&"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r5 = "({'package':'"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r5 = r11.c     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r5 = "','error_code':0,'error':'','location':{'y':"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            double r6 = r0.i()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r5 = ",'precision':"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            float r5 = r0.j()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r5 = ",'x':"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            double r6 = r0.h()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.StringBuilder r0 = r4.append(r6)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "},'version_code':'"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "2.3.0"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "','version':'"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "2.3.0"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r4 = "'})"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            java.lang.String r3 = r0.toString()     // Catch:{ Throwable -> 0x01bb, all -> 0x022d }
            goto L_0x0143
        L_0x02e2:
            r0 = move-exception
            r2.close()     // Catch:{ Exception -> 0x02eb }
            r12.close()     // Catch:{ Exception -> 0x02eb }
            goto L_0x0005
        L_0x02eb:
            r0 = move-exception
            goto L_0x0005
        L_0x02ee:
            r0 = move-exception
            r2.close()     // Catch:{ Exception -> 0x0335 }
            r12.close()     // Catch:{ Exception -> 0x0335 }
        L_0x02f5:
            throw r0     // Catch:{ Throwable -> 0x026c }
        L_0x02f6:
            r0 = move-exception
            r1.close()     // Catch:{ Exception -> 0x02ff }
            r12.close()     // Catch:{ Exception -> 0x02ff }
            goto L_0x0005
        L_0x02ff:
            r0 = move-exception
            goto L_0x0005
        L_0x0302:
            r0 = move-exception
            r1.close()     // Catch:{ Exception -> 0x032b }
            r12.close()     // Catch:{ Exception -> 0x032b }
        L_0x0309:
            throw r0     // Catch:{ Throwable -> 0x026c }
        L_0x030a:
            r1 = move-exception
            r2.close()     // Catch:{ Exception -> 0x0313 }
            r12.close()     // Catch:{ Exception -> 0x0313 }
            goto L_0x026b
        L_0x0313:
            r1 = move-exception
            goto L_0x026b
        L_0x0316:
            r0 = move-exception
            r2.close()     // Catch:{ Exception -> 0x031e }
            r12.close()     // Catch:{ Exception -> 0x031e }
        L_0x031d:
            throw r0     // Catch:{ Throwable -> 0x026c }
        L_0x031e:
            r1 = move-exception
            goto L_0x031d
        L_0x0320:
            r1 = move-exception
            goto L_0x026b
        L_0x0323:
            r0 = move-exception
            r2 = r3
            goto L_0x022e
        L_0x0327:
            r0 = move-exception
            r2 = r1
            goto L_0x022e
        L_0x032b:
            r1 = move-exception
            goto L_0x0309
        L_0x032d:
            r1 = move-exception
            r1 = r3
            goto L_0x01be
        L_0x0331:
            r1 = move-exception
            r1 = r2
            goto L_0x01be
        L_0x0335:
            r1 = move-exception
            goto L_0x02f5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.d.a(java.net.Socket):void");
    }

    /* access modifiers changed from: private */
    public void h() {
        try {
            b();
            this.u = false;
            this.v = false;
            this.x = false;
            this.y = false;
            this.w = 0;
            this.z.b();
            this.g.clear();
            if (this.b) {
                Process.killProcess(Process.myPid());
            }
            if (this.d != null) {
                this.d.removeCallbacksAndMessages((Object) null);
            }
        } catch (Throwable th) {
        }
    }

    public void onDestroy() {
        try {
            synchronized (this.j) {
                this.h = false;
                this.j.notify();
            }
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: private */
    public void i() {
        try {
            if (this.z != null) {
                this.z.h();
            }
        } catch (Throwable th) {
        }
    }
}
