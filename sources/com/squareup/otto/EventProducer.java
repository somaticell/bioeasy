package com.squareup.otto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class EventProducer {
    private final int hashCode;
    private final Method method;
    final Object target;
    private boolean valid = true;

    EventProducer(Object target2, Method method2) {
        if (target2 == null) {
            throw new NullPointerException("EventProducer target cannot be null.");
        } else if (method2 == null) {
            throw new NullPointerException("EventProducer method cannot be null.");
        } else {
            this.target = target2;
            this.method = method2;
            method2.setAccessible(true);
            this.hashCode = ((method2.hashCode() + 31) * 31) + target2.hashCode();
        }
    }

    public boolean isValid() {
        return this.valid;
    }

    public void invalidate() {
        this.valid = false;
    }

    public Object produceEvent() throws InvocationTargetException {
        if (!this.valid) {
            throw new IllegalStateException(toString() + " has been invalidated and can no longer produce events.");
        }
        try {
            return this.method.invoke(this.target, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof Error) {
                throw ((Error) e2.getCause());
            }
            throw e2;
        }
    }

    public String toString() {
        return "[EventProducer " + this.method + "]";
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EventProducer other = (EventProducer) obj;
        if (!this.method.equals(other.method) || this.target != other.target) {
            return false;
        }
        return true;
    }
}
