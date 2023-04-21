package com.facebook.imagepipeline.animated.base;

public class AnimatedDrawableFrameInfo {
    public final DisposalMethod disposalMethod;
    public final int frameNumber;
    public final int height;
    public final boolean shouldBlendWithPreviousFrame;
    public final int width;
    public final int xOffset;
    public final int yOffset;

    public enum DisposalMethod {
        DISPOSE_DO_NOT,
        DISPOSE_TO_BACKGROUND,
        DISPOSE_TO_PREVIOUS
    }

    public AnimatedDrawableFrameInfo(int frameNumber2, int xOffset2, int yOffset2, int width2, int height2, boolean shouldBlendWithPreviousFrame2, DisposalMethod disposalMethod2) {
        this.frameNumber = frameNumber2;
        this.xOffset = xOffset2;
        this.yOffset = yOffset2;
        this.width = width2;
        this.height = height2;
        this.shouldBlendWithPreviousFrame = shouldBlendWithPreviousFrame2;
        this.disposalMethod = disposalMethod2;
    }
}
