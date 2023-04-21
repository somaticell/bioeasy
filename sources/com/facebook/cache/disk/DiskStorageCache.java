package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.common.disk.DiskTrimmable;
import com.facebook.common.disk.DiskTrimmableRegistry;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.common.statfs.StatFsHelper;
import com.facebook.common.time.Clock;
import com.facebook.common.time.SystemClock;
import com.facebook.common.util.SecureHashUtil;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class DiskStorageCache implements FileCache, DiskTrimmable {
    private static final long FILECACHE_SIZE_UPDATE_PERIOD_MS = TimeUnit.MINUTES.toMillis(30);
    private static final long FUTURE_TIMESTAMP_THRESHOLD_MS = TimeUnit.HOURS.toMillis(2);
    public static final int START_OF_VERSIONING = 1;
    private static final Class<?> TAG = DiskStorageCache.class;
    private static final double TRIMMING_LOWER_BOUND = 0.02d;
    private static final long UNINITIALIZED = -1;
    private final CacheErrorLogger mCacheErrorLogger;
    private final CacheEventListener mCacheEventListener;
    @GuardedBy("mLock")
    private long mCacheSizeLastUpdateTime;
    private long mCacheSizeLimit;
    private final long mCacheSizeLimitMinimum;
    private final CacheStats mCacheStats;
    private final Clock mClock;
    private final long mDefaultCacheSizeLimit;
    private final EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
    private final Object mLock = new Object();
    private final long mLowDiskSpaceCacheSizeLimit;
    private final StatFsHelper mStatFsHelper;
    private final DiskStorageSupplier mStorageSupplier;

    @VisibleForTesting
    static class CacheStats {
        private long mCount = -1;
        private boolean mInitialized = false;
        private long mSize = -1;

        CacheStats() {
        }

        public synchronized boolean isInitialized() {
            return this.mInitialized;
        }

        public synchronized void reset() {
            this.mInitialized = false;
            this.mCount = -1;
            this.mSize = -1;
        }

        public synchronized void set(long size, long count) {
            this.mCount = count;
            this.mSize = size;
            this.mInitialized = true;
        }

        public synchronized void increment(long sizeIncrement, long countIncrement) {
            if (this.mInitialized) {
                this.mSize += sizeIncrement;
                this.mCount += countIncrement;
            }
        }

        public synchronized long getSize() {
            return this.mSize;
        }

        public synchronized long getCount() {
            return this.mCount;
        }
    }

    public static class Params {
        public final long mCacheSizeLimitMinimum;
        public final long mDefaultCacheSizeLimit;
        public final long mLowDiskSpaceCacheSizeLimit;

        public Params(long cacheSizeLimitMinimum, long lowDiskSpaceCacheSizeLimit, long defaultCacheSizeLimit) {
            this.mCacheSizeLimitMinimum = cacheSizeLimitMinimum;
            this.mLowDiskSpaceCacheSizeLimit = lowDiskSpaceCacheSizeLimit;
            this.mDefaultCacheSizeLimit = defaultCacheSizeLimit;
        }
    }

    public DiskStorageCache(DiskStorageSupplier diskStorageSupplier, EntryEvictionComparatorSupplier entryEvictionComparatorSupplier, Params params, CacheEventListener cacheEventListener, CacheErrorLogger cacheErrorLogger, @Nullable DiskTrimmableRegistry diskTrimmableRegistry) {
        this.mLowDiskSpaceCacheSizeLimit = params.mLowDiskSpaceCacheSizeLimit;
        this.mDefaultCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mStatFsHelper = StatFsHelper.getInstance();
        this.mStorageSupplier = diskStorageSupplier;
        this.mEntryEvictionComparatorSupplier = entryEvictionComparatorSupplier;
        this.mCacheSizeLastUpdateTime = -1;
        this.mCacheEventListener = cacheEventListener;
        this.mCacheSizeLimitMinimum = params.mCacheSizeLimitMinimum;
        this.mCacheErrorLogger = cacheErrorLogger;
        this.mCacheStats = new CacheStats();
        if (diskTrimmableRegistry != null) {
            diskTrimmableRegistry.registerDiskTrimmable(this);
        }
        this.mClock = SystemClock.get();
    }

    public DiskStorage.DiskDumpInfo getDumpInfo() throws IOException {
        return this.mStorageSupplier.get().getDumpInfo();
    }

    public boolean isEnabled() {
        try {
            return this.mStorageSupplier.get().isEnabled();
        } catch (IOException e) {
            return false;
        }
    }

    public BinaryResource getResource(CacheKey key) {
        FileBinaryResource resource;
        try {
            synchronized (this.mLock) {
                resource = this.mStorageSupplier.get().getResource(getResourceId(key), key);
                if (resource == null) {
                    this.mCacheEventListener.onMiss();
                } else {
                    this.mCacheEventListener.onHit();
                }
            }
            return resource;
        } catch (IOException ioe) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.GENERIC_IO, TAG, "getResource", ioe);
            this.mCacheEventListener.onReadException();
            return null;
        }
    }

    public boolean probe(CacheKey key) {
        boolean z;
        try {
            synchronized (this.mLock) {
                z = this.mStorageSupplier.get().touch(getResourceId(key), key);
            }
            return z;
        } catch (IOException e) {
            this.mCacheEventListener.onReadException();
            return false;
        }
    }

    private FileBinaryResource createTemporaryResource(String resourceId, CacheKey key) throws IOException {
        maybeEvictFilesInCacheDir();
        return this.mStorageSupplier.get().createTemporary(resourceId, key);
    }

    private void deleteTemporaryResource(FileBinaryResource fileResource) {
        File tempFile = fileResource.getFile();
        if (tempFile.exists()) {
            FLog.e(TAG, "Temp file still on disk: %s ", tempFile);
            if (!tempFile.delete()) {
                FLog.e(TAG, "Failed to delete temp file: %s", tempFile);
            }
        }
    }

    private FileBinaryResource commitResource(String resourceId, CacheKey key, FileBinaryResource temporary) throws IOException {
        FileBinaryResource resource;
        synchronized (this.mLock) {
            resource = this.mStorageSupplier.get().commit(resourceId, temporary, key);
            this.mCacheStats.increment(resource.size(), 1);
        }
        return resource;
    }

    public BinaryResource insert(CacheKey key, WriterCallback callback) throws IOException {
        FileBinaryResource temporary;
        this.mCacheEventListener.onWriteAttempt();
        String resourceId = getResourceId(key);
        try {
            temporary = createTemporaryResource(resourceId, key);
            this.mStorageSupplier.get().updateResource(resourceId, temporary, callback, key);
            FileBinaryResource commitResource = commitResource(resourceId, key, temporary);
            deleteTemporaryResource(temporary);
            return commitResource;
        } catch (IOException ioe) {
            this.mCacheEventListener.onWriteException();
            FLog.e(TAG, "Failed inserting a file into the cache", (Throwable) ioe);
            throw ioe;
        } catch (Throwable th) {
            deleteTemporaryResource(temporary);
            throw th;
        }
    }

    public void remove(CacheKey key) {
        synchronized (this.mLock) {
            try {
                this.mStorageSupplier.get().remove(getResourceId(key));
            } catch (IOException e) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.DELETE_FILE, TAG, "delete: " + e.getMessage(), e);
            }
        }
    }

    public long clearOldEntries(long cacheExpirationMs) {
        long oldestRemainingEntryAgeMs = 0;
        synchronized (this.mLock) {
            try {
                long now = this.mClock.now();
                DiskStorage storage = this.mStorageSupplier.get();
                int itemsRemovedCount = 0;
                long itemsRemovedSize = 0;
                for (DiskStorage.Entry entry : storage.getEntries()) {
                    long entryAgeMs = Math.max(1, Math.abs(now - entry.getTimestamp()));
                    if (entryAgeMs >= cacheExpirationMs) {
                        long entryRemovedSize = storage.remove(entry);
                        if (entryRemovedSize > 0) {
                            itemsRemovedCount++;
                            itemsRemovedSize += entryRemovedSize;
                        }
                    } else {
                        oldestRemainingEntryAgeMs = Math.max(oldestRemainingEntryAgeMs, entryAgeMs);
                    }
                }
                storage.purgeUnexpectedResources();
                if (itemsRemovedCount > 0) {
                    maybeUpdateFileCacheSize();
                    this.mCacheStats.increment(-itemsRemovedSize, (long) (-itemsRemovedCount));
                    reportEviction(CacheEventListener.EvictionReason.CONTENT_STALE, itemsRemovedCount, itemsRemovedSize);
                }
            } catch (IOException ioe) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "clearOldEntries: " + ioe.getMessage(), ioe);
            }
        }
        return oldestRemainingEntryAgeMs;
    }

    private void reportEviction(CacheEventListener.EvictionReason reason, int itemCount, long itemSize) {
        this.mCacheEventListener.onEviction(reason, itemCount, itemSize);
    }

    private void maybeEvictFilesInCacheDir() throws IOException {
        synchronized (this.mLock) {
            boolean calculatedRightNow = maybeUpdateFileCacheSize();
            updateFileCacheSizeLimit();
            long cacheSize = this.mCacheStats.getSize();
            if (cacheSize > this.mCacheSizeLimit && !calculatedRightNow) {
                this.mCacheStats.reset();
                maybeUpdateFileCacheSize();
            }
            if (cacheSize > this.mCacheSizeLimit) {
                evictAboveSize((this.mCacheSizeLimit * 9) / 10, CacheEventListener.EvictionReason.CACHE_FULL);
            }
        }
    }

    @GuardedBy("mLock")
    private void evictAboveSize(long desiredSize, CacheEventListener.EvictionReason reason) throws IOException {
        DiskStorage storage = this.mStorageSupplier.get();
        try {
            Collection<DiskStorage.Entry> entries = getSortedEntries(storage.getEntries());
            long deleteSize = this.mCacheStats.getSize() - desiredSize;
            int itemCount = 0;
            long sumItemSizes = 0;
            for (DiskStorage.Entry entry : entries) {
                if (sumItemSizes > deleteSize) {
                    break;
                }
                long deletedSize = storage.remove(entry);
                if (deletedSize > 0) {
                    itemCount++;
                    sumItemSizes += deletedSize;
                }
            }
            this.mCacheStats.increment(-sumItemSizes, (long) (-itemCount));
            storage.purgeUnexpectedResources();
            reportEviction(reason, itemCount, sumItemSizes);
        } catch (IOException ioe) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "evictAboveSize: " + ioe.getMessage(), ioe);
            throw ioe;
        }
    }

    private Collection<DiskStorage.Entry> getSortedEntries(Collection<DiskStorage.Entry> allEntries) {
        long threshold = this.mClock.now() + FUTURE_TIMESTAMP_THRESHOLD_MS;
        ArrayList<DiskStorage.Entry> sortedList = new ArrayList<>(allEntries.size());
        ArrayList<DiskStorage.Entry> listToSort = new ArrayList<>(allEntries.size());
        for (DiskStorage.Entry entry : allEntries) {
            if (entry.getTimestamp() > threshold) {
                sortedList.add(entry);
            } else {
                listToSort.add(entry);
            }
        }
        Collections.sort(listToSort, this.mEntryEvictionComparatorSupplier.get());
        sortedList.addAll(listToSort);
        return sortedList;
    }

    @GuardedBy("mLock")
    private void updateFileCacheSizeLimit() {
        if (this.mStatFsHelper.testLowDiskSpace(StatFsHelper.StorageType.INTERNAL, this.mDefaultCacheSizeLimit - this.mCacheStats.getSize())) {
            this.mCacheSizeLimit = this.mLowDiskSpaceCacheSizeLimit;
        } else {
            this.mCacheSizeLimit = this.mDefaultCacheSizeLimit;
        }
    }

    public long getSize() {
        return this.mCacheStats.getSize();
    }

    public void clearAll() {
        synchronized (this.mLock) {
            try {
                this.mStorageSupplier.get().clearAll();
            } catch (IOException ioe) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "clearAll: " + ioe.getMessage(), ioe);
            }
            this.mCacheStats.reset();
        }
    }

    public boolean hasKey(CacheKey key) {
        try {
            return this.mStorageSupplier.get().contains(getResourceId(key), key);
        } catch (IOException e) {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToMinimum() {
        /*
            r12 = this;
            r8 = 0
            java.lang.Object r5 = r12.mLock
            monitor-enter(r5)
            r12.maybeUpdateFileCacheSize()     // Catch:{ all -> 0x0037 }
            com.facebook.cache.disk.DiskStorageCache$CacheStats r4 = r12.mCacheStats     // Catch:{ all -> 0x0037 }
            long r0 = r4.getSize()     // Catch:{ all -> 0x0037 }
            long r6 = r12.mCacheSizeLimitMinimum     // Catch:{ all -> 0x0037 }
            int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x001e
            int r4 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x001e
            long r6 = r12.mCacheSizeLimitMinimum     // Catch:{ all -> 0x0037 }
            int r4 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r4 >= 0) goto L_0x0020
        L_0x001e:
            monitor-exit(r5)     // Catch:{ all -> 0x0037 }
        L_0x001f:
            return
        L_0x0020:
            r6 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            long r8 = r12.mCacheSizeLimitMinimum     // Catch:{ all -> 0x0037 }
            double r8 = (double) r8     // Catch:{ all -> 0x0037 }
            double r10 = (double) r0     // Catch:{ all -> 0x0037 }
            double r8 = r8 / r10
            double r2 = r6 - r8
            r6 = 4581421828931458171(0x3f947ae147ae147b, double:0.02)
            int r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x0035
            r12.trimBy(r2)     // Catch:{ all -> 0x0037 }
        L_0x0035:
            monitor-exit(r5)     // Catch:{ all -> 0x0037 }
            goto L_0x001f
        L_0x0037:
            r4 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0037 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.cache.disk.DiskStorageCache.trimToMinimum():void");
    }

    public void trimToNothing() {
        clearAll();
    }

    private void trimBy(double trimRatio) {
        synchronized (this.mLock) {
            try {
                this.mCacheStats.reset();
                maybeUpdateFileCacheSize();
                long cacheSize = this.mCacheStats.getSize();
                evictAboveSize(cacheSize - ((long) (((double) cacheSize) * trimRatio)), CacheEventListener.EvictionReason.CACHE_MANAGER_TRIMMED);
            } catch (IOException ioe) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "trimBy: " + ioe.getMessage(), ioe);
            }
        }
    }

    @GuardedBy("mLock")
    private boolean maybeUpdateFileCacheSize() {
        long now = android.os.SystemClock.elapsedRealtime();
        if (this.mCacheStats.isInitialized() && this.mCacheSizeLastUpdateTime != -1 && now - this.mCacheSizeLastUpdateTime <= FILECACHE_SIZE_UPDATE_PERIOD_MS) {
            return false;
        }
        calcFileCacheSize();
        this.mCacheSizeLastUpdateTime = now;
        return true;
    }

    @GuardedBy("mLock")
    private void calcFileCacheSize() {
        long size = 0;
        int count = 0;
        boolean foundFutureTimestamp = false;
        int numFutureFiles = 0;
        int sizeFutureFiles = 0;
        long maxTimeDelta = -1;
        long now = this.mClock.now();
        long timeThreshold = now + FUTURE_TIMESTAMP_THRESHOLD_MS;
        try {
            for (DiskStorage.Entry entry : this.mStorageSupplier.get().getEntries()) {
                count++;
                size += entry.getSize();
                if (entry.getTimestamp() > timeThreshold) {
                    foundFutureTimestamp = true;
                    numFutureFiles++;
                    sizeFutureFiles = (int) (((long) sizeFutureFiles) + entry.getSize());
                    maxTimeDelta = Math.max(entry.getTimestamp() - now, maxTimeDelta);
                }
            }
            if (foundFutureTimestamp) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.READ_INVALID_ENTRY, TAG, "Future timestamp found in " + numFutureFiles + " files , with a total size of " + sizeFutureFiles + " bytes, and a maximum time delta of " + maxTimeDelta + "ms", (Throwable) null);
            }
            this.mCacheStats.set(size, (long) count);
        } catch (IOException ioe) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.GENERIC_IO, TAG, "calcFileCacheSize: " + ioe.getMessage(), ioe);
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public String getResourceId(CacheKey key) {
        try {
            return SecureHashUtil.makeSHA1HashBase64(key.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
