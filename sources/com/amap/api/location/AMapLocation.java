package com.amap.api.location;

import android.location.Location;

public class AMapLocation extends Location {
    public static final int ERROR_CODE_FAILURE_AUTH = 7;
    public static final int ERROR_CODE_FAILURE_CELL = 11;
    public static final int ERROR_CODE_FAILURE_CONNECTION = 4;
    public static final int ERROR_CODE_FAILURE_INIT = 9;
    public static final int ERROR_CODE_FAILURE_LOCATION = 6;
    public static final int ERROR_CODE_FAILURE_LOCATION_PARAMETER = 3;
    public static final int ERROR_CODE_FAILURE_LOCATION_PERMISSION = 12;
    public static final int ERROR_CODE_FAILURE_PARSER = 5;
    public static final int ERROR_CODE_FAILURE_WIFI_INFO = 2;
    public static final int ERROR_CODE_INVALID_PARAMETER = 1;
    public static final int ERROR_CODE_SERVICE_FAIL = 10;
    public static final int ERROR_CODE_UNKNOWN = 8;
    public static final int LOCATION_SUCCESS = 0;
    public static final int LOCATION_TYPE_AMAP = 7;
    public static final int LOCATION_TYPE_CELL = 6;
    public static final int LOCATION_TYPE_FIX_CACHE = 4;
    public static final int LOCATION_TYPE_GPS = 1;
    public static final int LOCATION_TYPE_OFFLINE = 8;
    public static final int LOCATION_TYPE_SAME_REQ = 2;
    public static final int LOCATION_TYPE_WIFI = 5;
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    private boolean l = true;
    private int m = 0;
    private String n = "success";
    private String o = "";
    private int p = 0;
    private double q = 0.0d;
    private double r = 0.0d;
    private int s = 0;

    public AMapLocation(String str) {
        super(str);
    }

    public AMapLocation(Location location) {
        super(location);
        this.q = location.getLatitude();
        this.r = location.getLongitude();
    }

    public int getLocationType() {
        return this.p;
    }

    public void setLocationType(int i2) {
        this.p = i2;
    }

    public String getLocationDetail() {
        return this.o;
    }

    public void setLocationDetail(String str) {
        this.o = str;
    }

    public int getErrorCode() {
        return this.m;
    }

    public void setErrorCode(int i2) {
        if (this.m == 0) {
            switch (i2) {
                case 0:
                    this.n = "success";
                    break;
                case 1:
                    this.n = "重要参数为空";
                    break;
                case 2:
                    this.n = "WIFI信息不足";
                    break;
                case 3:
                    this.n = "请求参数获取出现异常";
                    break;
                case 4:
                    this.n = "网络连接异常";
                    break;
                case 5:
                    this.n = "解析XML出错";
                    break;
                case 6:
                    this.n = "定位结果错误";
                    break;
                case 7:
                    this.n = "KEY错误";
                    break;
                case 8:
                    this.n = "其他错误";
                    break;
                case 9:
                    this.n = "初始化异常";
                    break;
                case 10:
                    this.n = "定位服务启动失败";
                    break;
                case 11:
                    this.n = "错误的基站信息，请检查是否插入SIM卡";
                    break;
                case 12:
                    this.n = "缺少定位权限";
                    break;
            }
            this.m = i2;
        }
    }

    public String getErrorInfo() {
        return this.n;
    }

    public void setErrorInfo(String str) {
        this.n = str;
    }

    public String getCountry() {
        return this.h;
    }

    public void setCountry(String str) {
        this.h = str;
    }

    public String getRoad() {
        return this.i;
    }

    public void setRoad(String str) {
        this.i = str;
    }

    public String getAddress() {
        return this.f;
    }

    public void setAddress(String str) {
        this.f = str;
    }

    public String getProvince() {
        return this.a;
    }

    public void setProvince(String str) {
        this.a = str;
    }

    public String getCity() {
        return this.b;
    }

    public void setCity(String str) {
        this.b = str;
    }

    public String getDistrict() {
        return this.c;
    }

    public void setDistrict(String str) {
        this.c = str;
    }

    public String getCityCode() {
        return this.d;
    }

    public void setCityCode(String str) {
        this.d = str;
    }

    public String getAdCode() {
        return this.e;
    }

    public void setAdCode(String str) {
        this.e = str;
    }

    public String getPoiName() {
        return this.g;
    }

    public void setPoiName(String str) {
        this.g = str;
    }

    public double getLatitude() {
        return this.q;
    }

    public void setLatitude(double d2) {
        this.q = d2;
    }

    public double getLongitude() {
        return this.r;
    }

    public void setLongitude(double d2) {
        this.r = d2;
    }

    public int getSatellites() {
        return this.s;
    }

    public void setSatellites(int i2) {
        this.s = i2;
    }

    public String getStreet() {
        return this.j;
    }

    public void setStreet(String str) {
        this.j = str;
    }

    public String getStreetNum() {
        return this.k;
    }

    public void setNumber(String str) {
        this.k = str;
    }

