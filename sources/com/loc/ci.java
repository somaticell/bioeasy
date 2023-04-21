package com.loc;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.NeighboringCellInfo;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class ci {
    private Context a;
    private int b = 0;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;

    protected ci(Context context) {
        this.a = context;
        a(768);
        b(768);
    }

    private static int a(int i, int i2) {
        return i < i2 ? i : i2;
    }

    protected static ca a(Location location, cl clVar, int i, byte b2, long j, boolean z) {
        int i2;
        ca caVar = new ca();
        if (i <= 0 || i > 3 || clVar == null) {
            return null;
        }
        boolean z2 = i == 1 || i == 3;
        boolean z3 = i == 2 || i == 3;
        byte[] bytes = clVar.o().getBytes();
        System.arraycopy(bytes, 0, caVar.c, 0, a(bytes.length, caVar.c.length));
        byte[] bytes2 = clVar.f().getBytes();
        System.arraycopy(bytes2, 0, caVar.g, 0, a(bytes2.length, caVar.g.length));
        byte[] bytes3 = clVar.g().getBytes();
        System.arraycopy(bytes3, 0, caVar.a, 0, a(bytes3.length, caVar.a.length));
        byte[] bytes4 = clVar.h().getBytes();
        System.arraycopy(bytes4, 0, caVar.b, 0, a(bytes4.length, caVar.b.length));
        caVar.d = (short) clVar.p();
        caVar.e = (short) clVar.q();
        caVar.f = (byte) clVar.r();
        byte[] bytes5 = clVar.s().getBytes();
        System.arraycopy(bytes5, 0, caVar.h, 0, a(bytes5.length, caVar.h.length));
        long j2 = j / 1000;
        if (location != null && clVar.e()) {
            by byVar = new by();
            byVar.b = (int) j2;
            bz bzVar = new bz();
            bzVar.a = (int) (location.getLongitude() * 1000000.0d);
            bzVar.b = (int) (location.getLatitude() * 1000000.0d);
            bzVar.c = (int) location.getAltitude();
            bzVar.d = (int) location.getAccuracy();
            bzVar.e = (int) location.getSpeed();
            bzVar.f = (short) ((int) location.getBearing());
            if (Build.MODEL.equals("sdk") || (cl.b(clVar.x()) && cb.b)) {
                bzVar.g = 1;
            } else {
                bzVar.g = 0;
            }
            bzVar.h = b2;
            if (bzVar.d > 25) {
                bzVar.h = 5;
            }
            bzVar.i = System.currentTimeMillis();
            bzVar.j = clVar.n();
            byVar.c = bzVar;
            i2 = 1;
            caVar.j.add(byVar);
        } else if (!z) {
            return null;
        } else {
            by byVar2 = new by();
            byVar2.b = (int) j2;
            cd cdVar = new cd();
            cdVar.a = clVar.w();
            for (int i3 = 0; i3 < cdVar.a; i3++) {
                ce ceVar = new ce();
                ceVar.a = (byte) clVar.b(i3).length();
                System.arraycopy(clVar.b(i3).getBytes(), 0, ceVar.b, 0, ceVar.a);
                ceVar.c = clVar.c(i3);
                ceVar.d = clVar.d(i3);
                ceVar.e = clVar.e(i3);
                ceVar.f = clVar.f(i3);
                ceVar.g = clVar.g(i3);
                ceVar.h = (byte) clVar.h(i3).length();
                System.arraycopy(clVar.h(i3).getBytes(), 0, ceVar.i, 0, ceVar.h);
                ceVar.j = clVar.i(i3);
                cdVar.b.add(ceVar);
            }
            byVar2.g = cdVar;
            i2 = 1;
            caVar.j.add(byVar2);
        }
        if (clVar.c() && !clVar.i() && z2 && !z) {
            by byVar3 = new by();
            byVar3.b = (int) j2;
            dg dgVar = new dg();
            List a2 = clVar.a(location.getSpeed());
            if (a2 != null && a2.size() >= 3) {
                dgVar.a = (short) ((Integer) a2.get(0)).intValue();
                dgVar.b = ((Integer) a2.get(1)).intValue();
            }
            dgVar.c = clVar.k();
            List l = clVar.l();
            dgVar.d = (byte) l.size();
            int i4 = 0;
            while (true) {
                int i5 = i4;
                if (i5 >= l.size()) {
                    break;
                }
                ck ckVar = new ck();
                ckVar.a = (short) ((NeighboringCellInfo) l.get(i5)).getLac();
                ckVar.b = ((NeighboringCellInfo) l.get(i5)).getCid();
                ckVar.c = (byte) ((NeighboringCellInfo) l.get(i5)).getRssi();
                dgVar.e.add(ckVar);
                i4 = i5 + 1;
            }
            byVar3.d = dgVar;
            i2 = 2;
            caVar.j.add(byVar3);
        }
        int i6 = i2;
        if (clVar.c() && clVar.i() && z2 && !z) {
            by byVar4 = new by();
            byVar4.b = (int) j2;
            cj cjVar = new cj();
            List b3 = clVar.b(location.getSpeed());
            if (b3 != null && b3.size() >= 6) {
                cjVar.a = ((Integer) b3.get(3)).intValue();
                cjVar.b = ((Integer) b3.get(4)).intValue();
                cjVar.c = (short) ((Integer) b3.get(0)).intValue();
                cjVar.d = (short) ((Integer) b3.get(1)).intValue();
                cjVar.e = ((Integer) b3.get(2)).intValue();
                cjVar.f = clVar.k();
            }
            byVar4.e = cjVar;
            i6++;
            caVar.j.add(byVar4);
        }
        if (clVar.d() && z3 && !z) {
            by byVar5 = new by();
            cf cfVar = new cf();
            List t = clVar.t();
            byVar5.b = (int) (((Long) t.get(0)).longValue() / 1000);
            cfVar.a = (byte) (t.size() - 1);
            int i7 = 1;
            while (true) {
                int i8 = i7;
                if (i8 >= t.size()) {
                    break;
                }
                List list = (List) t.get(i8);
                if (list != null && list.size() >= 3) {
                    cg cgVar = new cg();
                    byte[] bytes6 = ((String) list.get(0)).getBytes();
                    System.arraycopy(bytes6, 0, cgVar.a, 0, a(bytes6.length, cgVar.a.length));
                    cgVar.b = (short) ((Integer) list.get(1)).intValue();
                    byte[] bytes7 = ((String) list.get(2)).getBytes();
                    System.arraycopy(bytes7, 0, cgVar.c, 0, a(bytes7.length, cgVar.c.length));
                    cfVar.b.add(cgVar);
                }
                i7 = i8 + 1;
            }
            byVar5.f = cfVar;
            i6++;
            caVar.j.add(byVar5);
        }
        caVar.i = (short) i6;
        if (i6 >= 2 || z) {
            return caVar;
        }
        return null;
    }

    protected static File a(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/files/"));
    }

    public static Object a(Object obj, String str, Object... objArr) {
        Class<?> cls = obj.getClass();
        Class<Integer>[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return declaredMethod.invoke(obj, objArr);
    }

    private static ArrayList a(File[] fileArr) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < fileArr.length; i++) {
            if (fileArr[i].isFile() && fileArr[i].getName().length() == 10 && TextUtils.isDigitsOnly(fileArr[i].getName())) {
                arrayList.add(fileArr[i]);
            }
        }
        return arrayList;
    }

    protected static byte[] a(BitSet bitSet) {
        byte[] bArr = new byte[(bitSet.size() / 8)];
        for (int i = 0; i < bitSet.size(); i++) {
            int i2 = i / 8;
            bArr[i2] = (byte) (((bitSet.get(i) ? 1 : 0) << (7 - (i % 8))) | bArr[i2]);
        }
        return bArr;
    }

    protected static byte[] a(byte[] bArr) {
        byte[] bArr2 = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.finish();
            gZIPOutputStream.close();
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bArr2;
        } catch (Exception e2) {
            return bArr2;
        }
    }

    protected static byte[] a(byte[] bArr, int i) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        int indexOf = new String(bArr).indexOf(0);
        if (indexOf <= 0) {
            i = 1;
        } else if (indexOf + 1 <= i) {
            i = indexOf + 1;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        bArr2[i - 1] = 0;
        return bArr2;
    }

    public static int b(Object obj, String str, Object... objArr) {
        Class<?> cls = obj.getClass();
        Class<Integer>[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return ((Integer) declaredMethod.invoke(obj, objArr)).intValue();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.util.BitSet b(byte[] r9) {
        /*
            r4 = 1
            r1 = 0
            java.util.BitSet r7 = new java.util.BitSet
            int r0 = r9.length
            int r0 = r0 << 3
            r7.<init>(r0)
            r0 = r1
            r2 = r1
        L_0x000c:
            int r3 = r9.length
            if (r0 >= r3) goto L_0x002b
            r3 = 7
            r5 = r3
        L_0x0011:
            if (r5 < 0) goto L_0x0028
            int r6 = r2 + 1
            byte r3 = r9[r0]
            int r8 = r4 << r5
            r3 = r3 & r8
            int r3 = r3 >> r5
            if (r3 != r4) goto L_0x0026
            r3 = r4
        L_0x001e:
            r7.set(r2, r3)
            int r2 = r5 + -1
            r5 = r2
            r2 = r6
            goto L_0x0011
        L_0x0026:
            r3 = r1
            goto L_0x001e
        L_0x0028:
            int r0 = r0 + 1
            goto L_0x000c
        L_0x002b:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ci.b(byte[]):java.util.BitSet");
    }

    private File c(long j) {
        boolean z;
        File file;
        boolean z2 = false;
        if (Process.myUid() == 1000) {
            return null;
        }
        try {
            z = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e2) {
            z = false;
        }
        if (c() && !z) {
            file = null;
        } else if (d() <= ((long) (this.d / 2))) {
            return null;
        } else {
            File file2 = new File(a(this.a).getPath() + File.separator + "carrierdata");
            if (!file2.exists() || !file2.isDirectory()) {
                file2.mkdirs();
            }
            file = new File(file2.getPath() + File.separator + j);
            try {
                z2 = file.createNewFile();
            } catch (IOException e3) {
            }
        }
        if (!z2) {
            return null;
        }
        return file;
    }

    protected static boolean c() {
        if (Build.VERSION.SDK_INT >= 9) {
            try {
                return ((Boolean) Environment.class.getMethod("isExternalStorageRemovable", (Class[]) null).invoke((Object) null, (Object[]) null)).booleanValue();
            } catch (Exception e2) {
            }
        }
        return true;
    }

    protected static long d() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
    }

    private File e() {
        boolean z;
        File file;
        File[] listFiles;
        if (Process.myUid() == 1000) {
            return null;
        }
        try {
            z = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e2) {
            z = false;
        }
        if (!c() || z) {
            File file2 = new File(a(this.a).getPath() + File.separator + "carrierdata");
            if (file2.exists() && file2.isDirectory() && (listFiles = file2.listFiles()) != null && listFiles.length > 0) {
                ArrayList a2 = a(listFiles);
                if (a2.size() == 1) {
                    if (((File) a2.get(0)).length() < ((long) this.f)) {
                        file = (File) a2.get(0);
                        return file;
                    }
                } else if (a2.size() >= 2) {
                    file = (File) a2.get(0);
                    File file3 = (File) a2.get(1);
                    if (file.getName().compareTo(file3.getName()) <= 0) {
                        file = file3;
                    }
                    return file;
                }
            }
        }
        file = null;
        return file;
    }

    private int f() {
        boolean z;
        File[] listFiles;
        if (Process.myUid() == 1000) {
            return 0;
        }
        try {
            z = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e2) {
            z = false;
        }
        if (c() && !z) {
            return 0;
        }
        File file = new File(a(this.a).getPath() + File.separator + "carrierdata");
        if (!file.exists() || !file.isDirectory() || (listFiles = file.listFiles()) == null || listFiles.length <= 0) {
            return 0;
        }
        ArrayList a2 = a(listFiles);
        return a2.size() == 1 ? ((File) a2.get(0)).length() <= 0 ? 10 : 1 : a2.size() >= 2 ? 2 : 0;
    }

    private File g() {
        boolean z;
        File file;
        File a2;
        File[] listFiles;
        if (Process.myUid() == 1000) {
            return null;
        }
        try {
            z = "mounted".equals(Environment.getExternalStorageState());
        } catch (Exception e2) {
            z = false;
        }
        if ((!c() || z) && (a2 = a(this.a)) != null) {
            File file2 = new File(a2.getPath() + File.separator + "carrierdata");
            if (file2.exists() && file2.isDirectory() && (listFiles = file2.listFiles()) != null && listFiles.length > 0) {
                ArrayList a3 = a(listFiles);
                if (a3.size() >= 2) {
                    File file3 = (File) a3.get(0);
                    file = (File) a3.get(1);
                    if (file3.getName().compareTo(file.getName()) <= 0) {
                        file = file3;
                    }
                    return file;
                }
            }
        }
        file = null;
        return file;
    }

    /* access modifiers changed from: protected */
    public int a() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public synchronized File a(long j) {
        File e2;
        e2 = e();
        if (e2 == null) {
            e2 = c(j);
        }
        return e2;
    }

    /* access modifiers changed from: protected */
    public void a(int i) {
        this.b = i;
        this.d = ((this.b << 3) * 1500) + this.b + 4;
        if (this.b == 256 || this.b == 768) {
            this.f = this.d / 100;
        } else if (this.b == 8736) {
            this.f = this.d - 5000;
        }
    }

    /* access modifiers changed from: protected */
    public File b() {
        return g();
    }

    /* access modifiers changed from: protected */
    public void b(int i) {
        this.c = i;
        this.e = ((this.c << 3) * 1000) + this.c + 4;
        this.g = this.e;
    }

    /* access modifiers changed from: protected */
    public synchronized boolean b(long j) {
        boolean z = false;
        synchronized (this) {
            int f2 = f();
            if (f2 != 0) {
                if (f2 == 1) {
                    if (c(j) != null) {
                        z = true;
                    }
                } else if (f2 == 2) {
                    z = true;
                }
            }
        }
        return z;
    }
}
