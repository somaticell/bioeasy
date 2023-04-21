package com.ble.ble.adaption;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;
import com.ble.api.DataUtil;
import com.ble.ble.BleCallBack;
import com.ble.ble.BleService;
import com.ble.ble.constants.BleUUIDS;
import com.ble.ble.util.GattUtil;
import com.ble.gatt.GattAttributes;
import java.util.Timer;
import java.util.TimerTask;

public class AdaptionUtil {
    private final String a = "AdaptionUtil";
    private BleService b;
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public boolean d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public float f;
    /* access modifiers changed from: private */
    public OnResultListener g;
    private Timer h;
    private BleCallBack i = new BleCallBack() {
        public void onCharacteristicRead(String str, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            String uuid = bluetoothGattCharacteristic.getUuid().toString();
            if (str.equals(AdaptionUtil.this.c)) {
                if (BleUUIDS.CHARACTERS[3].toString().equals(uuid)) {
                    AdaptionUtil.this.a(bluetoothGattCharacteristic.getValue());
                } else if (GattAttributes.Software_Revision.toString().equals(uuid)) {
                    try {
                        String str2 = new String(bluetoothGattCharacteristic.getValue());
                        Log.d("AdaptionUtil", "模块软件版本===" + str2 + "===");
                        float unused = AdaptionUtil.this.f = Float.valueOf(str2.substring(str2.indexOf(86) + 1)).floatValue();
                        Log.i("AdaptionUtil", "模块软件版本===" + AdaptionUtil.this.f + "===");
                        if (AdaptionUtil.this.f < 2.3f) {
                            AdaptionUtil.this.g();
                            if (AdaptionUtil.this.g != null) {
                                AdaptionUtil.this.g.onError(AdaptionUtil.this.c, new Error(4, "Device [" + AdaptionUtil.this.c + "] current version is " + AdaptionUtil.this.f + ", can not adapt."));
                                return;
                            }
                            return;
                        }
                        boolean unused2 = AdaptionUtil.this.d = true;
                        new Timer().schedule(new a(), 0, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    private class a extends TimerTask {
        int a;

        a() {
        }

        public void run() {
            switch (this.a) {
                case 0:
                    boolean unused = AdaptionUtil.this.e();
                    break;
                case 1:
                    boolean unused2 = AdaptionUtil.this.d = true;
                    boolean unused3 = AdaptionUtil.this.f();
                    cancel();
                    break;
                default:
                    cancel();
                    break;
            }
            this.a++;
        }
    }

    private class b extends TimerTask {
        int a;
        byte[] b;

        b(int i) {
            int unused = AdaptionUtil.this.e = i;
            this.b = DataUtil.hexToByteArray(String.format("%08x", new Object[]{Integer.valueOf(i)}));
        }

        public void run() {
            switch (this.a) {
                case 0:
                    boolean unused = AdaptionUtil.this.b(this.b);
                    break;
                case 1:
                    boolean unused2 = AdaptionUtil.this.d = false;
                    boolean unused3 = AdaptionUtil.this.f();
                    cancel();
                    break;
                default:
                    cancel();
                    break;
            }
            this.a++;
        }
    }

    public AdaptionUtil(BleService bleService) {
        this.b = bleService;
    }

    private void a() {
        if (this.h == null) {
            this.h = new Timer();
            this.h.schedule(new TimerTask() {
                public void run() {
                    Log.e("AdaptionUtil", "写入配置超时！");
                    AdaptionUtil.this.g();
                    if (AdaptionUtil.this.g != null) {
                        AdaptionUtil.this.g.onError(AdaptionUtil.this.c, new Error(3, "Device [" + AdaptionUtil.this.c + "] write configs timeout!"));
                    }
                }
            }, 2000);
        }
    }

    /* access modifiers changed from: private */
    public void a(byte[] bArr) {
        int a2 = a.a(bArr);
        if (this.d) {
            new Timer().schedule(new b(this.f >= 2.6f ? a2 | 4194304 : this.f >= 2.3f ? a2 | 131072 : a2), 0, 300);
        } else if (a2 == this.e) {
            g();
            if (this.g != null) {
                this.g.onSuccess(this.c);
            }
        } else {
            Log.e("AdaptionUtil", "read value: " + a2 + ", write value: " + this.e);
            g();
            if (this.g != null) {
                this.g.onError(this.c, new Error(2, "Device [" + this.c + "] write configs failed!"));
            }
        }
    }

    private void b() {
        if (this.h != null) {
            this.h.cancel();
            this.h = null;
        }
    }

    /* access modifiers changed from: private */
    public boolean b(byte[] bArr) {
        if (this.b == null) {
            return false;
        }
        BluetoothGatt bluetoothGatt = this.b.getBluetoothGatt(this.c);
        return this.b.send(bluetoothGatt, GattUtil.getGattCharacteristic(bluetoothGatt, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[2]), bArr, false);
    }

    private void c() {
        if (this.b != null) {
            this.b.removeBleCallBack(this.i);
        }
    }

    private void d() {
        if (this.b.getConnectionState(this.c) == 2) {
            BluetoothGatt bluetoothGatt = this.b.getBluetoothGatt(this.c);
            BluetoothGattCharacteristic gattCharacteristic = GattUtil.getGattCharacteristic(bluetoothGatt, GattAttributes.Device_Information, GattAttributes.Software_Revision);
            if (gattCharacteristic != null) {
                Log.d("AdaptionUtil", "readFirmwareRevision() - " + this.b.read(bluetoothGatt, gattCharacteristic) + ", gatt=" + bluetoothGatt + ", c=" + gattCharacteristic);
                return;
            }
            g();
            if (this.g != null) {
                this.g.onError(this.c, new Error(4, "Device [" + this.c + "] not need to adapt."));
                return;
            }
            return;
        }
        g();
        if (this.g != null) {
            this.g.onError(this.c, new Error(1, "Device [" + this.c + "] is disconnected."));
        }
    }

    /* access modifiers changed from: private */
    public boolean e() {
        if (this.b == null) {
            return false;
        }
        BluetoothGatt bluetoothGatt = this.b.getBluetoothGatt(this.c);
        BluetoothGattCharacteristic gattCharacteristic = GattUtil.getGattCharacteristic(bluetoothGatt, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[4]);
        return this.b.send(bluetoothGatt, gattCharacteristic, new byte[]{64}, false);
    }

    /* access modifiers changed from: private */
    public boolean f() {
        if (this.b == null) {
            return false;
        }
        BluetoothGatt bluetoothGatt = this.b.getBluetoothGatt(this.c);
        return this.b.read(bluetoothGatt, GattUtil.getGattCharacteristic(bluetoothGatt, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[3]));
    }

    /* access modifiers changed from: private */
    public void g() {
        c();
        b();
    }

    public void setOnResultListener(OnResultListener onResultListener) {
        this.g = onResultListener;
    }

    public void writeAdaptionConfigs(String str) {
        a();
        this.b.addBleCallBack(this.i);
        this.c = str;
        d();
    }
}
