package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

final class dg implements Serializable {
    protected short a = 0;
    protected int b = 0;
    protected byte c = 0;
    protected byte d = 0;
    protected ArrayList e = new ArrayList();
    private byte f = 2;

    dg() {
    }

    /* access modifiers changed from: protected */
    public final Boolean a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.f);
            dataOutputStream.writeShort(this.a);
            dataOutputStream.writeInt(this.b);
            dataOutputStream.writeByte(this.c);
            dataOutputStream.writeByte(this.d);
            for (byte b2 = 0; b2 < this.d; b2++) {
                dataOutputStream.writeShort(((ck) this.e.get(b2)).a);
                dataOutputStream.writeInt(((ck) this.e.get(b2)).b);
                dataOutputStream.writeByte(((ck) this.e.get(b2)).c);
            }
            return true;
        } catch (IOException e2) {
            return null;
        }
    }
}
