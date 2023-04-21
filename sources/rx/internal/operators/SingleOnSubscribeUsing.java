package rx.internal.operators;

import java.util.Arrays;
import java.util.Collection;
import rx.Single;
import rx.SingleSubscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.plugins.RxJavaPlugins;

public final class SingleOnSubscribeUsing<T, Resource> implements Single.OnSubscribe<T> {
    final Action1<? super Resource> disposeAction;
    final boolean disposeEagerly;
    final Func0<Resource> resourceFactory;
    final Func1<? super Resource, ? extends Single<? extends T>> singleFactory;

    public SingleOnSubscribeUsing(Func0<Resource> resourceFactory2, Func1<? super Resource, ? extends Single<? extends T>> observableFactory, Action1<? super Resource> disposeAction2, boolean disposeEagerly2) {
        this.resourceFactory = resourceFactory2;
        this.singleFactory = observableFactory;
        this.disposeAction = disposeAction2;
        this.disposeEagerly = disposeEagerly2;
    }

    public void call(final SingleSubscriber<? super T> child) {
        try {
            final Resource resource = this.resourceFactory.call();
            try {
                Single<? extends T> single = (Single) this.singleFactory.call(resource);
                if (single == null) {
                    handleSubscriptionTimeError(child, resource, new NullPointerException("The single"));
                    return;
                }
                SingleSubscriber<T> parent = new SingleSubscriber<T>() {
                    public void onSuccess(T value) {
                        if (SingleOnSubscribeUsing.this.disposeEagerly) {
                            try {
                                SingleOnSubscribeUsing.this.disposeAction.call(resource);
                            } catch (Throwable ex) {
                                Exceptions.throwIfFatal(ex);
                                child.onError(ex);
                                return;
                            }
                        }
                        child.onSuccess(value);
                        if (!SingleOnSubscribeUsing.this.disposeEagerly) {
                            try {
                                SingleOnSubscribeUsing.this.disposeAction.call(resource);
                            } catch (Throwable ex2) {
                                Exceptions.throwIfFatal(ex2);
                                RxJavaPlugins.getInstance().getErrorHandler().handleError(ex2);
                            }
                        }
                    }

                    public void onError(Throwable error) {
                        SingleOnSubscribeUsing.this.handleSubscriptionTimeError(child, resource, error);
                    }
                };
                child.add(parent);
                single.subscribe((SingleSubscriber<? super Object>) parent);
            } catch (Throwable ex) {
                handleSubscriptionTimeError(child, resource, ex);
            }
        } catch (Throwable ex2) {
            Exceptions.throwIfFatal(ex2);
            child.onError(ex2);
        }
    }

    /* access modifiers changed from: package-private */
    public void handleSubscriptionTimeError(SingleSubscriber<? super T> t, Resource resource, Throwable ex) {
        Exceptions.throwIfFatal(ex);
        if (this.disposeEagerly) {
            try {
                this.disposeAction.call(resource);
            } catch (Throwable ex2) {
                Exceptions.throwIfFatal(ex2);
                ex = new CompositeException((Collection<? extends Throwable>) Arrays.asList(new Throwable[]{ex, ex2}));
            }
        }
        t.onError(ex);
        if (!this.disposeEagerly) {
            try {
                this.disposeAction.call(resource);
            } catch (Throwable ex22) {
                Exceptions.throwIfFatal(ex22);
                RxJavaPlugins.getInstance().getErrorHandler().handleError(ex22);
            }
        }
    }
}
