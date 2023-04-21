package com.ble.ble;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alipay.android.phone.mrpc.core.Headers;
import com.alipay.android.phone.mrpc.core.RpcException;
import com.baidu.mapapi.UIMsg;
import com.ble.api.DataUtil;
import com.ble.api.EncodeUtil;
import com.ble.ble.constants.BleRegConstants;
import com.ble.ble.constants.BleUUIDS;
import com.ble.ble.util.GattUtil;
import com.ble.gatt.GattAttributes;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public final class BleService extends Service {
    public static final int INIT_BLE_NOT_SUPPORTED = 1;
    public static final int INIT_BLUETOOTH_DISABLED = 3;
    public static final int INIT_BLUETOOTH_NOT_SUPPORTED = 2;
    public static final int INIT_SUCCESS = 0;
    private final String a = "BleService";
    private int b = 4;
    /* access modifiers changed from: private */
    public final List<BluetoothGatt> c = new ArrayList();
    /* access modifiers changed from: private */
    public final List<BleCallBack> d = new ArrayList();
    private BluetoothManager e = null;
    /* access modifiers changed from: private */
    public BluetoothAdapter f = null;
    /* access modifiers changed from: private */
    public BluetoothDevice g = null;
    /* access modifiers changed from: private */
    public boolean h = false;
    private int i = RpcException.ErrorCode.SERVER_OPERATIONTYPEMISSED;
    /* access modifiers changed from: private */
    public HashMap<String, Timer> j = new HashMap<>();
    /* access modifiers changed from: private */
    public int k;
    private LocalBinder l = new LocalBinder();
    /* access modifiers changed from: private */
    public List<String> m = new ArrayList();
    /* access modifiers changed from: private */
    public boolean n = false;
    /* access modifiers changed from: private */
    public boolean o = true;
    /* access modifiers changed from: private */
    public final BluetoothGattCallback p = new BluetoothGattCallback() {
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            String address = bluetoothGatt.getDevice().getAddress();
            byte[] value = bluetoothGattCharacteristic.getValue();
            if (bluetoothGattCharacteristic.getUuid().toString().equals(BleUUIDS.CHARACTERS[1].toString())) {
                if (BleService.this.o) {
                    value = new EncodeUtil().decodeMessage(value);
                    bluetoothGattCharacteristic.setValue(value);
                }
                byte[] bArr = value;
                for (BleCallBack onCharacteristicChanged : BleService.this.d) {
                    onCharacteristicChanged.onCharacteristicChanged(address, bArr);
                }
            }
            for (BleCallBack onCharacteristicChanged2 : BleService.this.d) {
                onCharacteristicChanged2.onCharacteristicChanged(address, bluetoothGattCharacteristic);
            }
        }

        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            String address = bluetoothGatt.getDevice().getAddress();
            if (bluetoothGattCharacteristic.getUuid().equals(BleUUIDS.CHARACTERS[3])) {
                String a2 = BleService.this.a(bluetoothGattCharacteristic.getValue());
                for (BleCallBack onRegRead : BleService.this.d) {
                    onRegRead.onRegRead(address, a2, BleService.this.k, i);
                }
            }
            for (BleCallBack onCharacteristicRead : BleService.this.d) {
                onCharacteristicRead.onCharacteristicRead(address, bluetoothGattCharacteristic, i);
            }
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            String address = bluetoothGatt.getDevice().getAddress();
            for (BleCallBack onCharacteristicWrite : BleService.this.d) {
                onCharacteristicWrite.onCharacteristicWrite(address, bluetoothGattCharacteristic, i);
            }
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            if (BleService.this.n) {
                String address = bluetoothGatt.getDevice().getAddress();
                if (i == 0) {
                    Log.d("BleService", "onConnectionStateChange() - " + address + ", status = " + i + ", newState=" + i2);
                    switch (i2) {
                        case 0:
                            if (BleService.this.g != null && BleService.this.g.getAddress().equals(address)) {
                                BleService.this.c();
                                boolean unused = BleService.this.h = false;
                            }
                            BleService.this.stopReadRssi(address);
                            for (BleCallBack onDisconnected : BleService.this.d) {
                                onDisconnected.onDisconnected(address);
                            }
                            BleService.this.closeBluetoothGatt(address);
                            if (BleService.this.f.isEnabled() && BleService.this.m.contains(address)) {
                                BleService.this.a(100);
                                return;
                            }
                            return;
                        case 2:
                            BleService.this.c();
                            boolean unused2 = BleService.this.h = false;
                            Log.i("BleService", "Connected: " + address + ", discoverServices: " + bluetoothGatt.discoverServices());
                            for (BleCallBack onConnected : BleService.this.d) {
                                onConnected.onConnected(address);
                            }
                            return;
                        default:
                            return;
                    }
                } else {
                    Log.w("BleService", "onConnectionStateChange() - " + address + ", status = " + i + ", newState=" + i2);
                    BleService.this.stopReadRssi(address);
                    if (i2 == 0) {
                        Log.d("BleService", address + " disconnected");
                        if (BleService.this.f.isEnabled() && BleService.this.m.contains(address)) {
                            BleService.this.a(100);
                        }
                        for (BleCallBack onDisconnected2 : BleService.this.d) {
                            onDisconnected2.onDisconnected(address);
                        }
                    }
                    for (BleCallBack onConnectionError : BleService.this.d) {
                        onConnectionError.onConnectionError(address, i, i2);
                    }
                    BleService.this.closeBluetoothGatt(address);
                }
            }
        }

        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            byte[] value = bluetoothGattDescriptor.getValue();
            String address = bluetoothGatt.getDevice().getAddress();
            String uuid = bluetoothGattDescriptor.getUuid().toString();
            Log.d("BleService", "onDescriptorRead() - " + address + " " + uuid + " status = " + i + " value[" + DataUtil.byteArrayToHex(value) + "]");
            for (BleCallBack onDescriptorRead : BleService.this.d) {
                onDescriptorRead.onDescriptorRead(address, bluetoothGattDescriptor, i);
            }
            if (BleUUIDS.CLIENT_CHARACTERISTIC_CONFIG.toString().equals(uuid)) {
                UUID uuid2 = bluetoothGattDescriptor.getCharacteristic().getService().getUuid();
                UUID uuid3 = bluetoothGattDescriptor.getCharacteristic().getUuid();
                if (value == null) {
                    return;
                }
                if (value == BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) {
                    for (BleCallBack onNotifyStateRead : BleService.this.d) {
                        onNotifyStateRead.onNotifyStateRead(uuid2, uuid3, true);
                    }
                } else if (value == BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE) {
                    for (BleCallBack onNotifyStateRead2 : BleService.this.d) {
                        onNotifyStateRead2.onNotifyStateRead(uuid2, uuid3, false);
                    }
                }
            }
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            Log.d("BleService", "onDescriptorWrite() - " + bluetoothGatt.getDevice().getAddress() + " " + bluetoothGattDescriptor.getUuid().toString() + " status = " + i + " value[" + DataUtil.byteArrayToHex(bluetoothGattDescriptor.getValue()) + "]");
        }

        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            String address = bluetoothGatt.getDevice().getAddress();
            Log.d("BleService", "onMtuChanged() - mtu=" + i + ", status=" + i2 + ", address=" + address);
            for (BleCallBack onMtuChanged : BleService.this.d) {
                onMtuChanged.onMtuChanged(address, i, i2);
            }
        }

        public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i, int i2) {
            String address = bluetoothGatt.getDevice().getAddress();
            Log.v("BleService", "onReadRemoteRssi() - " + address + " rssi = " + i + ", status = " + i2);
            for (BleCallBack onReadRemoteRssi : BleService.this.d) {
                onReadRemoteRssi.onReadRemoteRssi(address, i, i2);
            }
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            String address = bluetoothGatt.getDevice().getAddress();
            Log.d("BleService", "onServicesDiscovered() " + address + " - status = " + i);
            if (i == 0) {
                for (BleCallBack onServicesDiscovered : BleService.this.d) {
                    onServicesDiscovered.onServicesDiscovered(address);
                }
                return;
            }
            for (BleCallBack onServicesUndiscovered : BleService.this.d) {
                onServicesUndiscovered.onServicesUndiscovered(address, i);
            }
        }
    };
    private Timer q = null;
    private Timer r;
    private final BroadcastReceiver s = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10)) {
                    case 10:
                        Log.w("BleService", "Bluetooth off.");
                        return;
                    case 12:
                        if (BleService.this.m.size() > 0) {
                            BleService.this.a((int) UIMsg.m_AppUI.MSG_APP_SAVESCREEN);
                            return;
                        }
                        return;
                    case 13:
                        BleService.this.f();
                        Log.w("BleService", "Bluetooth turning off.");
                        return;
                    default:
                        return;
                }
            }
        }
    };

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public BleService getService(BleCallBack bleCallBack) {
            BleService.this.d.add(bleCallBack);
            return BleService.this;
        }
    }

    /* access modifiers changed from: private */
    public String a(byte[] bArr) {
        switch (this.k) {
            case 2:
            case 14:
            case 15:
            case 18:
            case 35:
            case 39:
                return String.valueOf(Integer.parseInt(String.format("%x", new Object[]{Byte.valueOf(bArr[0])}), 16));
            case 3:
            case 41:
                StringBuilder sb = new StringBuilder(bArr.length);
                for (byte b2 : bArr) {
                    if (b2 != 0) {
                        sb.append((char) (b2 & BLEServiceApi.CONNECTED_STATUS));
                    }
                }
                return sb.toString().trim();
            case 5:
                return String.format("%02X:%02X:%02X:%02X:%02X:%02X", new Object[]{Byte.valueOf(bArr[5]), Byte.valueOf(bArr[4]), Byte.valueOf(bArr[3]), Byte.valueOf(bArr[2]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[0])});
            case 16:
            case 17:
            case 36:
            case 37:
                return String.valueOf(Integer.valueOf(String.format("%02x%02x", new Object[]{Byte.valueOf(bArr[1]), Byte.valueOf(bArr[0])}), 16).intValue());
            case 19:
                String format = String.format(Locale.ROOT, "%02d%02d%02d", new Object[]{Byte.valueOf(bArr[2]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[0])});
                String format2 = String.format(Locale.ROOT, "%02d%02d ", new Object[]{Integer.valueOf(bArr[3] + 1), Integer.valueOf(bArr[4] + 1)});
                int parseInt = Integer.parseInt(String.format(Locale.ROOT, "%02x", new Object[]{Byte.valueOf(bArr[6])}) + String.format(Locale.ROOT, "%02x", new Object[]{Byte.valueOf(bArr[5])}), 16);
                String str = String.format(Locale.ROOT, "%04d", new Object[]{Integer.valueOf(parseInt)}) + format2 + format;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ROOT);
                long currentTimeMillis = System.currentTimeMillis();
                try {
                    currentTimeMillis = simpleDateFormat.parse(str).getTime();
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
                return String.valueOf(currentTimeMillis);
            case 34:
                String format3 = String.format("%x.%x", new Object[]{Byte.valueOf(bArr[1]), Byte.valueOf(bArr[0])});
                if (format3.charAt(0) == '0') {
                    format3 = format3.substring(1);
                }
                if (format3.charAt(format3.length() - 1) == '0') {
                    format3 = format3.substring(0, format3.length() - 1);
                }
                Log.d("BleService", "Firmware Version: " + format3);
                return format3;
            case 38:
                return String.format(Locale.ROOT, "%02X%02X%02X%02X", new Object[]{Byte.valueOf(bArr[3]), Byte.valueOf(bArr[2]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[0])});
            default:
                return "Unknown register data(" + this.k + "): " + DataUtil.byteArrayToHex(bArr);
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.q == null) {
            this.q = new Timer();
            this.q.schedule(new TimerTask() {
                public void run() {
                    BleService.this.b();
                }
            }, (long) this.i);
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        if (this.r == null) {
            this.r = new Timer();
            this.r.schedule(new TimerTask() {
                int a = 0;

                public void run() {
                    boolean z;
                    Log.d("BleService", "AutoConnectTimer running...");
                    if (!BleService.this.f.isEnabled() || BleService.this.m.size() == 0 || BleService.this.e()) {
                        BleService.this.d();
                        return;
                    }
                    boolean z2 = true;
                    while (z2) {
                        try {
                            String str = (String) BleService.this.m.get(this.a);
                            switch (BleService.this.getConnectionState(str)) {
                                case 2:
                                    z = z2;
                                    break;
                                default:
                                    BleService.this.connect(str, true);
                                    z = false;
                                    break;
                            }
                            if (this.a < BleService.this.m.size() - 1) {
                                this.a++;
                            } else {
                                this.a = 0;
                            }
                            z2 = z;
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                            this.a = 0;
                        }
                    }
                }
            }, (long) i2, (long) (this.i + 100));
        }
    }

    private void a(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (bluetoothGattCharacteristic != null) {
            String uuid = bluetoothGattCharacteristic.getUuid().toString();
            int properties = bluetoothGattCharacteristic.getProperties();
            if ((properties & 4) == 4) {
                Log.d("BleService", "setCharacteristicWriteType() - uuid: " + uuid + "[write_no_response]");
                bluetoothGattCharacteristic.setWriteType(1);
            } else if ((properties & 64) == 64) {
                Log.d("BleService", "setCharacteristicWriteType() - uuid: " + uuid + "[signed_write]");
                bluetoothGattCharacteristic.setWriteType(4);
            } else {
                Log.d("BleService", "setCharacteristicWriteType() - uuid: " + uuid + "[write]");
                bluetoothGattCharacteristic.setWriteType(2);
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean a(String str) {
        BluetoothGatt bluetoothGatt = getBluetoothGatt(str);
        BluetoothGattCharacteristic gattCharacteristic = GattUtil.getGattCharacteristic(bluetoothGatt, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[3]);
        if (gattCharacteristic != null) {
            return bluetoothGatt.readCharacteristic(gattCharacteristic);
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0009, code lost:
        r1 = getBluetoothGatt(r6);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.lang.String r6, int r7) {
        /*
            r5 = this;
            r0 = 0
            if (r7 < 0) goto L_0x0008
            byte[][] r1 = com.ble.ble.constants.BleRegConstants.REG
            int r1 = r1.length
            if (r7 < r1) goto L_0x0009
        L_0x0008:
            return r0
        L_0x0009:
            android.bluetooth.BluetoothGatt r1 = r5.getBluetoothGatt(r6)
            java.util.UUID r2 = com.ble.ble.constants.BleUUIDS.PRIMARY_SERVICE
            java.util.UUID[] r3 = com.ble.ble.constants.BleUUIDS.CHARACTERS
            r4 = 4
            r3 = r3[r4]
            android.bluetooth.BluetoothGattCharacteristic r2 = com.ble.ble.util.GattUtil.getGattCharacteristic(r1, r2, r3)
            if (r2 == 0) goto L_0x0008
            r0 = 1
            r2.setWriteType(r0)
            byte[][] r0 = com.ble.ble.constants.BleRegConstants.REG
            r0 = r0[r7]
            r2.setValue(r0)
            boolean r0 = r1.writeCharacteristic(r2)
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ble.ble.BleService.a(java.lang.String, int):boolean");
    }

    private boolean a(String str, boolean z) {
        if (str == null || this.h || !this.n) {
            Log.d("BleService", "connectByMac() - address=" + str + ", connecting=" + this.h + ", isActive" + this.n);
            return false;
        }
        this.h = true;
        try {
            final BluetoothDevice remoteDevice = this.f.getRemoteDevice(str);
            new Thread() {
                public void run() {
                    synchronized (BleService.this.c) {
                        if (BleService.this.n) {
                            try {
                                BleService.this.c.add(remoteDevice.connectGatt(BleService.this, false, BleService.this.p));
                                BluetoothDevice unused = BleService.this.g = remoteDevice;
                                BleService.this.a();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }.start();
            if (z) {
                if (!this.m.contains(str)) {
                    this.m.add(str);
                }
            } else if (this.m.contains(str)) {
                this.m.remove(str);
            }
            Log.d("BleService", "Create a new connection: " + str);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private byte[] a(int i2, int i3) {
        switch (i2) {
            case 2:
            case 14:
            case 15:
            case 18:
            case 39:
                return new byte[]{(byte) i3};
            case 16:
            case 17:
            case 36:
            case 37:
                return DataUtil.hexToReversedByteArray(String.format("%04x", new Object[]{Integer.valueOf(i3)}));
            case 38:
                return DataUtil.hexToReversedByteArray(String.format("%08x", new Object[]{Integer.valueOf(i3)}));
            default:
                return null;
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (!(this.e == null || this.g == null || this.e.getConnectionState(this.g, 7) == 2)) {
            closeBluetoothGatt(this.g.getAddress());
            if (this.m.contains(this.g.getAddress())) {
                a(100);
            } else {
                Log.d("BleService", "Connect timeout: " + this.g.getAddress());
                for (BleCallBack onConnectTimeout : this.d) {
                    onConnectTimeout.onConnectTimeout(this.g.getAddress());
                }
            }
        }
        this.h = false;
        c();
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.q != null) {
            this.q.cancel();
            this.q = null;
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        if (this.r != null) {
            this.r.cancel();
            this.r = null;
            Log.d("BleService", "stopAutoConnectTimer()");
        }
    }

    /* access modifiers changed from: private */
    public boolean e() {
        for (String connectionState : this.m) {
            if (getConnectionState(connectionState) != 2) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void f() {
        synchronized (this.c) {
            try {
                for (BluetoothGatt next : this.c) {
                    next.disconnect();
                    next.close();
                }
                this.c.clear();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public boolean addBleCallBack(BleCallBack bleCallBack) {
        if (this.d.contains(bleCallBack)) {
            return false;
        }
        this.d.add(bleCallBack);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0056, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0057, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void closeBluetoothGatt(java.lang.String r6) {
        /*
            r5 = this;
            r2 = -1
            java.util.List<android.bluetooth.BluetoothGatt> r3 = r5.c
            monitor-enter(r3)
            r1 = 0
        L_0x0005:
            java.util.List<android.bluetooth.BluetoothGatt> r0 = r5.c     // Catch:{ Exception -> 0x0056 }
            int r0 = r0.size()     // Catch:{ Exception -> 0x0056 }
            if (r1 >= r0) goto L_0x005e
            java.util.List<android.bluetooth.BluetoothGatt> r0 = r5.c     // Catch:{ Exception -> 0x0056 }
            java.lang.Object r0 = r0.get(r1)     // Catch:{ Exception -> 0x0056 }
            android.bluetooth.BluetoothGatt r0 = (android.bluetooth.BluetoothGatt) r0     // Catch:{ Exception -> 0x0056 }
            if (r0 == 0) goto L_0x0053
            android.bluetooth.BluetoothDevice r4 = r0.getDevice()     // Catch:{ Exception -> 0x0056 }
            java.lang.String r4 = r4.getAddress()     // Catch:{ Exception -> 0x0056 }
            boolean r4 = r4.equals(r6)     // Catch:{ Exception -> 0x0056 }
            if (r4 == 0) goto L_0x0053
            r0.disconnect()     // Catch:{ Exception -> 0x0056 }
            r0.close()     // Catch:{ Exception -> 0x0056 }
            r0 = r1
        L_0x002c:
            if (r0 == r2) goto L_0x0051
            java.util.List<android.bluetooth.BluetoothGatt> r1 = r5.c     // Catch:{ Exception -> 0x0056 }
            r1.remove(r0)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r0 = "BleService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0056 }
            r1.<init>()     // Catch:{ Exception -> 0x0056 }
            java.lang.String r2 = "closeBluetoothGatt() - mBluetoothGattList.size() = "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x0056 }
            java.util.List<android.bluetooth.BluetoothGatt> r2 = r5.c     // Catch:{ Exception -> 0x0056 }
            int r2 = r2.size()     // Catch:{ Exception -> 0x0056 }
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0056 }
            android.util.Log.d(r0, r1)     // Catch:{ Exception -> 0x0056 }
        L_0x0051:
            monitor-exit(r3)     // Catch:{ all -> 0x005b }
            return
        L_0x0053:
            int r1 = r1 + 1
            goto L_0x0005
        L_0x0056:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x005b }
            goto L_0x0051
        L_0x005b:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x005b }
            throw r0
        L_0x005e:
            r0 = r2
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ble.ble.BleService.closeBluetoothGatt(java.lang.String):void");
    }

    public void connect(List<String> list) {
        if (list != null && list.size() != 0) {
            int i2 = 0;
            while (i2 < list.size() && this.m.size() < this.b) {
                try {
                    if (!this.m.contains(list.get(i2))) {
                        this.m.add(list.get(i2));
                    }
                    i2++;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            a(100);
        }
    }

    public boolean connect(String str, boolean z) {
        synchronized (this.c) {
            try {
                for (BluetoothGatt device : this.c) {
                    if (device.getDevice().getAddress().equals(str)) {
                        Log.w("BleService", "Connecting " + str + " or have connected.");
                        return false;
                    }
                }
                if (this.c.size() < this.b) {
                    boolean a2 = a(str, z);
                    return a2;
                }
                Log.w("BleService", "connect() - Have connected " + this.c.size() + " devices!");
                return false;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void disconnect(String str) {
        synchronized (this.c) {
            try {
                Iterator<BluetoothGatt> it = this.c.iterator();
                while (true) {
                    if (it.hasNext()) {
                        BluetoothGatt next = it.next();
                        if (next.getDevice().getAddress().equals(str)) {
                            next.disconnect();
                            break;
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public boolean enableCharacteristicIndication(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (bluetoothGatt == null) {
            Log.w("BleService", "enableCharacteristicIndication() - gatt is null");
            return false;
        } else if (bluetoothGattCharacteristic == null) {
            Log.w("BleService", "enableCharacteristicIndication() - characteristic is null");
            return false;
        } else {
            BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(GattAttributes.Client_Characteristic_Configuration);
            if (descriptor != null) {
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                bluetoothGatt.writeDescriptor(descriptor);
            }
            return bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, true);
        }
    }

    @Deprecated
    public boolean enableNotification(String str) {
        BluetoothGatt bluetoothGatt = getBluetoothGatt(str);
        return setCharacteristicNotification(bluetoothGatt, GattUtil.getGattCharacteristic(bluetoothGatt, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[1]), true);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.bluetooth.BluetoothGatt getBluetoothGatt(java.lang.String r5) {
        /*
            r4 = this;
            java.util.List<android.bluetooth.BluetoothGatt> r1 = r4.c
            monitor-enter(r1)
            java.util.List<android.bluetooth.BluetoothGatt> r0 = r4.c     // Catch:{ Exception -> 0x0025 }
            java.util.Iterator r2 = r0.iterator()     // Catch:{ Exception -> 0x0025 }
        L_0x0009:
            boolean r0 = r2.hasNext()     // Catch:{ Exception -> 0x0025 }
            if (r0 == 0) goto L_0x0029
            java.lang.Object r0 = r2.next()     // Catch:{ Exception -> 0x0025 }
            android.bluetooth.BluetoothGatt r0 = (android.bluetooth.BluetoothGatt) r0     // Catch:{ Exception -> 0x0025 }
            android.bluetooth.BluetoothDevice r3 = r0.getDevice()     // Catch:{ Exception -> 0x0025 }
            java.lang.String r3 = r3.getAddress()     // Catch:{ Exception -> 0x0025 }
            boolean r3 = r3.equals(r5)     // Catch:{ Exception -> 0x0025 }
            if (r3 == 0) goto L_0x0009
            monitor-exit(r1)     // Catch:{ all -> 0x002c }
        L_0x0024:
            return r0
        L_0x0025:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x002c }
        L_0x0029:
            monitor-exit(r1)     // Catch:{ all -> 0x002c }
            r0 = 0
            goto L_0x0024
        L_0x002c:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x002c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ble.ble.BleService.getBluetoothGatt(java.lang.String):android.bluetooth.BluetoothGatt");
    }

    public int getConnectTimeout() {
        return this.i;
    }

    public List<BluetoothDevice> getConnectedDevices() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.c) {
            try {
                for (BluetoothGatt device : this.c) {
                    BluetoothDevice device2 = device.getDevice();
                    if (this.e.getConnectionState(device2, 7) == 2) {
                        arrayList.add(device2);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return arrayList;
    }

    public int getConnectedNumber() {
        int i2 = 0;
        synchronized (this.c) {
            try {
                for (BluetoothGatt device : this.c) {
                    i2 = this.e.getConnectionState(device.getDevice(), 7) == 2 ? i2 + 1 : i2;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return i2;
    }

    public int getConnectionState(String str) {
        try {
            return this.e.getConnectionState(this.f.getRemoteDevice(str), 7);
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public int getMaxConnectedNumber() {
        return this.b;
    }

    public int initialize() {
        if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            Log.w("BleService", "initialize() - BLE is not supported.");
            return 1;
        }
        if (this.e == null) {
            this.e = (BluetoothManager) getSystemService("bluetooth");
            if (this.e == null) {
                Log.w("BleService", "initialize() - Unable to initialize BluetoothManager.");
                return 2;
            }
        }
        this.f = this.e.getAdapter();
        if (this.f == null) {
            Log.w("BleService", "initialize() - Unable to obtain a BluetoothAdapter.");
            return 2;
        } else if (this.f.isEnabled()) {
            return 0;
        } else {
            Log.w("BleService", "initialize() - Bluetooth is disabled.");
            return 3;
        }
    }

    public IBinder onBind(Intent intent) {
        return this.l;
    }

    public void onCreate() {
        super.onCreate();
        registerReceiver(this.s, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        this.n = true;
    }

    public void onDestroy() {
        super.onDestroy();
        this.n = false;
        this.d.clear();
        c();
        d();
        f();
        stopReadRssi();
        unregisterReceiver(this.s);
    }

    public boolean read(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null) {
            return false;
        }
        return bluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
    }

    public boolean readNotifyState(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGattDescriptor descriptor;
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null || (descriptor = bluetoothGattCharacteristic.getDescriptor(BleUUIDS.CLIENT_CHARACTERISTIC_CONFIG)) == null) {
            return false;
        }
        return bluetoothGatt.readDescriptor(descriptor);
    }

    public boolean readNotifyState(String str, UUID uuid, UUID uuid2) {
        BluetoothGattService service;
        BluetoothGatt bluetoothGatt = getBluetoothGatt(str);
        if (bluetoothGatt == null || (service = bluetoothGatt.getService(uuid)) == null) {
            return false;
        }
        return readNotifyState(bluetoothGatt, service.getCharacteristic(uuid2));
    }

    public void readReg(final String str, int i2) {
        if (i2 < 0 || i2 >= BleRegConstants.REG.length) {
            Log.w("BleService", "Unknown regFlag");
            return;
        }
        this.k = i2;
        a(str, i2);
        new Timer().schedule(new TimerTask() {
            public void run() {
                boolean unused = BleService.this.a(str);
            }
        }, 30);
    }

    public boolean refresh(String str) {
        try {
            BluetoothGatt bluetoothGatt = getBluetoothGatt(str);
            if (bluetoothGatt == null) {
                return false;
            }
            Method method = bluetoothGatt.getClass().getMethod(Headers.REFRESH, new Class[0]);
            if (method != null) {
                boolean booleanValue = ((Boolean) method.invoke(bluetoothGatt, new Object[0])).booleanValue();
                if (booleanValue) {
                    Log.i("BleService", "refresh() - success!");
                    return booleanValue;
                }
                Log.e("BleService", "refresh() - failed!");
                return booleanValue;
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void removeBleCallBack(BleCallBack bleCallBack) {
        this.d.remove(bleCallBack);
    }

    public boolean requestConnectionPriority(String str, int i2) {
        BluetoothGatt bluetoothGatt;
        if (Build.VERSION.SDK_INT < 21 || (bluetoothGatt = getBluetoothGatt(str)) == null) {
            return false;
        }
        return bluetoothGatt.requestConnectionPriority(i2);
    }

    public boolean requestMtu(String str, int i2) {
        BluetoothGatt bluetoothGatt;
        if (Build.VERSION.SDK_INT < 21 || (bluetoothGatt = getBluetoothGatt(str)) == null) {
            return false;
        }
        return bluetoothGatt.requestMtu(i2);
    }

    public boolean send(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, String str, boolean z) {
        return send(bluetoothGatt, bluetoothGattCharacteristic, DataUtil.hexToByteArray(str), z);
    }

    public boolean send(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, boolean z) {
        if (bluetoothGatt == null || bArr == null || bArr.length <= 0 || bluetoothGattCharacteristic == null) {
            return false;
        }
        a(bluetoothGattCharacteristic);
        if (z) {
            bArr = new EncodeUtil().encodeMessage(bArr);
        }
        bluetoothGattCharacteristic.setValue(bArr);
        return bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
    }

    @Deprecated
    public boolean send(String str, String str2) {
        return send(str, str2, true);
    }

    public boolean send(String str, String str2, boolean z) {
        return send(str, DataUtil.hexToByteArray(str2), z);
    }

    @Deprecated
    public boolean send(String str, byte[] bArr) {
        return send(str, bArr, true);
    }

    public boolean send(String str, byte[] bArr, boolean z) {
        BluetoothGatt bluetoothGatt = getBluetoothGatt(str);
        return send(bluetoothGatt, GattUtil.getGattCharacteristic(bluetoothGatt, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[0]), bArr, z);
    }

    public void setAdvMfr(final String str, final String str2) {
        if (str2 != null && str2.length() != 0 && str2.length() <= 20) {
            a(str, 41);
            new Timer().schedule(new TimerTask() {
                public void run() {
                    BluetoothGatt bluetoothGatt = BleService.this.getBluetoothGatt(str);
                    BleService.this.send(bluetoothGatt, GattUtil.getGattCharacteristic(bluetoothGatt, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[2]), str2.getBytes(), false);
                }
            }, 30);
        }
    }

    public void setAutoConnect(String str, boolean z) {
        if (str != null) {
            if (z && !this.m.contains(str)) {
                this.m.add(str);
            } else if (!z && this.m.contains(str)) {
                this.m.remove(str);
                if (this.m.isEmpty()) {
                    d();
                    b();
                }
            }
        }
    }

    public boolean setCharacteristicNotification(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z) {
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null) {
            return false;
        }
        BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(BleUUIDS.CLIENT_CHARACTERISTIC_CONFIG);
        if (descriptor != null) {
            descriptor.setValue(z ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            bluetoothGatt.writeDescriptor(descriptor);
        }
        return bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, z);
    }

    public void setConnectTimeout(int i2) {
        if (i2 >= 2500) {
            this.i = i2;
        }
    }

    public void setDecode(boolean z) {
        this.o = z;
    }

    public void setMaxConnectedNumber(int i2) {
        this.b = i2;
    }

    public void setReg(final String str, int i2, int i3) {
        if (i2 < 0 || i2 >= BleRegConstants.REG.length) {
            Log.w("BleService", "Unknown regFlag: " + i2);
            return;
        }
        a(str, i2);
        final byte[] a2 = a(i2, i3);
        new Timer().schedule(new TimerTask() {
            public void run() {
                BluetoothGatt bluetoothGatt = BleService.this.getBluetoothGatt(str);
                BleService.this.send(bluetoothGatt, GattUtil.getGattCharacteristic(bluetoothGatt, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[2]), a2, false);
            }
        }, 30);
    }

    public void setSlaverName(final String str, final String str2) {
        if (str2 != null && str2.length() != 0 && str2.length() <= 20) {
            a(str, 3);
            new Timer().schedule(new TimerTask() {
                public void run() {
                    BluetoothGatt bluetoothGatt = BleService.this.getBluetoothGatt(str);
                    BleService.this.send(bluetoothGatt, GattUtil.getGattCharacteristic(bluetoothGatt, BleUUIDS.PRIMARY_SERVICE, BleUUIDS.CHARACTERS[2]), str2.getBytes(), false);
                }
            }, 30);
        }
    }

    public void startReadRssi(int i2) {
        if (i2 >= 1) {
            synchronized (this.c) {
                try {
                    for (BluetoothGatt device : this.c) {
                        BluetoothDevice device2 = device.getDevice();
                        if (this.e.getConnectionState(device2, 7) == 2) {
                            startReadRssi(device2.getAddress(), i2);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r2 = getBluetoothGatt(r7);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startReadRssi(final java.lang.String r7, int r8) {
        /*
            r6 = this;
            r0 = 1
            if (r8 >= r0) goto L_0x0004
        L_0x0003:
            return
        L_0x0004:
            android.bluetooth.BluetoothGatt r2 = r6.getBluetoothGatt(r7)
            if (r2 == 0) goto L_0x0003
            java.util.HashMap<java.lang.String, java.util.Timer> r0 = r6.j
            java.lang.Object r0 = r0.get(r7)
            java.util.Timer r0 = (java.util.Timer) r0
            if (r0 != 0) goto L_0x0003
            java.util.Timer r0 = new java.util.Timer
            r0.<init>()
            com.ble.ble.BleService$10 r1 = new com.ble.ble.BleService$10
            r1.<init>(r7, r2)
            r2 = 0
            long r4 = (long) r8
            r0.schedule(r1, r2, r4)
            java.util.HashMap<java.lang.String, java.util.Timer> r1 = r6.j
            r1.put(r7, r0)
            java.lang.String r0 = "BleService"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "startReadRssi() - "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r7)
            java.lang.String r1 = r1.toString()
            android.util.Log.i(r0, r1)
            goto L_0x0003
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ble.ble.BleService.startReadRssi(java.lang.String, int):void");
    }

    public void stopReadRssi() {
        for (Map.Entry<String, Timer> value : this.j.entrySet()) {
            ((Timer) value.getValue()).cancel();
        }
    }

    public void stopReadRssi(String str) {
        Timer timer = this.j.get(str);
        if (timer != null) {
            timer.cancel();
            this.j.remove(str);
        }
    }
}
