package cn.com.bioeasy.healty.app.healthapp.ble;

public interface BLEServiceApi {
    public static final byte CONNECTED_STATUS = -1;
    public static final byte FIRMWARE_CMD = 34;
    public static final byte FIRMWARE_UPLOAD = -99;
    public static final byte LED_CMD = 33;
    public static final String NOT_CONNECTED = "-99";
    public static final byte POWER_CMD = 35;
    public static final byte POWER_STATUS_CMD = 38;
    public static final String RESULT_NG = "00";
    public static final String RESULT_OK = "01";
    public static final byte TEST_CMD = 1;
    public static final byte TEST_WITH_ALL_DATA = 2;

    void sendDebugTestDataCmd(Byte b);

    void sendLEDBrightnessCmd(byte b);

    void sendPowerStatusCmd();

    void sendPowerTimeCmd(byte b);

    void sendTestCmd(Byte b);

    void updateFirmwareCmd(int i);

    void uploadFirmwareData(byte[] bArr);
}
