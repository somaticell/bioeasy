package com.baidu.location.b;

import android.content.SharedPreferences;
import com.baidu.location.Jni;
import com.baidu.location.f;
import com.baidu.location.f.b;
import com.baidu.location.f.c;
import com.baidu.location.f.i;
import com.baidu.location.f.j;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Locale;
import org.achartengine.chart.TimeChart;
import org.json.JSONObject;

public class e {
    private static e i = null;
    private static final String k = (i.a + "/conlts.dat");
    private static int l = -1;
    private static int m = -1;
    private static int n = 0;
    public boolean a = true;
    public boolean b = true;
    public boolean c = false;
    public boolean d = true;
    public boolean e = true;
    public boolean f = true;
    public boolean g = true;
    public boolean h = false;
    private a j = null;

    class a extends com.baidu.location.f.e {
        String a = null;
        boolean b = false;
        boolean c = false;

        public a() {
            this.k = new HashMap();
        }

        public void a() {
            this.h = j.c();
            this.i = 2;
            String encode = Jni.encode(this.a);
            this.a = null;
            if (this.b) {
                this.k.put("qt", "grid");
            } else {
                this.k.put("qt", "conf");
            }
            this.k.put("req", encode);
        }

        public void a(String str, boolean z) {
            if (!this.c) {
                this.c = true;
                this.a = str;
                this.b = z;
                if (z) {
                    a(true, "loc.map.baidu.com");
                } else {
                    c(j.f);
                }
            }
        }

        public void a(boolean z) {
            if (!z || this.j == null) {
                e.this.c((String) null);
            } else if (this.b) {
                e.this.a(this.m);
            } else {
                e.this.c(this.j);
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.c = false;
        }
    }

    private e() {
    }

    public static e a() {
        if (i == null) {
            i = new e();
        }
        return i;
    }

    private void a(int i2) {
        boolean z = true;
        this.a = (i2 & 1) == 1;
        this.b = (i2 & 2) == 2;
        this.c = (i2 & 4) == 4;
        this.d = (i2 & 8) == 8;
        this.f = (i2 & 65536) == 65536;
        if ((i2 & 131072) != 131072) {
            z = false;
        }
        this.g = z;
        if ((i2 & 16) == 16) {
            this.e = false;
        }
    }

