package com.loc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;

/* compiled from: MPos */
public class ay {
    private ArrayList<a> a = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<c> b = new ArrayList<>();
    /* access modifiers changed from: private */
    public short[][] c = ((short[][]) Array.newInstance(Short.TYPE, new int[]{128, 128}));
    private b d = new b();

    /* compiled from: MPos */
    public class c {
        public double a = 0.0d;
        public double b = 0.0d;
        public int c = 0;
        public String d = "0";
        public int e = -1;

        /* access modifiers changed from: private */
        public boolean a(c cVar) {
            boolean z = false;
            double b2 = b(cVar);
            if (b2 < 500.0d) {
                return true;
            }
            if ((this.e <= 0 || cVar.e != 0) && (this.e != 0 || cVar.e <= 0)) {
                if (this.e > 0 || cVar.e > 0) {
                    if (b2 < 5000.0d || b2 < ((double) this.c) || b2 < ((double) cVar.c)) {
                        z = true;
                    }
                    return z;
                }
                if (b2 < ((double) this.c) || b2 < ((double) cVar.c) || b2 < 500.0d) {
                    z = true;
                }
                return z;
            } else if (this.e == 1 || cVar.e == 1) {
                if (b2 >= 3000.0d || (b2 >= ((double) this.c) * 1.5d && b2 >= ((double) cVar.c) * 1.5d)) {
                    return false;
                }
                return true;
            } else if (b2 >= 5000.0d) {
                return false;
            } else {
                return true;
            }
        }

        public c(double d2, double d3) {
            this.a = d2;
            this.b = d3;
        }

        public c(double d2, double d3, int i, int i2) {
            this.a = d2;
            this.b = d3;
            this.c = i;
            this.e = i2;
        }

        public c(double d2, double d3, int i, String str, double d4, int i2) {
            this.a = d2;
            this.b = d3;
            this.d = str;
            this.c = i;
            this.e = i2;
        }

        /* access modifiers changed from: private */
        public double b(c cVar) {
            double d2 = 6356725.0d + ((21412.0d * (90.0d - this.a)) / 90.0d);
            double cos = Math.cos((this.a * 3.1415926535898d) / 180.0d) * d2 * (((cVar.b - this.b) * 3.1415926535898d) / 180.0d);
            double d3 = d2 * (((cVar.a - this.a) * 3.1415926535898d) / 180.0d);
            return Math.sqrt((d3 * d3) + (cos * cos));
        }
    }

    /* compiled from: MPos */
    class a implements Comparable<a> {
        private ArrayList<c> b;
        private ArrayList<c> c;
        /* access modifiers changed from: private */
        public double d;
        private String e;

        public a() {
            this.b = new ArrayList<>();
            this.c = new ArrayList<>();
            this.d = 0.0d;
            this.e = "";
            this.d = 0.0d;
            this.e = "";
        }

        /* renamed from: a */
        public int compareTo(a aVar) {
            double d2 = aVar.d - this.d;
            if (d2 > 0.0d) {
                return 1;
            }
            return d2 == 0.0d ? 0 : -1;
        }

        public void a() {
            double size = (double) this.c.size();
            double d2 = 0.0d;
            Iterator<c> it = this.b.iterator();
            while (true) {
                double d3 = d2;
                if (it.hasNext()) {
                    d2 = (it.next().e > 1 ? 0.6d : 1.0d) + d3;
                } else {
                    this.d = (d3 * 3.0d) + size;
                    this.d += ((d3 * 3.0d) + 0.1d) * (size + 0.1d);
                    return;
                }
            }
        }

        public void a(c cVar) {
            if (cVar.e == 0) {
                this.c.add(cVar);
            } else if (cVar.e > 0) {
                this.b.add(cVar);
            }
            if ("0".equals(this.e)) {
                this.e = cVar.d;
            }
        }

        public c b() {
            int i;
            int i2;
            int intValue;
            int i3;
            double d2 = 0.0d;
            int i4 = this.b.isEmpty() ? 0 : 3;
            if (!this.c.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                Iterator<c> it = this.c.iterator();
                double d3 = 0.0d;
                double d4 = 0.0d;
                while (it.hasNext()) {
                    c next = it.next();
                    arrayList.add(Integer.valueOf(next.c));
                    d3 += next.a;
                    d4 = 1.0d + d4;
                    d2 = next.b + d2;
                }
                Collections.sort(arrayList);
                if (arrayList.size() == 1) {
                    intValue = ((Integer) arrayList.get(0)).intValue();
                } else {
                    intValue = ((Integer) arrayList.get(arrayList.size() / 2)).intValue();
                }
                if (this.c.size() == 1) {
                    i3 = 500;
                } else if (intValue > 500) {
                    i3 = 300;
                } else if (intValue < 30) {
                    i3 = 30;
                } else {
                    i3 = intValue;
                }
                return new c(d3 / d4, d2 / d4, i3, this.e, this.d, i4);
            }
            Iterator<c> it2 = this.b.iterator();
            if (!it2.hasNext()) {
                return null;
            }
            c next2 = it2.next();
            if (next2.e == 1) {
                return new c(next2.a, next2.b, next2.c, this.e, this.d, 1);
            }
            if (0 < next2.c) {
                i = next2.c;
            } else {
                i = 0;
            }
            double d5 = 0.0d + next2.b;
            double d6 = 0.0d + next2.a;
            double d7 = 0.0d + 1.0d;
            if (i > 5000) {
                i2 = 5000;
            } else {
                i2 = i;
            }
            return new c(d6 / d7, d5 / d7, i2, this.e, this.d, 2);
        }
    }

