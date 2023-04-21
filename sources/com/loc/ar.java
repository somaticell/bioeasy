package com.loc;

import android.os.Build;
import com.alipay.sdk.sys.a;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/* compiled from: HttpUrlUtil */
public class ar {
    private static as a;
    private int b;
    private int c;
    private boolean d;
    private SSLContext e;
    private Proxy f;
    private volatile boolean g;
    private long h;
    private long i;
    private HostnameVerifier j;

    ar(int i2, int i3, Proxy proxy, boolean z) {
        this.g = false;
        this.h = -1;
        this.i = 0;
        this.j = new av(this);
        this.b = i2;
        this.c = i3;
        this.f = proxy;
        this.d = z;
        if (z) {
            try {
                SSLContext instance = SSLContext.getInstance("TLS");
                instance.init((KeyManager[]) null, (TrustManager[]) null, (SecureRandom) null);
                this.e = instance;
            } catch (NoSuchAlgorithmException e2) {
                y.a(e2, "HttpUrlUtil", "HttpUrlUtil");
            } catch (Throwable th) {
                y.a(th, "HttpUtil", "HttpUtil");
            }
        }
    }

    ar(int i2, int i3, Proxy proxy) {
        this(i2, i3, proxy, false);
    }

    /* access modifiers changed from: package-private */
    public void a(long j2) {
        this.i = j2;
    }

