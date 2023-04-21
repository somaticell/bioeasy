package com.loc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.zip.GZIPInputStream;

public final class de {
    private RandomAccessFile a;
    private ci b;
    private File c = null;

    protected de(ci ciVar) {
        this.b = ciVar;
    }

    private static byte a(byte[] bArr) {
        byte[] bArr2 = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            byte[] bArr3 = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = gZIPInputStream.read(bArr3, 0, bArr3.length);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr3, 0, read);
            }
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            gZIPInputStream.close();
            byteArrayInputStream.close();
        } catch (Exception e) {
        }
        return bArr2[0];
    }

    private static int a(int i, int i2, int i3) {
        int i4 = ((i3 - 1) * 1500) + i;
        while (i4 >= i2) {
            i4 -= 1500;
        }
        return i4;
    }

    private int a(BitSet bitSet) {
        for (int i = 0; i < bitSet.length(); i++) {
            if (bitSet.get(i)) {
                return this.b.a() + (i * 1500) + 4;
            }
        }
        return 0;
    }

    private ArrayList a(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        while (i <= i2) {
            try {
                this.a.seek((long) i);
                int readInt = this.a.readInt();
                this.a.readLong();
                if (readInt <= 0 || readInt > 1500) {
                    return null;
                }
                byte[] bArr = new byte[readInt];
                this.a.read(bArr);
                byte a2 = a(bArr);
                if (a2 != 3 && a2 != 4 && a2 != 41) {
                    return null;
                }
                arrayList.add(bArr);
                i += 1500;
            } catch (IOException e) {
            }
        }
        return arrayList;
    }

    private BitSet b() {
        byte[] bArr = new byte[this.b.a()];
        try {
            this.a.read(bArr);
            return ci.b(bArr);
        } catch (IOException e) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x006d, code lost:
        if (r4.a != null) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r4.a.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0087, code lost:
        if (r4.a != null) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
        r4.a.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0091, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0094, code lost:
        if (r4.a != null) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        r4.a.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x006a A[ExcHandler: FileNotFoundException (e java.io.FileNotFoundException), Splitter:B:4:0x000a] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0084 A[ExcHandler: NullPointerException (e java.lang.NullPointerException), Splitter:B:4:0x000a] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0091 A[ExcHandler: all (r0v2 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:4:0x000a] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:30:0x0059=Splitter:B:30:0x0059, B:18:0x003b=Splitter:B:18:0x003b, B:69:0x009b=Splitter:B:69:0x009b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int a() {
        /*
            r4 = this;
            r0 = 0
            monitor-enter(r4)
            com.loc.ci r1 = r4.b     // Catch:{ all -> 0x0067 }
            java.io.File r1 = r1.b()     // Catch:{ all -> 0x0067 }
            r4.c = r1     // Catch:{ all -> 0x0067 }
            java.io.File r1 = r4.c     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            if (r1 == 0) goto L_0x0059
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            com.loc.ci r2 = r4.b     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            java.io.File r2 = r2.b()     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            java.lang.String r3 = "rw"
            r1.<init>(r2, r3)     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            r4.a = r1     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            com.loc.ci r1 = r4.b     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            int r1 = r1.a()     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            byte[] r1 = new byte[r1]     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            boolean r2 = com.loc.cl.c     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            if (r2 == 0) goto L_0x003e
            java.io.RandomAccessFile r2 = r4.a     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            if (r2 == 0) goto L_0x003e
            java.io.RandomAccessFile r2 = r4.a     // Catch:{ IOException -> 0x003d, FileNotFoundException -> 0x006a, NullPointerException -> 0x0084, all -> 0x0091 }
            r2.close()     // Catch:{ IOException -> 0x003d, FileNotFoundException -> 0x006a, NullPointerException -> 0x0084, all -> 0x0091 }
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x003b
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x00a0 }
            r1.close()     // Catch:{ IOException -> 0x00a0 }
        L_0x003b:
            monitor-exit(r4)     // Catch:{ all -> 0x0067 }
        L_0x003c:
            return r0
        L_0x003d:
            r2 = move-exception
        L_0x003e:
            java.io.RandomAccessFile r2 = r4.a     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            r2.read(r1)     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            java.util.BitSet r2 = com.loc.ci.b((byte[]) r1)     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            r1 = r0
        L_0x0048:
            int r3 = r2.size()     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            if (r1 >= r3) goto L_0x0059
            boolean r3 = r2.get(r1)     // Catch:{ FileNotFoundException -> 0x006a, IOException -> 0x0077, NullPointerException -> 0x0084, all -> 0x0091 }
            if (r3 == 0) goto L_0x0056
            int r0 = r0 + 1
        L_0x0056:
            int r1 = r1 + 1
            goto L_0x0048
        L_0x0059:
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x0062
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x009e }
            r1.close()     // Catch:{ IOException -> 0x009e }
        L_0x0062:
            r1 = 0
            r4.c = r1     // Catch:{ all -> 0x0067 }
            monitor-exit(r4)     // Catch:{ all -> 0x0067 }
            goto L_0x003c
        L_0x0067:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x006a:
            r1 = move-exception
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x0062
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x0075 }
            r1.close()     // Catch:{ IOException -> 0x0075 }
            goto L_0x0062
        L_0x0075:
            r1 = move-exception
            goto L_0x0062
        L_0x0077:
            r1 = move-exception
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x0062
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x0082 }
            r1.close()     // Catch:{ IOException -> 0x0082 }
            goto L_0x0062
        L_0x0082:
            r1 = move-exception
            goto L_0x0062
        L_0x0084:
            r1 = move-exception
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x0062
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x008f }
            r1.close()     // Catch:{ IOException -> 0x008f }
            goto L_0x0062
        L_0x008f:
            r1 = move-exception
            goto L_0x0062
        L_0x0091:
            r0 = move-exception
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x009b
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x009c }
            r1.close()     // Catch:{ IOException -> 0x009c }
        L_0x009b:
            throw r0     // Catch:{ all -> 0x0067 }
        L_0x009c:
            r1 = move-exception
            goto L_0x009b
        L_0x009e:
            r1 = move-exception
            goto L_0x0062
        L_0x00a0:
            r1 = move-exception
            goto L_0x003b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.de.a():int");
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:97:0x00f0=Splitter:B:97:0x00f0, B:44:0x0055=Splitter:B:44:0x0055, B:55:0x007a=Splitter:B:55:0x007a, B:32:0x003e=Splitter:B:32:0x003e} */
    protected final synchronized com.loc.ch a(int r8) {
        /*
            r7 = this;
            r0 = 0
            monitor-enter(r7)
            com.loc.ci r1 = r7.b     // Catch:{ all -> 0x001a }
            if (r1 != 0) goto L_0x0008
        L_0x0006:
            monitor-exit(r7)
            return r0
        L_0x0008:
            monitor-enter(r7)     // Catch:{ all -> 0x001a }
            com.loc.ci r1 = r7.b     // Catch:{ all -> 0x0017 }
            java.io.File r1 = r1.b()     // Catch:{ all -> 0x0017 }
            r7.c = r1     // Catch:{ all -> 0x0017 }
            java.io.File r1 = r7.c     // Catch:{ all -> 0x0017 }
            if (r1 != 0) goto L_0x001d
            monitor-exit(r7)     // Catch:{ all -> 0x0017 }
            goto L_0x0006
        L_0x0017:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x001a }
            throw r0     // Catch:{ all -> 0x001a }
        L_0x001a:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        L_0x001d:
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            java.io.File r2 = r7.c     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            java.lang.String r3 = "rw"
            r1.<init>(r2, r3)     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            r7.a = r1     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            boolean r1 = com.loc.cl.c     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            if (r1 == 0) goto L_0x0041
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            if (r1 == 0) goto L_0x0041
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ IOException -> 0x0040 }
            r1.close()     // Catch:{ IOException -> 0x0040 }
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ all -> 0x0017 }
            if (r1 == 0) goto L_0x003e
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ Exception -> 0x00fe }
            r1.close()     // Catch:{ Exception -> 0x00fe }
        L_0x003e:
            monitor-exit(r7)     // Catch:{ all -> 0x0017 }
            goto L_0x0006
        L_0x0040:
            r1 = move-exception
        L_0x0041:
            java.util.BitSet r1 = r7.b()     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            if (r1 != 0) goto L_0x0057
            java.io.File r1 = r7.c     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            r1.delete()     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ all -> 0x0017 }
            if (r1 == 0) goto L_0x0055
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ Exception -> 0x00fb }
            r1.close()     // Catch:{ Exception -> 0x00fb }
        L_0x0055:
            monitor-exit(r7)     // Catch:{ all -> 0x0017 }
            goto L_0x0006
        L_0x0057:
            int r1 = r7.a((java.util.BitSet) r1)     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            java.io.File r2 = r7.c     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            long r2 = r2.length()     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            int r2 = (int) r2     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            int r2 = a(r1, r2, r8)     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            java.util.ArrayList r3 = r7.a(r1, r2)     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            if (r3 != 0) goto L_0x007c
            java.io.File r1 = r7.c     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            r1.delete()     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ all -> 0x0017 }
            if (r1 == 0) goto L_0x007a
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ Exception -> 0x00f9 }
            r1.close()     // Catch:{ Exception -> 0x00f9 }
        L_0x007a:
            monitor-exit(r7)     // Catch:{ all -> 0x0017 }
            goto L_0x0006
        L_0x007c:
            r4 = 2
            int[] r4 = new int[r4]     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            r5 = 0
            com.loc.ci r6 = r7.b     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            int r6 = r6.a()     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            int r1 = r1 - r6
            int r1 = r1 + -4
            int r1 = r1 / 1500
            r4[r5] = r1     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            r1 = 1
            com.loc.ci r5 = r7.b     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            int r5 = r5.a()     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            int r2 = r2 - r5
            int r2 = r2 + -4
            int r2 = r2 / 1500
            r4[r1] = r2     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            com.loc.ch r1 = new com.loc.ch     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            java.io.File r2 = r7.c     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            r1.<init>(r2, r3, r4)     // Catch:{ FileNotFoundException -> 0x00c8, Exception -> 0x00d7, all -> 0x00e6 }
            java.io.RandomAccessFile r2 = r7.a     // Catch:{ all -> 0x0017 }
            if (r2 == 0) goto L_0x00ab
            java.io.RandomAccessFile r2 = r7.a     // Catch:{ Exception -> 0x00f7 }
            r2.close()     // Catch:{ Exception -> 0x00f7 }
        L_0x00ab:
            if (r1 == 0) goto L_0x00bd
            int r2 = r1.c()     // Catch:{ all -> 0x0017 }
            r3 = 100
            if (r2 <= r3) goto L_0x00bd
            int r2 = r1.c()     // Catch:{ all -> 0x0017 }
            r3 = 5242880(0x500000, float:7.34684E-39)
            if (r2 < r3) goto L_0x00f1
        L_0x00bd:
            java.io.File r1 = r7.c     // Catch:{ all -> 0x0017 }
            r1.delete()     // Catch:{ all -> 0x0017 }
            r1 = 0
            r7.c = r1     // Catch:{ all -> 0x0017 }
            monitor-exit(r7)     // Catch:{ all -> 0x0017 }
            goto L_0x0006
        L_0x00c8:
            r1 = move-exception
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ all -> 0x0017 }
            if (r1 == 0) goto L_0x0101
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ Exception -> 0x00d4 }
            r1.close()     // Catch:{ Exception -> 0x00d4 }
            r1 = r0
            goto L_0x00ab
        L_0x00d4:
            r1 = move-exception
            r1 = r0
            goto L_0x00ab
        L_0x00d7:
            r1 = move-exception
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ all -> 0x0017 }
            if (r1 == 0) goto L_0x0101
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ Exception -> 0x00e3 }
            r1.close()     // Catch:{ Exception -> 0x00e3 }
            r1 = r0
            goto L_0x00ab
        L_0x00e3:
            r1 = move-exception
            r1 = r0
            goto L_0x00ab
        L_0x00e6:
            r0 = move-exception
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ all -> 0x0017 }
            if (r1 == 0) goto L_0x00f0
            java.io.RandomAccessFile r1 = r7.a     // Catch:{ Exception -> 0x00f5 }
            r1.close()     // Catch:{ Exception -> 0x00f5 }
        L_0x00f0:
            throw r0     // Catch:{ all -> 0x0017 }
        L_0x00f1:
            monitor-exit(r7)     // Catch:{ all -> 0x001a }
            r0 = r1
            goto L_0x0006
        L_0x00f5:
            r1 = move-exception
            goto L_0x00f0
        L_0x00f7:
            r2 = move-exception
            goto L_0x00ab
        L_0x00f9:
            r1 = move-exception
            goto L_0x007a
        L_0x00fb:
            r1 = move-exception
            goto L_0x0055
        L_0x00fe:
            r1 = move-exception
            goto L_0x003e
        L_0x0101:
            r1 = r0
            goto L_0x00ab
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.de.a(int):com.loc.ch");
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:40:0x0072=Splitter:B:40:0x0072, B:22:0x0037=Splitter:B:22:0x0037, B:45:0x007b=Splitter:B:45:0x007b, B:71:0x00af=Splitter:B:71:0x00af} */
    protected final synchronized void a(com.loc.ch r5) {
        /*
            r4 = this;
            r0 = 0
            monitor-enter(r4)
            monitor-enter(r4)     // Catch:{ all -> 0x003c }
            java.io.File r1 = r5.a     // Catch:{ all -> 0x0039 }
            r4.c = r1     // Catch:{ all -> 0x0039 }
            java.io.File r1 = r4.c     // Catch:{ all -> 0x0039 }
            if (r1 != 0) goto L_0x000e
            monitor-exit(r4)     // Catch:{ all -> 0x0039 }
        L_0x000c:
            monitor-exit(r4)
            return
        L_0x000e:
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            java.io.File r2 = r4.c     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            java.lang.String r3 = "rw"
            r1.<init>(r2, r3)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            r4.a = r1     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            com.loc.ci r1 = r4.b     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            int r1 = r1.a()     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            byte[] r1 = new byte[r1]     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            boolean r2 = com.loc.cl.c     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            if (r2 == 0) goto L_0x0040
            java.io.RandomAccessFile r2 = r4.a     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            if (r2 == 0) goto L_0x0040
            java.io.RandomAccessFile r2 = r4.a     // Catch:{ IOException -> 0x003f, FileNotFoundException -> 0x008b, all -> 0x00a5 }
            r2.close()     // Catch:{ IOException -> 0x003f, FileNotFoundException -> 0x008b, all -> 0x00a5 }
            java.io.RandomAccessFile r0 = r4.a     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0037
            java.io.RandomAccessFile r0 = r4.a     // Catch:{ IOException -> 0x00b4 }
            r0.close()     // Catch:{ IOException -> 0x00b4 }
        L_0x0037:
            monitor-exit(r4)     // Catch:{ all -> 0x0039 }
            goto L_0x000c
        L_0x0039:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x003c }
            throw r0     // Catch:{ all -> 0x003c }
        L_0x003c:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x003f:
            r2 = move-exception
        L_0x0040:
            java.io.RandomAccessFile r2 = r4.a     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            r2.read(r1)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            java.util.BitSet r0 = com.loc.ci.b((byte[]) r1)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            boolean r1 = r5.b()     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            if (r1 == 0) goto L_0x0072
            int[] r1 = r5.b     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            r2 = 0
            r1 = r1[r2]     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
        L_0x0054:
            int[] r2 = r5.b     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            r3 = 1
            r2 = r2[r3]     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            if (r1 > r2) goto L_0x0062
            r2 = 0
            r0.set(r1, r2)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            int r1 = r1 + 1
            goto L_0x0054
        L_0x0062:
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            r2 = 0
            r1.seek(r2)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            byte[] r2 = com.loc.ci.a((java.util.BitSet) r0)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
            r1.write(r2)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0098, all -> 0x00a5 }
        L_0x0072:
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0039 }
            if (r1 == 0) goto L_0x007b
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x00b2 }
            r1.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x007b:
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0086
            java.io.File r0 = r4.c     // Catch:{ all -> 0x0039 }
            r0.delete()     // Catch:{ all -> 0x0039 }
        L_0x0086:
            r0 = 0
            r4.c = r0     // Catch:{ all -> 0x0039 }
            monitor-exit(r4)     // Catch:{ all -> 0x0039 }
            goto L_0x000c
        L_0x008b:
            r1 = move-exception
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0039 }
            if (r1 == 0) goto L_0x007b
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x0096 }
            r1.close()     // Catch:{ IOException -> 0x0096 }
            goto L_0x007b
        L_0x0096:
            r1 = move-exception
            goto L_0x007b
        L_0x0098:
            r1 = move-exception
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0039 }
            if (r1 == 0) goto L_0x007b
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x00a3 }
            r1.close()     // Catch:{ IOException -> 0x00a3 }
            goto L_0x007b
        L_0x00a3:
            r1 = move-exception
            goto L_0x007b
        L_0x00a5:
            r0 = move-exception
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ all -> 0x0039 }
            if (r1 == 0) goto L_0x00af
            java.io.RandomAccessFile r1 = r4.a     // Catch:{ IOException -> 0x00b0 }
            r1.close()     // Catch:{ IOException -> 0x00b0 }
        L_0x00af:
            throw r0     // Catch:{ all -> 0x0039 }
        L_0x00b0:
            r1 = move-exception
            goto L_0x00af
        L_0x00b2:
            r1 = move-exception
            goto L_0x007b
        L_0x00b4:
            r0 = move-exception
            goto L_0x0037
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.de.a(com.loc.ch):void");
    }
}
