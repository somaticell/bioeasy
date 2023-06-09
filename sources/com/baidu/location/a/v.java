package com.baidu.location.a;

import android.location.Location;
import com.baidu.location.Jni;
import com.baidu.location.d.b;
import com.baidu.location.d.g;
import com.baidu.location.d.h;
import com.baidu.location.f.e;
import com.baidu.location.f.i;
import com.baidu.location.f.j;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;

public class v {
    private static v A = null;
    private static ArrayList<String> b = new ArrayList<>();
    private static ArrayList<String> c = new ArrayList<>();
    private static ArrayList<String> d = new ArrayList<>();
    private static String e = (i.a + "/yo.dat");
    private static final String f = (i.a + "/yoh.dat");
    private static final String g = (i.a + "/yom.dat");
    private static final String h = (i.a + "/yol.dat");
    private static final String i = (i.a + "/yor.dat");
    private static File j = null;
    private static int k = 8;
    private static int l = 8;
    private static int m = 16;
    private static int n = 1024;
    private static double o = 0.0d;
    private static double p = 0.1d;
    private static double q = 30.0d;
    private static double r = 100.0d;
    private static int s = 0;
    private static int t = 64;
    private static int u = 128;
    private static Location v = null;
    private static Location w = null;
    private static Location x = null;
    private static g y = null;
    private int B;
    long a;
    private a z;

    private class a extends e {
        boolean a = false;
        int b = 0;
        int c = 0;
        private ArrayList<String> e = new ArrayList<>();
        private boolean f = true;

        public a() {
            this.k = new HashMap();
        }

        public void a() {
            this.h = j.c();
            if (this.b != 1) {
                this.h = j.e();
            }
            this.i = 2;
            if (this.e != null) {
                for (int i = 0; i < this.e.size(); i++) {
                    if (this.b == 1) {
                        this.k.put("cldc[" + i + "]", this.e.get(i));
                    } else {
                        this.k.put("cltr[" + i + "]", this.e.get(i));
                    }
                }
                this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
                if (this.b != 1) {
                    this.k.put("qt", "cltrg");
                }
            }
        }

