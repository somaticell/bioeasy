package com.facebook.imagepipeline.producers;

import android.os.Build;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public abstract class LocalFetchProducer implements Producer<EncodedImage> {
    private final boolean mDecodeFileDescriptorEnabledForKitKat;
    private final Executor mExecutor;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    /* access modifiers changed from: protected */
    public abstract EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException;

    /* access modifiers changed from: protected */
    public abstract String getProducerName();

    protected LocalFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, boolean fileDescriptorEnabled) {
        this.mExecutor = executor;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mDecodeFileDescriptorEnabledForKitKat = fileDescriptorEnabled && Build.VERSION.SDK_INT == 19;
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        ProducerListener listener = producerContext.getListener();
        String requestId = producerContext.getId();
        final ImageRequest imageRequest = producerContext.getImageRequest();
        final StatefulProducerRunnable cancellableProducerRunnable = new StatefulProducerRunnable<EncodedImage>(consumer, listener, getProducerName(), requestId) {
            /* access modifiers changed from: protected */
            public EncodedImage getResult() throws Exception {
                EncodedImage encodedImage = LocalFetchProducer.this.getEncodedImage(imageRequest);
                if (encodedImage == null) {
                    return null;
                }
                encodedImage.parseMetaData();
                return encodedImage;
            }

            /* access modifiers changed from: protected */
            public void disposeResult(EncodedImage result) {
                EncodedImage.closeSafely(result);
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(cancellableProducerRunnable);
    }

    /* access modifiers changed from: protected */
    public EncodedImage getByteBufferBackedEncodedImage(InputStream inputStream, int length) throws IOException {
        CloseableReference<PooledByteBuffer> ref;
        if (length < 0) {
            try {
                ref = CloseableReference.of(this.mPooledByteBufferFactory.newByteBuffer(inputStream));
            } catch (Throwable th) {
                Closeables.closeQuietly(inputStream);
                CloseableReference.closeSafely((CloseableReference<?>) null);
                throw th;
            }
        } else {
            ref = CloseableReference.of(this.mPooledByteBufferFactory.newByteBuffer(inputStream, length));
        }
        EncodedImage encodedImage = new EncodedImage(ref);
        Closeables.closeQuietly(inputStream);
        CloseableReference.closeSafely((CloseableReference<?>) ref);
        return encodedImage;
    }

    /* access modifiers changed from: protected */
    public EncodedImage getEncodedImage(InputStream inputStream, int length) throws IOException {
        Runtime runTime = Runtime.getRuntime();
        long javaMax = runTime.maxMemory();
        long javaFree = Math.min(javaMax - (runTime.totalMemory() - runTime.freeMemory()), 8388608);
        if (!this.mDecodeFileDescriptorEnabledForKitKat || !(inputStream instanceof FileInputStream) || javaMax < 64 * javaFree) {
            return getByteBufferBackedEncodedImage(inputStream, length);
        }
        return getInputStreamBackedEncodedImage(new File(inputStream.toString()), length);
    }

    /* access modifiers changed from: protected */
    public EncodedImage getInputStreamBackedEncodedImage(final File file, int length) throws IOException {
        return new EncodedImage(new Supplier<FileInputStream>() {
            public FileInputStream get() {
                try {
                    return new FileInputStream(file);
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }, length);
    }
}
