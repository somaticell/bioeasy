package com.ble.ble.oad;

import com.ble.ble.BleService;

public class OADManager {
    public static OADProxy getOADProxy(BleService bleService, OADListener oADListener, OADType oADType) {
        switch (oADType) {
            case cc2541_oad:
            case cc2541_large_img_oad:
            case cc2640_on_chip_oad:
                return new CC2541OADProxy(bleService, oADListener, oADType);
            case cc2640_off_chip_oad:
                return new a(bleService, oADListener);
            case cc2640_oad_2_0_0:
            case cc2640_r2_oad:
                return new b(bleService, oADListener, oADType);
            default:
                return null;
        }
    }
}
