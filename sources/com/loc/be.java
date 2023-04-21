package com.loc;

import android.support.v4.os.EnvironmentCompat;
import java.util.Locale;

/* compiled from: Cgi */
public class be {
    public String a = "";
    public String b = "";
    public int c = 0;
    public int d = 0;
    public int e = 0;
    public int f = 0;
    public int g = 0;
    public int h = 0;
    public int i = 0;
    public int j = -113;
    public int k = 9;

    protected be(int i2) {
        this.k = i2;
    }

    public String toString() {
        switch (this.k) {
            case 1:
                return String.format(Locale.US, "GSM lac=%d, cid=%d, mnc=%s", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.d), this.b});
            case 2:
                return String.format(Locale.US, "CDMA bid=%d, nid=%d, sid=%d", new Object[]{Integer.valueOf(this.i), Integer.valueOf(this.h), Integer.valueOf(this.g)});
            default:
                return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public boolean a(be beVar) {
        if (beVar == null) {
            return false;
        }
        switch (beVar.k) {
            case 1:
                if (this.k == 1 && beVar.c == this.c && beVar.d == this.d && beVar.b != null && beVar.b.equals(this.b)) {
                    return true;
                }
                return false;
            case 2:
                if (this.k == 2 && beVar.i == this.i && beVar.h == this.h && beVar.g == this.g) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }
}
