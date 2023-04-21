package okhttp3.internal.http;

import com.alipay.sdk.cons.a;
import com.baidu.mapapi.UIMsg;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.InternalCache;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.http.CacheStrategy;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpEngine {
    private static final ResponseBody EMPTY_BODY = new ResponseBody() {
        public MediaType contentType() {
            return null;
        }

        public long contentLength() {
            return 0;
        }

        public BufferedSource source() {
            return new Buffer();
        }
    };
    public static final int MAX_FOLLOW_UPS = 20;
    public final boolean bufferRequestBody;
    private BufferedSink bufferedRequestBody;
    private Response cacheResponse;
    private CacheStrategy cacheStrategy;
    private final boolean callerWritesRequestBody;
    final OkHttpClient client;
    private final boolean forWebSocket;
    /* access modifiers changed from: private */
    public HttpStream httpStream;
    /* access modifiers changed from: private */
    public Request networkRequest;
    private final Response priorResponse;
    private Sink requestBodyOut;
    long sentRequestMillis = -1;
    private CacheRequest storeRequest;
    public final StreamAllocation streamAllocation;
    private boolean transparentGzip;
    private final Request userRequest;
    private Response userResponse;

    public HttpEngine(OkHttpClient client2, Request request, boolean bufferRequestBody2, boolean callerWritesRequestBody2, boolean forWebSocket2, StreamAllocation streamAllocation2, RetryableSink requestBodyOut2, Response priorResponse2) {
        this.client = client2;
        this.userRequest = request;
        this.bufferRequestBody = bufferRequestBody2;
        this.callerWritesRequestBody = callerWritesRequestBody2;
        this.forWebSocket = forWebSocket2;
        this.streamAllocation = streamAllocation2 == null ? new StreamAllocation(client2.connectionPool(), createAddress(client2, request)) : streamAllocation2;
        this.requestBodyOut = requestBodyOut2;
        this.priorResponse = priorResponse2;
    }

    public void sendRequest() throws RequestException, RouteException, IOException {
        if (this.cacheStrategy == null) {
            if (this.httpStream != null) {
                throw new IllegalStateException();
            }
            Request request = networkRequest(this.userRequest);
            InternalCache responseCache = Internal.instance.internalCache(this.client);
            Response cacheCandidate = responseCache != null ? responseCache.get(request) : null;
            this.cacheStrategy = new CacheStrategy.Factory(System.currentTimeMillis(), request, cacheCandidate).get();
            this.networkRequest = this.cacheStrategy.networkRequest;
            this.cacheResponse = this.cacheStrategy.cacheResponse;
            if (responseCache != null) {
                responseCache.trackResponse(this.cacheStrategy);
            }
            if (cacheCandidate != null && this.cacheResponse == null) {
                Util.closeQuietly((Closeable) cacheCandidate.body());
            }
            if (this.networkRequest == null && this.cacheResponse == null) {
                this.userResponse = new Response.Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(UIMsg.d_ResultType.LOC_INFO_UPLOAD).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build();
            } else if (this.networkRequest == null) {
                this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).build();
                this.userResponse = unzip(this.userResponse);
            } else {
                boolean success = false;
                try {
                    this.httpStream = connect();
                    this.httpStream.setHttpEngine(this);
                    if (writeRequestHeadersEagerly()) {
                        long contentLength = OkHeaders.contentLength(request);
                        if (!this.bufferRequestBody) {
                            this.httpStream.writeRequestHeaders(this.networkRequest);
                            this.requestBodyOut = this.httpStream.createRequestBody(this.networkRequest, contentLength);
                        } else if (contentLength > 2147483647L) {
                            throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                        } else if (contentLength != -1) {
                            this.httpStream.writeRequestHeaders(this.networkRequest);
                            this.requestBodyOut = new RetryableSink((int) contentLength);
                        } else {
                            this.requestBodyOut = new RetryableSink();
                        }
                    }
                    success = true;
                } finally {
                    if (!success && cacheCandidate != null) {
                        Util.closeQuietly((Closeable) cacheCandidate.body());
                    }
                }
            }
        }
    }

    private boolean writeRequestHeadersEagerly() {
        return this.callerWritesRequestBody && permitsRequestBody(this.networkRequest) && this.requestBodyOut == null;
    }

    private HttpStream connect() throws RouteException, RequestException, IOException {
        return this.streamAllocation.newStream(this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis(), this.client.retryOnConnectionFailure(), !this.networkRequest.method().equals("GET"));
    }

    private static Response stripBody(Response response) {
        return (response == null || response.body() == null) ? response : response.newBuilder().body((ResponseBody) null).build();
    }

    public void writingRequestHeaders() {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        this.sentRequestMillis = System.currentTimeMillis();
    }

    /* access modifiers changed from: package-private */
    public boolean permitsRequestBody(Request request) {
        return HttpMethod.permitsRequestBody(request.method());
    }

    public Sink getRequestBody() {
        if (this.cacheStrategy != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public BufferedSink getBufferedRequestBody() {
        BufferedSink result;
        BufferedSink result2 = this.bufferedRequestBody;
        if (result2 != null) {
            return result2;
        }
        Sink requestBody = getRequestBody();
        if (requestBody != null) {
            result = Okio.buffer(requestBody);
            this.bufferedRequestBody = result;
        } else {
            result = null;
        }
        return result;
    }

    public boolean hasResponse() {
        return this.userResponse != null;
    }

    public Request getRequest() {
        return this.userRequest;
    }

    public Response getResponse() {
        if (this.userResponse != null) {
            return this.userResponse;
        }
        throw new IllegalStateException();
    }

    public Connection getConnection() {
        return this.streamAllocation.connection();
    }

    public HttpEngine recover(IOException e, Sink requestBodyOut2) {
        if (!this.streamAllocation.recover(e, requestBodyOut2) || !this.client.retryOnConnectionFailure()) {
            return null;
        }
        return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, close(), (RetryableSink) requestBodyOut2, this.priorResponse);
    }

    public HttpEngine recover(IOException e) {
        return recover(e, this.requestBodyOut);
    }

    private void maybeCache() throws IOException {
        InternalCache responseCache = Internal.instance.internalCache(this.client);
        if (responseCache != null) {
            if (CacheStrategy.isCacheable(this.userResponse, this.networkRequest)) {
                this.storeRequest = responseCache.put(stripBody(this.userResponse));
            } else if (HttpMethod.invalidatesCache(this.networkRequest.method())) {
                try {
                    responseCache.remove(this.networkRequest);
                } catch (IOException e) {
                }
            }
        }
    }

    public void releaseStreamAllocation() throws IOException {
        this.streamAllocation.release();
    }

    public void cancel() {
        this.streamAllocation.cancel();
    }

    public StreamAllocation close() {
        if (this.bufferedRequestBody != null) {
            Util.closeQuietly((Closeable) this.bufferedRequestBody);
        } else if (this.requestBodyOut != null) {
            Util.closeQuietly((Closeable) this.requestBodyOut);
        }
        if (this.userResponse != null) {
            Util.closeQuietly((Closeable) this.userResponse.body());
        } else {
            this.streamAllocation.connectionFailed((IOException) null);
        }
        return this.streamAllocation;
    }

    private Response unzip(Response response) throws IOException {
        if (!this.transparentGzip || !"gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding")) || response.body() == null) {
            return response;
        }
        GzipSource responseBody = new GzipSource(response.body().source());
        Headers strippedHeaders = response.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
        return response.newBuilder().headers(strippedHeaders).body(new RealResponseBody(strippedHeaders, Okio.buffer((Source) responseBody))).build();
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals("HEAD")) {
            return false;
        }
        int responseCode = response.code();
        if ((responseCode < 100 || responseCode >= 200) && responseCode != 204 && responseCode != 304) {
            return true;
        }
        if (OkHeaders.contentLength(response) != -1 || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"))) {
            return true;
        }
        return false;
    }

    private Request networkRequest(Request request) throws IOException {
        Request.Builder result = request.newBuilder();
        if (request.header("Host") == null) {
            result.header("Host", Util.hostHeader(request.url(), false));
        }
        if (request.header("Connection") == null) {
            result.header("Connection", "Keep-Alive");
        }
        if (request.header("Accept-Encoding") == null) {
            this.transparentGzip = true;
            result.header("Accept-Encoding", "gzip");
        }
        List<Cookie> cookies = this.client.cookieJar().loadForRequest(request.url());
        if (!cookies.isEmpty()) {
            result.header("Cookie", cookieHeader(cookies));
        }
        if (request.header("User-Agent") == null) {
            result.header("User-Agent", Version.userAgent());
        }
        return result.build();
    }

    private String cookieHeader(List<Cookie> cookies) {
        StringBuilder cookieHeader = new StringBuilder();
        int size = cookies.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                cookieHeader.append("; ");
            }
            Cookie cookie = cookies.get(i);
            cookieHeader.append(cookie.name()).append('=').append(cookie.value());
        }
        return cookieHeader.toString();
    }

    public void readResponse() throws IOException {
        Response networkResponse;
        if (this.userResponse == null) {
            if (this.networkRequest == null && this.cacheResponse == null) {
                throw new IllegalStateException("call sendRequest() first!");
            } else if (this.networkRequest != null) {
                if (this.forWebSocket) {
                    this.httpStream.writeRequestHeaders(this.networkRequest);
                    networkResponse = readNetworkResponse();
                } else if (!this.callerWritesRequestBody) {
                    networkResponse = new NetworkInterceptorChain(0, this.networkRequest).proceed(this.networkRequest);
                } else {
                    if (this.bufferedRequestBody != null && this.bufferedRequestBody.buffer().size() > 0) {
                        this.bufferedRequestBody.emit();
                    }
                    if (this.sentRequestMillis == -1) {
                        if (OkHeaders.contentLength(this.networkRequest) == -1 && (this.requestBodyOut instanceof RetryableSink)) {
                            this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(((RetryableSink) this.requestBodyOut).contentLength())).build();
                        }
                        this.httpStream.writeRequestHeaders(this.networkRequest);
                    }
                    if (this.requestBodyOut != null) {
                        if (this.bufferedRequestBody != null) {
                            this.bufferedRequestBody.close();
                        } else {
                            this.requestBodyOut.close();
                        }
                        if (this.requestBodyOut instanceof RetryableSink) {
                            this.httpStream.writeRequestBody((RetryableSink) this.requestBodyOut);
                        }
                    }
                    networkResponse = readNetworkResponse();
                }
                receiveHeaders(networkResponse.headers());
                if (this.cacheResponse != null) {
                    if (validate(this.cacheResponse, networkResponse)) {
                        this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), networkResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
                        networkResponse.body().close();
                        releaseStreamAllocation();
                        InternalCache responseCache = Internal.instance.internalCache(this.client);
                        responseCache.trackConditionalCacheHit();
                        responseCache.update(this.cacheResponse, stripBody(this.userResponse));
                        this.userResponse = unzip(this.userResponse);
                        return;
                    }
                    Util.closeQuietly((Closeable) this.cacheResponse.body());
                }
                this.userResponse = networkResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(networkResponse)).build();
                if (hasBody(this.userResponse)) {
                    maybeCache();
                    this.userResponse = unzip(cacheWritingResponse(this.storeRequest, this.userResponse));
                }
            }
        }
    }

    class NetworkInterceptorChain implements Interceptor.Chain {
        private int calls;
        private final int index;
        private final Request request;

        NetworkInterceptorChain(int index2, Request request2) {
            this.index = index2;
            this.request = request2;
        }

        public Connection connection() {
            return HttpEngine.this.streamAllocation.connection();
        }

        public Request request() {
            return this.request;
        }

        public Response proceed(Request request2) throws IOException {
            this.calls++;
            if (this.index > 0) {
                Interceptor caller = HttpEngine.this.client.networkInterceptors().get(this.index - 1);
                Address address = connection().route().address();
                if (!request2.url().host().equals(address.url().host()) || request2.url().port() != address.url().port()) {
                    throw new IllegalStateException("network interceptor " + caller + " must retain the same host and port");
                } else if (this.calls > 1) {
                    throw new IllegalStateException("network interceptor " + caller + " must call proceed() exactly once");
                }
            }
            if (this.index < HttpEngine.this.client.networkInterceptors().size()) {
                NetworkInterceptorChain chain = new NetworkInterceptorChain(this.index + 1, request2);
                Interceptor interceptor = HttpEngine.this.client.networkInterceptors().get(this.index);
                Response interceptedResponse = interceptor.intercept(chain);
                if (chain.calls != 1) {
                    throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
                } else if (interceptedResponse != null) {
                    return interceptedResponse;
                } else {
                    throw new NullPointerException("network interceptor " + interceptor + " returned null");
                }
            } else {
                HttpEngine.this.httpStream.writeRequestHeaders(request2);
                Request unused = HttpEngine.this.networkRequest = request2;
                if (HttpEngine.this.permitsRequestBody(request2) && request2.body() != null) {
                    BufferedSink bufferedRequestBody = Okio.buffer(HttpEngine.this.httpStream.createRequestBody(request2, request2.body().contentLength()));
                    request2.body().writeTo(bufferedRequestBody);
                    bufferedRequestBody.close();
                }
                Response response = HttpEngine.this.readNetworkResponse();
                int code = response.code();
                if ((code != 204 && code != 205) || response.body().contentLength() <= 0) {
                    return response;
                }
                throw new ProtocolException("HTTP " + code + " had non-zero Content-Length: " + response.body().contentLength());
            }
        }
    }

    /* access modifiers changed from: private */
    public Response readNetworkResponse() throws IOException {
        this.httpStream.finishRequest();
        Response networkResponse = this.httpStream.readResponseHeaders().request(this.networkRequest).handshake(this.streamAllocation.connection().handshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
        if (!this.forWebSocket) {
            networkResponse = networkResponse.newBuilder().body(this.httpStream.openResponseBody(networkResponse)).build();
        }
        if ("close".equalsIgnoreCase(networkResponse.request().header("Connection")) || "close".equalsIgnoreCase(networkResponse.header("Connection"))) {
            this.streamAllocation.noNewStreams();
        }
        return networkResponse;
    }

    private Response cacheWritingResponse(final CacheRequest cacheRequest, Response response) throws IOException {
        Sink cacheBodyUnbuffered;
        if (cacheRequest == null || (cacheBodyUnbuffered = cacheRequest.body()) == null) {
            return response;
        }
        final BufferedSource source = response.body().source();
        final BufferedSink cacheBody = Okio.buffer(cacheBodyUnbuffered);
        return response.newBuilder().body(new RealResponseBody(response.headers(), Okio.buffer(new Source() {
            boolean cacheRequestClosed;

            public long read(Buffer sink, long byteCount) throws IOException {
                try {
                    long bytesRead = source.read(sink, byteCount);
                    if (bytesRead == -1) {
                        if (!this.cacheRequestClosed) {
                            this.cacheRequestClosed = true;
                            cacheBody.close();
                        }
                        return -1;
                    }
                    sink.copyTo(cacheBody.buffer(), sink.size() - bytesRead, bytesRead);
                    cacheBody.emitCompleteSegments();
                    return bytesRead;
                } catch (IOException e) {
                    if (!this.cacheRequestClosed) {
                        this.cacheRequestClosed = true;
                        cacheRequest.abort();
                    }
                    throw e;
                }
            }

            public Timeout timeout() {
                return source.timeout();
            }

            public void close() throws IOException {
                if (!this.cacheRequestClosed && !Util.discard(this, 100, TimeUnit.MILLISECONDS)) {
                    this.cacheRequestClosed = true;
                    cacheRequest.abort();
                }
                source.close();
            }
        }))).build();
    }

    private static boolean validate(Response cached, Response network) {
        Date networkLastModified;
        if (network.code() == 304) {
            return true;
        }
        Date lastModified = cached.headers().getDate("Last-Modified");
        if (lastModified == null || (networkLastModified = network.headers().getDate("Last-Modified")) == null || networkLastModified.getTime() >= lastModified.getTime()) {
            return false;
        }
        return true;
    }

    private static Headers combine(Headers cachedHeaders, Headers networkHeaders) throws IOException {
        Headers.Builder result = new Headers.Builder();
        int size = cachedHeaders.size();
        for (int i = 0; i < size; i++) {
            String fieldName = cachedHeaders.name(i);
            String value = cachedHeaders.value(i);
            if ((!"Warning".equalsIgnoreCase(fieldName) || !value.startsWith(a.e)) && (!OkHeaders.isEndToEnd(fieldName) || networkHeaders.get(fieldName) == null)) {
                result.add(fieldName, value);
            }
        }
        int size2 = networkHeaders.size();
        for (int i2 = 0; i2 < size2; i2++) {
            String fieldName2 = networkHeaders.name(i2);
            if (!"Content-Length".equalsIgnoreCase(fieldName2) && OkHeaders.isEndToEnd(fieldName2)) {
                result.add(fieldName2, networkHeaders.value(i2));
            }
        }
        return result.build();
    }

    public void receiveHeaders(Headers headers) throws IOException {
        if (this.client.cookieJar() != CookieJar.NO_COOKIES) {
            List<Cookie> cookies = Cookie.parseAll(this.userRequest.url(), headers);
            if (!cookies.isEmpty()) {
                this.client.cookieJar().saveFromResponse(this.userRequest.url(), cookies);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0069, code lost:
        if (r13.client.followRedirects() == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x006b, code lost:
        r1 = r13.userResponse.header("Location");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0073, code lost:
        if (r1 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0075, code lost:
        r9 = r13.userRequest.url().resolve(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007f, code lost:
        if (r9 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0093, code lost:
        if (r9.scheme().equals(r13.userRequest.url().scheme()) != false) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009b, code lost:
        if (r13.client.followSslRedirects() == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009d, code lost:
        r3 = r13.userRequest.newBuilder();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a7, code lost:
        if (okhttp3.internal.http.HttpMethod.permitsRequestBody(r2) == false) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ad, code lost:
        if (okhttp3.internal.http.HttpMethod.redirectsToGet(r2) == false) goto L_0x00d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00af, code lost:
        r3.method("GET", (okhttp3.RequestBody) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b4, code lost:
        r3.removeHeader("Transfer-Encoding");
        r3.removeHeader("Content-Length");
        r3.removeHeader("Content-Type");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c7, code lost:
        if (sameConnection(r9) != false) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c9, code lost:
        r3.removeHeader("Authorization");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d8, code lost:
        r3.method(r2, (okhttp3.RequestBody) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        return r13.client.authenticator().authenticate(r6, r13.userResponse);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        return r3.url(r9).build();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public okhttp3.Request followUpRequest() throws java.io.IOException {
        /*
            r13 = this;
            r10 = 0
            okhttp3.Response r11 = r13.userResponse
            if (r11 != 0) goto L_0x000b
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            r10.<init>()
            throw r10
        L_0x000b:
            okhttp3.internal.http.StreamAllocation r11 = r13.streamAllocation
            okhttp3.internal.io.RealConnection r0 = r11.connection()
            if (r0 == 0) goto L_0x0027
            okhttp3.Route r6 = r0.route()
        L_0x0017:
            okhttp3.Response r11 = r13.userResponse
            int r4 = r11.code()
            okhttp3.Request r11 = r13.userRequest
            java.lang.String r2 = r11.method()
            switch(r4) {
                case 300: goto L_0x0063;
                case 301: goto L_0x0063;
                case 302: goto L_0x0063;
                case 303: goto L_0x0063;
                case 307: goto L_0x0053;
                case 308: goto L_0x0053;
                case 401: goto L_0x0046;
                case 407: goto L_0x0029;
                case 408: goto L_0x00dc;
                default: goto L_0x0026;
            }
        L_0x0026:
            return r10
        L_0x0027:
            r6 = r10
            goto L_0x0017
        L_0x0029:
            if (r6 == 0) goto L_0x003f
            java.net.Proxy r8 = r6.proxy()
        L_0x002f:
            java.net.Proxy$Type r10 = r8.type()
            java.net.Proxy$Type r11 = java.net.Proxy.Type.HTTP
            if (r10 == r11) goto L_0x0046
            java.net.ProtocolException r10 = new java.net.ProtocolException
            java.lang.String r11 = "Received HTTP_PROXY_AUTH (407) code while not using proxy"
            r10.<init>(r11)
            throw r10
        L_0x003f:
            okhttp3.OkHttpClient r10 = r13.client
            java.net.Proxy r8 = r10.proxy()
            goto L_0x002f
        L_0x0046:
            okhttp3.OkHttpClient r10 = r13.client
            okhttp3.Authenticator r10 = r10.authenticator()
            okhttp3.Response r11 = r13.userResponse
            okhttp3.Request r10 = r10.authenticate(r6, r11)
            goto L_0x0026
        L_0x0053:
            java.lang.String r11 = "GET"
            boolean r11 = r2.equals(r11)
            if (r11 != 0) goto L_0x0063
            java.lang.String r11 = "HEAD"
            boolean r11 = r2.equals(r11)
            if (r11 == 0) goto L_0x0026
        L_0x0063:
            okhttp3.OkHttpClient r11 = r13.client
            boolean r11 = r11.followRedirects()
            if (r11 == 0) goto L_0x0026
            okhttp3.Response r11 = r13.userResponse
            java.lang.String r12 = "Location"
            java.lang.String r1 = r11.header(r12)
            if (r1 == 0) goto L_0x0026
            okhttp3.Request r11 = r13.userRequest
            okhttp3.HttpUrl r11 = r11.url()
            okhttp3.HttpUrl r9 = r11.resolve(r1)
            if (r9 == 0) goto L_0x0026
            java.lang.String r11 = r9.scheme()
            okhttp3.Request r12 = r13.userRequest
            okhttp3.HttpUrl r12 = r12.url()
            java.lang.String r12 = r12.scheme()
            boolean r7 = r11.equals(r12)
            if (r7 != 0) goto L_0x009d
            okhttp3.OkHttpClient r11 = r13.client
            boolean r11 = r11.followSslRedirects()
            if (r11 == 0) goto L_0x0026
        L_0x009d:
            okhttp3.Request r11 = r13.userRequest
            okhttp3.Request$Builder r3 = r11.newBuilder()
            boolean r11 = okhttp3.internal.http.HttpMethod.permitsRequestBody(r2)
            if (r11 == 0) goto L_0x00c3
            boolean r11 = okhttp3.internal.http.HttpMethod.redirectsToGet(r2)
            if (r11 == 0) goto L_0x00d8
            java.lang.String r11 = "GET"
            r3.method(r11, r10)
        L_0x00b4:
            java.lang.String r10 = "Transfer-Encoding"
            r3.removeHeader(r10)
            java.lang.String r10 = "Content-Length"
            r3.removeHeader(r10)
            java.lang.String r10 = "Content-Type"
            r3.removeHeader(r10)
        L_0x00c3:
            boolean r10 = r13.sameConnection(r9)
            if (r10 != 0) goto L_0x00ce
            java.lang.String r10 = "Authorization"
            r3.removeHeader(r10)
        L_0x00ce:
            okhttp3.Request$Builder r10 = r3.url((okhttp3.HttpUrl) r9)
            okhttp3.Request r10 = r10.build()
            goto L_0x0026
        L_0x00d8:
            r3.method(r2, r10)
            goto L_0x00b4
        L_0x00dc:
            okio.Sink r11 = r13.requestBodyOut
            if (r11 == 0) goto L_0x00e6
            okio.Sink r11 = r13.requestBodyOut
            boolean r11 = r11 instanceof okhttp3.internal.http.RetryableSink
            if (r11 == 0) goto L_0x00f1
        L_0x00e6:
            r5 = 1
        L_0x00e7:
            boolean r11 = r13.callerWritesRequestBody
            if (r11 == 0) goto L_0x00ed
            if (r5 == 0) goto L_0x0026
        L_0x00ed:
            okhttp3.Request r10 = r13.userRequest
            goto L_0x0026
        L_0x00f1:
            r5 = 0
            goto L_0x00e7
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.HttpEngine.followUpRequest():okhttp3.Request");
    }

    public boolean sameConnection(HttpUrl followUp) {
        HttpUrl url = this.userRequest.url();
        return url.host().equals(followUp.host()) && url.port() == followUp.port() && url.scheme().equals(followUp.scheme());
    }

    private static Address createAddress(OkHttpClient client2, Request request) {
        SSLSocketFactory sslSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        CertificatePinner certificatePinner = null;
        if (request.isHttps()) {
            sslSocketFactory = client2.sslSocketFactory();
            hostnameVerifier = client2.hostnameVerifier();
            certificatePinner = client2.certificatePinner();
        }
        return new Address(request.url().host(), request.url().port(), client2.dns(), client2.socketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, client2.proxyAuthenticator(), client2.proxy(), client2.protocols(), client2.connectionSpecs(), client2.proxySelector());
    }
}
