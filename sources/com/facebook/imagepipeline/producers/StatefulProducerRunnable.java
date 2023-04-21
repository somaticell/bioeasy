package com.facebook.imagepipeline.producers;

import com.facebook.common.executors.StatefulRunnable;
import java.util.Map;

public abstract class StatefulProducerRunnable<T> extends StatefulRunnable<T> {
    private final Consumer<T> mConsumer;
    private final ProducerListener mProducerListener;
    private final String mProducerName;
    private final String mRequestId;

    /* access modifiers changed from: protected */
    public abstract void disposeResult(T t);

    public StatefulProducerRunnable(Consumer<T> consumer, ProducerListener producerListener, String producerName, String requestId) {
        this.mConsumer = consumer;
        this.mProducerListener = producerListener;
        this.mProducerName = producerName;
        this.mRequestId = requestId;
        this.mProducerListener.onProducerStart(this.mRequestId, this.mProducerName);
    }

    /* access modifiers changed from: protected */
    public void onSuccess(T result) {
        this.mProducerListener.onProducerFinishWithSuccess(this.mRequestId, this.mProducerName, this.mProducerListener.requiresExtraMap(this.mRequestId) ? getExtraMapOnSuccess(result) : null);
        this.mConsumer.onNewResult(result, true);
    }

    /* access modifiers changed from: protected */
    public void onFailure(Exception e) {
        this.mProducerListener.onProducerFinishWithFailure(this.mRequestId, this.mProducerName, e, this.mProducerListener.requiresExtraMap(this.mRequestId) ? getExtraMapOnFailure(e) : null);
        this.mConsumer.onFailure(e);
    }

    /* access modifiers changed from: protected */
    public void onCancellation() {
        this.mProducerListener.onProducerFinishWithCancellation(this.mRequestId, this.mProducerName, this.mProducerListener.requiresExtraMap(this.mRequestId) ? getExtraMapOnCancellation() : null);
        this.mConsumer.onCancellation();
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getExtraMapOnSuccess(T t) {
        return null;
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getExtraMapOnFailure(Exception exception) {
        return null;
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getExtraMapOnCancellation() {
        return null;
    }
}
