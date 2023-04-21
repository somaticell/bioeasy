package com.facebook.cache.common;

import com.facebook.cache.common.CacheEventListener;

public class NoOpCacheEventListener implements CacheEventListener {
    private static NoOpCacheEventListener sInstance = null;

    private NoOpCacheEventListener() {
    }

    public static synchronized NoOpCacheEventListener getInstance() {
        NoOpCacheEventListener noOpCacheEventListener;
        synchronized (NoOpCacheEventListener.class) {
            if (sInstance == null) {
                sInstance = new NoOpCacheEventListener();
            }
            noOpCacheEventListener = sInstance;
        }
        return noOpCacheEventListener;
    }

    public void onHit() {
    }

    public void onMiss() {
    }

    public void onWriteAttempt() {
    }

    public void onReadException() {
    }

    public void onWriteException() {
    }

    public void onEviction(CacheEventListener.EvictionReason evictionReason, int itemCount, long itemSize) {
    }
}
