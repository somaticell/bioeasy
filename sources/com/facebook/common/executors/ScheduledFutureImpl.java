package com.facebook.common.executors;

import android.os.Handler;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;

public class ScheduledFutureImpl<V> implements RunnableFuture<V>, ScheduledFuture<V> {
    private final Handler mHandler;
    private final FutureTask<V> mListenableFuture;

    public ScheduledFutureImpl(Handler handler, Callable<V> callable) {
        this.mHandler = handler;
        this.mListenableFuture = new FutureTask<>(callable);
    }

    public ScheduledFutureImpl(Handler handler, Runnable runnable, @Nullable V result) {
        this.mHandler = handler;
        this.mListenableFuture = new FutureTask<>(runnable, result);
    }

    public long getDelay(TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    public int compareTo(Delayed other) {
        throw new UnsupportedOperationException();
    }

    public void run() {
        this.mListenableFuture.run();
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.mListenableFuture.cancel(mayInterruptIfRunning);
    }

    public boolean isCancelled() {
        return this.mListenableFuture.isCancelled();
    }

    public boolean isDone() {
        return this.mListenableFuture.isDone();
    }

    public V get() throws InterruptedException, ExecutionException {
        return this.mListenableFuture.get();
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.mListenableFuture.get(timeout, unit);
    }
}
