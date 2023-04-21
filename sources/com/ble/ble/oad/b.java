package com.ble.ble.oad;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.ble.api.DataUtil;
import com.ble.ble.BleCallBack;
import com.ble.ble.BleService;
import com.ble.gatt.GattAttributes;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

final class b extends OADProxy {
    private final int f = 0;
    private final int g = 1;
    private final int h = 2;
    private final int i = 3;
    /* access modifiers changed from: private */
    public final c j = new c();
    /* access modifiers changed from: private */
    public final a k = new a();
    private Timer l = null;
    private Timer m = null;
    private int n = 0;
    private int o;
    /* access modifiers changed from: private */
    public BluetoothGatt p = null;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic q = null;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic r = null;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic s = null;
    private final BleCallBack t = new BleCallBack() {
        public void onCharacteristicChanged(String str, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            String uuid = bluetoothGattCharacteristic.getUuid().toString();
            if (GattAttributes.TI_OAD_Image_Identify.toString().equals(uuid)) {
                Log.e("CC26xxR2OADProxy", "CC26xx OAD Image Identify Rx: " + str + " [" + DataUtil.byteArrayToHex(bluetoothGattCharacteristic.getValue()) + "]");
            } else if (GattAttributes.TI_OAD_Image_Block.toString().equals(uuid)) {
                byte[] value = bluetoothGattCharacteristic.getValue();
                Log.d("CC26xxR2OADProxy", "CC26xx OAD Block:  " + DataUtil.byteArrayToHex(value));
                if (value[0] == 0 && value[1] == 0) {
                    b.this.c.setCharacteristicNotification(b.this.p, b.this.r, false);
                    b.this.b();
                }
            } else if (GattAttributes.TI_OAD_Image_Status.toString().equals(uuid)) {
                byte b = bluetoothGattCharacteristic.getValue()[0] & BLEServiceApi.CONNECTED_STATUS;
                switch (b) {
                    case 0:
                        Log.i("CC26xxR2OADProxy", "CC26xx OAD Image Status: " + str + "[OAD succeeded]");
                        break;
                    case 1:
                        Log.e("CC26xxR2OADProxy", "CC26xx OAD Image Status: " + str + "[The downloaded image’s CRC doesn’t match the one expected from the metadata]");
                        break;
                    case 2:
                        Log.e("CC26xxR2OADProxy", "CC26xx OAD Image Status: " + str + "[The external flash cannot be opened]");
                        break;
                    case 3:
                        Log.e("CC26xxR2OADProxy", "CC26xx OAD Image Status: " + str + "[The block number of the received packet doesn’t match the one requested. An overflow has occurred.]");
                        break;
                    default:
                        Log.e("CC26xxR2OADProxy", "CC26xx OAD Image Status: " + DataUtil.byteArrayToHex(bluetoothGattCharacteristic.getValue()) + "[unknown]");
                        break;
                }
                if (b != 0) {
                    b.this.stopProgramming();
                }
            }
        }
    };

    private class a {
        int a;
        short b;
        short c;
        short d;
        short e;
        byte f;
        byte[] g;
        byte[] h;

        private a() {
            this.g = new byte[4];
            this.h = new byte[16];
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.b = DataUtil.buildUint16(this.h[1], this.h[0]);
            this.c = DataUtil.buildUint16(this.h[3], this.h[2]);
            this.d = DataUtil.buildUint16(this.h[5], this.h[4]);
            this.a = DataUtil.buildUint16(this.h[7], this.h[6]);
            this.g[0] = this.h[8];
            this.g[1] = this.h[9];
            this.g[2] = this.h[10];
            this.g[3] = this.h[11];
            this.e = DataUtil.buildUint16(this.h[13], this.h[12]);
            this.f = this.h[14];
            Log.d("CC26xxR2OADProxy", toString());
        }

        public String toString() {
            return "ImgHdr.len = " + this.a + "\nImgHdr.ver = " + this.d + "\nImgHdr.uid = " + new String(this.g) + "\nImgHdr.addr = " + this.e + "\nImgHdr.imgType = " + this.f + "\nImgHdr.crc0 = " + String.format("%04x", new Object[]{Short.valueOf(this.b)});
        }
    }

    /* renamed from: com.ble.ble.oad.b$b  reason: collision with other inner class name */
    private class C0021b extends TimerTask {
        String a;
        int b;

        C0021b(String str) {
            this.a = str;
        }

        public void run() {
            switch (this.b) {
                case 0:
                    b.this.c.setCharacteristicNotification(b.this.p, b.this.q, true);
                    break;
                case 1:
                    b.this.c.setCharacteristicNotification(b.this.p, b.this.r, true);
                    break;
                case 2:
                    b.this.c.setCharacteristicNotification(b.this.p, b.this.s, true);
                    break;
            }
            this.b++;
            if (this.b == 10 && b.this.b != null) {
                cancel();
                b.this.d = State.prepared;
                b.this.b.onPrepared(this.a);
            }
            if (this.b > 10) {
                cancel();
            }
        }
    }

    private class c {
        int a;
        short b;
        short c;
        int d;

