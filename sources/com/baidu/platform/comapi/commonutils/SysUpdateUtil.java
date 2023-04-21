package com.baidu.platform.comapi.commonutils;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.Proxy;
import com.baidu.mapapi.NetworkUtil;
import com.baidu.platform.comapi.util.SysUpdateObserver;
import com.baidu.platform.comjni.engine.AppEngine;
import com.baidu.platform.comjni.map.commonmemcache.a;

public class SysUpdateUtil implements SysUpdateObserver {
    static a a = new a();
    public static boolean b = false;
    public static String c = "";
    public static int d = 0;

    public void init() {
        if (a != null) {
            a.a();
            a.b();
        }
    }

    public void updateNetworkInfo(Context context) {
        NetworkUtil.updateNetworkProxy(context);
    }

    public void updateNetworkProxy(Context context) {
        NetworkInfo activeNetworkInfo = NetworkUtil.getActiveNetworkInfo(context);
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            String lowerCase = activeNetworkInfo.getTypeName().toLowerCase();
            if (lowerCase.equals("wifi") && activeNetworkInfo.isConnected()) {
                AppEngine.SetProxyInfo((String) null, 0);
                b = false;
            } else if (lowerCase.equals("mobile") || (lowerCase.equals("wifi") && !NetworkUtil.isWifiConnected(activeNetworkInfo))) {
                String extraInfo = activeNetworkInfo.getExtraInfo();
                b = false;
                if (extraInfo != null) {
                    String lowerCase2 = extraInfo.toLowerCase();
                    if (lowerCase2.startsWith("cmwap") || lowerCase2.startsWith("uniwap") || lowerCase2.startsWith("3gwap")) {
                        c = "10.0.0.172";
                        d = 80;
                        b = true;
                    } else if (lowerCase2.startsWith("ctwap")) {
                        c = "10.0.0.200";
                        d = 80;
                        b = true;
                    } else if (lowerCase2.startsWith("cmnet") || lowerCase2.startsWith("uninet") || lowerCase2.startsWith("ctnet") || lowerCase2.startsWith("3gnet")) {
                        b = false;
                    }
                } else {
                    String defaultHost = Proxy.getDefaultHost();
                    int defaultPort = Proxy.getDefaultPort();
                    if (defaultHost != null && defaultHost.length() > 0) {
                        if ("10.0.0.172".equals(defaultHost.trim())) {
                            c = "10.0.0.172";
                            d = defaultPort;
                            b = true;
                        } else if ("10.0.0.200".equals(defaultHost.trim())) {
                            c = "10.0.0.200";
                            d = 80;
                            b = true;
                        }
                    }
                }
                if (b) {
                    AppEngine.SetProxyInfo(c, d);
                } else {
                    AppEngine.SetProxyInfo((String) null, 0);
                }
            }
        }
    }

    public void updatePhoneInfo() {
        if (a != null) {
            a.b();
        }
    }
}
