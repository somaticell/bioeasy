package com.facebook.datasource;

import android.util.Pair;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public abstract class AbstractDataSource<T> implements DataSource<T> {
    @GuardedBy("this")
    private DataSourceStatus mDataSourceStatus = DataSourceStatus.IN_PROGRESS;
    @GuardedBy("this")
    private Throwable mFailureThrowable = null;
    @GuardedBy("this")
    private boolean mIsClosed = false;
    @GuardedBy("this")
    private float mProgress = 0.0f;
    @GuardedBy("this")
    @Nullable
    private T mResult = null;
    private final ConcurrentLinkedQueue<Pair<DataSubscriber<T>, Executor>> mSubscribers = new ConcurrentLinkedQueue<>();

    private enum DataSourceStatus {
        IN_PROGRESS,
        SUCCESS,
        FAILURE
    }

    protected AbstractDataSource() {
    }

    public synchronized boolean isClosed() {
        return this.mIsClosed;
    }

    public synchronized boolean isFinished() {
        return this.mDataSourceStatus != DataSourceStatus.IN_PROGRESS;
    }

    public synchronized boolean hasResult() {
        return this.mResult != null;
    }

    @Nullable
    public synchronized T getResult() {
        return this.mResult;
    }

    public synchronized boolean hasFailed() {
        return this.mDataSourceStatus == DataSourceStatus.FAILURE;
    }

    @Nullable
    public synchronized Throwable getFailureCause() {
        return this.mFailureThrowable;
    }

    public synchronized float getProgress() {
        return this.mProgress;
    }

    public boolean close() {
        boolean z = true;
        synchronized (this) {
            if (this.mIsClosed) {
                z = false;
            } else {
                this.mIsClosed = true;
                T resultToClose = this.mResult;
                this.mResult = null;
                if (resultToClose != null) {
                    closeResult(resultToClose);
                }
                if (!isFinished()) {
                    notifyDataSubscribers();
                }
                synchronized (this) {
                    this.mSubscribers.clear();
                }
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public void closeResult(@Nullable T t) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0030, code lost:
        if (r0 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0032, code lost:
        notifyDataSubscriber(r4, r5, hasFailed(), wasCancelled());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void subscribe(com.facebook.datasource.DataSubscriber<T> r4, java.util.concurrent.Executor r5) {
        /*
            r3 = this;
            com.facebook.common.internal.Preconditions.checkNotNull(r4)
            com.facebook.common.internal.Preconditions.checkNotNull(r5)
            monitor-enter(r3)
            boolean r1 = r3.mIsClosed     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x000d
            monitor-exit(r3)     // Catch:{ all -> 0x0040 }
        L_0x000c:
            return
        L_0x000d:
            com.facebook.datasource.AbstractDataSource$DataSourceStatus r1 = r3.mDataSourceStatus     // Catch:{ all -> 0x0040 }
            com.facebook.datasource.AbstractDataSource$DataSourceStatus r2 = com.facebook.datasource.AbstractDataSource.DataSourceStatus.IN_PROGRESS     // Catch:{ all -> 0x0040 }
            if (r1 != r2) goto L_0x001c
            java.util.concurrent.ConcurrentLinkedQueue<android.util.Pair<com.facebook.datasource.DataSubscriber<T>, java.util.concurrent.Executor>> r1 = r3.mSubscribers     // Catch:{ all -> 0x0040 }
            android.util.Pair r2 = android.util.Pair.create(r4, r5)     // Catch:{ all -> 0x0040 }
            r1.add(r2)     // Catch:{ all -> 0x0040 }
        L_0x001c:
            boolean r1 = r3.hasResult()     // Catch:{ all -> 0x0040 }
            if (r1 != 0) goto L_0x002e
            boolean r1 = r3.isFinished()     // Catch:{ all -> 0x0040 }
            if (r1 != 0) goto L_0x002e
            boolean r1 = r3.wasCancelled()     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x003e
        L_0x002e:
            r0 = 1
        L_0x002f:
            monitor-exit(r3)     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x000c
            boolean r1 = r3.hasFailed()
            boolean r2 = r3.wasCancelled()
            r3.notifyDataSubscriber(r4, r5, r1, r2)
            goto L_0x000c
        L_0x003e:
            r0 = 0
            goto L_0x002f
        L_0x0040:
            r1 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0040 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.datasource.AbstractDataSource.subscribe(com.facebook.datasource.DataSubscriber, java.util.concurrent.Executor):void");
    }

    private void notifyDataSubscribers() {
        boolean isFailure = hasFailed();
        boolean isCancellation = wasCancelled();
        Iterator i$ = this.mSubscribers.iterator();
        while (i$.hasNext()) {
            Pair<DataSubscriber<T>, Executor> pair = i$.next();
            notifyDataSubscriber((DataSubscriber) pair.first, (Executor) pair.second, isFailure, isCancellation);
        }
    }

    private void notifyDataSubscriber(final DataSubscriber<T> dataSubscriber, Executor executor, final boolean isFailure, final boolean isCancellation) {
        executor.execute(new Runnable() {
            public void run() {
                if (isFailure) {
                    dataSubscriber.onFailure(AbstractDataSource.this);
                } else if (isCancellation) {
                    dataSubscriber.onCancellation(AbstractDataSource.this);
                } else {
                    dataSubscriber.onNewResult(AbstractDataSource.this);
                }
            }
        });
    }

    private synchronized boolean wasCancelled() {
        return isClosed() && !isFinished();
    }

    /* access modifiers changed from: protected */
    public boolean setResult(@Nullable T value, boolean isLast) {
        boolean result = setResultInternal(value, isLast);
        if (result) {
            notifyDataSubscribers();
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean setFailure(Throwable throwable) {
        boolean result = setFailureInternal(throwable);
        if (result) {
            notifyDataSubscribers();
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean setProgress(float progress) {
        boolean result = setProgressInternal(progress);
        if (result) {
            notifyProgressUpdate();
        }
        return result;
    }

    private boolean setResultInternal(@Nullable T value, boolean isLast) {
        boolean z;
        T resultToClose = null;
        try {
            synchronized (this) {
                if (this.mIsClosed || this.mDataSourceStatus != DataSourceStatus.IN_PROGRESS) {
                    resultToClose = value;
                    z = false;
                } else {
                    if (isLast) {
                        this.mDataSourceStatus = DataSourceStatus.SUCCESS;
                        this.mProgress = 1.0f;
                    }
                    if (this.mResult != value) {
                        resultToClose = this.mResult;
                        this.mResult = value;
                    }
                    z = true;
                    if (resultToClose != null) {
                        closeResult(resultToClose);
                    }
                }
            }
            return z;
        } finally {
            if (resultToClose != null) {
                closeResult(resultToClose);
            }
        }
    }

    private synchronized boolean setFailureInternal(Throwable throwable) {
        boolean z;
        if (this.mIsClosed || this.mDataSourceStatus != DataSourceStatus.IN_PROGRESS) {
            z = false;
        } else {
            this.mDataSourceStatus = DataSourceStatus.FAILURE;
            this.mFailureThrowable = throwable;
            z = true;
        }
        return z;
    }

    private synchronized boolean setProgressInternal(float progress) {
        boolean z = false;
        synchronized (this) {
            if (!this.mIsClosed && this.mDataSourceStatus == DataSourceStatus.IN_PROGRESS) {
                if (progress >= this.mProgress) {
                    this.mProgress = progress;
                    z = true;
                }
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public void notifyProgressUpdate() {
        Iterator i$ = this.mSubscribers.iterator();
        while (i$.hasNext()) {
            Pair<DataSubscriber<T>, Executor> pair = i$.next();
            final DataSubscriber<T> subscriber = (DataSubscriber) pair.first;
            ((Executor) pair.second).execute(new Runnable() {
                public void run() {
                    subscriber.onProgressUpdate(AbstractDataSource.this);
                }
            });
        }
    }
}
