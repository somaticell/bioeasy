package com.ble.ble.oad;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.util.Log;
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

public final class CC2541OADProxy extends OADProxy {
    /* access modifiers changed from: private */
    public final c f = new c();
    /* access modifiers changed from: private */
    public b g = new b();
    /* access modifiers changed from: private */
    public b h = new b();
    private Timer i = null;
    private Timer j = null;
    private int k = 0;
    /* access modifiers changed from: private */
    public boolean l;
    /* access modifiers changed from: private */
    public String m;
    /* access modifiers changed from: private */
    public BluetoothGatt n = null;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic o = null;
    private BluetoothGattCharacteristic p = null;
    private final BleCallBack q = new BleCallBack() {
        public void onCharacteristicChanged(String str, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            if (GattAttributes.TI_OAD_Image_Identify.toString().equals(bluetoothGattCharacteristic.getUuid().toString()) && CC2541OADProxy.this.n != null && CC2541OADProxy.this.n.getDevice().getAddress().equals(str)) {
                byte[] value = bluetoothGattCharacteristic.getValue();
                Log.i("CC2541OADProxy", "CC2541 OAD Image Identify Rx: " + str + '[' + DataUtil.byteArrayToHex(value) + ']');
                CC2541OADProxy.this.h.a = DataUtil.buildUint16(value[1], value[0]);
                CC2541OADProxy.this.h.d = Character.valueOf((CC2541OADProxy.this.h.a & 1) == 1 ? 'B' : 'A');
                CC2541OADProxy.this.h.b = DataUtil.buildUint16(value[3], value[2]);
                if (CC2541OADProxy.this.d == State.waitingImgInfo) {
                    int unused = CC2541OADProxy.this.a(CC2541OADProxy.this.m, CC2541OADProxy.this.l);
                }
            }
        }
    };

    private class a extends TimerTask {
        int a;

        private a() {
            this.a = 0;
        }

        public void run() {
            switch (this.a) {
                case 0:
                    CC2541OADProxy.this.o.setValue(new byte[]{0});
                    Log.d("CC2541OADProxy", "write 0: " + CC2541OADProxy.this.n.writeCharacteristic(CC2541OADProxy.this.o));
                    break;
                case 1:
                    if (CC2541OADProxy.this.e != OADType.cc2541_oad) {
                        Log.d("CC2541OADProxy", "$GetTargetImgInfoTask.run() - OAD Type: " + CC2541OADProxy.this.e.toString());
                        Log.w("CC2541OADProxy", "$GetTargetImgInfoTask.cancel(): " + cancel());
                        break;
                    } else {
                        Log.d("CC2541OADProxy", "&GetTargetImgInfoTask.run() - mState = " + CC2541OADProxy.this.d.toString());
                        if (CC2541OADProxy.this.d == State.waitingImgInfo) {
                            CC2541OADProxy.this.o.setValue(new byte[]{1});
                            Log.d("CC2541OADProxy", "write 1: " + CC2541OADProxy.this.n.writeCharacteristic(CC2541OADProxy.this.o));
                            break;
                        }
                    }
                    break;
                default:
                    Log.w("CC2541OADProxy", "$GetTargetImgInfoTask.cancel(): " + cancel());
                    break;
            }
            this.a++;
        }
    }

    private class b {
        short a;
        int b;
        byte[] c;
        Character d;

        private b() {
            this.c = new byte[4];
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
            this.b = 0;
            this.d = 0;
            this.c = (short) (CC2541OADProxy.this.g.b / 4);
        }
    }

    private class d extends TimerTask {
        private d() {
        }

        public void run() {
            CC2541OADProxy.this.b();
        }
    }

    private class e extends TimerTask {
        private e() {
        }

        public void run() {
            CC2541OADProxy.this.f.d += 1000;
            if (CC2541OADProxy.this.b != null) {
                CC2541OADProxy.this.b.onProgressChanged(CC2541OADProxy.this.n.getDevice().getAddress(), CC2541OADProxy.this.f.a, CC2541OADProxy.this.f.c * 16, (long) CC2541OADProxy.this.f.d);
            }
        }
    }

    @Deprecated
    public CC2541OADProxy(Context context, BleService bleService, OADListener oADListener) {
        super(bleService, oADListener);
        this.e = OADType.cc2541_oad;
        this.c.addBleCallBack(this.q);
    }

    CC2541OADProxy(BleService bleService, OADListener oADListener, OADType oADType) {
        super(bleService, oADListener);
        this.e = oADType;
        this.c.addBleCallBack(this.q);
    }

