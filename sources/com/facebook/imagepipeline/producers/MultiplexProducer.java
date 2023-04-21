package com.facebook.imagepipeline.producers;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.imagepipeline.common.Priority;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class MultiplexProducer<K, T extends Closeable> implements Producer<T> {
    /* access modifiers changed from: private */
    public final Producer<T> mInputProducer;
    @GuardedBy("this")
    @VisibleForTesting
    final Map<K, MultiplexProducer<K, T>.Multiplexer> mMultiplexers = new HashMap();

    /* access modifiers changed from: protected */
    public abstract T cloneOrNull(T t);

    /* access modifiers changed from: protected */
    public abstract K getKey(ProducerContext producerContext);

    protected MultiplexProducer(Producer<T> inputProducer) {
        this.mInputProducer = inputProducer;
    }

    public void produceResults(Consumer<T> consumer, ProducerContext context) {
        boolean createdNewMultiplexer;
        MultiplexProducer<K, T>.Multiplexer multiplexer;
        K key = getKey(context);
        do {
            createdNewMultiplexer = false;
            synchronized (this) {
                multiplexer = getExistingMultiplexer(key);
                if (multiplexer == null) {
                    multiplexer = createAndPutNewMultiplexer(key);
                    createdNewMultiplexer = true;
                }
            }
        } while (!multiplexer.addNewConsumer(consumer, context));
        if (createdNewMultiplexer) {
            multiplexer.startInputProducerIfHasAttachedConsumers();
        }
    }

    /* access modifiers changed from: private */
    public synchronized MultiplexProducer<K, T>.Multiplexer getExistingMultiplexer(K key) {
        return this.mMultiplexers.get(key);
    }

    private synchronized MultiplexProducer<K, T>.Multiplexer createAndPutNewMultiplexer(K key) {
        MultiplexProducer<K, T>.Multiplexer multiplexer;
        multiplexer = new Multiplexer(key);
        this.mMultiplexers.put(key, multiplexer);
        return multiplexer;
    }

    /* access modifiers changed from: private */
    public synchronized void removeMultiplexer(K key, MultiplexProducer<K, T>.Multiplexer multiplexer) {
        if (this.mMultiplexers.get(key) == multiplexer) {
            this.mMultiplexers.remove(key);
        }
    }

    @VisibleForTesting
    class Multiplexer {
        /* access modifiers changed from: private */
        public final CopyOnWriteArraySet<Pair<Consumer<T>, ProducerContext>> mConsumerContextPairs = Sets.newCopyOnWriteArraySet();
        @GuardedBy("Multiplexer.this")
        @Nullable
        private MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer mForwardingConsumer;
        private final K mKey;
        @GuardedBy("Multiplexer.this")
        @Nullable
        private T mLastIntermediateResult;
        @GuardedBy("Multiplexer.this")
        private float mLastProgress;
        /* access modifiers changed from: private */
        @GuardedBy("Multiplexer.this")
        @Nullable
        public BaseProducerContext mMultiplexProducerContext;

        public Multiplexer(K key) {
            this.mKey = key;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
            monitor-enter(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0035, code lost:
            if (r2 == r9.mLastIntermediateResult) goto L_0x0053;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0037, code lost:
            r2 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
            monitor-exit(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
            if (r2 == null) goto L_0x004a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x003e, code lost:
            if (r3 <= 0.0f) goto L_0x0043;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r10.onProgressUpdate(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0043, code lost:
            r10.onNewResult(r2, false);
            closeSafely(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004a, code lost:
            monitor-exit(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x004b, code lost:
            addCallbacks(r0, r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0053, code lost:
            if (r2 == null) goto L_0x0038;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            r2 = r9.this$0.cloneOrNull(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
            return true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0028, code lost:
            com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsPrefetchChanged(r4);
            com.facebook.imagepipeline.producers.BaseProducerContext.callOnPriorityChanged(r5);
            com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsIntermediateResultExpectedChanged(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0031, code lost:
            monitor-enter(r0);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean addNewConsumer(com.facebook.imagepipeline.producers.Consumer<T> r10, com.facebook.imagepipeline.producers.ProducerContext r11) {
            /*
                r9 = this;
                r6 = 0
                android.util.Pair r0 = android.util.Pair.create(r10, r11)
                monitor-enter(r9)
                com.facebook.imagepipeline.producers.MultiplexProducer r7 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x0050 }
                K r8 = r9.mKey     // Catch:{ all -> 0x0050 }
                com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer r7 = r7.getExistingMultiplexer(r8)     // Catch:{ all -> 0x0050 }
                if (r7 == r9) goto L_0x0012
                monitor-exit(r9)     // Catch:{ all -> 0x0050 }
            L_0x0011:
                return r6
            L_0x0012:
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r6 = r9.mConsumerContextPairs     // Catch:{ all -> 0x0050 }
                r6.add(r0)     // Catch:{ all -> 0x0050 }
                java.util.List r4 = r9.updateIsPrefetch()     // Catch:{ all -> 0x0050 }
                java.util.List r5 = r9.updatePriority()     // Catch:{ all -> 0x0050 }
                java.util.List r1 = r9.updateIsIntermediateResultExpected()     // Catch:{ all -> 0x0050 }
                T r2 = r9.mLastIntermediateResult     // Catch:{ all -> 0x0050 }
                float r3 = r9.mLastProgress     // Catch:{ all -> 0x0050 }
                monitor-exit(r9)     // Catch:{ all -> 0x0050 }
                com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsPrefetchChanged(r4)
                com.facebook.imagepipeline.producers.BaseProducerContext.callOnPriorityChanged(r5)
                com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsIntermediateResultExpectedChanged(r1)
                monitor-enter(r0)
                monitor-enter(r9)     // Catch:{ all -> 0x005f }
                T r6 = r9.mLastIntermediateResult     // Catch:{ all -> 0x005c }
                if (r2 == r6) goto L_0x0053
                r2 = 0
            L_0x0038:
                monitor-exit(r9)     // Catch:{ all -> 0x005c }
                if (r2 == 0) goto L_0x004a
                r6 = 0
                int r6 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
                if (r6 <= 0) goto L_0x0043
                r10.onProgressUpdate(r3)     // Catch:{ all -> 0x005f }
            L_0x0043:
                r6 = 0
                r10.onNewResult(r2, r6)     // Catch:{ all -> 0x005f }
                r9.closeSafely(r2)     // Catch:{ all -> 0x005f }
            L_0x004a:
                monitor-exit(r0)     // Catch:{ all -> 0x005f }
                r9.addCallbacks(r0, r11)
                r6 = 1
                goto L_0x0011
            L_0x0050:
                r6 = move-exception
                monitor-exit(r9)     // Catch:{ all -> 0x0050 }
                throw r6
            L_0x0053:
                if (r2 == 0) goto L_0x0038
                com.facebook.imagepipeline.producers.MultiplexProducer r6 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x005c }
                java.io.Closeable r2 = r6.cloneOrNull(r2)     // Catch:{ all -> 0x005c }
                goto L_0x0038
            L_0x005c:
                r6 = move-exception
                monitor-exit(r9)     // Catch:{ all -> 0x005c }
                throw r6     // Catch:{ all -> 0x005f }
            L_0x005f:
                r6 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x005f }
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.addNewConsumer(com.facebook.imagepipeline.producers.Consumer, com.facebook.imagepipeline.producers.ProducerContext):boolean");
        }

        private void addCallbacks(final Pair<Consumer<T>, ProducerContext> consumerContextPair, ProducerContext producerContext) {
            producerContext.addCallbacks(new BaseProducerContextCallbacks() {
                public void onCancellationRequested() {
                    boolean pairWasRemoved;
                    BaseProducerContext contextToCancel = null;
                    List<ProducerContextCallbacks> isPrefetchCallbacks = null;
                    List<ProducerContextCallbacks> priorityCallbacks = null;
                    List<ProducerContextCallbacks> isIntermediateResultExpectedCallbacks = null;
                    synchronized (Multiplexer.this) {
                        pairWasRemoved = Multiplexer.this.mConsumerContextPairs.remove(consumerContextPair);
                        if (pairWasRemoved) {
                            if (Multiplexer.this.mConsumerContextPairs.isEmpty()) {
                                contextToCancel = Multiplexer.this.mMultiplexProducerContext;
                            } else {
                                isPrefetchCallbacks = Multiplexer.this.updateIsPrefetch();
                                priorityCallbacks = Multiplexer.this.updatePriority();
                                isIntermediateResultExpectedCallbacks = Multiplexer.this.updateIsIntermediateResultExpected();
                            }
                        }
                    }
                    BaseProducerContext.callOnIsPrefetchChanged(isPrefetchCallbacks);
                    BaseProducerContext.callOnPriorityChanged(priorityCallbacks);
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(isIntermediateResultExpectedCallbacks);
                    if (contextToCancel != null) {
                        contextToCancel.cancel();
                    }
                    if (pairWasRemoved) {
                        ((Consumer) consumerContextPair.first).onCancellation();
                    }
                }

                public void onIsPrefetchChanged() {
                    BaseProducerContext.callOnIsPrefetchChanged(Multiplexer.this.updateIsPrefetch());
                }

                public void onIsIntermediateResultExpectedChanged() {
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(Multiplexer.this.updateIsIntermediateResultExpected());
                }

                public void onPriorityChanged() {
                    BaseProducerContext.callOnPriorityChanged(Multiplexer.this.updatePriority());
                }
            });
        }

        /* access modifiers changed from: private */
        public void startInputProducerIfHasAttachedConsumers() {
            boolean z = true;
            synchronized (this) {
                Preconditions.checkArgument(this.mMultiplexProducerContext == null);
                if (this.mForwardingConsumer != null) {
                    z = false;
                }
                Preconditions.checkArgument(z);
                if (this.mConsumerContextPairs.isEmpty()) {
                    MultiplexProducer.this.removeMultiplexer(this.mKey, this);
                    return;
                }
                ProducerContext producerContext = (ProducerContext) this.mConsumerContextPairs.iterator().next().second;
                this.mMultiplexProducerContext = new BaseProducerContext(producerContext.getImageRequest(), producerContext.getId(), producerContext.getListener(), producerContext.getCallerContext(), producerContext.getLowestPermittedRequestLevel(), computeIsPrefetch(), computeIsIntermediateResultExpected(), computePriority());
                this.mForwardingConsumer = new ForwardingConsumer();
                BaseProducerContext multiplexProducerContext = this.mMultiplexProducerContext;
                MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer forwardingConsumer = this.mForwardingConsumer;
                MultiplexProducer.this.mInputProducer.produceResults(forwardingConsumer, multiplexProducerContext);
            }
        }

        /* access modifiers changed from: private */
        @Nullable
        public synchronized List<ProducerContextCallbacks> updateIsPrefetch() {
            List<ProducerContextCallbacks> isPrefetchNoCallbacks;
            if (this.mMultiplexProducerContext == null) {
                isPrefetchNoCallbacks = null;
            } else {
                isPrefetchNoCallbacks = this.mMultiplexProducerContext.setIsPrefetchNoCallbacks(computeIsPrefetch());
            }
            return isPrefetchNoCallbacks;
        }

        private synchronized boolean computeIsPrefetch() {
            boolean z;
            Iterator i$ = this.mConsumerContextPairs.iterator();
            while (true) {
                if (i$.hasNext()) {
                    if (!((ProducerContext) i$.next().second).isPrefetch()) {
                        z = false;
                        break;
                    }
                } else {
                    z = true;
                    break;
                }
            }
            return z;
        }

        /* access modifiers changed from: private */
        @Nullable
        public synchronized List<ProducerContextCallbacks> updateIsIntermediateResultExpected() {
            List<ProducerContextCallbacks> isIntermediateResultExpectedNoCallbacks;
            if (this.mMultiplexProducerContext == null) {
                isIntermediateResultExpectedNoCallbacks = null;
            } else {
                isIntermediateResultExpectedNoCallbacks = this.mMultiplexProducerContext.setIsIntermediateResultExpectedNoCallbacks(computeIsIntermediateResultExpected());
            }
            return isIntermediateResultExpectedNoCallbacks;
        }

        private synchronized boolean computeIsIntermediateResultExpected() {
            boolean z;
            Iterator i$ = this.mConsumerContextPairs.iterator();
            while (true) {
                if (i$.hasNext()) {
                    if (((ProducerContext) i$.next().second).isIntermediateResultExpected()) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            return z;
        }

        /* access modifiers changed from: private */
        @Nullable
        public synchronized List<ProducerContextCallbacks> updatePriority() {
            List<ProducerContextCallbacks> priorityNoCallbacks;
            if (this.mMultiplexProducerContext == null) {
                priorityNoCallbacks = null;
            } else {
                priorityNoCallbacks = this.mMultiplexProducerContext.setPriorityNoCallbacks(computePriority());
            }
            return priorityNoCallbacks;
        }

        private synchronized Priority computePriority() {
            Priority priority;
            priority = Priority.LOW;
            Iterator i$ = this.mConsumerContextPairs.iterator();
            while (i$.hasNext()) {
                priority = Priority.getHigherPriority(priority, ((ProducerContext) i$.next().second).getPriority());
            }
            return priority;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x002e, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            ((com.facebook.imagepipeline.producers.Consumer) r1.first).onFailure(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0026, code lost:
            if (r0.hasNext() == false) goto L_0x0006;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
            r1 = r0.next();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onFailure(com.facebook.imagepipeline.producers.MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer r5, java.lang.Throwable r6) {
            /*
                r4 = this;
                monitor-enter(r4)
                com.facebook.imagepipeline.producers.MultiplexProducer<K, T>$Multiplexer.ForwardingConsumer r2 = r4.mForwardingConsumer     // Catch:{ all -> 0x003b }
                if (r2 == r5) goto L_0x0007
                monitor-exit(r4)     // Catch:{ all -> 0x003b }
            L_0x0006:
                return
            L_0x0007:
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r2 = r4.mConsumerContextPairs     // Catch:{ all -> 0x003b }
                java.util.Iterator r0 = r2.iterator()     // Catch:{ all -> 0x003b }
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r2 = r4.mConsumerContextPairs     // Catch:{ all -> 0x003b }
                r2.clear()     // Catch:{ all -> 0x003b }
                com.facebook.imagepipeline.producers.MultiplexProducer r2 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x003b }
                K r3 = r4.mKey     // Catch:{ all -> 0x003b }
                r2.removeMultiplexer(r3, r4)     // Catch:{ all -> 0x003b }
                T r2 = r4.mLastIntermediateResult     // Catch:{ all -> 0x003b }
                r4.closeSafely(r2)     // Catch:{ all -> 0x003b }
                r2 = 0
                r4.mLastIntermediateResult = r2     // Catch:{ all -> 0x003b }
                monitor-exit(r4)     // Catch:{ all -> 0x003b }
            L_0x0022:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x0006
                java.lang.Object r1 = r0.next()
                android.util.Pair r1 = (android.util.Pair) r1
                monitor-enter(r1)
                java.lang.Object r2 = r1.first     // Catch:{ all -> 0x0038 }
                com.facebook.imagepipeline.producers.Consumer r2 = (com.facebook.imagepipeline.producers.Consumer) r2     // Catch:{ all -> 0x0038 }
                r2.onFailure(r6)     // Catch:{ all -> 0x0038 }
                monitor-exit(r1)     // Catch:{ all -> 0x0038 }
                goto L_0x0022
            L_0x0038:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0038 }
                throw r2
            L_0x003b:
                r2 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x003b }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.onFailure(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer, java.lang.Throwable):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0024, code lost:
            if (r0.hasNext() == false) goto L_0x0006;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0026, code lost:
            r1 = r0.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002c, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
            ((com.facebook.imagepipeline.producers.Consumer) r1.first).onNewResult(r6, r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0034, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onNextResult(com.facebook.imagepipeline.producers.MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer r5, T r6, boolean r7) {
            /*
                r4 = this;
                monitor-enter(r4)
                com.facebook.imagepipeline.producers.MultiplexProducer<K, T>$Multiplexer.ForwardingConsumer r2 = r4.mForwardingConsumer     // Catch:{ all -> 0x0046 }
                if (r2 == r5) goto L_0x0007
                monitor-exit(r4)     // Catch:{ all -> 0x0046 }
            L_0x0006:
                return
            L_0x0007:
                T r2 = r4.mLastIntermediateResult     // Catch:{ all -> 0x0046 }
                r4.closeSafely(r2)     // Catch:{ all -> 0x0046 }
                r2 = 0
                r4.mLastIntermediateResult = r2     // Catch:{ all -> 0x0046 }
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r2 = r4.mConsumerContextPairs     // Catch:{ all -> 0x0046 }
                java.util.Iterator r0 = r2.iterator()     // Catch:{ all -> 0x0046 }
                if (r7 != 0) goto L_0x0039
                com.facebook.imagepipeline.producers.MultiplexProducer r2 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x0046 }
                java.io.Closeable r2 = r2.cloneOrNull(r6)     // Catch:{ all -> 0x0046 }
                r4.mLastIntermediateResult = r2     // Catch:{ all -> 0x0046 }
            L_0x001f:
                monitor-exit(r4)     // Catch:{ all -> 0x0046 }
            L_0x0020:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x0006
                java.lang.Object r1 = r0.next()
                android.util.Pair r1 = (android.util.Pair) r1
                monitor-enter(r1)
                java.lang.Object r2 = r1.first     // Catch:{ all -> 0x0036 }
                com.facebook.imagepipeline.producers.Consumer r2 = (com.facebook.imagepipeline.producers.Consumer) r2     // Catch:{ all -> 0x0036 }
                r2.onNewResult(r6, r7)     // Catch:{ all -> 0x0036 }
                monitor-exit(r1)     // Catch:{ all -> 0x0036 }
                goto L_0x0020
            L_0x0036:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0036 }
                throw r2
            L_0x0039:
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r2 = r4.mConsumerContextPairs     // Catch:{ all -> 0x0046 }
                r2.clear()     // Catch:{ all -> 0x0046 }
                com.facebook.imagepipeline.producers.MultiplexProducer r2 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x0046 }
                K r3 = r4.mKey     // Catch:{ all -> 0x0046 }
                r2.removeMultiplexer(r3, r4)     // Catch:{ all -> 0x0046 }
                goto L_0x001f
            L_0x0046:
                r2 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0046 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.onNextResult(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer, java.io.Closeable, boolean):void");
        }

        public void onCancelled(MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer forwardingConsumer) {
            synchronized (this) {
                if (this.mForwardingConsumer == forwardingConsumer) {
                    this.mForwardingConsumer = null;
                    this.mMultiplexProducerContext = null;
                    closeSafely(this.mLastIntermediateResult);
                    this.mLastIntermediateResult = null;
                    startInputProducerIfHasAttachedConsumers();
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            ((com.facebook.imagepipeline.producers.Consumer) r1.first).onProgressUpdate(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0024, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0014, code lost:
            if (r0.hasNext() == false) goto L_0x0006;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0016, code lost:
            r1 = r0.next();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onProgressUpdate(com.facebook.imagepipeline.producers.MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer r4, float r5) {
            /*
                r3 = this;
                monitor-enter(r3)
                com.facebook.imagepipeline.producers.MultiplexProducer<K, T>$Multiplexer.ForwardingConsumer r2 = r3.mForwardingConsumer     // Catch:{ all -> 0x0029 }
                if (r2 == r4) goto L_0x0007
                monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            L_0x0006:
                return
            L_0x0007:
                r3.mLastProgress = r5     // Catch:{ all -> 0x0029 }
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r2 = r3.mConsumerContextPairs     // Catch:{ all -> 0x0029 }
                java.util.Iterator r0 = r2.iterator()     // Catch:{ all -> 0x0029 }
                monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            L_0x0010:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x0006
                java.lang.Object r1 = r0.next()
                android.util.Pair r1 = (android.util.Pair) r1
                monitor-enter(r1)
                java.lang.Object r2 = r1.first     // Catch:{ all -> 0x0026 }
                com.facebook.imagepipeline.producers.Consumer r2 = (com.facebook.imagepipeline.producers.Consumer) r2     // Catch:{ all -> 0x0026 }
                r2.onProgressUpdate(r5)     // Catch:{ all -> 0x0026 }
                monitor-exit(r1)     // Catch:{ all -> 0x0026 }
                goto L_0x0010
            L_0x0026:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0026 }
                throw r2
            L_0x0029:
                r2 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0029 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.onProgressUpdate(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer, float):void");
        }

        private void closeSafely(Closeable obj) {
            if (obj != null) {
                try {
                    obj.close();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }

        private class ForwardingConsumer extends BaseConsumer<T> {
            private ForwardingConsumer() {
            }

            /* access modifiers changed from: protected */
            public void onNewResultImpl(T newResult, boolean isLast) {
                Multiplexer.this.onNextResult(this, newResult, isLast);
            }

            /* access modifiers changed from: protected */
            public void onFailureImpl(Throwable t) {
                Multiplexer.this.onFailure(this, t);
            }

            /* access modifiers changed from: protected */
            public void onCancellationImpl() {
                Multiplexer.this.onCancelled(this);
            }

            /* access modifiers changed from: protected */
            public void onProgressUpdateImpl(float progress) {
                Multiplexer.this.onProgressUpdate(this, progress);
            }
        }
    }
}
