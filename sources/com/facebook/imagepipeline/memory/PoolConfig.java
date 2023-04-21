package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import javax.annotation.concurrent.Immutable;

@Immutable
public class PoolConfig {
    private final PoolParams mBitmapPoolParams;
    private final PoolStatsTracker mBitmapPoolStatsTracker;
    private final PoolParams mFlexByteArrayPoolParams;
    private final MemoryTrimmableRegistry mMemoryTrimmableRegistry;
    private final PoolParams mNativeMemoryChunkPoolParams;
    private final PoolStatsTracker mNativeMemoryChunkPoolStatsTracker;
    private final PoolParams mSmallByteArrayPoolParams;
    private final PoolStatsTracker mSmallByteArrayPoolStatsTracker;

    private PoolConfig(Builder builder) {
        this.mBitmapPoolParams = builder.mBitmapPoolParams == null ? DefaultBitmapPoolParams.get() : builder.mBitmapPoolParams;
        this.mBitmapPoolStatsTracker = builder.mBitmapPoolStatsTracker == null ? NoOpPoolStatsTracker.getInstance() : builder.mBitmapPoolStatsTracker;
        this.mFlexByteArrayPoolParams = builder.mFlexByteArrayPoolParams == null ? DefaultFlexByteArrayPoolParams.get() : builder.mFlexByteArrayPoolParams;
        this.mMemoryTrimmableRegistry = builder.mMemoryTrimmableRegistry == null ? NoOpMemoryTrimmableRegistry.getInstance() : builder.mMemoryTrimmableRegistry;
        this.mNativeMemoryChunkPoolParams = builder.mNativeMemoryChunkPoolParams == null ? DefaultNativeMemoryChunkPoolParams.get() : builder.mNativeMemoryChunkPoolParams;
        this.mNativeMemoryChunkPoolStatsTracker = builder.mNativeMemoryChunkPoolStatsTracker == null ? NoOpPoolStatsTracker.getInstance() : builder.mNativeMemoryChunkPoolStatsTracker;
        this.mSmallByteArrayPoolParams = builder.mSmallByteArrayPoolParams == null ? DefaultByteArrayPoolParams.get() : builder.mSmallByteArrayPoolParams;
        this.mSmallByteArrayPoolStatsTracker = builder.mSmallByteArrayPoolStatsTracker == null ? NoOpPoolStatsTracker.getInstance() : builder.mSmallByteArrayPoolStatsTracker;
    }

    public PoolParams getBitmapPoolParams() {
        return this.mBitmapPoolParams;
    }

    public PoolStatsTracker getBitmapPoolStatsTracker() {
        return this.mBitmapPoolStatsTracker;
    }

    public MemoryTrimmableRegistry getMemoryTrimmableRegistry() {
        return this.mMemoryTrimmableRegistry;
    }

    public PoolParams getNativeMemoryChunkPoolParams() {
        return this.mNativeMemoryChunkPoolParams;
    }

    public PoolStatsTracker getNativeMemoryChunkPoolStatsTracker() {
        return this.mNativeMemoryChunkPoolStatsTracker;
    }

    public PoolParams getFlexByteArrayPoolParams() {
        return this.mFlexByteArrayPoolParams;
    }

    public PoolParams getSmallByteArrayPoolParams() {
        return this.mSmallByteArrayPoolParams;
    }

    public PoolStatsTracker getSmallByteArrayPoolStatsTracker() {
        return this.mSmallByteArrayPoolStatsTracker;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public PoolParams mBitmapPoolParams;
        /* access modifiers changed from: private */
        public PoolStatsTracker mBitmapPoolStatsTracker;
        /* access modifiers changed from: private */
        public PoolParams mFlexByteArrayPoolParams;
        /* access modifiers changed from: private */
        public MemoryTrimmableRegistry mMemoryTrimmableRegistry;
        /* access modifiers changed from: private */
        public PoolParams mNativeMemoryChunkPoolParams;
        /* access modifiers changed from: private */
        public PoolStatsTracker mNativeMemoryChunkPoolStatsTracker;
        /* access modifiers changed from: private */
        public PoolParams mSmallByteArrayPoolParams;
        /* access modifiers changed from: private */
        public PoolStatsTracker mSmallByteArrayPoolStatsTracker;

        private Builder() {
        }

        public Builder setBitmapPoolParams(PoolParams bitmapPoolParams) {
            this.mBitmapPoolParams = (PoolParams) Preconditions.checkNotNull(bitmapPoolParams);
            return this;
        }

        public Builder setBitmapPoolStatsTracker(PoolStatsTracker bitmapPoolStatsTracker) {
            this.mBitmapPoolStatsTracker = (PoolStatsTracker) Preconditions.checkNotNull(bitmapPoolStatsTracker);
            return this;
        }

        public Builder setFlexByteArrayPoolParams(PoolParams flexByteArrayPoolParams) {
            this.mFlexByteArrayPoolParams = flexByteArrayPoolParams;
            return this;
        }

        public Builder setMemoryTrimmableRegistry(MemoryTrimmableRegistry memoryTrimmableRegistry) {
            this.mMemoryTrimmableRegistry = memoryTrimmableRegistry;
            return this;
        }

        public Builder setNativeMemoryChunkPoolParams(PoolParams nativeMemoryChunkPoolParams) {
            this.mNativeMemoryChunkPoolParams = (PoolParams) Preconditions.checkNotNull(nativeMemoryChunkPoolParams);
            return this;
        }

        public Builder setNativeMemoryChunkPoolStatsTracker(PoolStatsTracker nativeMemoryChunkPoolStatsTracker) {
            this.mNativeMemoryChunkPoolStatsTracker = (PoolStatsTracker) Preconditions.checkNotNull(nativeMemoryChunkPoolStatsTracker);
            return this;
        }

        public Builder setSmallByteArrayPoolParams(PoolParams commonByteArrayPoolParams) {
            this.mSmallByteArrayPoolParams = (PoolParams) Preconditions.checkNotNull(commonByteArrayPoolParams);
            return this;
        }

        public Builder setSmallByteArrayPoolStatsTracker(PoolStatsTracker smallByteArrayPoolStatsTracker) {
            this.mSmallByteArrayPoolStatsTracker = (PoolStatsTracker) Preconditions.checkNotNull(smallByteArrayPoolStatsTracker);
            return this;
        }

        public PoolConfig build() {
            return new PoolConfig(this);
        }
    }
}
