package net.oschina.common.net;

import java.lang.reflect.Type;
import net.oschina.common.utils.Logger;

public abstract class Callback<T> implements DispatchRequestProgress, DispatchResponseProgress {
    public abstract void onFailure(Object obj, Object obj2, Exception exc);

    public abstract void onSuccess(T t, int i);

    /* access modifiers changed from: protected */
    public void dispatchStart(Object request) {
        onStart(request);
    }

    /* access modifiers changed from: protected */
    public void dispatchFinish() {
        onFinish();
    }

    /* access modifiers changed from: protected */
    public void dispatchFailure(Object request, Object response, Exception e) {
        onFailure(request, response, e);
    }

    /* access modifiers changed from: protected */
    public void dispatchSuccess(T response, int code) {
        onSuccess(response, code);
    }

    public void onStart(Object request) {
    }

    public void onFinish() {
    }

    /* access modifiers changed from: package-private */
    public Class<T> getReturnClass() {
        try {
            return (Class) new Type[]{getClass()}[0];
        } catch (RuntimeException e) {
            if (Logger.DEBUG) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void onRequestProgress(long current, long count) {
    }

    public void onResponseProgress(long current, long count) {
    }

    public void dispatchRequestProgress(long current, long count) {
        onRequestProgress(current, count);
    }

    public void dispatchResponseProgress(long current, long count) {
        onResponseProgress(current, count);
    }
}
