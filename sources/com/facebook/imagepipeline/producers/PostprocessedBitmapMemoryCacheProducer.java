package com.facebook.imagepipeline.producers;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.cache.MemoryCache;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import java.util.Map;

public class PostprocessedBitmapMemoryCacheProducer implements Producer<CloseableReference<CloseableImage>> {
    @VisibleForTesting
    static final String PRODUCER_NAME = "PostprocessedBitmapMemoryCacheProducer";
    @VisibleForTesting
    static final String VALUE_FOUND = "cached_value_found";
    private final CacheKeyFactory mCacheKeyFactory;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;
    private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

    public PostprocessedBitmapMemoryCacheProducer(MemoryCache<CacheKey, CloseableImage> memoryCache, CacheKeyFactory cacheKeyFactory, Producer<CloseableReference<CloseableImage>> inputProducer) {
        this.mMemoryCache = memoryCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        Map map = null;
        ProducerListener listener = producerContext.getListener();
        String requestId = producerContext.getId();
        ImageRequest imageRequest = producerContext.getImageRequest();
        Postprocessor postprocessor = imageRequest.getPostprocessor();
        if (postprocessor == null || postprocessor.getPostprocessorCacheKey() == null) {
            this.mInputProducer.produceResults(consumer, producerContext);
            return;
        }
        listener.onProducerStart(requestId, getProducerName());
        CacheKey cacheKey = this.mCacheKeyFactory.getPostprocessedBitmapCacheKey(imageRequest);
        CloseableReference<CloseableImage> cachedReference = this.mMemoryCache.get(cacheKey);
        if (cachedReference != null) {
            String producerName = getProducerName();
            if (listener.requiresExtraMap(requestId)) {
                map = ImmutableMap.of(VALUE_FOUND, "true");
            }
            listener.onProducerFinishWithSuccess(requestId, producerName, map);
            consumer.onProgressUpdate(1.0f);
            consumer.onNewResult(cachedReference, true);
            cachedReference.close();
            return;
        }
        Consumer<CloseableReference<CloseableImage>> cachedConsumer = new CachedPostprocessorConsumer(consumer, cacheKey, postprocessor instanceof RepeatedPostprocessor, this.mMemoryCache);
        String producerName2 = getProducerName();
        if (listener.requiresExtraMap(requestId)) {
            map = ImmutableMap.of(VALUE_FOUND, "false");
        }
        listener.onProducerFinishWithSuccess(requestId, producerName2, map);
        this.mInputProducer.produceResults(cachedConsumer, producerContext);
    }

    public static class CachedPostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private final CacheKey mCacheKey;
        private final boolean mIsRepeatedProcessor;
        private final MemoryCache<CacheKey, CloseableImage> mMemoryCache;

        public CachedPostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> consumer, CacheKey cacheKey, boolean isRepeatedProcessor, MemoryCache<CacheKey, CloseableImage> memoryCache) {
            super(consumer);
            this.mCacheKey = cacheKey;
            this.mIsRepeatedProcessor = isRepeatedProcessor;
            this.mMemoryCache = memoryCache;
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, boolean isLast) {
            if (newResult == null) {
                if (isLast) {
                    getConsumer().onNewResult(null, true);
                }
            } else if (isLast || this.mIsRepeatedProcessor) {
                CloseableReference<CloseableImage> newCachedResult = this.mMemoryCache.cache(this.mCacheKey, newResult);
                try {
                    getConsumer().onProgressUpdate(1.0f);
                    Consumer consumer = getConsumer();
                    if (newCachedResult != null) {
                        newResult = newCachedResult;
                    }
                    consumer.onNewResult(newResult, isLast);
                } finally {
                    CloseableReference.closeSafely((CloseableReference<?>) newCachedResult);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public String getProducerName() {
        return PRODUCER_NAME;
    }
}
