package com.loc;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import java.util.List;
import org.json.JSONObject;

/* compiled from: WifiManagerWrapper */
public class bg {
    private WifiManager a;
    private JSONObject b;
    private Context c;

    public bg(Context context, WifiManager wifiManager, JSONObject jSONObject) {
        this.a = wifiManager;
        this.b = jSONObject;
        this.c = context;
    }

    public void a(JSONObject jSONObject) {
        this.b = jSONObject;
    }

    public List<ScanResult> a() {
        try {
            if (this.a != null) {
                return this.a.getScanResults();
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public WifiInfo b() {
        if (this.a != null) {
            return this.a.getConnectionInfo();
        }
        return null;
    }

    public int c() {
        if (this.a != null) {
            return this.a.getWifiState();
        }
        return 4;
    }

    public boolean d() {
        if (this.a != null) {
            return this.a.startScan();
        }
        return false;
    }

    public boolean e() {
        try {
            if (String.valueOf(bv.a(this.a, "startScanActive", new Object[0])).equals("true")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean a(ConnectivityManager connectivityManager) {
        boolean z = true;
        WifiManager wifiManager = this.a;
        if (wifiManager == null || !f()) {
            return false;
        }
        try {
            if (bp.a(connectivityManager.getActiveNetworkInfo()) != 1 || !a(wifiManager.getConnectionInfo())) {
                z = false;
            }
            return z;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean f() {
        boolean z = false;
        WifiManager wifiManager = this.a;
        if (wifiManager == null) {
            return false;
        }
        try {
            z = wifiManager.isWifiEnabled();
        } catch (Exception e) {
        }
        if (z || bw.c() <= 17) {
            return z;
        }
        try {
            return String.valueOf(bv.a(wifiManager, "isScanAlwaysAvailable", new Object[0])).equals("true");
        } catch (Exception e2) {
            return z;
        }
    }

    public void a(boolean z) {
        Context context = this.c;
        if (this.a != null && context != null && z && bw.c() > 17) {
            if (bw.a(this.b, "autoenablewifialwaysscan")) {
                try {
                    if ("0".equals(this.b.getString("autoenablewifialwaysscan"))) {
                        return;
                    }
                } catch (Exception e) {
                }
            }
            ContentResolver contentResolver = context.getContentResolver();
            try {
                if (((Integer) bv.a("android.provider.Settings$Global", "getInt", new Object[]{contentResolver, "wifi_scan_always_enabled"}, new Class[]{ContentResolver.class, String.class})).intValue() == 0) {
                    bv.a("android.provider.Settings$Global", "putInt", new Object[]{contentResolver, "wifi_scan_always_enabled", 1}, new Class[]{ContentResolver.class, String.class, Integer.TYPE});
                }
            } catch (Exception e2) {
            }
        }
    }

    private boolean a(WifiInfo wifiInfo) {
        if (wifiInfo == null || TextUtils.isEmpty(wifiInfo.getBSSID()) || wifiInfo.getSSID() == null || wifiInfo.getBSSID().equals("00:00:00:00:00:00") || wifiInfo.getBSSID().contains(" :") || TextUtils.isEmpty(wifiInfo.getSSID())) {
            return false;
        }
        return true;
    }
}
