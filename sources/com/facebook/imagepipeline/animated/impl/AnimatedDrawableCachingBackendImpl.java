package com.facebook.imagepipeline.animated.impl;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.v4.util.SparseArrayCompat;
import bolts.Continuation;
import bolts.Task;
import com.facebook.common.executors.SerialExecutorService;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.common.time.MonotonicClock;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableCachingBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableOptions;
import com.facebook.imagepipeline.animated.base.DelegatingAnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.impl.AnimatedImageCompositor;
import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

public class AnimatedDrawableCachingBackendImpl extends DelegatingAnimatedDrawableBackend implements AnimatedDrawableCachingBackend {
    private static final int PREFETCH_FRAMES = 3;
    private static final Class<?> TAG = AnimatedDrawableCachingBackendImpl.class;
    private static final AtomicInteger sTotalBitmaps = new AtomicInteger();
    private final ActivityManager mActivityManager;
    private final AnimatedDrawableBackend mAnimatedDrawableBackend;
    private final AnimatedDrawableOptions mAnimatedDrawableOptions;
    private final AnimatedDrawableUtil mAnimatedDrawableUtil;
    private final AnimatedImageCompositor mAnimatedImageCompositor;
    private final double mApproxKiloBytesToHoldAllFrames;
    @GuardedBy("this")
    private final WhatToKeepCachedArray mBitmapsToKeepCached;
    @GuardedBy("this")
    private final SparseArrayCompat<CloseableReference<Bitmap>> mCachedBitmaps;
    @GuardedBy("ui-thread")
    private int mCurrentFrameIndex;
    @GuardedBy("this")
    private final SparseArrayCompat<Task<Object>> mDecodesInFlight;
    private final SerialExecutorService mExecutorService;
    @GuardedBy("this")
    private final List<Bitmap> mFreeBitmaps;
    private final double mMaximumKiloBytes;
    private final MonotonicClock mMonotonicClock;
    private final ResourceReleaser<Bitmap> mResourceReleaserForBitmaps;

    public AnimatedDrawableCachingBackendImpl(SerialExecutorService executorService, ActivityManager activityManager, AnimatedDrawableUtil animatedDrawableUtil, MonotonicClock monotonicClock, AnimatedDrawableBackend animatedDrawableBackend, AnimatedDrawableOptions options) {
        super(animatedDrawableBackend);
        this.mExecutorService = executorService;
        this.mActivityManager = activityManager;
        this.mAnimatedDrawableUtil = animatedDrawableUtil;
        this.mMonotonicClock = monotonicClock;
        this.mAnimatedDrawableBackend = animatedDrawableBackend;
        this.mAnimatedDrawableOptions = options;
        this.mMaximumKiloBytes = options.maximumBytes >= 0 ? (double) (options.maximumBytes / 1024) : (double) (getDefaultMaxBytes(activityManager) / 1024);
        this.mAnimatedImageCompositor = new AnimatedImageCompositor(animatedDrawableBackend, new AnimatedImageCompositor.Callback() {
            public void onIntermediateResult(int frameNumber, Bitmap bitmap) {
                AnimatedDrawableCachingBackendImpl.this.maybeCacheBitmapDuringRender(frameNumber, bitmap);
            }

            public CloseableReference<Bitmap> getCachedBitmap(int frameNumber) {
                return AnimatedDrawableCachingBackendImpl.this.getCachedOrPredecodedFrame(frameNumber);
            }
        });
        this.mResourceReleaserForBitmaps = new ResourceReleaser<Bitmap>() {
            public void release(Bitmap value) {
                AnimatedDrawableCachingBackendImpl.this.releaseBitmapInternal(value);
            }
        };
        this.mFreeBitmaps = new ArrayList();
        this.mDecodesInFlight = new SparseArrayCompat<>(10);
        this.mCachedBitmaps = new SparseArrayCompat<>(10);
        this.mBitmapsToKeepCached = new WhatToKeepCachedArray(this.mAnimatedDrawableBackend.getFrameCount());
        this.mApproxKiloBytesToHoldAllFrames = (double) (((this.mAnimatedDrawableBackend.getRenderedWidth() * this.mAnimatedDrawableBackend.getRenderedHeight()) / 1024) * this.mAnimatedDrawableBackend.getFrameCount() * 4);
    }

