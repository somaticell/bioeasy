package rx.internal.operators;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Completable;
import rx.Subscription;
import rx.internal.util.unsafe.MpscLinkedQueue;
import rx.subscriptions.CompositeSubscription;

public final class CompletableOnSubscribeMergeDelayErrorIterable implements Completable.CompletableOnSubscribe {
    final Iterable<? extends Completable> sources;

    public CompletableOnSubscribeMergeDelayErrorIterable(Iterable<? extends Completable> sources2) {
        this.sources = sources2;
    }

    public void call(Completable.CompletableSubscriber s) {
        final CompositeSubscription set = new CompositeSubscription();
        final AtomicInteger wip = new AtomicInteger(1);
        final Queue<Throwable> queue = new MpscLinkedQueue<>();
        s.onSubscribe(set);
        try {
            Iterator<? extends Completable> iterator = this.sources.iterator();
            if (iterator == null) {
                s.onError(new NullPointerException("The source iterator returned is null"));
                return;
            }
            while (!set.isUnsubscribed()) {
                try {
                    if (!iterator.hasNext()) {
                        if (wip.decrementAndGet() != 0) {
                            return;
                        }
                        if (queue.isEmpty()) {
                            s.onCompleted();
                            return;
                        } else {
                            s.onError(CompletableOnSubscribeMerge.collectErrors(queue));
                            return;
                        }
                    } else if (!set.isUnsubscribed()) {
                        try {
                            Completable c = (Completable) iterator.next();
                            if (set.isUnsubscribed()) {
                                return;
                            }
                            if (c == null) {
                                queue.offer(new NullPointerException("A completable source is null"));
                                if (wip.decrementAndGet() != 0) {
                                    return;
                                }
                                if (queue.isEmpty()) {
                                    s.onCompleted();
                                    return;
                                } else {
                                    s.onError(CompletableOnSubscribeMerge.collectErrors(queue));
                                    return;
                                }
                            } else {
                                wip.getAndIncrement();
                                final Completable.CompletableSubscriber completableSubscriber = s;
                                c.subscribe((Completable.CompletableSubscriber) new Completable.CompletableSubscriber() {
                                    public void onSubscribe(Subscription d) {
                                        set.add(d);
                                    }

                                    public void onError(Throwable e) {
                                        queue.offer(e);
                                        tryTerminate();
                                    }

                                    public void onCompleted() {
                                        tryTerminate();
                                    }

                                    /* access modifiers changed from: package-private */
                                    public void tryTerminate() {
                                        if (wip.decrementAndGet() != 0) {
                                            return;
                                        }
                                        if (queue.isEmpty()) {
                                            completableSubscriber.onCompleted();
                                        } else {
                                            completableSubscriber.onError(CompletableOnSubscribeMerge.collectErrors(queue));
                                        }
                                    }
                                });
                            }
                        } catch (Throwable e) {
                            queue.offer(e);
                            if (wip.decrementAndGet() != 0) {
                                return;
                            }
                            if (queue.isEmpty()) {
                                s.onCompleted();
                                return;
                            } else {
                                s.onError(CompletableOnSubscribeMerge.collectErrors(queue));
                                return;
                            }
                        }
                    } else {
                        return;
                    }
                } catch (Throwable e2) {
                    queue.offer(e2);
                    if (wip.decrementAndGet() != 0) {
                        return;
                    }
                    if (queue.isEmpty()) {
                        s.onCompleted();
                        return;
                    } else {
                        s.onError(CompletableOnSubscribeMerge.collectErrors(queue));
                        return;
                    }
                }
            }
        } catch (Throwable e3) {
            s.onError(e3);
        }
    }
}
