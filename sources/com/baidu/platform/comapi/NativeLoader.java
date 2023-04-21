package com.baidu.platform.comapi;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class NativeLoader {
    private static Context a;
    private static final Set<String> b = new HashSet();
    private static final Set<String> c = new HashSet();
    private static NativeLoader d;
    private static a e = a.ARMEABI;

    private enum a {
        ARMEABI("armeabi"),
        ARMV7("armeabi-v7a"),
        ARM64("arm64-v8a"),
        X86("x86"),
        X86_64("x86_64");
        
        private String f;

        private a(String str) {
            this.f = str;
        }

        public String a() {
            return this.f;
        }
    }

    private NativeLoader() {
    }

    @TargetApi(21)
    private static a a() {
        String str = Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0];
        if (str == null) {
            return a.ARMEABI;
        }
        if (str.contains("arm") && str.contains("v7")) {
            e = a.ARMV7;
        }
        if (str.contains("arm") && str.contains("64")) {
            e = a.ARM64;
        }
        if (str.contains("x86")) {
            if (str.contains("64")) {
                e = a.X86_64;
            } else {
                e = a.X86;
            }
        }
        return e;
    }

    private String a(a aVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("lib/").append(aVar.a()).append("/");
        return sb.toString();
    }

    private void a(Throwable th) {
        Log.e(NativeLoader.class.getSimpleName(), "loadException", th);
        for (String str : c) {
            Log.e(NativeLoader.class.getSimpleName(), str + " Failed to load.");
        }
    }

    private boolean a(String str, String str2) {
        return !copyNativeLibrary(str2, a.ARMV7) ? b(str, str2) : f(str2, str);
    }

    private boolean b(String str, String str2) {
        if (copyNativeLibrary(str2, a.ARMEABI)) {
            return f(str2, str);
        }
        Log.e(NativeLoader.class.getSimpleName(), "found lib" + str + ".so error");
        return false;
    }

    private boolean c(String str, String str2) {
        return !copyNativeLibrary(str2, a.ARM64) ? a(str, str2) : f(str2, str);
    }

    private boolean d(String str, String str2) {
        return !copyNativeLibrary(str2, a.X86) ? a(str, str2) : f(str2, str);
    }

    private boolean e(String str, String str2) {
        return !copyNativeLibrary(str2, a.X86_64) ? d(str, str2) : f(str2, str);
    }

    private boolean f(String str, String str2) {
        try {
            System.load(new File(getCustomizeNativePath(), str).getAbsolutePath());
            synchronized (b) {
                b.add(str2);
            }
            return true;
        } catch (Throwable th) {
            synchronized (c) {
                c.add(str2);
                a(th);
                return false;
            }
        }
    }

    public static synchronized NativeLoader getInstance() {
        NativeLoader nativeLoader;
        synchronized (NativeLoader.class) {
            if (d == null) {
                d = new NativeLoader();
                e = a();
            }
            nativeLoader = d;
        }
        return nativeLoader;
    }

    public static void setContext(Context context) {
        a = context;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0062 A[SYNTHETIC, Splitter:B:28:0x0062] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean copyNativeLibrary(java.lang.String r6, com.baidu.platform.comapi.NativeLoader.a r7) {
        /*
            r5 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r5.a((com.baidu.platform.comapi.NativeLoader.a) r7)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r1 = r1.toString()
            r3 = 0
            java.util.zip.ZipFile r2 = new java.util.zip.ZipFile     // Catch:{ Exception -> 0x0049, all -> 0x005e }
            java.lang.String r4 = r5.getCodePath()     // Catch:{ Exception -> 0x0049, all -> 0x005e }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0049, all -> 0x005e }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x006e }
            java.lang.String r4 = r5.getCustomizeNativePath()     // Catch:{ Exception -> 0x006e }
            r3.<init>(r4, r6)     // Catch:{ Exception -> 0x006e }
            java.util.zip.ZipEntry r1 = r2.getEntry(r1)     // Catch:{ Exception -> 0x006e }
            if (r1 != 0) goto L_0x0035
            if (r2 == 0) goto L_0x0034
            r2.close()     // Catch:{ IOException -> 0x0066 }
        L_0x0034:
            return r0
        L_0x0035:
            java.io.InputStream r1 = r2.getInputStream(r1)     // Catch:{ Exception -> 0x006e }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x006e }
            r4.<init>(r3)     // Catch:{ Exception -> 0x006e }
            r5.copyStream(r1, r4)     // Catch:{ Exception -> 0x006e }
            r1 = 1
            if (r2 == 0) goto L_0x0047
            r2.close()     // Catch:{ IOException -> 0x0068 }
        L_0x0047:
            r0 = r1
            goto L_0x0034
        L_0x0049:
            r1 = move-exception
            r2 = r3
        L_0x004b:
            java.lang.Class<com.baidu.platform.comapi.NativeLoader> r3 = com.baidu.platform.comapi.NativeLoader.class
            java.lang.String r3 = r3.getSimpleName()     // Catch:{ all -> 0x006c }
            java.lang.String r4 = "copyError"
            android.util.Log.e(r3, r4, r1)     // Catch:{ all -> 0x006c }
            if (r2 == 0) goto L_0x0034
            r2.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0034
        L_0x005c:
            r1 = move-exception
            goto L_0x0034
        L_0x005e:
            r1 = move-exception
            r2 = r3
        L_0x0060:
            if (r2 == 0) goto L_0x0065
            r2.close()     // Catch:{ IOException -> 0x006a }
        L_0x0065:
            throw r1
        L_0x0066:
            r1 = move-exception
            goto L_0x0034
        L_0x0068:
            r1 = move-exception
            goto L_0x0034
        L_0x006a:
            r1 = move-exception
            goto L_0x0034
        L_0x006c:
            r1 = move-exception
            goto L_0x0060
        L_0x006e:
            r1 = move-exception
            goto L_0x004b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.NativeLoader.copyNativeLibrary(java.lang.String, com.baidu.platform.comapi.NativeLoader$a):boolean");
    }

    /* access modifiers changed from: protected */
    public final void copyStream(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        byte[] bArr = new byte[4096];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.flush();
                    try {
                        try {
                            return;
                        } catch (IOException e2) {
                            return;
                        }
                    } catch (IOException e3) {
                        return;
                    }
                }
            } finally {
                try {
                    inputStream.close();
                    try {
                        fileOutputStream.close();
                    } catch (IOException e4) {
                        return;
                    }
                } catch (IOException e5) {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @TargetApi(8)
    public String getCodePath() {
        return 8 <= Build.VERSION.SDK_INT ? a.getPackageCodePath() : "";
    }

    /* access modifiers changed from: protected */
    public String getCustomizeNativePath() {
        File file = new File(a.getFilesDir(), "libs");
        file.mkdirs();
        return file.getAbsolutePath();
    }

    /* access modifiers changed from: protected */
    public boolean loadCustomizeNativeLibrary(String str) {
        String mapLibraryName = System.mapLibraryName(str);
        switch (e) {
            case ARM64:
                return c(str, mapLibraryName);
            case ARMV7:
                return a(str, mapLibraryName);
            case ARMEABI:
                return b(str, mapLibraryName);
            case X86_64:
                return e(str, mapLibraryName);
            case X86:
                return d(str, mapLibraryName);
            default:
                return false;
        }
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
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
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
    public synchronized boolean loadLibrary(java.lang.String r4) {
        /*
            r3 = this;
            r0 = 1
            monitor-enter(r3)
            java.util.Set<java.lang.String> r1 = b     // Catch:{ Throwable -> 0x0021 }
            monitor-enter(r1)     // Catch:{ Throwable -> 0x0021 }
            java.util.Set<java.lang.String> r2 = b     // Catch:{ all -> 0x0027 }
            boolean r2 = r2.contains(r4)     // Catch:{ all -> 0x0027 }
            if (r2 == 0) goto L_0x0010
            monitor-exit(r1)     // Catch:{ all -> 0x0027 }
        L_0x000e:
            monitor-exit(r3)
            return r0
        L_0x0010:
            monitor-exit(r1)     // Catch:{ all -> 0x0027 }
            java.lang.System.loadLibrary(r4)     // Catch:{ Throwable -> 0x0021 }
            java.util.Set<java.lang.String> r1 = b     // Catch:{ Throwable -> 0x0021 }
            monitor-enter(r1)     // Catch:{ Throwable -> 0x0021 }
            java.util.Set<java.lang.String> r2 = b     // Catch:{ all -> 0x001e }
            r2.add(r4)     // Catch:{ all -> 0x001e }
            monitor-exit(r1)     // Catch:{ all -> 0x001e }
            goto L_0x000e
        L_0x001e:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001e }
            throw r0     // Catch:{ Throwable -> 0x0021 }
        L_0x0021:
            r0 = move-exception
            boolean r0 = r3.loadCustomizeNativeLibrary(r4)     // Catch:{ all -> 0x002a }
            goto L_0x000e
        L_0x0027:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0027 }
            throw r0     // Catch:{ Throwable -> 0x0021 }
        L_0x002a:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.NativeLoader.loadLibrary(java.lang.String):boolean");
    }
}
