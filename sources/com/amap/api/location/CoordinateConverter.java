package com.amap.api.location;

import android.content.Context;
import com.loc.e;
import com.loc.j;

public class CoordinateConverter {
    DPoint a = null;
    private Context b;
    private CoordType c = null;
    private DPoint d = null;

    public enum CoordType {
        BAIDU,
        MAPBAR,
        MAPABC,
        SOSOMAP,
        ALIYUN,
        GOOGLE,
        GPS
    }

    public CoordinateConverter(Context context) {
        this.b = context;
    }

    public synchronized CoordinateConverter from(CoordType coordType) {
        this.c = coordType;
        return this;
    }

    public synchronized CoordinateConverter coord(DPoint dPoint) throws Exception {
        if (dPoint == null) {
            throw new IllegalArgumentException("传入经纬度对象为空");
        } else if (dPoint.getLongitude() > 180.0d || dPoint.getLongitude() < -180.0d) {
            throw new IllegalArgumentException("请传入合理经度");
        } else if (dPoint.getLatitude() > 90.0d || dPoint.getLatitude() < -90.0d) {
            throw new IllegalArgumentException("请传入合理纬度");
        } else {
            this.d = dPoint;
        }
        return this;
    }

    public synchronized DPoint convert() throws Exception {
        if (this.c == null) {
            throw new IllegalArgumentException("转换坐标类型不能为空");
        } else if (this.d == null) {
            throw new IllegalArgumentException("转换坐标源不能为空");
        } else if (this.d.getLongitude() > 180.0d || this.d.getLongitude() < -180.0d) {
            throw new IllegalArgumentException("请传入合理经度");
        } else if (this.d.getLatitude() > 90.0d || this.d.getLatitude() < -90.0d) {
            throw new IllegalArgumentException("请传入合理纬度");
        } else {
            switch (a.a[this.c.ordinal()]) {
                case 1:
                    this.a = j.a(this.d);
                    break;
                case 2:
                    this.a = j.b(this.b, this.d);
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    this.a = this.d;
                    break;
                case 7:
                    this.a = j.a(this.b, this.d);
                    break;
            }
        }
        return this.a;
    }

    public boolean isAMapDataAvailable(double d2, double d3) {
        return e.a(d2, d3);
    }
}
