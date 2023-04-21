package com.baidu.mapapi.http;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.baidu.mapapi.JNIInitializer;
import com.baidu.mapapi.common.Logger;
import com.baidu.platform.comapi.util.PermissionCheck;
import com.baidu.platform.comapi.util.e;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpClient {
    public static boolean isHttpsEnable = false;
    HttpURLConnection a;
    private String b = null;
    private String c = null;
    private int d;
    private int e;
    private String f;
    private ProtoResultCallback g;

    public enum HttpStateError {
        NO_ERROR,
        NETWORK_ERROR,
        INNER_ERROR,
        REQUEST_ERROR,
        SERVER_ERROR
    }

    public static abstract class ProtoResultCallback {
        public abstract void onFailed(HttpStateError httpStateError);

        public abstract void onSuccess(String str);
    }

    public HttpClient(String str, ProtoResultCallback protoResultCallback) {
        this.f = str;
        this.g = protoResultCallback;
    }

    private HttpURLConnection a() {
        HttpURLConnection httpURLConnection;
        try {
            URL url = new URL(this.b);
            if (isHttpsEnable) {
                httpURLConnection = (HttpsURLConnection) url.openConnection();
                ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(new b(this));
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            httpURLConnection.setRequestMethod(this.f);
            httpURLConnection.setDoOutput(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(this.d);
            httpURLConnection.setReadTimeout(this.e);
            return httpURLConnection;
        } catch (Exception e2) {
            Log.e("HttpClient", "url connect failed");
            if (Logger.debugEnable()) {
                e2.printStackTrace();
            } else {
                Logger.logW("HttpClient", e2.getMessage());
            }
            return null;
        }
    }

    private void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("status") || jSONObject.has("status_sp")) {
                switch (jSONObject.has("status") ? jSONObject.getInt("status") : jSONObject.getInt("status_sp")) {
                    case 105:
                    case 106:
                        int permissionCheck = PermissionCheck.permissionCheck();
                        if (permissionCheck != 0) {
                            Log.e("HttpClient", "permissionCheck result is: " + permissionCheck);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        } catch (JSONException e2) {
            Log.e("HttpClient", "Parse json happened exception");
            e2.printStackTrace();
        }
    }

    public static String getAuthToken() {
        return e.z;
    }

    public static String getPhoneInfo() {
        return e.c();
    }

    /* access modifiers changed from: protected */
    public boolean checkNetwork() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) JNIInitializer.getCachedContext().getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
        } catch (Exception e2) {
            if (Logger.debugEnable()) {
                e2.printStackTrace();
            } else {
                Logger.logW("HttpClient", e2.getMessage());
            }
            e2.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:104:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0076 A[Catch:{ all -> 0x0165 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0095 A[Catch:{ Exception -> 0x009c }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x015a  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0176 A[Catch:{ Exception -> 0x009c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void request(java.lang.String r8) {
        /*
            r7 = this;
            r2 = 0
            r7.b = r8
            boolean r0 = r7.checkNetwork()
            if (r0 != 0) goto L_0x0011
            com.baidu.mapapi.http.HttpClient$ProtoResultCallback r0 = r7.g
            com.baidu.mapapi.http.HttpClient$HttpStateError r1 = com.baidu.mapapi.http.HttpClient.HttpStateError.NETWORK_ERROR
            r0.onFailed(r1)
        L_0x0010:
            return
        L_0x0011:
            java.net.HttpURLConnection r0 = r7.a()
            r7.a = r0
            java.net.HttpURLConnection r0 = r7.a
            if (r0 != 0) goto L_0x002b
            java.lang.String r0 = "HttpClient"
            java.lang.String r1 = "url connection failed"
            android.util.Log.e(r0, r1)
            com.baidu.mapapi.http.HttpClient$ProtoResultCallback r0 = r7.g
            com.baidu.mapapi.http.HttpClient$HttpStateError r1 = com.baidu.mapapi.http.HttpClient.HttpStateError.INNER_ERROR
            r0.onFailed(r1)
            goto L_0x0010
        L_0x002b:
            java.lang.String r0 = r7.b
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x003b
            com.baidu.mapapi.http.HttpClient$ProtoResultCallback r0 = r7.g
            com.baidu.mapapi.http.HttpClient$HttpStateError r1 = com.baidu.mapapi.http.HttpClient.HttpStateError.REQUEST_ERROR
            r0.onFailed(r1)
            goto L_0x0010
        L_0x003b:
            r3 = 0
            java.net.HttpURLConnection r0 = r7.a     // Catch:{ Exception -> 0x009c }
            r0.connect()     // Catch:{ Exception -> 0x009c }
            java.net.HttpURLConnection r0 = r7.a     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            int r1 = r0.getResponseCode()     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            r0 = 200(0xc8, float:2.8E-43)
            if (r0 != r1) goto L_0x00dd
            java.net.HttpURLConnection r0 = r7.a     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.io.InputStream r1 = r0.getInputStream()     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0195, all -> 0x018a }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0195, all -> 0x018a }
            java.lang.String r4 = "UTF-8"
            r0.<init>(r1, r4)     // Catch:{ Exception -> 0x0195, all -> 0x018a }
            r3.<init>(r0)     // Catch:{ Exception -> 0x0195, all -> 0x018a }
            java.lang.StringBuffer r0 = new java.lang.StringBuffer     // Catch:{ Exception -> 0x006e, all -> 0x018e }
            r0.<init>()     // Catch:{ Exception -> 0x006e, all -> 0x018e }
        L_0x0062:
            int r2 = r3.read()     // Catch:{ Exception -> 0x006e, all -> 0x018e }
            r4 = -1
            if (r2 == r4) goto L_0x00b6
            char r2 = (char) r2     // Catch:{ Exception -> 0x006e, all -> 0x018e }
            r0.append(r2)     // Catch:{ Exception -> 0x006e, all -> 0x018e }
            goto L_0x0062
        L_0x006e:
            r0 = move-exception
            r2 = r3
        L_0x0070:
            boolean r3 = com.baidu.mapapi.common.Logger.debugEnable()     // Catch:{ all -> 0x0165 }
            if (r3 == 0) goto L_0x015a
            r0.printStackTrace()     // Catch:{ all -> 0x0165 }
        L_0x0079:
            java.lang.String r0 = "HttpClient"
            java.lang.String r3 = "Catch exception. INNER_ERROR"
            android.util.Log.e(r0, r3)     // Catch:{ all -> 0x0165 }
            com.baidu.mapapi.http.HttpClient$ProtoResultCallback r0 = r7.g     // Catch:{ all -> 0x0165 }
            com.baidu.mapapi.http.HttpClient$HttpStateError r3 = com.baidu.mapapi.http.HttpClient.HttpStateError.INNER_ERROR     // Catch:{ all -> 0x0165 }
            r0.onFailed(r3)     // Catch:{ all -> 0x0165 }
            if (r1 == 0) goto L_0x0091
            if (r2 == 0) goto L_0x0091
            r2.close()     // Catch:{ Exception -> 0x009c }
            r1.close()     // Catch:{ Exception -> 0x009c }
        L_0x0091:
            java.net.HttpURLConnection r0 = r7.a     // Catch:{ Exception -> 0x009c }
            if (r0 == 0) goto L_0x0010
            java.net.HttpURLConnection r0 = r7.a     // Catch:{ Exception -> 0x009c }
            r0.disconnect()     // Catch:{ Exception -> 0x009c }
            goto L_0x0010
        L_0x009c:
            r0 = move-exception
            boolean r1 = com.baidu.mapapi.common.Logger.debugEnable()
            if (r1 == 0) goto L_0x017c
            r0.printStackTrace()
        L_0x00a6:
            java.lang.String r0 = "HttpClient"
            java.lang.String r1 = "Catch connection exception, INNER_ERROR"
            android.util.Log.e(r0, r1)
            com.baidu.mapapi.http.HttpClient$ProtoResultCallback r0 = r7.g
            com.baidu.mapapi.http.HttpClient$HttpStateError r1 = com.baidu.mapapi.http.HttpClient.HttpStateError.INNER_ERROR
            r0.onFailed(r1)
            goto L_0x0010
        L_0x00b6:
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x006e, all -> 0x018e }
            r7.c = r0     // Catch:{ Exception -> 0x006e, all -> 0x018e }
            java.lang.String r0 = r7.c     // Catch:{ Exception -> 0x006e, all -> 0x018e }
            r7.a(r0)     // Catch:{ Exception -> 0x006e, all -> 0x018e }
            if (r1 == 0) goto L_0x00cb
            if (r3 == 0) goto L_0x00cb
            r3.close()     // Catch:{ Exception -> 0x009c }
            r1.close()     // Catch:{ Exception -> 0x009c }
        L_0x00cb:
            java.net.HttpURLConnection r0 = r7.a     // Catch:{ Exception -> 0x009c }
            if (r0 == 0) goto L_0x00d4
            java.net.HttpURLConnection r0 = r7.a     // Catch:{ Exception -> 0x009c }
            r0.disconnect()     // Catch:{ Exception -> 0x009c }
        L_0x00d4:
            com.baidu.mapapi.http.HttpClient$ProtoResultCallback r0 = r7.g
            java.lang.String r1 = r7.c
            r0.onSuccess(r1)
            goto L_0x0010
        L_0x00dd:
            java.lang.String r0 = "HttpClient"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            r4.<init>()     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.lang.String r5 = "responseCode is: "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            android.util.Log.e(r0, r4)     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            com.baidu.mapapi.http.HttpClient$HttpStateError r0 = com.baidu.mapapi.http.HttpClient.HttpStateError.NO_ERROR     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            r0 = 500(0x1f4, float:7.0E-43)
            if (r1 < r0) goto L_0x012c
            com.baidu.mapapi.http.HttpClient$HttpStateError r0 = com.baidu.mapapi.http.HttpClient.HttpStateError.SERVER_ERROR     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
        L_0x00fd:
            boolean r4 = com.baidu.mapapi.common.Logger.debugEnable()     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            if (r4 == 0) goto L_0x0136
            java.net.HttpURLConnection r1 = r7.a     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.io.InputStream r1 = r1.getErrorStream()     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.lang.String r4 = "HttpClient"
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x0195, all -> 0x018a }
            com.baidu.mapapi.common.Logger.logW(r4, r5)     // Catch:{ Exception -> 0x0195, all -> 0x018a }
        L_0x0112:
            com.baidu.mapapi.http.HttpClient$ProtoResultCallback r4 = r7.g     // Catch:{ Exception -> 0x0195, all -> 0x018a }
            r4.onFailed(r0)     // Catch:{ Exception -> 0x0195, all -> 0x018a }
            if (r1 == 0) goto L_0x0121
            if (r2 == 0) goto L_0x0121
            r3.close()     // Catch:{ Exception -> 0x009c }
            r1.close()     // Catch:{ Exception -> 0x009c }
        L_0x0121:
            java.net.HttpURLConnection r0 = r7.a     // Catch:{ Exception -> 0x009c }
            if (r0 == 0) goto L_0x0010
            java.net.HttpURLConnection r0 = r7.a     // Catch:{ Exception -> 0x009c }
            r0.disconnect()     // Catch:{ Exception -> 0x009c }
            goto L_0x0010
        L_0x012c:
            r0 = 400(0x190, float:5.6E-43)
            if (r1 < r0) goto L_0x0133
            com.baidu.mapapi.http.HttpClient$HttpStateError r0 = com.baidu.mapapi.http.HttpClient.HttpStateError.REQUEST_ERROR     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            goto L_0x00fd
        L_0x0133:
            com.baidu.mapapi.http.HttpClient$HttpStateError r0 = com.baidu.mapapi.http.HttpClient.HttpStateError.INNER_ERROR     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            goto L_0x00fd
        L_0x0136:
            java.lang.String r4 = "HttpClient"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            r5.<init>()     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.lang.String r6 = "Get response from server failed, http response code="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.lang.StringBuilder r1 = r5.append(r1)     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.lang.String r5 = ", error="
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            com.baidu.mapapi.common.Logger.logW(r4, r1)     // Catch:{ Exception -> 0x0191, all -> 0x0187 }
            r1 = r2
            goto L_0x0112
        L_0x015a:
            java.lang.String r3 = "HttpClient"
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0165 }
            com.baidu.mapapi.common.Logger.logW(r3, r0)     // Catch:{ all -> 0x0165 }
            goto L_0x0079
        L_0x0165:
            r0 = move-exception
            r3 = r2
            r2 = r1
        L_0x0168:
            if (r2 == 0) goto L_0x0172
            if (r3 == 0) goto L_0x0172
            r3.close()     // Catch:{ Exception -> 0x009c }
            r2.close()     // Catch:{ Exception -> 0x009c }
        L_0x0172:
            java.net.HttpURLConnection r1 = r7.a     // Catch:{ Exception -> 0x009c }
            if (r1 == 0) goto L_0x017b
            java.net.HttpURLConnection r1 = r7.a     // Catch:{ Exception -> 0x009c }
            r1.disconnect()     // Catch:{ Exception -> 0x009c }
        L_0x017b:
            throw r0     // Catch:{ Exception -> 0x009c }
        L_0x017c:
            java.lang.String r1 = "HttpClient"
            java.lang.String r0 = r0.getMessage()
            com.baidu.mapapi.common.Logger.logW(r1, r0)
            goto L_0x00a6
        L_0x0187:
            r0 = move-exception
            r3 = r2
            goto L_0x0168
        L_0x018a:
            r0 = move-exception
            r3 = r2
            r2 = r1
            goto L_0x0168
        L_0x018e:
            r0 = move-exception
            r2 = r1
            goto L_0x0168
        L_0x0191:
            r0 = move-exception
            r1 = r2
            goto L_0x0070
        L_0x0195:
            r0 = move-exception
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.mapapi.http.HttpClient.request(java.lang.String):void");
    }

    public void setMaxTimeOut(int i) {
        this.d = i;
    }

    public void setReadTimeOut(int i) {
        this.e = i;
    }
}
