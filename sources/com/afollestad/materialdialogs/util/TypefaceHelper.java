package com.afollestad.materialdialogs.util;

import android.graphics.Typeface;
import android.support.v4.util.SimpleArrayMap;

public class TypefaceHelper {
    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Typeface get(android.content.Context r7, java.lang.String r8) {
        /*
            android.support.v4.util.SimpleArrayMap<java.lang.String, android.graphics.Typeface> r3 = cache
            monitor-enter(r3)
            android.support.v4.util.SimpleArrayMap<java.lang.String, android.graphics.Typeface> r2 = cache     // Catch:{ all -> 0x002a }
            boolean r2 = r2.containsKey(r8)     // Catch:{ all -> 0x002a }
            if (r2 != 0) goto L_0x002d
            android.content.res.AssetManager r2 = r7.getAssets()     // Catch:{ RuntimeException -> 0x0026 }
            java.lang.String r4 = "fonts/%s"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ RuntimeException -> 0x0026 }
            r6 = 0
            r5[r6] = r8     // Catch:{ RuntimeException -> 0x0026 }
            java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch:{ RuntimeException -> 0x0026 }
            android.graphics.Typeface r1 = android.graphics.Typeface.createFromAsset(r2, r4)     // Catch:{ RuntimeException -> 0x0026 }
            android.support.v4.util.SimpleArrayMap<java.lang.String, android.graphics.Typeface> r2 = cache     // Catch:{ RuntimeException -> 0x0026 }
            r2.put(r8, r1)     // Catch:{ RuntimeException -> 0x0026 }
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
        L_0x0025:
            return r1
        L_0x0026:
            r0 = move-exception
            r1 = 0
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            goto L_0x0025
        L_0x002a:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            throw r2
        L_0x002d:
            android.support.v4.util.SimpleArrayMap<java.lang.String, android.graphics.Typeface> r2 = cache     // Catch:{ all -> 0x002a }
            java.lang.Object r2 = r2.get(r8)     // Catch:{ all -> 0x002a }
            android.graphics.Typeface r2 = (android.graphics.Typeface) r2     // Catch:{ all -> 0x002a }
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            r1 = r2
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: com.afollestad.materialdialogs.util.TypefaceHelper.get(android.content.Context, java.lang.String):android.graphics.Typeface");
    }
}
