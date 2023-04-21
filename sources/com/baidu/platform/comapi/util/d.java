package com.baidu.platform.comapi.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.storage.StorageManager;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class d {
    private static volatile d a = null;
    private boolean b = false;
    private boolean c = true;
    private final List<c> d = new ArrayList();
    private c e = null;
    private String f;

    private d() {
    }

    public static d a() {
        if (a == null) {
            synchronized (d.class) {
                if (a == null) {
                    a = new d();
                }
            }
        }
        return a;
    }

    private boolean a(String str) {
        Exception e2;
        boolean z;
        try {
            File file = new File(str + "/test.0");
            if (file.exists()) {
                file.delete();
            }
            z = file.createNewFile();
            try {
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e3) {
                e2 = e3;
                e2.printStackTrace();
                return z;
            }
        } catch (Exception e4) {
            Exception exc = e4;
            z = false;
            e2 = exc;
            e2.printStackTrace();
            return z;
        }
        return z;
    }

    @SuppressLint({"NewApi"})
    @TargetApi(14)
    private void c(Context context) {
        boolean z;
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService("storage");
            Method method = storageManager.getClass().getMethod("getVolumeList", new Class[0]);
            Method method2 = storageManager.getClass().getMethod("getVolumeState", new Class[]{String.class});
            Class<?> cls = Class.forName("android.os.storage.StorageVolume");
            Method method3 = cls.getMethod("isRemovable", new Class[0]);
            Method method4 = cls.getMethod("getPath", new Class[0]);
            Object[] objArr = (Object[]) method.invoke(storageManager, new Object[0]);
            if (objArr != null) {
                for (Object obj : objArr) {
                    String str = (String) method4.invoke(obj, new Object[0]);
                    if (str != null && str.length() > 0) {
                        if ("mounted".equals(method2.invoke(storageManager, new Object[]{str}))) {
                            boolean z2 = !((Boolean) method3.invoke(obj, new Object[0])).booleanValue();
                            if (Build.VERSION.SDK_INT <= 19 && a(str)) {
                                this.d.add(new c(str, !z2, z2 ? "内置存储卡" : "外置存储卡", context));
                            } else if (Build.VERSION.SDK_INT >= 19 && new File(str + File.separator + "BaiduMapSDKNew").exists() && str.equals(context.getSharedPreferences("map_pref", 0).getString("PREFFERED_SD_CARD", ""))) {
                                this.f = str + File.separator + "BaiduMapSDKNew";
                            }
                        }
                    }
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    File[] externalFilesDirs = context.getExternalFilesDirs((String) null);
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(this.d);
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= externalFilesDirs.length || externalFilesDirs[i2] == null) {
                            this.d.clear();
                            this.d.addAll(arrayList);
                        } else {
                            String absolutePath = externalFilesDirs[i2].getAbsolutePath();
                            Iterator<c> it = this.d.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (absolutePath.startsWith(it.next().a())) {
                                        z = true;
                                        break;
                                    }
                                } else {
                                    z = false;
                                    break;
                                }
                            }
                            String str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
                            if (!(str2 == null || z || absolutePath.indexOf(str2) == -1)) {
                                arrayList.add(new c(absolutePath, true, "外置存储卡", context));
                            }
                            i = i2 + 1;
                        }
                    }
                    this.d.clear();
                    this.d.addAll(arrayList);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0049, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0115, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0116, code lost:
        r2 = r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0049 A[ExcHandler: Exception (e java.lang.Exception), PHI: r1 
      PHI: (r1v8 java.util.Scanner) = (r1v7 java.util.Scanner), (r1v7 java.util.Scanner), (r1v7 java.util.Scanner), (r1v9 java.util.Scanner), (r1v9 java.util.Scanner), (r1v9 java.util.Scanner) binds: [B:26:0x0068, B:44:0x00af, B:45:?, B:5:0x001d, B:20:0x0053, B:21:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:5:0x001d] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d(android.content.Context r10) {
        /*
            r9 = this;
            r2 = 0
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            java.lang.String r1 = "/proc/mounts"
            r0.<init>(r1)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            boolean r1 = r0.exists()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            if (r1 == 0) goto L_0x0056
            java.util.Scanner r1 = new java.util.Scanner     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
        L_0x001d:
            boolean r0 = r1.hasNext()     // Catch:{ Exception -> 0x0049, all -> 0x0115 }
            if (r0 == 0) goto L_0x0053
            java.lang.String r0 = r1.nextLine()     // Catch:{ Exception -> 0x0049, all -> 0x0115 }
            java.lang.String r5 = "/dev/block/vold/"
            boolean r5 = r0.startsWith(r5)     // Catch:{ Exception -> 0x0049, all -> 0x0115 }
            if (r5 == 0) goto L_0x001d
            r5 = 9
            r6 = 32
            java.lang.String r0 = r0.replace(r5, r6)     // Catch:{ Exception -> 0x0049, all -> 0x0115 }
            java.lang.String r5 = " "
            java.lang.String[] r0 = r0.split(r5)     // Catch:{ Exception -> 0x0049, all -> 0x0115 }
            if (r0 == 0) goto L_0x001d
            int r5 = r0.length     // Catch:{ Exception -> 0x0049, all -> 0x0115 }
            if (r5 <= 0) goto L_0x001d
            r5 = 1
            r0 = r0[r5]     // Catch:{ Exception -> 0x0049, all -> 0x0115 }
            r3.add(r0)     // Catch:{ Exception -> 0x0049, all -> 0x0115 }
            goto L_0x001d
        L_0x0049:
            r0 = move-exception
        L_0x004a:
            r0.printStackTrace()     // Catch:{ all -> 0x0118 }
            if (r1 == 0) goto L_0x0052
            r1.close()
        L_0x0052:
            return
        L_0x0053:
            r1.close()     // Catch:{ Exception -> 0x0049, all -> 0x0115 }
        L_0x0056:
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            java.lang.String r1 = "/system/etc/vold.fstab"
            r0.<init>(r1)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            boolean r1 = r0.exists()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            if (r1 == 0) goto L_0x00b2
            java.util.Scanner r1 = new java.util.Scanner     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
        L_0x0068:
            boolean r0 = r1.hasNext()     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            if (r0 == 0) goto L_0x00af
            java.lang.String r0 = r1.nextLine()     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            java.lang.String r5 = "dev_mount"
            boolean r5 = r0.startsWith(r5)     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            if (r5 == 0) goto L_0x0068
            r5 = 9
            r6 = 32
            java.lang.String r0 = r0.replace(r5, r6)     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            java.lang.String r5 = " "
            java.lang.String[] r0 = r0.split(r5)     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            if (r0 == 0) goto L_0x0068
            int r5 = r0.length     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            if (r5 <= 0) goto L_0x0068
            r5 = 2
            r0 = r0[r5]     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            java.lang.String r5 = ":"
            boolean r5 = r0.contains(r5)     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            if (r5 == 0) goto L_0x00a3
            r5 = 0
            java.lang.String r6 = ":"
            int r6 = r0.indexOf(r6)     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            java.lang.String r0 = r0.substring(r5, r6)     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
        L_0x00a3:
            r4.add(r0)     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
            goto L_0x0068
        L_0x00a7:
            r0 = move-exception
            r2 = r1
        L_0x00a9:
            if (r2 == 0) goto L_0x00ae
            r2.close()
        L_0x00ae:
            throw r0
        L_0x00af:
            r1.close()     // Catch:{ Exception -> 0x0049, all -> 0x00a7 }
        L_0x00b2:
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            java.lang.String r1 = r0.getAbsolutePath()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            java.util.List<com.baidu.platform.comapi.util.c> r0 = r9.d     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            com.baidu.platform.comapi.util.c r5 = new com.baidu.platform.comapi.util.c     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            r6 = 0
            java.lang.String r7 = "Auto"
            r5.<init>(r1, r6, r7, r10)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            r0.add(r5)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
        L_0x00cb:
            boolean r0 = r3.hasNext()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            if (r0 == 0) goto L_0x010c
            java.lang.Object r0 = r3.next()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            boolean r5 = r4.contains(r0)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            if (r5 == 0) goto L_0x00cb
            boolean r5 = r0.equals(r1)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            if (r5 != 0) goto L_0x00cb
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            r5.<init>(r0)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            boolean r6 = r5.exists()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            if (r6 == 0) goto L_0x00cb
            boolean r6 = r5.isDirectory()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            if (r6 == 0) goto L_0x00cb
            boolean r5 = r5.canWrite()     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            if (r5 == 0) goto L_0x00cb
            java.util.List<com.baidu.platform.comapi.util.c> r5 = r9.d     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            com.baidu.platform.comapi.util.c r6 = new com.baidu.platform.comapi.util.c     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            r7 = 0
            java.lang.String r8 = "Auto"
            r6.<init>(r0, r7, r8, r10)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            r5.add(r6)     // Catch:{ Exception -> 0x0108, all -> 0x0113 }
            goto L_0x00cb
        L_0x0108:
            r0 = move-exception
            r1 = r2
            goto L_0x004a
        L_0x010c:
            if (r2 == 0) goto L_0x0052
            r2.close()
            goto L_0x0052
        L_0x0113:
            r0 = move-exception
            goto L_0x00a9
        L_0x0115:
            r0 = move-exception
            r2 = r1
            goto L_0x00a9
        L_0x0118:
            r0 = move-exception
            r2 = r1
            goto L_0x00a9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.util.d.d(android.content.Context):void");
    }

    public void a(Context context) {
        int i;
        c cVar;
        int i2 = 0;
        if (!this.b) {
            this.b = true;
            try {
                if (Build.VERSION.SDK_INT >= 14) {
                    c(context);
                } else {
                    d(context);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (this.d.size() > 0) {
                    c cVar2 = null;
                    for (c next : this.d) {
                        if (new File(next.b()).exists()) {
                            int i3 = i2 + 1;
                            cVar = next;
                            i = i3;
                        } else {
                            i = i2;
                            cVar = cVar2;
                        }
                        cVar2 = cVar;
                        i2 = i;
                    }
                    if (i2 == 0) {
                        this.e = b(context);
                        if (this.e == null) {
                            Iterator<c> it = this.d.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                c next2 = it.next();
                                if (a(context, next2)) {
                                    this.e = next2;
                                    break;
                                }
                            }
                        }
                    } else if (i2 != 1) {
                        this.e = b(context);
                    } else if (a(context, cVar2)) {
                        this.e = cVar2;
                    }
                    if (this.e == null) {
                        this.e = this.d.get(0);
                    }
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            try {
                if (this.e == null || !a(this.e.a())) {
                    this.c = false;
                    this.e = new c(context);
                    this.d.clear();
                    this.d.add(this.e);
                    return;
                }
                File file = new File(this.e.b());
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(this.e.c());
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                File file3 = new File(file2, ".nomedia");
                if (!file3.exists()) {
                    file3.createNewFile();
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    public boolean a(Context context, c cVar) {
        String a2 = cVar.a();
        if (!a(a2)) {
            return false;
        }
        SharedPreferences.Editor edit = context.getSharedPreferences("map_pref", 0).edit();
        edit.putString("PREFFERED_SD_CARD", a2);
        return edit.commit();
    }

    public c b() {
        return this.e;
    }

    public c b(Context context) {
        String string = context.getSharedPreferences("map_pref", 0).getString("PREFFERED_SD_CARD", "");
        if (string != null && string.length() > 0) {
            for (c next : this.d) {
                if (next.a().equals(string)) {
                    return next;
                }
            }
        }
        return null;
    }
}
