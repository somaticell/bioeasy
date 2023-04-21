package com.facebook.imagepipeline.animated.factory;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedImage;
import com.facebook.imagepipeline.animated.base.AnimatedImageResult;
import com.facebook.imagepipeline.animated.impl.AnimatedDrawableBackendProvider;
import com.facebook.imagepipeline.animated.impl.AnimatedImageCompositor;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.gif.GifImage;
import com.facebook.imagepipeline.image.CloseableAnimatedImage;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.webp.WebPImage;
import java.util.ArrayList;
import java.util.List;

public class AnimatedImageFactory {
    private final AnimatedDrawableBackendProvider mAnimatedDrawableBackendProvider;
    private final PlatformBitmapFactory mBitmapFactory;

    public AnimatedImageFactory(AnimatedDrawableBackendProvider animatedDrawableBackendProvider, PlatformBitmapFactory bitmapFactory) {
        this.mAnimatedDrawableBackendProvider = animatedDrawableBackendProvider;
        this.mBitmapFactory = bitmapFactory;
    }

    public CloseableImage decodeGif(EncodedImage encodedImage, ImageDecodeOptions options, Bitmap.Config bitmapConfig) {
        CloseableReference<PooledByteBuffer> bytesRef = encodedImage.getByteBufferRef();
        Preconditions.checkNotNull(bytesRef);
        try {
            Preconditions.checkState(!options.forceOldAnimationCode);
            PooledByteBuffer input = bytesRef.get();
            return getCloseableImage(options, GifImage.create(input.getNativePtr(), input.size()), bitmapConfig);
        } finally {
            CloseableReference.closeSafely((CloseableReference<?>) bytesRef);
        }
    }

    public CloseableImage decodeWebP(EncodedImage encodedImage, ImageDecodeOptions options, Bitmap.Config bitmapConfig) {
        CloseableReference<PooledByteBuffer> bytesRef = encodedImage.getByteBufferRef();
        Preconditions.checkNotNull(bytesRef);
        try {
            Preconditions.checkArgument(!options.forceOldAnimationCode);
            PooledByteBuffer input = bytesRef.get();
            return getCloseableImage(options, WebPImage.create(input.getNativePtr(), input.size()), bitmapConfig);
        } finally {
            CloseableReference.closeSafely((CloseableReference<?>) bytesRef);
        }
    }

    private CloseableAnimatedImage getCloseableImage(ImageDecodeOptions options, AnimatedImage image, Bitmap.Config bitmapConfig) {
        List<CloseableReference<Bitmap>> decodedFrames = null;
        CloseableReference<Bitmap> previewBitmap = null;
        try {
            int frameForPreview = options.useLastFrameForPreview ? image.getFrameCount() - 1 : 0;
            if (options.decodeAllFrames) {
                decodedFrames = decodeAllFrames(image, bitmapConfig);
                previewBitmap = CloseableReference.cloneOrNull(decodedFrames.get(frameForPreview));
            }
            if (options.decodePreviewFrame && previewBitmap == null) {
                previewBitmap = createPreviewBitmap(image, bitmapConfig, frameForPreview);
            }
            return new CloseableAnimatedImage(AnimatedImageResult.newBuilder(image).setPreviewBitmap(previewBitmap).setFrameForPreview(frameForPreview).setDecodedFrames(decodedFrames).build());
        } finally {
            CloseableReference.closeSafely((CloseableReference<?>) previewBitmap);
            CloseableReference.closeSafely((Iterable<? extends CloseableReference<?>>) decodedFrames);
        }
    }

    private CloseableReference<Bitmap> createPreviewBitmap(AnimatedImage image, Bitmap.Config bitmapConfig, int frameForPreview) {
        CloseableReference<Bitmap> bitmap = createBitmap(image.getWidth(), image.getHeight(), bitmapConfig);
        new AnimatedImageCompositor(this.mAnimatedDrawableBackendProvider.get(AnimatedImageResult.forAnimatedImage(image), (Rect) null), new AnimatedImageCompositor.Callback() {
            public void onIntermediateResult(int frameNumber, Bitmap bitmap) {
            }

            public CloseableReference<Bitmap> getCachedBitmap(int frameNumber) {
                return null;
            }
        }).renderFrame(frameForPreview, bitmap.get());
        return bitmap;
    }

    private List<CloseableReference<Bitmap>> decodeAllFrames(AnimatedImage image, Bitmap.Config bitmapConfig) {
        final List<CloseableReference<Bitmap>> bitmaps = new ArrayList<>();
        AnimatedDrawableBackend drawableBackend = this.mAnimatedDrawableBackendProvider.get(AnimatedImageResult.forAnimatedImage(image), (Rect) null);
        AnimatedImageCompositor animatedImageCompositor = new AnimatedImageCompositor(drawableBackend, new AnimatedImageCompositor.Callback() {
            public void onIntermediateResult(int frameNumber, Bitmap bitmap) {
            }

            public CloseableReference<Bitmap> getCachedBitmap(int frameNumber) {
                return CloseableReference.cloneOrNull((CloseableReference) bitmaps.get(frameNumber));
            }
        });
        for (int i = 0; i < drawableBackend.getFrameCount(); i++) {
            CloseableReference<Bitmap> bitmap = createBitmap(drawableBackend.getWidth(), drawableBackend.getHeight(), bitmapConfig);
            animatedImageCompositor.renderFrame(i, bitmap.get());
            bitmaps.add(bitmap);
        }
        return bitmaps;
    }

    @SuppressLint({"NewApi"})
    private CloseableReference<Bitmap> createBitmap(int width, int height, Bitmap.Config bitmapConfig) {
        CloseableReference<Bitmap> bitmap = this.mBitmapFactory.createBitmap(width, height, bitmapConfig);
        bitmap.get().eraseColor(0);
        if (Build.VERSION.SDK_INT >= 12) {
            bitmap.get().setHasAlpha(true);
        }
        return bitmap;
    }
}
