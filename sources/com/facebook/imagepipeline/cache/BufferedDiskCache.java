package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteStreams;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class BufferedDiskCache {
    /* access modifiers changed from: private */
    public static final Class<?> TAG = BufferedDiskCache.class;
    /* access modifiers changed from: private */
    public final FileCache mFileCache;
    /* access modifiers changed from: private */
    public final ImageCacheStatsTracker mImageCacheStatsTracker;
    private final PooledByteBufferFactory mPooledByteBufferFactory;
    /* access modifiers changed from: private */
    public final PooledByteStreams mPooledByteStreams;
    private final Executor mReadExecutor;
    /* access modifiers changed from: private */
    public final StagingArea mStagingArea = StagingArea.getInstance();
    private final Executor mWriteExecutor;

    public BufferedDiskCache(FileCache fileCache, PooledByteBufferFactory pooledByteBufferFactory, PooledByteStreams pooledByteStreams, Executor readExecutor, Executor writeExecutor, ImageCacheStatsTracker imageCacheStatsTracker) {
        this.mFileCache = fileCache;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mPooledByteStreams = pooledByteStreams;
        this.mReadExecutor = readExecutor;
        this.mWriteExecutor = writeExecutor;
        this.mImageCacheStatsTracker = imageCacheStatsTracker;
    }

    public Task<Boolean> contains(final CacheKey key) {
        Preconditions.checkNotNull(key);
        EncodedImage pinnedImage = this.mStagingArea.get(key);
        if (pinnedImage != null) {
            pinnedImage.close();
            FLog.v(TAG, "Found image for %s in staging area", (Object) key.toString());
            this.mImageCacheStatsTracker.onStagingAreaHit();
            return Task.forResult(true);
        }
        try {
            return Task.call(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    EncodedImage result = BufferedDiskCache.this.mStagingArea.get(key);
                    if (result != null) {
                        result.close();
                        FLog.v((Class<?>) BufferedDiskCache.TAG, "Found image for %s in staging area", (Object) key.toString());
                        BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaHit();
                        return true;
                    }
                    FLog.v((Class<?>) BufferedDiskCache.TAG, "Did not find image for %s in staging area", (Object) key.toString());
                    BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaMiss();
                    try {
                        return Boolean.valueOf(BufferedDiskCache.this.mFileCache.hasKey(key));
                    } catch (Exception e) {
                        return false;
                    }
                }
            }, this.mReadExecutor);
        } catch (Exception exception) {
            FLog.w(TAG, (Throwable) exception, "Failed to schedule disk-cache read for %s", key.toString());
            return Task.forError(exception);
        }
    }

    public Task<EncodedImage> get(final CacheKey key, final AtomicBoolean isCancelled) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(isCancelled);
        EncodedImage pinnedImage = this.mStagingArea.get(key);
        if (pinnedImage != null) {
            FLog.v(TAG, "Found image for %s in staging area", (Object) key.toString());
            this.mImageCacheStatsTracker.onStagingAreaHit();
            return Task.forResult(pinnedImage);
        }
        try {
            return Task.call(new Callable<EncodedImage>() {
                public EncodedImage call() throws Exception {
                    CloseableReference<PooledByteBuffer> ref;
                    if (isCancelled.get()) {
                        throw new CancellationException();
                    }
                    EncodedImage result = BufferedDiskCache.this.mStagingArea.get(key);
                    if (result != null) {
                        FLog.v((Class<?>) BufferedDiskCache.TAG, "Found image for %s in staging area", (Object) key.toString());
                        BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaHit();
                    } else {
                        FLog.v((Class<?>) BufferedDiskCache.TAG, "Did not find image for %s in staging area", (Object) key.toString());
                        BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaMiss();
                        try {
                            ref = CloseableReference.of(BufferedDiskCache.this.readFromDiskCache(key));
                            EncodedImage result2 = new EncodedImage(ref);
                            try {
                                CloseableReference.closeSafely((CloseableReference<?>) ref);
                                result = result2;
                            } catch (Exception e) {
                                EncodedImage encodedImage = result2;
                                return null;
                            }
                        } catch (Exception e2) {
                        } catch (Throwable th) {
                            CloseableReference.closeSafely((CloseableReference<?>) ref);
                            throw th;
                        }
                    }
                    if (!Thread.interrupted()) {
                        return result;
                    }
                    FLog.v((Class<?>) BufferedDiskCache.TAG, "Host thread was interrupted, decreasing reference count");
                    if (result != null) {
                        result.close();
                    }
                    throw new InterruptedException();
                }
            }, this.mReadExecutor);
        } catch (Exception exception) {
            FLog.w(TAG, (Throwable) exception, "Failed to schedule disk-cache read for %s", key.toString());
            return Task.forError(exception);
        }
    }

    public void put(final CacheKey key, EncodedImage encodedImage) {
        Preconditions.checkNotNull(key);
        Preconditions.checkArgument(EncodedImage.isValid(encodedImage));
        this.mStagingArea.put(key, encodedImage);
        final EncodedImage finalEncodedImage = EncodedImage.cloneOrNull(encodedImage);
        try {
            this.mWriteExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        BufferedDiskCache.this.writeToDiskCache(key, finalEncodedImage);
                    } finally {
                        BufferedDiskCache.this.mStagingArea.remove(key, finalEncodedImage);
                        EncodedImage.closeSafely(finalEncodedImage);
                    }
                }
            });
        } catch (Exception exception) {
            FLog.w(TAG, (Throwable) exception, "Failed to schedule disk-cache write for %s", key.toString());
            this.mStagingArea.remove(key, encodedImage);
            EncodedImage.closeSafely(finalEncodedImage);
        }
    }

    public Task<Void> remove(final CacheKey key) {
        Preconditions.checkNotNull(key);
        this.mStagingArea.remove(key);
        try {
            return Task.call(new Callable<Void>() {
                public Void call() throws Exception {
                    BufferedDiskCache.this.mStagingArea.remove(key);
                    BufferedDiskCache.this.mFileCache.remove(key);
                    return null;
                }
            }, this.mWriteExecutor);
        } catch (Exception exception) {
            FLog.w(TAG, (Throwable) exception, "Failed to schedule disk-cache remove for %s", key.toString());
            return Task.forError(exception);
        }
    }

    public Task<Void> clearAll() {
        this.mStagingArea.clearAll();
        try {
            return Task.call(new Callable<Void>() {
                public Void call() throws Exception {
                    BufferedDiskCache.this.mStagingArea.clearAll();
                    BufferedDiskCache.this.mFileCache.clearAll();
                    return null;
                }
            }, this.mWriteExecutor);
        } catch (Exception exception) {
            FLog.w(TAG, (Throwable) exception, "Failed to schedule disk-cache clear", new Object[0]);
            return Task.forError(exception);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.facebook.imagepipeline.memory.PooledByteBuffer readFromDiskCache(com.facebook.cache.common.CacheKey r10) throws java.io.IOException {
        /*
            r9 = this;
            java.lang.Class<?> r4 = TAG     // Catch:{ IOException -> 0x0053 }
            java.lang.String r5 = "Disk cache read for %s"
            java.lang.String r6 = r10.toString()     // Catch:{ IOException -> 0x0053 }
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r4, (java.lang.String) r5, (java.lang.Object) r6)     // Catch:{ IOException -> 0x0053 }
            com.facebook.cache.disk.FileCache r4 = r9.mFileCache     // Catch:{ IOException -> 0x0053 }
            com.facebook.binaryresource.BinaryResource r1 = r4.getResource(r10)     // Catch:{ IOException -> 0x0053 }
            if (r1 != 0) goto L_0x0025
            java.lang.Class<?> r4 = TAG     // Catch:{ IOException -> 0x0053 }
            java.lang.String r5 = "Disk cache miss for %s"
            java.lang.String r6 = r10.toString()     // Catch:{ IOException -> 0x0053 }
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r4, (java.lang.String) r5, (java.lang.Object) r6)     // Catch:{ IOException -> 0x0053 }
            com.facebook.imagepipeline.cache.ImageCacheStatsTracker r4 = r9.mImageCacheStatsTracker     // Catch:{ IOException -> 0x0053 }
            r4.onDiskCacheMiss()     // Catch:{ IOException -> 0x0053 }
            r0 = 0
        L_0x0024:
            return r0
        L_0x0025:
            java.lang.Class<?> r4 = TAG     // Catch:{ IOException -> 0x0053 }
            java.lang.String r5 = "Found entry in disk cache for %s"
            java.lang.String r6 = r10.toString()     // Catch:{ IOException -> 0x0053 }
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r4, (java.lang.String) r5, (java.lang.Object) r6)     // Catch:{ IOException -> 0x0053 }
            com.facebook.imagepipeline.cache.ImageCacheStatsTracker r4 = r9.mImageCacheStatsTracker     // Catch:{ IOException -> 0x0053 }
            r4.onDiskCacheHit()     // Catch:{ IOException -> 0x0053 }
            java.io.InputStream r3 = r1.openStream()     // Catch:{ IOException -> 0x0053 }
            com.facebook.imagepipeline.memory.PooledByteBufferFactory r4 = r9.mPooledByteBufferFactory     // Catch:{ all -> 0x006b }
            long r6 = r1.size()     // Catch:{ all -> 0x006b }
            int r5 = (int) r6     // Catch:{ all -> 0x006b }
            com.facebook.imagepipeline.memory.PooledByteBuffer r0 = r4.newByteBuffer(r3, r5)     // Catch:{ all -> 0x006b }
            r3.close()     // Catch:{ IOException -> 0x0053 }
            java.lang.Class<?> r4 = TAG     // Catch:{ IOException -> 0x0053 }
            java.lang.String r5 = "Successful read from disk cache for %s"
            java.lang.String r6 = r10.toString()     // Catch:{ IOException -> 0x0053 }
            com.facebook.common.logging.FLog.v((java.lang.Class<?>) r4, (java.lang.String) r5, (java.lang.Object) r6)     // Catch:{ IOException -> 0x0053 }
            goto L_0x0024
        L_0x0053:
            r2 = move-exception
            java.lang.Class<?> r4 = TAG
            java.lang.String r5 = "Exception reading from cache for %s"
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            r7 = 0
            java.lang.String r8 = r10.toString()
            r6[r7] = r8
            com.facebook.common.logging.FLog.w((java.lang.Class<?>) r4, (java.lang.Throwable) r2, (java.lang.String) r5, (java.lang.Object[]) r6)
            com.facebook.imagepipeline.cache.ImageCacheStatsTracker r4 = r9.mImageCacheStatsTracker
            r4.onDiskCacheGetFail()
            throw r2
        L_0x006b:
            r4 = move-exception
            r3.close()     // Catch:{ IOException -> 0x0053 }
            throw r4     // Catch:{ IOException -> 0x0053 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.cache.BufferedDiskCache.readFromDiskCache(com.facebook.cache.common.CacheKey):com.facebook.imagepipeline.memory.PooledByteBuffer");
    }

    /* access modifiers changed from: private */
    public void writeToDiskCache(CacheKey key, final EncodedImage encodedImage) {
        FLog.v(TAG, "About to write to disk-cache for key %s", (Object) key.toString());
        try {
            this.mFileCache.insert(key, new WriterCallback() {
                public void write(OutputStream os) throws IOException {
                    BufferedDiskCache.this.mPooledByteStreams.copy(encodedImage.getInputStream(), os);
                }
            });
            FLog.v(TAG, "Successful disk-cache write for key %s", (Object) key.toString());
        } catch (IOException ioe) {
            FLog.w(TAG, (Throwable) ioe, "Failed to write to disk-cache for key %s", key.toString());
        }
    }
}
