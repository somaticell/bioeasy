package rx.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import rx.exceptions.CompositeException;

public enum ExceptionsUtils {
    ;
    
    private static final Throwable TERMINATED = null;

    static {
        TERMINATED = new Throwable("Terminated");
    }

    public static boolean addThrowable(AtomicReference<Throwable> field, Throwable error) {
        Throwable current;
        Throwable next;
        do {
            current = field.get();
            if (current == TERMINATED) {
                return false;
            }
            if (current == null) {
                next = error;
            } else if (current instanceof CompositeException) {
                List<Throwable> list = new ArrayList<>(((CompositeException) current).getExceptions());
                list.add(error);
                next = new CompositeException((Collection<? extends Throwable>) list);
            } else {
                next = new CompositeException(current, error);
            }
        } while (!field.compareAndSet(current, next));
        return true;
    }

    public static Throwable terminate(AtomicReference<Throwable> field) {
        Throwable current = field.get();
        if (current != TERMINATED) {
            return field.getAndSet(TERMINATED);
        }
        return current;
    }

    public static boolean isTerminated(AtomicReference<Throwable> field) {
        return isTerminated(field.get());
    }

    public static boolean isTerminated(Throwable error) {
        return error == TERMINATED;
    }
}
