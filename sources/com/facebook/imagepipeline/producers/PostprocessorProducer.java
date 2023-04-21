package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessorRunner;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class PostprocessorProducer implements Producer<CloseableReference<CloseableImage>> {
    @VisibleForTesting
    static final String NAME = "PostprocessorProducer";
    @VisibleForTesting
    static final String POSTPROCESSOR = "Postprocessor";
    /* access modifiers changed from: private */
    public final PlatformBitmapFactory mBitmapFactory;
    /* access modifiers changed from: private */
    public final Executor mExecutor;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;

    /* JADX WARNING: type inference failed for: r2v0, types: [com.facebook.imagepipeline.producers.Producer<com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage>>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PostprocessorProducer(com.facebook.imagepipeline.producers.Producer<com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage>> r2, com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory r3, java.util.concurrent.Executor r4) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r2)
            com.facebook.imagepipeline.producers.Producer r0 = (com.facebook.imagepipeline.producers.Producer) r0
            r1.mInputProducer = r0
            r1.mBitmapFactory = r3
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r4)
            java.util.concurrent.Executor r0 = (java.util.concurrent.Executor) r0
            r1.mExecutor = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.PostprocessorProducer.<init>(com.facebook.imagepipeline.producers.Producer, com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory, java.util.concurrent.Executor):void");
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext context) {
        Consumer<CloseableReference<CloseableImage>> postprocessorConsumer;
        ProducerListener listener = context.getListener();
        Postprocessor postprocessor = context.getImageRequest().getPostprocessor();
        PostprocessorConsumer basePostprocessorConsumer = new PostprocessorConsumer(consumer, listener, context.getId(), postprocessor, context);
        if (postprocessor instanceof RepeatedPostprocessor) {
            postprocessorConsumer = new RepeatedPostprocessorConsumer(basePostprocessorConsumer, (RepeatedPostprocessor) postprocessor, context);
        } else {
            postprocessorConsumer = new SingleUsePostprocessorConsumer(basePostprocessorConsumer);
        }
        this.mInputProducer.produceResults(postprocessorConsumer, context);
    }

    private class PostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        @GuardedBy("PostprocessorConsumer.this")
        private boolean mIsClosed;
        /* access modifiers changed from: private */
        @GuardedBy("PostprocessorConsumer.this")
        public boolean mIsDirty = false;
        /* access modifiers changed from: private */
        @GuardedBy("PostprocessorConsumer.this")
        public boolean mIsLast = false;
        @GuardedBy("PostprocessorConsumer.this")
        private boolean mIsPostProcessingRunning = false;
        private final ProducerListener mListener;
        private final Postprocessor mPostprocessor;
        private final String mRequestId;
        /* access modifiers changed from: private */
        @GuardedBy("PostprocessorConsumer.this")
        @Nullable
        public CloseableReference<CloseableImage> mSourceImageRef = null;

        public PostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> consumer, ProducerListener listener, String requestId, Postprocessor postprocessor, ProducerContext producerContext) {
            super(consumer);
            this.mListener = listener;
            this.mRequestId = requestId;
            this.mPostprocessor = postprocessor;
            producerContext.addCallbacks(new BaseProducerContextCallbacks(PostprocessorProducer.this) {
                public void onCancellationRequested() {
                    PostprocessorConsumer.this.maybeNotifyOnCancellation();
                }
            });
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, boolean isLast) {
            if (CloseableReference.isValid(newResult)) {
                updateSourceImageRef(newResult, isLast);
            } else if (isLast) {
                maybeNotifyOnNewResult((CloseableReference<CloseableImage>) null, true);
            }
        }

        /* access modifiers changed from: protected */
        public void onFailureImpl(Throwable t) {
            maybeNotifyOnFailure(t);
        }

        /* access modifiers changed from: protected */
        public void onCancellationImpl() {
            maybeNotifyOnCancellation();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0019, code lost:
            com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x001c, code lost:
            if (r1 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x001e, code lost:
            submitPostprocessing();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void updateSourceImageRef(@javax.annotation.Nullable com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage> r4, boolean r5) {
            /*
                r3 = this;
                monitor-enter(r3)
                boolean r2 = r3.mIsClosed     // Catch:{ all -> 0x0022 }
                if (r2 == 0) goto L_0x0007
                monitor-exit(r3)     // Catch:{ all -> 0x0022 }
            L_0x0006:
                return
            L_0x0007:
                com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage> r0 = r3.mSourceImageRef     // Catch:{ all -> 0x0022 }
                com.facebook.common.references.CloseableReference r2 = com.facebook.common.references.CloseableReference.cloneOrNull(r4)     // Catch:{ all -> 0x0022 }
                r3.mSourceImageRef = r2     // Catch:{ all -> 0x0022 }
                r3.mIsLast = r5     // Catch:{ all -> 0x0022 }
                r2 = 1
                r3.mIsDirty = r2     // Catch:{ all -> 0x0022 }
                boolean r1 = r3.setRunningIfDirtyAndNotRunning()     // Catch:{ all -> 0x0022 }
                monitor-exit(r3)     // Catch:{ all -> 0x0022 }
                com.facebook.common.references.CloseableReference.closeSafely((com.facebook.common.references.CloseableReference<?>) r0)
                if (r1 == 0) goto L_0x0006
                r3.submitPostprocessing()
                goto L_0x0006
            L_0x0022:
                r2 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0022 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.PostprocessorProducer.PostprocessorConsumer.updateSourceImageRef(com.facebook.common.references.CloseableReference, boolean):void");
        }

        private void submitPostprocessing() {
            PostprocessorProducer.this.mExecutor.execute(new Runnable() {
                public void run() {
                    CloseableReference<CloseableImage> closeableImageRef;
                    boolean isLast;
                    synchronized (PostprocessorConsumer.this) {
                        closeableImageRef = PostprocessorConsumer.this.mSourceImageRef;
                        isLast = PostprocessorConsumer.this.mIsLast;
                        CloseableReference unused = PostprocessorConsumer.this.mSourceImageRef = null;
                        boolean unused2 = PostprocessorConsumer.this.mIsDirty = false;
                    }
                    if (CloseableReference.isValid(closeableImageRef)) {
                        try {
                            PostprocessorConsumer.this.doPostprocessing(closeableImageRef, isLast);
                        } finally {
                            CloseableReference.closeSafely((CloseableReference<?>) closeableImageRef);
                        }
                    }
                    PostprocessorConsumer.this.clearRunningAndStartIfDirty();
                }
            });
        }

        /* access modifiers changed from: private */
        public void clearRunningAndStartIfDirty() {
            boolean shouldExecuteAgain;
            synchronized (this) {
                this.mIsPostProcessingRunning = false;
                shouldExecuteAgain = setRunningIfDirtyAndNotRunning();
            }
            if (shouldExecuteAgain) {
                submitPostprocessing();
            }
        }

        private synchronized boolean setRunningIfDirtyAndNotRunning() {
            boolean z = true;
            synchronized (this) {
                if (this.mIsClosed || !this.mIsDirty || this.mIsPostProcessingRunning || !CloseableReference.isValid(this.mSourceImageRef)) {
                    z = false;
                } else {
                    this.mIsPostProcessingRunning = true;
                }
            }
            return z;
        }

        /* access modifiers changed from: private */
        public void doPostprocessing(CloseableReference<CloseableImage> sourceImageRef, boolean isLast) {
            Preconditions.checkArgument(CloseableReference.isValid(sourceImageRef));
            if (!shouldPostprocess(sourceImageRef.get())) {
                maybeNotifyOnNewResult(sourceImageRef, isLast);
                return;
            }
            this.mListener.onProducerStart(this.mRequestId, PostprocessorProducer.NAME);
            CloseableReference<CloseableImage> destImageRef = null;
            try {
                destImageRef = postprocessInternal(sourceImageRef.get());
                this.mListener.onProducerFinishWithSuccess(this.mRequestId, PostprocessorProducer.NAME, getExtraMap(this.mListener, this.mRequestId, this.mPostprocessor));
                maybeNotifyOnNewResult(destImageRef, isLast);
            } catch (Exception e) {
                this.mListener.onProducerFinishWithFailure(this.mRequestId, PostprocessorProducer.NAME, e, getExtraMap(this.mListener, this.mRequestId, this.mPostprocessor));
                maybeNotifyOnFailure(e);
            } finally {
                CloseableReference.closeSafely((CloseableReference<?>) destImageRef);
            }
        }

        private Map<String, String> getExtraMap(ProducerListener listener, String requestId, Postprocessor postprocessor) {
            if (!listener.requiresExtraMap(requestId)) {
                return null;
            }
            return ImmutableMap.of(PostprocessorProducer.POSTPROCESSOR, postprocessor.getName());
        }

        private boolean shouldPostprocess(CloseableImage sourceImage) {
            return sourceImage instanceof CloseableStaticBitmap;
        }

        private CloseableReference<CloseableImage> postprocessInternal(CloseableImage sourceImage) {
            CloseableStaticBitmap staticBitmap = (CloseableStaticBitmap) sourceImage;
            CloseableReference<Bitmap> bitmapRef = this.mPostprocessor.process(staticBitmap.getUnderlyingBitmap(), PostprocessorProducer.this.mBitmapFactory);
            try {
                return CloseableReference.of(new CloseableStaticBitmap(bitmapRef, sourceImage.getQualityInfo(), staticBitmap.getRotationAngle()));
            } finally {
                CloseableReference.closeSafely((CloseableReference<?>) bitmapRef);
            }
        }

        private void maybeNotifyOnNewResult(CloseableReference<CloseableImage> newRef, boolean isLast) {
            if ((!isLast && !isClosed()) || (isLast && close())) {
                getConsumer().onNewResult(newRef, isLast);
            }
        }

        private void maybeNotifyOnFailure(Throwable throwable) {
            if (close()) {
                getConsumer().onFailure(throwable);
            }
        }

        /* access modifiers changed from: private */
        public void maybeNotifyOnCancellation() {
            if (close()) {
                getConsumer().onCancellation();
            }
        }

        private synchronized boolean isClosed() {
            return this.mIsClosed;
        }

        private boolean close() {
            boolean z = true;
            synchronized (this) {
                if (this.mIsClosed) {
                    z = false;
                } else {
                    CloseableReference<CloseableImage> oldSourceImageRef = this.mSourceImageRef;
                    this.mSourceImageRef = null;
                    this.mIsClosed = true;
                    CloseableReference.closeSafely((CloseableReference<?>) oldSourceImageRef);
                }
            }
            return z;
        }
    }

    class SingleUsePostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private SingleUsePostprocessorConsumer(PostprocessorConsumer postprocessorConsumer) {
            super(postprocessorConsumer);
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, boolean isLast) {
            if (isLast) {
                getConsumer().onNewResult(newResult, isLast);
            }
        }
    }

    class RepeatedPostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> implements RepeatedPostprocessorRunner {
        @GuardedBy("RepeatedPostprocessorConsumer.this")
        private boolean mIsClosed;
        @GuardedBy("RepeatedPostprocessorConsumer.this")
        @Nullable
        private CloseableReference<CloseableImage> mSourceImageRef;

        private RepeatedPostprocessorConsumer(PostprocessorConsumer postprocessorConsumer, RepeatedPostprocessor repeatedPostprocessor, ProducerContext context) {
            super(postprocessorConsumer);
            this.mIsClosed = false;
            this.mSourceImageRef = null;
            repeatedPostprocessor.setCallback(this);
            context.addCallbacks(new BaseProducerContextCallbacks(PostprocessorProducer.this) {
                public void onCancellationRequested() {
                    if (RepeatedPostprocessorConsumer.this.close()) {
                        RepeatedPostprocessorConsumer.this.getConsumer().onCancellation();
                    }
                }
            });
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(CloseableReference<CloseableImage> newResult, boolean isLast) {
            if (isLast) {
                setSourceImageRef(newResult);
                updateInternal();
            }
        }

        /* access modifiers changed from: protected */
        public void onFailureImpl(Throwable throwable) {
            if (close()) {
                getConsumer().onFailure(throwable);
            }
        }

        /* access modifiers changed from: protected */
        public void onCancellationImpl() {
            if (close()) {
                getConsumer().onCancellation();
            }
        }

        public synchronized void update() {
            updateInternal();
        }

        private void updateInternal() {
            synchronized (this) {
                if (!this.mIsClosed) {
                    CloseableReference<CloseableImage> sourceImageRef = CloseableReference.cloneOrNull(this.mSourceImageRef);
                    try {
                        getConsumer().onNewResult(sourceImageRef, false);
                    } finally {
                        CloseableReference.closeSafely((CloseableReference<?>) sourceImageRef);
                    }
                }
            }
        }

        private void setSourceImageRef(CloseableReference<CloseableImage> sourceImageRef) {
            synchronized (this) {
                if (!this.mIsClosed) {
                    CloseableReference<CloseableImage> oldSourceImageRef = this.mSourceImageRef;
                    this.mSourceImageRef = CloseableReference.cloneOrNull(sourceImageRef);
                    CloseableReference.closeSafely((CloseableReference<?>) oldSourceImageRef);
                }
            }
        }

        /* access modifiers changed from: private */
        public boolean close() {
            boolean z = true;
            synchronized (this) {
                if (this.mIsClosed) {
                    z = false;
                } else {
                    CloseableReference<CloseableImage> oldSourceImageRef = this.mSourceImageRef;
                    this.mSourceImageRef = null;
                    this.mIsClosed = true;
                    CloseableReference.closeSafely((CloseableReference<?>) oldSourceImageRef);
                }
            }
            return z;
        }
    }
}
