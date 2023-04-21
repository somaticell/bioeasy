package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

final class cd implements Serializable {
    protected byte a = 0;
    protected ArrayList b = new ArrayList();
    private byte c = 3;

    cd() {
    }

    /* access modifiers changed from: protected */
    public final Boolean a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.c);
            dataOutputStream.writeByte(this.a);
            for (int i = 0; i < this.b.size(); i++) {
                ce ceVar = (ce) this.b.get(i);
                dataOutputStream.writeByte(ceVar.a);
                byte[] bArr = new byte[ceVar.a];
                System.arraycopy(ceVar.b, 0, bArr, 0, ceVar.a < ceVar.b.length ? ceVar.a : ceVar.b.length);
                dataOutputStream.write(bArr);
                dataOutputStream.writeDouble(ceVar.c);
                dataOutputStream.writeInt(ceVar.d);
                dataOutputStream.writeInt(ceVar.e);
                dataOutputStream.writeDouble(ceVar.f);
                dataOutputStream.writeByte(ceVar.g);
                dataOutputStream.writeByte(ceVar.h);
                byte[] bArr2 = new byte[ceVar.h];
                System.arraycopy(ceVar.i, 0, bArr2, 0, ceVar.h < ceVar.i.length ? ceVar.h : ceVar.i.length);
                dataOutputStream.write(bArr2);
                dataOutputStream.writeByte(ceVar.j);
            }
            return true;
        } catch (IOException e) {
            return null;
        }
    }
}
