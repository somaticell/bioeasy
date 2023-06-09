package com.facebook.imagepipeline.producers;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.IOException;
import java.util.concurrent.Executor;

public class LocalAssetFetchProducer extends LocalFetchProducer {
    @VisibleForTesting
    static final String PRODUCER_NAME = "LocalAssetFetchProducer";
    private final AssetManager mAssetManager;

    public LocalAssetFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, AssetManager assetManager, boolean decodeFileDescriptorEnabled) {
        super(executor, pooledByteBufferFactory, decodeFileDescriptorEnabled);
        this.mAssetManager = assetManager;
    }

    /* access modifiers changed from: protected */
    public EncodedImage getEncodedImage(ImageRequest imageRequest) throws IOException {
        return getEncodedImage(this.mAssetManager.open(getAssetName(imageRequest), 2), getLength(imageRequest));
    }

    private int getLength(ImageRequest imageRequest) {
        int i;
        AssetFileDescriptor fd = null;
        try {
            fd = this.mAssetManager.openFd(getAssetName(imageRequest));
            i = (int) fd.getLength();
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e) {
                }
            }
        } catch (IOException e2) {
            i = -1;
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e3) {
                }
            }
        } catch (Throwable th) {
            if (fd != null) {
                try {
                    fd.close();
                } catch (IOException e4) {
                }
            }
            throw th;
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public String getProducerName() {
        return PRODUCER_NAME;
    }

    private static String getAssetName(ImageRequest imageRequest) {
        return imageRequest.getSourceUri().getPath().substring(1);
    }
}
