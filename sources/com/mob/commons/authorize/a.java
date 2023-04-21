package com.mob.commons.authorize;

import android.content.Context;
import com.mob.commons.LockAction;
import com.mob.commons.MobProduct;
import com.mob.commons.d;
import com.mob.tools.utils.FileLocker;
import com.mob.tools.utils.ResHelper;
import java.io.File;

/* compiled from: Authorizer */
public final class a {
    public final String a(final Context context, final MobProduct mobProduct) {
        final String[] strArr = new String[1];
        d.a(new File(ResHelper.getCacheRoot(context), "comm/locks/.globalLock"), new LockAction() {
            public boolean run(FileLocker fileLocker) {
                strArr[0] = a.this.b(context, mobProduct);
                return false;
            }
        });
        return strArr[0];
    }

    /* access modifiers changed from: private */
    public final File b(Context context) {
        File file = new File(ResHelper.getCacheRoot(context), "comm/dbs/.duid");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b9 A[SYNTHETIC, Splitter:B:51:0x00b9] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00c7 A[SYNTHETIC, Splitter:B:59:0x00c7] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00fd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String b(android.content.Context r9, com.mob.commons.MobProduct r10) {
        /*
            r8 = this;
            r2 = 0
            java.io.File r0 = r8.b(r9)     // Catch:{ Throwable -> 0x00cb }
            boolean r1 = r0.exists()     // Catch:{ Throwable -> 0x00cb }
            if (r1 == 0) goto L_0x00d3
            boolean r1 = r0.isFile()     // Catch:{ Throwable -> 0x00cb }
            if (r1 == 0) goto L_0x00d3
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00ae, all -> 0x00c3 }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x00ae, all -> 0x00c3 }
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch:{ Throwable -> 0x00ae, all -> 0x00c3 }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x00ae, all -> 0x00c3 }
            java.lang.Object r0 = r1.readObject()     // Catch:{ Throwable -> 0x00f3 }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x00f3 }
            if (r1 == 0) goto L_0x0026
            r1.close()     // Catch:{ Throwable -> 0x00ea }
        L_0x0026:
            if (r0 != 0) goto L_0x00fd
            java.util.HashMap r0 = r8.c(r9, r10)
            r4 = r0
        L_0x002d:
            if (r4 == 0) goto L_0x00fb
            java.lang.String r0 = "duid"
            java.lang.Object r0 = r4.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x00ad
            java.lang.String r1 = "deviceInfo"
            java.lang.Object r1 = r4.get(r1)     // Catch:{ Throwable -> 0x00ef }
            java.util.HashMap r1 = (java.util.HashMap) r1     // Catch:{ Throwable -> 0x00ef }
            boolean r1 = r8.a((android.content.Context) r9, (java.util.HashMap<java.lang.String, java.lang.String>) r1)     // Catch:{ Throwable -> 0x00ef }
            if (r1 == 0) goto L_0x00f8
            r1 = 1
            java.lang.String r3 = r8.a(r9, r10, r4, r1)     // Catch:{ Throwable -> 0x00ef }
            if (r3 == 0) goto L_0x00f8
        L_0x004e:
            java.lang.String r0 = "appInfo"
            java.lang.Object r0 = r4.get(r0)     // Catch:{ Throwable -> 0x00df }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x00df }
            if (r0 != 0) goto L_0x00f5
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x00df }
            r0.<init>()     // Catch:{ Throwable -> 0x00df }
            java.lang.String r1 = "appInfo"
            r4.put(r1, r0)     // Catch:{ Throwable -> 0x00df }
            r1 = r0
        L_0x0063:
            java.lang.String r0 = "DeviceHelper"
            java.lang.String r5 = "getInstance"
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x00d6 }
            r7 = 0
            r6[r7] = r9     // Catch:{ Throwable -> 0x00d6 }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeStaticMethod(r0, r5, r6)     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r5 = "getPackageName"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x00d6 }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r0, r5, r6)     // Catch:{ Throwable -> 0x00d6 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00d6 }
            r2 = r0
        L_0x007d:
            java.lang.Object r0 = r1.get(r2)     // Catch:{ Throwable -> 0x00df }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x00df }
            if (r0 != 0) goto L_0x008d
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x00df }
            r0.<init>()     // Catch:{ Throwable -> 0x00df }
            r1.put(r2, r0)     // Catch:{ Throwable -> 0x00df }
        L_0x008d:
            java.lang.String r1 = r10.getProductTag()     // Catch:{ Throwable -> 0x00df }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Throwable -> 0x00df }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00df }
            java.lang.String r1 = com.mob.MobSDK.getAppkey()     // Catch:{ Throwable -> 0x00df }
            if (r1 != 0) goto L_0x00a1
            java.lang.String r1 = r10.getProductAppkey()     // Catch:{ Throwable -> 0x00df }
        L_0x00a1:
            if (r0 == 0) goto L_0x00a9
            boolean r0 = r0.equals(r1)     // Catch:{ Throwable -> 0x00df }
            if (r0 != 0) goto L_0x00ac
        L_0x00a9:
            r8.a((android.content.Context) r9, (com.mob.commons.MobProduct) r10, (java.util.HashMap<java.lang.String, java.lang.Object>) r4)     // Catch:{ Throwable -> 0x00df }
        L_0x00ac:
            r0 = r3
        L_0x00ad:
            return r0
        L_0x00ae:
            r0 = move-exception
            r1 = r2
        L_0x00b0:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x00f1 }
            r3.w(r0)     // Catch:{ all -> 0x00f1 }
            if (r1 == 0) goto L_0x00d3
            r1.close()     // Catch:{ Throwable -> 0x00bf }
            r0 = r2
            goto L_0x0026
        L_0x00bf:
            r0 = move-exception
            r0 = r2
            goto L_0x0026
        L_0x00c3:
            r0 = move-exception
            r1 = r2
        L_0x00c5:
            if (r1 == 0) goto L_0x00ca
            r1.close()     // Catch:{ Throwable -> 0x00ed }
        L_0x00ca:
            throw r0     // Catch:{ Throwable -> 0x00cb }
        L_0x00cb:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
        L_0x00d3:
            r0 = r2
            goto L_0x0026
        L_0x00d6:
            r0 = move-exception
            com.mob.tools.log.NLog r5 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x00df }
            r5.w(r0)     // Catch:{ Throwable -> 0x00df }
            goto L_0x007d
        L_0x00df:
            r0 = move-exception
            r1 = r0
            r0 = r3
        L_0x00e2:
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()
            r2.w(r1)
            goto L_0x00ad
        L_0x00ea:
            r1 = move-exception
            goto L_0x0026
        L_0x00ed:
            r1 = move-exception
            goto L_0x00ca
        L_0x00ef:
            r1 = move-exception
            goto L_0x00e2
        L_0x00f1:
            r0 = move-exception
            goto L_0x00c5
        L_0x00f3:
            r0 = move-exception
            goto L_0x00b0
        L_0x00f5:
            r1 = r0
            goto L_0x0063
        L_0x00f8:
            r3 = r0
            goto L_0x004e
        L_0x00fb:
            r0 = r2
            goto L_0x00ad
        L_0x00fd:
            r4 = r0
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.b(android.content.Context, com.mob.commons.MobProduct):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x013c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean a(android.content.Context r8, java.util.HashMap<java.lang.String, java.lang.String> r9) {
        /*
            r7 = this;
            r2 = 0
            r3 = 1
            r4 = 0
            if (r9 != 0) goto L_0x0007
            r0 = r3
        L_0x0006:
            return r0
        L_0x0007:
            java.lang.String r0 = "DeviceHelper"
            java.lang.String r1 = "getInstance"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0039 }
            r6 = 0
            r5[r6] = r8     // Catch:{ Throwable -> 0x0039 }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeStaticMethod(r0, r1, r5)     // Catch:{ Throwable -> 0x0039 }
            r1 = r0
        L_0x0016:
            java.lang.String r0 = "adsid"
            java.lang.Object r5 = r9.get(r0)
            if (r1 == 0) goto L_0x004b
            java.lang.String r0 = "getAdvertisingID"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x0043 }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r6)     // Catch:{ Throwable -> 0x0043 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0043 }
        L_0x0029:
            if (r0 == 0) goto L_0x004d
            if (r5 != 0) goto L_0x002f
            if (r0 != 0) goto L_0x0037
        L_0x002f:
            if (r5 == 0) goto L_0x004d
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L_0x004d
        L_0x0037:
            r0 = r3
            goto L_0x0006
        L_0x0039:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            r1 = r2
            goto L_0x0016
        L_0x0043:
            r0 = move-exception
            com.mob.tools.log.NLog r6 = com.mob.tools.MobLog.getInstance()
            r6.w(r0)
        L_0x004b:
            r0 = r2
            goto L_0x0029
        L_0x004d:
            java.lang.String r0 = "imei"
            java.lang.Object r5 = r9.get(r0)
            if (r1 == 0) goto L_0x0072
            java.lang.String r0 = "getIMEI"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x006a }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r6)     // Catch:{ Throwable -> 0x006a }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x006a }
        L_0x0060:
            if (r5 == 0) goto L_0x0068
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L_0x0074
        L_0x0068:
            r0 = r3
            goto L_0x0006
        L_0x006a:
            r0 = move-exception
            com.mob.tools.log.NLog r6 = com.mob.tools.MobLog.getInstance()
            r6.w(r0)
        L_0x0072:
            r0 = r2
            goto L_0x0060
        L_0x0074:
            java.lang.String r0 = "serialno"
            java.lang.Object r5 = r9.get(r0)
            if (r1 == 0) goto L_0x009a
            java.lang.String r0 = "getSerialno"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x0092 }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r6)     // Catch:{ Throwable -> 0x0092 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0092 }
        L_0x0087:
            if (r5 == 0) goto L_0x008f
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L_0x009c
        L_0x008f:
            r0 = r3
            goto L_0x0006
        L_0x0092:
            r0 = move-exception
            com.mob.tools.log.NLog r6 = com.mob.tools.MobLog.getInstance()
            r6.w(r0)
        L_0x009a:
            r0 = r2
            goto L_0x0087
        L_0x009c:
            java.lang.String r0 = "mac"
            java.lang.Object r5 = r9.get(r0)
            if (r1 == 0) goto L_0x00c2
            java.lang.String r0 = "getMacAddress"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x00ba }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r6)     // Catch:{ Throwable -> 0x00ba }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00ba }
        L_0x00af:
            if (r5 == 0) goto L_0x00b7
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L_0x00c4
        L_0x00b7:
            r0 = r3
            goto L_0x0006
        L_0x00ba:
            r0 = move-exception
            com.mob.tools.log.NLog r6 = com.mob.tools.MobLog.getInstance()
            r6.w(r0)
        L_0x00c2:
            r0 = r2
            goto L_0x00af
        L_0x00c4:
            java.lang.String r0 = "model"
            java.lang.Object r5 = r9.get(r0)
            if (r1 == 0) goto L_0x00ea
            java.lang.String r0 = "getModel"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x00e2 }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r6)     // Catch:{ Throwable -> 0x00e2 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00e2 }
        L_0x00d7:
            if (r5 == 0) goto L_0x00df
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L_0x00ec
        L_0x00df:
            r0 = r3
            goto L_0x0006
        L_0x00e2:
            r0 = move-exception
            com.mob.tools.log.NLog r6 = com.mob.tools.MobLog.getInstance()
            r6.w(r0)
        L_0x00ea:
            r0 = r2
            goto L_0x00d7
        L_0x00ec:
            java.lang.String r0 = "factory"
            java.lang.Object r5 = r9.get(r0)
            if (r1 == 0) goto L_0x0112
            java.lang.String r0 = "getManufacturer"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x010a }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r6)     // Catch:{ Throwable -> 0x010a }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x010a }
        L_0x00ff:
            if (r5 == 0) goto L_0x0107
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L_0x0114
        L_0x0107:
            r0 = r3
            goto L_0x0006
        L_0x010a:
            r0 = move-exception
            com.mob.tools.log.NLog r6 = com.mob.tools.MobLog.getInstance()
            r6.w(r0)
        L_0x0112:
            r0 = r2
            goto L_0x00ff
        L_0x0114:
            java.lang.String r0 = "androidid"
            java.lang.Object r5 = r9.get(r0)
            if (r1 == 0) goto L_0x013a
            java.lang.String r0 = "getAndroidID"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x0132 }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r6)     // Catch:{ Throwable -> 0x0132 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0132 }
        L_0x0127:
            if (r5 == 0) goto L_0x012f
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L_0x013c
        L_0x012f:
            r0 = r3
            goto L_0x0006
        L_0x0132:
            r0 = move-exception
            com.mob.tools.log.NLog r6 = com.mob.tools.MobLog.getInstance()
            r6.w(r0)
        L_0x013a:
            r0 = r2
            goto L_0x0127
        L_0x013c:
            java.lang.String r0 = "sysver"
            java.lang.Object r5 = r9.get(r0)
            if (r1 == 0) goto L_0x0150
            java.lang.String r0 = "getOSVersionName"
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x015b }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r1, r0, r6)     // Catch:{ Throwable -> 0x015b }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x015b }
            r2 = r0
        L_0x0150:
            if (r5 == 0) goto L_0x0158
            boolean r0 = r5.equals(r2)
            if (r0 != 0) goto L_0x0164
        L_0x0158:
            r0 = r3
            goto L_0x0006
        L_0x015b:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            goto L_0x0150
        L_0x0164:
            r0 = r4
            goto L_0x0006
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.a(android.content.Context, java.util.HashMap):boolean");
    }

    private final String a() {
        return "sd" + "k." + "co" + "mm" + "on" + "ap" + ".s" + "dk";
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0151 A[SYNTHETIC, Splitter:B:33:0x0151] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x015e A[SYNTHETIC, Splitter:B:39:0x015e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.HashMap<java.lang.String, java.lang.Object> c(android.content.Context r11, com.mob.commons.MobProduct r12) {
        /*
            r10 = this;
            r6 = 0
            java.lang.String r0 = "DeviceHelper"
            java.lang.String r1 = "getInstance"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0165 }
            r3 = 0
            r2[r3] = r11     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r2 = com.mob.tools.utils.ReflectHelper.invokeStaticMethod(r0, r1, r2)     // Catch:{ Throwable -> 0x0165 }
            r1 = -1
            java.lang.String r0 = "getCarrier"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00fd }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r0, r3)     // Catch:{ Throwable -> 0x00fd }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00fd }
            int r0 = com.mob.tools.utils.ResHelper.parseInt(r0)     // Catch:{ Throwable -> 0x00fd }
            r7 = r0
        L_0x0020:
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ Throwable -> 0x0165 }
            r8.<init>()     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "adsid"
            java.lang.String r1 = "getAdvertisingID"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "imei"
            java.lang.String r1 = "getIMEI"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "serialno"
            java.lang.String r1 = "getSerialno"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "androidid"
            java.lang.String r1 = "getAndroidID"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "mac"
            java.lang.String r1 = "getMacAddress"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "model"
            java.lang.String r1 = "getModel"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "factory"
            java.lang.String r1 = "getManufacturer"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "carrier"
            java.lang.Integer r1 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "screensize"
            java.lang.String r1 = "getScreenSize"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "sysver"
            java.lang.String r1 = "getOSVersionName"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0165 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = "plat"
            r1 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x0165 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            com.mob.tools.utils.Hashon r9 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x0165 }
            r9.<init>()     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = r9.fromHashMap(r8)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r1 = r10.a()     // Catch:{ Throwable -> 0x0165 }
            byte[] r0 = com.mob.tools.utils.Data.AES128Encode((java.lang.String) r1, (java.lang.String) r0)     // Catch:{ Throwable -> 0x0165 }
            r1 = 2
            java.lang.String r0 = android.util.Base64.encodeToString(r0, r1)     // Catch:{ Throwable -> 0x0165 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0165 }
            r2.<init>()     // Catch:{ Throwable -> 0x0165 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r3 = "m"
            r1.<init>(r3, r0)     // Catch:{ Throwable -> 0x0165 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0165 }
            com.mob.tools.network.NetworkHelper$NetworkTimeOut r5 = new com.mob.tools.network.NetworkHelper$NetworkTimeOut     // Catch:{ Throwable -> 0x0165 }
            r5.<init>()     // Catch:{ Throwable -> 0x0165 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.readTimout = r0     // Catch:{ Throwable -> 0x0165 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.connectionTimeout = r0     // Catch:{ Throwable -> 0x0165 }
            com.mob.tools.network.NetworkHelper r0 = new com.mob.tools.network.NetworkHelper     // Catch:{ Throwable -> 0x0165 }
            r0.<init>()     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r1 = "http://devs.data.mob.com:80/dinfo"
            r3 = 0
            r4 = 0
            java.lang.String r0 = r0.httpPost(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0165 }
            java.util.HashMap r0 = r9.fromJson((java.lang.String) r0)     // Catch:{ Throwable -> 0x0165 }
            if (r0 != 0) goto L_0x0101
            r0 = r6
        L_0x00fc:
            return r0
        L_0x00fd:
            r0 = move-exception
            r7 = r1
            goto L_0x0020
        L_0x0101:
            java.lang.String r1 = "duid"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0165 }
            if (r0 == 0) goto L_0x0111
            int r1 = r0.length()     // Catch:{ Throwable -> 0x0165 }
            if (r1 > 0) goto L_0x0113
        L_0x0111:
            r0 = r6
            goto L_0x00fc
        L_0x0113:
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ Throwable -> 0x0165 }
            r1.<init>()     // Catch:{ Throwable -> 0x0165 }
            java.lang.String r2 = "duid"
            r1.put(r2, r0)     // Catch:{ Throwable -> 0x0146, all -> 0x015a }
            java.lang.String r0 = "carrier"
            java.lang.String r2 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x0146, all -> 0x015a }
            r8.put(r0, r2)     // Catch:{ Throwable -> 0x0146, all -> 0x015a }
            java.lang.String r0 = "deviceInfo"
            r1.put(r0, r8)     // Catch:{ Throwable -> 0x0146, all -> 0x015a }
            java.io.File r0 = r10.b(r11)     // Catch:{ Throwable -> 0x0146, all -> 0x015a }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0146, all -> 0x015a }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x0146, all -> 0x015a }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x0146, all -> 0x015a }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0146, all -> 0x015a }
            r2.writeObject(r1)     // Catch:{ Throwable -> 0x0173 }
            if (r2 == 0) goto L_0x0144
            r2.flush()     // Catch:{ Throwable -> 0x0175 }
            r2.close()     // Catch:{ Throwable -> 0x0175 }
        L_0x0144:
            r0 = r1
            goto L_0x00fc
        L_0x0146:
            r0 = move-exception
            r2 = r6
        L_0x0148:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x0171 }
            r3.w(r0)     // Catch:{ all -> 0x0171 }
            if (r2 == 0) goto L_0x0144
            r2.flush()     // Catch:{ Throwable -> 0x0158 }
            r2.close()     // Catch:{ Throwable -> 0x0158 }
            goto L_0x0144
        L_0x0158:
            r0 = move-exception
            goto L_0x0144
        L_0x015a:
            r0 = move-exception
            r2 = r6
        L_0x015c:
            if (r2 == 0) goto L_0x0164
            r2.flush()     // Catch:{ Throwable -> 0x016f }
            r2.close()     // Catch:{ Throwable -> 0x016f }
        L_0x0164:
            throw r0     // Catch:{ Throwable -> 0x0165 }
        L_0x0165:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            r0 = r6
            goto L_0x00fc
        L_0x016f:
            r1 = move-exception
            goto L_0x0164
        L_0x0171:
            r0 = move-exception
            goto L_0x015c
        L_0x0173:
            r0 = move-exception
            goto L_0x0148
        L_0x0175:
            r0 = move-exception
            goto L_0x0144
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.c(android.content.Context, com.mob.commons.MobProduct):java.util.HashMap");
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x014f A[SYNTHETIC, Splitter:B:33:0x014f] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x015c A[SYNTHETIC, Splitter:B:39:0x015c] */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[Catch:{  }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String a(android.content.Context r11, com.mob.commons.MobProduct r12, java.util.HashMap<java.lang.String, java.lang.Object> r13, boolean r14) {
        /*
            r10 = this;
            r6 = 0
            java.lang.String r0 = "DeviceHelper"
            java.lang.String r1 = "getInstance"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0163 }
            r3 = 0
            r2[r3] = r11     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r2 = com.mob.tools.utils.ReflectHelper.invokeStaticMethod(r0, r1, r2)     // Catch:{ Throwable -> 0x0163 }
            r1 = -1
            java.lang.String r0 = "getCarrier"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x00fd }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r0, r3)     // Catch:{ Throwable -> 0x00fd }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x00fd }
            int r0 = com.mob.tools.utils.ResHelper.parseInt(r0)     // Catch:{ Throwable -> 0x00fd }
            r7 = r0
        L_0x0020:
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ Throwable -> 0x0163 }
            r8.<init>()     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "adsid"
            java.lang.String r1 = "getAdvertisingID"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "imei"
            java.lang.String r1 = "getIMEI"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "serialno"
            java.lang.String r1 = "getSerialno"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "androidid"
            java.lang.String r1 = "getAndroidID"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "mac"
            java.lang.String r1 = "getMacAddress"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "model"
            java.lang.String r1 = "getModel"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "factory"
            java.lang.String r1 = "getManufacturer"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "carrier"
            java.lang.Integer r1 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "screensize"
            java.lang.String r1 = "getScreenSize"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "sysver"
            java.lang.String r1 = "getOSVersionName"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0163 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r2, r1, r3)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = "plat"
            r1 = 1
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            com.mob.tools.utils.Hashon r9 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x0163 }
            r9.<init>()     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = r9.fromHashMap(r8)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r1 = r10.a()     // Catch:{ Throwable -> 0x0163 }
            byte[] r0 = com.mob.tools.utils.Data.AES128Encode((java.lang.String) r1, (java.lang.String) r0)     // Catch:{ Throwable -> 0x0163 }
            r1 = 2
            java.lang.String r0 = android.util.Base64.encodeToString(r0, r1)     // Catch:{ Throwable -> 0x0163 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0163 }
            r2.<init>()     // Catch:{ Throwable -> 0x0163 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r3 = "m"
            r1.<init>(r3, r0)     // Catch:{ Throwable -> 0x0163 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0163 }
            com.mob.tools.network.NetworkHelper$NetworkTimeOut r5 = new com.mob.tools.network.NetworkHelper$NetworkTimeOut     // Catch:{ Throwable -> 0x0163 }
            r5.<init>()     // Catch:{ Throwable -> 0x0163 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.readTimout = r0     // Catch:{ Throwable -> 0x0163 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.connectionTimeout = r0     // Catch:{ Throwable -> 0x0163 }
            com.mob.tools.network.NetworkHelper r0 = new com.mob.tools.network.NetworkHelper     // Catch:{ Throwable -> 0x0163 }
            r0.<init>()     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r1 = "http://devs.data.mob.com:80/dinfo"
            r3 = 0
            r4 = 0
            java.lang.String r0 = r0.httpPost(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0163 }
            java.util.HashMap r0 = r9.fromJson((java.lang.String) r0)     // Catch:{ Throwable -> 0x0163 }
            if (r0 != 0) goto L_0x0101
            r0 = r6
        L_0x00fc:
            return r0
        L_0x00fd:
            r0 = move-exception
            r7 = r1
            goto L_0x0020
        L_0x0101:
            java.lang.String r1 = "duid"
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0163 }
            if (r0 == 0) goto L_0x0111
            int r1 = r0.length()     // Catch:{ Throwable -> 0x0163 }
            if (r1 > 0) goto L_0x0113
        L_0x0111:
            r0 = r6
            goto L_0x00fc
        L_0x0113:
            java.lang.String r1 = "duid"
            r13.put(r1, r0)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r1 = "carrier"
            java.lang.String r2 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x0163 }
            r8.put(r1, r2)     // Catch:{ Throwable -> 0x0163 }
            java.lang.String r1 = "deviceInfo"
            r13.put(r1, r8)     // Catch:{ Throwable -> 0x0163 }
            if (r14 == 0) goto L_0x00fc
            java.io.File r1 = r10.b(r11)     // Catch:{ Throwable -> 0x0144, all -> 0x0158 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0144, all -> 0x0158 }
            r3.<init>(r1)     // Catch:{ Throwable -> 0x0144, all -> 0x0158 }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x0144, all -> 0x0158 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0144, all -> 0x0158 }
            r2.writeObject(r13)     // Catch:{ Throwable -> 0x0171 }
            if (r2 == 0) goto L_0x00fc
            r2.flush()     // Catch:{ Throwable -> 0x0142 }
            r2.close()     // Catch:{ Throwable -> 0x0142 }
            goto L_0x00fc
        L_0x0142:
            r1 = move-exception
            goto L_0x00fc
        L_0x0144:
            r1 = move-exception
            r2 = r6
        L_0x0146:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x016f }
            r3.w(r1)     // Catch:{ all -> 0x016f }
            if (r2 == 0) goto L_0x00fc
            r2.flush()     // Catch:{ Throwable -> 0x0156 }
            r2.close()     // Catch:{ Throwable -> 0x0156 }
            goto L_0x00fc
        L_0x0156:
            r1 = move-exception
            goto L_0x00fc
        L_0x0158:
            r0 = move-exception
            r2 = r6
        L_0x015a:
            if (r2 == 0) goto L_0x0162
            r2.flush()     // Catch:{ Throwable -> 0x016d }
            r2.close()     // Catch:{ Throwable -> 0x016d }
        L_0x0162:
            throw r0     // Catch:{ Throwable -> 0x0163 }
        L_0x0163:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            r0 = r6
            goto L_0x00fc
        L_0x016d:
            r1 = move-exception
            goto L_0x0162
        L_0x016f:
            r0 = move-exception
            goto L_0x015a
        L_0x0171:
            r1 = move-exception
            goto L_0x0146
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.a(android.content.Context, com.mob.commons.MobProduct, java.util.HashMap, boolean):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0136 A[SYNTHETIC, Splitter:B:24:0x0136] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0142 A[SYNTHETIC, Splitter:B:29:0x0142] */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void a(android.content.Context r9, com.mob.commons.MobProduct r10, java.util.HashMap<java.lang.String, java.lang.Object> r11) {
        /*
            r8 = this;
            r6 = 0
            java.lang.String r0 = "duid"
            java.lang.Object r0 = r11.get(r0)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = "DeviceHelper"
            java.lang.String r2 = "getInstance"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0149 }
            r4 = 0
            r3[r4] = r9     // Catch:{ Throwable -> 0x0149 }
            java.lang.Object r7 = com.mob.tools.utils.ReflectHelper.invokeStaticMethod(r1, r2, r3)     // Catch:{ Throwable -> 0x0149 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0149 }
            r2.<init>()     // Catch:{ Throwable -> 0x0149 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r3 = "product"
            java.lang.String r4 = r10.getProductTag()     // Catch:{ Throwable -> 0x0149 }
            r1.<init>(r3, r4)     // Catch:{ Throwable -> 0x0149 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = com.mob.MobSDK.getAppkey()     // Catch:{ Throwable -> 0x0149 }
            if (r1 != 0) goto L_0x0034
            java.lang.String r1 = r10.getProductAppkey()     // Catch:{ Throwable -> 0x0149 }
        L_0x0034:
            com.mob.tools.network.KVPair r3 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r4 = "appkey"
            r3.<init>(r4, r1)     // Catch:{ Throwable -> 0x0149 }
            r2.add(r3)     // Catch:{ Throwable -> 0x0149 }
            com.mob.tools.network.KVPair r1 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r3 = "duid"
            r1.<init>(r3, r0)     // Catch:{ Throwable -> 0x0149 }
            r2.add(r1)     // Catch:{ Throwable -> 0x0149 }
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = "apppkg"
            java.lang.String r3 = "getPackageName"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0149 }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r7, r3, r4)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0149 }
            r0.<init>(r1, r3)     // Catch:{ Throwable -> 0x0149 }
            r2.add(r0)     // Catch:{ Throwable -> 0x0149 }
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = "appver"
            java.lang.String r3 = "getAppVersion"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0149 }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r7, r3, r4)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0149 }
            r0.<init>(r1, r3)     // Catch:{ Throwable -> 0x0149 }
            r2.add(r0)     // Catch:{ Throwable -> 0x0149 }
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = "sdkver"
            int r3 = r10.getSdkver()     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0149 }
            r0.<init>(r1, r3)     // Catch:{ Throwable -> 0x0149 }
            r2.add(r0)     // Catch:{ Throwable -> 0x0149 }
            com.mob.tools.network.KVPair r0 = new com.mob.tools.network.KVPair     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = "network"
            java.lang.String r3 = "getDetailNetworkTypeForStatic"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0149 }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r7, r3, r4)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0149 }
            r0.<init>(r1, r3)     // Catch:{ Throwable -> 0x0149 }
            r2.add(r0)     // Catch:{ Throwable -> 0x0149 }
            com.mob.tools.network.NetworkHelper$NetworkTimeOut r5 = new com.mob.tools.network.NetworkHelper$NetworkTimeOut     // Catch:{ Throwable -> 0x0149 }
            r5.<init>()     // Catch:{ Throwable -> 0x0149 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.readTimout = r0     // Catch:{ Throwable -> 0x0149 }
            r0 = 30000(0x7530, float:4.2039E-41)
            r5.connectionTimeout = r0     // Catch:{ Throwable -> 0x0149 }
            com.mob.tools.network.NetworkHelper r0 = new com.mob.tools.network.NetworkHelper     // Catch:{ Throwable -> 0x0149 }
            r0.<init>()     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = "http://devs.data.mob.com:80/dsign"
            r3 = 0
            r4 = 0
            java.lang.String r0 = r0.httpPost(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0149 }
            com.mob.tools.utils.Hashon r1 = new com.mob.tools.utils.Hashon     // Catch:{ Throwable -> 0x0149 }
            r1.<init>()     // Catch:{ Throwable -> 0x0149 }
            java.util.HashMap r0 = r1.fromJson((java.lang.String) r0)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = "true"
            java.lang.String r2 = "reup"
            java.lang.Object r2 = r0.get(r2)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0149 }
            boolean r1 = r1.equals(r2)     // Catch:{ Throwable -> 0x0149 }
            if (r1 == 0) goto L_0x00db
            r1 = 0
            java.lang.String r1 = r8.a(r9, r10, r11, r1)     // Catch:{ Throwable -> 0x0149 }
            if (r1 == 0) goto L_0x00db
        L_0x00db:
            java.lang.String r1 = "200"
            java.lang.String r2 = "status"
            java.lang.Object r0 = r0.get(r2)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x0149 }
            boolean r0 = r1.equals(r0)     // Catch:{ Throwable -> 0x0149 }
            if (r0 == 0) goto L_0x012a
            java.lang.String r0 = "appInfo"
            java.lang.Object r0 = r11.get(r0)     // Catch:{ Throwable -> 0x0149 }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = "getPackageName"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0149 }
            java.lang.Object r1 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r7, r1, r2)     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x0149 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Throwable -> 0x0149 }
            java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r1 = r10.getProductTag()     // Catch:{ Throwable -> 0x0149 }
            java.lang.String r2 = r10.getProductAppkey()     // Catch:{ Throwable -> 0x0149 }
            r0.put(r1, r2)     // Catch:{ Throwable -> 0x0149 }
            java.io.File r0 = r8.b(r9)     // Catch:{ Throwable -> 0x012b, all -> 0x013f }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x012b, all -> 0x013f }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x012b, all -> 0x013f }
            java.io.ObjectOutputStream r1 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x012b, all -> 0x013f }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x012b, all -> 0x013f }
            r1.writeObject(r11)     // Catch:{ Throwable -> 0x0157 }
            if (r1 == 0) goto L_0x012a
            r1.flush()     // Catch:{ Throwable -> 0x0159 }
            r1.close()     // Catch:{ Throwable -> 0x0159 }
        L_0x012a:
            return
        L_0x012b:
            r0 = move-exception
            r1 = r6
        L_0x012d:
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x0154 }
            r2.w(r0)     // Catch:{ all -> 0x0154 }
            if (r1 == 0) goto L_0x012a
            r1.flush()     // Catch:{ Throwable -> 0x013d }
            r1.close()     // Catch:{ Throwable -> 0x013d }
            goto L_0x012a
        L_0x013d:
            r0 = move-exception
            goto L_0x012a
        L_0x013f:
            r0 = move-exception
        L_0x0140:
            if (r6 == 0) goto L_0x0148
            r6.flush()     // Catch:{ Throwable -> 0x0152 }
            r6.close()     // Catch:{ Throwable -> 0x0152 }
        L_0x0148:
            throw r0     // Catch:{ Throwable -> 0x0149 }
        L_0x0149:
            r0 = move-exception
            com.mob.tools.log.NLog r1 = com.mob.tools.MobLog.getInstance()
            r1.w(r0)
            goto L_0x012a
        L_0x0152:
            r1 = move-exception
            goto L_0x0148
        L_0x0154:
            r0 = move-exception
            r6 = r1
            goto L_0x0140
        L_0x0157:
            r0 = move-exception
            goto L_0x012d
        L_0x0159:
            r0 = move-exception
            goto L_0x012a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.a(android.content.Context, com.mob.commons.MobProduct, java.util.HashMap):void");
    }

    public final String a(final Context context) {
        final String[] strArr = new String[1];
        d.a(new File(ResHelper.getCacheRoot(context), "comm/locks/.globalLock"), new LockAction() {
            /* JADX WARNING: Removed duplicated region for block: B:14:0x002d  */
            /* JADX WARNING: Removed duplicated region for block: B:22:0x004d A[SYNTHETIC, Splitter:B:22:0x004d] */
            /* JADX WARNING: Removed duplicated region for block: B:30:0x0059 A[SYNTHETIC, Splitter:B:30:0x0059] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean run(com.mob.tools.utils.FileLocker r6) {
                /*
                    r5 = this;
                    r1 = 0
                    r4 = 0
                    com.mob.commons.authorize.a r0 = com.mob.commons.authorize.a.this     // Catch:{ Throwable -> 0x005d }
                    android.content.Context r2 = r5     // Catch:{ Throwable -> 0x005d }
                    java.io.File r0 = r0.b(r2)     // Catch:{ Throwable -> 0x005d }
                    boolean r2 = r0.exists()     // Catch:{ Throwable -> 0x005d }
                    if (r2 == 0) goto L_0x0065
                    boolean r2 = r0.isFile()     // Catch:{ Throwable -> 0x005d }
                    if (r2 == 0) goto L_0x0065
                    java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0042, all -> 0x0055 }
                    r3.<init>(r0)     // Catch:{ Throwable -> 0x0042, all -> 0x0055 }
                    java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch:{ Throwable -> 0x0042, all -> 0x0055 }
                    r2.<init>(r3)     // Catch:{ Throwable -> 0x0042, all -> 0x0055 }
                    java.lang.Object r0 = r2.readObject()     // Catch:{ Throwable -> 0x006d }
                    java.util.HashMap r0 = (java.util.HashMap) r0     // Catch:{ Throwable -> 0x006d }
                    if (r2 == 0) goto L_0x002b
                    r2.close()     // Catch:{ Throwable -> 0x0067 }
                L_0x002b:
                    if (r0 != 0) goto L_0x0035
                    com.mob.commons.authorize.a r0 = com.mob.commons.authorize.a.this
                    android.content.Context r1 = r5
                    java.util.HashMap r0 = r0.c(r1)
                L_0x0035:
                    java.lang.String[] r1 = r0
                    java.lang.String r2 = "duid"
                    java.lang.Object r0 = r0.get(r2)
                    java.lang.String r0 = (java.lang.String) r0
                    r1[r4] = r0
                    return r4
                L_0x0042:
                    r0 = move-exception
                    r2 = r1
                L_0x0044:
                    com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x006b }
                    r3.w(r0)     // Catch:{ all -> 0x006b }
                    if (r2 == 0) goto L_0x0065
                    r2.close()     // Catch:{ Throwable -> 0x0052 }
                    r0 = r1
                    goto L_0x002b
                L_0x0052:
                    r0 = move-exception
                    r0 = r1
                    goto L_0x002b
                L_0x0055:
                    r0 = move-exception
                    r2 = r1
                L_0x0057:
                    if (r2 == 0) goto L_0x005c
                    r2.close()     // Catch:{ Throwable -> 0x0069 }
                L_0x005c:
                    throw r0     // Catch:{ Throwable -> 0x005d }
                L_0x005d:
                    r0 = move-exception
                    com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()
                    r2.w(r0)
                L_0x0065:
                    r0 = r1
                    goto L_0x002b
                L_0x0067:
                    r1 = move-exception
                    goto L_0x002b
                L_0x0069:
                    r2 = move-exception
                    goto L_0x005c
                L_0x006b:
                    r0 = move-exception
                    goto L_0x0057
                L_0x006d:
                    r0 = move-exception
                    goto L_0x0044
                */
                throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.AnonymousClass2.run(com.mob.tools.utils.FileLocker):boolean");
            }
        });
        return strArr[0];
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0158 A[SYNTHETIC, Splitter:B:28:0x0158] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0164 A[SYNTHETIC, Splitter:B:33:0x0164] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.HashMap<java.lang.String, java.lang.Object> c(android.content.Context r9) {
        /*
            r8 = this;
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r1 = 0
            java.lang.String r0 = "DeviceHelper"
            java.lang.String r2 = "getInstance"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x014e }
            r4 = 0
            r3[r4] = r9     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r6 = com.mob.tools.utils.ReflectHelper.invokeStaticMethod(r0, r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r0 = "getModel"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r0, r2)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x014e }
            if (r0 != 0) goto L_0x0145
            java.lang.String r0 = ""
            r4 = r0
        L_0x0024:
            java.lang.String r0 = "getIMEI"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r0, r2)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x014e }
            if (r0 != 0) goto L_0x0148
            java.lang.String r0 = ""
            r3 = r0
        L_0x0034:
            java.lang.String r0 = "getMacAddress"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r0, r2)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x014e }
            if (r0 != 0) goto L_0x014b
            java.lang.String r0 = ""
            r2 = r0
        L_0x0044:
            java.lang.String r0 = "getSerialno"
            r7 = 0
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r0 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r0, r7)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x014e }
            if (r0 != 0) goto L_0x0053
            java.lang.String r0 = ""
        L_0x0053:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x014e }
            r7.<init>()     // Catch:{ Throwable -> 0x014e }
            java.lang.StringBuilder r4 = r7.append(r4)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r7 = ":"
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ Throwable -> 0x014e }
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r4 = ":"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x014e }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r3 = ":"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x014e }
            byte[] r0 = com.mob.tools.utils.Data.SHA1((java.lang.String) r0)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "duid"
            java.lang.String r0 = com.mob.tools.utils.Data.byteToHex(r0)     // Catch:{ Throwable -> 0x014e }
            r5.put(r2, r0)     // Catch:{ Throwable -> 0x014e }
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x014e }
            r0.<init>()     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "adsid"
            java.lang.String r3 = "getAdvertisingID"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "imei"
            java.lang.String r3 = "getIMEI"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "serialno"
            java.lang.String r3 = "getSerialno"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "androidid"
            java.lang.String r3 = "getAndroidID"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "mac"
            java.lang.String r3 = "getMacAddress"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "model"
            java.lang.String r3 = "getModel"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "factory"
            java.lang.String r3 = "getManufacturer"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "carrier"
            java.lang.String r3 = "getCarrier"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "screensize"
            java.lang.String r3 = "getScreenSize"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "sysver"
            java.lang.String r3 = "getOSVersionName"
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x014e }
            java.lang.Object r3 = com.mob.tools.utils.ReflectHelper.invokeInstanceMethod(r6, r3, r4)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "plat"
            r3 = 1
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x014e }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x014e }
            java.lang.String r2 = "deviceInfo"
            r5.put(r2, r0)     // Catch:{ Throwable -> 0x014e }
            java.io.File r0 = r8.b(r9)     // Catch:{ Throwable -> 0x014e }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x014e }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x014e }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x014e }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x014e }
            r2.writeObject(r5)     // Catch:{ Throwable -> 0x0170, all -> 0x016d }
            if (r2 == 0) goto L_0x0144
            r2.flush()     // Catch:{ Throwable -> 0x0173 }
            r2.close()     // Catch:{ Throwable -> 0x0173 }
        L_0x0144:
            return r5
        L_0x0145:
            r4 = r0
            goto L_0x0024
        L_0x0148:
            r3 = r0
            goto L_0x0034
        L_0x014b:
            r2 = r0
            goto L_0x0044
        L_0x014e:
            r0 = move-exception
        L_0x014f:
            com.mob.tools.log.NLog r2 = com.mob.tools.MobLog.getInstance()     // Catch:{ all -> 0x0161 }
            r2.w(r0)     // Catch:{ all -> 0x0161 }
            if (r1 == 0) goto L_0x0144
            r1.flush()     // Catch:{ Throwable -> 0x015f }
            r1.close()     // Catch:{ Throwable -> 0x015f }
            goto L_0x0144
        L_0x015f:
            r0 = move-exception
            goto L_0x0144
        L_0x0161:
            r0 = move-exception
        L_0x0162:
            if (r1 == 0) goto L_0x016a
            r1.flush()     // Catch:{ Throwable -> 0x016b }
            r1.close()     // Catch:{ Throwable -> 0x016b }
        L_0x016a:
            throw r0
        L_0x016b:
            r1 = move-exception
            goto L_0x016a
        L_0x016d:
            r0 = move-exception
            r1 = r2
            goto L_0x0162
        L_0x0170:
            r0 = move-exception
            r1 = r2
            goto L_0x014f
        L_0x0173:
            r0 = move-exception
            goto L_0x0144
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.authorize.a.c(android.content.Context):java.util.HashMap");
    }
}
