package dagger.internal;

public final class InstanceFactory<T> implements Factory<T> {
    private final T instance;

    public static <T> Factory<T> create(T instance2) {
        if (instance2 != null) {
            return new InstanceFactory(instance2);
        }
        throw new NullPointerException();
    }

    private InstanceFactory(T instance2) {
        this.instance = instance2;
    }

    public T get() {
        return this.instance;
    }
}
