package com.loc;

import android.content.Context;
import android.os.Build;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/* compiled from: ProxyUtil */
public class t {
    public static Proxy a(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                return a(context, new URI("http://restapi.amap.com"));
            }
            return b(context);
        } catch (URISyntaxException e) {
            y.a(e, "ProxyUtil", "getProxy");
            return null;
        } catch (Throwable th) {
            y.a(th, "ProxyUtil", "getProxy");
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0063 A[SYNTHETIC, Splitter:B:28:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006c A[Catch:{ Throwable -> 0x0154 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00bf A[Catch:{ all -> 0x0164 }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00ee A[SYNTHETIC, Splitter:B:70:0x00ee] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x012e A[SYNTHETIC, Splitter:B:92:0x012e] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0144 A[SYNTHETIC, Splitter:B:99:0x0144] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.net.Proxy b(android.content.Context r12) {
        /*
            r10 = 0
            r6 = 80
            r9 = 1
            r8 = -1
            r7 = 0
            int r0 = com.loc.q.h(r12)
            if (r0 != 0) goto L_0x015f
            java.lang.String r0 = "content://telephony/carriers/preferapn"
            android.net.Uri r1 = android.net.Uri.parse(r0)
            android.content.ContentResolver r0 = r12.getContentResolver()
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            android.database.Cursor r2 = r0.query(r1, r2, r3, r4, r5)     // Catch:{ SecurityException -> 0x00ae, Throwable -> 0x011f, all -> 0x0140 }
            if (r2 == 0) goto L_0x01aa
            boolean r0 = r2.moveToFirst()     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            if (r0 == 0) goto L_0x01aa
            java.lang.String r0 = "apn"
            int r0 = r2.getColumnIndex(r0)     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            java.lang.String r0 = r2.getString(r0)     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            if (r0 == 0) goto L_0x0038
            java.util.Locale r1 = java.util.Locale.US     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            java.lang.String r0 = r0.toLowerCase(r1)     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
        L_0x0038:
            if (r0 == 0) goto L_0x0078
            java.lang.String r1 = "ctwap"
            boolean r1 = r0.contains(r1)     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            if (r1 == 0) goto L_0x0078
            java.lang.String r3 = a()     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            int r0 = b()     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            boolean r1 = android.text.TextUtils.isEmpty(r3)     // Catch:{ SecurityException -> 0x0180, Throwable -> 0x016a }
            if (r1 != 0) goto L_0x01ad
            java.lang.String r1 = "null"
            boolean r1 = r3.equals(r1)     // Catch:{ SecurityException -> 0x0180, Throwable -> 0x016a }
            if (r1 != 0) goto L_0x01ad
            r1 = r9
        L_0x0059:
            if (r1 != 0) goto L_0x005d
            java.lang.String r3 = "10.0.0.200"
        L_0x005d:
            if (r0 != r8) goto L_0x0060
            r0 = r6
        L_0x0060:
            r8 = r0
        L_0x0061:
            if (r2 == 0) goto L_0x0066
            r2.close()     // Catch:{ Throwable -> 0x00a2 }
        L_0x0066:
            boolean r0 = a((java.lang.String) r3, (int) r8)     // Catch:{ Throwable -> 0x0154 }
            if (r0 == 0) goto L_0x015f
            java.net.Proxy r0 = new java.net.Proxy     // Catch:{ Throwable -> 0x0154 }
            java.net.Proxy$Type r1 = java.net.Proxy.Type.HTTP     // Catch:{ Throwable -> 0x0154 }
            java.net.InetSocketAddress r2 = java.net.InetSocketAddress.createUnresolved(r3, r8)     // Catch:{ Throwable -> 0x0154 }
            r0.<init>(r1, r2)     // Catch:{ Throwable -> 0x0154 }
        L_0x0077:
            return r0
        L_0x0078:
            if (r0 == 0) goto L_0x01aa
            java.lang.String r1 = "wap"
            boolean r0 = r0.contains(r1)     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            if (r0 == 0) goto L_0x01aa
            java.lang.String r3 = a()     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            int r1 = b()     // Catch:{ SecurityException -> 0x017a, Throwable -> 0x0167 }
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch:{ SecurityException -> 0x018f, Throwable -> 0x0173 }
            if (r0 != 0) goto L_0x01a6
            java.lang.String r0 = "null"
            boolean r0 = r3.equals(r0)     // Catch:{ SecurityException -> 0x018f, Throwable -> 0x0173 }
            if (r0 != 0) goto L_0x01a6
            r0 = r9
        L_0x009a:
            if (r0 != 0) goto L_0x009e
            java.lang.String r3 = "10.0.0.172"
        L_0x009e:
            if (r1 != r8) goto L_0x01a3
            r8 = r6
            goto L_0x0061
        L_0x00a2:
            r0 = move-exception
            java.lang.String r1 = "ProxyUtil"
            java.lang.String r2 = "getHostProxy2"
            com.loc.y.a(r0, r1, r2)
            r0.printStackTrace()
            goto L_0x0066
        L_0x00ae:
            r0 = move-exception
            r1 = r7
            r2 = r8
            r3 = r7
        L_0x00b2:
            java.lang.String r4 = "ProxyUtil"
            java.lang.String r5 = "getHostProxy"
            com.loc.y.a(r0, r4, r5)     // Catch:{ all -> 0x0164 }
            java.lang.String r0 = com.loc.q.j(r12)     // Catch:{ all -> 0x0164 }
            if (r0 == 0) goto L_0x01a0
            java.util.Locale r2 = java.util.Locale.US     // Catch:{ all -> 0x0164 }
            java.lang.String r4 = r0.toLowerCase(r2)     // Catch:{ all -> 0x0164 }
            java.lang.String r0 = a()     // Catch:{ all -> 0x0164 }
            int r2 = b()     // Catch:{ all -> 0x0164 }
            java.lang.String r5 = "ctwap"
            int r5 = r4.indexOf(r5)     // Catch:{ all -> 0x0164 }
            if (r5 == r8) goto L_0x0100
            boolean r4 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0164 }
            if (r4 != 0) goto L_0x00e5
            java.lang.String r4 = "null"
            boolean r4 = r0.equals(r4)     // Catch:{ all -> 0x0164 }
            if (r4 != 0) goto L_0x00e5
            r10 = r9
            r3 = r0
        L_0x00e5:
            if (r10 != 0) goto L_0x00e9
            java.lang.String r3 = "10.0.0.200"
        L_0x00e9:
            if (r2 != r8) goto L_0x01a0
            r8 = r6
        L_0x00ec:
            if (r1 == 0) goto L_0x0066
            r1.close()     // Catch:{ Throwable -> 0x00f3 }
            goto L_0x0066
        L_0x00f3:
            r0 = move-exception
            java.lang.String r1 = "ProxyUtil"
            java.lang.String r2 = "getHostProxy2"
            com.loc.y.a(r0, r1, r2)
            r0.printStackTrace()
            goto L_0x0066
        L_0x0100:
            java.lang.String r5 = "wap"
            int r4 = r4.indexOf(r5)     // Catch:{ all -> 0x0164 }
            if (r4 == r8) goto L_0x01a0
            boolean r2 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0164 }
            if (r2 != 0) goto L_0x019c
            java.lang.String r2 = "null"
            boolean r2 = r0.equals(r2)     // Catch:{ all -> 0x0164 }
            if (r2 != 0) goto L_0x019c
            r2 = r9
        L_0x0118:
            if (r2 != 0) goto L_0x011c
            java.lang.String r0 = "10.0.0.200"
        L_0x011c:
            r8 = r6
            r3 = r0
            goto L_0x00ec
        L_0x011f:
            r0 = move-exception
            r2 = r7
            r3 = r7
        L_0x0122:
            java.lang.String r1 = "ProxyUtil"
            java.lang.String r4 = "getHostProxy1"
            com.loc.y.a(r0, r1, r4)     // Catch:{ all -> 0x0162 }
            r0.printStackTrace()     // Catch:{ all -> 0x0162 }
            if (r2 == 0) goto L_0x0066
            r2.close()     // Catch:{ Throwable -> 0x0133 }
            goto L_0x0066
        L_0x0133:
            r0 = move-exception
            java.lang.String r1 = "ProxyUtil"
            java.lang.String r2 = "getHostProxy2"
            com.loc.y.a(r0, r1, r2)
            r0.printStackTrace()
            goto L_0x0066
        L_0x0140:
            r0 = move-exception
            r2 = r7
        L_0x0142:
            if (r2 == 0) goto L_0x0147
            r2.close()     // Catch:{ Throwable -> 0x0148 }
        L_0x0147:
            throw r0
        L_0x0148:
            r1 = move-exception
            java.lang.String r2 = "ProxyUtil"
            java.lang.String r3 = "getHostProxy2"
            com.loc.y.a(r1, r2, r3)
            r1.printStackTrace()
            goto L_0x0147
        L_0x0154:
            r0 = move-exception
            java.lang.String r1 = "ProxyUtil"
            java.lang.String r2 = "getHostProxy2"
            com.loc.y.a(r0, r1, r2)
            r0.printStackTrace()
        L_0x015f:
            r0 = r7
            goto L_0x0077
        L_0x0162:
            r0 = move-exception
            goto L_0x0142
        L_0x0164:
            r0 = move-exception
            r2 = r1
            goto L_0x0142
        L_0x0167:
            r0 = move-exception
            r3 = r7
            goto L_0x0122
        L_0x016a:
            r1 = move-exception
            r8 = r0
            r3 = r7
            r0 = r1
            goto L_0x0122
        L_0x016f:
            r1 = move-exception
            r8 = r0
            r0 = r1
            goto L_0x0122
        L_0x0173:
            r0 = move-exception
            r8 = r1
            r3 = r7
            goto L_0x0122
        L_0x0177:
            r0 = move-exception
            r8 = r1
            goto L_0x0122
        L_0x017a:
            r0 = move-exception
            r1 = r2
            r3 = r7
            r2 = r8
            goto L_0x00b2
        L_0x0180:
            r1 = move-exception
            r3 = r7
            r11 = r2
            r2 = r0
            r0 = r1
            r1 = r11
            goto L_0x00b2
        L_0x0188:
            r1 = move-exception
            r11 = r1
            r1 = r2
            r2 = r0
            r0 = r11
            goto L_0x00b2
        L_0x018f:
            r0 = move-exception
            r3 = r7
            r11 = r1
            r1 = r2
            r2 = r11
            goto L_0x00b2
        L_0x0196:
            r0 = move-exception
            r11 = r2
            r2 = r1
            r1 = r11
            goto L_0x00b2
        L_0x019c:
            r2 = r10
            r0 = r3
            goto L_0x0118
        L_0x01a0:
            r8 = r2
            goto L_0x00ec
        L_0x01a3:
            r8 = r1
            goto L_0x0061
        L_0x01a6:
            r0 = r10
            r3 = r7
            goto L_0x009a
        L_0x01aa:
            r3 = r7
            goto L_0x0061
        L_0x01ad:
            r1 = r10
            r3 = r7
            goto L_0x0059
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.t.b(android.content.Context):java.net.Proxy");
    }

    private static boolean a(String str, int i) {
        return (str == null || str.length() <= 0 || i == -1) ? false : true;
    }

    private static String a() {
        String str;
        try {
            str = android.net.Proxy.getDefaultHost();
        } catch (Throwable th) {
            y.a(th, "ProxyUtil", "getDefHost");
            str = null;
        }
        if (str == null) {
            return "null";
        }
        return str;
    }

    private static Proxy a(Context context, URI uri) {
        if (q.h(context) == 0) {
            try {
                List<Proxy> select = ProxySelector.getDefault().select(uri);
                if (select == null || select.isEmpty()) {
                    return null;
                }
                Proxy proxy = select.get(0);
                if (proxy == null || proxy.type() == Proxy.Type.DIRECT) {
                    return null;
                }
                return proxy;
            } catch (Throwable th) {
                y.a(th, "ProxyUtil", "getProxySelectorCfg");
            }
        }
        return null;
    }

    private static int b() {
        try {
            return android.net.Proxy.getDefaultPort();
        } catch (Throwable th) {
            y.a(th, "ProxyUtil", "getDefPort");
            return -1;
        }
    }
}
