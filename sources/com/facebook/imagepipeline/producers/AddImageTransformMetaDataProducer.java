package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.image.EncodedImage;

public class AddImageTransformMetaDataProducer implements Producer<EncodedImage> {
    private final Producer<EncodedImage> mInputProducer;

    public AddImageTransformMetaDataProducer(Producer<EncodedImage> inputProducer) {
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext context) {
        this.mInputProducer.produceResults(new AddImageTransformMetaDataConsumer(consumer), context);
    }

    private static class AddImageTransformMetaDataConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private AddImageTransformMetaDataConsumer(Consumer<EncodedImage> consumer) {
            super(consumer);
        }

        /* access modifiers changed from: protected */
        public void onNewResultImpl(EncodedImage newResult, boolean isLast) {
            if (newResult == null) {
                getConsumer().onNewResult(null, isLast);
                return;
            }
            if (!EncodedImage.isMetaDataAvailable(newResult)) {
                newResult.parseMetaData();
            }
            getConsumer().onNewResult(newResult, isLast);
        }
    }
}