    public void setOffset(boolean z) {
        this.l = z;
    }

    public boolean isOffset() {
        return this.l;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("latitude=" + this.q);
        stringBuffer.append("longitude=" + this.r);
        stringBuffer.append("province=" + this.a + "#");
        stringBuffer.append("city=" + this.b + "#");
        stringBuffer.append("district=" + this.c + "#");
        stringBuffer.append("cityCode=" + this.d + "#");
        stringBuffer.append("adCode=" + this.e + "#");
        stringBuffer.append("address=" + this.f + "#");
        stringBuffer.append("country=" + this.h + "#");
        stringBuffer.append("road=" + this.i + "#");
        stringBuffer.append("poiName=" + this.g + "#");
        stringBuffer.append("street=" + this.j + "#");
        stringBuffer.append("streetNum=" + this.k + "#");
        stringBuffer.append("errorCode=" + this.m + "#");
        stringBuffer.append("errorInfo=" + this.n + "#");
        stringBuffer.append("locationDetail=" + this.o + "#");
        stringBuffer.append("locationType=" + this.p);
        return stringBuffer.toString();
    }

    public String toStr() {
        return toStr(1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[Catch:{ Exception -> 0x00e2 }, ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toStr(int r7) {
        /*
            r6 = this;
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x00e2 }
            r1.<init>()     // Catch:{ Exception -> 0x00e2 }
            switch(r7) {
                case 1: goto L_0x000c;
                case 2: goto L_0x00ab;
                case 3: goto L_0x00b4;
                default: goto L_0x0009;
            }     // Catch:{ Exception -> 0x00e2 }
        L_0x0009:
            if (r1 != 0) goto L_0x00e6
        L_0x000b:
            return r0
        L_0x000c:
            java.lang.String r2 = "country"
            java.lang.String r3 = r6.h     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "province"
            java.lang.String r3 = r6.a     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "city"
            java.lang.String r3 = r6.b     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "cityCode"
            java.lang.String r3 = r6.d     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "district"
            java.lang.String r3 = r6.c     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "adCode"
            java.lang.String r3 = r6.e     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "address"
            java.lang.String r3 = r6.f     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "road"
            java.lang.String r3 = r6.i     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "street"
            java.lang.String r3 = r6.j     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "number"
            java.lang.String r3 = r6.k     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "poiName"
            java.lang.String r3 = r6.g     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "errorCode"
            int r3 = r6.m     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "errorInfo"
            java.lang.String r3 = r6.n     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "locationDetail"
            java.lang.String r3 = r6.o     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "altitude"
            double r4 = r6.getAltitude()     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "bearing"
            float r3 = r6.getBearing()     // Catch:{ Exception -> 0x00e2 }
            double r4 = (double) r3     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "speed"
            float r3 = r6.getSpeed()     // Catch:{ Exception -> 0x00e2 }
            double r4 = (double) r3     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "satellites"
            int r3 = r6.s     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            android.os.Bundle r2 = r6.getExtras()     // Catch:{ Throwable -> 0x00ec }
            if (r2 == 0) goto L_0x00ab
            java.lang.String r3 = "desc"
            boolean r3 = r2.containsKey(r3)     // Catch:{ Throwable -> 0x00ec }
            if (r3 == 0) goto L_0x00ab
            java.lang.String r3 = "desc"
            java.lang.String r4 = "desc"
            java.lang.String r2 = r2.getString(r4)     // Catch:{ Throwable -> 0x00ec }
            r1.put(r3, r2)     // Catch:{ Throwable -> 0x00ec }
        L_0x00ab:
            java.lang.String r2 = "time"
            long r4 = r6.getTime()     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x00e2 }
        L_0x00b4:
            java.lang.String r2 = "locationType"
            int r3 = r6.p     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "accuracy"
            float r3 = r6.getAccuracy()     // Catch:{ Exception -> 0x00e2 }
            double r4 = (double) r3     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "latitude"
            double r4 = r6.getLatitude()     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "longitude"
            double r4 = r6.getLongitude()     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r4)     // Catch:{ Exception -> 0x00e2 }
            java.lang.String r2 = "provider"
            java.lang.String r3 = r6.getProvider()     // Catch:{ Exception -> 0x00e2 }
            r1.put(r2, r3)     // Catch:{ Exception -> 0x00e2 }
            goto L_0x0009
        L_0x00e2:
            r1 = move-exception
            r1 = r0
            goto L_0x0009
        L_0x00e6:
            java.lang.String r0 = r1.toString()
            goto L_0x000b
        L_0x00ec:
            r2 = move-exception
            goto L_0x00ab
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.location.AMapLocation.toStr(int):java.lang.String");
    }

    public float getAccuracy() {
        return super.getAccuracy();
    }

    public float getBearing() {
        return super.getBearing();
    }

    public double getAltitude() {
        return super.getAltitude();
    }

    public float getSpeed() {
        return super.getSpeed();
    }

    public String getProvider() {
        return super.getProvider();
    }
}
