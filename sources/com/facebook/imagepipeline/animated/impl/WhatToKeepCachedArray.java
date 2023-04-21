package com.facebook.imagepipeline.animated.impl;

import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
class WhatToKeepCachedArray {
    private final boolean[] mData;

    WhatToKeepCachedArray(int size) {
        this.mData = new boolean[size];
    }

    /* access modifiers changed from: package-private */
    public boolean get(int index) {
        return this.mData[index];
    }

    /* access modifiers changed from: package-private */
    public void setAll(boolean value) {
        for (int i = 0; i < this.mData.length; i++) {
            this.mData[i] = value;
        }
    }

    /* access modifiers changed from: package-private */
    public void removeOutsideRange(int start, int end) {
        for (int i = 0; i < this.mData.length; i++) {
            if (AnimatedDrawableUtil.isOutsideRange(start, end, i)) {
                this.mData[i] = false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void set(int index, boolean value) {
        this.mData[index] = value;
    }
}
