package com.baidu.location;

import android.support.v7.widget.ActivityChooserView;
import android.text.TextUtils;
import com.baidu.platform.comapi.location.CoordinateType;

public final class LocationClientOption {
    public static final int GpsFirst = 1;
    public static final int GpsOnly = 3;
    public static final int LOC_SENSITIVITY_HIGHT = 1;
    public static final int LOC_SENSITIVITY_LOW = 3;
    public static final int LOC_SENSITIVITY_MIDDLE = 2;
    public static final int MIN_AUTO_NOTIFY_INTERVAL = 10000;
    public static final int MIN_SCAN_SPAN = 1000;
    public static final int NetWorkFirst = 2;
    protected LocationMode a;
    public String addrType = "detail";
    public float autoNotifyLocSensitivity = 0.5f;
    public int autoNotifyMaxInterval = 0;
    public int autoNotifyMinDistance = 0;
    public int autoNotifyMinTimeInterval = 0;
    public String coorType = CoordinateType.GCJ02;
    public boolean disableLocCache = true;
    public boolean enableSimulateGps = false;
    public boolean isIgnoreCacheException = false;
    public boolean isIgnoreKillProcess = true;
    public boolean isNeedAltitude = false;
    public boolean isNeedAptag = false;
    public boolean isNeedAptagd = false;
    public boolean isNeedPoiRegion = false;
    public boolean isNeedRegular = false;
    public boolean location_change_notify = false;
    public boolean mIsNeedDeviceDirect = false;
    public boolean openGps = false;
    public int priority = 1;
    public String prodName = "SDK6.0";
    public int scanSpan = 0;
    public String serviceName = "com.baidu.location.service_v2.9";
    public int timeOut = 12000;
    public int wifiCacheTimeOut = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

    public enum LocationMode {
        Hight_Accuracy,
        Battery_Saving,
        Device_Sensors
    }

    public LocationClientOption() {
    }

    public LocationClientOption(LocationClientOption locationClientOption) {
        this.coorType = locationClientOption.coorType;
        this.addrType = locationClientOption.addrType;
        this.openGps = locationClientOption.openGps;
        this.scanSpan = locationClientOption.scanSpan;
        this.timeOut = locationClientOption.timeOut;
        this.prodName = locationClientOption.prodName;
        this.priority = locationClientOption.priority;
        this.location_change_notify = locationClientOption.location_change_notify;
        this.serviceName = locationClientOption.serviceName;
        this.disableLocCache = locationClientOption.disableLocCache;
        this.isIgnoreCacheException = locationClientOption.isIgnoreCacheException;
        this.isIgnoreKillProcess = locationClientOption.isIgnoreKillProcess;
        this.enableSimulateGps = locationClientOption.enableSimulateGps;
        this.a = locationClientOption.a;
        this.isNeedAptag = locationClientOption.isNeedAptag;
        this.isNeedAptagd = locationClientOption.isNeedAptagd;
        this.isNeedPoiRegion = locationClientOption.isNeedPoiRegion;
        this.isNeedRegular = locationClientOption.isNeedRegular;
        this.mIsNeedDeviceDirect = locationClientOption.mIsNeedDeviceDirect;
        this.isNeedAltitude = locationClientOption.isNeedAltitude;
        this.autoNotifyMaxInterval = locationClientOption.autoNotifyMaxInterval;
        this.autoNotifyLocSensitivity = locationClientOption.autoNotifyLocSensitivity;
        this.autoNotifyMinTimeInterval = locationClientOption.autoNotifyMinTimeInterval;
        this.autoNotifyMinDistance = locationClientOption.autoNotifyMinDistance;
        this.wifiCacheTimeOut = locationClientOption.wifiCacheTimeOut;
    }

    public void SetIgnoreCacheException(boolean z) {
        this.isIgnoreCacheException = z;
    }

    /* access modifiers changed from: package-private */
    public int a() {
        return this.autoNotifyMaxInterval;
    }

    /* access modifiers changed from: package-private */
    public float b() {
        return this.autoNotifyLocSensitivity;
    }

    public void disableCache(boolean z) {
        this.disableLocCache = z;
    }

    public String getAddrType() {
        return this.addrType;
    }

    public int getAutoNotifyMinDistance() {
        return this.autoNotifyMinDistance;
    }

    public int getAutoNotifyMinTimeInterval() {
        return this.autoNotifyMinTimeInterval;
    }

    public String getCoorType() {
        return this.coorType;
    }

