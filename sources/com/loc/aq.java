package com.loc;

import java.net.Proxy;

/* compiled from: DownloadManager */
public class aq {
    private ar a;
    private at b;

    /* compiled from: DownloadManager */
    public interface a {
        void a(Throwable th);

        void a(byte[] bArr, long j);

        void b();

        void c();
    }

    public aq(at atVar) {
        this(atVar, 0, -1);
    }

    public aq(at atVar, long j, long j2) {
        Proxy proxy;
        this.b = atVar;
        if (atVar.c == null) {
            proxy = null;
        } else {
            proxy = atVar.c;
        }
        this.a = new ar(this.b.a, this.b.b, proxy);
        this.a.b(j2);
        this.a.a(j);
    }

    public void a(a aVar) {
        this.a.a(this.b.c(), this.b.a(), this.b.b(), aVar);
    }
}
