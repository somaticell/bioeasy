package com.baidu.platform.comapi.map;

public class VersionInfo {
    public static final String KIT_NAME = "BaiduMapSDK_map_v";
    public static final String VERSION_DESC = "baidumapapi_map";

    public static String getApiVersion() {
        return com.baidu.mapapi.VersionInfo.VERSION_INFO;
    }

    public static String getKitName() {
        return "BaiduMapSDK_map_v4_5_2";
    }

    public static String getVersionDesc() {
        return VERSION_DESC;
    }
}