    public LocationMode getLocationMode() {
        return this.a;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getProdName() {
        return this.prodName;
    }

    public int getScanSpan() {
        return this.scanSpan;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public int getTimeOut() {
        return this.timeOut;
    }

    public boolean isDisableCache() {
        return this.disableLocCache;
    }

    public boolean isLocationNotify() {
        return this.location_change_notify;
    }

    public boolean isOpenGps() {
        return this.openGps;
    }

    public boolean optionEquals(LocationClientOption locationClientOption) {
        return this.coorType.equals(locationClientOption.coorType) && this.addrType.equals(locationClientOption.addrType) && this.openGps == locationClientOption.openGps && this.scanSpan == locationClientOption.scanSpan && this.timeOut == locationClientOption.timeOut && this.prodName.equals(locationClientOption.prodName) && this.location_change_notify == locationClientOption.location_change_notify && this.priority == locationClientOption.priority && this.disableLocCache == locationClientOption.disableLocCache && this.isIgnoreCacheException == locationClientOption.isIgnoreCacheException && this.isIgnoreKillProcess == locationClientOption.isIgnoreKillProcess && this.isNeedAptag == locationClientOption.isNeedAptag && this.isNeedAptagd == locationClientOption.isNeedAptagd && this.isNeedPoiRegion == locationClientOption.isNeedPoiRegion && this.isNeedRegular == locationClientOption.isNeedRegular && this.mIsNeedDeviceDirect == locationClientOption.mIsNeedDeviceDirect && this.autoNotifyMaxInterval == locationClientOption.autoNotifyMaxInterval && this.autoNotifyLocSensitivity == locationClientOption.autoNotifyLocSensitivity && this.autoNotifyMinTimeInterval == locationClientOption.autoNotifyMinTimeInterval && this.autoNotifyMinDistance == locationClientOption.autoNotifyMinDistance && this.wifiCacheTimeOut == locationClientOption.wifiCacheTimeOut && this.isNeedAltitude == locationClientOption.isNeedAltitude && this.a == locationClientOption.a;
    }

    @Deprecated
    public void setAddrType(String str) {
        if (!TextUtils.isEmpty(str)) {
            if ("all".equals(str)) {
                setIsNeedAddress(true);
            } else {
                setIsNeedAddress(false);
            }
        }
    }

    public void setCoorType(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.equals(CoordinateType.GCJ02) || lowerCase.equals(BDLocation.BDLOCATION_GCJ02_TO_BD09) || lowerCase.equals("bd09ll")) {
            this.coorType = lowerCase;
        }
    }

    public void setEnableSimulateGps(boolean z) {
        this.enableSimulateGps = z;
    }

    public void setIgnoreKillProcess(boolean z) {
        this.isIgnoreKillProcess = z;
    }

    public void setIsNeedAddress(boolean z) {
        if (z) {
            this.addrType = "all";
        } else {
            this.addrType = "noaddr";
        }
    }

    public void setIsNeedAltitude(boolean z) {
        this.isNeedAltitude = z;
    }

    public void setIsNeedLocationDescribe(boolean z) {
        this.isNeedAptag = z;
    }

    public void setIsNeedLocationPoiList(boolean z) {
        this.isNeedAptagd = z;
    }

    public void setLocationMode(LocationMode locationMode) {
        switch (locationMode) {
            case Hight_Accuracy:
                this.openGps = true;
                this.priority = 1;
                break;
            case Battery_Saving:
                this.openGps = false;
                this.priority = 2;
                break;
            case Device_Sensors:
                this.priority = 3;
                this.openGps = true;
                break;
            default:
                throw new IllegalArgumentException("Illegal this mode : " + locationMode);
        }
        this.a = locationMode;
    }

    public void setLocationNotify(boolean z) {
        this.location_change_notify = z;
    }

    public void setNeedDeviceDirect(boolean z) {
        this.mIsNeedDeviceDirect = z;
    }

    public void setOpenAutoNotifyMode() {
        setOpenAutoNotifyMode(0, 0, 1);
    }

    public void setOpenAutoNotifyMode(int i, int i2, int i3) {
        int i4 = 180000;
        if (i > 180000) {
            i4 = i + 1000;
        }
        if (i4 < 10000) {
            throw new IllegalArgumentException("Illegal this maxLocInterval : " + i4 + " , maxLocInterval must >= " + 10000);
        }
        switch (i3) {
            case 1:
                this.autoNotifyLocSensitivity = 0.5f;
                break;
            case 2:
                this.autoNotifyLocSensitivity = 0.3f;
                break;
            case 3:
                this.autoNotifyLocSensitivity = 0.1f;
                break;
            default:
                throw new IllegalArgumentException("Illegal this locSensitivity : " + i3);
        }
        this.autoNotifyMaxInterval = i4;
        this.autoNotifyMinTimeInterval = i;
        this.autoNotifyMinDistance = i2;
    }

    public void setOpenGps(boolean z) {
        this.openGps = z;
    }

    @Deprecated
    public void setPriority(int i) {
        if (i == 1 || i == 2) {
            this.priority = i;
        }
    }

    public void setProdName(String str) {
        if (str.length() > 64) {
            str = str.substring(0, 64);
        }
        this.prodName = str;
    }

    public void setScanSpan(int i) {
        this.scanSpan = i;
    }

    @Deprecated
    public void setSema(boolean z, boolean z2, boolean z3) {
        this.isNeedAptag = z;
        this.isNeedPoiRegion = z2;
        this.isNeedRegular = z3;
    }

    public void setServiceName(String str) {
        this.serviceName = str;
    }

    public void setTimeOut(int i) {
        this.timeOut = i;
    }

    public void setWifiCacheTimeOut(int i) {
        if (i >= 10000) {
            this.wifiCacheTimeOut = i;
        }
    }
}
