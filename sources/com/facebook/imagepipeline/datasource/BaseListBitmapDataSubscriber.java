package com.facebook.imagepipeline.datasource;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.List;

public abstract class BaseListBitmapDataSubscriber extends BaseDataSubscriber<List<CloseableReference<CloseableImage>>> {
    /* access modifiers changed from: protected */
    public abstract void onNewResultListImpl(List<Bitmap> list);

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public void onNewResultImpl(com.facebook.datasource.DataSource<java.util.List<com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage>>> r7) {
        /*
            r6 = this;
            r5 = 0
            boolean r4 = r7.isFinished()
            if (r4 != 0) goto L_0x0008
        L_0x0007:
            return
        L_0x0008:
            java.lang.Object r3 = r7.getResult()
            java.util.List r3 = (java.util.List) r3
            if (r3 != 0) goto L_0x0014
            r6.onNewResultListImpl(r5)
            goto L_0x0007
        L_0x0014:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0045 }
            int r4 = r3.size()     // Catch:{ all -> 0x0045 }
            r0.<init>(r4)     // Catch:{ all -> 0x0045 }
            java.util.Iterator r2 = r3.iterator()     // Catch:{ all -> 0x0045 }
        L_0x0021:
            boolean r4 = r2.hasNext()     // Catch:{ all -> 0x0045 }
            if (r4 == 0) goto L_0x005f
            java.lang.Object r1 = r2.next()     // Catch:{ all -> 0x0045 }
            com.facebook.common.references.CloseableReference r1 = (com.facebook.common.references.CloseableReference) r1     // Catch:{ all -> 0x0045 }
            if (r1 == 0) goto L_0x005a
            java.lang.Object r4 = r1.get()     // Catch:{ all -> 0x0045 }
            boolean r4 = r4 instanceof com.facebook.imagepipeline.image.CloseableBitmap     // Catch:{ all -> 0x0045 }
            if (r4 == 0) goto L_0x005a
            java.lang.Object r4 = r1.get()     // Catch:{ all -> 0x0045 }
            com.facebook.imagepipeline.image.CloseableBitmap r4 = (com.facebook.imagepipeline.image.CloseableBitmap) r4     // Catch:{ all -> 0x0045 }
            android.graphics.Bitmap r4 = r4.getUnderlyingBitmap()     // Catch:{ all -> 0x0045 }
            r0.add(r4)     // Catch:{ all -> 0x0045 }
            goto L_0x0021
        L_0x0045:
            r4 = move-exception
            java.util.Iterator r2 = r3.iterator()
        L_0x004a:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L_0x0076
            java.lang.Object r1 = r2.next()
            com.facebook.common.references.CloseableReference r1 = (com.facebook.common.references.CloseableReference) r1
            com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r1)
            goto L_0x004a
        L_0x005a:
            r4 = 0
            r0.add(r4)     // Catch:{ all -> 0x0045 }
            goto L_0x0021
        L_0x005f:
            r6.onNewResultListImpl(r0)     // Catch:{ all -> 0x0045 }
            java.util.Iterator r2 = r3.iterator()
        L_0x0066:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x0007
            java.lang.Object r1 = r2.next()
            com.facebook.common.references.CloseableReference r1 = (com.facebook.common.references.CloseableReference) r1
            com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r1)
            goto L_0x0066
        L_0x0076:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.datasource.BaseListBitmapDataSubscriber.onNewResultImpl(com.facebook.datasource.DataSource):void");
    }
}
