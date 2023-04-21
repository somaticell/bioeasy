package com.baidu.location.indoor;

import com.baidu.location.BDLocation;
import com.baidu.location.indoor.g;
import com.baidu.location.indoor.mapversion.a.a;
import com.baidu.location.indoor.o;
import java.util.Date;

class j implements o.a {
    final /* synthetic */ g a;

    j(g gVar) {
        this.a = gVar;
    }

    public synchronized void a(double d, double d2) {
        this.a.a = true;
        this.a.b = true;
        double unused = this.a.N = 0.4d;
        if (this.a.L > 0.1d || this.a.M > 0.1d) {
            boolean z = false;
            double[] dArr = null;
            if (this.a.af && this.a.ag) {
                double[] a2 = a.a(this.a.z, d, d2);
                boolean z2 = a2 != null;
                if (a2 != null) {
                }
                double[] dArr2 = a2;
                z = z2;
                dArr = dArr2;
            }
            double[] a3 = (!this.a.af || !this.a.ag || !z) ? this.a.a(this.a.M, this.a.L, d, d2) : dArr;
            this.a.ah.add(Float.valueOf((float) d2));
            double unused2 = this.a.O = d2;
            try {
                double e = this.a.M;
                double d3 = this.a.L;
                if (this.a.ad != null) {
                    e = this.a.ad.getLatitude();
                    d3 = this.a.ad.getLongitude();
                }
                if (a3[0] >= 1.0d && a3[1] >= 1.0d && n.a(a3[0], a3[1], e, d3) <= 10000.0d) {
                    BDLocation bDLocation = new BDLocation();
                    bDLocation.setLocType(161);
                    bDLocation.setLatitude(a3[0]);
                    bDLocation.setLongitude(a3[1]);
                    bDLocation.setRadius(15.0f);
                    if (this.a.Y) {
                        bDLocation.setRadius(8.0f);
                    }
                    bDLocation.setDirection((float) d2);
                    bDLocation.setTime(this.a.d.format(new Date()));
                    double unused3 = this.a.M = a3[0];
                    double unused4 = this.a.L = a3[1];
                    if (this.a.z != null) {
                        bDLocation.setFloor(this.a.z);
                    }
                    if (this.a.A != null) {
                        bDLocation.setBuildingID(this.a.A);
                    }
                    if (this.a.C != null) {
                        bDLocation.setBuildingName(this.a.C);
                    }
                    bDLocation.setParkAvailable(this.a.F);
                    if (this.a.E != null) {
                        bDLocation.setNetworkLocationType(this.a.E);
                    } else if (this.a.Y) {
                        bDLocation.setNetworkLocationType("ble");
                    } else {
                        bDLocation.setNetworkLocationType("wf");
                    }
                    if (this.a.r) {
                        bDLocation.setIndoorLocMode(true);
                        g.p(this.a);
                        if (this.a.R.size() > 50) {
                            this.a.R.clear();
                        }
                        this.a.R.add(new g.C0010g(this.a.m.d(), d, d2));
                        if (this.a.w < 60 && this.a.m.d() % 3 == 0) {
                            g.t(this.a);
                            bDLocation.setNetworkLocationType("dr");
                            BDLocation bDLocation2 = new BDLocation(bDLocation);
                            if (0 != 0) {
                                bDLocation2.setNetworkLocationType((String) null);
                            } else {
                                bDLocation2.setNetworkLocationType("dr2");
                            }
                            if (this.a.Z == null || !this.a.Z.c()) {
                                this.a.a(bDLocation2, 21);
                            } else if (((long) this.a.U) > 2) {
                                this.a.Z.a(bDLocation2);
                            } else {
                                this.a.a(bDLocation2, 29);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
            }
        }
    }
}
