package com.facebook.imagepipeline.cache;

import com.facebook.common.logging.FLog;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.imagepipeline.cache.CountingMemoryCache;

public class NativeMemoryCacheTrimStrategy implements CountingMemoryCache.CacheTrimStrategy {
    private static final String TAG = "NativeMemoryCacheTrimStrategy";

    public double getTrimRatio(MemoryTrimType trimType) {
        switch (trimType) {
            case OnCloseToDalvikHeapLimit:
                return 0.0d;
            case OnAppBackgrounded:
            case OnSystemLowMemoryWhileAppInForeground:
            case OnSystemLowMemoryWhileAppInBackground:
                return 1.0d;
            default:
                FLog.wtf(TAG, "unknown trim type: %s", trimType);
                return 0.0d;
        }
    }
}
