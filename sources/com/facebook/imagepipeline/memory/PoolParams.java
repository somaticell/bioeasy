package com.facebook.imagepipeline.memory;

import android.support.v7.widget.ActivityChooserView;
import android.util.SparseIntArray;
import com.facebook.common.internal.Preconditions;
import javax.annotation.Nullable;

public class PoolParams {
    public static final int IGNORE_THREADS = -1;
    public final SparseIntArray bucketSizes;
    public final int maxBucketSize;
    public final int maxNumThreads;
    public final int maxSizeHardCap;
    public final int maxSizeSoftCap;
    public final int minBucketSize;

    public PoolParams(int maxSize, @Nullable SparseIntArray bucketSizes2) {
        this(maxSize, maxSize, bucketSizes2, 0, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, -1);
    }

    public PoolParams(int maxSizeSoftCap2, int maxSizeHardCap2, @Nullable SparseIntArray bucketSizes2) {
        this(maxSizeSoftCap2, maxSizeHardCap2, bucketSizes2, 0, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, -1);
    }

    public PoolParams(int maxSizeSoftCap2, int maxSizeHardCap2, @Nullable SparseIntArray bucketSizes2, int minBucketSize2, int maxBucketSize2, int maxNumThreads2) {
        Preconditions.checkState(maxSizeSoftCap2 >= 0 && maxSizeHardCap2 >= maxSizeSoftCap2);
        this.maxSizeSoftCap = maxSizeSoftCap2;
        this.maxSizeHardCap = maxSizeHardCap2;
        this.bucketSizes = bucketSizes2;
        this.minBucketSize = minBucketSize2;
        this.maxBucketSize = maxBucketSize2;
        this.maxNumThreads = maxNumThreads2;
    }
}
