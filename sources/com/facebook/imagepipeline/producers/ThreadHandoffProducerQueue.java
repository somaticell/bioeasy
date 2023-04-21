package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;

public class ThreadHandoffProducerQueue {
    private final Executor mExecutor;
    private boolean mQueueing = false;
    private final ArrayList<Runnable> mRunnableList;

    public ThreadHandoffProducerQueue(Executor executor) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mRunnableList = new ArrayList<>();
    }

    public synchronized void addToQueueOrExecute(Runnable runnable) {
        if (this.mQueueing) {
            this.mRunnableList.add(runnable);
        } else {
            this.mExecutor.execute(runnable);
        }
    }

    public synchronized void startQueueing() {
        this.mQueueing = true;
    }

    public synchronized void stopQueuing() {
        this.mQueueing = false;
        execInQueue();
    }

    private void execInQueue() {
        Iterator i$ = this.mRunnableList.iterator();
        while (i$.hasNext()) {
            this.mExecutor.execute(i$.next());
        }
        this.mRunnableList.clear();
    }

    public void remove(Runnable runnable) {
        this.mRunnableList.remove(runnable);
    }

    public synchronized boolean isQueueing() {
        return this.mQueueing;
    }
}
