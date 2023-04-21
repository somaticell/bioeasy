package com.loc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;

public final class df {
    private RandomAccessFile a;
    private ci b;
    private String c = "";
    private File d = null;

    protected df(ci ciVar) {
        this.b = ciVar;
    }

    /* access modifiers changed from: protected */
    public final synchronized void a(long j, byte[] bArr) {
        int i = 0;
        synchronized (this) {
            this.d = this.b.a(j);
            if (this.d != null) {
                try {
                    this.a = new RandomAccessFile(this.d, "rw");
                    byte[] bArr2 = new byte[this.b.a()];
                    int readInt = this.a.read(bArr2) == -1 ? 0 : this.a.readInt();
                    BitSet b2 = ci.b(bArr2);
                    int a2 = this.b.a() + 4 + (readInt * 1500);
                    if (readInt < 0 || readInt > (this.b.a() << 3)) {
                        this.a.close();
                        this.d.delete();
                        if (this.a != null) {
                            try {
                                this.a.close();
                            } catch (IOException e) {
                            }
                        }
                    } else {
                        this.a.seek((long) a2);
                        byte[] a3 = ci.a(bArr);
                        this.a.writeInt(a3.length);
                        this.a.writeLong(j);
                        this.a.write(a3);
                        b2.set(readInt, true);
                        this.a.seek(0);
                        this.a.write(ci.a(b2));
                        int i2 = readInt + 1;
                        if (i2 != (this.b.a() << 3)) {
                            i = i2;
                        }
                        this.a.writeInt(i);
                        if (!this.c.equalsIgnoreCase(this.d.getName())) {
                            this.c = this.d.getName();
                        }
                        this.d.length();
                        if (this.a != null) {
                            try {
                                this.a.close();
                            } catch (IOException e2) {
                            }
                        }
                        this.d = null;
                    }
                } catch (FileNotFoundException e3) {
                    if (this.a != null) {
                        try {
                            this.a.close();
                        } catch (IOException e4) {
                        }
                    }
                } catch (IOException e5) {
                    if (this.a != null) {
                        try {
                            this.a.close();
                        } catch (IOException e6) {
                        }
                    }
                } catch (Throwable th) {
                    if (this.a != null) {
                        try {
                            this.a.close();
                        } catch (IOException e7) {
                        }
                    }
                    throw th;
                }
            }
        }
        return;
    }
}
