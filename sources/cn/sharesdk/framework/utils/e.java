package cn.sharesdk.framework.utils;

import java.io.IOException;

/* compiled from: UnicodeEscaper */
public abstract class e implements Escaper {
    /* access modifiers changed from: protected */
    public abstract char[] a(int i);

    /* access modifiers changed from: protected */
    public int a(CharSequence charSequence, int i, int i2) {
        while (i < i2) {
            int b = b(charSequence, i, i2);
            if (b < 0 || a(b) != null) {
                break;
            }
            i += Character.isSupplementaryCodePoint(b) ? 2 : 1;
        }
        return i;
    }

    public String escape(String string) {
        int length = string.length();
        int a2 = a((CharSequence) string, 0, length);
        return a2 == length ? string : a(string, a2);
    }

    /* access modifiers changed from: protected */
    public final String a(String str, int i) {
        int length = str.length();
        int i2 = 0;
        char[] cArr = (char[]) new a().get();
        int i3 = 0;
        while (i < length) {
            int b = b(str, i, length);
            if (b < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] a2 = a(b);
            if (a2 != null) {
                int i4 = i - i2;
                int length2 = i3 + i4 + a2.length;
                if (cArr.length < length2) {
                    cArr = a(cArr, i3, length2 + (length - i) + 32);
                }
                if (i4 > 0) {
                    str.getChars(i2, i, cArr, i3);
                    i3 += i4;
                }
                if (a2.length > 0) {
                    System.arraycopy(a2, 0, cArr, i3, a2.length);
                    i3 += a2.length;
                }
            }
            int i5 = (Character.isSupplementaryCodePoint(b) ? 2 : 1) + i;
            i = a((CharSequence) str, i5, length);
            i2 = i5;
        }
        int i6 = length - i2;
        if (i6 > 0) {
            int i7 = i6 + i3;
            if (cArr.length < i7) {
                cArr = a(cArr, i3, i7);
            }
            str.getChars(i2, length, cArr, i3);
            i3 = i7;
        }
        return new String(cArr, 0, i3);
    }

    public Appendable escape(final Appendable out) {
        c.a(out);
        return new Appendable() {
            int a = -1;
            char[] b = new char[2];

            public Appendable append(CharSequence csq) throws IOException {
                return append(csq, 0, csq.length());
            }

            public Appendable append(CharSequence csq, int start, int end) throws IOException {
                int i;
                if (start < end) {
                    if (this.a != -1) {
                        char charAt = csq.charAt(start);
                        i = start + 1;
                        if (!Character.isLowSurrogate(charAt)) {
                            throw new IllegalArgumentException("Expected low surrogate character but got " + charAt);
                        }
                        char[] a2 = e.this.a(Character.toCodePoint((char) this.a, charAt));
                        if (a2 != null) {
                            a(a2, a2.length);
                            start++;
                        } else {
                            out.append((char) this.a);
                        }
                        this.a = -1;
                    } else {
                        i = start;
                    }
                    while (true) {
                        int a3 = e.this.a(csq, i, end);
                        if (a3 > start) {
                            out.append(csq, start, a3);
                        }
                        if (a3 == end) {
                            break;
                        }
                        int b2 = e.b(csq, a3, end);
                        if (b2 < 0) {
                            this.a = -b2;
                            break;
                        }
                        char[] a4 = e.this.a(b2);
                        if (a4 != null) {
                            a(a4, a4.length);
                        } else {
                            a(this.b, Character.toChars(b2, this.b, 0));
                        }
                        start = a3 + (Character.isSupplementaryCodePoint(b2) ? 2 : 1);
                        i = start;
                    }
                }
                return this;
            }

            public Appendable append(char c2) throws IOException {
                if (this.a != -1) {
                    if (!Character.isLowSurrogate(c2)) {
                        throw new IllegalArgumentException("Expected low surrogate character but got '" + c2 + "' with value " + c2);
                    }
                    char[] a2 = e.this.a(Character.toCodePoint((char) this.a, c2));
                    if (a2 != null) {
                        a(a2, a2.length);
                    } else {
                        out.append((char) this.a);
                        out.append(c2);
                    }
                    this.a = -1;
                } else if (Character.isHighSurrogate(c2)) {
                    this.a = c2;
                } else if (Character.isLowSurrogate(c2)) {
                    throw new IllegalArgumentException("Unexpected low surrogate character '" + c2 + "' with value " + c2);
                } else {
                    char[] a3 = e.this.a(c2);
                    if (a3 != null) {
                        a(a3, a3.length);
                    } else {
                        out.append(c2);
                    }
                }
                return this;
            }

            private void a(char[] cArr, int i) throws IOException {
                for (int i2 = 0; i2 < i; i2++) {
                    out.append(cArr[i2]);
                }
            }
        };
    }

    protected static final int b(CharSequence charSequence, int i, int i2) {
        if (i < i2) {
            char charAt = charSequence.charAt(i);
            int i3 = i + 1;
            if (charAt < 55296 || charAt > 57343) {
                return charAt;
            }
            if (charAt > 56319) {
                throw new IllegalArgumentException("Unexpected low surrogate character '" + charAt + "' with value " + charAt + " at index " + (i3 - 1));
            } else if (i3 == i2) {
                return -charAt;
            } else {
                char charAt2 = charSequence.charAt(i3);
                if (Character.isLowSurrogate(charAt2)) {
                    return Character.toCodePoint(charAt, charAt2);
                }
                throw new IllegalArgumentException("Expected low surrogate but got char '" + charAt2 + "' with value " + charAt2 + " at index " + i3);
            }
        } else {
            throw new IndexOutOfBoundsException("Index exceeds specified range");
        }
    }

    private static final char[] a(char[] cArr, int i, int i2) {
        char[] cArr2 = new char[i2];
        if (i > 0) {
            System.arraycopy(cArr, 0, cArr2, 0, i);
        }
        return cArr2;
    }

    /* compiled from: UnicodeEscaper */
    private static final class a extends ThreadLocal<char[]> {
        private a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public char[] initialValue() {
            return new char[1024];
        }
    }
}
