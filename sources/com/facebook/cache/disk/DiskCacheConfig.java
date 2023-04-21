package com.facebook.cache.disk;

import android.content.Context;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.NoOpCacheErrorLogger;
import com.facebook.cache.common.NoOpCacheEventListener;
import com.facebook.common.disk.DiskTrimmableRegistry;
import com.facebook.common.disk.NoOpDiskTrimmableRegistry;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.internal.Suppliers;
import java.io.File;
import javax.annotation.Nullable;

public class DiskCacheConfig {
    private final String mBaseDirectoryName;
    private final Supplier<File> mBaseDirectoryPathSupplier;
    private final CacheErrorLogger mCacheErrorLogger;
    private final CacheEventListener mCacheEventListener;
    private final long mDefaultSizeLimit;
    private final DiskTrimmableRegistry mDiskTrimmableRegistry;
    private final EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
    private final long mLowDiskSpaceSizeLimit;
    private final long mMinimumSizeLimit;
    private final int mVersion;

    private DiskCacheConfig(Builder builder) {
        this.mVersion = builder.mVersion;
        this.mBaseDirectoryName = (String) Preconditions.checkNotNull(builder.mBaseDirectoryName);
        this.mBaseDirectoryPathSupplier = (Supplier) Preconditions.checkNotNull(builder.mBaseDirectoryPathSupplier);
        this.mDefaultSizeLimit = builder.mMaxCacheSize;
        this.mLowDiskSpaceSizeLimit = builder.mMaxCacheSizeOnLowDiskSpace;
        this.mMinimumSizeLimit = builder.mMaxCacheSizeOnVeryLowDiskSpace;
        this.mEntryEvictionComparatorSupplier = (EntryEvictionComparatorSupplier) Preconditions.checkNotNull(builder.mEntryEvictionComparatorSupplier);
        this.mCacheErrorLogger = builder.mCacheErrorLogger == null ? NoOpCacheErrorLogger.getInstance() : builder.mCacheErrorLogger;
        this.mCacheEventListener = builder.mCacheEventListener == null ? NoOpCacheEventListener.getInstance() : builder.mCacheEventListener;
        this.mDiskTrimmableRegistry = builder.mDiskTrimmableRegistry == null ? NoOpDiskTrimmableRegistry.getInstance() : builder.mDiskTrimmableRegistry;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public String getBaseDirectoryName() {
        return this.mBaseDirectoryName;
    }

    public Supplier<File> getBaseDirectoryPathSupplier() {
        return this.mBaseDirectoryPathSupplier;
    }

    public long getDefaultSizeLimit() {
        return this.mDefaultSizeLimit;
    }

    public long getLowDiskSpaceSizeLimit() {
        return this.mLowDiskSpaceSizeLimit;
    }

    public long getMinimumSizeLimit() {
        return this.mMinimumSizeLimit;
    }

    public EntryEvictionComparatorSupplier getEntryEvictionComparatorSupplier() {
        return this.mEntryEvictionComparatorSupplier;
    }

    public CacheErrorLogger getCacheErrorLogger() {
        return this.mCacheErrorLogger;
    }

    public CacheEventListener getCacheEventListener() {
        return this.mCacheEventListener;
    }

    public DiskTrimmableRegistry getDiskTrimmableRegistry() {
        return this.mDiskTrimmableRegistry;
    }

    public static Builder newBuilder(@Nullable Context context) {
        return new Builder(context);
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public String mBaseDirectoryName;
        /* access modifiers changed from: private */
        public Supplier<File> mBaseDirectoryPathSupplier;
        /* access modifiers changed from: private */
        public CacheErrorLogger mCacheErrorLogger;
        /* access modifiers changed from: private */
        public CacheEventListener mCacheEventListener;
        /* access modifiers changed from: private */
        @Nullable
        public final Context mContext;
        /* access modifiers changed from: private */
        public DiskTrimmableRegistry mDiskTrimmableRegistry;
        /* access modifiers changed from: private */
        public EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
        /* access modifiers changed from: private */
        public long mMaxCacheSize;
        /* access modifiers changed from: private */
        public long mMaxCacheSizeOnLowDiskSpace;
        /* access modifiers changed from: private */
        public long mMaxCacheSizeOnVeryLowDiskSpace;
        /* access modifiers changed from: private */
        public int mVersion;

        private Builder(@Nullable Context context) {
            this.mVersion = 1;
            this.mBaseDirectoryName = "image_cache";
            this.mMaxCacheSize = 41943040;
            this.mMaxCacheSizeOnLowDiskSpace = 10485760;
            this.mMaxCacheSizeOnVeryLowDiskSpace = 2097152;
            this.mEntryEvictionComparatorSupplier = new DefaultEntryEvictionComparatorSupplier();
            this.mContext = context;
        }

        public Builder setVersion(int version) {
            this.mVersion = version;
            return this;
        }

        public Builder setBaseDirectoryName(String baseDirectoryName) {
            this.mBaseDirectoryName = baseDirectoryName;
            return this;
        }

        public Builder setBaseDirectoryPath(File baseDirectoryPath) {
            this.mBaseDirectoryPathSupplier = Suppliers.of(baseDirectoryPath);
            return this;
        }

        public Builder setBaseDirectoryPathSupplier(Supplier<File> baseDirectoryPathSupplier) {
            this.mBaseDirectoryPathSupplier = baseDirectoryPathSupplier;
            return this;
        }

        public Builder setMaxCacheSize(long maxCacheSize) {
            this.mMaxCacheSize = maxCacheSize;
            return this;
        }

        public Builder setMaxCacheSizeOnLowDiskSpace(long maxCacheSizeOnLowDiskSpace) {
            this.mMaxCacheSizeOnLowDiskSpace = maxCacheSizeOnLowDiskSpace;
            return this;
        }

        public Builder setMaxCacheSizeOnVeryLowDiskSpace(long maxCacheSizeOnVeryLowDiskSpace) {
            this.mMaxCacheSizeOnVeryLowDiskSpace = maxCacheSizeOnVeryLowDiskSpace;
            return this;
        }

        public Builder setEntryEvictionComparatorSupplier(EntryEvictionComparatorSupplier supplier) {
            this.mEntryEvictionComparatorSupplier = supplier;
            return this;
        }

        public Builder setCacheErrorLogger(CacheErrorLogger cacheErrorLogger) {
            this.mCacheErrorLogger = cacheErrorLogger;
            return this;
        }

        public Builder setCacheEventListener(CacheEventListener cacheEventListener) {
            this.mCacheEventListener = cacheEventListener;
            return this;
        }

        public Builder setDiskTrimmableRegistry(DiskTrimmableRegistry diskTrimmableRegistry) {
            this.mDiskTrimmableRegistry = diskTrimmableRegistry;
            return this;
        }

        public DiskCacheConfig build() {
            Preconditions.checkState((this.mBaseDirectoryPathSupplier == null && this.mContext == null) ? false : true, "Either a non-null context or a base directory path or supplier must be provided.");
            if (this.mBaseDirectoryPathSupplier == null && this.mContext != null) {
                this.mBaseDirectoryPathSupplier = new Supplier<File>() {
                    public File get() {
                        return Builder.this.mContext.getApplicationContext().getCacheDir();
                    }
                };
            }
            return new DiskCacheConfig(this);
        }
    }
}
