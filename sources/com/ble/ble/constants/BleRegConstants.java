package com.ble.ble.constants;

import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;

public class BleRegConstants {
    public static final int BAUDRATE_115200 = 4;
    public static final int BAUDRATE_19200 = 1;
    public static final int BAUDRATE_38400 = 2;
    public static final int BAUDRATE_57600 = 3;
    public static final int BAUDRATE_9600 = 0;
    public static final byte[][] REG = {new byte[]{6}, new byte[]{7}, new byte[]{13}, new byte[]{14}, new byte[]{16}, new byte[]{17}, new byte[]{18}, new byte[]{29}, new byte[]{30}, new byte[]{31}, new byte[]{32}, new byte[]{BLEServiceApi.LED_CMD}, new byte[]{BLEServiceApi.FIRMWARE_CMD}, new byte[]{BLEServiceApi.POWER_CMD}, new byte[]{36}, new byte[]{37}, new byte[]{BLEServiceApi.POWER_STATUS_CMD}, new byte[]{39}, new byte[]{40}, new byte[]{43}, new byte[]{44}, new byte[]{45}, new byte[]{46}, new byte[]{47}, new byte[]{48}, new byte[]{49}, new byte[]{50}, new byte[]{51}, new byte[]{52}, new byte[]{53}, new byte[]{54}, new byte[]{55}, new byte[]{56}, new byte[]{57}, new byte[]{61}, new byte[]{65}, new byte[]{66}, new byte[]{67}, new byte[]{15}, new byte[]{69}, new byte[]{70}, new byte[]{71}, new byte[]{72}};
    public static final int REG_ADV_INTERVAL = 16;
    public static final int REG_ADV_MFR_SPC = 41;
    public static final int REG_BATTERY_LEVEL = 35;
    public static final int REG_BLE_ADDRESS = 5;
    public static final int REG_BLE_NAME = 3;
    public static final int REG_CHANNEL_RATES = 2;
    public static final int REG_CONN_INTERVAL = 17;
    public static final int REG_CONN_TIMEOUT = 37;
    public static final int REG_PARA_UPDATE_TIMEOUT = 39;
    public static final int REG_PASSWORD = 38;
    public static final int REG_RX_GAIN = 15;
    public static final int REG_SLAVE_LATENCY = 36;
    public static final int REG_TRANS_SPEED = 18;
    public static final int REG_TX_POWER = 14;
    public static final int REG_UTC_TIME = 19;
    public static final int REG_VERSION = 34;
    public static final int RX_HIGH = 1;
    public static final int RX_LOW = 0;
    public static final int TRANS_SPEED_HIGH = 1;
    public static final int TRANS_SPEED_LOW = 0;
    public static final int TX_0 = 2;
    public static final int TX_NEGATIVE_23 = 0;
    public static final int TX_NEGATIVE_6 = 1;
}
