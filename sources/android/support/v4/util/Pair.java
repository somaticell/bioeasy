package android.support.v4.util;

import com.alipay.sdk.util.h;

public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F first2, S second2) {
        this.first = first2;
        this.second = second2;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair) o;
        if (!objectsEqual(p.first, this.first) || !objectsEqual(p.second, this.second)) {
            return false;
        }
        return true;
    }

    private static boolean objectsEqual(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.first == null ? 0 : this.first.hashCode();
        if (this.second != null) {
            i = this.second.hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        return "Pair{" + String.valueOf(this.first) + " " + String.valueOf(this.second) + h.d;
    }

    public static <A, B> Pair<A, B> create(A a, B b) {
        return new Pair<>(a, b);
    }
}
