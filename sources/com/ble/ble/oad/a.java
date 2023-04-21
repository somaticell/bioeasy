package com.ble.ble.oad;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.util.Log;
import com.baidu.mapapi.UIMsg;
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

final class a extends OADProxy {
    /* access modifiers changed from: private */
    public final b f = new b();
    /* access modifiers changed from: private */
    public C0020a g;
    private Timer h = null;
    private Timer i = null;
    private int j = 0;
    /* access modifiers changed from: private */
    public BluetoothGatt k = null;
    private BluetoothGattCharacteristic l = null;
    private BluetoothGattCharacteristic m = null;
    private final BleCallBack n = new BleCallBack() {
        public void onCharacteristicChanged(String str, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            if (GattAttributes.TI_OAD_Image_Identify.toString().equals(bluetoothGattCharacteristic.getUuid().toString())) {
                Log.e("CC26xxOADProxy", "CC26xx OAD Image Identify Rx: " + str + '[' + DataUtil.byteArrayToHex(bluetoothGattCharacteristic.getValue()) + ']');
            }
        }
    };

    /* renamed from: com.ble.ble.oad.a$a  reason: collision with other inner class name */
    private class C0020a {
        short a;
        short b;
        short c;
        short d;
        int e;
        byte f;
        byte[] g = new byte[4];

        C0020a(byte[] bArr, int i) {
            this.e = (i + UIMsg.m_AppUI.MSG_SENSOR) / 4;
            this.c = 0;
            byte[] bArr2 = this.g;
            byte[] bArr3 = this.g;
            byte[] bArr4 = this.g;
            this.g[3] = 69;
            bArr4[2] = 69;
            bArr3[1] = 69;
            bArr2[0] = 69;
            this.d = 1024;
            this.f = 1;
            this.a = a(1, bArr);
            this.b = -1;
            Log.d("CC26xxOADProxy", toString());
        }

        /* access modifiers changed from: package-private */
        public short a(int i, byte[] bArr) {
            long j = (long) (i * 4096);
            byte b2 = (byte) i;
            byte b3 = (byte) ((this.e * 4) / 4096);
            int i2 = (this.e * 4) - (b3 * 4096);
            byte b4 = (byte) (b3 + b2);
            short s = 0;
            while (true) {
                int i3 = 0;
                while (i3 < 4096) {
                    if (i == b2 && i3 == 0) {
                        i3 += 3;
                    } else if (i == b4 && i3 == i2) {
                        return a(a(s, (byte) 0), (byte) 0);
                    } else {
                        s = a(s, bArr[(int) (((long) i3) + j)]);
                    }
                    s = s;
                    i3++;
                }
                i++;
                j = (long) (i * 4096);
            }
        }

        /* access modifiers changed from: package-private */
        public short a(short s, byte b2) {
            byte b3 = 0;
            while (b3 < 8) {
                boolean z = (s & 32768) == 32768;
                short s2 = (short) (s << 1);
                if ((b2 & 128) == 128) {
                    s2 = (short) (s2 | 1);
                }
                if (z) {
                    s2 = (short) (s2 ^ 4129);
                }
                b2 = (byte) (b2 << 1);
                b3 = (byte) (b3 + 1);
                s = s2;
            }
            return s;
        }

        /* access modifiers changed from: package-private */
        public byte[] a() {
            byte[] bArr = new byte[16];
            bArr[0] = DataUtil.loUint16(this.a);
            bArr[1] = DataUtil.hiUint16(this.a);
            bArr[2] = DataUtil.loUint16(this.b);
            bArr[3] = DataUtil.hiUint16(this.b);
            bArr[4] = DataUtil.loUint16(this.c);
            bArr[5] = DataUtil.hiUint16(this.c);
            bArr[6] = DataUtil.loUint16((short) this.e);
            bArr[7] = DataUtil.hiUint16((short) this.e);
            byte b2 = this.g[0];
            bArr[11] = b2;
            bArr[10] = b2;
            bArr[9] = b2;
            bArr[8] = b2;
            bArr[12] = DataUtil.loUint16(this.d);
            bArr[13] = DataUtil.hiUint16(this.d);
            bArr[14] = this.f;
            bArr[15] = -1;
            return bArr;
        }

        public String toString() {
            return "ImgHdr.len = " + this.e + "\nImgHdr.ver = " + this.c + "\nImgHdr.uid = " + new String(this.g) + "\nImgHdr.addr = " + this.d + "\nImgHdr.imgType = " + this.f + "\nImgHdr.crc0 = " + String.format("%04x", new Object[]{Short.valueOf(this.a)});
        }
    }

    private class b {
        int a;
        short b;
        short c;
        int d;

