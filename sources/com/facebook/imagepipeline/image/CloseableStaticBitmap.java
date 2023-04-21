package com.facebook.imagepipeline.image;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageutils.BitmapUtil;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CloseableStaticBitmap extends CloseableBitmap {
    private volatile Bitmap mBitmap;
    @GuardedBy("this")
    private CloseableReference<Bitmap> mBitmapReference;
    private final QualityInfo mQualityInfo;
    private final int mRotationAngle;

    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.Object, com.facebook.common.references.ResourceReleaser<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CloseableStaticBitmap(android.graphics.Bitmap r3, com.facebook.common.references.ResourceReleaser<android.graphics.Bitmap> r4, com.facebook.imagepipeline.image.QualityInfo r5, int r6) {
        /*
            r2 = this;
            r2.<init>()
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r3)
            android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
            r2.mBitmap = r0
            android.graphics.Bitmap r1 = r2.mBitmap
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r4)
            com.facebook.common.references.ResourceReleaser r0 = (com.facebook.common.references.ResourceReleaser) r0
            com.facebook.common.references.CloseableReference r0 = com.facebook.common.references.CloseableReference.of(r1, r0)
            r2.mBitmapReference = r0
            r2.mQualityInfo = r5
            r2.mRotationAngle = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.image.CloseableStaticBitmap.<init>(android.graphics.Bitmap, com.facebook.common.references.ResourceReleaser, com.facebook.imagepipeline.image.QualityInfo, int):void");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [com.facebook.common.references.CloseableReference, com.facebook.common.references.CloseableReference<android.graphics.Bitmap>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CloseableStaticBitmap(com.facebook.common.references.CloseableReference<android.graphics.Bitmap> r2, com.facebook.imagepipeline.image.QualityInfo r3, int r4) {
        /*
            r1 = this;
            r1.<init>()
            com.facebook.common.references.CloseableReference r0 = r2.cloneOrNull()
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r0)
            com.facebook.common.references.CloseableReference r0 = (com.facebook.common.references.CloseableReference) r0
            r1.mBitmapReference = r0
            com.facebook.common.references.CloseableReference<android.graphics.Bitmap> r0 = r1.mBitmapReference
            java.lang.Object r0 = r0.get()
            android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
            r1.mBitmap = r0
            r1.mQualityInfo = r3
            r1.mRotationAngle = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.image.CloseableStaticBitmap.<init>(com.facebook.common.references.CloseableReference, com.facebook.imagepipeline.image.QualityInfo, int):void");
    }

    public void close() {
        CloseableReference<Bitmap> reference = detachBitmapReference();
        if (reference != null) {
            reference.close();
        }
    }

    private synchronized CloseableReference<Bitmap> detachBitmapReference() {
        CloseableReference<Bitmap> reference;
        reference = this.mBitmapReference;
        this.mBitmapReference = null;
        this.mBitmap = null;
        return reference;
    }

    public synchronized CloseableReference<Bitmap> convertToBitmapReference() {
        Preconditions.checkNotNull(this.mBitmapReference, "Cannot convert a closed static bitmap");
        return detachBitmapReference();
    }

    public synchronized boolean isClosed() {
        return this.mBitmapReference == null;
    }

    public Bitmap getUnderlyingBitmap() {
        return this.mBitmap;
    }

    public int getSizeInBytes() {
        return BitmapUtil.getSizeInBytes(this.mBitmap);
    }

    public int getWidth() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return 0;
        }
        return bitmap.getWidth();
    }

    public int getHeight() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return 0;
        }
        return bitmap.getHeight();
    }

    public int getRotationAngle() {
        return this.mRotationAngle;
    }

    public QualityInfo getQualityInfo() {
        return this.mQualityInfo;
    }
}
