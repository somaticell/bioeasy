package rx.plugins;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import rx.annotations.Experimental;

public class RxJavaPlugins {
    static final RxJavaErrorHandler DEFAULT_ERROR_HANDLER = new RxJavaErrorHandler() {
    };
    private static final RxJavaPlugins INSTANCE = new RxJavaPlugins();
    private final AtomicReference<RxJavaErrorHandler> errorHandler = new AtomicReference<>();
    private final AtomicReference<RxJavaObservableExecutionHook> observableExecutionHook = new AtomicReference<>();
    private final AtomicReference<RxJavaSchedulersHook> schedulersHook = new AtomicReference<>();
    private final AtomicReference<RxJavaSingleExecutionHook> singleExecutionHook = new AtomicReference<>();

    public static RxJavaPlugins getInstance() {
        return INSTANCE;
    }

    RxJavaPlugins() {
    }

    @Experimental
    public void reset() {
        INSTANCE.errorHandler.set((Object) null);
        INSTANCE.observableExecutionHook.set((Object) null);
        INSTANCE.singleExecutionHook.set((Object) null);
        INSTANCE.schedulersHook.set((Object) null);
    }

    public RxJavaErrorHandler getErrorHandler() {
        if (this.errorHandler.get() == null) {
            Object impl = getPluginImplementationViaProperty(RxJavaErrorHandler.class, System.getProperties());
            if (impl == null) {
                this.errorHandler.compareAndSet((Object) null, DEFAULT_ERROR_HANDLER);
            } else {
                this.errorHandler.compareAndSet((Object) null, (RxJavaErrorHandler) impl);
            }
        }
        return this.errorHandler.get();
    }

    public void registerErrorHandler(RxJavaErrorHandler impl) {
        if (!this.errorHandler.compareAndSet((Object) null, impl)) {
            throw new IllegalStateException("Another strategy was already registered: " + this.errorHandler.get());
        }
    }

    public RxJavaObservableExecutionHook getObservableExecutionHook() {
        if (this.observableExecutionHook.get() == null) {
            Object impl = getPluginImplementationViaProperty(RxJavaObservableExecutionHook.class, System.getProperties());
            if (impl == null) {
                this.observableExecutionHook.compareAndSet((Object) null, RxJavaObservableExecutionHookDefault.getInstance());
            } else {
                this.observableExecutionHook.compareAndSet((Object) null, (RxJavaObservableExecutionHook) impl);
            }
        }
        return this.observableExecutionHook.get();
    }

    public void registerObservableExecutionHook(RxJavaObservableExecutionHook impl) {
        if (!this.observableExecutionHook.compareAndSet((Object) null, impl)) {
            throw new IllegalStateException("Another strategy was already registered: " + this.observableExecutionHook.get());
        }
    }

    public RxJavaSingleExecutionHook getSingleExecutionHook() {
        if (this.singleExecutionHook.get() == null) {
            Object impl = getPluginImplementationViaProperty(RxJavaSingleExecutionHook.class, System.getProperties());
            if (impl == null) {
                this.singleExecutionHook.compareAndSet((Object) null, RxJavaSingleExecutionHookDefault.getInstance());
            } else {
                this.singleExecutionHook.compareAndSet((Object) null, (RxJavaSingleExecutionHook) impl);
            }
        }
        return this.singleExecutionHook.get();
    }

    public void registerSingleExecutionHook(RxJavaSingleExecutionHook impl) {
        if (!this.singleExecutionHook.compareAndSet((Object) null, impl)) {
            throw new IllegalStateException("Another strategy was already registered: " + this.singleExecutionHook.get());
        }
    }

    static Object getPluginImplementationViaProperty(Class<?> pluginClass, Properties propsIn) {
        Properties props = (Properties) propsIn.clone();
        String classSimpleName = pluginClass.getSimpleName();
        String implementingClass = props.getProperty("rxjava.plugin." + classSimpleName + ".implementation");
        if (implementingClass == null) {
            Iterator i$ = props.entrySet().iterator();
            while (true) {
                if (!i$.hasNext()) {
                    break;
                }
                Map.Entry<Object, Object> e = (Map.Entry) i$.next();
                String key = e.getKey().toString();
                if (key.startsWith("rxjava.plugin.") && key.endsWith(".class") && classSimpleName.equals(e.getValue().toString())) {
                    String implKey = "rxjava.plugin." + key.substring(0, key.length() - ".class".length()).substring("rxjava.plugin.".length()) + ".impl";
                    implementingClass = props.getProperty(implKey);
                    if (implementingClass == null) {
                        throw new RuntimeException("Implementing class declaration for " + classSimpleName + " missing: " + implKey);
                    }
                }
            }
        }
        if (implementingClass == null) {
            return null;
        }
        try {
            return Class.forName(implementingClass).asSubclass(pluginClass).newInstance();
        } catch (ClassCastException e2) {
            throw new RuntimeException(classSimpleName + " implementation is not an instance of " + classSimpleName + ": " + implementingClass);
        } catch (ClassNotFoundException e3) {
            throw new RuntimeException(classSimpleName + " implementation class not found: " + implementingClass, e3);
        } catch (InstantiationException e4) {
            throw new RuntimeException(classSimpleName + " implementation not able to be instantiated: " + implementingClass, e4);
        } catch (IllegalAccessException e5) {
            throw new RuntimeException(classSimpleName + " implementation not able to be accessed: " + implementingClass, e5);
        }
    }

    public RxJavaSchedulersHook getSchedulersHook() {
        if (this.schedulersHook.get() == null) {
            Object impl = getPluginImplementationViaProperty(RxJavaSchedulersHook.class, System.getProperties());
            if (impl == null) {
                this.schedulersHook.compareAndSet((Object) null, RxJavaSchedulersHook.getDefaultInstance());
            } else {
                this.schedulersHook.compareAndSet((Object) null, (RxJavaSchedulersHook) impl);
            }
        }
        return this.schedulersHook.get();
    }

    public void registerSchedulersHook(RxJavaSchedulersHook impl) {
        if (!this.schedulersHook.compareAndSet((Object) null, impl)) {
            throw new IllegalStateException("Another strategy was already registered: " + this.schedulersHook.get());
        }
    }
}
