package com.facebook.imagepipeline.cache;

import android.support.v7.widget.ActivityChooserView;
import com.facebook.common.internal.Supplier;

public class DefaultEncodedMemoryCacheParamsSupplier implements Supplier<MemoryCacheParams> {
    private static final int MAX_CACHE_ENTRIES = Integer.MAX_VALUE;
    private static final int MAX_EVICTION_QUEUE_ENTRIES = Integer.MAX_VALUE;

    public MemoryCacheParams get() {
        int maxCacheSize = getMaxCacheSize();
        return new MemoryCacheParams(maxCacheSize, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, maxCacheSize, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, maxCacheSize / 8);
    }

    private int getMaxCacheSize() {
        int maxMemory = (int) Math.min(Runtime.getRuntime().maxMemory(), 2147483647L);
        if (maxMemory < 16777216) {
            return 1048576;
        }
        if (maxMemory < 33554432) {
            return 2097152;
        }
        return 4194304;
    }
}
