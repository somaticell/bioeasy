package com.facebook.common.executors;

import com.facebook.common.logging.FLog;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConstrainedExecutorService extends AbstractExecutorService {
    /* access modifiers changed from: private */
    public static final Class<?> TAG = ConstrainedExecutorService.class;
    private final Executor mExecutor;
    private volatile int mMaxConcurrency;
    private final AtomicInteger mMaxQueueSize;
    /* access modifiers changed from: private */
    public final String mName;
    /* access modifiers changed from: private */
    public final AtomicInteger mPendingWorkers;
    private final Worker mTaskRunner;
    /* access modifiers changed from: private */
    public final BlockingQueue<Runnable> mWorkQueue;

    public ConstrainedExecutorService(String name, int maxConcurrency, Executor executor, BlockingQueue<Runnable> workQueue) {
        if (maxConcurrency <= 0) {
            throw new IllegalArgumentException("max concurrency must be > 0");
        }
        this.mName = name;
        this.mExecutor = executor;
        this.mMaxConcurrency = maxConcurrency;
        this.mWorkQueue = workQueue;
        this.mTaskRunner = new Worker();
        this.mPendingWorkers = new AtomicInteger(0);
        this.mMaxQueueSize = new AtomicInteger(0);
    }

    public static ConstrainedExecutorService newConstrainedExecutor(String name, int maxConcurrency, int queueSize, Executor executor) {
        return new ConstrainedExecutorService(name, maxConcurrency, executor, new LinkedBlockingQueue(queueSize));
    }

    public boolean isIdle() {
        return this.mWorkQueue.isEmpty() && this.mPendingWorkers.get() == 0;
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable parameter is null");
        } else if (!this.mWorkQueue.offer(runnable)) {
            throw new RejectedExecutionException(this.mName + " queue is full, size=" + this.mWorkQueue.size());
        } else {
            int queueSize = this.mWorkQueue.size();
            int maxSize = this.mMaxQueueSize.get();
            if (queueSize > maxSize && this.mMaxQueueSize.compareAndSet(maxSize, queueSize)) {
                FLog.v(TAG, "%s: max pending work in queue = %d", (Object) this.mName, (Object) Integer.valueOf(queueSize));
            }
            startWorkerIfNeeded();
        }
    }

    /* access modifiers changed from: private */
    public void startWorkerIfNeeded() {
        int currentCount = this.mPendingWorkers.get();
        while (currentCount < this.mMaxConcurrency) {
            int updatedCount = currentCount + 1;
            if (this.mPendingWorkers.compareAndSet(currentCount, updatedCount)) {
                FLog.v(TAG, "%s: starting worker %d of %d", (Object) this.mName, (Object) Integer.valueOf(updatedCount), (Object) Integer.valueOf(this.mMaxConcurrency));
                this.mExecutor.execute(this.mTaskRunner);
                return;
            }
            FLog.v(TAG, "%s: race in startWorkerIfNeeded; retrying", (Object) this.mName);
            currentCount = this.mPendingWorkers.get();
        }
    }

    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException();
    }

    public boolean isShutdown() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    private class Worker implements Runnable {
        private Worker() {
        }

        public void run() {
            int workers;
            boolean isEmpty;
            try {
                Runnable runnable = (Runnable) ConstrainedExecutorService.this.mWorkQueue.poll();
                if (runnable != null) {
                    runnable.run();
                } else {
                    FLog.v((Class<?>) ConstrainedExecutorService.TAG, "%s: Worker has nothing to run", (Object) ConstrainedExecutorService.this.mName);
                }
                if (isEmpty) {
                    FLog.v((Class<?>) ConstrainedExecutorService.TAG, "%s: worker finished; %d workers left", (Object) ConstrainedExecutorService.this.mName, (Object) Integer.valueOf(workers));
                }
            } finally {
                workers = ConstrainedExecutorService.this.mPendingWorkers.decrementAndGet();
                if (!ConstrainedExecutorService.this.mWorkQueue.isEmpty()) {
                    ConstrainedExecutorService.this.startWorkerIfNeeded();
                } else {
                    FLog.v((Class<?>) ConstrainedExecutorService.TAG, "%s: worker finished; %d workers left", (Object) ConstrainedExecutorService.this.mName, (Object) Integer.valueOf(workers));
                }
            }
        }
    }
}
