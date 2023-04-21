package com.lvrenyang.io;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.lvrenyang.io.base.IO;
import java.util.ArrayList;
import java.util.List;

public class Setting {
    private static final String TAG = "Setting";
    private IO IO = new IO();

    public interface OnProgressCallBack {
        void OnProgress(double d, double d2);
    }

    public void Set(IO io) {
        if (io != null) {
            this.IO = io;
        }
    }

    public IO GetIO() {
        return this.IO;
    }

    public byte[] WrapUSSICmd(byte p0, byte p1, byte[] src_cmd) {
        int src_cmd_length = src_cmd.length;
        int datalen = src_cmd_length + 1;
        int totallen = datalen + 5;
        byte[] dst_cmd = new byte[totallen];
        dst_cmd[0] = 31;
        dst_cmd[1] = 40;
        dst_cmd[2] = 15;
        dst_cmd[3] = (byte) ((int) (((long) datalen) & 255));
        dst_cmd[4] = (byte) ((int) ((((long) datalen) & 65280) >> 8));
        System.arraycopy(src_cmd, 0, dst_cmd, 5, src_cmd_length);
        dst_cmd[totallen - 1] = 0;
        for (int i = 5; i < totallen - 1; i++) {
            int i2 = totallen - 1;
            dst_cmd[i2] = (byte) (dst_cmd[i2] ^ dst_cmd[i]);
        }
        return dst_cmd;
    }

