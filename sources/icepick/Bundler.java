package icepick;

import android.os.Bundle;

public interface Bundler<T> {
    T get(String str, Bundle bundle);

    void put(String str, T t, Bundle bundle);
}
