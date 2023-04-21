package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

final class cj implements Serializable {
    protected int a = 0;
    protected int b = 0;
    protected short c = 0;
    protected short d = 0;
    protected int e = 0;
    protected byte f = 0;
    private byte g = 4;

    cj() {
    }

    /* access modifiers changed from: protected */
    public final Boolean a(DataOutputStream dataOutputStream) {
        boolean z = false;
        try {
            dataOutputStream.writeByte(this.g);
            dataOutputStream.writeInt(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeShort(this.c);
            dataOutputStream.writeShort(this.d);
            dataOutputStream.writeInt(this.e);
            dataOutputStream.writeByte(this.f);
            return true;
        } catch (IOException e2) {
            return z;
        }
    }
}
