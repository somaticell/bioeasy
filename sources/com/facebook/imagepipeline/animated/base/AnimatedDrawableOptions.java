package com.facebook.imagepipeline.animated.base;

import javax.annotation.concurrent.Immutable;

@Immutable
public class AnimatedDrawableOptions {
    public static AnimatedDrawableOptions DEFAULTS = newBuilder().build();
    public final boolean allowPrefetching;
    public final boolean enableDebugging;
    public final boolean forceKeepAllFramesInMemory;
    public final int maximumBytes;

    public AnimatedDrawableOptions(AnimatedDrawableOptionsBuilder builder) {
        this.forceKeepAllFramesInMemory = builder.getForceKeepAllFramesInMemory();
        this.allowPrefetching = builder.getAllowPrefetching();
        this.maximumBytes = builder.getMaximumBytes();
        this.enableDebugging = builder.getEnableDebugging();
    }

    public static AnimatedDrawableOptionsBuilder newBuilder() {
        return new AnimatedDrawableOptionsBuilder();
    }
}