    public boolean Setting_Ethernet_IPAddress(String ipAddress) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] ipbytes = IsIPValid(ipAddress);
            if (ipbytes == null) {
                throw new Exception("Invalid ip address: " + ipAddress);
            }
            byte[] cmd = {31, 105, 0, 0, 0, 0};
            System.arraycopy(ipbytes, 0, cmd, 2, 4);
            byte[] cmd2 = WrapUSSICmd((byte) 31, (byte) 115, cmd);
            if (this.IO.Write(cmd2, 0, cmd2.length) == cmd2.length) {
                result = true;
            } else {
                result = false;
            }
            return result;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Ethernet_MACAddress(String macAddress) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] macbytes = IsMACValid(macAddress);
            if (macbytes == null) {
                throw new Exception("Invalid mac address: " + macAddress);
            }
            byte[] cmd = {31, 109, 0, 0, 0, 0, 0, 0};
            System.arraycopy(macbytes, 0, cmd, 2, 6);
            byte[] cmd2 = WrapUSSICmd((byte) 31, (byte) 115, cmd);
            if (this.IO.Write(cmd2, 0, cmd2.length) == cmd2.length) {
                result = true;
            } else {
                result = false;
            }
            return result;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Ethernet_Speed(int index) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            byte[][] cmdSpeeds = {new byte[]{31, 112, 0, 0, -124}, new byte[]{31, 112, 1, 1, 1}, new byte[]{31, 112, 1, 1, 0}, new byte[]{31, 112, 1, 0, 1}, new byte[]{31, 112, 1, 0, 0}};
            if (index < 0 || index > 4) {
                throw new Exception("Invalid Parameter index:" + index);
            }
            byte[] cmd = WrapUSSICmd((byte) 31, (byte) 115, cmdSpeeds[index]);
            return this.IO.Write(cmd, 0, cmd.length) == cmd.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Basic_Common(int language, int combaudrate, int comparity, int fonttype, int density, int charsperline, boolean autoreprintlastreceipt, boolean beeper, boolean drawer, boolean cutter) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            byte[] cmd1 = {31, 115, 2, 0, 0, 0, 0, 0, -1, 0, 0};
            cmd1[2] = (byte) combaudrate;
            cmd1[3] = (byte) (cutter ? 0 : 1);
            cmd1[4] = (byte) (beeper ? 0 : 1);
            cmd1[5] = (byte) (drawer ? 0 : 1);
            cmd1[6] = (byte) charsperline;
            cmd1[7] = (byte) density;
            cmd1[8] = (byte) language;
            cmd1[9] = (byte) comparity;
            cmd1[10] = (byte) fonttype;
            byte[] cmd2 = {31, 114, 0};
            cmd2[2] = (byte) (autoreprintlastreceipt ? 1 : 0);
            byte[] cmd = ByteUtils.byteArraysToBytes(new byte[][]{WrapUSSICmd((byte) 31, (byte) 115, cmd1), WrapUSSICmd((byte) 31, (byte) 115, cmd2)});
            return this.IO.Write(cmd, 0, cmd.length) == cmd.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Wifi_SsidAndPassword(String ssid, String password) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            byte[] ssidBytes = ssid.getBytes();
            byte[] ssidHead = {31, 119, (byte) ssidBytes.length};
            byte[] passwordBytes = password.getBytes();
            byte[] passwordHead = {0, 0, 0};
            if (passwordBytes.length > 0) {
                passwordHead[0] = 3;
                passwordHead[1] = 1;
                passwordHead[2] = (byte) passwordBytes.length;
            }
            byte[] cmd = new byte[(ssidHead.length + ssidBytes.length + passwordHead.length + passwordBytes.length)];
            System.arraycopy(ssidHead, 0, cmd, 0, ssidHead.length);
            int offset = 0 + ssidHead.length;
            System.arraycopy(ssidBytes, 0, cmd, offset, ssidBytes.length);
            int offset2 = offset + ssidBytes.length;
            System.arraycopy(passwordHead, 0, cmd, offset2, passwordHead.length);
            int offset3 = offset2 + passwordHead.length;
            System.arraycopy(passwordBytes, 0, cmd, offset3, passwordBytes.length);
            int offset4 = offset3 + passwordBytes.length;
            byte[] cmd2 = WrapUSSICmd((byte) 31, (byte) 115, cmd);
            return this.IO.Write(cmd2, 0, cmd2.length) == cmd2.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Wifi_IPAddress(String ipAddress) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] ipbytes = IsIPValid(ipAddress);
            if (ipbytes == null) {
                throw new Exception("Invalid ip address: " + ipAddress);
            }
            byte[] cmd = {31, 87, 73, 0, 0, 0, 0};
            System.arraycopy(ipbytes, 0, cmd, 3, 4);
            byte[] cmd2 = WrapUSSICmd((byte) 31, (byte) 115, cmd);
            if (this.IO.Write(cmd2, 0, cmd2.length) == cmd2.length) {
                result = true;
            } else {
                result = false;
            }
            return result;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Bluetooth_NameAndPassword(String name, String password) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] cmdHead = {31, 66};
            byte[] nameBytes = name.getBytes();
            byte[] passwordBytes = password.getBytes();
            byte[] cmd = new byte[(nameBytes.length + 2 + 1 + passwordBytes.length + 1)];
            System.arraycopy(cmdHead, 0, cmd, 0, cmdHead.length);
            int offset = 0 + cmdHead.length;
            System.arraycopy(nameBytes, 0, cmd, offset, nameBytes.length);
            int offset2 = offset + nameBytes.length + 1;
            System.arraycopy(passwordBytes, 0, cmd, offset2, passwordBytes.length);
            int offset3 = offset2 + passwordBytes.length + 1;
            byte[] cmd2 = WrapUSSICmd((byte) 31, (byte) 115, cmd);
            if (this.IO.Write(cmd2, 0, cmd2.length) == cmd2.length) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return result;
    }

    public boolean Setting_BlackMark_Enable() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] cmd = {31, 27, 31, Byte.MIN_VALUE, 4, 5, 6, 68};
            if (this.IO.Write(cmd, 0, cmd.length) == cmd.length) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return result;
    }

    public boolean Setting_BlackMark_Disable() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] cmd = {31, 27, 31, Byte.MIN_VALUE, 4, 5, 6, 102};
            if (this.IO.Write(cmd, 0, cmd.length) == cmd.length) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return result;
    }

    public boolean Setting_BlackMark_Set(int length, int width, int feedcut, int feedprint) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            byte[] cmdDistance = {31, 27, 31, -127, 4, 5, 6, 9, 96};
            int n = length * 8;
            cmdDistance[8] = (byte) (n >> 8);
            cmdDistance[7] = (byte) n;
            byte[] cmdWidth = {31, 27, 31, -126, 4, 5, 6, 0, 80};
            int n2 = width * 8;
            cmdWidth[8] = (byte) (n2 >> 8);
            cmdWidth[7] = (byte) n2;
            byte[] cmdFeedCut = {29, 40, 70, 4, 0, 2, 0, 0, 0};
            int n3 = feedcut * 8;
            cmdFeedCut[8] = (byte) (n3 >> 8);
            cmdFeedCut[7] = (byte) n3;
            byte[] cmdFeedPrint = {29, 40, 70, 4, 0, 1, 0, 0, 0};
            int n4 = feedprint * 8;
            cmdFeedPrint[8] = (byte) (n4 >> 8);
            cmdFeedPrint[7] = (byte) n4;
            byte[] cmd = ByteUtils.byteArraysToBytes(new byte[][]{cmdDistance, cmdWidth, cmdFeedCut, cmdFeedPrint});
            return this.IO.Write(cmd, 0, cmd.length) == cmd.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Firmware_Update(byte[] firmware, OnProgressCallBack onProgress) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            if ((((((long) firmware[7]) & 255) << 24) | ((((long) firmware[6]) & 255) << 16) | ((((long) firmware[5]) & 255) << 8) | (((long) firmware[4]) & 255)) != ((long) firmware.length)) {
                throw new Exception("File Check Length Error");
            }
            byte[] cmd = new byte[(firmware.length + 6)];
            cmd[0] = 31;
            cmd[1] = 117;
            cmd[2] = firmware[7];
            cmd[3] = firmware[6];
            cmd[4] = firmware[5];
            cmd[5] = firmware[4];
            System.arraycopy(firmware, 0, cmd, 6, firmware.length);
            int nBytesWritten = 0;
            onProgress.OnProgress((double) 0, (double) cmd.length);
            while (nBytesWritten < cmd.length) {
                int nSended = this.IO.Write(cmd, nBytesWritten, Math.min(256, cmd.length - nBytesWritten));
                if (nSended < 0) {
                    throw new Exception("Write Failed");
                }
                nBytesWritten += nSended;
                onProgress.OnProgress((double) nBytesWritten, (double) cmd.length);
                Thread.sleep(10);
            }
            return nBytesWritten == cmd.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Logo_Download(List<String> files, OnProgressCallBack onProgress) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            List<byte[]> datas = new ArrayList<>();
            byte[] head = {28, 113, 0};
            datas.add(head);
            for (int i = 0; i < files.size(); i++) {
                Bitmap mBitmap = BitmapFactory.decodeFile(files.get(i));
                int dstw = mBitmap.getWidth();
                int dsth = mBitmap.getHeight();
                int[] dst = new int[(dstw * dsth)];
                mBitmap.getPixels(dst, 0, dstw, 0, 0, dstw, dsth);
                boolean[] dithered = new boolean[(dstw * dsth)];
                ImageProcessing.format_K_threshold(dstw, dsth, ImageProcessing.GrayImage(dst), dithered);
                datas.add(ImageProcessing.Image1ToNVData(dstw, dsth, dithered));
                head[2] = (byte) (head[2] + 1);
            }
            byte[] cmd = ByteUtils.ByteArrayListToBytes(datas);
            int nBytesWritten = 0;
            onProgress.OnProgress((double) 0, (double) cmd.length);
            while (nBytesWritten < cmd.length) {
                int nSended = this.IO.Write(cmd, nBytesWritten, Math.min(256, cmd.length - nBytesWritten));
                if (nSended < 0) {
                    throw new Exception("Write Failed");
                }
                nBytesWritten += nSended;
                onProgress.OnProgress((double) nBytesWritten, (double) cmd.length);
                Thread.sleep(10);
            }
            return nBytesWritten == cmd.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Logo_Print() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] cmd = new byte[1020];
            int idx = 0;
            for (int i = 1; i <= 255; i++) {
                int idx2 = idx + 1;
                cmd[idx] = 28;
                int idx3 = idx2 + 1;
                cmd[idx2] = 112;
                int idx4 = idx3 + 1;
                cmd[idx3] = (byte) i;
                idx = idx4 + 1;
                cmd[idx4] = 0;
            }
            if (this.IO.Write(cmd, 0, cmd.length) == cmd.length) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return result;
    }

    public boolean Setting_Hardware_Set(boolean bSetBluetooth, int nSetBluetoothValue, boolean bSetCutter, int nSetCutterValue, boolean bSetHeatDot, int nSetHeatDotValue, boolean bSetHeatOnTime, int nSetHeatOnTimeValue, boolean bSetHeatOffTime, int nSetHeatOffTimeValue, boolean bSetSensor, int nSetSensorValue, boolean bSetUsb, int nSetUsbValue, boolean bSetCutBeep, int nBeepCount, int nBeepOn, int nBeepOff, boolean bSetSpeed, int nSetSpeedValue) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            byte[] cmd = new byte[26];
            cmd[0] = 31;
            cmd[1] = 83;
            int maskbit = 0;
            boolean[] bmasklst = {bSetBluetooth, bSetCutter, bSetHeatDot, bSetHeatOnTime, bSetHeatOffTime, bSetSensor, bSetUsb, bSetCutBeep, bSetSpeed};
            for (int i = 0; i < bmasklst.length; i++) {
                if (bmasklst[i]) {
                    maskbit |= 1 << i;
                }
            }
            cmd[2] = (byte) ((int) (((long) (maskbit >> 24)) & 255));
            cmd[3] = (byte) ((int) (((long) (maskbit >> 16)) & 255));
            cmd[4] = (byte) ((int) (((long) (maskbit >> 8)) & 255));
            cmd[5] = (byte) ((int) (((long) (maskbit >> 0)) & 255));
            cmd[9] = 0;
            cmd[8] = 0;
            cmd[7] = 0;
            cmd[6] = 0;
            cmd[10] = (byte) (cmd.length - 12);
            cmd[11] = (byte) nSetBluetoothValue;
            cmd[12] = (byte) nSetCutterValue;
            cmd[13] = (byte) (nSetHeatDotValue / 8);
            cmd[14] = (byte) ((int) (((long) (nSetHeatOnTimeValue >> 8)) & 255));
            cmd[15] = (byte) ((int) (((long) (nSetHeatOnTimeValue >> 0)) & 255));
            cmd[16] = (byte) ((int) (((long) (nSetHeatOffTimeValue >> 8)) & 255));
            cmd[17] = (byte) ((int) (((long) (nSetHeatOffTimeValue >> 0)) & 255));
            cmd[18] = (byte) nSetSensorValue;
            cmd[19] = (byte) nSetUsbValue;
            long snd = (((((long) nBeepCount) & 127) * 2) << 24) + ((((long) nBeepOn) & 4095) << 12) + (((long) nBeepOff) & 4095);
            cmd[20] = (byte) ((int) ((snd >> 24) & 255));
            cmd[21] = (byte) ((int) ((snd >> 16) & 255));
            cmd[22] = (byte) ((int) ((snd >> 8) & 255));
            cmd[23] = (byte) ((int) ((snd >> 0) & 255));
            cmd[24] = (byte) nSetSpeedValue;
            cmd[cmd.length - 1] = 0;
            for (int i2 = 11; i2 <= cmd.length - 2; i2++) {
                int length = cmd.length - 1;
                cmd[length] = (byte) (cmd[length] ^ cmd[i2]);
            }
            return this.IO.Write(cmd, 0, cmd.length) == cmd.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Mqtt_ServerIPPort(String szip, String szport) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            byte[] ipbytes = IsIPValid(szip);
            if (ipbytes == null) {
                throw new Exception("Invalid ip address: " + szip);
            }
            int port = Integer.parseInt(szport);
            byte[] cmd = {31, 40, 84, 6, 0, 0, 0, 0, 0, (byte) ((int) (((long) (port >> 8)) & 255)), (byte) ((int) (((long) port) & 255))};
            System.arraycopy(ipbytes, 0, cmd, 5, 4);
            return this.IO.Write(cmd, 0, cmd.length) == cmd.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public boolean Setting_Mqtt_UserNamePassword(String user, String password) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            int data_len = user.length() + 1 + password.length();
            byte[] cmd = new byte[(data_len + 5)];
            int idx = 0 + 1;
            cmd[0] = 31;
            int idx2 = idx + 1;
            cmd[idx] = 40;
            int idx3 = idx2 + 1;
            cmd[idx2] = 81;
            int idx4 = idx3 + 1;
            cmd[idx3] = (byte) ((int) (((long) data_len) & 255));
            cmd[idx4] = (byte) ((int) (((long) (data_len >> 8)) & 255));
            byte[] user_bytes = user.getBytes();
            System.arraycopy(user_bytes, 0, cmd, idx4 + 1, user_bytes.length);
            int idx5 = user_bytes.length + 1 + 5;
            byte[] password_bytes = password.getBytes();
            System.arraycopy(password_bytes, 0, cmd, idx5, password_bytes.length);
            int idx6 = idx5 + 1;
            return this.IO.Write(cmd, 0, cmd.length) == cmd.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    private byte[] IsIPValid(String ip) {
        byte[] ipbytes = new byte[4];
        int valid = 0;
        String ipstr = ip + ".";
        int s = 0;
        for (int e = 0; e < ipstr.length(); e++) {
            if ('.' == ipstr.charAt(e)) {
                if (e - s > 3 || e - s <= 0) {
                    return null;
                }
                try {
                    int ipbyte = Integer.parseInt(ipstr.substring(s, e));
                    if (ipbyte < 0 || ipbyte > 255) {
                        return null;
                    }
                    ipbytes[valid] = (byte) ipbyte;
                    s = e + 1;
                    valid++;
                } catch (NumberFormatException e2) {
                    return null;
                }
            }
        }
        if (valid != 4) {
            return null;
        }
        return ipbytes;
    }

    private byte[] IsMACValid(String mac) {
        byte[] macbytes = new byte[6];
        int valid = 0;
        String macstr = mac + ":";
        int s = 0;
        for (int e = 0; e < macstr.length(); e++) {
            if (':' == macstr.charAt(e)) {
                if (e - s != 2) {
                    return null;
                }
                try {
                    int ipbyte = Integer.parseInt(macstr.substring(s, e), 16);
                    if (ipbyte < 0 || ipbyte > 255) {
                        return null;
                    }
                    macbytes[valid] = (byte) ipbyte;
                    s = e + 1;
                    valid++;
                } catch (NumberFormatException e2) {
                    return null;
                }
            }
        }
        if (valid != 6) {
            return null;
        }
        return macbytes;
    }
}