        private b() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = 0;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.a = 4096;
            this.b = 0;
            this.d = 0;
            this.c = (short) (a.this.g.e / 4);
        }
    }

    private class c extends TimerTask {
        private c() {
        }

        public void run() {
            a.this.a();
        }
    }

    private class d extends TimerTask {
        private d() {
        }

        public void run() {
            a.this.f.d += 1000;
            if (a.this.b != null) {
                a.this.b.onProgressChanged(a.this.k.getDevice().getAddress(), a.this.f.a + UIMsg.m_AppUI.MSG_SENSOR, a.this.f.c * 16, (long) a.this.f.d);
            }
        }
    }

    a(BleService bleService, OADListener oADListener) {
        super(bleService, oADListener);
        this.e = OADType.cc2640_off_chip_oad;
        this.c.addBleCallBack(this.n);
    }

    private int a(String str, boolean z) {
        InputStream fileInputStream;
        if (z) {
            try {
                fileInputStream = this.c.getAssets().open(str);
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        } else {
            fileInputStream = new FileInputStream(new File(str));
        }
        int read = fileInputStream.read(this.a, 0, this.a.length);
        fileInputStream.close();
        this.g = new C0020a(this.a, read);
        return read;
    }

    /* access modifiers changed from: private */
    public void a() {
        if (isProgramming()) {
            if (this.f.b < this.f.c) {
                byte[] bArr = new byte[18];
                bArr[0] = DataUtil.loUint16(this.f.b);
                bArr[1] = DataUtil.hiUint16(this.f.b);
                System.arraycopy(this.a, this.f.a, bArr, 2, 16);
                this.m.setValue(bArr);
                if (this.k.writeCharacteristic(this.m)) {
                    if (this.b != null) {
                        this.b.onBlockWrite(bArr);
                    }
                    this.j = 0;
                    b bVar = this.f;
                    bVar.b = (short) (bVar.b + 1);
                    this.f.a += 16;
                    if (this.f.b != 0 && this.f.b == this.f.c) {
                        stopProgramming();
                        return;
                    }
                    return;
                }
                this.j++;
                if (this.j > 100) {
                    Log.e("CC26xxOADProxy", "已连续100次发送失败，终止升级！");
                    stopProgramming();
                    return;
                }
                return;
            }
            stopProgramming();
        }
    }

    private boolean a(String str) {
        this.k = this.c.getBluetoothGatt(str);
        if (this.k != null) {
            BluetoothGattService service = this.k.getService(GattAttributes.TI_OAD_Service);
            if (service != null) {
                this.l = service.getCharacteristic(GattAttributes.TI_OAD_Image_Identify);
                this.m = service.getCharacteristic(GattAttributes.TI_OAD_Image_Block);
                this.l.setWriteType(1);
                this.m.setWriteType(1);
                this.c.setCharacteristicNotification(this.k, this.l, true);
                return true;
            }
            Log.e("CC26xxOADProxy", "OAD not supported: " + this.k.getDevice().getAddress());
        } else {
            Log.e("CC26xxOADProxy", "设备未连接，无法升级：" + str);
        }
        return false;
    }

    private void b() {
        if (this.h != null) {
            this.h.cancel();
            this.h.purge();
        }
        if (this.i != null) {
            this.i.cancel();
            this.i.purge();
        }
    }

    public void prepare(String str, String str2, boolean z) {
        if (this.d == State.programming) {
            throw new IllegalStateException("Can't prepare() in programming state.");
        }
        if (a(str) && a(str2, z) != -1) {
            this.d = State.prepared;
            if (this.b != null) {
                this.b.onPrepared(str);
            }
        }
    }

    public void release() {
        b();
        this.c.removeBleCallBack(this.n);
    }

    public void startProgramming(int i2) {
        if (this.d != State.prepared) {
            throw new IllegalStateException("start programming in illegal state: " + this.d + ", you should start programming in prepared state by call prepare()");
        }
        this.d = State.programming;
        this.l.setValue(this.g.a());
        this.k.writeCharacteristic(this.l);
        this.f.a();
        this.h = new Timer();
        this.h.scheduleAtFixedRate(new d(), 1000, 1000);
        this.i = new Timer();
        this.i.scheduleAtFixedRate(new c(), (long) i2, (long) i2);
    }

    public void stopProgramming() {
        b();
        if (this.k != null) {
            if (this.f.b == 0 || this.f.b != this.f.c) {
                if (this.d == State.programming) {
                    this.d = State.interrupted;
                } else {
                    this.d = State.idle;
                }
                if (this.b != null) {
                    this.b.onInterrupted(this.k.getDevice().getAddress(), this.f.a, this.f.c * 16, (long) this.f.d);
                    return;
                }
                return;
            }
            this.d = State.finished;
            if (this.b != null) {
                this.b.onFinished(this.k.getDevice().getAddress(), this.f.c * 16, (long) this.f.d);
            }
        }
    }
}