    /* access modifiers changed from: private */
    public int a(String str, boolean z) {
        InputStream fileInputStream;
        boolean z2 = true;
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
        this.g.a = DataUtil.buildUint16(this.a[5], this.a[4]);
        this.g.b = DataUtil.buildUint16(this.a[7], this.a[6]) & 65535;
        this.g.d = Character.valueOf((this.g.a & 1) == 1 ? 'B' : 'A');
        System.arraycopy(this.a, 8, this.g.c, 0, 4);
        if (this.g.d == this.h.d) {
            z2 = false;
        }
        if (z2) {
            this.d = State.prepared;
            if (this.b == null) {
                return read;
            }
            this.b.onPrepared(this.n.getDevice().getAddress());
            return read;
        }
        Log.e("CC2541OADProxy", "mFileImgHdr.imgType: " + this.g.d);
        Log.e("CC2541OADProxy", "mTargImgHdr.imgType: " + this.h.d);
        this.d = State.idle;
        return read;
    }

    private void a() {
        new Timer().schedule(new a(), 100, 100);
    }

    private boolean a(String str) {
        this.n = this.c.getBluetoothGatt(str);
        if (this.n != null) {
            BluetoothGattService service = this.n.getService(GattAttributes.TI_OAD_Service);
            if (service != null) {
                this.o = service.getCharacteristic(GattAttributes.TI_OAD_Image_Identify);
                this.p = service.getCharacteristic(GattAttributes.TI_OAD_Image_Block);
                this.o.setWriteType(1);
                this.p.setWriteType(1);
                this.c.setCharacteristicNotification(this.n, this.o, true);
                this.d = State.waitingImgInfo;
                a();
                return true;
            }
            Log.e("CC2541OADProxy", "OAD not supported: " + this.n.getDevice().getAddress());
        } else {
            Log.e("CC2541OADProxy", "设备未连接，无法升级：" + str);
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void b() {
        if (isProgramming()) {
            if (this.f.b < this.f.c) {
                byte[] bArr = new byte[18];
                bArr[0] = DataUtil.loUint16(this.f.b);
                bArr[1] = DataUtil.hiUint16(this.f.b);
                System.arraycopy(this.a, this.f.a, bArr, 2, 16);
                this.p.setValue(bArr);
                if (this.n.writeCharacteristic(this.p)) {
                    if (this.b != null) {
                        this.b.onBlockWrite(bArr);
                    }
                    this.k = 0;
                    c cVar = this.f;
                    cVar.b = (short) (cVar.b + 1);
                    this.f.a += 16;
                    if (this.f.b != 0 && this.f.b == this.f.c) {
                        stopProgramming();
                        return;
                    }
                    return;
                }
                this.k++;
                if (this.k > 100) {
                    Log.e("CC2541OADProxy", "已连续100次发送失败，终止升级！");
                    stopProgramming();
                    return;
                }
                return;
            }
            stopProgramming();
        }
    }

    private void c() {
        if (this.i != null) {
            this.i.cancel();
            this.i.purge();
        }
        if (this.j != null) {
            this.j.cancel();
            this.j.purge();
        }
    }

    public void prepare(String str, String str2, boolean z) {
        if (this.d == State.programming || this.d == State.waitingImgInfo) {
            throw new IllegalStateException("Can't prepare() in " + this.d.toString() + " state.");
        }
        this.m = str2;
        this.l = z;
        a(str);
    }

    public void release() {
        c();
        this.c.removeBleCallBack(this.q);
    }

    public void startProgramming(int i2) {
        if (this.d != State.prepared) {
            throw new IllegalStateException("start programming in illegal state: " + this.d + ", you should start programming in prepared state by call prepare()");
        }
        this.d = State.programming;
        byte[] bArr = new byte[12];
        bArr[0] = DataUtil.loUint16(this.g.a);
        bArr[1] = DataUtil.hiUint16(this.g.a);
        bArr[2] = DataUtil.loUint16((short) this.g.b);
        bArr[3] = DataUtil.hiUint16((short) this.g.b);
        System.arraycopy(this.g.c, 0, bArr, 4, 4);
        this.o.setValue(bArr);
        this.n.writeCharacteristic(this.o);
        this.f.a();
        this.i = new Timer();
        this.i.scheduleAtFixedRate(new e(), 1000, 1000);
        this.j = new Timer();
        this.j.scheduleAtFixedRate(new d(), (long) i2, (long) i2);
    }

    public void stopProgramming() {
        c();
        if (this.n != null) {
            if (this.f.b == 0 || this.f.b != this.f.c) {
                if (this.d == State.programming) {
                    this.d = State.interrupted;
                } else {
                    this.d = State.idle;
                }
                if (this.b != null) {
                    this.b.onInterrupted(this.n.getDevice().getAddress(), this.f.a, this.f.c * 16, (long) this.f.d);
                    return;
                }
                return;
            }
            this.d = State.finished;
            if (this.b != null) {
                this.b.onFinished(this.n.getDevice().getAddress(), this.f.c * 16, (long) this.f.d);
            }
        }
    }
}
