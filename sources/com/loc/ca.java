package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

final class ca implements Serializable {
    protected byte[] a = new byte[16];
    protected byte[] b = new byte[16];
    protected byte[] c = new byte[16];
    protected short d = 0;
    protected short e = 0;
    protected byte f = 0;
    protected byte[] g = new byte[16];
    protected byte[] h = new byte[32];
    protected short i = 0;
    protected ArrayList j = new ArrayList();
    private byte k = 41;
    private short l = 0;

    ca() {
    }

    private Boolean a(DataOutputStream dataOutputStream) {
        Boolean.valueOf(true);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream2.flush();
            dataOutputStream2.write(this.a);
            dataOutputStream2.write(this.b);
            dataOutputStream2.write(this.c);
            dataOutputStream2.writeShort(this.d);
            dataOutputStream2.writeShort(this.e);
            dataOutputStream2.writeByte(this.f);
            this.g[15] = 0;
            dataOutputStream2.write(ci.a(this.g, this.g.length));
            this.h[31] = 0;
            dataOutputStream2.write(ci.a(this.h, this.h.length));
            dataOutputStream2.writeShort(this.i);
            for (short s = 0; s < this.i; s = (short) (s + 1)) {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream3 = new DataOutputStream(byteArrayOutputStream2);
                dataOutputStream3.flush();
                by byVar = (by) this.j.get(s);
                if (byVar.c != null && !byVar.c.a(dataOutputStream3).booleanValue()) {
                    Boolean.valueOf(false);
                }
                if (byVar.d != null && !byVar.d.a(dataOutputStream3).booleanValue()) {
                    Boolean.valueOf(false);
                }
                if (byVar.e != null && !byVar.e.a(dataOutputStream3).booleanValue()) {
                    Boolean.valueOf(false);
                }
                if (byVar.f != null && !byVar.f.a(dataOutputStream3).booleanValue()) {
                    Boolean.valueOf(false);
                }
                if (byVar.g != null && !byVar.g.a(dataOutputStream3).booleanValue()) {
                    Boolean.valueOf(false);
                }
                byVar.a = Integer.valueOf(byteArrayOutputStream2.size() + 4).shortValue();
                dataOutputStream2.writeShort(byVar.a);
                dataOutputStream2.writeInt(byVar.b);
                dataOutputStream2.write(byteArrayOutputStream2.toByteArray());
            }
            this.l = Integer.valueOf(byteArrayOutputStream.size()).shortValue();
            dataOutputStream.writeByte(this.k);
            dataOutputStream.writeShort(this.l);
            dataOutputStream.write(byteArrayOutputStream.toByteArray());
            return true;
        } catch (IOException e2) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public final byte[] a() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        a(new DataOutputStream(byteArrayOutputStream));
        return byteArrayOutputStream.toByteArray();
    }
}