    /* access modifiers changed from: protected */
    public synchronized void finalize() throws Throwable {
        super.finalize();
        if (this.mCachedBitmaps.size() > 0) {
            FLog.d(TAG, "Finalizing with rendered bitmaps");
        }
        sTotalBitmaps.addAndGet(-this.mFreeBitmaps.size());
        this.mFreeBitmaps.clear();
    }

    private Bitmap createNewBitmap() {
        FLog.v(TAG, "Creating new bitmap");
        sTotalBitmaps.incrementAndGet();
        FLog.v(TAG, "Total bitmaps: %d", (Object) Integer.valueOf(sTotalBitmaps.get()));
        return Bitmap.createBitmap(this.mAnimatedDrawableBackend.getRenderedWidth(), this.mAnimatedDrawableBackend.getRenderedHeight(), Bitmap.Config.ARGB_8888);
    }

    public void renderFrame(int frameNumber, Canvas canvas) {
        throw new IllegalStateException();
    }

    public CloseableReference<Bitmap> getBitmapForFrame(int frameNumber) {
        this.mCurrentFrameIndex = frameNumber;
        CloseableReference<Bitmap> result = getBitmapForFrameInternal(frameNumber, false);
        schedulePrefetches();
        return result;
    }

    public CloseableReference<Bitmap> getPreviewBitmap() {
        return getAnimatedImageResult().getPreviewBitmap();
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public CloseableReference<Bitmap> getBitmapForFrameBlocking(int frameNumber) {
        this.mCurrentFrameIndex = frameNumber;
        CloseableReference<Bitmap> result = getBitmapForFrameInternal(frameNumber, true);
        schedulePrefetches();
        return result;
    }

    /* Debug info: failed to restart local var, previous not found, register: 7 */
    public AnimatedDrawableCachingBackend forNewBounds(Rect bounds) {
        AnimatedDrawableBackend newBackend = this.mAnimatedDrawableBackend.forNewBounds(bounds);
        return newBackend == this.mAnimatedDrawableBackend ? this : new AnimatedDrawableCachingBackendImpl(this.mExecutorService, this.mActivityManager, this.mAnimatedDrawableUtil, this.mMonotonicClock, newBackend, this.mAnimatedDrawableOptions);
    }

    public synchronized void dropCaches() {
        this.mBitmapsToKeepCached.setAll(false);
        dropBitmapsThatShouldNotBeCached();
        for (Bitmap freeBitmap : this.mFreeBitmaps) {
            freeBitmap.recycle();
            sTotalBitmaps.decrementAndGet();
        }
        this.mFreeBitmaps.clear();
        this.mAnimatedDrawableBackend.dropCaches();
        FLog.v(TAG, "Total bitmaps: %d", (Object) Integer.valueOf(sTotalBitmaps.get()));
    }

    public int getMemoryUsage() {
        int bytes = 0;
        synchronized (this) {
            for (Bitmap bitmap : this.mFreeBitmaps) {
                bytes += this.mAnimatedDrawableUtil.getSizeOfBitmap(bitmap);
            }
            for (int i = 0; i < this.mCachedBitmaps.size(); i++) {
                bytes += this.mAnimatedDrawableUtil.getSizeOfBitmap(this.mCachedBitmaps.valueAt(i).get());
            }
        }
        return bytes + this.mAnimatedDrawableBackend.getMemoryUsage();
    }

    public void appendDebugOptionString(StringBuilder sb) {
        if (this.mAnimatedDrawableOptions.forceKeepAllFramesInMemory) {
            sb.append("Pinned To Memory");
        } else {
            if (this.mApproxKiloBytesToHoldAllFrames < this.mMaximumKiloBytes) {
                sb.append("within ");
            } else {
                sb.append("exceeds ");
            }
            this.mAnimatedDrawableUtil.appendMemoryString(sb, (int) this.mMaximumKiloBytes);
        }
        if (shouldKeepAllFramesInMemory() && this.mAnimatedDrawableOptions.allowPrefetching) {
            sb.append(" MT");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0026, code lost:
        if (0 == 0) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        r1 = "renderedOnCallingThread";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
        com.facebook.common.logging.FLog.v(TAG, "obtainBitmap for frame %d took %d ms (%s)", (java.lang.Object) java.lang.Integer.valueOf(r14), (java.lang.Object) java.lang.Long.valueOf(r4), (java.lang.Object) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003a, code lost:
        if (0 == 0) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003c, code lost:
        r1 = "deferred";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003f, code lost:
        r1 = "ok";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
        if (r15 == false) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
        r3 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r0 = obtainBitmapInternal();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r13.mAnimatedImageCompositor.renderFrame(r14, r0.get());
        maybeCacheRenderedBitmap(r14, r0);
        r8 = r0.clone();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005f, code lost:
        r4 = r13.mMonotonicClock.now() - r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x006b, code lost:
        if (r4 <= 10) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006f, code lost:
        if (1 == 0) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0071, code lost:
        r1 = "renderedOnCallingThread";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0073, code lost:
        com.facebook.common.logging.FLog.v(TAG, "obtainBitmap for frame %d took %d ms (%s)", (java.lang.Object) java.lang.Integer.valueOf(r14), (java.lang.Object) java.lang.Long.valueOf(r4), (java.lang.Object) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ac, code lost:
        if (0 == 0) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ae, code lost:
        r1 = "deferred";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b1, code lost:
        r1 = "ok";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00b9, code lost:
        r4 = r13.mMonotonicClock.now() - r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00c7, code lost:
        if (r4 <= 10) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00cb, code lost:
        if (0 == 0) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00cd, code lost:
        r1 = "renderedOnCallingThread";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00cf, code lost:
        com.facebook.common.logging.FLog.v(TAG, "obtainBitmap for frame %d took %d ms (%s)", (java.lang.Object) java.lang.Integer.valueOf(r14), (java.lang.Object) java.lang.Long.valueOf(r4), (java.lang.Object) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00e0, code lost:
        if (1 == 0) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00e2, code lost:
        r1 = "deferred";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00e5, code lost:
        r1 = "ok";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0016, code lost:
        r4 = r13.mMonotonicClock.now() - r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0022, code lost:
        if (r4 <= 10) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.facebook.common.references.CloseableReference<android.graphics.Bitmap> getBitmapForFrameInternal(int r14, boolean r15) {
        /*
            r13 = this;
            r3 = 0
            r2 = 0
            com.facebook.common.time.MonotonicClock r8 = r13.mMonotonicClock
            long r6 = r8.now()
            monitor-enter(r13)     // Catch:{ all -> 0x0087 }
            com.facebook.imagepipeline.animated.impl.WhatToKeepCachedArray r8 = r13.mBitmapsToKeepCached     // Catch:{ all -> 0x0084 }
            r9 = 1
            r8.set(r14, r9)     // Catch:{ all -> 0x0084 }
            com.facebook.common.references.CloseableReference r0 = r13.getCachedOrPredecodedFrame(r14)     // Catch:{ all -> 0x0084 }
            if (r0 == 0) goto L_0x0042
            monitor-exit(r13)     // Catch:{ all -> 0x0084 }
            com.facebook.common.time.MonotonicClock r8 = r13.mMonotonicClock
            long r8 = r8.now()
            long r4 = r8 - r6
            r8 = 10
            int r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x0039
            java.lang.String r1 = ""
            if (r3 == 0) goto L_0x003a
            java.lang.String r1 = "renderedOnCallingThread"
        L_0x002a:
            java.lang.Class<?> r8 = TAG
            java.lang.String r9 = "obtainBitmap for frame %d took %d ms (%s)"
            java.lang.Integer r10 = java.lang.Integer.valueOf(r14)
            java.lang.Long r11 = java.lang.Long.valueOf(r4)
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r8, (java.lang.String) r9, (java.lang.Object) r10, (java.lang.Object) r11, (java.lang.Object) r1)
        L_0x0039:
            return r0
        L_0x003a:
            if (r2 == 0) goto L_0x003f
            java.lang.String r1 = "deferred"
            goto L_0x002a
        L_0x003f:
            java.lang.String r1 = "ok"
            goto L_0x002a
        L_0x0042:
            monitor-exit(r13)     // Catch:{ all -> 0x0084 }
            if (r15 == 0) goto L_0x00b9
            r3 = 1
            com.facebook.common.references.CloseableReference r0 = r13.obtainBitmapInternal()     // Catch:{ all -> 0x0087 }
            com.facebook.imagepipeline.animated.impl.AnimatedImageCompositor r9 = r13.mAnimatedImageCompositor     // Catch:{ all -> 0x00b4 }
            java.lang.Object r8 = r0.get()     // Catch:{ all -> 0x00b4 }
            android.graphics.Bitmap r8 = (android.graphics.Bitmap) r8     // Catch:{ all -> 0x00b4 }
            r9.renderFrame(r14, r8)     // Catch:{ all -> 0x00b4 }
            r13.maybeCacheRenderedBitmap(r14, r0)     // Catch:{ all -> 0x00b4 }
            com.facebook.common.references.CloseableReference r8 = r0.clone()     // Catch:{ all -> 0x00b4 }
            r0.close()     // Catch:{ all -> 0x0087 }
            com.facebook.common.time.MonotonicClock r9 = r13.mMonotonicClock
            long r10 = r9.now()
            long r4 = r10 - r6
            r10 = 10
            int r9 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r9 <= 0) goto L_0x0082
            java.lang.String r1 = ""
            if (r3 == 0) goto L_0x00ac
            java.lang.String r1 = "renderedOnCallingThread"
        L_0x0073:
            java.lang.Class<?> r9 = TAG
            java.lang.String r10 = "obtainBitmap for frame %d took %d ms (%s)"
            java.lang.Integer r11 = java.lang.Integer.valueOf(r14)
            java.lang.Long r12 = java.lang.Long.valueOf(r4)
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r9, (java.lang.String) r10, (java.lang.Object) r11, (java.lang.Object) r12, (java.lang.Object) r1)
        L_0x0082:
            r0 = r8
            goto L_0x0039
        L_0x0084:
            r8 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x0084 }
            throw r8     // Catch:{ all -> 0x0087 }
        L_0x0087:
            r8 = move-exception
            com.facebook.common.time.MonotonicClock r9 = r13.mMonotonicClock
            long r10 = r9.now()
            long r4 = r10 - r6
            r10 = 10
            int r9 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r9 <= 0) goto L_0x00ab
            java.lang.String r1 = ""
            if (r3 == 0) goto L_0x00e8
            java.lang.String r1 = "renderedOnCallingThread"
        L_0x009c:
            java.lang.Class<?> r9 = TAG
            java.lang.String r10 = "obtainBitmap for frame %d took %d ms (%s)"
            java.lang.Integer r11 = java.lang.Integer.valueOf(r14)
            java.lang.Long r12 = java.lang.Long.valueOf(r4)
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r9, (java.lang.String) r10, (java.lang.Object) r11, (java.lang.Object) r12, (java.lang.Object) r1)
        L_0x00ab:
            throw r8
        L_0x00ac:
            if (r2 == 0) goto L_0x00b1
            java.lang.String r1 = "deferred"
            goto L_0x0073
        L_0x00b1:
            java.lang.String r1 = "ok"
            goto L_0x0073
        L_0x00b4:
            r8 = move-exception
            r0.close()     // Catch:{ all -> 0x0087 }
            throw r8     // Catch:{ all -> 0x0087 }
        L_0x00b9:
            r2 = 1
            r0 = 0
            com.facebook.common.time.MonotonicClock r8 = r13.mMonotonicClock
            long r8 = r8.now()
            long r4 = r8 - r6
            r8 = 10
            int r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x0039
            java.lang.String r1 = ""
            if (r3 == 0) goto L_0x00e0
            java.lang.String r1 = "renderedOnCallingThread"
        L_0x00cf:
            java.lang.Class<?> r8 = TAG
            java.lang.String r9 = "obtainBitmap for frame %d took %d ms (%s)"
            java.lang.Integer r10 = java.lang.Integer.valueOf(r14)
            java.lang.Long r11 = java.lang.Long.valueOf(r4)
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r8, (java.lang.String) r9, (java.lang.Object) r10, (java.lang.Object) r11, (java.lang.Object) r1)
            goto L_0x0039
        L_0x00e0:
            if (r2 == 0) goto L_0x00e5
            java.lang.String r1 = "deferred"
            goto L_0x00cf
        L_0x00e5:
            java.lang.String r1 = "ok"
            goto L_0x00cf
        L_0x00e8:
            if (r2 == 0) goto L_0x00ed
            java.lang.String r1 = "deferred"
            goto L_0x009c
        L_0x00ed:
            java.lang.String r1 = "ok"
            goto L_0x009c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImpl.getBitmapForFrameInternal(int, boolean):com.facebook.common.references.CloseableReference");
    }

    /* access modifiers changed from: private */
    public void maybeCacheBitmapDuringRender(int frameNumber, Bitmap bitmap) {
        boolean cacheBitmap = false;
        synchronized (this) {
            if (this.mBitmapsToKeepCached.get(frameNumber)) {
                cacheBitmap = this.mCachedBitmaps.get(frameNumber) == null;
            }
        }
        if (cacheBitmap) {
            copyAndCacheBitmapDuringRendering(frameNumber, bitmap);
        }
    }

    private void copyAndCacheBitmapDuringRendering(int frameNumber, Bitmap sourceBitmap) {
        CloseableReference<Bitmap> destBitmapReference = obtainBitmapInternal();
        try {
            Canvas copyCanvas = new Canvas(destBitmapReference.get());
            copyCanvas.drawColor(0, PorterDuff.Mode.SRC);
            copyCanvas.drawBitmap(sourceBitmap, 0.0f, 0.0f, (Paint) null);
            maybeCacheRenderedBitmap(frameNumber, destBitmapReference);
        } finally {
            destBitmapReference.close();
        }
    }

    private CloseableReference<Bitmap> obtainBitmapInternal() {
        Bitmap bitmap;
        synchronized (this) {
            long nowNanos = System.nanoTime();
            long waitUntilNanos = nowNanos + TimeUnit.NANOSECONDS.convert(20, TimeUnit.MILLISECONDS);
            while (this.mFreeBitmaps.isEmpty() && nowNanos < waitUntilNanos) {
                try {
                    TimeUnit.NANOSECONDS.timedWait(this, waitUntilNanos - nowNanos);
                    nowNanos = System.nanoTime();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
            if (this.mFreeBitmaps.isEmpty()) {
                bitmap = createNewBitmap();
            } else {
                bitmap = this.mFreeBitmaps.remove(this.mFreeBitmaps.size() - 1);
            }
        }
        return CloseableReference.of(bitmap, this.mResourceReleaserForBitmaps);
    }

    /* access modifiers changed from: package-private */
    public synchronized void releaseBitmapInternal(Bitmap bitmap) {
        this.mFreeBitmaps.add(bitmap);
    }

    private synchronized void schedulePrefetches() {
        boolean keepOnePreceding;
        int i;
        int numToPrefetch;
        int i2 = 1;
        synchronized (this) {
            if (this.mAnimatedDrawableBackend.getFrameInfo(this.mCurrentFrameIndex).disposalMethod == AnimatedDrawableFrameInfo.DisposalMethod.DISPOSE_TO_PREVIOUS) {
                keepOnePreceding = true;
            } else {
                keepOnePreceding = false;
            }
            int i3 = this.mCurrentFrameIndex;
            if (keepOnePreceding) {
                i = 1;
            } else {
                i = 0;
            }
            int startFrame = Math.max(0, i3 - i);
            if (this.mAnimatedDrawableOptions.allowPrefetching) {
                numToPrefetch = 3;
            } else {
                numToPrefetch = 0;
            }
            if (!keepOnePreceding) {
                i2 = 0;
            }
            int numToPrefetch2 = Math.max(numToPrefetch, i2);
            int endFrame = (startFrame + numToPrefetch2) % this.mAnimatedDrawableBackend.getFrameCount();
            cancelFuturesOutsideOfRange(startFrame, endFrame);
            if (!shouldKeepAllFramesInMemory()) {
                this.mBitmapsToKeepCached.setAll(true);
                this.mBitmapsToKeepCached.removeOutsideRange(startFrame, endFrame);
                int frameNumber = startFrame;
                while (true) {
                    if (frameNumber < 0) {
                        break;
                    } else if (this.mCachedBitmaps.get(frameNumber) != null) {
                        this.mBitmapsToKeepCached.set(frameNumber, true);
                        break;
                    } else {
                        frameNumber--;
                    }
                }
                dropBitmapsThatShouldNotBeCached();
            }
            if (this.mAnimatedDrawableOptions.allowPrefetching) {
                doPrefetch(startFrame, numToPrefetch2);
            } else {
                cancelFuturesOutsideOfRange(this.mCurrentFrameIndex, this.mCurrentFrameIndex);
            }
        }
    }

    private static int getDefaultMaxBytes(ActivityManager activityManager) {
        if (activityManager.getMemoryClass() > 32) {
            return 5242880;
        }
        return 3145728;
    }

    private boolean shouldKeepAllFramesInMemory() {
        if (!this.mAnimatedDrawableOptions.forceKeepAllFramesInMemory && this.mApproxKiloBytesToHoldAllFrames >= this.mMaximumKiloBytes) {
            return false;
        }
        return true;
    }

    private synchronized void doPrefetch(int startFrame, int count) {
        for (int i = 0; i < count; i++) {
            final int frameNumber = (startFrame + i) % this.mAnimatedDrawableBackend.getFrameCount();
            boolean hasCached = hasCachedOrPredecodedFrame(frameNumber);
            Task<Object> future = this.mDecodesInFlight.get(frameNumber);
            if (!hasCached && future == null) {
                final Task<Object> newFuture = Task.call(new Callable<Object>() {
                    public Object call() {
                        AnimatedDrawableCachingBackendImpl.this.runPrefetch(frameNumber);
                        return null;
                    }
                }, this.mExecutorService);
                this.mDecodesInFlight.put(frameNumber, newFuture);
                newFuture.continueWith(new Continuation<Object, Object>() {
                    public Object then(Task<Object> task) throws Exception {
                        AnimatedDrawableCachingBackendImpl.this.onFutureFinished(newFuture, frameNumber);
                        return null;
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0017, code lost:
        r1 = r5.mAnimatedDrawableBackend.getPreDecodedFrame(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001d, code lost:
        if (r1 == null) goto L_0x0026;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        maybeCacheRenderedBitmap(r6, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0022, code lost:
        com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0 = obtainBitmapInternal();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r5.mAnimatedImageCompositor.renderFrame(r6, r0.get());
        maybeCacheRenderedBitmap(r6, r0);
        com.facebook.common.logging.FLog.v(TAG, "Prefetch rendered frame %d", (java.lang.Object) java.lang.Integer.valueOf(r6));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0047, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0048, code lost:
        com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004b, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x004c, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0050, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:16:0x001f, B:21:0x002a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void runPrefetch(int r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            com.facebook.imagepipeline.animated.impl.WhatToKeepCachedArray r2 = r5.mBitmapsToKeepCached     // Catch:{ all -> 0x0013 }
            boolean r2 = r2.get(r6)     // Catch:{ all -> 0x0013 }
            if (r2 != 0) goto L_0x000b
            monitor-exit(r5)     // Catch:{ all -> 0x0013 }
        L_0x000a:
            return
        L_0x000b:
            boolean r2 = r5.hasCachedOrPredecodedFrame(r6)     // Catch:{ all -> 0x0013 }
            if (r2 == 0) goto L_0x0016
            monitor-exit(r5)     // Catch:{ all -> 0x0013 }
            goto L_0x000a
        L_0x0013:
            r2 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0013 }
            throw r2
        L_0x0016:
            monitor-exit(r5)     // Catch:{ all -> 0x0013 }
            com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend r2 = r5.mAnimatedDrawableBackend
            com.facebook.common.references.CloseableReference r1 = r2.getPreDecodedFrame(r6)
            if (r1 == 0) goto L_0x0026
            r5.maybeCacheRenderedBitmap(r6, r1)     // Catch:{ all -> 0x0047 }
        L_0x0022:
            com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r1)
            goto L_0x000a
        L_0x0026:
            com.facebook.common.references.CloseableReference r0 = r5.obtainBitmapInternal()     // Catch:{ all -> 0x0047 }
            com.facebook.imagepipeline.animated.impl.AnimatedImageCompositor r3 = r5.mAnimatedImageCompositor     // Catch:{ all -> 0x004c }
            java.lang.Object r2 = r0.get()     // Catch:{ all -> 0x004c }
            android.graphics.Bitmap r2 = (android.graphics.Bitmap) r2     // Catch:{ all -> 0x004c }
            r3.renderFrame(r6, r2)     // Catch:{ all -> 0x004c }
            r5.maybeCacheRenderedBitmap(r6, r0)     // Catch:{ all -> 0x004c }
            java.lang.Class<?> r2 = TAG     // Catch:{ all -> 0x004c }
            java.lang.String r3 = "Prefetch rendered frame %d"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r6)     // Catch:{ all -> 0x004c }
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r2, (java.lang.String) r3, (java.lang.Object) r4)     // Catch:{ all -> 0x004c }
            r0.close()     // Catch:{ all -> 0x0047 }
            goto L_0x0022
        L_0x0047:
            r2 = move-exception
            com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r1)
            throw r2
        L_0x004c:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0047 }
            throw r2     // Catch:{ all -> 0x0047 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImpl.runPrefetch(int):void");
    }

    /* access modifiers changed from: private */
    public synchronized void onFutureFinished(Task<?> future, int frameNumber) {
        int index = this.mDecodesInFlight.indexOfKey(frameNumber);
        if (index >= 0 && this.mDecodesInFlight.valueAt(index) == future) {
            this.mDecodesInFlight.removeAt(index);
            if (future.getError() != null) {
                FLog.v(TAG, (Throwable) future.getError(), "Failed to render frame %d", Integer.valueOf(frameNumber));
            }
        }
    }

    private synchronized void cancelFuturesOutsideOfRange(int startFrame, int endFrame) {
        int index = 0;
        while (index < this.mDecodesInFlight.size()) {
            if (AnimatedDrawableUtil.isOutsideRange(startFrame, endFrame, this.mDecodesInFlight.keyAt(index))) {
                Task valueAt = this.mDecodesInFlight.valueAt(index);
                this.mDecodesInFlight.removeAt(index);
            } else {
                index++;
            }
        }
    }

    private synchronized void dropBitmapsThatShouldNotBeCached() {
        int index = 0;
        while (index < this.mCachedBitmaps.size()) {
            if (!this.mBitmapsToKeepCached.get(this.mCachedBitmaps.keyAt(index))) {
                this.mCachedBitmaps.removeAt(index);
                this.mCachedBitmaps.valueAt(index).close();
            } else {
                index++;
            }
        }
    }

    private synchronized void maybeCacheRenderedBitmap(int frameNumber, CloseableReference<Bitmap> bitmapReference) {
        if (this.mBitmapsToKeepCached.get(frameNumber)) {
            int existingIndex = this.mCachedBitmaps.indexOfKey(frameNumber);
            if (existingIndex >= 0) {
                this.mCachedBitmaps.valueAt(existingIndex).close();
                this.mCachedBitmaps.removeAt(existingIndex);
            }
            this.mCachedBitmaps.put(frameNumber, bitmapReference.clone());
        }
    }

    /* access modifiers changed from: private */
    public synchronized CloseableReference<Bitmap> getCachedOrPredecodedFrame(int frameNumber) {
        CloseableReference<Bitmap> ret;
        ret = CloseableReference.cloneOrNull(this.mCachedBitmaps.get(frameNumber));
        if (ret == null) {
            ret = this.mAnimatedDrawableBackend.getPreDecodedFrame(frameNumber);
        }
        return ret;
    }

    private synchronized boolean hasCachedOrPredecodedFrame(int frameNumber) {
        return this.mCachedBitmaps.get(frameNumber) != null || this.mAnimatedDrawableBackend.hasPreDecodedFrame(frameNumber);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public synchronized Map<Integer, Task<?>> getDecodesInFlight() {
        Map<Integer, Task<?>> map;
        map = new HashMap<>();
        for (int i = 0; i < this.mDecodesInFlight.size(); i++) {
            map.put(Integer.valueOf(this.mDecodesInFlight.keyAt(i)), this.mDecodesInFlight.valueAt(i));
        }
        return map;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public synchronized Set<Integer> getFramesCached() {
        Set<Integer> set;
        set = new HashSet<>();
        for (int i = 0; i < this.mCachedBitmaps.size(); i++) {
            set.add(Integer.valueOf(this.mCachedBitmaps.keyAt(i)));
        }
        return set;
    }
}
