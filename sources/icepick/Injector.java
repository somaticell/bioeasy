package icepick;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Injector {

    public static class Helper {
        private final String baseKey;
        private final Map<String, Bundler<?>> bundlers;

        public Helper(String str, Map<String, Bundler<?>> map) {
            this.baseKey = str;
            this.bundlers = map;
        }

        public <T> T getWithBundler(Bundle bundle, String str) {
            return this.bundlers.get(str).get(str + this.baseKey, bundle);
        }

        public <T> void putWithBundler(Bundle bundle, String str, T t) {
            this.bundlers.get(str).put(str + this.baseKey, t, bundle);
        }

        public boolean getBoolean(Bundle bundle, String str) {
            return bundle.getBoolean(str + this.baseKey);
        }

        public void putBoolean(Bundle bundle, String str, boolean z) {
            bundle.putBoolean(str + this.baseKey, z);
        }

        public Boolean getBoxedBoolean(Bundle bundle, String str) {
            if (bundle.containsKey(str + this.baseKey)) {
                return Boolean.valueOf(bundle.getBoolean(str + this.baseKey));
            }
            return null;
        }

        public void putBoxedBoolean(Bundle bundle, String str, Boolean bool) {
            if (bool != null) {
                bundle.putBoolean(str + this.baseKey, bool.booleanValue());
            }
        }

        public boolean[] getBooleanArray(Bundle bundle, String str) {
            return bundle.getBooleanArray(str + this.baseKey);
        }

        public void putBooleanArray(Bundle bundle, String str, boolean[] zArr) {
            bundle.putBooleanArray(str + this.baseKey, zArr);
        }

        public byte getByte(Bundle bundle, String str) {
            return bundle.getByte(str + this.baseKey);
        }

        public void putByte(Bundle bundle, String str, byte b) {
            bundle.putByte(str + this.baseKey, b);
        }

        public Byte getBoxedByte(Bundle bundle, String str) {
            if (bundle.containsKey(str + this.baseKey)) {
                return Byte.valueOf(bundle.getByte(str + this.baseKey));
            }
            return null;
        }

        public void putBoxedByte(Bundle bundle, String str, Byte b) {
            if (b != null) {
                bundle.putByte(str + this.baseKey, b.byteValue());
            }
        }

        public byte[] getByteArray(Bundle bundle, String str) {
            return bundle.getByteArray(str + this.baseKey);
        }

        public void putByteArray(Bundle bundle, String str, byte[] bArr) {
            bundle.putByteArray(str + this.baseKey, bArr);
        }

        public short getShort(Bundle bundle, String str) {
            return bundle.getShort(str + this.baseKey);
        }

        public void putShort(Bundle bundle, String str, short s) {
            bundle.putShort(str + this.baseKey, s);
        }

        public Short getBoxedShort(Bundle bundle, String str) {
            if (bundle.containsKey(str + this.baseKey)) {
                return Short.valueOf(bundle.getShort(str + this.baseKey));
            }
            return null;
        }

        public void putBoxedShort(Bundle bundle, String str, Short sh) {
            if (sh != null) {
                bundle.putShort(str + this.baseKey, sh.shortValue());
            }
        }

        public short[] getShortArray(Bundle bundle, String str) {
            return bundle.getShortArray(str + this.baseKey);
        }

        public void putShortArray(Bundle bundle, String str, short[] sArr) {
            bundle.putShortArray(str + this.baseKey, sArr);
        }

        public int getInt(Bundle bundle, String str) {
            return bundle.getInt(str + this.baseKey);
        }

        public void putInt(Bundle bundle, String str, int i) {
            bundle.putInt(str + this.baseKey, i);
        }

        public Integer getBoxedInt(Bundle bundle, String str) {
            if (bundle.containsKey(str + this.baseKey)) {
                return Integer.valueOf(bundle.getInt(str + this.baseKey));
            }
            return null;
        }

        public void putBoxedInt(Bundle bundle, String str, Integer num) {
            if (num != null) {
                bundle.putInt(str + this.baseKey, num.intValue());
            }
        }

        public int[] getIntArray(Bundle bundle, String str) {
            return bundle.getIntArray(str + this.baseKey);
        }

        public void putIntArray(Bundle bundle, String str, int[] iArr) {
            bundle.putIntArray(str + this.baseKey, iArr);
        }

        public long getLong(Bundle bundle, String str) {
            return bundle.getLong(str + this.baseKey);
        }

        public void putLong(Bundle bundle, String str, long j) {
            bundle.putLong(str + this.baseKey, j);
        }

        public Long getBoxedLong(Bundle bundle, String str) {
            if (bundle.containsKey(str + this.baseKey)) {
                return Long.valueOf(bundle.getLong(str + this.baseKey));
            }
            return null;
        }

        public void putBoxedLong(Bundle bundle, String str, Long l) {
            if (l != null) {
                bundle.putLong(str + this.baseKey, l.longValue());
            }
        }

        public long[] getLongArray(Bundle bundle, String str) {
            return bundle.getLongArray(str + this.baseKey);
        }

        public void putLongArray(Bundle bundle, String str, long[] jArr) {
            bundle.putLongArray(str + this.baseKey, jArr);
        }

        public float getFloat(Bundle bundle, String str) {
            return bundle.getFloat(str + this.baseKey);
        }

        public void putFloat(Bundle bundle, String str, float f) {
            bundle.putFloat(str + this.baseKey, f);
        }

        public Float getBoxedFloat(Bundle bundle, String str) {
            if (bundle.containsKey(str + this.baseKey)) {
                return Float.valueOf(bundle.getFloat(str + this.baseKey));
            }
            return null;
        }

        public void putBoxedFloat(Bundle bundle, String str, Float f) {
            if (f != null) {
                bundle.putFloat(str + this.baseKey, f.floatValue());
            }
        }

        public float[] getFloatArray(Bundle bundle, String str) {
            return bundle.getFloatArray(str + this.baseKey);
        }

        public void putFloatArray(Bundle bundle, String str, float[] fArr) {
            bundle.putFloatArray(str + this.baseKey, fArr);
        }

        public double getDouble(Bundle bundle, String str) {
            return bundle.getDouble(str + this.baseKey);
        }

        public void putDouble(Bundle bundle, String str, double d) {
            bundle.putDouble(str + this.baseKey, d);
        }

        public Double getBoxedDouble(Bundle bundle, String str) {
            if (bundle.containsKey(str + this.baseKey)) {
                return Double.valueOf(bundle.getDouble(str + this.baseKey));
            }
            return null;
        }

        public void putBoxedDouble(Bundle bundle, String str, Double d) {
            if (d != null) {
                bundle.putDouble(str + this.baseKey, d.doubleValue());
            }
        }

        public double[] getDoubleArray(Bundle bundle, String str) {
            return bundle.getDoubleArray(str + this.baseKey);
        }

        public void putDoubleArray(Bundle bundle, String str, double[] dArr) {
            bundle.putDoubleArray(str + this.baseKey, dArr);
        }

        public char getChar(Bundle bundle, String str) {
            return bundle.getChar(str + this.baseKey);
        }

        public void putChar(Bundle bundle, String str, char c) {
            bundle.putChar(str + this.baseKey, c);
        }

        public Character getBoxedChar(Bundle bundle, String str) {
            if (bundle.containsKey(str + this.baseKey)) {
                return Character.valueOf(bundle.getChar(str + this.baseKey));
            }
            return null;
        }

        public void putBoxedChar(Bundle bundle, String str, Character ch) {
            if (ch != null) {
                bundle.putChar(str + this.baseKey, ch.charValue());
            }
        }

        public char[] getCharArray(Bundle bundle, String str) {
            return bundle.getCharArray(str + this.baseKey);
        }

        public void putCharArray(Bundle bundle, String str, char[] cArr) {
            bundle.putCharArray(str + this.baseKey, cArr);
        }

        public String getString(Bundle bundle, String str) {
            return bundle.getString(str + this.baseKey);
        }

        public void putString(Bundle bundle, String str, String str2) {
            bundle.putString(str + this.baseKey, str2);
        }

        public String[] getStringArray(Bundle bundle, String str) {
            return bundle.getStringArray(str + this.baseKey);
        }

        public void putStringArray(Bundle bundle, String str, String[] strArr) {
            bundle.putStringArray(str + this.baseKey, strArr);
        }

        public CharSequence getCharSequence(Bundle bundle, String str) {
            return bundle.getCharSequence(str + this.baseKey);
        }

        public void putCharSequence(Bundle bundle, String str, CharSequence charSequence) {
            bundle.putCharSequence(str + this.baseKey, charSequence);
        }

        public CharSequence[] getCharSequenceArray(Bundle bundle, String str) {
            return bundle.getCharSequenceArray(str + this.baseKey);
        }

        public void putCharSequenceArray(Bundle bundle, String str, CharSequence[] charSequenceArr) {
            bundle.putCharSequenceArray(str + this.baseKey, charSequenceArr);
        }

        public Bundle getBundle(Bundle bundle, String str) {
            return bundle.getBundle(str + this.baseKey);
        }

        public void putBundle(Bundle bundle, String str, Bundle bundle2) {
            bundle.putBundle(str + this.baseKey, bundle2);
        }

        public <T extends Parcelable> T getParcelable(Bundle bundle, String str) {
            return bundle.getParcelable(str + this.baseKey);
        }

        public void putParcelable(Bundle bundle, String str, Parcelable parcelable) {
            bundle.putParcelable(str + this.baseKey, parcelable);
        }

        public Parcelable[] getParcelableArray(Bundle bundle, String str) {
            return bundle.getParcelableArray(str + this.baseKey);
        }

        public void putParcelableArray(Bundle bundle, String str, Parcelable[] parcelableArr) {
            bundle.putParcelableArray(str + this.baseKey, parcelableArr);
        }

        public <T extends Serializable> T getSerializable(Bundle bundle, String str) {
            return bundle.getSerializable(str + this.baseKey);
        }

        public void putSerializable(Bundle bundle, String str, Serializable serializable) {
            bundle.putSerializable(str + this.baseKey, serializable);
        }

        public ArrayList<Integer> getIntegerArrayList(Bundle bundle, String str) {
            return bundle.getIntegerArrayList(str + this.baseKey);
        }

        public void putIntegerArrayList(Bundle bundle, String str, ArrayList<Integer> arrayList) {
            bundle.putIntegerArrayList(str + this.baseKey, arrayList);
        }

        public ArrayList<String> getStringArrayList(Bundle bundle, String str) {
            return bundle.getStringArrayList(str + this.baseKey);
        }

        public void putStringArrayList(Bundle bundle, String str, ArrayList<String> arrayList) {
            bundle.putStringArrayList(str + this.baseKey, arrayList);
        }

        public ArrayList<CharSequence> getCharSequenceArrayList(Bundle bundle, String str) {
            return bundle.getCharSequenceArrayList(str + this.baseKey);
        }

        public void putCharSequenceArrayList(Bundle bundle, String str, ArrayList<CharSequence> arrayList) {
            bundle.putCharSequenceArrayList(str + this.baseKey, arrayList);
        }

        public <T extends Parcelable> ArrayList<T> getParcelableArrayList(Bundle bundle, String str) {
            return bundle.getParcelableArrayList(str + this.baseKey);
        }

        public void putParcelableArrayList(Bundle bundle, String str, ArrayList<? extends Parcelable> arrayList) {
            bundle.putParcelableArrayList(str + this.baseKey, arrayList);
        }

        public <T extends Parcelable> SparseArray<T> getSparseParcelableArray(Bundle bundle, String str) {
            return bundle.getSparseParcelableArray(str + this.baseKey);
        }

        public void putSparseParcelableArray(Bundle bundle, String str, SparseArray<? extends Parcelable> sparseArray) {
            bundle.putSparseParcelableArray(str + this.baseKey, sparseArray);
        }

        public Parcelable getParent(Bundle bundle) {
            return bundle.getParcelable(this.baseKey + "$$SUPER");
        }

        public Bundle putParent(Parcelable parcelable) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(this.baseKey + "$$SUPER", parcelable);
            return bundle;
        }
    }

    public static class Object<T> extends Injector {
        public void restore(T t, Bundle bundle) {
        }

        public void save(T t, Bundle bundle) {
        }
    }

    public static class View<T> extends Injector {
        public Parcelable restore(T t, Parcelable parcelable) {
            return parcelable;
        }

        public Parcelable save(T t, Parcelable parcelable) {
            return parcelable;
        }
    }
}
