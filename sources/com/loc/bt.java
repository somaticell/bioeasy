package com.loc;

import android.text.TextUtils;
import cn.com.bioeasy.app.utils.ListUtils;
import com.alipay.sdk.cons.a;
import java.util.zip.CRC32;

/* compiled from: Req */
public class bt {
    public String A = null;
    public String B = null;
    public String C = null;
    public String D = null;
    public String E = null;
    public String F = null;
    public byte[] G = null;
    public String a = a.e;
    public short b = 0;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;
    public String h = null;
    public String i = null;
    public String j = null;
    public String k = null;
    public String l = null;
    public String m = null;
    public String n = null;
    public String o = null;
    public String p = null;
    public String q = null;
    public String r = null;
    public String s = null;
    public String t = null;
    public String u = null;
    public String v = null;
    public String w = null;
    public String x = null;
    public String y = null;
    public String z = null;

    public byte[] a() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int length;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        b();
        int i20 = 3072;
        if (this.G != null) {
            i20 = 3072 + this.G.length + 1;
        }
        byte[] bArr = new byte[i20];
        bArr[0] = Byte.parseByte(this.a);
        byte[] b2 = bw.b((int) this.b);
        System.arraycopy(b2, 0, bArr, 1, b2.length);
        int length2 = b2.length + 1;
        try {
            byte[] bytes = this.c.getBytes("GBK");
            bArr[length2] = (byte) bytes.length;
            length2++;
            System.arraycopy(bytes, 0, bArr, length2, bytes.length);
            i2 = length2 + bytes.length;
        } catch (Exception e2) {
            bArr[length2] = 0;
            i2 = length2 + 1;
        }
        try {
            byte[] bytes2 = this.d.getBytes("GBK");
            bArr[i2] = (byte) bytes2.length;
            i2++;
            System.arraycopy(bytes2, 0, bArr, i2, bytes2.length);
            i3 = i2 + bytes2.length;
        } catch (Exception e3) {
            bArr[i2] = 0;
            i3 = i2 + 1;
        }
        try {
            byte[] bytes3 = this.o.getBytes("GBK");
            bArr[i3] = (byte) bytes3.length;
            i3++;
            System.arraycopy(bytes3, 0, bArr, i3, bytes3.length);
            i4 = i3 + bytes3.length;
        } catch (Exception e4) {
            bArr[i3] = 0;
            i4 = i3 + 1;
        }
        try {
            byte[] bytes4 = this.e.getBytes("GBK");
            bArr[i4] = (byte) bytes4.length;
            i4++;
            System.arraycopy(bytes4, 0, bArr, i4, bytes4.length);
            i5 = i4 + bytes4.length;
        } catch (Exception e5) {
            bArr[i4] = 0;
            i5 = i4 + 1;
        }
        try {
            byte[] bytes5 = this.f.getBytes("GBK");
            bArr[i5] = (byte) bytes5.length;
            i5++;
            System.arraycopy(bytes5, 0, bArr, i5, bytes5.length);
            i6 = i5 + bytes5.length;
        } catch (Exception e6) {
            bArr[i5] = 0;
            i6 = i5 + 1;
        }
        try {
            byte[] bytes6 = this.g.getBytes("GBK");
            bArr[i6] = (byte) bytes6.length;
            i6++;
            System.arraycopy(bytes6, 0, bArr, i6, bytes6.length);
            i7 = i6 + bytes6.length;
        } catch (Exception e7) {
            bArr[i6] = 0;
            i7 = i6 + 1;
        }
        try {
            byte[] bytes7 = this.u.getBytes("GBK");
            bArr[i7] = (byte) bytes7.length;
            i7++;
            System.arraycopy(bytes7, 0, bArr, i7, bytes7.length);
            i8 = i7 + bytes7.length;
        } catch (Exception e8) {
            bArr[i7] = 0;
            i8 = i7 + 1;
        }
        try {
            byte[] bytes8 = this.h.getBytes("GBK");
            bArr[i8] = (byte) bytes8.length;
            i8++;
            System.arraycopy(bytes8, 0, bArr, i8, bytes8.length);
            i9 = i8 + bytes8.length;
        } catch (Exception e9) {
            bArr[i8] = 0;
            i9 = i8 + 1;
        }
        try {
            byte[] bytes9 = this.p.getBytes("GBK");
            bArr[i9] = (byte) bytes9.length;
            i9++;
            System.arraycopy(bytes9, 0, bArr, i9, bytes9.length);
            i10 = i9 + bytes9.length;
        } catch (Exception e10) {
            bArr[i9] = 0;
            i10 = i9 + 1;
        }
        try {
            byte[] bytes10 = this.q.getBytes("GBK");
            bArr[i10] = (byte) bytes10.length;
            i10++;
            System.arraycopy(bytes10, 0, bArr, i10, bytes10.length);
            i11 = i10 + bytes10.length;
        } catch (Exception e11) {
            bArr[i10] = 0;
            i11 = i10 + 1;
        }
        if (TextUtils.isEmpty(this.t)) {
            bArr[i11] = 0;
            length = i11 + 1;
        } else {
            byte[] b3 = b(this.t);
            bArr[i11] = (byte) b3.length;
            int i21 = i11 + 1;
            System.arraycopy(b3, 0, bArr, i21, b3.length);
            length = i21 + b3.length;
        }
        try {
            byte[] bytes11 = this.v.getBytes("GBK");
            bArr[length] = (byte) bytes11.length;
            length++;
            System.arraycopy(bytes11, 0, bArr, length, bytes11.length);
            i12 = length + bytes11.length;
        } catch (Exception e12) {
            bArr[length] = 0;
            i12 = length + 1;
        }
        try {
            byte[] bytes12 = this.w.getBytes("GBK");
            bArr[i12] = (byte) bytes12.length;
            i12++;
            System.arraycopy(bytes12, 0, bArr, i12, bytes12.length);
            i13 = i12 + bytes12.length;
        } catch (Exception e13) {
            bArr[i12] = 0;
            i13 = i12 + 1;
        }
        try {
            byte[] bytes13 = this.x.getBytes("GBK");
            bArr[i13] = (byte) bytes13.length;
            i13++;
            System.arraycopy(bytes13, 0, bArr, i13, bytes13.length);
            i14 = i13 + bytes13.length;
        } catch (Exception e14) {
            bArr[i13] = 0;
            i14 = i13 + 1;
        }
        bArr[i14] = Byte.parseByte(this.y);
        int i22 = i14 + 1;
        bArr[i22] = Byte.parseByte(this.j);
        int i23 = i22 + 1;
        if (this.j.equals(a.e)) {
            bArr[i23] = Byte.parseByte(this.k);
            i23++;
        }
        if (this.j.equals(a.e) || this.j.equals("2")) {
            byte[] c2 = bw.c(Integer.parseInt(this.l));
            System.arraycopy(c2, 0, bArr, i23, c2.length);
            i23 += c2.length;
        }
        if (this.j.equals(a.e) || this.j.equals("2")) {
            byte[] c3 = bw.c(Integer.parseInt(this.m));
            System.arraycopy(c3, 0, bArr, i23, c3.length);
            i23 += c3.length;
        }
        if (this.j.equals(a.e) || this.j.equals("2")) {
            byte[] e15 = bw.e(this.n);
            System.arraycopy(e15, 0, bArr, i23, e15.length);
            i23 += e15.length;
        }
        bArr[i23] = Byte.parseByte(this.z);
        int i24 = i23 + 1;
        if (this.z.equals(a.e)) {
            byte[] e16 = bw.e(a("mcc"));
            System.arraycopy(e16, 0, bArr, i24, e16.length);
            int length3 = i24 + e16.length;
            byte[] e17 = bw.e(a("mnc"));
            System.arraycopy(e17, 0, bArr, length3, e17.length);
            int length4 = length3 + e17.length;
            byte[] e18 = bw.e(a("lac"));
            System.arraycopy(e18, 0, bArr, length4, e18.length);
            int length5 = length4 + e18.length;
            byte[] f2 = bw.f(a("cellid"));
            System.arraycopy(f2, 0, bArr, length5, f2.length);
            int length6 = f2.length + length5;
            int parseInt = Integer.parseInt(a("signal"));
            if (parseInt > 127) {
                parseInt = 0;
            }
            bArr[length6] = (byte) parseInt;
            int i25 = length6 + 1;
            if (this.B.length() == 0) {
                bArr[i25] = 0;
                i24 = i25 + 1;
            } else {
                int length7 = this.B.split("\\*").length;
                bArr[i25] = (byte) length7;
                i24 = i25 + 1;
                int i26 = 0;
                while (i26 < length7) {
                    byte[] e19 = bw.e(a("lac", i26));
                    System.arraycopy(e19, 0, bArr, i24, e19.length);
                    int length8 = i24 + e19.length;
                    byte[] f3 = bw.f(a("cellid", i26));
                    System.arraycopy(f3, 0, bArr, length8, f3.length);
                    int length9 = f3.length + length8;
                    int parseInt2 = Integer.parseInt(a("signal", i26));
                    if (parseInt2 > 127) {
                        parseInt2 = 0;
                    }
                    bArr[length9] = (byte) parseInt2;
                    i26++;
                    i24 = length9 + 1;
                }
            }
        } else if (this.z.equals("2")) {
            byte[] e20 = bw.e(a("mcc"));
            System.arraycopy(e20, 0, bArr, i24, e20.length);
            int length10 = i24 + e20.length;
            byte[] e21 = bw.e(a("sid"));
            System.arraycopy(e21, 0, bArr, length10, e21.length);
            int length11 = length10 + e21.length;
            byte[] e22 = bw.e(a("nid"));
            System.arraycopy(e22, 0, bArr, length11, e22.length);
            int length12 = length11 + e22.length;
            byte[] e23 = bw.e(a("bid"));
            System.arraycopy(e23, 0, bArr, length12, e23.length);
            int length13 = length12 + e23.length;
            byte[] f4 = bw.f(a("lon"));
            System.arraycopy(f4, 0, bArr, length13, f4.length);
            int length14 = length13 + f4.length;
            byte[] f5 = bw.f(a("lat"));
            System.arraycopy(f5, 0, bArr, length14, f5.length);
            int length15 = f5.length + length14;
            int parseInt3 = Integer.parseInt(a("signal"));
            if (parseInt3 > 127) {
                parseInt3 = 0;
            }
            bArr[length15] = (byte) parseInt3;
            int i27 = length15 + 1;
            bArr[i27] = 0;
            i24 = i27 + 1;
        }
        if (this.C.length() == 0) {
            bArr[i24] = 0;
            i15 = i24 + 1;
        } else {
            bArr[i24] = 1;
            int i28 = i24 + 1;
            try {
                String[] split = this.C.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                byte[] b4 = b(split[0]);
                System.arraycopy(b4, 0, bArr, i28, b4.length);
                i28 += b4.length;
                try {
                    byte[] bytes14 = split[2].getBytes("GBK");
                    bArr[i28] = (byte) bytes14.length;
                    i28++;
                    System.arraycopy(bytes14, 0, bArr, i28, bytes14.length);
                    i28 += bytes14.length;
                } catch (Exception e24) {
                    bArr[i28] = 0;
                    i28++;
                }
                int parseInt4 = Integer.parseInt(split[1]);
                if (parseInt4 > 127) {
                    parseInt4 = 0;
                }
                bArr[i28] = Byte.parseByte(String.valueOf(parseInt4));
                i15 = i28 + 1;
            } catch (Exception e25) {
                byte[] b5 = b("00:00:00:00:00:00");
                System.arraycopy(b5, 0, bArr, i28, b5.length);
                int length16 = i28 + b5.length;
                bArr[length16] = 0;
                int i29 = length16 + 1;
                bArr[i29] = Byte.parseByte("0");
                i15 = i29 + 1;
            }
        }
        String[] split2 = this.D.split("\\*");
        if (TextUtils.isEmpty(this.D) || split2.length == 0) {
            bArr[i15] = 0;
            i16 = i15 + 1;
        } else {
            bArr[i15] = (byte) split2.length;
            int i30 = i15 + 1;
            int i31 = 0;
            while (i31 < split2.length) {
                String[] split3 = split2[i31].split(ListUtils.DEFAULT_JOIN_SEPARATOR);
                byte[] b6 = b(split3[0]);
                System.arraycopy(b6, 0, bArr, i30, b6.length);
                int length17 = i30 + b6.length;
                try {
                    byte[] bytes15 = split3[2].getBytes("GBK");
                    bArr[length17] = (byte) bytes15.length;
                    length17++;
                    System.arraycopy(bytes15, 0, bArr, length17, bytes15.length);
                    i19 = length17 + bytes15.length;
                } catch (Exception e26) {
                    bArr[length17] = 0;
                    i19 = length17 + 1;
                }
                int parseInt5 = Integer.parseInt(split3[1]);
                if (parseInt5 > 127) {
                    parseInt5 = 0;
                }
                bArr[i19] = Byte.parseByte(String.valueOf(parseInt5));
                i31++;
                i30 = i19 + 1;
            }
            byte[] b7 = bw.b(Integer.parseInt(this.E));
            System.arraycopy(b7, 0, bArr, i30, b7.length);
            i16 = i30 + b7.length;
        }
        try {
            byte[] bytes16 = this.F.getBytes("GBK");
            if (bytes16.length > 127) {
                bytes16 = null;
            }
            if (bytes16 == null) {
                bArr[i16] = 0;
                i17 = i16 + 1;
            } else {
                bArr[i16] = (byte) bytes16.length;
                i16++;
                System.arraycopy(bytes16, 0, bArr, i16, bytes16.length);
                i17 = i16 + bytes16.length;
            }
        } catch (Exception e27) {
            bArr[i16] = 0;
            i17 = i16 + 1;
        }
        if (this.G != null) {
            i18 = this.G.length;
        } else {
            i18 = 0;
        }
        byte[] b8 = bw.b(i18);
        System.arraycopy(b8, 0, bArr, i17, b8.length);
        int length18 = i17 + b8.length;
        if (i18 > 0) {
            System.arraycopy(this.G, 0, bArr, length18, this.G.length);
            length18 += this.G.length;
        }
        byte[] bArr2 = new byte[length18];
        System.arraycopy(bArr, 0, bArr2, 0, length18);
        CRC32 crc32 = new CRC32();
        crc32.update(bArr2);
        byte[] a2 = bw.a(crc32.getValue());
        byte[] bArr3 = new byte[(a2.length + length18)];
        System.arraycopy(bArr2, 0, bArr3, 0, length18);
        System.arraycopy(a2, 0, bArr3, length18, a2.length);
        int length19 = length18 + a2.length;
        a(bArr3, 0);
        return bArr3;
    }

    private byte[] b(String str) {
        String[] split = str.split(":");
        if (split == null || split.length != 6) {
            String[] strArr = new String[6];
            for (int i2 = 0; i2 < strArr.length; i2++) {
                strArr[i2] = "0";
            }
            split = strArr;
        }
        byte[] bArr = new byte[6];
        for (int i3 = 0; i3 < split.length; i3++) {
            if (split[i3].length() > 2) {
                split[i3] = split[i3].substring(0, 2);
            }
            bArr[i3] = (byte) Integer.parseInt(split[i3], 16);
        }
        return bArr;
    }

    public String a(String str) {
        if (!this.A.contains(str + ">")) {
            return "0";
        }
        int indexOf = this.A.indexOf(str + ">");
        return this.A.substring(indexOf + str.length() + 1, this.A.indexOf("</" + str));
    }

    private String a(String str, int i2) {
        String[] split = this.B.split("\\*")[i2].split(ListUtils.DEFAULT_JOIN_SEPARATOR);
        if (str.equals("lac")) {
            return split[0];
        }
        if (str.equals("cellid")) {
            return split[1];
        }
        if (str.equals("signal")) {
            return split[2];
        }
        return null;
    }

    public void b() {
        if (TextUtils.isEmpty(this.a)) {
            this.a = "";
        }
        if (TextUtils.isEmpty(this.c)) {
            this.c = "";
        }
        if (TextUtils.isEmpty(this.d)) {
            this.d = "";
        }
        if (TextUtils.isEmpty(this.e)) {
            this.e = "";
        }
        if (TextUtils.isEmpty(this.f)) {
            this.f = "";
        }
        if (TextUtils.isEmpty(this.g)) {
            this.g = "";
        }
        if (TextUtils.isEmpty(this.h)) {
            this.h = "";
        }
        if (TextUtils.isEmpty(this.i)) {
            this.i = "";
        }
        if (TextUtils.isEmpty(this.j)) {
            this.j = "0";
        } else if (!this.j.equals(a.e) && !this.j.equals("2")) {
            this.j = "0";
        }
        if (TextUtils.isEmpty(this.k)) {
            this.k = "0";
        } else if (!this.k.equals("0") && !this.k.equals(a.e)) {
            this.k = "0";
        }
        if (TextUtils.isEmpty(this.l)) {
            this.l = "";
        }
        if (TextUtils.isEmpty(this.m)) {
            this.m = "";
        }
        if (TextUtils.isEmpty(this.n)) {
            this.n = "";
        }
        if (TextUtils.isEmpty(this.o)) {
            this.o = "";
        }
        if (TextUtils.isEmpty(this.p)) {
            this.p = "";
        }
        if (TextUtils.isEmpty(this.q)) {
            this.q = "";
        }
        if (TextUtils.isEmpty(this.r)) {
            this.r = "";
        }
        if (TextUtils.isEmpty(this.s)) {
            this.s = "";
        }
        if (TextUtils.isEmpty(this.t)) {
            this.t = "";
        }
        if (TextUtils.isEmpty(this.u)) {
            this.u = "";
        }
        if (TextUtils.isEmpty(this.v)) {
            this.v = "";
        }
        if (TextUtils.isEmpty(this.w)) {
            this.w = "";
        }
        if (TextUtils.isEmpty(this.x)) {
            this.x = "";
        }
        if (TextUtils.isEmpty(this.y)) {
            this.y = "0";
        } else if (!this.y.equals(a.e) && !this.y.equals("2")) {
            this.y = "0";
        }
        if (TextUtils.isEmpty(this.z)) {
            this.z = "0";
        } else if (!this.z.equals(a.e) && !this.z.equals("2")) {
            this.z = "0";
        }
        if (TextUtils.isEmpty(this.A)) {
            this.A = "";
        }
        if (TextUtils.isEmpty(this.B)) {
            this.B = "";
        }
        if (TextUtils.isEmpty(this.C)) {
            this.C = "";
        }
        if (TextUtils.isEmpty(this.D)) {
            this.D = "";
        }
        if (TextUtils.isEmpty(this.E)) {
            this.E = "";
        }
        if (TextUtils.isEmpty(this.F)) {
            this.F = "";
        }
        if (this.G == null) {
            this.G = new byte[0];
        }
    }

    public static void a(byte[] bArr, int i2) {
    }
}
