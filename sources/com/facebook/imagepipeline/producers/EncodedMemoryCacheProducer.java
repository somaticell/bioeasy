package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Map;

public class EncodedMemoryCacheProducer implements Producer<EncodedImage> {
    @VisibleForTesting
    static final String PRODUCER_NAME = "EncodedMemoryCacheProducer";
    @VisibleForTesting
    static final String VALUE_FOUND = "cached_value_found";
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<EncodedImage> mInputProducer;
    /* access modifiers changed from: private */
    public final MemoryCache<CacheKey, PooledByteBuffer> mMemoryCache;

    public EncodedMemoryCacheProducer(MemoryCache<CacheKey, PooledByteBuffer> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<EncodedImage> inputProducer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        EncodedImage cachedEncodedImage;
        Map map = null;
        String requestId = producerContext.getId();
        ProducerListener listener = producerContext.getListener();
        listener.onProducerStart(requestId, PRODUCER_NAME);
        final CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(producerContext.getImageRequest());
        CloseableReference<PooledByteBuffer> cachedReference = this.mMemoryCache.get(cacheKey);
        if (cachedReference != null) {
            try {
                cachedEncodedImage = new EncodedImage(cachedReference);
                if (listener.requiresExtraMap(requestId)) {
                    map = ImmutableMap.of(VALUE_FOUND, "true");
                }
                listener.onProducerFinishWithSuccess(requestId, PRODUCER_NAME, map);
                consumer.onProgressUpdate(1.0f);
                consumer.onNewResult(cachedEncodedImage, true);
                EncodedImage.closeSafely(cachedEncodedImage);
                CloseableReference.closeSafely((CloseableReference<?>) cachedReference);
            } catch (Throwable th) {
                CloseableReference.closeSafely((CloseableReference<?>) cachedReference);
                throw th;
            }
        } else if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.ENCODED_MEMORY_CACHE.getValue()) {
            if (listener.requiresExtraMap(requestId)) {
                map = ImmutableMap.of(VALUE_FOUND, "false");
            }
            listener.onProducerFinishWithSuccess(requestId, PRODUCER_NAME, map);
            consumer.onNewResult(null, true);
            CloseableReference.closeSafely((CloseableReference<?>) cachedReference);
        } else {
            Consumer<EncodedImage> consumerOfInputProducer = new DelegatingConsumer<EncodedImage, EncodedImage>(consumer) {
                public void onNewResultImpl(EncodedImage newResult, boolean isLast) {
                    if (!isLast || newResult == null) {
                        getConsumer().onNewResult(newResult, isLast);
                        return;
                    }
                    CloseableReference<PooledByteBuffer> ref = newResult.getByteBufferRef();
                    if (ref != null) {
                        try {
                            CloseableReference<PooledByteBuffer> cachedResult = EncodedMemoryCacheProducer.this.mMemoryCache.cache(cacheKey, ref);
                            if (cachedResult != null) {
                                try {
                                    EncodedImage cachedEncodedImage = new EncodedImage(cachedResult);
                                    cachedEncodedImage.copyMetaDataFrom(newResult);
                                    try {
                                        getConsumer().onProgressUpdate(1.0f);
                                        getConsumer().onNewResult(cachedEncodedImage, true);
                                        return;
                                    } finally {
                                        EncodedImage.closeSafely(cachedEncodedImage);
                                    }
                                } finally {
                                    CloseableReference.closeSafely((CloseableReference<?>) cachedResult);
                                }
                            }
                        } finally {
                            CloseableReference.closeSafely((CloseableReference<?>) ref);
                        }
                    }
                    getConsumer().onNewResult(newResult, true);
                }
            };
            if (listener.requiresExtraMap(requestId)) {
                map = ImmutableMap.of(VALUE_FOUND, "false");
            }
            listener.onProducerFinishWithSuccess(requestId, PRODUCER_NAME, map);
            this.mInputProducer.produceResults(consumerOfInputProducer, producerContext);
            CloseableReference.closeSafely((CloseableReference<?>) cachedReference);
        }
    }
}
