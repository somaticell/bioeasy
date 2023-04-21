package com.facebook.imagepipeline.animated.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.facebook.common.references.CloseableReference;

public abstract class DelegatingAnimatedDrawableBackend implements AnimatedDrawableBackend {
    private final AnimatedDrawableBackend mAnimatedDrawableBackend;

    public DelegatingAnimatedDrawableBackend(AnimatedDrawableBackend animatedDrawableBackend) {
        this.mAnimatedDrawableBackend = animatedDrawableBackend;
    }

    /* access modifiers changed from: protected */
    public AnimatedDrawableBackend getDelegate() {
        return this.mAnimatedDrawableBackend;
    }

    public AnimatedImageResult getAnimatedImageResult() {
        return this.mAnimatedDrawableBackend.getAnimatedImageResult();
    }

    public int getDurationMs() {
        return this.mAnimatedDrawableBackend.getDurationMs();
    }

    public int getFrameCount() {
        return this.mAnimatedDrawableBackend.getFrameCount();
    }

    public int getLoopCount() {
        return this.mAnimatedDrawableBackend.getLoopCount();
    }

    public int getWidth() {
        return this.mAnimatedDrawableBackend.getWidth();
    }

    public int getHeight() {
        return this.mAnimatedDrawableBackend.getHeight();
    }

    public int getRenderedWidth() {
        return this.mAnimatedDrawableBackend.getRenderedWidth();
    }

    public int getRenderedHeight() {
        return this.mAnimatedDrawableBackend.getRenderedHeight();
    }

    public AnimatedDrawableFrameInfo getFrameInfo(int frameNumber) {
        return this.mAnimatedDrawableBackend.getFrameInfo(frameNumber);
    }

    public void renderFrame(int frameNumber, Canvas canvas) {
        this.mAnimatedDrawableBackend.renderFrame(frameNumber, canvas);
    }

    public int getFrameForTimestampMs(int timestampMs) {
        return this.mAnimatedDrawableBackend.getFrameForTimestampMs(timestampMs);
    }

    public int getTimestampMsForFrame(int frameNumber) {
        return this.mAnimatedDrawableBackend.getTimestampMsForFrame(frameNumber);
    }

    public int getDurationMsForFrame(int frameNumber) {
        return this.mAnimatedDrawableBackend.getDurationMsForFrame(frameNumber);
    }

    public int getFrameForPreview() {
        return this.mAnimatedDrawableBackend.getFrameForPreview();
    }

    public int getMemoryUsage() {
        return this.mAnimatedDrawableBackend.getMemoryUsage();
    }

    public CloseableReference<Bitmap> getPreDecodedFrame(int frameNumber) {
        return this.mAnimatedDrawableBackend.getPreDecodedFrame(frameNumber);
    }

    public boolean hasPreDecodedFrame(int frameNumber) {
        return this.mAnimatedDrawableBackend.hasPreDecodedFrame(frameNumber);
    }

    public void dropCaches() {
        this.mAnimatedDrawableBackend.dropCaches();
    }
}
