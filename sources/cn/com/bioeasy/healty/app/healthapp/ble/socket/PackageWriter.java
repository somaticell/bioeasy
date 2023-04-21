package cn.com.bioeasy.healty.app.healthapp.ble.socket;

import com.ble.api.DataUtil;

public class PackageWriter {
    public static byte[] write(Byte cmd, String hexStr) {
        byte[] contents = null;
        if (hexStr != null) {
            try {
                contents = DataUtil.hexToByteArray(hexStr);
            } catch (Exception e) {
                return null;
            }
        }
        return write(cmd, contents);
    }

    public static byte[] write(Byte cmd, byte[] contents) {
        byte[] cmdBytes = null;
        if (cmd != null) {
            try {
                cmdBytes = new byte[]{cmd.byteValue()};
            } catch (Exception e) {
                return null;
            }
        }
        if (contents == null) {
            return cmdBytes;
        }
        return combile(cmdBytes, contents);
    }

    public static byte[] write(Byte cmd, Integer content) {
        byte[] contents = null;
        if (content != null) {
            try {
                contents = intToHexByteArray(content.intValue());
            } catch (Exception e) {
                return null;
            }
        }
        return write(cmd, contents);
    }

    public static byte[] write(Byte cmd, Byte content) {
        if (content == null) {
            return write(cmd, (byte[]) null);
        }
        try {
            return write(cmd, DataUtil.hexToByteArray(Integer.toHexString(content.byteValue())));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] combile(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[(data1.length + data2.length)];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

    public static byte[] intToHexByteArray(int value) {
        return DataUtil.hexToByteArray(Integer.toHexString(value));
    }

    public static int hexByteArrayToInt(byte[] bytes) {
        return Integer.parseInt(DataUtil.byteArrayToHex(bytes).replaceAll(" ", ""), 16);
    }
}
