package com.facebook.imagepipeline.platform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.common.TooManyBitmapsException;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.BitmapCounter;
import com.facebook.imagepipeline.memory.BitmapCounterProvider;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.nativecode.Bitmaps;

abstract class DalvikPurgeableDecoder implements PlatformDecoder {
    protected static final byte[] EOI = {-1, -39};
    private final BitmapCounter mUnpooledBitmapsCounter = BitmapCounterProvider.get();

    /* access modifiers changed from: package-private */
    public abstract Bitmap decodeByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> closeableReference, BitmapFactory.Options options);

    /* access modifiers changed from: package-private */
    public abstract Bitmap decodeJPEGByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> closeableReference, int i, BitmapFactory.Options options);

    DalvikPurgeableDecoder() {
    }

    public CloseableReference<Bitmap> decodeFromEncodedImage(EncodedImage encodedImage, Bitmap.Config bitmapConfig) {
        BitmapFactory.Options options = getBitmapFactoryOptions(encodedImage.getSampleSize(), bitmapConfig);
        CloseableReference<PooledByteBuffer> bytesRef = encodedImage.getByteBufferRef();
        Preconditions.checkNotNull(bytesRef);
        try {
            return pinBitmap(decodeByteArrayAsPurgeable(bytesRef, options));
        } finally {
            CloseableReference.closeSafely((CloseableReference<?>) bytesRef);
        }
    }

    public CloseableReference<Bitmap> decodeJPEGFromEncodedImage(EncodedImage encodedImage, Bitmap.Config bitmapConfig, int length) {
        BitmapFactory.Options options = getBitmapFactoryOptions(encodedImage.getSampleSize(), bitmapConfig);
        CloseableReference<PooledByteBuffer> bytesRef = encodedImage.getByteBufferRef();
        Preconditions.checkNotNull(bytesRef);
        try {
            return pinBitmap(decodeJPEGByteArrayAsPurgeable(bytesRef, length, options));
        } finally {
            CloseableReference.closeSafely((CloseableReference<?>) bytesRef);
        }
    }

    private static BitmapFactory.Options getBitmapFactoryOptions(int sampleSize, Bitmap.Config bitmapConfig) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = true;
        options.inPreferredConfig = bitmapConfig;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inSampleSize = sampleSize;
        if (Build.VERSION.SDK_INT >= 11) {
            options.inMutable = true;
        }
        return options;
    }

    protected static boolean endsWithEOI(CloseableReference<PooledByteBuffer> bytesRef, int length) {
        PooledByteBuffer buffer = bytesRef.get();
        return length >= 2 && buffer.read(length + -2) == -1 && buffer.read(length + -1) == -39;
    }

    public CloseableReference<Bitmap> pinBitmap(Bitmap bitmap) {
        try {
            Bitmaps.pinBitmap(bitmap);
            if (this.mUnpooledBitmapsCounter.increase(bitmap)) {
                return CloseableReference.of(bitmap, this.mUnpooledBitmapsCounter.getReleaser());
            }
            bitmap.recycle();
            throw new TooManyBitmapsException();
        } catch (Exception e) {
            bitmap.recycle();
            throw Throwables.propagate(e);
        }
    }
}
