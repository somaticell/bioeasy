package okhttp3.internal.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.WeakReference;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.RouteDatabase;
import okhttp3.internal.Util;
import okhttp3.internal.io.RealConnection;
import okio.Sink;

public final class StreamAllocation {
    public final Address address;
    private boolean canceled;
    private RealConnection connection;
    private final ConnectionPool connectionPool;
    private boolean released;
    private Route route;
    private RouteSelector routeSelector;
    private HttpStream stream;

    public StreamAllocation(ConnectionPool connectionPool2, Address address2) {
        this.connectionPool = connectionPool2;
        this.address = address2;
        this.routeSelector = new RouteSelector(address2, routeDatabase());
    }

    public HttpStream newStream(int connectTimeout, int readTimeout, int writeTimeout, boolean connectionRetryEnabled, boolean doExtensiveHealthChecks) throws RouteException, IOException {
        HttpStream resultStream;
        try {
            RealConnection resultConnection = findHealthyConnection(connectTimeout, readTimeout, writeTimeout, connectionRetryEnabled, doExtensiveHealthChecks);
            if (resultConnection.framedConnection != null) {
                resultStream = new Http2xStream(this, resultConnection.framedConnection);
            } else {
                resultConnection.socket().setSoTimeout(readTimeout);
                resultConnection.source.timeout().timeout((long) readTimeout, TimeUnit.MILLISECONDS);
                resultConnection.sink.timeout().timeout((long) writeTimeout, TimeUnit.MILLISECONDS);
                resultStream = new Http1xStream(this, resultConnection.source, resultConnection.sink);
            }
            synchronized (this.connectionPool) {
                this.stream = resultStream;
            }
            return resultStream;
        } catch (IOException e) {
            throw new RouteException(e);
        }
    }

