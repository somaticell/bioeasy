package com.baidu.location.d;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import com.baidu.location.f;
import java.util.List;

public class h {
    public static long a = 0;
    private static h b = null;
    private WifiManager c = null;
    private a d = null;
    private g e = null;
    private long f = 0;
    private long g = 0;
    private boolean h = false;
    /* access modifiers changed from: private */
    public Handler i = new Handler();
    private long j = 0;
    private long k = 0;

    private class a extends BroadcastReceiver {
        private long b;
        private boolean c;

        private a() {
            this.b = 0;
            this.c = false;
        }

        public void onReceive(Context context, Intent intent) {
            if (context != null) {
                String action = intent.getAction();
                if (action.equals("android.net.wifi.SCAN_RESULTS")) {
                    h.a = System.currentTimeMillis() / 1000;
                    h.this.i.post(new i(this));
                } else if (action.equals("android.net.wifi.STATE_CHANGE") && ((NetworkInfo) intent.getParcelableExtra("networkInfo")).getState().equals(NetworkInfo.State.CONNECTED) && System.currentTimeMillis() - this.b >= 5000) {
                    this.b = System.currentTimeMillis();
                    if (!this.c) {
                        this.c = true;
                    }
                }
            }
        }
    }

    private h() {
    }

    public static synchronized h a() {
        h hVar;
        synchronized (h.class) {
            if (b == null) {
                b = new h();
            }
            hVar = b;
        }
        return hVar;
    }

