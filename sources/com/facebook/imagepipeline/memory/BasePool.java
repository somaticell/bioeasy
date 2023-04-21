package com.facebook.imagepipeline.memory;

import android.annotation.SuppressLint;
import android.support.v7.widget.ActivityChooserView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;

public abstract class BasePool<V> implements Pool<V> {
    private final Class<?> TAG = getClass();
    private boolean mAllowNewBuckets;
    @VisibleForTesting
    final SparseArray<Bucket<V>> mBuckets;
    @GuardedBy("this")
    @VisibleForTesting
    final Counter mFree;
    @VisibleForTesting
    final Set<V> mInUseValues;
    final MemoryTrimmableRegistry mMemoryTrimmableRegistry;
    final PoolParams mPoolParams;
    private final PoolStatsTracker mPoolStatsTracker;
    @GuardedBy("this")
    @VisibleForTesting
    final Counter mUsed;

    /* access modifiers changed from: protected */
    public abstract V alloc(int i);

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public abstract void free(V v);

    /* access modifiers changed from: protected */
    public abstract int getBucketedSize(int i);

    /* access modifiers changed from: protected */
    public abstract int getBucketedSizeForValue(V v);

    /* access modifiers changed from: protected */
    public abstract int getSizeInBytes(int i);

    public BasePool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
        this.mMemoryTrimmableRegistry = (MemoryTrimmableRegistry) Preconditions.checkNotNull(memoryTrimmableRegistry);
        this.mPoolParams = (PoolParams) Preconditions.checkNotNull(poolParams);
        this.mPoolStatsTracker = (PoolStatsTracker) Preconditions.checkNotNull(poolStatsTracker);
        this.mBuckets = new SparseArray<>();
        initBuckets(new SparseIntArray(0));
        this.mInUseValues = Sets.newIdentityHashSet();
        this.mFree = new Counter();
        this.mUsed = new Counter();
    }

    /* access modifiers changed from: protected */
    public void initialize() {
        this.mMemoryTrimmableRegistry.registerMemoryTrimmable(this);
        this.mPoolStatsTracker.setBasePool(this);
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    public V get(int r10) {
        /*
            r9 = this;
            r9.ensurePoolSizeInvariant()
            int r1 = r9.getBucketedSize(r10)
            r3 = -1
            monitor-enter(r9)
            com.facebook.imagepipeline.memory.Bucket r0 = r9.getBucket(r1)     // Catch:{ all -> 0x0070 }
            if (r0 == 0) goto L_0x0054
            java.lang.Object r4 = r0.get()     // Catch:{ all -> 0x0070 }
            if (r4 == 0) goto L_0x0054
            java.util.Set<V> r5 = r9.mInUseValues     // Catch:{ all -> 0x0070 }
            boolean r5 = r5.add(r4)     // Catch:{ all -> 0x0070 }
            com.facebook.common.internal.Preconditions.checkState(r5)     // Catch:{ all -> 0x0070 }
            int r1 = r9.getBucketedSizeForValue(r4)     // Catch:{ all -> 0x0070 }
            int r3 = r9.getSizeInBytes(r1)     // Catch:{ all -> 0x0070 }
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r9.mUsed     // Catch:{ all -> 0x0070 }
            r5.increment(r3)     // Catch:{ all -> 0x0070 }
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r9.mFree     // Catch:{ all -> 0x0070 }
            r5.decrement(r3)     // Catch:{ all -> 0x0070 }
            com.facebook.imagepipeline.memory.PoolStatsTracker r5 = r9.mPoolStatsTracker     // Catch:{ all -> 0x0070 }
            r5.onValueReuse(r3)     // Catch:{ all -> 0x0070 }
            r9.logStats()     // Catch:{ all -> 0x0070 }
            r5 = 2
            boolean r5 = com.facebook.common.logging.FLog.isLoggable(r5)     // Catch:{ all -> 0x0070 }
            if (r5 == 0) goto L_0x0052
            java.lang.Class<?> r5 = r9.TAG     // Catch:{ all -> 0x0070 }
            java.lang.String r6 = "get (reuse) (object, size) = (%x, %s)"
            int r7 = java.lang.System.identityHashCode(r4)     // Catch:{ all -> 0x0070 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0070 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x0070 }
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r5, (java.lang.String) r6, (java.lang.Object) r7, (java.lang.Object) r8)     // Catch:{ all -> 0x0070 }
        L_0x0052:
            monitor-exit(r9)     // Catch:{ all -> 0x0070 }
        L_0x0053:
            return r4
        L_0x0054:
            int r3 = r9.getSizeInBytes(r1)     // Catch:{ all -> 0x0070 }
            boolean r5 = r9.canAllocate(r3)     // Catch:{ all -> 0x0070 }
            if (r5 != 0) goto L_0x0073
            com.facebook.imagepipeline.memory.BasePool$PoolSizeViolationException r5 = new com.facebook.imagepipeline.memory.BasePool$PoolSizeViolationException     // Catch:{ all -> 0x0070 }
            com.facebook.imagepipeline.memory.PoolParams r6 = r9.mPoolParams     // Catch:{ all -> 0x0070 }
            int r6 = r6.maxSizeHardCap     // Catch:{ all -> 0x0070 }
            com.facebook.imagepipeline.memory.BasePool$Counter r7 = r9.mUsed     // Catch:{ all -> 0x0070 }
            int r7 = r7.mNumBytes     // Catch:{ all -> 0x0070 }
            com.facebook.imagepipeline.memory.BasePool$Counter r8 = r9.mFree     // Catch:{ all -> 0x0070 }
            int r8 = r8.mNumBytes     // Catch:{ all -> 0x0070 }
            r5.<init>(r6, r7, r8, r3)     // Catch:{ all -> 0x0070 }
            throw r5     // Catch:{ all -> 0x0070 }
        L_0x0070:
            r5 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0070 }
            throw r5
        L_0x0073:
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r9.mUsed     // Catch:{ all -> 0x0070 }
            r5.increment(r3)     // Catch:{ all -> 0x0070 }
            if (r0 == 0) goto L_0x007d
            r0.incrementInUseCount()     // Catch:{ all -> 0x0070 }
        L_0x007d:
            monitor-exit(r9)     // Catch:{ all -> 0x0070 }
            r4 = 0
            java.lang.Object r4 = r9.alloc(r1)     // Catch:{ Throwable -> 0x00b7 }
        L_0x0083:
            monitor-enter(r9)
            java.util.Set<V> r5 = r9.mInUseValues     // Catch:{ all -> 0x00b4 }
            boolean r5 = r5.add(r4)     // Catch:{ all -> 0x00b4 }
            com.facebook.common.internal.Preconditions.checkState(r5)     // Catch:{ all -> 0x00b4 }
            r9.trimToSoftCap()     // Catch:{ all -> 0x00b4 }
            com.facebook.imagepipeline.memory.PoolStatsTracker r5 = r9.mPoolStatsTracker     // Catch:{ all -> 0x00b4 }
            r5.onAlloc(r3)     // Catch:{ all -> 0x00b4 }
            r9.logStats()     // Catch:{ all -> 0x00b4 }
            r5 = 2
            boolean r5 = com.facebook.common.logging.FLog.isLoggable(r5)     // Catch:{ all -> 0x00b4 }
            if (r5 == 0) goto L_0x00b2
            java.lang.Class<?> r5 = r9.TAG     // Catch:{ all -> 0x00b4 }
            java.lang.String r6 = "get (alloc) (object, size) = (%x, %s)"
            int r7 = java.lang.System.identityHashCode(r4)     // Catch:{ all -> 0x00b4 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x00b4 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r1)     // Catch:{ all -> 0x00b4 }
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r5, (java.lang.String) r6, (java.lang.Object) r7, (java.lang.Object) r8)     // Catch:{ all -> 0x00b4 }
        L_0x00b2:
            monitor-exit(r9)     // Catch:{ all -> 0x00b4 }
            goto L_0x0053
        L_0x00b4:
            r5 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x00b4 }
            throw r5
        L_0x00b7:
            r2 = move-exception
            monitor-enter(r9)
            com.facebook.imagepipeline.memory.BasePool$Counter r5 = r9.mUsed     // Catch:{ all -> 0x00cc }
            r5.decrement(r3)     // Catch:{ all -> 0x00cc }
            com.facebook.imagepipeline.memory.Bucket r0 = r9.getBucket(r1)     // Catch:{ all -> 0x00cc }
            if (r0 == 0) goto L_0x00c7
            r0.decrementInUseCount()     // Catch:{ all -> 0x00cc }
        L_0x00c7:
            monitor-exit(r9)     // Catch:{ all -> 0x00cc }
            com.facebook.common.internal.Throwables.propagateIfPossible(r2)
            goto L_0x0083
        L_0x00cc:
            r5 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x00cc }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.memory.BasePool.get(int):java.lang.Object");
    }

    public void release(V value) {
        Preconditions.checkNotNull(value);
        int bucketedSize = getBucketedSizeForValue(value);
        int sizeInBytes = getSizeInBytes(bucketedSize);
        synchronized (this) {
            Bucket<V> bucket = getBucket(bucketedSize);
            if (!this.mInUseValues.remove(value)) {
                FLog.e(this.TAG, "release (free, value unrecognized) (object, size) = (%x, %s)", Integer.valueOf(System.identityHashCode(value)), Integer.valueOf(bucketedSize));
                free(value);
                this.mPoolStatsTracker.onFree(sizeInBytes);
            } else if (bucket == null || bucket.isMaxLengthExceeded() || isMaxSizeSoftCapExceeded() || !isReusable(value)) {
                if (bucket != null) {
                    bucket.decrementInUseCount();
                }
                if (FLog.isLoggable(2)) {
                    FLog.v(this.TAG, "release (free) (object, size) = (%x, %s)", (Object) Integer.valueOf(System.identityHashCode(value)), (Object) Integer.valueOf(bucketedSize));
                }
                free(value);
                this.mUsed.decrement(sizeInBytes);
                this.mPoolStatsTracker.onFree(sizeInBytes);
            } else {
                bucket.release(value);
                this.mFree.increment(sizeInBytes);
                this.mUsed.decrement(sizeInBytes);
                this.mPoolStatsTracker.onValueRelease(sizeInBytes);
                if (FLog.isLoggable(2)) {
                    FLog.v(this.TAG, "release (reuse) (object, size) = (%x, %s)", (Object) Integer.valueOf(System.identityHashCode(value)), (Object) Integer.valueOf(bucketedSize));
                }
            }
            logStats();
        }
    }

    public void trim(MemoryTrimType memoryTrimType) {
        trimToNothing();
    }

    /* access modifiers changed from: protected */
    public void onParamsChanged() {
    }

    /* access modifiers changed from: protected */
    public boolean isReusable(V value) {
        Preconditions.checkNotNull(value);
        return true;
    }

    private synchronized void ensurePoolSizeInvariant() {
        Preconditions.checkState(!isMaxSizeSoftCapExceeded() || this.mFree.mNumBytes == 0);
    }

    private synchronized void initBuckets(SparseIntArray inUseCounts) {
        Preconditions.checkNotNull(inUseCounts);
        this.mBuckets.clear();
        SparseIntArray bucketSizes = this.mPoolParams.bucketSizes;
        if (bucketSizes != null) {
            for (int i = 0; i < bucketSizes.size(); i++) {
                int bucketSize = bucketSizes.keyAt(i);
                this.mBuckets.put(bucketSize, new Bucket(getSizeInBytes(bucketSize), bucketSizes.valueAt(i), inUseCounts.get(bucketSize, 0)));
            }
            this.mAllowNewBuckets = false;
        } else {
            this.mAllowNewBuckets = true;
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void trimToNothing() {
        List<Bucket<V>> bucketsToTrim = new ArrayList<>(this.mBuckets.size());
        SparseIntArray inUseCounts = new SparseIntArray();
        synchronized (this) {
            for (int i = 0; i < this.mBuckets.size(); i++) {
                Bucket<V> bucket = this.mBuckets.valueAt(i);
                if (bucket.getFreeListSize() > 0) {
                    bucketsToTrim.add(bucket);
                }
                inUseCounts.put(this.mBuckets.keyAt(i), bucket.getInUseCount());
            }
            initBuckets(inUseCounts);
            this.mFree.reset();
            logStats();
        }
        onParamsChanged();
        for (int i2 = 0; i2 < bucketsToTrim.size(); i2++) {
            Bucket<V> bucket2 = bucketsToTrim.get(i2);
            while (true) {
                V item = bucket2.pop();
                if (item == null) {
                    break;
                }
                free(item);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public synchronized void trimToSoftCap() {
        if (isMaxSizeSoftCapExceeded()) {
            trimToSize(this.mPoolParams.maxSizeSoftCap);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public synchronized void trimToSize(int targetSize) {
        int bytesToFree = Math.min((this.mUsed.mNumBytes + this.mFree.mNumBytes) - targetSize, this.mFree.mNumBytes);
        if (bytesToFree > 0) {
            if (FLog.isLoggable(2)) {
                FLog.v(this.TAG, "trimToSize: TargetSize = %d; Initial Size = %d; Bytes to free = %d", (Object) Integer.valueOf(targetSize), (Object) Integer.valueOf(this.mUsed.mNumBytes + this.mFree.mNumBytes), (Object) Integer.valueOf(bytesToFree));
            }
            logStats();
            for (int i = 0; i < this.mBuckets.size() && bytesToFree > 0; i++) {
                Bucket<V> bucket = this.mBuckets.valueAt(i);
                while (bytesToFree > 0) {
                    V value = bucket.pop();
                    if (value == null) {
                        break;
                    }
                    free(value);
                    bytesToFree -= bucket.mItemSize;
                    this.mFree.decrement(bucket.mItemSize);
                }
            }
            logStats();
            if (FLog.isLoggable(2)) {
                FLog.v(this.TAG, "trimToSize: TargetSize = %d; Final Size = %d", (Object) Integer.valueOf(targetSize), (Object) Integer.valueOf(this.mUsed.mNumBytes + this.mFree.mNumBytes));
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public synchronized Bucket<V> getBucket(int bucketedSize) {
        Bucket<V> newBucket;
        Bucket<V> bucket = this.mBuckets.get(bucketedSize);
        if (bucket != null || !this.mAllowNewBuckets) {
            newBucket = bucket;
        } else {
            if (FLog.isLoggable(2)) {
                FLog.v(this.TAG, "creating new bucket %s", (Object) Integer.valueOf(bucketedSize));
            }
            newBucket = newBucket(bucketedSize);
            this.mBuckets.put(bucketedSize, newBucket);
        }
        return newBucket;
    }

    /* access modifiers changed from: package-private */
    public Bucket<V> newBucket(int bucketedSize) {
        return new Bucket<>(getSizeInBytes(bucketedSize), ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, 0);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public synchronized boolean isMaxSizeSoftCapExceeded() {
        boolean isMaxSizeSoftCapExceeded;
        isMaxSizeSoftCapExceeded = this.mUsed.mNumBytes + this.mFree.mNumBytes > this.mPoolParams.maxSizeSoftCap;
        if (isMaxSizeSoftCapExceeded) {
            this.mPoolStatsTracker.onSoftCapReached();
        }
        return isMaxSizeSoftCapExceeded;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public synchronized boolean canAllocate(int sizeInBytes) {
        boolean z = false;
        synchronized (this) {
            int hardCap = this.mPoolParams.maxSizeHardCap;
            if (sizeInBytes > hardCap - this.mUsed.mNumBytes) {
                this.mPoolStatsTracker.onHardCapReached();
            } else {
                int softCap = this.mPoolParams.maxSizeSoftCap;
                if (sizeInBytes > softCap - (this.mUsed.mNumBytes + this.mFree.mNumBytes)) {
                    trimToSize(softCap - sizeInBytes);
                }
                if (sizeInBytes > hardCap - (this.mUsed.mNumBytes + this.mFree.mNumBytes)) {
                    this.mPoolStatsTracker.onHardCapReached();
                } else {
                    z = true;
                }
            }
        }
        return z;
    }

    @SuppressLint({"InvalidAccessToGuardedField"})
    private void logStats() {
        if (FLog.isLoggable(2)) {
            FLog.v(this.TAG, "Used = (%d, %d); Free = (%d, %d)", (Object) Integer.valueOf(this.mUsed.mCount), (Object) Integer.valueOf(this.mUsed.mNumBytes), (Object) Integer.valueOf(this.mFree.mCount), (Object) Integer.valueOf(this.mFree.mNumBytes));
        }
    }

    public synchronized Map<String, Integer> getStats() {
        Map<String, Integer> stats;
        stats = new HashMap<>();
        for (int i = 0; i < this.mBuckets.size(); i++) {
            stats.put(PoolStatsTracker.BUCKETS_USED_PREFIX + getSizeInBytes(this.mBuckets.keyAt(i)), Integer.valueOf(this.mBuckets.valueAt(i).getInUseCount()));
        }
        stats.put(PoolStatsTracker.SOFT_CAP, Integer.valueOf(this.mPoolParams.maxSizeSoftCap));
        stats.put(PoolStatsTracker.HARD_CAP, Integer.valueOf(this.mPoolParams.maxSizeHardCap));
        stats.put(PoolStatsTracker.USED_COUNT, Integer.valueOf(this.mUsed.mCount));
        stats.put(PoolStatsTracker.USED_BYTES, Integer.valueOf(this.mUsed.mNumBytes));
        stats.put(PoolStatsTracker.FREE_COUNT, Integer.valueOf(this.mFree.mCount));
        stats.put(PoolStatsTracker.FREE_BYTES, Integer.valueOf(this.mFree.mNumBytes));
        return stats;
    }

    @VisibleForTesting
    @NotThreadSafe
    static class Counter {
        private static final String TAG = "com.facebook.imagepipeline.common.BasePool.Counter";
        int mCount;
        int mNumBytes;

        Counter() {
        }

        public void increment(int numBytes) {
            this.mCount++;
            this.mNumBytes += numBytes;
        }

        public void decrement(int numBytes) {
            if (this.mNumBytes < numBytes || this.mCount <= 0) {
                FLog.wtf(TAG, "Unexpected decrement of %d. Current numBytes = %d, count = %d", Integer.valueOf(numBytes), Integer.valueOf(this.mNumBytes), Integer.valueOf(this.mCount));
                return;
            }
            this.mCount--;
            this.mNumBytes -= numBytes;
        }

        public void reset() {
            this.mCount = 0;
            this.mNumBytes = 0;
        }
    }

    public static class InvalidValueException extends RuntimeException {
        public InvalidValueException(Object value) {
            super("Invalid value: " + value.toString());
        }
    }

    public static class InvalidSizeException extends RuntimeException {
        public InvalidSizeException(Object size) {
            super("Invalid size: " + size.toString());
        }
    }

    public static class SizeTooLargeException extends InvalidSizeException {
        public SizeTooLargeException(Object size) {
            super(size);
        }
    }

    public static class PoolSizeViolationException extends RuntimeException {
        public PoolSizeViolationException(int hardCap, int usedBytes, int freeBytes, int allocSize) {
            super("Pool hard cap violation? Hard cap = " + hardCap + " Used size = " + usedBytes + " Free size = " + freeBytes + " Request size = " + allocSize);
        }
    }
}
