package com.facebook.imagepipeline.image;

import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;

public class CloseableAnimatedImage extends CloseableImage {
    private AnimatedImageResult mImageResult;

    public CloseableAnimatedImage(AnimatedImageResult imageResult) {
        this.mImageResult = imageResult;
    }

    public synchronized int getWidth() {
        return isClosed() ? 0 : this.mImageResult.getImage().getWidth();
    }

    public synchronized int getHeight() {
        return isClosed() ? 0 : this.mImageResult.getImage().getHeight();
    }

    public void close() {
        synchronized (this) {
            if (this.mImageResult != null) {
                AnimatedImageResult imageResult = this.mImageResult;
                this.mImageResult = null;
                imageResult.dispose();
            }
        }
    }

    public synchronized boolean isClosed() {
        return this.mImageResult == null;
    }

    public synchronized int getSizeInBytes() {
        return isClosed() ? 0 : this.mImageResult.getImage().getSizeInBytes();
    }

    public boolean isStateful() {
        return true;
    }

    public synchronized AnimatedImageResult getImageResult() {
        return this.mImageResult;
    }

    public synchronized AnimatedImage getImage() {
        return isClosed() ? null : this.mImageResult.getImage();
    }
}
