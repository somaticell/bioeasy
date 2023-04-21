package rx.observables;

import rx.Observable;
import rx.Subscriber;

public class GroupedObservable<K, T> extends Observable<T> {
    private final K key;

    public static <K, T> GroupedObservable<K, T> from(K key2, final Observable<T> o) {
        return new GroupedObservable<>(key2, new Observable.OnSubscribe<T>() {
            public void call(Subscriber<? super T> s) {
                o.unsafeSubscribe(s);
            }
        });
    }

    public static <K, T> GroupedObservable<K, T> create(K key2, Observable.OnSubscribe<T> f) {
        return new GroupedObservable<>(key2, f);
    }

    protected GroupedObservable(K key2, Observable.OnSubscribe<T> onSubscribe) {
        super(onSubscribe);
        this.key = key2;
    }

    public K getKey() {
        return this.key;
    }
}
