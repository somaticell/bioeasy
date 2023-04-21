package com.alipay.sdk.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;
import android.text.TextUtils;
import java.net.URL;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpParams;

public final class a {
    public static final String a = "application/octet-stream;binary/octet-stream";
    public String b;
    private Context c;

    private a(Context context) {
        this(context, (String) null);
    }

    public a(Context context, String str) {
        this.c = context;
        this.b = str;
    }

    private void a(String str) {
        this.b = str;
    }

    private String a() {
        return this.b;
    }

    private URL b() {
        try {
            return new URL(this.b);
        } catch (Exception e) {
            return null;
        }
    }

    public final HttpResponse a(byte[] bArr, List<Header> list) throws Throwable {
        HttpPost httpPost;
        HttpResponse httpResponse = null;
        new StringBuilder("requestUrl : ").append(this.b);
        b a2 = b.a();
        if (a2 != null) {
            try {
                HttpParams params = a2.c.getParams();
                if (Build.VERSION.SDK_INT >= 11) {
                    String g = g();
                    if (g == null || g.contains("wap")) {
                        URL b2 = b();
                        if (b2 != null) {
                            "https".equalsIgnoreCase(b2.getProtocol());
                            String property = System.getProperty("https.proxyHost");
                            String property2 = System.getProperty("https.proxyPort");
                            if (!TextUtils.isEmpty(property)) {
                                httpResponse = new HttpHost(property, Integer.parseInt(property2));
                            }
                        }
                    }
                } else {
                    NetworkInfo f = f();
                    if (f != null && f.isAvailable() && f.getType() == 0) {
                        String defaultHost = Proxy.getDefaultHost();
                        int defaultPort = Proxy.getDefaultPort();
                        if (defaultHost != null) {
                            httpResponse = new HttpHost(defaultHost, defaultPort);
                        }
                    }
                }
                if (httpResponse != null) {
                    params.setParameter("http.route.default-proxy", httpResponse);
                }
                if (bArr == null || bArr.length == 0) {
                    httpPost = new HttpGet(this.b);
                } else {
                    httpPost = new HttpPost(this.b);
                    ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bArr);
                    byteArrayEntity.setContentType(a);
                    httpPost.setEntity(byteArrayEntity);
                    httpPost.addHeader("Accept-Charset", "UTF-8");
                    httpPost.addHeader("Connection", "Keep-Alive");
                    httpPost.addHeader("Keep-Alive", "timeout=180, max=100");
                }
                if (list != null) {
                    for (Header addHeader : list) {
                        httpPost.addHeader(addHeader);
                    }
                }
                httpResponse = a2.a(httpPost);
                Header[] headers = httpResponse.getHeaders("X-Hostname");
                if (!(headers == null || headers.length <= 0 || headers[0] == null)) {
                    httpResponse.getHeaders("X-Hostname")[0].toString();
                }
                Header[] headers2 = httpResponse.getHeaders("X-ExecuteTime");
                if (!(headers2 == null || headers2.length <= 0 || headers2[0] == null)) {
                    httpResponse.getHeaders("X-ExecuteTime")[0].toString();
                }
            } catch (Throwable th) {
            }
        }
        return httpResponse;
        throw th;
    }

    private HttpHost c() {
        URL b2;
        if (Build.VERSION.SDK_INT >= 11) {
            String g = g();
            if ((g != null && !g.contains("wap")) || (b2 = b()) == null) {
                return null;
            }
            "https".equalsIgnoreCase(b2.getProtocol());
            String property = System.getProperty("https.proxyHost");
            String property2 = System.getProperty("https.proxyPort");
            if (!TextUtils.isEmpty(property)) {
                return new HttpHost(property, Integer.parseInt(property2));
            }
            return null;
        }
        NetworkInfo f = f();
        if (f == null || !f.isAvailable() || f.getType() != 0) {
            return null;
        }
        String defaultHost = Proxy.getDefaultHost();
        int defaultPort = Proxy.getDefaultPort();
        if (defaultHost != null) {
            return new HttpHost(defaultHost, defaultPort);
        }
        return null;
    }

    private HttpHost d() {
        NetworkInfo f = f();
        if (f == null || !f.isAvailable() || f.getType() != 0) {
            return null;
        }
        String defaultHost = Proxy.getDefaultHost();
        int defaultPort = Proxy.getDefaultPort();
        if (defaultHost != null) {
            return new HttpHost(defaultHost, defaultPort);
        }
        return null;
    }

    private HttpHost e() {
        URL b2;
        String g = g();
        if ((g != null && !g.contains("wap")) || (b2 = b()) == null) {
            return null;
        }
        "https".equalsIgnoreCase(b2.getProtocol());
        String property = System.getProperty("https.proxyHost");
        String property2 = System.getProperty("https.proxyPort");
        if (!TextUtils.isEmpty(property)) {
            return new HttpHost(property, Integer.parseInt(property2));
        }
        return null;
    }

    private NetworkInfo f() {
        try {
            return ((ConnectivityManager) this.c.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e) {
            return null;
        }
    }

    private String g() {
        try {
            NetworkInfo f = f();
            if (f == null || !f.isAvailable()) {
                return "none";
            }
            if (f.getType() == 1) {
                return "wifi";
            }
            return f.getExtraInfo().toLowerCase();
        } catch (Exception e) {
            return "none";
        }
    }
}
