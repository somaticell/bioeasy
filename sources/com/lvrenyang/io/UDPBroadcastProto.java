package com.lvrenyang.io;

import android.support.v4.media.TransportMediator;
import com.alibaba.fastjson.asm.Opcodes;
import com.lvrenyang.io.base.UDPIO;

public class UDPBroadcastProto {
    public static boolean configName(UDPIO udp, byte[] mac, byte[] name, int timeout, int interval, int max_retry) {
        boolean ret = false;
        for (int retry = 0; retry < max_retry && udp.IsOpened(); retry++) {
            byte[] cmd = new byte[Opcodes.LCMP];
            cmd[0] = 69;
            cmd[1] = 80;
            cmd[2] = 83;
            cmd[3] = 79;
            cmd[4] = 78;
            cmd[5] = 83;
            cmd[6] = 3;
            cmd[13] = -122;
            System.arraycopy(mac, 0, cmd, 14, 6);
            System.arraycopy(name, 0, cmd, 14 + 6, Math.min(name.length, TransportMediator.KEYCODE_MEDIA_PAUSE));
            if (udp.Write(cmd, 0, cmd.length) == cmd.length) {
                long time = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - time >= ((long) timeout) || !udp.IsOpened()) {
                        break;
                    }
                    byte[] data = new byte[1024];
                    if (udp.RecvDirect(data, 0, data.length, timeout) == 148) {
                        cmd[5] = 115;
                        if (ByteUtils.bytesEquals(cmd, 0, data, 0, cmd.length)) {
                            ret = true;
                            break;
                        }
                    }
                }
                if (ret) {
                    break;
                }
            }
        }
        return ret;
    }

    public static boolean configIP(UDPIO udp, byte[] mac, boolean dhcp, byte[] ip, byte[] netmask, byte[] gateway, int timeout, int interval, int max_retry) {
        boolean ret = false;
        for (int retry = 0; retry < max_retry && udp.IsOpened(); retry++) {
            byte[] bFixedIP = {Byte.MIN_VALUE, 8};
            byte[] bDhcpIP = {0, 12};
            byte[] cmd = {69, 80, 83, 79, 78, 83, 0, 0, 0, 16, 0, 0, 0, 21, 1, 100, -21, -116, 42, 42, -84, 0, 12, -64, -88, 10, -48, -1, -1, -1, 0, 0, 0, 0, 0};
            System.arraycopy(mac, 0, cmd, 15, 6);
            int offset = 15 + 6;
            if (!dhcp) {
                bDhcpIP = bFixedIP;
            }
            System.arraycopy(bDhcpIP, 0, cmd, offset, 2);
            int offset2 = offset + 2;
            System.arraycopy(ip, 0, cmd, offset2, 4);
            int offset3 = offset2 + 4;
            System.arraycopy(netmask, 0, cmd, offset3, 4);
            int offset4 = offset3 + 4;
            System.arraycopy(gateway, 0, cmd, offset4, 4);
            int offset5 = offset4 + 4;
            if (udp.Write(cmd, 0, cmd.length) == cmd.length) {
                long time = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - time >= ((long) timeout) || !udp.IsOpened()) {
                        break;
                    }
                    byte[] data = new byte[1024];
                    if (udp.RecvDirect(data, 0, data.length, timeout) == 37 && data[0] == 69 && data[1] == 80 && data[2] == 83 && data[3] == 79 && data[4] == 78 && data[5] == 115 && data[6] == 0 && data[7] == 0 && data[8] == 0 && data[9] == 16) {
                        ret = true;
                        break;
                    }
                }
                if (ret) {
                    break;
                }
            }
        }
        return ret;
    }

    public static boolean confirmConfiguration(UDPIO udp, byte[] mac, int timeout, int interval, int max_retry) {
        boolean ret = false;
        for (int retry = 0; retry < max_retry && udp.IsOpened(); retry++) {
            byte[] cmd = {69, 80, 83, 79, 78, 83, 0, 0, 1, 0, 0, 0, 0, 11, 100, -21, -116, 42, 42, -84, 1, 0, 18, 0, 0};
            System.arraycopy(mac, 0, cmd, 14, 6);
            int offset = 14 + 6;
            if (udp.Write(cmd, 0, cmd.length) == cmd.length) {
                long time = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - time >= ((long) timeout) || !udp.IsOpened()) {
                        break;
                    }
                    byte[] data = new byte[1024];
                    if (udp.RecvDirect(data, 0, data.length, timeout) == 25 && data[0] == 69 && data[1] == 80 && data[2] == 83 && data[3] == 79 && data[4] == 78 && data[5] == 115 && data[6] == 0 && data[7] == 0 && data[8] == 1 && data[9] == 0) {
                        ret = true;
                        break;
                    }
                }
                if (ret) {
                    break;
                }
            }
        }
        return ret;
    }
}
