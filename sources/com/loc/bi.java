package com.loc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* compiled from: BinaryRandomAccessFile */
public class bi {
    private ByteArrayInputStream a;
    private long b;
    private boolean c = false;
    private RandomAccessFile d = null;
    private boolean e = false;
    private final byte[] f = new byte[8];
    private a g;
    private String h = null;

    /* compiled from: BinaryRandomAccessFile */
    public static class a {
        public boolean a = true;
        public boolean b = true;
    }

    public bi(File file, a aVar) throws IOException, FileNotFoundException, OutOfMemoryError {
        if (aVar != null) {
            if (aVar.a) {
                byte[] a2 = bw.a(file);
                this.a = new ByteArrayInputStream(a2);
                this.b = (long) a2.length;
                this.c = false;
                this.h = file.getAbsolutePath();
            } else {
                this.d = new RandomAccessFile(file, "r");
                this.c = true;
            }
            this.g = aVar;
        }
    }

    public boolean a() {
        if (this.g == null) {
            return false;
        }
        return this.g.a;
    }

    public void b() throws IOException {
        synchronized (this) {
            if (!this.c) {
                if (this.a != null) {
                    this.a.close();
                    this.a = null;
                }
            } else if (this.d != null) {
                this.d.close();
                this.d = null;
            }
            this.e = true;
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        b();
        super.finalize();
    }

    public void a(long j) throws IOException {
        if (j < 0) {
            throw new IOException("offset < 0: " + j);
        }
        h();
        if (this.c) {
            this.d.seek(j);
            return;
        }
        this.a.reset();
        this.a.skip(j);
    }

    public final long c() throws IOException {
        h();
        if (this.c) {
            return this.d.readLong();
        }
        this.a.read(this.f);
        return bw.b(this.f);
    }

    public final int d() throws IOException {
        h();
        if (this.c) {
            return this.d.readUnsignedShort();
        }
        this.a.read(this.f, 0, 2);
        return bw.c(this.f);
    }

    public final int e() throws IOException {
        h();
        if (this.c) {
            return this.d.readInt();
        }
        this.a.read(this.f, 0, 4);
        return bw.d(this.f);
    }

    public final int f() throws IOException {
        h();
        if (this.c) {
            return this.d.readUnsignedByte();
        }
        return this.a.read();
    }

    public long g() throws IOException {
        if (this.e) {
            throw new IOException("file closed");
        } else if (this.c) {
            return this.d.length();
        } else {
            return this.b;
        }
    }

    private void h() throws IOException {
        if (this.e) {
            throw new IOException("file closed");
        }
    }
}
