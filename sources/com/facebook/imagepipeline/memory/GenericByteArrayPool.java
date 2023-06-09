package com.facebook.imagepipeline.memory;

import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.memory.BasePool;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class GenericByteArrayPool extends BasePool<byte[]> implements ByteArrayPool {
    private final int[] mBucketSizes;

    public GenericByteArrayPool(MemoryTrimmableRegistry memoryTrimmableRegistry, PoolParams poolParams, PoolStatsTracker poolStatsTracker) {
        super(memoryTrimmableRegistry, poolParams, poolStatsTracker);
        SparseIntArray bucketSizes = poolParams.bucketSizes;
        this.mBucketSizes = new int[bucketSizes.size()];
        for (int i = 0; i < bucketSizes.size(); i++) {
            this.mBucketSizes[i] = bucketSizes.keyAt(i);
        }
        initialize();
    }

    public int getMinBufferSize() {
        return this.mBucketSizes[0];
    }

    /* access modifiers changed from: protected */
    public byte[] alloc(int bucketedSize) {
        return new byte[bucketedSize];
    }

    /* access modifiers changed from: protected */
    public void free(byte[] value) {
        Preconditions.checkNotNull(value);
    }

    /* access modifiers changed from: protected */
    public int getSizeInBytes(int bucketedSize) {
        return bucketedSize;
    }

    /* access modifiers changed from: protected */
    public int getBucketedSize(int requestSize) {
        int intRequestSize = requestSize;
        if (intRequestSize <= 0) {
            throw new BasePool.InvalidSizeException(Integer.valueOf(requestSize));
        }
        for (int bucketedSize : this.mBucketSizes) {
            if (bucketedSize >= intRequestSize) {
                return bucketedSize;
            }
        }
        return requestSize;
    }

    /* access modifiers changed from: protected */
    public int getBucketedSizeForValue(byte[] value) {
        Preconditions.checkNotNull(value);
        return value.length;
    }
}
