package com.facebook.imagepipeline.image;

import android.graphics.Bitmap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.imageutils.BitmapUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class CloseableAnimatedBitmap extends CloseableBitmap {
    @GuardedBy("this")
    private List<CloseableReference<Bitmap>> mBitmapReferences;
    private volatile List<Bitmap> mBitmaps;
    private volatile List<Integer> mDurations;

    public CloseableAnimatedBitmap(List<CloseableReference<Bitmap>> bitmapReferences, List<Integer> durations) {
        boolean z;
        boolean z2 = true;
        Preconditions.checkNotNull(bitmapReferences);
        if (bitmapReferences.size() >= 1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "Need at least 1 frame!");
        this.mBitmapReferences = new ArrayList();
        this.mBitmaps = new ArrayList();
        for (CloseableReference<Bitmap> bitmapReference : bitmapReferences) {
            this.mBitmapReferences.add(bitmapReference.clone());
            this.mBitmaps.add(bitmapReference.get());
        }
        this.mDurations = (List) Preconditions.checkNotNull(durations);
        Preconditions.checkState(this.mDurations.size() != this.mBitmaps.size() ? false : z2, "Arrays length mismatch!");
    }

    public CloseableAnimatedBitmap(List<Bitmap> bitmaps, List<Integer> durations, ResourceReleaser<Bitmap> resourceReleaser) {
        boolean z;
        boolean z2 = true;
        Preconditions.checkNotNull(bitmaps);
        if (bitmaps.size() >= 1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "Need at least 1 frame!");
        this.mBitmaps = new ArrayList();
        this.mBitmapReferences = new ArrayList();
        for (Bitmap bitmap : bitmaps) {
            this.mBitmapReferences.add(CloseableReference.of(bitmap, resourceReleaser));
            this.mBitmaps.add(bitmap);
        }
        this.mDurations = (List) Preconditions.checkNotNull(durations);
        Preconditions.checkState(this.mDurations.size() != this.mBitmaps.size() ? false : z2, "Arrays length mismatch!");
    }

    public void close() {
        synchronized (this) {
            if (this.mBitmapReferences != null) {
                List<CloseableReference<Bitmap>> bitmapReferences = this.mBitmapReferences;
                this.mBitmapReferences = null;
                this.mBitmaps = null;
                this.mDurations = null;
                CloseableReference.closeSafely((Iterable<? extends CloseableReference<?>>) bitmapReferences);
            }
        }
    }

    public synchronized boolean isClosed() {
        return this.mBitmaps == null;
    }

    public List<Bitmap> getBitmaps() {
        return this.mBitmaps;
    }

    public List<Integer> getDurations() {
        return this.mDurations;
    }

    public Bitmap getUnderlyingBitmap() {
        List<Bitmap> bitmaps = this.mBitmaps;
        if (bitmaps != null) {
            return bitmaps.get(0);
        }
        return null;
    }

    public int getSizeInBytes() {
        List<Bitmap> bitmaps = this.mBitmaps;
        if (bitmaps == null || bitmaps.size() == 0) {
            return 0;
        }
        return BitmapUtil.getSizeInBytes(bitmaps.get(0)) * bitmaps.size();
    }

    public int getWidth() {
        List<Bitmap> bitmaps = this.mBitmaps;
        if (bitmaps == null) {
            return 0;
        }
        return bitmaps.get(0).getWidth();
    }

    public int getHeight() {
        List<Bitmap> bitmaps = this.mBitmaps;
        if (bitmaps == null) {
            return 0;
        }
        return bitmaps.get(0).getHeight();
    }
}
