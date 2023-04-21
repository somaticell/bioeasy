package com.ble.ble.constants;

import java.util.UUID;

public class BleUUIDS {
    public static final UUID[] CHARACTERS = {UUID.fromString("00001001-0000-1000-8000-00805f9b34fb"), UUID.fromString("00001002-0000-1000-8000-00805f9b34fb"), UUID.fromString("00001003-0000-1000-8000-00805f9b34fb"), UUID.fromString("00001004-0000-1000-8000-00805f9b34fb"), UUID.fromString("00001005-0000-1000-8000-00805f9b34fb")};
    public static final UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID PRIMARY_SERVICE = UUID.fromString("00001000-0000-1000-8000-00805f9b34fb");
}