    private RealConnection findHealthyConnection(int connectTimeout, int readTimeout, int writeTimeout, boolean connectionRetryEnabled, boolean doExtensiveHealthChecks) throws IOException, RouteException {
        RealConnection candidate;
        while (true) {
            candidate = findConnection(connectTimeout, readTimeout, writeTimeout, connectionRetryEnabled);
            synchronized (this.connectionPool) {
                if (candidate.successCount != 0) {
                    if (candidate.isHealthy(doExtensiveHealthChecks)) {
                        break;
                    }
                    connectionFailed(new IOException());
                } else {
                    break;
                }
            }
        }
        return candidate;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0048, code lost:
        if (r8 != null) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x004a, code lost:
        r8 = r9.routeSelector.next();
        r2 = r9.connectionPool;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0052, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r9.route = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0055, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0056, code lost:
        r0 = new okhttp3.internal.io.RealConnection(r8);
        acquire(r0);
        r2 = r9.connectionPool;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0060, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        okhttp3.internal.Internal.instance.put(r9.connectionPool, r0);
        r9.connection = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x006c, code lost:
        if (r9.canceled == false) goto L_0x007c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0075, code lost:
        throw new java.io.IOException("Canceled");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x007d, code lost:
        r0.connect(r10, r11, r12, r9.address.connectionSpecs(), r13);
        routeDatabase().connected(r0.route());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private okhttp3.internal.io.RealConnection findConnection(int r10, int r11, int r12, boolean r13) throws java.io.IOException, okhttp3.internal.http.RouteException {
        /*
            r9 = this;
            okhttp3.ConnectionPool r2 = r9.connectionPool
            monitor-enter(r2)
            boolean r1 = r9.released     // Catch:{ all -> 0x000f }
            if (r1 == 0) goto L_0x0012
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x000f }
            java.lang.String r3 = "released"
            r1.<init>(r3)     // Catch:{ all -> 0x000f }
            throw r1     // Catch:{ all -> 0x000f }
        L_0x000f:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x000f }
            throw r1
        L_0x0012:
            okhttp3.internal.http.HttpStream r1 = r9.stream     // Catch:{ all -> 0x000f }
            if (r1 == 0) goto L_0x001e
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x000f }
            java.lang.String r3 = "stream != null"
            r1.<init>(r3)     // Catch:{ all -> 0x000f }
            throw r1     // Catch:{ all -> 0x000f }
        L_0x001e:
            boolean r1 = r9.canceled     // Catch:{ all -> 0x000f }
            if (r1 == 0) goto L_0x002a
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x000f }
            java.lang.String r3 = "Canceled"
            r1.<init>(r3)     // Catch:{ all -> 0x000f }
            throw r1     // Catch:{ all -> 0x000f }
        L_0x002a:
            okhttp3.internal.io.RealConnection r6 = r9.connection     // Catch:{ all -> 0x000f }
            if (r6 == 0) goto L_0x0034
            boolean r1 = r6.noNewStreams     // Catch:{ all -> 0x000f }
            if (r1 != 0) goto L_0x0034
            monitor-exit(r2)     // Catch:{ all -> 0x000f }
        L_0x0033:
            return r6
        L_0x0034:
            okhttp3.internal.Internal r1 = okhttp3.internal.Internal.instance     // Catch:{ all -> 0x000f }
            okhttp3.ConnectionPool r3 = r9.connectionPool     // Catch:{ all -> 0x000f }
            okhttp3.Address r4 = r9.address     // Catch:{ all -> 0x000f }
            okhttp3.internal.io.RealConnection r7 = r1.get(r3, r4, r9)     // Catch:{ all -> 0x000f }
            if (r7 == 0) goto L_0x0045
            r9.connection = r7     // Catch:{ all -> 0x000f }
            monitor-exit(r2)     // Catch:{ all -> 0x000f }
            r6 = r7
            goto L_0x0033
        L_0x0045:
            okhttp3.Route r8 = r9.route     // Catch:{ all -> 0x000f }
            monitor-exit(r2)     // Catch:{ all -> 0x000f }
            if (r8 != 0) goto L_0x0056
            okhttp3.internal.http.RouteSelector r1 = r9.routeSelector
            okhttp3.Route r8 = r1.next()
            okhttp3.ConnectionPool r2 = r9.connectionPool
            monitor-enter(r2)
            r9.route = r8     // Catch:{ all -> 0x0079 }
            monitor-exit(r2)     // Catch:{ all -> 0x0079 }
        L_0x0056:
            okhttp3.internal.io.RealConnection r0 = new okhttp3.internal.io.RealConnection
            r0.<init>(r8)
            r9.acquire(r0)
            okhttp3.ConnectionPool r2 = r9.connectionPool
            monitor-enter(r2)
            okhttp3.internal.Internal r1 = okhttp3.internal.Internal.instance     // Catch:{ all -> 0x0076 }
            okhttp3.ConnectionPool r3 = r9.connectionPool     // Catch:{ all -> 0x0076 }
            r1.put(r3, r0)     // Catch:{ all -> 0x0076 }
            r9.connection = r0     // Catch:{ all -> 0x0076 }
            boolean r1 = r9.canceled     // Catch:{ all -> 0x0076 }
            if (r1 == 0) goto L_0x007c
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x0076 }
            java.lang.String r3 = "Canceled"
            r1.<init>(r3)     // Catch:{ all -> 0x0076 }
            throw r1     // Catch:{ all -> 0x0076 }
        L_0x0076:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0076 }
            throw r1
        L_0x0079:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0079 }
            throw r1
        L_0x007c:
            monitor-exit(r2)     // Catch:{ all -> 0x0076 }
            okhttp3.Address r1 = r9.address
            java.util.List r4 = r1.connectionSpecs()
            r1 = r10
            r2 = r11
            r3 = r12
            r5 = r13
            r0.connect(r1, r2, r3, r4, r5)
            okhttp3.internal.RouteDatabase r1 = r9.routeDatabase()
            okhttp3.Route r2 = r0.route()
            r1.connected(r2)
            r6 = r0
            goto L_0x0033
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.StreamAllocation.findConnection(int, int, int, boolean):okhttp3.internal.io.RealConnection");
    }

    public void streamFinished(boolean noNewStreams, HttpStream stream2) {
        synchronized (this.connectionPool) {
            if (stream2 != null) {
                if (stream2 == this.stream) {
                    if (!noNewStreams) {
                        this.connection.successCount++;
                    }
                }
            }
            throw new IllegalStateException("expected " + this.stream + " but was " + stream2);
        }
        deallocate(noNewStreams, false, true);
    }

    public HttpStream stream() {
        HttpStream httpStream;
        synchronized (this.connectionPool) {
            httpStream = this.stream;
        }
        return httpStream;
    }

    private RouteDatabase routeDatabase() {
        return Internal.instance.routeDatabase(this.connectionPool);
    }

    public synchronized RealConnection connection() {
        return this.connection;
    }

    public void release() {
        deallocate(false, true, false);
    }

    public void noNewStreams() {
        deallocate(true, false, false);
    }

    private void deallocate(boolean noNewStreams, boolean released2, boolean streamFinished) {
        RealConnection connectionToClose = null;
        synchronized (this.connectionPool) {
            if (streamFinished) {
                this.stream = null;
            }
            if (released2) {
                this.released = true;
            }
            if (this.connection != null) {
                if (noNewStreams) {
                    this.connection.noNewStreams = true;
                }
                if (this.stream == null && (this.released || this.connection.noNewStreams)) {
                    release(this.connection);
                    if (this.connection.allocations.isEmpty()) {
                        this.connection.idleAtNanos = System.nanoTime();
                        if (Internal.instance.connectionBecameIdle(this.connectionPool, this.connection)) {
                            connectionToClose = this.connection;
                        }
                    }
                    this.connection = null;
                }
            }
        }
        if (connectionToClose != null) {
            Util.closeQuietly(connectionToClose.socket());
        }
    }

    public void cancel() {
        HttpStream streamToCancel;
        RealConnection connectionToCancel;
        synchronized (this.connectionPool) {
            this.canceled = true;
            streamToCancel = this.stream;
            connectionToCancel = this.connection;
        }
        if (streamToCancel != null) {
            streamToCancel.cancel();
        } else if (connectionToCancel != null) {
            connectionToCancel.cancel();
        }
    }

    public void connectionFailed(IOException e) {
        synchronized (this.connectionPool) {
            if (this.connection != null && this.connection.successCount == 0) {
                if (!(this.route == null || e == null)) {
                    this.routeSelector.connectFailed(this.route, e);
                }
                this.route = null;
            }
        }
        deallocate(true, false, true);
    }

    public void acquire(RealConnection connection2) {
        connection2.allocations.add(new WeakReference(this));
    }

    private void release(RealConnection connection2) {
        int size = connection2.allocations.size();
        for (int i = 0; i < size; i++) {
            if (connection2.allocations.get(i).get() == this) {
                connection2.allocations.remove(i);
                return;
            }
        }
        throw new IllegalStateException();
    }

    public boolean recover(IOException e, Sink requestBodyOut) {
        boolean canRetryRequestBody;
        if (this.connection != null) {
            connectionFailed(e);
        }
        if (requestBodyOut == null || (requestBodyOut instanceof RetryableSink)) {
            canRetryRequestBody = true;
        } else {
            canRetryRequestBody = false;
        }
        if ((this.routeSelector == null || this.routeSelector.hasNext()) && isRecoverable(e) && canRetryRequestBody) {
            return true;
        }
        return false;
    }

    private boolean isRecoverable(IOException e) {
        if (e instanceof ProtocolException) {
            return false;
        }
        if (e instanceof InterruptedIOException) {
            return e instanceof SocketTimeoutException;
        }
        if ((!(e instanceof SSLHandshakeException) || !(e.getCause() instanceof CertificateException)) && !(e instanceof SSLPeerUnverifiedException)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.address.toString();
    }
}
