package cn.com.bioeasy.healty.app.healthapp.ble;

import com.ble.ble.LeScanRecord;

public class LeDevice {
    private LeScanRecord mRecord;
    private final String mac;
    private String name;
    private boolean oadSupported = false;
    private int rssi;
    private String rxData = "No data";

    public LeDevice(String name2, String mac2) {
        this.name = name2;
        this.mac = mac2;
    }

    public LeDevice(String name2, String mac2, int rssi2, byte[] scanRecord) {
        this.name = name2;
        this.mac = mac2;
        this.rssi = rssi2;
        this.mRecord = LeScanRecord.parseFromBytes(scanRecord);
    }

    public boolean isOadSupported() {
        return this.oadSupported;
    }

    public void setOadSupported(boolean oadSupported2) {
        this.oadSupported = oadSupported2;
    }

    public LeScanRecord getLeScanRecord() {
        return this.mRecord;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int rssi2) {
        this.rssi = rssi2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getMac() {
        return this.mac;
    }

    public String getRxData() {
        return this.rxData;
    }

    public void setRxData(String rxData2) {
        this.rxData = rxData2;
    }

    public boolean equals(Object o) {
        if (o instanceof LeDevice) {
            return ((LeDevice) o).getMac().equals(this.mac);
        }
        return false;
    }
}
