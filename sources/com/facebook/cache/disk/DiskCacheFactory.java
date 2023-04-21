package com.facebook.cache.disk;

import com.facebook.cache.disk.DiskStorageCache;

public class DiskCacheFactory {
    public static DiskStorageCache newDiskStorageCache(DiskCacheConfig diskCacheConfig) {
        DiskStorageSupplier diskStorageSupplier = newDiskStorageSupplier(diskCacheConfig);
        return new DiskStorageCache(diskStorageSupplier, diskCacheConfig.getEntryEvictionComparatorSupplier(), new DiskStorageCache.Params(diskCacheConfig.getMinimumSizeLimit(), diskCacheConfig.getLowDiskSpaceSizeLimit(), diskCacheConfig.getDefaultSizeLimit()), diskCacheConfig.getCacheEventListener(), diskCacheConfig.getCacheErrorLogger(), diskCacheConfig.getDiskTrimmableRegistry());
    }

    private static DiskStorageSupplier newDiskStorageSupplier(DiskCacheConfig diskCacheConfig) {
        return new DefaultDiskStorageSupplier(diskCacheConfig.getVersion(), diskCacheConfig.getBaseDirectoryPathSupplier(), diskCacheConfig.getBaseDirectoryName(), diskCacheConfig.getCacheErrorLogger());
    }
}