    private void a(JSONObject jSONObject) {
        boolean z = false;
        if (jSONObject != null) {
            int i2 = 14400000;
            int i3 = 10;
            try {
                if (!jSONObject.has("ipen") || jSONObject.getInt("ipen") != 0) {
                    z = true;
                }
                if (jSONObject.has("ipvt")) {
                    i2 = jSONObject.getInt("ipvt");
                }
                if (jSONObject.has("ipvn")) {
                    i3 = jSONObject.getInt("ipvn");
                }
                SharedPreferences.Editor edit = f.getServiceContext().getSharedPreferences("MapCoreServicePre", 0).edit();
                edit.putBoolean("ipLocInfoUpload", z);
                edit.putInt("ipValidTime", i2);
                edit.putInt("ipLocInfoUploadTimesPerDay", i3);
                edit.commit();
            } catch (Exception e2) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(byte[] bArr) {
        int i2 = 0;
        if (bArr != null) {
            if (bArr.length < 640) {
                j.w = false;
                j.t = j.r + 0.025d;
                j.s = j.q - 0.025d;
                i2 = 1;
            } else {
                j.w = true;
                j.s = Double.longBitsToDouble(((((long) bArr[7]) & 255) << 56) | ((((long) bArr[6]) & 255) << 48) | ((((long) bArr[5]) & 255) << 40) | ((((long) bArr[4]) & 255) << 32) | ((((long) bArr[3]) & 255) << 24) | ((((long) bArr[2]) & 255) << 16) | ((((long) bArr[1]) & 255) << 8) | (((long) bArr[0]) & 255));
                j.t = Double.longBitsToDouble(((((long) bArr[15]) & 255) << 56) | ((((long) bArr[14]) & 255) << 48) | ((((long) bArr[13]) & 255) << 40) | ((((long) bArr[12]) & 255) << 32) | ((((long) bArr[11]) & 255) << 24) | ((((long) bArr[10]) & 255) << 16) | ((((long) bArr[9]) & 255) << 8) | (((long) bArr[8]) & 255));
                j.v = new byte[625];
                while (i2 < 625) {
                    j.v[i2] = bArr[i2 + 16];
                    i2++;
                }
                i2 = 1;
            }
        }
        if (i2 != 0) {
            try {
                g();
            } catch (Exception e2) {
            }
        }
    }

    private void b(int i2) {
        File file = new File(k);
        if (!file.exists()) {
            i();
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(4);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            randomAccessFile.seek((long) ((readInt * n) + 128));
            byte[] bytes = (b.d + 0).getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            randomAccessFile.writeInt(i2);
            if (readInt2 == n) {
                randomAccessFile.seek(8);
                randomAccessFile.writeInt(readInt2 + 1);
            }
            randomAccessFile.close();
        } catch (Exception e2) {
        }
    }

    private boolean b(String str) {
        boolean z = true;
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ipconf")) {
                    try {
                        a(jSONObject.getJSONObject("ipconf"));
                    } catch (Exception e2) {
                    }
                }
                int parseInt = Integer.parseInt(jSONObject.getString("ver"));
                if (parseInt > j.x) {
                    j.x = parseInt;
                    if (jSONObject.has("gps")) {
                        String[] split = jSONObject.getString("gps").split("\\|");
                        if (split.length > 10) {
                            if (split[0] != null && !split[0].equals("")) {
                                j.y = Float.parseFloat(split[0]);
                            }
                            if (split[1] != null && !split[1].equals("")) {
                                j.z = Float.parseFloat(split[1]);
                            }
                            if (split[2] != null && !split[2].equals("")) {
                                j.A = Float.parseFloat(split[2]);
                            }
                            if (split[3] != null && !split[3].equals("")) {
                                j.B = Float.parseFloat(split[3]);
                            }
                            if (split[4] != null && !split[4].equals("")) {
                                j.C = Integer.parseInt(split[4]);
                            }
                            if (split[5] != null && !split[5].equals("")) {
                                j.D = Integer.parseInt(split[5]);
                            }
                            if (split[6] != null && !split[6].equals("")) {
                                j.E = Integer.parseInt(split[6]);
                            }
                            if (split[7] != null && !split[7].equals("")) {
                                j.F = Integer.parseInt(split[7]);
                            }
                            if (split[8] != null && !split[8].equals("")) {
                                j.G = Integer.parseInt(split[8]);
                            }
                            if (split[9] != null && !split[9].equals("")) {
                                j.H = Integer.parseInt(split[9]);
                            }
                            if (split[10] != null && !split[10].equals("")) {
                                j.I = Integer.parseInt(split[10]);
                            }
                        }
                    }
                    if (jSONObject.has("up")) {
                        String[] split2 = jSONObject.getString("up").split("\\|");
                        if (split2.length > 3) {
                            if (split2[0] != null && !split2[0].equals("")) {
                                j.J = Float.parseFloat(split2[0]);
                            }
                            if (split2[1] != null && !split2[1].equals("")) {
                                j.K = Float.parseFloat(split2[1]);
                            }
                            if (split2[2] != null && !split2[2].equals("")) {
                                j.L = Float.parseFloat(split2[2]);
                            }
                            if (split2[3] != null && !split2[3].equals("")) {
                                j.M = Float.parseFloat(split2[3]);
                            }
                        }
                    }
                    if (jSONObject.has("wf")) {
                        String[] split3 = jSONObject.getString("wf").split("\\|");
                        if (split3.length > 3) {
                            if (split3[0] != null && !split3[0].equals("")) {
                                j.N = Integer.parseInt(split3[0]);
                            }
                            if (split3[1] != null && !split3[1].equals("")) {
                                j.O = Float.parseFloat(split3[1]);
                            }
                            if (split3[2] != null && !split3[2].equals("")) {
                                j.P = Integer.parseInt(split3[2]);
                            }
                            if (split3[3] != null && !split3[3].equals("")) {
                                j.Q = Float.parseFloat(split3[3]);
                            }
                        }
                    }
                    if (jSONObject.has("ab")) {
                        String[] split4 = jSONObject.getString("ab").split("\\|");
                        if (split4.length > 3) {
                            if (split4[0] != null && !split4[0].equals("")) {
                                j.R = Float.parseFloat(split4[0]);
                            }
                            if (split4[1] != null && !split4[1].equals("")) {
                                j.S = Float.parseFloat(split4[1]);
                            }
                            if (split4[2] != null && !split4[2].equals("")) {
                                j.T = Integer.parseInt(split4[2]);
                            }
                            if (split4[3] != null && !split4[3].equals("")) {
                                j.U = Integer.parseInt(split4[3]);
                            }
                        }
                    }
                    if (jSONObject.has("zxd")) {
                        String[] split5 = jSONObject.getString("zxd").split("\\|");
                        if (split5.length > 4) {
                            if (split5[0] != null && !split5[0].equals("")) {
                                j.aq = Float.parseFloat(split5[0]);
                            }
                            if (split5[1] != null && !split5[1].equals("")) {
                                j.ar = Float.parseFloat(split5[1]);
                            }
                            if (split5[2] != null && !split5[2].equals("")) {
                                j.as = Integer.parseInt(split5[2]);
                            }
                            if (split5[3] != null && !split5[3].equals("")) {
                                j.at = Integer.parseInt(split5[3]);
                            }
                            if (split5[4] != null && !split5[4].equals("")) {
                                j.au = Integer.parseInt(split5[4]);
                            }
                        }
                    }
                    if (jSONObject.has("gpc")) {
                        String[] split6 = jSONObject.getString("gpc").split("\\|");
                        if (split6.length > 5) {
                            if (split6[0] != null && !split6[0].equals("")) {
                                if (Integer.parseInt(split6[0]) > 0) {
                                    j.Z = true;
                                } else {
                                    j.Z = false;
                                }
                            }
                            if (split6[1] != null && !split6[1].equals("")) {
                                if (Integer.parseInt(split6[1]) > 0) {
                                    j.aa = true;
                                } else {
                                    j.aa = false;
                                }
                            }
                            if (split6[2] != null && !split6[2].equals("")) {
                                j.ab = Integer.parseInt(split6[2]);
                            }
                            if (split6[3] != null && !split6[3].equals("")) {
                                j.ad = Integer.parseInt(split6[3]);
                            }
                            if (split6[4] != null && !split6[4].equals("")) {
                                int parseInt2 = Integer.parseInt(split6[4]);
                                if (parseInt2 > 0) {
                                    j.aj = (long) parseInt2;
                                    j.af = j.aj * 1000 * 60;
                                    j.ak = j.af >> 2;
                                } else {
                                    j.o = false;
                                }
                            }
                            if (split6[5] != null && !split6[5].equals("")) {
                                j.am = Integer.parseInt(split6[5]);
                            }
                        }
                    }
                    if (jSONObject.has("shak")) {
                        String[] split7 = jSONObject.getString("shak").split("\\|");
                        if (split7.length > 2) {
                            if (split7[0] != null && !split7[0].equals("")) {
                                j.an = Integer.parseInt(split7[0]);
                            }
                            if (split7[1] != null && !split7[1].equals("")) {
                                j.ao = Integer.parseInt(split7[1]);
                            }
                            if (split7[2] != null && !split7[2].equals("")) {
                                j.ap = Float.parseFloat(split7[2]);
                            }
                        }
                    }
                    if (jSONObject.has("dmx")) {
                        j.al = jSONObject.getInt("dmx");
                    }
                    return z;
                }
            } catch (Exception e3) {
                return false;
            }
        }
        z = false;
        return z;
    }

