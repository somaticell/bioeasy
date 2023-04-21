package com.loc;

import android.text.TextUtils;
import com.loc.bb;
import java.util.Locale;

/* compiled from: GeoHash */
public class bc {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final int[] b = {16, 8, 4, 2, 1};

    private bc() {
    }

    public static final String a(double d, double d2) {
        return a(d, d2, 6);
    }

    public static final String a(double d, double d2, int i) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        int i2 = 0;
        int i3 = 0;
        double[] dArr = {-90.0d, 90.0d};
        double[] dArr2 = {-180.0d, 180.0d};
        while (sb.length() < i) {
            if (z) {
                double d3 = (dArr2[0] + dArr2[1]) / 2.0d;
                if (d2 > d3) {
                    i3 |= b[i2];
                    dArr2[0] = d3;
                } else {
                    dArr2[1] = d3;
                }
            } else {
                double d4 = (dArr[0] + dArr[1]) / 2.0d;
                if (d > d4) {
                    i3 |= b[i2];
                    dArr[0] = d4;
                } else {
                    dArr[1] = d4;
                }
            }
            z = !z;
            if (i2 < 4) {
                i2++;
            } else {
                sb.append(a[i3]);
                i2 = 0;
                i3 = 0;
            }
        }
        return sb.toString();
    }

    public static final String[] a(String str) {
        String[] strArr = new String[24];
        strArr[0] = a(str, "right");
        strArr[1] = a(str, "btm");
        strArr[2] = a(str, "left");
        strArr[3] = a(str, "top");
        strArr[4] = a(strArr[0], "top");
        strArr[5] = a(strArr[0], "btm");
        strArr[6] = a(strArr[2], "top");
        strArr[7] = a(strArr[2], "btm");
        strArr[8] = a(strArr[0], "right");
        strArr[9] = a(strArr[8], "top");
        strArr[10] = a(strArr[9], "top");
        strArr[11] = a(strArr[10], "left");
        strArr[12] = a(strArr[11], "left");
        strArr[13] = a(strArr[12], "left");
        strArr[14] = a(strArr[13], "left");
        strArr[15] = a(strArr[14], "btm");
        strArr[16] = a(strArr[15], "btm");
        strArr[17] = a(strArr[16], "btm");
        strArr[18] = a(strArr[17], "btm");
        strArr[19] = a(strArr[18], "right");
        strArr[20] = a(strArr[19], "right");
        strArr[21] = a(strArr[20], "right");
        strArr[22] = a(strArr[21], "right");
        strArr[23] = a(strArr[22], "top");
        return strArr;
    }

    private static final String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        String lowerCase = str.toLowerCase(Locale.US);
        char charAt = lowerCase.charAt(lowerCase.length() - 1);
        String str3 = lowerCase.length() % 2 == 0 ? "odd" : "even";
        String substring = lowerCase.substring(0, lowerCase.length() - 1);
        if (((String) bb.a.a.get(str2).get(str3)).indexOf(charAt) != -1 && !TextUtils.isEmpty(substring)) {
            substring = a(substring, str2);
        }
        return substring + a[((String) bb.b.a.get(str2).get(str3)).indexOf(charAt)];
    }
}
