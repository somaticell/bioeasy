package icepick;

import android.os.Bundle;

public interface StateBundler<T> {
    <V extends T> V get(String str, Bundle bundle);

    void put(String str, T t, Bundle bundle);
}