    /* compiled from: MPos */
    private class b {
        /* access modifiers changed from: private */
        public ArrayList<ArrayList<Integer>> b;

        public b() {
            this.b = null;
            this.b = new ArrayList<>();
        }

        private void a(int i, int i2) {
            int i3;
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(i));
            arrayList.add(Integer.valueOf(i2));
            ay.this.c[i][i2] = -1;
            ay.this.c[i2][i] = -1;
            ArrayList arrayList2 = new ArrayList();
            for (int i4 = 0; i4 < ay.this.b.size(); i4++) {
                if (!(ay.this.c[i][i4] == 0 || ay.this.c[i2][i4] == 0)) {
                    arrayList2.add(Integer.valueOf(i4));
                }
            }
            while (!arrayList2.isEmpty()) {
                int intValue = ((Integer) arrayList2.get(0)).intValue();
                arrayList2.remove(0);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    ay.this.c[intValue][num.intValue()] = -1;
                    ay.this.c[num.intValue()][intValue] = -1;
                }
                arrayList.add(Integer.valueOf(intValue));
                int i5 = 0;
                while (i5 < arrayList2.size()) {
                    if (ay.this.c[intValue][((Integer) arrayList2.get(i5)).intValue()] == 0) {
                        arrayList2.remove(i5);
                        i3 = i5;
                    } else {
                        i3 = i5 + 1;
                    }
                    i5 = i3;
                }
            }
            this.b.add(arrayList);
        }

        public void a() throws Exception {
            int size = ay.this.b.size();
            for (int i = 0; i < size; i++) {
                for (int i2 = 0; i2 < size; i2++) {
                    if (ay.this.c[i][i2] > 0) {
                        a(i, i2);
                    }
                }
            }
            for (int i3 = 0; i3 < size; i3++) {
                boolean z = true;
                int i4 = 0;
                while (true) {
                    if (i4 >= size) {
                        break;
                    } else if (ay.this.c[i3][i4] > 0) {
                        throw new Exception("non visited edge");
                    } else if (ay.this.c[i3][i4] < 0) {
                        z = false;
                        break;
                    } else {
                        i4++;
                    }
                }
                if (z) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(Integer.valueOf(i3));
                    this.b.add(arrayList);
                }
            }
        }
    }

    public void a(int i, String str) throws Exception {
        int size = this.b.size();
        StringTokenizer stringTokenizer = new StringTokenizer(str, "\\|");
        if (stringTokenizer.countTokens() >= 3) {
            ArrayList arrayList = new ArrayList();
            while (stringTokenizer.hasMoreElements()) {
                arrayList.add(stringTokenizer.nextToken());
            }
            this.b.add(new c(Double.parseDouble((String) arrayList.get(0)), Double.parseDouble((String) arrayList.get(1)), Double.valueOf(Double.parseDouble((String) arrayList.get(2))).intValue(), i));
            if (this.b.size() > 128) {
                throw new Exception("Atomic Pos Larger Than MAXN 512!");
            }
            for (int i2 = 0; i2 < size; i2++) {
                for (int i3 = size; i3 < this.b.size(); i3++) {
                    if (this.b.get(i2).a(this.b.get(i3))) {
                        this.c[i2][i3] = 1;
                        this.c[i3][i2] = 1;
                    }
                }
            }
            arrayList.clear();
        }
    }

    public ArrayList<c> a(double d2, double d3) throws Exception {
        this.d.a();
        ArrayList<c> arrayList = new ArrayList<>();
        Iterator it = this.d.b.iterator();
        while (it.hasNext()) {
            a aVar = new a();
            Iterator it2 = ((ArrayList) it.next()).iterator();
            while (it2.hasNext()) {
                aVar.a(this.b.get(((Integer) it2.next()).intValue()));
            }
            aVar.a();
            this.a.add(aVar);
        }
        if (this.a.isEmpty()) {
            return null;
        }
        Collections.sort(this.a);
        if (!(d2 == 0.0d || d3 == 0.0d)) {
            c cVar = new c(d3, d2);
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= this.a.size()) {
                    break;
                }
                c b2 = this.a.get(i2).b();
                double b3 = cVar.b(b2);
                if (b2.e > 0) {
                    if (b3 < 7000.0d && b3 > 2.0d) {
                        double unused = this.a.get(i2).d = this.a.get(i2).d * 5.0d;
                    }
                } else if (b3 < 10000.0d && b3 > 2.0d) {
                    double unused2 = this.a.get(i2).d = this.a.get(i2).d * 5.0d;
                }
                i = i2 + 1;
            }
            Collections.sort(this.a);
        }
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 >= this.a.size()) {
                return arrayList;
            }
            if (arrayList.size() >= 5) {
                return arrayList;
            }
            arrayList.add(this.a.get(i4).b());
            i3 = i4 + 1;
        }
    }
}
