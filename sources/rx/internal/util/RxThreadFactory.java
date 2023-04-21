package rx.internal.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class RxThreadFactory extends AtomicLong implements ThreadFactory {
    public static final ThreadFactory NONE = new ThreadFactory() {
        public Thread newThread(Runnable r) {
            throw new AssertionError("No threads allowed.");
        }
    };
    final String prefix;

    public RxThreadFactory(String prefix2) {
        this.prefix = prefix2;
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, this.prefix + incrementAndGet());
        t.setDaemon(true);
        return t;
    }
}