        private c() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = 0;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.a = 0;
            if (b.this.e == OADType.cc2640_oad_2_0_0) {
                this.a += 16;
            }
            this.b = 0;
            this.d = 0;
            this.c = (short) (b.this.k.a / 4);
        }
    }

    private class d extends TimerTask {
        private d() {
        }

        public void run() {
            b.this.a();
        }
    }

    private class e extends TimerTask {
        private e() {
        }

        public void run() {
            b.this.j.d += 1000;
            if (b.this.b != null) {
                b.this.b.onProgressChanged(b.this.p.getDevice().getAddress(), b.this.j.a + 0, b.this.j.c * 16, (long) b.this.j.d);
            }
        }
    }

    b(BleService bleService, OADListener oADListener, OADType oADType) {
        super(bleService, oADListener);
        this.e = oADType;
        this.c.addBleCallBack(this.t);
    }

    private int a(String str, boolean z) {
        InputStream fileInputStream;
        if (z) {
            try {
                fileInputStream = this.c.getAssets().open(str);
            } catch (IOException e2) {
                e2.printStackTrace();
                return -1;
            }
        } else {
            fileInputStream = new FileInputStream(new File(str));
        }
        int read = fileInputStream.read(this.a, 0, this.a.length);
        fileInputStream.close();
        System.arraycopy(this.a, 0, this.k.h, 0, this.k.h.length);
        this.k.a();
        return read;
    }

    /* access modifiers changed from: private */
    public void a() {
        if (isProgramming()) {
            if (this.j.b < this.j.c) {
                byte[] bArr = new byte[18];
                bArr[0] = DataUtil.loUint16(this.j.b);
                bArr[1] = DataUtil.hiUint16(this.j.b);
                System.arraycopy(this.a, this.j.a, bArr, 2, 16);
                this.r.setValue(bArr);
                if (this.p.writeCharacteristic(this.r)) {
                    if (this.b != null) {
                        this.b.onBlockWrite(bArr);
                    }
                    this.n = 0;
                    c cVar = this.j;
                    cVar.b = (short) (cVar.b + 1);
                    this.j.a += 16;
                    if (this.j.b != 0 && this.j.b == this.j.c) {
                        stopProgramming();
                        return;
                    }
                    return;
                }
                this.n++;
                if (this.n > 100) {
                    Log.e("CC26xxR2OADProxy", "已连续100次发送失败，终止升级！");
                    stopProgramming();
                    return;
                }
                return;
            }
            stopProgramming();
        }
    }

    private boolean a(String str) {
        this.p = this.c.getBluetoothGatt(str);
        if (this.p != null) {
            BluetoothGattService service = this.p.getService(GattAttributes.TI_OAD_Service);
            if (service != null) {
                this.q = service.getCharacteristic(GattAttributes.TI_OAD_Image_Identify);
                this.r = service.getCharacteristic(GattAttributes.TI_OAD_Image_Block);
                this.s = service.getCharacteristic(GattAttributes.TI_OAD_Image_Status);
                this.q.setWriteType(1);
                this.r.setWriteType(1);
                return true;
            }
            Log.e("CC26xxR2OADProxy", "OAD not supported: " + this.p.getDevice().getAddress());
        } else {
            Log.e("CC26xxR2OADProxy", "设备未连接，无法升级：" + str);
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void b() {
        this.j.a();
        this.l = new Timer();
        this.l.scheduleAtFixedRate(new e(), 1000, 1000);
        this.m = new Timer();
        this.m.scheduleAtFixedRate(new d(), (long) this.o, (long) this.o);
    }

    private void c() {
        if (this.l != null) {
            this.l.cancel();
            this.l.purge();
        }
        if (this.m != null) {
            this.m.cancel();
            this.m.purge();
        }
    }

    public void prepare(String str, String str2, boolean z) {
        if (this.d == State.programming) {
            throw new IllegalStateException("Can't prepare() in programming state.");
        }
        if (a(str) && a(str2, z) != -1) {
            new Timer().schedule(new C0021b(str), 0, 100);
        }
    }

    public void release() {
        c();
        this.c.removeBleCallBack(this.t);
    }

    public void startProgramming(int i2) {
        if (this.d != State.prepared) {
            throw new IllegalStateException("start programming in illegal state: " + this.d + ", you should start programming in prepared state by call prepare()");
        }
        this.d = State.programming;
        this.o = i2;
        this.q.setValue(this.k.h);
        this.p.writeCharacteristic(this.q);
    }

    public void stopProgramming() {
        c();
        if (this.p != null) {
            if (this.j.b == 0 || this.j.b != this.j.c) {
                if (this.d == State.programming) {
                    this.d = State.interrupted;
                } else {
                    this.d = State.idle;
                }
                if (this.b != null) {
                    this.b.onInterrupted(this.p.getDevice().getAddress(), this.j.a, this.j.c * 16, (long) this.j.d);
                    return;
                }
                return;
            }
            this.d = State.finished;
            if (this.b != null) {
                this.b.onFinished(this.p.getDevice().getAddress(), this.j.c * 16, (long) this.j.d);
            }
        }
    }
}
