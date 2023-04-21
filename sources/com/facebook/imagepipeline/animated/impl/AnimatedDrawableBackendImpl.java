package com.facebook.imagepipeline.animated.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageFrame;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;
import javax.annotation.concurrent.GuardedBy;

public class AnimatedDrawableBackendImpl implements AnimatedDrawableBackend {
    private final AnimatedDrawableUtil mAnimatedDrawableUtil;
    private final AnimatedImage mAnimatedImage;
    private final AnimatedImageResult mAnimatedImageResult;
    private final int mDurationMs;
    private final int[] mFrameDurationsMs = this.mAnimatedImage.getFrameDurations();
    private final AnimatedDrawableFrameInfo[] mFrameInfos;
    private final int[] mFrameTimestampsMs;
    private final Rect mRenderedBounds;
    @GuardedBy("this")
    private Bitmap mTempBitmap;

    public AnimatedDrawableBackendImpl(AnimatedDrawableUtil animatedDrawableUtil, AnimatedImageResult animatedImageResult, Rect bounds) {
        this.mAnimatedDrawableUtil = animatedDrawableUtil;
        this.mAnimatedImageResult = animatedImageResult;
        this.mAnimatedImage = animatedImageResult.getImage();
        this.mAnimatedDrawableUtil.fixFrameDurations(this.mFrameDurationsMs);
        this.mDurationMs = this.mAnimatedDrawableUtil.getTotalDurationFromFrameDurations(this.mFrameDurationsMs);
        this.mFrameTimestampsMs = this.mAnimatedDrawableUtil.getFrameTimeStampsFromDurations(this.mFrameDurationsMs);
        this.mRenderedBounds = getBoundsToUse(this.mAnimatedImage, bounds);
        this.mFrameInfos = new AnimatedDrawableFrameInfo[this.mAnimatedImage.getFrameCount()];
        for (int i = 0; i < this.mAnimatedImage.getFrameCount(); i++) {
            this.mFrameInfos[i] = this.mAnimatedImage.getFrameInfo(i);
        }
    }

    private static Rect getBoundsToUse(AnimatedImage image, Rect targetBounds) {
        if (targetBounds == null) {
            return new Rect(0, 0, image.getWidth(), image.getHeight());
        }
        return new Rect(0, 0, Math.min(targetBounds.width(), image.getWidth()), Math.min(targetBounds.height(), image.getHeight()));
    }

    public AnimatedImageResult getAnimatedImageResult() {
        return this.mAnimatedImageResult;
    }

    public int getDurationMs() {
        return this.mDurationMs;
    }

    public int getFrameCount() {
        return this.mAnimatedImage.getFrameCount();
    }

    public int getLoopCount() {
        return this.mAnimatedImage.getLoopCount();
    }

    public int getWidth() {
        return this.mAnimatedImage.getWidth();
    }

    public int getHeight() {
        return this.mAnimatedImage.getHeight();
    }

    public int getRenderedWidth() {
        return this.mRenderedBounds.width();
    }

    public int getRenderedHeight() {
        return this.mRenderedBounds.height();
    }

    public AnimatedDrawableFrameInfo getFrameInfo(int frameNumber) {
        return this.mFrameInfos[frameNumber];
    }

    public int getFrameForTimestampMs(int timestampMs) {
        return this.mAnimatedDrawableUtil.getFrameForTimestampMs(this.mFrameTimestampsMs, timestampMs);
    }

    public int getTimestampMsForFrame(int frameNumber) {
        Preconditions.checkElementIndex(frameNumber, this.mFrameTimestampsMs.length);
        return this.mFrameTimestampsMs[frameNumber];
    }

    public int getDurationMsForFrame(int frameNumber) {
        return this.mFrameDurationsMs[frameNumber];
    }

    public int getFrameForPreview() {
        return this.mAnimatedImageResult.getFrameForPreview();
    }

    /* Debug info: failed to restart local var, previous not found, register: 4 */
    public AnimatedDrawableBackend forNewBounds(Rect bounds) {
        return getBoundsToUse(this.mAnimatedImage, bounds).equals(this.mRenderedBounds) ? this : new AnimatedDrawableBackendImpl(this.mAnimatedDrawableUtil, this.mAnimatedImageResult, bounds);
    }

    public synchronized int getMemoryUsage() {
        int bytes;
        bytes = 0;
        if (this.mTempBitmap != null) {
            bytes = 0 + this.mAnimatedDrawableUtil.getSizeOfBitmap(this.mTempBitmap);
        }
        return bytes + this.mAnimatedImage.getSizeInBytes();
    }

    public CloseableReference<Bitmap> getPreDecodedFrame(int frameNumber) {
        return this.mAnimatedImageResult.getDecodedFrame(frameNumber);
    }

    public boolean hasPreDecodedFrame(int index) {
        return this.mAnimatedImageResult.hasDecodedFrame(index);
    }

    public void renderFrame(int frameNumber, Canvas canvas) {
        AnimatedImageFrame frame = this.mAnimatedImage.getFrame(frameNumber);
        try {
            if (this.mAnimatedImage.doesRenderSupportScaling()) {
                renderImageSupportsScaling(canvas, frame);
            } else {
                renderImageDoesNotSupportScaling(canvas, frame);
            }
        } finally {
            frame.dispose();
        }
    }

    private void renderImageSupportsScaling(Canvas canvas, AnimatedImageFrame frame) {
        double xScale = ((double) this.mRenderedBounds.width()) / ((double) this.mAnimatedImage.getWidth());
        double yScale = ((double) this.mRenderedBounds.height()) / ((double) this.mAnimatedImage.getHeight());
        int frameWidth = (int) Math.round(((double) frame.getWidth()) * xScale);
        int frameHeight = (int) Math.round(((double) frame.getHeight()) * yScale);
        int xOffset = (int) (((double) frame.getXOffset()) * xScale);
        int yOffset = (int) (((double) frame.getYOffset()) * yScale);
        synchronized (this) {
            if (this.mTempBitmap == null) {
                this.mTempBitmap = Bitmap.createBitmap(this.mRenderedBounds.width(), this.mRenderedBounds.height(), Bitmap.Config.ARGB_8888);
            }
            this.mTempBitmap.eraseColor(0);
            frame.renderFrame(frameWidth, frameHeight, this.mTempBitmap);
            canvas.drawBitmap(this.mTempBitmap, (float) xOffset, (float) yOffset, (Paint) null);
        }
    }

    public void renderImageDoesNotSupportScaling(Canvas canvas, AnimatedImageFrame frame) {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int xOffset = frame.getXOffset();
        int yOffset = frame.getYOffset();
        synchronized (this) {
            if (this.mTempBitmap == null) {
                this.mTempBitmap = Bitmap.createBitmap(this.mAnimatedImage.getWidth(), this.mAnimatedImage.getHeight(), Bitmap.Config.ARGB_8888);
            }
            this.mTempBitmap.eraseColor(0);
            frame.renderFrame(frameWidth, frameHeight, this.mTempBitmap);
            canvas.save();
            canvas.scale(((float) this.mRenderedBounds.width()) / ((float) this.mAnimatedImage.getWidth()), ((float) this.mRenderedBounds.height()) / ((float) this.mAnimatedImage.getHeight()));
            canvas.translate((float) xOffset, (float) yOffset);
            canvas.drawBitmap(this.mTempBitmap, 0.0f, 0.0f, (Paint) null);
            canvas.restore();
        }
    }

    public synchronized void dropCaches() {
        if (this.mTempBitmap != null) {
            this.mTempBitmap.recycle();
            this.mTempBitmap = null;
        }
    }
}
