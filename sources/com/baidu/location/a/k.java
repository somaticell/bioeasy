package com.baidu.location.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.platform.comapi.location.CoordinateType;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class k {
    /* access modifiers changed from: private */
    public static long j = 12000;
    public e a;
    private Context b;
    /* access modifiers changed from: private */
    public WebView c;
    /* access modifiers changed from: private */
    public LocationClient d;
    /* access modifiers changed from: private */
    public a e;
    /* access modifiers changed from: private */
    public List<b> f;
    /* access modifiers changed from: private */
    public boolean g;
    /* access modifiers changed from: private */
    public long h;
    /* access modifiers changed from: private */
    public BDLocation i;
    /* access modifiers changed from: private */
    public f k;
    /* access modifiers changed from: private */
    public boolean l;

    private class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        private String a(BDLocation bDLocation) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("latitude", bDLocation.getLatitude());
                jSONObject.put("longitude", bDLocation.getLongitude());
                jSONObject.put("radius", (double) bDLocation.getRadius());
                jSONObject.put("errorcode", 1);
                if (bDLocation.hasAltitude()) {
                    jSONObject.put("altitude", bDLocation.getAltitude());
                }
                if (bDLocation.hasSpeed()) {
                    jSONObject.put("speed", (double) (bDLocation.getSpeed() / 3.6f));
                }
                if (bDLocation.getLocType() == 61) {
                    jSONObject.put("direction", (double) bDLocation.getDirection());
                }
                if (bDLocation.getBuildingName() != null) {
                    jSONObject.put("buildingname", bDLocation.getBuildingName());
                }
                if (bDLocation.getBuildingID() != null) {
                    jSONObject.put("buildingid", bDLocation.getBuildingID());
                }
                if (bDLocation.getFloor() != null) {
                    jSONObject.put("floor", bDLocation.getFloor());
                }
                return jSONObject.toString();
            } catch (Exception e) {
                return null;
            }
        }

        private void a(String str) {
            if (k.this.l) {
                k.this.e.removeCallbacks(k.this.k);
                boolean unused = k.this.l = false;
            }
            if (k.this.f != null && k.this.f.size() > 0) {
                Iterator it = k.this.f.iterator();
                while (it.hasNext()) {
                    try {
                        b bVar = (b) it.next();
                        if (bVar.b() != null) {
                            k.this.c.loadUrl("javascript:" + bVar.b() + "('" + str + "')");
                        }
                        it.remove();
                    } catch (Exception e) {
                        return;
                    }
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0064  */
        /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r10) {
            /*
                r9 = this;
                r8 = 2
                r3 = 1
                r2 = 0
                r1 = 0
                int r0 = r10.what
                switch(r0) {
                    case 1: goto L_0x000a;
                    case 2: goto L_0x00ae;
                    case 3: goto L_0x00ea;
                    case 4: goto L_0x0115;
                    case 5: goto L_0x00bb;
                    case 6: goto L_0x00d1;
                    default: goto L_0x0009;
                }
            L_0x0009:
                return
            L_0x000a:
                java.lang.Object r0 = r10.obj
                com.baidu.location.a.k$b r0 = (com.baidu.location.a.k.b) r0
                com.baidu.location.a.k r4 = com.baidu.location.a.k.this
                java.util.List r4 = r4.f
                if (r4 == 0) goto L_0x001f
                com.baidu.location.a.k r4 = com.baidu.location.a.k.this
                java.util.List r4 = r4.f
                r4.add(r0)
            L_0x001f:
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.LocationClient r0 = r0.d
                if (r0 == 0) goto L_0x0009
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.LocationClient r0 = r0.d
                int r0 = r0.requestLocation()
                if (r0 == 0) goto L_0x016d
                long r4 = java.lang.System.currentTimeMillis()
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                long r6 = r0.h
                long r4 = r4 - r6
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.BDLocation r0 = r0.i
                if (r0 == 0) goto L_0x016d
                r6 = 10000(0x2710, double:4.9407E-320)
                int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r0 > 0) goto L_0x016d
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.a.k$a r0 = r0.e
                android.os.Message r0 = r0.obtainMessage(r8)
                com.baidu.location.a.k r4 = com.baidu.location.a.k.this
                com.baidu.location.BDLocation r4 = r4.i
                r0.obj = r4
                r0.sendToTarget()
                r0 = r2
            L_0x0062:
                if (r0 == 0) goto L_0x0009
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                boolean r0 = r0.l
                if (r0 == 0) goto L_0x0080
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.a.k$a r0 = r0.e
                com.baidu.location.a.k r4 = com.baidu.location.a.k.this
                com.baidu.location.a.k$f r4 = r4.k
                r0.removeCallbacks(r4)
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                boolean unused = r0.l = r2
            L_0x0080:
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.a.k$f r0 = r0.k
                if (r0 != 0) goto L_0x0094
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.a.k$f r2 = new com.baidu.location.a.k$f
                com.baidu.location.a.k r4 = com.baidu.location.a.k.this
                r2.<init>()
                com.baidu.location.a.k.f unused = r0.k = r2
            L_0x0094:
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.a.k$a r0 = r0.e
                com.baidu.location.a.k r1 = com.baidu.location.a.k.this
                com.baidu.location.a.k$f r1 = r1.k
                long r4 = com.baidu.location.a.k.j
                r0.postDelayed(r1, r4)
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                boolean unused = r0.l = r3
                goto L_0x0009
            L_0x00ae:
                java.lang.Object r0 = r10.obj
                com.baidu.location.BDLocation r0 = (com.baidu.location.BDLocation) r0
                java.lang.String r0 = r9.a((com.baidu.location.BDLocation) r0)
                r9.a((java.lang.String) r0)
                goto L_0x0009
            L_0x00bb:
                org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x016a }
                r0.<init>()     // Catch:{ Exception -> 0x016a }
                java.lang.String r2 = "errorcode"
                r3 = 0
                r0.put(r2, r3)     // Catch:{ Exception -> 0x016a }
                java.lang.String r1 = r0.toString()     // Catch:{ Exception -> 0x016a }
            L_0x00ca:
                if (r1 == 0) goto L_0x0009
                r9.a((java.lang.String) r1)
                goto L_0x0009
            L_0x00d1:
                org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x00e7 }
                r0.<init>()     // Catch:{ Exception -> 0x00e7 }
                java.lang.String r2 = "errorcode"
                r3 = 2
                r0.put(r2, r3)     // Catch:{ Exception -> 0x00e7 }
                java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00e7 }
            L_0x00e0:
                if (r0 == 0) goto L_0x0009
                r9.a((java.lang.String) r0)
                goto L_0x0009
            L_0x00e7:
                r0 = move-exception
                r0 = r1
                goto L_0x00e0
            L_0x00ea:
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                java.util.List r0 = r0.f
                if (r0 != 0) goto L_0x010b
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                java.util.List unused = r0.f = r1
            L_0x00fc:
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.LocationClient r0 = r0.d
                com.baidu.location.a.k r1 = com.baidu.location.a.k.this
                com.baidu.location.a.k$e r1 = r1.a
                r0.registerLocationListener((com.baidu.location.BDAbstractLocationListener) r1)
                goto L_0x0009
            L_0x010b:
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                java.util.List r0 = r0.f
                r0.clear()
                goto L_0x00fc
            L_0x0115:
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                java.util.List r0 = r0.f
                if (r0 == 0) goto L_0x012b
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                java.util.List r0 = r0.f
                r0.clear()
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                java.util.List unused = r0.f = r1
            L_0x012b:
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.LocationClient r0 = r0.d
                com.baidu.location.a.k r3 = com.baidu.location.a.k.this
                com.baidu.location.a.k$e r3 = r3.a
                r0.unRegisterLocationListener((com.baidu.location.BDAbstractLocationListener) r3)
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                r4 = 0
                long unused = r0.h = r4
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.BDLocation unused = r0.i = r1
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.a.k$f r0 = r0.k
                if (r0 == 0) goto L_0x0163
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                boolean r0 = r0.l
                if (r0 == 0) goto L_0x0163
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                com.baidu.location.a.k$a r0 = r0.e
                com.baidu.location.a.k r1 = com.baidu.location.a.k.this
                com.baidu.location.a.k$f r1 = r1.k
                r0.removeCallbacks(r1)
            L_0x0163:
                com.baidu.location.a.k r0 = com.baidu.location.a.k.this
                boolean unused = r0.l = r2
                goto L_0x0009
            L_0x016a:
                r0 = move-exception
                goto L_0x00ca
            L_0x016d:
                r0 = r3
                goto L_0x0062
            */
            throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.k.a.handleMessage(android.os.Message):void");
        }
    }

    private class b {
        private String b = null;
        private String c = null;
        private long d = 0;

        b(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject != null && jSONObject.has(com.alipay.sdk.packet.d.o)) {
                    this.b = jSONObject.getString(com.alipay.sdk.packet.d.o);
                }
                if (jSONObject != null && jSONObject.has(com.alipay.sdk.authjs.a.c)) {
                    this.c = jSONObject.getString(com.alipay.sdk.authjs.a.c);
                }
                if (jSONObject != null && jSONObject.has(com.alipay.sdk.data.a.f)) {
                    long j = jSONObject.getLong(com.alipay.sdk.data.a.f);
                    if (j >= 1000) {
                        long unused = k.j = j;
                    }
                }
                this.d = System.currentTimeMillis();
            } catch (Exception e) {
                this.b = null;
                this.c = null;
            }
        }

        public String a() {
            return this.b;
        }

        public String b() {
            return this.c;
        }
    }

    private static final class c {
        /* access modifiers changed from: private */
        public static final k a = new k();
    }

    private class d {
        private d() {
        }

        @JavascriptInterface
        public void sendMessage(String str) {
            if (str != null && k.this.g) {
                b bVar = new b(str);
                if (bVar.a() != null && bVar.a().equals("requestLoc") && k.this.e != null) {
                    Message obtainMessage = k.this.e.obtainMessage(1);
                    obtainMessage.obj = bVar;
                    obtainMessage.sendToTarget();
                }
            }
        }

        @JavascriptInterface
        public void showLog(String str) {
        }
    }

    public class e extends BDAbstractLocationListener {
        public e() {
        }

        public void onReceiveLocation(BDLocation bDLocation) {
            if (k.this.g && bDLocation != null) {
                BDLocation bDLocation2 = new BDLocation(bDLocation);
                int locType = bDLocation2.getLocType();
                String coorType = bDLocation2.getCoorType();
                if (locType == 61 || locType == 161 || locType == 66) {
                    if (coorType != null) {
                        if (coorType.equals(CoordinateType.GCJ02)) {
                            bDLocation2 = LocationClient.getBDLocationInCoorType(bDLocation2, "gcj2wgs");
                        } else if (coorType.equals(BDLocation.BDLOCATION_GCJ02_TO_BD09)) {
                            bDLocation2 = LocationClient.getBDLocationInCoorType(LocationClient.getBDLocationInCoorType(bDLocation2, BDLocation.BDLOCATION_BD09_TO_GCJ02), "gcj2wgs");
                        } else if (coorType.equals("bd09ll")) {
                            bDLocation2 = LocationClient.getBDLocationInCoorType(LocationClient.getBDLocationInCoorType(bDLocation2, BDLocation.BDLOCATION_BD09LL_TO_GCJ02), "gcj2wgs");
                        }
                    }
                    long unused = k.this.h = System.currentTimeMillis();
                    BDLocation unused2 = k.this.i = new BDLocation(bDLocation2);
                    Message obtainMessage = k.this.e.obtainMessage(2);
                    obtainMessage.obj = bDLocation2;
                    obtainMessage.sendToTarget();
                    return;
                }
                k.this.e.obtainMessage(5).sendToTarget();
            }
        }
    }

    private class f implements Runnable {
        private f() {
        }

        public void run() {
            boolean unused = k.this.l = false;
            k.this.e.obtainMessage(6).sendToTarget();
        }
    }

    private k() {
        this.b = null;
        this.d = null;
        this.a = new e();
        this.e = null;
        this.f = null;
        this.g = false;
        this.h = 0;
        this.i = null;
        this.k = null;
        this.l = false;
    }

    public static k a() {
        return c.a;
    }

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    private void a(WebView webView) {
        webView.addJavascriptInterface(new d(), "BaiduLocAssistant");
    }

    public void a(Context context, WebView webView, LocationClient locationClient) {
        if (!this.g && Integer.valueOf(Build.VERSION.SDK_INT).intValue() >= 17) {
            this.b = context;
            this.c = webView;
            this.d = locationClient;
            this.e = new a(Looper.getMainLooper());
            this.e.obtainMessage(3).sendToTarget();
            webView.getSettings().setJavaScriptEnabled(true);
            a(this.c);
            this.g = true;
        }
    }

    public void b() {
        if (this.g) {
            this.e.obtainMessage(4).sendToTarget();
            this.g = false;
        }
    }
}
