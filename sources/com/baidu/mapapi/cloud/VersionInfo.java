package com.baidu.mapapi.cloud;

public class VersionInfo {
    public static final String KIT_NAME = "BaiduMapSDK_cloud_v4_5_2";
    public static final String VERSION_DESC = "baidumapapi_cloud";

    public static String getApiVersion() {
        return com.baidu.mapapi.VersionInfo.VERSION_INFO;
    }

    public static String getKitName() {
        return KIT_NAME;
    }

    public static String getVersionDesc() {
        return VERSION_DESC;
    }
}
