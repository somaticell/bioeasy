package com.alipay.android.phone.mrpc.core;

import android.content.ContentResolver;
import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.alipay.sdk.util.h;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Callable;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class HttpWorker implements Callable<Response> {
    private static final String TAG = "HttpWorker";
    private static final HttpRequestRetryHandler sHttpRequestRetryHandler = new ZHttpRequestRetryHandler();
    private String etagCacheKey = null;
    private boolean hasEtagInResponse = false;
    private boolean hasIfNoneMatchInRequest = false;
    protected Context mContext;
    private CookieManager mCookieManager;
    private CookieStore mCookieStore = new BasicCookieStore();
    private HttpHost mHttpHost;
    protected HttpManager mHttpManager;
    private HttpUriRequest mHttpRequest;
    private HttpContext mLocalContext = new BasicHttpContext();
    private String mOperationType;
    private AbstractHttpEntity mPostDataEntity;
    protected HttpUrlRequest mRequest;
    private int mRetryTimes = 0;
    private URL mTargetUrl;
    String mUrl;

    public HttpWorker(HttpManager httpManager, HttpUrlRequest httpUrlRequest) {
        this.mHttpManager = httpManager;
        this.mContext = this.mHttpManager.mContext;
        this.mRequest = httpUrlRequest;
    }

    private void abortRequest() {
        if (this.mHttpRequest != null) {
            this.mHttpRequest.abort();
        }
    }

    private void addRequestHeaders() {
        ArrayList<Header> headers = getHeaders();
        if (headers != null && !headers.isEmpty()) {
            Iterator<Header> it = headers.iterator();
            while (it.hasNext()) {
                getHttpUriRequest().addHeader(it.next());
            }
        }
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(getHttpUriRequest());
        AndroidHttpClient.modifyRequestToKeepAlive(getHttpUriRequest());
        getHttpUriRequest().addHeader("cookie", getCookieManager().getCookie(this.mRequest.getUrl()));
    }

    private HttpResponse executeHttpClientRequest() {
        new StringBuilder("By Http/Https to request. operationType=").append(getOperationType()).append(" url=").append(this.mHttpRequest.getURI().toString());
        getHttpClient().getParams().setParameter("http.route.default-proxy", getProxy());
        HttpHost httpHost = getHttpHost();
        if (getTargetPort() == 80) {
            httpHost = new HttpHost(getTargetURL().getHost());
        }
        return getHttpClient().execute(httpHost, (HttpRequest) this.mHttpRequest, this.mLocalContext);
    }

    private HttpResponse executeRequest() {
        return executeHttpClientRequest();
    }

    private CookieManager getCookieManager() {
        if (this.mCookieManager != null) {
            return this.mCookieManager;
        }
        this.mCookieManager = CookieManager.getInstance();
        return this.mCookieManager;
    }

    private AndroidHttpClient getHttpClient() {
        return this.mHttpManager.getHttpClient();
    }

    private HttpHost getHttpHost() {
        if (this.mHttpHost != null) {
            return this.mHttpHost;
        }
        URL targetURL = getTargetURL();
        this.mHttpHost = new HttpHost(targetURL.getHost(), getTargetPort(), targetURL.getProtocol());
        return this.mHttpHost;
    }

    private HttpUriRequest getHttpUriRequest() {
        if (this.mHttpRequest != null) {
            return this.mHttpRequest;
        }
        AbstractHttpEntity postData = getPostData();
        if (postData != null) {
            HttpPost httpPost = new HttpPost(getUri());
            httpPost.setEntity(postData);
            this.mHttpRequest = httpPost;
        } else {
            this.mHttpRequest = new HttpGet(getUri());
        }
        return this.mHttpRequest;
    }

    private String getOperationType() {
        if (!TextUtils.isEmpty(this.mOperationType)) {
            return this.mOperationType;
        }
        this.mOperationType = this.mRequest.getTag("operationType");
        return this.mOperationType;
    }

    private HttpHost getProxy() {
        HttpHost proxy = NetworkUtils.getProxy(this.mContext);
        if (proxy == null || !TextUtils.equals(proxy.getHostName(), "127.0.0.1") || proxy.getPort() != 8087) {
            return proxy;
        }
        return null;
    }

    private int getTargetPort() {
        URL targetURL = getTargetURL();
        return targetURL.getPort() == -1 ? targetURL.getDefaultPort() : targetURL.getPort();
    }

    private URL getTargetURL() {
        if (this.mTargetUrl != null) {
            return this.mTargetUrl;
        }
        this.mTargetUrl = new URL(this.mRequest.getUrl());
        return this.mTargetUrl;
    }

    private TransportCallback getTransportCallback() {
        return this.mRequest.getCallback();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x02c3, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(8, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x02c4, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x02c5, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x02cc, code lost:
        if (getTransportCallback() != null) goto L_0x02ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x02ce, code lost:
        getTransportCallback().onFailed(r13.mRequest, 9, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x02dd, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x02f4, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(9, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x02f5, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x02f6, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x02fd, code lost:
        if (getTransportCallback() != null) goto L_0x02ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0035, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02ff, code lost:
        getTransportCallback().onFailed(r13.mRequest, 6, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x030c, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0321, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(6, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0322, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0323, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0328, code lost:
        if (r13.mRetryTimes <= 0) goto L_0x032a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x032a, code lost:
        r13.mRetryTimes++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0336, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x034b, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(0, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x034c, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003d, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x034d, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0354, code lost:
        if (getTransportCallback() != null) goto L_0x0356;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0356, code lost:
        getTransportCallback().onFailed(r13.mRequest, 0, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0370, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(0, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:?, code lost:
        return call();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00f4, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0100, code lost:
        throw new java.lang.RuntimeException("Url parser error!", r2.getCause());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x015e, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x015f, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0166, code lost:
        if (getTransportCallback() != null) goto L_0x0168;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0168, code lost:
        getTransportCallback().onFailed(r13.mRequest, 2, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0175, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x018a, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(2, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x018b, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x018c, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0193, code lost:
        if (getTransportCallback() != null) goto L_0x0195;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0195, code lost:
        getTransportCallback().onFailed(r13.mRequest, 2, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01a2, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01b7, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(2, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01b8, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01b9, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01c0, code lost:
        if (getTransportCallback() != null) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01c2, code lost:
        getTransportCallback().onFailed(r13.mRequest, 6, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01cf, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001a, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01e4, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(6, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01e5, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01e6, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01ed, code lost:
        if (getTransportCallback() != null) goto L_0x01ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01ef, code lost:
        getTransportCallback().onFailed(r13.mRequest, 3, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01fc, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0211, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(3, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0212, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0213, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x021a, code lost:
        if (getTransportCallback() != null) goto L_0x021c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001b, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x021c, code lost:
        getTransportCallback().onFailed(r13.mRequest, 3, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0229, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x023e, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(3, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x023f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0240, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0247, code lost:
        if (getTransportCallback() != null) goto L_0x0249;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0249, code lost:
        getTransportCallback().onFailed(r13.mRequest, 4, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0256, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x026b, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(4, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x026c, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0022, code lost:
        if (getTransportCallback() != null) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x026d, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0274, code lost:
        if (getTransportCallback() != null) goto L_0x0276;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0276, code lost:
        getTransportCallback().onFailed(r13.mRequest, 5, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0284, code lost:
        new java.lang.StringBuilder().append(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x029a, code lost:
        throw new com.alipay.android.phone.mrpc.core.HttpException(5, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x029b, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x029c, code lost:
        abortRequest();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x02a3, code lost:
        if (getTransportCallback() != null) goto L_0x02a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x02a5, code lost:
        getTransportCallback().onFailed(r13.mRequest, 8, java.lang.String.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        getTransportCallback().onFailed(r13.mRequest, r2.getCode(), r2.getMsg());
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02c4 A[ExcHandler: UnknownHostException (r2v8 'e' java.net.UnknownHostException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x02f5 A[ExcHandler: IOException (r2v6 'e' java.io.IOException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0322 A[ExcHandler: NullPointerException (r2v2 'e' java.lang.NullPointerException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00f4 A[ExcHandler: URISyntaxException (r2v26 'e' java.net.URISyntaxException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x015e A[ExcHandler: SSLHandshakeException (r2v24 'e' javax.net.ssl.SSLHandshakeException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x018b A[ExcHandler: SSLPeerUnverifiedException (r2v22 'e' javax.net.ssl.SSLPeerUnverifiedException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x01b8 A[ExcHandler: SSLException (r2v20 'e' javax.net.ssl.SSLException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x001a A[ExcHandler: HttpException (r2v28 'e' com.alipay.android.phone.mrpc.core.HttpException A[CUSTOM_DECLARE]), Splitter:B:34:0x0104] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01e5 A[ExcHandler: ConnectionPoolTimeoutException (r2v18 'e' org.apache.http.conn.ConnectionPoolTimeoutException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0212 A[ExcHandler: ConnectTimeoutException (r2v16 'e' org.apache.http.conn.ConnectTimeoutException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x023f A[ExcHandler: SocketTimeoutException (r2v14 'e' java.net.SocketTimeoutException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x026c A[ExcHandler: NoHttpResponseException (r2v12 'e' org.apache.http.NoHttpResponseException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x029b A[ExcHandler: HttpHostConnectException (r2v10 'e' org.apache.http.conn.HttpHostConnectException A[CUSTOM_DECLARE]), Splitter:B:1:0x0005] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.android.phone.mrpc.core.Response call() {
        /*
            r13 = this;
            r12 = 4
            r11 = 0
            r10 = 6
            r9 = 3
            r8 = 2
            android.content.Context r2 = r13.mContext     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            boolean r2 = com.alipay.android.phone.mrpc.core.NetworkUtils.isNetworkAvailable(r2)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r2 != 0) goto L_0x003e
            com.alipay.android.phone.mrpc.core.HttpException r2 = new com.alipay.android.phone.mrpc.core.HttpException     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r3 = 1
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r4 = "The network is not available"
            r2.<init>(r3, r4)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            throw r2     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
        L_0x001a:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x0035
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            int r5 = r2.getCode()
            java.lang.String r6 = r2.getMsg()
            r3.onFailed(r4, r5, r6)
        L_0x0035:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            throw r2
        L_0x003e:
            com.alipay.android.phone.mrpc.core.TransportCallback r2 = r13.getTransportCallback()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r2 == 0) goto L_0x004d
            com.alipay.android.phone.mrpc.core.TransportCallback r2 = r13.getTransportCallback()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r3 = r13.mRequest     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r2.onPreExecute(r3)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
        L_0x004d:
            r13.addRequestHeaders()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            org.apache.http.protocol.HttpContext r2 = r13.mLocalContext     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r3 = "http.cookie-store"
            org.apache.http.client.CookieStore r4 = r13.mCookieStore     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r2.setAttribute(r3, r4)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            com.alipay.android.phone.mrpc.core.AndroidHttpClient r2 = r13.getHttpClient()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            org.apache.http.client.HttpRequestRetryHandler r3 = sHttpRequestRetryHandler     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r2.setHttpRequestRetryHandler(r3)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            org.apache.http.HttpResponse r4 = r13.executeRequest()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            com.alipay.android.phone.mrpc.core.HttpManager r5 = r13.mHttpManager     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            long r2 = r6 - r2
            r5.addConnectTime(r2)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            org.apache.http.client.CookieStore r2 = r13.mCookieStore     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.util.List r2 = r2.getCookies()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r3 = r13.mRequest     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            boolean r3 = r3.isResetCookie()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r3 == 0) goto L_0x008a
            android.webkit.CookieManager r3 = r13.getCookieManager()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r3.removeAllCookie()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
        L_0x008a:
            boolean r3 = r2.isEmpty()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r3 != 0) goto L_0x0104
            java.util.Iterator r3 = r2.iterator()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
        L_0x0094:
            boolean r2 = r3.hasNext()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r2 == 0) goto L_0x0104
            java.lang.Object r2 = r3.next()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            org.apache.http.cookie.Cookie r2 = (org.apache.http.cookie.Cookie) r2     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r5 = r2.getDomain()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r5 == 0) goto L_0x0094
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r5.<init>()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r6 = r2.getName()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r6 = "="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r6 = r2.getValue()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r6 = "; domain="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r6 = r2.getDomain()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            boolean r2 = r2.isSecure()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r2 == 0) goto L_0x0101
            java.lang.String r2 = "; Secure"
        L_0x00d7:
            java.lang.StringBuilder r2 = r5.append(r2)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r2 = r2.toString()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            android.webkit.CookieManager r5 = r13.getCookieManager()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r6 = r13.mRequest     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r6 = r6.getUrl()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r5.setCookie(r6, r2)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            android.webkit.CookieSyncManager r2 = android.webkit.CookieSyncManager.getInstance()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r2.sync()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            goto L_0x0094
        L_0x00f4:
            r2 = move-exception
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.String r4 = "Url parser error!"
            java.lang.Throwable r2 = r2.getCause()
            r3.<init>(r4, r2)
            throw r3
        L_0x0101:
            java.lang.String r2 = ""
            goto L_0x00d7
        L_0x0104:
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r2 = r13.mRequest     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            com.alipay.android.phone.mrpc.core.Response r3 = r13.processResponse(r4, r2)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r4 = -1
            if (r3 == 0) goto L_0x011a
            byte[] r2 = r3.getResData()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r2 == 0) goto L_0x011a
            byte[] r2 = r3.getResData()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            int r2 = r2.length     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            long r4 = (long) r2     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
        L_0x011a:
            r6 = -1
            int r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r2 != 0) goto L_0x0135
            boolean r2 = r3 instanceof com.alipay.android.phone.mrpc.core.HttpUrlResponse     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r2 == 0) goto L_0x0135
            r0 = r3
            com.alipay.android.phone.mrpc.core.HttpUrlResponse r0 = (com.alipay.android.phone.mrpc.core.HttpUrlResponse) r0     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r2 = r0
            com.alipay.android.phone.mrpc.core.HttpUrlHeader r2 = r2.getHeader()     // Catch:{ Exception -> 0x0371, HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322 }
            java.lang.String r4 = "Content-Length"
            java.lang.String r2 = r2.getHead(r4)     // Catch:{ Exception -> 0x0371, HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322 }
            java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x0371, HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322 }
        L_0x0135:
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r2 = r13.mRequest     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r2 = r2.getUrl()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r2 == 0) goto L_0x015d
            java.lang.String r4 = r13.getOperationType()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            if (r4 != 0) goto L_0x015d
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r4.<init>()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r4 = "#"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            java.lang.String r4 = r13.getOperationType()     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
            r2.append(r4)     // Catch:{ HttpException -> 0x001a, URISyntaxException -> 0x00f4, SSLHandshakeException -> 0x015e, SSLPeerUnverifiedException -> 0x018b, SSLException -> 0x01b8, ConnectionPoolTimeoutException -> 0x01e5, ConnectTimeoutException -> 0x0212, SocketTimeoutException -> 0x023f, NoHttpResponseException -> 0x026c, HttpHostConnectException -> 0x029b, UnknownHostException -> 0x02c4, IOException -> 0x02f5, NullPointerException -> 0x0322, Exception -> 0x034c }
        L_0x015d:
            return r3
        L_0x015e:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x0175
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            java.lang.String r5 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r8, r5)
        L_0x0175:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            java.lang.Integer r4 = java.lang.Integer.valueOf(r8)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x018b:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x01a2
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            java.lang.String r5 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r8, r5)
        L_0x01a2:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            java.lang.Integer r4 = java.lang.Integer.valueOf(r8)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x01b8:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x01cf
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            java.lang.String r5 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r10, r5)
        L_0x01cf:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            java.lang.Integer r4 = java.lang.Integer.valueOf(r10)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x01e5:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x01fc
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            java.lang.String r5 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r9, r5)
        L_0x01fc:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            java.lang.Integer r4 = java.lang.Integer.valueOf(r9)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x0212:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x0229
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            java.lang.String r5 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r9, r5)
        L_0x0229:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            java.lang.Integer r4 = java.lang.Integer.valueOf(r9)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x023f:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x0256
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            java.lang.String r5 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r12, r5)
        L_0x0256:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            java.lang.Integer r4 = java.lang.Integer.valueOf(r12)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x026c:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x0284
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            r5 = 5
            java.lang.String r6 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r5, r6)
        L_0x0284:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            r4 = 5
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x029b:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x02b4
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            r5 = 8
            java.lang.String r6 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r5, r6)
        L_0x02b4:
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            r4 = 8
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x02c4:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x02dd
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            r5 = 9
            java.lang.String r6 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r5, r6)
        L_0x02dd:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            r4 = 9
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x02f5:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x030c
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            java.lang.String r5 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r10, r5)
        L_0x030c:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            java.lang.Integer r4 = java.lang.Integer.valueOf(r10)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x0322:
            r2 = move-exception
            r13.abortRequest()
            int r3 = r13.mRetryTimes
            if (r3 > 0) goto L_0x0336
            int r2 = r13.mRetryTimes
            int r2 = r2 + 1
            r13.mRetryTimes = r2
            com.alipay.android.phone.mrpc.core.Response r3 = r13.call()
            goto L_0x015d
        L_0x0336:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r2)
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            java.lang.Integer r4 = java.lang.Integer.valueOf(r11)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x034c:
            r2 = move-exception
            r13.abortRequest()
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            if (r3 == 0) goto L_0x0363
            com.alipay.android.phone.mrpc.core.TransportCallback r3 = r13.getTransportCallback()
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r4 = r13.mRequest
            java.lang.String r5 = java.lang.String.valueOf(r2)
            r3.onFailed(r4, r11, r5)
        L_0x0363:
            com.alipay.android.phone.mrpc.core.HttpException r3 = new com.alipay.android.phone.mrpc.core.HttpException
            java.lang.Integer r4 = java.lang.Integer.valueOf(r11)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r3.<init>(r4, r2)
            throw r3
        L_0x0371:
            r2 = move-exception
            goto L_0x0135
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mrpc.core.HttpWorker.call():com.alipay.android.phone.mrpc.core.Response");
    }

    /* access modifiers changed from: protected */
    public void fillResponse(HttpUrlResponse httpUrlResponse, HttpResponse httpResponse) {
        String str;
        String str2 = null;
        long period = getPeriod(httpResponse);
        Header contentType = httpResponse.getEntity().getContentType();
        if (contentType != null) {
            HashMap<String, String> contentType2 = getContentType(contentType.getValue());
            str = contentType2.get("charset");
            str2 = contentType2.get("Content-Type");
        } else {
            str = null;
        }
        httpUrlResponse.setContentType(str2);
        httpUrlResponse.setCharset(str);
        httpUrlResponse.setCreateTime(System.currentTimeMillis());
        httpUrlResponse.setPeriod(period);
    }

    /* access modifiers changed from: protected */
    public HashMap<String, String> getContentType(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (String str2 : str.split(h.b)) {
            String[] split = str2.indexOf(61) == -1 ? new String[]{"Content-Type", str2} : str2.split("=");
            hashMap.put(split[0], split[1]);
        }
        return hashMap;
    }

    /* access modifiers changed from: protected */
    public ArrayList<Header> getHeaders() {
        return this.mRequest.getHeaders();
    }

    /* access modifiers changed from: protected */
    public long getPeriod(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Cache-Control");
        if (firstHeader != null) {
            String[] split = firstHeader.getValue().split("=");
            if (split.length >= 2) {
                try {
                    return parserMaxage(split);
                } catch (NumberFormatException e) {
                }
            }
        }
        Header firstHeader2 = httpResponse.getFirstHeader("Expires");
        if (firstHeader2 != null) {
            return AndroidHttpClient.parseDate(firstHeader2.getValue()) - System.currentTimeMillis();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public AbstractHttpEntity getPostData() {
        if (this.mPostDataEntity != null) {
            return this.mPostDataEntity;
        }
        byte[] reqData = this.mRequest.getReqData();
        String tag = this.mRequest.getTag("gzip");
        if (reqData != null) {
            if (TextUtils.equals(tag, "true")) {
                this.mPostDataEntity = AndroidHttpClient.getCompressedEntity(reqData, (ContentResolver) null);
            } else {
                this.mPostDataEntity = new ByteArrayEntity(reqData);
            }
            this.mPostDataEntity.setContentType(this.mRequest.getContentType());
        }
        return this.mPostDataEntity;
    }

    public HttpUrlRequest getRequest() {
        return this.mRequest;
    }

    /* access modifiers changed from: protected */
    public URI getUri() {
        String url = this.mRequest.getUrl();
        if (this.mUrl != null) {
            url = this.mUrl;
        }
        if (url != null) {
            return new URI(url);
        }
        throw new RuntimeException("url should not be null");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x008d A[SYNTHETIC, Splitter:B:18:0x008d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.android.phone.mrpc.core.Response handleResponse(org.apache.http.HttpResponse r10, int r11, java.lang.String r12) {
        /*
            r9 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "开始handle，handleResponse-1,"
            r1.<init>(r2)
            java.lang.Thread r2 = java.lang.Thread.currentThread()
            long r2 = r2.getId()
            r1.append(r2)
            org.apache.http.HttpEntity r2 = r10.getEntity()
            if (r2 == 0) goto L_0x009e
            org.apache.http.StatusLine r1 = r10.getStatusLine()
            int r1 = r1.getStatusCode()
            r3 = 200(0xc8, float:2.8E-43)
            if (r1 != r3) goto L_0x009e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "200，开始处理，handleResponse-2,threadid = "
            r1.<init>(r3)
            java.lang.Thread r3 = java.lang.Thread.currentThread()
            long r4 = r3.getId()
            r1.append(r4)
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x0087 }
            r1.<init>()     // Catch:{ all -> 0x0087 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00a8 }
            r6 = 0
            r9.writeData(r2, r6, r1)     // Catch:{ all -> 0x00a8 }
            byte[] r2 = r1.toByteArray()     // Catch:{ all -> 0x00a8 }
            r0 = 0
            r9.hasEtagInResponse = r0     // Catch:{ all -> 0x00a8 }
            com.alipay.android.phone.mrpc.core.HttpManager r0 = r9.mHttpManager     // Catch:{ all -> 0x00a8 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00a8 }
            long r4 = r6 - r4
            r0.addSocketTime(r4)     // Catch:{ all -> 0x00a8 }
            com.alipay.android.phone.mrpc.core.HttpManager r0 = r9.mHttpManager     // Catch:{ all -> 0x00a8 }
            int r3 = r2.length     // Catch:{ all -> 0x00a8 }
            long r4 = (long) r3     // Catch:{ all -> 0x00a8 }
            r0.addDataSize(r4)     // Catch:{ all -> 0x00a8 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a8 }
            java.lang.String r3 = "res:"
            r0.<init>(r3)     // Catch:{ all -> 0x00a8 }
            int r3 = r2.length     // Catch:{ all -> 0x00a8 }
            r0.append(r3)     // Catch:{ all -> 0x00a8 }
            com.alipay.android.phone.mrpc.core.HttpUrlResponse r0 = new com.alipay.android.phone.mrpc.core.HttpUrlResponse     // Catch:{ all -> 0x00a8 }
            com.alipay.android.phone.mrpc.core.HttpUrlHeader r3 = r9.handleResponseHeader(r10)     // Catch:{ all -> 0x00a8 }
            r0.<init>(r3, r11, r12, r2)     // Catch:{ all -> 0x00a8 }
            r9.fillResponse(r0, r10)     // Catch:{ all -> 0x00a8 }
            r1.close()     // Catch:{ IOException -> 0x007a }
        L_0x0079:
            return r0
        L_0x007a:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "ArrayOutputStream close error!"
            java.lang.Throwable r0 = r0.getCause()
            r1.<init>(r2, r0)
            throw r1
        L_0x0087:
            r1 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
        L_0x008b:
            if (r1 == 0) goto L_0x0090
            r1.close()     // Catch:{ IOException -> 0x0091 }
        L_0x0090:
            throw r0
        L_0x0091:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "ArrayOutputStream close error!"
            java.lang.Throwable r0 = r0.getCause()
            r1.<init>(r2, r0)
            throw r1
        L_0x009e:
            if (r2 != 0) goto L_0x0079
            org.apache.http.StatusLine r1 = r10.getStatusLine()
            r1.getStatusCode()
            goto L_0x0079
        L_0x00a8:
            r0 = move-exception
            goto L_0x008b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mrpc.core.HttpWorker.handleResponse(org.apache.http.HttpResponse, int, java.lang.String):com.alipay.android.phone.mrpc.core.Response");
    }

    /* access modifiers changed from: protected */
    public HttpUrlHeader handleResponseHeader(HttpResponse httpResponse) {
        HttpUrlHeader httpUrlHeader = new HttpUrlHeader();
        for (Header header : httpResponse.getAllHeaders()) {
            httpUrlHeader.setHead(header.getName(), header.getValue());
        }
        return httpUrlHeader;
    }

    /* access modifiers changed from: protected */
    public long parserMaxage(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            if ("max-age".equalsIgnoreCase(strArr[i]) && strArr[i + 1] != null) {
                try {
                    return Long.parseLong(strArr[i + 1]);
                } catch (Exception e) {
                }
            }
        }
        return 0;
    }

    public Response processResponse(HttpResponse httpResponse, HttpUrlRequest httpUrlRequest) {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String reasonPhrase = httpResponse.getStatusLine().getReasonPhrase();
        if (statusCode == 200 || willHandleOtherCode(statusCode, reasonPhrase)) {
            return handleResponse(httpResponse, statusCode, reasonPhrase);
        }
        throw new HttpException(Integer.valueOf(httpResponse.getStatusLine().getStatusCode()), httpResponse.getStatusLine().getReasonPhrase());
    }

    /* access modifiers changed from: protected */
    public boolean willHandleOtherCode(int i, String str) {
        return i == 304;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r14.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x006c, code lost:
        com.alipay.android.phone.mrpc.core.IOUtil.closeStream(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeData(org.apache.http.HttpEntity r11, long r12, java.io.OutputStream r14) {
        /*
            r10 = this;
            if (r14 != 0) goto L_0x000d
            r11.consumeContent()
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Output stream may not be null"
            r0.<init>(r1)
            throw r0
        L_0x000d:
            java.io.InputStream r1 = com.alipay.android.phone.mrpc.core.AndroidHttpClient.getUngzippedContent(r11)
            long r2 = r11.getContentLength()
            r0 = 2048(0x800, float:2.87E-42)
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0047 }
        L_0x0019:
            int r4 = r1.read(r0)     // Catch:{ Exception -> 0x0047 }
            r5 = -1
            if (r4 == r5) goto L_0x0069
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r5 = r10.mRequest     // Catch:{ Exception -> 0x0047 }
            boolean r5 = r5.isCanceled()     // Catch:{ Exception -> 0x0047 }
            if (r5 != 0) goto L_0x0069
            r5 = 0
            r14.write(r0, r5, r4)     // Catch:{ Exception -> 0x0047 }
            long r4 = (long) r4     // Catch:{ Exception -> 0x0047 }
            long r12 = r12 + r4
            com.alipay.android.phone.mrpc.core.TransportCallback r4 = r10.getTransportCallback()     // Catch:{ Exception -> 0x0047 }
            if (r4 == 0) goto L_0x0019
            r4 = 0
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0019
            com.alipay.android.phone.mrpc.core.TransportCallback r4 = r10.getTransportCallback()     // Catch:{ Exception -> 0x0047 }
            com.alipay.android.phone.mrpc.core.HttpUrlRequest r5 = r10.mRequest     // Catch:{ Exception -> 0x0047 }
            double r6 = (double) r12     // Catch:{ Exception -> 0x0047 }
            double r8 = (double) r2     // Catch:{ Exception -> 0x0047 }
            double r6 = r6 / r8
            r4.onProgressUpdate(r5, r6)     // Catch:{ Exception -> 0x0047 }
            goto L_0x0019
        L_0x0047:
            r0 = move-exception
            r0.getCause()     // Catch:{ all -> 0x0064 }
            java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0064 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0064 }
            java.lang.String r4 = "HttpWorker Request Error!"
            r3.<init>(r4)     // Catch:{ all -> 0x0064 }
            java.lang.String r0 = r0.getLocalizedMessage()     // Catch:{ all -> 0x0064 }
            java.lang.StringBuilder r0 = r3.append(r0)     // Catch:{ all -> 0x0064 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0064 }
            r2.<init>(r0)     // Catch:{ all -> 0x0064 }
            throw r2     // Catch:{ all -> 0x0064 }
        L_0x0064:
            r0 = move-exception
            com.alipay.android.phone.mrpc.core.IOUtil.closeStream(r1)
            throw r0
        L_0x0069:
            r14.flush()     // Catch:{ Exception -> 0x0047 }
            com.alipay.android.phone.mrpc.core.IOUtil.closeStream(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mrpc.core.HttpWorker.writeData(org.apache.http.HttpEntity, long, java.io.OutputStream):void");
    }
}
