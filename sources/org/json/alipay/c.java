package org.json.alipay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class c {
    private int a;
    private Reader b;
    private char c;
    private boolean d;

    private c(Reader reader) {
        this.b = !reader.markSupported() ? new BufferedReader(reader) : reader;
        this.d = false;
        this.a = 0;
    }

    public c(String str) {
        this((Reader) new StringReader(str));
    }

    private String a(int i) {
        int i2 = 0;
        if (i == 0) {
            return "";
        }
        char[] cArr = new char[i];
        if (this.d) {
            this.d = false;
            cArr[0] = this.c;
            i2 = 1;
        }
        while (i2 < i) {
            try {
                int read = this.b.read(cArr, i2, i - i2);
                if (read == -1) {
                    break;
                }
                i2 += read;
            } catch (IOException e) {
                throw new JSONException((Throwable) e);
            }
        }
        this.a += i2;
        if (i2 < i) {
            throw a("Substring bounds error");
        }
        this.c = cArr[i - 1];
        return new String(cArr);
    }

    public final JSONException a(String str) {
        return new JSONException(str + toString());
    }

    public final void a() {
        if (this.d || this.a <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.a--;
        this.d = true;
    }

    public final char b() {
        if (this.d) {
            this.d = false;
            if (this.c != 0) {
                this.a++;
            }
            return this.c;
        }
        try {
            int read = this.b.read();
            if (read <= 0) {
                this.c = 0;
                return 0;
            }
            this.a++;
            this.c = (char) read;
            return this.c;
        } catch (IOException e) {
            throw new JSONException((Throwable) e);
        }
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 142 */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0006, code lost:
        continue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001b, code lost:
        if (r1 == 10) goto L_0x0006;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001d, code lost:
        if (r1 == 13) goto L_0x0006;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final char c() {
        /*
            r5 = this;
            r4 = 13
            r3 = 10
            r0 = 47
        L_0x0006:
            char r1 = r5.b()
            if (r1 != r0) goto L_0x003c
            char r1 = r5.b()
            switch(r1) {
                case 42: goto L_0x002f;
                case 47: goto L_0x0017;
                default: goto L_0x0013;
            }
        L_0x0013:
            r5.a()
        L_0x0016:
            return r0
        L_0x0017:
            char r1 = r5.b()
            if (r1 == r3) goto L_0x0006
            if (r1 == r4) goto L_0x0006
            if (r1 != 0) goto L_0x0017
            goto L_0x0006
        L_0x0022:
            r2 = 42
            if (r1 != r2) goto L_0x002f
            char r1 = r5.b()
            if (r1 == r0) goto L_0x0006
            r5.a()
        L_0x002f:
            char r1 = r5.b()
            if (r1 != 0) goto L_0x0022
            java.lang.String r0 = "Unclosed comment"
            org.json.alipay.JSONException r0 = r5.a((java.lang.String) r0)
            throw r0
        L_0x003c:
            r2 = 35
            if (r1 != r2) goto L_0x004b
        L_0x0040:
            char r1 = r5.b()
            if (r1 == r3) goto L_0x0006
            if (r1 == r4) goto L_0x0006
            if (r1 != 0) goto L_0x0040
            goto L_0x0006
        L_0x004b:
            if (r1 == 0) goto L_0x0051
            r2 = 32
            if (r1 <= r2) goto L_0x0006
        L_0x0051:
            r0 = r1
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.alipay.c.c():char");
    }

    public final Object d() {
        char c2 = c();
        switch (c2) {
            case '\"':
            case '\'':
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    char b2 = b();
                    switch (b2) {
                        case 0:
                        case 10:
                        case 13:
                            throw a("Unterminated string");
                        case '\\':
                            char b3 = b();
                            switch (b3) {
                                case 'b':
                                    stringBuffer.append(8);
                                    break;
                                case 'f':
                                    stringBuffer.append(12);
                                    break;
                                case 'n':
                                    stringBuffer.append(10);
                                    break;
                                case 'r':
                                    stringBuffer.append(13);
                                    break;
                                case 't':
                                    stringBuffer.append(9);
                                    break;
                                case 'u':
                                    stringBuffer.append((char) Integer.parseInt(a(4), 16));
                                    break;
                                case 'x':
                                    stringBuffer.append((char) Integer.parseInt(a(2), 16));
                                    break;
                                default:
                                    stringBuffer.append(b3);
                                    break;
                            }
                        default:
                            if (b2 != c2) {
                                stringBuffer.append(b2);
                                break;
                            } else {
                                return stringBuffer.toString();
                            }
                    }
                }
            case '(':
            case '[':
                a();
                return new a(this);
            case '{':
                a();
                return new b(this);
            default:
                StringBuffer stringBuffer2 = new StringBuffer();
                char c3 = c2;
                while (c3 >= ' ' && ",:]}/\\\"[{;=#".indexOf(c3) < 0) {
                    stringBuffer2.append(c3);
                    c3 = b();
                }
                a();
                String trim = stringBuffer2.toString().trim();
                if (trim.equals("")) {
                    throw a("Missing value");
                } else if (trim.equalsIgnoreCase("true")) {
                    return Boolean.TRUE;
                } else {
                    if (trim.equalsIgnoreCase("false")) {
                        return Boolean.FALSE;
                    }
                    if (trim.equalsIgnoreCase("null")) {
                        return b.a;
                    }
                    if ((c2 < '0' || c2 > '9') && c2 != '.' && c2 != '-' && c2 != '+') {
                        return trim;
                    }
                    if (c2 == '0') {
                        if (trim.length() <= 2 || !(trim.charAt(1) == 'x' || trim.charAt(1) == 'X')) {
                            try {
                                return new Integer(Integer.parseInt(trim, 8));
                            } catch (Exception e) {
                            }
                        } else {
                            try {
                                return new Integer(Integer.parseInt(trim.substring(2), 16));
                            } catch (Exception e2) {
                            }
                        }
                    }
                    try {
                        return new Integer(trim);
                    } catch (Exception e3) {
                        try {
                            return new Long(trim);
                        } catch (Exception e4) {
                            try {
                                return new Double(trim);
                            } catch (Exception e5) {
                                return trim;
                            }
                        }
                    }
                }
        }
    }

    public final String toString() {
        return " at character " + this.a;
    }
}