        public void a(boolean z) {
            if (z && this.j != null) {
                if (this.e != null) {
                    this.e.clear();
                }
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    if (jSONObject.has("ison") && jSONObject.getInt("ison") == 0) {
                        this.f = false;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.a = false;
        }

        public synchronized void b() {
            int i = 0;
            synchronized (this) {
                if (!this.a) {
                    if (o <= 4 || this.c >= o) {
                        this.c = 0;
                        this.a = true;
                        this.b = 0;
                        try {
                            if (this.e == null || this.e.size() < 1) {
                                if (this.e == null) {
                                    this.e = new ArrayList<>();
                                }
                                this.b = 0;
                                while (true) {
                                    String b2 = this.b < 2 ? v.b() : null;
                                    if (b2 != null || this.b == 1 || !this.f) {
                                        this.b = 1;
                                    } else {
                                        this.b = 2;
                                        try {
                                            b2 = g.b();
                                        } catch (Exception e2) {
                                            b2 = null;
                                        }
                                    }
                                    if (b2 != null) {
                                        if (!b2.contains("err!")) {
                                            this.e.add(b2);
                                            i += b2.length();
                                            if (i < com.baidu.location.f.a.i) {
                                            }
                                        }
                                    }
                                }
                            }
                            if (this.e == null || this.e.size() < 1) {
                                this.e.clear();
                                this.a = false;
                            } else if (this.b != 1) {
                                c(j.e());
                            } else {
                                c(j.f);
                            }
                        } catch (Exception e3) {
                            if (this.e != null) {
                                this.e.clear();
                            }
                        }
                    } else {
                        this.c++;
                    }
                }
            }
        }
    }

    private v() {
        this.z = null;
        this.B = 0;
        this.a = 0;
        this.z = new a();
        this.B = 0;
    }

    private static synchronized int a(List<String> list, int i2) {
        int i3;
        synchronized (v.class) {
            if (list == null || i2 > 256 || i2 < 0) {
                i3 = -1;
            } else {
                try {
                    if (j == null) {
                        j = new File(e);
                        if (!j.exists()) {
                            j = null;
                            i3 = -2;
                        }
                    }
                    RandomAccessFile randomAccessFile = new RandomAccessFile(j, "rw");
                    if (randomAccessFile.length() < 1) {
                        randomAccessFile.close();
                        i3 = -3;
                    } else {
                        randomAccessFile.seek((long) i2);
                        int readInt = randomAccessFile.readInt();
                        int readInt2 = randomAccessFile.readInt();
                        int readInt3 = randomAccessFile.readInt();
                        int readInt4 = randomAccessFile.readInt();
                        long readLong = randomAccessFile.readLong();
                        if (!a(readInt, readInt2, readInt3, readInt4, readLong) || readInt2 < 1) {
                            randomAccessFile.close();
                            i3 = -4;
                        } else {
                            byte[] bArr = new byte[n];
                            int i4 = readInt2;
                            int i5 = k;
                            while (i5 > 0 && i4 > 0) {
                                randomAccessFile.seek(((long) ((((readInt + i4) - 1) % readInt3) * readInt4)) + readLong);
                                int readInt5 = randomAccessFile.readInt();
                                if (readInt5 > 0 && readInt5 < readInt4) {
                                    randomAccessFile.read(bArr, 0, readInt5);
                                    if (bArr[readInt5 - 1] == 0) {
                                        list.add(new String(bArr, 0, readInt5 - 1));
                                    }
                                }
                                i5--;
                                i4--;
                            }
                            randomAccessFile.seek((long) i2);
                            randomAccessFile.writeInt(readInt);
                            randomAccessFile.writeInt(i4);
                            randomAccessFile.writeInt(readInt3);
                            randomAccessFile.writeInt(readInt4);
                            randomAccessFile.writeLong(readLong);
                            randomAccessFile.close();
                            i3 = k - i5;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    i3 = -5;
                }
            }
        }
        return i3;
    }

    public static synchronized v a() {
        v vVar;
        synchronized (v.class) {
            if (A == null) {
                A = new v();
            }
            vVar = A;
        }
        return vVar;
    }

    public static String a(int i2) {
        String str;
        ArrayList<String> arrayList;
        String str2;
        String str3 = null;
        if (i2 == 1) {
            str = f;
            arrayList = b;
        } else if (i2 == 2) {
            str = g;
            arrayList = c;
        } else if (i2 == 3) {
            str = h;
            arrayList = d;
        } else if (i2 != 4) {
            return null;
        } else {
            str = i;
            arrayList = d;
        }
        if (arrayList == null) {
            return null;
        }
        if (arrayList.size() < 1) {
            a(str, (List<String>) arrayList);
        }
        synchronized (v.class) {
            int size = arrayList.size();
            if (size > 0) {
                try {
                    str2 = arrayList.get(size - 1);
                    try {
                        arrayList.remove(size - 1);
                    } catch (Exception e2) {
                        str3 = str2;
                        str2 = str3;
                        return str2;
                    }
                } catch (Exception e3) {
                    str2 = str3;
                    return str2;
                }
            } else {
                str2 = null;
            }
        }
        return str2;
    }

    public static void a(int i2, boolean z2) {
        String str;
        ArrayList<String> arrayList;
        int i3;
        boolean z3;
        int i4;
        int i5;
        if (i2 == 1) {
            String str2 = f;
            if (!z2) {
                str = str2;
                arrayList = b;
            } else {
                return;
            }
        } else if (i2 == 2) {
            String str3 = g;
            if (z2) {
                str = str3;
                arrayList = b;
            } else {
                str = str3;
                arrayList = c;
            }
        } else if (i2 == 3) {
            String str4 = h;
            if (z2) {
                str = str4;
                arrayList = c;
            } else {
                str = str4;
                arrayList = d;
            }
        } else if (i2 == 4) {
            String str5 = i;
            if (z2) {
                str = str5;
                arrayList = d;
            } else {
                return;
            }
        } else {
            return;
        }
        File file = new File(str);
        if (!file.exists()) {
            a(str);
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(4);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            int readInt4 = randomAccessFile.readInt();
            int readInt5 = randomAccessFile.readInt();
            int size = arrayList.size();
            while (true) {
                i3 = readInt5;
                if (size <= l) {
                    z3 = false;
                    break;
                }
                readInt5 = z2 ? i3 + 1 : i3;
                if (readInt3 >= readInt) {
                    if (!z2) {
                        z3 = true;
                        i3 = readInt5;
                        break;
                    }
                    randomAccessFile.seek((long) ((readInt4 * readInt2) + 128));
                    byte[] bytes = (arrayList.get(0) + 0).getBytes();
                    randomAccessFile.writeInt(bytes.length);
                    randomAccessFile.write(bytes, 0, bytes.length);
                    arrayList.remove(0);
                    i4 = readInt4 + 1;
                    if (i4 > readInt3) {
                        i4 = 0;
                    }
                    i5 = readInt3;
                } else {
                    randomAccessFile.seek((long) ((readInt2 * readInt3) + 128));
                    byte[] bytes2 = (arrayList.get(0) + 0).getBytes();
                    randomAccessFile.writeInt(bytes2.length);
                    randomAccessFile.write(bytes2, 0, bytes2.length);
                    arrayList.remove(0);
                    int i6 = readInt4;
                    i5 = readInt3 + 1;
                    i4 = i6;
                }
                size--;
                readInt3 = i5;
                readInt4 = i4;
            }
            randomAccessFile.seek(12);
            randomAccessFile.writeInt(readInt3);
            randomAccessFile.writeInt(readInt4);
            randomAccessFile.writeInt(i3);
            randomAccessFile.close();
            if (z3 && i2 < 4) {
                a(i2 + 1, true);
            }
        } catch (Exception e2) {
        }
    }

    public static void a(com.baidu.location.d.a aVar, g gVar, Location location, String str) {
        String a2;
        g gVar2 = null;
        if (com.baidu.location.b.e.a().a) {
            if ((j.u == 3 && !a(location, gVar) && !a(location, false)) || aVar == null || aVar.c()) {
                return;
            }
            if (aVar != null && aVar.a()) {
                if (!a(location, gVar)) {
                    gVar = null;
                }
                String a3 = j.a(aVar, gVar, location, str, 1);
                if (a3 != null) {
                    c(Jni.encode(a3));
                    w = location;
                    v = location;
                    if (gVar != null) {
                        y = gVar;
                    }
                }
            } else if (gVar == null || !gVar.k() || !a(location, gVar)) {
                if (!a(location) && !b.a().d()) {
                    str = "&cfr=1" + str;
                } else if (!a(location) && b.a().d()) {
                    str = "&cfr=3" + str;
                } else if (b.a().d()) {
                    str = "&cfr=2" + str;
                }
                if (a(location, gVar)) {
                    gVar2 = gVar;
                }
                if ((aVar != null || gVar2 != null) && (a2 = j.a(aVar, gVar2, location, str, 3)) != null) {
                    e(Jni.encode(a2));
                    v = location;
                    if (gVar2 != null) {
                        y = gVar2;
                    }
                }
            } else {
                if (!a(location) && !b.a().d()) {
                    str = "&cfr=1" + str;
                } else if (!a(location) && b.a().d()) {
                    str = "&cfr=3" + str;
                } else if (b.a().d()) {
                    str = "&cfr=2" + str;
                }
                String a4 = j.a(aVar, gVar, location, str, 2);
                if (a4 != null) {
                    d(Jni.encode(a4));
                    x = location;
                    v = location;
                    if (gVar != null) {
                        y = gVar;
                    }
                }
            }
        }
    }

    public static void a(String str) {
        try {
            File file = new File(str);
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
                randomAccessFile.writeInt(32);
                randomAccessFile.writeInt(2048);
                randomAccessFile.writeInt(1040);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
            }
        } catch (Exception e2) {
        }
    }

    private static boolean a(int i2, int i3, int i4, int i5, long j2) {
        return i2 >= 0 && i2 < i4 && i3 >= 0 && i3 <= i4 && i4 >= 0 && i4 <= 1024 && i5 >= 128 && i5 <= 1024;
    }

    private static boolean a(Location location) {
        if (location == null) {
            return false;
        }
        if (w == null || v == null) {
            w = location;
            return true;
        }
        double distanceTo = (double) location.distanceTo(w);
        return ((double) location.distanceTo(v)) > ((distanceTo * ((double) j.S)) + ((((double) j.R) * distanceTo) * distanceTo)) + ((double) j.T);
    }

    private static boolean a(Location location, g gVar) {
        if (location == null || gVar == null || gVar.a == null || gVar.a.isEmpty() || gVar.b(y)) {
            return false;
        }
        if (x != null) {
            return true;
        }
        x = location;
        return true;
    }

    public static boolean a(Location location, boolean z2) {
        return com.baidu.location.d.e.a(v, location, z2);
    }

    public static boolean a(String str, List<String> list) {
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(8);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            byte[] bArr = new byte[n];
            int i2 = readInt2;
            int i3 = l + 1;
            boolean z2 = false;
            while (i3 > 0 && i2 > 0) {
                if (i2 < readInt3) {
                    readInt3 = 0;
                }
                try {
                    randomAccessFile.seek((long) (((i2 - 1) * readInt) + 128));
                    int readInt4 = randomAccessFile.readInt();
                    if (readInt4 > 0 && readInt4 < readInt) {
                        randomAccessFile.read(bArr, 0, readInt4);
                        if (bArr[readInt4 - 1] == 0) {
                            list.add(0, new String(bArr, 0, readInt4 - 1));
                            z2 = true;
                        }
                    }
                    i3--;
                    i2--;
                } catch (Exception e2) {
                    return z2;
                }
            }
            randomAccessFile.seek(12);
            randomAccessFile.writeInt(i2);
            randomAccessFile.writeInt(readInt3);
            randomAccessFile.close();
            return z2;
        } catch (Exception e3) {
            return false;
        }
    }

    public static String b() {
        return d();
    }

    public static synchronized void b(String str) {
        ArrayList<String> arrayList;
        synchronized (v.class) {
            if (!str.contains("err!")) {
                int i2 = j.p;
                if (i2 == 1) {
                    arrayList = b;
                } else if (i2 == 2) {
                    arrayList = c;
                } else if (i2 == 3) {
                    arrayList = d;
                }
                if (arrayList != null) {
                    if (arrayList.size() <= m) {
                        arrayList.add(str);
                    }
                    if (arrayList.size() >= m) {
                        a(i2, false);
                    }
                    while (arrayList.size() > m) {
                        arrayList.remove(0);
                    }
                }
            }
        }
    }

    private static void c(String str) {
        b(str);
    }

    public static String d() {
        String str = null;
        for (int i2 = 1; i2 < 5; i2++) {
            str = a(i2);
            if (str != null) {
                return str;
            }
        }
        a((List<String>) d, t);
        if (d.size() > 0) {
            str = d.get(0);
            d.remove(0);
        }
        if (str != null) {
            return str;
        }
        a((List<String>) d, s);
        if (d.size() > 0) {
            str = d.get(0);
            d.remove(0);
        }
        if (str != null) {
            return str;
        }
        a((List<String>) d, u);
        if (d.size() <= 0) {
            return str;
        }
        String str2 = d.get(0);
        d.remove(0);
        return str2;
    }

    private static void d(String str) {
        b(str);
    }

    public static void e() {
        l = 0;
        a(1, false);
        a(2, false);
        a(3, false);
        l = 8;
    }

    private static void e(String str) {
        b(str);
    }

    public static String f() {
        File file = new File(g);
        if (file.exists()) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(20);
                int readInt = randomAccessFile.readInt();
                if (readInt > 128) {
                    String str = "&p1=" + readInt;
                    randomAccessFile.seek(20);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.close();
                    return str;
                }
                randomAccessFile.close();
            } catch (Exception e2) {
            }
        }
        File file2 = new File(h);
        if (file2.exists()) {
            try {
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(file2, "rw");
                randomAccessFile2.seek(20);
                int readInt2 = randomAccessFile2.readInt();
                if (readInt2 > 256) {
                    String str2 = "&p2=" + readInt2;
                    randomAccessFile2.seek(20);
                    randomAccessFile2.writeInt(0);
                    randomAccessFile2.close();
                    return str2;
                }
                randomAccessFile2.close();
            } catch (Exception e3) {
            }
        }
        File file3 = new File(i);
        if (!file3.exists()) {
            return null;
        }
        try {
            RandomAccessFile randomAccessFile3 = new RandomAccessFile(file3, "rw");
            randomAccessFile3.seek(20);
            int readInt3 = randomAccessFile3.readInt();
            if (readInt3 > 512) {
                String str3 = "&p3=" + readInt3;
                randomAccessFile3.seek(20);
                randomAccessFile3.writeInt(0);
                randomAccessFile3.close();
                return str3;
            }
            randomAccessFile3.close();
            return null;
        } catch (Exception e4) {
            return null;
        }
    }

    public void c() {
        if (h.j()) {
            this.z.b();
        }
    }
}
