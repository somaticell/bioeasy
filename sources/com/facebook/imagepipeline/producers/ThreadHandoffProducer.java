package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import java.util.Map;

public class ThreadHandoffProducer<T> implements Producer<T> {
    @VisibleForTesting
    protected static final String PRODUCER_NAME = "BackgroundThreadHandoffProducer";
    /* access modifiers changed from: private */
    public final Producer<T> mInputProducer;
    /* access modifiers changed from: private */
    public final ThreadHandoffProducerQueue mThreadHandoffProducerQueue;

    public ThreadHandoffProducer(Producer<T> inputProducer, ThreadHandoffProducerQueue inputThreadHandoffProducerQueue) {
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
        this.mThreadHandoffProducerQueue = inputThreadHandoffProducerQueue;
    }

    public void produceResults(Consumer<T> consumer, ProducerContext context) {
        ProducerListener producerListener = context.getListener();
        String requestId = context.getId();
        final ProducerListener producerListener2 = producerListener;
        final String str = requestId;
        final Consumer<T> consumer2 = consumer;
        final ProducerContext producerContext = context;
        final StatefulProducerRunnable<T> statefulRunnable = new StatefulProducerRunnable<T>(consumer, producerListener, PRODUCER_NAME, requestId) {
            /* access modifiers changed from: protected */
            public void onSuccess(T t) {
                producerListener2.onProducerFinishWithSuccess(str, ThreadHandoffProducer.PRODUCER_NAME, (Map<String, String>) null);
                ThreadHandoffProducer.this.mInputProducer.produceResults(consumer2, producerContext);
            }

            /* access modifiers changed from: protected */
            public void disposeResult(T t) {
            }

            /* access modifiers changed from: protected */
            public T getResult() throws Exception {
                return null;
            }
        };
        context.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                statefulRunnable.cancel();
                ThreadHandoffProducer.this.mThreadHandoffProducerQueue.remove(statefulRunnable);
            }
        });
        this.mThreadHandoffProducerQueue.addToQueueOrExecute(statefulRunnable);
    }
}
