package com.baidu.platform.comapi.commonutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class a {
    private static final boolean a = (Build.VERSION.SDK_INT >= 8);

    public static Bitmap a(String str, Context context) {
        try {
            InputStream open = context.getAssets().open(str);
            if (open != null) {
                return BitmapFactory.decodeStream(open);
            }
            return null;
        } catch (Exception e) {
            return BitmapFactory.decodeFile(b("assets/" + str, str, context));
        }
    }

    private static void a(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
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
                        } catch (IOException e) {
                            return;
                        }
                    } catch (IOException e2) {
                        return;
                    }
                }
            } finally {
                try {
                    inputStream.close();
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3) {
                        return;
                    }
                } catch (IOException e4) {
                    return;
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0079 A[SYNTHETIC, Splitter:B:23:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007e A[Catch:{ IOException -> 0x0082 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x008e A[SYNTHETIC, Splitter:B:32:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0093 A[Catch:{ IOException -> 0x0097 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.lang.String r7, java.lang.String r8, android.content.Context r9) {
        /*
            r0 = 0
            android.content.res.AssetManager r1 = r9.getAssets()     // Catch:{ Exception -> 0x005f, all -> 0x0087 }
            java.io.InputStream r2 = r1.open(r7)     // Catch:{ Exception -> 0x005f, all -> 0x0087 }
            if (r2 == 0) goto L_0x00b0
            int r1 = r2.available()     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            byte[] r3 = new byte[r1]     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            r2.read(r3)     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            r1.<init>()     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            java.io.File r5 = r9.getFilesDir()     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            java.lang.String r5 = "/"
            java.lang.StringBuilder r1 = r1.append(r5)     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            r4.<init>(r1)     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            boolean r1 = r4.exists()     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            if (r1 == 0) goto L_0x0041
            r4.delete()     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
        L_0x0041:
            r4.createNewFile()     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            r1.<init>(r4)     // Catch:{ Exception -> 0x00a9, all -> 0x009c }
            r1.write(r3)     // Catch:{ Exception -> 0x00ac, all -> 0x00a1 }
            r1.close()     // Catch:{ Exception -> 0x00ac, all -> 0x00a1 }
        L_0x004f:
            if (r2 == 0) goto L_0x0054
            r2.close()     // Catch:{ IOException -> 0x005a }
        L_0x0054:
            if (r1 == 0) goto L_0x0059
            r1.close()     // Catch:{ IOException -> 0x005a }
        L_0x0059:
            return
        L_0x005a:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0059
        L_0x005f:
            r1 = move-exception
            r1 = r0
        L_0x0061:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a3 }
            r2.<init>()     // Catch:{ all -> 0x00a3 }
            java.lang.String r3 = "assets/"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00a3 }
            java.lang.StringBuilder r2 = r2.append(r7)     // Catch:{ all -> 0x00a3 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00a3 }
            b(r2, r8, r9)     // Catch:{ all -> 0x00a3 }
            if (r1 == 0) goto L_0x007c
            r1.close()     // Catch:{ IOException -> 0x0082 }
        L_0x007c:
            if (r0 == 0) goto L_0x0059
            r0.close()     // Catch:{ IOException -> 0x0082 }
            goto L_0x0059
        L_0x0082:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0059
        L_0x0087:
            r1 = move-exception
            r2 = r0
            r6 = r0
            r0 = r1
            r1 = r6
        L_0x008c:
            if (r2 == 0) goto L_0x0091
            r2.close()     // Catch:{ IOException -> 0x0097 }
        L_0x0091:
            if (r1 == 0) goto L_0x0096
            r1.close()     // Catch:{ IOException -> 0x0097 }
        L_0x0096:
            throw r0
        L_0x0097:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0096
        L_0x009c:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
            goto L_0x008c
        L_0x00a1:
            r0 = move-exception
            goto L_0x008c
        L_0x00a3:
            r2 = move-exception
            r6 = r2
            r2 = r1
            r1 = r0
            r0 = r6
            goto L_0x008c
        L_0x00a9:
            r1 = move-exception
            r1 = r2
            goto L_0x0061
        L_0x00ac:
            r0 = move-exception
            r0 = r1
            r1 = r2
            goto L_0x0061
        L_0x00b0:
            r1 = r0
            goto L_0x004f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.commonutils.a.a(java.lang.String, java.lang.String, android.content.Context):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00be A[SYNTHETIC, Splitter:B:36:0x00be] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String b(java.lang.String r9, java.lang.String r10, android.content.Context r11) {
        /*
            r1 = 0
            java.lang.String r0 = ""
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.io.File r2 = r11.getFilesDir()
            java.lang.String r2 = r2.getAbsolutePath()
            r4.<init>(r2)
            boolean r2 = a
            if (r2 == 0) goto L_0x0018
            java.lang.String r0 = r11.getPackageCodePath()
        L_0x0018:
            java.util.zip.ZipFile r2 = new java.util.zip.ZipFile     // Catch:{ Exception -> 0x00cd, all -> 0x00ba }
            r2.<init>(r0)     // Catch:{ Exception -> 0x00cd, all -> 0x00ba }
            java.lang.String r0 = "/"
            int r0 = r10.lastIndexOf(r0)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            if (r0 <= 0) goto L_0x0071
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.io.File r5 = r11.getFilesDir()     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            r3.<init>(r5)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            r5 = 0
            java.lang.String r5 = r10.substring(r5, r0)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            int r0 = r0 + 1
            int r6 = r10.length()     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.String r6 = r10.substring(r0, r6)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            r7.<init>()     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.String r8 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.String r8 = "/"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.StringBuilder r5 = r7.append(r5)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            r0.<init>(r5, r6)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
        L_0x0061:
            r3.mkdirs()     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.util.zip.ZipEntry r3 = r2.getEntry(r9)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            if (r3 != 0) goto L_0x009d
            if (r2 == 0) goto L_0x006f
            r2.close()     // Catch:{ IOException -> 0x00c2 }
        L_0x006f:
            r0 = r1
        L_0x0070:
            return r0
        L_0x0071:
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.io.File r0 = r11.getFilesDir()     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.String r5 = "assets"
            r3.<init>(r0, r5)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.String r5 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            r0.<init>(r5, r10)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            goto L_0x0061
        L_0x0086:
            r0 = move-exception
            r1 = r2
        L_0x0088:
            java.lang.Class<com.baidu.platform.comapi.commonutils.a> r2 = com.baidu.platform.comapi.commonutils.a.class
            java.lang.String r2 = r2.getSimpleName()     // Catch:{ all -> 0x00ca }
            java.lang.String r3 = "copyAssetsError"
            android.util.Log.e(r2, r3, r0)     // Catch:{ all -> 0x00ca }
            if (r1 == 0) goto L_0x0098
            r1.close()     // Catch:{ IOException -> 0x00c4 }
        L_0x0098:
            java.lang.String r0 = r4.toString()
            goto L_0x0070
        L_0x009d:
            java.io.InputStream r1 = r2.getInputStream(r3)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            r3.<init>(r0)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            a((java.io.InputStream) r1, (java.io.FileOutputStream) r3)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            java.lang.String r0 = "/"
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            r0.append(r9)     // Catch:{ Exception -> 0x0086, all -> 0x00c8 }
            if (r2 == 0) goto L_0x0098
            r2.close()     // Catch:{ IOException -> 0x00b8 }
            goto L_0x0098
        L_0x00b8:
            r0 = move-exception
            goto L_0x0098
        L_0x00ba:
            r0 = move-exception
            r2 = r1
        L_0x00bc:
            if (r2 == 0) goto L_0x00c1
            r2.close()     // Catch:{ IOException -> 0x00c6 }
        L_0x00c1:
            throw r0
        L_0x00c2:
            r0 = move-exception
            goto L_0x006f
        L_0x00c4:
            r0 = move-exception
            goto L_0x0098
        L_0x00c6:
            r1 = move-exception
            goto L_0x00c1
        L_0x00c8:
            r0 = move-exception
            goto L_0x00bc
        L_0x00ca:
            r0 = move-exception
            r2 = r1
            goto L_0x00bc
        L_0x00cd:
            r0 = move-exception
            goto L_0x0088
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.commonutils.a.b(java.lang.String, java.lang.String, android.content.Context):java.lang.String");
    }
}
