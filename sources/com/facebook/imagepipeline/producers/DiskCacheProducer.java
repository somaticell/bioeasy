package com.facebook.imagepipeline.producers;

import bolts.Continuation;
import bolts.Task;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.cache.BufferedDiskCache;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;

public class DiskCacheProducer implements Producer<EncodedImage> {
    @VisibleForTesting
    static final String PRODUCER_NAME = "DiskCacheProducer";
    @VisibleForTesting
    static final String VALUE_FOUND = "cached_value_found";
    private final CacheKeyFactory mCacheKeyFactory;
    private final BufferedDiskCache mDefaultBufferedDiskCache;
    private final Producer<EncodedImage> mInputProducer;
    private final BufferedDiskCache mSmallImageBufferedDiskCache;

    public DiskCacheProducer(BufferedDiskCache defaultBufferedDiskCache, BufferedDiskCache smallImageBufferedDiskCache, CacheKeyFactory cacheKeyFactory, Producer<EncodedImage> inputProducer) {
        this.mDefaultBufferedDiskCache = defaultBufferedDiskCache;
        this.mSmallImageBufferedDiskCache = smallImageBufferedDiskCache;
        this.mCacheKeyFactory = cacheKeyFactory;
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        ImageRequest imageRequest = producerContext.getImageRequest();
        if (!imageRequest.isDiskCacheEnabled()) {
            maybeStartInputProducer(consumer, consumer, producerContext);
            return;
        }
        final ProducerListener listener = producerContext.getListener();
        final String requestId = producerContext.getId();
        listener.onProducerStart(requestId, PRODUCER_NAME);
        final CacheKey cacheKey = this.mCacheKeyFactory.getEncodedCacheKey(imageRequest);
        final BufferedDiskCache cache = imageRequest.getImageType() == ImageRequest.ImageType.SMALL ? this.mSmallImageBufferedDiskCache : this.mDefaultBufferedDiskCache;
        final Consumer<EncodedImage> consumer2 = consumer;
        final ProducerContext producerContext2 = producerContext;
        Continuation<EncodedImage, Void> continuation = new Continuation<EncodedImage, Void>() {
            public Void then(Task<EncodedImage> task) throws Exception {
                if (task.isCancelled() || (task.isFaulted() && (task.getError() instanceof CancellationException))) {
                    listener.onProducerFinishWithCancellation(requestId, DiskCacheProducer.PRODUCER_NAME, (Map<String, String>) null);
                    consumer2.onCancellation();
                } else if (task.isFaulted()) {
                    listener.onProducerFinishWithFailure(requestId, DiskCacheProducer.PRODUCER_NAME, task.getError(), (Map<String, String>) null);
                    DiskCacheProducer.this.maybeStartInputProducer(consumer2, new DiskCacheConsumer(consumer2, cache, cacheKey), producerContext2);
                } else {
                    EncodedImage cachedReference = task.getResult();
                    if (cachedReference != null) {
                        listener.onProducerFinishWithSuccess(requestId, DiskCacheProducer.PRODUCER_NAME, DiskCacheProducer.getExtraMap(listener, requestId, true));
                        consumer2.onProgressUpdate(1.0f);
                        consumer2.onNewResult(cachedReference, true);
                        cachedReference.close();
                    } else {
                        listener.onProducerFinishWithSuccess(requestId, DiskCacheProducer.PRODUCER_NAME, DiskCacheProducer.getExtraMap(listener, requestId, false));
                        DiskCacheProducer.this.maybeStartInputProducer(consumer2, new DiskCacheConsumer(consumer2, cache, cacheKey), producerContext2);
                    }
                }
                return null;
            }
        };
        AtomicBoolean isCancelled = new AtomicBoolean(false);
        cache.get(cacheKey, isCancelled).continueWith(continuation);
        subscribeTaskForRequestCancellation(isCancelled, producerContext);
    }

    /* access modifiers changed from: private */
    public void maybeStartInputProducer(Consumer<EncodedImage> consumerOfDiskCacheProducer, Consumer<EncodedImage> consumerOfInputProducer, ProducerContext producerContext) {
        if (producerContext.getLowestPermittedRequestLevel().getValue() >= ImageRequest.RequestLevel.DISK_CACHE.getValue()) {
            consumerOfDiskCacheProducer.onNewResult(null, true);
        } else {
            this.mInputProducer.produceResults(consumerOfInputProducer, producerContext);
        }
    }

    @VisibleForTesting
    static Map<String, String> getExtraMap(ProducerListener listener, String requestId, boolean valueFound) {
        if (!listener.requiresExtraMap(requestId)) {
            return null;
        }
        return ImmutableMap.of(VALUE_FOUND, String.valueOf(valueFound));
    }

    private void subscribeTaskForRequestCancellation(final AtomicBoolean isCancelled, ProducerContext producerContext) {
        producerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                isCancelled.set(true);
            }
        });
    }

    private class DiskCacheConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final BufferedDiskCache mCache;
        private final CacheKey mCacheKey;

        private DiskCacheConsumer(Consumer<EncodedImage> consumer, BufferedDiskCache cache, CacheKey cacheKey) {
            super(consumer);
            this.mCache = cache;
            this.mCacheKey = cacheKey;
        }

        public void onNewResultImpl(EncodedImage newResult, boolean isLast) {
            if (newResult != null && isLast) {
                this.mCache.put(this.mCacheKey, newResult);
            }
            getConsumer().onNewResult(newResult, isLast);
        }
    }
}
