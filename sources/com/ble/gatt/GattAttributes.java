package com.ble.gatt;

import java.util.HashMap;
import java.util.UUID;

public class GattAttributes {
    public static final UUID Characteristic_User_Description = UUID.fromString("00002901-0000-1000-8000-00805f9b34fb");
    public static final UUID Client_Characteristic_Configuration = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID Device_Information = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");
    public static final UUID Device_Name = UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb");
    public static final UUID Firmware_Revision = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");
    public static final UUID Generic_Access = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");
    public static final UUID Generic_Attribute = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
    public static final UUID Hardware_Revision = UUID.fromString("00002a27-0000-1000-8000-00805f9b34fb");
    public static final UUID Manufacturer_Name = UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb");
    public static final UUID Model_Number = UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb");
    public static final UUID Software_Revision = UUID.fromString("00002a28-0000-1000-8000-00805f9b34fb");
    public static final UUID TI_OAD_Image_Block = UUID.fromString("f000ffc2-0451-4000-b000-000000000000");
    public static final UUID TI_OAD_Image_Count = UUID.fromString("f000ffc3-0451-4000-b000-000000000000");
    public static final UUID TI_OAD_Image_Identify = UUID.fromString("f000ffc1-0451-4000-b000-000000000000");
    public static final UUID TI_OAD_Image_Status = UUID.fromString("f000ffc4-0451-4000-b000-000000000000");
    public static final UUID TI_OAD_Service = UUID.fromString("f000ffc0-0451-4000-b000-000000000000");
    public static final UUID TI_Reset = UUID.fromString("f000ffd1-0451-4000-b000-000000000000");
    public static final UUID TI_Reset_Service = UUID.fromString("f000ffd0-0451-4000-b000-000000000000");
    private static HashMap<String, String> a = new HashMap<>();

    static {
        a.put("00001800-0000-1000-8000-00805f9b34fb", "Generic Access");
        a.put("00001801-0000-1000-8000-00805f9b34fb", "Generic Attribute");
        a.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information");
        a.put("00002a00-0000-1000-8000-00805f9b34fb", "Device Name");
        a.put("00002a01-0000-1000-8000-00805f9b34fb", "Appearance");
        a.put("00002a02-0000-1000-8000-00805f9b34fb", "Peripheral Privacy Flag");
        a.put("00002a03-0000-1000-8000-00805f9b34fb", "Reconnection Address");
        a.put("00002a04-0000-1000-8000-00805f9b34fb", "Peripheral Preferred Connection Parameters");
        a.put("00002a05-0000-1000-8000-00805f9b34fb", "Service Changed");
        a.put("00002a23-0000-1000-8000-00805f9b34fb", "System ID");
        a.put("00002a24-0000-1000-8000-00805f9b34fb", "Model Number String");
        a.put("00002a25-0000-1000-8000-00805f9b34fb", "Serial Number String");
        a.put("00002a26-0000-1000-8000-00805f9b34fb", "Firmware Revision String");
        a.put("00002a27-0000-1000-8000-00805f9b34fb", "Hardware Revision");
        a.put("00002a28-0000-1000-8000-00805f9b34fb", "Software Revision");
        a.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
        a.put("00002a2a-0000-1000-8000-00805f9b34fb", "IEEE 11073-20601 Regulatory Certification Data List");
        a.put("00002a50-0000-1000-8000-00805f9b34fb", "PnP ID");
        a.put("00002901-0000-1000-8000-00805f9b34fb", "Characteristic User Description");
        a.put("00002902-0000-1000-8000-00805f9b34fb", "Client Characteristic Configuration");
        a.put("f000ffc0-0451-4000-b000-000000000000", "TI OAD Service");
        a.put("f000ffc1-0451-4000-b000-000000000000", "OAD Image Identify");
        a.put("f000ffc2-0451-4000-b000-000000000000", "OAD Image Block");
        a.put("f000ffc3-0451-4000-b000-000000000000", "OAD Image Count");
        a.put("f000ffc4-0451-4000-b000-000000000000", "OAD Image Status");
        a.put("f000ffd0-0451-4000-b000-000000000000", "TI Reset Service");
        a.put("f000ffd1-0451-4000-b000-000000000000", "Reset");
    }

    public static String lookup(String str, String str2) {
        String str3 = a.get(str);
        return str3 == null ? str2 : str3;
    }
}