    private String a(long j2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf((int) (j2 & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j2 >> 8) & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j2 >> 16) & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j2 >> 24) & 255)));
        return stringBuffer.toString();
    }

    public static boolean a(g gVar, g gVar2) {
        boolean a2 = a(gVar, gVar2, 0.7f);
        long currentTimeMillis = System.currentTimeMillis() - com.baidu.location.a.a.c;
        if (currentTimeMillis <= 0 || currentTimeMillis >= 30000 || !a2 || gVar2.f() - gVar.f() <= 30) {
            return a2;
        }
        return false;
    }

    public static boolean a(g gVar, g gVar2, float f2) {
        int i2;
        if (gVar == null || gVar2 == null) {
            return false;
        }
        List<ScanResult> list = gVar.a;
        List<ScanResult> list2 = gVar2.a;
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null) {
            return false;
        }
        int size = list.size();
        int size2 = list2.size();
        if (size == 0 && size2 == 0) {
            return true;
        }
        if (size == 0 || size2 == 0) {
            return false;
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 < size) {
            String str = list.get(i3).BSSID;
            if (str != null) {
                int i5 = 0;
                while (true) {
                    if (i5 >= size2) {
                        i2 = i4;
                        break;
                    } else if (str.equals(list2.get(i5).BSSID)) {
                        i2 = i4 + 1;
                        break;
                    } else {
                        i5++;
                    }
                }
            } else {
                i2 = i4;
            }
            i3++;
            i4 = i2;
        }
        return ((float) i4) >= ((float) size) * f2;
    }

    public static boolean j() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) f.getServiceContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
        } catch (Exception e2) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void t() {
        if (this.c != null) {
            try {
                List<ScanResult> scanResults = this.c.getScanResults();
                if (scanResults != null) {
                    g gVar = new g(scanResults, System.currentTimeMillis());
                    if (this.e == null || !gVar.a(this.e)) {
                        this.e = gVar;
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    public void b() {
        this.j = 0;
    }

    public synchronized void c() {
        if (!this.h) {
            if (f.isServing) {
                this.c = (WifiManager) f.getServiceContext().getApplicationContext().getSystemService("wifi");
                this.d = new a();
                try {
                    f.getServiceContext().registerReceiver(this.d, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
                } catch (Exception e2) {
                }
                this.h = true;
            }
        }
    }

    public List<WifiConfiguration> d() {
        try {
            if (this.c != null) {
                return this.c.getConfiguredNetworks();
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public synchronized void e() {
        if (this.h) {
            try {
                f.getServiceContext().unregisterReceiver(this.d);
                a = 0;
            } catch (Exception e2) {
            }
            this.d = null;
            this.c = null;
            this.h = false;
        }
    }

    public boolean f() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.g > 0 && currentTimeMillis - this.g <= 5000) {
            return false;
        }
        this.g = currentTimeMillis;
        b();
        return g();
    }

    public boolean g() {
        if (this.c == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f > 0) {
            if (currentTimeMillis - this.f <= this.j + 5000 || currentTimeMillis - (a * 1000) <= this.j + 5000) {
                return false;
            }
            if (j() && currentTimeMillis - this.f <= 10000 + this.j) {
                return false;
            }
        }
        return i();
    }

    @SuppressLint({"NewApi"})
    public String h() {
        if (this.c == null) {
            return "";
        }
        try {
            return (this.c.isWifiEnabled() || (Build.VERSION.SDK_INT > 17 && this.c.isScanAlwaysAvailable())) ? "&wifio=1" : "";
        } catch (Exception | NoSuchMethodError e2) {
            return "";
        }
    }

    @SuppressLint({"NewApi"})
    public boolean i() {
        long currentTimeMillis = System.currentTimeMillis() - this.k;
        if (currentTimeMillis >= 0 && currentTimeMillis <= 2000) {
            return false;
        }
        this.k = System.currentTimeMillis();
        try {
            if (!this.c.isWifiEnabled() && (Build.VERSION.SDK_INT <= 17 || !this.c.isScanAlwaysAvailable())) {
                return false;
            }
            this.c.startScan();
            this.f = System.currentTimeMillis();
            return true;
        } catch (Exception | NoSuchMethodError e2) {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        r1 = new com.baidu.location.d.g(r6.c.getScanResults(), 0);
     */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean k() {
        /*
            r6 = this;
            r0 = 0
            android.net.wifi.WifiManager r1 = r6.c     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            boolean r1 = r1.isWifiEnabled()     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            if (r1 != 0) goto L_0x0017
            int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            r2 = 17
            if (r1 <= r2) goto L_0x001d
            android.net.wifi.WifiManager r1 = r6.c     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            boolean r1 = r1.isScanAlwaysAvailable()     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            if (r1 == 0) goto L_0x001d
        L_0x0017:
            boolean r1 = j()     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            if (r1 == 0) goto L_0x001e
        L_0x001d:
            return r0
        L_0x001e:
            com.baidu.location.d.g r1 = new com.baidu.location.d.g     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            android.net.wifi.WifiManager r2 = r6.c     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            java.util.List r2 = r2.getScanResults()     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            r4 = 0
            r1.<init>(r2, r4)     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            if (r1 == 0) goto L_0x001d
            boolean r1 = r1.e()     // Catch:{ NoSuchMethodError -> 0x0037, Exception -> 0x0035 }
            if (r1 == 0) goto L_0x001d
            r0 = 1
            goto L_0x001d
        L_0x0035:
            r1 = move-exception
            goto L_0x001d
        L_0x0037:
            r1 = move-exception
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.d.h.k():boolean");
    }

    public WifiInfo l() {
        if (this.c == null) {
            return null;
        }
        try {
            WifiInfo connectionInfo = this.c.getConnectionInfo();
            if (connectionInfo == null || connectionInfo.getBSSID() == null || connectionInfo.getRssi() <= -100) {
                return null;
            }
            String bssid = connectionInfo.getBSSID();
            if (bssid != null) {
                String replace = bssid.replace(":", "");
                if ("000000000000".equals(replace) || "".equals(replace)) {
                    return null;
                }
            }
            return connectionInfo;
        } catch (Error | Exception e2) {
            return null;
        }
    }

    public String m() {
        StringBuffer stringBuffer = new StringBuffer();
        WifiInfo l = a().l();
        if (l == null || l.getBSSID() == null) {
            return null;
        }
        String replace = l.getBSSID().replace(":", "");
        int rssi = l.getRssi();
        String n = a().n();
        if (rssi < 0) {
            rssi = -rssi;
        }
        if (replace == null || rssi >= 100) {
            return null;
        }
        stringBuffer.append("&wf=");
        stringBuffer.append(replace);
        stringBuffer.append(com.alipay.sdk.util.h.b);
        stringBuffer.append("" + rssi + com.alipay.sdk.util.h.b);
        String ssid = l.getSSID();
        if (ssid != null && (ssid.contains(com.alipay.sdk.sys.a.b) || ssid.contains(com.alipay.sdk.util.h.b))) {
            ssid = ssid.replace(com.alipay.sdk.sys.a.b, "_");
        }
        stringBuffer.append(ssid);
        stringBuffer.append("&wf_n=1");
        if (n != null) {
            stringBuffer.append("&wf_gw=");
            stringBuffer.append(n);
        }
        return stringBuffer.toString();
    }

    public String n() {
        DhcpInfo dhcpInfo;
        if (this.c == null || (dhcpInfo = this.c.getDhcpInfo()) == null) {
            return null;
        }
        return a((long) dhcpInfo.gateway);
    }

    public g o() {
        return (this.e == null || !this.e.i()) ? q() : this.e;
    }

    public g p() {
        return (this.e == null || !this.e.j()) ? q() : this.e;
    }

    public g q() {
        if (this.c != null) {
            try {
                return new g(this.c.getScanResults(), this.f);
            } catch (Exception e2) {
            }
        }
        return new g((List<ScanResult>) null, 0);
    }

    public void r() {
    }

    public String s() {
        try {
            WifiInfo connectionInfo = this.c.getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getMacAddress();
            }
            return null;
        } catch (Error | Exception e2) {
            return null;
        }
    }
}
