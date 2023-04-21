package okhttp3.internal.io;

import android.support.v7.widget.ActivityChooserView;
import com.facebook.common.time.Clock;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Connection;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.ConnectionSpecSelector;
import okhttp3.internal.Platform;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.framed.ErrorCode;
import okhttp3.internal.framed.FramedConnection;
import okhttp3.internal.framed.FramedStream;
import okhttp3.internal.http.Http1xStream;
import okhttp3.internal.http.OkHeaders;
import okhttp3.internal.http.StreamAllocation;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public final class RealConnection extends FramedConnection.Listener implements Connection {
    public int allocationLimit;
    public final List<Reference<StreamAllocation>> allocations = new ArrayList();
    public volatile FramedConnection framedConnection;
    private Handshake handshake;
    public long idleAtNanos = Clock.MAX_TIME;
    public boolean noNewStreams;
    private Protocol protocol;
    private Socket rawSocket;
    private final Route route;
    public BufferedSink sink;
    public Socket socket;
    public BufferedSource source;
    public int successCount;

    public RealConnection(Route route2) {
        this.route = route2;
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 134 */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005f A[SYNTHETIC, Splitter:B:15:0x005f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void connect(int r10, int r11, int r12, java.util.List<okhttp3.ConnectionSpec> r13, boolean r14) throws okhttp3.internal.http.RouteException {
        /*
            r9 = this;
            r7 = 0
            okhttp3.Protocol r5 = r9.protocol
            if (r5 == 0) goto L_0x000d
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "already connected"
            r5.<init>(r6)
            throw r5
        L_0x000d:
            r4 = 0
            okhttp3.internal.ConnectionSpecSelector r1 = new okhttp3.internal.ConnectionSpecSelector
            r1.<init>(r13)
            okhttp3.Route r5 = r9.route
            java.net.Proxy r3 = r5.proxy()
            okhttp3.Route r5 = r9.route
            okhttp3.Address r0 = r5.address()
            okhttp3.Route r5 = r9.route
            okhttp3.Address r5 = r5.address()
            javax.net.ssl.SSLSocketFactory r5 = r5.sslSocketFactory()
            if (r5 != 0) goto L_0x005b
            okhttp3.ConnectionSpec r5 = okhttp3.ConnectionSpec.CLEARTEXT
            boolean r5 = r13.contains(r5)
            if (r5 != 0) goto L_0x005b
            okhttp3.internal.http.RouteException r5 = new okhttp3.internal.http.RouteException
            java.net.UnknownServiceException r6 = new java.net.UnknownServiceException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "CLEARTEXT communication not supported: "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r13)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            r5.<init>(r6)
            throw r5
        L_0x0051:
            java.net.Socket r5 = new java.net.Socket     // Catch:{ IOException -> 0x0078 }
            r5.<init>(r3)     // Catch:{ IOException -> 0x0078 }
        L_0x0056:
            r9.rawSocket = r5     // Catch:{ IOException -> 0x0078 }
            r9.connectSocket(r10, r11, r12, r1)     // Catch:{ IOException -> 0x0078 }
        L_0x005b:
            okhttp3.Protocol r5 = r9.protocol
            if (r5 != 0) goto L_0x00a3
            java.net.Proxy$Type r5 = r3.type()     // Catch:{ IOException -> 0x0078 }
            java.net.Proxy$Type r6 = java.net.Proxy.Type.DIRECT     // Catch:{ IOException -> 0x0078 }
            if (r5 == r6) goto L_0x006f
            java.net.Proxy$Type r5 = r3.type()     // Catch:{ IOException -> 0x0078 }
            java.net.Proxy$Type r6 = java.net.Proxy.Type.HTTP     // Catch:{ IOException -> 0x0078 }
            if (r5 != r6) goto L_0x0051
        L_0x006f:
            javax.net.SocketFactory r5 = r0.socketFactory()     // Catch:{ IOException -> 0x0078 }
            java.net.Socket r5 = r5.createSocket()     // Catch:{ IOException -> 0x0078 }
            goto L_0x0056
        L_0x0078:
            r2 = move-exception
            java.net.Socket r5 = r9.socket
            okhttp3.internal.Util.closeQuietly((java.net.Socket) r5)
            java.net.Socket r5 = r9.rawSocket
            okhttp3.internal.Util.closeQuietly((java.net.Socket) r5)
            r9.socket = r7
            r9.rawSocket = r7
            r9.source = r7
            r9.sink = r7
            r9.handshake = r7
            r9.protocol = r7
            if (r4 != 0) goto L_0x009f
            okhttp3.internal.http.RouteException r4 = new okhttp3.internal.http.RouteException
            r4.<init>(r2)
        L_0x0096:
            if (r14 == 0) goto L_0x009e
            boolean r5 = r1.connectionFailed(r2)
            if (r5 != 0) goto L_0x005b
        L_0x009e:
            throw r4
        L_0x009f:
            r4.addConnectException(r2)
            goto L_0x0096
        L_0x00a3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.io.RealConnection.connect(int, int, int, java.util.List, boolean):void");
    }

    private void connectSocket(int connectTimeout, int readTimeout, int writeTimeout, ConnectionSpecSelector connectionSpecSelector) throws IOException {
        this.rawSocket.setSoTimeout(readTimeout);
        try {
            Platform.get().connectSocket(this.rawSocket, this.route.socketAddress(), connectTimeout);
            this.source = Okio.buffer(Okio.source(this.rawSocket));
            this.sink = Okio.buffer(Okio.sink(this.rawSocket));
            if (this.route.address().sslSocketFactory() != null) {
                connectTls(readTimeout, writeTimeout, connectionSpecSelector);
            } else {
                this.protocol = Protocol.HTTP_1_1;
                this.socket = this.rawSocket;
            }
            if (this.protocol == Protocol.SPDY_3 || this.protocol == Protocol.HTTP_2) {
                this.socket.setSoTimeout(0);
                FramedConnection framedConnection2 = new FramedConnection.Builder(true).socket(this.socket, this.route.address().url().host(), this.source, this.sink).protocol(this.protocol).listener(this).build();
                framedConnection2.sendConnectionPreface();
                this.allocationLimit = framedConnection2.maxConcurrentStreams();
                this.framedConnection = framedConnection2;
                return;
            }
            this.allocationLimit = 1;
        } catch (ConnectException e) {
            throw new ConnectException("Failed to connect to " + this.route.socketAddress());
        }
    }

    /* JADX WARNING: type inference failed for: r10v7, types: [java.net.Socket] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void connectTls(int r15, int r16, okhttp3.internal.ConnectionSpecSelector r17) throws java.io.IOException {
        /*
            r14 = this;
            okhttp3.Route r10 = r14.route
            boolean r10 = r10.requiresTunnel()
            if (r10 == 0) goto L_0x000b
            r14.createTunnel(r15, r16)
        L_0x000b:
            okhttp3.Route r10 = r14.route
            okhttp3.Address r1 = r10.address()
            javax.net.ssl.SSLSocketFactory r7 = r1.sslSocketFactory()
            r8 = 0
            r6 = 0
            java.net.Socket r10 = r14.rawSocket     // Catch:{ AssertionError -> 0x00d2 }
            okhttp3.HttpUrl r11 = r1.url()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r11 = r11.host()     // Catch:{ AssertionError -> 0x00d2 }
            okhttp3.HttpUrl r12 = r1.url()     // Catch:{ AssertionError -> 0x00d2 }
            int r12 = r12.port()     // Catch:{ AssertionError -> 0x00d2 }
            r13 = 1
            java.net.Socket r10 = r7.createSocket(r10, r11, r12, r13)     // Catch:{ AssertionError -> 0x00d2 }
            r0 = r10
            javax.net.ssl.SSLSocket r0 = (javax.net.ssl.SSLSocket) r0     // Catch:{ AssertionError -> 0x00d2 }
            r6 = r0
            r0 = r17
            okhttp3.ConnectionSpec r3 = r0.configureSecureSocket(r6)     // Catch:{ AssertionError -> 0x00d2 }
            boolean r10 = r3.supportsTlsExtensions()     // Catch:{ AssertionError -> 0x00d2 }
            if (r10 == 0) goto L_0x0051
            okhttp3.internal.Platform r10 = okhttp3.internal.Platform.get()     // Catch:{ AssertionError -> 0x00d2 }
            okhttp3.HttpUrl r11 = r1.url()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r11 = r11.host()     // Catch:{ AssertionError -> 0x00d2 }
            java.util.List r12 = r1.protocols()     // Catch:{ AssertionError -> 0x00d2 }
            r10.configureTlsExtensions(r6, r11, r12)     // Catch:{ AssertionError -> 0x00d2 }
        L_0x0051:
            r6.startHandshake()     // Catch:{ AssertionError -> 0x00d2 }
            javax.net.ssl.SSLSession r10 = r6.getSession()     // Catch:{ AssertionError -> 0x00d2 }
            okhttp3.Handshake r9 = okhttp3.Handshake.get(r10)     // Catch:{ AssertionError -> 0x00d2 }
            javax.net.ssl.HostnameVerifier r10 = r1.hostnameVerifier()     // Catch:{ AssertionError -> 0x00d2 }
            okhttp3.HttpUrl r11 = r1.url()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r11 = r11.host()     // Catch:{ AssertionError -> 0x00d2 }
            javax.net.ssl.SSLSession r12 = r6.getSession()     // Catch:{ AssertionError -> 0x00d2 }
            boolean r10 = r10.verify(r11, r12)     // Catch:{ AssertionError -> 0x00d2 }
            if (r10 != 0) goto L_0x00ef
            java.util.List r10 = r9.peerCertificates()     // Catch:{ AssertionError -> 0x00d2 }
            r11 = 0
            java.lang.Object r2 = r10.get(r11)     // Catch:{ AssertionError -> 0x00d2 }
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2     // Catch:{ AssertionError -> 0x00d2 }
            javax.net.ssl.SSLPeerUnverifiedException r10 = new javax.net.ssl.SSLPeerUnverifiedException     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ AssertionError -> 0x00d2 }
            r11.<init>()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r12 = "Hostname "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ AssertionError -> 0x00d2 }
            okhttp3.HttpUrl r12 = r1.url()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r12 = r12.host()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r12 = " not verified:"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r12 = "\n    certificate: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r12 = okhttp3.CertificatePinner.pin(r2)     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r12 = "\n    DN: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ AssertionError -> 0x00d2 }
            java.security.Principal r12 = r2.getSubjectDN()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r12 = r12.getName()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r12 = "\n    subjectAltNames: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ AssertionError -> 0x00d2 }
            java.util.List r12 = okhttp3.internal.tls.OkHostnameVerifier.allSubjectAltNames(r2)     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r11 = r11.toString()     // Catch:{ AssertionError -> 0x00d2 }
            r10.<init>(r11)     // Catch:{ AssertionError -> 0x00d2 }
            throw r10     // Catch:{ AssertionError -> 0x00d2 }
        L_0x00d2:
            r4 = move-exception
            boolean r10 = okhttp3.internal.Util.isAndroidGetsocknameError(r4)     // Catch:{ all -> 0x00df }
            if (r10 == 0) goto L_0x0149
            java.io.IOException r10 = new java.io.IOException     // Catch:{ all -> 0x00df }
            r10.<init>(r4)     // Catch:{ all -> 0x00df }
            throw r10     // Catch:{ all -> 0x00df }
        L_0x00df:
            r10 = move-exception
            if (r6 == 0) goto L_0x00e9
            okhttp3.internal.Platform r11 = okhttp3.internal.Platform.get()
            r11.afterHandshake(r6)
        L_0x00e9:
            if (r8 != 0) goto L_0x00ee
            okhttp3.internal.Util.closeQuietly((java.net.Socket) r6)
        L_0x00ee:
            throw r10
        L_0x00ef:
            okhttp3.CertificatePinner r10 = r1.certificatePinner()     // Catch:{ AssertionError -> 0x00d2 }
            okhttp3.HttpUrl r11 = r1.url()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r11 = r11.host()     // Catch:{ AssertionError -> 0x00d2 }
            java.util.List r12 = r9.peerCertificates()     // Catch:{ AssertionError -> 0x00d2 }
            r10.check((java.lang.String) r11, (java.util.List<java.security.cert.Certificate>) r12)     // Catch:{ AssertionError -> 0x00d2 }
            boolean r10 = r3.supportsTlsExtensions()     // Catch:{ AssertionError -> 0x00d2 }
            if (r10 == 0) goto L_0x0144
            okhttp3.internal.Platform r10 = okhttp3.internal.Platform.get()     // Catch:{ AssertionError -> 0x00d2 }
            java.lang.String r5 = r10.getSelectedProtocol(r6)     // Catch:{ AssertionError -> 0x00d2 }
        L_0x0110:
            r14.socket = r6     // Catch:{ AssertionError -> 0x00d2 }
            java.net.Socket r10 = r14.socket     // Catch:{ AssertionError -> 0x00d2 }
            okio.Source r10 = okio.Okio.source((java.net.Socket) r10)     // Catch:{ AssertionError -> 0x00d2 }
            okio.BufferedSource r10 = okio.Okio.buffer((okio.Source) r10)     // Catch:{ AssertionError -> 0x00d2 }
            r14.source = r10     // Catch:{ AssertionError -> 0x00d2 }
            java.net.Socket r10 = r14.socket     // Catch:{ AssertionError -> 0x00d2 }
            okio.Sink r10 = okio.Okio.sink((java.net.Socket) r10)     // Catch:{ AssertionError -> 0x00d2 }
            okio.BufferedSink r10 = okio.Okio.buffer((okio.Sink) r10)     // Catch:{ AssertionError -> 0x00d2 }
            r14.sink = r10     // Catch:{ AssertionError -> 0x00d2 }
            r14.handshake = r9     // Catch:{ AssertionError -> 0x00d2 }
            if (r5 == 0) goto L_0x0146
            okhttp3.Protocol r10 = okhttp3.Protocol.get(r5)     // Catch:{ AssertionError -> 0x00d2 }
        L_0x0132:
            r14.protocol = r10     // Catch:{ AssertionError -> 0x00d2 }
            r8 = 1
            if (r6 == 0) goto L_0x013e
            okhttp3.internal.Platform r10 = okhttp3.internal.Platform.get()
            r10.afterHandshake(r6)
        L_0x013e:
            if (r8 != 0) goto L_0x0143
            okhttp3.internal.Util.closeQuietly((java.net.Socket) r6)
        L_0x0143:
            return
        L_0x0144:
            r5 = 0
            goto L_0x0110
        L_0x0146:
            okhttp3.Protocol r10 = okhttp3.Protocol.HTTP_1_1     // Catch:{ AssertionError -> 0x00d2 }
            goto L_0x0132
        L_0x0149:
            throw r4     // Catch:{ all -> 0x00df }
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.io.RealConnection.connectTls(int, int, okhttp3.internal.ConnectionSpecSelector):void");
    }

    private void createTunnel(int readTimeout, int writeTimeout) throws IOException {
        Request tunnelRequest = createTunnelRequest();
        String requestLine = "CONNECT " + Util.hostHeader(tunnelRequest.url(), true) + " HTTP/1.1";
        do {
            Http1xStream tunnelConnection = new Http1xStream((StreamAllocation) null, this.source, this.sink);
            this.source.timeout().timeout((long) readTimeout, TimeUnit.MILLISECONDS);
            this.sink.timeout().timeout((long) writeTimeout, TimeUnit.MILLISECONDS);
            tunnelConnection.writeRequest(tunnelRequest.headers(), requestLine);
            tunnelConnection.finishRequest();
            Response response = tunnelConnection.readResponse().request(tunnelRequest).build();
            long contentLength = OkHeaders.contentLength(response);
            if (contentLength == -1) {
                contentLength = 0;
            }
            Source body = tunnelConnection.newFixedLengthSource(contentLength);
            Util.skipAll(body, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, TimeUnit.MILLISECONDS);
            body.close();
            switch (response.code()) {
                case 200:
                    if (!this.source.buffer().exhausted() || !this.sink.buffer().exhausted()) {
                        throw new IOException("TLS tunnel buffered too many bytes!");
                    }
                    return;
                case 407:
                    tunnelRequest = this.route.address().proxyAuthenticator().authenticate(this.route, response);
                    break;
                default:
                    throw new IOException("Unexpected response code for CONNECT: " + response.code());
            }
        } while (tunnelRequest != null);
        throw new IOException("Failed to authenticate with proxy");
    }

    private Request createTunnelRequest() throws IOException {
        return new Request.Builder().url(this.route.address().url()).header("Host", Util.hostHeader(this.route.address().url(), true)).header("Proxy-Connection", "Keep-Alive").header("User-Agent", Version.userAgent()).build();
    }

    /* access modifiers changed from: package-private */
    public boolean isConnected() {
        return this.protocol != null;
    }

    public Route route() {
        return this.route;
    }

    public void cancel() {
        Util.closeQuietly(this.rawSocket);
    }

    public Socket socket() {
        return this.socket;
    }

    public boolean isHealthy(boolean doExtensiveChecks) {
        int readTimeout;
        if (this.socket.isClosed() || this.socket.isInputShutdown() || this.socket.isOutputShutdown()) {
            return false;
        }
        if (this.framedConnection != null || !doExtensiveChecks) {
            return true;
        }
        try {
            readTimeout = this.socket.getSoTimeout();
            this.socket.setSoTimeout(1);
            if (this.source.exhausted()) {
                this.socket.setSoTimeout(readTimeout);
                return false;
            }
            this.socket.setSoTimeout(readTimeout);
            return true;
        } catch (SocketTimeoutException e) {
            return true;
        } catch (IOException e2) {
            return false;
        } catch (Throwable th) {
            this.socket.setSoTimeout(readTimeout);
            throw th;
        }
    }

    public void onStream(FramedStream stream) throws IOException {
        stream.close(ErrorCode.REFUSED_STREAM);
    }

    public void onSettings(FramedConnection connection) {
        this.allocationLimit = connection.maxConcurrentStreams();
    }

    public Handshake handshake() {
        return this.handshake;
    }

    public boolean isMultiplexed() {
        return this.framedConnection != null;
    }

    public Protocol protocol() {
        if (this.framedConnection == null) {
            return this.protocol != null ? this.protocol : Protocol.HTTP_1_1;
        }
        return this.framedConnection.getProtocol();
    }

    public String toString() {
        return "Connection{" + this.route.address().url().host() + ":" + this.route.address().url().port() + ", proxy=" + this.route.proxy() + " hostAddress=" + this.route.socketAddress() + " cipherSuite=" + (this.handshake != null ? this.handshake.cipherSuite() : "none") + " protocol=" + this.protocol + '}';
    }
}
