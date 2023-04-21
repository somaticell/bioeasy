package com.facebook.imagepipeline.animated.base;

public class AnimatedDrawableOptionsBuilder {
    private boolean mAllowPrefetching = true;
    private boolean mEnableDebugging;
    private boolean mForceKeepAllFramesInMemory;
    private int mMaximumBytes = -1;

    public boolean getForceKeepAllFramesInMemory() {
        return this.mForceKeepAllFramesInMemory;
    }

    public AnimatedDrawableOptionsBuilder setForceKeepAllFramesInMemory(boolean forceKeepAllFramesInMemory) {
        this.mForceKeepAllFramesInMemory = forceKeepAllFramesInMemory;
        return this;
    }

    public boolean getAllowPrefetching() {
        return this.mAllowPrefetching;
    }

    public AnimatedDrawableOptionsBuilder setAllowPrefetching(boolean allowPrefetching) {
        this.mAllowPrefetching = allowPrefetching;
        return this;
    }

    public int getMaximumBytes() {
        return this.mMaximumBytes;
    }

    public AnimatedDrawableOptionsBuilder setMaximumBytes(int maximumBytes) {
        this.mMaximumBytes = maximumBytes;
        return this;
    }

    public boolean getEnableDebugging() {
        return this.mEnableDebugging;
    }

    public AnimatedDrawableOptionsBuilder setEnableDebugging(boolean enableDebugging) {
        this.mEnableDebugging = enableDebugging;
        return this;
    }

    public AnimatedDrawableOptions build() {
        return new AnimatedDrawableOptions(this);
    }
}
