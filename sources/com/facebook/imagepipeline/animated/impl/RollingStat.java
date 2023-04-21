package com.facebook.imagepipeline.animated.impl;

import android.os.SystemClock;

class RollingStat {
    private static final int WINDOWS = 60;
    private final short[] mStat = new short[60];

    /* access modifiers changed from: package-private */
    public void incrementStats(int toAdd) {
        int newCount;
        long nowSeconds = SystemClock.uptimeMillis() / 1000;
        int statsIndex = (int) (nowSeconds % 60);
        int marker = (int) ((nowSeconds / 60) & 255);
        short bucketData = this.mStat[statsIndex];
        int prevCount = bucketData & 255;
        if (marker != ((bucketData >> 8) & 255)) {
            newCount = toAdd;
        } else {
            newCount = prevCount + toAdd;
        }
        this.mStat[statsIndex] = (short) ((marker << 8) | newCount);
    }

    /* access modifiers changed from: package-private */
    public int getSum(int previousSeconds) {
        long nowSeconds = SystemClock.uptimeMillis() / 1000;
        int statsIndexStart = (int) ((nowSeconds - ((long) previousSeconds)) % 60);
        int currentMarker = (int) ((nowSeconds / 60) & 255);
        int sum = 0;
        for (int i = 0; i < previousSeconds; i++) {
            short bucketData = this.mStat[(statsIndexStart + i) % 60];
            int count = bucketData & 255;
            if (((bucketData >> 8) & 255) == currentMarker) {
                sum += count;
            }
        }
        return sum;
    }
}
