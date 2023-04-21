package com.ble.ble.util;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.util.Log;
import java.util.List;
import java.util.UUID;

public class GattUtil {
    public static BluetoothGattCharacteristic getGattCharacteristic(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2) {
        BluetoothGattService service;
        if (bluetoothGatt == null || uuid == null || uuid2 == null || (service = bluetoothGatt.getService(uuid)) == null) {
            return null;
        }
        return service.getCharacteristic(uuid2);
    }

    public static BluetoothGattDescriptor getGattDescriptor(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2, UUID uuid3) {
        BluetoothGattService service;
        BluetoothGattCharacteristic characteristic;
        if (bluetoothGatt == null || uuid == null || uuid2 == null || uuid3 == null || (service = bluetoothGatt.getService(uuid)) == null || (characteristic = service.getCharacteristic(uuid2)) == null) {
            return null;
        }
        return characteristic.getDescriptor(uuid3);
    }

    public static void logUuid(BluetoothGatt bluetoothGatt) {
        List<BluetoothGattService> services = bluetoothGatt.getServices();
        if (services != null) {
            for (BluetoothGattService next : services) {
                Log.e("GattUtil", "logUuid() - Service uuid: " + next.getUuid().toString());
                List<BluetoothGattCharacteristic> characteristics = next.getCharacteristics();
                if (characteristics != null) {
                    for (BluetoothGattCharacteristic next2 : characteristics) {
                        Log.i("GattUtil", "logUuid() - Characteristic uuid: " + next2.getUuid().toString() + ", properties = " + next2.getProperties());
                        List<BluetoothGattDescriptor> descriptors = next2.getDescriptors();
                        if (descriptors != null) {
                            for (BluetoothGattDescriptor uuid : descriptors) {
                                Log.w("GattUtil", "logUuid() - Descriptor uuid: " + uuid.getUuid().toString());
                            }
                        }
                    }
                }
            }
        }
    }
}
