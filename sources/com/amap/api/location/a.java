package com.amap.api.location;

import com.amap.api.location.CoordinateConverter;

/* compiled from: CoordinateConverter */
/* synthetic */ class a {
    static final /* synthetic */ int[] a = new int[CoordinateConverter.CoordType.values().length];

    static {
        try {
            a[CoordinateConverter.CoordType.BAIDU.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[CoordinateConverter.CoordType.MAPBAR.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[CoordinateConverter.CoordType.MAPABC.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            a[CoordinateConverter.CoordType.SOSOMAP.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            a[CoordinateConverter.CoordType.ALIYUN.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
        try {
            a[CoordinateConverter.CoordType.GOOGLE.ordinal()] = 6;
        } catch (NoSuchFieldError e6) {
        }
        try {
            a[CoordinateConverter.CoordType.GPS.ordinal()] = 7;
        } catch (NoSuchFieldError e7) {
        }
    }
}
