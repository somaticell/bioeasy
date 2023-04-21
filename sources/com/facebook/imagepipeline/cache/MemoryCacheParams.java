package com.facebook.imagepipeline.cache;

public class MemoryCacheParams {
    public final int maxCacheEntries;
    public final int maxCacheEntrySize;
    public final int maxCacheSize;
    public final int maxEvictionQueueEntries;
    public final int maxEvictionQueueSize;

    public MemoryCacheParams(int maxCacheSize2, int maxCacheEntries2, int maxEvictionQueueSize2, int maxEvictionQueueEntries2, int maxCacheEntrySize2) {
        this.maxCacheSize = maxCacheSize2;
        this.maxCacheEntries = maxCacheEntries2;
        this.maxEvictionQueueSize = maxEvictionQueueSize2;
        this.maxEvictionQueueEntries = maxEvictionQueueEntries2;
        this.maxCacheEntrySize = maxCacheEntrySize2;
    }
}
