package com.loc;

import android.content.Context;
import com.loc.am;
import java.io.File;
import java.util.List;

/* compiled from: DexFileManager */
class ah {
    ah() {
    }

    /* compiled from: DexFileManager */
    static class a {
        a() {
        }

        static void a(aa aaVar, am amVar, String str) {
            al alVar = new al();
            alVar.a(amVar);
            aaVar.a(alVar, str);
        }

        static am a(aa aaVar, String str) {
            List c = aaVar.c(al.b(str), new al());
            if (c == null || c.size() <= 0) {
                return null;
            }
            return (am) c.get(0);
        }
    }

    static String a(String str) {
        return str + ".dex";
    }

    static boolean a(String str, String str2) {
        String a2 = s.a(str);
        if (a2 == null || !a2.equalsIgnoreCase(str2)) {
            return false;
        }
        return true;
    }

    static String b(String str, String str2) {
        return s.b(str + str2) + ".jar";
    }

    static String a(Context context, String str, String str2) {
        return a(context, b(str, str2));
    }

    static String a(Context context, String str) {
        return a(context) + File.separator + str;
    }

    static String a(Context context) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "dex";
    }

    static void a(Context context, aa aaVar, String str) {
        c(context, aaVar, str);
    }

    private static void c(Context context, aa aaVar, String str) {
        File file = new File(a(context, str));
        if (file.exists()) {
            file.delete();
        }
        aaVar.a(al.b(str), new al());
    }

    static void b(final Context context, final String str, final String str2) {
        new Thread() {
            public void run() {
                try {
                    aa aaVar = new aa(context, ak.c());
                    List<am> c2 = aaVar.c(al.a(str), new al());
                    if (c2 != null && c2.size() > 0) {
                        for (am amVar : c2) {
                            if (!str2.equalsIgnoreCase(amVar.d())) {
                                ah.a(context, aaVar, amVar.a());
                            }
                        }
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }.start();
    }

    static void b(Context context, aa aaVar, String str) {
        c(context, aaVar, str);
        c(context, aaVar, a(str));
    }

    static void a(aa aaVar, Context context, v vVar) {
        al alVar = new al();
        String a2 = vVar.a();
        String b = b(a2, vVar.b());
        am a3 = a.a(aaVar, b);
        if (a3 != null) {
            b(context, aaVar, b);
            List c = aaVar.c(al.a(a2, a3.e()), alVar);
            if (c != null && c.size() > 0) {
                am amVar = (am) c.get(0);
                amVar.a("errorstatus");
                a.a(aaVar, amVar, al.b(amVar.a()));
                File file = new File(a(context, amVar.a()));
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v5, types: [java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v11 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0040 A[SYNTHETIC, Splitter:B:18:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0045 A[SYNTHETIC, Splitter:B:21:0x0045] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void a(android.content.Context r5, com.loc.aa r6, com.loc.v r7, com.loc.am r8, java.lang.String r9) throws java.lang.Throwable {
        /*
            r2 = 0
            java.lang.String r0 = r7.a()     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0069, Throwable -> 0x006e, all -> 0x007b }
            java.lang.String r1 = r8.a()     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0069, Throwable -> 0x006e, all -> 0x007b }
            b((android.content.Context) r5, (com.loc.aa) r6, (java.lang.String) r1)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0069, Throwable -> 0x006e, all -> 0x007b }
            java.io.File r1 = new java.io.File     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0069, Throwable -> 0x006e, all -> 0x007b }
            r1.<init>(r9)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0069, Throwable -> 0x006e, all -> 0x007b }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0069, Throwable -> 0x006e, all -> 0x007b }
            r3.<init>(r1)     // Catch:{ FileNotFoundException -> 0x008b, IOException -> 0x0069, Throwable -> 0x006e, all -> 0x007b }
            java.io.File r4 = new java.io.File     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0086, Throwable -> 0x0081 }
            java.lang.String r1 = r7.b()     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0086, Throwable -> 0x0081 }
            java.lang.String r0 = a((android.content.Context) r5, (java.lang.String) r0, (java.lang.String) r1)     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0086, Throwable -> 0x0081 }
            r4.<init>(r0)     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0086, Throwable -> 0x0081 }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0086, Throwable -> 0x0081 }
            r0 = 1
            r1.<init>(r4, r0)     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0086, Throwable -> 0x0081 }
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r0]     // Catch:{ FileNotFoundException -> 0x0038, IOException -> 0x0088, Throwable -> 0x0083, all -> 0x007e }
        L_0x002d:
            int r2 = r3.read(r0)     // Catch:{ FileNotFoundException -> 0x0038, IOException -> 0x0088, Throwable -> 0x0083, all -> 0x007e }
            if (r2 <= 0) goto L_0x0049
            r4 = 0
            r1.write(r0, r4, r2)     // Catch:{ FileNotFoundException -> 0x0038, IOException -> 0x0088, Throwable -> 0x0083, all -> 0x007e }
            goto L_0x002d
        L_0x0038:
            r0 = move-exception
            r2 = r3
        L_0x003a:
            throw r0     // Catch:{ all -> 0x003b }
        L_0x003b:
            r0 = move-exception
            r3 = r2
            r2 = r1
        L_0x003e:
            if (r3 == 0) goto L_0x0043
            r3.close()     // Catch:{ IOException -> 0x0071 }
        L_0x0043:
            if (r2 == 0) goto L_0x0048
            r2.close()     // Catch:{ IOException -> 0x0076 }
        L_0x0048:
            throw r0
        L_0x0049:
            java.lang.String r0 = r8.a()     // Catch:{ FileNotFoundException -> 0x0038, IOException -> 0x0088, Throwable -> 0x0083, all -> 0x007e }
            java.lang.String r0 = com.loc.al.b((java.lang.String) r0)     // Catch:{ FileNotFoundException -> 0x0038, IOException -> 0x0088, Throwable -> 0x0083, all -> 0x007e }
            com.loc.ah.a.a(r6, r8, r0)     // Catch:{ FileNotFoundException -> 0x0038, IOException -> 0x0088, Throwable -> 0x0083, all -> 0x007e }
            if (r3 == 0) goto L_0x0059
            r3.close()     // Catch:{ IOException -> 0x005f }
        L_0x0059:
            if (r1 == 0) goto L_0x005e
            r1.close()     // Catch:{ IOException -> 0x0064 }
        L_0x005e:
            return
        L_0x005f:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0059
        L_0x0064:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x005e
        L_0x0069:
            r0 = move-exception
            r3 = r2
        L_0x006b:
            throw r0     // Catch:{ all -> 0x006c }
        L_0x006c:
            r0 = move-exception
            goto L_0x003e
        L_0x006e:
            r0 = move-exception
            r3 = r2
        L_0x0070:
            throw r0     // Catch:{ all -> 0x006c }
        L_0x0071:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0043
        L_0x0076:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0048
        L_0x007b:
            r0 = move-exception
            r3 = r2
            goto L_0x003e
        L_0x007e:
            r0 = move-exception
            r2 = r1
            goto L_0x003e
        L_0x0081:
            r0 = move-exception
            goto L_0x0070
        L_0x0083:
            r0 = move-exception
            r2 = r1
            goto L_0x0070
        L_0x0086:
            r0 = move-exception
            goto L_0x006b
        L_0x0088:
            r0 = move-exception
            r2 = r1
            goto L_0x006b
        L_0x008b:
            r0 = move-exception
            r1 = r2
            goto L_0x003a
        L_0x008e:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.ah.a(android.content.Context, com.loc.aa, com.loc.v, com.loc.am, java.lang.String):void");
    }

    static boolean a(Context context, aa aaVar, String str, v vVar) {
        return a(aaVar, str, a(context, str), vVar);
    }

    static boolean a(aa aaVar, String str, String str2, v vVar) {
        am a2 = a.a(aaVar, str);
        if (a2 == null || !vVar.b().equals(a2.d()) || !a(str2, a2.b())) {
            return false;
        }
        return true;
    }

    static String a(Context context, aa aaVar, v vVar) {
        List c = aaVar.c(al.b(vVar.a(), "copy"), new al());
        if (c == null || c.size() == 0) {
            return null;
        }
        a((List<am>) c);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= c.size()) {
                return null;
            }
            am amVar = (am) c.get(i2);
            if (a(context, aaVar, amVar.a(), vVar)) {
                try {
                    a(context, aaVar, vVar, new am.a(b(vVar.a(), vVar.b()), amVar.b(), vVar.a(), vVar.b(), amVar.e()).a("usedex").a(), a(context, amVar.a()));
                    return amVar.e();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            } else {
                a(context, aaVar, amVar.a());
                i = i2 + 1;
            }
        }
    }

    static void a(List<am> list) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < list.size() - 1) {
                int i3 = i2 + 1;
                while (true) {
                    int i4 = i3;
                    if (i4 >= list.size()) {
                        break;
                    }
                    am amVar = list.get(i2);
                    am amVar2 = list.get(i4);
                    if (an.a(amVar2.e(), amVar.e()) > 0) {
                        list.set(i2, amVar2);
                        list.set(i4, amVar);
                    }
                    i3 = i4 + 1;
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    static void a(Throwable th) {
        ao.a(ao.a() + File.separator + "dynamiclog.txt", w.a(th), false);
    }
}
