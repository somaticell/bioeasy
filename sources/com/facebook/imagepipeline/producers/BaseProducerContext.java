package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class BaseProducerContext implements ProducerContext {
    @GuardedBy("this")
    private final List<ProducerContextCallbacks> mCallbacks = new ArrayList();
    private final Object mCallerContext;
    private final String mId;
    private final ImageRequest mImageRequest;
    @GuardedBy("this")
    private boolean mIsCancelled = false;
    @GuardedBy("this")
    private boolean mIsIntermediateResultExpected;
    @GuardedBy("this")
    private boolean mIsPrefetch;
    private final ImageRequest.RequestLevel mLowestPermittedRequestLevel;
    @GuardedBy("this")
    private Priority mPriority;
    private final ProducerListener mProducerListener;

    public BaseProducerContext(ImageRequest imageRequest, String id, ProducerListener producerListener, Object callerContext, ImageRequest.RequestLevel lowestPermittedRequestLevel, boolean isPrefetch, boolean isIntermediateResultExpected, Priority priority) {
        this.mImageRequest = imageRequest;
        this.mId = id;
        this.mProducerListener = producerListener;
        this.mCallerContext = callerContext;
        this.mLowestPermittedRequestLevel = lowestPermittedRequestLevel;
        this.mIsPrefetch = isPrefetch;
        this.mPriority = priority;
        this.mIsIntermediateResultExpected = isIntermediateResultExpected;
    }

    public ImageRequest getImageRequest() {
        return this.mImageRequest;
    }

    public String getId() {
        return this.mId;
    }

    public ProducerListener getListener() {
        return this.mProducerListener;
    }

    public Object getCallerContext() {
        return this.mCallerContext;
    }

    public ImageRequest.RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    public synchronized boolean isPrefetch() {
        return this.mIsPrefetch;
    }

    public synchronized Priority getPriority() {
        return this.mPriority;
    }

    public synchronized boolean isIntermediateResultExpected() {
        return this.mIsIntermediateResultExpected;
    }

    public synchronized boolean isCancelled() {
        return this.mIsCancelled;
    }

    public void addCallbacks(ProducerContextCallbacks callbacks) {
        boolean cancelImmediately = false;
        synchronized (this) {
            this.mCallbacks.add(callbacks);
            if (this.mIsCancelled) {
                cancelImmediately = true;
            }
        }
        if (cancelImmediately) {
            callbacks.onCancellationRequested();
        }
    }

    public void cancel() {
        callOnCancellationRequested(cancelNoCallbacks());
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> setIsPrefetchNoCallbacks(boolean isPrefetch) {
        ArrayList arrayList;
        if (isPrefetch == this.mIsPrefetch) {
            arrayList = null;
        } else {
            this.mIsPrefetch = isPrefetch;
            arrayList = new ArrayList(this.mCallbacks);
        }
        return arrayList;
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> setPriorityNoCallbacks(Priority priority) {
        ArrayList arrayList;
        if (priority == this.mPriority) {
            arrayList = null;
        } else {
            this.mPriority = priority;
            arrayList = new ArrayList(this.mCallbacks);
        }
        return arrayList;
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> setIsIntermediateResultExpectedNoCallbacks(boolean isIntermediateResultExpected) {
        ArrayList arrayList;
        if (isIntermediateResultExpected == this.mIsIntermediateResultExpected) {
            arrayList = null;
        } else {
            this.mIsIntermediateResultExpected = isIntermediateResultExpected;
            arrayList = new ArrayList(this.mCallbacks);
        }
        return arrayList;
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> cancelNoCallbacks() {
        ArrayList arrayList;
        if (this.mIsCancelled) {
            arrayList = null;
        } else {
            this.mIsCancelled = true;
            arrayList = new ArrayList(this.mCallbacks);
        }
        return arrayList;
    }

    public static void callOnCancellationRequested(@Nullable List<ProducerContextCallbacks> callbacks) {
        if (callbacks != null) {
            for (ProducerContextCallbacks callback : callbacks) {
                callback.onCancellationRequested();
            }
        }
    }

    public static void callOnIsPrefetchChanged(@Nullable List<ProducerContextCallbacks> callbacks) {
        if (callbacks != null) {
            for (ProducerContextCallbacks callback : callbacks) {
                callback.onIsPrefetchChanged();
            }
        }
    }

    public static void callOnIsIntermediateResultExpectedChanged(@Nullable List<ProducerContextCallbacks> callbacks) {
        if (callbacks != null) {
            for (ProducerContextCallbacks callback : callbacks) {
                callback.onIsIntermediateResultExpectedChanged();
            }
        }
    }

    public static void callOnPriorityChanged(@Nullable List<ProducerContextCallbacks> callbacks) {
        if (callbacks != null) {
            for (ProducerContextCallbacks callback : callbacks) {
                callback.onPriorityChanged();
            }
        }
    }
}
