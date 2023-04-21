package com.mob.tools.network;

import android.content.Context;
import android.os.Build;
import com.alipay.sdk.util.h;
import com.facebook.common.util.UriUtil;
import com.mob.tools.MobLog;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;

public class NetworkHelper {
    public static int connectionTimeout;
    public static int readTimout;

    public static class NetworkTimeOut {
        public int connectionTimeout;
        public int readTimout;
    }

    public String httpGet(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("httpGet: " + url, new Object[0]);
        if (values != null) {
            String param = kvPairsToUrl(values);
            if (param.length() > 0) {
                url = url + "?" + param;
            }
        }
        HttpURLConnection conn = getConnection(url, timeout);
        if (headers != null) {
            Iterator<KVPair<String>> it = headers.iterator();
            while (it.hasNext()) {
                KVPair<String> header = it.next();
                conn.setRequestProperty(header.name, (String) header.value);
            }
        }
        conn.connect();
        int status = conn.getResponseCode();
        if (status == 200) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
            for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
                if (sb.length() > 0) {
                    sb.append(10);
                }
                sb.append(txt);
            }
            br.close();
            conn.disconnect();
            String resp = sb.toString();
            MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
            return resp;
        }
        StringBuilder sb2 = new StringBuilder();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
        for (String txt2 = br2.readLine(); txt2 != null; txt2 = br2.readLine()) {
            if (sb2.length() > 0) {
                sb2.append(10);
            }
            sb2.append(txt2);
        }
        br2.close();
        conn.disconnect();
        HashMap<String, Object> errMap = new HashMap<>();
        errMap.put("error", sb2.toString());
        errMap.put("status", Integer.valueOf(status));
        throw new Throwable(new Hashon().fromHashMap(errMap));
    }

    public String downloadCache(Context context, String url, String cacheFolder, boolean skipIfCached, NetworkTimeOut timeout) throws Throwable {
        return downloadCache(context, url, cacheFolder, skipIfCached, timeout, (FileDownloadListener) null);
    }

    public String downloadCache(Context context, String url, String cacheFolder, boolean skipIfCached, NetworkTimeOut timeout, FileDownloadListener downloadListener) throws Throwable {
        List<String> headers;
        int dot;
        List<String> headers2;
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("downloading: " + url, new Object[0]);
        if (skipIfCached) {
            File cache = new File(ResHelper.getCachePath(context, cacheFolder), Data.MD5(url));
            if (skipIfCached && cache.exists()) {
                MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
                if (downloadListener != null) {
                    downloadListener.onProgress(100, cache.length(), cache.length());
                }
                return cache.getAbsolutePath();
            }
        }
        HttpURLConnection conn = getConnection(url, timeout);
        conn.connect();
        int status = conn.getResponseCode();
        if (status == 200) {
            String name = null;
            Map<String, List<String>> map = conn.getHeaderFields();
            if (!(map == null || (headers2 = map.get("Content-Disposition")) == null || headers2.size() <= 0)) {
                for (String part : headers2.get(0).split(h.b)) {
                    if (part.trim().startsWith("filename")) {
                        name = part.split("=")[1];
                        if (name.startsWith("\"") && name.endsWith("\"")) {
                            name = name.substring(1, name.length() - 1);
                        }
                    }
                }
            }
            if (name == null) {
                name = Data.MD5(url);
                if (!(map == null || (headers = map.get("Content-Type")) == null || headers.size() <= 0)) {
                    String value = headers.get(0);
                    String value2 = value == null ? "" : value.trim();
                    if (value2.startsWith("image/")) {
                        String type = value2.substring("image/".length());
                        StringBuilder append = new StringBuilder().append(name).append(".");
                        if ("jpeg".equals(type)) {
                            type = "jpg";
                        }
                        name = append.append(type).toString();
                    } else {
                        int index = url.lastIndexOf(47);
                        String lastPart = null;
                        if (index > 0) {
                            lastPart = url.substring(index + 1);
                        }
                        if (lastPart != null && lastPart.length() > 0 && (dot = lastPart.lastIndexOf(46)) > 0 && lastPart.length() - dot < 10) {
                            name = name + lastPart.substring(dot);
                        }
                    }
                }
            }
            File cache2 = new File(ResHelper.getCachePath(context, cacheFolder), name);
            if (!skipIfCached || !cache2.exists()) {
                if (!cache2.getParentFile().exists()) {
                    cache2.getParentFile().mkdirs();
                }
                if (cache2.exists()) {
                    cache2.delete();
                }
                if (downloadListener != null) {
                    try {
                        if (downloadListener.isCanceled()) {
                            return null;
                        }
                    } finally {
                        if (cache2.exists()) {
                            cache2.delete();
                        }
                    }
                }
                InputStream is = conn.getInputStream();
                int contentLength = conn.getContentLength();
                FileOutputStream fileOutputStream = new FileOutputStream(cache2);
                byte[] buf = new byte[1024];
                int downloadLength = 0;
                for (int len = is.read(buf); len > 0; len = is.read(buf)) {
                    fileOutputStream.write(buf, 0, len);
                    downloadLength += len;
                    if (downloadListener != null) {
                        downloadListener.onProgress(contentLength <= 0 ? 100 : (downloadLength * 100) / contentLength, (long) downloadLength, (long) contentLength);
                        if (downloadListener.isCanceled()) {
                            break;
                        }
                    }
                }
                if (downloadListener != null) {
                    if (downloadListener.isCanceled()) {
                        if (cache2.exists()) {
                            cache2.delete();
                        }
                        fileOutputStream.flush();
                        is.close();
                        fileOutputStream.close();
                        return null;
                    }
                    downloadListener.onProgress(100, cache2.length(), cache2.length());
                }
                fileOutputStream.flush();
                is.close();
                fileOutputStream.close();
                conn.disconnect();
                MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
                return cache2.getAbsolutePath();
            }
            conn.disconnect();
            MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
            if (downloadListener != null) {
                downloadListener.onProgress(100, cache2.length(), cache2.length());
            }
            return cache2.getAbsolutePath();
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
        for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
            if (sb.length() > 0) {
                sb.append(10);
            }
            sb.append(txt);
        }
        br.close();
        conn.disconnect();
        HashMap<String, Object> errMap = new HashMap<>();
        errMap.put("error", sb.toString());
        errMap.put("status", Integer.valueOf(status));
        throw new Throwable(new Hashon().fromHashMap(errMap));
    }

    public void rawGet(String url, RawNetworkCallback callback, NetworkTimeOut timeout) throws Throwable {
        rawGet(url, (ArrayList<KVPair<String>>) null, callback, timeout);
    }

    public void rawGet(String url, ArrayList<KVPair<String>> headers, RawNetworkCallback callback, NetworkTimeOut timeout) throws Throwable {
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("rawGet: " + url, new Object[0]);
        HttpURLConnection conn = getConnection(url, timeout);
        if (headers != null) {
            Iterator<KVPair<String>> it = headers.iterator();
            while (it.hasNext()) {
                KVPair<String> header = it.next();
                conn.setRequestProperty(header.name, (String) header.value);
            }
        }
        conn.connect();
        int status = conn.getResponseCode();
        if (status == 200) {
            if (callback != null) {
                callback.onResponse(conn.getInputStream());
            }
            conn.disconnect();
            MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
            return;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
        for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
            if (sb.length() > 0) {
                sb.append(10);
            }
            sb.append(txt);
        }
        br.close();
        conn.disconnect();
        HashMap<String, Object> errMap = new HashMap<>();
        errMap.put("error", sb.toString());
        errMap.put("status", Integer.valueOf(status));
        throw new Throwable(new Hashon().fromHashMap(errMap));
    }

    public void rawGet(String url, HttpResponseCallback callback, NetworkTimeOut timeout) throws Throwable {
        rawGet(url, (ArrayList<KVPair<String>>) null, callback, timeout);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0076, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0077, code lost:
        r0.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x007a, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rawGet(java.lang.String r12, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r13, com.mob.tools.network.HttpResponseCallback r14, com.mob.tools.network.NetworkHelper.NetworkTimeOut r15) throws java.lang.Throwable {
        /*
            r11 = this;
            r10 = 0
            long r4 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "rawGet: "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r12)
            java.lang.String r6 = r6.toString()
            java.lang.Object[] r7 = new java.lang.Object[r10]
            r3.i(r6, r7)
            java.net.HttpURLConnection r0 = r11.getConnection(r12, r15)
            if (r13 == 0) goto L_0x0041
            java.util.Iterator r6 = r13.iterator()
        L_0x002b:
            boolean r3 = r6.hasNext()
            if (r3 == 0) goto L_0x0041
            java.lang.Object r1 = r6.next()
            com.mob.tools.network.KVPair r1 = (com.mob.tools.network.KVPair) r1
            java.lang.String r7 = r1.name
            T r3 = r1.value
            java.lang.String r3 = (java.lang.String) r3
            r0.setRequestProperty(r7, r3)
            goto L_0x002b
        L_0x0041:
            r0.connect()
            if (r14 == 0) goto L_0x007b
            com.mob.tools.network.HttpConnectionImpl23 r3 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x0074 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x0074 }
            r14.onResponse(r3)     // Catch:{ Throwable -> 0x0074 }
            r0.disconnect()
        L_0x0051:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "use time: "
            java.lang.StringBuilder r6 = r6.append(r7)
            long r8 = java.lang.System.currentTimeMillis()
            long r8 = r8 - r4
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            java.lang.Object[] r7 = new java.lang.Object[r10]
            r3.i(r6, r7)
            return
        L_0x0074:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0076 }
        L_0x0076:
            r3 = move-exception
            r0.disconnect()
            throw r3
        L_0x007b:
            r0.disconnect()
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.rawGet(java.lang.String, java.util.ArrayList, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    public String jsonPost(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        final HashMap<String, String> map = new HashMap<>();
        jsonPost(url, values, headers, timeout, (HttpResponseCallback) new HttpResponseCallback() {
            public void onResponse(HttpConnection conn) throws Throwable {
                int status = conn.getResponseCode();
                if (status == 200 || status == 201) {
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
                    for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
                        if (sb.length() > 0) {
                            sb.append(10);
                        }
                        sb.append(txt);
                    }
                    br.close();
                    map.put(UriUtil.LOCAL_RESOURCE_SCHEME, sb.toString());
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
                for (String txt2 = br2.readLine(); txt2 != null; txt2 = br2.readLine()) {
                    if (sb2.length() > 0) {
                        sb2.append(10);
                    }
                    sb2.append(txt2);
                }
                br2.close();
                HashMap<String, Object> errMap = new HashMap<>();
                errMap.put("error", sb2.toString());
                errMap.put("status", Integer.valueOf(status));
                throw new Throwable(new Hashon().fromHashMap(errMap));
            }
        });
        if (map.containsKey(UriUtil.LOCAL_RESOURCE_SCHEME)) {
            return map.get(UriUtil.LOCAL_RESOURCE_SCHEME);
        }
        return null;
    }

    public void jsonPost(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout, HttpResponseCallback callback) throws Throwable {
        HashMap<String, Object> valuesMap = new HashMap<>();
        Iterator<KVPair<String>> it = values.iterator();
        while (it.hasNext()) {
            KVPair<String> p = it.next();
            valuesMap.put(p.name, p.value);
        }
        jsonPost(url, valuesMap, headers, timeout, callback);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00d8, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00d9, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00dc, code lost:
        throw r14;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void jsonPost(java.lang.String r19, java.util.HashMap<java.lang.String, java.lang.Object> r20, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r21, com.mob.tools.network.NetworkHelper.NetworkTimeOut r22, com.mob.tools.network.HttpResponseCallback r23) throws java.lang.Throwable {
        /*
            r18 = this;
            long r12 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r14 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "jsonPost: "
            java.lang.StringBuilder r15 = r15.append(r16)
            r0 = r19
            java.lang.StringBuilder r15 = r15.append(r0)
            java.lang.String r15 = r15.toString()
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r14.i(r15, r16)
            r0 = r18
            r1 = r19
            r2 = r22
            java.net.HttpURLConnection r5 = r0.getConnection(r1, r2)
            r14 = 1
            r5.setDoOutput(r14)
            r14 = 0
            r5.setChunkedStreamingMode(r14)
            java.lang.String r14 = "content-type"
            java.lang.String r15 = "application/json"
            r5.setRequestProperty(r14, r15)
            if (r21 == 0) goto L_0x0061
            java.util.Iterator r15 = r21.iterator()
        L_0x0047:
            boolean r14 = r15.hasNext()
            if (r14 == 0) goto L_0x0061
            java.lang.Object r6 = r15.next()
            com.mob.tools.network.KVPair r6 = (com.mob.tools.network.KVPair) r6
            java.lang.String r0 = r6.name
            r16 = r0
            T r14 = r6.value
            java.lang.String r14 = (java.lang.String) r14
            r0 = r16
            r5.setRequestProperty(r0, r14)
            goto L_0x0047
        L_0x0061:
            com.mob.tools.network.StringPart r10 = new com.mob.tools.network.StringPart
            r10.<init>()
            if (r20 == 0) goto L_0x0076
            com.mob.tools.utils.Hashon r14 = new com.mob.tools.utils.Hashon
            r14.<init>()
            r0 = r20
            java.lang.String r14 = r14.fromHashMap(r0)
            r10.append(r14)
        L_0x0076:
            r5.connect()
            java.io.OutputStream r9 = r5.getOutputStream()
            java.io.InputStream r7 = r10.toInputStream()
            r14 = 65536(0x10000, float:9.18355E-41)
            byte[] r4 = new byte[r14]
            int r8 = r7.read(r4)
        L_0x0089:
            if (r8 <= 0) goto L_0x0094
            r14 = 0
            r9.write(r4, r14, r8)
            int r8 = r7.read(r4)
            goto L_0x0089
        L_0x0094:
            r9.flush()
            r7.close()
            r9.close()
            if (r23 == 0) goto L_0x00dd
            com.mob.tools.network.HttpConnectionImpl23 r14 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x00d6 }
            r14.<init>(r5)     // Catch:{ Throwable -> 0x00d6 }
            r0 = r23
            r0.onResponse(r14)     // Catch:{ Throwable -> 0x00d6 }
            r5.disconnect()
        L_0x00ac:
            com.mob.tools.log.NLog r14 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "use time: "
            java.lang.StringBuilder r15 = r15.append(r16)
            long r16 = java.lang.System.currentTimeMillis()
            long r16 = r16 - r12
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r15 = r15.toString()
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r14.i(r15, r16)
            return
        L_0x00d6:
            r11 = move-exception
            throw r11     // Catch:{ all -> 0x00d8 }
        L_0x00d8:
            r14 = move-exception
            r5.disconnect()
            throw r14
        L_0x00dd:
            r5.disconnect()
            goto L_0x00ac
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.jsonPost(java.lang.String, java.util.HashMap, java.util.ArrayList, com.mob.tools.network.NetworkHelper$NetworkTimeOut, com.mob.tools.network.HttpResponseCallback):void");
    }

    public String httpPost(String url, ArrayList<KVPair<String>> values, KVPair<String> file, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        return httpPost(url, values, file, headers, 0, timeout);
    }

    public String httpPost(String url, ArrayList<KVPair<String>> values, KVPair<String> file, ArrayList<KVPair<String>> headers, int chunkLength, NetworkTimeOut timeout) throws Throwable {
        ArrayList<KVPair<String>> files = new ArrayList<>();
        if (!(file == null || file.value == null || !new File((String) file.value).exists())) {
            files.add(file);
        }
        return httpPostFiles(url, values, files, headers, chunkLength, timeout);
    }

    public String httpPostFiles(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> files, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        return httpPostFiles(url, values, files, headers, 0, timeout);
    }

    public String httpPostFiles(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> files, ArrayList<KVPair<String>> headers, int chunkLength, NetworkTimeOut timeout) throws Throwable {
        final HashMap<String, String> tmpMap = new HashMap<>();
        httpPost(url, values, files, headers, chunkLength, new HttpResponseCallback() {
            public void onResponse(HttpConnection conn) throws Throwable {
                int status = conn.getResponseCode();
                if (status == 200 || status < 300) {
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
                    for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
                        if (sb.length() > 0) {
                            sb.append(10);
                        }
                        sb.append(txt);
                    }
                    br.close();
                    tmpMap.put("resp", sb.toString());
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
                for (String txt2 = br2.readLine(); txt2 != null; txt2 = br2.readLine()) {
                    if (sb2.length() > 0) {
                        sb2.append(10);
                    }
                    sb2.append(txt2);
                }
                br2.close();
                HashMap<String, Object> errMap = new HashMap<>();
                errMap.put("error", sb2.toString());
                errMap.put("status", Integer.valueOf(status));
                throw new Throwable(new Hashon().fromHashMap(errMap));
            }
        }, timeout);
        return tmpMap.get("resp");
    }

    public void httpPost(String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> files, ArrayList<KVPair<String>> headers, HttpResponseCallback callback, NetworkTimeOut timeout) throws Throwable {
        httpPost(url, values, files, headers, 0, callback, timeout);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ed, code lost:
        r14 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ee, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00f1, code lost:
        throw r14;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void httpPost(java.lang.String r19, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r20, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r21, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r22, int r23, com.mob.tools.network.HttpResponseCallback r24, com.mob.tools.network.NetworkHelper.NetworkTimeOut r25) throws java.lang.Throwable {
        /*
            r18 = this;
            long r12 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r14 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "httpPost: "
            java.lang.StringBuilder r15 = r15.append(r16)
            r0 = r19
            java.lang.StringBuilder r15 = r15.append(r0)
            java.lang.String r15 = r15.toString()
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r14.i(r15, r16)
            r0 = r18
            r1 = r19
            r2 = r25
            java.net.HttpURLConnection r5 = r0.getConnection(r1, r2)
            r14 = 1
            r5.setDoOutput(r14)
            java.lang.String r14 = "Connection"
            java.lang.String r15 = "Keep-Alive"
            r5.setRequestProperty(r14, r15)
            if (r21 == 0) goto L_0x0078
            int r14 = r21.size()
            if (r14 <= 0) goto L_0x0078
            r0 = r18
            r1 = r19
            r2 = r20
            r3 = r21
            com.mob.tools.network.HTTPPart r10 = r0.getFilePostHTTPPart(r5, r1, r2, r3)
            if (r23 < 0) goto L_0x0058
            r0 = r23
            r5.setChunkedStreamingMode(r0)
        L_0x0058:
            if (r22 == 0) goto L_0x008b
            java.util.Iterator r15 = r22.iterator()
        L_0x005e:
            boolean r14 = r15.hasNext()
            if (r14 == 0) goto L_0x008b
            java.lang.Object r6 = r15.next()
            com.mob.tools.network.KVPair r6 = (com.mob.tools.network.KVPair) r6
            java.lang.String r0 = r6.name
            r16 = r0
            T r14 = r6.value
            java.lang.String r14 = (java.lang.String) r14
            r0 = r16
            r5.setRequestProperty(r0, r14)
            goto L_0x005e
        L_0x0078:
            r0 = r18
            r1 = r19
            r2 = r20
            com.mob.tools.network.HTTPPart r10 = r0.getTextPostHTTPPart(r5, r1, r2)
            long r14 = r10.length()
            int r14 = (int) r14
            r5.setFixedLengthStreamingMode(r14)
            goto L_0x0058
        L_0x008b:
            r5.connect()
            java.io.OutputStream r9 = r5.getOutputStream()
            java.io.InputStream r7 = r10.toInputStream()
            r14 = 65536(0x10000, float:9.18355E-41)
            byte[] r4 = new byte[r14]
            int r8 = r7.read(r4)
        L_0x009e:
            if (r8 <= 0) goto L_0x00a9
            r14 = 0
            r9.write(r4, r14, r8)
            int r8 = r7.read(r4)
            goto L_0x009e
        L_0x00a9:
            r9.flush()
            r7.close()
            r9.close()
            if (r24 == 0) goto L_0x00f2
            com.mob.tools.network.HttpConnectionImpl23 r14 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x00eb }
            r14.<init>(r5)     // Catch:{ Throwable -> 0x00eb }
            r0 = r24
            r0.onResponse(r14)     // Catch:{ Throwable -> 0x00eb }
            r5.disconnect()
        L_0x00c1:
            com.mob.tools.log.NLog r14 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r16 = "use time: "
            java.lang.StringBuilder r15 = r15.append(r16)
            long r16 = java.lang.System.currentTimeMillis()
            long r16 = r16 - r12
            java.lang.StringBuilder r15 = r15.append(r16)
            java.lang.String r15 = r15.toString()
            r16 = 0
            r0 = r16
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r16 = r0
            r14.i(r15, r16)
            return
        L_0x00eb:
            r11 = move-exception
            throw r11     // Catch:{ all -> 0x00ed }
        L_0x00ed:
            r14 = move-exception
            r5.disconnect()
            throw r14
        L_0x00f2:
            r5.disconnect()
            goto L_0x00c1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.httpPost(java.lang.String, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList, int, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    private HTTPPart getFilePostHTTPPart(HttpURLConnection conn, String url, ArrayList<KVPair<String>> values, ArrayList<KVPair<String>> files) throws Throwable {
        String boundary = UUID.randomUUID().toString();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        MultiPart mp = new MultiPart();
        StringPart sp = new StringPart();
        if (values != null) {
            Iterator<KVPair<String>> it = values.iterator();
            while (it.hasNext()) {
                KVPair<String> value = it.next();
                sp.append("--").append(boundary).append("\r\n");
                sp.append("Content-Disposition: form-data; name=\"").append(value.name).append("\"\r\n\r\n");
                sp.append((String) value.value).append("\r\n");
            }
        }
        mp.append(sp);
        Iterator<KVPair<String>> it2 = files.iterator();
        while (it2.hasNext()) {
            KVPair<String> file = it2.next();
            StringPart sp2 = new StringPart();
            File imageFile = new File((String) file.value);
            sp2.append("--").append(boundary).append("\r\n");
            sp2.append("Content-Disposition: form-data; name=\"").append(file.name).append("\"; filename=\"").append(imageFile.getName()).append("\"\r\n");
            String mime = URLConnection.getFileNameMap().getContentTypeFor((String) file.value);
            if (mime == null || mime.length() <= 0) {
                if (((String) file.value).toLowerCase().endsWith("jpg") || ((String) file.value).toLowerCase().endsWith("jpeg")) {
                    mime = "image/jpeg";
                } else if (((String) file.value).toLowerCase().endsWith("png")) {
                    mime = "image/png";
                } else if (((String) file.value).toLowerCase().endsWith("gif")) {
                    mime = "image/gif";
                } else {
                    FileInputStream fis = new FileInputStream((String) file.value);
                    mime = URLConnection.guessContentTypeFromStream(fis);
                    fis.close();
                    if (mime == null || mime.length() <= 0) {
                        mime = "application/octet-stream";
                    }
                }
            }
            sp2.append("Content-Type: ").append(mime).append("\r\n\r\n");
            mp.append(sp2);
            FilePart fp = new FilePart();
            fp.setFile((String) file.value);
            mp.append(fp);
            StringPart sp3 = new StringPart();
            sp3.append("\r\n");
            mp.append(sp3);
        }
        StringPart sp4 = new StringPart();
        sp4.append("--").append(boundary).append("--\r\n");
        mp.append(sp4);
        return mp;
    }

    private HTTPPart getTextPostHTTPPart(HttpURLConnection conn, String url, ArrayList<KVPair<String>> values) throws Throwable {
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        StringPart sp = new StringPart();
        if (values != null) {
            sp.append(kvPairsToUrl(values));
        }
        return sp;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00db, code lost:
        r20 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00dc, code lost:
        if (r9 != null) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00e1, code lost:
        r6.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00e4, code lost:
        throw r20;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rawPost(java.lang.String r25, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r26, com.mob.tools.network.HTTPPart r27, com.mob.tools.network.RawNetworkCallback r28, com.mob.tools.network.NetworkHelper.NetworkTimeOut r29) throws java.lang.Throwable {
        /*
            r24 = this;
            long r18 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r20 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            r21.<init>()
            java.lang.String r22 = "rawpost: "
            java.lang.StringBuilder r21 = r21.append(r22)
            r0 = r21
            r1 = r25
            java.lang.StringBuilder r21 = r0.append(r1)
            java.lang.String r21 = r21.toString()
            r22 = 0
            r0 = r22
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r22 = r0
            r20.i(r21, r22)
            r0 = r24
            r1 = r25
            r2 = r29
            java.net.HttpURLConnection r6 = r0.getConnection(r1, r2)
            r20 = 1
            r0 = r20
            r6.setDoOutput(r0)
            r20 = 0
            r0 = r20
            r6.setChunkedStreamingMode(r0)
            if (r26 == 0) goto L_0x0066
            java.util.Iterator r21 = r26.iterator()
        L_0x0048:
            boolean r20 = r21.hasNext()
            if (r20 == 0) goto L_0x0066
            java.lang.Object r8 = r21.next()
            com.mob.tools.network.KVPair r8 = (com.mob.tools.network.KVPair) r8
            java.lang.String r0 = r8.name
            r22 = r0
            T r0 = r8.value
            r20 = r0
            java.lang.String r20 = (java.lang.String) r20
            r0 = r22
            r1 = r20
            r6.setRequestProperty(r0, r1)
            goto L_0x0048
        L_0x0066:
            r6.connect()
            java.io.OutputStream r13 = r6.getOutputStream()
            java.io.InputStream r10 = r27.toInputStream()
            r20 = 65536(0x10000, float:9.18355E-41)
            r0 = r20
            byte[] r5 = new byte[r0]
            int r12 = r10.read(r5)
        L_0x007b:
            if (r12 <= 0) goto L_0x0089
            r20 = 0
            r0 = r20
            r13.write(r5, r0, r12)
            int r12 = r10.read(r5)
            goto L_0x007b
        L_0x0089:
            r13.flush()
            r10.close()
            r13.close()
            int r15 = r6.getResponseCode()
            r20 = 200(0xc8, float:2.8E-43)
            r0 = r20
            if (r15 != r0) goto L_0x00e9
            if (r28 == 0) goto L_0x00e5
            java.io.InputStream r9 = r6.getInputStream()
            r0 = r28
            r0.onResponse(r9)     // Catch:{ Throwable -> 0x00d9 }
            if (r9 == 0) goto L_0x00ac
            r9.close()     // Catch:{ Throwable -> 0x015a }
        L_0x00ac:
            r6.disconnect()
        L_0x00af:
            com.mob.tools.log.NLog r20 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            r21.<init>()
            java.lang.String r22 = "use time: "
            java.lang.StringBuilder r21 = r21.append(r22)
            long r22 = java.lang.System.currentTimeMillis()
            long r22 = r22 - r18
            java.lang.StringBuilder r21 = r21.append(r22)
            java.lang.String r21 = r21.toString()
            r22 = 0
            r0 = r22
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r22 = r0
            r20.i(r21, r22)
            return
        L_0x00d9:
            r16 = move-exception
            throw r16     // Catch:{ all -> 0x00db }
        L_0x00db:
            r20 = move-exception
            if (r9 == 0) goto L_0x00e1
            r9.close()     // Catch:{ Throwable -> 0x015d }
        L_0x00e1:
            r6.disconnect()
            throw r20
        L_0x00e5:
            r6.disconnect()
            goto L_0x00af
        L_0x00e9:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.io.InputStreamReader r11 = new java.io.InputStreamReader
            java.io.InputStream r20 = r6.getErrorStream()
            java.lang.String r21 = "utf-8"
            java.nio.charset.Charset r21 = java.nio.charset.Charset.forName(r21)
            r0 = r20
            r1 = r21
            r11.<init>(r0, r1)
            java.io.BufferedReader r4 = new java.io.BufferedReader
            r4.<init>(r11)
            java.lang.String r17 = r4.readLine()
        L_0x010b:
            if (r17 == 0) goto L_0x0124
            int r20 = r14.length()
            if (r20 <= 0) goto L_0x011a
            r20 = 10
            r0 = r20
            r14.append(r0)
        L_0x011a:
            r0 = r17
            r14.append(r0)
            java.lang.String r17 = r4.readLine()
            goto L_0x010b
        L_0x0124:
            r4.close()
            r6.disconnect()
            java.util.HashMap r7 = new java.util.HashMap
            r7.<init>()
            java.lang.String r20 = "error"
            java.lang.String r21 = r14.toString()
            r0 = r20
            r1 = r21
            r7.put(r0, r1)
            java.lang.String r20 = "status"
            java.lang.Integer r21 = java.lang.Integer.valueOf(r15)
            r0 = r20
            r1 = r21
            r7.put(r0, r1)
            java.lang.Throwable r20 = new java.lang.Throwable
            com.mob.tools.utils.Hashon r21 = new com.mob.tools.utils.Hashon
            r21.<init>()
            r0 = r21
            java.lang.String r21 = r0.fromHashMap(r7)
            r20.<init>(r21)
            throw r20
        L_0x015a:
            r20 = move-exception
            goto L_0x00ac
        L_0x015d:
            r21 = move-exception
            goto L_0x00e1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.rawPost(java.lang.String, java.util.ArrayList, com.mob.tools.network.HTTPPart, com.mob.tools.network.RawNetworkCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    public void rawPost(String url, ArrayList<KVPair<String>> headers, HTTPPart data, HttpResponseCallback callback, NetworkTimeOut timeout) throws Throwable {
        rawPost(url, headers, data, 0, callback, timeout);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00b2, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00b3, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b6, code lost:
        throw r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void rawPost(java.lang.String r19, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r20, com.mob.tools.network.HTTPPart r21, int r22, com.mob.tools.network.HttpResponseCallback r23, com.mob.tools.network.NetworkHelper.NetworkTimeOut r24) throws java.lang.Throwable {
        /*
            r18 = this;
            long r12 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r11 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "rawpost: "
            java.lang.StringBuilder r14 = r14.append(r15)
            r0 = r19
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r14 = r14.toString()
            r15 = 0
            java.lang.Object[] r15 = new java.lang.Object[r15]
            r11.i(r14, r15)
            r0 = r18
            r1 = r19
            r2 = r24
            java.net.HttpURLConnection r5 = r0.getConnection(r1, r2)
            r11 = 1
            r5.setDoOutput(r11)
            if (r22 < 0) goto L_0x0037
            r11 = 0
            r5.setChunkedStreamingMode(r11)
        L_0x0037:
            if (r20 == 0) goto L_0x0053
            java.util.Iterator r14 = r20.iterator()
        L_0x003d:
            boolean r11 = r14.hasNext()
            if (r11 == 0) goto L_0x0053
            java.lang.Object r6 = r14.next()
            com.mob.tools.network.KVPair r6 = (com.mob.tools.network.KVPair) r6
            java.lang.String r15 = r6.name
            T r11 = r6.value
            java.lang.String r11 = (java.lang.String) r11
            r5.setRequestProperty(r15, r11)
            goto L_0x003d
        L_0x0053:
            r5.connect()
            java.io.OutputStream r9 = r5.getOutputStream()
            java.io.InputStream r7 = r21.toInputStream()
            r11 = 65536(0x10000, float:9.18355E-41)
            byte[] r4 = new byte[r11]
            int r8 = r7.read(r4)
        L_0x0066:
            if (r8 <= 0) goto L_0x0071
            r11 = 0
            r9.write(r4, r11, r8)
            int r8 = r7.read(r4)
            goto L_0x0066
        L_0x0071:
            r9.flush()
            r7.close()
            r9.close()
            if (r23 == 0) goto L_0x00b7
            com.mob.tools.network.HttpConnectionImpl23 r11 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x00b0 }
            r11.<init>(r5)     // Catch:{ Throwable -> 0x00b0 }
            r0 = r23
            r0.onResponse(r11)     // Catch:{ Throwable -> 0x00b0 }
            r5.disconnect()
        L_0x0089:
            com.mob.tools.log.NLog r11 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "use time: "
            java.lang.StringBuilder r14 = r14.append(r15)
            long r16 = java.lang.System.currentTimeMillis()
            long r16 = r16 - r12
            r0 = r16
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r14 = r14.toString()
            r15 = 0
            java.lang.Object[] r15 = new java.lang.Object[r15]
            r11.i(r14, r15)
            return
        L_0x00b0:
            r10 = move-exception
            throw r10     // Catch:{ all -> 0x00b2 }
        L_0x00b2:
            r11 = move-exception
            r5.disconnect()
            throw r11
        L_0x00b7:
            r5.disconnect()
            goto L_0x0089
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.rawPost(java.lang.String, java.util.ArrayList, com.mob.tools.network.HTTPPart, int, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0104, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0105, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0108, code lost:
        throw r13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getHttpPostResponse(java.lang.String r21, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r22, com.mob.tools.network.KVPair<java.lang.String> r23, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r24, com.mob.tools.network.HttpResponseCallback r25, com.mob.tools.network.NetworkHelper.NetworkTimeOut r26) throws java.lang.Throwable {
        /*
            r20 = this;
            long r14 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r13 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "httpPost: "
            java.lang.StringBuilder r16 = r16.append(r17)
            r0 = r16
            r1 = r21
            java.lang.StringBuilder r16 = r0.append(r1)
            java.lang.String r16 = r16.toString()
            r17 = 0
            r0 = r17
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r17 = r0
            r0 = r16
            r1 = r17
            r13.i(r0, r1)
            r0 = r20
            r1 = r21
            r2 = r26
            java.net.HttpURLConnection r5 = r0.getConnection(r1, r2)
            r13 = 1
            r5.setDoOutput(r13)
            r13 = 0
            r5.setChunkedStreamingMode(r13)
            if (r23 == 0) goto L_0x008f
            r0 = r23
            T r13 = r0.value
            if (r13 == 0) goto L_0x008f
            java.io.File r16 = new java.io.File
            r0 = r23
            T r13 = r0.value
            java.lang.String r13 = (java.lang.String) r13
            r0 = r16
            r0.<init>(r13)
            boolean r13 = r16.exists()
            if (r13 == 0) goto L_0x008f
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r0 = r23
            r6.add(r0)
            r0 = r20
            r1 = r21
            r2 = r22
            com.mob.tools.network.HTTPPart r11 = r0.getFilePostHTTPPart(r5, r1, r2, r6)
        L_0x006f:
            if (r24 == 0) goto L_0x009a
            java.util.Iterator r16 = r24.iterator()
        L_0x0075:
            boolean r13 = r16.hasNext()
            if (r13 == 0) goto L_0x009a
            java.lang.Object r7 = r16.next()
            com.mob.tools.network.KVPair r7 = (com.mob.tools.network.KVPair) r7
            java.lang.String r0 = r7.name
            r17 = r0
            T r13 = r7.value
            java.lang.String r13 = (java.lang.String) r13
            r0 = r17
            r5.setRequestProperty(r0, r13)
            goto L_0x0075
        L_0x008f:
            r0 = r20
            r1 = r21
            r2 = r22
            com.mob.tools.network.HTTPPart r11 = r0.getTextPostHTTPPart(r5, r1, r2)
            goto L_0x006f
        L_0x009a:
            r5.connect()
            java.io.OutputStream r10 = r5.getOutputStream()
            java.io.InputStream r8 = r11.toInputStream()
            r13 = 65536(0x10000, float:9.18355E-41)
            byte[] r4 = new byte[r13]
            int r9 = r8.read(r4)
        L_0x00ad:
            if (r9 <= 0) goto L_0x00b8
            r13 = 0
            r10.write(r4, r13, r9)
            int r9 = r8.read(r4)
            goto L_0x00ad
        L_0x00b8:
            r10.flush()
            r8.close()
            r10.close()
            if (r25 == 0) goto L_0x0109
            com.mob.tools.network.HttpConnectionImpl23 r13 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x0102 }
            r13.<init>(r5)     // Catch:{ Throwable -> 0x0102 }
            r0 = r25
            r0.onResponse(r13)     // Catch:{ Throwable -> 0x0102 }
            r5.disconnect()
        L_0x00d0:
            com.mob.tools.log.NLog r13 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "use time: "
            java.lang.StringBuilder r16 = r16.append(r17)
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r14
            r0 = r16
            r1 = r18
            java.lang.StringBuilder r16 = r0.append(r1)
            java.lang.String r16 = r16.toString()
            r17 = 0
            r0 = r17
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r17 = r0
            r0 = r16
            r1 = r17
            r13.i(r0, r1)
            return
        L_0x0102:
            r12 = move-exception
            throw r12     // Catch:{ all -> 0x0104 }
        L_0x0104:
            r13 = move-exception
            r5.disconnect()
            throw r13
        L_0x0109:
            r5.disconnect()
            goto L_0x00d0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.getHttpPostResponse(java.lang.String, java.util.ArrayList, com.mob.tools.network.KVPair, java.util.ArrayList, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    public String httpPut(String url, ArrayList<KVPair<String>> values, KVPair<String> file, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        return httpPut(url, values, file, headers, timeout, (OnReadListener) null);
    }

    public String httpPut(String url, ArrayList<KVPair<String>> values, KVPair<String> file, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout, OnReadListener listener) throws Throwable {
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("httpPut: " + url, new Object[0]);
        if (values != null) {
            String param = kvPairsToUrl(values);
            if (param.length() > 0) {
                url = url + "?" + param;
            }
        }
        HttpURLConnection conn = getConnection(url, timeout);
        conn.setDoOutput(true);
        conn.setChunkedStreamingMode(0);
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/octet-stream");
        if (headers != null) {
            Iterator<KVPair<String>> it = headers.iterator();
            while (it.hasNext()) {
                KVPair<String> header = it.next();
                conn.setRequestProperty(header.name, (String) header.value);
            }
        }
        conn.connect();
        OutputStream os = conn.getOutputStream();
        FilePart fp = new FilePart();
        if (listener != null) {
            fp.setOnReadListener(listener);
        }
        fp.setFile((String) file.value);
        InputStream is = fp.toInputStream();
        byte[] buf = new byte[65536];
        for (int len = is.read(buf); len > 0; len = is.read(buf)) {
            os.write(buf, 0, len);
        }
        os.flush();
        is.close();
        os.close();
        int status = conn.getResponseCode();
        if (status == 200 || status == 201) {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("utf-8")));
            for (String txt = br.readLine(); txt != null; txt = br.readLine()) {
                if (sb.length() > 0) {
                    sb.append(10);
                }
                sb.append(txt);
            }
            br.close();
            conn.disconnect();
            String resp = sb.toString();
            MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
            return resp;
        }
        StringBuilder sb2 = new StringBuilder();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn.getErrorStream(), Charset.forName("utf-8")));
        for (String txt2 = br2.readLine(); txt2 != null; txt2 = br2.readLine()) {
            if (sb2.length() > 0) {
                sb2.append(10);
            }
            sb2.append(txt2);
        }
        br2.close();
        HashMap<String, Object> errMap = new HashMap<>();
        errMap.put("error", sb2.toString());
        errMap.put("status", Integer.valueOf(status));
        throw new Throwable(new Hashon().fromHashMap(errMap));
    }

    public ArrayList<KVPair<String[]>> httpHead(String url, ArrayList<KVPair<String>> values, KVPair<String> kVPair, ArrayList<KVPair<String>> headers, NetworkTimeOut timeout) throws Throwable {
        long time = System.currentTimeMillis();
        MobLog.getInstance().i("httpHead: " + url, new Object[0]);
        if (values != null) {
            String param = kvPairsToUrl(values);
            if (param.length() > 0) {
                url = url + "?" + param;
            }
        }
        HttpURLConnection conn = getConnection(url, timeout);
        conn.setRequestMethod("HEAD");
        if (headers != null) {
            Iterator<KVPair<String>> it = headers.iterator();
            while (it.hasNext()) {
                KVPair<String> header = it.next();
                conn.setRequestProperty(header.name, (String) header.value);
            }
        }
        conn.connect();
        Map<String, List<String>> map = conn.getHeaderFields();
        ArrayList<KVPair<String[]>> list = new ArrayList<>();
        if (map != null) {
            for (Map.Entry<String, List<String>> ent : map.entrySet()) {
                List<String> value = ent.getValue();
                if (value == null) {
                    list.add(new KVPair(ent.getKey(), new String[0]));
                } else {
                    String[] hds = new String[value.size()];
                    for (int i = 0; i < hds.length; i++) {
                        hds[i] = value.get(i);
                    }
                    list.add(new KVPair(ent.getKey(), hds));
                }
            }
        }
        conn.disconnect();
        MobLog.getInstance().i("use time: " + (System.currentTimeMillis() - time), new Object[0]);
        return list;
    }

    public void httpPatch(String url, ArrayList<KVPair<String>> values, KVPair<String> file, long offset, ArrayList<KVPair<String>> headers, OnReadListener listener, HttpResponseCallback callback, NetworkTimeOut timeout) throws Throwable {
        if (Build.VERSION.SDK_INT >= 23) {
            httpPatchImpl23(url, values, file, offset, headers, listener, callback, timeout);
        } else {
            httpPatchImpl(url, values, file, offset, headers, listener, callback, timeout);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x01c8, code lost:
        r27 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x01c9, code lost:
        r5.getConnectionManager().shutdown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x01d0, code lost:
        throw r27;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void httpPatchImpl(java.lang.String r33, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r34, com.mob.tools.network.KVPair<java.lang.String> r35, long r36, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r38, com.mob.tools.network.OnReadListener r39, com.mob.tools.network.HttpResponseCallback r40, com.mob.tools.network.NetworkHelper.NetworkTimeOut r41) throws java.lang.Throwable {
        /*
            r32 = this;
            long r24 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r27 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r28 = new java.lang.StringBuilder
            r28.<init>()
            java.lang.String r29 = "httpPatch: "
            java.lang.StringBuilder r28 = r28.append(r29)
            r0 = r28
            r1 = r33
            java.lang.StringBuilder r28 = r0.append(r1)
            java.lang.String r28 = r28.toString()
            r29 = 0
            r0 = r29
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r29 = r0
            r27.i(r28, r29)
            if (r34 == 0) goto L_0x0057
            r0 = r32
            r1 = r34
            java.lang.String r15 = r0.kvPairsToUrl(r1)
            int r27 = r15.length()
            if (r27 <= 0) goto L_0x0057
            java.lang.StringBuilder r27 = new java.lang.StringBuilder
            r27.<init>()
            r0 = r27
            r1 = r33
            java.lang.StringBuilder r27 = r0.append(r1)
            java.lang.String r28 = "?"
            java.lang.StringBuilder r27 = r27.append(r28)
            r0 = r27
            java.lang.StringBuilder r27 = r0.append(r15)
            java.lang.String r33 = r27.toString()
        L_0x0057:
            com.mob.tools.network.HttpPatch r19 = new com.mob.tools.network.HttpPatch
            r0 = r19
            r1 = r33
            r0.<init>((java.lang.String) r1)
            if (r38 == 0) goto L_0x0086
            java.util.Iterator r28 = r38.iterator()
        L_0x0066:
            boolean r27 = r28.hasNext()
            if (r27 == 0) goto L_0x0086
            java.lang.Object r10 = r28.next()
            com.mob.tools.network.KVPair r10 = (com.mob.tools.network.KVPair) r10
            java.lang.String r0 = r10.name
            r29 = r0
            T r0 = r10.value
            r27 = r0
            java.lang.String r27 = (java.lang.String) r27
            r0 = r19
            r1 = r29
            r2 = r27
            r0.setHeader(r1, r2)
            goto L_0x0066
        L_0x0086:
            com.mob.tools.network.FilePart r9 = new com.mob.tools.network.FilePart
            r9.<init>()
            r0 = r39
            r9.setOnReadListener(r0)
            r0 = r35
            T r0 = r0.value
            r27 = r0
            java.lang.String r27 = (java.lang.String) r27
            r0 = r27
            r9.setFile((java.lang.String) r0)
            r0 = r36
            r9.setOffset(r0)
            java.io.InputStream r14 = r9.toInputStream()
            long r28 = r9.length()
            long r16 = r28 - r36
            org.apache.http.entity.InputStreamEntity r8 = new org.apache.http.entity.InputStreamEntity
            r0 = r16
            r8.<init>(r14, r0)
            java.lang.String r27 = "application/offset+octet-stream"
            r0 = r27
            r8.setContentEncoding(r0)
            r0 = r19
            r0.setEntity(r8)
            org.apache.http.params.BasicHttpParams r11 = new org.apache.http.params.BasicHttpParams
            r11.<init>()
            if (r41 != 0) goto L_0x01b2
            int r7 = connectionTimeout
        L_0x00c8:
            if (r7 <= 0) goto L_0x00cd
            org.apache.http.params.HttpConnectionParams.setConnectionTimeout(r11, r7)
        L_0x00cd:
            if (r41 != 0) goto L_0x01b8
            int r20 = readTimout
        L_0x00d1:
            if (r20 <= 0) goto L_0x00d8
            r0 = r20
            org.apache.http.params.HttpConnectionParams.setSoTimeout(r11, r0)
        L_0x00d8:
            r0 = r19
            r0.setParams(r11)
            r5 = 0
            java.lang.String r27 = "https://"
            r0 = r33
            r1 = r27
            boolean r27 = r0.startsWith(r1)
            if (r27 == 0) goto L_0x01c0
            java.lang.String r27 = java.security.KeyStore.getDefaultType()
            java.security.KeyStore r26 = java.security.KeyStore.getInstance(r27)
            r27 = 0
            r28 = 0
            r26.load(r27, r28)
            com.mob.tools.network.SSLSocketFactoryEx r22 = new com.mob.tools.network.SSLSocketFactoryEx
            r0 = r22
            r1 = r26
            r0.<init>(r1)
            org.apache.http.conn.ssl.X509HostnameVerifier r27 = org.apache.http.conn.ssl.SSLSocketFactory.STRICT_HOSTNAME_VERIFIER
            r0 = r22
            r1 = r27
            r0.setHostnameVerifier(r1)
            org.apache.http.params.BasicHttpParams r18 = new org.apache.http.params.BasicHttpParams
            r18.<init>()
            org.apache.http.HttpVersion r6 = org.apache.http.HttpVersion.HTTP_1_1
            r0 = r18
            org.apache.http.params.HttpProtocolParams.setVersion(r0, r6)
            java.lang.String r27 = "UTF-8"
            r0 = r18
            r1 = r27
            org.apache.http.params.HttpProtocolParams.setContentCharset(r0, r1)
            org.apache.http.conn.scheme.SchemeRegistry r21 = new org.apache.http.conn.scheme.SchemeRegistry
            r21.<init>()
            org.apache.http.conn.scheme.PlainSocketFactory r13 = org.apache.http.conn.scheme.PlainSocketFactory.getSocketFactory()
            org.apache.http.conn.scheme.Scheme r27 = new org.apache.http.conn.scheme.Scheme
            java.lang.String r28 = "http"
            r29 = 80
            r0 = r27
            r1 = r28
            r2 = r29
            r0.<init>(r1, r13, r2)
            r0 = r21
            r1 = r27
            r0.register(r1)
            org.apache.http.conn.scheme.Scheme r27 = new org.apache.http.conn.scheme.Scheme
            java.lang.String r28 = "https"
            r29 = 443(0x1bb, float:6.21E-43)
            r0 = r27
            r1 = r28
            r2 = r22
            r3 = r29
            r0.<init>(r1, r2, r3)
            r0 = r21
            r1 = r27
            r0.register(r1)
            org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager r4 = new org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
            r0 = r18
            r1 = r21
            r4.<init>(r0, r1)
            org.apache.http.impl.client.DefaultHttpClient r5 = new org.apache.http.impl.client.DefaultHttpClient
            r0 = r18
            r5.<init>(r4, r0)
        L_0x0167:
            r0 = r19
            org.apache.http.HttpResponse r12 = r5.execute(r0)
            if (r40 == 0) goto L_0x01d1
            com.mob.tools.network.HttpConnectionImpl r27 = new com.mob.tools.network.HttpConnectionImpl     // Catch:{ Throwable -> 0x01c6 }
            r0 = r27
            r0.<init>(r12)     // Catch:{ Throwable -> 0x01c6 }
            r0 = r40
            r1 = r27
            r0.onResponse(r1)     // Catch:{ Throwable -> 0x01c6 }
            org.apache.http.conn.ClientConnectionManager r27 = r5.getConnectionManager()
            r27.shutdown()
        L_0x0184:
            com.mob.tools.log.NLog r27 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r28 = new java.lang.StringBuilder
            r28.<init>()
            java.lang.String r29 = "use time: "
            java.lang.StringBuilder r28 = r28.append(r29)
            long r30 = java.lang.System.currentTimeMillis()
            long r30 = r30 - r24
            r0 = r28
            r1 = r30
            java.lang.StringBuilder r28 = r0.append(r1)
            java.lang.String r28 = r28.toString()
            r29 = 0
            r0 = r29
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r29 = r0
            r27.i(r28, r29)
            return
        L_0x01b2:
            r0 = r41
            int r7 = r0.connectionTimeout
            goto L_0x00c8
        L_0x01b8:
            r0 = r41
            int r0 = r0.readTimout
            r20 = r0
            goto L_0x00d1
        L_0x01c0:
            org.apache.http.impl.client.DefaultHttpClient r5 = new org.apache.http.impl.client.DefaultHttpClient
            r5.<init>()
            goto L_0x0167
        L_0x01c6:
            r23 = move-exception
            throw r23     // Catch:{ all -> 0x01c8 }
        L_0x01c8:
            r27 = move-exception
            org.apache.http.conn.ClientConnectionManager r28 = r5.getConnectionManager()
            r28.shutdown()
            throw r27
        L_0x01d1:
            org.apache.http.conn.ClientConnectionManager r27 = r5.getConnectionManager()
            r27.shutdown()
            goto L_0x0184
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.httpPatchImpl(java.lang.String, java.util.ArrayList, com.mob.tools.network.KVPair, long, java.util.ArrayList, com.mob.tools.network.OnReadListener, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x011b, code lost:
        r13 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x011c, code lost:
        r5.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x011f, code lost:
        throw r13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void httpPatchImpl23(java.lang.String r21, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r22, com.mob.tools.network.KVPair<java.lang.String> r23, long r24, java.util.ArrayList<com.mob.tools.network.KVPair<java.lang.String>> r26, com.mob.tools.network.OnReadListener r27, com.mob.tools.network.HttpResponseCallback r28, com.mob.tools.network.NetworkHelper.NetworkTimeOut r29) throws java.lang.Throwable {
        /*
            r20 = this;
            long r14 = java.lang.System.currentTimeMillis()
            com.mob.tools.log.NLog r13 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "httpPatch: "
            java.lang.StringBuilder r16 = r16.append(r17)
            r0 = r16
            r1 = r21
            java.lang.StringBuilder r16 = r0.append(r1)
            java.lang.String r16 = r16.toString()
            r17 = 0
            r0 = r17
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r17 = r0
            r0 = r16
            r1 = r17
            r13.i(r0, r1)
            if (r22 == 0) goto L_0x0059
            r0 = r20
            r1 = r22
            java.lang.String r11 = r0.kvPairsToUrl(r1)
            int r13 = r11.length()
            if (r13 <= 0) goto L_0x0059
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r0 = r21
            java.lang.StringBuilder r13 = r13.append(r0)
            java.lang.String r16 = "?"
            r0 = r16
            java.lang.StringBuilder r13 = r13.append(r0)
            java.lang.StringBuilder r13 = r13.append(r11)
            java.lang.String r21 = r13.toString()
        L_0x0059:
            r0 = r20
            r1 = r21
            r2 = r29
            java.net.HttpURLConnection r5 = r0.getConnection(r1, r2)
            r13 = 1
            r5.setDoOutput(r13)
            r13 = 0
            r5.setChunkedStreamingMode(r13)
            java.lang.String r13 = "PATCH"
            r5.setRequestMethod(r13)
            java.lang.String r13 = "Content-Type"
            java.lang.String r16 = "application/offset+octet-stream"
            r0 = r16
            r5.setRequestProperty(r13, r0)
            if (r26 == 0) goto L_0x0099
            java.util.Iterator r16 = r26.iterator()
        L_0x007f:
            boolean r13 = r16.hasNext()
            if (r13 == 0) goto L_0x0099
            java.lang.Object r7 = r16.next()
            com.mob.tools.network.KVPair r7 = (com.mob.tools.network.KVPair) r7
            java.lang.String r0 = r7.name
            r17 = r0
            T r13 = r7.value
            java.lang.String r13 = (java.lang.String) r13
            r0 = r17
            r5.setRequestProperty(r0, r13)
            goto L_0x007f
        L_0x0099:
            r5.connect()
            java.io.OutputStream r10 = r5.getOutputStream()
            com.mob.tools.network.FilePart r6 = new com.mob.tools.network.FilePart
            r6.<init>()
            r0 = r27
            r6.setOnReadListener(r0)
            r0 = r23
            T r13 = r0.value
            java.lang.String r13 = (java.lang.String) r13
            r6.setFile((java.lang.String) r13)
            r0 = r24
            r6.setOffset(r0)
            java.io.InputStream r8 = r6.toInputStream()
            r13 = 65536(0x10000, float:9.18355E-41)
            byte[] r4 = new byte[r13]
            int r9 = r8.read(r4)
        L_0x00c4:
            if (r9 <= 0) goto L_0x00cf
            r13 = 0
            r10.write(r4, r13, r9)
            int r9 = r8.read(r4)
            goto L_0x00c4
        L_0x00cf:
            r10.flush()
            r8.close()
            r10.close()
            if (r28 == 0) goto L_0x0120
            com.mob.tools.network.HttpConnectionImpl23 r13 = new com.mob.tools.network.HttpConnectionImpl23     // Catch:{ Throwable -> 0x0119 }
            r13.<init>(r5)     // Catch:{ Throwable -> 0x0119 }
            r0 = r28
            r0.onResponse(r13)     // Catch:{ Throwable -> 0x0119 }
            r5.disconnect()
        L_0x00e7:
            com.mob.tools.log.NLog r13 = com.mob.tools.MobLog.getInstance()
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            r16.<init>()
            java.lang.String r17 = "use time: "
            java.lang.StringBuilder r16 = r16.append(r17)
            long r18 = java.lang.System.currentTimeMillis()
            long r18 = r18 - r14
            r0 = r16
            r1 = r18
            java.lang.StringBuilder r16 = r0.append(r1)
            java.lang.String r16 = r16.toString()
            r17 = 0
            r0 = r17
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r17 = r0
            r0 = r16
            r1 = r17
            r13.i(r0, r1)
            return
        L_0x0119:
            r12 = move-exception
            throw r12     // Catch:{ all -> 0x011b }
        L_0x011b:
            r13 = move-exception
            r5.disconnect()
            throw r13
        L_0x0120:
            r5.disconnect()
            goto L_0x00e7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.network.NetworkHelper.httpPatchImpl23(java.lang.String, java.util.ArrayList, com.mob.tools.network.KVPair, long, java.util.ArrayList, com.mob.tools.network.OnReadListener, com.mob.tools.network.HttpResponseCallback, com.mob.tools.network.NetworkHelper$NetworkTimeOut):void");
    }

    private String kvPairsToUrl(ArrayList<KVPair<String>> values) throws Throwable {
        StringBuilder sb = new StringBuilder();
        Iterator<KVPair<String>> it = values.iterator();
        while (it.hasNext()) {
            KVPair<String> value = it.next();
            String encodedName = Data.urlEncode(value.name, "utf-8");
            String encodedValue = value.value != null ? Data.urlEncode((String) value.value, "utf-8") : "";
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(encodedName).append('=').append(encodedValue);
        }
        return sb.toString();
    }

    private HttpURLConnection getConnection(String urlStr, NetworkTimeOut timeout) throws Throwable {
        Object obj;
        int readTimout2;
        Object methods;
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        String filedName = "methodTokens";
        boolean staticType = false;
        Object methods2 = null;
        if (0 != 0) {
            try {
                methods2 = ReflectHelper.getStaticField("HttpURLConnection", filedName);
            } catch (Throwable th) {
            }
        } else {
            methods2 = ReflectHelper.getInstanceField(conn, filedName);
        }
        if (methods2 == null) {
            filedName = "PERMITTED_USER_METHODS";
            staticType = true;
            if (1 != 0) {
                try {
                    methods = ReflectHelper.getStaticField("HttpURLConnection", filedName);
                } catch (Throwable th2) {
                    obj = methods2;
                }
            } else {
                methods = ReflectHelper.getInstanceField(conn, filedName);
            }
            obj = methods;
        } else {
            obj = methods2;
        }
        if (obj != null) {
            String[] methodTokens = (String[]) obj;
            String[] myMethodTokens = new String[(methodTokens.length + 1)];
            System.arraycopy(methodTokens, 0, myMethodTokens, 0, methodTokens.length);
            myMethodTokens[methodTokens.length] = HttpPatch.METHOD_NAME;
            if (staticType) {
                ReflectHelper.setStaticField("HttpURLConnection", filedName, myMethodTokens);
            } else {
                ReflectHelper.setInstanceField(conn, filedName, myMethodTokens);
            }
        }
        if (Build.VERSION.SDK_INT < 8) {
            System.setProperty("http.keepAlive", "false");
        }
        if (conn instanceof HttpsURLConnection) {
            HostnameVerifier hostnameVerifier = SSLSocketFactory.STRICT_HOSTNAME_VERIFIER;
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init((KeyManager[]) null, new TrustManager[]{new SimpleX509TrustManager((KeyStore) null)}, new SecureRandom());
            httpsConn.setSSLSocketFactory(sc.getSocketFactory());
            httpsConn.setHostnameVerifier(hostnameVerifier);
        }
        int connectionTimeout2 = timeout == null ? connectionTimeout : timeout.connectionTimeout;
        if (connectionTimeout2 > 0) {
            conn.setConnectTimeout(connectionTimeout2);
        }
        if (timeout == null) {
            readTimout2 = readTimout;
        } else {
            readTimout2 = timeout.readTimout;
        }
        if (readTimout2 > 0) {
            conn.setReadTimeout(readTimout2);
        }
        return conn;
    }

    public static final class SimpleX509TrustManager implements X509TrustManager {
        private X509TrustManager standardTrustManager;

        public SimpleX509TrustManager(KeyStore keystore) {
            try {
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
                tmf.init(keystore);
                TrustManager[] trustManagers = tmf.getTrustManagers();
                if (trustManagers == null || trustManagers.length == 0) {
                    throw new NoSuchAlgorithmException("no trust manager found.");
                }
                this.standardTrustManager = (X509TrustManager) trustManagers[0];
            } catch (Exception e) {
                MobLog.getInstance().d("failed to initialize the standard trust manager: " + e.getMessage(), new Object[0]);
                this.standardTrustManager = null;
            }
        }

        public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
            if (certificates == null) {
                throw new IllegalArgumentException("there were no certificates.");
            } else if (certificates.length == 1) {
                certificates[0].checkValidity();
            } else if (this.standardTrustManager != null) {
                this.standardTrustManager.checkServerTrusted(certificates, authType);
            } else {
                throw new CertificateException("there were one more certificates but no trust manager found.");
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
