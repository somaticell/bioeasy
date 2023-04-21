package rx.schedulers;

import rx.Scheduler;

@Deprecated
public final class TrampolineScheduler extends Scheduler {
    private TrampolineScheduler() {
        throw new AssertionError();
    }

    public Scheduler.Worker createWorker() {
        return null;
    }
}
