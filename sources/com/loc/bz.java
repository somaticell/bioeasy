package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

final class bz implements Serializable {
    protected int a = 0;
    protected int b = 0;
    protected int c = 0;
    protected int d = 0;
    protected int e = 0;
    protected short f = 0;
    protected byte g = 0;
    protected byte h = 0;
    protected long i = 0;
    protected long j = 0;
    private byte k = 1;

    bz() {
    }

    /* access modifiers changed from: protected */
    public final Boolean a(DataOutputStream dataOutputStream) {
        boolean z = false;
        if (dataOutputStream == null) {
            return z;
        }
        try {
            dataOutputStream.writeByte(this.k);
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeInt(this.c);
            dataOutputStream.writeInt(this.d);
            dataOutputStream.writeInt(this.e);
            dataOutputStream.writeShort(this.f);
            dataOutputStream.writeByte(this.g);
            dataOutputStream.writeByte(this.h);
            dataOutputStream.writeLong(this.i);
            dataOutputStream.writeLong(this.j);
            return true;
        } catch (IOException e2) {
            return z;
        }
    }
}
