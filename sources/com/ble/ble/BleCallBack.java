package com.ble.ble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import java.util.UUID;

public abstract class BleCallBack {
    public void onCharacteristicChanged(String str, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    public void onCharacteristicChanged(String str, byte[] bArr) {
    }

    public void onCharacteristicRead(String str, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
    }

    @Deprecated
    public void onCharacteristicRead(String str, byte[] bArr, int i) {
    }

    public void onCharacteristicWrite(String str, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
    }

    public void onConnectTimeout(String str) {
    }

    public void onConnected(String str) {
    }

    public void onConnectionError(String str, int i, int i2) {
    }

    public void onDescriptorRead(String str, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
    }

    public void onDisconnected(String str) {
    }

    public void onMtuChanged(String str, int i, int i2) {
    }

    public void onNotifyStateRead(UUID uuid, UUID uuid2, boolean z) {
    }

    public void onReadRemoteRssi(String str, int i, int i2) {
    }

    public void onRegRead(String str, String str2, int i, int i2) {
    }

    public void onServicesDiscovered(String str) {
    }

    public void onServicesUndiscovered(String str, int i) {
    }
}
