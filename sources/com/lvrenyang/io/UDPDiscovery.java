package com.lvrenyang.io;

import android.util.Log;
import com.lvrenyang.io.base.UDPIO;
import java.net.DatagramPacket;
import java.util.Locale;

public class UDPDiscovery {
    private static final String TAG = "UDPDiscovery";
    public UDPDiscoveredPrinterCallBack discoveredPrinterCallback = null;
    public UDPDiscoveryFinishedCallBack discoveryFinishedCallback = null;
    public UDPDiscoveryStartedCallBack discoveryStartedCallback = null;

    public interface UDPDiscoveredPrinterCallBack {
        void onDiscoveredPrinter(String str, String str2, String str3);
    }

    public interface UDPDiscoveryFinishedCallBack {
        void onDiscoverFinished();
    }

    public interface UDPDiscoveryStartedCallBack {
        void onDiscoverStarted();
    }

    public void discoverPrinter(UDPIO udp, int timeout, int interval, int max_retry) {
        if (this.discoveryStartedCallback != null) {
            this.discoveryStartedCallback.onDiscoverStarted();
        }
        for (int retry = 0; retry < max_retry && udp.IsOpened(); retry++) {
            byte[] cmd = {69, 80, 83, 79, 78, 81, 3, 0, 0, 1, 0, 0, 0, 0};
            if (udp.Write(cmd, 0, cmd.length) == cmd.length) {
                long time = System.currentTimeMillis();
                while (System.currentTimeMillis() - time < ((long) timeout) && udp.IsOpened()) {
                    DatagramPacket packet = udp.RecvPacketDirect(1024, interval);
                    if (packet != null) {
                        byte[] data = packet.getData();
                        if (packet.getLength() == 184 && data[0] == 69 && data[1] == 80 && data[2] == 83 && data[3] == 79 && data[4] == 78 && data[5] == 113) {
                            int namelen = 0;
                            int i = 56;
                            while (true) {
                                if (i >= data.length) {
                                    break;
                                } else if (data[i] == 0) {
                                    namelen = i - 56;
                                    break;
                                } else {
                                    i++;
                                }
                            }
                            String name = new String(data, 56, namelen);
                            byte[] ipBytes = packet.getAddress().getAddress();
                            String ip = String.format(Locale.CHINA, "%d.%d.%d.%d", new Object[]{Long.valueOf(((long) ipBytes[0]) & 255), Long.valueOf(((long) ipBytes[1]) & 255), Long.valueOf(((long) ipBytes[2]) & 255), Long.valueOf(((long) ipBytes[3]) & 255)});
                            String mac = String.format(Locale.CHINA, "%02X-%02X-%02X-%02X-%02X-%02X", new Object[]{Long.valueOf(((long) data[14]) & 255), Long.valueOf(((long) data[15]) & 255), Long.valueOf(((long) data[16]) & 255), Long.valueOf(((long) data[17]) & 255), Long.valueOf(((long) data[18]) & 255), Long.valueOf(((long) data[19]) & 255)});
                            Log.i(TAG, "Discovered MAC:" + mac + " IP:" + ip + " Name:" + name);
                            if (this.discoveredPrinterCallback != null) {
                                this.discoveredPrinterCallback.onDiscoveredPrinter(mac, ip, name);
                            }
                        }
                    }
                }
            }
        }
        if (this.discoveryFinishedCallback != null) {
            this.discoveryFinishedCallback.onDiscoverFinished();
        }
    }

    public static String discoverPrinterIPByName(String local_ip, int local_port, String dest_ip, int dest_port, String name, int timeout, int interval, int max_retry) {
        String ip = null;
        UDPIO udp = new UDPIO();
        if (udp.Open(local_ip, local_port, dest_ip, dest_port)) {
            udp.SetBroadcast(true);
            udp.SetReuseAddress(true);
            for (int retry = 0; retry < max_retry && udp.IsOpened(); retry++) {
                byte[] cmd = {69, 80, 83, 79, 78, 81, 3, 0, 0, 1, 0, 0, 0, 0};
                if (udp.Write(cmd, 0, cmd.length) == cmd.length) {
                    long time = System.currentTimeMillis();
                    while (true) {
                        if (System.currentTimeMillis() - time >= ((long) timeout) || !udp.IsOpened()) {
                            break;
                        }
                        DatagramPacket packet = udp.RecvPacketDirect(1024, interval);
                        if (packet != null) {
                            byte[] data = packet.getData();
                            if (packet.getLength() == 184 && data[0] == 69 && data[1] == 80 && data[2] == 83 && data[3] == 79 && data[4] == 78 && data[5] == 113) {
                                int namelen = 0;
                                int i = 56;
                                while (true) {
                                    if (i >= data.length) {
                                        break;
                                    } else if (data[i] == 0) {
                                        namelen = i - 56;
                                        break;
                                    } else {
                                        i++;
                                    }
                                }
                                if (name.equals(new String(data, 56, namelen))) {
                                    byte[] ipBytes = packet.getAddress().getAddress();
                                    ip = String.format(Locale.CHINA, "%d.%d.%d.%d", new Object[]{Long.valueOf(((long) ipBytes[0]) & 255), Long.valueOf(((long) ipBytes[1]) & 255), Long.valueOf(((long) ipBytes[2]) & 255), Long.valueOf(((long) ipBytes[3]) & 255)});
                                    Log.i(TAG, "Discovered " + name + " " + ip);
                                    break;
                                }
                            }
                        }
                    }
                    if (ip != null) {
                        break;
                    }
                }
            }
            udp.Close();
        }
        return ip;
    }
}
