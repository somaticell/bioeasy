package com.facebook.imagepipeline.animated.impl;

import android.support.v4.view.PointerIconCompat;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.LinkedHashSet;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class AnimatedFrameCache {
    private final CountingMemoryCache<CacheKey, CloseableImage> mBackingCache;
    private final CountingMemoryCache.EntryStateObserver<CacheKey> mEntryStateObserver = new CountingMemoryCache.EntryStateObserver<CacheKey>() {
        public void onExclusivityChanged(CacheKey key, boolean isExclusive) {
            AnimatedFrameCache.this.onReusabilityChange(key, isExclusive);
        }
    };
    @GuardedBy("this")
    private final LinkedHashSet<CacheKey> mFreeItemsPool = new LinkedHashSet<>();
    private final CacheKey mImageCacheKey;

    @VisibleForTesting
    static class FrameKey implements CacheKey {
        private final int mFrameIndex;
        private final CacheKey mImageCacheKey;

        public FrameKey(CacheKey imageCacheKey, int frameIndex) {
            this.mImageCacheKey = imageCacheKey;
            this.mFrameIndex = frameIndex;
        }

        public String toString() {
            return Objects.toStringHelper((Object) this).add("imageCacheKey", (Object) this.mImageCacheKey).add("frameIndex", this.mFrameIndex).toString();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof FrameKey)) {
                return false;
            }
            FrameKey that = (FrameKey) o;
            if (this.mImageCacheKey == that.mImageCacheKey && this.mFrameIndex == that.mFrameIndex) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.mImageCacheKey.hashCode() * PointerIconCompat.TYPE_ALL_SCROLL) + this.mFrameIndex;
        }
    }

    public AnimatedFrameCache(CacheKey imageCacheKey, CountingMemoryCache<CacheKey, CloseableImage> backingCache) {
        this.mImageCacheKey = imageCacheKey;
        this.mBackingCache = backingCache;
    }

    public synchronized void onReusabilityChange(CacheKey key, boolean isReusable) {
        if (isReusable) {
            this.mFreeItemsPool.add(key);
        } else {
            this.mFreeItemsPool.remove(key);
        }
    }

    @Nullable
    public CloseableReference<CloseableImage> cache(int frameIndex, CloseableReference<CloseableImage> imageRef) {
        return this.mBackingCache.cache(keyFor(frameIndex), imageRef, this.mEntryStateObserver);
    }

    @Nullable
    public CloseableReference<CloseableImage> get(int frameIndex) {
        return this.mBackingCache.get(keyFor(frameIndex));
    }

    @Nullable
    public CloseableReference<CloseableImage> getForReuse() {
        CloseableReference<CloseableImage> imageRef;
        do {
            CacheKey key = popFirstFreeItemKey();
            if (key == null) {
                return null;
            }
            imageRef = this.mBackingCache.reuse(key);
        } while (imageRef == null);
        return imageRef;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: com.facebook.cache.common.CacheKey} */
    /* JADX WARNING: Multi-variable type inference failed */
    @javax.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.facebook.cache.common.CacheKey popFirstFreeItemKey() {
        /*
            r4 = this;
            monitor-enter(r4)
            r1 = 0
            java.util.LinkedHashSet<com.facebook.cache.common.CacheKey> r3 = r4.mFreeItemsPool     // Catch:{ all -> 0x001b }
            java.util.Iterator r2 = r3.iterator()     // Catch:{ all -> 0x001b }
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x001b }
            if (r3 == 0) goto L_0x0019
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x001b }
            r0 = r3
            com.facebook.cache.common.CacheKey r0 = (com.facebook.cache.common.CacheKey) r0     // Catch:{ all -> 0x001b }
            r1 = r0
            r2.remove()     // Catch:{ all -> 0x001b }
        L_0x0019:
            monitor-exit(r4)
            return r1
        L_0x001b:
            r3 = move-exception
            monitor-exit(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.animated.impl.AnimatedFrameCache.popFirstFreeItemKey():com.facebook.cache.common.CacheKey");
    }

    private FrameKey keyFor(int frameIndex) {
        return new FrameKey(this.mImageCacheKey, frameIndex);
    }
}