    /* access modifiers changed from: package-private */
    public void b(long j2) {
        this.h = j2;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r1v30, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r1v31 */
    /* JADX WARNING: type inference failed for: r1v33 */
    /* JADX WARNING: type inference failed for: r1v41 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x01b1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01b2, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x01be, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01bf, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01cb, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01cc, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01d7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01d8, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x01f4, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x01f5, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0201, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0202, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x020d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x020e, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x022a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x022b, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0237, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0238, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x0243, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0244, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0260, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0261, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x026d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x026e, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0279, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x027a, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x0296, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x0297, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x02a3, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x02a4, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x02af, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x02b0, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x02f3, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x02f5, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x02f8, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x02fb, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:229:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ee, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ef, code lost:
        r9 = r2;
        r2 = r1;
        r1 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0121, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0131, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0132, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0162, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:?, code lost:
        r2.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0172, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0173, code lost:
        r0.printStackTrace();
        com.loc.y.a(r0, "HttpUrlUtil", "makeDownloadGetRequest");
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x01ea A[SYNTHETIC, Splitter:B:118:0x01ea] */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x01ef A[SYNTHETIC, Splitter:B:121:0x01ef] */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x0220 A[SYNTHETIC, Splitter:B:134:0x0220] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0225 A[SYNTHETIC, Splitter:B:137:0x0225] */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0256 A[SYNTHETIC, Splitter:B:150:0x0256] */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x025b A[SYNTHETIC, Splitter:B:153:0x025b] */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x028c A[SYNTHETIC, Splitter:B:166:0x028c] */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x0291 A[SYNTHETIC, Splitter:B:169:0x0291] */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x02bf A[SYNTHETIC, Splitter:B:180:0x02bf] */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x02c4 A[SYNTHETIC, Splitter:B:183:0x02c4] */
    /* JADX WARNING: Removed duplicated region for block: B:195:0x02f3 A[ExcHandler: Throwable (th java.lang.Throwable), Splitter:B:19:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x02f5 A[ExcHandler: IOException (e java.io.IOException), Splitter:B:19:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x02f8 A[ExcHandler: SocketTimeoutException (e java.net.SocketTimeoutException), Splitter:B:19:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x02fb A[ExcHandler: SocketException (e java.net.SocketException), Splitter:B:19:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:214:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:218:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:220:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:222:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:224:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:226:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0121 A[ExcHandler: MalformedURLException (e java.net.MalformedURLException), Splitter:B:19:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0127 A[SYNTHETIC, Splitter:B:65:0x0127] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x012c A[SYNTHETIC, Splitter:B:68:0x012c] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0162 A[ExcHandler: UnknownHostException (e java.net.UnknownHostException), Splitter:B:19:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0168 A[SYNTHETIC, Splitter:B:90:0x0168] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x016d A[SYNTHETIC, Splitter:B:93:0x016d] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:147:0x0251=Splitter:B:147:0x0251, B:62:0x0122=Splitter:B:62:0x0122, B:87:0x0163=Splitter:B:87:0x0163, B:115:0x01e5=Splitter:B:115:0x01e5, B:163:0x0287=Splitter:B:163:0x0287, B:131:0x021b=Splitter:B:131:0x021b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.lang.String r11, java.util.Map<java.lang.String, java.lang.String> r12, java.util.Map<java.lang.String, java.lang.String> r13, com.loc.aq.a r14) {
        /*
            r10 = this;
            r1 = 0
            r0 = 1
            r8 = 1024(0x400, float:1.435E-42)
            r3 = 0
            r2 = 0
            r4 = 0
            if (r14 != 0) goto L_0x0038
            if (r1 == 0) goto L_0x000e
            r2.close()     // Catch:{ IOException -> 0x0014, Throwable -> 0x0020 }
        L_0x000e:
            if (r1 == 0) goto L_0x0013
            r4.disconnect()     // Catch:{ Throwable -> 0x002c }
        L_0x0013:
            return
        L_0x0014:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r2 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r2, r3)
            goto L_0x000e
        L_0x0020:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r2 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r2, r3)
            goto L_0x000e
        L_0x002c:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r2 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r2)
            goto L_0x0013
        L_0x0038:
            java.lang.String r2 = a((java.util.Map<java.lang.String, java.lang.String>) r13)     // Catch:{ ConnectException -> 0x0306, MalformedURLException -> 0x0302, UnknownHostException -> 0x02fe, SocketException -> 0x01e3, SocketTimeoutException -> 0x0219, IOException -> 0x024f, Throwable -> 0x0285, all -> 0x02bb }
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch:{ ConnectException -> 0x0306, MalformedURLException -> 0x0302, UnknownHostException -> 0x02fe, SocketException -> 0x01e3, SocketTimeoutException -> 0x0219, IOException -> 0x024f, Throwable -> 0x0285, all -> 0x02bb }
            r4.<init>()     // Catch:{ ConnectException -> 0x0306, MalformedURLException -> 0x0302, UnknownHostException -> 0x02fe, SocketException -> 0x01e3, SocketTimeoutException -> 0x0219, IOException -> 0x024f, Throwable -> 0x0285, all -> 0x02bb }
            r4.append(r11)     // Catch:{ ConnectException -> 0x0306, MalformedURLException -> 0x0302, UnknownHostException -> 0x02fe, SocketException -> 0x01e3, SocketTimeoutException -> 0x0219, IOException -> 0x024f, Throwable -> 0x0285, all -> 0x02bb }
            if (r2 == 0) goto L_0x004f
            java.lang.String r5 = "?"
            java.lang.StringBuffer r5 = r4.append(r5)     // Catch:{ ConnectException -> 0x0306, MalformedURLException -> 0x0302, UnknownHostException -> 0x02fe, SocketException -> 0x01e3, SocketTimeoutException -> 0x0219, IOException -> 0x024f, Throwable -> 0x0285, all -> 0x02bb }
            r5.append(r2)     // Catch:{ ConnectException -> 0x0306, MalformedURLException -> 0x0302, UnknownHostException -> 0x02fe, SocketException -> 0x01e3, SocketTimeoutException -> 0x0219, IOException -> 0x024f, Throwable -> 0x0285, all -> 0x02bb }
        L_0x004f:
            java.lang.String r2 = r4.toString()     // Catch:{ ConnectException -> 0x0306, MalformedURLException -> 0x0302, UnknownHostException -> 0x02fe, SocketException -> 0x01e3, SocketTimeoutException -> 0x0219, IOException -> 0x024f, Throwable -> 0x0285, all -> 0x02bb }
            r4 = 0
            java.net.HttpURLConnection r2 = r10.a((java.lang.String) r2, (java.util.Map<java.lang.String, java.lang.String>) r12, (boolean) r4)     // Catch:{ ConnectException -> 0x0306, MalformedURLException -> 0x0302, UnknownHostException -> 0x02fe, SocketException -> 0x01e3, SocketTimeoutException -> 0x0219, IOException -> 0x024f, Throwable -> 0x0285, all -> 0x02bb }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r4.<init>()     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.String r5 = "bytes="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            long r6 = r10.i     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.String r5 = "-"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.String r4 = r4.toString()     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.String r5 = "RANGE"
            r2.setRequestProperty(r5, r4)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r2.connect()     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            int r5 = r2.getResponseCode()     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r4 = 200(0xc8, float:2.8E-43)
            if (r5 == r4) goto L_0x010e
            r4 = r0
        L_0x0084:
            r6 = 206(0xce, float:2.89E-43)
            if (r5 == r6) goto L_0x0111
        L_0x0088:
            r0 = r0 & r4
            if (r0 == 0) goto L_0x00b5
            com.loc.l r0 = new com.loc.l     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r3.<init>()     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.String r4 = "网络异常原因："
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.String r4 = r2.getResponseMessage()     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.String r4 = " 网络异常状态码："
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.StringBuilder r3 = r3.append(r5)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            java.lang.String r3 = r3.toString()     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r0.<init>(r3)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r14.a(r0)     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
        L_0x00b5:
            java.io.InputStream r1 = r2.getInputStream()     // Catch:{ ConnectException -> 0x030a, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
        L_0x00bd:
            boolean r3 = java.lang.Thread.interrupted()     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            if (r3 != 0) goto L_0x013e
            boolean r3 = r10.g     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            if (r3 != 0) goto L_0x013e
            r3 = 0
            r4 = 1024(0x400, float:1.435E-42)
            int r3 = r1.read(r0, r3, r4)     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            if (r3 <= 0) goto L_0x013e
            long r4 = r10.h     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r6 = -1
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 == 0) goto L_0x00e0
            long r4 = r10.i     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            long r6 = r10.h     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x013e
        L_0x00e0:
            if (r3 != r8) goto L_0x0114
            long r4 = r10.i     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r14.a(r0, r4)     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
        L_0x00e7:
            long r4 = r10.i     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            long r6 = (long) r3     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            long r4 = r4 + r6
            r10.i = r4     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            goto L_0x00bd
        L_0x00ee:
            r0 = move-exception
            r9 = r2
            r2 = r1
            r1 = r9
        L_0x00f2:
            r14.a(r0)     // Catch:{ all -> 0x02ee }
            if (r2 == 0) goto L_0x00fa
            r2.close()     // Catch:{ IOException -> 0x0197, Throwable -> 0x01a4 }
        L_0x00fa:
            if (r1 == 0) goto L_0x0013
            r1.disconnect()     // Catch:{ Throwable -> 0x0101 }
            goto L_0x0013
        L_0x0101:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r2 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r2)
            goto L_0x0013
        L_0x010e:
            r4 = r3
            goto L_0x0084
        L_0x0111:
            r0 = r3
            goto L_0x0088
        L_0x0114:
            byte[] r4 = new byte[r3]     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r5 = 0
            r6 = 0
            java.lang.System.arraycopy(r0, r5, r4, r6, r3)     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            long r6 = r10.i     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            r14.a(r4, r6)     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            goto L_0x00e7
        L_0x0121:
            r0 = move-exception
        L_0x0122:
            r14.a(r0)     // Catch:{ all -> 0x02ec }
            if (r1 == 0) goto L_0x012a
            r1.close()     // Catch:{ IOException -> 0x01b1, Throwable -> 0x01be }
        L_0x012a:
            if (r2 == 0) goto L_0x0013
            r2.disconnect()     // Catch:{ Throwable -> 0x0131 }
            goto L_0x0013
        L_0x0131:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r2 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r2)
            goto L_0x0013
        L_0x013e:
            boolean r0 = r10.g     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            if (r0 == 0) goto L_0x015e
            r14.b()     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
        L_0x0145:
            if (r1 == 0) goto L_0x014a
            r1.close()     // Catch:{ IOException -> 0x017f, Throwable -> 0x018b }
        L_0x014a:
            if (r2 == 0) goto L_0x0013
            r2.disconnect()     // Catch:{ Throwable -> 0x0151 }
            goto L_0x0013
        L_0x0151:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r2 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r2)
            goto L_0x0013
        L_0x015e:
            r14.c()     // Catch:{ ConnectException -> 0x00ee, MalformedURLException -> 0x0121, UnknownHostException -> 0x0162, SocketException -> 0x02fb, SocketTimeoutException -> 0x02f8, IOException -> 0x02f5, Throwable -> 0x02f3 }
            goto L_0x0145
        L_0x0162:
            r0 = move-exception
        L_0x0163:
            r14.a(r0)     // Catch:{ all -> 0x02ec }
            if (r1 == 0) goto L_0x016b
            r1.close()     // Catch:{ IOException -> 0x01cb, Throwable -> 0x01d7 }
        L_0x016b:
            if (r2 == 0) goto L_0x0013
            r2.disconnect()     // Catch:{ Throwable -> 0x0172 }
            goto L_0x0013
        L_0x0172:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r2 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r2)
            goto L_0x0013
        L_0x017f:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x014a
        L_0x018b:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x014a
        L_0x0197:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r2 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r2, r3)
            goto L_0x00fa
        L_0x01a4:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r2 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r2, r3)
            goto L_0x00fa
        L_0x01b1:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x012a
        L_0x01be:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x012a
        L_0x01cb:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x016b
        L_0x01d7:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x016b
        L_0x01e3:
            r0 = move-exception
            r2 = r1
        L_0x01e5:
            r14.a(r0)     // Catch:{ all -> 0x02ec }
            if (r1 == 0) goto L_0x01ed
            r1.close()     // Catch:{ IOException -> 0x0201, Throwable -> 0x020d }
        L_0x01ed:
            if (r2 == 0) goto L_0x0013
            r2.disconnect()     // Catch:{ Throwable -> 0x01f4 }
            goto L_0x0013
        L_0x01f4:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r2 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r2)
            goto L_0x0013
        L_0x0201:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x01ed
        L_0x020d:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x01ed
        L_0x0219:
            r0 = move-exception
            r2 = r1
        L_0x021b:
            r14.a(r0)     // Catch:{ all -> 0x02ec }
            if (r1 == 0) goto L_0x0223
            r1.close()     // Catch:{ IOException -> 0x0237, Throwable -> 0x0243 }
        L_0x0223:
            if (r2 == 0) goto L_0x0013
            r2.disconnect()     // Catch:{ Throwable -> 0x022a }
            goto L_0x0013
        L_0x022a:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r2 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r2)
            goto L_0x0013
        L_0x0237:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x0223
        L_0x0243:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x0223
        L_0x024f:
            r0 = move-exception
            r2 = r1
        L_0x0251:
            r14.a(r0)     // Catch:{ all -> 0x02ec }
            if (r1 == 0) goto L_0x0259
            r1.close()     // Catch:{ IOException -> 0x026d, Throwable -> 0x0279 }
        L_0x0259:
            if (r2 == 0) goto L_0x0013
            r2.disconnect()     // Catch:{ Throwable -> 0x0260 }
            goto L_0x0013
        L_0x0260:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r2 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r2)
            goto L_0x0013
        L_0x026d:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x0259
        L_0x0279:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x0259
        L_0x0285:
            r0 = move-exception
            r2 = r1
        L_0x0287:
            r14.a(r0)     // Catch:{ all -> 0x02ec }
            if (r1 == 0) goto L_0x028f
            r1.close()     // Catch:{ IOException -> 0x02a3, Throwable -> 0x02af }
        L_0x028f:
            if (r2 == 0) goto L_0x0013
            r2.disconnect()     // Catch:{ Throwable -> 0x0296 }
            goto L_0x0013
        L_0x0296:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r2 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r2)
            goto L_0x0013
        L_0x02a3:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x028f
        L_0x02af:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r0, r1, r3)
            goto L_0x028f
        L_0x02bb:
            r0 = move-exception
            r2 = r1
        L_0x02bd:
            if (r1 == 0) goto L_0x02c2
            r1.close()     // Catch:{ IOException -> 0x02c8, Throwable -> 0x02d4 }
        L_0x02c2:
            if (r2 == 0) goto L_0x02c7
            r2.disconnect()     // Catch:{ Throwable -> 0x02e0 }
        L_0x02c7:
            throw r0
        L_0x02c8:
            r1 = move-exception
            r1.printStackTrace()
            java.lang.String r3 = "HttpUrlUtil"
            java.lang.String r4 = "makeDownloadGetRequest"
            com.loc.y.a(r1, r3, r4)
            goto L_0x02c2
        L_0x02d4:
            r1 = move-exception
            r1.printStackTrace()
            java.lang.String r3 = "HttpUrlUtil"
            java.lang.String r4 = "makeDownloadGetRequest"
            com.loc.y.a(r1, r3, r4)
            goto L_0x02c2
        L_0x02e0:
            r1 = move-exception
            r1.printStackTrace()
            java.lang.String r2 = "HttpUrlUtil"
            java.lang.String r3 = "makeDownloadGetRequest"
            com.loc.y.a(r1, r2, r3)
            goto L_0x02c7
        L_0x02ec:
            r0 = move-exception
            goto L_0x02bd
        L_0x02ee:
            r0 = move-exception
            r9 = r1
            r1 = r2
            r2 = r9
            goto L_0x02bd
        L_0x02f3:
            r0 = move-exception
            goto L_0x0287
        L_0x02f5:
            r0 = move-exception
            goto L_0x0251
        L_0x02f8:
            r0 = move-exception
            goto L_0x021b
        L_0x02fb:
            r0 = move-exception
            goto L_0x01e5
        L_0x02fe:
            r0 = move-exception
            r2 = r1
            goto L_0x0163
        L_0x0302:
            r0 = move-exception
            r2 = r1
            goto L_0x0122
        L_0x0306:
            r0 = move-exception
            r2 = r1
            goto L_0x00f2
        L_0x030a:
            r0 = move-exception
            r9 = r2
            r2 = r1
            r1 = r9
            goto L_0x00f2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ar.a(java.lang.String, java.util.Map, java.util.Map, com.loc.aq$a):void");
    }

    /* access modifiers changed from: package-private */
    public au a(String str, Map<String, String> map, byte[] bArr) throws l {
        try {
            HttpURLConnection a2 = a(str, map, true);
            if (bArr != null && bArr.length > 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(a2.getOutputStream());
                dataOutputStream.write(bArr);
                dataOutputStream.close();
            }
            a2.connect();
            return a(a2);
        } catch (ConnectException e2) {
            e2.printStackTrace();
            throw new l("http连接失败 - ConnectionException");
        } catch (MalformedURLException e3) {
            e3.printStackTrace();
            throw new l("url异常 - MalformedURLException");
        } catch (UnknownHostException e4) {
            e4.printStackTrace();
            throw new l("未知主机 - UnKnowHostException");
        } catch (SocketException e5) {
            e5.printStackTrace();
            throw new l("socket 连接异常 - SocketException");
        } catch (SocketTimeoutException e6) {
            e6.printStackTrace();
            throw new l("socket 连接超时 - SocketTimeoutException");
        } catch (IOException e7) {
            e7.printStackTrace();
            throw new l("IO 操作异常 - IOException");
        } catch (Throwable th) {
            y.a(th, "HttpUrlUtil", "makePostReqeust");
            throw new l("未知的错误");
        }
    }

    /* access modifiers changed from: package-private */
    public HttpURLConnection a(String str, Map<String, String> map, boolean z) throws IOException {
        Object obj;
        HttpURLConnection httpURLConnection;
        q.a();
        URL url = new URL(str);
        if (this.f != null) {
            obj = url.openConnection(this.f);
        } else {
            obj = (HttpURLConnection) url.openConnection();
        }
        if (this.d) {
            httpURLConnection = (HttpsURLConnection) obj;
            ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(this.e.getSocketFactory());
            ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(this.j);
        } else {
            httpURLConnection = (HttpURLConnection) obj;
        }
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            httpURLConnection.setRequestProperty("Connection", "close");
        }
        a(map, httpURLConnection);
        if (z) {
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
        } else {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
        }
        return httpURLConnection;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x003d A[SYNTHETIC, Splitter:B:12:0x003d] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0042 A[SYNTHETIC, Splitter:B:15:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0047 A[SYNTHETIC, Splitter:B:18:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004c A[SYNTHETIC, Splitter:B:21:0x004c] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0051 A[SYNTHETIC, Splitter:B:24:0x0051] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.loc.au a(java.net.HttpURLConnection r10) throws com.loc.l, java.io.IOException {
        /*
            r9 = this;
            r1 = 0
            java.util.Map r5 = r10.getHeaderFields()     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            int r0 = r10.getResponseCode()     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            r2 = 200(0xc8, float:2.8E-43)
            if (r0 == r2) goto L_0x0055
            com.loc.l r2 = new com.loc.l     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            r3.<init>()     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            java.lang.String r4 = "网络异常原因："
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            java.lang.String r4 = r10.getResponseMessage()     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            java.lang.String r4 = " 网络异常状态码："
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            throw r2     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
        L_0x0035:
            r0 = move-exception
            r2 = r1
            r3 = r1
            r4 = r1
        L_0x0039:
            throw r0     // Catch:{ all -> 0x003a }
        L_0x003a:
            r0 = move-exception
        L_0x003b:
            if (r4 == 0) goto L_0x0040
            r4.close()     // Catch:{ IOException -> 0x00c8 }
        L_0x0040:
            if (r3 == 0) goto L_0x0045
            r3.close()     // Catch:{ Exception -> 0x00d5 }
        L_0x0045:
            if (r1 == 0) goto L_0x004a
            r1.close()     // Catch:{ Exception -> 0x00e2 }
        L_0x004a:
            if (r2 == 0) goto L_0x004f
            r2.close()     // Catch:{ Exception -> 0x00ef }
        L_0x004f:
            if (r10 == 0) goto L_0x0054
            r10.disconnect()     // Catch:{ Throwable -> 0x00fc }
        L_0x0054:
            throw r0
        L_0x0055:
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            r4.<init>()     // Catch:{ IOException -> 0x0035, all -> 0x0145 }
            java.io.InputStream r3 = r10.getInputStream()     // Catch:{ IOException -> 0x0160, all -> 0x014b }
            java.io.PushbackInputStream r2 = new java.io.PushbackInputStream     // Catch:{ IOException -> 0x0165, all -> 0x0150 }
            r0 = 2
            r2.<init>(r3, r0)     // Catch:{ IOException -> 0x0165, all -> 0x0150 }
            r0 = 2
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x0169, all -> 0x0154 }
            r2.read(r0)     // Catch:{ IOException -> 0x0169, all -> 0x0154 }
            r2.unread(r0)     // Catch:{ IOException -> 0x0169, all -> 0x0154 }
            r6 = 0
            byte r6 = r0[r6]     // Catch:{ IOException -> 0x0169, all -> 0x0154 }
            r7 = 31
            if (r6 != r7) goto L_0x0096
            r6 = 1
            byte r0 = r0[r6]     // Catch:{ IOException -> 0x0169, all -> 0x0154 }
            r6 = -117(0xffffffffffffff8b, float:NaN)
            if (r0 != r6) goto L_0x0096
            java.util.zip.GZIPInputStream r0 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x0169, all -> 0x0154 }
            r0.<init>(r2)     // Catch:{ IOException -> 0x0169, all -> 0x0154 }
            r1 = r0
        L_0x0081:
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ IOException -> 0x0091, all -> 0x015a }
        L_0x0085:
            int r6 = r1.read(r0)     // Catch:{ IOException -> 0x0091, all -> 0x015a }
            r7 = -1
            if (r6 == r7) goto L_0x0098
            r7 = 0
            r4.write(r0, r7, r6)     // Catch:{ IOException -> 0x0091, all -> 0x015a }
            goto L_0x0085
        L_0x0091:
            r0 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
            goto L_0x0039
        L_0x0096:
            r1 = r2
            goto L_0x0081
        L_0x0098:
            com.loc.as r0 = a     // Catch:{ IOException -> 0x0091, all -> 0x015a }
            if (r0 == 0) goto L_0x00a1
            com.loc.as r0 = a     // Catch:{ IOException -> 0x0091, all -> 0x015a }
            r0.a()     // Catch:{ IOException -> 0x0091, all -> 0x015a }
        L_0x00a1:
            com.loc.au r0 = new com.loc.au     // Catch:{ IOException -> 0x0091, all -> 0x015a }
            r0.<init>()     // Catch:{ IOException -> 0x0091, all -> 0x015a }
            byte[] r6 = r4.toByteArray()     // Catch:{ IOException -> 0x0091, all -> 0x015a }
            r0.a = r6     // Catch:{ IOException -> 0x0091, all -> 0x015a }
            r0.b = r5     // Catch:{ IOException -> 0x0091, all -> 0x015a }
            if (r4 == 0) goto L_0x00b3
            r4.close()     // Catch:{ IOException -> 0x0109 }
        L_0x00b3:
            if (r3 == 0) goto L_0x00b8
            r3.close()     // Catch:{ Exception -> 0x0115 }
        L_0x00b8:
            if (r2 == 0) goto L_0x00bd
            r2.close()     // Catch:{ Exception -> 0x0121 }
        L_0x00bd:
            if (r1 == 0) goto L_0x00c2
            r1.close()     // Catch:{ Exception -> 0x012d }
        L_0x00c2:
            if (r10 == 0) goto L_0x00c7
            r10.disconnect()     // Catch:{ Throwable -> 0x0139 }
        L_0x00c7:
            return r0
        L_0x00c8:
            r4 = move-exception
            java.lang.String r5 = "HttpUrlUtil"
            java.lang.String r6 = "parseResult"
            com.loc.y.a(r4, r5, r6)
            r4.printStackTrace()
            goto L_0x0040
        L_0x00d5:
            r3 = move-exception
            java.lang.String r4 = "HttpUrlUtil"
            java.lang.String r5 = "parseResult"
            com.loc.y.a(r3, r4, r5)
            r3.printStackTrace()
            goto L_0x0045
        L_0x00e2:
            r1 = move-exception
            java.lang.String r3 = "HttpUrlUtil"
            java.lang.String r4 = "parseResult"
            com.loc.y.a(r1, r3, r4)
            r1.printStackTrace()
            goto L_0x004a
        L_0x00ef:
            r1 = move-exception
            java.lang.String r2 = "HttpUrlUtil"
            java.lang.String r3 = "parseResult"
            com.loc.y.a(r1, r2, r3)
            r1.printStackTrace()
            goto L_0x004f
        L_0x00fc:
            r1 = move-exception
            java.lang.String r2 = "HttpUrlUtil"
            java.lang.String r3 = "parseResult"
            com.loc.y.a(r1, r2, r3)
            r1.printStackTrace()
            goto L_0x0054
        L_0x0109:
            r4 = move-exception
            java.lang.String r5 = "HttpUrlUtil"
            java.lang.String r6 = "parseResult"
            com.loc.y.a(r4, r5, r6)
            r4.printStackTrace()
            goto L_0x00b3
        L_0x0115:
            r3 = move-exception
            java.lang.String r4 = "HttpUrlUtil"
            java.lang.String r5 = "parseResult"
            com.loc.y.a(r3, r4, r5)
            r3.printStackTrace()
            goto L_0x00b8
        L_0x0121:
            r2 = move-exception
            java.lang.String r3 = "HttpUrlUtil"
            java.lang.String r4 = "parseResult"
            com.loc.y.a(r2, r3, r4)
            r2.printStackTrace()
            goto L_0x00bd
        L_0x012d:
            r1 = move-exception
            java.lang.String r2 = "HttpUrlUtil"
            java.lang.String r3 = "parseResult"
            com.loc.y.a(r1, r2, r3)
            r1.printStackTrace()
            goto L_0x00c2
        L_0x0139:
            r1 = move-exception
            java.lang.String r2 = "HttpUrlUtil"
            java.lang.String r3 = "parseResult"
            com.loc.y.a(r1, r2, r3)
            r1.printStackTrace()
            goto L_0x00c7
        L_0x0145:
            r0 = move-exception
            r2 = r1
            r3 = r1
            r4 = r1
            goto L_0x003b
        L_0x014b:
            r0 = move-exception
            r2 = r1
            r3 = r1
            goto L_0x003b
        L_0x0150:
            r0 = move-exception
            r2 = r1
            goto L_0x003b
        L_0x0154:
            r0 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
            goto L_0x003b
        L_0x015a:
            r0 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
            goto L_0x003b
        L_0x0160:
            r0 = move-exception
            r2 = r1
            r3 = r1
            goto L_0x0039
        L_0x0165:
            r0 = move-exception
            r2 = r1
            goto L_0x0039
        L_0x0169:
            r0 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ar.a(java.net.HttpURLConnection):com.loc.au");
    }

    private void a(Map<String, String> map, HttpURLConnection httpURLConnection) {
        if (map != null) {
            for (String next : map.keySet()) {
                httpURLConnection.addRequestProperty(next, map.get(next));
            }
        }
        try {
            httpURLConnection.addRequestProperty("csid", UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
        } catch (Throwable th) {
            y.a(th, "HttpUrlUtil", "addHeaders");
        }
        httpURLConnection.setConnectTimeout(this.b);
        httpURLConnection.setReadTimeout(this.c);
    }

    static String a(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry next : map.entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            if (str2 == null) {
                str2 = "";
            }
            if (sb.length() > 0) {
                sb.append(a.b);
            }
            sb.append(URLEncoder.encode(str));
            sb.append("=");
            sb.append(URLEncoder.encode(str2));
        }
        return sb.toString();
    }
}
