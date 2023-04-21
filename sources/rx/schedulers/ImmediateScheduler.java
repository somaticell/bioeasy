package rx.schedulers;

import rx.Scheduler;

@Deprecated
public final class ImmediateScheduler extends Scheduler {
    private ImmediateScheduler() {
        throw new AssertionError();
    }

    public Scheduler.Worker createWorker() {
        return null;
    }
}
