package com.loc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

final class cf implements Serializable {
    protected byte a = 0;
    protected ArrayList b = new ArrayList();
    private byte c = 8;

    cf() {
    }

    /* access modifiers changed from: protected */
    public final Boolean a(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeByte(this.c);
            dataOutputStream.writeByte(this.a);
            for (byte b2 = 0; b2 < this.a; b2++) {
                cg cgVar = (cg) this.b.get(b2);
                dataOutputStream.write(cgVar.a);
                dataOutputStream.writeShort(cgVar.b);
                dataOutputStream.write(ci.a(cgVar.c, cgVar.c.length));
            }
            return true;
        } catch (IOException e) {
            return null;
        }
    }
}
