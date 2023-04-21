package com.loc;

import android.location.Location;
import android.net.wifi.ScanResult;
import android.telephony.CellLocation;
import java.util.List;

public final class cz {
    private static int c = 10;
    private static int d = 100;
    private static float f = 0.5f;
    protected dd a = new dd(this);
    protected da b = new da(this);
    private cl e;

    protected cz(cl clVar) {
        this.e = clVar;
    }

    protected static void a() {
    }

    protected static void a(int i) {
        c = i;
    }

    protected static void b(int i) {
        d = i;
    }

    /* access modifiers changed from: protected */
    public final boolean a(Location location) {
        List j;
        boolean z = false;
        if (!(this.e == null || (j = this.e.j()) == null || location == null)) {
            "cell.list.size: " + j.size();
            db dbVar = null;
            if (j.size() >= 2) {
                db dbVar2 = new db((CellLocation) j.get(1));
                if (this.b.b == null) {
                    dbVar = dbVar2;
                    z = true;
                } else {
                    boolean z2 = location.distanceTo(this.b.b) > ((float) d);
                    if (!z2) {
                        db dbVar3 = this.b.a;
                        z2 = !(dbVar2.e == dbVar3.e && dbVar2.d == dbVar3.d && dbVar2.c == dbVar3.c && dbVar2.b == dbVar3.b && dbVar2.a == dbVar3.a);
                    }
                    "collect cell?: " + z2;
                    z = z2;
                    dbVar = dbVar2;
                }
            }
            if (z) {
                this.b.a = dbVar;
            }
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public final boolean b(Location location) {
        List list;
        boolean z;
        boolean z2;
        int i;
        if (this.e == null) {
            return false;
        }
        List a2 = this.e.a(false);
        if (a2.size() >= 2) {
            List list2 = (List) a2.get(1);
            if (this.a.b == null) {
                z = true;
            } else if (list2 == null || list2.size() <= 0) {
                list = list2;
                z = false;
            } else {
                z = location.distanceTo(this.a.b) > ((float) c);
                if (!z) {
                    List list3 = this.a.a;
                    float f2 = f;
                    if (list2 == null || list3 == null) {
                        z2 = false;
                    } else if (list2 == null || list3 == null) {
                        z2 = false;
                    } else {
                        int size = list2.size();
                        int size2 = list3.size();
                        float f3 = (float) (size + size2);
                        if (size == 0 && size2 == 0) {
                            z2 = true;
                        } else if (size == 0 || size2 == 0) {
                            z2 = false;
                        } else {
                            int i2 = 0;
                            int i3 = 0;
                            while (i2 < size) {
                                String str = ((ScanResult) list2.get(i2)).BSSID;
                                if (str != null) {
                                    int i4 = 0;
                                    while (true) {
                                        if (i4 >= size2) {
                                            break;
                                        } else if (str.equals(((dc) list3.get(i4)).a)) {
                                            i = i3 + 1;
                                            break;
                                        } else {
                                            i4++;
                                        }
                                    }
                                    i2++;
                                    i3 = i;
                                }
                                i = i3;
                                i2++;
                                i3 = i;
                            }
                            z2 = ((float) (i3 << 1)) >= f3 * f2;
                        }
                    }
                    z = !z2;
                } else {
                    list = list2;
                }
            }
            list = list2;
        } else {
            list = null;
            z = false;
        }
        if (z) {
            this.a.a.clear();
            int size3 = list.size();
            for (int i5 = 0; i5 < size3; i5++) {
                this.a.a.add(new dc(((ScanResult) list.get(i5)).BSSID));
            }
        }
        return z;
    }
}
