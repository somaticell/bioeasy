package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegParser;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.memory.ByteArrayPool;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class DecodeProducer implements Producer<CloseableReference<CloseableImage>> {
    private static final String BITMAP_SIZE_KEY = "bitmapSize";
    private static final String HAS_GOOD_QUALITY_KEY = "hasGoodQuality";
    private static final String IMAGE_TYPE_KEY = "imageType";
    private static final String IS_FINAL_KEY = "isFinal";
    public static final String PRODUCER_NAME = "DecodeProducer";
    private final ByteArrayPool mByteArrayPool;
    /* access modifiers changed from: private */
    public final boolean mDownsampleEnabled;
    /* access modifiers changed from: private */
    public final boolean mDownsampleEnabledForNetwork;
    /* access modifiers changed from: private */
    public final Executor mExecutor;
    /* access modifiers changed from: private */
    public final ImageDecoder mImageDecoder;
    private final Producer<EncodedImage> mInputProducer;
    private final ProgressiveJpegConfig mProgressiveJpegConfig;

    /* JADX WARNING: type inference failed for: r8v0, types: [com.facebook.imagepipeline.producers.Producer<com.facebook.imagepipeline.image.EncodedImage>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DecodeProducer(com.facebook.imagepipeline.memory.ByteArrayPool r2, java.util.concurrent.Executor r3, com.facebook.imagepipeline.decoder.ImageDecoder r4, com.facebook.imagepipeline.decoder.ProgressiveJpegConfig r5, boolean r6, boolean r7, com.facebook.imagepipeline.producers.Producer<com.facebook.imagepipeline.image.EncodedImage> r8) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r2)
            com.facebook.imagepipeline.memory.ByteArrayPool r0 = (com.facebook.imagepipeline.memory.ByteArrayPool) r0
            r1.mByteArrayPool = r0
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r3)
            java.util.concurrent.Executor r0 = (java.util.concurrent.Executor) r0
            r1.mExecutor = r0
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r4)
            com.facebook.imagepipeline.decoder.ImageDecoder r0 = (com.facebook.imagepipeline.decoder.ImageDecoder) r0
            r1.mImageDecoder = r0
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r5)
            com.facebook.imagepipeline.decoder.ProgressiveJpegConfig r0 = (com.facebook.imagepipeline.decoder.ProgressiveJpegConfig) r0
            r1.mProgressiveJpegConfig = r0
            r1.mDownsampleEnabled = r6
            r1.mDownsampleEnabledForNetwork = r7
            java.lang.Object r0 = com.facebook.common.internal.Preconditions.checkNotNull(r8)
            com.facebook.imagepipeline.producers.Producer r0 = (com.facebook.imagepipeline.producers.Producer) r0
            r1.mInputProducer = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.DecodeProducer.<init>(com.facebook.imagepipeline.memory.ByteArrayPool, java.util.concurrent.Executor, com.facebook.imagepipeline.decoder.ImageDecoder, com.facebook.imagepipeline.decoder.ProgressiveJpegConfig, boolean, boolean, com.facebook.imagepipeline.producers.Producer):void");
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
        ProgressiveDecoder progressiveDecoder;
        if (!UriUtil.isNetworkUri(producerContext.getImageRequest().getSourceUri())) {
            progressiveDecoder = new LocalImagesProgressiveDecoder(consumer, producerContext);
        } else {
            progressiveDecoder = new NetworkImagesProgressiveDecoder(consumer, producerContext, new ProgressiveJpegParser(this.mByteArrayPool), this.mProgressiveJpegConfig);
        }
        this.mInputProducer.produceResults(progressiveDecoder, producerContext);
    }

    private abstract class ProgressiveDecoder extends DelegatingConsumer<EncodedImage, CloseableReference<CloseableImage>> {
        private final ImageDecodeOptions mImageDecodeOptions;
        @GuardedBy("this")
        private boolean mIsFinished = false;
        /* access modifiers changed from: private */
        public final JobScheduler mJobScheduler;
        /* access modifiers changed from: private */
        public final ProducerContext mProducerContext;
        private final ProducerListener mProducerListener;

        /* access modifiers changed from: protected */
        public abstract int getIntermediateImageEndOffset(EncodedImage encodedImage);

        /* access modifiers changed from: protected */
        public abstract QualityInfo getQualityInfo();

        public ProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, final ProducerContext producerContext) {
            super(consumer);
            this.mProducerContext = producerContext;
            this.mProducerListener = producerContext.getListener();
            this.mImageDecodeOptions = producerContext.getImageRequest().getImageDecodeOptions();
            this.mJobScheduler = new JobScheduler(DecodeProducer.this.mExecutor, new JobScheduler.JobRunnable(DecodeProducer.this) {
                public void run(EncodedImage encodedImage, boolean isLast) {
                    if (encodedImage != null) {
                        if (DecodeProducer.this.mDownsampleEnabled) {
                            ImageRequest request = producerContext.getImageRequest();
                            if (DecodeProducer.this.mDownsampleEnabledForNetwork || !UriUtil.isNetworkUri(request.getSourceUri())) {
                                encodedImage.setSampleSize(DownsampleUtil.determineSampleSize(request, encodedImage));
                            }
                        }
                        ProgressiveDecoder.this.doDecode(encodedImage, isLast);
                    }
                }
            }, this.mImageDecodeOptions.minDecodeIntervalMs);
            this.mProducerContext.addCallbacks(new BaseProducerContextCallbacks(DecodeProducer.this) {
                public void onIsIntermediateResultExpectedChanged() {
                    if (ProgressiveDecoder.this.mProducerContext.isIntermediateResultExpected()) {
                        ProgressiveDecoder.this.mJobScheduler.scheduleJob();
                    }
                }
            });
        }

        public void onNewResultImpl(EncodedImage newResult, boolean isLast) {
            if (isLast && !EncodedImage.isValid(newResult)) {
                handleError(new NullPointerException("Encoded image is not valid."));
            } else if (!updateDecodeJob(newResult, isLast)) {
            } else {
                if (isLast || this.mProducerContext.isIntermediateResultExpected()) {
                    this.mJobScheduler.scheduleJob();
                }
            }
        }

        public void onFailureImpl(Throwable t) {
            handleError(t);
        }

        public void onCancellationImpl() {
            handleCancellation();
        }

        /* access modifiers changed from: protected */
        public boolean updateDecodeJob(EncodedImage ref, boolean isLast) {
            return this.mJobScheduler.updateJob(ref, isLast);
        }

        /* access modifiers changed from: private */
        public void doDecode(EncodedImage encodedImage, boolean isLast) {
            long queueTime;
            QualityInfo quality;
            if (!isFinished() && EncodedImage.isValid(encodedImage)) {
                try {
                    queueTime = this.mJobScheduler.getQueuedTime();
                    int length = isLast ? encodedImage.getSize() : getIntermediateImageEndOffset(encodedImage);
                    quality = isLast ? ImmutableQualityInfo.FULL_QUALITY : getQualityInfo();
                    this.mProducerListener.onProducerStart(this.mProducerContext.getId(), DecodeProducer.PRODUCER_NAME);
                    CloseableImage image = DecodeProducer.this.mImageDecoder.decodeImage(encodedImage, length, quality, this.mImageDecodeOptions);
                    this.mProducerListener.onProducerFinishWithSuccess(this.mProducerContext.getId(), DecodeProducer.PRODUCER_NAME, getExtraMap(image, queueTime, quality, isLast));
                    handleResult(image, isLast);
                } catch (Exception e) {
                    this.mProducerListener.onProducerFinishWithFailure(this.mProducerContext.getId(), DecodeProducer.PRODUCER_NAME, e, getExtraMap((CloseableImage) null, queueTime, quality, isLast));
                    handleError(e);
                } finally {
                    EncodedImage.closeSafely(encodedImage);
                }
            }
        }

        private Map<String, String> getExtraMap(@Nullable CloseableImage image, long queueTime, QualityInfo quality, boolean isFinal) {
            if (!this.mProducerListener.requiresExtraMap(this.mProducerContext.getId())) {
                return null;
            }
            String queueStr = String.valueOf(queueTime);
            String qualityStr = String.valueOf(quality.isOfGoodEnoughQuality());
            String finalStr = String.valueOf(isFinal);
            String imageTypeStr = String.valueOf(this.mProducerContext.getImageRequest().getImageType());
            if (!(image instanceof CloseableStaticBitmap)) {
                return ImmutableMap.of("queueTime", queueStr, DecodeProducer.HAS_GOOD_QUALITY_KEY, qualityStr, DecodeProducer.IS_FINAL_KEY, finalStr, DecodeProducer.IMAGE_TYPE_KEY, imageTypeStr);
            }
            Bitmap bitmap = ((CloseableStaticBitmap) image).getUnderlyingBitmap();
            return ImmutableMap.of(DecodeProducer.BITMAP_SIZE_KEY, bitmap.getWidth() + "x" + bitmap.getHeight(), "queueTime", queueStr, DecodeProducer.HAS_GOOD_QUALITY_KEY, qualityStr, DecodeProducer.IS_FINAL_KEY, finalStr, DecodeProducer.IMAGE_TYPE_KEY, imageTypeStr);
        }

        private synchronized boolean isFinished() {
            return this.mIsFinished;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void maybeFinish(boolean r2) {
            /*
                r1 = this;
                monitor-enter(r1)
                if (r2 == 0) goto L_0x0007
                boolean r0 = r1.mIsFinished     // Catch:{ all -> 0x0013 }
                if (r0 == 0) goto L_0x0009
            L_0x0007:
                monitor-exit(r1)     // Catch:{ all -> 0x0013 }
            L_0x0008:
                return
            L_0x0009:
                r0 = 1
                r1.mIsFinished = r0     // Catch:{ all -> 0x0013 }
                monitor-exit(r1)     // Catch:{ all -> 0x0013 }
                com.facebook.imagepipeline.producers.JobScheduler r0 = r1.mJobScheduler
                r0.clearJob()
                goto L_0x0008
            L_0x0013:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0013 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder.maybeFinish(boolean):void");
        }

        private void handleResult(CloseableImage decodedImage, boolean isFinal) {
            CloseableReference<CloseableImage> decodedImageRef = CloseableReference.of(decodedImage);
            try {
                maybeFinish(isFinal);
                getConsumer().onNewResult(decodedImageRef, isFinal);
            } finally {
                CloseableReference.closeSafely((CloseableReference<?>) decodedImageRef);
            }
        }

        private void handleError(Throwable t) {
            maybeFinish(true);
            getConsumer().onFailure(t);
        }

        private void handleCancellation() {
            maybeFinish(true);
            getConsumer().onCancellation();
        }
    }

    private class LocalImagesProgressiveDecoder extends ProgressiveDecoder {
        public LocalImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext) {
            super(consumer, producerContext);
        }

        /* access modifiers changed from: protected */
        public synchronized boolean updateDecodeJob(EncodedImage encodedImage, boolean isLast) {
            boolean updateDecodeJob;
            if (!isLast) {
                updateDecodeJob = false;
            } else {
                updateDecodeJob = super.updateDecodeJob(encodedImage, isLast);
            }
            return updateDecodeJob;
        }

        /* access modifiers changed from: protected */
        public int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return encodedImage.getSize();
        }

        /* access modifiers changed from: protected */
        public QualityInfo getQualityInfo() {
            return ImmutableQualityInfo.of(0, false, false);
        }
    }

    private class NetworkImagesProgressiveDecoder extends ProgressiveDecoder {
        private int mLastScheduledScanNumber = 0;
        private final ProgressiveJpegConfig mProgressiveJpegConfig;
        private final ProgressiveJpegParser mProgressiveJpegParser;

        public NetworkImagesProgressiveDecoder(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext producerContext, ProgressiveJpegParser progressiveJpegParser, ProgressiveJpegConfig progressiveJpegConfig) {
            super(consumer, producerContext);
            this.mProgressiveJpegParser = (ProgressiveJpegParser) Preconditions.checkNotNull(progressiveJpegParser);
            this.mProgressiveJpegConfig = (ProgressiveJpegConfig) Preconditions.checkNotNull(progressiveJpegConfig);
        }

        /* access modifiers changed from: protected */
        public synchronized boolean updateDecodeJob(EncodedImage encodedImage, boolean isLast) {
            boolean ret;
            ret = super.updateDecodeJob(encodedImage, isLast);
            if (!isLast && EncodedImage.isValid(encodedImage)) {
                if (!this.mProgressiveJpegParser.parseMoreData(encodedImage)) {
                    ret = false;
                } else {
                    int scanNum = this.mProgressiveJpegParser.getBestScanNumber();
                    if (scanNum <= this.mLastScheduledScanNumber || scanNum < this.mProgressiveJpegConfig.getNextScanNumberToDecode(this.mLastScheduledScanNumber)) {
                        ret = false;
                    } else {
                        this.mLastScheduledScanNumber = scanNum;
                    }
                }
            }
            return ret;
        }

        /* access modifiers changed from: protected */
        public int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return this.mProgressiveJpegParser.getBestScanEndOffset();
        }

        /* access modifiers changed from: protected */
        public QualityInfo getQualityInfo() {
            return this.mProgressiveJpegConfig.getQualityInfo(this.mProgressiveJpegParser.getBestScanNumber());
        }
    }
}