    /* access modifiers changed from: private */
    public void c(String str) {
        int i2;
        m = -1;
        if (str != null) {
            try {
                if (b(str)) {
                    f();
                }
            } catch (Exception e2) {
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ctr")) {
                    m = Integer.parseInt(jSONObject.getString("ctr"));
                }
            } catch (Exception e3) {
            }
            try {
                j();
                if (m != -1) {
                    i2 = m;
                    b(m);
                } else {
                    i2 = l != -1 ? l : -1;
                }
                if (i2 != -1) {
                    a(i2);
                }
            } catch (Exception e4) {
            }
        }
    }

    private void e() {
        String str = "&ver=" + j.x + "&usr=" + b.a().b() + "&app=" + b.d + "&prod=" + b.e;
        if (this.j == null) {
            this.j = new a();
        }
        this.j.a(str, false);
    }

    private void f() {
        String str = i.a + "/config.dat";
        byte[] bytes = String.format(Locale.CHINA, "{\"ver\":\"%d\",\"gps\":\"%.1f|%.1f|%.1f|%.1f|%d|%d|%d|%d|%d|%d|%d\",\"up\":\"%.1f|%.1f|%.1f|%.1f\",\"wf\":\"%d|%.1f|%d|%.1f\",\"ab\":\"%.2f|%.2f|%d|%d\",\"gpc\":\"%d|%d|%d|%d|%d|%d\",\"zxd\":\"%.1f|%.1f|%d|%d|%d\",\"shak\":\"%d|%d|%.1f\",\"dmx\":%d}", new Object[]{Integer.valueOf(j.x), Float.valueOf(j.y), Float.valueOf(j.z), Float.valueOf(j.A), Float.valueOf(j.B), Integer.valueOf(j.C), Integer.valueOf(j.D), Integer.valueOf(j.E), Integer.valueOf(j.F), Integer.valueOf(j.G), Integer.valueOf(j.H), Integer.valueOf(j.I), Float.valueOf(j.J), Float.valueOf(j.K), Float.valueOf(j.L), Float.valueOf(j.M), Integer.valueOf(j.N), Float.valueOf(j.O), Integer.valueOf(j.P), Float.valueOf(j.Q), Float.valueOf(j.R), Float.valueOf(j.S), Integer.valueOf(j.T), Integer.valueOf(j.U), Integer.valueOf(j.Z ? 1 : 0), Integer.valueOf(j.aa ? 1 : 0), Integer.valueOf(j.ab), Integer.valueOf(j.ad), Long.valueOf(j.aj), Integer.valueOf(j.am), Float.valueOf(j.aq), Float.valueOf(j.ar), Integer.valueOf(j.as), Integer.valueOf(j.at), Integer.valueOf(j.au), Integer.valueOf(j.an), Integer.valueOf(j.ao), Float.valueOf(j.ap), Integer.valueOf(j.al)}).getBytes();
        try {
            File file = new File(str);
            if (!file.exists()) {
                File file2 = new File(i.a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(0);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.close();
                } else {
                    return;
                }
            }
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
            randomAccessFile2.seek(0);
            randomAccessFile2.writeBoolean(true);
            randomAccessFile2.seek(2);
            randomAccessFile2.writeInt(bytes.length);
            randomAccessFile2.write(bytes);
            randomAccessFile2.close();
        } catch (Exception e2) {
        }
    }

    private void g() {
        try {
            File file = new File(i.a + "/config.dat");
            if (!file.exists()) {
                File file2 = new File(i.a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(0);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.close();
                } else {
                    return;
                }
            }
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
            randomAccessFile2.seek(1);
            randomAccessFile2.writeBoolean(true);
            randomAccessFile2.seek(1024);
            randomAccessFile2.writeDouble(j.s);
            randomAccessFile2.writeDouble(j.t);
            randomAccessFile2.writeBoolean(j.w);
            if (j.w && j.v != null) {
                randomAccessFile2.write(j.v);
            }
            randomAccessFile2.close();
        } catch (Exception e2) {
        }
    }

    private void h() {
        try {
            File file = new File(i.a + "/config.dat");
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                if (randomAccessFile.readBoolean()) {
                    randomAccessFile.seek(2);
                    int readInt = randomAccessFile.readInt();
                    byte[] bArr = new byte[readInt];
                    randomAccessFile.read(bArr, 0, readInt);
                    b(new String(bArr));
                }
                randomAccessFile.seek(1);
                if (randomAccessFile.readBoolean()) {
                    randomAccessFile.seek(1024);
                    j.s = randomAccessFile.readDouble();
                    j.t = randomAccessFile.readDouble();
                    j.w = randomAccessFile.readBoolean();
                    if (j.w) {
                        j.v = new byte[625];
                        randomAccessFile.read(j.v, 0, 625);
                    }
                }
                randomAccessFile.close();
            }
        } catch (Exception e2) {
        }
        if (!j.o || f.isServing) {
        }
    }

    private void i() {
        try {
            File file = new File(k);
            if (!file.exists()) {
                File file2 = new File(i.a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (!file.createNewFile()) {
                    file = null;
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(128);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
            }
        } catch (Exception e2) {
        }
    }

    private void j() {
        int i2 = 0;
        try {
            File file = new File(k);
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(4);
                int readInt = randomAccessFile.readInt();
                if (readInt > 3000) {
                    randomAccessFile.close();
                    n = 0;
                    i();
                    return;
                }
                int readInt2 = randomAccessFile.readInt();
                randomAccessFile.seek(128);
                byte[] bArr = new byte[readInt];
                while (true) {
                    if (i2 >= readInt2) {
                        break;
                    }
                    randomAccessFile.seek((long) ((readInt * i2) + 128));
                    int readInt3 = randomAccessFile.readInt();
                    if (readInt3 > 0 && readInt3 < readInt) {
                        randomAccessFile.read(bArr, 0, readInt3);
                        if (bArr[readInt3 - 1] == 0) {
                            String str = new String(bArr, 0, readInt3 - 1);
                            b.a();
                            if (str.equals(b.d)) {
                                l = randomAccessFile.readInt();
                                n = i2;
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                    i2++;
                }
                if (i2 == readInt2) {
                    n = readInt2;
                }
                randomAccessFile.close();
            }
        } catch (Exception e2) {
        }
    }

    public void a(String str) {
        if (this.j == null) {
            this.j = new a();
        }
        this.j.a(str, true);
    }

    public void b() {
        h();
    }

    public void c() {
    }

    public void d() {
        if (System.currentTimeMillis() - c.a().d() > TimeChart.DAY) {
            c.a().c(System.currentTimeMillis());
            e();
        }
    }
}
