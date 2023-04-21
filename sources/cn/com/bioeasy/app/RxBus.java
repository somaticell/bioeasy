package cn.com.bioeasy.app;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {
    private final Subject bus = new SerializedSubject(PublishSubject.create());

    public void post(Object o) {
        this.bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return this.bus.ofType(eventType);
    }
}
